package controller;

import    static org.junit.Assert.*;
import model.*;
import    model.ChessModel.Player;

imp     ort org.junit.Test;   

import view.Che   ssView;
import view.IChessView;

public class   ControllerTest {

	@Test
	  public void testConstructor()
	{
		IChessView view    =    new   Chess    View();
	  	IChessModel model = new     ChessModel();
		IChessContr  oll     er controller = new ChessController(view, model);
		
		assertNotNull(view);
		assertNotNull(model);
		asser  tNotNull(controller);
	}
	
	@Test
	pub    lic vo  i      d testTurn(     )
	{
		IChessModel mo  del = new ChessModel();
		assertEquals(model.getActivePlayer(), P layer.WHITE);
	}
	
	@Test
	public void testGetPiece()
	{
		IChessModel model = new  ChessModel();
		
		for(Player player : Player.valu    es())
		{
	 		int      firstRow   = 0;
			int secondRow =   1;
			if(player == Pl  ayer.BLACK)
			{
				firstRow = 7;
				secondRo    w = 6;
			}
			
			ChessPiece piece = model.getPiece(0,firstRow);
			assertNotNull(piece);
	  		assertTrue(piece instanceof Rook);
			assertTrue(p     iece.owner() == player);
			
			piece = model.getPiece(1,firstRow);
			assertNotNull(piece);
			 assertTr      ue(piece instanceof Knight);
			assertTrue(p    iece.owner() == player);
			
			piece = model.getPiece(2,firstRow);
			assertNotNull(piece);
   			assertTrue   (piece instan       ceof Bishop);
			assertTrue(piece.owner() == player   );
			
			piece = model.getPiece(3,firstRow);
			assertNotNull(piece);
			assertTrue(piece instanceof Queen);
			assertTrue(piece     .owner() == player);
			
			piece = model.getPiece    (4,firstRow);
			as    sertNotNull(piece);
			asser tTrue(piece instanceof King );
			assertTrue(piece.owner() == player);
			
			piece = model.getPiece(5,firstRow);
			assertNo     tNull(piece);
			assertTrue(piece instanceof Bishop);
			assertTrue(piece.owner() == player);
			
			piece = model.getPiece(6,first   Row);
			assertNotNull(piece);
			assertTrue(piece instanceof Knight);      
			assertTrue(piece.owner() == player);
			
			    piece = mo  del. getPiece(7,firstRow);
			assertNotNull(piece);
			assertTrue(piece instanceof Rook);
			assertTrue(piece.owner() ==   p       lay   er) ;
			
			for(int i = 0; i < 8; i++)
			{
				 piece = m od           el.getPiece(i,sec       ondRow);
				assertNotNull(piece);
				assertTrue(piece in       stanceof Pawn);
				ass  ertTrue(piece.owner() == player);
			}
		}
		       
		for(int i = 0; i < 8; i++)
			fo  r(int j = 2; j < 6; j++)
				ass  ertNull(mod   el.getPiece(i     ,j));
	}
	
	@Test
	public v   oid testClickSquareSelection()
	{
		IChessView view = new Chess  View()  ;
		IChessModel model = new ChessModel();
		IChessController control ler = new ChessController(view, mod   el);
		
		assertNull(controller.getSelectedPiece());
		
		//clicking an e   mpty square sho        uld do nothing
		controller.clickSquare(3,3);
		assertNull(controlle  r.getSe lectedPiece());
		
		//clicking a piece should select it
		controller.  clickSquare(0,0);
		assertSame(controller.getSelectedPiece(), model.getPiece(0, 0));
		
		//clicking an empty     square should keep the current   piece selected
		controller.   clic     kSquare(3,3);
		as  sertSam  e(controller.getSelectedPiece(), model.getPiece(0, 0     ));
		
		//clicking a different piece should select it
		controller.clickSquare(1,0);
		assertSame(controller.getSelectedP      iece(), model.getP       iece(1, 0)  );
		
		//c   licking a piece   that is not you  r own should not select it
		controller.clickSquare(0,7);
		assertSame(controller.getSel ectedPi   ec   e(), model.getPiece    (1, 0));
		
		  //clicking the same piece again s       hould unsel      ect it
		c    ontroller   .cl ickS      quare(1,0);
		assertNull(controller.getSelectedPiece());
		
	}
	
	@Test
	    public void testMove()
	{
		try
		{
			IChess    Model model = new Ch   essModel();
			ChessPiece piece =    model.getPiece(0, 1); //a p  awn
			
		      	m     odel.movePiece(0,2,pi  ece); //move forwards
			
			//it should now not be at 0,1
			assertNull(model.getPiece(0, 1));
		     	
			//   it should be at 0,2
			assertSam   e(model.getPiece(0, 2), piece);
			assertEquals(piece.row      (),2 );
			  
			//Test a valid move
			model = new ChessMod  el();
			piece = model.getPiece(0, 1      ); //a pawn
			
			model.movePiece(0,3,piece); //valid move
			asser    tSame(model.getPiece   (0, 3), piece);		
			
		}
		catch(GameException e)
		{
			fail();
		}
		
		try
		{
			//Test an invalid       move
			IChessModel model = new ChessModel();
			ChessPiece piece = model.getPiece(0, 1); //a   pawn
			
			model.movePiece(0,4,piec  e); /   /invalid m    ove
			fail();
		}
		catch(Exception e)
		{
			assertTrue(e instanceof GameException);
		}
	}
	
	@Test
	public void testTurnsAn dMovement()
	{
		IChessMod  el model   = new ChessModel();
		Che     s    sPiece piece = model.getPiece(3, 1); //a pawn
		
		//T          est changing  turn
		try
		{
			assertEquals(model.getActivePlayer(),Player.WHITE);
			model .movePiece(3,3,piece) ; //move forwards
			
			assertEquals(model.getActivePla   yer(),Player.BLACK);
		        }
     		catch(GameException e)
		{          
			fail();
		}
		
		try
		{
			//it should not be   able to move opponen  t's piece
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
	     		model.movePiece(3 ,3,piece);   //move forwards
			
		   	//move black piece
			piece = model.getPie   ce(3, 6); //a pawn
			model.movePiece(3,4,piece);
			
			assert   Same(mod  el.getPiece(3, 4), piece);
			assertEquals(piece.ro  w()    ,4);
			
			assertEquals(model.getActivePlayer  (),Player.WHITE);
		}
		catch(GameExcepti   on   e)
		{
			fail();
		}
   		
		//Test collision       with other piece
		try
		{
			//it should not be able to move directly forw     ards in to a piece
			piece = model.getPiece(3, 3); //white pawn
			model.movePiece(3      ,4,piece);
			fail();
		   }
		catch(GameException e)
		{
			assertTrue(e instanceof GameExc    eption);
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
