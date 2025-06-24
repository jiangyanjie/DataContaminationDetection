/*
   *   To change    this template,  choose Tools |  Templates
 * an d open the templ     ate in         the editor.
 */

   /*
      * F ramePrincipal.j   ava
 *
    * C   reated on May   10, 2013, 12:5  4:40 PM
 */
package Presentacio;

import Domini.CtrDomini;
import javax.swing.JOptionP  ane  ;

/**
   *  
 * @author miquel.masriera    
 */
public class Cont   r       o    lad  orPresentacio extends javax.swing.               JFrame {
    
    CtrD   omin      i cd;
       String u   n     itatDocent;
    PanelLogin pLogin;
    Panel           MenuPrincipal   pMe     nup;
    PanelHorariLe ctiu    pHorariLectiu;
   
    p   ublic ControladorPresentacio() {   
         initCom    ponent   s();
           thi  s.se  tResizable(false);
                        th is.         setVisible(    true);
        pLogi    n   =   new PanelLogin(this);
            pMenup = n        e   w PanelMenuPrincip   al(this)    ;
           p    HorariLect iu = new Pan elHorariLectiu(this);

                   
                     th    i    s .Layered.add(pLogin);
                         this.Layered.add(pM enup);      
          th    is.Laye          red.add(pH         orariLectiu)      ;
        can   viaPanel("l  ogin");
    }
          
        
             /**    
     * el que  f    a     aques     ta funciÃ³ es   mo strar en el        fr  ame principal   el panell q    ue    se li
             * indica.
     * prime       r de tot f a invisibles tots els pa  nells i desprÃ©s n    om  Ã©s activa              el que 
             * s ha de mostrar 
     * 
     * @para             m   nomPanel nom        d       el pan    ell a     mostrar
     */
    public void canviaPanel(String nomP     a nel){
        pLogin.set    Visibl e(false        );
             pMenup       .setVi   sible(f     alse);
             pHorar     iLecti   u.setVisib  le(false)   ;

        i   f(nomPanel.equals("login    "))       pLogin.s   etVis   ib      le(true)  ;
             else if(nomPanel.equa    ls("menuPrincipal")     ) {
             pM en     up.setNom   Unit  atD     ocent(unitatDocent);
              pMenup       .setVisible(true);
        }
        else if (n omPanel.e     quals("PanelHorariLectiu   ")) pHorariL   ectiu.setVisible(true);            
    }

    @Supp  ressWarnings("unchecked")
    // <editor-fold defaultst     ate ="    collapsed" d  es  c=     "Generated Code">//GE   N-BEG     IN:initComponents
    private void initC omponents  () {

            Layere   d = ne  w javax.swing. JLayeredPane();

          setDefaultCloseOpera   t  ion(javax.swing.W     i          ndowCo  ns tants.EXIT_ON   _CL  OSE);

        javax   .swin g.GroupLayou           t   layout = new javax.swing.Group    Layout(getContentPane())    ;
            getCon   tentPane(  ).setLay   out(lay  out);
         layout.setHo         ri        zontalGroup(
                      layo     ut.createPara    llel  Group(javax.swin     g.Grou pLayout.     Ali      g   n     ment.LEADING    )
                 .add       Component(Layered,          javax.swing.G   roupLayout.DEFAULT_SIZE, 700, Short    .MAX_VALUE) 
           );
                 layout.se tVerticalGroup(
                     lay          o    ut.c reatePar   allelGroup(javax.swing.GroupLayout        .Alignment.     LEADING)
                .addComponent(Layered, j    avax.s  wing.GroupLayou    t.DEFA     ULT_SIZE, 550,    Short. MA    X_VALUE)
        );

        pack();
    }// </edi    tor-fold>//G         EN-END:initComponents  


    
        // Variab l       es declaratio  n - d        o    not mo dify//GEN-BEGIN:va  riables
    priv       ate javax.swing.JL   ayeredPan        e Layered;
    // E    n   d of variables de   claration//GEN-END   :variable s
   
    public void         mostraAvis(Str   ing    tex    t) {
         //PODEMOS TENER UN SEGUNDO PARA METRO QUE INDICARA QUE TIPO DE AVISO ES PARA MOSTRAR UN WARNING_MESSAGE      O OTR     O TIPO DE MENSAJE.
         //TIPOS DE MENSAJE: WARNING_MESSAGE, ERROR_   MESSAGE, PLAIN_MESSAGE, INFORMATIO N_MESSAGE
        JOptionPane.showMessageDialog(this, text, "GeneradorHoraris::ERROR",   JOptionPane.WARNING_MESSAGE);
    }

    pub  lic void identificarUnitatDocent(Strin  g nomUnitatDo  cent) {
        System.out.println(nomUnitatDocent);
        unitatDocent = nomUnitatDocent;
        cd = new CtrDomini(unitatDocent);
    }
}