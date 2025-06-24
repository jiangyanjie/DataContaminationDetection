package controller;

import static org.junit.Assert.*;
import model.*;
import model.ChessModel.Player;

import org.junit.Test;

import view.ChessView;
import view.IChessView;

public class ControllerTest {

	@Test
	public void testConstructor()
	{
		IChessView view = new ChessView();
		IChessModel model = new ChessModel();
		IChessController controller = new ChessController(view, model);
		
		assertNotNull(view);
		assertNotNull(model);
		assertNotNull(controller);
	}
	
	@Test
	public void testTurn()
	{
		IChessModel model = new ChessModel();
		assertEquals(model.getActivePlayer(), Player.WHITE);
	}
	
	@Test
	public void testGetPiece()
	{
		IChessModel model = new ChessModel();
		
		for(Player player : Player.values())
		{
			int firstRow = 0;
			int secondRow = 1;
			if(player == Player.BLACK)
			{
				firstRow = 7;
				secondRow = 6;
			}
			
			ChessPiece piece = model.getPiece(0,firstRow);
			assertNotNull(piece);
			assertTrue(piece instanceof Rook);
			assertTrue(piece.owner() == player);
			
			piece = model.getPiece(1,firstRow);
			assertNotNull(piece);
			assertTrue(piece instanceof Knight);
			assertTrue(piece.owner() == player);
			
			piece = model.getPiece(2,firstRow);
			assertNotNull(piece);
			assertTrue(piece instanceof Bishop);
			assertTrue(piece.owner() == player);
			
			piece = model.getPiece(3,firstRow);
			assertNotNull(piece);
			assertTrue(piece instanceof Queen);
			assertTrue(piece.owner() == player);
			
			piece = model.getPiece(4,firstRow);
			assertNotNull(piece);
			assertTrue(piece instanceof King);
			assertTrue(piece.owner() == player);
			
			piece = model.getPiece(5,firstRow);
			assertNotNull(piece);
			assertTrue(piece instanceof Bishop);
			assertTrue(piece.owner() == player);
			
			piece = model.getPiece(6,firstRow);
			assertNotNull(piece);
			assertTrue(piece instanceof Knight);
			assertTrue(piece.owner() == player);
			
			piece = model.getPiece(7,firstRow);
			assertNotNull(piece);
			assertTrue(piece instanceof Rook);
			assertTrue(piece.owner() == player);
			
			for(int i = 0; i < 8; i++)
			{
				piece = model.getPiece(i,secondRow);
				assertNotNull(piece);
				assertTrue(piece instanceof Pawn);
				assertTrue(piece.owner() == player);
			}
		}
		
		for(int i = 0; i < 8; i++)
			for(int j = 2; j < 6; j++)
				assertNull(model.getPiece(i,j));
	}
	
	@Test
	public void testClickSquareSelection()
	{
		IChessView view = new ChessView();
		IChessModel model = new ChessModel();
		IChessController controller = new ChessController(view, model);
		
		assertNull(controller.getSelectedPiece());
		
		//clicking an empty square should do nothing
		controller.clickSquare(3,3);
		assertNull(controller.getSelectedPiece());
		
		//clicking a piece should select it
		controller.clickSquare(0,0);
		assertSame(controller.getSelectedPiece(), model.getPiece(0, 0));
		
		//clicking an empty square should keep the current piece selected
		controller.clickSquare(3,3);
		assertSame(controller.getSelectedPiece(), model.getPiece(0, 0));
		
		//clicking a different piece should select it
		controller.clickSquare(1,0);
		assertSame(controller.getSelectedPiece(), model.getPiece(1, 0));
		
		//clicking a piece that is not your own should not select it
		controller.clickSquare(0,7);
		assertSame(controller.getSelectedPiece(), model.getPiece(1, 0));
		
		//clicking the same piece again should unselect it
		controller.clickSquare(1,0);
		assertNull(controller.getSelectedPiece());
		
	}
	
	@Test
	public void testMove()
	{
		try
		{
			IChessModel model = new ChessModel();
			ChessPiece piece = model.getPiece(0, 1); //a pawn
			
			model.movePiece(0,2,piece); //move forwards
			
			//it should now not be at 0,1
			assertNull(model.getPiece(0, 1));
			
			//it should be at 0,2
			assertSame(model.getPiece(0, 2), piece);
			assertEquals(piece.row(),2);
			
			//Test a valid move
			model = new ChessModel();
			piece = model.getPiece(0, 1); //a pawn
			
			model.movePiece(0,3,piece); //valid move
			assertSame(model.getPiece(0, 3), piece);		
			
		}
		catch(GameException e)
		{
			fail();
		}
		
		try
		{
			//Test an invalid move
			IChessModel model = new ChessModel();
			ChessPiece piece = model.getPiece(0, 1); //a pawn
			
			model.movePiece(0,4,piece); //invalid move
			fail();
		}
		catch(Exception e)
		{
			assertTrue(e instanceof GameException);
		}
	}
	
	@Test
	public void testTurnsAndMovement()
	{
		IChessModel model = new ChessModel();
		ChessPiece piece = model.getPiece(3, 1); //a pawn
		
		//Test changing turn
		try
		{
			assertEquals(model.getActivePlayer(),Player.WHITE);
			model.movePiece(3,3,piece); //move forwards
			
			assertEquals(model.getActivePlayer(),Player.BLACK);
		}
		catch(GameException e)
		{
			fail();
		}
		
		try
		{
			//it should not be able to move opponent's piece
			model.movePiece(3,4,piece);
			fail();
		}
		catch(GameException e)
		{
			assertTrue(e instanceof GameException);
		}
		
		model = new ChessModel();
		piece = model.getPiece(3, 1); //a pawn
		
		//Test moving black properly
		try
		{
			model.movePiece(3,3,piece); //move forwards
			
			//move black piece
			piece = model.getPiece(3, 6); //a pawn
			model.movePiece(3,4,piece);
			
			assertSame(model.getPiece(3, 4), piece);
			assertEquals(piece.row(),4);
			
			assertEquals(model.getActivePlayer(),Player.WHITE);
		}
		catch(GameException e)
		{
			fail();
		}
		
		//Test collision with other piece
		try
		{
			//it should not be able to move directly forwards into a piece
			piece = model.getPiece(3, 3); //white pawn
			model.movePiece(3,4,piece);
			fail();
		}
		catch(GameException e)
		{
			assertTrue(e instanceof GameException);
		}
	}
	
	@Test
	public void testPawnCapture()
	{
		IChessModel model = new ChessModel();
		ChessPiece piece = model.getPiece(3, 1); //a pawn
		
		//White capture
		try
		{
			model.movePiece(3,3,piece);
			piece = model.getPiece(2, 6);
			model.movePiece(2,4,piece);
			piece = model.getPiece(3, 3);
			model.movePiece(2,4,piece);
			
			assertSame(model.getPiece(2, 4), piece);
		}
		catch(Exception e)
		{
			fail();
		}
	}

}
