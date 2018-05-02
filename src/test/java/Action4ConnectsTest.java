import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Ignore;
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
    public void testCopyBoard() {
        board = new char[][] {
            "---a".toCharArray(),
            "-12a".toCharArray(),
            "-34a".toCharArray(),
            "-xaa".toCharArray()
        };

        assertEquals(new char[][]{
            "12".toCharArray(),
            "34".toCharArray(),
        }, Action4Connects.cutBoard(board, 1, 2, 1, 2));
    }

    @Test
    public void testAddToColumn() {
        board = new char[][] {
            "-----a".toCharArray(),
            "--xaax".toCharArray()
        };

        assertEquals('b', Action4Connects.addToPosition(Action4Connects.getNewPosition(1, board), board, 'b')[1][1]);
        assertEquals('-', board[1][1]);
        assertEquals(new char[][] {
            "----ba".toCharArray(),
            "--xaax".toCharArray()
        }, Action4Connects.addToPosition(Action4Connects.getNewPosition(0, board), board, 'b'));
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

    @Test
    public void testChoosingWinningStrategy() {
        char[][] board = new char[][] {
            "--xxx".toCharArray(),
            "---oo".toCharArray(),
            "----o".toCharArray()
        };
        char[][] board2 = new char[][] {
            "---oo".toCharArray(),
            "--xxx".toCharArray(),
            "----o".toCharArray()
        };
        char[][] board3 = new char[][] {
            "---oo".toCharArray(),
            "----o".toCharArray(),
            "--xxx".toCharArray(),
        };

        assertEquals(0, (new Action4Connects(board, (char[][] b) -> 0.0, 'x', 1)).getAction());
        assertEquals(1, (new Action4Connects(board2, (char[][] b) -> 0.0, 'x', 1)).getAction());
        assertEquals(2, (new Action4Connects(board3, (char[][] b) -> 0.0, 'x', 1)).getAction());
    }
    
    public void testChoosingBlockingStrategy() {
        char[][] board = new char[][] {
            "---xx".toCharArray(),
            "--ooo".toCharArray(),
            "---xo".toCharArray()
        };
        char[][] board2 = new char[][] {
            "--ooo".toCharArray(),
            "---xx".toCharArray(),
            "-----".toCharArray()
        };
        char[][] board3 = new char[][] {
            "--xxo".toCharArray(),
            "---xx".toCharArray(),
            "--ooo".toCharArray()
        };
        char[][] board4 = new char[][] {
            "---oo".toCharArray(),
            "--xxo".toCharArray(),
            "--oxx".toCharArray(),
            "---oo".toCharArray(),
            "----o".toCharArray()
        };

        assertEquals(1, (new Action4Connects(board, (char[][] b) -> 0.0, 'x', 1)).getAction());
        assertEquals(0, (new Action4Connects(board2, (char[][] b) -> 0.0, 'x', 1)).getAction());
        assertEquals(3, (new Action4Connects(board3, (char[][] b) -> 0.0, 'x', 1)).getAction());
        assertEquals(1, (new Action4Connects(board3, (char[][] b) -> 0.0, 'x', 1)).getAction());
    }

    @Test
    @Ignore
    public void testCalculateTreeWithSimpleHeuristic() {
        action4Connects = new Action4Connects(board, (b) -> { return 0.0; }, 'x');

        System.out.println(action4Connects.getValue());
        System.out.println(action4Connects.getAction());
    }
}