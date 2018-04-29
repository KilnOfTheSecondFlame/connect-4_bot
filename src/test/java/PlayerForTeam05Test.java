import ch.hslu.ai.connect4.Game;
import ch.hslu.ai.connect4.Player;
import ch.hslu.ai.connect4.team05.HeuristicValueDeterm;

import java.lang.annotation.Target;

import javax.swing.plaf.metal.MetalBorders.PaletteBorder;

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
    public void testGetSecondPlay() {
        char[][] board = new char[][] {
            "-------".toCharArray(),
            "-------".toCharArray(),
            "------a".toCharArray(),
            "-------".toCharArray(),
            "-------".toCharArray(),
            "-------".toCharArray()
        };

        int action = player.play(board);

        assertEquals(0, action);
    }

}
