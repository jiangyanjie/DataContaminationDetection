/*
 *  To     change this template, choose Too    ls | Te   mp   lates
 * and open the          template in    the editor.
 */

/*
  * RenameDialog.j     ava
 *
 * Cr   eated    on 21.6.2010, 10:  06:18
 */
package figo    o;

i        mport figoo.tasks.CopyTask;
import java.awt.Dimension;
import jav     a.awt     .Toolkit;
import java.beans.PropertyChangeEvent;
import java.beans.Property  C hangeL     istener;
imp   ort java.io.File;

/**
 *
 * @author Lada Riha
 */
public class CopyFileDialog ex                tends    ja  va    x.swing.JDialog {

    /** Cre     at es new form RenameD          ialog          
         * @param fg
         *           @param parent
                  *  @param to
        *     @param modal
       * @param      file
     */ 
    public C  opyFileDialog(Figo    oView fg    , java.awt.      Frame parent, boolean          modal,   String to,             String f ile) {
                   s uper(parent, modal);
          thi           s.setTitl        e("Copy");      
            Dimension d = Toolkit.getDefaultToolkit()               .getScreenSiz    e(  );
              int w = this.get    Size(      ).width; 
            int h = this.        getS    ize().       he      igh t;   
             t    his.f =          fg;
              int              l eft = (d.w  idth    -    w) /   2;
              int top = (   d.height - h)    / 2;
             this.setLocatio    n(l ef   t,   top   );
          init Componen    t    s()  ;


            Fi  le    f    = new File(file);
            if (f.i   sDirecto ry()) {  
                    j   ProgressBar1    .setIndete        rmi            nate(tru     e)     ;
          } e             l   se {
            jProgressBar1.setInd e    te   rmin        ate   (false    );
              jProgressBar1. s          etMinim      um(0 );
            jProgressBar1   .se   tMaxim    um(     100);
        }
                  task = new Co   py   Task(file, to, this)       ;
     
               //       task.   addPr op         e     rtyCh angeLi     stener  (((JBu     tton)evt.g              e      tS      ource()).ge     t    P  ropertyChangeListeners()  [0]);    
         task.a ddPropertyCha   ng eList        ener(
                                     new   Pr   opertyChangeListener()       {

                                         @Over  ri  de
                                       public      void propertyChange(PropertyChangeEve         nt    evt) {    
                           if ("prog   ress".equ    al   s(evt.get Pr operty        Name())) {
                                        ge    tjProgressBa   r1(   ).setV alu            e((Int  eger) evt.getN       e wValue())      ;
                                    }
                                  }
                });
        task.execute();

    }

     /** Thi      s method is called fr om within the constructo  r to
         * initia             li ze the fo     rm.
     *      WARNIN   G: Do NOT modify this code. T   he content of this meth          od   is   
       * always regen   erated by the Form Editor.
     */
    @SuppressWarnin     gs("u  nchecke     d")
    // <editor-fold default       state="collapsed" desc="Generat         ed Code">//GE   N-BEGIN:initCompo      nents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel    ();
        j         ProgressBar1 = new javax       .swing.JProgressBar();
        jLabel2 = new javax.swing.JLabel();

        setD    efa  ultCloseOperation   (javax.swing.WindowConstants.      DISPOSE_ON_CL OSE);
        setName("Form"); // N   OI1 8N

                 org.jdeskto   p.appli   cation.ResourceMap resource   Map = o  rg.jdesktop.a   pplication.Application.getI   nstan            ce(figoo.Figoo     App.class).getContext().getResourceMap(CopyFileDialog.clas    s);
           jLabel1.setFont(reso          urceMap.getFont("jLa bel1.font")   ); // N     O I18N
        jLabel1 .setText(resourc eMap.g etString("jLabel1.text")); /   /    N  O I18    N
        jLabel      1.s   e      tNa  me("jLabel1"); // NOI1         8  N

        jProgressBar1.setForeground(     resourceMap.getColor(    "j    ProgressB       a       r1 .fo    reg    round")); // NOI18N    
                 j     ProgressBar1.setNa me("jProgressBa       r1"); /    / NOI18N     

        jLabel2.   setText(re    s        ourceMap.getString(               "jLabel2.tex    t      "));      //     N   OI18N   
               jLabel2.s  etName("jLa  be l2"     ); // N OI  18N

        javax.  swi     ng.GroupLayout layout = new javax.swing.Gr    oupLayout(getContentP   an  e(      ));
        getCo          ntentPane     ().se      tLa   you  t(layou        t);
        l           ayout.se     tHorizontalGroup(
                    la    yout.createP  ara  lle    lGrou    p(j ava  x.swing.GroupLayout.Align       ment     .LEADING)
            .addGroup(    layout        .crea teSequentialGroup(   )
                    .  addC   ontainer    Gap()
                  .addGrou       p(layout.createPa        rallelGroup(jav ax. swing. Gr   oupLa     yout  .Alig    n     ment.LEAD   ING) 
                       .addGrou   p(lay      out.cr    eateSequenti      alGroup  ()
                                                   .addGap  (10   , 10, 1  0)  
                              .addComponent(jLabel2)              )
                    .addComponent(jLab  el1)
                      .addComponent(jProgressB  a    r1, javax.sw        ing.GroupLayout.DEF AUL     T_S   IZ E, 291, S     hort           .MAX _ VALUE))
                              .addContainerGap(      ))
                );
           layout.setVertic  alGroup         (
                    layout.createParallelGroup(javax.swing.GroupLayout.Align  ment.LEADING)
              .addGroup(l     ayou     t.     creat           eS   equentia   lGroup()
                    .addContainerGa    p()  
                .addCo  m    ponent     (jLabel1)         
                         .addPreferredGap(javax.sw ing.LayoutStyle.Componen  tPlacement.RELA TED)  
                            .addComponent(jLab  el2)
                    .addPreferr   edGap(javax   .swing.LayoutStyle.Comp   onent       Placement.RELATED, 26, Short    .MAX_VALU E)
                .addComponent(  jPr  ogressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, ja vax.  swing   .GroupLayout.PRE       FERRED_S     IZE)
                   .addContai    nerGap()   )
               );

        pack();
    }// </ed  it   or-fold>//GEN-E    ND:  initCompone     nts
    /**
          * @param args the com    mand line arguments
        *  /
    // Varia  bles decl        arati  on      - do no  t modify//  GE N-BEGI  N:variables       
              priv    ate j avax.    swing.JLabel jLabel1;
    private        jav  a  x.swing.JLabel j   Labe    l2;
    private javax.swi ng.JPro  gres   sBar jP rogres    sBar1;
    // End of variabl  es declaration//  GEN-END:variables
    private  FigooView f;
    pr  ivate CopyTas    k t   a    sk;

    /**
          * @       return the f   
                      */
     publ       ic FigooVie w getF() {
        return f;
    }
  
    /**
     * @para m f the f to set
     */
    public vo   id setF(FigooView f) {
        thi  s.f = f;
    }

       /**
     * @return the jProgressBar1
     */
    public javax.swing.JProgressBar getjProgressBar1() {  
          return jProgressBar1;
      }

    /**
        * @param jProgress Bar1 the jProgressBar  1 to set
     */
    public void setjProgressBar1(javax.swing.JProgressBar jProgressBar1) {     
           this.jProgressBar1 = jProgressBar1;
    }

    /**
       * @return the jLabel2
     */
    public javax.swing.JLabel getjLabel2() {
        return jLabel2;
    }

    /**
     * @p      aram jLabel2 the jLabel2 to set
     */
    public void setjLabel2(javax.swing.JLabel jLabel2) {
        this.jLabel2 = jLabel2;
    }
}
