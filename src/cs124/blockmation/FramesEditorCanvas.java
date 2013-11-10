package cs124.blockmation;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * A panel that can be used in a Swing application to edit a blockmation movie
 * model.
 * 
 * @author Josh Tumath (jmt14@aber.ac.uk)
 */
public class FramesEditorCanvas extends AbstractFramesCanvas
implements MouseListener, MouseMotionListener {
	private static final long serialVersionUID = 5284767659048135350L;
	////////////////////////////////////////////////////////////////////////////
	// Fields
	////////////////////////////////////////////////////////////////////////////
	/** The type (i.e. colour) of block that is painted when the user clicks. */
	private Block brush = Block.DARK_GRAY;
	
	
	////////////////////////////////////////////////////////////////////////////
	// Constructors
	////////////////////////////////////////////////////////////////////////////
	/**
	 * Construct a JPanel to hold a canvas that can be used to edit the contents
	 * of a blockmation movie model.
	 * @param model the blockmation movie model to be drawn
	 */
	public FramesEditorCanvas(Model model) {
		super(model);
		
		addMouseListener(this);
		addMouseMotionListener(this);
	}
	
	
	////////////////////////////////////////////////////////////////////////////
	// Methods
	////////////////////////////////////////////////////////////////////////////
	/**
	 * Set the type (i.e. colour) of block that is painted when the user clicks.
	 * @param brush the type (i.e. colour) of block that is painted when the
	 *        user clicks.
	 */
	public void setBrush(Block brush) {
		this.brush = brush;
	}
	
	/**
	 * Create a new frame at the end of the blockmation movie. It copies the
	 * content from the previous frame.
	 */
	public void createNewFrame() {
		int previousCurrentFrameId = getModel().getTotalFrames() - 1;
		
		// Clone the previous frame
		Frame newCurrentFrame = null;
		
		try {
			newCurrentFrame = (Frame) getModel().getFrame(previousCurrentFrameId).clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		
		// Create a blank frame
		getModel().addNewFrame();
		
		//Copy over the data from the previous frame
		int newCurrentFrameId = getModel().getTotalFrames() - 1;
		getModel().setFrame(newCurrentFrameId, newCurrentFrame);
		
		setCurrentFrameId(newCurrentFrameId);
	}
	
	/**
	 * Called by a mouse event. Paints the block that the user has clicked on
	 * with the current brush colour.
	 * @param e the <code>MouseEvent</code> showing where the user's mouse is
	 */
	private void paintBlock(MouseEvent e) {
		int mouseX = e.getX();
		int mouseY = e.getY();
		int dimensions = getModel().getDimensions();
		int currentFrameId = getCurrentFrameId();
		
		// XXX: Find a more efficient algorithm
		for(int x = 0; x < dimensions; x++) {
			for(int y = 0; y < dimensions; y++) {
				if(mouseX > x * BLOCK_SIZE + MARGIN_WIDTH && mouseX < (x + 1) * BLOCK_SIZE + MARGIN_WIDTH
				&& mouseY > y * BLOCK_SIZE + MARGIN_WIDTH && mouseY < (y + 1) * BLOCK_SIZE + MARGIN_WIDTH) {
					getModel().getFrame(currentFrameId).setBlock(x, y, brush);
				}
			}
        }
		
		repaint();
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// FIXME: Find out why mouse clicks are ignored for a short moment if a click has just been made
		paintBlock(e);
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		paintBlock(e);
	}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}
	
	@Override
	public void mouseMoved(MouseEvent e) {}
}
