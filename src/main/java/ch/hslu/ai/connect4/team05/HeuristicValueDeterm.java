package ch.hslu.ai.connect4.team05;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import javax.swing.JList.DropLocation;
import javax.xml.crypto.Data;

import ch.hslu.ai.connect4.Game;

public class HeuristicValueDeterm {

    private final char[][] board;
    private final char playerSymbol;
    private final Double inf = Double.POSITIVE_INFINITY;

    public HeuristicValueDeterm(final char[][] board, char playerSymbol) {
        this.playerSymbol = playerSymbol;
        this.board = board;
    }

    private void appendValueToIndexOfArray(final List<List<Character>> array, final char value, final int index) {
        if(array.get(index) == null) {
            array.set(index, new LinkedList<>());
        }
        array.get(index).add(value);
    }

    public double evaluate() {
        double value = 0;

        for (int index = 0; index < board.length; index++) {
            // value of rows
            value += lineHeuristicValue(board[index].clone());
            
            char[] lines = board[index].clone();
            // value of columns
            char[] line = new char[board[index].length];
            for (int row = 0; row < board[index].length; row++) {
                line[row] = board[index][row];
            }
            value += lineHeuristicValue(line);

        }

        // prefill arraylist with null objects to simplify access, since size is 0 at start
        List[] defaultList = new List[board.length + board[0].length - 1];
        Arrays.fill(defaultList, null);
 
        List<List<Character>> diagonalLRLines = new ArrayList<>((List)Arrays.asList(defaultList));
        List<List<Character>> diagonalRLLines = new ArrayList<>((List)Arrays.asList(defaultList));

        // value of left diagonals (start with 4th row, as lower ones can't have any 4 symbols in a line

        // loop over board and create lists of all lines
        for (int rowIndex = 0; rowIndex < board.length; rowIndex++) {
            for (int columnIndex = 0; columnIndex < board[0].length; columnIndex++ ) {
                char val = board[rowIndex][columnIndex];
                appendValueToIndexOfArray(diagonalRLLines, val, rowIndex + columnIndex);
                appendValueToIndexOfArray(diagonalLRLines, val, rowIndex + (board[0].length - columnIndex - 1));
            }
        }

        // remove first and last 3, since the size to win
        // of those diagonals in connect-4 are min 4 
        if(diagonalLRLines.size() > 7) {
            diagonalLRLines = diagonalLRLines.subList(3, diagonalLRLines.size() - 4);
        } else {
            diagonalLRLines.clear();
        }

        if(diagonalRLLines.size() > 7) {
            diagonalRLLines = diagonalRLLines.subList(3, diagonalRLLines.size() - 4);
        } else {
            diagonalRLLines.clear();
        }

        for (List<Character> line : diagonalLRLines) {
            value += lineHeuristicValue(convert(line));
        }
        for (List<Character> line : diagonalRLLines) {
            value += lineHeuristicValue(convert(line));
        }

        return value;
    } 

    private static char[] convert(final List<Character> list){
        final char[] array = new char[list.size()];
        for(int i = 0; i < array.length; i++)
            array[i] = list.get(i);
        return array;
    }

    /*
    Possible four = 1;
    Actual four = 100;
    Enemy four = -infinity;
     */
    public double lineHeuristicValue(final char[] line) {
        double result = 0.0;
        List<Integer> playerLines = new ArrayList<>();
        int playerLine = 0;
        int possPlayerLine = 0;
        int enemyLine = 0;
        for (int index = 0; index < line.length; index++) {
            if (line[index] == Game.EMPTY) {
                possPlayerLine++;
                playerLine = 0;
                enemyLine = 0;
            } else if (line[index] == playerSymbol) {
                playerLine++;
                possPlayerLine++;
                enemyLine = 0;
            } else {
                enemyLine++;
                playerLines.add(possPlayerLine);
                possPlayerLine = 0;
                playerLine = 0;
            }
            if (enemyLine > 3) {
                return -inf;
            }
            if (playerLine > 3) {
                result += 100;
            }
        }
        playerLines.add(possPlayerLine);
        Iterator<Integer> lineIterator = playerLines.iterator();
        while (lineIterator.hasNext()) {
            int value = lineIterator.next();
            if (value > 3) {
                result += value - 3;
            }
        }
        return result;
    }

}