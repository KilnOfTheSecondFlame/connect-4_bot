import ch.hslu.ai.connect4.Game;
import ch.hslu.ai.connect4.Player;
import ch.hslu.ai.connect4.team05.HeuristicValueDeterm;

import java.lang.annotation.Target;

import org.junit.Before;
import org.junit.Ignore;
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
    public void testLinerHeuristicValue() {
        HeuristicValueDeterm heuristicValueDeterm = new HeuristicValueDeterm(board, 'x');

        double v1 = heuristicValueDeterm.lineHeuristicValue(new char[]{ '-', '-', '-','x', 'o', 'o' });
        double v2 = heuristicValueDeterm.lineHeuristicValue(new char[]{ '-', '-', 'o','x', 'o', 'o' });
        double v3 = heuristicValueDeterm.lineHeuristicValue(new char[]{ 'x', 'x', 'x','x', 'o', 'o' });
        double v4 = heuristicValueDeterm.lineHeuristicValue(new char[]{ 'x', 'x', 'o','o', 'o', 'o' });

        assertEquals(1.0, v1, 0.02);
        assertEquals(0.0, v2, 0.02);
        assertEquals(101.0, v3, 0.02);
        assertEquals(Double.NEGATIVE_INFINITY, v4, 0.02);
    }

    @Test
    public void testBoardHeuristicValue() {
        char[][] board = new char[][] {
            "---x-o-".toCharArray(),
            "---x-o-".toCharArray(),
            "---x-o-".toCharArray(),
            "x-ooxx-".toCharArray()
        };

        double value = (new HeuristicValueDeterm(board, 'x')).evaluate();

        assertEquals(4.0, value, 0.2);
    }
}
