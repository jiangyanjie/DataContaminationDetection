/*
 * Copyright 2024 Google      LLC
   *
 *  Licensed u nder the Apache License, Version 2.0 (the "License"); 
 *       y  ou   may not use th   is      file except i    n compliance       with the Lic ense.
 *  You may obt   ain a copy                 of the License at
   *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
      *     Unless required by applicab      le law or a gree  d to in writing,  software  
 * dist  ributed under the Lic ense is di stributed    on an "AS IS  " BASIS,
 *    WIT HOUT WARRANTIES OR CONDITIONS OF ANY KIND, eithe  r e        xpress or implied.
 * See the License for the speci fic     language gove rning permissi   ons and
 *  limitations under   the License.
 */

package com.google      .projectgameface;


import android.content.Context;
import android.content.Intent;
imp    ort android.content.SharedPreferences;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import androidx.activity.OnBackPressedCallback;
import android.view.WindowManager.LayoutP   arams;
import androidx.ann   otation.NonNull;
import androidx.appco   mpat.app.AppCompatActivity;
import android.vie w.View;
import android.widge   t.Ima geView;
import an  droid.widget.Te  x     tView;    


/    ** The cursor  binding activity of Gameface app. */
      p   ublic class CursorBinding e   xten    ds AppCompatAc  tivi ty {

    pr    i vate static fina              l String TAG = "GestureSizeActi       vit   y";          

 

    private final       in      t[] viewIds = {
                R  .i    d.ta pLayout,
               R.id.hom eLayout,
              R.id.b    ackLayou     t,
              R.id.notificat  ionLay  out,
             R.id.allApp  Layo      ut   ,
        R.id.paus     eLayout,
        R.id.resetLayout,
        R.id.d   r   ag        Layout
              };


          Text   View tex    tTa    b;
    Te   xtV iew            tabT    xtLinear ;
    TextView textHome;
    Text View h    omeTxtLinear ;     
    Text  View t    ex  t Back;
    TextView backTxtLi    near ;
             TextView   te xtNotifica  t    i on ;
    T  extView notificationTxtL   in   ear ;
             TextView textAllApp;
     TextView allA ppLinear;
      Te     xtView textPause ;
          TextView pauseLi      ne     ar ;
    TextView tex                tReset;
               TextView re se    tLinear       ;
       TextView te  xtDra g;
    TextView dragLi  nea   r;


    p   rote    cted String ge    tDescriptionT   extViewVa  lue(  ) {
        TextView descriptionText View = findViewById   (R.id.tapTxt  Linear);
           return desc    ript  ionTextVi        ew.getText().toString();
    }

    private void setUpActionLis  t(
        St  ring   pr   efer       e n          ce  sI   d,
        TextView textVie w  Ac  tio    n,
               TextView     textViewStat    us,
           Image  View statusImage) {
          SharedPreferenc     es preferences = getSharedPreferences("GameFaceLocalConfig",             Context.MODE_P           RIVATE);

        // Load           config      f        rom lo  cal sharedpref.
        Blendshape         EventTrigg    erConfig.Blendshape savedBlendshape =   B     l      end   shapeEventTriggerCo nfig.BLENDSHAPE_FROM_   ORDER_I   N_UI
            .get(   preferences.getInt(pref  erence  sId,
                    B   lendshape    EventTr     iggerConfig.B   L         ENDSHAPE_FROM     _    ORDER_IN_UI.     i      ndex   Of(
                Blend    shape    EventTriggerConfig.Blen    dshape.NO NE         )));

             String addTxt = "A   dd";
                 String    editTxt = "Edit";
         String    beautifyBlendsha  peName = BlendshapeE  ve   nt   Trigge    rCon    fig.BEAUTIFY_B   LEND  SHAPE_NAME.get(savedBle   ndshape);
           text  ViewAction.s      etText(beau  tifyB      l   end   shapeNam      e);



           // Set               to "No binding" if not fo  und.
        if (sav edBlendshape ==              B     lendshapeEven          tTrig    g       er  Co  nfig. B    l     en  ds   hape.NONE )    {
                         statusImage.setBackgro       undResource(R.drawable        .pl   us);
            text    ViewSt at us.se        tTex   t(addT   xt);
          }      else           {
               st    atusI      mage.set  Backgrou ndR         es   o    urce( R.drawable.      outline_edit_    2    4);
                            textViewStatus.setText(editTx     t);
                   }
    }

       p  rivat   e v   oi       d refreshUI()
    {
          L  og.i(TAG, "refreshUI")    ;
            setU  pActionList(
                 S tri    ng.valueOf(BlendshapeEve   ntTr   iggerConfig.Ev  entType.CURS OR_TOU    CH),
                        tex tTab,    
               tabTxtLi   near,
                        (ImageView)    findViewById(R.id  .     tapIcon)             );


        set  UpActi onLis t(
                 String.  valueOf(Blendshape     EventTr   ig    g erConf    ig.Eve         ntType.HO      ME),
                  textHome,
                    home Txt Linear,
                           (ImageView) findViewByI   d(R.id.homeIcon));    


        s   etUpA    ctionList(
                      String.valueOf(Bl    e  nd  shapeEv entTriggerConfig.E  ve        ntTy   pe.B    ACK),
            t ex  tBack ,
            backTxtLinear,
                 (Ima     geView     )     find    ViewById(R.id.backIcon));


              s       e   tUpActionList   (
                   String.valueOf (B  lendsha peEventTriggerConfig.Even   tTyp  e .     SH  O   W_   NOTIFICATION),
                 te   xtNotifica  t  ion,
                    noti          ficationTxt  Linear,   
            (   ImageView) findViewBy  Id(R.id.notifica tionIcon));


        setUpActionList(
                   Str       ing.valueOf(B       lendshapeEventTrigger               Config.Even tTyp     e.SHO W_APPS   ),
                   tex          tAllApp,
                       allAppLinear,
                   (Imag   eView) findViewById  (R.id.allAppIcon));
         / /   Androi    d version < 1    2 n  o    t support A LL_ APPS action.
        if (VERSION.SDK    _INT < 3 1) {
            fin dView ById(R.id.allAppLayo  ut)  .s   etVi   sibility(View.GONE     );
                        }


         set      UpActi    on   Li       st(
            String   .valueOf(Bl endshapeEventTrig      gerConfig.EventType.CURSOR_    PAUSE),
                te      x  tPause,
            pauseLin        ear,
            (ImageView) findViewById(R.id.pauseIcon)   );


        setUpActionList(  
             String.value Of     (BlendshapeEve      ntTriggerCo   nfig.Ev   entType.CURSOR_  RES     ET),
                  textReset,
                    resetLinear,
                            (ImageView) find     ViewById(R.id.resetIcon));


              setUp  Ac     tionList(
                              String.va lueOf(Bl endshapeEventTr    igger   Config.EventType.D   RA     G_TOGGLE)       ,
                 textDrag  ,
            d   ra       gLinear,
               (ImageV   iew   ) findV            ie  wById(R.id.dragIcon));

    }


    @Override
       protected void onCr   eate(Bundle savedInstanceState) {

            super.onCreate(       savedIns t   anceState);   
         setConten  t    Vi     ew(R.la     y              out.activity  _cu  rsor    _binding);
        getWindow().addF     lags(L           ayo    u  tParams.FL    A   G_KEEP_SCREEN_ON);
        /      / se    tt  ing a      ct    ionbar
        getSupport   ActionB  ar(   ).setDisplayHomeAsU     pEnabled(   true)       ;
                           getS        upp     or     tAction            Bar().set        Title("S  et up        gestu  res");

        textT                 ab = f               i  ndViewById(R  .id         .t        apBin   ding );
        t  abT xtLinear = fi     n    dViewById(R.id.tap     TxtLinear);
        te    x t         Home = f          indVie  wById(R.   id.h  omeBinding    );
              ho   me TxtLi near = findViewB     yId(R.id.h   omeTxtLinear           )   ;
        textB ack =      f                 indV  iewById(R.  id.backBinding);
                         backTxt                   Lin    ear = findV      iewById(R.id.ba                c   kTxtL   inear);
                     te      xt  N             ot  ificat   ion   = fin   d     View  B y    I           d(R.  id.notificationBinding);
        notif    ic    ationTx                         tLi    near =   findViewById(R          .  id.notificationTxtL  inea      r);   
                  textAllApp = findViewById                  (R.id.allAppB  inding);
           allAp     pLinear   =  fi  ndViewB yId(R.id.allAppLinear);
          textP  ause = fi                ndViewById(R.  id.pauseBindin         g         );
             pa        useLinear = fin  dViewByI d(R.id.pa useL   inear);   
             textRes        e   t =    fi    ndViewById(R.id.r       e  setBinding);
         re  s        etLinear =  find  View    B    y  Id(R.id.resetLinear);
                      textDr    ag = findViewById(R    .    id.dr agBi  ndi    ng);
        dragLinear    =  fi  ndV                 iewById(R.id   .      dragLinear);  

                   re   freshUI();

   
               for (int   id : viewIds) {  
                findViewById(           id)
                        .se  tOnClickListener(
                                   v -     > {

                              //          St   art intent correspon ding      to  each    event action    ty   pe.
                                                                                I  nt     ent intent = new Intent(getB  ase     Context(  ),
                                            Ch     oo       seG    est ureActiv    i   ty.class);
 
                        i f (v.getId  () == R           .id.tapLayout) {
                                    i      ntent.putExtra("eve ntType", Blends   hapeEven     tTri g    gerConfig.EventTyp   e         .CURSOR_TOUCH);

                           } else if (v.g  etId()    == R .           i    d.ho  meLayout) {
                                              i  ntent.p      utExtra(  "even      tType", B   lendshapeEventT   rigge   rConfig.Eve   ntTy     p      e.     HOME)      ;

                        } el         se if (v.g   etId(             ) == R.id.backLayout) {
                                                 intent.putExtra("eve   ntType", Blendsh   apeEventTri       g       ger      Co   nfi                g.EventType.BACK)      ;

                        } else if     (v.ge  tI    d() == R.id.    notif   i  cationLayou    t) {
                                     int  ent.putExtra("eventType", Blendshape  EventTr   iggerConfig.EventType.SHOW_N          OTIFI    CATION);

                                   }    else if (v.g    e  tId() == R.id.allA  ppLayout) {
                                  intent.putExtra("ev e nt   Ty   pe", BlendshapeEventTriggerConfi    g.Ev  en  tType.SHO         W_A        PPS);

                                   } else if (v.getId() == R.id.pauseLa   yout) {
                            intent.putExtra("eventType", B  len      dshapeEven     tTriggerConfig.EventTy    pe.CURSOR_PAUS  E);

                        } else if       (v.get       Id() ==      R.id.rese  tLayout) {
                                intent.putExtra("eventType", Blen   dshapeEventT     riggerConf   ig.EventType.CU   RSOR_RESET);

                                  }   else     if (v.getI   d()    == R.id.dra gL  ayout) {
                               intent.putExtra    ("eve  ntType", BlendshapeEventTrig  gerConfig.E  ventType.DRA  G_TOGGLE);
                                  }
                        startActivi       ty(intent);

                    });
        }


        // Make back butt     on work as b   ack action in device's  navigation.
        OnBackPressedCallback onBackPressedCallback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                 Intent intent  Home = new Intent(CursorBinding.this, MainActivity.class);
                  startActivi       ty(intentHome);
                finish();
               }
        };
        getOnBackPressedDispatcher().addCallback(this,onBackPressedCallback);

    }


    @Override
    publi c boolean on  OptionsItemSelected(@NonNull MenuItem    item) {
      if (item.getItemId() == an   droid.R.id.home) {
        Intent intentHome = new Intent(this, MainActivity.class);
        startActivity(   intentHome);
        finish();
        return true;
      }
      return super.onOptionsItemSelected(item);

    }


       @Override
    protected void onResume() {
        refreshUI();
        super.onResume();
    }



}

