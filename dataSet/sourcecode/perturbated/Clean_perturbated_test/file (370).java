package  com.live.adsfatene.biblioteca_publica.views;

import com.live.adsfatene.biblioteca_publica.controllers.AplicacaoController;
import java.sql.SQLException;
import javax.swing.JFrame;

publi    c clas      s Ap   licacaoView extend    s jav ax.swing.JFrame  {

       private fi  nal  AplicacaoController aplicacaoCon   troller  ;
       privat  e final SobreView sobreView;

    public AplicacaoView(AplicacaoControlle        r aplicac     aoCo ntroller) {
        in  itComponents  ();
                      thi s     .aplicaca   oController = aplicaca  oContr    o         l   ler;
        sobr eView = new SobreView(thi s, tru    e);
        sob  reView.setResiz abl   e(  false);
        sobreView.setDefaultCloseOper     at ion(JFrame   .HIDE_ON    _CLOSE  );
     }

    /**
     * This method is called fr   om within   the constructor to        initi     aliz       e the f    orm.
     * WARNING: Do NOT m   odify th    is    c    ode     .    The content of this method is always
     * r    egenerate   d b     y the Form Editor.  
     */
       @Suppre    ssWa   rnings(   "unchecked")
    // <edito r-fold defaultstat    e="collapsed" des       c=   "Ge     n   er  ated Code">//GEN      -BEGIN       :initComponents
    private     void initCompo nents() {
     
         jMenuBar1 = new javax    .swing.    JMen uBar();
            jMenuOpc      oes = new javax.swin    g.JMenu      ();
        jMenuItemInicio       = ne     w javax.swing.JMenuItem();
                    jS   epar    ator   1 = new javax.sw   ing.JPopupMenu.Separat       or();   
           jMenuItemMateriais     = new javax.swing.JMenuI     te         m();
        jMenuItemEst       oques = new javax  .s   wing.JMenuIte  m();
             jMenuI  temCidadao   s = new javax.swing.J  MenuItem();
        jMe    n uItemEmprestim      os = new javax.swing.JMenuItem();
        j     MenuItemDanificados = new java  x.s        win   g.JMenuItem();
        j     S    eparator    2 = new javax.swing.    JPo    pupMenu.S      eparator();
        jMenuItemSair = new javax.swing.JMen     uItem();
        jMenuAjuda =      new javax.swing   .JMe   nu();
        jMenuItemSobre = new ja  vax.swing.    JMenuItem  ();
   
          setDefau l   tCloseOperation(   javax.swing.WindowConstants.DISPOSE_O N_CL   OSE);

              jMenuOpcoes.setText("OpÃ§Ãµes");  
     
        jMenu   Item     Inicio.setAcc   e  lerator(jav  ax.s wing.Key     Str   oke.getKeyStro          k  e(    java.awt.eve  nt.K    eyEvent.VK_F1, 0    ));
           jMenuItemInicio.setText("InÃ­cio");
        j   MenuI temInicio.a       ddActionListener    (n ew java.awt.e vent.Actio     nListener() {
                public void actionPerformed(   java.awt.ev   ent.ActionEvent   evt)      {    
                       jMenuItemI  n i    cioAction  Perf           ormed(ev    t);
                      }
        });
         jMenu Opcoes.add(jMen    uItemInicio);
           j MenuOpcoe   s.add   (jSeparator1);

          jMenuItemMateria is.setA    cceler     ator(javax.swing.Ke  yS   t   roke.getKeyStroke(java.aw  t.event.KeyEven  t.VK_F2, 0));
        jMenuItemMater  iais.    setText("Mater   iais")           ;
        jMe  nuIt   emMateri   ai   s.addActionListene    r(ne        w j  ava.awt.eve    nt.Actio    nListener() {
             public  v    oid a       ct    ionPerf    ormed                  (ja   va.a  wt.event.Act     ionEvent evt) {
                 jMenuItemMateria     isActionPerformed(evt);  
             }
        });
        jMen    uOpcoes    .             add(jMenuI t    emMateriais);
  
        jMenuI           temEstoques.setAccelerator(javax.swing.KeyStroke.getKeySt    roke(jav      a.awt.event.     K   eyEvent.VK_F3, 0));
        jMenuItem    Estoques.setT        ext   ("Esto    ques");
         jMenuItemEstoque     s.ad dActionListener(new      java.awt  .ev     ent.A      ctionL  istene   r() {   
                public     void actionPerformed(java   .aw     t.event  .ActionEv  ent     evt)        {
                         jMenuItemE       stoquesAct     ionP       erformed(evt);
            }
             });
            jMenuO  pcoes.add(jMenuI      te     mEs          toques);

        jMenuItemCidadaos.setAcc  elera    tor(javax.swing.Key  Stroke.getKey  Stroke(java.awt.event.KeyEvent.V   K_F4, 0 )) ;
        jMenuItemCidadaos.setText("  C idadÃ£os");
           jMenu   ItemC   idadaos.addActionListener(new jav  a.awt.event.ActionListener(      ) {
                   public  void ac     tionPerformed(java.awt.event.ActionEv ent evt       ) {
                jMenuItemCidadaosAction    Performed(evt);
             }
             });
              jMenuO     pcoes.ad   d(jMenuI  temCidadaos);

         jMenuItemEmp   resti mos.setAccelerator(   javax.swing.KeySt   rok    e.get    KeyStroke(j      ava.   aw    t   . event.Ke    yEven    t.VK_  F5, 0))         ;
        jMenuItemEmprestim     os.setText("Empresti     mos");
         jMenuItemEmprestim         os.a     ddA  ctionLis   tener(new jav      a.awt.event.Actio   n  Liste     ner() {
                  p    ublic void acti  onPer     fo    rmed(java.awt.      event.ActionEvent        evt) { 
                  j  Men uItemEmprest  imosActionPerf        ormed(evt);
                  }
                  });
        jMenuOpc      o es.     add(jMen    uIt emE        mprestimos);

        j  MenuI   te   mDanificados   .setAccelerator(javax.swing.KeyStroke .getKeyStroke(  java    .a  wt.event. KeyEvent   .VK_F6, 0));
        jMenuItemDanificado       s.setT     ext("Dan   ificados");
             jMenuItemDani  f   ica dos.addActionList ener (new java.awt.        event.Actio   nL        istener()     {
                  public void              actionPe     rformed(java.awt.event.Act        ion  Event evt) {
                   jMenu   It  emDanificado  sA    ctionPerformed(evt);
               }
        });
        jMe   nuOpcoes.add(jM   e             nuItemDanif    icados);      
            jMenuOp  coes.add(jSeparator2);

        jMen   uI               temSair.setAcceler ator(javax.swin g.KeyStroke.getKey     Stro         k   e(java.awt.e   vent.KeyEvent.VK_F4 ,   ja va.awt.event.Inpu      tE  vent.ALT_MAS   K  ));
              jMe    nuItemSair.setTex t("Sair"        );
        jMenuItemSair.add  Act ion  Listener(new      java.awt.even t.ActionListener() {
                  pu   blic void actionPerformed(ja    va.awt.event.ActionEvent evt) {
                     jMenuI  temSairActionPerformed(evt);
                                }
        });
        jMenuOpcoes.add(jMenuItemSair);

          jMenuBar1.add(jMenuOpcoes);

                 jMenuAj uda.se     tText("Ajud       a")     ;

        jM   enuItemSobre.setText("Sobre"   );
            jMenuItemS    obre.addActionListener(new java.awt.event    . ActionListener    () {
               public  void actionPer  formed(java.awt.event.Ac     tio     nEvent evt)  {
                jMenuIt    e    mSobre ActionPerfor  m      ed(evt);
            }
              })    ;
          jMenuAjuda.add(jMen uI   tem   Sobr   e);

           jMenuBar     1.add(jMe   nuAjuda);

           setJMenuBar(jMenuBar1);

        javax.     swing.GroupLayout layout = new jav  ax.swing.GroupLayo   ut(getC ontentPane());
        getC   ont    entPane().set  Lay   out(layout);
                    layout.setHor    izontalGroup(
            layout.createParallelGroup(javax.swing      . Gr  ou     p    Layou                t.Al ignment.LEADING)
             .addGap(0         , 400, Short.  M        AX_VALUE)
              );
         layout.setVerticalGroup(
            layo  ut.createPar   all       elGroup(javax.s        wing.GroupLayout.Al ignment.LEADI       NG)
              .addGap(0, 279, Short.MAX_VALUE)
           );

        pack(    );
          }// </editor-f    old>//GEN-END:initComponents

    private void jMenuIt        emMateriaisActio  nPerformed(java.awt.eve  nt.ActionEvent evt) {//GEN-FIRST:event_jMenuItemMateriaisActionPerformed
        aplicacaoController.getMateriais          Con    troller().iniciar();
              }//GEN-LAST:eve  nt_jMenuItemMateriaisActionPerformed

       pr  ivate void jMenuItemInicioActionPerf       ormed    (  java.awt.e  vent.ActionEvent evt) {//GEN-FIRST:event_jMenuItemInicioActionPerformed
        aplicacaoControlle      r.iniciar();
    }//GEN-     LAST:event_jMen   uItemInicioAct   ionP   e    rformed

    @Override    
    public vo   id dispose() {
        try {
            ap    licacaoController.getConexao().getConnect ion()         .close()       ;
                  }      catch (   SQLE    xception ex) {   
            throw new RuntimeException(ex.g    etMessage());   
        } f  inally {
            s  uper.dispose();
        }
    }

    private void jMenuItemSa  irActionPerformed(java.awt.eve    nt.ActionEvent    evt) {//GEN-FIRS         T:event_jMenuItemSairActionPerf     ormed
        di spose();
        }//GEN-LAST:event_jMe     nuItemS   airActionP erformed

    private void jMe   nuIt  emSobreActionPerfo  rme d(java.awt.event  .ActionEvent evt) {//GEN-  FIRST:event_jMenuItemSobreAct  ionPerf    orm  ed   
        sobreView.setLocationRelativeTo(this);
        sobreView  .setVisible(t  ru   e);
        }//GEN-L AST:event_jMenuItemSobreAc    tionPerforme   d

    private void jMenuItemEstoquesActionPerformed(java.awt.even    t.ActionEvent evt) {//GEN-FIRST:event_jMenu     ItemEstoquesActionPerformed
        apl icacaoController.getEstoquesController().ini   ciar();
       }//GEN-   L AST:event_jMenuItemEstoquesActionPerformed

    private void j   MenuItemD    anificadosAct    ionPerformed(java.aw    t.event.Acti     onEvent evt) {//GEN-FIRST:event_jMenuItem     DanificadosActionPerformed
           aplicacaoController.g   etDanific adosCont roll er().iniciar()   ;
    }//GEN-LAST:event_jMenuItemDanificadosActionPerforme  d

        private void jMenuItemCidadaosAc    tionPerf     ormed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuIte mCidadaosActionPerformed
        aplicaca  oController.getCidadaosController().i    ni  ciar();
    }//GEN-LAST:event_jMenuItemCidadao    sActionPerformed

    private        void jMenuItemEmprestimosActionPerformed(java  .awt.event.ActionEvent evt) {//GEN-FIRST:ev    ent_jMenuItemEmprestimosActionPer     formed
             aplicacaoController.getEmprestimosController().iniciar();
    }//GEN-LAST:event_jMenuItemEmprestimosActionPerformed

    // Variables decl aration     - d   o not modify//GEN-BEGIN:variables
    private javax.swing.JMenu jMenuAjuda;    
    pr    ivate javax.swing.JMenuBar jMenuBar1;
    private javax.swing  .JMenuItem jMenuItemCidadaos;
     priva     te javax.swing.JMenuItem jMenuIt  e   mDanificados;
    pri   vate javax.swing.JMenuItem jMenuItemEmprestimo  s;    
    private javax.swing.JMenuItem jM  enuItemEstoques;
    private javax.swing.JMenuItem jMenuItemInicio;
    private javax.sw   ing.JMenuItem jMenuItemMateriais;
       private javax.swing.JMenuItem jMenu    ItemS  air;
    private javax.swing.JMenuItem jMenuItemSobre;
    private javax.s  wing.JMenu jMenuOpcoes;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    // End of variables declaration//GEN-END:variables

    public void habilitar(boolean aFlag){
        jMenuOpcoes.setEnabled(aFlag);
    }
}