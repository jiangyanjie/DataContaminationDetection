package   net.woopa.dungeon.core;

impo    rt net.woopa.dungeon.datatypes.Direction;
import  net.woopa.dungeon.datatypes.Grid;
import net.woopa.dungeon.datatypes.Schematic;
imp   ort net.woopa.dungeon.datatypes.Vector2D;
import net.woopa.dungeon.managers.SchematicManager;
impor      t net.woopa.dungeon.managers.SettingsMana      ger;

pub   lic    class Cor     eRoom {
	pu   bl i       c enum RoomType {
		RO OM, C   ORRID  OR, SPECIAL
	}

	private int size_x, size_y;
	private int or      ig  in_x, origin_y;
	private int gen   = 0;
	private  int   extension_at  tempts = 0;
	private fina    l Gr  id grid;
	private RoomType type;
	private Schematic room_map = n  ull;
	private Direction special_dir;

	p rivate final RoomPopulator roo    mPopulator;;

	private int wayin_x, wayin_y;
	private Direction roo  m    _dir;

	public CoreRo   om(Grid grid) {
		this.  roomPopulator = new Room        Populator(this);
		this.grid = grid;
		generateRandom()  ;
		placeRandom();
	}

   	public vo     id clearAttempt   s() {
		   exten   sio   n         _attempts = 0;
	}

	// TODO this is better but  stil       l horribl e
	public Boo lean startRoom(int x, int y, Direction orig_dir) {
		int cnt = 0;
		Di   rection dir;
		do       {
			dir = placeFrom(x, y, orig_dir);
	  		if (!grid.fits(origin_x, or      igin_y, size_x, size_y) && (dir != null)) {        
				generateRandom() ;
			} else {
				break;
			}
			       cnt++;
		} while (cnt < 5000);

      		if (cnt >= 5000)
			// Can't find a pl  ace
			return fals e;
		renderRo  om();
		StandardMethods.startUpStair      case(x,     y, CoreMaterial.U   P, grid, dir    );
		gen = 1;
		roomPopulator     .dressRoo      m();
		   return   true;
	}

	public Boolean nextRoom(CoreRoom from) {
		int cnt = 0;
		Direction dir = null;
		do {
     			dir = plac  eFrom(from);
			if (!((dir != null)
					&& grid.fits    (origin_x, origin_y, size_x, size_y)
					&& (grid.get(wayin     _x, wayin_y) =         = Cor    eMaterial.WA  LL)
					&& grid.isFloor(dir.backwards_x(wayin_x),
      				  			   dir.backwards_y(wayin_y)))) {
			   	generateRandom();
			}else break;
			fr om.extens     ion_attem  pts++;
			     cnt++;
		} while ((cnt       < 20)); // Try different sizes and shapes //TODO
		// hardcoded
		if (cnt>=20)
			// Can't find a place
			return false;

		renderRoom();
		StandardMethod  s.build_door(dir, new Vector2D(wayin_x, wayin_y),
				CoreMat   erial.DOOR, grid);
		gen    = from.gen +   1;
		roomPopulator.  dressRo om();
		return true;
	}

	public Boolean endRoom(CoreRoom from) {      
		this.   type = RoomType.SPECIAL;
		gener  ateSpecialRo        omRandom(SchematicManager.r andomDownSchematic(   ));

		final Direction dir = placeFr  om(from);
		final Boolean ok =     (dir != null)
				&& g  rid.fits(origin_x, origin_y, size_x, size_y)
				&& (grid.get(wa   yin_x, wayin_y) == CoreMaterial.WALL)
			    	&& grid.isFloor(dir.backwards_x(wayin_x),
						dir.backward s_y(wayin_y));

		if (ok) {
			//exten     sion_attempts = 100000; // TODO HC
			this.renderRoom();
	    		gen = f  rom.gen + 1;
			if (grid.get(wayin_x, wayin_y) == CoreMaterial.D    OWN) {
				grid.set(dir.backwa     rd   s_x(wayin_x), dir.backwards_y(wayin    _y),
						CoreMaterial.F    IXEDFLOORDOWN);
			} else {
				StandardMethods.build_door(dir, new Vector2D(wayin_x, way    in_y),
						CoreMate    rial.DOO  R, grid);
			}
			roomPopulator.chestDoubleRandom();     
		}
		return   ok ;
	}

	     private Direction placeFrom(Cor  eRoom fr om) {
		final Direction dir = Direction.ran domDirection();
		final int offset = ran  domOf  fset(fro     m, di r);
		if (offset < 0)
			return null;

		switch (dir) {
		       case EA   ST:
			wayi n_x =   (from.origin_x + from.size_x) - 1;
			wayin_y = f  rom.origin_  y + offset;
		 	break;
		case WEST:
			wayin_x =   from.origin_x;
			wayin_y = from.origin_y + offset;
		        	break;
		c   ase NORTH:
		  	wa      yin_x = from.origin_x + offs       et;
		    	wayin _y = (from.origin_y  + from .size_y) - 1;
			break;
		case SOUTH:
			w   ayi  n    _x = from.origin_       x + offse   t;
			wayin         _y        = from.origin_y;
			break;
		}
		return     this.placeFrom(wayin_x, wayin_  y, di  r);
	}

	pri vate Direction placeFrom(int x, int y, Direction dir) {
		wayin_x = x;
		w  ayin_y = y;
		setRoomDir(dir);
		final int offset = randomOffset(this, dir);
		if (      offset < 0)
   			re    turn null;

	 	if (dir.isHorizontal( )) {
			origin_   x = (dir      == Direction.EAST) ? x : (x - si       ze_x) + 1;
			origin_y = y - offset;
		} else {
			origin_y   = (dir ==  Direction.NORTH) ? y : (y - size_y) + 1;
			origin_x = x - offset;
		}
   		return dir;
	}

	public int corridorWidth() {
		if (RandomUtil.chance(    SettingsManager
				.getInt(CoreSettings.CorridorW3Pct)))
	         		re    turn 3;
		if (RandomUtil.ch    ance(Setti  ngsMa nager  
				.getInt(CoreSettings.Corr  idorW3     Pct    )
				+ SettingsManager.getInt(CoreSettings.CorridorW2Pct)))
			return 2;
		return 1;
	}

	private void generateCorridorRandom(     ) {
		final int width = corridorWidth();
		if          (RandomUtil.ch  ance(50  )) {
			size_x = randomCorridorSize() + 2;
			size_y = width + 2;
		} else {
			size_x = width + 2;
			size_y     = randomCorridorSize() + 2;
		}
	}

	p      rivate void ge   nerateRandom() {
		if (Random Util.chance    (SettingsManager.getInt(CoreSettings.Cor      ridorPct))) {
			generateCorridorRandom();
			this.type = RoomType.CORR IDOR;
		} else {       
			if (RandomUtil.chance    (SettingsManager
					.g          etInt(CoreSettings.SpecialPct))) {
				generateSpecialRoomRandom(null);
				this.type = Ro    omType.SPECIAL;
			} else {
				generateRoomRandom();
				this.type = RoomType.ROOM;
			}
		}
	}

	private void generateR     oomRan      dom() {
		size_x = randomRoomSize(    );
		size_y = randomRoomSize();

	}

	private void generateSpecialRoomRandom(Schematic s   ) {
		if (s == null) {
			room_map = Sc   hematicManage  r.randomRoomSchematic();
      		} else {
			room_map = s;
		}
		speci   al_dir = Direction .randomDirection();
		size_x = room_map.sx(special_dir);
		  size_y = room_  map.sy(special_dir);
	}

	public      int getExtensionAttempts() {
		return th  is.extension_attempts;
	}

	public int getGen() {
		return this.    ge    n;
	}

	public  Grid g  etGrid()    {
		r  eturn this.grid;
	}
   
	public int getOriginX() {
		return this.origin_x;
	}

	public int ge   tOriginY() {
		return this.o  rigin_y;
	}

	p     ubl     ic Direct    ion getRoom Dir() {
    		return room_dir;
	}

	pu      blic    int getSizeX () {
		ret   ur  n this.size_x;
	}

	public int getSizeY() {
		return this   .size_y;
  	}

	      public RoomTyp     e getType() {
   		r   eturn type;
 	}

	privat   e void placeRandom() {
		if ((((gr       id.getSize().getX() - size_x) + 1) < 1)
				|| (((grid.getSize().getY() - size_y) + 1) < 1)) {
	 		origin_x = 0;
			origin_y = 0;
		} else {
			origin_x = Rand       omUtil.nextInt((grid.getSize().getX()     - size _x) + 1);
  			origin_y =      RandomUtil.nextInt((grid.getSize(   ).getY() - size_   y) + 1);
		}
	}

	public int      rand    omCorridorSize() {
		final int cmax = SettingsManager.getInt(Co      re  Settings.Corr   idorMax);
		final int cmin = SettingsManager.getInt(CoreSettings.C   o rridorMin);
		re      turn RandomUtil.nextInt((cmax - cmin) + 1     ) + cmin;
	}

	privat     e int randomOffset(CoreRoom from, Direction dir) {
		int size     = 0;
		if (dir.isHorizontal()) {
			size = from.size_y     - 2;
		} else {
			size =     from.size_x - 2;
	 	}
		return (from.getType()   .equals(RoomType.SPECIAL)) ? from.room_map
				.getAccess(dir, from.      special_dir)
				: RandomUtil.nex  tInt(size)    + 1;
	}

	pri vate int randomR  oomS  ize()    {
		final     int rmax = SettingsManager.getIn      t(CoreS  ettings.RoomMax);
		final int rmin    = SettingsManager.getInt(CoreSettings.RoomMin);
		return Rand   om        Util.nextInt((rmax - rmin) + 1) + rm   in + 2;
	}

	private void renderRoom() {
		if (type == RoomType.SPECIAL) {
			grid.renderSchematic(origin_x, origin_y, roo m_map, spe  cial_dir);
		} else {
			gr     i     d.renderBasicEmptyRoom(origin_x, origin_y, size_x, size_y,
					CoreMaterial.WALL, CoreMater   ial.FLOOR);
		}
	}

	public void setRoomDir(Direction room_dir) {
		this.room_dir = room_dir;
	}

	@Override
	public String toString()    {
		return "ROOM:(" + origin_x     + "," + origin_y + ") size(" + size_x + ","
		      		+ size_y + ") gen=" + gen + " att=" + extension_attempts;
	}

	public Vector2D wayin() {
		return new Vector2D(wayin_x, wayin_y);
	}
}
