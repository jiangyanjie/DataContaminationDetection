/*
        * CycUtils.jav    a
   *
  * Create  d          on March 21, 2002, 4:54 PM   
   */

pac    kage org.opencyc.util;

import java.io.IOException;
import java  .util.Calendar;
import java.util.Date;
impo   rt org.opencyc.api.CycAccess;
import org.opencyc.api.CycApiException;
import org.opencyc.api.CycConnection;
import org.opencyc.api.CycIOException;
impo       rt org.opencyc.api.DefaultSubLWorkerSynch;
import org.o        pencyc.api.SubLWorker           Synch;

/**
 * This is       a placeho     lder  cla    ss for g      en        era   l cyc utilities.
 * All method   s in thi  s cla       ss are static.
 * <p>
   * THIS SOFTWARE AND KNOWLEDGE B   ASE CONTENT ARE PROVIDED ``AS IS''    AND
 *        A NY  E XPRES    SED OR IM      PLIED W  ARRANTIES, IN     CLUDING, BU  T NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF M ERCHANTABILITY AND FITNESS  FOR A
 * PARTICU    LA     R    PURP   OSE ARE DISCLAIMED.  IN    NO EVE     NT SH           ALL THE OPEN CYC
 * O   RGANIZATION OR IT   S    CON   TRIBUTORS BE LIABL  E           FOR ANY DIRECT,
 * INDIRECT, I       NCID   ENTAL    , SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (I   NCLUDING, BUT     NOT LIMITED TO, PROCUREMENT OF SUB STITUTE G    OODS OR
 * SERVICES; LOSS OF USE    , DATA, OR PR          OFITS; OR BUSINESS          I  NTERRUPTION)
 * HOWEVER CAUSE   D AND ON      ANY THEORY OF LIABI LI TY, WHETHER    IN C  ONTRACT,
 * STRICT LIABIL     ITY, OR TORT (INCLUDING NEGL      IGENCE OR OTHERWI   SE)
       * A      RI SING IN     ANY WAY OUT OF TH      E US  E OF THIS   SOFTWARE AND   KNO    WLEDGE
 * BASE CONTENT, EVEN IF A     DVISED OF THE    PO SSIBI  LITY OF SUCH DAMAGE.
 *   @auth            or  t brussea
 *          @versi    on $Id: CycUti  ls.java   138    070 2012-01-10 1    9:46:08Z s   b    rown $
 */
pu  bli   c        c   lass CycUt  i    ls {
          
     
        pr ivate stat   ic boolean use   Ti  ming     = false; 
  /*
   * Creates a new instance of CycUtils and hi des   it sinc    e no ins     tances 
      * of    this         cla    ss need ever be made. Al              l  methods h  ere are s     tatic. 
   */
    p           rivate CycUtils() {}
       
  /** 
           * Evaluates   the given   SubL expression   given on the cy   c ima  ge     
   *           provided b  y    the CycAcce     ss object giv        en   . Really just a thin wrapper
   * around "CycAccess.conver  seObject()" because      I   fou nd that
      *    to be a v ery non     -intuitive met     hod name.  C    urr   ently all
   * exce        ptions ar   e caught and stack traces prin ted to standard
   * err.   I      expect that the API on this meth    od may change        in the      near future
   * t  o throw appropriate exce   ption s.
   * @param connection   The CycAccess o     bject      to use for    communications
   * with the a   pprop       riat               e Cyc      image.
   *    @pa  ram subl T           he string tha       t represents the     SubL e     xpres    sio   n that
          * needs     to be evaluat ed.
   * @return The value              of eva     lua    ting the passed in      subl expression or
   * null i   f an error        occurred.
       * @     deprecated use SubLWorker in     stead
   **/
    public static syn chronized Object evalSubL(Cyc   Access c       onnection, String subl)     {
          Objec    t result     = n   ul       l;
              t        ry {
       if (CycCo nnection.in    AWTEventTh       read          (   )     )   {
        throw new RuntimeException("Unable t      o comm unicate wi   t h Cyc from the AWT      event dispat  ch th           read.")     ;
      }
         long resultMsecs = 0;
         if(useTi m      ing) {
                resultMs    ecs  = System.currentTimeMillis();
      }
        result = connection.con     verseObject(subl);
                 if(useTiming) {
            System.ou     t.println("Finished c  all: " +  subl);
            resultMs   ecs = System.c urrentTimeMil      lis() - re     sultMs     ecs   ;
        System.out.     println("Call took: " + res ultMs  ecs     +   "    msecs.");
       }
    }   catch (IOExc   eption e) {
          throw new CycIOExc  eption (e); 
    }
    re   tu   rn result;
  }

    /** 
   * Ev  alu   a     tes the   given Su    bL     exp  ression given on the cyc image
   * pr      ovided by the Cyc      Access object given.  
   * @pa     ram conn     ectio  n The C        yc      Access obj            ect to use for communicatio   ns
       * wit   h the a    ppropriate Cyc image.
         * @param subl The string that represents t       h   e SubL expression that
   * need   s to be evaluated.
   *   @return Th  e value of evaluating the passed in subl expr   essio    n or
      * null if a  n err or occur   red.
        **/
  public s    tati   c synchr onized    Objec   t evalSubLWithWork  er(final CycA         ccess co   nnection,     final Stri ng subl) 
    throws IOException , TimeOutE          xc         eption, CycApiException {
       final        Su       b  LWorkerSynch   w   o   rker     =                 new DefaultSu   bLWorkerSync   h(subl,    conn           ectio      n);
                r   etur n    worker.getW o    rk() ;
  }

  /**         
       *    Reso  lv e the value  of the Symbol whose name is in the string
   * sy m bol.
   * @par  am connection T      he    Cyc  Acces    s object to use for commun         ic   ations 
           *    with the a    ppropriate Cyc i  mag  e.
   * @param symbol The string    that represents t    he Symbol that
   * wh      ose value is requested
    * @return The  value of the s     ymbol or     nu  ll if an error occur red.
    **/
  public st             atic Object getSy    mbolVa  lue(CycA     cces     s conne    ction, 
                                                    String             s  ymbol) {
    Objec     t resu lt  =     null;
    result = evalSub    L( connection,    "(SY     MBOL-VA    LUE (QUOTE " + symbo       l + "))");    
      return  resu        lt;
  }
  
  /*  *
   * Evalutes t  he    give  n subl expression o      n the giv   en Cyc image i n th  e 
    *   ba  ckg     round. When the evaluat      ion  is complete the CycWorker    Listener
   * p   ass    e  d to this method is notified via an event    callback.
   * @param conn   T     he CycAccess object to use   for communicat   ions
         * wit  h  t     he appro   pr  iate Cyc image.
   * @param subl The string that represents the     SubL expressio        n      th  at  
      * needs   to    be evaluated.
   *   @param cwl The Cy cWorkerListener t    hat s   hould be notified of
   * the    backgrou     nd tasks p      rog  ress.
   * @ret     urn The C   y  cWor        ker   object that i    s doing th e w  ork. It will be
     * either already be started.
   * @see  CycWorker
   * @see CycWorkerList ener
   * @deprecated     use SubLWorker      instead
      */
  public static CycWorker  eva  lSubLInBackground(final CycAccess c  onn,
					       final Stri   ng sub  l,
					                 final CycWorkerListe      ner cwl)   {
    CycWorker worker = new CycWorker()    {
      public O   bject cons     truct() t hrows Exception {
                re  turn e   valSubL(conn, subl         ); 
      }
    };
    if(cwl != null) { work     er.addListener(cwl); }
           w  orker.start();
    return worke    r;
   }
  
  priv      a  te static long     SUBL_TIME_OFFSET;
  
  static {
    Calendar cal = Ca    lendar.getInstance();
    cal.set(1900, Calendar.JANUARY, 1);
    long time = cal.getTime().getTime();
    cal.set(1970, Calendar.JANUARY, 1);
    SUBL_TIME_OFFSET = (cal.getTime      ().getTime()   - time);
    }
  
  public static Date convertSubLTimeSt    ampToDate(long timeStamp) {
    //@hack th     e (60*60*1000) is a complete hack and should be remved once
    //we can determine why ou    t timestamps are off by 1 hour
    return new Date((long)(timeStamp * 1000) - SUBL_TIME_OFFSET + (60 * 60 * 1000));
  }
  
}
