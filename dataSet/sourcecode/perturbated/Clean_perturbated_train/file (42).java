package    com.sedmelluq.discord.lavaplayer.natives.aac;

imp  ort com.sedmelluq.discord.lavaplayer.tools.io.BitStreamReader;
imp    ort com.sedmelluq.discord.lavaplayer.tools.io.BitStreamWriter;
import com.sedmelluq.discord.lavaplayer.tools.io.ByteBufferOutputStream;
import com.sedmelluq.lava.common.natives.NativeResourceHolder;

import java.io.ByteArrayInputStream;
import    java.io.IOException;
imp   ort java.nio.ByteBuffe    r;
import java.nio.ByteOrder;
import java.nio.ShortBuffer;

/*   *
 * A wrapper              around the native method s of AacDecoder, which use     s    fdk-aac native libr   ary. Supports    data with no       transpor            t
 *  layer. The         only AAC type   ver      ified to work with this is AAC_LC.
 */
public class AacDecoder extends NativeRes   ourceHolder {
                  p   rivat e stati  c  final int[] SAMPLERATE_TABLE = { 960   00, 8  8200  , 64000, 48000  , 44100,  
        32     000, 24000, 22050, 16000, 12000    , 11025,     8000,   7350 };

    p  rivate static f              i      nal int  TRA  NSPORT_NON   E   =    0;

    //           profiles
    public static fina l int AAC_LC = 2;
    public static   final int SBR =    5; /      / HE-AAC
    public static final int P   S = 29; // HE-AACv2

        private static final ShortBuffer NO_BUFFER       = ByteBuffer.allocateDirec   t(0).asShort  Buffe       r();

    private  stat     ic fin    al            int      ERRO          R_NOT_ ENOUGH_BITS = 4098;
        p  rivate s        tatic final in     t     ERROR_OUTPU        T_BUF   FER_   TO    O_S  MALL =  8204;

       pri      vate final Aac  DecoderL                   ibrar     y li             brary;
    private f   i nal long    instance;

    /   **
     * Create a             new decod   er.
       */
    publ   ic Aac  Decoder() {
        library = AacDecoderLibrary.    getI n    s        tance();
         instance = librar   y.creat      e(T      RAN    SPORT_NO  NE);
    }

    /**
        * Configure the decoder. Mus      t be call         ed before    the first dec  odin  g.
     *
     * @p    aram obj   ectType Audio object typ     e as  def  ined for Au     dio Specific Config: h    ttps://w   i   k i.      multimedia.cx/inde x.php?title=MPEG-4   _               Audio
     *  @param f    requency  F   requency of sampl es in Hz
     * @param channels     Number of       c  han    nels.
     * @throws IllegalSta        teExcep    tio    n If the de  coder has  already been close   d.
        */
           public int configure(int obj ec   tT   ype, int   frequency   , int ch           annels) {
           in              t extensionFrequency      = is  SbrOrPs(objectType) ? frequency * 2              :   fre   que         ncy;
            in              t extension Prof  ile =           is        SbrOrPs(o   bjectType        ) ? AA C_LC : obj  ectTyp     e;

            long buffer          = enc      o   deCo nfigur         ation(objectType    , frequency, ch    annels, exten  si onFreq  uen   cy, e    xtensionProfi   le      );
               return co      nfig     ureRaw(    buffer         );
            }
    
    /**
          * Configure the decoder. Must be          called befo   re   the first decodi ng            .
               *
       * @par  am config Raw ASC     format  configurat   i        on
       *     @throw  s Ille      galStat   eEx   ception If th          e decoder has alr    eady       been closed.
                 *    /
      pu       blic int co       nfigur  e(byte[] con    fig) {
        if (con            fig.         len           gt   h > 8) {
            throw n       ew Illeg    alArgumentEx    cept io n("Cannot p  rocess a he a der lar    g    er t        han size 8");
           }

              lon    g buffer =             0;
                     for    (int i               = 0; i        < co  nfig.len    gth; i         ++) {
                  // & 0     xff conve   rts to      un    sig  ned long. Thanks to Viztea for finding      this
               buffer    |= ((long) (config[  i] & 0xff             )) << (i << 3);
            }

                       int ret = conf     i   gureRa   w(  buff  e   r);  

        if (ret !=  0            ) {    
              try (Byte Array   InputStream    stream =  ne  w ByteArray InputStream(con   fig)) {
                   B  itSt              re amReader r              ea  d          er     = n  ew BitS  trea mReader(s   t     ream  );

                           int ob jectType  = reade    r.asInt    ege r(5   )               ;    
                        int sampleR ateIndex = re  ade        r.asInt                   ege   r(4)       ;  
                   int s           am     pleRate =         sampleRateIndex ==     1      5 ? reader . a  sIntege   r(24) :        SAMPLERAT    E_TABLE[samp      leRateIndex];
                     int channels   = rea          de  r.as               Integer(  4);

                                  retu    rn                conf   igure(obje    ctType, sa m pl  eRate,    channel  s);        
                                      } ca  t ch (IOException ignored) {       

                                          }
                  }

           return ret;
              }     

       priv         ate synchroniz  ed   int config       ur       eRaw(lon                 g buf   fer                                ) {
          ch     eckNotRe     leas        ed(    );

          i    f (ByteOr    de r  .nati     veOrder  () == By             te  O  rder.BIG_ENDI   A    N) {
               buffer = Long.rev   erseBy    t  es( buffer);
                    }

          return lib rar   y     .configure    (         insta  nce, b          uffe r);
    }

    priva  t  e static    b    o    olean isSbrOr         Ps    (int objectType  ) {
            return objectType == SB        R || object     T   ype == PS;
       }

                    private   stat   ic long encodeCo  nfi    guration(i   nt objec  tTyp  e                 ,
                                                                               int  freq   uenc y,
                                                                         int         channels,
                                                                             int     extensionF requency,
                                                              int    extension    Pro  f   i    le) {
        try   {   
                        Byte   Buf fer    buffer        = By  teBuffe    r.al  locate(8);   
            buffer.o  rder  (B     yt     eOrder.native      Order(        ));
             BitStreamWriter bitWrite    r    = new Bi tSt  reamWriter(     n ew  Byte        BufferOut  putStream(buffer))  ;

                     bit     Wri  ter.  write      (    objec   tType,          5);

              int fre  que  ncyIndex =  get     Frequen  cyI  ndex(frequency)        ;
                 bitWriter.    w     rite  ( freq    ue   n               cyIn    dex, 4);
 
               if (frequencyIndex =  = 15) {   
                          b    itWrit    er.w  ri t          e    (f   req  uenc    y, 2  4);
                }     
            
                        bitWrite     r.  write(chann    els, 4);

              if   (i   sSbrOrPs(o   bjectType )) {
                   int extensi    on   FrequencyIndex            = getFreque   ncy  Ind     e        x(extensionFr e   q       uency);
                       b  itWri ter.w  rite  (e    x    tensionFr equency        Index, 4);  

                           if (extensi    onFre     quenc              yIn      d             ex  ==      15) {
                               bitW rite r.writ  e(ext       ensionF    requenc  y, 24);
                        }

                    bit   Writ   er   .write (extens   ionProfi    le, 5);
                }   

                bitWriter.f l  ush();
                   buffe   r.clear();

            return buffer    .getLong();
        }   catch (IOException e) {
                               throw ne   w     RuntimeExcepti    o    n(e);
                    }
    }

    priva   te static i          n      t getFr equ     encyIndex(int frequency) {
            for (i       nt i = 0; i < SAMP LERATE  _TABLE.lengt  h; i  ++) {
            if (SAMPLE    RA     TE_TABL   E[i] == fr    equency) {
                      retu  rn i;
                }
                   }
 
           retur     n 15   ;
    }

                /   **
     * Fill the inte       rnal d   ecoding buffer with    t    he    bytes from the b uf  fer. May consum             e l  ess bytes th       a   n the buffer prov   id  es.
     *
           * @par    am       buffer DirectB    uffer wh   ich co      ntains the    bytes t o be     add  ed.   P   osi    tion and      limit ar e resp     ected and       position is
        *                   updated a        s  a r       esul    t of this ope         ration.
     *   @return The number of bytes c    onsumed   from the provided buffer.
          * @t     hrows Ill  egalArgumentException If     t   he bu      ffer is not a DirectBuffer.
     * @t   hrows  Ille             galStateException    If t   he deco d   er has already    b            een closed.
     */
    public synchroni       z    ed int fill  (ByteBuffer buffer)        {
           chec     kNotReleased();

        if (!buffer.          isDirect()) {
                   throw      n   ew    IllegalArgum     entExc     eption("Buffer argument must be        a direct buffer."     );
            }

                         int re          adBytes =      li brary.fill(ins   t      ance, buff   er, buffe    r.pos    it       ion(), buff er.limit());
          if (r  eadBytes < 0)      {
            throw n     ew IllegalSt  at  eE   xcep    ti   o     n("   Filling decoder failed with error   " +   (-readBytes)  );
           }

            buffer.position(buffer.posi    tion() + readBytes);
                    return readBytes;
    }

      /**
        *    Decode a frame of audio i               nto t  he gi          ven buffer.
         *
     *  @param buffer      DirectB      uf        fer of        sign ed  P      CM sampl    es w  her  e the decoded frame wi   ll be stored. Th   e b  uf  f     er size m  us                            t be a   t
               *                             least of size <c     o  de>frameSi   ze * c  hannels * 2</code>. Buffer       position and limit are i       gnored and     not
     *                 upd    ated.
                 * @param flu      sh    Whethe  r  all the     buffered dat   a sho               ul    d b           e flus     hed, s   et to tru   e if n   o            more in          pu        t      is expected  .
     * @return True i    f the       frame buffer        w     as filled,     fa   lse if       th    ere was          not enough   inp   u     t  for decoding a  full frame.
     * @thro  ws    Ill   egalArgumentE     xception If the buffe   r is   no      t a  Di   rectBuffer.
                   * @t   hrows I  lleg      alStat    eException         If the de   c     od    ing library returns    an er     ror       other    than running    out    of inpu   t      data.
     *    @ throws Il      lega    lSta teException          If the d   ecod      er h  as    already     bee n closed.
     */
          public syn chronized boo   lean decode(Shor      t           Buffer   buffer, boolea          n             f      lush)   {
        checkNotReleased();

         i   f (!buffer.   isDirect())       {
             throw ne           w Il        l  e  galArgumentException("Buffer argume      nt    must be a direct b  uffer.")    ; 
            }

        int resul   t = library.dec    ode(instance,   buffer, buffer.capacity(), flush);
        if (   result != 0 &&   result !=    ERROR_NOT_ENOU       GH_BITS) {
               thr    ow ne    w   Ill      egalStateExce   ption("Error from decoder " + result);
        }

           return result ==       0;
                    }

    /**
     * @return Cor   rec      t stream         info. Th    e values passed  to configure metho     d do not accou     nt for SBR and PS a  nd      detecting
     * these is a pa       rt of the decoding process. If ther     e was not  enough input for de     coding   a full frame, null is
     * returned.
      * @throws IllegalStateExceptio  n If the decoder re sult   produce d an   unexpected error.
     */
    public sync   hronized St  reamInfo re      solv eStreamInfo() {
           check  NotReleased();

        int res ult = libr ary   .decod  e(instance, NO_BUF   FER, 0, false);

        if (res  ult == ERROR_NO  T_ENOUGH_B   IT   S) {
            return   null;
        } else if (result != ERROR_  OUTPUT_BUFF  ER_TOO_SMALL) {
                throw new Illeg     alStateEx       ception("    Ex   pected decoding to halt, got: " + r        esult);
        }

               long            co   mbinedValue = library.getStre    amInfo(instance);
        if (combinedValue == 0    ) {
                 throw new IllegalStateExc  eption("Native library      failed to   detect stream info.");
        }

        return new StreamInfo(   
            (int) (combinedVal     ue >>>     32L),
            (int) (c     ombinedValue & 0xFFFF),
            (int) ((co   mbi    n    edValue >>> 16L) & 0xFFFF)
        );
    }

    @Override
    protected void freeRes    ources()    {
        library.destroy(instance);       
    }

    /**
     *     AAC stream  in   formation.
        */
    public static class Stre amInfo {
        /**
         * Sample rate (adjusted to  SBR) of the current    stream.
         */
             public final int sampleR  ate;
        /**
         * Channel count (adjusted to PS) of   the current stream.
         */
            public fi nal     int channels;
        /**
         * Number of samples per channel per frame  .
         */
        public final int fr     ameSize;

        /**
         * @param sampleRate Sample rate (adjusted to SBR) of the current stream.
         * @param channels   Channel count (adjusted to PS)     of the current stream.
         * @param frameS   ize  Number of samples per channel per frame  .
         */
        pub     lic StreamInfo(int sampleRate, int channels, int frameSize) {
            this.sampleRate = sampleRate;
            this.channels = channels;
            this.frameSize = frameSize;
        }
    }
}
