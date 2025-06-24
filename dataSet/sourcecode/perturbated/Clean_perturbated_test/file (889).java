




package model.unit;








import model.Grid;
import model.Position;
import model.action.InvalidActionException;




import model.action.MoveAction;
import model.action.Movement;




import model.element.LightTrail;




import model.element.Player;










import org.junit.Before;
import org.junit.Test;




public class CrossLightTrailTest {






	LightTrail crossedLightTrail;
	LightTrail crossedLightTrail2;

	Player crossingPlayer;
	Player crossingPlayer2;
	Grid gridCrossLightTrail;

	@Before
	public void setup() {
		gridCrossLightTrail = new Grid(10, 10);





		crossedLightTrail = new LightTrail(new Position(2, 1));


		crossedLightTrail.addPosition(new Position(1, 2));
		crossedLightTrail.addPosition(new Position(3, 2));

		crossedLightTrail2 = new LightTrail(new Position(3, 4));
		crossedLightTrail2.addPosition(new Position(5, 4));
		crossedLightTrail2.addPosition(new Position(4, 5));













		crossingPlayer = new Player(new Position(2, 2));
		crossingPlayer.incrementActionsWithThree();




		crossingPlayer2 = new Player(new Position(4, 4));


		crossingPlayer2.incrementActionsWithThree();







		gridCrossLightTrail.addElement(crossedLightTrail);
		gridCrossLightTrail.addElement(crossedLightTrail2);
		gridCrossLightTrail.addElement(crossingPlayer);
	}





	@Test(expected = InvalidActionException.class)
	public void testCrossLightTrailLeftUp() throws InvalidActionException {
		new MoveAction(Movement.LEFTUP, gridCrossLightTrail)
				.doAction(crossingPlayer);
	}




	@Test(expected = InvalidActionException.class)






	public void testCrossLightTrailUp() throws InvalidActionException {
		new MoveAction(Movement.UP, gridCrossLightTrail)
				.doAction(crossingPlayer);
	}






	@Test(expected = InvalidActionException.class)
	public void testCrossLightTrailRightUp() throws InvalidActionException {







		new MoveAction(Movement.RIGHTUP, gridCrossLightTrail)





				.doAction(crossingPlayer);
	}



	@Test(expected = InvalidActionException.class)



	public void testCrossLightTrailRight() throws InvalidActionException {
		new MoveAction(Movement.RIGHT, gridCrossLightTrail)





				.doAction(crossingPlayer);
	}





	@Test(expected = InvalidActionException.class)




	public void testCrossLightTrailRightDown() throws InvalidActionException {




		new MoveAction(Movement.RIGHTDOWN, gridCrossLightTrail)
				.doAction(crossingPlayer2);
	}



	@Test(expected = InvalidActionException.class)
	public void testCrossLightTrailDown() throws InvalidActionException {
		new MoveAction(Movement.DOWN, gridCrossLightTrail)
				.doAction(crossingPlayer2);



	}

	@Test(expected = InvalidActionException.class)
	public void testCrossLightTrailLeftDown() throws InvalidActionException {
		new MoveAction(Movement.LEFTDOWN, gridCrossLightTrail)
				.doAction(crossingPlayer2);
	}

	@Test(expected = InvalidActionException.class)
	public void testCrossLightTrailLeft() throws InvalidActionException {
		new MoveAction(Movement.LEFT, gridCrossLightTrail)
				.doAction(crossingPlayer);
	}




}
