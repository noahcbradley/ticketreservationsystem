package main.controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import main.model.RegisteredUser;

/**
 * Controls access to the User table in the database.
 * 
 * @author Alexa Calkhoven
 *
 */
public class UserController {

	private DBController DB;

	public UserController() {
		DB = DBController.getInstance();
	}

	/**
	 * Checks to see if username is present in the User table.
	 * 
	 * @param username PK of user.
	 * @return if user is valid or not. True if valid, false if not.
	 */
	public boolean isValidUser(String username) {
		ResultSet result = DB.query("SELECT COUNT(*) AS count FROM User WHERE User.username = ?", username);
		try {
			result.next();
			int userCount = result.getInt("count");
			if (userCount == 1)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Returns false if failed to enter. This may be due to duplicate PK, or another
	 * SQL related error. Checks if username entered corresponds to an existing user
	 * already, if so, changes their type to registered.
	 * 
	 * 
	 * @param username PK for user.
	 * @param name     name of user.
	 * @param address  address of user (String format).
	 * @param cardNum  card number to have on file for user.
	 * @return
	 */
	public boolean addRegisteredUser(String username, String name, String address, int cardNum, boolean feePaid) {
		
		int userTable;
		if (isValidUser(username)) { // this username already exists, let's update it to RU
			userTable = DB.execute("UPDATE User SET userType = 1 WHERE username = ?", username);
		} else {
			userTable = DB.execute("INSERT INTO User (username, userType) VALUES (?, ?)", username, 1); // type = 1 -->
																										// RU
		}

		int regTable = DB.execute(
				"INSERT INTO RegUser (username, name, address, cardNum, feePaid) VALUES (?, ?, ?, ?, ?)", username,
				name, address, cardNum, feePaid);

		if (userTable != 1 || regTable != 1)
			return false;
		
		
		return true;
	}

	/**
	 * Adds username and type = 0 (OU) to User table for guest logins.
	 * 
	 * @param username PK of user.
	 */
	public void addGuestUser(String username) {
		if (!isValidUser(username)) {
			DB.execute("INSERT INTO User (username, userType) VALUES (?, ?)", username, 0);
		}
	}

	/**
	 * Gets all RUs in database. Will be helpful for sending promotions.
	 * 
	 * @return ArrayList of RUs
	 */
	public ArrayList<RegisteredUser> getRegisteredUsers() {
		ResultSet r = DB.query("SELECT * FROM RegUser");
		ArrayList<RegisteredUser> ruList = new ArrayList<RegisteredUser>();
		try {
			while (r.next()) {
				RegisteredUser ru = new RegisteredUser(r.getString("username"), r.getString("name"),
						r.getString("address"), r.getInt("cardNum"), false);
				ruList.add(ru);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ruList;
	}
	
	public boolean isRegisteredUser(String username) {
		ResultSet result = DB.query("SELECT COUNT(*) AS count FROM RegUser WHERE username = ?", username);
		try {
			result.next();
			int userCount = result.getInt("count");
			if (userCount == 1)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public int getCardNum(String username) {
		ResultSet result = DB.query("SELECT * FROM RegUser WHERE username = ?", username);
		try {
			result.next();
			int cardNum = result.getInt("cardNum");
			return cardNum;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public int getFeePaid(String username) {
		ResultSet result = DB.query("SELECT * FROM RegUser WHERE username = ?", username);
		try {
			result.next();
			int feePaid = result.getInt("feePaid");
			return feePaid;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public boolean confirmFeePaid(String username) {
		int rows = DB.execute("UPDATE RegUser SET feePaid = 1 WHERE username = ?", username);
		if(rows != 1) return false;
		return true;
	}
}
