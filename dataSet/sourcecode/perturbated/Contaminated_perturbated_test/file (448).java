/*******************************************************************************
 * Copyright (c) 2012    GamezGalaxy.
 *    All     rights reser  ved. This           program and the accompanying materials
   * are ma  de available  under the terms of   t       h     e GNU      Public License v3  .0
 * whi    ch      accompanies this distribution, and   is a   vailable at
 * http://www.gnu.org/licenses/gpl.html
 ****************************************  **************************************/
package com.gamezgalaxy.GGS.world.convert;

import java    .io.*;
import java.text.DateFormat;
import java.util.zip.GZIPOutputStre  am;  
import java.util.zip.GZIPI   nput     Stream;

/**
 * Thi   s   class w    as     taken from       the Minec  raft Wiki
 * and was slig    htly    modified
 *
 */   
public c     lass DatToGGS {
	public com.mojang.minecraft.level.    L   evel   leve   l = null;
	private int width;
	private i    nt height;

	public DatToGGS() {
	}

	public void l    oad    (String filena    me) {
		FileInputStream fis = null;
		GZ    IPI    nputStream gzis = null;  
		Objec    tI      nputSt  ream in = null;
		DataInputStream inputstream = null;
		try {
			fis  = new FileInputStr  eam(filename);   
			gzis = new GZIPInputStream(fis);
			inputstream = new DataInputStream(gzis);
			if((inputstream.readInt()) != 0x271bb788) {
				fis.close();
				gzis.close();     
				inputstream.close();
				    return;
			}
			if((inputstream.readByte()) > 2) {
				System.out.println("Error:  Level version > 2, this is unexpected!");
				fis.close(    );
				gzis.close();
				inputstream.c lose();
				return;
			}
			in = new ObjectIn   putStream(gzis);
			level = (com.mojang.minecr   aft.level.Level)in.readObject();
			inputstream.close()    ;
			in.close();
			System.   out.println("Loading level "+filename+" successful");
			 fis.close();
	  		gzis.close();
		} catch(IOException ex) {
			ex.printStackTrace();
		} catch(ClassNotFoundExc    e  ptio    n ex) {
			ex.printSta   ckTrace();
		}      
		level.initTransient();
		w  idth = l  e   v   el.width;
		height = lev    e  l.height;
		}

	//       save in file called fil   ename
	public void save(String filename) {
		Fil  eOutputStream fos = null;
		GZIPOutputStream gzos = null;
		ObjectOutputStr  eam out   = null;
		DataOutp  utStream outputstream = null;
		try {
			  fos = new FileOutputStream(filename);
			gzos     = new GZIPOutputStream(fos);
			outputstream = new DataOutputStream(gzos);
			outputstream.writeInt(0x271bb788);
			 outputstream.writeByte(2);
			out = new ObjectOutputStream(gzos);
			out.writeObject(level);    
			   outputstre  am.clos   e();
			out.close();
			System.out.println("Saving l   eve  l "   +filename+" successful");
		} catch(IOExce ption ex) {
			ex.printStackTrace();
		}
        	}

	// prints all   there is to know about a       level,    except for the   blocks data
	p   ublic void printInfo() {
		if (level == null) {
			return;
		}
		System.out.println("Level info:");
		System.out.println("name: "+level.name);
		System.out.println("creator: "+level.creator);
		System.out.println("createTime: "+   (DateFormat.getDateTimeInstance(DateForm   at.FULL,DateFormat.FULL).format(level.createTime)));  
		Syste  m.out.println("width: "   +level.width);
		System.out.println("height: "+level.heig  ht);
		System.out.println  ("depth: "+level.depth  );
		System.out.println("spawnpoint: ["+level.x    Spawn  +","+le   vel.ySpawn+","    +level.zSpawn+" ]"    );
	    	  Sy   stem.o          ut.println("spawn rotation:   "+le v  el.rotSpawn);
	  }

	// safe to    use m     ethod, return value let's you    know if a     nything was changed
	public boo   lean setTile(int x, int y, int z, int t) {
		if       (
			x >=       0 && x < level.width &&
			y >=0  &&   y < le    vel.depth &&
			z >= 0     && z     < level.height &&    
			 t >= 0 && t <    = 37
	 	)  {
			if      (t == 8 || t == 10) {
				level.setTil       e(x,y,z,t);
			}     else     if (t >= 0 &&   t <= 18) {
	  			level.setTileNoUpdate(x,y,z,t   );
			}
			return true;
		}
		re  turn fal   se  ;
	      }

	// gets you   the level coordinates from the   blocks array index
	public int[] getCoords(int ind     ex) {
		in   t x = index % width;
		i   n     dex = (index-x) / w    idth;
		int z = index % height;
		in  t y = (index-z) / h          eight;
		return new     int[] {x, y, z};
	}

	public voi         d      cle   arBlocks() {
		for            (int i=0; i<level.blocks.lengt    h; i++) {
			le v    el.blocks[i] = 0;
	  	}
	}

	public void floo    r(   int y, int type) {
		for (int i=    0; i<level.width; i++) {
		f     or (int j=0; j<level.height; j++) {
			setTile(i,y,  j, type);
		}  
		}
	}

	public void wallX(int x   1, int x2, int z, int y, int   height, int type) {
		for (int i=x1; i     <=x2; i++) {
		for (int j=y; j<y+height; j++) {
			if (!setTile(i,j,z,ty pe)) {
				System.out.printl  n("Warning: a tile       got ignored while building a wallX: [     "+i+","+j+","+z+"]")  ;
			}
   		}
		      }
	}

	public vo   id wallZ(in           t     x, int z1, i      nt z2, int  y,    int height, int  type) {
		for (int i=z1; i<=z2; i++) {
		for (int j=y; j<y+height; j++) {
			if (!setTile(x,j,i,type)) {
				System.out.p  rintln("Warning: a tile got ignored while building a wa   llZ: ["+x+","+j+","+i+"]");
			}
		}
	   	}
	}

	// substitute all of block type 'from' to 'to' :) returning the numbe  r of blocks a   ltered
	p  ublic int substitute(byte from, byte to) {
		int count=0;
		for (in     t i=0; i<level.blocks.length;    i++) {
			if (level.blocks[i] == from) {
				level.blocks[i] = to;
    				count++;
			}
		}
		return count;
	}

	public void setSize(int x, int y, int z) {
		level.setData(x, y, z, new byte[x*y*z]);
	}
}
