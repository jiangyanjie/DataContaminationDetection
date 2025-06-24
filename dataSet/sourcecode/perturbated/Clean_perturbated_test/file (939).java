/*          The following code was generate       d     by JFlex 1      .4.3 on 17.09.13 00:09 */

/* CSVLexer.java   is a generated f  ile  .  You prob   ably want to
 * edit CSVLexe    r.le  x to    mak    e change      s.  Use JFlex to generate it.
       * JFlex may b  e obtained  from
 * <a href="http:  //j    fl   ex.de">the JFlex website</a>.
 * This file  was te   sted t o work with   jflex 1.4              (a  n       d   may not
     * work with      more   r    ecent version because    it needs a s  ke leton file)
 * Run:     <br>    
 * jflex   --skel csv.jflex.skel CSVLexer.lex<br>
 * You will then have a file called CSVLexer.java
 */

   /*
 * Copyright (C)   2001-2   010 Stephen   Ostermiller
     * http://ostermiller.org/c    ontac     t.pl   ?r         egarding=      Java+Utilities
 *
 * This program     is free software; you can redistribute it an  d   /or mod       ify    
            * i   t under the terms of the       GNU G  eneral Public License as publishe         d by
      * the F  ree Software Foundatio   n; either version   2 of the Lic       ense,  or
 * (a    t your option)       any later version.
 *
 * T   his program is distrib      u  ted i    n the   ho      pe that i   t will be useful,
 * but WI   THOUT ANY WA    RRANTY; without even the implied war    ranty of
 * MERCH   A   NTABILIT    Y or FITNESS FOR A PARTICULAR PURP    OSE.  See the
 * GNU General Public License for more details  .
 *
 *    See L   ICENSE.txt for details.
   */

pa   c kage c     om.Ostermiller.util;
import     ja       va  .i    o.*;

/**
 * Read files in        comma   s    eparated value      format.
 * More info      rmation about this class is a  vailable from <a     hr         ef=
 *     "http://ost ermille r.or     g/utils/CSV.   html">ost   ermil ler.org</a>.
 *
 * The    use of thi      s class is no lon  ger recommended.	It is now r   ecommen     ded th      at you    use
 * com.Ostermille  r.util.CSV Pa    rser instead.	That class,   h  as a cleaner API,     an  d methods
   * for       returning all the valu      es on a line    in a      Str  ing  array.
 *
 * CSV is a file format used as a p   ortable  represe  nt ation of a da tabase.
  *      Each   line i s         one entry or record and the fiel     ds in a record a        re sep         a rate d by com mas.
 *  Comm   as may be prece  ded or followed by arbitrary space and     /or tab charact    ers which are
 * ignored.  
 * <P>
 * If    field  includes    a comma or a new line     , the whole field must be surrounded with double quotes.
 * When the        field is in quo  tes,  any         quo  te lit eral s must be escaped by   \"            B    ackslash
 * literals must be escaped by     \\.	Ot     herwise a backslash an the character       following it
 * will be t        re      ated as th   e following c h aracter, ie."\n"  i       s   equivel  en  t to "n".  Other escape
 * seque  nces m      ay be set using the     setEs      capes() method.	Text  that comes after quote  s that    h   ave
 *   be en closed    but come before the next com       ma will  be       ignored.
    * <P>
 * Empty fields are returne  d a     s a   s     S   tring of length zero:  "   ".  T  he following line has four empty
 *        fields and two no    n-empty fields in it.      There   is an empty field on each end, and two    i   n the
 * middl     e.<br>
 * <pre>,s     econd,,    ,fifth,</pre>
 *      <P>
 * Blank lines a    re alway    s igno  red.	Othe   r  lines will be         ig  nor   e    d if the y start with a
    * comment charac         ter as set by the setCommentS     tart() method.
 * <P>
 * An    exampl    e of how CVSLexer might be us    ed:
 * <pre>
 *    CSVLexer shredder =    new CSVLex      er(Sy    s  tem. in);
 * shredder.setCommentStart("#;!");
 * shre  dder     .set    Escapes("nrtf", "\n\  r\t\f");
 * String  t;
 * while ((  t = shredde  r.g  etNextToken()) !=     n  u     ll) {
      *    	   Sys   tem.    out.pr    intln("" + shredder.getLineN  umber() + " " + t);
 *        }   
  * < /pre>
 *
 *    @author S    tephen Ostermiller htt  p://oste      rmiller .o     r     g/co   n  tact.p l?regarding=Java+Ut       iliti     es
 * @sin  ce ostermiller  ut     ils 1.00.  00
 */


public class CSVLexe    r            {
/* 
 * Copyright        (C) 2003-2010 Stephen   Osterm       ill         er
 * http://ostermill   er.o  rg/con    tact.pl?rega   rding=Java+Utilities
 *
 * This program is free software; you can   redistri         bute          it  and/or modify
 * it under the     terms of the GNU Ge      neral Pu     blic License as pu    blished by
 * the Free Soft   ware Foundation; either versi  o    n     2 of           the Lic e  nse,        or
 *    (  at your  option)         any later version.
 *
 * This pro   gram is d   istr   ibuted i    n the hope that it will b   e   useful,
 * bu t WI   THOUT AN   Y   WARRANTY; without even the imp       l     ied       war       rant    y of
 *      MERCHANTABILITY   or FI      T        NESS FOR A PARTICULAR       PURPOSE.      See the
 * GNU General P   u    blic Lice        nse f      or more details.
      *    
 * See LICE   N   SE .txt for detai    ls.               
 */

          /** This    char acter denotes the end      of   file */
  public static final i  nt    YYEOF =        -1;

     /** initial size of the lookahead buffer */
  private st  atic final int ZZ    _BUFFERSIZ  E = 16384;   

  /** lexical sta    t   es            */      
  public static final int BEFOR   E      = 2;
        public static fi       nal int YYINIT   IAL = 0;
  public stat  i   c f       i    nal    int CO  MMENT = 6;
  pu   blic static fin a    l      int A   FT    ER         = 4       ;

  /**
       * ZZ_LEX  STATE[l] is the state in the      DFA for the l exical st ate l
     *  ZZ_LEX        STATE[l+1] i     s the        state in   t    he DFA for t    he lexical state l
   *                   at the begi  nning of a    li ne
   * l  is of the   f          orm l = 2*k, k a n  on negative integer
   */
     p   r   ivate     static final in      t ZZ_   LEX  STATE[] =       {      
      0  ,                0         ,  1,  1,  2,  2,    3,     3
  };  
   
  /** 
   * Tra  nsla   tes characters  to     character clas  ses
   */
  private stati   c      final String ZZ_CMAP_PACKED = 
    "\11\0\1\1\1\3\1\0\1\1\1\2\22\0\1  \1\1\0\1\5" +
    "\11\0\1\4\57\0\1\6\uf  fa3\0" ;

         /**            
           * Translat    e      s char   acters     to charact er classes
   */
  priva  te         static final char [] Z        Z_CMAP =             zzUnpackCMap(    Z  Z_CM   A  P_PACKED);

  /** 
   * T ranslates DFA st ates     t   o   actio n switch labels.
          */
  priva   te static   final int [] ZZ_AC    TI   ON   = z         zU       npackAction();
      
  private   static        final String    ZZ_ACTION_PACKED_0 =
    "\1\0\3\1\1\2\1\3\2\4   \1\5\1\6\1\7"+
    "\1\1\2\10\    1\11\1\        12\2\1\1\13\1\1\2\0"    +
    "\1\14\ 2\  0\1\15\1\0";

  private              st atic int [] zzUn    packA    c   tion(   ) {
           int [] result = new int[2   7];
    int   o  ffset = 0;
    offset =  zzUnpa   c   kAction(ZZ_AC      TION_PACKED_0, offset, res         ult);   
     retu rn res     u        lt    ;
  }      

  private static int zzUnpackActi on(String      packed, int offs    et, int [] result)       {
    int i = 0;                 /*                 i   ndex in packed         string  */
    int j = offset;     /* index in un          packed array */
    int l = packed.lengt   h(  );
    while (i < l) {     
               int count = packed.charA  t(i++);
                      int value = p       a   cke     d.charAt(  i++);
      do    r  e  sult[     j     ++] = value; while (--count > 0)   ;
    }
    return j;
  }

  
  /** 
   *  Tr a nslates a state to a row index i        n   the t   ransi    t  ion table 
   */
  priv   ate stat    ic  final int []     Z       Z_ROWMAP = zzUnpackRowMap();
        
  private static final    String ZZ_ROWMAP_PACKED_0 =
    "\0\0\0      \7\  0\  16\0\      25\0         \34\0\4  3\0   \52\0\61"+
    "\0\      61\0     \7  0\0\   77\0\106   \0\115      \0\61\0\61\0\124"+
    "\0\133\0\142\0\61\0\151\0\  34\0\4      3\0    \61\0 \160"+
    "\0\77\0\61\0        \1 67"   ;

      priva  te stat     ic int [   ] zzUn    packRowMap()    {
      i  nt  [] resu   lt = new int [27];
      in     t offse   t = 0;
      of      fse t = zzUnpackRowMap(ZZ_ROWMAP_PA C   KED_0, offset, r     esult ) ;
    return   resu   lt;
  }

  pr  ivat  e s    ta   tic int      zzUnpackRo  wMa p     (String packed, int offse  t, int                    [] resu       lt) {
       int    i =      0  ;  /*    index    i  n packed string  */
           int    j = offset;  /* index in   u   npacked    array */   
          int l = packed.le  ngth();
    while (   i < l) {
            int  h         igh =    packed.charAt(   i++) <<       16;
            result[j++] = high |   p acked  .   charAt   (i++);
    }
    return j;
   }

  /** 
   * The transition table of the DFA
   */
  private s      tatic final i   nt [] ZZ_TRANS = zzUnpackTrans();

  private static      fin    al String ZZ_TR ANS_PACKED_0 =
    "\1\5    \1\6\1\7     \1\  10\1\11\1\12\1\5\1\13 "+
    "\1      \1  4\1\15\1\16\1\17\1\20\1              \13 \1\21\1\22"+
       "\1\7\1\10\     1\23\      2\21\1\24\1\4\1\7  \1\10"+  
    "\3\24 \1\5\1\25\  3\0\2\5    \1\0\1   \26\1\7"+  
    "\1\10   \6\0\1\10\      12\0\5\12\1\27\  1\30\1\13"+
    "\1\31\3\0\2\13    \1\0\1\14\10\0\1\16\3\0"+
    "\5\20\1\32  \1\33\2\21\3\0\    3\21\1\22\1\  7"+
    "\1   \10\1\0\2\21\2     \24\2\0\3\24\7\12\7\20";
         
  private static int   [] zz  Un     packTran  s() {
             int [] result = new int[     12  6];
    int offset = 0;
      offset = zzUnpa ckTran   s(ZZ_TRANS_PA   CKED_0, offset, resu       l t);
    return result;
  }

  p    rivate static    int zzUnpackTrans  (String pac   ke    d   , i   nt offset, i nt [] result) {
        int i = 0;         /* index in p               ac       ked st    ring  */
       in  t      j      = offset;  /* inde        x in unpacked arr  ay             */
             int l = packed.length();
       w h     ile (i < l) {
             int         co unt = pack    ed.charAt   (i++)    ;
           int value = packed.charAt(i++)   ;
          valu e--;
          do result[j++] = value; while (--count > 0);
    }
       ret     urn j;
        }

     /**
   * Per instance refere   nce to the cha   racter map.
   * This can be cloned and modif  ied per inst    ance if needed.  
   *    It is initall           y  set to the static valu           e.
   */
  private char [] zz        cmap_instance    =  ZZ_CMA    P;

  /    *           error c        odes */
  private stat  ic final int ZZ_UNKNOWN_ERR   OR    = 0;
    private         static final int ZZ_   NO    _MATCH =      1;
      p   rivate static final int ZZ_P        USHBACK_    2BIG = 2;

  /* error   messages for    the codes above */
  priva  te st atic     final String   Z       Z_ERR   O         R_MS     G[] = {
    "Unkow    n inte   rnal scanner error",
    "Error:  cou  ld not match input",
    "Error: pus   hb  ack value     was too la      r  g  e   "
  };

            /**
   * ZZ_ATTRIBUTE  [aS    tat    e] conta    ins t   he attributes of      state    <code>a   State</code>
     */
  priva   te          s          tatic           final int [] ZZ_ATTRI BUT          E = zzUn    pa  ckAttribute();

  private static fi      nal S   tring ZZ_ATTRIBUTE_PA CK      ED_0    =
          "\1\0\6\1\2\11     \   4\1\2\11\3\1\1\ 1   1\1\1"+
    "\2\0\1\11\2        \0\1     \11\1\0";
 
  private static in    t [] zzUnpackAtt ribute() {
            int [] result     = new int[2    7];
    in   t offset = 0;
        offse  t = zzUnpack     Attribute(  ZZ_ATTRIBUTE_PACKED_0,  offset, result);
    return result;
  }

  private static int   zzUnpackA   tt    ribute(Stri   ng packed  ,   int offset, int [] r   esult)         {   
    int i = 0;       /* index in pack      ed string  */
    int j     = offset;     /* index in un  pa  cked a  rray       */
    i  nt     l = packed. length();
    while (i < l) {
      int              count = packed.cha rAt(i++  );
        int val   ue = packed.cha        rAt(i++);
      do result[ j++] = va   lue; whil   e (--c    o  unt >    0);
    }
    return      j;
  }

  /**   the i nput device */
  private java.io.Reader zzReader;

     /** the current state of t    he DFA */
  private int       zzState;

  /**      the  current lexical st ate */
  private        int zzLexicalState = YYINITIAL;
    
  /** this buff e   r co      ntains the current text to b e    matc   hed and   is
      the   so    urc  e of the yytext() string */
  private char zz     Buffer [] = new char[ZZ_BUFFERSIZ  E];

  /** the textposition  at the last ac    ce pting       state */
  privat   e   int zzMarkedPos;
   
  /** the textposition   at the last state to b      e included in yytext */
  @Su  pp       r   es    sWa   rnings("unu sed")
pr     ivate  int zzPu  shbackPos;

  /** the curr ent      text position in the buffe      r     */
  private    int zzCurrent     Pos  ;

  /** star   tRead mar  ks th       e be    ginning of the yytext() stri   ng in the      buffer */
  private int zzStartRead;

  /** endRead marks the last character in the buffer,  that has been read
      from input */
     private int      zz    EndRead;

  /** n umber of newlines encount      ered up  to the start of the matched text */
  @SuppressWarnings       ("unused")  
private int yyline;

  /** the     number of characters up to the        st  art    o     f the  matched text */
  @Suppre ssWarnings("unused")   
private    i nt yycha        r;

  /**
   * the number   of        charact   er    s from the last newline up   to the s    tart     of the
   * matc        he      d text
   */
  @Suppres  sWarnings("unused")
private     int yyc   olumn;

  /**
   * zz  AtBOL == true <=> the scanner is currently at the beginning of a line
        */
  @Suppress     W       arnings("u   nused")
pri      vate boolea  n zz   AtBOL =       true;

  /** zzAtEOF == true <=> the   scanner is at the EOF */
  pri    vate boolean zzAtEOF;

  /* user co  de       : */
	/**
	 * Prin ts out tokens and l   ine num     bers from a file  or System.in.
	 * If no arguments are    given, System.in will b  e used for input.
	 * If m            ore arguments are g  iven,     the first argument will b     e u   sed as
	 * the nam   e of the file t     o   use as in       pu     t
   	 *
	 * @param arg  s program arguments, of whi     ch the firs    t is a filename
	 *
	       * @sinc       e ostermillerutil   s 1.    00.00
	 */
    	public static void main(    Strin g[] args){
		InputStream in;
		try {
			if (arg      s.length > 0){
				Fil e       f = new F ile(arg    s[0]);
				if (f.exists()){
					if (f.c anRead()){
						in = new FileIn  putStream(f);
					} else {
		  				th     ro w new IOE   xception("Could          not open " + args[0   ]);
  					}
		 		} el     se {
			  		throw new      IOException("Could not find " + args[0]     );
				}
			} else {
 	  			                 in = System.i         n;
			}
			CSVLexer shre      dder = new CSVLexer(in  );
			shredder.setCommentStar    t("#;!");
			shredder. setEscapes("nrtf", "\   n     \r\t\f")       ;
			     String t;
		  	while ((t = shredder.getNex     tTok     en()) !=       null) {
				Syste  m.out.printl    n("" + shredder   .g    etLineNumber() + " " + t);
			}
		} catch (IOException      e){
  			System.out.println(e.getMessage());
		}
	}

	priv    ate char delim  it e   r = ',';
	private char quote       = '\"';

	/**
	 * Checks t  hat zzcmap_instance is an inst            an ce variable (n   ot just
     	 * a    poi    nter to a s tatic v   ari    able).  If i  t i s a    pointer to a static
	 *   variable, i   t will be cloned.
	 *
	 * @since ostermillerutils 1.00.00     
	 */
	pri  vate void ensur eChar    acterMapIsInstance(){      
		if (ZZ_      CM  AP == z     zcmap_instance)             {
			zzcmap_insta    nce = new char   [ZZ_CMAP.    length];
			System        .arraycopy(ZZ_CMAP, 0, zzcmap_instance, 0, ZZ_CMAP.le  ng    th);
		}
	}

	/**
	 *    Ensu res that     the given character i        s not us      ed for so   me spec  ial p     urpose
	 *    in p   a       rsing.  This method should be called before setting some ch  a ra    cter
	 * to be a   delimiter so that th   e pars  ing d  oesn'       t break.  Exa   mples of bad
	 *     characters are quotes, com    mas, and whi       tespac    e.
	 *
	 * @since ostermillerutils 1.00.00
	 */
	private boolean             c harIsSafe(char c){
		// There are two    character cla        sses that one could use as a delimit    er.
  		// The first would be the class that most characters are in.    These
		// a re no  rmally data.  The second   is the class that the tab is usuall   y in.
		return (zzcmap_instance[c] == ZZ_CMAP['a'] || z    zcmap  _instance[c]      == ZZ_CMAP['\t']);
	}     

	/**
	 * Change the charac         ter c       lasses of t  he two    given cha     racters.  This
	 * will make th    e stat e machine behave as i  f the characters were switched
	 *       when they are e ncounter     ed in the       inp    ut.
 	 *
	 * @param o ld th   e old c   haracter, its    value wi  ll be return  ed to ini      tial        
	 * @param two second     char acter
	 *
	 * @since o    stermillerutils 1.00.00
	 */
	private vo   id upd     ateCharacterClasses(char oldChar,   char newChar){
		// bef   ore     modifying the character map, make sure it i sn't static.   
   		ensureCharacterM  apIsI        nstance();
		//      make   t   he newChar behave like the old Char
    		zzcmap_in  sta   nce[newChar]   =        zzcmap_insta  nce   [oldChar];
		// make th  e o      ldChar behave lik   e it isn't spec    i      al.
		switch        (old  Char){
			case ',':
			case '"': {
				// These should act as normal ch  aracter,
				//       not   delimite     rs or quotes right no            w.
				zzcmap_instance[oldChar] = ZZ _CMAP['a'] ;
			} break;
	  		default: {
				// Se     t the it back to th e way it     would act
				// if not    used as a delimiter or quote.
				zzcmap_instance[oldChar]  = ZZ_CMAP[oldChar];
			} break;
		}
	}

	/**    
	 * Change this Lexer so that it      uses a new        delimiter.
	 * <p>
	 * The      init   ial character  is a comma, the  delimiter cannot be c  hanged
	 * to a quote or other chara       cte r t    hat has spe  cial mea   n    ing     in CSV.     
	 *
	 * @param newDelim del        imiter to which to switch.
	     *    @throws BadDeli   miterException if the   character c     annot be used as a delimiter.
	     *
	    *   @since o  stermillerutils 1.00.00
	 *   /
	public    void changeDel imiter(char newDe lim) throws     BadD   elimiterException {
		if (newDelim == delimite   r) return; // no need to     do   anything.
		if (!  charIsSafe(newDelim)){
	   		 throw new BadDelimiterExcepti  on(new   Delim + " i   s not a safe delim iter.");
		  }
		upda  teCharacterClass  es(de    limiter, newDelim);
		// keep a record of the current delimiter.
		delimiter = newDelim;
	}

	/**
	 * Ch     ange this  Lexer s   o that it uses a    new character   for quoting.
	   * <p>
	 * The initial character is a double quote ("), the delimiter cannot      be changed
	 * to a comma or  other character that has s  p          ecial m eaning      in CSV.
	 *
	 * @param     newQuote character to use for qu oting.
	 * @th  rows BadQuoteExc       eption   if t      he charac    ter cannot be used as a quote.
	 *
	 * @since oster   millerutils 1.00.00
	 */
	public void chan     g  eQuote(char newQuote) throws    BadQuoteException {
		if (newQuote    == quote) r    eturn; // no need to do anyt   hing.
		i       f (!   charIsSafe(newQuote)){
			throw new B  adQuoteException(n     ewQuote + " is not a     s afe quo   te.");
		}
		    updateC      haracterClasse    s(quo      te, newQuote);
		// keep a record   o  f the cur    rent quote.
    		quote = newQuote;
	}

	private S   tring    esca pes = "";    
	private String r  epl     acements =    "";

	/*   *  
	 * Spec    ify escape se   quences and their replacemen  ts.
	 * Escape se  quences se  t here    a   re in addition to    \\ and \"        .
	 * \\ and \" are always valid es      cape       sequences.      This method
	 * allows standard escape sequenced to be used.  For example
	 * "\n" can be set to be a newline rather th   an  an 'n'.
   	 *      A common way    to c       all     this method might be:<br>
	 * <code>setEscapes("nrtf",       "\n\r\t\f");      </code><br>
	 * which would        set the escape sequence   s t   o be the   Java escape
	 * sequences.  C  har    acters that follo w a       \ that     are     not escape
	 * sequences will    still be int         erpret ed a      s that character.     <br>
	 * The tw         o a        rguemnts to this method must    b         e t   he  same length.  If
	          * they are not  , the lo   nger of      the two wil   l be trunc     ated.
	 *
	 * @      param   escapes a list    of c   haracters that will represent escape sequen  ces.
	 * @param replac  em    ent  s the list of repac     e        ment ch  aracters for those escape s    equences.
	 *
	  * @sinc    e                    oster   millerutils 1.00.00
	 */
	publi     c v      oi    d s     etEscapes(String escapes, Strin   g replacements){
		int le     ngth = esc   apes.length();
		   if      (replacements .length() < leng       th)     {
			length =     r  e  p       lacements   .  length();
		 }
		this.    escapes = escapes.substring(      0, l     ength);
     		this.replaceme    nts = rep lacem   ents.substring(0,   len  gth);
	}

	pr     ivate    String unescape(String s){
		i f (s.index     Of('\     \') == -1){
	  		re tur   n   s.subst ring(1 ,   s.len          gth()-1);
		}
		 StringBuffer s b = new Strin       gBu   ffer(s.leng  th    ());
		f   or (          int i  =    1; i<s.length()-1; i  ++){
			char c =         s   .charAt(i);
			    i      f   (c =     = '\\       '){   
  	    			char c1 = s.charAt(++i);
				int index;
			                    	i  f (c1 == '\\    '    || c1       == '  \"'){
      					sb.  append(c1);
		     		} else if ((       i         nd       ex =     escapes.inde          xOf  (c1)) != -1){
     					sb  .a  ppend(replacements.charAt(i  ndex));
		     	    	} else          {
					sb.append(c1  );
				}
			} el   se {
			          	sb   .append(c);     
  			}
	    	}
		return sb.toStrin    g();
	}

   	pri    vate String co  mm   entDel    ims = "";

	      /**
	 * Set the characters that     in    dicate a comment     at the beginning of             the line.
	 * For example if the string "#  ; !     " were passed in, a         l   l of the fo  llow  ing  lines
	 *    would   be comments:<br>
	                     * <pre>    # Comment
	 * ; Another Comment
	 * ! Yet another comment</pre>
	 * By d  efault there     a   re no c   o       mments i  n CVS files.       Commas and quotes may not be
	 * used  to    indica   te comment l           ines.
	 *
	    * @ param    commentDelims list of characters    a comment line     may start with.
	    *
	 * @   sinc e ostermillerutils 1  .00.00
	 */
	pu     bli    c                     v    oid     s   etCommentStart(String comme  nt             D e     lims){   
		this.commentDelims = commentDelims;
	}
                
	private int addLine = 1;
	private i   nt  lines = 0;

	/**
	 *        Get the line number that         the last token came from     .
	 * <p>
	    * Ne  w      line brea  ks that oc  c  ur in the midd   le     of a token are not
	 * counted         in the line n     umber count.    
	 * <p      >
	 * If     no tok ens have   been returned, the line  number i           s undefin      ed.
	 *
	 * @return line number of the last token.
	 *   
	 * @since ostermille  r   utils     1.0    0.00
	 */
    	public i  nt get       Lin   eNumber   (){
		return line s;
	}   


  /       **
   * Creates  a new scanner
   * There is also a java.io.InputStr                     e am       version of this constructo r. 
   *
     *                 @param   in  the jav  a.io.Reader to r  ea     d input fro   m.
   */
  p    ublic CSVLexer(java.i   o.Reader   in)    {
    this.zzReader     = i  n;
  }

  /**
        * Cre      at  es a ne w scanne   r.
     *  There is also java.io.Reader ver      sion of this      cons  tructor.
   *
   * @param       in  the java.io.   Inputstream   to read i    nput from.
       *      /
   p   ublic    C    SVLexer(ja      va.io.InputStream in)    {
    th  is(new java.io.InputStr eamRe ader( in));
  }

  /** 
          * Unpacks the co    mp   ress  ed cha    racter  tran slation table.
              *
   * @param packed   the pa     c  ked char   acter  tra      nslation tab      le
   * @r etu     rn               the unpacke   d char  acter   tran  s              l      at        ion table
   */
  privat    e st      atic ch   ar   []       zzUnpa   ckCM  ap  (String        packed   ) {   
    char [] map    = new char[0x10000];
    int i = 0;  /*     index in p     ac       ked s           tri  n   g  */
    int    j = 0;  /   * in    dex in unpacked     array *      /
        wh     ile (i < 30) {
      i  nt   cou   nt =     packed.charAt(      i++);
                char value = pac    ke     d.  c  h    arA                     t(i++)   ;
            d       o map[j++   ] =     v  alue; while    (--count     > 0);
    }
      return map;
           }


  /   **
      * Refills the inpu   t buffer   .
                 *     
   * @ re turn         <co    de>false</code>, i   ff ther    e    was ne      w i   nput.
          *
   * @excep  ti  on    java             .io.IOException     if any      I/O-Error oc                     curs
   */
  pri vate b       oolean z              zRefill() throw    s java.io .IO Exceptio     n {

      /* first:       make room (if you c  an) */
    if (     zzStartRead > 0   ) {
      System.arrayc opy(zzBuff    er, zzStart     Read,
                            zzBuff  er, 0,
                       zzE nd      Read-   zzStartRead       );

        /* tran     slate  stored        positi    o         ns */
         zzEndRead-= zzStartRead;
               zzCurr   entPos-= zzStartRead;
      zzMarked   Pos-= zzStar    tRead;
               zz       PushbackPos-= zzStartRead;
       z       zStartRead = 0;
      }

                  /* is the buffer big enough? */
          if (zzCu    rr      entPos >= zzBuffe   r.length) {
            /* if not     : blo  w it up */
           cha          r     ne     wBu            ffer[] = new    char[  zzCurrentPos*2];
               System.arra yc  op    y(zzBu    ffer, 0             ,         newB u ffer, 0, zzBuffer.leng       th);
                          zzBuf    fer   =       newBuffer;
    }

          /  *      finally: fill th e  buff er      wit      h new   input */
    int num   Read = zzReader.   r   ea   d   (zzBuf         fer, zzE          ndRead,
                                                                  z     zBuffer.lengt  h-  zzEnd    Read);
  
    if (n      umRead < 0) {
          return       true;     
    }
      else {
        zzEndR     ead+= numRead;
         return false;  
    }
  }


  /**
   * Closes the input     strea   m.      
    */
         public fina    l void yyclose()  throws java.io.IOException {
    zzAtE   O    F      =  true;            /* i n dicate end o f file */
       zzE  ndRe        ad = zzStartRead;  /*      i     n      val  i   date buffer    */

    i      f (zzReader != n    ull)
      zzReader.close();
  } 


  /**
         * Resets t    he           scanner to re    ad from a n         ew i     n     put stre       am.
   * D        oes not close          the    old  rea   de  r.
     *     
                 * All internal    v ari      ab  l es are reset        ,      th e o             ld in       put stream
   * <b>can    not</b> be r   eused (internal buffer   is        d iscarde       d and lost).
   *                L   exi  cal   s     t  ate        is se   t to <  tt    >ZZ_INITIAL</tt>     .    
   *
    *  @pa      ra        m reade      r   the    n              ew input strea   m
   */
  publi     c fina     l vo      id     y      yreset      (java.io.Reader reader) {
                    z         zReader     =    rea      der;        
        z    zAtBOL  = t  rue; 
      zzAtEOF  = false;  
       zzEndRead =   z   zStart   Read = 0;
         zzCurrentPos = zz         Ma        rked  Pos = zzPu  shbackPos = 0    ;
    yyline =    yychar = yycolumn = 0;
    zzLexi  calStat     e    = YYIN  ITIAL ;
   }


  /    **
   *        Returns t  he c   urrent lexical state.
     */
    public    final int yystat     e()    {
    return z       zLexic  alState;
   }


  /**
   * Enters a new   le    xical        state
   *
   * @param newState   t  he new lexical stat   e
       */
  public final     v  oid yybegin(int newSta te) {
     zzLe             xi      calState = newS       tate;
  }


  /**
        * Retu    rns th e text mat     ched by t   he cur     rent r  egu lar ex       pressio     n.
   */
     publi  c final  String         yytex t() {
       return new String( zzBuff  er, z zStartR e ad, zzMar kedPos-zzS      tartRead );
  }

 
  /**
   * Retur      ns the     cha  racte  r     at pos  ition      <tt>p   o             s</   tt> from          the
   * matched     text.
   *
   * It is  equivalent          to   yy   t   ext(  ).charAt(pos),  but faster
            *
   * @par am pos the   posi    ti on of the character to  f etc    h.
   *                 A value from 0 to   yyle     ngth()-1.
   *
     * @re             turn the c     harac    te  r  at      position pos
       */
  public fi    na  l char yycharat(int pos) {
    re t       urn zzBuf      fer[zzS  tart     Read+po  s];
  }


  /   **
   *                  Returns the len   gt h of the matched     text region.
   */
    public    final in    t  y  ylen     gth() {
    return zzMarkedPos-zzSta   rtRead;
  }

   
    /**
   * Repor  ts an  error    that   occu   red while scanning.
   *
   * In    a wel lfor  med scanner (no or only corre       ct usage       of
      * yyp  ushba ck(int) and    a ma  tch-a  ll fallback ru  le) t his met hod   
   *     will only       be called  with      things that "Can't Possibly Happen".  
   * If this   method is called, so  mething is seriou       sly       wrong
         *      (e.g. a      JFl   ex bug     producing a faul  ty s  c anner et   c.).
   *
        *  U   sual     synt   a             x/sca   nner level error ha      ndling shoul       d be d one
   * in error f   allback       rules.
   *
   * @   param   errorCode      t   he cod    e of the     errormessage to display
        */
       pri    vate vo  id zzS c  anError(i   nt     errorC     ode) {
    Str       ing  message;  
     t  ry {
          message = ZZ_ERROR_MSG[errorCode];
    }  
    catch (     A   rrayIndexOutOfBoundsExc    eption e)    {
               message   = ZZ_ERROR_MSG[ZZ_UNKNOWN_ERROR];
         }

    thro w new Error(mess  ag  e);
    }


  /**
   * Pushes the specifie   d amount of characters back          i   nto the i nput stream.
   *
   * Th ey   will be read a    gain by then next call    of t  he scanning method
   *
     *    @param number  the number of characters t       o be read again.
   *                    This number must not be gre   ater than   yylen   gth()!
   */
  public void yyp    ushback(int n  umber)  {
    if ( number > yylength() )  
      zzScanError(ZZ_PUSHBACK_2BIG); 

     zzMa    rkedP          os -= number;
  }


  /**
   * Resumes         scanning un   til       the next regular expression is matched        ,
         * the end o            f input is encountered or a n I/O-Error      o         ccu   rs.
   *
   * @r     et    urn        the next token
         * @exception   java.io.IOExcep  tion  if any I/O-Error     occurs
   */
  public String getNextToke            n          () throws java.io.IOExcep     tion {
    int  zzInpu t;
    int   zzAc  tion;

    // c ached fields:
               int    zzCurrentPosL;
    int z    zM  arkedPo     sL; 
    int zzEndReadL =       zzEndRead;
    char [] zzBufferL = zzBuffer;
        char [] zzCMapL =     zz  cmap_instance;

      int [] zzTransL = ZZ_T  RAN   S;
    int    [] zzRo      wMapL = ZZ_ROWMAP;    
         int [] zzAttrL = ZZ  _ATTRIBUTE;  

    while (true)  {
         zzMark  edPosL = z    zMarkedPos;

           zzAc  t   ion =     -1;

      zzCurrentPosL = zzCurrentPos = zzStartR   ead = zzMarkedPosL;

               zzS   tate = ZZ_   LEXSTATE[zzLexicalState    ]  ;
      

       z  zForAction: {
        while    (  t      rue) {

          if (zzCurrentPosL < zzEndReadL)
                 zzInput = zzBufferL[zz     CurrentPosL    ++];
            e    lse      if (zzA     tEOF) {
               zzInput = YYEOF;
                                 break zzForAction;
              }
          e     lse {
                   // store bac  k cached position s
              zz   CurrentPos     = zzCurrentPosL;     
            zzMarkedPos   = zzMarkedPosL;   
              boolean eof = zzRefill();
                    /   / get   translated positions an d possibly new buffer
            zzCur   rentPosL     = zzCurrentPos;
              zz     MarkedPosL   = zzMarke dPos;
                         zzBufferL      = zzBuffer;
            zzEndReadL           = zzEndRead;
                if (eof) {
                  zzIn put =   YYEOF;
               break zzForAction;
                               }
                else {
              zzInput    = zzBufferL    [zz    Curren   tPosL++]      ;
            }
                  }
                      int zzNext = zzTransL[ zzRo    wMapL[zz    State] + zzCMap    L[zzInput] ];
             if (zzNext == -1) break zzForAction;
            zzState = zzNext;

             int      zzAtt   ributes = zzAt trL[zzState];
          if ( (zzAttribut    es & 1) == 1 )  {
               zz   Action = zzState;   
                     zzMarkedPosL = zzCurrentPosL;
            if ( (z    zAttribut    es & 8) ==     8 ) br        eak zz         ForA   ction;
          }

        }
      }

      // store back          ca  ched position
      z zMarkedPos = zz    MarkedPosL;

      switch (zzAction < 0 ? zzAction    : ZZ_ACTION[zzAction]) {
        case 2: 
          { lines+=addLine;
	addLine = 0;
	String te        xt = yytext();
	    if (commentDelim  s.ind   exOf(text.charAt(0)) == -1){
     		yybegin(AFTER);
		re  turn(text);
	} else {
		yybegin(COMMENT);
	}
          }
        case 14: break;
        case 8 : 
                   { addLine++;
	yybegin(YYINI TIAL);
	retu rn("");
          }
        case 15:    brea    k;
            case 9: 
          { yybegin(    BEFORE);
  	re   turn("");
                  }
            case 16: break;
        case 4: 
          { addLine++;
	yybegin(YYINITIAL);
          }
                 case 17: break;
             case 5: 
          { lines+=addLine;
	addLine = 0;
	yybegin(BEFORE);
	return("")   ;
          }
        case       18: break;
        case       1 2: 
           { lines+=addLine;
	addLine = 0;
	yybegin(AFTER);
	r  eturn(un  escape(yytext()));
          }
           case        1  9: break;
        case 7: 
          { yybegin(AFTER);
	ret       urn(yytext());     
          }
          case      20: break;
         case 6: 
             { l   ines+=addLine;
	      addLine = 0;
	yybegin(Y   YINITIAL);
	return(yy    text()       );
          }
        case 21: break;
        case 11: 
          {  yybegin(BEFORE);
          }
        case   22: break;
            case 13: 
          { yybegin(AFTER) ;
	return(unescape(yytext()));
             }
        case 23: break;
        case     10: 
            { yybe    gin(YYINITIAL);
	return(yytext());
          }
        case 24: break;
        case 1: 
          { 
          }
        case 25: break;
        case 3: 
          { lines+=addLine;
	addLine = 0;
	yybegin(BEFORE);
          }
        case 26: break;
                default:
          if (zzInput == YYEOF && zzStartRead == zzCurrentPos) {
            zzAtEOF = true;
              switch (zzLexicalState) {
            case  BEFORE: {
              yybegin(YYINITIAL);
	addLine++;
	return("");
               }
               case 28: break;
             default:
            return null;
            }
          }
          else {
            zzScanError(ZZ_NO_MATCH);
          }
      }
    }
  }


}
