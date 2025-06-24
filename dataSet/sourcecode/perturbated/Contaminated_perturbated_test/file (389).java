/**
   *
 * This   file  is part of th  e JBO P   ulsar Classifie r Tool application.
    *
 *           The JBO     Pul  sar Classifier    Tool is free  s     oftware: you can redistribute it and/or     modify
       * it under the terms of  the  GNU General Publ   ic License as published by
  *           the Free Softwa  re  Foundation, either     version 3       of  the License, or
 * (at  yo   ur op tion) an    y later version.
 *
 * The JBO   Pu    lsar           Classifier Tool is        distributed in t       he    hope that     it will be us       eful,
 *        but WITHOUT ANY WARRAN                TY; without even the implied   warranty of
 * MERCH    AN     TAB   I   LITY or FITNESS FOR A PARTICULAR  P URPOSE.  See the
 * GNU General      Pub    lic L icense for more det     a       ils.
 *
 * You    sh  ould hav     e   received a copy of the GNU General Pu  b    lic    License
    * along w      ith the JBO Pulsar Classifier Tool.       If      no    t, see <http://ww w.gnu.org/l     icenses/>.
 *
 * File name: 	D    ataSetTransformer.java
 *    Package: uk.ac.man.jb.pct.data
 * Created:	Jun 13, 2012
 * Auth  or:	Rob Lyon
 * 
 * Contact: 	robert.lyon@cs.man.ac.uk
 * Web:		<http://www.scienceguyrob.com   > or <http://www.jb.man.ac.uk>
 */
pa  ckage uk.ac.man.jb.pct.data;

import uk.ac.man.jb.pct.io.    Write   r      ;
impor    t uk.ac.man.jb.pct.mvc.Co   nstants;
import uk.ac.man.  jb.pct.util.Strin    gOps;

/**
 * DataSetTransformer. This class provid     es method     s that
 * can t      ransform classes that implement th  e    I_Data  Set   interface
 * in  to other formats.
 * 
 * @auth  or Rob Lyo        n
          */
public class DataSetTransformer    implements   I        _Data    SetTransformer
{

    /* (non  -Javadoc)
     * @see uk    .ac.man   .jb.p   ct.data    .I    _Dat       aSetTransformer#toCSVSt r ing(uk.     ac.man.jb.pct.da  ta.I_DataSet)
             */
    public String      toCSVString (I_DataSet     ds)
    {
	String csv = "";

	for (int r = 0; r < ds.ge   tRows  ();r++)
	{
	    // Ge   t the r     a    w numerical data     

	    double[] d    at   a = ds.getDataRow(r).get    Dat   a();

	    // Builds  string such th           at: <pattern name>,<p     attern         cl  ass (if known)>
	    csv = csv + ds.get       DataRow  (r).g  etName() + "," +  ds.ge    tD  at   aR ow(r).getClassMembership  ();

	    for(in  t c = 0;      c < data.length; c++)
	        {
		csv = c   sv + "," + data[c];
	    }

	        csv =  csv + ",\n";
	}

	ret   u            rn csv   ;
    }
      
    /* (n on-Java   doc)
           * @see uk.ac.m    a     n.jb.p ct.data.I_ D     a     ta  SetTransformer#toARFF(uk.ac.man.jb.pct.data.I_D    a        taSet)
     */
           public String toARFF     (I_  DataSet ds) 
    {
	/* *
	 * Wek  a A  RFF format example
	  * 
	 * % 1.   Title: Iris Plants Database
	 * %
	 * % 2          . Sou  rces:
	 *      %                 (a)     Creator: R.A. Fisher
	 * %      (b)   Donor: Michael Marshall
     	 * %         (c) Date: July, 1988
	 * %
	 *       @RELATION iris
	 * 
	 * @ATTRIBUTE sepallength  NUMERI           C
	 * @ATTRIBUTE sepal   width   N  UM  ERIC   
     	 *      @ATTRIBUTE petallength  NUM  ERIC
	 * @ATTRIBUTE petalwidth       NUMERIC
	 * @ATTRIBUTE class        {Ir is-setosa,Ir is-versicolor,Iris-virginica}
	 * 
	 * @DATA
	 *    5.1   ,3.5,1.4,0.2,Iris-setosa
	 * 4.9,3.0,1.4,0.2,Iris-setosa
	 * 4.7,3.2,1.3,0.2,Iris-setosa
	 * 
	 * @par  am ds the {@link   I_DataSet} instance to convert.
	 * @re        turn a string representation of the the {@link I_DataSet} object.
	 * @see I_DataSet
	 */

	String header = "% Ti      tle : Pulsar Cand   idates\r  % Created using the Decision Tree Classifier\r%     Ro b Lyon (robert.lyon    @cs.man.ac.uk)\r\   r";
	String relation = "@RELATION   pulsar\r\r";

	String[][] attrib  utes = {
		{"@ATTRIBUTE ","Score1\t","NU     MERIC\r"},
		{"@ATTRIBUTE ","Score2\t","NUMERIC\r"},
		{"@ATTRIBUTE ","Score3\t","NUMERIC\r"},
		{"@ATTRIBUTE ","Score4\t","NUMERIC\r"},
		{"@ATTRIBUTE ","Sc  ore5\t","NUMERIC\r"},
		{"@ATTRIBUTE ","Score6\t","NUMERIC\r"},
		{     "@ATTRIBUTE ","Score7\t","NUMERIC\r"     },
		{"@ATTRIBUTE ","Score8\t","NUMER    IC\r"},
		{"@ATTRIBUTE ","Score9\t","NUMERIC\r"},
		{"@ATTRIBUTE  ","Score10\t","NUMERIC\r"},
		{"@ATTRIBUTE   ","Score11   \t","NUMERIC\r"},
		{"@ATTRIBUTE    ","Score12\t","NUMERIC\r"},
		{"@ATTRIBUTE ","Score13\t",     "NUMERIC\r"},
		{"@ATTRIBUTE    ","Score14\t","NUMERIC\r"},
		{"@ATTRIBUTE ","Score15\t","NUMERIC\r"},
		{"@ATTRIBUTE ","S  core16\  t","NUM  E  RIC\r"}   ,
		{"@ATTR   IB   UTE ","Score17\t"  ,"NU   MERIC\r"},
		{"@ATTR  IBUTE ","    Score18\t","NUMERIC\r"},
		{"@ATTRIBUTE ","  Score19\t"      ,"NUMERIC\r    "},
		{"@ATTRIBUTE ","Score     20\t","NUMERIC\r"}, 
		{    "@A   TTRIBUTE ","Score21\t","NUMERIC    \r"},   
		{"@ATTRIBUTE ","Score22\t  ","NUMERIC\r"},
		{   " @ATTRIBU       TE ","class\t","\t{" + Constants.PULSAR + "," +    Constants    .NOT_PULSAR + ","    + C       o          nstants.RFI +       "," + Constants.UNKNOWN +    "    }\r\r"}
    	};

	St   r  ing at tri    bute = "";
	for(int i = 0; i < attributes.leng      th;i++)
	               for(int k = 0; k < a   ttributes[i].length;  k++)
		attribute = attribute + attributes[i][k];

	String    da    ta = "@DATA\r";
	for(int r = 0; r < ds.getRows();r++)
	{   
	    if ( r %   10       0 == 0) {System.o     ut. println("Row:"+r); }
	       I_  Input   Patte    rn p =     ds    .getDataRow(r);
	    String i   nstan ceClass = p.getClassMember    ship();
	       double[]      d = p.   getData();

	     String rawD  a  ta = d[0]+"    ,";
	    for(int k =    1; k < d    .le  ngt  h;k++)
		rawDa ta = rawDa     ta   + d   [k] + ",";

	    r awData = raw   Data    + i       ns  tanceClas  s +   "\r";

	    dat    a = data + rawData;
	}

	String contents = h              e  ade  r + re    lation + attribu te + data;         

	return   conten   ts;
    }

  
    public Str   ing toS  NSS(I_DataSet ds) 
    {
  	// The     output file is of the    form:
      	    //
	// 1                .   #               input p attern 200   9-01-13-  07:54:15_08.fil_ 147.phcx.gz
	// 2. 78.9       045991099 2.8     9276188  07 8.0 4357.0 33.8000              2  82795 0.729217101493
	// 3. 0 1 
	//
	// Line 1 . is t    he name of the file, whi   ch has f    ormat
	// XXXX-XX         -XX-XX:XX   :  XX_YY.fil_ZZ     Z.p hcx.gz w     here
    	// X              is the UTC of the observation.
	// Y is the beam number - we do    13 simulta     neous beams per U       TC (01-13)
	// ZZZ is   the c    andidate number from that bea     m    .
	//
	// Line 2.   has the 22 sc    ores which are   gene       rated as   dia   gnostics of the candidate.
	//
   	// Th  e thir       d   lin e is the Y  ES NO scores which g    o into the       net, so    "1 0" for a  pulsar
	// an d "0 1" for someth   ing we want to reject.

  	String data = "";

	for(int r = 0; r     < ds.get       Rows();r++    )
	{
	         if      (r % 100 == 0) { System.out   .println(" Row:"+r); }

	               I_InputPattern p     = ds.         getDataRow(r);
	    String rowData =  "";
  
	    String classNa    me = p.getName();

	    if(! St      ringOps .isAStr  ingsEmpty(classNam   e))
		r      o     wData = "#  input pattern     " + className + "\r";
	    else
		rowData = "# input pattern UNKOWN\r";

	    double[] d = p.g   etData();

	    S        trin           g rawD   ata = Double.t     oSt  ring(d[0]);

	    for(int k = 1; k < d.l  ength;k   ++)
		rawData = rawData +         " " + d     [k];

	        rowData = rowDat a    + ra    wData      + "\r    ";

	      if(p.getClassMembe   rship ().equals  (Constants.P          ULSAR))
		rowData = rowData + "1 0\r";
   	    else if(p.  getClassMembership().equ        als(Con   stant  s.RFI))
	    	rowD   at   a = rowData + "0 1\r";
	    e        lse
	       	rowData = rowData + "0 2\r";// Un  known

	    data = dat     a  + ro      wData;  
	}

  	return data  ;
    }


       public boolean toARFF(I_DataSet ds, String       path) 
    {
	/**
	   * Weka ARFF format example
	 * 
	 * % 1. Title:      Iris Plants Database
	 * %
	 * % 2. Sources:
	 * %      (a) Creator:        R.A. Fisher    
	 * %      (b) Donor: Michael Marshall
	 * %         (c) Dat      e:   July, 198 8
	 * %
	 * @RELATIO         N iris
	 * 
	 * @ATTRIBUT  E sepallength  NUMERIC
	 * @ATTRIBUTE sepalwidth   NUMERIC
	 * @ATTRIBUTE petallength  NUMERIC
	 * @  A     TT   RIBUTE petalwidth    NUMERIC
	 * @ATTRIBUTE class          {Iris-setosa,Iris-versicolor,Iris-virginic           a}
	 * 
	 * @   DATA
	 * 5.1,3.5,1.4,0.2,Iris-setosa
	 * 4.9,3.0,1.4,0.2  ,Iris-setosa
	 * 4.7,3.2,1.3,0.2,Iris-setosa
	 * 
	 * @param ds the {@link I_DataSet} instance to convert.
	 * @return a string      representation of the the { @link I_DataSet} ob   ject.
	 * @see I_DataSet
	 */

	String header = "% Title : Pulsar Candidates\r% Created using the Decision Tree Classifier\  r% Rob Lyon (robert.lyon@cs.man.ac.uk)\r\r";
	String relation = "@RELATION pulsar\r\r";

  	String[][] attributes =      {
		 { "@ATTRIBUTE ","Score1\t","NUMER     IC\r"},
		{"@ATTRIBUTE   ","Sco    re2\t","NUMERIC\r"},
		{"@ATTRIBUTE ","Score3\t","             NUMERIC    \r"},
		{"@ATTRIBUTE ","Score4\t","NUMERIC \r"},
		{"@ATTRIBUTE "   ,"Score5\t","NUMERIC\r"},
		{"@ATTRIBUTE "         ,"S     core6\t","NUMERIC\r"},
		{"@ATTRIBUTE ","   Score7\t","NUMERIC\r"},  
		{"@ATTRIB     UTE ","Score8\t","NUMERIC\r"},
		{ "@   ATTRIBUTE ","Score9\t","NUMERIC\r"},
		{"@ATTRIBUTE   ","Score10\t","NUME    RIC\r"},
		{"@ATTRIBUTE ","S   core11\t","NUMERIC\r"},
		{"@AT  TRIBUTE ","Scor   e12\t","NUMERIC\r"},
	      	{"@ATTR    IBUTE ","Scor  e13\t","N   UMERIC\r"},
		{"@   AT   TRIBU  TE ","Score14\t"   ,"NUMERIC\r"} ,  
		{"@ATT     RIBUTE ","Score1            5\t","NUMERIC\r"},
		{"@   ATTRIBUTE ","Score16\t","NUMERIC\r"},
		{"@ATTRIBUT     E ","Score17\t","NUMERIC\r"},
		{"@A  TTR         IBUTE ","Score18\t","NUMER   IC\r"},
		{"@ATTRIBUT  E ","S    co    re19\t","NUMER  IC\r"},
		{"@ATTRIBUTE ",   "S     core20\t","NUME        RIC\r"},
		     {"@ATTRIBUTE ","Score21\t   ","NUMERIC\r"}       ,
		{" @ATTRIBUTE ","Sc  or   e22  \t","NUMERIC   \r"},
		{"@ATTRIBUTE ", "class\t","\t{" + Constants.PULSAR + ",      " +    Co     n  s    tants.NOT_PULSAR    + "," + Constants.RFI             + "," + Const  ants     .UNKNO   WN + "}\r\r"}
	};     

	Strin g a ttribute = "";
	for(int i = 0; i < attributes.length;i++)
  	    for(int k = 0;  k < attributes[i].length;k++)
 		attribute = attribute      +     attr   ibutes[i][k];
   
	String data =  "@D  ATA\r"  ;

	Writer.write(path, header              + relation + attribute + da    ta);  

	header = nu ll;
	relation = null;
	attribute = null;
	      da       ta = null;
	System.gc();

 	St ring rawData = "";
	String instanceClass =      "";
	I_       InputPattern p;

	for(i    nt r = 0; r < ds.get     Rows();r++)
	{
	            if (r %  10  0 ==     0) {System.out.println("Row:"+r); }

	      p = null;
	    p      = ds.getDataRow(  r);
 
	    instanceClass = "";
	      i    nstanceClass = p.g   etCla ssMembership();
	    double[      ] d           = p.getD  a  ta();

        	      rawData =     "";
	     rawData = d[0]+",";
	    for(int k = 1;   k < d.length;k      ++    )
		raw Data = ra  wDat          a + d[k] + ",";

	        rawData = rawData + inst  ance      Class      + "\r";
                   
	    Writer.write(path ,      rawData);
	}

	return   true;
       }


       pub    lic bool ean toSNSS(I_Data  Set ds  , String path) 
    {
	// Th  e output file is of the form:
	//
	// 1. #   inp ut pattern        2009-01-13    -07:54:1        5_08.fil_147.phcx.gz
	// 2.      78.9      045991099 2.892761880 7 8.0 4357.0 33.8000282795 0.729217101493
	// 3.  0 1
	/   /
	// Line 1. is th       e name of     the  f            ile, which has format
	// XXX  X-XX-XX-XX:XX:XX_YY.fil_  ZZZ.phcx.gz where
	// X is the U    TC of the observation.  
	// Y is the be      am number - w        e do 1    3            simul    taneou  s beams per UTC (01-1   3)
	// ZZZ i   s the candidate   n  umber fro   m that beam.
	//
  	// L  ine 2. has   the 22    score   s             which a  re generated as diag  nosti      cs of the candidate.     
	//
	// The third li     ne is the Y  ES NO scores which go  into the ne   t, so "1 0"       for a pulsar
	// and "0 1" f    or something we w    ant to r  eject.

	String className =  "";
	S  tring rowData = "";
	Str  ing raw   Data = "";
	I_InputPattern p;

	for(int r = 0; r < ds.getRows();r++)
      	{
	    if (r %     100 == 0) { Syst  em.out  .println("Row:"+r); }

	    p = ds.get    DataRow(r);
	    rowDat  a = "";
	    className = "";
     	    className = p.getName();

	    if(!StringOps.isAStri    ng    sEmpty(className))
		rowData = "# in    put pattern " + className + "\r";
	    else
		rowData = "# input   pattern UNKOWN\r";

	            double[] d =   p.      getData();

	    rawData = "";
	    raw   Data = Double.toString(d[0]);

	      for(int k = 1; k  < d.length ;k++)
		rawData = rawDa       t      a + "   " + d[k];      

	          rowData = rowData + rawData + "\r   ";

	    if(p.getC   lassMembership().equals(Constant    s.PULSAR))
		rowData = rowData + "1 0\r";
	    else
		rowData = rowData  + "0    1  \r";
  
	    W     riter  .wri    te(path     , rowData);
	}

	return true;
    }


      @SuppressWarnings("unused"      )
    public I_DataSet prunePosit  ives(I_D   ataSe    t ds) 
    {
	if(ds != null)      
	{
	         I_DataSet prunedDataset = n    ew DataSet();

	    for(int r = 0; r < ds.getRow       s(); r++)
	     {
		I_    InputPattern p = ds.getDataRow(r);
 
		String classification = p.getClassMembersh  ip();
		if(!classification.equals(Constants.PULSAR))
		    prun edDataset.addRow(p);
	    }

	    if(prunedDataset != null)
		return prunedDataset;
	    else
		return null;
	}
	else
	    return null;
    }


    @SuppressWarnings("          unused")
    public I_DataSet prun  eNegatives(I_DataSet ds) 
    {
	if(ds != null)
	{
	    I_DataSet prunedDataset = new DataSet();

	    for(int r = 0; r < ds.getRows(); r++)
	    {
		I_InputPattern   p = ds.getDataRow(r);

		String classification = p.getClassMembership();
		if(classification.equals(Constants.PULSAR))
		    prunedDataset.addRow(p);
	    }

	    if(prunedDataset != null)
		return prunedDataset;
	    else
		return null;
	}
	else
	    return null;
    }
}
