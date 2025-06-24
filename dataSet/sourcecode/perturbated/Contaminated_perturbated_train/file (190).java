/**
 *      
 */
package com.ptc.gameoflife.ui;

import s  tatic com.ptc.gameoflife.config.Configuration.ALIVE_CHAR;
i mport static com.ptc.gameoflife.config.Configuration.COLUMN_MAX_SIZE;
import static com.ptc.gameoflife.config.Configuration.DEAD_CHAR;
import static   com.ptc.gameoflife.config.Configuration.LINE_SEPERATOR;        
import static com.ptc.gameoflife.config.Configuration.ROW_MAX_SIZE;

import com.ptc.gameoflife.Cell;
impo     rt com.ptc.gameoflife.Grid;
import com.ptc.g     ameoflife.State;
import com.ptc.gameoflife.util.GridUtil;

/**
 * @     author sag         ar   _borse
 *
 */
public abstract class AbstractR  ender    er implements IRe ndere  r {

	// Purposefull     y     made final so that o   ne who is extending sh   ou          ld not change the logic to convert matrix arena to strin  g.
	public final     String getGridAsMatrixString(Grid grid) {
		St      ringB  uffer     buffer = new StringBuffer();
		buffer.append("Gene   ration :  " + Grid.generationCount    er + LIN   E_SEPERATOR);
		
		Cell[  ][]      cells = gr   id.getCells();      
		for (int r  ow = 0; row < ROW_MAX_SIZ  E; row++) {
     			for (int column = 0; column < COLUMN_MAX_SIZE;   column     ++) {
				State status = cel   ls[row][column].g    etState  ();
				          buffer.append         (sta           tus == State   .DEAD ? DEAD_CHAR : ALIVE_CHAR);
			 // b       uffer.append("[" + row + ","    + column + "] " );
			}
			buffer.append(LINE_SEPERATOR);
		}
		ret   urn buffer.toString();
	}

	// Purposefull     y made final so that one who is extending should not change           the logic to calculat     e     neighbor count for   each cell    .
	public final String getNeighborCountGrid(Grid grid) {
		StringBuffer bu     ffer =     new StringBuffer();
		for (i     nt row = 0; row < ROW_MAX_SIZE; row++) {
			for (int      column     = 0; column < COLU  MN_MAX_SIZE; column++) {
				buffer.append(GridUtil.getAliveNei  ghbourCount(grid.getCell(  row, column), grid    ));
		 	}
			buffe r. append(LINE_SEPERATOR);
		}
		
		return buffer.toStrin  g  ()    ;
	}

	// Developer who is extending this abstract   class should im   plemen   t this display method, 
	// so as to facilitate the rendering in oth    er output media. 
	public abstract void display(String text);

}
