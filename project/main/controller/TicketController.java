package main.controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import main.model.Ticket;

/**
 * Controls access to the Ticket table in the database.
 * 
 * @author Alexa Calkhoven
 *
 */
public class TicketController {

	private DBController DB;

	public TicketController() {
		DB = DBController.getInstance();
	}

	/**
	 * Adds ticket to the database.
	 * 
	 * @param seatId     PK of seat for ticket.
	 * @param showtimeId PK of showtime for ticket.
	 * @param paymentId  PK of payment made for ticket.
	 * @return Ticket if added successfully. Null if not.
	 */
	public Ticket addTicket(int seatId, int showtimeId, int paymentId) {
		// note: ticketId is automatically generated
		// timePurchased will be set to current time/date (date and time)
		//Date date = new Date();
		Timestamp ts = new Timestamp(System.currentTimeMillis());
		int ticketId = DB.executeReturnKey(
				"INSERT INTO Ticket (seatId, showtimeId, paymentId, timePurchased) VALUES (?, ?, ?, ?)", seatId,
				showtimeId, paymentId, ts);
		if (ticketId == -1)
			return null;
		return new Ticket(ticketId, seatId, showtimeId, paymentId, ts);
	}

	/**
	 * Gets all tickets for a user.
	 * 
	 * @param username Username to retrieve tickets for.
	 * @return ArrayList of tickets.
	 */
	// Returns a list of tickets associated with a user.
	public ArrayList<Ticket> getTickets(String username) {
		ResultSet r = DB.query("SELECT ticketId FROM UserToTicket WHERE username = ?", username);
		ArrayList<Ticket> ticketList = new ArrayList<Ticket>();
		try {
			while (r.next()) {
				int ticketId = r.getInt("ticketId");
				ResultSet t = DB.query("SELECT * FROM Ticket WHERE ticketId = ?", ticketId);
				t.next();
				Ticket ticket = new Ticket(t.getInt("ticketId"), t.getInt("seatId"), t.getInt("showtimeId"),
						t.getInt("paymentId"), t.getTimestamp("timePurchased"));
				ticketList.add(ticket);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ticketList;
	}

	/**
	 * Inserts a row to UserToTicket, assigning a ticket to a user.
	 * 
	 * @param username FK of user to be assigned ticket.
	 * @param ticketId FK of ticket being assigned.
	 * @return True if successful, False if failed.
	 */
	public boolean assignTicketToUser(String username, int ticketId) {
		int rows = DB.execute("INSERT INTO UserToTicket (username, ticketId) VALUES (?, ?)", username, ticketId);
		if (rows != 1)
			return false;
		return true;
	}

	/**
	 * Deletes ticket from Ticket table. Cascade deletes all entries with ticketId
	 * in UserToTicket as well.
	 * 
	 * @param ticketId ticket to be deleted.
	 * @return true is successful, false if error.
	 */
	public boolean cancelTicket(int ticketId) {
		int rows = DB.execute("DELETE FROM Ticket WHERE ticketId = ?", ticketId);
		if (rows != 1)
			return false;
		return true;
	}

	/**
	 * Gets a ticket from the database.
	 * 
	 * @param ticketId PK of ticket to get.
	 * @return Ticket if found. Null if error.
	 */
	public Ticket getTicket(int ticketId) {
		ResultSet r = DB.query("SELECT * FROM Ticket WHERE ticketId = ?", ticketId);
		try {
			if (r.next()) {
				return new Ticket(r.getInt("ticketId"), r.getInt("seatId"), r.getInt("showtimeId"),
						r.getInt("paymentId"), r.getTimestamp("timePurchased"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
