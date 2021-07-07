package main.model;

import java.sql.Timestamp;
import java.util.Date;

import main.controller.EmailController;
import main.controller.TicketController;
import main.controller.UserController;
import main.controller.PaymentController;
import main.controller.SeatController;
import main.controller.MovieController;
import main.controller.ShowtimeController;

/**
 * This class holds information about a ticket.
 * It is used to provide information to the GUI and update information in the dataBase when functions are called.
 * @author Alexa Calkhoven
 * @author Madisson Carle
 * @author Trevor Brown
 * @author Noah Bradley
 *
 */
public class Ticket {

	private int ticketId;
	private int seatId;
	private int showtimeId;
	private int paymentId;
	private Timestamp timePurchased;

	/**
	 * Ticket constructor
	 */
	public Ticket() {

	}

	/**
	 * Constructor of ticket 
	 * @param ticketId The unique ticket ID
	 * @param seatId The corresponding seat ID
	 * @param showtimeId The showtime ID of the ticket
	 * @param paymentId The tickets payment ID
	 * @param timePurchased The time the ticket was purchased
	 */
	public Ticket(int ticketId, int seatId, int showtimeId, int paymentId, Timestamp timePurchased) {
		this.ticketId = ticketId;
		this.seatId = seatId;
		this.showtimeId = showtimeId;
		this.paymentId = paymentId;
		this.timePurchased = timePurchased;

	}

	/**
	 * Converts Ticket class to a string to be passed over to the GUI.
	 * @return Returns the ticket class as a String.
	 */
	//NOTE :  reformatted so the time purchased would be readable. And got rid of other values that would add clutter in GUI area.
	@Override
	public String toString() {
		return "Ticket ID: " + ticketId 
				+ " timePurchased: " + timePurchased.toString() + "\n";
	}

	//getters and setters
	public int getTicketId() {
		return ticketId;
	}

	public void setTicketId(int ticketId) {
		this.ticketId = ticketId;
	}

	public int getSeatId() {
		return seatId;
	}

	public void setSeatId(int seatId) {
		this.seatId = seatId;
	}

	public int getShowtimeId() {
		return showtimeId;
	}

	public void setShowtimeId(int showtimeId) {
		this.showtimeId = showtimeId;
	}

	public int getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(int paymentId) {
		this.paymentId = paymentId;
	}

	public Timestamp getTimePurchased() {
		return timePurchased;
	}

	public void setTimePurchased(Timestamp timePurchased) {
		this.timePurchased = timePurchased;
	}

	/**
	 * Determines if the ticket is still able to be cancelled.
	 * The is determined by comparing the time of ticket purchase to the current time. 
	 * If the time is less than 72 hours the ticket be canceled.
	 * @return Returns true if it can be cancelled and false if it cannot be cancelled.
	 */
	public boolean validCancel() {

		ShowtimeController sc = new ShowtimeController();
		
		
		Showtime st = sc.getShowtime(showtimeId);
		
		if(st ==null) { //database error, so cancellation will not work.
			return false; 
		}
		
		
		Timestamp ts = new Timestamp(System.currentTimeMillis());
		
		long timeDifMS =  st.getTimeOfShow().getTime() - ts.getTime();				// get current time in ms and
																					// subtract purchase time in ms.

		int timeDifHrs = (int) timeDifMS / 3600000; // get the integer time difference in hours
		System.out.println(timeDifHrs);
		if (timeDifHrs >= 72) {
			return true;
		}

		return false;

	}

	/**
	 *This method first calls validCancel() to see if the ticket can be cancelled. 
	 *If it cannot be it returns, otherwise it creates an email refund receipt which will be different for different user types.
	 *RU get full refund and ordinary users get 15%deduction.
	 *The receipt is then sent and the seat of the ticket is freed up for the showtime.
	 * @param username The user who is canceling their ticket.
	 * @param type The user type. 
	 */
	@SuppressWarnings("deprecation")
	public void cancelTicket(String username) {

		
		//the controllers responsible for reading information from database. 
		TicketController tc = new TicketController();

		EmailController ec = new EmailController();

		PaymentController pc = new PaymentController();

		UserController uc = new UserController(); //to see if username matches a registered user or not. 
			
		SeatController sc = new SeatController();
		
		// check if cancellation is valid
		if (validCancel() == false) {
			return;
		}

		// Format Messaging for email:

		String message = "";
		Date currentDate = new Date();
		Payment payment = pc.getPayment(paymentId);
	
		if (payment == null) {
			System.err.println("Error in retrieving payment information for refund.");
			return;
		}

		// rough form of what it should like. Next version should have the actual amount
		// refunded by using payment id to get amount.
		
		
		//quick check in db if a user is registered or not. 
		int type = 0; //defaults to non registered user.
		
		
		for(RegisteredUser ru: uc.getRegisteredUsers()) {
			if(ru.getUsername().equals(username)) { //set the type to registerd user and exit the loop
				type =1;
				break;
			}
		}

		if (type == 1) { // if it is a registered user generate a return receipt email for a Ru - no 15%
							// deduction

			message = "Receipt for: " + username + "\n";
			message += "100% movie credit refund issued \n";
			message += "Ticket ID: " + ticketId + " seat ID: " + seatId + " Showtime ID: " + showtimeId + "Payment ID: "
					+ paymentId + "\n";
			message += "Date Purchased: " + timePurchased.toString() + "\n";
			message += "Date Refunded: " + currentDate.getMonth() + "/" + currentDate.getDate() + "/"
					+ (currentDate.getYear() + 1900) + " " + currentDate.getHours() + " : " + currentDate.getMinutes() + "\n";
			message += "You will be refunded $" + payment.amount + " as a movie credit. Show this email to redeem.";

		} else { // user is not RU so no discount
			message = "Receipt for: " + username + "\n";
			message += "85% movie credit refund issued \n"; // 100% minus 15% fee
			message += "Ticket ID: " + ticketId + " seat ID: " + seatId + " Showtime ID: " + showtimeId + "Payment ID: "
					+ paymentId + "\n";
			message += "Date Purchased: " + timePurchased.toString() + "\n";
			message += "Date Refunded: " + currentDate.getMonth() + "/" + currentDate.getDate() + "/"
					+ (currentDate.getYear() + 1900) + " " + currentDate.getHours() + " : " + currentDate.getMinutes() + "\n";
			message += "You will be refunded $" + 0.85 * payment.amount
					+ " as a movie credit. Show this email to redeem.";

		}

		// set up email
		Email email = ec.addEmail(0, message);

		ec.addEmailToUser(username, email.getEmailId()); // add the email to the user

		tc.cancelTicket(ticketId); // call the ticket controller to cancel the ticket. Should also free up the
									// occupied seat.
			
		sc.markSeatAsEmpty(seatId); //let the seat no longer be marked as occupied 
		
	}

	/**
	 * This method first requests payment and if it goes through it will update the seat status to taken and then a receipt message is made and sent to the user's email.
	 * @param username The user's username to be used when booking a ticket.
	 */
	public void makePurchaseReceipt(String username) {

		EmailController ec = new EmailController();

		PaymentController pc = new PaymentController();
		
		SeatController sc = new SeatController();
		
		ShowtimeController stc = new ShowtimeController();
		
		MovieController mc = new MovieController();

		// Format Messaging for email:

		String message = "";
		Payment payment = pc.getPayment(paymentId);
		Seat seat = sc.getSeat(seatId);
		Showtime st = stc.getShowtime(showtimeId);
		Movie movie = mc.getMovie(st.getMovieId());
		
		if (payment == null) {
			System.err.println("Error in retrieving payment information for purchase.");
		}

		message = "Receipt for: " + username + "\n";
		message += "Movie ticket purchased \n";
		message += "Ticket ID: " + ticketId + " seat ID: " + seatId + " Showtime ID: " + showtimeId + "Payment ID: "
				+ paymentId + "\n";
		message += "Seat: row " + seat.getRow() + " column " + seat.getCol() + ", screen " + seat.getScreen() + "\n";
		message += "Movie: " + movie.getName() + "\n";
		message += "Time: " + st.getTimeOfShow() + "\n";
		message += "Date Purchased: " + timePurchased.toString() + "\n";
		message += "Cost: $" + payment.amount + ". Show this email as your ticket.\n";

		// set up email
		Email email = ec.addEmail(0, message);

		ec.addEmailToUser(username, email.getEmailId()); // add the email to the user
	}

}

