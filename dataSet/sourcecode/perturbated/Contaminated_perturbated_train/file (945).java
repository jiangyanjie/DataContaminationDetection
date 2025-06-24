/*
      * To c   hange    this template,     cho      os   e    Tools | Templates
 * and     open the template in the    editor.
 */
package student.gui;

import java.awt.event.Acti  onEvent;
import java.awt.event.ActionListener;
import java.rmi.Remote  Exc    eption;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.event.Ch   angeEvent;
import javax.swing.event.ChangeListener;
import         student.remot  e.client.Client;

/**
  *
    * @author Eileen L iu <el544@cornel   l  .edu>
           */
public class ControlPan    elInteractionHandler {

    private Interact ionHandler          maste  rController;
        //priv  a           te final WorldFrame view    ;
    Cont    rolP   an         el cp;
         private Timer rntm     r = new Timer("StepTimer  "        ,true);
    private TmrTsk rntsk;
         private int period;

    pu  blic    Co     ntrolPa  n  elInterac    tion       Handler(final Inter    actionHandler _parent){//final World _model, fina  l    WorldFrame _view) {
          mas  terController = _    parent;
        //view       =               masterController.g     etView()   ;  
        cp = masterCo ntroller.ge                 tV        iew().worldDisplay.        controls;
        boolean   a  dmin = masterControl      ler.    getAdmin() !  = null;
        /*cp    .rand       om.addActi onListener(new Actio    nListener(){
                                @  O    verride
             public        void   actio  nPe  rformed(ActionEvent     arg0) {
                                      master  Control    ler.getModel(   ).to   ggle            Wait(   );
                                              }
            })     ;
               cp.   wait.  ad   dActionListe  n         er(new    Act    ionLi     sten   e  r(){  
                      @  Override
             public void actionPe    rformed    (Act   i o   nE    vent arg  0) {
                       mas    terCo  ntr  oll e   r.      g   etM         odel().toggl  eWa it();
             }    
        });*/
         i     f(admin)
                           cp. runBut  to   n.addActionLi                      stener(new A cti onListener()       {
                             @Override
                    public voi     d actio   nPerfo rme   d(     Ac tion   Event arg0   ) {
                                         /  /rnts        k    .setRunn     ing(true);
                     rn   tsk   = new       TmrTsk(); 
                      p eriod =      cp.s  peedSlider.getValue();   
                                           rntmr.s  chedul  e    (rnt  sk,    0, period);      
                                                //TODO:    FIX, don't  sta             rt timer from zer   o, save cu       rrent time     and start from that
                                              maste  rCo  n     t     roller .ge    tView  ().worl dDis   play   .con  t   ro      ls.    run   Button.s etEnabled(fal   se);
                                 maste   rControlle     r          .g    etView().wor ldD      isplay.c  ontrols.stopButton.s    etEnabled(true);
                                                                 masterController.getV  iew().    worldDispla           y.   controls            .stepButt     on.se      tEnabled(fal  se);      
                                   //ma  sterControlle    r.  getModel() .toggle    Run();
                                          }
                     }  );
             els    e cp.ru  nButt                     on.setEna   bled  (fa       lse)   ;
        
        i f(admin)    
               cp.stopButton.addA      ctio   nList e       ner  (                new   A     cti   onLis   te ner(){       
                                  @Override
                    public       vo       id actionPe      rforme   d   (       Act             ionEvent arg0) {
                                                  r  ntsk   .cancel();
                                   rntmr.   pur       g    e(     );
                                  //rnt   sk.setR    unning(f     alse)     ;
                          master    Contr  o     ller  .getView()   .worldD          isp     lay.  c                      ont       r o   ls.stopButton.se       tEnabled(f   alse);
                       maste  rCo ntroller.getVi   ew().worldDisplay.        controls.stepButton.set            Enabled          (tru e    );
                                   m     asterC ont   roller      .     getVi  ew()   .worl dDisp la     y.c  ontr          ol s.runButton.s   etE      n    abled(tr   ue);  
                               /  /master  Controll   er.get  Model().    tog  gleRun   ()         ;
                                 }
              });
          e    lse  cp.stopButton.setEnab    led(       fal     s      e); 
        
              if(admin  )
                cp .st     epBu tton.a dd     ActionListen  e   r  (new              Act      io  nL          ist   ener(){
                            @   Overr  ide
                   public voi     d actio  nPerformed(Act  ionEven t arg0) {     
                            new T    m rTsk()     .run();
                                         }
                   });
         else cp.s  tepButton.s  etEnabled(false);     
                   
           if(admin)
                 cp.      s   pe         edSlider.addChang      e  Listener(new Cha     ngeListener(){
                      @Override
                          public void st                  at  e                   Changed(ChangeEvent e) {
                        rnt  sk.c     ancel();
                      rntmr.p   urge();      
                    //rntsk.   setRun  n     in g(fals    e);
                         masterController.getView().worldDi    s  play  .co  nt    rols.stopButto  n.setEnabled(false )    ;
                            masterCo   ntroller.getVie  w().worldDisplay.controls.stepB     utton.setEnabled(true)       ;
                      masterController.g    etView   ()      .worldDisplay.co   ntrols   .runButton.setEnabled(true);   
                          //masterController.  getMode     l    () .toggleRun();
                }
                   });
        else cp.speedSlider.setEn     able   d(false);
    }
    
    private class TmrTsk ext ends TimerTask {
        /*private boolean    runni  ng =   true;
        public v  oid setRunni  ng(     boolea   n b){
            running = b;
        }*/
        @Override
        public void run() {
            //if(running)       {
            try {
                 if (masterController.getAdmin()  != null) 
                    masterController.getAdmin().simStep(mast  erController.getLogin().getToken(), masterCon    troller.getLogin().getUser());
            } catch (RemoteException ex) {
                Cli ent.connectionError(masterController.getView(), ex);
            }
            masterController.getView()    .repaint(     );
            //}
       }
    }
}
