package cs124.blockmation;

/**
 * A panel that can be used in a Swing application to edit a blockmation movie
 * model.
 * 
 * @author Josh Tumath (jmt14@aber.ac.uk)
 */
public class FramesPlayerCanvas extends AbstractFramesCanvas implements Runnable {
	private static final long serialVersionUID = -583910748561725354L;
	////////////////////////////////////////////////////////////////////////////
	// Fields
	////////////////////////////////////////////////////////////////////////////
	// Playback speeds
	/**
	 * The interval between each frame (in milliseconds) if the user requests
	 * to play at a slow playback speed.
	 */
	protected static final int SLOW_PLAYBACK = 500;
	
	/**
	 * The interval between each frame (in milliseconds) if the user requests
	 * to play at a medium playback speed.
	 */
	protected static final int MEDIUM_PLAYBACK = 200;
	
	/**
	 * The interval between each frame (in milliseconds) if the user requests
	 * to play at a fast playback speed.
	 */
	protected static final int FAST_PLAYBACK = 100;
	
	/**
	 * The current playback speed (i.e. the time interval between each frame) in
	 * milliseconds.
	 */
	private int playbackSpeed = MEDIUM_PLAYBACK;
	
	
	////////////////////////////////////////////////////////////////////////////
	// Constructors
	////////////////////////////////////////////////////////////////////////////
	/**
	 * Construct a JPanel to hold a canvas that can be used to play the contents
	 * of a blockmation movie model.
	 * @param model
	 */
	public FramesPlayerCanvas(Model model) {
		super(model);
	}
	
	
	////////////////////////////////////////////////////////////////////////////
	// Methods
	////////////////////////////////////////////////////////////////////////////
	/**
	 * Set the current playback speed (i.e. the time interval between each
	 * frame) in milliseconds.
	 * <p>
	 * The constants <code>SLOW_PLAYBACK</code>, <code>MEDIUM_PLAYBACK</code>
	 * and <code>FAST_PLAYBACK</code> can be used. Alternatively, you can use
	 * your own value.
	 * @param playbackSpeed
	 */
	public void setPlaybackSpeed(int playbackSpeed) {
		this.playbackSpeed = playbackSpeed;
	}
	
	@Override
	public void run() {
		setCurrentFrameId(0);
		
		int totalFrames = getModel().getTotalFrames();
		
		for(int i = 1; i < totalFrames; i++) {
			try {
				Thread.sleep(playbackSpeed);
			} catch (InterruptedException e) {
				break;
			}
			
			goToNextFrame();
		}
	}
}
