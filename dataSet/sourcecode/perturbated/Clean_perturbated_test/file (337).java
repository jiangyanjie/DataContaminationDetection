/*
 * To   change this templat   e, choose   T  ools |    Templ           ates
 *      an     d open the tem  plate in th   e editor.
 */
  package ui.pane   l;

import java.awt.Component;
import j ava.awt.Dimension;
import java.awt.Panel;
import   java.awt.event.Mouse   Adapter;
import java.awt.event.   MouseEvent;
import java.util.ArrayList;
import java.util.Ar          rays       ;
  import java.util.List;
import ja  vax.swing.*;

/**
 *
 * @author      samuel
      */
publi  c class APFDPanel extends javax.swing.JPanel {

    Def      aultL    i    stMod  el       listMod el =     new DefaultLi  stMode   l();  
     JLi            st list;
    List<List                  <String>>     combinations;
      
    /**    
          * Creat   es ne   w form A PFDPanel
     */
    pub  lic  APFDPanel(Li    st<   String>           te      sts)  {
               initComponents();
         
                  c        o   mbin    at   ions = new ArrayList<List<Stri      ng>>(    );
           lis  tUserCombin ation.setModel(listMode   l);     
                                   
        /      /  Cr eate      a  list co    nta         ining CheckListItem's

        List<Ch    eckL    istItem>  item   s =                   new Array    List<CheckList  Item>();

                    //String [] testsName    = {"A", "B", "V",        "C"};

                    for (String   test      : t     ests){
            items.ad d(new CheckListItem(test));
               }


                  lis     t     = new JList(items.t     oArray());
   

         //    Use a Check    L   istR  enderer (see  below)
      // to re nde           rer list       cells
       lis      t.s         etCellRe     nderer(new CheckListRenderer());
      list.setSele           ct      ionM  ode(
              ListSelec           tionMo    del.         MULT      IPLE_INTERVA          L_SELECTION);
 
      // A   dd    a mou  s     e                 listener   to handle cha  nging              selection
    
            list.addMous eLis         ten   er(n    ew     MouseA   dapt er(  )
      {
              public void   mous eCl icke  d(Mouse     Event   event)
           {
                     J  List list = (J       L    i    st ) ev   ent.getSource();     

               /     / Get index   of   i      te     m      click      ed
              int ind ex       = list.locatio    nToIndex(event.ge  tPoint());
                          Che  ckL  istItem   item =     (CheckListIte   m)
                     li     st.                         g       etModel().getElem   entAt(index);
                 
                     // Toggle se           lec     ted      state
                  i  tem    .setSelected(! it  em.isSelected());

            // Re    paint cell
                           list.r  epaint(list.ge  tC el  lBound    s(index, i   ndex)      );
             }
                 }); 
      
       JScrollPane sc     rollpane = new JSc             rollPane(    );
              scrol    l  pane.set     ViewportView(list);   
            
                   scrol   lpane.setVertica   lS   cro   llBarPolicy(    JScrollPane.VERTICAL_SCROLLBA        R_AS_NEEDED);
       sc  rollpane    .setHorizo      ntalScroll    BarPolicy(JScro llP        ane.HORIZONTAL_ SCROLLBAR_AS_NEEDED  );
                  scrollpane.setMinimum         S     ize(    new Dim   ension(200,     200)    );
              scrollpan        e.setS    i     ze(new  Dimension(50      0,                                      200   )   );
         
                te  stsPan  el.ad  d(scro llpane)   ;
       la  b      elVa lue.setVisible(false);
    }
    
    pu        bli         c     void       removeCombination(){
               
           if          (listUserCombination.isS     el   ect  ionEmp  ty())           
                re     turn         ;
          
             int i         n          dex = list     U   serCombinatio    n          .get  SelectedIndex(    );
         
                             
                  if (inde              x >=   0){
                           li  stModel.remove(i    nd     ex);
           
            lis  t .repaint(list         . ge   tC  e   llBound   s(  index, index    )) ;     
                    
            comb  i   n   ations.remove(i   nde x);
        }
    }
      
    publi   c vo   id setVal  ueAPFD  (String value){
          l      abelValue.     s       etV   i    sible(t   rue);    
        this.labe         lValue.se        tT             ext("APFD = " + v       alue + "%  ")      ;  
    }
    
    pub   lic Lis  t<Lis   t<String>  > getCombin    ation(){
                ret  u   rn c  ombinations  ;
    }  
    
    pu  bl ic vo id addCombinati  on(){   
            List<Stri  ng>     combination = ne    w ArrayList<String  >()   ;
              
              for          (in       t  i = 0; i < li     st .getModel().getSize(); i++){   
                       C         heckListI   te m item = (CheckListItem) l   ist  .getModel(           ).getEle    men tAt(i);
            if(   ite     m.isSelected())
                          comb  inat   ion.add(item.toString()    );
        }
                  
              combinations.add(combi   nation);
                
            setNo   tS  electedCheckBox();
        
                inser tU   serCombination(combi       nation);
                      
      }
    
     public    void inse         rtUserCombi     nation(List<St    ring> tests){
         list   Mo     del.addElement(tfFaultName. getText() + "   "    + tests.  toStrin g());
               }
    
    pu       blic void setNotSelectedCheckBox(){
        
        for (int i = 0   ; i < list.getModel().g    et      Siz     e(); i++){
                        Chec   kListIte   m item   = (    CheckL istItem) list.getMod el().getElementAt(i);
                    i         f( item.isSelecte d())      {
                it   em.setSelected(false);
                lis   t  .repaint(list.   getCellBo     unds(i, i));
              }
                }
    }
               
    /       **
     *     This method is    called from      within    the constructor to i ni   ti al ize the form.
        * WAR   NI    NG: Do N      OT modify this code.  The content of  this method is always
     * regenerate  d by the F      or     m              Editor.
     */
    @Suppre          ssWarning      s("unchecked"  )
    // <e           ditor-fold defaultst ate        ="collapsed"   d     e   sc="Generated Code"     >//GEN-B  EG    IN:initCompone  n  ts
            pr        ivate voi         d initCompon ents() {

             jPanel1 = new javax.swing    .JPane   l();
                        jLabel2 = new javax.sw    ing.JLabel();
           labelV alue = new javax.swing  .JLabel();
               jPanel2 = new j  avax.swing.JPanel();
                   jLabel1 = new javax .swing.        JLabel();
              jLabel3 = new jav    ax.swing.JL    abel();
        jButt     on2 = new jav      ax  .sw   i ng.JButton()     ;   
        test  sPanel = new   javax.swin  g  .JPane   l ();
        jB utton3      = new j     a   vax.swing.       JButto   n();
           tfFaultName = ne w javax.swing.JTextField();
        jPanel3 = n     ew javax.sw         ing.JPan    el();
        jScr  ollPane1 = new j    a      va x.swing.JScrollPane()     ;
        listUserCombinati     on = new javax.sw   ing  .JList();

        set  Background(java.awt.Col      or.white);

        j      Panel1.set    Ba     c  kground(ja      va.awt.    Color.da  rk     Gray);

        j       L  abel2.setForeground(java.awt.Colo   r.w      hit e);
        jLabel2.setIc   on(new javax.swing.ImageI   con(getClass().getResource("/ui/reso urce/icon        s/butto  n-apfd.png"))); /       / NOI 1        8  N
        jLabel2.setText("Aver        age Perc  ent of Fault Detect  e           d - APFD")   ;  

        labe           lVa  lue.setBackgr     ound(java.awt.Color.  white);
        labelValue.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
          labelValue.setForeground(java.aw           t.Color.ye llow);
          labelValue.setT   ext            ("v    alue    ");

        javax.swing.Grou      pLayout jPanel1Layout = new       jav    ax         .swing.GroupLayout(jPane l1);
        jPanel     1.setL    ay       out   (jPanel1Lay  out   );
             jPanel1     Layo  ut.setH          o  rizontal    Gr   oup(
              jPanel      1Layout.cr  eat   eParallelGroup(  javax.swi  ng.GroupLayout.Alignment.LEADING)
                .   addGroup(jP anel1Layout.createSequen tialGrou   p()
                    .addContainerG  ap(       )
                           .addComponent(jLabel2)
                .a     ddPre    ferredGap(javax.swing.LayoutStyle.Compo     nentP      l  acemen   t.RELATED,   9 0     ,  Short.      MAX_ VA   LUE)
                   .addComponent(labelValue)
                        .addGap(20, 20, 20))
           );
              jPanel1Layout  .setVertic    alGroup(  
                  jPanel1L     ayout.cre  a  teParall   elGroup(javax.swing.Grou    pLayout.Alig nment. LEA     DING)
            .addG      roup(    java    x.swing.  Gro     upLa   yout.Align   ment .   TR    AILING, jPanel1Lay      out.createSequentialGroup      ()
                     .addContainerG    ap(24, Short.MA  X_VAL   UE)    
                    .addGroup(         j  Panel1Layout.createPar  allelGrou   p(    j   av  ax.swing.GroupLay   ou     t.Alignmen           t.BAS      ELINE)
                                .addComponent(jLabe   l        2)
                                          .addComp   onent(labe  lValue   ))
                     .addContainer    Gap()      )
            );

                jPanel2.setBackground   (java.awt.   Colo  r.whi    t   e   );
             jPanel2.se         t      Border(javax.swing.B    orde     rFa    ctory.crea   te            TitledBorder(""));  

             jLab     el1.setFont(new java.awt.Font(      "Ubuntu"  , 0,  12)); //    NOI1   8N 
        j  Label   1.  setText("F     aults    Name");

           jLab  el3.setFo  nt(new java.awt.Font("Ubuntu", 0, 12)); //   NOI18N 
             j Label3.   setText("Failed      T  est Cases");
   
         jButton2.setFont     (    n     ew java.awt.Font("Ubuntu", 0, 12))    ; // NO       I18  N
                  jB utto n2.setIcon(n    ew    javax.swing.Imag     eIcon(getClass().ge   tResource("/ui/re       source/ic  o ns/icn _button_ minus.png")))   ; // NOI  18N
           j       Button    2.setText("remove"            );
            jB    utton      2.add                        Act    ionListen      er(new java .awt.e  ven t.Ac    t   ionListener() {
            public void ac        tionPerfor  med(j   ava.awt.event.ActionEvent e   vt) {
                             jButton2   ActionPerfor      med(evt);
                 }
         });

        testsPane   l.setB                ackground(java.awt.Color.white     )  ;
        testsPanel.s  etBorder(java    x.swing.B orderFactory.createTit      ledBord               er("    " ));
        
        javax.         swing.Grou   pLayout testsPa  nelLayout =  new      java   x.swi  ng.GroupLayou    t(t        est   sPan    el);
                           test  sPanel.set    L           ayout(testsPa       n   elLayout);
        testsP  anelLayout.setH    orizontalGroup(
            te        st  sPanelLa  yo    u  t.c reatePar          a      llelGroup(javax.swing.GroupLayout.       Ali  gn    ment.LEADING)
            .          addGap(0, 0, Short.MAX           _VALUE  )          
             )         ;
                     te   stsPa nelLayo   ut.        setVert     icalGroup(  
                          testsPanelLayou     t.c     re    ateParallelGr    oup(javax.swing.G  roupLay     out         .Alignment.         LEADING) 
                                   .  ad   dGap(0, 159, Short.MAX    _VALUE)
         );

        jButton3.   se      tFont(new j    ava.awt.Font("Ubunt         u", 0,       12  )     ); // N     OI18N
        jButt  on    3.set    Icon(n   ew javax.s wing.ImageIcon(ge tCla   ss().    g       etResource          ("/ui/resour ce    /i cons/ic   n_button_confirm.   pn      g")));   /  / NO      I18N
                   jBut    ton3.setText("c      onfirm");
               jButton3.addActionListene    r(new jav    a.awt.event  .ActionListe     ner    ()      {
                            public void actionPerformed(j     ava.awt.ev   en     t.ActionEvent e  vt)        {
                    jBu      tton3Action         Performed(evt);
                       }
                        });
   
          javax.swing.Grou    pLayo    ut jPan    el2Layout     = new             ja vax  .swing.G     roup  Layout(jPanel2   ); 
                jPanel2.set  Layout(jPane    l2Layout);
        j    Panel2La  yout.se     tH  or   izo   ntalGrou   p(
              j    Pane     l      2Lay     out   .create   ParallelGroup(java   x.   swing.G roupLayout.Alignme   nt.LEADING)
                   .addGroup(jPane l2Lay     o  ut.createS   equentialGroup() 
                .addGr    oup(  jP        anel2Layou t.c    reateParallelGr     o   u  p(jav   ax.swin  g.Grou   pLayout.Alig        nment.LE    ADING, fals      e)   
                                  .addGroup(jPanel2Layout      .createSequentia  lG   r  oup()
                                       .addCo   ntainerGap()
                                     .add  Group(jPanel2Layout.c    reatePar      alle   lG  ro   up    (javax  .   swing.G  roupL    a   y out.Alig  nment.LEADING   ,           false)
                                      .addGroup(jPanel2Layo ut.cre         ateSe     quenti   alGroup()
                                                    .addCom po      nent(jLabel1)
                                      .addGap(85, 85    , 85))  
                                    .   addGroup(jPan    el2Layou    t.        createSequen  tialG     roup     ()
                                         .addComponent(tf FaultName)
                                     .addGap(7, 7, 7))))
                       .addGroup(j                        Panel2Layout.crea  te       Sequenti        alGroup(    )
                            .addComponent(jButton2)
                           .       addPr  eferredGap(     j     avax.swing.  LayoutStyle.Compone     nt  Pl    ac   ement.RELATE   D, j      avax  .swing.GroupLayout.DEF  AULT_SI     ZE,  S       h          or   t.MAX_VA       LUE)
                        .addComponent(j  Button3)     
                             .addGap(7, 7, 7 )))        
                     .    a  ddGroup(jPa nel2Layout.createParallelGr     o           u   p(jav  ax.   swing.GroupLayout       .Alignm    ent. LEADING)
                      .addGroup(jPanel2Layout.cre  a   t   eSequent   i   alGroup()   
                                .addComponent(j     Label3)
                                        .add   Gap(0       , 0, Short  .MAX_     VA   LUE))
                      .  addCompo     ne  nt(testsPane   l, j         avax.      swing.GroupLayout.DEFAULT_SIZE, ja va       x.s  win      g.Grou pLayout.DEFAUL   T_SIZE,     Short.MAX_V                  ALUE))       
                                 .addC    ontaine   rGap())
        );
        jPanel2Layout.setV  ertica   lGroup(
            jPane   l2Layout.c rea           teParallelGroup(j             avax.     swing.GroupLayout.Alignment.LEADING)
                               .   a   ddGroup(jPanel2Layo         ut.   createSequentia   lGroup()
                .addGroup(jPane       l2L        ayout.createPar   allelGroup(javax      .swing.GroupLayout.Ali    gnment        .BASELINE)
                    .addCompone      nt(jLabel1)
                                   .addComponen    t(jLabel3))
                .    a    ddGap(8, 8, 8)
                .ad dGroup(jPanel2   Layout.createParall    elGroup(javax.sw    ing       .Gro  upLayout.  Ali    gnment.LEADING, fa ls    e)
                                        .      addComponent(te   stsPanel,           java  x.swin      g.Group  Layout.PREFERRED_S   IZ  E, java    x.s    wing.     Gro     upLayout.         DEFAULT_SIZE, javax. swing.GroupL   a  yo       ut.PREFER  RED_SIZE)
                          .ad dGroup(jPanel2Layout.c  reateSequentialGr  oup()
                                   .ad  dCompon    ent(   tfFaultName, javax     .s     wing.    GroupLayou     t.PREFERRE D_SIZE,    javax.swing.Grou  pLay     out    .DEFAULT   _SIZE, javax.swing. GroupLa   yout.PR  E    FERRED_SIZE)
                            .add   Prefer  redGap(javax.sw    ing.L     ayoutSt yle.  ComponentPla        cement.RE   LATED, ja   vax.swing.GroupLayou   t.      DEFAULT_SIZE   ,                         Short.MAX_VALUE)
                                   .ad       d    Group(jPanel2Lay   out.create  Parallel    G roup (javax.swing.GroupLayou      t.Alignment.    BASELIN   E)  
                                  .addComponent(jButton2)
                                  .addComponent(jButton3  ))
                                  .addGap(6, 6, 6))))
           );

        jScrollPane1.s etViewpo  r tView(listUserCombination);

        javax.swing.GroupLayout jPanel3Layout      = new javax.swing.GroupLayout(jPanel   3);
        jPane l3.setLayout(jPane  l3Layou            t);
        jPanel3Layout.setHorizontalGroup(
               jPanel3Layou    t.create     Para  llelGroup(javax.swing.GroupLayou      t.Alignment.LEADING)
            .addGroup(jPanel3Layo   ut.createSequenti    alGroup()
                         .addContainerGap(   )
                    .ad          dC    omponent(jScr   ollPane    1)
                .addContainerGap())
          );
         jPanel3Layout.setVerticalGro    up(
              jPanel3Layout.createParallelGroup  (javax.swing     .GroupLayout.Alignment.LEADING)
             .addGroup(jPanel3Lay     out.createS  equen tialGroup()
                     .addContainerG      ap  ()
                   .addComponent(jScrollPa     ne1, ja vax.swing.GroupLayout.DEFAULT_SIZE, 59, Sh     ort.MAX_VALUE)
                .addContainerGap())
            );

            javax.swing.GroupLa y  out layout = new javax  .swing.GroupLayout(this);
        this.setLayo   ut(layout);
        layo       ut.setHo  rizontal     Group(
                             layout.creat       eParallelGro       up(javax.swing.GroupLayout.Alignment .LEADING)
                 .a    ddComponent(jPanel1,     javax.swing.GroupLayout.DEFA ULT_SIZE, javax   .swing.Grou   pLayout.DEFAULT_SIZE, Short.MAX_VAL   UE)
                 .ad   dGroup(layout.createSequentialGroup()
                      .ad      dContainerGap()
                       .add  Group(l  ayout.createParalle   lGroup(javax.swing.GroupLayout.Alignment.LEADING)
                              .addComponent(jPanel2, javax.swing.Gr oupLayout.DEFAULT_SIZE, javax.swing.GroupLayou   t.DEFAULT    _SI   ZE, Short.MAX_VALUE)
                      .addCompone     nt(jPanel3, javax.swing.Grou pLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAU LT_SIZE, javax.swing.GroupL  ayou t.DEFAULT_SIZ    E, Short.MAX   _VALUE))  
                    .addContainerGap())
          );
        layout.setVerti      ca        lGroup(
                  layout. createParallelGroup(javax.swing  .GroupL    ayout.Ali    gnment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayo  ut.PREFERRE  D_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                   .addPreferredGap(javax.swing.Lay  outStyle.ComponentPlacement.RELATED )
                    .addComponent(jPanel2, javax.swing.GroupL ayout.P REFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,     javax.swing.GroupLayout.P   REFERRED_SIZE)
                .ad      dPreferredGap    (javax.sw   ing.LayoutStyle.ComponentPlacement.RELATED)
                   .addC    omponent(jPanel3,        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DE  FAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:in   itCo  mponents

    private void jButton3ActionPerformed(java.awt.even t.ActionEvent evt) {//GEN-      FIRST:event_jButton3ActionPerformed
              addCombination();
    }//GEN-LAST:event_jButton3Acti  onPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
       removeCombination();
      }//GEN-LAST:event   _jButton2ActionPerformed
       
    // Variables declaration  - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton2;
      p rivat       e javax.swing.     JButton jButton3;
         private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel    jLabel2;
    private javax.swing    .JLabel jLabel3;
    private     javax       .swing.JPanel jPanel1;
    private javax.swing.J   Panel jPanel2;
    private javax.swing.JPanel jPan   el3;
    private javax.swing.JScrollPane jScrollPane1;
    privat e javax.swing.JLabel labelValue;
    private javax.swing.JList listUserCombination;
    private javax.swing.JPanel te  stsPanel;
    privat      e javax.sw ing.JTextField tfFaultName;
    // End of va       riables declaration//GEN-END:variables
}


// Represents items in the list that can be selected
class CheckListItem
{
   private String  label;
   private boolean isSelected = false;
     
   public CheckListItem(String label)
   {
      this.label = label;
   }

   public boolean isSelected()
   {
      return isSelected;
   }

   public void setSelected(b      oolean isSelecte  d)
   {
      this.isSelected = isSelected;
   }

          pu   blic String toString()
   {
      return label;
   }
}

// Handles rendering cells in th   e list using a check box

class CheckListRenderer extends JCheckBox implements Lis    tCellRenderer
{
   public Component getListCellRendererComponent(
         JList list, Object value, int index,
         boolean isSelected, boolean hasFocus)
   {
      setEnabled(list.isEnabled());
      setSelected(((CheckListItem)value).isSelected());
      setFont(list.getFont());
      setBackground(list.getBackground());
      setForeground(list.getForeground());
      setText(value.toString());
      return this;
   }
}
