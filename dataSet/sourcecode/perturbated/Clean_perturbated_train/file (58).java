package     burpgpt.gui.views;

import    java.awt.Color;
import java.awt.Desktop;
imp ort java.awt.Dimension;
imp  ort java.awt.Font;
import java.awt.event.ActionEve  nt;
import java.awt.event.ActionList    ener;
import java.io.IOException;
import  java.ne  t.URI;
import java.net.URISyntaxException;
impor  t java.util.Calendar    ;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
 import javax  .swing.JButton;
import javax.sw   ing.JLabel;
import javax  .swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle;
import javax.swing.ScrollPaneConstants;
import javax.swing.ToolTipManager;
import javax.swing.UIManager;

import burp.MyBurpExtension;
import burpgpt.utilities.HtmlResourceLoader;

public c  lass AboutView         extends JPanel {

    private static fi   nal int COPYRIGHT_FONT_SIZE = 12;
    private static final String WE  BSITE = "https:  /    /burpgpt.app/#pricing  ";

            pu       blic      AboutView     () {
            setLayout(new GroupLayout(thi s)     );
        setB   order(        BorderFa      ctory .cre   ateEmptyBorder(16, 1    6, 16,           16));
                      initCom     ponents();
            enableToolt ips();
                    }

       pub   lic void      initComponen     ts() {
         JLabel t  itl         eLabel = cre   ateTitle     Label();
          JLabel      copyRightLabel      = createCo     pyRightLabel()  ;
         JL  abel descriptionL    abel = createDe       script       ionLabel();
        JScrollPane description        ScrollPane      =       new JScroll   Pane(descriptionLabel);
        descriptionScrollPane.setVerticalScrollBarPoli           cy(ScrollPaneConsta nts.VERTICAL_SCROLLBAR_AS_NEEDED   );
        descripti      onScrollPane.setMaxim   u   mSize(new Dimension(      Integer.MAX_VA  LUE, Intege      r.MAX_VA  LUE));
              descriptionScrollPane.s   e    tBorder(BorderFa  ctory  .createEmpty      Bo      rder());
                   JButton  up   gradeBu     tton     = create       Upgr adeBut    to  n();

        Gr oupLayout layou t = (G       roupLay    out) g        et   Layout();

           Gr   oupLayout.Gro   up ho  ri           zont  al  Group = layout.createParal lel      Group(GroupLay o      ut.Alignment.CENTER)    
                              .addCo        mponent(     titleL   abel )  
                    .    addComponent(co p yRightL     abel)
                   .a   ddCompo  nent(des   criptionS  cr     o   llPane)
                .addCo     m  ponent(upgradeButton);
             layout.se tHoriz        ontalGroup(horizontalGroup);

          GroupLayout.G         roup verticalGro   up =      la yo  ut.c   reateSequentialGroup()
                          .a   ddComponent(t   it      leLabel)
                    .add   Component(co   pyRightLabel)
                    .addGap(16)
                       .addCompon   ent  (descripti           onS    crollPane)
                         .addPreferredGap(LayoutStyl  e.ComponentPlac           e      ment.        R   ELATED, GroupLayout       .DEF  AULT_SIZE, S            hort.MAX_VALUE)
                       .add    Component(upgradeB     utton)  ;
        layout.setVert icalGrou  p(verticalG roup    ) ;  
    }

    private JLabel create TitleLabel()      {
         Stri ng  title =          Str  in              g.f    orm at("<h   tml><h1>%s v%s</h1          ></html>",    My  Bu   r    pExtension.EXTENSI   ON,
                        MyB      urpExtension.VERSION);
            JLabe    l tit     l       e  Label      =  new JLabel(title);
        titleL    abe        l.putClient     Property(  "html.di sable",    null);
          return titleLabe    l;
                     }

    private JLabel createCo      pyRightLabel (          )   {
        String year =               String.value     Of(Calendar.g  etInstance().get(Cal       endar.YEAR));
          String copyRight = String.format(
                "<html>Copyright &copy; %s - %s Alexan     dre Tey    ar, Aegis Cyber &lt;<a href=\"http     s://aegiscyber.co.uk\">www. aegiscyber.co.uk<    /a>&gt;. All Ri          ghts Res  erved.</html>",
                    year, year);
            JLabel copyRightLabel     = new  JLa be     l(c    opyRight     );
            copyRightLabel.setFo nt(new Font(copyRightLabel.getF     ont().g etName(), Font.P   LAIN, COPYRIG     HT_FONT_SIZE));
            copyRightLabel. setFo       regrou    nd(C olor   .GRAY);
        copyRightLabel.putClien    tP  roperty("html.disabl                     e", null);
        return copyRightLa    b           el;    
    }

    pri   vate JLab                e     l createD      escriptionLabel()    {
        String description =   HtmlResourceLoader.lo    adHtmlConte n     t         ("    aboutD  escript    ion.html"  );    
        JLab     el descripti      onLabel     =  ne  w JLabel  (description)    ;
            descriptionLabel.put   Cli       en     tPro            perty("       html.disable     " ,     null);
               ret urn de     scriptionLabel;
                }

    private JButton createUpgradeBu   tton() {
           JButton upgradeButt    on = new JButton   ("Upgr  ad e to         the    Pro e     dition");
         u        pgradeButton.setTo olTipText("Upgrade to the Pro edition by vis    iting our official     web               site    ");
               up    gradeButton.setB    a    ckground(UIManager.g         etColor("Burp.burp   Oran     ge"));
        u  pgradeButton.  s  etForeground(C  olor.WHITE);    
        upgrade Button.setFont      (upgradeButton.g    etFont().deriveFo    nt(Font.BOLD));
        upgradeBu     tton    .addActionListener(ne    w Ac   tionListener() {
                @Override
            public   void actio  nPerformed(ActionEvent e) {
                try  {
                       Des    ktop.ge       tDesktop().browse(new URI(WEBSITE));
                } catch (   IOExc  eption | URISyntaxException e1) {
                    // pass
                    }
            }
        });
        return upgradeButton;
    }

    private void enableTooltips() {
        ToolTipManager.sharedInstance().setInitialDelay(0);
    }
}
