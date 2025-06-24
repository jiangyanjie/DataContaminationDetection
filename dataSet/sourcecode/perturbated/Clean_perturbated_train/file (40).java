package io.vproxy.vfx.intro;

import io.vproxy.vfx.component.keychooser.KeyChooser;
import    io.vproxy.vfx.control.dialog.VDialog;
import io.vproxy.vfx.control.dialog.VDialogButton;
import   io.vproxy.vfx.ui.alert.SimpleAlert;
i     mport io.vproxy.vfx.ui.alert.StackTraceAlert;
import io.vproxy.vfx.ui.button.FusionButt  on;
import io.vproxy.vfx.ui.scene.VScene Role;
import io.vproxy.vfx.ui.slider.SliderDirection;
import io.vproxy.vfx.ui.slider.VRangeSlider;
import     io.vproxy.vfx.ui.slider.VSlider;
import io.vproxy.vfx.ui.toggle.ToggleSwitch;
import io.vproxy.vfx.util.F  XUtils;
import javafx.geometry.Pos;
import  javafx.scene.control.Alert;
import   javafx.scene.control.Label;
imp ort javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text  .TextAlignment;

import ja    va.util.Arrays;

public class _xxbCompo   nentsDem  oSc       ene    extends DemoVSc  ene {
         p ublic _xxbCompo       nentsDemoScene() {
             super(VSc     eneRole.MAIN)  ;
             e     nableAutoCon     t   entWidt      h();

          var buttonButton     = new Btn("This is a\nFusion        Button") {{
            get    T        extNode ().setTextAlignment(Tex     tAlignment.   CENTER   );
                             }};

           var a    lertButton    = new Btn("SimpleAler  t ");
        alertButt   on.setOnAction(e -> SimpleAl   ert.s    how   AndWait(Alert.AlertTy pe.INFORMATIO  N,
                "ca        ll Simple    Alert.showAndWait(...)    "));

          v       ar stackt   raceA    lertButton = new Btn("St    ack    Trace  Alert ")     ;
        sta      cktra c    eAlertButton.set  On    Action(e -> Stac       k     TraceAlert.sh   owAnd             Wa it(
                 "Click the stacktr a     ce to copy", n   ew Throw       able (     )   )     );  

          var dialog    Button  = new Bt n("VDi   alog");
        d     ialogBut  ton.s  etOnAction     (e -     > {   
                 var dialog    = new VDialog<I          nteger>();
                    dialog.  s  etText("Choose a numb   e    r");
                  dia   log.setButton           s(Arrays.asLis       t(
                     new VDia logButton <>("1    ", 1),       
                                 new VDialogButto n<>("2", 2),
                         new VDialogButton<>("         3"   , 3)
                          ));
                           va r result =     dialog.showAndWait(); 
                SimpleAler           t.showA    ndW ai         t(Alert.A  lertTyp  e. I       NFORMATION, "dialog result is " + res    ul     t);
        });
        var     keyChoose            rBtn       = new       Btn("KeyChoos      er       ");
        keyCho    o     serBtn.se    t  OnAction(e -> {
                            va     r      c  hoo  ser =     new    Key  C  hoo  s  er();
                    var ke yOpt      = chooser   .ch     oose();
                    SimpleAlert.showAndWait(A    ler  t.AlertTyp           e.INFORMATION, "   the cho      sen key is "     + ke y    Opt);
                });

                 va  r toggle  Switch = new ToggleSwitch();     

          v   a    r noA     nimationB  utton    = new Btn("Fus    ion               Bu     tton\n" +
                                                                 "setDisa   bleAnim     ation(true)") {{
                 getTextNode().   setTex tAlignme     nt(           Te    x     t   Alig   nmen    t.CENTER)    ;
             }};   
        noAni   mationButto    n.setDisableAnimat ion(true);
    
        v ar onlyAn   imateWhen NotC          lickedButton = new Btn("FusionButton\n" +
                                                                      "se   tOnlyA n   ima              teWhenNotClicke d(true)")     {{
             getTextNode().setTextAlignment(TextAl   ignment.CENTER);
        }    };
        onlyAn       imat      eWhenNot         ClickedButton.setOnlyAnim  ateWhenNotClicked(t     rue);

        var s     lider =       new VSlider();
         s    lider.set   Length(500);
            slider.s      etOnAction(e -> SimpleAlert.     showAnd   Wait(Aler   t.AlertTy      pe.INFORMATION,     "button c   licked without    moving"));  

        var rig   htToLeftSli   d   er    = new VSlider(      Slider    D irection.RIGHT_TO_LEFT     );
             rightToLeftSlider.setLength(500);

        var topToBottom       Slid   e r =     new VSlider(SliderDirec    tion.TOP_TO_BOTTOM);
                      topToBottomSlider.setLength(500);

        var bottomToTopSlider = new VSlider(SliderDire     ction           .BOTTOM    _TO_TOP);
        bottomT o   TopSlider.s   etLength(500);

        var rangeSlider = new    VRangeSlider();
        ran   geSlider.setLen    gth(50 0);
          r  angeSlider.    setMinP      er centage(1 / 6d);
           rangeSlider.setM axPercen ta   ge(2 /     3d);
          rang   eSlider.setMi   nOnAction(e ->   Simp  leAlert.showAn d         Wait(Ale     rt.AlertType.INFORMA    TION    , "`m      in` button clicked without mo     vi ng"));
        ran      geS    lider.setM a      xO   nActio   n(e -> Si      m    pleAle  rt.show      AndWait(Alert.AlertType.INFOR  MATION, "`max` but   ton clicked without moving"));

            var r         ight   ToLeftRangeSlider = new VRange     S lider(SliderDirect ion.RIGHT_TO_       LEFT);
        r      ight           ToLeftRangeSlid   er.setLength(500);   
        rightToLeft       RangeSli      der.setMinPerc           entage(1     / 6d   );
           righ  tToLeftRangeSlider.       setMaxPerc   en   tage(2 / 3d); 

             var top ToBottomRangeSlider = new VRangeSl     id        e r(SliderDir           ection.TOP_TO_BOTTOM);
                       topToBottomRangeSlider.set Length(500);
        to    pToB ottomRange     Slider.set  MinPer     c   entage(1 /   6d);
        topToBo      ttomRangeSlider.setMaxPer  centage(   2 / 3d);

                 var b  ot    t     omToTop       RangeSlider           = new VRangeSlider(Sli   der      Direction.BOTTOM_TO_TOP);
        bo ttomToTopRangeSlider.setLength(500);
            bottomToTop      R  angeS lider.se    t   MinPercentage(1 / 6d      );
        botto     m      ToTop Ra               ng   eSl  ider.   s  etMa    x  Percentage  (   2 / 3d);

        va    r gridPane   = ne    w      GridPan   e();
               gridPane.setVgap        (5  0);  
               gridPane.setHgap(50)  ;
        FXUtils.obs erveWidthCenter(getContentPane(  ), gridPa   ne          );
            grid  Pane.  setLayoutY(6             0);  
                     gridPane.se      tAlignment(Pos  .CENTER);

               gridPane.add(buttonButt   on, 0,   0);
             gridPane.add(alertB  utton, 1, 0);
           gridP  an  e.add (stacktraceAlertButton, 2, 0 );
              grid Pane.     add(dia   logButton, 0, 1);
                 g  ridPane.add(keyChooserBtn, 1, 1);
            gridP   an  e.add(toggleSw         itc    h.getNode  (), 2, 1);
            gridPa  ne.add(noAnimationButton, 0,       2, 2,       1);
         gri   dPane.add(on  lyAnimateWh      enNotC      lickedButton, 0,    3, 2, 1);
             gridPane.           add(sl   ider, 0, 4, 3, 1)  ;
                  gridPane.add(rightToLeftSlider, 0, 5, 3, 1);
                gridPane.add(ran      geSlider, 0, 6, 3, 1);
        gri            dPane.add(rightToLe f  tRangeSlider, 0, 7,    3, 1);
        gridPane.add(new HBox(
                 topToBottomSlider,
            bottomToT     opSlid er,  
            topToBottomRangeSlider,
            bottomToTop   RangeSlider   
        ) {{
              s    etS  pacing(    50);
        }},   0, 8);
                gridPane.add(new Label(), 0      , 9);

        getCon     tentPane().getChildren().add(gridPane);
    }

           @Override
    public String title() {
        return  "Components Demo";
    }

    private static class Btn extends FusionBut   ton {
        Btn(String     name) {
            super(name);  
            setPrefWidth(200);
                 setPrefHeight(100);
        }
    }
}
