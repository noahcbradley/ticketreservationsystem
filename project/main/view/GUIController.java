package main.view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import main.controller.EmailController;
import main.controller.MovieController;
import main.controller.PaymentController;
import main.controller.SeatController;
import main.controller.ShowtimeController;
import main.model.Email;
import main.model.Movie;
import main.model.Payment;
import main.model.Seat;
import main.model.Showtime;
import main.model.Ticket;
import resources.Constants;
import main.controller.TicketController;
import main.controller.UserController;

/*
 * Controls buttons and frames of the GUI
 * 
 * @author Alexa Calkhoven
 * @author Madisson Carle
 * @author Trevor Brown
 * @author Noah Bradley
 */
public class GUIController {
	private MainFrame mainFrame;
	private UserLoginFrame loginFrame;
	private HomePageFrame homeFrame;
	private String type;
	private String username;
	private UserController uc;

	/*
	 * Constructor
	 * 
	 * @param f first frame of GUI
	 */
	public GUIController(MainFrame f) {
		mainFrame = f;
		mainFrame.addActionListeners(new LoginGuestListener(), new LoginRUListener(), new LoginOUListener());
	}

	/*
	 * Checks database to validate user entered username
	 * 
	 * @param u username defined by user
	 * @return true if username is valid, false otherwise
	 */
	public boolean validateUsername(String u) {
		uc = new UserController();

		// checks to see if username is in DB
		if (uc.isValidUser(u)) {
			if (type.equals("G")) {
				JOptionPane.showMessageDialog(mainFrame, "This username is already taken... Try again");
				return false;
			} else
				return true;
		} else {
			if (type.equals("G")) {
				uc.addGuestUser(u);
				return true;
			} else {
				JOptionPane.showMessageDialog(mainFrame, "Invalid Username... Try again"); 															
				return false;
			}
		}
	}

	/*
	 * Login guest button class
	 */
	public class LoginGuestListener implements ActionListener {

		/*
		 * Function called if this classes button is pressed.
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			type = "G";
			username = displayInputDialog("Username: ");
			if(username==null)
				return;
			boolean valid = validateUsername(username);
			if (!valid)
				return;

			loginFrame = new UserLoginFrame("Ticket Reservation System");
			loginFrame.addActionListeners(new LoginListener());
			mainFrame.dispose();
		}
	}

	/*
	 * Login OU button class
	 */
	public class LoginOUListener implements ActionListener {

		/*
		 * Function called if this classes button is pressed.
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			type = "O";
			username = displayInputDialog("Username: ");
			if(username==null)
				return;
			boolean valid = validateUsername(username);
			if (!valid)
				return;
			boolean isOu = validateOU(username);
			if (!isOu)
				return;
			loginFrame = new UserLoginFrame("Ticket Reservation System");
			loginFrame.addActionListeners(new LoginListener());
			mainFrame.dispose();
		}
	}

	/*
	 * Login RU button class
	 */
	public class LoginRUListener implements ActionListener {

		/*
		 * Function called if this classes button is pressed.
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			type = "R";
			username = displayInputDialog("Username: ");
			if(username==null)
				return;
			boolean valid = validateUsername(username);
			if (!valid)
				return;
			boolean isRu = validateRU(username);
			if (!isRu)
				return;
			loginFrame = new UserLoginFrame("Ticket Reservation System");
			loginFrame.addActionListeners(new LoginListener());
			mainFrame.dispose();
		}
	}

	/*
	 * Checks database to validate if user is an RU.
	 * 
	 * @param u username defined by user
	 * 
	 * @return true if user is valid RU, false otherwise
	 */
	public boolean validateRU(String u) {
		uc = new UserController();

		// checks to see if username is in DB
		if (!uc.isRegisteredUser(u)) {
			JOptionPane.showMessageDialog(mainFrame,
					"User is not a Registered User... Try logging in as an Ordinary User"); // should pop up an error
																							// window
			return false;
		}
		return true;
	}
	
	/*
	 * Checks database to validate if user is an RU.
	 * 
	 * @param u username defined by user
	 * 
	 * @return true if user is valid RU, false otherwise
	 */
	public boolean validateOU(String u) {
		uc = new UserController();

		// checks to see if username is in DB
		if (uc.isRegisteredUser(u)) {
			JOptionPane.showMessageDialog(mainFrame,
					"User is a Registered User... Try logging in as an Registered User"); // should pop up an error
																							// window
			return false;
		}
		return true;
	}

	/*
	 * Login button after users username is validated
	 */
	public class LoginListener implements ActionListener {

		/*
		 * Function called if this classes button is pressed.
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			homeFrame = new HomePageFrame("Ticket Reservation System", username, type);
			homeFrame.addActionListeners(new ViewMoviesListener(), new PurchaseTicketListener(),
					new ViewTicketsListener(), new CancelTicketListener(), new ViewEmailListener(),
					new PaySubscriptionListener(), new QuitListener(), new SearchMovieListener(),
					new RegisterListener());
			loginFrame.dispose();
		}
	}

	/*
	 * View movies button class
	 */
	public class ViewMoviesListener implements ActionListener {

		/*
		 * Function called if this classes button is pressed.
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			viewMovies();
		}
	}

	/*
	 * Called to display the list of movies currently available to the user
	 */
	private void viewMovies() {
		String message = "-----Movies-----\n";
		MovieController mc = new MovieController();
		ArrayList<Movie> movieList = mc.getMovies();
		for (int i = 0; i < movieList.size(); i++) {
			message += (movieList.get(i).toString() + "\n");
		}
		homeFrame.printToTextArea(message);
	}

	/**
	 * Allows user to purchase a ticket.
	 */
	public class PurchaseTicketListener implements ActionListener {

		/*
		 * Function called if this classes button is pressed.
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			viewMovies();
			String movie = displayInputDialog("Enter the name of the movie you would like to book a ticket for: ");
			if (movie == null)
				return;
			Movie m = searchMovie(movie);
			if (m == null)
				return;
			String showtime = displayInputDialog("Enter the id of the showtime you would like to book a ticket for: ");
			if (showtime == null)
				return;
			int showtimeId;
			try {
				showtimeId = Integer.parseInt(showtime);
			} catch (NumberFormatException n) {
				homeFrame.printToTextArea("Invalid showtime id entered.");
				return;
			}
			ShowtimeController stc = new ShowtimeController();
			Showtime selectedShowtime = stc.getShowtime(showtimeId);
			if (selectedShowtime == null || selectedShowtime.getMovieId() != m.getMovieId()) {
				homeFrame.printToTextArea("Invalid showtime selected.");
				return;
			}
			SeatController sc = new SeatController();
			Seat[][] seats = sc.getSeats(showtimeId);
			String seatChart = "{id} = Seat reserved for Registered Users.\n";
			seatChart += "[id] = Seat unavailable.\n";
			seatChart += "(id) = Seat available.\n";
			seatChart += "-----Screen-----\n";
			for (int i = 0; i < Constants.NUM_ROWS; i++) {
				for (int j = 0; j < Constants.NUM_COLS; j++) {
					Seat current = seats[i][j];
					int seatId = current.getSeatId();
					// {01} (02) (03) [04] [05]
					// curly brackets: reserved for RUs
					// round brackets: available
					// square brackets: unavailable
					String seatLabel = "";
					if (seatId < 10)
						seatLabel += "0";
					seatLabel += String.valueOf(seatId);
					if (current.isTaken())
						seatLabel = "[" + seatLabel + "]";
					else if (current.getType() == 1)
						seatLabel = "{" + seatLabel + "}";
					else
						seatLabel = "(" + seatLabel + ")";
					seatChart += (seatLabel + " ");
				}
				seatChart += "\n";
			}
			homeFrame.printToTextArea(seatChart);
			String seat = displayInputDialog("Enter the seat id you would like to book: ");
			if (seat == null)
				return;
			int seatId;
			try {
				seatId = Integer.parseInt(seat);
			} catch (NumberFormatException n) {
				homeFrame.printToTextArea("Invalid seat id entered.");
				return;
			}
			if (sc.isValidSeat(username, seatId)) {
				// go through with purchase
				homeFrame.printToTextArea("Selected ticket is $9.99. Please enter your payment information.");
				String cardNum = paymentProcess(username);
				int card = Integer.parseInt(cardNum);
				PaymentController pc = new PaymentController();
				Payment p = pc.addPayment(9.99, card);
				TicketController tc = new TicketController();
				Ticket t = tc.addTicket(seatId, showtimeId, p.getPaymentId());
				tc.assignTicketToUser(username, t.getTicketId());
				t.makePurchaseReceipt(username);
				sc.markSeatAsTaken(seatId);
				homeFrame.printToTextArea("Your receipt and ticket have been sent to your email!");
			} else {
				homeFrame.printToTextArea("This seat is not available for purchase.\n");
				return;
			}
		}
	}

	/*
	 * Button used to view users tickets
	 */
	public class ViewTicketsListener implements ActionListener {

		/*
		 * Function called when this classes button is pressed
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			String message = "-----My Tickets-----\n";
			TicketController tc = new TicketController();
			ArrayList<Ticket> ticketList = tc.getTickets(username);
			for (int i = 0; i < ticketList.size(); i++) {
				message += ticketList.get(i).toString();
			}
			homeFrame.printToTextArea(message);
		}
	}

	/*
	 * Button used to cancel user's ticket
	 */
	public class CancelTicketListener implements ActionListener {

		/*
		 * Function called if this classes button is pressed
		 */
		@Override
		public void actionPerformed(ActionEvent e) {

			String message = "-----My Tickets-----\n";
			TicketController tc = new TicketController();
			ArrayList<Ticket> ticketList = tc.getTickets(username);
			for (int i = 0; i < ticketList.size(); i++) {
				message += ticketList.get(i).toString();
			}

			homeFrame.printToTextArea(message);

			// get user inputted ticket ID value from the input dialog box.
			int ticketId = Integer.parseInt(displayInputDialog("Enter the ticketId you would like to cancel: "));

			Ticket ticket = null; // temp ticket object set to null for error checking.

			// check the ticket list to see if the ids match.
			for (Ticket t : ticketList) {
				if (t.getTicketId() == ticketId) {
					ticket = t;
					break;
				}
			}

			if (ticket == null) {

				// display error pane
				JOptionPane.showMessageDialog(new JFrame(), "Error! Ticket not found.", "ERROR",
						JOptionPane.ERROR_MESSAGE);
				return; // to avoid errors
			}

			boolean isCancellable = ticket.validCancel(); // check if we can cancel

			if (isCancellable == true) {
				ticket.cancelTicket(username);
				homeFrame.printToTextArea("Ticket " + ticketId + " successfully cancelled!");
			} else {
				JOptionPane.showMessageDialog(new JFrame(),
						"Error. You may not cancel your ticket less than 72 hours before the movie.", "ERROR",
						JOptionPane.ERROR_MESSAGE);

				return; // unsure about return but following Noah logic with the return in other spots.
			}

		}
	}

	/*
	 * Class responsible for the view email button
	 */
	public class ViewEmailListener implements ActionListener {

		/*
		 * Function called if this classes button is pressed
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			String message = "-----My Emails-----\n";
			EmailController ec = new EmailController();
			ArrayList<Email> emailList = ec.getEmails(username);
			for (int i = 0; i < emailList.size(); i++) {
				message += (emailList.get(i).getMessage() + "\n");
			}
			homeFrame.printToTextArea(message);
		}
	}

	/*
	 * Ensures the credit card entered by user is the correct one
	 * 
	 * @return card number
	 */
	public String paymentProcess(String u) {
		String cardNo = "";
		String message = "";
		if(type.equals("R")) {
			System.out.println(u);
			cardNo = String.valueOf(uc.getCardNum(u));
			message += "Card number preloaded... ";
		} else {
			cardNo = displayInputDialog(
					"Please enter your card number. Note: All card numbers must be between 10000000-99999999");
		}
		
		if (cardNo == null)
			return "cancel";
		
		message += "Is your card number: " + cardNo + "?\nIf yes Enter Y. If no Enter N";
		String confirm = displayInputDialog(message);
		if (confirm == null)
			return "cancel";
		if (confirm.equals("Y") && (Integer.parseInt(cardNo) < 99999999 && Integer.parseInt(cardNo) > 10000000))
			return cardNo;
		else
			return paymentProcess(u);
	}

	/*
	 * Used for registering a guest user into an OU or RU
	 * 
	 * @return array of strings filled with user info
	 */
	public String[] enterUserInfo() {
		String name = displayInputDialog("Please enter your name");
		if (name == null) {
			String[] temp = { "cancel" };
			return temp;
		}
		String address = displayInputDialog("Please enter your address");
		if (address == null) {
			String[] temp = { "cancel" };
			return temp;
		}
		String confirm = displayInputDialog("Please confirm this information is correct: \n" + "Name: " + name
				+ "\nAddress: " + address + "\n If yes Enter Y. If no Enter N");
		if (confirm == null) {
			String[] temp = { "cancel" };
			return temp;
		}
		if (confirm.equals("Y")) {
			String[] temp = { name, address };
			return temp;
		} else
			return enterUserInfo();
	}

	/*
	 * Enables user to pay their subscription and keep their membership.
	 */
	public class PaySubscriptionListener implements ActionListener {

		/*
		 * Function called when this classes button is pressed
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			if(!type.equals("R")) return;
			if(userNotPaid(username)) {
				double payment = 20.00;
				homeFrame.printToTextArea("Your outstanding payment is: $" + payment);
				String cardNo = paymentProcess(username);
				if (cardNo.equals("cancel"))
					return;
				String confirm = displayInputDialog("Please enter 'Confirm' if you would like to pay $" + payment
						+ " for a Registered User subscription");
				if (confirm == null)
					return;
				else if (confirm.equals("Confirm")) {
					PaymentController pc = new PaymentController();
					pc.addPayment(payment, Integer.parseInt(cardNo));
					String receipt = "A Registered User Subscription payment of $" + payment
							+ " was made to your account. Have a nice day!";
					EmailController ec = new EmailController();
					Email email = ec.addEmail(0, receipt);
					ec.addEmailToUser(username, email.getEmailId());
				}
				uc.confirmFeePaid(username);
				homeFrame.printToTextArea("Payment confirmed! Receipt sent to email");
			} else {
				homeFrame.printToTextArea("You have already paid your fee!");
			}

		}
	}
	
	public boolean userNotPaid(String u) {
		UserController uc = new UserController();
		int r = uc.getFeePaid(u);
		if(r == 0) return true;
		return false;
	}

	/*
	 * Used as the logout button
	 */
	public class QuitListener implements ActionListener {

		/*
		 * Function called when classes button is pressed. Logs out of current session
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			username=null;
			mainFrame.dispose();
			loginFrame.dispose();
			homeFrame.dispose();
			MainFrame frame = new MainFrame("Ticket Reservation System");
			GUIController gc= new GUIController(frame);
			//System.exit(1);
		}
	}

	/*
	 * Used only by guest users. Gives them the opportunity to register as an OU or
	 * RU. Class is a button.
	 */
	public class RegisterListener implements ActionListener {

		/*
		 * Function called when register is pressed
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			String selectedType = displayInputDialog(
					"Enter 'RU' to become a Registered User or 'OU' to become an Ordinary User");
			if (selectedType == null)
				return;
			if (selectedType.compareTo("RU") == 0) {
				boolean added = addNewUserCredentials(selectedType);
				if (added)
					homeFrame.printToTextArea("You are now a Registered User. Logout and login to see the changes.");
				else
					homeFrame.printToTextArea("One or more of your inputs was invalid. Please try again.");
			} else if (selectedType.compareTo("OU") == 0) {
				boolean added = addNewUserCredentials(selectedType);
				if (added)
					homeFrame.printToTextArea("You are now a Ordinary User. Logout and login to see the changes.");
				else
					homeFrame.printToTextArea("Something went wrong... Try again.");
			} else
				homeFrame.printToTextArea("Invalid entry... Try again!");

		}
	}

	/*
	 * Button responsible for searching for a movie
	 */
	public class SearchMovieListener implements ActionListener {

		/*
		 * Function called when register is pressed
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			// make into a function (have to use in the purchase ticket)
			String movie = displayInputDialog("Enter the name of a movie you would like to search for: ");
			searchMovie(movie);
		}
	}

	/*
	 * Gets the guests credentials to add them as a user
	 * 
	 * @param t
	 * 
	 * @return true if user fills all fields correctly, fasle otherwise
	 */
	public boolean addNewUserCredentials(String t) {
		if (t.compareTo("RU") == 0) {
			// System.out.println(username);
			String n = displayInputDialog("Enter your name: ");
			if (n == null)
				return false;
			String a = displayInputDialog("Enter your address: ");
			if (a == null)
				return false;
			String c = displayInputDialog("Enter your card number, must be between 10000000-99999999: ");
			if (c == null)
				return false;
			uc.addRegisteredUser(username, n, a, Integer.parseInt(c), false); // assume member must pay fee when logged
																				// in as RU
		} else if (t.compareTo("OU") == 0)
			uc.addGuestUser(username);
		return true;
	}

	/*
	 * Looks for the movie in the database the user defined
	 * 
	 * @param movie user defined movie
	 * 
	 * @return movie object if found, null otherwise
	 */
	private Movie searchMovie(String movie) {
		MovieController mc = new MovieController();
		Movie m = mc.searchMovie(movie);
		String message = "-----Movie-----\n";
		if (m == null) {
			message += "Sorry, movie entered is not available.";
			homeFrame.printToTextArea(message);
			return null;
		} else {
			message += (movie.toString() + "\n");
			message += "-----Showtimes-----\n";
			ShowtimeController sc = new ShowtimeController();
			ArrayList<Showtime> sList = sc.getShowtimesForMovie(m.getMovieId());
			for (int i = 0; i < sList.size(); i++) {
				message += (sList.get(i).toString() + "\n");
			}
		}
		homeFrame.printToTextArea(message);
		return m;
	}

	public String displayInputDialog(String s) {
		return JOptionPane.showInputDialog(s);
	}

}
