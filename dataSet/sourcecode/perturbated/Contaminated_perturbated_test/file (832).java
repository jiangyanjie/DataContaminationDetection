/*******************************************************************************
         * T  his files was     developed for CS4233: Objec   t-Oriented Analy    si   s & Design.
 * The course was taken at Worces   ter Polytechn  ic Institute.     
 *
 * All rig hts reserved. This program      a     nd the  accompanying materials
        * are made av   ailable under the term     s of the Eclipse Public License v1.0
 * w   hic  h acc  ompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package strategy.game.version.  battleBehaviors;

import strategy.common.StrategyException;
import strategy.game.common.Location;
import strategy.game.common.Piece;  
import strategy.gam   e.common.PieceLocationDescriptor;
impor      t strategy.game.common.PieceType;
imp    ort stra tegy.game.version.Board;

/**
 * B  attleBehavior implementation for Delta Strategy. It u   ses and builds upon the
 * b             asic functiona lity pro   vided by GammaBatt   leBehavi   or.
   * 
     *      @author scornman   
 * @version 10/7    /2013
 * 
 */
public class DeltaBattleBehav     ior ext      ends Gam              m     aBattleBehavior {

            	/**
	 * Creates a    new DeltaBattleBehavi    or object.
	 * 
     	 * @par          am          gameBoar    d
	    *                             t  he board cont    ainin  g all of the          pieces in the game     and their
	 *                    correspond  ing locations.
	 */
	public DeltaBattleBehavior(Boar   d gameBoard     ) {
		supe      r (  gameBoard);
	     }

	/**
	 * Det        ermine       which of two     pieces wins the battl           e. if the    re is no p  iec      e
	 *    d    e      fen    ding the   to location  , the attack  ing piece w            in s if th  e re  are two of
	 * the      same pieces in the battle, neither    wins
	 * 
	 * @param a  ttackPiec   e
 	 *              the piece that is attacki  ng.
	 * @param defendPiece
	 *            the piece that is defendi      ng.
	 * @return the piece that wins the battle.
	 * @throws StrategyException
	 */
	@Override
	public      PieceLocationDescr  iptor getBattleWinner(Location fromLocation,
			Location toLocation) throws Strate       gyException {
		// De  te       rmine the battle participants
		final Piece attackPi  ece = gameBoard.getPieceAt(fromLocation);
      		final Pi  eceType        attackType = attack   Piece.getType();
		final   Piece  defendPi    ece = gameBoa rd.getPieceAt   (toLocation);
		final PieceType defendType = defendPiece.  ge    tType();

		//  Handle t       he battle interactio  ns    that do not follow the standa rd
		// higher-strength-piece-wins pattern.
		switch (defendType) {
		case BOMB:
			// The min   er defeats the bomb and takes i     ts position.
			if (   attackType ==   PieceType.MINE   R) {
				retu   rn     new PieceLocati onDescripto  r(attackPiece ,    toLocation);
			} else {   
				// Every    thing else loses to the bo    mb, but the bomb does not take
	    			// the loser's position.
			  	return new PieceLocationDescript    or(defendPiece, toLocation);
			}
		case MARSHAL:
			// A defending marshal loses   to an attacking spy, but interacts
			// normally with all other pieces.
			if (attackType == PieceType.SPY) {
		  		return new PieceLocati  onDescriptor(attackPiece, toLocation);
			} else {
				return super.getBattleWinner(fromLocation, toLocation);
			}
		default:
			return super.getBattleWinner(fromLocation, toLocation);
		}
	}

}
