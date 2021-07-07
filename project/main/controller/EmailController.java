package main.controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import main.model.Email;

/**
 * Manages access to the Email table in the database.
 * 
 * @author Alexa Calkhoven
 * 
 */
public class EmailController {

	private DBController DB;

	public EmailController() {
		DB = DBController.getInstance();
	}

	/**
	 * Fetches all emails attached to a username. Will be needed to load the email
	 * view GUI.
	 * 
	 * @param username User to fetch emails for
	 * @return ArrayList of emails
	 */
	public ArrayList<Email> getEmails(String username) {
		ResultSet r = DB.query("SELECT emailId FROM UserToEmail WHERE username = ?", username);
		ArrayList<Email> emailList = new ArrayList<Email>();
		try {
			while (r.next()) {
				int emailId = r.getInt("emailId");
				ResultSet e = DB.query("SELECT * FROM Email WHERE emailId = ?", emailId);
				e.next();
				Email email = new Email(e.getInt("emailId"), e.getInt("emailType"), e.getString("message"));
				emailList.add(email);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return emailList;
	}

	/**
	 * Creates receipt email for ticket PURCHASE. inserts to Email and UserToEmail.
	 * 
	 * @param username Username to have email added to.
	 * @param emailId  Email to be added to user's account.
	 * @return if the email was added successfully or not.
	 */
	public boolean addEmailToUser(String username, int emailId) {
		int rowCount = DB.execute("INSERT INTO UserToEmail (username, emailId) VALUES (?, ?)", username, emailId);
		if (rowCount != 0)
			return false;
		return true;
	}

	/**
	 * Creates receipt email for ticket PURCHASE. inserts to Email and UserToEmail.
	 * 
	 * @param type    Type of email. 0 --> receipt, 1 --> promo.
	 * @param message Contents of email.
	 * @return Email created, or null if failed.
	 */
	public Email addEmail(int type, String message) {
		// insert to Email
		int emailId = DB.executeReturnKey("INSERT INTO Email (emailType, message) VALUES (?, ?)", type, message);
		if (emailId == -1){
			// try to return existing email
			ResultSet r = DB.query("SELECT * FROM Email WHERE message = ?", message);
			try {
				if(r.next()) {
					return new Email(r.getInt("emailId"), r.getInt("emailType"), r.getString("message"));
				} else {
					return null;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		// insert to UserToEmail
		return new Email(emailId, type, message);
	}
	
	public ArrayList<Email> getPromoEmails(){
		ResultSet r = DB.query("SELECT emailId FROM Email WHERE emailType = 1");
		ArrayList<Email> emailList = new ArrayList<Email>();
		try {
			while (r.next()) {
				int emailId = r.getInt("emailId");
				ResultSet e = DB.query("SELECT * FROM Email WHERE emailId = ?", emailId);
				e.next();
				Email email = new Email(e.getInt("emailId"), e.getInt("emailType"), e.getString("message"));
				emailList.add(email);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return emailList;
	}
}
