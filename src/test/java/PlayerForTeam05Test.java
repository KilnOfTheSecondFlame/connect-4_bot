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
    @Ignore
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

    @Test
    public void testBlockingAction() {/*
        int a1 = player.play(new char[][] {
            "------".toCharArray(),
            "------".toCharArray(),
            "------".toCharArray(),
            "------".toCharArray(),
            "------".toCharArray(),
            "-----a".toCharArray(),
            "------".toCharArray(),
        });
        int a2 = player.play(new char[][] {
            "-----e".toCharArray(),
            "------".toCharArray(),
            "-----a".toCharArray(),
            "-----a".toCharArray(),
            "------".toCharArray(),
            "------".toCharArray(),
            "------".toCharArray(),
        });
        int a3 = player.play(new char[][] {
            "----ee".toCharArray(),
            "------".toCharArray(),
            "-----a".toCharArray(),
            "-----a".toCharArray(),
            "------".toCharArray(),
            "-----a".toCharArray(),
            "------".toCharArray(),
        });*/

        int a4 = player.play(new char[][] {
            "--eaaa".toCharArray(),
            "------".toCharArray(),
            "-----e".toCharArray(),
            "-----e".toCharArray(),
            "------".toCharArray(),
            "-----e".toCharArray(),
            "------".toCharArray(),
        });
/*
        assertEquals(0, a1);
        assertEquals(1, a2);
        assertEquals(4, a3);*/
        assertEquals(4, a4);
    }

}
