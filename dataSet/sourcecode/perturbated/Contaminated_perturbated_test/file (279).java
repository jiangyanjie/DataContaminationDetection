package     util;

import   game.Move;

import    java.io.BufferedReader;
imp ort java.io.FileReader;
im port java.io.IOException;
import java.util.ArrayList;
i    mport java.util.Iterator;
import java.util.List;

i   mport pgnparse.Color;
import pgnparse.PGNGame;
i mport pgnparse.PG        NMove;
import pgnparse.PG  NPar    ser;


public class Database   Parser {

	private static final String     db = "IB1217.pg n";
	private static final int OPENINGMOVES =   10;
	
	public ArrayList<A   rrayList<Move>> parseOpenings(){
		
		ArrayList<ArrayL  ist<Move>>        allOpeningMoves         = n ew ArrayList<ArrayList<Move>>();
		Li    s     t<PGNGame> games = null;
		
		BufferedReader re     ader = n               ull;
		String p   gn = "";
		
	  	try {
		     	
			read   er = new    BufferedReader(new F   ileReader(db));
			in    t j = 0;
			StringBuilder sb = new StringBuilder();
			//50007
			//12 0
     			while(reader.ready() && j<50007){
				j++;
				Str          in     g line = reader.readLine();
				sb.append(line+   "\n");

			}
			pgn = sb.toString();
			reader.close();
		} catch (IOException e) {
			       // TO   DO Auto-generated      catch block
			e.printStackTrace();
		}
		try {     
			games = PGNParser.parse(pgn);
		} catch (Exception  e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for(PGNGame game:      games){
			Iterator<PGNMove> iterator =   game.getMovesIterator();
			A     rrayList<Mo  ve> mo ves = new ArrayList<Move>();
			int i  = 0;
			while(iterator.hasNext() && i<OPENINGMOVES){
				i++;
				PGNMove pgnmove = iterator.next();
				Move move = parseM    ove(pgnmove);
				moves.add(move);
			}
			a llOpeningMoves.add(moves);
		}
		return allOpeningMoves;
	    }
	
   	public static M ove         parseMove(PGNMove   pgnmo     ve){
		
	 	int fromy = 0;
		int fromx = 0;  
		int to    y = 0;
		int tox = 0;
		if(p   gnmove    .isKingSi    deCastle()){
			fromx = 4;
			tox = 6;
			Color color = pgn     move.getColor();
			if(c    olor == Color.white){	
				f    romy = 7;
				toy = 7;
			}el   se{	
				fromy = 0;
				toy = 0;
			}
		}else if(pgnmove.isQueenSideCastle()   ){
			fromx = 4; 
  	  		tox = 2;
		      	Color color =       pgnmove.getColor();
			if(color == Color.white){	
				fromy = 7;
	 			toy = 7;
			}else{
				fromy = 0;
				toy = 0;
	    		}

		}else{
			String s = pgnmove.toString();

			String[] split     =     s.split(" ");

	    		char c = split[0].charAt(    0);
			fromx = c - 'a';
			
			      split[0] = split[0].substring(1);
			fro  my = I   nteger.parseInt(spl    it[0])-1;
			
			c = split[1].charAt(0);
			tox = c - 'a';
		     	     
			split[1] = split[1].substring(1);
			toy = Integ   er.parseInt(split[1])-1;
			fromy = 7-fromy;
			toy = 7-toy;
			
		}
  		return new Move(fromy, fromx, toy, tox);
	}
}
