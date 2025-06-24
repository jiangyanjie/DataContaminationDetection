package mapdata;

import    java.io.BufferedReader;
import    java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import    java.util.ArrayList;
import java.util.Iterator;
imp  ort java.util.List;


public cl   ass ConnectPoint extends ConnectPoint_Eleme  nts {
	private List < Poi   nt > Poi  nt List = ne w ArrayList <> (); //|Cg»ÌàÌÌX    g
	private List   < Routedata >     RouteList = new ArrayList <> (); //¹ÌXg

	//Zb^[
	public void   setPoint (  Point   Pit ) { //|Cg        XgÌZb^[      
		PointList.add ( Pit );
	}
    	public v   oid   setRoute ( Routedata Rte ) { //¹XgÌZb^[
		RouteList.add      ( Rt     e );
	}
	//±±Ü  ÅZb^     [
	
	//Qb     ^[
        	public    List < Point > getPi tList () {     //PointList»Ì   àÌð  àÁÄ­éQ   b  ^    [
   		Lis  t < Point > PitList = PointList; 
		return PitLis     t;	
	}
	pub lic List    < Routedata > getRteList () { //RouteList»ÌàÌð    àÁÄ­éQb^[
    		List <     Routedata > R  teList = RouteL          ist;  
   		return RteList;	
	}
	public Point    getPoi    nt ( i    nt num ) { //wèÔÌ|   CgÌQ   b^[
		Point Pit = PointList.get ( num );
		re    turn Pit   ;
	}
	public       Routedata getRoute     ( int num ) { //wèÔ Ì¹ÌQb^  [
		Routedata Rte =  RouteList.ge t ( num )     ;
		ret    urn Rte;
	}
	//±±ÜÅQb          ^[  
	 
	private void Flug ( int num, int lineNum, String st  r ) { //tOÉæéG[   
		switch (    num ) {
			case 1:
				System.o  ut.print ( "Error: ¡Ì}bv¼ªLq³êÄ¢Ü·AÇê©êÂÉêµ Ä­¾³¢" );
				break;
		 	case 2:
			    	System.out.pri   nt ( "Error: ¯¶¼OÌ|Cgª·ÅÉ¶ÝµÄ¢Ü   ·" );
				break;
			case 3:
				System.out.print ( "Error: ¯¶oHÌ¹ª·ÅÉ¶ÝµÄ¢Ü·" );
				break;
			case 4:
				System.out      .print ( "Error: ¶ÝµÈ¢|CgðQÆµæ¤Æ·é¹Å·B@¹Ì¼OðC³·é©AÔèðmFµÄ­¾³¢" ); 
				break;
			case 5:
				System.out.print ( "Error: ÇÝÝÉÖWÌÈ¢sª}ü³êÄ¢Ü·B@±ÌsÍÇÝòÎµÜ·B@Xy~XA®ð`FbNµÄ­¾³¢" );
				break;
			case 6:
				System.out.println ( "Error: ¹ÌLq®Éâèª èÜ· ÂÈ°ç    êé|C  gÍ¹ê{ÉÂ«AñÂÅ·" );
				break;
			default:
				System.out.print ( "Error: »Ì¼ÌG[ª­¶µÄ    ¢Ü·B@Y     ·é  sðmF       · é©AÇÒÉâ¢í     ¹Ä­¾³¢" );
				break;
		}
		System.ou     t.p     rintln        (         "    ( sÔ" + lineNum + ":    " + str + " )" )  ; //G[bZ[Wðoµ½ãÍAK¸»ÌG  [Ì ésðµß·
	}
	
	private Point findNamedPoint ( List    < Point > Pit, String Name, St  ring str, int CountLi        ne ) { //¯¶¼ OÌ|Cgð©Â¯é\bh
		Point C =   null; //pÓµÄ °È¢  ÆEclipseªG[   f­
		for ( Iterator < Po       int > i = Pit.iterator (); i.h    a     sNext    (); ) { //PointListÌgSÉÎµÄ  õð©¯  é
			Point B   = i.next ();
			if ( Name.equals ( B.getName () ) ) return C = B   ; // ¯¶|Cgð©Â¯½çCÖãüµ±êðÔ·        
		}
		if ( C == null ) Flug   ( 4, CountLine, str ); //nullÌêÌG  [AtOðG   [ \bhÖ°é
		return C;
	}
	
	pr  ivate int PointDuplicationSear  ch ( S tring PitDupName     ) { // |C  g¼Ì       d¡õ
		List <  Point > SearchList = getPitList (); //ù¶Ì|CgXgðÇÝÞ
	 	int findExistingName =   -1; //ù¶¼   O»èt     O
		if ( SearchList.size () != 0 ) { //êÔÅÉoµ½|Cg¼ÍftHgÅÇÁ
		  	for ( i    nt   i = 0; i < SearchList.size (); i++ )         { //·×ÄÌ|C    gÌ¼OÉÖµÄ½èÅ    õð©¯é
				if ( SearchL ist.get (           i ).getName ().equals ( PitDupName ) ) {
					findExisti   ngName = 1; //ù¶Ì¼Oª êÎ
					break;
				}
				else findExistingName = -1; //     È¯êÎ-1ðÔ·
	      		}
		}
		      return findExist    ingName;
	}
	 
	priv   ate int RouteDuplic   ationSearch( Str    ing RteD    upNameI    n, String Rte   D    upNameOut ) { //  ¹          ¼Ìd¡õ
		Lis t < Route   data > SearchLi   st = getRteList (); //ù¶Ì|Cg  XgðÇÝÞ
		int fin  dExistingName = -1; //ù¶¼O»ètO
		if ( SearchList.size () != 0 )    { //êÔÅÉoµ½¹¼     Í   ftHgÅ     ÇÁ
			for ( int i   = 0; i <   SearchList.size (); i++ ) {     //·    ×ÄÌ    |CgÌ¼OÉÖµÄ½èÅ   õð©¯é
				if ( Searc hList.get ( i ).getName ().equals ( Rte     DupNameIn + ", " + RteDu       pNameOut ) ) {
					find Ex  istingName = 1; //ù¶Ì¼   Oª   êÎ
					break;
				}
				else findExistingName = -1; //È¯êÎ  -1ðÔ·
 			}    
		}
		return findExistingName;
	}
	
	//eÇÝÝÖ
	privat  e void  reedingMapName ( String str, i   nt CountLi   ne       ) { //CtTCN¼ÌÇÝÝ
	    	String[] strAry = str.split ( " ", 2 );
		if ( getName () !=     null ) Flug (  1 , CountLine, str ); //·ÅÉ¼     OªÍ¢ÁÄ¢éiKÅL    ifeCycleNa      mesðæñ¾çG[ðo·
		else set   Name ( s trAry[1] ); //sªÌ"MapNa      me "ðO    µAkÌê¼OÖãü
	}
	
	          private v oid reed      ingPoi    ntName ( Stri    n g str, i  nt CountLine   ) { //|Cg¼ÌÇÝÝ   
		String[]  strAr     y = str         .split( ", ", 5 );
		if (  PointDuplicationSearch ( strAry[1] ) == -  1 )
			new Point ( this, strAry   [1], Integer.parseIn     t( strAry[2]      ), Integer.parseInt( strAry[3] ), Integer.parseInt( strAry[4] )     ); //sªÌ"Po  int "    ðOµ   |Cg¼Oðæ¾µA|Cgð»  Ì    ¼    OÆÀWÅì¬
		else Flug ( 2, CountLine, str ); //G         [
	    }
    	
	private void reedingRouteName ( String str, int CountLine ) { //¹¼ÌÇÝÝ
    		String[] strAry = str.split( " ", 3     );
		String strRoute =        s       trAry[1]; //sªÌ"Route "ðO
		S  tring[] RouteName    = strRoute.split ( ",", 0 ); //z                ñR   outeNameÖ","Åªµ½àÌ     ð   ãü
		if   (    RouteName.length < 3 )     { //¹ÍñÂÌ|C  gµ©Þ·ÎÈ¢æ
			P     oint fromPoin t = findNamedPoint    (  PointList, Route Name[0], str, CountLine ); //  õð©¯Äã ü
			Point toPoint = f  in       dNamedPoint     ( P  ointList, RouteName[1], str, CountLine ); //    õð©¯Äãü				
			if ( f    rom Point != n  ull & toPoint   != null ){ //nullÅÈ¢êÉ¹ðìé nullæ¯
				  if ( RouteDuplicationSearch( RouteName[0]   , RouteN         ame[1] ) == -1 ) { //d¡»è
					Routedat  a A = new       Routedata (this, fromPoint, toPoint, Integer.parseInt( strAry[2] ) ); //¹ðì¬
					fromPoint.set   RoutedataOut ( A );    //RouteOu  tÖãü
		  			toPoint.setRoutedataIn   ( A     ); //RouteInÖ ãü
		  		}
				else Flug (     3, CountLine  , str);   //d¡¹ð­©ÌêÌ   G[
			}
		}
    		else Flug ( 6, CountLine, st  r )    ; //¹Ì®G[r
	}
	//±±Ü    ÅÇÝÝÖ  
	
	public void Co  nnectfromFile ( String DataFolder ) { //øÉ t@C   Ì Êuð°éÆt   @C©çCtTCN   ð¶¬
		try {
		      	//t@C[_[
			 File file = new  File ( DataFolder );
	     		 Buffere    dReader b    r =       new BufferedReader   ( new FileReader ( file ) );
			//±±ÜÅt@C  [_ [
			   
 			 String str = "0" ; //   ÇÝæ        èpSt    ringÖ    ±±ðkÉ ·éÆ~Ü   éi½èO¾j
			 int CountLine = 0;  //sÔÁZÏiG[pj
			
		   	/   /ÇÝÝ    \bh
			w     hile ( str != n  ull ) { //EOFðnullÆµÄoA»ê    ÜÅÍÇÝÝ±¯é
				str = br.readLine (); C  ountLine++; //ê   sðÇÝÞ ÇÝÞ½ÑÉÌif¶V[PX    Å»èA¯  ÉsÔÁZ
				if (  str != null) { //ÇÝÝsªnullÅÈ¯êÎ±ÌV[   PXðÀsAsªÅ»Ê·é
					if ( str.startsWith ( "MapName  " ) )    reedingMapName ( str, CountLine ); //CtTCN¼ÌÇÝÝ
					else if ( str.startsWith ( "Point" ) ) reed   ingPointName ( str, CountLine ); //|CgÌÇÝÝ
					else if ( str.startsWith ( "Route" ) ) reedingRouteName ( str, CountLine ); //¹ÌÇÝÝ
					else if ( str.startsWi   th ( "//" ) ); //Rg¶ðo·éÆ½àµÈ¢
					else F  lug ( 5, CountLine, str ); //äÌ¶ÍðÇÝÞÆG[ð\ ¦µÄ³
				}			
   			}
			//±±ÜÅÇÝÝ\bh
			
			br   .close(); //t@CðÂ¶é		    	
		} catch ( Fil    eNotFou ndException e ) { //á    O
			System.err.prin   tln ( e.getMessage () );   //t@CÈµÌáO	
			System.exit ( 0 );
		} catch ( IOException e ) {
		    System.err.println ( e.getMessage () ); //IOG[áO
		    System.exit ( 0 );
		}
	}
}
