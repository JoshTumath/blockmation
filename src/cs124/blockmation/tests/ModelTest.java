package cs124.blockmation.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import cs124.blockmation.Block;
import cs124.blockmation.Frame;
import cs124.blockmation.Model;

public class ModelTest {
	@Test
	public void testDimensionsField() {
		Model model = new Model(10);
		
		assertEquals(
				"Model dimension incorrect",
				model.getDimensions(),
				10);
	}
	
	@Test
	public void testAddNewFrame() {
		Model model = new Model(10);
		
		model.addNewFrame();
		
		assertEquals(
				"Frames list does not have new frame",
				model.getTotalFrames(),
				1);
		assertEquals(
				"New frame does not contain a Block",
				Block.LIGHT_GRAY,
				model.getFrame(0).getBlock(5, 7));
	}
	
	@Test
	public void testAddNewFrames100Times() {
		
		Model model = new Model(10);
		
		for(int i = 0; i < 100; i++) {
			model.addNewFrame();
		}
		
		assertEquals(
				"Frames list does not have new frame",
				model.getTotalFrames(),
				100);
		assertEquals(
				"New frame does not contain a Block",
				Block.LIGHT_GRAY,
				model.getFrame(99).getBlock(5, 7));
	}
	
	@Test
	public void testSetFrame() {
		Model model = new Model(10);
		Frame frame = new Frame(10);
		
		frame.setBlock(5, 7, Block.RED);
		model.addNewFrame();
		model.setFrame(0, frame);
		
		assertEquals(
				"A new frame was not set in the model",
				model.getFrame(0).getBlock(5, 7),
				Block.RED);
	}
}