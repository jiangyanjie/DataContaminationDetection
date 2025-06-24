package ConwaysGameOfLife;

import java.util.Arrays;
import java.util.Iterator;

public class ConwaysGameOfLife {
	
	private int width;
	private int height;
	private boolean[][] petriDish;
	
	/**
	 * Create new ConwaysGameOfLife with specified width and height
	 * @param width (number of columns, cells horizontally)
	 * @param height (number of rows, cells vertically)
	 */
	public ConwaysGameOfLife(int width, int height){
		this.width = width;
		this.height = height;
		petriDish = new boolean[height][width];
		initPetriDish();
	}
	
	/**
	 * Default constructor (create 50x50 matrix)
	 */
	public ConwaysGameOfLife(){
		this(50,50);
	}
	
	/**
	 * Initialize dish with all dead cells
	 */
	private void initPetriDish(){
		for (boolean[] row : petriDish){
			Arrays.fill(row, false);
		}
	}
	
	/**
	 * Initialize dish with live cells at 30% probability
	 */
	public void initPetriDishWithRandom(){
		for (boolean[] row : petriDish){
			boolean state = (Math.random() < 0.3);
			Arrays.fill(row, state);
		}
	}

	/**
	 * @return width (number of columns)
	 */
	public int getWidth(){
		return width;
	}
	
	/**
	 * @return height (number of rows)
	 */
	public int getHeight(){
		return height;
	}
	
	/**
	 * Iterate through cells and determine next generation using the following steps:
	 * - Determine current state
	 * - Count number of Neighbors
	 * - Set state in temporary variable holding cells for next generation
	 * - Copy temporary variable of next generation to overwrite current generation
	 */
	public void evolve(){
		boolean[][] nextEvolution = new boolean[height][width];
		ConwaysGameOfLifeIterator iterator = (ConwaysGameOfLifeIterator) this.createIterator();
		boolean alive;
		int x = 0;
		int y = 0;
		while(iterator.hasNext()){
			x = iterator.getX();
			y = iterator.getY();
			int countLiveNeighbors = iterator.neighbors();
			alive = (boolean) iterator.next();
			boolean cellFate = determineCellFate(alive, countLiveNeighbors);
			nextEvolution[y][x] = cellFate;
		}
		
		System.out.println("\n---------------\nBefore evolution:");
		System.out.println("");
		displayGameBoard();
		this.petriDish = nextEvolution.clone();
		System.out.println("---------------\nAfter evolution:");
		displayGameBoard();
	}
	
	/**
	 * Determine fate of cell (dead or alive) given alive status and number of live neighbors
	 * @param alive
	 * @param countLiveNeighbors
	 * @return true (if fate is alive), false otherwise
	 */
	public boolean determineCellFate(boolean alive, int countLiveNeighbors){
		if (alive) {
			if (countLiveNeighbors == 2 || countLiveNeighbors == 3){
				return true;
			}
		} else {	// dead
			if (countLiveNeighbors == 3){
				return true;
			}
		}
		// Cell dies of overcrowding or loneliness :(
		return false;
	}
	
	
	/**
	 * Iterator for looping through game board left-to-right top-to-bottom
	 * @return ConwaysGameOfLifeIterator
	 */
	public Iterator createIterator(){
		return new ConwaysGameOfLifeIterator(this.petriDish);
	}
	
	/**
	 * Return alive/dead state of cell at given x,y coordinate
	 * @param x (x-coordinate)
	 * @param y (y-coordinate)
	 * @returns true (if alive state), false otherwise
	 */
	public boolean getStateAtPosition(int x, int y){
		return this.petriDish[y][x];
	}
	
	/**
	 * Set alive/dead state of cell at given x,y coordinate
	 * @param x (x-coordinate)
	 * @param y (y-coordinate)
	 */
	public void setStateAtPosition(int x, int y, boolean state){
		if (validatePosition(x,y)){
			this.petriDish[y][x] = state;
		} else {
			throw new ArrayIndexOutOfBoundsException();
		}
	}
	
	/**
	 * Validates that coordinates are within bounds of petriDish
	 * @param x (x-coordinate)
	 * @param y (y-coordinate)
	 * @return whether or not point is valid
	 */
	private boolean validatePosition(int x, int y) {
		if (x >= 0 && x < this.width && y >= 0 && y < this.height){
			return true;
		}
		return false;
	}
	
	public void displayGameBoard(){
		ConwaysGameOfLifeIterator iterator = (ConwaysGameOfLifeIterator) createIterator();
		int x = 0;
		while (iterator.hasNext()){
			x += 1;
			char cell = ((boolean) iterator.next()) ? '0' : 'X';
			
			System.out.print(cell);
			if ((x % width) == 0){
				System.out.println("\n");
			}
		}
		
	}
}
