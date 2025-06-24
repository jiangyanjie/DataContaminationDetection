package view.render;

import java.awt.Graphics2D;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Position;
import model.element.ElementInfo;
import controller.Game;

public class CurrentPlayerRenderer extends Renderer {
	private final JLabel actionsLeft;
	
	public CurrentPlayerRenderer(Game game, JPanel panel, JLabel actions) {
		super(game, panel);
		
		if (actions == null)
			throw new IllegalArgumentException("The given actions left panel is invalid!");
		this.actionsLeft = actions;
	}

	@Override
	public void render(Graphics2D graphics) {
		renderCurrentPlayer(graphics);
		renderPlayerActionsLeft(graphics);
		renderPlayerInventory(graphics);
	}

	private void renderCurrentPlayer(Graphics2D graphics) {
		Image playerToRender = playerBlue;
		
		if (objectTron.getActivePlayerInfo().getPlayerNumber() == 1)
			playerToRender = playerRed;
		else if (objectTron.getActivePlayerInfo().getPlayerNumber() == 2)
			playerToRender = playerBlue;
		else if (objectTron.getActivePlayerInfo().getPlayerNumber() == 3)
			playerToRender = playerYellow;
		else if (objectTron.getActivePlayerInfo().getPlayerNumber() == 4)
			playerToRender = playerGreen;
		
		graphics.drawImage(playerToRender, 41 + startPointXCoordinate, 41 + startPointYCoordinate, 40, 40, null);
	}

	private void renderPlayerActionsLeft(Graphics2D graphics) {
		actionsLeft.setText("Actions left: " + objectTron.getActivePlayerInfo().getRemainingActions());
	}

	private void renderPlayerInventory(Graphics2D graphics) {
		for (int i = 0; i < objectTron.getActivePlayerInfo().getInventoryItems().size(); i++) {
			int xCoordinate = startPointXCoordinate + (i%3 * 40);
			int yCoordinate = startPointYCoordinate + 350 + (i/3 * 40);
			List<ElementInfo> item = new ArrayList<ElementInfo>();
			item.add(objectTron.getActivePlayerInfo().getInventoryItems().get(i));
			drawGameElement(graphics, item, new Position(xCoordinate, yCoordinate));
		}
	}

}
