import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import ch.hslu.ai.connect4.team05.Action4Connects;
import ch.hslu.ai.connect4.team05.PatternMatcher.Position;

public class Action4ConnectsTest {
    
    Action4Connects action4Connects;
    char[][] board;
    
    @Before
    public void setup() {
        board = new char[][] {
            "------".toCharArray(),
            "------".toCharArray(),
            "------".toCharArray(),
            "------".toCharArray(),
            "------".toCharArray(),
            "------".toCharArray(),
            "------".toCharArray()
        };
    }

    @Test
    public void testColumnIsFull() {
        board = new char[][] {
            "-----a".toCharArray(),
            "xaxaax".toCharArray()
        };

        assertTrue(Action4Connects.columnIsFull(board, 1));
        assertFalse(Action4Connects.columnIsFull(board, 0));
    }

    @Test
    public void testAddToColumn() {
        board = new char[][] {
            "-----a".toCharArray(),
            "--xaax".toCharArray()
        };

        assertEquals('b', Action4Connects.addToPosition(Action4Connects.getNewPosition(1, board), board, 'b')[1][1]);
        assertEquals('-', board[1][1]);
    }

    @Test
    public void testCalculateTreeWithSimpleHeuristic() {
        action4Connects = new Action4Connects(board, (b) -> { return 0.0; }, 'x');

        System.out.println(action4Connects.getValue());
        System.out.println(action4Connects.getAction());
    }

    @Test
    public void playerWinsTest() {
        assertTrue(Action4Connects.playerWins(new char[][] {
            "------".toCharArray(),
            "------".toCharArray(),
            "--x---".toCharArray(),
            "---x--".toCharArray(),
            "----x-".toCharArray(),
            "-----x".toCharArray(),
            "------".toCharArray()
        }, new Position(2, 3), 'x'));
        
        assertFalse(Action4Connects.playerWins(new char[][] {
            "------".toCharArray(),
            "------".toCharArray(),
            "x-xx-x".toCharArray(),
            "------".toCharArray(),
            "----x-".toCharArray(),
            "-----x".toCharArray(),
            "------".toCharArray()
        }, new Position(2, 3), 'x'));
    }
}