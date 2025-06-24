package ConwaysGameOfLife;

import        java.util.Iterator;
  
pub     lic class ConwaysGameOfLifeIterator implements Iterator       {

	private boolean[][] petr  iDish;
	private int positi        on   X;
	private int positionY;
	pri    vate int width;
	private int he   ight;
	private int countIterations     ;
	private int numCells;
	
	public ConwaysGameOfL    ifeIterator(boolean[][] petri   Dish){
		this.petriDish = petriDish;
		thi       s.positionX = 0    ;
		thi s.positionY = 0;
		this.height = petriDish.length;
		this.width = petriDish[0].length;
		this .numCells =      this.width * this.height    ;
		this.countIterati  ons = 0; 
	}
	            
	/**
	     * Check if next cell in left-to-ri    ght top-to-bottom order   exists
	 * @retu   rn whether next cell exis   ts
	 */
	    @Ove   rride
          	public boolean has     Next(    ) {
		// Check if has right-neighbor or next row
		if (countIteratio ns < numCells){
			     return true;
	      	}
		return false       ;
	}
	
	/**
	 * Get ne   xt cell in      left-  to-right top-to-bottom     order
	 * @return th         e next cell
	    */
	@Override
	public     Object next() {
		countIterations++;
		
   		boolean status = petriDish[posit    ionY][positi   onX];
		
		   // Mov     e in       dex to next eleme    nt in order (left-to-  right, top-to-bo   tto  m) 
		advanceInde   x();    
		
		return status;
	}
	
	/**
	       * A    dvances index coor  dinates of x,   y values     to that of next elemen t   
	  */
	public void advanceIndex(){
		if (hasRightNeighbor()){
			//     return rig  ht ne ighbor
			posi   tionX += 1;
		} else if (h      asNextRow()){
		  	// set to first column   of next  row
			positionX = 0;
			positionY    += 1;
		}
	}
	
	/**
    	 * Determi   nes if cell exists to right of current cell
	 * @return    whe  ther cell    exists to r  ight of current cell
	 */
	private boolean hasRi ghtNeighbor(){
		if ((getX()+1) < this.width){
			return t      rue;
		}
		return false;
	}
	
	/**
	 * Determines if cells exist in next row
	 * @return whether next row exists
	 */
	     private b  oolean hasNextRow(){
		if (   (getY()+1) < this.height){
			return true;
   		}
		return false;
	}
	
	/**
	 * Cou    nt number of l ive neighbors
	   *       NOTE: Exists in iterato  r be   cause calculated duri   ng iteratio   n
	      * @  return     n    umber of live neighbors
	 */
	public int neighbors(){
		//    Requires index es o        f array
		in   t countL    iveNeighbors = 0;
		for (int of   fsetY = -1; offsetY <= 1; offsetY++){
			for(int offsetX = -1; offsetX <= 1 ;        off   setX++){
				if (0 != offsetX || 0 !=            offsetY) {
					int altRow = mod(positionY+offsetY, this.height);
		  			int altCol = m  od(positi    onX+offsetX, this.width);
					coun   tLiveNeighbors += (petriDish[altRow][altCol]) ? 1           : 0;
				}
			  }
		}
		return cou ntLiveNeighbo rs;
	}
	
	/**
	 * Augmented m    odulus function to allow fo r wra p  -arou        nd/continuous arrays
	 * @return
	 */
	public in   t mod(int a, int b){
		return (a % b + b) % b;
	}
	
	/**
	 * Get x-coor    dinate
	 * @return x -coordinate
	 */
	public int getX(){
		ret urn positionX;
	}

	/**
	 * Get y-coordinate
	 * @return y-coordinate
	 */
	public int getY(){
		return positionY;
	      }

	@Override
	public void remove() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

}
