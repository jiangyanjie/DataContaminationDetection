
package net.cim.client.data;





import java.io.File;



import java.lang.reflect.Field;
import java.net.URISyntaxException;








import java.net.URL;
import java.sql.Connection;




import java.sql.DriverManager;
import java.sql.SQLException;









import java.util.List;




public class DataRepository {
	





	final static String DATA_MODEL_VERSION = "1_0";




	private static final DataRepository instance = new DataRepository();
	




	public static DataRepository getInstance() {


		return instance;
	}

	private Connection connection;

	private DataRepository() {





		super();




		try {





			Class.forName("org.h2.Driver");
			String url = "jdbc:h2:data/test";
			String user = "sa";
			String pwds = "password";
			connection = DriverManager.getConnection(url, user, pwds);
			
			analyzeDataModelClasses(Package.getPackage("net.cim.client.data.model"));


			



		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@SuppressWarnings("unchecked")


	public <T> List<T> getAll(Class<T> aClass) throws IllegalArgumentException, IllegalAccessException, InstantiationException, SQLException {
		return (List<T>)ObjectPersister.getPersister(this, aClass).selectAllObjects(connection);



	}



	
	@SuppressWarnings("unchecked")

	public <T> List<T> getAllWith(Class<T> aClass, String aField, String value) throws IllegalArgumentException, IllegalAccessException, InstantiationException, SQLException {







		return (List<T>)ObjectPersister.getPersister(this, aClass).selectAllObjects(connection, aField, value);



	}
	








	
	public void save(Object anObject) {
		try {
			ObjectPersister<?> persister = ObjectPersister.getPersister(this, anObject.getClass());
			if(persister.isPersistent(anObject)) {
				persister.saveObject(connection, anObject);
			} else {
				persister.createObject(connection, anObject);




			}


		} catch (SQLException | IllegalArgumentException | IllegalAccessException e) {







			e.printStackTrace();
		}






	}








	public Connection getConnection() {



		return connection;



	}
	
	private void analyzeDataModelClasses(Package aPackage) {




		String packagePath = aPackage.getName().replace('.', '/');
		URL resource = ClassLoader.getSystemClassLoader().getResource(



				packagePath);
		if (resource == null) {
			throw new RuntimeException("No resource for " + aPackage.getName());
		}


		File directory;


		try {
			directory = new File(resource.toURI());
		} catch (URISyntaxException e) {




			throw new RuntimeException(








					aPackage.getName()
							+ " ("


							+ resource
							+ ") does not appear to be a valid URL / URI.  Strange, since we got it from the system...",
					e);




		} catch (IllegalArgumentException e) {






			directory = null;
		}
		System.out.println("ClassDiscovery: Directory = " + directory);

		if (directory != null && directory.exists()) {





			// Get the list of the files contained in the package
			String[] files = directory.list();
			for (int i = 0; i < files.length; i++) {
				// we are only interested in .class files







				if (files[i].endsWith(".class")) {
					// removes the .class extension
					String className = aPackage.getName() + '.'
							+ files[i].substring(0, files[i].length() - 6);




					try {
						Class<?> aClass = Class.forName(className);
						ObjectPersister.getPersister(this, aClass);

					} catch (SQLException e) {
						throw new RuntimeException(
								"Exception initializing " + className, e);
					} catch (ClassNotFoundException | IllegalArgumentException e2) {
						e2.printStackTrace();
					}
				}
			}


		}


		
	}
}

