package ch.hslu.ai.connect4;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;

/**
 * This class represents an intelligent computer player.
 * 
 * @author Marc Pouly
 */

public abstract class Player {
	
	private final String name;
	private final char symbol;
	
	/*
	 * Each player automatically obtains a symbol from this set.
	 * Transformation via set type avoid duplicates.
	 */
	
	private static Iterator<Character> symbols = new HashSet<Character>(Arrays.asList('x', 'o', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'y', 'z')).iterator();
	
	/**
	 * Constructor:
	 * @param name The name of this player.
	 * @param symbol The symbol that belongs to this player.
	 */
	
	public Player(String name) {
		
		if(!symbols.hasNext()) {
			throw new IllegalArgumentException("No symbol found for player. There seems to be too many players registered.");
		}
		
		symbol = symbols.next();
		
		if(symbol == Game.EMPTY) {
			throw new IllegalArgumentException("Symbol "+Game.EMPTY+" is reserved for empty cells.");
		}
		
		this.name = name;
	}
	
	/**
	 * @return The symbol that belongs to this player.
	 */
	
	public char getSymbol() {
		return symbol;
	}
	
	/**
	 * @return The name of this computer player.
	 */
	
	public String getName() {
		return name;
	}
	
    /**
     * The following method allows you to implement your own game intelligence.
     * The method must return the column number where the computer player puts the next disc. 
     * board[i][j] = cell content at position (i,j), i = column, j = row
     * Make sure you do not play an already full column.
     * 
     * If board[i][j] = this.getSymbol(), the cell (i,j) contains one of your discs
     * If board[i][j] = '-', the cell is empty
     * Otherwise, the cell contains one of your opponent's discs
     * 
     * @param board The current game board
     * @return The column number where you want to put your disc
     */
	
    public abstract int play(char[][] board);

}
