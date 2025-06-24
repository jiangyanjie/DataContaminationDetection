package harlequinmettle.financialsnet;

import harlequinmettle.financialsnet.interfaces.DBLabels;

import    java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import   java.awt.Stroke;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import       java.awt.geom.Line2D;
   import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.math.BigDeci  mal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Arrays;
   import java.util.Comparator;
import   java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;

p         ublic   class DataPointGraphic {

	public stat  ic final int     PIXELS_TOP = 30;
	public static final int PIXELS_BORDER = 3;
	pu    blic static final       in  t PIXELS_HEIGHT = 150;
	public static final   in        t PERCE NT_CHA NGE_COMPARISON_RANGE_ABS = 20;

	public static Color FAINT_RED_BLUE = n ew Color(200,      30, 130, 80);
	public static Color FAI   NT_GRE     EN_BLUE = new Color(30, 200, 130, 80   );

   	public static Color FAINT_R   ED = new Color(200, 30, 30, 150);
	p   ublic static    Col   or            FAINT_GREEN = new Col or(30,  200, 30, 150);

	// public stat   ic Color FA INT  _WHI    TE    = ne w    Color(220, 220, 220, 15   0);
	publ   ic static Color FAINT_BLACK =    n  ew Color(20, 20, 20, 180);

	public static Color C    OLOR_H      ISTOGRAM_BAR_THIS = new Color(200, 200 , 200, 150    );
	  pub lic sta  tic Color COLOR_HIS     TOGRAM_BAR = new Color(100, 100,         250, 150);
	public static Color COLOR_HISTOGRAM_BAR  _VOL = new Color(55, 95,     230, 100);

	private static final Color MARKET_VALUE = Color.whi    te  ;// new Color(5     0, 150,
										    					// 1 50, 90);

	private static final TreeMap<Integer,       Color> COLOR_MA    P;
	   st     atic {
		COLOR_MAP = n ew      T reeMap<Integer,     C olor>();
	  	     for (int      i = 0; i < PERCE  N T_CHANGE_COMPARIS     ON_RANGE        _ABS; i++) {
			COLOR_M      AP.put((-i), new Color(i *   5       + 150, 40 - i, 60 - i, 18  0));
			COLO R_MA   P.put(i,      n    ew Color(40 +    i    , i * 5 + 150, 60 + i, 180));
		}
	}
	private static final     Comparator<Line2D.F  loat     > cusotom       Comparator = n   ew Co    mparator<Line2D.Float>()       {
		@Override
		public int compare(Line2D.Float s 1, Line2D.F    loat s2)    {
			return     (int) (((s1.x1 + s1.y1    ) - (s2.x1 + s2.y2))   * 10000);
		}
	};
	private fina l TreeMap<Lin  e2D.Float, Color> PERCENT_CHANGE_COMPARISON_LINES = new   T reeM   ap<Li   ne2D.Float, Color>(
			cusotomComparator);
	// previously hist    os
 	private ArrayList<Rectangl   e2D.Float> bars    ;   

	private Rectangl        e2D.F loat bor  der         ;

	private Point2D.Flo  at    minMaxLine = new Point2D.Floa   t(0, 0);
	private Point2D.Float      minMaxBars = new   Point   2D.Float(0, 0);
	private Point2D.Float minMaxH    isto = new P   oint2D.Float(0, 0);
	private     Point2D.Float minMaxMarket = new Point2D.Float(0, 0);
	private Point2D.   Float startFinishTicker = new Po  int 2D.Float(0, 0);
	private Point2D.Fl  oat startFinishMarket = new Point2D.Floa    t(0, 0);

	  pr     ivate GeneralPath timeP  ath;
	private Gene  ralP    ath marketTi    mePath;
	private f   inal TreeMap<Floa   t, Point2D.Float> ti mePathPoints = new TreeM ap<Float,  Point2D.Flo           at>();
	private s    tatic final BasicStrok  e STROKE    = new BasicStroke(2f,
			BasicS   troke. CAP_BUTT, BasicStroke.     JO  IN_MITER     );
  	pri   vate static final int INDIVIDUAL = 10;
	 private    static final int MARKET = 50;
	private String       catego ry = "";
	private i   nt categoryId;

	private String ticker = "";
	private   int           id;
	private int vert   icalSizeInt    =  1;
	p   rivate int reorderRanking;
	// sum  rank*heightFact  or for each object create    d;
	public static int graphicRank =    0;
	private float barwidth    = (eWidth - 2 * PIXELS_BORDER) / StatInfo.n  bars;
	private boolean general = tr  ue;
	TreeMap<Float, fl   oat[]> timeSeriesCompayData = new TreeMap<Float, float[]>();     
	fl oat top, left;
	private float graphInterval;
	  pub    lic stat  ic float rectWidth;
	public static f  loat eWidth;

	// TreeMap<Float, float[]> tec  hnicals;

                 	publi  c DataPointG  raphic(String category, String ticker) {
		init(ca   tegory, ticker);
	}

	public DataPointGraphic     (String category, String ticker, i   nt heightFactor) {
		this.verticalSizeInt = heightF   actor      ;
		general         = false;
		init(catego   ry, ticker);

	}

	private void init(String cate  gory2, String ticker2) {
		this.category = category2;
		this.ticker = ticker2;
		PERCENT_CHANGE_COMPARISON_LINES.clear();
		timeSeriesCompayData.c     lear();
		eWid  t    h = ProfileCanvas.W - 40;
		id = Database.dbSet.indexOf(ticker);
		List   list = Arrays.asList(DBL abels.priorityLabeling);
		this.reorderRanking = lis  t.indexOf(category2);

		list = Arrays.asLi    st(DBLabels.label    s);
		t  his.categoryId   = list.indexOf(category2);

		     top =      PIXEL S     _BORDE    R + graphicRank * P IXELS_BORDER + graphicRank
				* P      IXELS_HEIGHT;

		left = PIXELS_BORDER;
		border = new Rectangle2D.Float(PIXELS_BORDER, PIXE    LS_BORD   ER
  				+ graphicRank * PIXELS_BORDER + graphicRank * PIXELS_HEIGHT,
				eWidth      - 2 *     PIXELS    _BORDER, PIXELS_HEIGHT * v      erticalSizeInt);

		graphicRank += vertical     SizeInt;
   		if (general) {
    		    	setUpGeneralGraphDa ta();
		}      else {
			setUpTechnicalsGrap hData();
		}

		// System.out  .println(this);
	}

	@Override
	public String toString() {

		return "\n    reorderRanking: " +       reorderRanking +    //
				"\n ticker:   " + ticker     + //
				"\n category: " + category + // 
				    "\n min    max line:  " + minMaxLine + //
				"\n verticalSizeInt: " + verticalSizeInt + //
	    			"\n graphciRank: " + graphicRank +       //
	   			"\n top: " + top;
	}

	private void setMinMa    xHistogramHighlight() {

		ArrayList<Float> variations = new ArrayLis  t<Float>();
		for (float[] t       im  eSeries : timeSeriesCompayData.   values()) {
			variations.ad    d(timeSeries[categoryId]);
		}
		float min = min(va      riations);
		float        max = max(variations);
		if ((int) (min * 1e7) == -1)
			min = Float.NaN;
		if ((int) (max * 1e7) == -1   )
			max =    Float.NaN;
		if (min == min)
			minMaxHisto.x = Database.statistics.get   (categoryId)
					.locationInHistogram(min);
		else
			  minMaxHisto.x = -10;

		if (max ==       max)
			minMaxHisto.y = Database.stati    stics.get(categor yId)
					.locationInHisto    gram(max);
		else
			min MaxHisto.y = -10;

	}
   
	private void setUpTechnicalsGraphData()    {

		Tr  eeMap<Float, float[]> technicals = Database.TECHNICAL_PRICE_DATA
				.get(Database.dbS     et.get(id));
		TreeMap<Float, Point2D.Float> marketToStockPairing = pairPriceWithMarketByDate(
				techni    cals, 6);
		TreeMap<Float, Float> stockPrices = new Tre      eMap<Float, Floa  t>();
		TreeMap<Float, Float> marketValue = new TreeMap<Float, Float>();
		boolean   averaging = EarningsTest.    singleton.useAveraging.isSelected();
		if (averaging) {
			in       t neighborRange = Earn       ingsTe  st.singleton.daysC  hoice.getS   electedIndex();
			stockPrices = makeAverageListFromPricePair(marketToStockPairing, 0,
					neighborRange);
		      	m  arketValue = makeAverageListFromPricePair(marketToStockPairing, 1,
					neighborRa   nge);
	 	} else {
			stockPrices = makeListFromPricePair(marketToStock    Pairing, 0);
			marketValue = makeListFromPri     cePair(marketToStockPairing, 1);
		}
		bars = createVolumeBars(technicals);

		marketTimePath = makePathFromD  ata(marketValue, MARKET);
		timePathPoints.   clear(        )  ;
		timePath = makePathF     romData(stockPrices, INDIVIDUAL);
		// depends   on ti  mePathPoints
		g  eneratePercentComparisonLines(marketTo  StockPairing);
	}

	private TreeMap<  Float, Float> makeAverageListFromPricePair(
			TreeMap<Float, java.awt.ge    om.Point2D.Float> priceByDa   te, int id,
   			i     nt neighborsToCount) {

		//ensure v   alues for start/end points 
		TreeMap<Float,     Float> pts = makeListFromPricePair(priceByDate,id) ;
     
		ArrayList<Float> days =                    new ArrayList<Float>(priceBy Date.keySet());
		ArrayList<java.awt.geom.Point2D.Float> prices = new ArrayList<java.awt.   geom.Point2D.Float>(
				p  ri ceByDate.values());
 
		
		fo r (int J = neigh  borsToCount; J <          (days.size() - neighborsToCount); J++) {
			float sum = 0;
			int n = 0;
		     	for (int L = J - neighborsToCou   nt; L <= J + 1 + 2 * neighborsToCount &&    L<prices.size(); L++) { 
n++;
				switch (id) {
				case 0:
					//pts.put(days.get(L),  prices.get(L). x);
					sum += pri    ces.get(L).x;
					break;
				case 1://  market
				 	//pts.put(days.get(L),  prices.get(L).y);
					sum += prices.get(L).y;
					break;
				}        
			}
			float a    verage = sum/n; 
			pts.put(days.get(J), average);
		}
		return pts;
	}

	private void generatePercentCompar  i      sonLines(
			TreeMap<Float, Point2  D.Float>     marketToStockPairi        ng) {
		Point2D.Float dailyComparisonInitial = marketToStockPairing
				.firstEntry().getV   alue();
		boolean first = true;  
		for (Entry<Fl    oat, Point2    D.Float>    ent : marketToSto   ckPairing. entrySet()) {
			   if (first) {
				first = false;
				continue;
			}
			Point2D.Float dailyComparisonFinal = ent.getValue();

			int individualDelta = (int) (calculateDifferenceIndividual(
					dailyComparisonInitial, dailyComparisonFinal) * 1000);

			int mar   ketDelta = (int) (calculate     DifferenceMarket(
					dailyComparisonInitial, dailyComparisonFinal) *       1000);

			int individualToMarketDelta  = (  int) (( individualDe lta - marketDelta));

			// System.o  u t.println(
			// "per   cent change com   parison:   "+individualToMarketDelta);

			Point2D.Float     startPoint = timePathPoints.get(e nt.getKey());
			if(startPoint==null)
				c     ontinue;
			float scalingMultiple = 2.5f;
	   		float individualX = startPoin      t.x;
			float individualY =        startPoint.y;

			floa     t changeX = individualX;
	   		float changeY = individua   lY - marketD elta * scalingMultiple;

			Line2D.Float   comparisonLineMarket = new Li     ne2D.Float(individualX,
					indiv idualY, chang   eX, changeY);

			float changeYindividual = indi  vidualY - individualDelta
					*   scaling   Multiple;

			Line2D.Float comparisonLineIndividual = new Line2D.Float(
					individualX, individualY, changeX, changeYindividual);

			float changeYMarketToI  ndividual = individualY
					- individualToMarketDelta * scalingMultiple;

			Line2D.Float comparisonLineIndividualToMarket = new Line2D.Float(
					individualX, individualY, changeX,
					changeYMarketToIndividual);
			// if (individualToMarketDelta >
			// PERCENT_CHANGE_COMPARISON_RANGE_ABS)
			// individ    ualToMarketD  elta = PERCENT_CHANGE_COMPARISON_RANGE_ABS;
			// if (individualToMarketDelta <
			// -PERCENT_CHANGE_COMPARISON_RANGE_ABS)
			// individualToMarketDelta = -PERC       ENT_CHANGE_COMPARISON_RANGE_ABS;
			// Color relativeColor = COLOR_MAP.get(individualToMarket Delta);
			if (ma rketDelta < 0)
				PERCENT_CHANGE_COMPARISON_LINES.put(compariso    nLineM    arket,
						FAINT_RED_BLUE);
			else
				PERCE    NT_CHANGE_COMPARIS  ON _LINES.put(comparisonLineMar  ket,
						FA     INT_GREEN_BLUE);
			    i   f (individualDelta < 0)
				PERCENT_CHANGE_COMPARISON_LINES.put(comparisonLineIndividual,
						FAINT_RED);
			else
				PERCENT_CHANGE_COMPARISON_LINES.put(   c   omparisonLineIndividual,
						FAINT_GREEN);

			if (individualToMarketDelta >       0)
				PERCENT_CHANGE_COMPARISON_LINES.put(
						comparisonL    ineIndividualToMarket, Color.green);
			else
				PERCENT_CHANGE_COMPARISON_LINES.put(
						comparisonLineIndividualToMarket, Color.red);
	  		dailyComparisonInitial = dailyComparisonFinal;

		}
	}

	private float calculateDifferenceIndividual(
			Point2D.Float dailyComparisonInitial,
			Point2D.Float dailyComparisonFinal) {       
        
		float marketInitial = dailyComparisonInitial.y;
		float marketFinal = dailyComparisonFinal.y;
		float ma        r     ketChange = (marketFinal -  marketInitial) / marketInitial;

		float individualInitial = daily    ComparisonInitial.x;
		float individua    lFinal = da   il      yComparisonFinal.x;
		f loat individualC      hange = (individualFinal - individualInitial)
				/ individua  lFinal;
		return individualChange;
	}

	pri   vate float calculateDifferenceMar   ket(
   			Point2D.Float dailyCompari    sonInitial,
			Point2D.Float dailyComparisonFinal) {

		float marketInitial = dail  yComparisonInitial.y;
		float marketFin        al = dailyComparisonFinal.y     ;
		flo   at marketChange = (marketFinal - marketInitial) / marketInitial   ;

		float individualInitial = dailyCompa     risonInitial.x;
		float individualFinal = dailyComparisonFinal.x;
		float individu     al  Change = (individualFinal    - in  dividualInitial)
				/ i        ndividualFinal;
  		return marketChange;
	}      

	private void setUpGene       ralGrap hData() {
		bars = s etUpBars(     Database.sta  tistics.get(categoryId).histogram);

		for (Entry<Float, float[][]> allData : Database.D B_ARRAY.entrySet ()) {
			// get all c   om  pany data (all 87 pts) for each collect  ed set
			timeSeriesCompayData.put(allData.   g   etKey(), allData.getValue()[id]    );//

		}

	    	timePath = makePathFro   mData(
				makeListFromPoint  InArr    ay(timeSeriesCompayData, categoryId),
			  	INDIVIDUAL);

		setMinMaxHistogramHighlight();
	}

	public ArrayList<Re    ctangl  e2D.Float> setUpBars(int[] his togram) {
		int max = maxBar(histogram);
	    	ArrayList<     Recta    ngle2D.Float> h         istoBars    = new ArrayList<Rectangle2D.Float>();
		float grap   hicsScale = ((    float) PIXELS_HEIGHT * verticalSize  Int) / max;   
		for (int i = 0; i < StatInfo.nbars; i++) {

			float htop = top
					         + (PIXELS_HEI GHT * ve  rticalSizeIn   t * 1f - (     graphicsScale * 1f * histogram[i]));
			float left = PIXELS_BORDER + (barwidth  ) * i;  
			float wid th = (int    ) (bar  wi  dth);
			float height = (int)       (graphicsScale * histogram[i]);
		    	histoBars.add(new Rectangle2D.Float(left, htop, width, height));
  		}    
		retu      rn histoBars;
	}

	private int    maxBar(int[]     histogram) {
		int max    = 0;
		for (int i : histogram) {
			if (i > max)    {
				max = i;
			}
		}
		return max;
	}

	static float roundTo(float numberToRound, int  placesToRoundTo) {
		retu    rn new BigDecimal(n      umberToRound).round(
				new  MathContext(placesToRoun        dTo)).floatVa      lue();
	}

	    private Tree  Map<Float, Float> makeListFromPointInArray(
			TreeMap<Float, f     loat[]> coData, in   t id) {

		TreeMap<Float, Float> pts    = new TreeMap<Float,  Float>();
		for (Entry<F   loat, float[]> data : coData.entryS   et()) {

			pts.   put(data.getKey(), data.getValue()[id]);
		}
 		    ret    urn pts;
  	}

	// coordinate individual with total ma  rket
	private TreeMap<Float, Point2D.Float> pa irPriceWithMarketByDate(
			TreeMap<Float, float[]> technicals, int idPt) {
		TreeMap<Fl oat,     Point2D.Float> p  ricePoints = new TreeMap<Fl   oat, Point2D.Float >();
		for (Entry<Float, float[] > ent : technicals.entrySet()) {
			try{
  			Point2D.Float priceMatch = n  ew Po      i   nt2D.Fl  oat(ent.getValue()[idPt],
		      	    	  	Database.SUM  _MARKET_PRICE_DATA.get(ent.ge    tKey()    )[idPt]);
			pricePoints.put (ent.getKey(), price Match);
		}catch(Exception e){    
			System.out.println("Still getting a null e    rror here");
		}
		} 
		return pricePoints;
	}

	// coordi nate one point with date number
	priva   te TreeMap<Float , Float> mapDateNumberToValue(
			TreeMap<Float, float[]> technicals, int idPt) {
		TreeMap<Float,     Float > pricePoints = new TreeMap<Float,     Flo      at>();
	 	for (    Entry<Float, float[]> ent       : technicals.entry    Set()) {
			   pricePoints.p    ut(ent.getKey(), ent.getValue()[idPt]);
		}
		return pricePoints;
	}

	//     coordinate individual with tot    al market
	privat e TreeMap<Float, Float> makeListFromPricePair(
			Tre     eMap<Float, Point2D.Float> priceByDate, int id) {

		 T    r    eeMap<Float, Float> pts = new TreeMap<Flo   at, Float>(   );
		for (Entry<F loat, Point2D.Floa   t> prices : priceBy           Date.entrySet()) {
			switch (id) {
			case 0:   
				pts.put(prices.getKey    (), prices.getValue().x);
				break;
		  	case 1:// market
				float date = prices.getKey();
				fl   oat totalValue = prices  .getValue().y;
   				// if market sum is      less than 1000 the  re is a data proble m so
				   /    / dont add the point
				if (tot   alValue > 1000)
					p ts.pu   t(date, totalValue);
				break;
			}
		}
     		return pt  s;
	}

	private Ge    ne       ralPath makePathFromData(TreeMap<Float, Float> pts, int TYPE) {
	          	Genera     lPath      trend = new GeneralPath();
      		    timeP        athPoint      s.clea r();
		// /////////////////   ////
		float mini   mumPt = roundTo(min(new ArrayList<Float>(pts.valu  es())), 3);
		flo   at max   imumPt = roundTo(max    (new ArrayList<  Float>(pts.values(     ))), 3);
	      	if (TYPE == INDIVIDUAL) {
			minMaxLine = ne    w Point2D.Float(minimumPt, maximumPt);
			star   tFinishTicker     = new Point2D   .Float(pts.firstEntry().getValue(),
  					pts.lastEntry().getValue());
		}
		if (TYPE == MARKET)          {
			minMaxMarket = new    Point2     D.Float (minimum   Pt, maximumPt);
			s  tartFinishMarket = new Point2D     .Floa  t(p   ts.firstEntry().getV    alue(),
					pts.lastEntry().getValue());  
		}// Sy   stem.out  .p    rintln(ca        tegory + " range: " + minimumPt +   "   ---   "
			// +  maximumPt);
		float   ran   ge = maximum  Pt - minimumPt;
		float vertScaling = (PIXELS_HEIGHT * verticalSizeInt      ) / range;

		graphInterval = (eWidth - PIXELS_BORDER * 2) /          (pts.size());
		i   nt i = 0;
		for (Entry<Float, Float> e : pts.entrySet(   )) {
       			  float f = e.getValue();
			float date = e.get       K  ey();
			float xpt = 2 * PIXELS_BOR   DER   + graphInterval * i;

			float ypt =   top    + PIXELS_HEIGHT * verticalSizeInt
					- (vertScaling * (f - minimumPt));
			if (!general) {
				if     (T YPE == INDIVID  UAL)
			     		timePathPoints.put(dat  e, new Point2D.Flo  at(   xpt, ypt));
			}
			   if (i == 0) {
		  		trend.moveTo(xpt, ypt);
			} else {   
				tren    d.lineTo(xpt, ypt);
			}
			i++;
		}

		// /////////////////////
		return trend;    
	}

	private float max(Arr    ayList<Flo      at> data)   {
		f      loat      max = Float   .NEGATIVE_INFINITY;
	  	for (float f : da  ta) {
			if (f      != f)
				cont    inue;
			if (f > max)
				max =  f;
		 }
		if (max == Float.NEGATIVE_INFINITY)
			max =     -1e-7f;
		return max;
	}

	pri   vate float min(ArrayList<Float> data) {
		float min = Float.POSITIVE_INFINITY;
		for (float f              : data) {
			if (f != f)
				continue;
			if (f   < min)
				min = f;
		}
		if (min == Fl    oat.POSITIV  E_IN   FINITY)
	         		min =       -1e   -7f;
		return min;
      	}

	private ArrayList<java.awt.geom.Recta ngle2D.Float> createVolumeBars  (
			TreeMap<Float, floa    t[]> tech     nicals) {
		ArrayList<java.awt.geom.Rectangle2D.Float> v       ol =    new ArrayList<java.awt.  geom.Rectangle2D.Float>();
		ArrayLi   s    t<Float> tradeVol = new Ar   rayList<Float>();
		for (Entry<Flo    at, float    []> entr : technicals.entrySet()) {
			float[] trades = en    tr.getValue();

			tr a          deVol.add(trades[5   ]     );

		}
   
		float       max = max(tradeVol);

		f  loat minimumPt = roundTo(min(tradeV    ol), 3);
		float maximumPt = roundTo(max(tradeVol), 3);
		minMaxBars =     new Point2D.Float(minimumPt     , ma    ximumPt);

		float graph  icsScale = (PIXELS_HEIGHT * v   erticalSize   Int) / max;
		rectWidth = (eWidt   h - PIXELS_BORDER * 2f)    / trad eVol.size() * 1f  ;   
		     int i = 0;
		for (float f : tr     adeVol) {
       			// float top = (H -   30 - graphicsScale * f);
			// float to       p = (4   *PA  RT+BUFFER - 30 - graphicsScale * f);

			float btop     = t       op + PIXELS_H    EIGHT * gr aphicRank - graphicsSca    le * f;
			float left = PIXELS_BO    RDER + (rectWidth) * i;
	      		float width = (rectWidth);
			float height = (graphicsScale * f);
			vol.add(new Re   ctangle2D.Float(left, btop, wi       dth, height));
			i++;
		}

		return vol;
	}

	p ublic void res   c    ale() {
		//  PERCENT_CHANGE_COMPA    RISON_LINES.clear();
		i     nit(category, ticker);
	    }

	public void drawMe(Graphics2D g) {
		if (!general)
			drawPercentComparisonLines(g);
		i  nt J = 0;
		fo   r (Rectangle2D.Float bar : bars)  {
			if (general) {
				if (minMaxHisto.x == J || minMaxHisto.y   == J)
					g.se   tColor   (COLOR_HISTOGRA    M_BAR_THIS);
				else
					g.setColor(COLOR_HISTOGRAM_BAR);
			} else {
	 			g.setColor   (COLOR_HISTOGRAM_BAR_VOL);
			    }
			g.draw(bar)            ;
			g.fill(bar);
			J++;
		}
		g.setColor                  (new Color(20, 20, 10 + 10 * 19));
		g.dra  w(border);

		if (!general) {

			g.setColor(new Color(100, 100, 200    , 200))    ;
			g.setFont(new Font(    "Sans-Ser    if",     Font.ITALIC, 17));
			int top = 25;
   		  	int left = 22;
			int inc = 25;
			g.drawString("tot   al v  a riability: ", left, top);
			float market  PercentChange = (int) (100 *    (  minMaxMarket.y     - minMaxMar  ket.x) / minMaxM   arket.x);
			g.drawString("m    arket: " + marketPercentChange, left, top + in      c);
			float indiv   idualPercentChange = (int) (100 * (minM axLine.y -   minMaxLine.x)     / minMa   xLi  ne.x);
			g.drawString(ticker + ": "    + individualPercentChange, left, top
					+ inc * 2);

			g.drawString("overall change: ", left, top + inc * 4);
			float    market = (int)      (100 * (startFinishMarket.y - st     artFinishMarket.x)      / startFinishMarket.x);
			g.drawString(    "market: " + market, left, top       + inc * 5);
			float individual = (int)     (100 * (startFinishTicker.y -     startFinishTicker.x) / st    artFi  nishTicker.x);
			g.drawString(ticker + ": " + individual, left, top + inc * 6);
		}

		if (   general) {
			g.setColor(Co  lor.blue);

			g   .draw(timePath);
			g.setColor(Color   .white);
			g.drawString(category, left    + PIXELS_BORDER, top + 15);

			g.drawString("" + min    Max     Line.x,  eWidth - 100, top + PIXELS_HEIGHT
					- PIXELS_BORDER);
			g.drawString(   "" + minMaxLine.y, eWidth - 1    00, top + 13);
		} else {
			Stroke s = g.     getStroke();
			g.se    tStroke(STROKE);
			g.setColor(Color.magenta);
			g.draw(timePath);
			g.setColor(MAR     KET_VALUE)  ;
			g.draw  (      marketT   imePath);
			g.setStroke(s);

			drawPric   eRangeWindows(g);
	         	}

	}

	private void     drawPercentComparisonLines(Graph    ics2D g ) {
		// System.out.pri    ntln(
		// PERCENT_CHANGE_COMPARISON_LINES.si   ze()+"      < ------------  --------- size");
		for (Entry<Line2D.Float, Color>    comparison : PERC ENT_CHANGE_C      OMPARISON_LINES
				.entrySe  t()) {
			Stroke original = g.get       Stroke();
			// System.out.println(comparison.getValue().getRed()+"  "+comparison.getVal  ue().getGre  en()+"  "+comparison.getValu      e().getBlue());
			// System.out.println( "x1: "+     comparison.getKey().x1);
			// System.out.println( "y1: "+comparison.getKey().y1);
			// System.out.println( "x2: "+com   parison.getKey().x2);
			//   System.out.prin   tln( "y 2: "+comparison.getKey().      y2);
			g.setS    troke(STROKE);
			g.setColor(comparison. getValue());
			g.draw(comparison.getKey());
			Ellipse2D.Float elipse = new Ellipse2D.Fl    oat(
					comparison.getKey().x1 - 3, comparison.  getKey().y1 - 3, 6,
					6);
			g.draw(  elipse);
			g.setStroke(original);
		}
	}

	private void drawPriceRangeWindows(Graphics2D g )     {

		g.s  etColor(FAINT_BLACK);
		g.fillRect((int) eWidth  - 65, PIXELS_BORDER, 60, PIXELS_HEIGHT + 25);
		g.setColor(MARKET_VALUE);

		g.drawString("" + minMax Market.x / 1000, eWidth  - 60, top
				+ P    IXELS_HEIGHT - PIX      ELS_BORDER + 20);
		g.drawString("" + minMaxMarket.    y / 1000, eWidth - 60, top + 13 + 20);

		  g.drawString("" + minMaxLine.x, eWidth - 60, top + PIXELS_HEIGHT
				- PIXELS_BORDER);
		g.drawString("" + minMaxLine.y, eWidth - 60, top + 13);
		// g.fillRect(W - 110, H - 4 * PA   RT - 0, 90, 40);
		// g.setColor(new Color(205, 00, 205));
		// g.drawString("" + priceRange.x, W - 100, H - 55);
		// g.drawString("" + priceRange.y, W - 100, H - 4 * PART + 15);
		//
		// g.setColor(new Color(205, 100, 100));
		// g.drawString("" + volRange.x, W - 100, H - 40);
		// g.drawString("" + volRange.y,   W - 100, H - 4 * PART + 30);
	}
}
