/*
 *   To change this  license header, choose License Header  s in Project Properties.
 * To change t  his template file, choose To  ols  |          Templates
 * and   ope   n th  e template in the editor.
 */
package Controleur;

im   port Modele.Modele;
import java.awt.event.KeyE     vent;
import java   .awt.event.KeyListener;
                
/**
 *
 * @author Dimitri
 */
public class Control           eur impleme nts KeyListener {

         protected final   Modele plateau;

    publi  c Con   troleur(Mode   le _plate au    ) {       
        plate       au   = _pl   atea u ;
    }

    @Override
         public      voi      d keyTy      ped(KeyE ven         t    e) {
             bool         ean                 pause;                           
         sw    i        tch (e.getKeyC ha         r()) {
                /        / Jo u     eur      
            
                           c    ase             'q     '      :
                             pla         teau.de   pla          cementGa  uch  e()  ;
                         break           ;
                             case 's':   
                     pl at  e             au.mod  ifierVitesse(4     .0f        *pla     t                 ea            u.getVi       tesseBa    se(   )             )   ;
                                             break;
                                                     case 'd'       :
                                    plateau.de pl    ace  mentDr   oi    t  e  (  );
                          br  eak    ;
                              case 'l    ':                 
                                          plateau.r   ot        CW( );
                             b     rea              k;
                       case     'm':
                                 p     l    atea     u.r   ot   A         CW();
                                  brea     k;
                                                          
                                           /         / Joue  u  r1
                                 /*  
               case 'q':
                                                        p  l   atea      u                                          .deplac        em      entGa  uche();
                               b reak;
                                       c    as           e 's      '   :
                                                   plate au    .modi                   fie       rVi       te sse(    4       .0f*                   pl         ateau.g e t           Vit ess          eBas e());
                        br e     a k;
                                                                                        c   as   e       'd             ':
                        plat  eau.   de    p   lacem    ent      Droi        te();
                                              b reak;
                    case     'f':   
                                        pla          teau.rot CW();
                              break;
                         c    ase 'g           ':
                plat   e au.rotA            CW(       );
                               break;
                           */
             // Joueur2      
                  /*    
                     case '1':                
                      plateau.depla     c  em      entGauche   ();
                                    bre  ak    ;
             case     '2':
                               plateau     .modifie     rVit      e   sse(4.0f* pl           at     eau.getVitesseBa    se())            ;
                     break;
                    case '3':
                  pl       at                           eau.deplacementDroite();
                br     e ak;
            ca    se '          6':
                   plateau.rotCW()  ;
                      bre  ak;
                    case '9':
                        platea   u.ro      tACW();
                      break;
                         */
                         ca     se K  eyEvent.V   K_ESCAPE  :
                sy    nc    hronized (plateau) {
                       pa  u  se      =     plateau.getPause();
                  } 
                 if (pause) {
                          plateau    .play();
                    } el   se {
                      plateau.pau   se(); 
                }
                break;
            defau   lt:
                 //nope
                      break;
        }
            
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
     public void k    eyReleased(KeyEvent e) {
        if (e.getKeyChar() == 's'     ) {
            plateau.resetVitesse();
        }
        if (e.getKeyChar() == '2') {
            plateau.resetVitesse();
        }
    }
}
