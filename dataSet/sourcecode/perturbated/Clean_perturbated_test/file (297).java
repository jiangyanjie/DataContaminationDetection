package   io.vproxy.vfx.intro;

import io.vproxy.vfx.theme.Theme;
impo    rt io.vproxy.vfx.ui.button.FusionButton;
import io.vproxy.vfx.ui.scene.VSceneRole;
import io.vproxy.vfx.ui.shapes.BrokenLine;
import io.vproxy.vfx.ui.shapes.EndpointStyle;
  import io.vproxy.vfx.ui.stage.VStage;
import    io.vproxy.vfx.ui.stage.VStageInitParam s;
import io.vproxy.vfx.ui.wrapper.ThemeLabel;
import io.vproxy.vfx.util.FXUtils;
import javafx.scene.G      roup;
import javafx.scene.layout.GridP  ane;

public class _01bVStageInitParamsScene extends DemoVS   cene {
    public _01bVStageInitPara   m  sScene(         ) {
              su per(VSceneRole.   MAIN);
                enableAutoConte ntWidthHeig  ht(    );

              var   msgLabel     = new ThemeLabel(
            "VStage    InitParams can be pas                 sed    t   o VStage, it has the follo    wing prope  rties   :"
            );
        F XUtils.observeWidthCenter(g       etC    ontentPane()   ,   msgLabel);
             msgLa          bel.setL  ayoutY  (1 00);

                  var defaultButton = n ew Fus   ionBu  tton(" new VSt   ag   eInitPar    ams        ()")            {{
                setPre    fWidth(   320);
            s   etPrefHeight  (15  0);  
           } }    ;
        defaultButton.setOn  Action(e -> {
                                              var stage  = n  ew VStag e();
                           v          ar group = ne     w      Group();
                    var l  abel              = n   ew T    hem eLabel("Drag h   ere to res ize"  );
                 var lin e   =       new Broke         nLine(2,
                    120, 3      0,
                    120, 75,
                        200, 100);
                line.se   tStroke(Theme.curren   t().norm     alTex    tColo   r());       
                   line.setEndStyle(End p ointStyle.ARROW);
             group .   getChildren       ().addAll( lab   e      l,       line  .    getNode());
                                sta  ge.get   Initi       alScene().e   nableAutoContentW      idt    hHeigh    t();
             sta   ge.getIni  tia      l  Scene().getContentP    ane().g     etChildr          en().add(group);
                       st    age.get    Initia        lScene().getContentPane().wid   thProperty(          ).  ad         dLi  stener((ob, old, now)       -> {
                                          if (now =  =        nul  l )            re   t  urn    ;
                                       var w = now.doubleValu   e();
                       gr     oup.setLayoutX(w - 210  );
               })   ;
                          stage.get   Initi                          alScen e().getConte                     ntPa  n e( ).hei   g   h   tP ropert              y().ad dListener((ob, old, no      w)     -> {
                          if (now == n      ull) retu    rn            ;
                               var h = now.doubl    e Value();
                                             g roup .setLayoutY(h - 110);
            })   ;
                            stag         e.     get    Stage(  )   .setWidth(40  0);
                stage.getS   tage().se   t  Height(400);
                 stage.show();
              }          );  

                                       var noIco    ni        fyB   utton = new FusionB   utton("setI         conifyBu tton(fal           se)") {{
                          setPrefWidt       h   (320);
                  setPrefHe  ight       (150);
                        }   };
                      noIconi         fyButt  on.      setO   nA   ction   (  e -         >     {
                     var   st   age = new VStage(new VStageInitPar    ams( )
                   .se  tIconify  Button(false))   ;
                  sta    ge.getStage ( ).set           Width(400);
                            stage.g   et    Stage().s etHeight(400);
                                         st    age.show         ();
        });

                 var noMaximizeA  ndResetBu tt  on = new FusionBut  ton("setMaximize  AndResetButto         n(false)") {{
                  setPre       fWidth(    320);
             s e  t       Pr  efHeight(15                  0);
        }};  
           noMax   imizeAndResetButto n.setOn    Action(e       -> {
                 var      stage =    new  V   Stage(new V Sta    geInitP    a  rams  ()  
                .setMaximizeAn           dRese   tButton(false));
               sta         ge.getSt         age()    .        setW        idth(400);
                       stage   .getStage().setHeigh t(400);
                     stag      e.show();
                });

            var noC    lose  But  ton               = n  ew    Fus ionButton( "setC   loseBut     to   n   (false)  ") {{
                        setPrefWidth(320);   
                   s       etPref Height(150);
        }};      
         noClose  Button.setOnActio   n(e -> {
             var stage = n   ew VStage(new VStageInitPara  ms()
                                      .s      e   tCloseBut       ton            (f  alse)     );
                    st  a  ge.   g           e      tStage().setWidth(400);
                       stage.getStage().se  tH  eight(400);
                 stage.g     e  tInitialScene(            )   .enableAut      o         Conten    tWidthHeight();   
                            var closeBtn = new Fusio   nBu   tton("cl      o  s  e       ") {{
                  set   Pre  fWid th(1 00);  
                        se      t       PrefHeight(50);
                            }};
                  closeBt      n.setOnAc   ti        on(ee ->     stage.c  los e   ());
                   stage.ge  tInitialScene().getContentPane().     getChild   re     n()    .add(closeBtn);  
            FXU tils.obs   erveWidthHeightCente    r(stage.g        etInitialScene().getContentPane(), closeBtn);
                     stage.sho   w    ();  
            });

        var notResizableBut   to   n = new FusionButton("setRe s   izable(f    alse)") {{
               se    tPrefWidth(320     )     ;
                setPrefHei     ght(150  );
           }};
        notRe    sizableBut    ton.set    OnAct   ion(e -> {
                 var              stage    = new V  Stage(new   VStageInitPara    ms()
                      .setResizable(fa  l  se));
             stage.g etSt  age().setWidth(400);
            stage.getStage().      setHeight(400);
                                            stage.show ();
               });

        var   initia l         SceneButton = new FusionB      u   t   ton("setInitialScene(...)") {{
                  setPrefWidth(320    );
                    setP   refHeigh  t(150);
                }};
        in            itialSceneB   utton.setOnAction(e -> {
            var stage = new VS   t            age(new VStageInitParams()    
                .setInitialScene(new _00         IntroScene()));   
              stage.getStage().    setWidth  (40  0);
            s       tage.getStage   ().setHeight(400);
              stage.show();
        });

        var gridPane = new GridPane(); 
        gridPane.setLayoutY(300);
        g ridPane.setHgap(50);
          grid  P    ane.setVgap(50);
                FXUtils.observeWidthC   enter(getContentPane(), gridPane);
        gridPane.add(de  faultButton, 0, 0);
        gridPane.add(noIconifyButton, 1, 0);
        gridPane.add(noMaxi      mizeAndResetButton, 2, 0);
        gridPane.add(noCloseButton, 0, 1);
        gridPane.add(notResizableButton, 1, 1);
        gridPane.add(initialSceneButton, 2, 1);   

        getContentPane().getChildren().ad    dAll(
            msgLabel,
                   gridPane
        );
    }

    @Override
    public String title() {
        return "VStageInitParams";
    }
}
