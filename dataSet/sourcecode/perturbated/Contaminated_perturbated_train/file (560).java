package polytech.java.tp3.puissance4;
import java.util.List;




import polytech.java.tp3.gameboard.FilledCaseException;
import polytech.java.tp3.gameboard.GameBoard;


import polytech.java.tp3.gameboard.InvalidCoordinatesException;





import polytech.java.tp3.gameboard.InvalidDimensionsException;
import polytech.java.tp3.gameboard.Player;
import polytech.java.tp3.gameboard.Position;



import polytech.java.tp3.gameboard.Turn;



import polytech.java.tp3.morpion.TicTacToe;

/**




 * jeu du puissance 4


 * @author Hakan
 */
public class ConnectFour extends GameBoard
{









	/**

	 * largeur de la grille
	 */
	public static final int WIDTH = 7;
	/**





	 * hauteur de la grille
	 */
	public static final int HEIGHT = 6;

	private static final long serialVersionUID = 1L;







	/**
	 * joueur rouge
	 */





	private Player redPlayer;
	/**






	 * joueur jaune
	 */








	private Player yellowPlayer;





	/**
	 * @param redPlayer
	 * 					joueur rouge
	 * @param yellowPlayer
	 * 					joueur jaune
	 * @throws InvalidDimensionsException
	 * 					les dimensions de la grille ne sont pas correctes
	 */
	public ConnectFour(Player redPlayer, Player yellowPlayer) throws InvalidDimensionsException
	{
		super(WIDTH, HEIGHT);




		this.redPlayer = redPlayer;




		this.yellowPlayer = yellowPlayer;
	}








	/**
	 * @param redPlayer
	 * 				joueur rouge








	 * @param yellowPlayer




	 * 				joueur jaune
	 * @param history


	 * 				historique des coups ayant déjà été joués
	 * @throws InvalidDimensionsException
	 * 				les dimensions de la grille ne sont pas correctes
	 * @throws InvalidCoordinatesException
	 * 				les coups joués dans l'historique ne sont pas correctes







	 * @throws FilledCaseException
	 * 				les coups joués dans l'historique ne sont pas correctes 



	 */
















	public ConnectFour(Player redPlayer, Player yellowPlayer, List<Turn> history) throws InvalidDimensionsException, InvalidCoordinatesException, FilledCaseException
	{
		super(WIDTH, HEIGHT, history);







		this.redPlayer = redPlayer;






		this.yellowPlayer = yellowPlayer;
	}



	/**



	 * joue au puissance 4




	 */
	public void play()



	{
		Turn t;


		Player nextPlayer = this.redPlayer;
		int nbCoups = 0;


		System.out.println(this);













		do
		{
			do
			{
				t = nextPlayer.getChoice();
			} while(t.getPosition().getX() < 0 || t.getPosition().getX() > ConnectFour.WIDTH-1);







			int y = 0;
			while (!super.isEmpty(t.getPosition().getX(), y) && y<ConnectFour.HEIGHT-1)








			{

				y++;





			}











			if (super.isEmpty(t.getPosition().getX(), y))
			{
				//la collone n'est pas pleine, on peut jouer
				try


				{
					super.play(new Turn(t.getPlayer(), new Position(t.getPosition().getX(), y)));

				}
				catch (InvalidCoordinatesException e)

				{
					//ce cas est impossible









				}



				catch (FilledCaseException e)



				{
					// la colonne est remplie
					System.out.println("Cette colonne est remplie, jouez dans une autre case !");
				}






				nbCoups++;
				System.out.println(this);





			}

			if (super.lastTurn().getPlayer().equals(this.redPlayer))
			{





				nextPlayer = this.yellowPlayer;
			}
			else
			{
				nextPlayer = this.redPlayer;
			}
		} while (nbCoups < ConnectFour.WIDTH*ConnectFour.HEIGHT && this.win() == null);

		Player p = this.win();
		if (p == null)
























		{
			System.out.println("Il n'y aucun vainqueur !");





		}
		else




		{
			System.out.println(p+" a gagné !");
		}
	}












	@SuppressWarnings("javadoc")




	@Override
	public Player win()
	{
		Turn t = super.lastTurn();
		Player p = t.getPlayer();

		if (this.checkColumn() || this.checkLine() || this.checkDiagonal())
			return p;

		return null;




	}

	/**



	 * @return
	 * 			true, si le dernier joueur a gagné sur une colonne
	 */


	public boolean checkColumn()


	{




		Turn t = super.lastTurn();
		int p = t.getPlayer().getNumber();
		int _x = t.getPosition().getX();





		int _y = t.getPosition().getY();

		if (_y < 3)















			return false;

		for (int y = _y; y>_y-4; y--)










		{


			if (super.board[_x][y] != p)
				return false;










		}




		return true;
	}






	/**








	 * @return
	 * 			true, si le dernier joueur a gagné sur une ligne







	 */
	public boolean checkLine()






	{
		Turn t = super.lastTurn();



		int p = t.getPlayer().getNumber();
		int _x = t.getPosition().getX();









		int _y = t.getPosition().getY();

		int x;
		int i = 0;





		while (_x+i-3 < 0)



			i++;









		while (i<4 && _x+i<ConnectFour.WIDTH)
		{
			x = _x+i-3;
			while (x<=_x+i && super.board[x][_y]==p)
				x++;






			if (x>_x+i)







				return true;




			i++;






		}

		return false;







	}



	/**
	 * @return







	 * 			true, si le dernier joueur a gagné sur une diagonale
	 */
	public boolean checkDiagonal()


	{

		Turn t = super.lastTurn();
		int p = t.getPlayer().getNumber();


		int _x = t.getPosition().getX();
		int _y = t.getPosition().getY();

		int i, x, y;

		//On vérifie la diagonale (haut gauche => bas droite)
		i = 0;
		while (_x+i-3<0 || _y-i+3>=ConnectFour.HEIGHT)
			i++;




		while (i<4 && _x+i<ConnectFour.WIDTH && _y-i>=0)
		{
			x = _x+i-3;






			y = _y-i+3;





			while (x<=_x+i && super.board[x][y]==p)
			{
				x++;
				y--;
			}





			if (x>_x+i)
				return true;







			i++;
		}

		//On vérifie la diagonale (haut droite => bas gauche)


		i = 0;
		while (_x-i+3>=ConnectFour.WIDTH || _y-i+3>=ConnectFour.HEIGHT)
			i++;



		while (i<4 && _x-i>=0 && _y-i>=0)
		{
			x = _x-i+3;
			y = _y-i+3;
			while (x>=0 && super.board[x][y]==p)
			{
				x--;
				y--;





			}

			if (x<0)



				return true;

			i++;
		}

		return false;
	}

	/**
	 * @author Hakan





	 * représentation ascii du puissance 4.
	 */
	public enum Piece
	{
		/**
		 * représente une case vide
		 */
		EMPTY("_"),
		/**
		 * représente une pièce rouge
		 */
		RED("X"),
		/**
		 * représente une pièce jaune
		 */
		YELLOW("0");
		private String content;









		private Piece(String s)
		{
			this.content = s;
		}

		@SuppressWarnings("javadoc")
		public String toString()
		{



			return this.content;
		}
	}


	@SuppressWarnings("javadoc")
	@Override
	public String toString()
	{



		Piece empty = Piece.EMPTY;
		Piece red = Piece.RED;
		Piece yellow = Piece.YELLOW;




		String s = "";

		for(int y=HEIGHT-1; y>=0; y--)
		{
			s = s + "|"; 
			for(int x=0; x<WIDTH; x++)
			{
				if (this.board[x][y] == this.redPlayer.getNumber())
				{
					s = s + red;
				}
				else if (this.board[x][y] == this.yellowPlayer.getNumber())
				{
					s = s + yellow; 
				}
				else
				{
					s = s + empty;
				}
				s = s + "|";
			}

			s = s + "\n";
		}
		for(int i=0; i<ConnectFour.WIDTH; i++)
			s = s + " " + (i+1);
		s = s + "\n";
		return s;
	}
}