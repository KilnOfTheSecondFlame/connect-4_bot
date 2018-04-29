package ch.hslu.ai.connect4.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JComponent;

/**
 * Table cell content in game board
 *
 * @author Marc Pouly
 */

public class Disc extends JComponent {
	
	private Color color;
	private String colorName;
	private char symbol;
	
	/**
	 * Constructor:
	 * @param color The disc color for this player.
	 * @param colorName The name of the color.
	 * @param symbol The symbol for this player.
	 */
	
	public Disc(Color color, String colorName, char symbol) {
		this.color = color;
		this.symbol = symbol;
		this.colorName = colorName;
	}
	
	/**
	 * @return The name of the disc color
	 */
	
	public String getColorName() {
		return colorName;
	}
	
    /**
     * @return The symbol representing the owner of this cell
     */
	
	public char getSymbol() {
		return symbol;
	}
    
    /**
     * @return The color representing the owner of this cell
     */
    
	public Color getColor() {
		return color;
	}
    
    /**
     * Paint method:
     */
    
    @Override
    public void paintComponent(Graphics graphics) {
        final Graphics2D g2d = (Graphics2D) graphics;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setPaint(getColor());
        g2d.fillOval(5, 5, 50, 50);
    }
}
