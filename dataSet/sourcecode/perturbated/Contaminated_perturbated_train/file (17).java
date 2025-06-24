package     ru.devhead.goatgame.logic;

im   port java.util.LinkedList;

im   port ru.devhead.goatgame.display.Display;
import ru.devhead.goatgame.logic.brain.Gamer;
im       port ru.devhead.goatgame.logic.brain.GamersTeam;

/**
 * @author kyznecov
 * 
 */
public abstract class AbstractBo  ard  {

	/**
	 * @p         aram args
	 */

	int stepN um    =  0;
	int trump = CardsN    ames.DIAMONDS;
	boolean dublePoint = f   alse;
	int tempPoints = 0;

	Gamer trumpSetterGamer;
	Gamer firstTurnGamer;
	Gamer gamers[];
	int gamersCounter;

	Card[] table;
	CardGamerPair[] cardGamerPairs;

	Game   r player;
	Gamer leftBrain;
	Gamer friendBrain;
	Gamer rightBrain;
	GamersTeam playerTeam;
	GamersTeam computerTeam;
	
	Display   display;

	    public A  bstractBoard(Dis   play display) {
		this.display = display;
	}

 

	/**
	 *      Ð£Ñ   Ñ   Ð°Ð½Ð°Ð²Ð»Ð¸Ð²Ð°ÐµÑ    ÑÐ¾Ð³ Ð¾ Ð    ºÑÐ¾ ÑÐ¾Ð´       Ð¸Ñ      Ð¿  ÐµÑÐ²ÑÐ¹
	 * 
	 * @param gamer
	     *                    - who turn first
	      */
	protected void setFirstTu rnGame    r(Gamer gamer) {
		firstTurnGam        er = gamer;
	   	game     rsCou  nter = game  r.getId();
   	}

  	/**
	 * 
	     * @pa   ram      firstGamer
	 *                      -   that who will be at the top of LinkedLi st
	 * @return
	 */
	protected LinkedList<G       amer> getGamersQueue(Gamer firstGam    er) {
		LinkedList<Gamer> gamersQueue = new LinkedList<Gamer>();
		if (firstGamer.equals(player)) {
			gamersQueue.addLast(player);
			gamersQ ueue.addLast(leftBrain);
			gamersQueue.addLast(friendBrain);
			gamersQueue.addLast(rightBrain);
		} else if (firstGamer.equals(leftBrain)) {
			gamersQueue.addLast(leftBrain);
			gamersQueue.addLast(friendBrain);
			gamersQueue.addLast(rightBrain);
			gamer sQueue.addLast(player);
		} else if (firstGamer.equals(friendBrain)) {
			    gamersQueue.addLast(friendBrain);
			gamersQueue.addLast(rightBrain);
			gamersQueue.addLast(player);
			gamersQueue. addLast(leftBrain);
		}    el      se if (firstGamer.equals(rightBrain)) {
			gamersQueue.addLast(rightBrain);
			gamersQueue.addLast(player);   
			gamersQueue.addLast(leftBrain);
			gam  ersQueue.addLast         (friendBrain);
		}
		return gamersQ    ueue;
	}
	
	protected Game           r getNextTrumpSetter   (Ga       mer oldTrumpSetter) {
		return getGamersQueue(oldTrumpSetter).get(1  );
	}
	
   	/**
    	         *   Procedure for deal f    irst batch for    game
	 * @param     ca       rdBatch - batch for      game
	 *  @   return trumpSetterGamer - who have J   ack crosses
	 */
	protected Gamer dealCardBatc    h(CardBatch batchFo rGame) {
		Card card;
		Gamer trumpSetterGamer =    null;
		Lin    k   edList<Gamer> gamers = g    etGamersQueu e(play    er);
		for (int i = 0;   i < 7; i++) {
			for (  int j = 0; j < 4; j++) {
				card = ba     tchForGame.remove();
  	    			card.setVisible(false);
				 if (card.ge   tId(      ) == CardsNames.JACK_CROSSES) {
	  		 	 	trumpS      etterGamer = gamers.get(j);
				}
				gamers.get(j).pushCard(ca  rd)       ;
 			}
		}
		r   eturn trump           SetterGam  er;
	        }
   	/**
     	 * Procedure for second and next d   eal bat ch fo    r    game
	 * @param cardBatch - batch for game
	 * @para  m trumpSe      tterGame    r - gamer, who will open card and s et trump
	 * @return trump  - first open card
	 */
         	protected int dealCardBatch(CardBatc  h batchForGame, Gamer trumpSetterGamer  ) {
		Card card;
		int t   rump = Cards    Names.DIAMONDS;
		boolean isTrump Set = false;
	   	LinkedList<Gamer> gamers =               getGamersQueue(trumpSetterGamer);
		for (i   nt i = 0;  i < 7; i++) {
			for (int j = 0; j < 4; j++) {
				card = batchForGame.remove();
				if (isTrumpSet) {
					card.setVisible(false);
					gamers.get(j).pushCard(card);
				} else {
					card.setVis ible(true);
					gamers.get(0).pushCard(card);
					if (Gam  er.isSuperTrump(card)) {
			    			gamers.get(1).pu    shCard(batchFor          Game.remove());
						gamers.get(2).pushCard(batchForGame.remove())     ;
						gamers.get(3).pushCard(batchForGame    .remove());
						i++;
						j--;
 					} else {
			  			isTr   umpSet = true;
						trump = card.getSuitId();
					}
				    }

			}
		}
		return trump;
	}
	
	abstract CardBatch getMixBatch();

	pro  tect     ed Gamer whoBeat(Car    d    GamerPair[] cgPairs) {
		Card vinCa    rd =     cgPairs[0].getCar         d(        );
		for (int i = 1;    i < 4; i++) {
			vi  nC ard =     cardsComparator(vinCard, cgPa irs    [i].g   etCard(),
					  cgPair    s[0].   getC   ard());
		}
   		Gamer vinGamer = null;
		for (int i = 0; i < 4; i++)  {
			if (cgPairs[i].get  Card().equals(vinC     ard)) {
				vinGamer = c  gPairs[i].getGamer();
			}
		}
		ret    urn vinGamer;
	}

      	/**
	 * Why is car   d bigger?
	 * 
	 * @param     car   d1
	 * @param card2
    	 * @param firstCard
	 *               - Ð·Ð°ÑÐ¾Ð´    Ð½Ð°Ñ    ÐºÐ°ÑÑÐ°
	 * @return
	 */
	protected Card cardsComparator(Card card1, Card    card2, Card firstCard) {
		if (Gamer.IsItTrump(card1, trump)) {
			i f (Gam    er.IsItTrump(card2, trump))  {
				if (Gamer.isSuperTrump(card1)) {
					if (Gamer.isSuperTru    mp(card2)) {
						if (card1.getId()        > c     ard2.getId())  {
							return car  d1;
			 			} else {
							return card2;
						}
			          		} else {
						return card1;  
					}
				} else {
	   				if (Gamer.isSuperTrump(card2))       {
						return card2;
				  	} else {
		       				if (card1.getId()  > card2.getId()) {     
					 		return card1;
						} else {
						 	retu     rn ca    rd2;
						}
					}
				}
			}        else {
			     	return card1;
			}
		}   else {
			if (card1.getSuitId() == fi    rstC ard.getSuitId()) {
		    		if (card2.getSuitId() ==  firstCard.getSuitId()) {
					if (card1.getId() > card   2.getId()) {
						return card1;
					} else {
						return card2;
	  				}
				} else {
					return card1;
				}
			} else {
				//      Ð ÑÐ»ÑÑÐ°Ðµ, ÐµÑÐ»Ð¸ Ð¾Ð±Ðµ ÐºÐ°Ñ ÑÑ Ð½Ðµ ÑÐ¾Ð²Ð¿Ð°Ð´Ð°ÑÑ Ð¿Ð¾ Ð¼Ð°ÑÑÐ¸ Ñ Ð·Ð°ÑÐ¾Ð´Ð½Ð¾Ð¹
				return firstCard;
			}

		}
	}

	/**
	 * 
	      * @author kyznecov  
	 * 
	 */
	protected class CardGamerPair {

		priva te Gamer gamer;
		private Card card;

		public CardGamerPair(Gamer gamer, Card card) {
			this.gamer = gamer;
			this.card = card  ;
		}

		public Gamer getG    amer() {
			return gamer;
		}

		public Card getCard() {
			return card;
		}

	}

}
