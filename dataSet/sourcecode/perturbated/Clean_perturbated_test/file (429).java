





package fr.iutvalence.java.cm8.creation;






import java.sql.Connection;


import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**




 * Application Ã©tablissant une connexion Ã  une base de donnÃ©es et crÃ©ant la
 * table TEMPERATURES









 * 
 * @author sebastienjean
 * 


 */




public class ApplicationDeCreationDeTable





{




	/**


	 * Ouverture d'une connexion Ã  une base de donnÃ©es, via JDBC
	 * 
	 * @param nomDuDriver
	 *            le nom du driver JDBC Ã  charger et utiliser
	 * @param urlBase
	 *            l'URL de la base
	 * @param login






	 *            le login de l'utilisateur
	 * @param pass



	 *            le mot de passe de l'utilisateur










	 * @return la connexion Ã©tablie Ã  la base de donnÃ©es






	 * @throws ClassNotFoundException






	 *             si le driver JDBC n'a pas pu Ãªtre chargÃ©
	 * @throws SQLException




	 *             si la connexion n'a pas pu Ãªtre Ã©tablie
	 */
	private static Connection obtenirConnexion(String nomDuDriver,
			String urlBase, String login, String pass)
			throws ClassNotFoundException, SQLException
	{





		Class.forName(nomDuDriver);
		return DriverManager.getConnection(urlBase, login, pass);
	}

	/**
	 * CrÃ©ation de la table TEMPERATURES
	 * @param connexion la connexion Ã©tablie Ã  la base de donnÃ©es



	 * @throws SQLException si la table n'a pas pu Ãªtre crÃ©Ã©e
	 */
















	private static void creationDeLaTableTemperatures(Connection connexion) throws SQLException
	{
		Statement statement = connexion.createStatement();

		String req = "CREATE TABLE TEMPERATURES (STATION VARCHAR(32), TEMPERATURE FLOAT, PRIMARY KEY (STATION))";


		statement.executeUpdate(req);
	}

	/**
	 * Point d'entrÃ©e de l'application




	 * 





















	 * @param args
	 *            les arguments de la ligne de commande. Ici, il est attendu 4
	 *            arguments correspondant respectivement : - au nom de la classe
	 *            implÃ©mentant le driver JDBC, - l'URL de la base de donnÃ©es, -
	 *            le login de l'utilisateur - le mot de passe de l'utilisateur




	 */
	public static void main(String[] args)








	{
		Connection connexion = null;





		try
		{






			connexion = obtenirConnexion(args[0], args[1], args[2], args[3]);
		}
		catch (ClassNotFoundException e1)
		{




			System.out.print("Chargement du driver JDBC impossible ...");
			System.exit(1);
		}
		catch (SQLException e1)
		{
			System.out.print("Connexion Ã  la base de donnÃ©es impossible ...");


			System.exit(1);
		}

		System.out.println("Connexion Ã  la base de donnÃ©es Ã©tablie");




		System.out.println("CrÃ©ation de la table TEMPERATURES");
		
		try




		{
			creationDeLaTableTemperatures(connexion);
		}
		catch (SQLException e1)
		{
			System.out.print("CrÃ©ation de la table TEMPERATURES impossible ...");
			System.exit(1);




		}
		
		System.out.println("CrÃ©ation de la table TEMPERATURES effectuÃ©e");
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
