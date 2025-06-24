package      mcomp.dissertation.live.streamer;

import   java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import        java.io.IOException;
import java.util.Date;
import java.util.concurrent.ConcurrentLinke  dQueue;
import java.util.concurrent.ScheduledExecutorService;

import   mcomp.dissertation.live.serverconnect.NettyServerConnect;

import org.apache.log4j.Lo gger;

    /**
 *        This abstra  ct class is responsible for streaming live data fro  m a C   SV file.
 */
public abstract   class AbstractLiveStreamer<  E> {

            private      File file;
      protecte         d i       nt streamRate  ;
   private ScheduledExecutorService execut    or;
   privat          e String folderLo        cation;
   private String dateString;
   private Concurrent  LinkedQueue<E> buff    er;
    priva          te String     server     IP;
       pri   vate int      serverPo   rt;
   pr          otected Buffere     dReader br;
   private stat ic final Logger   LOGGE   R = Logger  
            .getLog ger(AbstractLiveStreamer.cla      s       s);  

   /**
    * 
    * @param st  reamRate
     * @pa  ram monitor
          * @param ex      ecuto  r
    *      @  para    m           folderLocation
        * @    param dateS   tring
       * @param serve     rIP
    * @param s         erverPort
    */
   publ        ic AbstractLiv   eStreamer(f       in      a  l int  streamRate, fi     nal Obje ct monitor,
         final Scheduled   Exec  ut  or      S   ervice     exe      cutor, final String folderLocat     i        on,
              final String dateString  , fin      al String      serverIP, f          inal int ser           v    e       rPort,
               fina  l Co ncurrentL    i nkedQ ueue<E> buffe   r) { 

      try {
                        thi  s.str     eamRate = stre    amR ate;
                 th   i    s.date  String     = dateString;
              this.serverIP   = serverIP;
              this.folderLocation = folderLocation;
            this.file =    rea   dFileD ata();
            this.execu      tor = e xec     utor;   
         this.serverPo   rt = serverPort ;
                   this.b  uffer = buffer;
                  this.br         = new B    uff       eredR eader(new FileReade r               (fil      e));
           R     untime.getRuntim   e().  addS hutdownHook( new ShutDown   H             ook());
                         c rea  teServerSett   ings()   ;

           } catch (IOException e) {
          LO      GGER.error("Error parsing the file", e)    ;

           } catch (In   terr   upt        edE          xception e) {
               LOGGER.      error(   "Un able to connect to s     erver..", e);
    
           }  catc   h (Exception e) {
                      LOGGE      R.error("Error finding the    required   fil e to strea      m o     ff..", e);
      }
   }

   private void createServ     erSettings()           throws                         I   nte  r      ruptedExcept io     n {      

                   Net     t  y ServerConnect<E>   send = new NettySe      rv    erC onnect<E>(s    e      rve     rIP, buffer, 
            e    xec         utor, strea     mRate);   
      send.connectToNe    ttyServer(se                  rve   rPor t  );

   }

          /**
    * 
    * @r         et           urn
    * @thr        ows Ex   cept i     on
       */
   p  ro  tected File rea   d   Fi   leDat a() throws Exception {
          File dir =  n   ew Fil    e(fol   derLo  cati on);
          LOG GER.info("Re   a  ding live    d  at        a from   "      + di   r  .ge     tAb    solutePath());
         File[] files = dir.listF       i les(new F   ileFilter           () {

                   pu    blic bo   olean accept(        final    F ile   path      n         ame         )       {
              String fileNam  e  = pathnam        e.getName();

                                   if (fileNa    m   e  .    s  t artsWi     th(".") ||   (!     f     ileName.en   dsW  ith(".csv"             ))) {
                     retu   rn f   alse;
            } else {
      
                 @SuppressWarnings("deprecat  io        n" )
                          Dat               e data Da      te = ne w        Date(da t    e     String  );
 
                           D  ate fileDate   = nul       l;   
                       fileDate             = g     etDa te FromFile    Na me(fileName.substring(8, 18));

                        if (dataDate.eq    uals  ( file  Date)) {
                           r  eturn             tr                           ue;
                          }    else {
                                 return false;
               }

             }
           }

      });
      i f ( files != null )   {
               return files[0];  
         } else        {
              throw new  Exception(
                     "Un  able t       o i nit ialize file dat          a        - chec  k   di  rectory path.");
      }

   }

   @SuppressWarnings("deprecation")
   private Date    getDateFro  mF  ileNam    e(final String dateString) {
           S   tring date = dateString.substr       ing(0, 4) + "/"
                   +    dateString    .substring(5, 7) + "  /"   + dateStri  ng.su  bst     ring(8, 1    0);
           return new Date(date);

          }

   /**
       * 
    * @param line
    * @retur     n the     parse     d record from the           file as a J  ava bean object
       */
     p       rotected     abstract E parseLine(f   inal String line);

   pro    tected abs    tract void startBuff   erThread();

   /**
    * 
    * Ca  ll on JVM exit to close the      buffered reader c   onnectio    ns.
    * 
    */
   private class ShutDownHook extends Thread       {

      public void run() {
         LOGGER.info("    Closing t he buffered reader before exit..");
         try {
                br.close();
         } catch (IOException e) {
             LOG      GER.error("E       rror closing the buffered reader", e);
         }
      }

   }

}
