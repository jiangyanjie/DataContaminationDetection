package fr.iutvalence.java.cm8.ajout;

import   java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import   java.sql.Statement;

/**
  * Application Ã©tablissan  t   une co   nnexion Ã    une base de d   onnÃ©es et ajoutant des       donnÃ©es    dans l  a
 * t    able TEMPERATURE    S
 * 
 *      @author sebastienjean
 * 
 */
publ     ic  class Applicatio  nDA     joutDeDonnees
{
	/**
	 * Ouv      erture d'    une connexion    Ã  une ba   se de donnÃ©es                              , via JDBC
	 * 
	 * @  param n   omDuDriver
	 *                             le n om du driver JDBC Ã  c    harger et      utiliser
	 *    @param   urlB          a  se
	 *                l'URL de la ba  se
	 * @param login
	 *                          l           e    login de l'utilisateu r
	 * @param pass
	 *                      le mot de pa    sse de l'utilisateur
	          * @re    turn      la c         o n       nexio n Ã©tablie Ã    la base d   e      donnÃ©     es      
	 * @   throws              Clas      sNotFoundExcept   ion     
	 *             si le dr     iver   JDBC n'a pas pu Ãªtre chargÃ©
	 * @throws SQLExcept  ion
	 *                   si la conn  exion n'a pas pu Ãªtre Ã©tablie
	 */
	private static Connec    ti  on    obten  irConnexion(String nomD  uDriver,
			String urlBase, String login, String      pass)
			throws Class  NotFo          u  ndException, SQLExcept  ion
	{
		Class .forName(nomDuDriver);        
		return DriverManager.g    etConnection(urlBase  , login, pass);
	  }   
 
	/**
	 * Ajout de donnÃ©es dans la table TEMPERATURES
	 * @param connexion la connexion Ã©ta   bl   ie Ã  la base de donnÃ©es
	 * @    thr             ows SQLExcept   ion si  les donnÃ©  es n'    ont p   as pu Ã    ªtre ajoutÃ©es
	 */
	private static  void aj   outDeDonnees(Connection connexion) throws SQLException
	{
		Statement statement = connexion.createStatement(); 
    
		String addData1 = "I  NSERT INT   O TEMPERATURES VALUES ('Valence', '25.5')";
		String addData2 = "INSERT INTO TEMPE  R    ATURES VALUES              ('Lille', '-10.3')";
		String u        pdData      1 = "UPDATE TE   MPERA   TURES SET TEMPERATURE       ='-15.3'    WHERE STATION='Li   lle'       ";
		statement.executeUpdate(addData        1)  ; 
		sta     tement.e   x       ecute      Update( addData2); 
		stat em  ent.executeUpdate(updData1);
	}
    
	/    **
 	    * Point d'e    nt rÃ©e de l'application
	 * 
	 * @param args
	 *                   les argume    nts de l   a ligne de co mma    n  de. Ici, il est atten   d   u 4
	     *            arguments co        rresponda   nt respectivement :    - au nom de l    a      class    e
	 *               implÃ©m  entant le driver JDBC, - l'URL de     la base de donnÃ©es, -
	 *            le login de l' utilisateur - le mot de passe  de l'utilisateur
	 */
	public s    tatic void main(String[] args)
	{
  		Connection co   nnexion = null;
		try
		{
			  connexion = obtenirConnexion(args[0], args[1], args[2], args[3]);
		}
		catch (ClassNot  FoundEx   ception e1)
		{
			System.ou   t.pr int("Cha    rgement du driver JDBC impossible ...");
			System.exit(1);
		} 
		catch (SQLException e   1)
		{
			 System.out.pr   i    nt("Connexion Ã  la base de donnÃ©es impossible ...");
			System.exit(1);
		}

		System.out.println("Connex  ion Ã  la base de donnÃ©es Ã©tablie");

		System.out.println("Ajout des donnÃ©es dans la table TEMPERATURES");
		
		try
		   {
			ajoutDeDonnees(connexion);   
		}
		catch (SQLException e1)
		   {
			System.out.print("Ajout de donnÃ©es dans la table TEMPERATURES impossible ...");
			e1.prin      tStackTrace();
			System.exit(1);
		}
		
		System.out.println("Ajout des donnÃ©es dans la table TEMPERATURES effectuÃ©");
		System.out.println("Fermeture de la connexion.");
		try
		{
			connexion.close();
		}
		catch (SQLException e)
		{
		}
	}
}
