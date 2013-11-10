package cs124.blockmation.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import cs124.blockmation.Block;
import cs124.blockmation.Frame;

public class FrameTest {
	@Test
	public void testCreateNewFrameSize1() {
		Frame frame = new Frame(1);
		assertEquals(
				"Frame does not contain Block.LIGHT_GRAY",
				frame.getBlock(0, 0),
				Block.LIGHT_GRAY);
	}
	
	@Test
	public void testCreateNewFrameSize10() {
		Frame frame = new Frame(10);
		
		for(int x = 0; x < 10; x++) {
			for(int y = 0; y < 10; y++)
				assertEquals(
						"Frame" + x + y + "does not contain Block.LIGHT_GRAY",
						frame.getBlock(x, y),
						Block.LIGHT_GRAY);
		}
	}
	
	@Test
	public void testSetBlock() {
		Frame frame = new Frame(10);
		
		frame.setBlock(5, 7, Block.RED);
		
		assertEquals(
				"Frame is not being set to a different value",
				frame.getBlock(5, 7),
				Block.RED);
	}
	
	@Test
	public void testClear() {
		Frame frame = new Frame(10);
		
		frame.setBlock(5, 7, Block.RED);
		frame.clear();
		
		assertEquals(
				"Frame has not been cleared",
				frame.getBlock(5, 7),
				Block.LIGHT_GRAY);
	}
	
	@Test
	public void testClone() {
		Frame frame1 = new Frame(10);
		
		frame1.setBlock(5, 7, Block.RED);
		Frame frame2 = null;
		try {
			frame2 = (Frame) frame1.clone();
		} catch (CloneNotSupportedException e) {
			fail("CloneNotSupportedException");
		}
		
		assertEquals(
				"Frame has not been cleared",
				frame1.getBlock(5, 7),
				frame2.getBlock(5, 7));
	}
}
