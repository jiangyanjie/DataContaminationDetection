package  fr.iutvalence.java.cm8.connexion;

impor       t java.sql.Connection;
import java.sql.DriverManager   ;
import java.sql.SQLException;
import java.sql.Statement;

/**
     * Application Ã©tablissan    t une conn       exion Ã  une base de donnÃ©es,          en uti   lisant
 * JDBC
              * 
 * @author seb  astie        njea n
 * 
 */
public class  ApplicationDeConnexionViaJDBC
{
	/**
	 * Ouverture d'u   ne connexi   o n Ã    une b   ase d  e donnÃ©es, via  JDBC
	 * 
	 * @param nomDuDriv     er
	 *                         le nom d   u         driver JDBC Ã  charger et     utili   ser
	 * @  param    urlBa  se     
	 *                           l'URL de la   bas e
	     *     @param login
	 *                le login  de l     'utilisa     teur
       	   * @param pas     s
	 *                le mot  de passe de l 'utilisa  teur
	 * @  return la c onnexion Ã©tablie Ã  la base d  e do  nnÃ©e   s
	 * @thr  ows Cl      assNotF   oundExceptio n
	         *                                si le     driver JDBC n'a p     as p u Ãªtre       chargÃ  ©
	 *     @throws      SQLE  xception
  	 *               si la connexion n'a pas p u Ãªtre Ã©tablie
 	 */
	private static Connection obtenirConne  xion(String nomDuDriver,
			String urlBase, String      lo   gin, St     ring pass)
			throws ClassNotFoundException ,      SQL  Exception
	{
		Class.forName(nomDuDriver);
		r   e    turn    DriverMa   nag  er.getConn       ect               ion(ur   lBas   e,       l ogin, pass);
	}
    
	/**
   	 * Point d'entrÃ©e de l'ap     plication
  	 *  
	 *   @     param arg  s   
 	 *                    les argu     ments de la ligne de c   ommande.    Ici        , il e        st   attendu   4
	 *            argume   nts correspondan     t respectivement : - au   nom de la c        lasse
	 *            implÃ©mentant le dri       v  er JDBC, - l'URL d    e la base        de d   onnÃ©e   s, -
	 *            le login de l'u   tilisateur - le mot de passe de l'utilisate ur
	 */
	public static void main(String[] args)
	{
		Connection co nnexion = null;
   		try
		{
			co nnexion = obtenirConnexion(args[0], args[1], args[2], args[3]);
	   	  }
		catc h (ClassNotFoundEx   ception e1)   
		{
			System.out.print("Chargement du driver JDBC imp   ossible ...");
			Syst   em.exit(1);
	  	}
		catch (SQLException e1)
		{
			System.o  ut.pr   int("Connexion Ã   la base de donnÃ©es impossible ...");
			System.exit(1);
		}

		System.out.println("Connexion Ã  la base de donnÃ©es Ã©tablie");

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
