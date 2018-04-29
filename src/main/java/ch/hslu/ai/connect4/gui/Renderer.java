package ch.hslu.ai.connect4.gui;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;

import ch.hslu.ai.connect4.Game;
import ch.hslu.ai.connect4.Player;

/**
 * This class is used to administer and draw the game board situation.
 *
 * @author Marc Pouly
 */

public class Renderer extends AbstractTableModel implements TableCellRenderer {
		
	private Game game;
    private Disc[][] discs;
	private final Disc p1, p2, empty;
    
    /**
     * Constructor:
     * @param game The current game object
     * @param output output = true logs all moves to the console.
     */
    
    public Renderer(Game game, boolean output) {
    	this.game = game;
    	
    	p1 = new Disc(new Color(255, 215, 0), "yellow", game.getPlayer1().getSymbol());
    	p2 = new Disc(new Color(34, 139, 34), "green", game.getPlayer2().getSymbol());
    	empty = new Disc(Color.WHITE, "white", game.getEmptySymbol());
    	
    	clear();
    	
    	if(output) {
    		System.out.print(game.getPlayer1().getName()+" plays "+getDisc(game.getPlayer1()).getColorName()+" and ");
    		System.out.println(game.getPlayer2().getName()+" plays "+getDisc(game.getPlayer2()).getColorName()+".");
    	}
    }

    /**
     * Reset game board to initial state
     */
    
    public void clear() {
    	discs = new Disc[game.getColumns()][game.getRows()];
    	for(int i = 0; i < game.getColumns(); i++) {
    		for(int j = 0; j < game.getRows(); j++) {
    			discs[i][j] = empty;
    		}
    	}
    }
    
    /**
     * {@inheritDoc}
     */
    
    @Override
    public Component getTableCellRendererComponent(JTable table, final Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        return discs[column][row];
    }
    
    /**
     * {@inheritDoc}
     */
    
    @Override
    public int getRowCount() {
        return game.getRows();
    }
    
    /**
     * {@inheritDoc}
     */

    @Override
    public int getColumnCount() {
        return game.getColumns();
    }

    /**
     * {@inheritDoc}
     */
    
    @Override
    public String getColumnName(int column) {
        return column+"";
    }
    
    /**
     * {@inheritDoc}
     */
    
    @Override
    public Object getValueAt(int row, int column) {
        return discs[column][row];
    }
        
    /**
     * Performs a move on the game board and updates the table
     * @param column The column number where the disc is entered
     * @param player The player to whom the move belongs to
     */
    
    public void update(int column, Player player) {
    	int row = game.playColumn(column, player);
    	discs[column][row] = getDisc(player);
    }
    
    /**
     * @return A disc instance that belongs to the given player
     */
    
    private Disc getDisc(Player player) {
    	return game.isFirstMover(player) ? p1 : p2;	
    }
}
