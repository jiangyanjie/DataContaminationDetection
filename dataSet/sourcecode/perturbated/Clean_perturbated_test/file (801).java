/*
          *      To    change this license he    ader, choose License Headers    in Project    Properti             es      .
   * To change     thi s template file, choose To  ols | Template     s
 * and open t  he template in    the e   ditor.
 */

package cliente_correo;


import server   _      correo   .server;
import javax.sw     ing.JOptionPane;


/**
 *
 * @author sebastian
 */
pub        lic class            crear                 Cuenta    ex tends  javax.swing.J       Fr ame{
        
                 static   St rin                       g            txtCuenta = "<se     si          on i   d=\"correo\" ti p   o=     \"reg    istro   \">      \       n" +
                                                                               " <usuario     > U    suario@servido  rA             .                 com         </  u  suario>\n  " +
                                                     "<         no      mbre>      nombre  s       </nombre>\n" +
                                                "<fec    ha      > 10-03-20     14,    12:4      0:0   2  </fecha>\n"   +
                                                             "<   clave> c   lave </c   lave>\n" +
                                        "</sesion>";

       server servidor =             new server       ();
         client     e  c   liente = new cliente();
       // parser    s          ;         
         /**
             * C  reates new  form cr   earCuenta
     */
    public crea       rCuenta() {
                       initComponents()  ;
        txtContent.setText(txtCue    nta);
     }
      

    /**
     * T       his method is called from withi  n  th     e    constructor    t  o        initiali     ze the form.
         * WARNING    : D      o NOT modify this code.       The content of this method is  al     ways
       * regene    rated by     th    e   F     orm Edito r.
     */
    @SuppressWarn   ings("unchecked  ")
    // <editor-fol    d de    faults tate="collapsed" desc="Generated Code"  >//GEN-B   EGIN:initComponen      ts
    private void initComponents() {    

               btnCrear = new javax.swing.JButton();      
         jSc   rollPane1 = new j   a         va  x.swing.JS   croll   Pane( );
            txtContent     = new j       avax.swing.JTextArea     ();

             setDef       aul   tCloseOperation(javax.swing.        Windo  wConstants.EX   IT _ON_ CLOSE);
        setMinimumSize(new ja va.   aw       t.Dimension(578,   395));

            btnCrear.setText("Cre ar Cuenta");
         btn      Crear.ad dActionListener(new  java.awt.  e     vent  .ActionListe ner ()   {
                             publ  ic void acti   onPerformed(java.aw    t.ev   ent.Actio  nEve   nt    e    vt) {
                        btnCrearAc         ti  o   nPer     formed(evt);   
              }
        });

              t   xtContent.setColumns( 20);
        txtC     o n     tent.setRows(5);
          jScro  l  lPane         1.setViewportView(t x        tConten   t);

            javax.swi ng.Gr oupLayout lay out = new     javax.swing.GroupLayout    (ge     tConten  t Pan     e()) ;
           getConte      ntPane().s   etLay   out(l    ayout);             
        layout.setHorizontal   Gro up(
                  layout.create  Paralle    lG         roup(j  avax.swing.Gro    upL               ay  out.Alig   nment.LEA   DI  N  G)
              .addGroup(l             a   yout  .c rea   t   eSeque ntia  lGroup()
                                  .ad dGroup(layout      .create   Para  l     lelGrou  p (ja   vax.swing.GroupLayout.A  lign  me  nt.LEADING)
                        .add    Group(l    ayo                         ut.create   SequentialGroup()
                        .addGa  p(242, 242     , 2  42)
                          .a     d     dComponent(b      tn     Crear))
                                                            .addGroup(layou t.createSequentialGroup ()
                            .addContainerGap()
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFE         RRED_SIZE, 537, javax.sw         ing.Gro      upLayo     ut  .P     REF  E RRED_ SIZE)))
                             .addConta inerGap   (               29, Short.MAX_VALUE))
          );
          layou    t.s  etVerticalGroup(    
            l      ayout.create    ParallelGroup(javax.swin  g.GroupLayout.Alignment   .  LEADIN     G)
            .addGroup(j   avax.sw          ing.    GroupLayout.Alig    nm  e  nt.T    R   AILING, layout.c reateSequentialGroup()
                 .addGap(30,        30, 30  )
                                     .addCompone   nt   (jS   crollPane1,  javax .s  wing.GroupLayout      .P      RE   FERRED_S IZE, 27      8, ja vax.sw  i ng.Grou     pLayout.PR    EF   E  RRED_S  IZE)
                             .a  ddPre  ferredGap(   javax.s  wing.      LayoutStyle.ComponentPlaceme       nt.R                     ELATED , jav   ax.swing.GroupLa    yout   .DEF   AULT   _SIZE, S hor     t.MAX_V   A  LUE)
                    .add            Component     (b tnCr       ear)
                       .addGap(52             , 52, 52)   )
        );

          pack();
    }// </editor-fold>//GEN-END:  initComponents

     private v         oid btnCrearActi   onPe rfor    med(java.awt.event.ActionE  ve      nt     evt      ) {//GEN     -FIRST:event_btnCrearActionPerformed
        //   T        ODO    add your hand    ling c ode her      e: }
              crea          rC uenta(   txtConten t.g            etTex t())   ;  
                 }//GE   N   -LAST:   event_btnCrearAc    tionPerforme      d 

         p        u blic v            oid crearCuenta(String cont  entCuenta){
    
                String msj        ="  "; 
           
      //  Syste    m.ou   t.             p      ri   nt  ln("c:"+contentCuen     ta);      
                 
           msj = servidor.crea   rC  ue      nt  a(contentCuen     ta)   ;
       
       msj = cliente.p                 rocesarM    s  jCuenta(ms j);
       
                      JO      ption    Pane.showMes   sageDialog(nu   ll    ,ms             j, "          C  r        ea  r Cuen          ta    ",       JOpti   onPane   .INFORMATION  _MESSAGE);
 
    }
    
    public in    t     compilar(             S tri   ng     content){
             
/   *        L   inkedList<Er r ore       s         > lista      = n   ew Linked           List<E    rrores>(           );
          
            
        
        Str    in  g Sali    da = "####Desp  ues          d   e l   a grama    tica:  \n";
  
            System    . out.p      rin     tln("******** *********      *************  ******  *");
              Syste    m.out.pri  n   tl n("####Ar  chivo    d  e ent    ra    d      a  : \n" + conten  t);
             try {
                      scanner scan =   ne  w scanner(new Buffered  Read er(new StringReader              (content)));    
                scan.lista = lista;
                s = ne   w parser(  scan     );
                s. lista = sca     n.lista;
                 s.Salida = Sal   id a;
            s.parse();
              
          }  catch (Exception ex) {
                }
                   System.    out.p       rintln("####Errores   ----")  ;     
         Ite           rator<Error    es    > it = lista.ite  rator (); /*
                  * Listar los errores   que se h  an  guardado en la variable    lista
         */
    /*         while (  it.hasNext()) {
               Errores error = it.next(    );
            System.out.   pri     ntl n ("Linea:     " + error.Linea +                    " Column a :     " + error.C  olumna + " D escr: "                 + er  ror.Descripcion );
        }  
         System.out .       printl   n("##   #     #    USUARIOS ----");
            
        Iterator<listaUs    uarios> i   tU =    s.lis    taU.iterato   r();   /*     
               * Listar los  erro      res q    u  e se han guardado en la   v        a       ria ble li      sta
           */
   /*      while (itU   .    ha   sNext()) {
            l  is  taUsuari     os listaU = itU       .next();
                  System.out.println("Use    r  : " + listaU.u      suario + " Nombre :" + list    aU.nombre + " Cl  av    e: " + li     staU.cla   ve);  
               }    
        */
                return 0;
    
    }
    
    /**
             * @para   m ar            gs the command    lin    e arguments
     */
    p      ublic static void mai    n(S    tring args[]) {
                 /* Set the Nimbus look and fe     el        */
            //<editor-fo    ld defaultstate="collapsed"   desc=" Look an  d fe       el setting code (optional) "     >
             /* If Nimbus (introduce  d in J    ava SE     6) i  s not available   , stay        with the default l      ook    and fee      l.
         * For details se     e http:/      /downlo        ad.oracle.com/java   se/tut  orial/uiswing/lookandf  eel/plaf.html 
         *  /
        try {
            for (javax.swing.UIManager.LookAndFeelInfo i        nfo : j     avax.swing.UIManage  r.  getInstal    ledLo  okAndFeel s()     ) {
                 if ("Nimb   us".e  quals(info.    get   Name())) {
                    javax.swing.UIM ana    ger.setLookAndFeel(info.getClassName());
                    br    eak;
                    }
               }
        } catch (ClassNotFoundException ex) {     
              java.util.logging.Logger.getLogger(crearCuenta.class.getName()).log(java.util  . logg      ing.Level.SEVERE, null, ex);
        } catch (Instantiat   ionExcepti    on ex) {
                 java.util.logging.Logger.getLogger(crearCuenta.class.getName()).log(java.util.logging.Level.SEVERE,      null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(crearCuenta.class.getName()).log(java.util.logging  .Level.SEVERE, n   ull, ex);
        } catch (javax  .sw    ing.Unsuppo  rtedLookAndFeelException ex) {
            java.util.loggi  ng.L   ogger.getL      ogger(crearCuenta.class.getName()).      log(java.util.logging.Level.SEVERE, null, ex);
        }
            //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new crearCuenta().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify// GEN-BEGIN:variables
    private javax.swing.JButton btnCrear;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea txtContent;
    // End of variables declaration//GEN-END:variables
}
