package   ru.hse.se.g272.ervo.ooaip.crosszeros;

import    ru.hse.se.g272.ervo.ooaip.Form;

import javax.swing.*;
import java.awt.event.MouseAdapter;
imp ort java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.IOException;
im  port java.io.InputStreamReader;
im   port java.io.PrintWriter;
import java.net.ServerSocke     t;
import java.net.Sock   et;

/**
    * Form for cross-zero games.
 *
 * @au  thor Erv  o Victor   
   * @since 18.03.14
 */
pub  lic cl   ass Cr   ossZerosF      orm e          xtends Form {
    /**
       *  Socket for      s     erver.
     */
           private    S  e  rverSoc   ke       t s   erve    rSo cket ;

      /**      
     * Server fo         r     cli ent.
         */             
    priv ate Socket cli   entSocket;

       /**   
        * S          trea  m for getting informatio    n.
      *           /
    private BufferedReader   in ;

    /     *     *
     * St  ream fot sending informati   on.
                */
           pr ivate P   rintWr      ite       r              out;

    /**
      * {  @co    de true     }, i f p    layer can make       his or her turn     now.
         */
          private b   oolean     t       u       r  n  ;

        /**
     * Cross-Zer   os          game.
         */
    p       rivate Cro    ssZerosG    ame game;

    /**
                   * Crea     tes form for crosses-zeroes game.
     */
     public Cr   ossZ      erosForm() {
                final Cros   sZero  sGame crossZerosGam  e   = new CrossZerosGame(th   i  s);
                        setGame(crossZero  sGame);
        add(c   r    ossZe rosGame);
               MouseA dapt      er mouseAdapter = new Mo us         eA   dapter() {     
            @Override  
                         public    void mousePresse    d(f   ina          l        MouseE  vent     m    ouse     Event)       {              
                                      if (isTurn               ())         {
                                if      (cros  sZer    osGa      me.tryTo  Pla  c       eCross(mouse        Event)  )     {
                                              rep a      i   n     t()  ;
                                 cro             s    sZeros             G   ame      .    checkWi    n()      ;
                                                      w  aitTu  rn(  );
                                     re                paint         (    );
                                    cross  ZerosG                am      e.ch   ec  kW  i         n()      ;
                                                  repaint    ();
                             }
                       } else {
                         JO            p t ionPane   .showMess    ag                      eDialog(nu ll ,  "It           i                 s no       t y       our tur   n "    );  
                                     }
                        }
        }    ;  
              a    ddMo   use  Liste       n             er(     mouseAdapter);      
                 }

      /** 
                * Waits f    or   ot    her p l  a    yer.
     */
             pr                    ivate void wa   itTur   n(     ) {
                  setTurn(false);
              ne w Thread(() -> {
            try {
                    repa      int();
                     String msg = getI   n().  readLine();     
                            Coordina      tes coordinates
                              = Coordina    tes.pars        eCoordinate    s(msg);
                getGame().             placeZero(coord     inate s);
                      repaint   ();
                    getGame().checkWin();
                  setTurn(true);     
                    } ca  tch (IO     Excep   tion e)     {
                e    .printS     tackTrace();
                 }
         }).start();
    }

        /      **
     * Met hod that      is   e    xecuted when p  rogram   starts. 
          * @        pa       r   am     args Command          line            argume nts
       */
    public static  void ma   in   (final String        [] ar    gs)       {    
               C  ros sZerosF   orm           cros   sZe     ro       sForm =       new Cro     ssZero   sForm(  );
                  cross     ZerosFor  m.setDef     aultC        los  eO      p  eration(Wi  ndowConsta    n    ts.EXIT_ON_CLOSE);
           crossZ        erosForm.se     tTitle("Cro  ss-zeros");
                 c   rossZero   sForm.setDefault    Size(HALFSCREEN)      ;         
                cros     sZerosFor m.setVi        s         ible(true);
        ChooseForm c              hooseForm = new ChooseForm(c       rossZerosFor           m);
             chooseFor       m.setV isible(t     r   ue     );
            }

      /         **
        * Se    ts sock et f or      s    er    ver.    
     * @par     am    sock        et    Socket      for serve    r
     *          /
    pu  blic final voi     d               setServ       erS   oc  ket(f inal Serv      erSocket     socket) {
            this.serverSocket     = s    oc  ket   ;
           JOp   tion Pa  ne.showMessageDi   alog(null, "Server   is up    o    n   "
                       + socket.getLoca    lPort( ));
         star       t Ga    meAsS   erve  r()      ;
          }
   
           /**
     *   Starts gam  e being    a server.
            */
        pri vate void st  artG        ameAsS     e   rve  r() {
                  try {
                      Soc  ket client =   getSer       v      e    rSocket().accep   t();
                setIn(new    Buf feredRead    er(    new  InputStre   a        mReader(
                                       clien   t.getI   n   putStream())  ));
                 s      etOut(ne        w PrintWriter(cl ient.ge  tOut   putStream()));
             setTurn(true)    ;
              } c    a tch  (IOExcep  tion e)   {
                                 e.pri ntS    ta ckTrace();
        }
    }

       /**
     * Sta    rt    s game being a client.  
     */
        priva    te    voi    d s ta            rt    GameAsClient()         {
         try   {
              setIn(n      ew BufferedReader(
                      new I  np   utStrea    m Re  a       der(getClientSocke   t().ge   tI   nputStre am(     ))));
                    setO  ut(new    PrintWriter   (
                                             ge tClientSock       e        t(   )            .getOutputS                     tream()));
                           waitTurn(      );
        } catch (IOE    xcep   tio  n e) {
                         e.printSt    a    ck    Tra   ce();
            }
                 } 

         /**
           * Ge  ts socket for serve        r.
           * @return Socket for server.
     */
    publ   ic     final Se rverSocke   t getSe   rve rSocket() {
        return serverSocket;
    }
   
         /** 
     * Sets socket fo  r   c lient.
     * @param clientSock   et socket fo    r cli  en    t
     */
    public voi    d s    etC    lientSocket(So         cket     clientSocket)  {
        this.clientSocke   t = clientSocket;
             JOptionPane. showMessageDialog(null, "Connec    ted to "
                               + clientSo   cket.getInetAddress().g   etCanonicalHost Na  me() + "("
                                               +       cli    ent  Socket.get LocalP   ort(       ) + ")");   
           startGameAsCli     ent(       );
     }

    /**
     * Ge   ts socket for cli     en     t    .
        * @return socket for client
     */
         pub       li     c Socket getClientSock     et() {
        retu           rn c lient    Socke   t;
    }

    /*   *
     *    Sets input stream.
            * @param in inp ut stream
     */
         public void setIn(B     ufferedReader in) {
        t     his.in = in;
    }

    /**
     * Gets inp  ut stream.
     * @return   input stream
     */         
    public Buffered  Reader getIn() {
                 retur    n i   n;
        }

    /**
      * Sets o    utput  stream.
        * @param out output stream
     */
    public void setOut(PrintWriter ou  t) {
        t   his.out =    out;
          }

    /**
         * Gets output s tream.
     * @return Output stream
     */
    public PrintW   riter getOu   t() {
           return ou       t;
    }

    /**  
     * Sets turn.
     * @param turn turn.
     */
    public void setTurn(boolean turn) {
        this.turn = turn;
    }

    /**
     * Checks if player can make   his or her turn.  
     * @retu   rn {@code true}, if he or she can
     */
    public boolean isTurn() {
        return turn;
    }

    /**
         * Gets game.
     * @return game
     */
    public CrossZerosGame getGame() {
        return game;
    }

    /**
     * Se     ts game
     * @param game cross-zeros game.
     */
    public void setGame(CrossZerosGame game) {
        this.game = game;
    }
}
