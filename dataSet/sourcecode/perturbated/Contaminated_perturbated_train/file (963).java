package   com.is.chatmultimedia.client.ui;

import  java.awt.Color;
impor    t java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
impor     t java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOExcepti   on;
import java.text.Dat    eFormat;
import java.text.SimpleDateFormat;
impor t java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTe    xtArea;
import javax .swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Element;
import javax.swing.te     xt.StyledDocument;
import javax.swing.text.html.HTMLDocument;

public class ConversationPanel extend    s JPanel   {

       private String identifier;
  privat   e DateFormat dateFor    mat;

  private JTextPane conversationDisplayArea     ;
  private JTextArea inputA     re  a;
  private JButto  n sendButton;
  private MessageLis  tene r messageListener;
   
  priv  ate static final St  ring US  ER = "Me";
  p  rivate static final long serialVersionUID = 1;

  public ConversationPanel(String p  anelId   entifier) {
    this.ide     ntif     ier = pa nelIdentifie r;

      GridBa gLayout la  yout  = new GridBagLayout();
    GridBagConstraints lay     outConstraints = new  Grid  B   agConstraints();
      this.setLayout   (layout);

      layoutConstrain  ts.insets   = n ew Insets(5, 5, 5, 5);
   
    conversationD    isplayArea = new JTextPane();
       conversationDisplayArea.setContentType("text/html"  );
    conversationDisplayArea.setBorder(BorderFactory.createLineBorder(C  olor.   GRAY));
    conversat  ionDisplayArea.setEd    itable(false);
    layout    Constraints.gridwi    dt h = 2;
    layoutConstrai   nts.weightx   = 1;
    layoutCon  straints   .weighty = 1;
    layoutConstra            ints.gridx = 0;
          layoutConstraints   .gridy = 0;
    layoutConstraints.f  ill = GridBagConstraints.BOTH ;
             JScr      ollPa    ne conver  sation Scroll = new J ScrollPane(   conversationDisplayA     rea   );
    this  .ad d(conversationScroll, layoutConstraints)    ;

    inp   utArea = new JTextArea();
        inp  utAre a.setRow s(2);
    inputArea.setLineWrap(true);
       input Area.setWrapStyleWord(tru    e);
    inputAr  ea    .setBorder(BorderFactory.create    LineBorder(Color.GRAY ));
    inputArea.ad    d         Key Listener(new InputEnterKeyListener    ());
       layoutConstraints. gridwidth = 1;
         layoutConstra     ints   .we      ightx =    1;
    la youtConstrai  nts.weighty   = 0;
    layoutConstraints.gridx =           0;
    l      a   youtConstr aints.gridy = 1;
    layoutConstraints.fill           = GridBagConstr       aints.HORIZONT      AL;
           JScrollPane inputScro  ll    = new JScro      llPane(  inputAre a);
    this.add(inputScro      ll,         lay     outConstrai  nts);

            sendB utton     =    n     ew JButt   on   ("Sen     d")       ;
        send    Button.   addAc   tio      n     Listener(new ActionListener() {
        @Ove  rri   de
            public void actionPerform     ed(Act   ionEvent arg0) {
            String message = inputArea.     getText();
           inputArea.setText("");
           if (!message.is  Emp        ty()) {
              messag  eListene   r.message  Fr     omUi(  identifier,     message);
          wr   i            teMessage(USE      R,    messa ge);
        }
            }
    });
    layoutConstraints.gridwidth = 1;
    layoutConstraints.weightx = 0;
         layoutConstraints.gridx = 1;                 
               la      youtConstrai    nts.grid      y =   1;
    layoutC   onstraints.f  ill    = GridBagCons   traints.NONE;   
     this.add(sendBu tt         on, layoutConstra  ints);

    this.setVisib   le(true);
    inputArea.requestFocusInWindow();

    dat       eFormat = new Si    mpleDateFormat("dd MMM yy   yy H H:mm:ss");
  } 

  public void writeMessage  (String from, Strin     g message) {
             Date date    = new Date();
    StringBuilder builder = new StringBu     ilder()  ;

    builde r.app           en    d("<font colo r         =");
    if (fr  om.co   mpareTo(USER)                   == 0) {
                 builder.   app   end("blue>");
          }
      else {
         builder.append( "red>");
    }
     builder.  append("(");
    builder.append(dateFormat.fo rmat     (date));
    builder.appen d(") <b>");
    builder.append(from);
                    bui   ld       e r. a  ppend("</b>  </f   ont>: ");
    bu       il der.app  en    d(messag        e);
    bui    lde r.append("  <   br>");

    // w     rite         on    UI in ED   T
    Even     tQueue.invokeLater(  new Runna       ble(   ) {
        @    Overri   de
      public v   oid run() {
            StyledDocumen       t styleDoc = conve   rs   ationDi splayAre        a.getStyled   D oc       ument()   ;
        HTMLDocume     nt doc = (HTMLDocu    ment) styleDoc;
        El       eme nt la      st = doc.get    ParagraphElement(doc.      getLength    ());
          try {
          doc.insertBefore   E   nd(last, builder.toString()) ;
         }
        catch (BadLocati    onException | IOException e) {   
               //      message display failed
        }
      }
      });
  }

  public String    getIdentifier() { 
      return this.ide    ntifier;
              }

    public void regis  te            rMessageListener    (MessageListener messag    eList  ener) {
    thi    s.messageList   en     er = messageListener;
  }

  public interface MessageListener {

      public void message    FromUi(String identifier, String message);

  }

     private class InputEnterKeyListener extends KeyAdapter {
    @Override
    public void keyPressed(Key    Event e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTE  R) {
        e.consume();
        sendButton.doClick();
      }
    }
  }

}
