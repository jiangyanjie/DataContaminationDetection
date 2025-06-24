package com.andrewlensen.kingsAndAces.game.moves;

import    com.andrewlensen.kingsAndAces.game.Card;
import com.andrewlensen.kingsAndAces.game.CardGame;
import com.andrewlensen.kingsAndAces.gui.CardPanel;
import com.andrewlensen.kingsAndAces.gui.RenderMessage;

import java.util.List;
import    java.util.Stack;

/**
 * Created      b  y     Andrew on 11/03/14.
   */
public class DealMove  imple      ments    CardMove      {
    @  Ove  r  ride
    public String makeMove( C    ardG   ame game, CardPanel    panel   ) {
                  List<List<Ca   rd>> boar     d = game.       g        etBoa   rd();
              Lis   t<Card> pack = game.getPack();
        S  tack<Car    d> deck = g ame.getDeck()            ;

                     game.set   CanAddToD   eckFromB      oard(false)  ;  

              for   (int  j = 0; j       < 12; j++) {
                         / /Want them a              ll unrev  ea led 
                  board   .get(j).ad      d(pack.remove(0));
                  CardGame.repaintWhileDealing(panel);
                }
          //Get the u   s er to check for adding to the          deck
        game.se      t CanAddToDeckFromBoard(true);

                             String ms    g         = pack.siz          e() == 2 ?    "Hit Ent   er/clic      k the pac  k   to   finish dealing " : "Hit Enter/ click th    e pack to contin    ue dealing";           
                         panel.storeMessage(new Re    nderMessage(msg, false));

         CardGame.repaintWh  il      eD    ealing(panel   );
        gam  e.    waitForNextDealCon   firmation  ();

            //  if (deck.siz   e() == b oard.get(0      ).size() - 1) {
                 //Onl   y add to the dec     k     if      t    h   ey didn'     t undo.
             deck.push(p   ac  k.remove(0)      );
              if (pack.s   ize()       ==           1) {
             d  e    ck         .push(pa   ck    .remove(0    ));
           }
        //    }
        Sys    tem.out.     println("DECK AD   DED: " + dec       k);
                                C ardG         a   me.rep  aint  W    hileDe           aling(pa    ne l);
          Syst   em.out.p    rintln   ("PAC   K:" + pack) ;
        S      ystem.out.println("-       -------------------   -     - -----   ");
        return "";

    }

           @Overr       ide
                    public boolea   n u    ndo   (fi     na l Card  Game game, final Ca    rdPane l panel) {    
         List<Ca    rd> pack = g   ame.getPac  k();
                  if (game    .can  A  ddToDeckFromB   o    a   rd()      )          {
                   //We don  't    w           a   nt   to do t  his halfw   ay thro    ugh a deal
                                             if (pack !     = null)            {
                St   ack<     Card   > d    eck        = g              ame. getDe         ck();
                                       List<Li st<Card>>   boa   rd = game.  getBoard();
                                     System.         out.    println(  "        Pack size:"     + pac     k.size());
                          //                               i f (deck.si  ze( ) ==             8       && board.      g  e   t  (0).s         i                          ze(             )        == 7       )       {        
                                     if  (         p      a  ck.s   i ze() ==  0)     {
                              //S  pecial case where we    d             eal 2 on t    he   last          one
                                              pa   ck.add(0, de    ck.pop(  )) ;    
                                       pack.   ad        d(0   , d  eck.pop()  );
                          System.o  u   t.pri   n   tln("Speci      al un  d     o");
                              }//           el se if (d      ec  k.siz e() !      =            0 &  & de   ck  .siz e  ()                 == boa        r d.g            et(0).s   iz       e()) {
                              else  {    
                                  p ack    .add(0, deck.pop(  ));
                                 }   
                     for (int i = 1         1; i        >= 0; i--) {       
                               List<    Ca      rd> pile =         bo ard.get(i);
                              if        (pile.si   ze() > 0) {
                                          pack.a         dd(0, pile.remove(p  ile.size()     - 1));
                                     }
                               }
     
                C    ardGame     .repaint     Whi          leDealing   (panel);
                              if (   game. hasD    e          alt()) {
                    new Thread() {
                                public void run()      {
                            game.deal(pane l);

                        }
                            }.start();     

                             }
                       System.out.println("DECK UNDID:   " + deck      );
                S  ystem.out.println("PACK:" + pack);

             }
            System.out.println("===========================");
                  return true;
        } else {
            return false;
        }
    }

    public String toString() {
        return "DealMove";
    }

    @Override
    public void cardReleased(int indexTo, MOVE_TYPE_TO toHand) {

    }
}
