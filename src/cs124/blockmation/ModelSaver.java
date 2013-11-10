package cs124.blockmation;

import java.io.Closeable;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Used to save a model into a blockmation movie file.
 * 
 * @author Josh Tumath (jmt14@aber.ac.uk)
 */
public class ModelSaver implements Closeable {
	////////////////////////////////////////////////////////////////////////////
	// Fields
	////////////////////////////////////////////////////////////////////////////
	/** The file writer used to save a model into a blockmation movie file. */
	private FileWriter modelFile;
	
	/** The model to be saved. */
	private Model model;
	
	
	////////////////////////////////////////////////////////////////////////////
	// Constructors
	////////////////////////////////////////////////////////////////////////////
	/**
	 * Constructs a new model saver for a blockmation file.
	 * @param file a blockmation file
	 * @throws IOException if there is a problem while setting up the file
	 */
	public ModelSaver(File file, Model model) throws IOException {
		// Check that there's a file extension
		if(!file.getPath().endsWith(".txt")) {
		    file = new File(file.getPath() + ".txt");
		}
		
		modelFile = new FileWriter(file.getPath());
		this.model = model;
	}
	
	////////////////////////////////////////////////////////////////////////////
	// Methods
	////////////////////////////////////////////////////////////////////////////
	/**
	 * Saves a model into a .blockmation file.
	 * 
	 * The .blockmation file is stored with data in the following order:
	 * <ol>
	 * 	 <li>The number of frames in the movie</li>
	 *   <li>The width and height of the frames in the movie</li>
	 *   <li>The individual blocks in each frame in the movie</li>
	 * </ol>
	 * @param model the model to be saved
	 * @throws IOException if there is a problem while saving the file
	 */
	public void save() throws IOException {
		writeTotalFrames();
		writeDimensions();
		writeFrames();
	}
	
	/**
	 * Writes a line showing the number of frames in the movie.
	 * @param model the model to be saved
	 * @throws IOException if there is a problem while saving the file
	 */
	private void writeTotalFrames() throws IOException {
		modelFile.write(String.valueOf(model.getTotalFrames()) + "\n");
	}
	
	/**
	 * Writes a line showing the width and height of the frames in the movie.
	 * @param model the model to be saved
	 * @throws IOException if there is a problem while saving the file
	 */
	private void writeDimensions() throws IOException {
		modelFile.write(String.valueOf(model.getDimensions()) + "\n");
	}
	
	/**
	 * Writes a series of characters in squares representing a frame in the
	 * movie. Each colour is represented by a character. Each frame is shown
	 * one-after-the-other.
	 * 
	 * For example, the output in a 3 x 3 frame might be:
	 * <pre>
	 * lll
	 * lrl
	 * lll</pre>
	 * @param model the model to be saved
	 * @throws IOException if there is a problem while saving the file
	 */
	private void writeFrames() throws IOException {
		int totalFrames = model.getTotalFrames();
		int dimensions = model.getDimensions();
		
		// Loop through each frame
		for(int currentFrame = 0; currentFrame < totalFrames; currentFrame++) {
			// Loop through and write each block as a character
			for(int y = 0; y < dimensions; y++) {
				for(int x = 0; x < dimensions; x++) {
					modelFile.write(blockToChar(
						model.getFrame(currentFrame).getBlock(x, y)));
				}
				
				// Don't write a new line if it's the end of the file
				if(currentFrame < totalFrames - 1 || y < dimensions - 1) {
					modelFile.write("\n");
				}
			}
		}
	}
	
	/**
	 * Converts a Block to its character equivalent.
	 * @param block the Block enumerator
	 * @return a character representing a Block
	 */
	private char blockToChar(Block block) {
		switch(block) {
		case LIGHT_GRAY:
			return 'l';
		case DARK_GRAY:
			return 'd';
		case RED:
			return 'r';
		case GREEN:
			return 'g';
		case BLUE:
			return 'b';
		default:
			System.err.println(
				getClass().getName() + " does not support " + block);
			
			return '#';
		}
	}
	
	/**
	 * Close the file.
	 */
	@Override
	public void close() throws IOException {
		modelFile.close();
	}
}
