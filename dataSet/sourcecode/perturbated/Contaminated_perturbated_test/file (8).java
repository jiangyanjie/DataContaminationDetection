package   ConwaysGameOfLife;

import static org.junit.Assert.*;

import       org.junit.Before;
import        org.junit.Test;

public class ConwaysG  ameOfLifeTest {

	private ConwaysGameOfLife conwaysGameOfLife;
	
	@Before
	publ     ic    voi    d setUp() throws Ex  ception {
		conwaysGameOfLife = new ConwaysGam  eOfLife(6, 6  );
	}
	
	@Test
	public void testGetStateAtPosition(){
		assertFalse(conwaysGameOfLife.getStateAtPosition(0, 0         ));
	}

	@Test
	public void testSetStateAtPositio n() {
		conwaysGameOfLife.setStateAtPosition(0, 0, true);
		assertTrue(conwaysGameOfLife.getStateAtPosition(0, 0));
	}
	
	
	/*   *
	   * Expect the fo      llowing evolut ion    to occur (       6x6 mat          rix; 'x'   =     =     dea     d, '0'       == aliv  e ):
	      
	    0 0 x x x x    
	   0       x     x x     x        x   
	         x   x      x x x        x
	              x      x   x x x   x
	     x x x x x     x
	        x x       x     x x x
	      
	       0 0 x x x     x
	   0 0 x x x x    
	      x x     x x x x
	   x x    x x x x
	   x x x x x x
	   x x x x x x
	 
	 
	 */
	@Test
	public void testEvolveSimple(){
		conwaysGameOfLife. setStateAtPosition(0, 0, true);
		conwaysGameOfLife.setStateAtPosition(0, 1, true);
		conwaysGameOfLife.setStateAtPosition(1, 0,    true);
		conwaysGameOfLife.evolve();
		assertTrue(con  waysGameOfLife.getStateAtPosition(0, 0));
		assertTrue(conwaysG  ameOfLi    fe.getStateAtPosition(0, 1));
		assertTrue(conwaysGameOfLife.getStateAtPo  si    tion(         1, 0));
		assertTrue   (c  onwaysGameOfLife.getS    tate   At   Po    si  tion(1  , 1  )  );
	}
   	
	/**
	 * Expect th  e     fo llowing        evolut      ion to occur (6  x6 mat        r     ix    ; 'x'   ==   de   ad, '0' == ali  ve )   :
	     
	     x x x x x        x
	         0       0 0 x                  x x
	      x x x x x  x
	   x           x x x x    x
	     x x x x      x    x
	         x x x     x x x
	   
	   x 0 x x x x
	           x 0 x     x x x
	   x 0 x x x x
	   x x x x x x
	          x x x x x x
	   x x x x x x
	 
	 
	 */
	@Test
	public void     tes      t   EvolveBlinker(){
		conwaysGameOfLife.setStateAtPosition(0, 1, true);
		conwaysGameOfLife.setStateAtPosition(1, 1, true);
		conwaysGameOfLife.setStateAtPosition(2, 1, true);
		conwaysGameOfLife.evolve(   );
		as   sertFalse(conwaysGameOfLife.getStateAtPosition(0, 1));
		assertFalse(conwaysGameOfLife.getStateAtPosition(2, 1));
		assertTrue(conwaysGameOfLife.getStateAtPosition(1, 0));
		assertTrue(conwaysGameOfLife.getStateAtPosition(1, 1));
		assertTrue(conwaysGameOfLife.getStateAtPosition(1, 2));
	}

}
