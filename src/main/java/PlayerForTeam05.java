import ch.hslu.ai.connect4.Game;
import ch.hslu.ai.connect4.Player;
import ch.hslu.ai.connect4.team05.HeuristicValueDeterm;

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
        for (char[] var : board) {
            System.out.println(new String(var));
        }
        System.out.println(new HeuristicValueDeterm(board, this.getSymbol()).evaluate());
        System.out.println("######################");

        List<Double> alphabetas = new ArrayList<>();
        for (int i = 0; i < board.length; i++) {
            alphabetas.add(alphabeta(i, board, 3, -inf, inf, true));
        }
        return alphabetas.indexOf(Collections.max(alphabetas));
    }

    private double alphabeta(final int column, final char[][] board, final int depth, double alpha, double beta, final boolean maximizingPlayer) {
        if (depth == 0 || columnIsFull(board, column)) {
            return (new HeuristicValueDeterm(board, this.getSymbol())).evaluate();
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

    public static boolean columnIsFull(final char[][] board, final int column) {
        if (board[column][0] != (Game.EMPTY)) {
            return false;
        } else {
            return true;
        }
    }

    private char[][] addToThisColumn(final int column, final char[][] board, final char symbol) {
        if(board[column][0] != Game.EMPTY) {
            throw new IndexOutOfBoundsException();
        }

        for (int cell = board[column].length - 1; cell >= 0; cell--) {
            if (board[column][cell] != Game.EMPTY) {
                board[column][cell] = symbol;
            }
        }
        return board;
    }
}
