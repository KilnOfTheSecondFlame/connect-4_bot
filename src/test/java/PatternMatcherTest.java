import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import ch.hslu.ai.connect4.team05.PatternMatcher;
import ch.hslu.ai.connect4.team05.PatternMatcher.Pattern;

public class PatternMatcherTest {



    public void setup() {

    }

    @Test
    public void testPatternMatcherSimple() {
        Pattern boardPart = new Pattern(new char[][]{
            {'.','0'},
            {'0','.'}
        }, 'x');

        assertTrue(boardPart.equals(new char[][]{
            {'a','x'},
            {'x','-'}
        }));
        assertFalse(boardPart.equals(new char[][]{
            {'-','-'},
            {'x','c'}
        }));
        assertFalse(boardPart.equals(new char[][]{
            {'-','x'}
        }));
        assertFalse(boardPart.equals(new char[][]{
            {'-','o','a'},
            {'b','-','b'}
        }));
    }

    @Test
    public void testPatternMatcherSpecialCharacters() {
        Pattern boardPart = new Pattern(new char[][] {
            {'.','0'},
            {'0','$'},
        }, 'x');

        assertTrue(boardPart.equals(new char[][]{
            {'-','x'},
            {'x','a'},
        }));
        assertFalse(boardPart.equals(new char[][]{
            {'-','x'},
            {'x','-'},
        }));
    }

    @Test
    public void testOverBoard() {
        PatternMatcher patternMatcher = new PatternMatcher(new char[][] {
            {'.','0'},
            {'0','.'},
        }, 'a');

        assertEquals(1, patternMatcher.match(new char[][]{
            {'-', '-', '-'},
            {'-', '-', 'a'},
            {'-', 'a', 'b'},
        }));

        assertEquals(0, patternMatcher.match(new char[][]{
            {'-', '-', 'b'},
            {'-', 'a', 'a'},
            {'-', 'b', 'b'},
        }));

        assertEquals(2, patternMatcher.match(new char[][]{
            {'-', 'a', 'b'},
            {'a', 'b', 'a'},
            {'-', 'a', 'b'},
        }));
    }

    @Test
    public void testComplexPatternOverBoard() {
        PatternMatcher matcher = new PatternMatcher(new char[][]{
            {'-','$','.'},
            {'.','0','.'},
            {'.','.','0'}
        }, 'x');

        assertEquals(1, matcher.match(new char[][]{
            "-------".toCharArray(),
            "-----ax".toCharArray(),
            "-----xa".toCharArray(),
            "------x".toCharArray(),
            "-------".toCharArray(),
            "------a".toCharArray()
        }));
    }

    @Test
    public void testComplexPatternCounter() {
        PatternMatcher matcher = new PatternMatcher(new char[][]{
            {'1','$','.'},
            {'.','1','.'},
            {'.','.','1'}
        }, 'x');

        assertEquals(2, matcher.match(new char[][]{
            "-------".toCharArray(),
            "-----ax".toCharArray(),
            "-----xa".toCharArray(),
            "-----ax".toCharArray(),
            "----xxa".toCharArray(),
            "------x".toCharArray()
        }));

        assertEquals(1, matcher.match(new char[][]{
            "-------".toCharArray(),
            "-----ax".toCharArray(),
            "-----xa".toCharArray(),
            "-----a-".toCharArray(),
            "----xxa".toCharArray(),
            "------x".toCharArray()
        }));
    }
}