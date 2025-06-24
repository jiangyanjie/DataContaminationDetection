package ConwaysGameOfLife;

import java.util.Iterator;

public class ConwaysGameOfLifeIterator implements Iterator {

	private boolean[][] petriDish;
	private int positionX;
	private int positionY;
	private int width;
	private int height;
	private int countIterations;
	private int numCells;
	
	public ConwaysGameOfLifeIterator(boolean[][] petriDish){
		this.petriDish = petriDish;
		this.positionX = 0;
		this.positionY = 0;
		this.height = petriDish.length;
		this.width = petriDish[0].length;
		this.numCells = this.width * this.height;
		this.countIterations = 0;
	}
	
	/**
	 * Check if next cell in left-to-right top-to-bottom order exists
	 * @return whether next cell exists
	 */
	@Override
	public boolean hasNext() {
		// Check if has right-neighbor or next row
		if (countIterations < numCells){
			return true;
		}
		return false;
	}
	
	/**
	 * Get next cell in left-to-right top-to-bottom order
	 * @return the next cell
	 */
	@Override
	public Object next() {
		countIterations++;
		
		boolean status = petriDish[positionY][positionX];
		
		// Move index to next element in order (left-to-right, top-to-bottom) 
		advanceIndex();
		
		return status;
	}
	
	/**
	 * Advances index coordinates of x,y values to that of next element
	 */
	public void advanceIndex(){
		if (hasRightNeighbor()){
			// return right neighbor
			positionX += 1;
		} else if (hasNextRow()){
			// set to first column of next row
			positionX = 0;
			positionY += 1;
		}
	}
	
	/**
	 * Determines if cell exists to right of current cell
	 * @return whether cell exists to right of current cell
	 */
	private boolean hasRightNeighbor(){
		if ((getX()+1) < this.width){
			return true;
		}
		return false;
	}
	
	/**
	 * Determines if cells exist in next row
	 * @return whether next row exists
	 */
	private boolean hasNextRow(){
		if ((getY()+1) < this.height){
			return true;
		}
		return false;
	}
	
	/**
	 * Count number of live neighbors
	 * NOTE: Exists in iterator because calculated during iteration
	 * @return number of live neighbors
	 */
	public int neighbors(){
		// Requires indexes of array
		int countLiveNeighbors = 0;
		for (int offsetY = -1; offsetY <= 1; offsetY++){
			for(int offsetX = -1; offsetX <= 1; offsetX++){
				if (0 != offsetX || 0 != offsetY) {
					int altRow = mod(positionY+offsetY, this.height);
					int altCol = mod(positionX+offsetX, this.width);
					countLiveNeighbors += (petriDish[altRow][altCol]) ? 1 : 0;
				}
			}
		}
		return countLiveNeighbors;
	}
	
	/**
	 * Augmented modulus function to allow for wrap-around/continuous arrays
	 * @return
	 */
	public int mod(int a, int b){
		return (a % b + b) % b;
	}
	
	/**
	 * Get x-coordinate
	 * @return x-coordinate
	 */
	public int getX(){
		return positionX;
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
