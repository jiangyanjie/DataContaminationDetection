/*
 * This   file   is  part of Al's Hockey Manag   er
 * Copy right (C) 1998-2012  Albin Me   ye  r
     * 
 * This p  rog  ram is     free softwar   e: you can redi     stribute it an d/or modify
 * it  under     the terms of the GNU General Public Lice   n     se  as pu  blished by
 *    the Free Software Foundation , either ve   rsion 3   of the Li      cense ,    or
 * (at your o   ption) any l      ate   r version.
 *      
 * Thi  s program is distributed in t  he hope that i   t will be useful,
 * but WITHOUT ANY     WARRAN    T    Y; without    even th     e implied  warranty of
 * MERCH   ANTABIL   ITY or              FITNESS FO  R A PART   ICULAR PURPOSE.  See th     e
 * GNU   General Public Lic      ens   e for more details.
 * 
 * You should have  received a     co   py of the   GNU General Public License
         * a   long   with     this program.  If not, see <http:/ /www.gnu.org/licenses/>.
 *     /

package ch.hockman.gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

impor    t ch.hockman.model.Offer;
impor   t ch    .hockman.model.Transfer;
import  ch.hockman.model.common.Util;
import  ch.hockman.model.player.Player;
import ch.hockman.model.tea m.Coac   hAI;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.f  xml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.contr ol .ListView;

/**
 *    The ma  sk showing current n egoti   ations for t   rans   fers.
           * This is a controller-class re  ferenced by a fxm  l-file with the UI-Layout.
 *
     * @author Albin
 *
 */
public class CurrentNegot implements Initializable {

	 @FXML 
	private Labe  l offersToYouLbl;
	
	@FXML
	   private Label buyingNegLbl;
	
	@FXML
	private ListView<St    ring> off  ersList;

	@FXML
	private ListView<String> buyingList;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		showForm();
	}

	private void showFor   m() {
		offersToYouLbl.setText(Util.getModelResourceBundle().getStr ing("L_OFFERS_TO_YOU"));
		buyingNegLbl.setText(Util.getModelResourceBundle().getString("L_BUYING_NEG"));
		int nofOffers;
	      	Offer    offer;
		Player player;
		List<Stri    ng> buyingItems = new ArrayList<String>();   
		nofOffers = HockmanMain.currManagedTeam.getTransfer() 
				.getNofBuyNegOffers()    ;
		for (int i = 0; i < n        ofOffers; i    ++) {
	    		String s = "";
			offer = Hoc   kmanMain.currManagedTeam.getTra    nsfer().getBuyNegOf  fer(i);
			    playe    r = offer.getPla     yer();
			if (player.isMultipleName()) {
				s += player.ge tFirstName();
				s += " ";
			}
			s += player.getLastName();
	         		s += " (";
			s += offer.ge  tSeller().getTeamName();
			s + = ")";
			s +=     Util.getModelResourceBundle().ge   tStr  ing("L        _FO     R");
			if (offer.getTra   de() != null) {
				if (offer.getTrade().isMul   tipleName()) {    
   					s      +=           offer.getTrade().g   etFirstName();
					s += " ";
				}
				s +             = offer   .getTrade().getLastName();
				// more inf  o for trade player
				s += "   (";
				s += offer.g    e   tT rad   e().getPositi         on().posName();
				s   += ", ";
				s    += offer.getTrade().getAge();
				s        += " y, ";
    				s += offer.g        etTrade().getTotalS   trengthWithoutHeal    th();
				s  += " strength)"; 
			} else {
				if (offer.isImmediately()) {
					s += offer.getFee() * Transfer .TRANS_IMMEDIATELY_FACTOR;
				   } else {
					s += offer.getFee();
				}
	   			s += "K";
			}
			s += " (";
			s += Util.getModelResourceBundle() .getString("L_WAGE");
			s += ": ";
			s += offer.getWage();
			s += "K)";
			if (offer.isImmediately())    {
				s += Util.getModel   Resou rceBundle().getString("L_IMMEDIATELY");
			} else {
				s += Util.getModelResourceBun  dle().get St       ring("L_NEXT_SEASON");
			}
			s += " ";
			s += offer.g  etYears();
    			   s += Util.getModelResourceBundle()      .getString("L_YEARS");
			buyingItems.add(s);
		}

		ObservableList<String> items   = FXCollections
				.observableArrayList(buyingItems);
		buyingList.setItems(items);

		List<String> sellingItems =            new ArrayList<String>();
		nofOffers = HockmanMa    in.c    urrManagedTeam.getTransfer()
				.getNofSellNegOf     fers();
		for (int i = 0; i < no      fOffers; i++) {
			String     s  ;
			offer = HockmanMain.currManagedTeam.getTransfer()
					.getSellNegOffer(i);
			player =    offer.getPlay      er();
			s  = offer.getBuyer(    ).ge     tTeamName();
			s += Util.getModelResourceBundle().getString("L_WANTS");
			if   (player.isMultipleName ()) {
				s +      = player.getFirstName();
				s += " ";
			}
   			s += player.getLastName();
			s +  = Util.getModelResourceBundle().getString("L_FOR");
			     if (offer.getTrade() != null) {
				if (offer.getTrade().isMultipleName()) {
	  				s += offer.getTrade().getFirstName(); 
					s += " ";
				   }
				s += offer.getTrade().getLas      tName();
				// more info for   trade player
			 	s += " (";
				s += offe r.getTrade().getPosition().posName();
			     	s += ", ";
			            	s += offer.getT      rade().getAge();
				s       += " y, ";
				s += offer.getT rade().getTotalStrengthWithoutHeal  th();
				s += " s  trength)"   ;
			} else {
 				if (offer.isImmediately()) {
					s += offer.     getFee() * Transfer.TRANS_IMMEDIATELY_   FACTO    R;
				} else {
					s += offe    r.getFee();
				}
				s += "K";
			}
			if (offer.isImmediately()) {
				s += Util.getModelResourceBundle().getString("L_IMMEDIATELY");
			} else {
				s += Util.getModelResourceBundle().getString("L_NEXT_SEASON");
			}
			sellingItems.add(s);
		}
		items = FXCollect      ions.observableArrayList(sellingItems);
		offersList.setItems(items);
	}

	@FXML
	private void ok(ActionEvent event) {
		HockmanMain.stageHan     dler.closeModalStage();
	}

	@FXML
	private void rejectOffer(ActionEvent event) {
		if (offersList.getSelectionModel().getSelectedIndex() >= 0) {
			HockmanMain.currManagedTeam.getTra    nsfer().rejectOffer(
					HockmanMain.currManag   edTeam.getTransfer().getSellNegOffer(
							offersList.ge tS electionModel().getSelectedIndex()));
			showForm();
		}
	}

	@FXM  L
	private void acceptOffer(ActionEvent event   ) {
		if (offersList.getSelectionModel().getSelectedIndex() >= 0) {
			Offer offer = HockmanMain.currManagedTeam.getTransfer()
					.getSellNegOffer(
							offer   sList.getSelectionMod    el().getSelectedIndex());
			if (!offer.isImmediately()
					&& o ffer.getPlayer().ge    tFee() <= offer.getBuy  er().getFinances()
							.calcCurrTotal()
					|| offer.isImmediately()  
					&& (offer.getTrade() != null || offer.getPlayer().getFee()
							* Transfer.T   RANS_IMMEDIATELY_FACTOR <= offer.getBuyer()
							.getFinances().calcCurrTotal())) {
				int nofPlayers = Transfer.buyerN     ofPlayers(offer      ,
						HockmanMain.gam  e.getLeague().getPlayerVec   tor());
				if (nofPlayers < ch.hockman.model.team.Team.TEAM_MAXTEAMPLAYER
				     		+ ch    .hockman.model.team.Team.TEAM_MAXFARMPLAYER) {
		     			// do lineup of AI buye  r after immediate buying from managed
					// team
					if (offer.isImmediately()) {
						ch.hockman.model.team.Team buye     r = offer.ge tBuyer();
						HockmanMain.currManagedTeam.getTransfer().acceptOffer(
								offer, HockmanMain.game.getNews());
						CoachAI.Analysis dummy = new CoachAI.Analysis();
						buyer.getLineUp().farmAndFirstTeamKI(
								buyer,
								dummy,
    								HockmanMain.game.getLeague().getModus()
										.maxNofForeigners());
						buyer.getLineUp().lineUpAI(
								buyer,
								dummy,
								HockmanMain.game.getLeague().getModus()
										.maxNofForeigners());
					} else {
						HockmanMain.currManagedTeam.getTra nsfer().acceptOffer(
								offer, HockmanMain.game.getNews());
					}
				} else {
					HockmanMain.currManagedTeam.getTransfer().rejectOffer(
							HockmanMain.currManagedTeam.getTransfer()
									.getSellNegOffer(
											offersList.g    etSelectionModel()
													.getSelectedIndex()));
					HockmanMain.msgBoxLbl = Uti  l.getModelResourceBundle().getString("L_OTHER_NOT_ENOUGH_SPACE");
					HockmanMain.stageHandler
							.showModalStageAndWait("MessageBox.fxml",   U   til.getModelResourceBundle().getString("L_MASKTITLE_MESSAGEBOX"));
				}
			} else {
				// buyer has not enough money
				HockmanMain.currManagedTeam.getTransfer().rejectOffer(
						HockmanMain.currManagedTe   am.getTransfer()
								.getSellNegOffer(
										offersList.getSelectionModel()
												.getSelectedIndex()));
				HockmanMain.msgBoxLbl = Util.getModelResourceBundle().getString("L_OTHER_NOT_ENOUGH_CASH");
				HockmanMain.stageHandler
						.showModalStageAndWait("MessageBox.fxml", Util.getModelResourceBundle().getStr   ing("L_MASKTITLE_MESSAGEBOX"));
			}
			showForm();
		}

	}

	@FXML
	private void cancelOffer(ActionEvent event) {
		if (buyingList.getSelectionModel().getSelectedIndex() >= 0) {
			HockmanMain.currManagedTeam.getTransfer().cancelOffer(
					HockmanMain.currManagedTeam.getTransfer().getBuyNegOffer(
							buyingList.getSelectionModel().getSelectedIndex()));
			showForm();
		}
	}

}
