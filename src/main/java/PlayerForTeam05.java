import ch.hslu.ai.connect4.Game;
import ch.hslu.ai.connect4.Player;
import ch.hslu.ai.connect4.team05.Action4Connects;
import ch.hslu.ai.connect4.team05.HeuristicValueDeterm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public class PlayerForTeam05 extends Player {

    private char player;
    private char otherPlayer;

    private final boolean complexHeuristic;
    private final int maxDeph;

    private Function<char[][], Double> heuristicFunction;
    
    public PlayerForTeam05() {
        this(5, true);
    }

    public PlayerForTeam05(int maxDeph, boolean complexHeuristic) {
        super("Player for Team05");

        this.complexHeuristic = complexHeuristic;
        this.maxDeph = maxDeph;
    }

    private void updateHeuristicFunction() {
        heuristicFunction = new HeuristicValueDeterm(player, otherPlayer);
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
        char detectedEnemey = Action4Connects.getOtherPlayer(board, this.getSymbol());
        if(detectedEnemey != otherPlayer) {
            otherPlayer = detectedEnemey;
            if(complexHeuristic) {
                updateHeuristicFunction();
            } else {
                heuristicFunction = (b) -> 0.0;
            }
        }

        return new Action4Connects(board, heuristicFunction, this.getSymbol(), this.maxDeph).getAction();
    }
}
