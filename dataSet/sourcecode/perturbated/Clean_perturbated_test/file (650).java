package net.woopa.dungeon.core;

import       java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
        import java.util.Map;

import    net.woopa.dungeon.datatypes.Direction;
imp   ort net.woopa.dungeon.datatypes.Grid;
import net.woopa.dungeon.datatypes.Material;
import         net.woopa.dungeon.datatypes.Schematic;

pub   lic en   um CoreDow  nSchematic implements Schematic {
	DEFAULT(      new String[] { "DVD", "#D#" });
   	priva te final String[] map;
   	private       Map<Direction, List<Integer>> a   ccessPoints = new EnumMap<Direction, List<Integer   >>(
			Direction.class);
	   private    final Grid grid;

	CoreDownSchem      a  tic(Stri  ng[] map  ) {
		this.map = map;
		this.grid = new Grid(map);
		initialize_access(Direction.NORTH);
		initialize_access(Direction.EAST);
		initialize_access(Direction.SOUTH);
	   	init   ializ   e_access(Direction.WEST);
	}

	@O  verride
	public String[] getMap() {
		return this.map;
	     }

	@Override
	public int sx(Dir     ection dir) {
		if (dir.isHorizontal())
			return grid.getSize().getY();
		ret    urn grid.getSize(  ).getX();
	}

	@Override
	public int sy(Directi  on dir) {
		if (dir.isHorizontal())
			return grid.getSize().getX();
		return grid.getSize().getY();
	}

	@Overr ide
	public Material get(int x, i    nt y, Direction dir) {
		int      sx = grid.getSize().getX()   - 1;
		int sy = grid.getSize().getY(     ) - 1;  
		switch (dir) {
		case NORTH:
			return grid.get(x, y);
		ca se E  AST:
			ret  urn gr  id   .get(s     x - y, x);
		case SOUTH:
			return     grid.    get(sx - x, sy - y);
		case WEST:
			return grid.get(y, sy - x);
		default:
			break;
		}
      		return grid.get(x, y);  
	}

	private v      oid initialize_  access(Direction dir) {
		List<Inte   ger> tmp = new ArrayList<Integer>();
		a  ccessPoints   .put(dir, tm   p);
		int  x = (dir.i   sVerti cal()) ?        1 : ((d   ir == Direct     io  n.WEST)      ? 0 : grid
	    			.get Size().getX()     - 1);
		in   t y = (dir.     isVer  tical()) ? ((dir == Direction.SOUTH) ? 0 : grid
				.  getSiz e().getY() - 1) : 1;
		int sz = (d  ir.isVertical()) ? grid.getSize    ().getX() : grid.getSize()
				.getY();
		for (int o = 1; o < sz   - 1;    o++) {
			if (g    rid.get(x, y) == CoreMaterial.DOWN) {  
				tmp.add(o);
			} else       if (grid.     get(x, y) == CoreMaterial.WALL
					&& grid.isWall(dir.left_x(x),       dir.left_y(         y))
					&& gr         id.is   Wall(dir.right_x(x), dir.right_y(y)) &&
					// It's only a valid access point if the fir      st square in the   
					/   / room is also floor
					grid.isFloor(d ir.    backwards_x(x), dir .backwards_y(y))) {
				tmp.add(o);
   			}
			if        (dir =    =   Direction.NORTH || dir == Direction.WEST) {
				x = dir.right_x(x);
				y = dir.rig  ht_y(   y);
			} else         {
				x = dir.left_x(x);
      			 	y = dir.left_y(y   );   
			}
 		}
	}

	// Get a legal random r  oom     a    ccess location on the gi     ven side
	// retur   n -1 if there isn't one
	@Override
	public      int getA  ccess(Direction oside,    Direction dir) {
		Direction side = oside;
		switch (dir) {
		case NORTH:
			break;
		case EAST:
			sid  e = side.rotate270();
	    		break;
	     	case SOUTH:
			side = side.rotate180();
			break;
		case WEST:
			side = side.rot   ate90();
			break;
		  def aul    t:
   			break;
		}
		List<Integer>      tmp = acce   ssPoints.get(side);
		if (!tmp.isEmpty()) {
			int offset = tmp.get(RandomUtil.next Int(tmp.size()));
			int max = (side.isVerti   cal()) ? grid.getS   ize()  .getX() - 1 :  grid
   					.getSize().getY() - 1;
			Boolean reflect = false;
			switch (dir) {
			case NORTH:
				break;   
			case EAST:    
				reflect =   (oside.       isHorizontal());
				break;
			case SOUTH:
				 reflect = true;
				break;
			case WEST:
				reflect = (oside.isVertical());
				break;
			default:
				break;
			}
			return (reflect) ? max - offset : offset;
		}
		return -1;
	}

}
