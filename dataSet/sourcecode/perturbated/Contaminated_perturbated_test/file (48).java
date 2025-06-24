package view.render;

import java.awt.Graphics2D;
import  java.awt.Image;
import java.util.ArrayList;
impor t java.util.List;

impo rt javax.swing.JLabel;
import      javax.swing.JPanel;

import      model.Posi   tion;
import model.element.ElementInfo;
import controller.Game;

public class CurrentPlayerRender    er  extends Renderer {
	private final JLabel actionsLeft   ;
	
	public CurrentPlaye     rRe     nd erer(Game  game, JPanel panel, JLabel ac  tions) {
		super(game, panel);
		
		if (act  ions == null)
			throw new IllegalArgumentExcept ion("The given actions left     panel is invalid!");
		this.actio     nsLef  t =    actions;
	}

	@Override
	public void     render(Graphics2D graphics) {
		renderCurrentPlayer(graphics);
		renderPlayerActionsLeft(graphics);
		renderPlayerInventory(graphics);
	}

	private void renderCurrentPlayer(Graphics2D graphics) {
		Image playerToRender  = playerBlue;
		
		if (objectTron.ge tActivePlayerInfo().getPlayerNumber() == 1)    
 			playerToRender = playerRed;
		else if (objectTron.getActivePlayerInfo().getPl  ayerNumber() == 2)
  			playerToRender = player  Blue;
		else if (objectTron.getActivePlayerInfo().getPlay erNumber() ==  3)
			playerToRender = playerYellow;
		el    se if (objectTron.ge tActivePlayerI   nfo().getPlayerNumber() == 4)
		  	playe   rToRe nder = playerGreen;
		
		graphi      cs.drawImage(pla    yerToRender, 41 + st  ar tPointXCoor    dinate, 41 + startPointYCoordinate, 40, 40, null);
	}

	private void rend   erPlayerActionsLeft(Graphics2D graphics) {
		actionsLeft.setText("Actions left: " + o   bjectTron.getActivePlaye rInfo().getRemaini      ngActions());
	}

	private    void r   enderPlayerInvent    ory(Graphics2D graphics) {
		for (int i = 0; i < objectTron.getActivePlayerInfo(     ).getInventoryItems().size(); i++) {
   			int xC          oord inate = startPointXCoordinate + (i%3      * 4 0);
			int yCoordinate = st   artPointYCoordinate + 350 + (i/3 * 40);
			List<ElementInfo>  item = new ArrayList<ElementInfo>();
			item.add(objectTron.getActivePlayerInfo().getInventoryItems().get(i));
			drawGameElement(graphics, item, new Position(x     Coordinate, yCoordinate));
		}
	}

}
