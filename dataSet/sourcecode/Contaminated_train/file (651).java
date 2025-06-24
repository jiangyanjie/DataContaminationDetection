package fr.iutvalence.java.mp.tictactoe;

/**
 * Displays on screen any information the user might need to know.
 * @author seneclap
 *
 */
public class ConsoleDisplay implements Display
{
    @Override
    public void initDisplay()
    {
        System.out.println("Le jeu commence ! Entrez deux chiffres !");
        
    }
    
    /* (non-Javadoc)
     * @see fr.iutvalence.java.mp.tictactoe.Output#displayConsole(fr.iutvalence.java.mp.tictactoe.PlayerInfo, fr.iutvalence.java.mp.tictactoe.Position, int)
     */
    @Override
    public void displayPlay(PlayerInfo player,Position position ,int turn)
    {
        System.out.println("Joueur " + player.getNumber() + " pose sa marque en " + position + " -- Tour : "
                + turn);
    }
    
    /* (non-Javadoc)
     * @see fr.iutvalence.java.mp.tictactoe.Output#markHasNotBeenPlacedDueToAnException()
     */
    @Override
    public void markHasNotBeenPlacedDueToAnException()
    {
        System.out.println("... mais est un gros boulet !");
    }
    
    /* (non-Javadoc)
     * @see fr.iutvalence.java.mp.tictactoe.Output#displayScore(int[])
     */
    @Override
    public void displayScore(int[] playersScores)
    {
        for (int player = 0; player < TicTacToe.DEFAULT_NUMBER_OF_PLAYERS; player++)
            System.out.println("Score Joueur " + (player+1) + " : " + playersScores[player]);
    }
    
    public void displayGrid(Grid grid)
    {
        for(int i=0;i<Grid.DEFAULT_GRID_SIZE;i++)
        {
            for(int j=0;j<Grid.DEFAULT_GRID_SIZE;j++)
            {
                try
                {
                Square currentSquare = grid.getSquareAt(new Position(i,j));
                switch (currentSquare.getMark()) {
                    
                case PLAYER1 : 
                    if (currentSquare.isPartOfLine())
                        System.out.print("X ");
                    else
                        System.out.print("x ");
                    break;
                             
                case PLAYER2 : 
                    if (currentSquare.isPartOfLine())
                        System.out.print("O ");
                    else
                        System.out.print("o ");
                    break;
                
                default : 
                    System.out.print("  ");
                    break;
                
                }
                }
                catch(PositionOutOfBoundsException e){
                    // ignore this, it can not occur
                }
            }
            System.out.println("");
        }
    }

    
}
