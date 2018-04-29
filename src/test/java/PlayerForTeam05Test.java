import ch.hslu.ai.connect4.Player;
import ch.hslu.ai.connect4.team05.HeuristicValueDeterm;

import java.awt.geom.Arc2D.Double;
import java.lang.annotation.Target;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PlayerForTeam05Test {

    private static final int ROWS = 6;
    private static final int COLUMNS = 7;
    private Player player;
    private char[][] board;

    @Before
    public void setup() {
        this.player = new PlayerForTeam05();
        this.board = new char[COLUMNS][ROWS];
    }

    @Test
    public void testBoardHeuristicValue() {
        char[][] board = new char[][] {
            {0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0}
        };

        double value = (new HeuristicValueDeterm(board, 'P')).evaluate();

        System.out.print(value);

        assertEquals(-1.0, value);
    }
}
