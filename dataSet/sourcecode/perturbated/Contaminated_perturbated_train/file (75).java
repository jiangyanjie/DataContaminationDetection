/*
        * Author:       Andreas    Bjoru, abjru20     12@my.fit.edu
 * C ourse: CSE        4051,      Fall   2013
    * Project: pr   oj08, La mbda Lifting Game
 */
package impl.display;

import static impl.utils.UIUtils.di  splay;
import static javax.swing.JOptionPa ne.PLAIN_MESSAGE;
import static     javax.swing.JOptionPane.showMessageDialog;
import impl.utils.Tuple;

import java.aw     t.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.EventListener;
import java.ut       il.ResourceBundle;

import api.IGa     meController;

/**
 * Abstract factory responsible  fo        r creating different {@link EventListene    r}'s.
 * This class fo   llows the 'abstract factory' patte   rn.
 */
public    abstract class   Ab  str   actEventFactory {

      /**
    * D  efines a      factory capable of   creation {@l    ink ActionList    ener} instances.   
    */
        interface EventFactory<T   extends EventListener, V     > {
      public T creat  e (fin       al  V input  );
   }
    
              /**
      * Returns a factory cap   able of cre   ating an action  that disp oses the    main
    * di  splay (i.e.   exits the swin    g event loop).
           */
        pu    blic static  f inal   Eve ntFactory<A    ctionListener, Tup  le<? extends Co          nta     iner, ResourceBundle>>   EX  IT_ACTION =      
         new E  ve   ntFa    ctory<Act    ionListene   r, Tuple<? exte   n ds Con       taine   r, R  esourceBundl   e   >>  (   ) {

                       @         Over   rid e
              public      ActionList     ener create (
                                  fin     a      l Tuple<? extends   Cont  ainer,    Re sou          rceBundl   e>  in          p       ut) {
                 re    turn new                  Act  ionList    ener (             ) { 

                       @ Override    
                                           public    v   oid actio            n  Performed (f  inal Act   i  on   Event    e) {
                                     display (i  nput.   _1).dispos    e ();
                           }
                             }     ;
             }
            } ;

   /*    *
    *    Ret   u   rns       a factory capable of creatin     g an action th      a  t    di         sp   lays       the about dialog.
     */
         publ             ic static final E ve  nt    Factor   y          <Act    io   nListener,              Tuple<? ex         tends Cont ai   ner, Re     sourceBundle>  > ABOU   T_DIA    LOG_A CTION       =
         new Ev       ent   Fac    tory <       Acti    onList          en     er, T    up      le<? extends Container, Resou   r  ceB undl            e>> ()     {    

            @O verride
                                      p ublic Act  ion         Liste                   ner cr   eate (
                               f    inal  Tup le    <? extends Co ntainer, R  esource    Bund   le> i      n) {
                return   new Acti    onL ist  ener () {

                                                      @Ove         rr  i    de   
                           public       void ac    t       io      n   Perform   ed (f inal ActionEvent e) {
                                          final String ti  t  le = in._2.g    e     t   Stri      ng ( "dlg.about.title");
                                             final St  ring m  es   sage       = in    ._2 .getStrin   g ("dlg.about.text");
                                     showMessageDialog (dis       play          (in   ._1)  ,   m es  sage, tit  le, PLAIN_MESSAGE)     ;
                          }
                             };
                   }      
                  };

               /**
               * Retur   ns a f       actory capable of          c     reat ing a key           l        isten          er    th  at   ac  ts u   pon
         * pred    efined keys    in   order to cont    rol t   he game.
             */
   p     ubl    ic       sta    tic      fi  n             al Eve  ntF                  actory<Ke          yListener, IGame      C  ont     r   oll       er>           KE    Y_CON   T   ROLS               =                    
                                new E                    ven          tFactory<K  eyLis    tener, IG         a       m    eC          ontroller> ()              {     

                          @O  v  e       rri de  
                        public     Key         Lis          tener create (final     I   Game  Contr      oller     co    ntroller    ) {
                                         r     eturn    new       KeyAda     pter () {                  
                        @Ov    erride
                                         pu     blic       void     keyRelea          se       d (final KeyEvent e)         {
                                         fi    n    al int key = e.          getKeyCode  ( );
                            s    wi      t  ch (key) {   
                          case     KeyEven  t.VK_DOWN:
                                   c ase K    eyEvent.VK_D:
                                                        co          ntroller.moveDown    (          );
                                        break    ;
                                        case Key  Event.VK_UP:
                                   case K        e   yEv  ent.VK_U:
                                    contro    ller.moveUp    ();
                           break;
                                             cas   e K     eyEvent.VK_LEFT:
                          case KeyEv  ent.VK_L:
                                controller.mo   veLeft (); 
                                 br    eak;  
                                  case Ke yE vent.V  K_RIGHT:
                            case Ke      yEvent.VK_R:
                        controller.moveRigh t ();
                                     break;
                             case Key Event.VK_W:  
                               con  t     roll er.moveWait  ();
                               break;
                        case KeyEvent.VK_ESCAPE:
                     case KeyEven  t.VK_A:
                          controller.abort ();
                        break;
                     default:
                     }
                    }
               };
            }
         };

}
