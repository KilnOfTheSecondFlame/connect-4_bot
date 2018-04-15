package ch.hslu.ai.connect4;

/**
 * This class contains the elementary game logic.
 * 
 * @author Marc Pouly
 */

public class Game {
	
	/**
	 * SReserved symbol for empty cells.
	 */
	
	public static final char EMPTY = '-';
	
	private char[][] board;
	private int rows, columns;
	private Player player1, player2;
	
    /**
     * Constructor:
     * @param rows The number of rows on the game board
     * @param columns The number of columns on the game board.
     * @param player1 The first mover in this game
     * @param player2 The second mover in this game
     */
	
	public Game(int rows, int columns, Player player1, Player player2) {
    	this.rows = rows;
    	this.columns = columns;
    	this.player1 = player1;
    	this.player2 = player2;
    	reset();
	}
	
	/**
	 * Resets to start state
	 */
	
	public void reset() {
    	board = new char[columns][rows];
    	for(int i = 0; i < columns; i++) {
    		for(int j = 0; j < rows; j++) {
    			board[i][j] = EMPTY;
    		}
    	}
	}
	
	/**
	 * Basic Game Logic: Alternatives between players
	 * @return 0 if game was a draw, 1 if player1 won and 2 if player 2 won.
	 */

	public int startGame() {
		int counter = 0;
		while (true) {
			
			Player current = (counter % 2 == 0) ? getPlayer1() : getPlayer2();
			int column = current.play(getGameBoard());
			
			// Check input validity:
			
			if(column < 0) {
				System.out.println(current.getName()+" tries to play a negative column number -> disqualification.");
				return (current == player1)? 2 : 1;
			}
			
	    	if(isColumnFull(column, current)) {
	    		System.out.println(current.getName()+" tries to play an already full column -> disqualification.");
	    		return (current == player1)? 2 : 1;
	    	}
			
			playColumn(column, current);
			counter++;
			
			if (isGameDrawn()) {
				return 0;
			}

			// For Player 1:
			if (hasWon(getPlayer1())) {
				return 1;
			}

			// For Player 2:
			if (hasWon(getPlayer2())) {
				return 2;
			}
			
			// continue ...
		}
	}
	
    /**
     * Performs a move on the game board.
     * @param column The column number where the disc is entered
     * @param player The player to whom the mode belongs to
     * @return The row index where the disc is being placed
     */
    
    public int playColumn(int column, Player player) {
    	
		if(column < 0) {
			throw new IllegalArgumentException(player.getName()+" tries to play a negative column number.");
		}
		
    	if(isColumnFull(column, player)) {
    		throw new IllegalArgumentException(player.getName()+" tries to play an already full column.");
    	}
    	
    	// Search empty cell:
    	int row = rows-1;
    	for(; board[column][row] != EMPTY; row--);
    	board[column][row] = player.getSymbol();
    	return row;
    }
	
    /**
     * @param column The column number where the player wants to play
     * @param player The player who wants to execute this move
     * @return Is the specified column full already ?
     */
    
    public boolean isColumnFull(int column, Player player) {
    	if(column < 0 || column >= columns) {
    		throw new IllegalArgumentException("Invalid column number by "+player.getName()+": Number must be non-negative and < "+columns+", played "+column+".");
    	}
    	return board[column][0] != EMPTY;
    }
   
	/**
	 * @return A game is a draw if no more discs can be placed
	 */

	public boolean isGameDrawn() {
		for (int i = 0; i < columns; i++) {
			for (int j = 0; j < rows; j++) {
				if (board[i][j] == EMPTY) {
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * @param player One of the players participating in this game.
	 * @return Whether the given player has won the game
	 */
	
	public boolean hasWon(Player player) {
		return hasFourInColumn(player) || hasFourInRow(player) || hasFourInDiagonal(player);
	}

	/**
	 * @return true if the current player has 4 consecutive discs in one column, false otherwise
	 */

	private boolean hasFourInColumn(Player player) {
		for (int i = 0; i < columns; i++) {
			int counter = 0;
			for (int j = 0; j < rows && counter < 4; j++) {
				if (board[i][j] == player.getSymbol()) {
					counter++;
				} else {
					counter = 0;
				}
			}
			if (counter == 4) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * @return true if the current player has 4 consecutive discs in one row, false otherwise
	 */

	private boolean hasFourInRow(Player player) {
		for (int i = 0; i < rows; i++) {
			int counter = 0;
			for (int j = 0; j < columns && counter < 4; j++) {
				if (board[j][i] == player.getSymbol()) {
					counter++;
				} else {
					counter = 0;
				}
			}
			if (counter == 4) {
				return true;
			}
		}
		return false;
	}
	
	/** 
	 * @return true if the current player has 4 consecutive discs in one diagonal, false otherwise
	 */

	private boolean hasFourInDiagonal(Player player) {

		// Left-to-right diagonal:
		for (int i = 0; i <= columns - 4; i++) {
			for (int j = 0; j <= rows - 4; j++) {
				char[] cells = new char[] { board[i][j], board[i + 1][j + 1],
						board[i + 2][j + 2], board[i + 3][j + 3] };
				if (equal(cells, player.getSymbol())) {
					return true;
				}
			}
		}

		// Right-to-left diagonal:
		for (int i = columns - 1; i >= 3; i--) {
			for (int j = 0; j <= rows - 4; j++) {
				char[] cells = new char[] { board[i][j], board[i - 1][j + 1],
						board[i - 2][j + 2], board[i - 3][j + 3] };
				if (equal(cells, player.getSymbol())) {
					return true;
				}
			}
		}

		return false;
	}
    
    /**
     * @param player One of the players participating in this game.
     * @return Whether the given player is the first mover in the game
     */
    
    public boolean isFirstMover(Player player) {
    	return player == player1;
    }
    
    /**
     * @return The number of rows
     */

	public int getRows() {
		return rows;
	}
	
	/**
	 * @return The number of columns
	 */

	public int getColumns() {
		return columns;
	}
	
	/**
	 * @return The first mover
	 */
	
	public Player getPlayer1() {
		return player1;
	}

	/**
	 * @return The second mover
	 */
	
	public Player getPlayer2() {
		return player2;
	}

    /**
     * @return Game situation as character array
     */
    
    public char[][] getGameBoard() {
    	char[][] clone = new char[columns][rows];
    	for(int i = 0; i < columns; i++) {
    		for(int j = 0; j < rows; j++) {
    			clone[i][j] = board[i][j];
    		}
    	}
    	return clone;
    }
    
    /**
     * @return Symbol representing an empty cell
     */
    
    public char getEmptySymbol() {
    	return EMPTY;
    }
	
	/**
	 * @return Whether the array just contains the given symbol
	 */

	private boolean equal(char[] array, char symbol) {
		for (int i = 0; i < array.length; i++) {
			if (array[i] != symbol) {
				return false;
			}
		}
		return true;
	}
}
