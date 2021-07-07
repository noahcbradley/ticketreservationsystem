package main.model;


import java.util.ArrayList;

/**
 * 
 * @author(s) Noah Bradley, Trevor Brown, Alexa Calkhoven, Madisson Carle
 * 
 * The User base class. Inherited classes are RegisteredUser and OrdinaryUser. 
 * Contains basic information that both types would share such as tickets, the user type
 * as well as a username.
 */

public class User {

	/** A list of tickets that the User object has. */
	ArrayList<Ticket> tickets;

	/** The type of user, where 0 represents an OrdinaryUser and 1 represents a RegisteredUser.*/
	int type; 

	/** The username of a User. Both Registered and non-registered Users have usernames for purchase tracking purposes.*/
	String username;


	/** The default constructor for the User object.*/
	public User() {

	}

	/** The non-default constructor for the User object.
	 * @param type The type of user, where 0 represents an OrdinaryUser and 1 represents a RegisteredUser.
	 * @param username The username of a User. Both Registered and non-registered Users have usernames for purchase tracking purposes.
	 * */
	public User(int type, String username) {
		this.type = type;
		this.username = username;
	}

	// getters and setters
	public ArrayList<Ticket> getTickets() {
		return tickets;
	}

	public void setTickets(ArrayList<Ticket> tickets) {
		this.tickets = tickets;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}



	/*
	 * Handled in the controller
	// other functions
	Ticket buyTicket(Seat seat, Showtime showtime) {
		tickets.add(new Ticket(seat, showtime));
	}

	void selectSeat(Seat seat) {
		seat.claimSeat();
	}
	 */ 
	/**
	 * Converts class information to a string for GUI to use.
	 * @return Returns class in String form.
	 */
	@Override
	public String toString() {
		String t= " type: " + type + "\tusername: " + username + "\ttickets: \n"; 
		for(int i=0;i<tickets.size();i++)
			t+=tickets.get(i).toString();

		return t;
	}

	/** 
	 * The cancelTicket function, which is responsible for passing the username and type into the ticket classes cancel ticket function
	 * in order to cancel a ticket to receive a refund and receipt, as well as access the database. 
	 * @param t the Ticket object to be cancelled.
	 * */
	void cancelTicket(Ticket t) { //i think we need this here, so we can pass user name into the ticket class for a refund to simplify. 

		t.cancelTicket(username);

	}





}
