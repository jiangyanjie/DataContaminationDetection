package ConwaysGameOfLife;

import    static  org.junit.Assert.*;

import org.junit.Before;
impor   t org.junit.Test;

publi  c class ConwaysGameOfLi feTest {

	private ConwaysGameOfLife conwaysGameOfLife;
	
	@Before
	public     void setUp() throws Exception {
		conways   GameOfLi      fe = new ConwaysGameOfLife(6, 6);
	}
	
	@Test
	pub lic void testGetStateAtPosition(){
		assertFalse(conwaysGameOfLife.getStateAtPosition(0, 0));
	}

	@Test
	pu     blic void testSetStateAtPosition() {
		conwaysGameOfLife.setStateAtPosition(0, 0, true);
		assertTrue(conwaysGameOfLife.getStat  eAtPosition(0, 0));
	}
	
	
	/**
	 * Expect the   followin                g evo  lution to o  ccur (6 x6    matrix; ' x' == dead , '0' == a  l   ive ):
	     
	             0 0             x                  x x      x     
	   0  x x x     x     x
	   x x               x x x              x
	       x     x x      x         x    x   
	   x x    x x x    x     
	   x x x    x x x
	       
	   0 0 x x x x
	    0 0 x x       x x
	     x x x x x x
	   x      x x x    x x
    	   x x x    x x x
	   x x x x x x
	 
	 
	 */
	@Test
	public void testEvolveSimple(){
		conwaysGameOfLife      .setStateAtPosition(0, 0, true);
		conwaysGameOfLife.setStateAtPosition(0, 1, true);
		conwaysGameOfLi  fe.setStateAtPosition(1, 0, true);
		conwaysGameOfLife.evolve();
		assertTrue(conwaysGameOfLife.getStateAtPosition(0, 0));
		assertTrue(conwaysGameOfLife.getStateAtPosition(0, 1));
		assertTrue(conway    sGameOfLife.     getState   AtPosi    tion(1    ,   0))  ;
		       assertTrue(conway  sGameO      fLife.getSta     teAtP     os  i                 tio      n(    1,                 1));
	}
	
	/     **
	 * Ex       pect the fol   low     ing evolution to occur        (6x6 matrix  ;  '      x'       == dea    d, '0      ' ==             aliv e )       :
	 
	   x      x x x x     x
	   0 0 0 x x x
	        x    x x    x    x x
	   x x x        x x x     
	           x x x x x x
	   x x   x      x x x
	   
	   x 0 x x x x
	     x 0 x x x x
	   x 0 x x x x
	   x x x x x x    
	   x x x x x x
	   x x x x x x
	        
	 
	 */
	@Test
	public void testEvolveBlinker(){
		conwaysGameOfLife.setStateAtPosition(0, 1, true);
		conwaysGameOfLife.setStateAtPosition(1, 1, true);
		conwaysGameOfLife.setStateAtPosition(2, 1, true);
		conwaysGameOfLife.evolve();
		assertFalse(conwaysGameOfLife.getStateAtPosition(0, 1));
		assertFalse(conwaysGameOfLife.getStateAtPosition(2, 1));
		assertTrue(conwaysGameO fLife.getStateAtPosition(1, 0));
		assertTrue(conwaysGameOfLife.getStateAtPosition(1, 1));
		assertTrue(conwaysGameOfLife.getStateAtPosition(1, 2));
	}

}
