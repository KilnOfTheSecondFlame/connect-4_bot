import ch.hslu.ai.connect4.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class PlayerForTeam05 extends Player {

    private final Double inf = Double.POSITIVE_INFINITY;

    public PlayerForTeam05() {
        super("Player for Team05");
    }

    /**
     * The following method allows you to implement your own game intelligence.
     * At the moment, this is a dumb random number generator.
     * The method must return the column number where the computer player puts the next disc.
     * board[column][row] = cell content at position (column, row)
     * <p>
     * If board[column][row] = this.getSymbol(), the cell contains one of your discs
     * If board[column][row] = '-', the cell is empty
     * Otherwise, the cell contains one of your opponent's discs
     *
     * @param board The current game board
     * @return The columns number where you want to put your disc
     */

    @Override
    public int play(final char[][] board) {
        List<Double> alphabetas = new ArrayList<>();
        for (int i = 0; i < board.length; i++) {
            alphabetas.add(alphabeta(i, board, 3, -inf, inf, true));
        }
        return alphabetas.indexOf(Collections.max(alphabetas));
    }

    private double evaluateNode(final int column, final char[][] board) {
        // TODO Heuristics go in here
        return 0;
    }

    private double possibleFoursInThisLine(final char[] line) {
        return 0;
    }

    private double alphabeta(final int column, final char[][] board, final int depth, double alpha, double beta, final boolean maximizingPlayer) {
        if (depth == 0 || columnIsFull(board, column)) {
            return evaluateNode(column, board);
        }
        if (maximizingPlayer) {
            for (int max_column = 0; max_column < board.length; max_column++) {
                alpha = Math.max(alpha, alphabeta(max_column, addToThisColumn(max_column, board, this.getSymbol()), depth - 1, alpha, beta, false));
                if (beta <= alpha) {
                    break;
                }
                return alpha;
            }
        } else {
            for (int min_column = 0; min_column < board.length; min_column++) {
                beta = Math.min(beta, alphabeta(min_column, addToThisColumn(min_column, board, '$'), depth - 1, alpha, beta, true));
                if (beta <= alpha) {
                    break;
                }
            }
            return beta;
        }
        return 0;
    }

    private boolean columnIsFull(final char[][] board, final int column) {
        if (board[column][board[column].length - 1] == ('-')) return false;
        return true;
    }

    private char[][] addToThisColumn(final int column, final char[][] board, final char symbol) {
        for (int cell = 0; cell < board[column].length; cell++) {
            if (board[column][cell] == '-') {
                char[][] result_board = board;
                result_board[column][cell] = symbol;
                return result_board;
            }
        }
        throw new IndexOutOfBoundsException();
    }
}
