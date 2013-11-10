package cs124.blockmation;

import java.util.Vector;

/**
 * The model represents a movie. It stores a list of all the frames in the
 * movie.
 * 
 * @author Josh Tumath (jmt14@aber.ac.uk)
 */
public class Model {
	////////////////////////////////////////////////////////////////////////////
	// Fields
	////////////////////////////////////////////////////////////////////////////
	/** List of the frames in this model. */
	private Vector<Frame> frames = new Vector<>();
	
	/** The width/height of the frames in this model. */
	private int dimensions;
	
	
	////////////////////////////////////////////////////////////////////////////
	// Constructors
	////////////////////////////////////////////////////////////////////////////
	/**
	 * Constructs a new empty model to store the frames in a new movie.
	 * @param dimensions the width/height of the frame
	 */
	public Model(int dimensions) {
		this.dimensions = dimensions;
	}
	
	
	////////////////////////////////////////////////////////////////////////////
	// Methods
	////////////////////////////////////////////////////////////////////////////
	/**
	 * Get a frame from the model.
	 * @param index the position of the frame
	 * @return the requested frame
	 */
	public Frame getFrame(int index) {
		return frames.get(index);
	}
	
	/**
	 * Replace a Frame object with a different one.
	 * @param index the position of the frame
	 * @param frame the new frame
	 */
	public void setFrame(int index, Frame frame) {
		frames.set(index, frame);
	}
	
	/**
	 * Get the number of frames in the model.
	 * @return the number of frames in the model
	 */
	public int getTotalFrames() {
		return frames.size();
	}
	
	/**
	 * Get the frame's width/height
	 * @return the frame's width/height
	 */
	public int getDimensions() {
		return dimensions;
	}
	
	/**
	 * Adds a new frame to the end of the list.
	 */
	public void addNewFrame() {
		frames.add(new Frame(dimensions));
	}
}
