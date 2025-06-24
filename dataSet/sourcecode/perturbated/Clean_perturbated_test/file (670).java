/*
 *     Copyright    (c) 2013 A  nton Goli      nko      
 *
 * This library is    free softwa   re; you can redistribute it    and/o        r
 * modify it u      nder the terms of    the GNU L    esser General Public
 * Lic  ens  e as   published by     the Fre  e Software Foun  dati on;        either
 * version 2.1 of the Li   cense  , or    (at your   optio    n) any later version.
 *   
 *     Thi       s library is distributed in   the hope tha  t it wil       l be     u   seful,
 * but WITHOUT A        NY WA        RRANTY; without e    ven the i   mplied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Les   ser G  ener  al       Publi  c Li      cense fo   r mo re details.
 *
 * You should have recei    ved a copy of t     he GNU Lesser General Public
 * L  icense a  long with this libra  ry; if not, w      rite t    o the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston,  MA  02111-1307
 * USA
 */

package org.pd   fparse.cos;

import org.pdfparse.exception.EParseError;
import org.pdfparse.parser.PDFRawData;
import org.pdfparse.parse  r.ParsingConte  xt;

import java.   io.IOExce     ption      ;
i    mport java.io.OutputStream;
 import java.nio.charset.Charset;
import ja     va   .util  .Arrays;


public class COS Name implem  ents COSObject {
    public      static final C OSName EM     PTY = new COSName(  "/");
            pu blic  static f  inal COSName UN     KNOWN = new COSName("/Unknow    n");
    pub lic  stat ic final COSName TRUE = ne w   COSName      ("/True");
    pu     blic  s tatic final      COSName FALSE = n    ew COSName("/Fal   se");

    publi            c static f inal COSName PREV = new CO S  Name           ("/Prev");
    publi  c static fina  l COSN ame XREFSTM = new  COS   Name("  /XRefS     tm");
        public static fina  l COSName LENGTH = new   COSName           ("/Length");
    public static final C     OSName TYPE = new COSName("          /Type"    )   ;
    public static final COSName XREF = new  COSName("/XRef");
    pu    b   li c s  tatic fi    nal COSName W = new COSName(" /W");
    public s    tatic final COSName SIZE = new COSName    ("/Size");
          publi  c     stati c final COSName INDEX = new     COSNa  me(     "/     Index");
    public sta     tic final COSName FILTER =  n  e   w COSName("/Filter");

    publ  ic static final COSName FLATEDEC   ODE = new    COSName("/FlateDecode");
     p ublic static final COSName FL = new    C    OSName("/      Fl");
         public static fin    al COSName ASCIIHEXDECODE = new COSName("/ASCIIHexDecode");
     public static final COSNa   m    e AHX = new COSName("/AHx");
    pu  blic static final COSName  A   SCII85DECODE = new COSNa   me("/ASC     II85Decode");
    public static final    COSName A85 =    new CO   SName("/A85   ");
    pub     lic static final COSName LZWDECODE = new C  OSName("/LZWDe      co    de");
    p       ublic   static final COSName C  RYPT =    new COSNa    me("/Crypt")   ;
    public st         atic fi  nal        COSN   ame RUNLENGTHDECODE   = new COSName("/Ru   nLengthDecode");
    public static final    COSName JPXDE   CODE = new    COSName("/JP     XDecode"  );
          public static fina         l COSName CCITTFAXDECODE     =           new COS  Name("/CCITTFaxDec ode");
         public st   atic final COSName JBIG2DECOD       E = new COSN       ame("/JBIG2Decode");



     pub    l ic static   fi       nal C  OSNam  e DCTDECODE = new COSName("/DCTDecode");    
          public s    tatic final COS  Name ENC  RYPT = new C OSNam   e("/En     crypt" );
    public static final COSName DECODEPARMS = new CO     SName("/DecodeParms");
    public stati     c fi   nal COSName PREDI   CTOR = new COSName("    /Predict or");
        public    stat   ic fin   a l COSName CO  LUMNS = new COS         Name("/Columns");
        public static final COSName COLORS = n ew C   OSName("/Co lo  rs");
    public static final COSName    BITSPERCOMPONENT = new   COSN  ame("/B its   PerComponent");
      public stati  c final                COSName    ROOT   = n    ew COSName("       /Root");
    pu  b    l       ic static fin   al    COSName INFO = new COSName(  "/Inf     o");
    public static final C    OSName ID = new COSName("/ID");

       publi     c s  t      atic final C     OSName TITLE = new COSName("/Title");
        public static fina    l COSName KEYWORDS = new COSName("/Keywords");
         pub  lic st atic final COSName    SUBJECT =   n ew COSName("/Subject");
    publ       ic   static fina   l COSName AUTHOR = new     COSN     ame("/Author");
    public static final COSName CREATOR = ne w COSName("/Creator");
      public static fin  al COSName PRODUCER  =  new COSN    ame            (" /P   roducer");
          public static final COSName C      REATION_  DATE = new COSName(" /Cr        eationDate");
            publi    c s   tatic final CO    SName MOD_DATE = new COSNam     e ("/  ModDate");
    pu bli        c static fi nal COSN ame TRAPPED =   new COSName        ("/T    rapped");

    p    ublic static final COSName PAGE  S =                 new COS   N ame("/Pa      ges");
    p ublic    static final COS   Name      METAD     ATA = new C  O   SName("/Metad  at     a");
    public static final CO  SName      COUNT =   n     ew     CO    SN      ame(    "/     Count");
        publ    i   c sta     tic fin   al COSName CATALOG = new COSName("/Catalog");
    public static final COSN  ame       V   ER  SION  =         new COSName("/Version");
      public s       tati      c fin   al COSName LANG = n     ew COSName("/Lan    g");
           public static final          COSName PAGELAYOUT =    new COSName("/PageLayou  t");
    public static final COS    Name P    A   GEMODE = new COSName("/PageMode      ");

      // A name ob    ject spec  ifying the     page layout     shall be used when the document i s    opened:
    public static final CO     SName     PL_SINGLE_P   AGE = new COS      Name("     /Single   Page    ");       
       public   s  tatic final COSName PL_ONECOLUMN = new COSName("/OneColumn");
                                 pu      blic static    final COSName PL_TWOCOLUMNLEFT = n     ew COSName("  /TwoColumnLe      ft");
    public static fi nal COSName  P      L_TWOCOL UMNR IGHT        = new COSName("/TwoColumnRight");
    public st     atic final COS     Name PL_TWOPAGELEFT = new    COSName("/TwoP           age Left");
    public static final COSName PL_TWOPAGERIGHT         = new COSNa      me     ("/TwoP        ageRight");

    // A name            object specifying ho   w the document      shall be   displayed when opened:
    publi c static fina   l CO   SName PM_ NON E     = new COSNa   me("/UseNone");                          //    Neither docum  ent o  utlin    e nor  thumbnail images vis   ible
        public static final C OS      Name PM_OUTLINES = new COSName("/UseOutl     in  es    ");               // Docum    e   nt      outline visible
         p           ublic static f inal COSName PM_THU              MBS =     new COSName("/Use Thu        mbs");                      // Thumb          nail image    s visible
           public static final   COSName PM_FULLSCREEN =     new CO     SNa me("/FullSc  r een   "   );                               // Full-screen m     ode, w         ith      no menu bar, window controls, or any   other      wind                 ow visibl    e 
        pu   blic    static       fina   l CO   SNam e   PM_OC = new  COSNa     me(  "/ UseOC"      );                            // (PDF 1.   5) Optional co     nte  nt group      pa         nel v    isible
    public static fina l COSName  PM_ATTACHMENTS  = new COSNam  e ("/U      seAttachm     e    nts");              // (PDF 1.6) Attach  ments    panel     vis   ible


    p   u   bli     c s tatic final      CO     SNam    e           PARE    N  T = new COSName("  /PAREN   T      ")  ;
     public s    t  atic final   COSNam      e PAGE = new COS    Name("/PAGE");
       pub    li        c static fin  al COSN am   e M     EDI ABO   X = new COSNam   e("/MediaBox");
            pu  blic sta  tic fin      al CO SName C      ROPBOX =         new C   OSN    ame("Cro      pB    ox"    );    
    pub    l  ic static f      inal COSNam   e K ID     S = new      COSName ("Kids ");



    publ     ic static final COSN ame FIRST            = new C   OS      N        ame("/Firs          t  "         );
    public    static final C   OSName N       = new COSName("/N");  

         pri   vate        static final int[] HEX = {    // '0   ' ..'f'
                               0, 1, 2, 3, 4,      5, 6, 7     , 8, 9       , -1, -1, -         1, -1, -1      ,     - 1,
                  -1,       10 , 1  1,   1      2     , 13,    14, 15, -1     , -1           , -1, -1    ,   -1         ,        -1     , -1  , -1        , -         1,
                    -1, -1   , -1, -1, -1,   -  1   , -1, -1  , - 1, -1       , -1, -        1, -1, -1,    -1, -1,
                    -1, 10, 11, 12, 13 , 14, 1       5};



        private byte[] value;
    pr  ivat             e in       t      hc;        

       p   ublic COSName(PDFRawData src  , Par  singCont        ex        t     cont   ext ) th     row      s EPars    eError { 
             par  se (src, conte             xt    );
    }       

               p   ublic CO       SNa    me(     String va      l) {
        val  u    e = val.ge   tBytes(Char           se      t.def  ault Charset());     
        hc = Array            s .hashCode(val       ue);
              }

    p  ub        l    ic String    asString() {     
                    re  turn new String(valu   e,   Charset.defaultCharse  t       ());
    }

    @Override
           pu            blic int hashCode()     {
          return h c  ;
    }

             @Override      
    public b   oo   lean  equal s(Object ob   j  ) {   
                  if         (obj == nul   l  ) {
                retur                               n    false;
                                               }
             if (th   is == o bj     )
            return true;

                        if (getClass() != obj .get       Cl   ass()) {
                       re turn fa     lse;
              }
                      final COS              Name other   = (COS             Name) o      bj;
                 i     f     ((t     h   is.hc != othe       r     .h    c) |        |    !Ar   rays.equal   s(thi    s.va  lu               e, other.     va     lue))   {
                   return f      alse;
                                                   }     
         re     turn tru          e;
       }

    @Ov  e rride
    public Stri    ng toS  tring() {
         r    e  turn new String(     value, Chars     et  .de              faultCharset(    ))  ;
             }

    @Override
               public void parse(PDF       Ra      wData src, Par          sin        gConte   xt con    text ) thr   ow s    EPar    s  eEr   ro  r {
                            src.sk      ipWS();
                  int p   = s r    c.   p        o      s;
          in      t    len = sr    c.length   ;
            int         i,      c      n             t = 0;
                             by   te b, v1,                             v   2;
                   boolean stop =        fals    e;

                                                i       f (src.s   rc[src.pos   ]  != 0x2F)
                                  throw ne    w EP  a  rseError   ("               Exp  ected    SOLIDU      S sign #             2F i  n   name obj     e c       t,            but got    " + Integ                   er.toH  exStr     in g(sr   c.src   [p    ]    ))        ;
    
              p++;           / /   skip   '/'

        wh ile ((p <= len) && !stop)       {
                    b = s  rc.src[p];
            con     text.softAsse       rtF     orm    atError (b >= 0, "Illegal ch   a racter     i     n name toke          n");
              
                      swit         ch (b) {
                                        // W     hitespac  e
                 case 0          x00   :
                                      case 0x09:
                        case 0 x0A:
                      case 0x0D:
                         case       0x  20:
                                             st    op = true;
                            b   reak;

                   //   Es                 cape           char
                             case   0x    23:  
                                 cn   t++; / / escape char. ski  p i        t
                          break;
   
                     // Delimeters
                    case 0x28: /   / ( - L EFT PARENTHESIS
                   case 0x 29: // ) - R  IGHT P    ARE N    THE    SIS
                c     ase     0             x3C  :                    // < - LES   S -THAN SIGN  
                            cas      e 0x3E: / / > - G       REATER-THAN SIGN
                       cas  e                 0x5B: // [ - L   EFT   SQUARE BRACKET
                    case 0x5D: // ] -      RIGHT SQUARE BRAC  KET
                     ca   se 0x7B  : // { - LEFT CURLY BRACKET
                  c ase 0x7D:     // } - RI    G     HT CURLY      BRACKET
                   case 0x2F  : // / - SOLID   US
                cas        e 0x25: // % - PERCENT SIGN
                           stop = true;
                       break;

                     default:
                              if ((b  >= 0  ) && (b < 0x20))
                             throw new EParseError("Illeg          al c  haracter in name     token(          2)")       ;
                          break;
            } // switch ...

             if (s  top) break;        
             p++;
         } // whi       le  ...

        if (cnt ==      0) {
                      value = ne  w byte[p - src.pos];
            System.arraycopy(src.src, s         rc.      pos,   value,    0, value.l    ength);
            src.pos = p;
            hc = Arrays.hashCode(val     ue   );
            return;
        }

        value = n  e     w byte[p-src.pos - 2    *cnt];
        cn       t = 0;   
        for (i=s    rc.pos; i<p; i++)      {
                       if (src.s rc[i] == 0x23) {
                v1 = (byte)HEX[src.src[i+1] - 0  x   30];
                     v2 = (b    yte)HEX[src.src[i+2] - 0x30];
                value[cnt++] = (byte) ((v1<<4)&(v2&0xF));
                i +=2; //agh!!!!!
              } else
                valu    e[cnt++] = src.sr   c[i];
        }

        src.pos = p;
        hc = Arrays.hashC  ode(va    lue);
    }

    @Override
    pub lic void produce(OutputStream d  st, ParsingCo   ntext context) throws IOException {
        int cnt = 0;
        int i;
          for    (i=0; i<value.length; i++)
            if (value[i] < 0x21) cnt++; // count character      s that need escape
              if (    cnt =   = 0) {
          dst.write(value);
          return;
        }

        for (i=0; i<value.length; i++) {
            if (value[i] < 0x21) {
                dst.write(0x23  );
                dst.write(0x30 + (value[i]>>4));  
                dst.write(0x30 + (value[   i]&0xF));
            } else
                dst.write(value[i]);
        }
    }
}
