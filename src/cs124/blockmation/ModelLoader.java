package cs124.blockmation;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/**
 * Used to load a model from a blockmation movie file.
 * 
 * @author Josh Tumath (jmt14@aber.ac.uk)
 */
public class ModelLoader implements Closeable {
	////////////////////////////////////////////////////////////////////////////
	// Fields
	////////////////////////////////////////////////////////////////////////////
	/** The scanner used to read a blockmation movie file. */
	private Scanner modelFile;
	
	
	////////////////////////////////////////////////////////////////////////////
	// Constructors
	////////////////////////////////////////////////////////////////////////////
	/**
	 * Constructs a new model loader for a blockmation movie file.
	 * @param file a blockmation file
	 * @throws FileNotFoundException if the file does not exist, is a directory
	 *                               rather than a regular file, or for some
	 *                               other reason cannot be opened for reading.
	 */
	ModelLoader(File file) throws FileNotFoundException {
		modelFile =
			new Scanner(new BufferedReader(new FileReader(file.getPath())));
	}
	
	
	////////////////////////////////////////////////////////////////////////////
	// Methods
	////////////////////////////////////////////////////////////////////////////
	/**
	 * Load the blockmation movie file and store its data in a model object.
	 * @throws InvalidBlockmationFileException if the blockmation file's data
	 *                                         is not formatted correctly
	 * @return the model stored in the file
	 */
	public Model load() throws InvalidBlockmationFileException {
		int totalFrames = loadTotalFrames();
		int dimensions = loadDimensions();
		
		return loadFrames(totalFrames, dimensions);
	}
	
	/**
	 * Get and store the total amount of frames in the file.
	 * This must be used first.
	 * @throws InvalidBlockmationFileException if the blockmation file's data
	 *                                         is not formatted correctly
	 */
	private int loadTotalFrames() throws InvalidBlockmationFileException {
		// Check that there is a number to read
		if(!modelFile.hasNextInt()) {
			throw new InvalidBlockmationFileException();
		}
		
		return modelFile.nextInt();
	}
	
	/**
	 * Get and store the width/height of the frames.
	 * This must be used second.
	 * @throws InvalidBlockmationFileException if the blockmation file's data
	 *                                         is not formatted correctly
	 */
	private int loadDimensions() throws InvalidBlockmationFileException {
		// Check that there is a number to read
		if(!modelFile.hasNextInt()) {
			throw new InvalidBlockmationFileException();
		}
		
		return modelFile.nextInt();
	}
	
	/**
	 * Get and store the frames of the file into the model.
	 * This must be used third.
	 * @throws InvalidBlockmationFileException if the blockmation file's data
	 *                                         is not formatted correctly
	 */
	private Model loadFrames(int totalFrames, int dimensions) throws InvalidBlockmationFileException {
		// Create a new blank model to load the frames into
		Model model = new Model(dimensions);
		
		// Stores a line from the file
		String lineOfBlocks;
		
		// Loop through each frame
		for(int currentFrame = 0; currentFrame < totalFrames; currentFrame++) {
			// Create a new blank frame to put data into
			model.addNewFrame();
			
			for(int y = 0; y < dimensions; y++) {
				// Throw if the next line doesn't exist
				if(!modelFile.hasNext()) {
					throw new InvalidBlockmationFileException();
				}
				
				// Store a line of blocks in the file
				lineOfBlocks = modelFile.next();
				
				// Loop through each character in the line of blocks
				for(int x = 0; x < dimensions; x++) {
					model.getFrame(currentFrame).setBlock(
						x, y,
						charToBlock(lineOfBlocks.charAt(x))
					);
				}
			}
		}
		
		return model;
	}
	
	/**
	 * Converts a character to its Block equivalent.
	 * @param character the character representing a block
	 * @return a Block enumerator
	 * @throws InvalidBlockmationFileException if the character is not one of
	 *                                         the characters that represents
	 *                                         a block
	 */
	private Block charToBlock(char character) throws InvalidBlockmationFileException {
		switch(character) {
		case 'l':
			return Block.LIGHT_GRAY;
		case 'd':
			return Block.DARK_GRAY;
		case 'r':
			return Block.RED;
		case 'g':
			return Block.GREEN;
		case 'b':
			return Block.BLUE;
		default:
			throw new InvalidBlockmationFileException();
		}
	}
	
	@Override
	public void close() throws IOException {
		modelFile.close();
	}
}
