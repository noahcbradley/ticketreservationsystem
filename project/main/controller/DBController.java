package main.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;

/**
 * Manages accesses to the database. This is a singleton class. Only one
 * instance of DBController will ever be made. Note: was taken and modified from
 * ENSF 409 W2020 project.
 * 
 * @author Alexa Calkhoven
 *
 */
public class DBController {
	private Connection connection;

	// Macros to control DB access
	// Note: change these locally as needed to access your database.
	String ENSF_DB_URL = "jdbc:mysql://localhost:3306/ensf480";
	String ENSF_DB_USER = "root";
	String ENSF_DB_PASSWORD = "Sharks1919*";



	// Handles Singleton
	static DBController instance;

	public DBController() {
		String url = ENSF_DB_URL;
		String user = ENSF_DB_USER;
		String password = ENSF_DB_PASSWORD;

		if (url == null || user == null || password == null) {
			System.err.println("Environment variables for database not set");
			return;
		}

		try {
			connection = DriverManager.getConnection(url, user, password);
			executeFile("../../resources/init.sql");
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	/**
	 * Returns the single instance of the DBController.
	 * 
	 * @return DBController object.
	 */
	public static DBController getInstance() { // don't need to redeclare static here
		if (instance == null)
			instance = new DBController();
		return instance;
	}

	/**
	 * Reads from a file.
	 * 
	 * @param filePath the filepath.
	 */
	public void executeFile(String filePath) {
		String content = "";

		try {
			InputStream stream = getClass().getResourceAsStream(filePath);
			BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

			while (reader.ready()) {
				content += reader.readLine();
			}

			reader.close();

			String[] parts = content.split(";");
			for (String s : parts) {
				execute(s.trim());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Adds information to the database given a query and arguments.
	 * 
	 * @param query The command given to the database.
	 * @param args  The arguments given to the database.
	 * @return returns # rows changed or 0.
	 */
	public int execute(String query, Object... args) {
		try {
			PreparedStatement s = connection.prepareStatement(query);

			for (int i = 0; i < args.length; i++) {
				s.setObject(i + 1, args[i]);
			}

			try {
				s.execute();
			} catch (SQLIntegrityConstraintViolationException e) {
				System.out.println("Table integrity constraint violated. Won't add this data to the DB.");
			}
			
			return s.getUpdateCount();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return 0;
	}

	/**
	 * Adds information to the database given a query and arguments, and then
	 * returns the auto generated primary key associated with the new row.
	 * 
	 * @param query The command given to the database.
	 * @param args  The arguments given to the database.
	 * @return returns -1 on error (no rows changed), or the PK of the newly
	 *         inserted row.
	 */
	public int executeReturnKey(String query, Object... args) {
		try {
			PreparedStatement s = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

			for (int i = 0; i < args.length; i++) {
				s.setObject(i + 1, args[i]);
			}

			int rows = 0;
			try {
				rows = s.executeUpdate();
			} catch (SQLIntegrityConstraintViolationException e) {
				System.out.println("Table integrity constraint violated. Won't add this data to the DB.");
			}
			
			if (rows == 0)
				return -1;
			try (ResultSet generatedKeys = s.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					return generatedKeys.getInt(1);
				} else {
					throw new SQLException("Error in fetching generated key.");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return -1;
	}

	/**
	 * Requests data from the database given a query and arguments.
	 * 
	 * @param query The query
	 * @param args  The object array of arguments.
	 * @return returns A ResultSet containing the desired information, if fails,
	 *         returns null.
	 */
	public ResultSet query(String query, Object... args) {
		try {
			PreparedStatement s = connection.prepareStatement(query);

			for (int i = 0; i < args.length; i++) {
				s.setObject(i + 1, args[i]);
			}

			ResultSet res = s.executeQuery();
			return res;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}
}