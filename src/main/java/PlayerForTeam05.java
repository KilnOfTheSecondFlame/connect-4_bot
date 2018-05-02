import ch.hslu.ai.connect4.Game;
import ch.hslu.ai.connect4.Player;
import ch.hslu.ai.connect4.team05.Action4Connects;
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
        Action4Connects.printBoard(board);

        int action = new Action4Connects(board, (b) -> 0.0, this.getSymbol(), 3).getAction();
        System.out.println("My Action:" + action);
        return action;
    }
}
