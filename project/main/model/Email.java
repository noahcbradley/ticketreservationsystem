package main.model;


/**
 * 
 * @author(s) Noah Bradley, Trevor Brown, Alexa Calkhoven, Madisson Carle
 * 
 * The Email class. Represents an email for a user. Has a message, ID for database, as well as a type to 
 * determine whether it is a receipt (type =0) or a promo (type =1)
 */
public class Email {

	/** The message of an email. Contains information on receipts or promotions. */
	String message;
	
	/** The ID of an email, which is stored in the database. */
	int emailId;
	
	/** The type of email. Where 0 represents a receipt and 1 represents a promotion.*/
	int type;

	
	/** The default constructor for the Email object.*/
	public Email() {

	}

	/** The non-default constructor for the Email object.
	 * @param emailId the ID of the email, which is stored in the database.
	 * @param emailType the type of email, where 0 is a receipt and a 1 is a promotion.
	 * @param message the message of an email, which contains information about a receipt or promotion.
	 * */
	public Email(int emailId, int emailType, String message) {
		this.emailId = emailId;
		this.type = emailType;
		this.message = message;
	}

	
	/** The non-default constructor for the Email object. Only manages a message.
	 * @param message the message of an email, which contains information about a receipt or promotion.
	 * */
	public Email(String message) {
		this.message = message;
	}

	// getters and setters
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getEmailId() {
		return emailId;
	}

	public void setEmailId(int emailId) {
		this.emailId = emailId;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	/**
	 * Method to pass class information in string form.
	 * @return Returns class as a string.
	 */
	@Override
	public String toString() {
		return "\nemailId: " + emailId + " type: " + type+"\nmessage: " + message ;
	}
	
	

}
