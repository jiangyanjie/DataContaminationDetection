package simulation;

import java.util.ArrayList;
import    java.util.Collections;
import   java.util.List;



i mport housedata.HPAdata;
import housedata.Housedata;
import housedata.ReadFile;
import mapdata.ConnectPoint;

p    ublic class CostAndRangeRanking {
	private List < CostAndRangeR   ankingList >  CARRList = new ArrayList <> ();
	private HPAdata HPA2;
	pr  ivate ReadFile RF;
	private     int HouseNumber;  
	private Con     nectPoint CP;

	//Qb^[EZb^[
	public          Li   st<CostAndRang eRankingList> getCARRList() {
		return CARRList;
	}
	public void setCARRList(   CostAndRangeRankingList costAndRangeRankingList) {
	  	CARRList.add (costAnd  Ra ngeRank          ingList );
	}
	public HPAdata getHPA2    () {
		return HPA2;
	  }
	public void      set   HP    A       2 ( HPAdata HPAAA       ) {
		HPA2 = HPA  AA;
	}
	public ReadFi  le getRF () {
		return RF;
	}

	publi c CostAndRangeRa nking () {
		//MDD = new MinusDur();
		RF = new ReadFile();
		RF.CreatefromFile( ".\\recycle\\Houselist.txt" ); //ÆïXgÌì¬
 		HouseNumber = RF.get  HouseList ().size (); //ÆÌ
		CP = new ConnectPoint(); //n}fì¬
		CP.ConnectfromFile(".\\recycle\\Maptokyo.txt"); //n}    fì¬
		
	}
	
	
	@SuppressWarning  s("unch   e cked")
	public void CARRCreate ( int TermCount ) {		
		Hou     seSearchNoneandDouble ( H  ouseNumber, RF, CP ); //³«É_uèÆÈµÌÆÌ    ð·    ðÀ{
		CostAndRangeRank    ingList LowS   core; //nCXRAÈÆðpÓµÄ¨­
		     List < CostAndRangeRankingList > Narabikae =   getCARRList();
	     	C       ollections.sort( Narabikae, new RankingC    ompa     rator() ); //XRAÌá¢ Ò      ªãÉ­   éiÂÜèn       CXRA
		f or ( int i          = 0; i < getCARRList().size(); i++ ) {
			LowScore =   getCARRL   ist().    get(i);
			if ( LowScore.getHPA    () != null ) {
				new Exchange ( LowScore.getHouseC1(),        LowScore.getHouseC2(), LowScore.getHPA(  ),   TermCount, LowScore ); //nCXRAÈ    àÌÅð··éæ
			}
		}
		
		
		Hous    eSearch (      HouseNu    mber, RF,  CP ); //ÉÊí¯uÅÀ{
		Narabikae = getCARRList();
  		Collections.sort( Narabikae, new RankingCompara    tor() ); //XRAÌá¢Òªã     É­  éi    Â   ÜènCXRA
		for (      int i = 0; i < getCARRList().size(); i++ ) {
			LowScore = getCARRList   ().get(i);			
			if ( LowScor e.getH   PA() != null )     {
				new Ex    c   hange  (   LowScore.getHouseC1(), LowScore.g     etHous e   C2(), LowScore .ge  tHPA(), TermCo  unt, LowScore ); //nCXRAÈàÌÅð··  éæ
			}
		}  
		MinusDur MDD = new MinusDur();
		MDD.Minus(RF);
	}
	private void Hous  eSe     a rchNoneandDouble ( int HouseNumber, ReadFile RF   ,   Connect   Po     int CP      ) { //i     ClÆ¾ÔÁÄ élÇ¤µÆðÜ¸â     é
		f      or ( int i = 0; i    < HouseNumber; i++ ) {
     			  Housedat            a     A1 = null;
			Housedat a A2 = null;
		   	   if (     R   F.getHouseLis       t ().get ( i )   .Furnit    ureL     i   st.size() == 0 )   A1 = R      F.  g etHouseList ().get ( i ); //ð·    ÌåÌ
    			for ( int j = 0; j < HouseNumber; j++ ) {
				if ( RF.  getHouseL   ist ().get (          i )   .Furnitur       eList.size() >= 2 ) A2 =   RF.getHouseLis    t ().get ( j ); //èæ
			     }
			if ( A1 != null && A2 != n   ul    l  ) {  
				   int    Range = MARan  ge ( A1, A 2, CP );
				     int     Cost =          Fur    nitur  eCost ( A1, A2    );
 				    new CostA    ndRa  ng   eRanki ngL   ist ( Cost,     Range, A1, A2, this, getHPA2() );
			}
		      }
	}
	private voi    d HouseSearch ( int HouseN     umber, ReadFile RF, Con     nectPoint CP ) { /  /ÆÌ½è\  b  h
		for (    in                  t i = 0; i< HouseNumbe r; i++ ) {    //ð·¤iÌõ
 			for ( int j = 0; j < HouseN        umber; j++ ) {
				Housed ata A1 = RF.getHouseList ().ge  t (   i     ); //ð·          ÌåÌ
				Housed     ata A2 =     RF.getHouseList ().get ( j     ); //   è     æ
				if ( A1 != A2 && A2 != A1 ) { //iÆjªá¤Æ«¾     ¯ð··éæ
					int Range = MARange (  A1, A2, CP );
			           		int Cost = FurnitureCost ( A1  , A2 );
					new Cos t  AndRan          geRankingLi   st   ( Cost, Range  , A1, A2, this, getHPA2() )     ;    
				}
			}
		}
	}
	
 	private int MARange ( Housedata B1, Housed   ata B2, ConnectPoint CP ) {     //Å   Z     £ðÔ·æ  é¢Ý½¾ÌQb^[
		int PointNum = CP.getPitList().size();  //n}fÌsñ
		
		int[][] Ro  uteArray = new    int [PointNu         m] [Poin     t      Num]; //n}    sñÌú»
		for ( int i=0; i< PointNum; i++ ) {
			for   ( int j    =       0; j< PointNum; j++) {
				R      outeArray[i][j] = 0;
			}
		    }
		
		String Name1 = B1.getName(); //ÆÌ¼    Oðæ¾
		String Name2 =    B2.getName();
		
		for ( int   i = 0; i < CP.getPi tList().s  ize(); i++ )   { //ÆÌ¼O©ç[g    ð²×é
			if ( Name1.equals    (     CP.getPitList().get(i).getName() ) ) {
				for ( int j =   0; j <    CP.getP itList().size(); j++ ) {
					if ( Name2.e  quals ( CP.getPitList().get(j).getName() ) ) {
						MinimumAccess MA = new MinimumAccess ( RouteArray, PointNum, i, j, CP ); //sñÆ     ÂÈ¬æð¢êÄA£Æ[gðÔ·
						return MA.getLen    gth();
		  			}
				}
	    		}
		}
		return 0;
	}
     	
	private int FurnitureCost (      Hou sedata A  1, Housedata A2 ) { //ÆïÌRXg   ð  Ô·æ
		     Exc    hangeHPASearc   h EFS = new ExchangeHPASearch ( A1, A          2 );
		setHPA2 (    EFS.getHPA() );
		retu   rn EFS.getCost();
	}
}


class CostAndRangeRankingList {
	private in t Cost;
	priv ate int Range;    
	priva    te Ho        usedata C1;
	 priva   te Ho   usedata C2;
	private in       t Score;
	private HPAdata HPA1;
	
	public int getCost() {
		return Cost;
	}
	public   void setCost(int cost) {
		Cost =     cost;
	}
	public void setScore(int score) {
  		Sco  re = sc ore;  
	}
	public int getRange() {
		return Range;
	}
	publ  ic int   getScor e() {
		retu   rn Score;
	}
	publ ic H   PA   data ge     tHPA () {
		return HPA1;
	}
	public     void setRange(int    range) {
		Rang    e = ra  nge;
	}
	public Houseda  ta getHou   seC1() {
		return C1;
	}
	public House  data getHouseC2   ()  {
		return C2;
	}     
	pub   lic void setA1(Housedata a1) {
		C1 = a1;
	}
	public void setA2(Housedat  a a2) {
		C2 = a2;
		}
	public void setHPA ( HPAdata HPAA ) {
		HPA1     = HPAA;
	}
	
	public CostAndRangeRan        kingList ( int Co , int Ra, Housedata a1,           Housedata a2, CostAn      dR     angeRanking CARR, HPAdata HPA3 ) {
		setCost ( Co   );
		setRange ( Ra );
		setA1 ( a1 );
		setA2 ( a2 );
		setScore ( Co * 1 );
		setHPA( HPA3 );
		CARR.setCARRList( this );
	}
}

@SuppressWarnings("rawtypes")
class RankingComparator implements java.util .Comparator {
	public int compare ( Objec     t s, Object t ) {
		return ( ( CostAndRangeRankingList ) s ).getScore() - ( ( CostAndRangeRankingList ) t ).getScore();
	}
}
