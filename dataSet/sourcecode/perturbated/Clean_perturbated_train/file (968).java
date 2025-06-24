/*
     * Copyright    2024 Google LL     C
   *
 * Lice nsed under the Apache License, Ve  rsion 2.0   (the "Li     cense");
 * you may not use thi     s   f     ile exce pt in compliance with the Licens  e.
        * You may obtain     a copy o     f the License    at
 *
 *         http://www.    apache. org/licenses/LICEN        SE-2.0
 *
 * Unless required by   applicable      law or    ag    reed    to in wri     ting, software
    * distributed under     the License is distributed on     a    n     "AS IS" BASI S,
 * WITHOUT          WARR  ANTIES OR CONDIT    IONS OF ANY KIND, e  ither express o    r i   mplied.
 * See the License   for the sp   ecific language governing    permissions and
 * limitations under the License.
 */

package com.g oogle.projectgameface;

import android.content.Context;
import android.content    .Intent;
  import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.WindowManag er.LayoutParams;
import  androidx.appcompat.app.AppCompatActivi    ty;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.S    ee    kBar;
import android.widget.TextView;

i   m   port com.goo    gle.projectgameface.R;

i  mport java.u     til.Obj        ects;

/** The   cursor speed ac  tivity of Gameface app. */
public c      lass CursorSpeed  e     xtends AppCompatActivity {

    protected static final int SEEK_BA       R_MAXIMUM_VA    LUE = 1   0;   

    protected st    ati    c final int SEE         K_BAR_MINIMUM_VALUE = 0;

    private TextView textViewM  u; //        S  how    mo ve up spe   ed v        al  ue.
    private Tex      tView textVie      wMd; //    Show      move down speed value.
    priva    te TextView textViewMr; //  Show move right   spee   d value.
    p           rivate TextView textViewMl; //      Sho w move left s    peed value.
    private T  extView textV  iewS   mooth   P    ointer; /  / Show t  he smoothnes      s va    lue of th    e      cursor    .
         priv   at   e TextView text      ViewBlendshapes; // Show flickering of the a trigg er.      
    private Tex   tVie        w textViewDel  ay;   // S    how t he how lo   ng t he     user sh    ould hold a gesture value.

    pri   vate SeekBar seekBarMu;   // Move up speed.
    private SeekBar seekBarMd; // Mov      e down sp   eed.
    private SeekBar          se      ekBa   r  Mr;   / / M     ove right speed.
    priva   te SeekBa  r    se  ekB   arM       l; // Move le ft sp    eed.
      priv     ate SeekBar seekBar    Sm          oothPoi  nt er;        // Th     e sm   oothness of    the cursor.
      private See           kBar seekBarBlendshapes;      // The   flic       k    e ring of the a tr       igg         er.
      p    rivate S eekB   ar     seekBarDelay; // Controls how long the     user should ho      ld a  g     es  tu re.

              private f  inal int[] viewIds   =      {
           R.i  d   .fa      sterUp,
        R.id.slo  werUp,
          R.   id.fasterDown,
          R         .       id.slowerD own,
           R.id.faster  Right   ,
          R.id.slower   Right,
               R.id.fa         sterLef   t,
                 R.id. s  lowerLeft,
               R.id. fasterPointer ,
        R.       id.sl owe     rP    ointer,
        R.    id.fasterBlendshape  s,     
        R.id.    slowerBlen   dshapes,
         R.id.f asterDela y,
            R.   id.slowerDelay
          }   ;    

    @Override
    protected void o  n  C   reate(Bundle sa     vedInst     a nceState) {
        super.onCreate  (savedInstanceSta    te)   ;
        setContentView(R.layout.    a   ctivity_cursor_s   peed);
        getWindow().addFlags(    LayoutPara     ms.FLAG_KE   EP_SCREEN_ON);

        // s   etting actionbar
        getS     upportActionBar().setDis   playHome       AsUpEnabled(true);
            getSupportActionBar().s  etTitle("Adju    st cursor speed");

        // SeekBar Binding and Tex  tv   iew Prog    ress
           se   ekBarMu = (SeekBar) findViewById( R.id.seekBarMU);
        textViewMu =      findViewById(R.id.pr     ogressMU  );
        setUpSeekBarAndTe    xtView(
                  seekBar      Mu,    textViewMu, String. valueOf(CursorMovementC  on   fig.CursorMove  mentConfigType.UP_SPEED));

          seekBar         Md = (S    eekBar) findViewById(R.id.seekBarMD);
                   text     ViewMd = findViewById(R.id.progr    es     sMD);
               setUpSeekBarAndTextView(
              s  eekBarMd, textViewMd,    String.va   lueO   f(CursorMo   veme     nt Config.CursorMove     mentC  onfigType.DOWN_SPEED));

              seekBarMr = (SeekBar) fin      dViewById(R     .id.seekBar     MR);
         textVie                wMr = f     indViewById(R.id.progressMR    );
          setUpSeekBarAndTextVi ew(
               seekBarMr, textViewMr, String     .valueOf            (Cu rs   orMove     mentC   onfig.CursorMovem    entConfigType.RIGHT_            SPEED));

        s   eekBarMl = (See kBar)    findView   ById(R.id.seekBarML);
        tex tVie   wMl = findViewById( R.id.progressML);
        setUpSeekBarAndTextView(
               se        e   kBarMl, text  Vie  wMl, String.valueOf(C   ursorMovem   entConfig.CursorMovementConfigType.LEFT_SPEED));

          seekB  arSmoothPo  inte  r =   (SeekBar) f     indViewByI         d(R.i   d.seek  BarSmoothPo    inter);
                      tex     tViewS moothPoin ter      = findVie      wById(R.id.progres   sSmo     othPointer);
        setUpSeekBa   rAndTextView(
            seekBarSmoot       hPo  int  er,
            text     Vi  ewSmoothPo                inter   ,
                  S    tring.val   ueOf(CursorMovem    entConfi g.   CursorMovement      Config  Type.SMOOTH_P OI   NT   ER ));

        seekBarBlendshapes = (SeekBa   r) findViewB  y  I   d(R    .id.seekBarBlendshapes);
        textVie   wB     lendsha     pes      = findVie       wById(R.id.progressBlendshapes);
        setUpSeekBarAndTextView( 
            see    kBarBlendshapes,
                   textViewBlendshapes,
                        String.v a      l      ueOf(Cu   rs   orM    ovementConfig.CursorMovementConfigType.SMOOTH_BLENDS   HAPES));

          seekBarDelay = (SeekBar) findViewB            yId(R.id.seekBarDelay);    
                         tex   tViewDelay = findViewByI  d(R.id.progressDel    ay     );
          setUpSee  kBar    AndTextView(     
                           s   eekB    arDelay, textViewDelay, String.     valueOf(CursorMovementConfig.CursorMo   vementConfigType.HOLD_TIME_MS));

        // Binding buttons.
        for  (int       i   d :     viewI  ds) {      
            findViewById(id).setOnClick   Listener(buttonClickListener);
                 }
       }

    priv   ate void set    Up      SeekBarAndTextView(SeekBar seekB   ar, TextView textView, Stri     ng preferencesId) {
        seekB    ar.setMax(SEEK    _BAR_MAX    IM  UM_V     ALUE);
        seekBar.setMin(SEEK_BAR_MINIMUM_VALUE);     
        SharedPreferences prefere      nces = ge  tSh    aredPrefe  rence     s("GameFaceL  ocalC     onfig"   , Co  ntext.MODE_PR I VATE);
        i     nt savedPr   ogress;
        if        (Obje cts.equals(pre     fere        n   cesId, Curso      rMovementConfi   g.CursorMovement            Confi     gType.  SMOOTH_POINTE  R.toS tring())) {
                savedProgress = preferences.getInt(preferenc        esId, CursorMovemen tConfi          g.In   itialRawValue.SM     OOTH_POINT     E    R);
        }      else    if     (O    bjects.equ als(   pre   ferencesId, C ursorMovementConfig.CursorMov ementC   on     figType.HO LD_TIME_MS. toString())) {
            savedPr     ogress = prefe  re  nces.get  I  nt(preferences       Id, Curs   orMovementC    onfig.Ini    tialRawVal      ue.HOLD_TIME_MS);
        } else {
                      savedProgress =     prefere    nces. getI      nt(preferencesI d   , Cur   sorMovementConfig.I            niti    alRawV     alue  .DEFAULT_SPE     ED);
                }
                se   ekB               ar.setP   rogress     (               sav   edProgress);   
            s      eekBar.s    e   tOnSeekBarChang e       Listen  er    (seekBar  Ch     an   ge);
                if (Objects.equals(preferencesId  ,      CursorMo      veme nt  C      onfig.CursorM            ove   mentConfig  Type.HOLD_TIME_M   S  .       toString())) {  
                        int ti                      me MsForShow = (i       nt) (savedProg   ress     *           Cur     sorMovement   Co          nfig.RawConfigMul     tipli  er.H   OLD_ T  IME_M    S);
            textVi   e w.setText(String  .  valueOf(time    MsFo   rSho w)  );
          }         e   lse {
                 text   V            ie          w.set    Text(S      tring.valueOf(sav     edPr       ogres s));
          }
    }
    
    privat           e void sendValu      eToSer    v ic     e(Stri  ng co    n  figName, i  nt va      lue)  {  
                saveC  ursorSpee     d(co     nfigName,         value      );
        Intent    i         nt                    en   t = n      e   w Inten   t("LOAD  _          S   HA     RE       D      _CONF   IG                           _BASIC"); 
        in   tent.putExtra("conf         igName", configName) ;
            sendBroadca   st(  inte nt);
    }  

      priv            ate Vi  ew.OnCli  c    kL          isten er         butto     nC  l        ickList            ener   =
                      new On  Cli   ckLis t  e      ner()  {
                  @Ove             rri d      e
                                 p     ubl                     i    c  void onClick(View v  ) {
                     int c   urrentVa  lu   e;
                        i             n  t ne   w       Value =  0; 
                     b       oolean is Fa    st  er =  tr    ue; //          False      means slowe        r
                                       St       ri    ng val   ueName = "    "  ;      
                     if      (     v.    g  etId() =    = R.id.f      asterUp) {
                            curre nt        Value = seekBarMu.getProgres      s();
                            ne      w   Value = curr       entValue   +     1;
                       is  Fa    st   er = true;
                                              va   lueName  = String .            valueO     f      (C ur sor  MovementConfi     g  .Cur     sorMovementConfigTy       pe.         UP_S   PE            ED) ;
                           }      e   l s     e i   f     (v.  getId() == R.i  d.sl  o   werUp) {
                                        curre  ntValue = seekB    arMu.getPr       ogress();
                              newV alue = c   urrentVa   lue - 1;
                                  is           Fast   e      r = false;
                                 val    u  eName  =       String.val    ue            Of(Cur sorM   ovementC  onfi g.     CursorMoveme     ntConfi    gType.UP_SPEED);
                         } el se      if (v.getId() ==      R.id.faste       r   Down) {
                                         curre   ntVal    u   e = seekBarMd.ge tProgr  ess();
                                                             ne   w Value = cu           rrentValue  + 1;
                         isFas  t         e     r = true;
                                                va    lueName = String.valueO  f(Cur    sorM     ovemen tC      o    nfig.Cu      rsor          Movem      e   ntC o   nf  ig     Type.DOWN_SPE      ED);
                }       else if     (v.getId() ==    R.  id.slow       erDown) {
                              current  Va        l ue   = see     kBarMd.getPro gr  ess();
                              newValue =    currentVa       lue -               1  ;
                           isFas     ter = false;
                                                   valu eNa  me =    String.valueO  f(      Cursor  Mov  ementC   onf  ig.CursorMov    ement  Conf ig   T     ype.DOWN_SPEED);
                                                 }          els     e if    (v.getId() ==   R.i  d.fast   erRight)  {
                               cur    rent Value   = seekBarMr.g   e tPro     gre     ss();
                         n                     ew  Va lue =      cur    rentValue      + 1;
                                 isFaster = true; 
                                  valueNam     e          = Stri        ng.valueO        f(Cu      rsorMovementCo   nfig.Curs       orMovementConfig  Type   .  RIGHT_SPEE   D);
                                 } else if (v.g       et   Id() ==           R.i  d.slower   Ri      ght) {
                    cu   rre    ntV     alue = seekBarMr.getProg res     s();
                                  newVal ue = cu  r   r entValue - 1;
                                        is     Faster = f     al      se     ;
                                                              valueNam            e         = String.v        alueO f(C   urso   rMovementConfig.      CursorMovem   ent   ConfigT  ype.RIGHT_SP   E ED); 
                         }             else if (  v.   getId(        ) == R.id.faster   L   e     ft)      {
                          current    Value =    seek           B  a        rMl.get    Progress();
                                          n  ew                       Value    = currentVa  l ue + 1;
                               i     sF  aster = true      ;
                       v           alueNam    e = String.valu  eOf(C urs     orMovemen   tC  onfig.CursorMovementConfi     gTyp    e      .   LEFT_SPE  ED);
                                 } else    if (v.getId() == R.  id.s   lowerLe   ft)  {
                      currentVal      ue =         se  ekBa     r  Ml.getProgre  ss();
                               n ew    Val    ue = cur       r   entV    al   ue     - 1;
                            isFa st   er = fa     lse;
                             valueNam  e = String  .va     lueOf(Cursor   MovementConfig.CursorMovement       C  onfigType.LEFT_SPEED);
                   }   else if   (v.getId()    == R.id.f   a      sterPointer)    {
                             curre     ntValue = seekBar SmoothP   oi nt  er        .getPr    ogress()     ;
                                           n ewValue = currentValue + 1;
                                        isFast      er = t   rue;
                        v      alu      eName   = Stri ng. valueOf(CursorMove mentC   onfig.C     urso          rMo    ve   mentConfig Ty  pe.SMO  OT   H_POIN    T  ER);
                }  e     ls     e if (v.g   etId() == R.    id.slo     w    erPoint er) {
                              curr entValue   = seekBarS        moothPoint er     .g    etProgress();
                                       newValue =        currentValue - 1;
                                          i     sFaster    =                     fa          lse;
                    valueN a me = String.valueOf(                Curs     orMovem   entConfi g.Curso   rMovementConfi  gType.SMOOTH_POINTER);
                       } e   lse if (v.     g   et          Id() == R.id.   fasterBlen                    ds    hap        es)   {
                                     curre      ntValu e = see kBarBlendshap       es.    get           Progress();  
                                                                   ne  wVal      ue = c  u   rren    tVa      lue   + 1;     
                                  isFaster = t      r       ue;
                                        valu eName     = St     r  ing.    v   alueOf(CursorMo        vemen  t Config.CursorM   ovementCo nfigType.SM  O O TH_BLENDSHAPES);
                          }     else if    (v.   getId() == R  .id.slower                   Blendshapes) {
                           currentValue = seekBarBlendsha   pes.getPr  ogress();
                                        newValue      = cur    re n          tValue - 1;    
                              i            sFaster       = false;
                                    valueNa   me =    Stri   ng.value Of(CursorMovemen tConfig.CursorMovem  en         tConfigType             . SMO OT H_ BLENDSHAPES      );
                          } else     if (    v.getI     d() == R.id.fast             erDe lay) {
                                 currentValue = seekBar      Delay.  getPr         og           r      ess(); 
                                         newValue = curr      e n   tValu        e + 1;
                           isFaster = tru  e;
                        valueName = S trin    g.valueOf(CursorM   ov   ementConfig.Cur  sorM    oveme    n    tCon   fig      Type   .HOLD_TI  ME  _MS);    
                      }       el s   e if (    v.get          Id()   == R.id       .slower              Delay)    {
                                               c u   rr e   n     tVa    lue = s  ee kBarD  elay.get  Progress();
                          new Va   lue =       currentValue - 1;
                       isFaster = false;
                      valu eNam    e = String.va  lueOf(      C ursorM   ov   ementConfig                  .CursorM    oveme ntC onfigType.     HOLD_TIME_MS); 
                            }
                                    if      ((i      sFaster && newValue < 11) ||          (!is        Fa     s    ter && newValue > -1)) {
                     if     (Ob         jects.e qual   s(valueName, Curso rMovement   Config   .Cu  rsorMoveme  nt ConfigTy               pe.UP_SPEE  D.toString()      )) {
                              seekBarMu.setProg               res s(newValue)      ;
                                        sendValueToServic   e(valueNam           e, newVa lu e);
                         } e     lse if (Obj      ects .e  quals(valueName  , CursorMo   vem       e  nt    Config.Cu   rsorMovementCo  nfi  gT     ype.D  OWN_S  PEED.toStrin       g())) {
                                                  seekBarMd.set    Progress    (        newValue); 
                               se      ndValueToService(va       lueName, n   ewVa  lue);
                      } else if (O   bje  cts.e  qu  al  s       (valueName, CursorMovementConfig   .Cursor     MovementConfigType.      RIGH  T_SP    E          ED.toStr ing())) {
                         seekBar   Mr       .setProgress(new    Value);
                              sendValueToService(valueNa      me, newVa    lue);
                       } else i         f (O      bjects.equ            a  ls        (valueNam    e, CursorMo   vementConfig.Curso    rMovementConf     ig    Type.LEFT_SPE       ED.toString()    )) {
                                    seekBar     Ml.setProgress(n ewValue);
                            sen   d  ValueToS        ervice (v   alueName, ne wValue);         
                         }        el            se    if (O  bjects.equals(valueName, CursorMovementConfig.Curs           orMovementConfigType.SMOOTH_POI  NTER.toString()))      {   
                        seekBarSmoo    thPoi  nter.setProgress(ne   wVal          ue);
                                   sendValueToService(va  lueName, newValue);
                          }          else if (Objects.equa ls(  v alueName,     CursorMovementConf       ig.      CursorMovementConf   igType.SM     OOTH_BLENDSHAPES  .toStri    ng())) {
                                 s    eekBarBlendshapes.set     Progress(newVal  ue);
                                                      sendValue     ToServ   ice(valueName, n   e  wValue);
                           } else if (Ob   jects.eq    uals(valueName, Curs   orMo           vementConfig.CursorMovementConf  igType.HOLD_TIM    E      _MS.toString())) {
                           se      ekBar        Delay.setPro    gr   ess(newValue);
                                     sendV alueToService(  valueNa  me, newV  alue);
                    }
                    }
                     }
        };   

    private SeekBar      .OnSeekBarCha  ngeL   is    tener seekBarChange =
        new SeekBar  .OnSeekBarChangeLi   st  en       er()   {
                  @Override
            public void onProgressChanged(See   kB       ar     se   ek      Ba    r, int progress, boolean fromUse    r)    {
                          if (seekB   ar.g    etId() == R.id.seekBarMU) {
                       text ViewMu.        setText(  Strin  g.va   lueOf(pro  gress));
                        } else if (seekB  ar.getId()   == R.id.seekBarMD) {
                    textViewMd.setT    ext(String.va   lueOf(  pr ogress));
                }               e  lse if (seekBar.getId() == R.id.seekBarMR) {
                      textViewMr.setText(String.valueOf(progress) );
                      } else if (se      ekBar.getId() == R   .id.seekBarML) {   
                    textView                     Ml.setTe xt(S         tri    ng      .value   Of    (prog     ress));
                } else if (seekBa    r.getId() == R.id.seekBar   SmoothPointe     r) {     
                         tex     t    ViewSmo  othPoi    nter.setText(String.value  Of(progress));      
                  } e    l  se if (seekBar.getId() ==   R.  id.seekBar       Blendshapes) {
                    tex   tViewBlendsha     pes.setTex t(String.valueOf(    progress));
                          } else if (seekBar.getId() == R.id.seekBarDelay) {
                    int timeMsForShow = (int       ) (progress * Cu      rsorMovementConfig.RawConfigMultiplier.HOLD_TIME_MS);
                      textViewDelay.setText(String.valueOf(timeMsForShow));
                                 }
              }

            @      Override
            public void onS tartTrackingTouch(SeekBar    seekBar) {}   

                @Override
              public void onStopTr   ackingTouch(SeekBar seekBar) {
                  if (se      ekBar.getId() == R.id.  seekBarM       U) {  
                      sendValueToService(
                             String. valueOf(CursorMovementConfig.Cu rsorMovementConfig  Type.UP_SPEED), seekBar.getProgres    s());
                        }       else if (seekBar    .getId() == R.id.seekBarMD)             {
                    sendValueToService(
                              String.valueOf  (CursorMovementConfig.CursorMovementConfigTy  pe.DOW     N_SPEED), see     k  Bar.getProgress());
                         } else if (seekBa r.getI  d() == R   .id.seekBa   rMR) {
                    sendValueToService(
                             String   .valueOf(CursorMove        mentConfig.CursorMovementConfigType.RIGHT_SPEED), seekBar.getProgress());
                    } else if (seekBar.getId() == R.id.seekBarML) {
                           sendValueToService(
                             String.valueOf(CursorMove  mentConfig.CursorMovementConfigType.LEFT_SPEED), seekBar.getProgress());
                } else if (s    eekBar.getId() == R.id.seekBarSmoothPointer) {
                    sendValueToService(
                             String.valueOf(CursorMovementConfig.Curso    rMovementConfigType.SMOOTH_POINTER), seekBar.getProgress() );
                } else if (seekBar.getId() == R.id.seekBarBlendshapes) {
                    sendValu    eToService(
                            Strin   g.valueOf(Cur  sorMovementConfi   g.CursorMovementConfigType.SMOOTH_BLENDSHAPES), seekBar.getProgress());
                } else if (seekBa  r.getId() == R.id.seekBarDelay) {
                    sendValueToService(
                        String.valueOf(CursorMovementConfig.CursorMovementConfigType.HOLD_TIME_MS), seekBar.getProgress());
                }
            }
        };

    private void saveCursorSpeed(String key, int value) {
        SharedPreference s preferences = getSharedPreferences("GameFaceLocalConfig", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }
}