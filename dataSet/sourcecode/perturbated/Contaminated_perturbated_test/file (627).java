package    com.andrewlensen.kingsAndAces.game.moves;


import com.andrewlensen.kingsAndAces.game.Card;
import com.andrewlensen.kingsAndAces.game.CardGame;
import com.andrewlensen.kingsAndAces.game.Hand;
import com.andrewlensen.kingsAndAces.gui.CardPanel;
imp    ort com.sun.istack.internal.Nullable;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

publi   c class DeckClickMove implements Card  Move {
        pri   vate Hand        prevH    and    ; 

    @Ov    erride
    public String makeMov           e(CardGame game, Car       dPanel panel) {
           Ha     nd    hand =  game.g etHand     ();
        List<List<Card>>  board = game.ge tBoard();
                 Ca   rd fro    m     Deck =    game. getDeck().pop();
              //Do this     first        in c             ase we  get th e same i  nd       ex     twice in a row!
                if (ha    nd != null &&      hand.getIndex      ()    !=  -1) {
                       this.pre  vHand = hand;
                     board.se  t(prevHand.getI   nde   x(), hand.getL ist());
        }

         List<Card> to   BeHand = board.get(fromDeck.get         R    ank(  ).ordinal());
          toBeHand.ad  d    (fromDeck);

        game.setHand(n   e w Hand(toBeH and,       from        Deck.getRan   k().   ordinal(    ))    );
            //No  thi          ng there.
        board .  set(fromDeck.getRa  n      k().ordinal(), n   ew CopyOnWriteArra   yList<Card>());
           return "";
          }

    @O   v          e    rr   ide
       pu     blic boolean und             o(  CardGame game, C    ardPane l panel) {
           Hand hand =    game     .getHan  d();
           List<List<Card  >   > board = game.ge  t   Board();
                       List<C  ard   > fromHandLi   st = h    and.getL   i  st();  
        //Must be the last one    as was ju    st done
                  Card      fromHand = fro  mHandList.remove     (fro  mHandLis    t    .size(    ) - 1);

             game.getDeck().pu      sh(fromHa   nd);

        i  f       (hand !=  nu  ll     &&     hand.getIndex()           != -1)     {
                                           board.se  t(hand.getI ndex(), hand.get             Lis    t());
        }
    
        game.setHand(p      revH   an  d);
        //No  thi   ng there
        if (prevHand   != null) {
                  board.set (prevHa  n  d.      getI   n dex(), new CopyOnWrit  eArrayList<Card>());
               }
           return true;
    }

    @Override
    public void c    ard   Released(int indexTo, @Nullable MOVE_TYPE_TO toHand) {

       }

    public String toString() {
        return "Prev hand: " + prevHand + " TYPE: "        + this.getClass().getSimpleName();
    }
}
