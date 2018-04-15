package ch.hslu.ai.connect4;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.TableColumn;

import ch.hslu.ai.connect4.gui.Renderer;

/**
 * Implementation of a GUI for Connect-4 with Java AWT/Swing.
 * 
 * @author Marc Pouly
 */

public final class GameBoard extends JFrame {

	/**
	 * Display log on console:
	 */

	private static final boolean OUTPUT = true;

	/*
	 * Game Play Components:
	 */

	private Game game;
	private boolean active = true;

	/*
	 * GUI Components:
	 */

	private int sleep;
	private JTable table;
	private Renderer renderer;

	/**
	 * Constructor:
	 * @param game The connect-4 game logic
	 * @param sleep Waiting time between two moves
	 * @param player1 The first mover
	 * @param player2 The second mover
	 */

	public GameBoard(Game game, int sleep, Player player1, Player player2) {

		super("Connect-4");
		
		this.game = game;
		this.sleep = sleep;

		/*
		 * Game board table:
		 */

		table = buildJTable();
		this.setContentPane(table);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocation(100, 100);

		pack();
		setVisible(true);
		setResizable(false);

	}

	/**
	 * @return The tabular game board
	 */

	private JTable buildJTable() {

		JTable jtable = new JTable(game.getRows(), game.getColumns());
		jtable.setRowHeight(60);
		jtable.setGridColor(Color.black);
		jtable.setEnabled(false);

		this.renderer = new Renderer(game, OUTPUT);

		for (int i = 0; i < game.getColumns(); i++) {
			TableColumn column = jtable.getColumn(jtable.getColumnName(i));
			column.setCellRenderer(renderer);
			column.setMinWidth(60);
			column.setMaxWidth(60);
			column.setPreferredWidth(60);

		}

		jtable.setBorder(BorderFactory.createLineBorder(Color.black, 1));
		return jtable;
	}

	/**
	 * Basic Game Logic: Alternatives between players and updates GUI
	 */

	public void startGame() {
		
		int counter = 0;
		
		while (active) {
			
			Player current = (counter % 2 == 0) ? game.getPlayer1() : game.getPlayer2();
			
			int play = current.play(game.getGameBoard());
			
	    	if(OUTPUT) {
	    		String index = game.isFirstMover(current) ? "[1]" : "[2]";
	    		System.out.println(index+": "+current.getName()+" plays column "+play+". ");
	    	}
	    	
			renderer.update(play, current);
			this.repaint();
			counter++;
			
			try {
				Thread.sleep(sleep);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}	
			
			if (game.isGameDrawn()) {
				active = false;
				JOptionPane.showMessageDialog(this, "Game is a draw.");
			}

			// For Player 1:
			if (game.hasWon(game.getPlayer1())) {
				active = false;
				JOptionPane.showMessageDialog(this, game.getPlayer1().getName() + " won this game.");
			}

			// For Player 2:
			if (game.hasWon(game.getPlayer2())) {
				active = false;
				JOptionPane.showMessageDialog(this, game.getPlayer2().getName()	+ " won this game.");
			}
		}
	}
}
