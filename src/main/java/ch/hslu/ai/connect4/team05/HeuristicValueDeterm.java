package ch.hslu.ai.connect4.team05;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import javax.print.attribute.standard.MediaSize.Other;
import javax.swing.JList.DropLocation;
import javax.xml.crypto.Data;

import ch.hslu.ai.connect4.Game;
import ch.hslu.ai.connect4.team05.PatternMatcher.Position;

public class HeuristicValueDeterm implements Function<char[][], Double> {

    private char player;
    private char otherPlayer;

    private final static int PLAYER = 0; 
    private final static int OTHER_PLAYER = 1; 

    private class MultiPatterns {
        private final PatternMatcher[] patterns;

        MultiPatterns(char s, char player) {
            patterns = new PatternMatcher[]{
                createColumnPattern(s, player),
                createDiagonalLeftRightPattern(s, player),
                createDiagonalRightLeftPattern(s, player),
                createRowPattern(s, player)
            };
        }

        int match(char[][] board) {
            return 
                patterns[0].match(board) +
                patterns[1].match(board) +
                patterns[2].match(board) +
                patterns[3].match(board);
        }

        List<Position> getMatches() {
            List<Position> matches = patterns[0].getMatches();
            matches.addAll(patterns[1].getMatches());
            matches.addAll(patterns[2].getMatches());
            matches.addAll(patterns[3].getMatches());
            return matches;
        }
    }

    /**
     * Class proviedes value for next move is a win for given player
     */
    private class MatchNextMoveWin {
        final MultiPatterns multiPatterns;

        MatchNextMoveWin(char player) {
            multiPatterns = new MultiPatterns('1', player);
        }

        Double match(char[][] board) {
            if(multiPatterns.match(board) > 1) {
                return 1.0;
            } else {
                return 0.0;
            }
        }
    }

    /**
     * Matcher which counts how many patterns with next move win exist
     */
    private final MatchNextMoveWin[] matchNextMoveWin = new MatchNextMoveWin[2];

    /**
     * Matcher which counts how many patterns with two moves for win exist
     */
    private MultiPatterns[] twoEmptySpace = new MultiPatterns[2];

    /**
     * Matcher which counts how many patterns with three moves for win exist
     */
    private MultiPatterns[] threeEmptySpace = new MultiPatterns[2];

    private static final char GROUND = '#';

    public static PatternMatcher createColumnPattern(char s, char player) {
        return new PatternMatcher(new char[][]{
            { s , s , s , s , '$'}
        }, player);
    }

    public static PatternMatcher createRowPattern(char s, char player) {
        return new PatternMatcher(new char[][]{
            { s , '$'},
            { s , '$'},
            { s , '$'},
            { s , '$'},
        }, player);
    }

    public static PatternMatcher createDiagonalRightLeftPattern(char s, char player) {
        return new PatternMatcher(new char[][]{
            { s ,'$','.','.','.'},
            {'.', s ,'$','.','.'},
            {'.','.', s ,'$','.'},
            {'.','.','.', s ,'$'},
        }, player);
    }

    public static PatternMatcher createDiagonalLeftRightPattern(char s, char player) {
        return new PatternMatcher(new char[][]{
            {'.','.','.', s ,'$'},
            {'.','.', s ,'$','.'},
            {'.', s ,'$','.','.'},
            { s ,'$','.','.','.'},
        }, player);
    }

    public static char[][] appendGround(final char[][] board) {
        char[][] copy = new char[board.length][];
        for(int i = 0; i < board.length; i++) {
            copy[i] = Arrays.copyOf(board[i], board[i].length + 1);
            copy[i][copy[i].length - 1] = GROUND;
        }
        return copy;
    }

    public HeuristicValueDeterm(char player, char otherPlayer) {
        this.player = player;
        this.otherPlayer = otherPlayer;

        this.matchNextMoveWin[PLAYER] = new MatchNextMoveWin(player);
        this.matchNextMoveWin[OTHER_PLAYER] = new MatchNextMoveWin(otherPlayer);

        this.twoEmptySpace[PLAYER] = new MultiPatterns('2', player);
        this.twoEmptySpace[OTHER_PLAYER] = new MultiPatterns('2', otherPlayer);

        this.threeEmptySpace[PLAYER] = new MultiPatterns('3', player);
        this.threeEmptySpace[OTHER_PLAYER] = new MultiPatterns('3', otherPlayer);
    }

    /**
     * Compares player output and returns value (pos if player wins, negative if otherPlayer wins) 
     * if one matches more als other.
     * If both have same number of matches, return 0.0
     */
    private Double comparePlayer(char[][] board, MultiPatterns[] patterns, Double value) {
        int playerMatches = patterns[PLAYER].match(board);
        int otherPlayerMatches = patterns[OTHER_PLAYER].match(board);

        if(playerMatches > otherPlayerMatches) return value;
        if(otherPlayerMatches > playerMatches) return -value;
        return 0.0;
    }

    @Override
    public Double apply(char[][] board) {
        board = appendGround(board);

        Double value = 0.0;

        // check if it is possible to win next round
        if((value = this.matchNextMoveWin[PLAYER].match(board)) > 0) return value;
        if((value = this.matchNextMoveWin[OTHER_PLAYER].match(board)) > 0) return -value;

        if((value = comparePlayer(board, this.twoEmptySpace, 0.5)) != 0.0) return value;
//        if((value = comparePlayer(board, this.threeEmptySpace, 0.25)) != 0.0) return value;

        return value;
    }

}