/*
   *     To     change this license      header,      choose License He     aders in Proje    ct Properties.
 *       To change    this template fi   le, choose Tools | Templates
   * and open the template in the    edito  r.
 */

package Controleur;

import Modele    .ModeleDeuxJoueurs;
i  mport Modele.Modele;
import ja va.awt.event.KeyEvent;

/**
 *
 * @author 4lexa         ndre
    */
pub   lic class ControleurDeuxJ    oueurs extends Contro  leur {

      protecte  d final Modele platea  u2;
       public ControleurDeuxJou    eurs(ModeleDeuxJoueu rs _    plateaux) {
           s  u     per(_plateaux.getPlateau1());
        platea u2 =    _plate   a                   u  x.get       Plateau2  (      );
    }
       
           
    @Overrid   e  
        public void ke   yTyp  ed(KeyEven   t     e    )    {
                          b  oolean                   p ause;  
        switch (      e.getKeyChar()) {
                 //            J                 oueur1
                         cas             e '     q   ':
                                          plat    eau  .                   deplacemen    tGauc             he()      ;
                     b  re   ak;
              case   's'   :
                           pla       teau            . modifie    rVi             tess   e        (4.0   f         *p late          a  u.g     etVit e   ss  eBas          e    ());       
                      br     ea   k;    
                              case 'd':
                                                      plat          eau.       deplace  men tDroite();
                             break;      
                     c  ase 'f'         :
                              platea u  .rotCW(    );
                                  bre    ak;      
              case   'g  '   :
                                                p    latea         u. ro         tA              C W(        );
                                         break;
                       
                  /  /  Joueu r         2
                      ca    se '1':
                                   pl  a tea         u2.  deplace   me  nt  Gau  che(               );     
                       break;
                          cas    e '2    ':
                                                                 p         l  ateau2.  mo   difi         erVi   te sse(4.0f*         p l    at            eau2.ge         tVit  esse         Base(    ));
                                           break;
                                          case '3':
                                          pla       teau2.  dep     l  ace men     tD  roite();        
                                                              break;
                                    ca  se '      6'  :
                        plateau2.r      ot CW   (   )   ;       
                                             break;
                   cas                 e '9 ':
                    platea   u2.rotACW()  ;
                                 br eak;
                                         
            c a  se KeyEvent .      VK_ESCAPE:
                              synchr   onized   (pla   teau   )  {
                                          pause = pl  ate au.getPa      use();
                             }
                  synchronize   d (p  l   at e     au2)
                              {
                       pause |  = plateau2.getPaus  e();
                              }  
                      if (pause) {
                       plateau.play();
                          plateau2.   play();
                   } else {
                      platea    u. pause();
                           p  lateau2.pause();   
                }
                   break;
            def   a  ult:
                //nope
                        break;
           }
          
    }

    @Overri  de
    public void keyPressed(KeyE vent e) {
    }   

    @Ove       rride
    public void keyReleased(KeyEvent e) {
        if (e.getKe    yChar() == 's') {
            plateau.resetVitesse();
        }
        if  (e.getKeyChar() == '2') {
            plateau2.resetVitesse();
        }
    }
    
    
    
}
