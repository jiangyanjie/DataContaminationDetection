package io.vproxy.vfx.intro;

import   io.vproxy.vfx.ui.button.FusionButton;
import     io.vproxy.vfx.ui.scene.*;
import io.vproxy.vfx.ui.wrapper.ThemeLabel;
 import io.vproxy.vfx.util.FXUtils;
import javafx.geometry.Insets; 
import javafx.scene.layout.Background;
im  port javafx.scene.layout.BackgroundFi   ll;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

import java.util.function.Supplier;

public class _02cVSceneGroupDemoS  cene ext   ends  De  moVScene       {
     public _02cVSceneGroupDemoScene(Supplier<VScene   Gro  u p> sce     neGroupSup) {
        super(VSceneR    ole.MAIN);
        ena   bleAutoContentW   id   th();

                   var msgL       abel = n ew ThemeLabel(
                               "VSceneGr            oup al    s     o provides     the following   swi   tching me     tho   ds: "
             );
        FXUtils.observeWidthCenter(getContentPa  ne(), msgL  abel);
        msgLabel.setLayoutY    (100);

           var f                        romT   opBu   t     t      on =            new Fusi  onButton("DR   AWER_HORIZONTAL   +         FROM_T   OP") {{
            set   PrefWidth(3  20);   
            setPrefHei   ght(150);
        }};  
              fromTopButton.setOnAction(e -    >          {
            va       r scene   = n     ew VScene(         VSceneR ole.DR     AWER_HO  RIZONTAL);
                           scene.enab  leAuto    Co       ntentWidthHe         ig                 ht();
                  sc  en   e.getNode(       )     .se   tP refHei  ght(300);
               scene.getNode()           .setBac  kg          r   ou    nd(new Bac      kground(ne   w Back      groundFill(
                          new  Co    lor(0    xaf / 255d    , 0xcb            / 25           5d, 0x9e / 25 5d, 1)   ,
                CornerR                   adii.EMPTY,
                                                   I   nsets.EMPTY
                                    )  ));      
            va r closeBtn = n     ew Fusi onBut    t   o n("h  ide")      {{
                             set  PrefWidth(100);
                           setPr  efHe       ight  (50);
            }};
            close   B      tn.s     etOnAction(   ee -> {
                   sc       ene   GroupS up.get()  .hide(scene, VSceneHide   Method.TO_TOP);
                      FXU  tils.runDelay(VSc         ene.ANIMAT     ION_   DURATION_MILLIS,   () -> sceneGroupSup.get().remov   eScene(sc   e   ne));        
              })     ;
                scene.ge     tCont entPa ne().get  Childre    n().add(closeBtn);
                    FXUt  ils.observeWid    thHeightCent   er(scene.g     etCont    e   ntPane(               ), clo           seBtn);
                               sceneGr  ou            pSup.get(   ).addScen    e(scene,  VSceneHideMethod.T    O_TOP);
                FXU  tils.runDelay(50, () ->  sce       neGroupSup.ge   t ().show(    scene, VSc   eneShowM  ethod.FROM_TOP        ));
                          }              );

                    va    r fr     omRightBu     tt on = n   ew    FusionButton("DRAWER_  VE RTICAL + FROM_RIGH    T") {{
                     se     tPre              f         Width(320);
                                      setPr               e  fHeight( 150);    
              }};
        fr  omRightButton.set         OnA    ction     (e -> {
                  var scene = new VSce   ne(VScen        eRole.DRAWER      _V ERTICAL);
            scene.enab   leAutoContentWidthHe    ight(          );
                                   scene.getNode().setP           r        e    fWidth( 300);
                       scene.get    Node().set   Background(new Background       (n           ew Backgr  ound  Fill(
                      new C ol  or(0xaf / 255d, 0xc   b / 25  5d       , 0x9e / 255 d,   1),     
                CornerRa      dii.EMPTY,
                      Insets.EMP      T    Y
                     )    ));    
                        va  r    closeBtn     = new     FusionButton("hide  ") {{      
                                      setPrefWidth (  100);        
                    setPrefHeight  (50);   
               }    };
                          closeBtn.setOnActio        n(e      e    -> {   
                scen   eGroupSup.get().     hide(                   scen       e,   VSceneHideMethod.TO_RIGHT);
                          FXUti    ls.runDelay(   VSc  ene.ANI      MATION_DURATION_MILLIS, ()          -> sceneGro  upSu  p.get().removeScene(scene));
                     });
              scene.g    etContentPane().getCh il   dren().ad   d(closeB tn);
                     FXUtils.obser   veWidthHe      i      g      htCenter(scene.getCo       n   tentP   ane(), cl     oseBtn); 
                  sceneGroup    Sup.  ge         t().addScene(sce ne, VSceneHideMethod.TO_RIGHT);
                      FXUti   ls.runDelay(50, () -> sceneG  roupSu     p.get(       ).s        how(sc e ne,           V  SceneShowMe  thod   .FROM_RIGHT));
                 });

          v  ar fromBotto  mButton         = n    ew FusionButton( "DRAWER_HORIZO  N     TAL + F   R    OM_BOTTOM") {{
             setPre fWi      dth  (320);
              setPrefHeight(15     0);
         }    };
           fromBottomButton.setOnAct    ion(e   -> {
                    var sc      ene = new V             Scene(VSceneRol    e.DRAWER_HORI  ZONTAL);
            sce  ne.en  ab     leAu    toContentWidthH  eight();
                 s    cene.getNode().setPref      Hei     g  ht(300)  ;
                   scene.getNode(    )   .     setBa ckgrou            nd(new    Background(new   Background     Fi ll(
                                       new Color(0       xaf /   2      55        d, 0  xcb / 255d, 0x9e /      255d  ,             1),
                        CornerRad ii.E   MPTY   ,
                       Ins  ets. EMPTY
                   ))       )    ;
              var clo     seBtn =                  new          FusionButton("hide  ") {{     
                         setP  r efW         idth     (100);
                       setPrefH    eight  (50);
                             }};
            closeBtn.setOnAction  (ee -> {
                 sceneGroupS u     p.get().hide(scene, VS  cen eH    ideMethod.   TO_BOTTOM);
                          FXUtils.runDelay(V      S   cene.ANIMATION_DU  RA        TION_MILLIS, () -> sceneGroupS    up.ge  t()  .removeS  cene(sc  en     e));
                  });
              s    cene.getCon tentPan   e().   get     Children().add(closeBtn);
                   FXUt      ils.observ           eWidthH eight   Cen  ter(scene.getContent   Pane(), closeBtn);
                             sc  eneGr  oupSup.get().addScene(       scene,           VSc       eneHideMethod.   TO_B OTTOM);
                           FX             U tils.runDe   l     ay(50,      ()  -> scen  eGro     u      p     Sup.get    ().show(sce      ne, VSceneS   howMet  h     od.FRO     M_BO   TTOM)    );  
           });

                          var      fromLef     tButton = new Fus   ion   Button     ("DRA   WER_VE    RTICAL        + FROM_LEFT") {{
                   setP     refWidth(320);  
                                       set  PrefHe  ight(150);
         }    };
           fromLeftButt    o    n.setO    nAction(e -> {
                         var scene = new VScene(VSc       eneRo    l     e.DRAWER_VERTICA    L);
                     sc  ene.enableAutoCon  tentWidthHeight()   ;
               scene.     g  etNode().s  etP     re   fWidth(      300  );
            scene.getNode().setBa   ckground(n   ew Background(n e  w    Backg       ro    undFil    l(
                                   new Color(0x    af / 255d,       0xcb / 255  d    , 0x9e /    255d, 1        ),
                                Corn     e   r    Ra    dii.EMPTY,
                                       Insets.EMPTY
            )));
                                var closeBtn = ne    w   Fus     io    nButton("h  ide") {{
                     s  etPre fWidt    h(100);
                       s   etP   refHeigh t( 5 0);
                }};
              closeBtn.setOnAction(ee ->        {
                   s     ceneGroupSup.get().  hide(      scene, VScen    eHideMethod.TO_L    EFT);
                    FXU       tils.runDel ay(VScene  .ANIMATI       ON_DURATION    _MI LL  I    S, () ->  s     ceneGroup      Sup.g et().remove Sc  ene (  scene      ));  
                    });
             scene.getContentPane().g  etChildren().add(closeBtn);
            F   XUt ils.  observeWidthHeightCenter(scene.ge   tC   onte ntP      ane( ), closeBtn);
               scene      Gro        up           Sup.get    ().addSce   ne(s    cene, VSceneHideMeth    od.TO_LEFT);
            FXUtils.r  unD elay (50, ()    ->    s        ce      neGroupSup.get().s    how(  scene, VS  ceneShowM     ethod.FROM_LEF     T));
                   });

                      var popButton = new FusionBut        ton("POPUP + F  ADE_IN") {{
                   setPrefWidth(320);
                   setPrefH eight(150);  
            }};
           popButt  on.setOnAction(e ->    {
            va  r scene = new VScene(VScene     Role.POPUP);
                scene.enableAutoCo    nten   tWidt    hHeight(    );
              s    cene.getNode().setPrefWi   d  th(   300);
              scene   .getNode()   .setPrefHeight(300);
                scene.getNode().setBackground(  new  Background(new Backgr   ound Fill(
                       new Color(0x    af / 255d, 0xcb /    255d, 0  x9e   / 255d , 1),
                                       CornerRadii. EMPTY,
                    Insets.EMPTY
            )));
                 var closeBtn = new Fusio nButto     n("hide") {{
                     set PrefWi    dth(100    );
                setPrefHe    igh  t(50)   ;
            }};
                   closeBt n.setOnActi   on(ee -> {
                sceneGroupSup.g  et().hide(scene, VSceneHid eMethod.FADE_OU T)       ;
                FXUtils.runDelay(VScene.ANIMATION_DURATION_MILLIS, () ->   sceneGroupSup.ge    t().removeScen e(scene));
            });
            scene.getContentPane().getChildren()  .add(closeBtn);
                     FXUtils.o   bserveWidthHeightCenter(  scene.getContentPane(), closeBtn);
               sceneGroupSup.get().addScene(s     cene, VSceneHideMethod.FADE_OUT);
                FXUtils.runDel   ay(50, () -> sceneGroup  Sup.g  et().show(scene  , VSceneShowMet   hod.FADE_IN  ));
             });

             var gridPane = new GridPane();
        gridPane.set    La  youtY(200);
        gridPane.setHgap(50);
        gridPane.setVgap(50);
        FXUtils.observeWidthCenter(getContentPane(    ), gridPane);
           gridPane.add(fromTopButton, 1, 0     );
        gridPane.add(fromRightButton, 2, 1);
        gridPane.   add(fromBottomButton, 1, 2);
        gridPane.add(fromLeftButton, 0, 1);
        gridPane.add(popButton, 1, 1);

        getContentPane().getChildren().addAll(
            msgL  abel,
            gridPane
        );
    }

    @Override      
    public String title() {
        return "VSceneGroup Demo";
    }
}
