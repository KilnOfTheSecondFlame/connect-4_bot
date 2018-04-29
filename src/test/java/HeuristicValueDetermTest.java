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

        assertEquals(16.0, value, 0.2);
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
        double v1 = (new HeuristicValueDeterm(board1, 'e')).evaluate();
        double v2 = (new HeuristicValueDeterm(board2, 'e')).evaluate();

        System.out.println(v1 + " - " + v2);
    }

    @Test
    public void testBoardHeuristicValueWin() {
        char[][] board = new char[][] {
            "-x-----".toCharArray(),
            "-ox----".toCharArray(),
            "-oox---".toCharArray(),
            "-xoox--".toCharArray()
        };

        double value = (new HeuristicValueDeterm(board, 'x')).evaluate();

        assertEquals(117.0, value, 0.2);
    }

    @Test
    public void testBoardHeuristicValueLost() {
        char[][] board = new char[][] {
            "-x--o--".toCharArray(),
            "-oxox--".toCharArray(),
            "-ooxo--".toCharArray(),
            "-ooxo--".toCharArray()
        };

        double value = (new HeuristicValueDeterm(board, 'x')).evaluate();

        assertEquals(Double.NEGATIVE_INFINITY, value, 0.2);
    }


}