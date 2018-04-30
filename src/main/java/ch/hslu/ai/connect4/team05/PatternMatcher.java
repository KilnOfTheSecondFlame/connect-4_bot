package ch.hslu.ai.connect4.team05;

import java.net.PortUnreachableException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.DoubleFunction;
import java.util.function.Function;

/**
 * Used to match given patterns for a given space
 * Pattern can be defined as a char, on which 
 * - '.' empty is nothing / not part of the pattern,
 * - '$' is any kind of player (a-z) 
 * - '-' is an empty slot on board
 * - '0' is own player in pattern
 * - '#' can be used by the board as border indication
 * - '1-9' is how many times it can be empty in on this spot - it will count.
 *         it will not count up if own player is there, but will fail when if it is not empty
 *         and not own player on this spot
 *         be sure to ONLY use ONE number
 */
public class PatternMatcher {
    final char player;
    final Pattern pattern;
    
    public static class Position {
        public final int Column;
        public final int Row;

        public Position(int Column, int Row) {
            this.Column = Column;
            this.Row = Row;
        }

        @Override
        public String toString() {
            return new StringBuilder()
                .append('[')
                .append(Column)
                .append(',')
                .append(Row)
                .append(']').toString();
        }
    }

    public static class Pattern {
        private final char[][] part;
        private final char player;
        
        public Pattern(char[][] part, char player) {
            this.part = part;
            this.player = player;
        }
        
        @Override
        public boolean equals(Object other) {
            if (other == null) return false;
            if (other == this) return true;
            if (other instanceof Pattern) return Arrays.deepEquals(this.part,((Pattern)other).part);
            if (!(other instanceof char[][])) return false;

            char[][] board = (char[][])other;
            if(part.length != board.length || part[0].length != board[0].length) {
                return false;
            }

            int emptyCounter = 0;
            for (int i = 0; i < part.length; i++) {
                for (int j = 0; j < part[0].length; j++) {
                    switch (part[i][j]) {
                        case '$':
                            if (
                                !(board[i][j] >= 'a' && board[i][j] <= 'z') &&
                                !(board[i][j] >= 'A' && board[i][j] <= 'Z') &&
                                !(board[i][j] == '#')
                            ) return false;
                            break;
                        case '0':
                            if (board[i][j] != player) return false;
                            break;
                        case '-':
                            if (board[i][j] != '-') return false;
                            break;
                        default:
                            // add dynamic to have a number of empty places
                            if(part[i][j] >= '1' && part[i][j] <= '9') {
                                if(board[i][j] == '-') {
                                    if(++emptyCounter > (part[i][j] - '0')) return false;
                                } else if(board[i][j] != player) {
                                    return false;
                                }
                            }
                            break;
                    }
                }
            }
            return true;
        }

    }

    public static final char[][] HORIZONTAL_LINE = new char[][] {
        "0000".toCharArray()
    };

    public static final char[][] VERTICAL_LINE = new char[][] {
        {'0'},
        {'0'},
        {'0'},
        {'0'}
    };

    public static final char[][] DIAGONAL_LEFT_RIGHT_LINE = new char[][] {
        "0...".toCharArray(),
        ".0..".toCharArray(),
        "..0.".toCharArray(),
        "...0".toCharArray(),
    };

    public static final char[][] DIAGONAL_RIGHT_LEFT_LINE = new char[][] {
        "...0".toCharArray(),
        "..0.".toCharArray(),
        ".0..".toCharArray(),
        "0...".toCharArray(),
    };

    public PatternMatcher(char[][] pattern, char player) {
        this.pattern = new Pattern(pattern, player);
        this.player = player;
    }

    private List<Position> matches = new LinkedList<>();

    public List<Position> getMatches() {
        return matches;
    }

    public boolean matchAt(final char[][] board, int column, int row) {
        char[][] slidingWindow = new char[pattern.part.length][pattern.part[0].length];
        // create subBoard / sliding window 
        for(int x = 0; x < pattern.part.length; x++) {
            slidingWindow[x] = Arrays.copyOfRange(board[x + column], row, row + pattern.part[0].length);
        }
        return pattern.equals(slidingWindow);
    }

    /**
     * Return number of matches
     */
    public int match(final char[][] board) {
        this.matches.clear();
        int count = 0;

        // iteration
        for(int i = 0; i <= board.length - pattern.part.length; i++) {
            for(int j = 0; j <= board[i].length - pattern.part[0].length; j++) {                
                if(matchAt(board, i, j)) {
                    matches.add(new Position(i, j));
                    count++;
                }
            }
        }

        return count;
    }
}