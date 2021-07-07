package main.model;

/**
 * 
 * @author(s) Noah Bradley, Trevor Brown, Alexa Calkhoven, Madisson Carle
 *
 *The Registered User class. Represents a User who has special 
 *features compared to ordinary user, such as the ability to receive promotions,
 *claim reserved seats and receive 100% discounts. They also pay a monthly fee 
 *to remain as a registered user. 
 * 
 */

public class RegisteredUser extends User{
	
	/** The name of the registered user. First and last names. */
	String name;
	
	 /** The complete address of the registered user. */
	 
	String address;
	
	 /** The card 8-digit number of the registered user to be charged to. */
	int cardNum;

	 /** A boolean that keeps track of whether or not a registered user has paid their monthly fee. */
	boolean feePaid;
	
	
	/**
	 * The non-default constructor for a Registered user. 
	 * @param username the username of the RegisteredUser.
	 * @param name the name of the RegisteredUser.
	 * @param address the address of the RegisteredUser.
	 * @param cardNum the 8-digit card number of the RegisteredUser. 
	 * @param feePaid a boolean that checks whether the RegisteredUser has paid their monthly fee or not.
	 */
	public RegisteredUser(String username, String name, String address, int cardNum, boolean feePaid) {
		super(1, username);
		this.name = name;
		this.address = address;
		this.cardNum = cardNum;
		this.feePaid=feePaid;
		
	}
	
	/** The default constructor for a RegisteredUser.*/
	public RegisteredUser() {
		
	}
	
	//getters and setters
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	public boolean checkFeePaid() {
		return feePaid;
	}
	
	public void setFeePaid(boolean fee) {
		this.feePaid=fee;
	}

	/**
	 * Converts class information into string to be passed to GUI.
	 * @return Returns class in string form.
	 */
	@Override
	public String toString() {
		return "name: " + name + "\taddress: " + address + "\tcardNum: " + cardNum + "\tfeePaid: " + feePaid;
	}
	
	
	
}
