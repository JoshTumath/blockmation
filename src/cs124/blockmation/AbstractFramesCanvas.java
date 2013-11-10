package cs124.blockmation;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

/**
 * An abstract panel that can be used in a Swing application to show a
 * blockmation movie model.
 * 
 * @author Josh Tumath (jmt14@aber.ac.uk)
 */
public abstract class AbstractFramesCanvas extends JPanel {
	private static final long serialVersionUID = -2366418650451150666L;
	////////////////////////////////////////////////////////////////////////////
	// Fields
	////////////////////////////////////////////////////////////////////////////
	/** The blockmation movie model being presented in the canvas. */
	private Model model;
	
	/** The index number of the current frame in the model being viewed. */
	private int currentFrameId;
	
	/** The width/height of the rendered blocks in pixels. */
	public static final int BLOCK_SIZE = 10;
	
	/** The width/height of the rendered blocks in pixels. */
	public static final int MARGIN_WIDTH = 10;
	
	
	////////////////////////////////////////////////////////////////////////////
	// Constructors
	////////////////////////////////////////////////////////////////////////////
	/**
	 * Construct a JPanel to hold a canvas that renders a blockmation movie
	 * model.
	 * @param model the blockmation movie model to be drawn
	 */
	public AbstractFramesCanvas(Model model) {
		this.model = model;
		
		// Check that the model contains at least one frame
		if(model.getTotalFrames() > 0) {
			setCurrentFrameId(0);
		}
		
		setBackground(Color.WHITE);
	}

	
	////////////////////////////////////////////////////////////////////////////
	// Methods
	////////////////////////////////////////////////////////////////////////////
	/**
	 * Get the blockmation movie model.
	 * @return the blockmation movie
	 */
	public Model getModel() {
		return model;
	}
	
	/**
	 * Set a new blockmation movie model.
	 * @param model the new blockmation movie model.
	 */
	public void setModel(Model model) {
		this.model = model;
	}
	
	/**
	 * Get the current frame being rendered in the canvas.
	 * @return the current frame being rendered in the canvas
	 */
	public int getCurrentFrameId() {
		return currentFrameId;
	}
	
	/**
	 * Set the current frame being rendered in the canvas.
	 * @param index the index number of the new current frame
	 */
	public void setCurrentFrameId(int index) {
		currentFrameId = index;
		
		repaint();
	}
	
	/**
	 * Go to the previous frame.
	 * @throws IndexOutOfBoundsException if there is no previous frame
	 */
	public void goToPreviousFrame() throws IndexOutOfBoundsException {
		int newFrameId = currentFrameId - 1;
		
		// Throw if we're being asked to a negative frame
		if(newFrameId < 0) {
			throw new IndexOutOfBoundsException();
		}
		
		setCurrentFrameId(newFrameId);
	}
	
	/**
	 * Go to the next frame.
	 * @throws IndexOutOfBoundsException if there is no following frame
	 */
	public void goToNextFrame() throws IndexOutOfBoundsException {
		int newFrameId = currentFrameId + 1;
		
		// Throw if we're being asked to go beyond the total number of frames.
		if(newFrameId >= model.getTotalFrames()) {
			throw new IndexOutOfBoundsException();
		}
		
		setCurrentFrameId(newFrameId);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		// Don't bother drawing the grid if it's empty
		if(model.getDimensions() > 0) {
			drawGrid(g);
		}
	}
	
	/**
	 * Renders the current blockmation movie frame in the canvas.
	 * @param g The <code>Graphics</code> object to protect
	 */
	private void drawGrid(Graphics g) {
		int frameSize = model.getDimensions();
		
		// Loop through each element of the array and draw it.
		for(int x = 0; x < frameSize; x++) {
			for(int y = 0; y < frameSize; y++) {
				g.setColor(getBlockColor(model.getFrame(currentFrameId).getBlock(x, y)));
				g.fillRect(
					x * BLOCK_SIZE + MARGIN_WIDTH,
					y * BLOCK_SIZE + MARGIN_WIDTH,
					BLOCK_SIZE,
					BLOCK_SIZE);
			}
		}
	}
	
	/**
	 * Gets the colour of a <code>Block</code> in the form of a
	 * <code>Color</code> object. 
	 * @param block the block to get the colour of
	 * @return the colour of the block
	 */
	private Color getBlockColor(Block block) {
		switch(block) {
		case LIGHT_GRAY:
			return Color.LIGHT_GRAY;
		case DARK_GRAY:
			return Color.DARK_GRAY;
		case RED:
			return Color.RED;
		case GREEN:
			return Color.GREEN;
		case BLUE:
			return Color.BLUE;
		default:
			// Error block
			return Color.WHITE;
		}
	}
}
