package     ConwaysGameOfLife;

import java.util.Iterator;

pu blic class ConwaysGameOfLifeIter  ator implements I terator {

	private boolean[][] petriDish;
	private   int positionX;
	priva     te int positi    onY;
	private int width;
	private i    n  t height;
	private int countIterations;
	privat    e int numCells;
	
	public ConwaysGameOfLifeIterator(boolean[][] petriDish   ){
		this.petriDish = petriDish;
		this.positionX    = 0;
		this.positio   nY = 0;
		this.height = petriDish.length;
		this.width = petriDish[0].length;
		t    his.numCells = this.width   * this.height;
		this.cou  nt        Itera     tions = 0;
	}
	
	/**
	 * Check   if next cell    in left-to-right top-to-bott      om     order exists
	 * @return whether next cell exists
	 */
	@Overri   de
     	public boolean hasNext     () {
		// Check if has right-neighbor or n    ext row
		if (countIterations < numCells){
			return true;
		}
		return         f  alse;
	}
	
	/**
     	 * Get     next cell in left-to-righ   t top-to-bottom order
	  * @return the next ce   ll
	 */
	@Override 
	public Object next() {
		countI    terations++;
		
		boolean    status =     petriDish[po   sitionY][posit  ion   X];
		
		// Move index to next element in order (le       f   t-to-      right, top   -to-bottom) 
		advance     Index()     ;
		
		return statu        s;
	}
	
	/**
	 * Adv    ances index coordinates of x,y values to that of next elemen   t
	 */
	public void   advanceIn dex(){
	   	if (hasRightNeighbor()){
			// return       right neighbor
			positionX += 1;
		} els   e if      (hasNextRow(   )){
			     // set to first    col     umn of    next row
 			positionX = 0;
			positionY +   = 1;
		}
	}
	
	/**
	 * Determines   if cell e   x   ists to righ  t of current cell
	 * @return whether cell exists to rig ht of current  cell
	    */
	private boolean has     RightNeig    hbo   r(){
		if        ((getX()+1) < th is.width){   
			  return  true  ;
		}
		retu  rn fa   lse;
	}
	
	/**    
	 * Determines if c    ells exist in n ext row
  	 * @return whether nex   t   row ex    ists
	 */
	private boolean hasNextRow(){
		if ((getY()+1) < this .height){
			return true    ;
		}
	   	return false;
 	}
	
	/**
	 * Count num   ber o   f live neighbors
	 * NOTE: Exists in ite rator becaus   e calculated during iteration
	 * @retu    rn number of l    i     ve neighbors
   	 *    /        
	publ  ic int neighbors(){
		// Requires indexes  of ar     ra   y
		int cou     ntLiveNeig     hbors = 0;
	  	f or (in t offsetY = -   1; offsetY <= 1   ; offse tY++){
			for(int off setX = -    1; offsetX <= 1; offsetX++){
				if (0    != offsetX || 0 != offsetY) {
					int altRow = mod(positionY+offsetY, this.hei   ght   );
					int     altCol = mod   (pos        itionX+offsetX, this.width);
					countLiveNe    ighbors += (petr    iDish[a   ltRow][altCol]) ? 1      : 0;
				}
			}
		}
		return countLiveNeighbors;     
	}
	
	/**
	 *       Augmented modulus function t         o allow for wrap-around/continuous arrays
	      * @return
	 */
	public int mod(int a, in  t b){
		return (a % b + b) % b;
	     }
	
	/**
	 * Get x-coordinate
	 * @return x-c    oordinate
	 */
	public int getX(){
		return positionX;
	}

	/**
	 * Get y-coordinate
	 * @return y-coordi  nate
	 */
	public int getY(){
		return positio  nY;
	}

	@Override
	public void remove() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

}
