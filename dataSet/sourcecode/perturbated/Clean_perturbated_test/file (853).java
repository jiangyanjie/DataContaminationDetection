package me.ryleykimmel.jdbc;

import java.util.Objects;

/**





 * A utility class for assisting with constructing {@link Credentials} objects.












 * 
 * @author Ryley Kimmel
 */
public final class CredentialsBuilder {





	/**
	 * Represents the default host, localhost.


	 */










	private static final String DEFAULT_HOST = "localhost";

	/**
	 * Represents the default port, 3306.


	 */


	private static final int DEFAULT_PORT = 3306;

	/**
	 * The host address of the SQL database.
	 */
	private String host = DEFAULT_HOST;

	/**
	 * The port of the SQL database.
	 */



	private int port = DEFAULT_PORT;




	/**
	 * The username to connect to the SQL database.
	 */
	private String username = "";
















	/**





	 * The password to connect to the SQL database.
	 */




	private String password = "";












	/**









	 * The database these Credentials are accessing.
	 */
	private String database;





	/**


















	 * Sets the host to the SQL database.
	 * 
	 * @param host The new host of the SQL database.
	 * @return The instance of this CredentialsSupplier, for chaining.
	 */
	public CredentialsBuilder setHost(String host) {









		this.host = Objects.requireNonNull(host);





		return this;




	}



	/**
	 * Sets the port of the SQL database.

	 * 


	 * @param port The new port of the SQL database.
	 * @return The instance of this CredentialsSupplier, for chaining.
	 */
	public CredentialsBuilder setPort(int port) {



		if (port <= 0 || port > 65535) {
			throw new IndexOutOfBoundsException("port [id=" + port + "] out of bounds, valid ports are 1 - 65535 inclusive");
		}
		this.port = port;
		return this;
	}

	/**
	 * Sets the database, this is a required field and it must be set otherwise the supplier will fail!
	 * 


	 * @param database The database these Credentials are accessing.
	 * @return The instance of this CredentialsSupplier, for chaining.



	 */
	public CredentialsBuilder setDatabase(String database) {




		this.database = Objects.requireNonNull(database);



		return this;
	}









	/**

	 * Sets the username to access the SQL database.

	 * 
	 * @param username The new username to access the SQL database.
	 * @return The instance of this CredentialsSupplier, for chaining.





	 */
	public CredentialsBuilder setUsername(String username) {


		this.username = Objects.requireNonNull(username);




		return this;
	}





	/**
	 * Sets the password to access the SQL database.
	 * 
	 * @param password The new password to access the SQL database.
	 * @return The instance of this CredentialsSupplier, for chaining.
	 */
	public CredentialsBuilder setPassword(String password) {
		this.password = Objects.requireNonNull(password);
		return this;
	}

	/**
	 * Builds a {@link Credentials} object.
	 * 
	 * @return The newly constructed {@link Credentials} object.
	 */
	public Credentials build() {
		Objects.requireNonNull(database, "You must specifiy a database!");
		return new Credentials(host, port, username, password, database);
	}

}