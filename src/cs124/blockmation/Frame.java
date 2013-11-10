package cs124.blockmation;

import java.util.Arrays;

/**
 * An animation frame in a blockmation movie. Stores a square grid with
 * different coloured blocks in it.
 * 
 * @author Josh Tumath (jmt14@aber.ac.uk)
 */
public class Frame implements Cloneable {
	////////////////////////////////////////////////////////////////////////////
	// Fields
	////////////////////////////////////////////////////////////////////////////
	/** Stores the blocks in a frame. */
	private Block[][] blocks;

	
	////////////////////////////////////////////////////////////////////////////
	// Constructors
	////////////////////////////////////////////////////////////////////////////
	/**
	 * Constructs a new frame with a given size.
	 * @param dimensions the width and the height of the frame
	 */
	public Frame(int dimensions) {
		blocks = new Block[dimensions][dimensions];
		
		// Fill the array with background blocks for now.
		for (Block[] row: blocks) {
			Arrays.fill(row, Block.LIGHT_GRAY);
		}
	}

	
	////////////////////////////////////////////////////////////////////////////
	// Methods
	////////////////////////////////////////////////////////////////////////////
	/**
	 * Gets the value of a block in the frame.
	 * @param x the x coordinate of the block
	 * @param y the y coordinate of the block
	 * @throws ArrayIndexOutOfBoundsException if a coordinate is outside the
	 *         grid
	 * @return the requested block
	 */
	public Block getBlock(int x, int y) {
		// Check if the parameters are inside the array.
		if(x < 0 || x >= blocks.length) {
			throw new ArrayIndexOutOfBoundsException(x);
		} else if(y < 0 || y >= blocks[0].length) {
			throw new ArrayIndexOutOfBoundsException(y);
		} else {
			return blocks[x][y];
		}
	}
	
	/**
	 * Sets the value of a block in the frame.
	 * @param x the x coordinate of the block
	 * @param y the y coordinate of the block
	 * @param type the new type of block
	 * @throws ArrayIndexOutOfBoundsException if a coordinate is outside the
	 *         grid
	 */
	public void setBlock(int x, int y, Block type) {
		// Check if the parameters are inside the array.
		if(x < 0 || x >= blocks.length) {
			throw new ArrayIndexOutOfBoundsException(x);
		} else if(y < 0 || y >= blocks[0].length) {
			throw new ArrayIndexOutOfBoundsException(y);
		} else {
			blocks[x][y] = type;
		}
	}
	
	/**
	 * Reset all of the blocks in the frame to light gray.
	 */
	public void clear() {
		for (Block[] row: blocks) {
			Arrays.fill(row, Block.LIGHT_GRAY);
		}
	}
	
	/**
	 * Clones the content of the current frame into a new frame.
	 */
	@Override
	public Object clone() throws CloneNotSupportedException {
		Frame cloneFrame = new Frame(blocks.length);
		
		// Loop through each block and copy their value into the new block 
		for(int x = 0; x < blocks.length; x++) {
			for(int y = 0; y < blocks[0].length; y++) {
				cloneFrame.setBlock(x, y, getBlock(x, y));
			}
		}
		
		return cloneFrame;
	}
}
