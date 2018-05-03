import ch.hslu.ai.connect4.Game;
import ch.hslu.ai.connect4.Player;
import ch.hslu.ai.connect4.team05.HeuristicValueDeterm;

import java.lang.annotation.Target;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

public class HeuristicValueDetermTest {
    
    private char[][] board;

    @Test
    public void testAppendGround() {
        char[][] board = new char[][] {
            {'-','x'},
            {'x','-'},
        };

        assertEquals(new char[][] {
            {'-','x','#'},
            {'x','-','#'},
        }, HeuristicValueDeterm.appendGround(board));
        assertEquals(new char[][] {
            {'-','x'},
            {'x','-'},
        }, board);
    }

    @Test
    public void testHeuristicValueWonLost() {
        char[][] board = new char[][] {
            "-------".toCharArray(),
            "-----xx".toCharArray(),
            "----xoo".toCharArray(),
            "---xxox".toCharArray(),
            "---xoxo".toCharArray(),
        };

        assertEquals((Double)1.0, (new HeuristicValueDeterm('x', 'o')).apply(board));
        assertEquals((Double)(-1.0), (new HeuristicValueDeterm('o', 'x')).apply(board));
    }

    @Test
    public void testHeurisitcValueWonLostHorizontal() {
        char[][] board = new char[][] {
            "------o".toCharArray(),
            "-----xx".toCharArray(),
            "----xoo".toCharArray(),
            "---xxxo".toCharArray(),
            "---xoxo".toCharArray(),
        };

        assertEquals((Double)1.0, (new HeuristicValueDeterm('x', 'o')).apply(board));
        assertEquals((Double)(-1.0), (new HeuristicValueDeterm('o', 'x')).apply(board));
    }

    @Test
    public void testBetterPositioning() {
        char[][] board = new char[][] {
            "-------".toCharArray(),
            "------x".toCharArray(),
            "-------".toCharArray(),
            "-----ox".toCharArray(),
            "------o".toCharArray(),
            "-------".toCharArray(),
            "------o".toCharArray(),
        };

        assertEquals((Double).5, (new HeuristicValueDeterm('x', 'o')).apply(board));
        assertEquals((Double)(-.5), (new HeuristicValueDeterm('o', 'x')).apply(board));
    }

    @Test
    public void testBetterPositioningAfterOneMove() {
        char[][] board = new char[][] {
            "-------".toCharArray(),
            "-------".toCharArray(),
            "-------".toCharArray(),
            "-----xo".toCharArray(), // wins horizontaly
            "------x".toCharArray(), // wins horizontaly
            "-------".toCharArray(),
            "-------".toCharArray(),
        };

//        assertEquals((Double).25, (new HeuristicValueDeterm('x', 'o')).apply(board));
//        assertEquals((Double)(-.25), (new HeuristicValueDeterm('o', 'x')).apply(board));
    }

    @Test
    public void testBoardHeuristicValue() {
        char[][] board = new char[][] {
            "---x-o-".toCharArray(),
            "---x-o-".toCharArray(),
            "---x-o-".toCharArray(),
            "x-ooxx-".toCharArray()
        };

//        double value = (new HeuristicValueDeterm(board, 'x')).evaluate();

//        assertEquals(16.0, value, 0.2);
    }
    
    @Test
    public void testHeuristcValuesforCloseLost() {
        char[][] board1 = new char[][] {
            "--eaee".toCharArray(),
            "------".toCharArray(),
            "------".toCharArray(),
            "-----a".toCharArray(),
            "-----a".toCharArray(),
            "-----a".toCharArray(),
            "------".toCharArray()
        };
        
        char[][] board2 = new char[][] {
            "---aea".toCharArray(),
            "------".toCharArray(),
            "-----e".toCharArray(),
            "-----a".toCharArray(),
            "-----a".toCharArray(),
            "-----a".toCharArray(),
            "-----e".toCharArray()
        };
//        double v1 = (new HeuristicValueDeterm(board1, 'e')).evaluate();
//        double v2 = (new HeuristicValueDeterm(board2, 'e')).evaluate();

//        System.out.println(v1 + " - " + v2);
    }

    @Test
    public void testBoardHeuristicValueWin() {
        char[][] board = new char[][] {
            "-x-----".toCharArray(),
            "-ox----".toCharArray(),
            "-oox---".toCharArray(),
            "-xoox--".toCharArray()
        };

//        double value = (new HeuristicValueDeterm(board, 'x')).evaluate();

//        assertEquals(117.0, value, 0.2);
    }

    @Test
    public void testBoardHeuristicValueLost() {
        char[][] board = new char[][] {
            "-x--o--".toCharArray(),
            "-oxox--".toCharArray(),
            "-ooxo--".toCharArray(),
            "-ooxo--".toCharArray()
        };

//        double value = (new HeuristicValueDeterm(board, 'x')).evaluate();

//        assertEquals(Double.NEGATIVE_INFINITY, value, 0.2);
    }


}