/**
 * 
 */
package com.ptc.gameoflife.ui;

import static com.ptc.gameoflife.config.Configuration.ALIVE_CHAR;
import static com.ptc.gameoflife.config.Configuration.COLUMN_MAX_SIZE;
import static com.ptc.gameoflife.config.Configuration.DEAD_CHAR;
import static com.ptc.gameoflife.config.Configuration.LINE_SEPERATOR;
import static com.ptc.gameoflife.config.Configuration.ROW_MAX_SIZE;

import com.ptc.gameoflife.Cell;
import com.ptc.gameoflife.Grid;
import com.ptc.gameoflife.State;
import com.ptc.gameoflife.util.GridUtil;

/**
 * @author sagar_borse
 *
 */
public abstract class AbstractRenderer implements IRenderer {

	// Purposefully made final so that one who is extending should not change the logic to convert matrix arena to string.
	public final String getGridAsMatrixString(Grid grid) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("Generation : " + Grid.generationCounter + LINE_SEPERATOR);
		
		Cell[][] cells = grid.getCells();
		for (int row = 0; row < ROW_MAX_SIZE; row++) {
			for (int column = 0; column < COLUMN_MAX_SIZE; column++) {
				State status = cells[row][column].getState();
				buffer.append(status == State.DEAD ? DEAD_CHAR : ALIVE_CHAR);
			 // buffer.append("[" + row + "," + column + "] ");
			}
			buffer.append(LINE_SEPERATOR);
		}
		return buffer.toString();
	}

	// Purposefully made final so that one who is extending should not change the logic to calculate neighbor count for each cell.
	public final String getNeighborCountGrid(Grid grid) {
		StringBuffer buffer = new StringBuffer();
		for (int row = 0; row < ROW_MAX_SIZE; row++) {
			for (int column = 0; column < COLUMN_MAX_SIZE; column++) {
				buffer.append(GridUtil.getAliveNeighbourCount(grid.getCell(row, column), grid));
			}
			buffer.append(LINE_SEPERATOR);
		}
		
		return buffer.toString();
	}

	// Developer who is extending this abstract class should implement this display method, 
	// so as to facilitate the rendering in other output media. 
	public abstract void display(String text);

}
