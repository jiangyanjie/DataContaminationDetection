package ConwaysGameOfLife;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ConwaysGameOfLifeIteratorTest {
	
	ConwaysGameOfLifeIterator iterator;
	ConwaysGameOfLife game;
	
	@Before
	public void setUp() throws Exception {
		game = new ConwaysGameOfLife(50,50);
		iterator = (ConwaysGameOfLifeIterator) game.createIterator();
	}

	/**
	 * Test Get X-coordinate
	 */
	@Test
	public void testGetX() {
		assertEquals(0,iterator.getX());
		iterator.next();
		assertEquals(1, iterator.getX());
	}
	
	/**
	 * Test Get Y-coordinate
	 */
	@Test
	public void testGetY() {
		assertEquals(0,iterator.getY());
		iterator.next();
		assertEquals(0, iterator.getY());
	}
	
	/**
	 * Tests that cell at 0,0 has no neighbors
	 */
	@Test
	public void testNeighborsEmpty(){
		game.setStateAtPosition(0, 0, true);
		assertEquals(0, iterator.neighbors());
	}
	
	/**
	 * Sets current cell at 0,0 coordinate.
	 * Checks that all surrounding neighbors are found and correctly returned.
	 * 
	 * Looks like:
		
		X X O O O ... X
		X X O O O ... X
		O O O O O ... O
		... 
		O O O O O ... O
		X X 0 0 0 ... X
	 */
	@Test
	public void testNeighborsWrapAroundFull(){
		game.setStateAtPosition(0, 0, true); // current cell
		game.setStateAtPosition(1, 0, true); // right
		game.setStateAtPosition(1, 1, true); // bottom-right
		game.setStateAtPosition(0, 1, true); // bottom
		game.setStateAtPosition(49, 0, true); // left
		game.setStateAtPosition(49, 1, true); // bottom-left
		game.setStateAtPosition(0, 49, true); // top
		game.setStateAtPosition(1, 49, true); // top-right
		game.setStateAtPosition(49, 49, true); // top-left
		assertEquals(8, iterator.neighbors());
	}

}
