package ch.hslu.ai.connect4.team05;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class HeuristicValueDeterm {

    private final char[][] board;
    private final char playerSymbol;
    private final Double inf = Double.POSITIVE_INFINITY;

    public HeuristicValueDeterm(final char[][] board, char playerSymbol) {
        this.playerSymbol = playerSymbol;
        this.board = board;
    }

    public double evaluate() {
        double value = 0;

        for (int index = 0; index < board.length; index++) {
            // value of rows
            char[] line = new char[board.length];
            System.arraycopy(board[index], 0, line, 0, board[index].length - 1);
            value += lineHeuristicValue(line);
            // value of columns
            line = new char[board[index].length];
            for (int row = 0; row < board[index].length; row++) {
                line[row] = board[index][row];
            }
            value += lineHeuristicValue(line);
        }
        // value of left diagonals (start with 4th row, as lower ones can't have any 4 symbols in a line
        for (int rowIndex = 3; rowIndex < board[0].length; rowIndex++) {
            char[] line = new char[rowIndex + 1];
            int columnIndex = 0;
            for (int row = rowIndex; row >= 0; row--) {
                line[columnIndex] = board[columnIndex][row];
                columnIndex++;
            }
            value += lineHeuristicValue(line);
        }
        for (int columnIndex = 0; columnIndex <= board.length - 4; columnIndex++) {
            int columnLoopIndex = columnIndex;
            char[] line = new char[board[columnIndex].length];
            int lineIndex = 0;
            for (int rowIndex = board[columnIndex].length - 1; rowIndex >= 0; rowIndex--) {
                line[lineIndex] = board[columnLoopIndex][rowIndex];
                columnLoopIndex++;
                lineIndex++;
                if (columnLoopIndex >= board.length) {
                    break;
                }
            }
            value += lineHeuristicValue(line);
        }
        // value of right diagonals
        for (int rowIndex = 3; rowIndex < board[0].length; rowIndex++) {
            char[] line = new char[rowIndex + 1];
            int columnIndex = board.length - 1;
            int lineIndex = 0;
            for (int row = rowIndex; row >= 0; row--) {
                line[lineIndex] = board[columnIndex][row];
                columnIndex--;
                lineIndex++;
            }
            value += lineHeuristicValue(line);
        }
        for (int columnIndex = board.length-1; columnIndex >= 3; columnIndex--) {
            int columnLoopIndex = columnIndex;
            char[] line = new char[board[columnIndex].length];
            int lineIndex = 0;
            for (int rowIndex = board[columnIndex].length - 1; rowIndex >= 0; rowIndex--) {
                line[lineIndex] = board[columnLoopIndex][rowIndex];
                columnLoopIndex--;
                lineIndex++;
                if (columnLoopIndex <= 0) {
                    break;
                }
            }
            value += lineHeuristicValue(line);
        }
        return value;
    }

    /*
    Possible four = 1;
    Actual four = 100;
    Enemy four = -infinity;
     */
    private double lineHeuristicValue(final char[] line) {
        double result = 0.0;
        List<Integer> playerLines = new ArrayList<>();
        int playerLine = 0;
        int possPlayerLine = 0;
        int enemyLine = 0;
        for (int index = 0; index < line.length; index++) {
            if (line[index] == '-') {
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