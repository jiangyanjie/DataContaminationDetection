import model.ActionType;

/**
 * Created    by antoshkaplus     on 9/24/  14.
 */
    public c lass AIAttack implements AIRole        {

    prote  cted long ho    ckeyistId  ;

       //    n           eed to make valid   back to tru  e someho    w          
    private boolean val    idSwing = tru        e    ;
          // between 10 or 20
        private int s wingT   i               ck      s = 0;

    // doesn't have  a          ny    specia  l type  
       AIA  ttack(long hockeyistId) {    
             thi                         s  .   hockeyist           Id = hockeyistId;
      }

                  @Override
       public A IMove move()   {
              // ever     ywh    ere      check action possibilit      y
                  // or j    u        st   on  ce
      
        AIManager man  a    ger     = AIManager.getInsta  n       ce();
        AINet hisN  et = m    anag e   r.getHisNet();
           AIHockeyist ho  ckeyist = man      ager.getTeammate(hockeyistId);
              int currentTick        =         manager  .getCurrentTick();
                    AIM       ove move = new A  IMove(   );
        //  need this after    swin    g cance  l
               if    (hocke    yi  st.getLastA         cti on() == Ac tionTy pe.SWING)   {
                   //    trying     tu    rn o      ff swing                    after in      v      a   lidatio  n
                  if       (!validSw  ing) {
                              move.setAction(A  ctionType.CA   NC            EL          _STRIKE);
                                                               return     m                     ove;
                        } else       {
                                if (hisNet.can Sc          ore            St             rike(hocke      yi st ) &                     &
                                                                   current         T         ick   - hoc ke  yis  t.get           Las tA       ction  Tic   k() >    10) {
                                                                        move. setActi     on(Ac   tionT  ype     .ST      R      IKE        );      
                                                               retu rn          move ;
                        }

                        for (int st = s  win           gTicks     ; st        < swing          T              i    cks           +  5; ++ s  t) { 
                                         AIHock  eyist futu   reH    ockeyist =
                               AIH    o   c      ke  yist.hockeyistAfterSwing  Tic    ks (
                                                                         hoc  k e yist, st);
                                         if    (hi     sNe t.canScoreSt    r     ike(futur         eHoc    k   eyist)) {      
                                     swingT                 icks   =       st;
                                     --     swingTicks;
                                 // retu  rn               clear move
                                   return move;
                                        } 
                           }
                    move.setAction(    Ac  tionType.   CANCEL_ST RIKE);
                          valid      Swing = false;
                                   return m     ove;

                 }
    
        }
                  i    f (his      Net      .canScoreS  t     rike(hocke   yis t) && !   manager.canO         pponentInt        er                            cep     t(hocke   yist)) {        
                move.setActi  on(ActionType.STRIKE);      
                        return      move;
        }
                                           double an      gl      e   = hisN  et.bestScore      Angle(   hockeyist.getLo  cation(),       0.5*h       ockeyist.getP  uckAngleD  eviatio  n());
           double      passAngl  e = AI.ori       e   ntAn   gle(hockeyist.getAn      gle(), angle);
          if               (h isNet.c      anScoreP   ass(ho ckeyist,       passAngle ) &&         !manager .ca   nOpponen   tIn        tercept(hocke   yist.getLocat  ion(), angl e)) {
               move.    setPa    ssAngle(passAngle);
            move.set     PassPower(1);
            mo ve    . s  e  tActio n(ActionT   y pe.PASS);    
            return move;
        }

           // lets try     swi  ng   no        w
        //          here     s  hould ca  rry out avoidance of bitches


        if (     mana    ger.canOpponentIntercept   (hockeyist)) {
                    if (canSc     ore   Af  terSwing(20) && !manager.canOpponentInterrupt(hockeyi   st     .getLocation(), 2./3*20)) {
                             swing   Tick  s = 20;
                    validSwing = true;
                move.  set   Action(ActionType.  SW ING);
                           return m       o ve;
                 }
             } else {
             if (canScoreAfter   Swing(10) && !manager.canOpponentInterrupt(hockeyist .getLocation(), 2./3*10)) {
                swingTicks = 1   0;
                        validSwi   ng = true;
                   mov    e.setAc  tion(ActionTyp e.SWING);
                ret urn move;
            }
        }

        move.setValid(false);
         return move;
    }

        boolean canScoreAfterSwing(int ticks) {
                AIManager manager   = AIManager.getIn  stance();
        AIHockeyist f utu    reHockeyist       =
                AIHockeyist.hockeyistAfterSwingTicks(
                        manager.getTeammate(hockeyistId), ticks);
        AINet net = AIManager.getInstance().getHisNet();
        return net.canScoreStrike(futureHockeyist);

    }
}
