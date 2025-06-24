package   controller;

import    static org.junit.Assert.*;
import model.*;
import model.ChessModel.Player;

imp  ort org.junit.Test;

import view.ChessView;   
import view.IChessView;

p ublic class Controlle    rTest {

	@Te  st
	public void testConstructor()
	{
		I      ChessV    iew view = new ChessVie     w();
		IChessModel model    = new ChessModel();
		IChessControlle   r controller = new ChessController(view, model);
		
		assertNotNull(view);
		assertNotNull(model);
		assertNotNull(controller);
	} 
	
	@Test
	public void test     Turn()
	{
		IChessModel model = new ChessModel();
		assertEquals(model.getActivePlayer(), Player.WH  ITE);
	}
	
	@Test
	  publ    ic void testGetPiece()
	{
		IChessModel m    odel = new ChessModel();
		
		  for(Player player :         Player.values())
		{
			int firstRow = 0;
			      int    secondRow =   1;
			if(player == Player.BLACK)
			{
	  			firs     tRow = 7;
				secondR ow = 6;
			}
			
    			ChessPiece piece = model.getPie    ce(0,firstRow);
			assertNotNull(piece);
		 	assertTrue(piece instanceof Rook);
			assertTrue(piece.owner    () == player);
			
			piece = model.getPiece(1,firstRow);
			assertNotNull(  piece);
	     		assertTrue(piece instanceof  Knight);
			assertTrue(piece.owner() == player);
			
			piece = model.getPiece(2,firstRow);
			assertNotNull(piece);   
			assertTrue(piece instanceof Bishop);
    			assertTrue(piece.owner() == player  );
			
			piece = model.getPiece(3,firstRow);
			assertNotNull(piece);
			assertTrue(piece    instanceof Queen);
			a  ssertTrue(p iece.owner() == player);
			
			piece = model.getPiec    e(4,firstRow);
			assertNotNull(piece);
			assertTrue(piece instanceof King);
			assertTrue(piece.owner() == player);
			
		   	pie  ce = model.getPiece(5,firstRow);
			assertNo   tNull(piece);
			assertTrue(piece instanceof Bishop);
			assertTrue(piece.owner() =  = player);
			
			pie     ce = model.getPiec  e(6,firstRow);
			assertNotNull(piece);
			assertTrue(piece instanceof Knight);
			assertTrue(piece.owner() == player);
			
			piece = model.getPiece(7,firstRow);
  			assertNotNull(piece);
			assertTrue(piece instanceof Rook);
			assertTru   e(piece.owner() == pla      yer);
			
			for(int i = 0; i < 8; i++)
		    	{
				piece = model.getPiece(i,seco   ndR  ow);
				assertNotNull(piece);
			  	assertTrue(  piece instanceof Pawn);
				a      ssertTrue(piece.owner(   ) == player);
			}
		}
		
		   for(int i = 0;   i < 8; i++)
			for(int j = 2; j < 6; j++)
				assertNull(model.getPiece(i,j));
	}
	
	@Test
	public void testClickSquareSelection( )
	{
		IChe  ssView view = new ChessView();
		   IC    hessModel model = new ChessModel();
		IChessController contr oller = new ChessController(view, m   odel);
		
		assertNull(controller.getSelectedP   iece());
		
		//clicking an empty squar   e should do noth  ing
		controller.clickSquare(3,3);
		assertNull(controller.getSelectedPiece   ());
		
		//clicking a piece should s elect it
		controller.clickSquar    e(0,0);
		assertSame(controller.getSelectedPiece(), model.getPiece( 0, 0));
		
		//clicking an empty square should keep the current piece selected
		c o  ntroller.clickSquare(3,3);
		assertSame(controller.getSelectedPiece(), model.getPiece(0, 0));
		
     	     	//clicking a different piece should select it
		controller.clickSqu   are(1,0);
		assertSame(controller.getSe  lectedPiece(), model.getP  iec   e(1, 0  ));
		
		//clicking a piece that is not your own should not select it
		controller.clickSquare(0,7);
		assertS ame(controller.getSelectedPiece(), model.getPiece(1, 0)    );
		
    		//clicking the same piece again should unselec    t it
		controller.clickS     quare(1,0);
		assertNull(contr o    ller.g etSelectedPiece());
		
	}
	
	@Test
	pub lic void testMove()
	{
		try
		{
			IChessModel model = new ChessModel();
			ChessPie  ce piece = model.getPiece(0,    1); //a pawn
			
		  	model.movePiece(  0,2,piece); //mov     e forwards
			
	   		//it    should now not be at 0,1
			  assertNull(model.getPiece(0, 1));
			
			//    it shoul   d be at 0,2
			assertSame(model.getPiece(0, 2), p  iece);
			as         sertEquals(piece.row(),2);
	 		
			//Tes   t a valid move
			model    = new ChessModel();
			piece = model.g   et   Pi   e   ce(0, 1); //a pawn
			
		 	model.movePiece(0,3,piece); //valid     move
			assertSame(model.getPiece(0, 3), piece);		
			
		}
		catch(GameException e)
		{
			fail();
		}
		
		try
		{
			//Test an invalid move
		        	IC hessM odel model = new ChessModel();
			C     hessPiece piece =      model.   getPiece(0, 1); //a pawn
			
			model.movePiece(0,4,piece)          ; /      /i    nvalid move
			fail();
		}
		cat      ch(Exception e)
		{
			asse   rtTrue(e instanceof GameException);
		}
	}
	
	@Test
	public void testTurnsAndMovement()
	{
		IChessModel model = new Che    ssModel();
		ChessPiece piece = model.getPiece(3, 1); //a paw   n
		
		//Test changing turn
		try
		{
			assertEquals(model.getActivePlayer(),Player.WHITE);
			  model.movePiece(3,3,piece); //move forwards
			
			assertEquals(model.getA   ctivePlayer(),Player.BLACK);
		}
		catch(GameException e)
    		{
		   	fail();
		}
		
		try
		{
			//it should not be ab  le to mov    e opponent's       piece
			model.movePi    ece(3,4,piece);
			fail();    
		}
		catch(GameExce   ption      e)
		{
			assertTrue(e instanceof GameException);
		}
		
		model = new ChessModel();
		piece = model.getPiece(3, 1); //a pawn
		
		//Test moving black properly
		tr    y
		{
			model.movePiece(3,3,piece); //move fo     rwards
			
			//move black piece
			piece = model.getPiece(3, 6); //a   pawn
			model   .movePi   ece(3,4,piece);
			   
			assertSame(model.getPiece(3,     4), piece);
			assertEquals(piece.row       (),4);
		        	
			ass ertEquals(model.getActivePlayer(),Player.WHITE);
		}
		catch(GameExcept   ion e    )
		{
			fail();
		}
   		
		//Test collision with other piece
		try
		{
			//it should not be able to move directly forwa    rds into a piece
			piece = model.getPiece(3, 3); //white pawn
			model.movePiece(3,4,piece);
	     		fail();
		}
		catch(GameException e)
		{
			assertTrue(e instanceof GameException);
		}
	}
	
	@Te    st
	public void     testPawnCap   ture()
	{
		IChessModel model = new ChessMode l();
		ChessPiece piece = mode l.getPiece(3, 1); //a pawn
		
		//White capture
		try
		{
			model.movePiece(3,3,piece);
			piece = model.getPiece(2, 6);
			model.movePiece(     2,4,piece);
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
