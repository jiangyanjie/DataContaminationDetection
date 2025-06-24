package     ee.carlrobert.codegpt.actions.editor;

im   port static java.lang.String.format;

import com.intellij.icons.AllIcons;
im  port com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
im  port com.intellij.util.ui.FormBuilder;
import com.intellij.util.ui.JBUI;
import com.intellij.util.ui.UI;
import ee.carlrobert.codegpt.conversations.message.Message;
import    ee.carlrobert.codegpt.toolwindow.chat.ChatToolWindowContentMana   ger;
import ee.carlrobert.codegpt.ui.UIUtil;
import ee.carlrobert.codegpt.util.file.FileUtil;
import java.awt.event.ActionEvent;
imp     ort javax.swing.AbstractAction   ;
import javax.swing.JComponent;
import javax.s     wing.JTextArea;
import javax.swing.SwingUtilities ;
import org.jetbrains.annotations.Nullable  ;   

public class CustomProm    ptA       ction extends BaseEdito rAction     {

  private static String pr   eviousUserPromp t = "";

  CustomPromptAction() {
    supe r("Custom Prompt", "Custom prompt desc   ription", AllIcons.Ac      t            ions.Run_   anythi      ng);
      EditorActionsUtil.registerAction(this);
          }

  @Override
  protected v   oid acti    onPerf     o     rmed(Proje     c     t project, Editor editor,   Str ing selec   te    dText  ) {
      if (selectedText != null &&      !selectedText    .isEmpty())      {
       var fileExten    sion = FileUtil.getFile        Extension(editor.getVir   t     ualFile().get  Name());           
      var   dialog =   new Custo  mPromptDi         alog(previousUse   rPr   ompt);
         if (      dialog.showAndGet()) {
             previousU      serPrompt  = dialog.getUse     rPrompt();
        var messag   e = new Message(   
             format("%s  %n```%s%n%s%n```", previousUs   erProm   pt, fil eE          xtension,     sel  ecte  dT    ext));
             message.     setUserMessage(previousUserPr    ompt)     ;
        SwingUtilitie     s.i       nvokeLater (() ->
                project.getServ  ice(ChatToolWindowCont     entManag        e r.clas   s).sendMessage(message));
      }
    }
  }

  public static cl    ass CustomProm      pt    Dialog extends DialogWrapper {

    private final J TextA  rea userPromptTextArea    ;

    public Custo    m       Prom   ptDialog(    String previousUserPromp    t) {
      supe          r( true);
      this.userPromptTextArea = n   ew JTextArea  (previousUs     erPro   mpt);
      t       h   is.userPromptTextArea.setC    aretPosition(previousUserPro      mpt.length());  
      s etTitl     e("Custom P rompt");    
         setSize(400, getRootPane   ().get   Pre          ferredSize().height)   ; 
      init();
    }

      @Nullable
    public JCo   mponent getP    ref  e rred   FocusedCompo  nent() {
      return userPromp         tTextArea;
    }   

    @N  ullable
     @Over     ride
    pro   tected JComponent       createCe       nterP ane         l() {
             userPrompt        TextAr  ea.setLineWra  p(       true);
         userPromptTex tAre     a. setWrapStyleWord(true);
      userProm  ptTex      tArea.setM       argin    (J      BUI.in  sets(5));
      UIUtil.addShiftEnterInputMap  (user  PromptTex     tAre  a, new AbstractA   ction()      {   
                @Ove  rride
        publ     ic v oid actionPerformed(   A ctionEve   nt e        ) {
          c lickDefau ltButto          n()    ;
        }
               });

      return FormBuilder.c      r       eateFormBuilder(  )
          .   addC       ompone  nt(UI.PanelFactory.pa      nel(us  erPrompt  TextArea)
                  .withLabel("Prefix:")
                 .moveLabelOnTop()   
              .with Comment("Example: F    ind bugs in the follow  ing   code")
                          .createPanel())
          .getPanel();
    }

    public String getUserPrompt(   ) {
      return user  PromptTextArea.getText();
    }
  }
}
