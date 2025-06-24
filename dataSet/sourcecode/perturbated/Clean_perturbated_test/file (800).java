/*
    * To    change this license   heade     r, c     hoose License Headers in       Project Properties   .
    * To change this template file, choose Tools | Templa  tes     
 *    and open the template  in the editor.
 */

package UI;

im port italler.Cliente;
import italle    r.Coch    e;
import italler.ctrlTaller;
import java.util.ArrayList;
import javax.swing.DefaultListModel;

/**
 *
  * @author Ar       clorenS  arth
    */
public   class cre       arCocheWnd extends j     av  ax.swing.JDialog {
    
    ct       rlTaller tall    e    r;
    C  o   che          modCoche;
       Defau  ltListModel    clientsModel;
    Ar   ray  List<Clie           nte> cList;
             
          /**
     * Creates new    form cre         arCoche   W  nd
     */
    public cre   arCocheWnd(ja      v  a.awt.Frame paren  t,     bool ean        mo   dal, Coc    he mo          d) {
                 supe  r(par  en     t    , modal);
                               initComp  onents(   );
          t   aller =      ctrl Tal   ler.getInstance();
          mo       dCoche = mod;
           clientsMode   l = new Defaul       tLi      st                     Mod el();
           
        if(modCoche !=  null){
            matText.setText(m   o  dCoch     e.   get   Ma tric    ula());
               marcaText    .setT ext(modCoche.  getMarca());
             m       o    deloText.se    tTex t  (m    o dCo  che.getModelo()   );
                    numKmText.setText(modCoche.getNumKm());
                     numBastText  .s   etText     (modCoche.g   etNumBast());
                     pr    op    ietarioText             .setText(modCoche.ge        tPropietario(      ));
          }
        
               c List = talle  r  .searchClientes(""    )  ;
                 clientsMo            del.clear();
                for(int i=0; i<cList.size(); ++i ){
                clientsM  odel.addElement(cList.get(i).getNombre())   ;
         }
        clientsList.s              etM        odel(client        sModel);  
        }
    
             /**
            * This method i s called f      rom with   in th   e constructor to              initialize the form.
     * WARNING: Do NO  T mo   dif  y th  is  code.    The content     of     th              is method is always
     * regen     erated by the Form E ditor.
           */
    @SuppressW          arning s("unchecked")
    //    <e  ditor-fold defa    ult     s     tate="   collapsed" desc="Generated        Code"> //GEN-B  EGIN:initCom   ponents
          private voi   d initC  omponents() {

        matTex   t = new   javax.swing  .JTextField();
         marcaText = ne w javax.swing.JT    extField();
        mod el    oT   ext =    new jav    ax.swing.JTextField();
             propietar  ioTex        t = n      ew javax.swing.  JTextField();
        jL        ab el1 = new javax.swing.     JLabel();
          jLabe l2 = new javax.swing.JLabel();
         jLabel3 = new     javax.swing.JLabel();  
        numKmText = new java  x.swing.JTextField();
        numBastText = new javax.swin g.JTextField   ();
           jLabel4           = new javax.swin g.JLabel();
        jLabel   5 = new jav   ax.swing.JLabel();
            jLa   bel6 = n  ew j     avax.swing .JLabel(     );
        jToggleButt    on1 = new javax.swing.JTog  gleButton     ();
                            jToggleButton2 = new javax  .swin         g.JToggleBut   t   on();
        searchPropietar ioT  ext = ne  w javax. swing.JText         Fi  eld();
                      jS  crol        lP    ane    1 = new javax    .swing.JScrol   l  Pane();
        clientsL    ist =      new         javax.     sw  ing.JList();

        setDefaultClose  Ope      ration(ja     vax.swing.W       indowCons     tants.   DIS  POS E_     ON_CLOSE);
        set      Resizable(false);

        matText.addA ctio     n   Lis     t    ener (new java.aw     t.eve      nt.A  c      ti   onList     ener() {
            public   void acti on  Pe    rfo rmed(jav    a.awt.event.A c     tionEvent    ev           t) {   
                                  mat TextActionPerformed(e     vt)       ;
            }
             });

        modeloText.addActio   nListener(new jav   a.aw        t.event.A   ct i  onLis tener()   {
               publi   c void actionPerf   ormed(java.a wt.ev  ent.ActionEvent e    vt) {
                                    mo  deloTextAc  tionPer  formed(   evt)         ;
              }
                }); 

                 propietarioT   ex   t     .setEditab         le(false);
        propietarioText.a           ddActionListener(new java.a     wt.ev    ent.Actio  nLi  s     te       ner() { 
            public v  oid actionPerformed(ja   va.awt.        e    vent.      Actio        nEv    ent evt              ) {
                               p       ropie  tarioTextAc      tionPerformed(e  vt);   
              }
        });

        jL  abe       l1.set   Text   (  "Matr            icu  la");

                jLabel2.s etT  e    xt   (  "Marca ");

           jLabel3.se     tT   ext    (  "Mod    el  o")   ;

         jLabel4.    se     tText   ("NÂº      K  m");

            jLabel5.setTe      x        t("NÂº Bastidor");

        jLabel6.   setText("Propiet     ario");

        jToggleBut           ton           1.setText("Ace  ptar");
        jToggleButto n1  .addAct       ionListener(new ja    v         a.awt.event.ActionList ener() {
                     pu  bl ic voi  d acti         onPer formed(j a  va.      awt.even t  .ActionEvent evt) {
                 jToggleButton1ActionPerformed(ev  t);
            }
         })  ;

          jTog gleButton2  .setTe  xt("Ca   ncelar");
        jTog   gleBu  tto  n2.    addActionListener(new java.a    wt.event.ActionList       ener() {
                    public voi    d    a  ctionPe   rform  e        d(java.awt.event.ActionEvent    evt   ) {
                     jToggleButton2ActionPerfo     r   med(evt)  ;
                }
               });

            searchPropietar   ioT     ext.setText   ("T         eclee aqu   i p ara bus       car el prop  ietario que   desea para el  vehiculo");
           searchPropie      tarioText.addActionL  istener(n   ew      java.awt.event.ActionL    istener      () {
                public void           actionPerformed(jav a.awt.event  .A            ctionEvent e  v   t)  {
                          se                 archPropietar   i   oT             e  xtAct ionPerformed   (evt);
            }
                   });
                              s  earchProp             ie         tar    i      oText.add        KeyLis     t ener(new java.   awt    .even   t.KeyAdapter(  ) {
                public void keyPre  sse  d(java.awt.   event.Key Event ev t     ) {  
                 se     archPropietarioTe  xtKeyPressed(evt);
               }
             publ ic void        keyT  yped(ja    va.     awt.ev     e nt.       KeyE  vent evt) {
                searchPropi    et   arioT   ext  KeyTyped(evt);
                                        }
           });

            client sList.setSel ectionMod  e(javax.swing.L     istSelectio  nModel.  SINGLE_   SELECTION);
        clientsList .addMouseLis   tener(n  ew java    .awt.event.                                      MouseAdapter() {
                   public     void mouseCl icked(java.awt.event.MouseEven t  evt) {
                         client  sLi          stMou    seClic    ked(evt);
                   }
        });
                 cli entsList   .ad   dKeyListener(new j  ava.aw   t.event.KeyAdapter(  ) {
                                                 pub lic        void keyPres     sed(java.            a wt.event.Ke     yEvent ev   t)     {
                     clientsLi      stK eyPressed(evt);
                        }
        }    );
                  jScr     ollPan        e1.setViewpor  tV iew(c    li ents           List              );

                    javax.sw  ing.GroupLayout la   yout =  ne   w javax.swing.GroupLayout(g  etC ontentPa      ne());
        ge  t  ContentPane().  set  Layout(layout);     
        layout.s   et   Hori zontalGroup   (
              lay   out.createPa        rallel  G ro     up(ja    vax.s   win    g       .Grou   pLayo   ut.Align     me  nt.LEA  DING)
                .addG  roup(layout.createS    equentia            lGro  up()
                     .addGap(28, 28, 28)
                                    .      a ddG     roup(la      yout.creat           eParallelGr oup(java       x.swing.GroupLayou   t.Alignm  e         nt.    LEADING)
                                   .addGr   ou    p(l   ayout.createSeq         uentialGroup              ()
                              .addGroup(layout     .createPara   llelGroup(javax.sw    ing.      Gro     u      pLayout.A   lign     ment.LEADING)
                                                .ad     dCo    mpone nt(jL    ab  el    3, j                           avax.swing.               GroupLayout.DEFAU LT_SIZ     E,   javax.swin   g.GroupLayout.DEFAU      LT_SIZE,     Short.MAX_VALUE) 
                                                         .  addGrou      p(j        avax.swin      g.G    roupLayout.A      lignment.T            RA        ILING,    layout.cre ateSeq   uentialGroup ()   
                                                 .add Gap(0,       0,       Sh   ort.          MAX_VAL     UE)
                                               .   ad dCompone nt(   jLabe l1, java  x.            swing    .G       ro upLayou               t.P  REFERRED_SIZE,   56       , javax.swing            .GroupLayout  .PREFER RED  _SIZE  ))
                                   .ad  dGr   oup(l      a     y out.cr  eateSe         quentialGr         o    up()  
                                     .    addComp   one  nt(                        jLa   bel2, ja vax  .swi   ng.   Group    Layout.PREFERRED_SIZE, 4 1,   javax.swing.G   ro      up  L a    yout.            PREFERRED_       SIZ          E)
                                                   .addGap(0, 0    ,   Sh   or   t.M  AX_VALUE)))   
                                            .addP  referre     dGap(   j        a           vax.s  wi          ng.La     youtStyle.Compo   nentPlacem  ent.UNREL  ATED)
                                  .         add Group(    layou        t.createParallelGro  up(    java  x.swing  .    GroupLayout.Alignment.LEADING)
                                          .a ddGroup(la yo    ut.createSe                 quentialGroup()
                                           .     addComponent(modelo   Tex        t, java   x.s       wing.Gr  oup   Layout.PREFERRED_SIZE, 226, jav ax.swing.Gro   upLayou    t  .PREFE R    RED_ SIZE)
                                                                   .     ad   dGap(     0   , 0, Short.MAX_VALUE))
                                                          .a       ddGroup(javax .swing.GroupLayou  t.Alignm    ent.T RAILIN         G  ,       layout.   c   rea teSequentialGroup()     
                                      .addGr         oup(layout.createParalle lGroup  (ja      vax.sw    ing.GroupLayout.Ali    gnmen  t  .L  EADING    )
                                                                                         .addCompon   ent    (marcaText, javax.swing.Grou   pL ayout.P       R  EFE     RRED_SIZ   E, 225, javax.swing     .G    r   oupLayout.P        REFE    RRE   D_SI     ZE)       
                                                             .addComponen   t(matText   , javax.swing.GroupL  ayou     t.PREFERRE   D_SIZE, 15  2,  javax.s wing.   Gro  u pLayou t.PREFE   RRE    D_SI        ZE))
                                                          .   addP  ref          erredG   ap(javax.s  wing   .L   ay   outStyle.Com    pon  entPlac  ement.RELATED, 57, Short.MAX  _VALUE)
                                                   .add          Gr  oup   (layou    t.c     reat eParallelGroup(ja   vax  .     swin  g.G  roupLa yout.Al  ignment.LEADING)   
                                                          .addComponent(jLabel5)
                                       .addGroup(   layo    u      t.createSequentialGroup()
                                                             .addGap(10, 10, 10)
                                               .addCo  mponent(jLabe   l4 )))
                                                   .addPre   ferredGap(j   avax.swing.LayoutStyl     e.   Co  mponentPlacement.U   NRELATED)
                                   .addGr        oup(layo      u  t.cr         eateParallelGroup(ja         vax.    sw  ing.Gro   upLayo     ut.Alignmen  t.LEADIN     G, false)
                                                    .addComp   o     n ent(n umKm        Tex   t, java    x.swin    g  .GroupL   ayout.D         EFAULT_SIZE, 95, Short.MAX_   VALUE)
                                                        .addComponent(numBastT      e          xt)))     
                                              .a ddGroup(javax.swi     ng   .GroupL   ayout.Alig    nment.TRAILIN   G,          layout.cr     eate   Sequen           tialG      ro      up()
                                           .addGap(0,       0,         Sh   ort.MAX_VAL UE)
                                .addCo  mponent(jTo     ggleButton1)
                                              .ad  dPreferre  dGap(javax .swin  g.L ay  outS    tyle.Compone     ntPlacement.    RELATED)
                                   .ad   dC omponen    t(  jToggleButto  n2))))
                       .ad   dGr   oup(layout.creat     eSequentialGroup()
                                     .a  dd    G  roup(layo u    t.cr    eate      ParallelGroup( javax.swing.       G  roupLa   y  out.Al  ignment.   TRAILING, false) 
                                         .ad   d  Component(searchPro     pietarioText   , ja  vax.swing.G  roup    L     ayout.A    lig  nmen t   .LEADING)
                                             .addComponent(jScrollPa  ne1, javax.swi    ng.      Gr oupLayout           .Alignment.LEADI      NG)
                                                  .addGroup (lay  o  ut.cr  eate Seq  uentialGroup()
                                  .   addComponent(j          Label6,    java  x.sw  ing.GroupLayout.P REFERRED_SIZE,   62,     javax.swing.G     roupLayout. PREFERRED_SIZE)
                                                .addPr           eferredGap(         j   av    a     x.swin  g.Layo      utStyle .Co  mponen    tPlaceme nt.  RELATED     )      
                                                             .addComp  onen    t   (prop  iet         a  rioText, j         avax.swing.G  roupLa y out.PREFER        RE D_SIZE, 2   83, j   avax.swing.GroupLay out.    PR     EFERR  ED_SI       ZE)))
                            .addGap(0, 0, Short.  MAX   _VALUE  )))
                                 .addGap(2  8,     28, 28))    
        );
        layout.         s  et      VerticalGrou     p(
                    la yout.creat  ePara  l  lelGroup(javax.swing.GroupLayou    t.Alignment.LEADING   )
                  .add     Group(layo      ut  .createSequential    Group()  
                            .addConta    inerG   ap()
                .addGroup(layout.createP  arall elGroup(java           x.  swing.G    ro      up  Lay          o  ut.Align    ment.BASELINE)
                         .addCom   pon   ent(n umKmText, javax     .swing.GroupLayout.P REFERRED_SIZE, javax.swing    .GroupLayo       ut.DE FAU   LT_S     IZE, javax.swing.Gr    o      up  Layout.PREFERRED_SIZ  E)
                       .addComponent(                j     Labe  l4)
                    .addComponent(ma   tText, javax.swing.GroupLayout.P  REFERRED_SIZ     E, javax.    s    wing.Group   Layou  t.DEF   A ULT_S      IZE, javax   .swing.      G   roupLayout.PRE   F    ERR    ED_SIZ           E)
                               .a  ddComponent(jLab  e       l1, javax      .swing.GroupLayo  ut.DEFAULT_SIZE, javax. swing.GroupLayout.DEFAU LT_S   IZE, Short       .MA        X_VALUE))
                    .addPreferr    edGap(javax.swing.Layo   utSty      l      e  .   Componen  tPlacement.UNRELATED  )
                .ad  dGroup(        lay   out   .    crea        teParallelGroup(ja    vax.sw  ing.GroupLayout.A   lignme    nt.  BASELINE)
                      .addComponent(ma          rc     aText, ja   vax  .swi       ng.GroupLayout.PR  EFERRED_SIZ         E   , javax    .swing.GroupLayo    ut.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_S IZE)
                         .a       ddComponent(jLabel2, javax.swing       .       GroupLayout.DE     FAULT_SI    ZE,    ja    vax.swing.GroupLayout.DEFAUL T_S  IZE,   S   hor         t.MAX_VAL      UE)
                           .addComponent(numBast          Text, java   x.swing.GroupLayout.PREFERRED_SIZE, javax.swing.Gro     upLayout.DEFAU        L     T_SIZE,       java x.swing.GroupLayout .PREFERRED_SIZE   )
                          .ad        dComponen  t(jLabe        l5))
                    .   addPrefer    r        edGap       (javax.swing         .LayoutStyle.C omponentP   lace   m  ent.UNRELATED)
                 .addGroup(layout.cre     ateParall  elGroup(java  x.swing.Gro   upLayout.        Alignment.TRAILING)
                          .addGroup(l    ayout  .createS   equentialGro   up()
                                  .ad           dG ap(0, 0     , Short.MAX_ V    ALUE)
                                           .addGroup(layout.crea   teParallelGroup(javax.sw  ing.GroupLayout.Ali  gnment     .BASELINE)
                                      .addCo   mponent(jToggleBut  ton1)
                                         .addComponent(jTog  gle     B  utton2)))     
                                             .addGroup         (lay   out .createS     equentialGroup()
                          .addGro up(l    ay   out.cr         eateParallelGroup(javax. swing.Grou    p  Layo      ut.       Alignme  nt.BAS     ELINE)
                                       .addCo     mp   one     nt(jLa   bel3, javax.swing.GroupL    ayout.PREFERR   ED_SIZE   , 20    ,    javax.swing.    GroupLayout     .PREFE       RRED_SIZ    E)
                                                     .addCom     pone   nt(mod    e  loText))   
                                         .addPreferredGap(javax.swin  g.L ayou  tStyle.ComponentPlac    e   ment.UNRELATED)
                                        .addGroup(   layo  ut.createParal lelGroup(javax.swing.   GroupLayout.Alignmen  t.BASELINE)
                                     .addCompone n     t(   p ropietarioText  , j   avax     .  s   wing.Group             Layout    .PREFERRED_SIZE,   javax.swing.GroupL      ayout.D     EFAULT_   SIZE, java  x.swin  g.GroupLayout.PR EFERRED_SIZE)
                                                      .ad    dComp onent(jLabel6))         
                                    .addGap(27, 27, 27)
                                .addC  o    mponent(searchPropietarioT    ex    t, jav   ax.s       win       g.GroupLayou   t.PREFERRED_S  IZE, j       avax .swing        .Gr     oupLayou t.DEFAULT_SIZE, javax.sw    ing.Gro     upLayout.PRE     F ERRED _  SIZE)
                             .addPrefe rre     d     Gap(j  a             vax.swing.LayoutSty    le.Co   mponent     Placement.RELATED)    
                                         .addComponent(jS crollP an   e1, java     x.sw in  g.GroupLayout.PR  EFE RRED_SI      ZE, j               avax.swing.     GroupLayout.DE  FAULT_SIZE, java  x    .sw        ing.Gro    upLayout.PREFER RED_SIZE  )))
                   .addGap(20, 20, 20)  )
                  );

        pac k();
    }/      / </editor-f old>//GEN-END:initComponen ts     

               private void   mode       loTextAction     Performed(java.awt.event.Acti       onEvent ev  t) {//GEN  -FIRST:event_modeloTextA    ctionPerformed
        // TODO      add your handling    cod         e here:  
    }/   /GEN-LAST:event_modelo  TextActionPerfo      rmed

       priv   ate    void   propietarioTextA       ctionP     er  formed(j  ava.awt.event.A    ctionE  ven    t evt) {/     /GEN-FIRST:eve   nt_propietarioT  extActionPerformed     
           // TODO add   your           handling code   here:
    }//GEN-LAST:      event_propietarioTextActio  nPerformed

    private void m    atT   extActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIR   ST:event    _mat        Tex     tActionPerf  orme   d
                // TODO a      dd       your han    dling code here:
    }//  GEN  -LAST:ev       ent_matTex  tActi     onPerformed

    private void searchP  r opietario       Te     xtActionPerformed(java.awt.even     t.ActionEvent evt)        {//GE    N-FIRST:event_search   P     r       opiet       arioT     extActio       nPerfor     med
        cList = tal   ler.sea    rchCl     ientes(s  earch    PropietarioText.getText());
        client sMode  l.clear()  ;
        for(int i=0; i     <cLis t.size  (); ++i){
                  clientsModel.add       Element(c   List.get(i).             getNom  bre());
          }
        clientsLis  t.s    etM   o        del(cli entsModel);     
     }            //GEN-LAST:event_searchPropietarioTextActio  nPe  rformed

        pr  ivate void jToggleButton1ActionPe   rformed(java.aw    t.eve  nt.    ActionEvent evt) {//G       EN-FIRST:event_jToggleButton1Acti onPerformed
                 if(modCo       che == null){   
            try{
                   taller.crearCoche(matText.getT          ext(),mar caTe  xt.getText(),
                                                            modeloText.get   Te   xt(),numK mText.      getText(),
                                      numB     as       tText  .getText(   ),p   ropi   etarioText.getText());    
                      this.dis     pose();
                    }
             catch(Except         ion e){
                excepW    i  nd     w = new excepWin   d  (new javax  .swing.JF           rame(),true,e.getMessage());
                w.show(); 
               }
        }     
        else  {
                            try{
                       taller.modif   icarCoche(matTex  t.getText(),modCoche.getMa  tricula(),marcaText.getT  ext(),
                                            mo deloText.getText(),numKmText.getText(),
                                     numBastTe   xt.getText(),prop    ietarioText.getText());
                              this.dispose();
                    }
            catch(Excepti    on e){
                      exc epWi     nd w =    new excepW    ind(new javax.swing.JFrame(),tr     ue,e.ge   tMessage  ());
                 w.show();
                 }
        }
    }//GEN-   LA  ST:ev    ent_jToggleButton1  Actio     nPerforme   d

    private v    oid jToggleButton2Actio   nPerformed(ja    va.awt  .event.A   ctionEvent evt) {//GEN-FIRST:event_jToggleButton2ActionPer   for        med
        this.d  i  spose();
    }//GEN-LAST:event_jToggleButton2Act  ionPerformed

        private void cli   entsListMouseClicked(java.   awt.event.Mous   eEvent evt) {//GEN      -FIRST:event_clientsListMouseClicked
               propieta  rioText .s    etTe      xt((St      ri     ng   )clientsL  ist.getSele    ctedVal ue())   ;
    
    }//GEN-LAST:event_clientsListM    ouseCli        cked

        private voi   d clientsListKeyPressed(          java.awt.event.KeyEvent evt) {//GEN-FIRST:event_clientsListKey Pressed
           
                 }//GEN-LAST:event_clientsLis tKeyPressed

     private void searchPropi   etarioTextKeyPressed(java.awt.event.KeyEvent evt) {//GEN -FIRST:event_search     Propiet   arioTextKey   Presse d
            cList = taller.searchClien    te   s(searchProp  ietarioText.getText());
        clientsModel.clear();
         for(int i=0; i<cList.s  iz        e(); ++i){
            cl  ientsModel.addElemen t(cL   ist.get(i).getNomb     re());
           }
        clientsList.setM  odel(cl ientsModel);
    }     //GEN-LAST:event_searchPropietar   ioTextKeyPr     esse   d

    private void searchPropie   tarioTe  xtKeyTyped(j  ava.awt.event.Ke yEvent evt) {//GEN-FIR  ST:event_searchPropietarioTex   tKeyT      yped
             cList = taller.searchClientes(searchPropietar ioText.getText()  );   
            clients Model.clear ();
          for(i     nt i=0; i<cList.si  ze(); ++i){
               cli      e   ntsModel.addElement(cList.get(i).getNombre(         ));
         }
         clientsList.setModel(clientsModel);
        }//GEN-LAST:event_searchPro   pietarioTextKeyTyped

    /**
       * @param         args                   the command line arguments
     */
    public static void main(String args[]) {
        /* Set the    Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc =" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not ava   ilable, stay with the default look and fee   l.
            * For details see http    ://downl  oad.oracle.com/javase/tutorial/uiswing/lookandfeel/pla      f.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.ge  tInstalledLookAndFeels()) {
                if ("Nimbus".equa ls(info.getNam   e())) {
                    javax.swing.UIManager   .set LookAndFeel(info.getCla   ssName());
                        break;
                        }
              }
        } catch (ClassNotFoundException ex) {
                 java.ut     il.l ogging.Logger.getLogger(crearCocheWnd.class.getN   ame()).log(jav     a.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationExce   ption ex) {
            java.util.logging.    Logger.getLogger(     crearCocheWnd.class.getName()).log(java.util.logging.Lev el.SEVERE, null, ex);
        } catch (  IllegalAccessExc     eption ex) {
              java.util.logging.Logger.getLogger(crearCocheWnd.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);     
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
                  java.util.logging.L   ogger.getLogger(crearCocheWnd.class.getName()).log(java.util.logging.Level.SEVERE, null,    ex);
        }
            //</editor-fol          d>

        /* Create and display the dialog */
        java.awt.EventQue     ue.inv  o   keLater(new Ru  nnable() {
               public void run() {
                   crearCocheWnd dialog = new crearCocheWnd(new javax.swing.JFrame(), true,null);
                dialog.addWindowListener(new ja    va .awt.event.WindowAdapter() {     
                    @Override
                    publi  c void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }
  
    // Variables declaration - do n   ot modify//GEN-BEGIN:variables
    private javax.swing.JList clientsList;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToggleButton jToggleButton1;
    private javax.swing.JToggleButton jToggleButton2;
    private javax.swing.JTextField marcaText;
    private javax.swing.JTextField matText;
    private javax.swing.JTextField modeloText;
    private javax.swing.JTextField numBastText;
    private javax.swing.JTextField numKmText;
    private javax.swing.JTextField propietarioText;
    private javax.swing.JTextField searchPropietarioText;
    // End of variables declaration//GEN-END:variables
}
