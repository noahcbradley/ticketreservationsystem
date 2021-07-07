package main.controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import main.model.Seat;
import resources.Constants;

/**
 * Controls access to the Seat table in the database.
 * 
 * @author Alexa Calkhoven
 *
 */
public class SeatController {

	private DBController DB;

	public SeatController() {
		DB = DBController.getInstance();
	}

	/**
	 * Retrieves a 2D array of seats from the database.
	 * 
	 * @param showtimeId Showtime to recieve seats for.
	 * @return 2D array of seats.
	 */
	public Seat[][] getSeats(int showtimeId) {
		ResultSet r = DB.query("SELECT seatId FROM ShowtimeToSeat WHERE showtimeId = ?", showtimeId);
		Seat[][] seatList = new Seat[Constants.NUM_ROWS][Constants.NUM_COLS];
		try {
			while (r.next()) {
				int seatId = r.getInt("seatId");
				ResultSet s = DB.query("SELECT * FROM Seat WHERE seatId = ?", seatId);
				s.next();
				int row = s.getInt("rowNumber");
				int col = s.getInt("colNumber");
				Seat seat = new Seat(s.getInt("seatId"), row, col, s.getString("screen"), s.getBoolean("isTaken"),
						s.getInt("seatType"));
				seatList[row][col] = seat;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return seatList;
	}

	/**
	 * Determines if a seat with seatId is valid. First checks that it exists. Then
	 * checks if it's taken. Lastly checks if the username entered is valid to
	 * purchase the seat or not.
	 * 
	 * Note: may be beneficial to break these down into separate functions.
	 * 
	 * @param username PK of user.
	 * @param seatId   points to the seat that we want to check.
	 * @return true if seat is valid, false if not.
	 */
	public boolean isValidSeat(String username, int seatId) {
		// check first that the seat exists
		ResultSet countResult = DB.query("SELECT COUNT(*) AS count FROM Seat WHERE Seat.seatId = ?", seatId);
		try {
			countResult.next();
			int seatCount = countResult.getInt("count");
			if (seatCount != 1)
				return false;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// check if seat is taken
		ResultSet seatResult = DB.query("SELECT * FROM Seat WHERE Seat.seatId = ?", seatId);
		try {
			seatResult.next();
			boolean taken = seatResult.getBoolean("isTaken");
			if (taken)
				return false;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// check if seat is the correct type
		// seatType = 0 --> normal seat
		// seatType = 1 --> reserved seat
		// userType = 0 --> OU
		// userType = 1 --> RU
		ResultSet seatTypeResult = DB.query("SELECT seatType FROM Seat WHERE Seat.seatId = ?", seatId);
		ResultSet userTypeResult = DB.query("SELECT userType FROM User WHERE User.username = ?", username);
		try {
			seatTypeResult.next();
			userTypeResult.next();
			int seatType = seatTypeResult.getInt("seatType");
			if (seatType == 0)
				return true;
			int userType = userTypeResult.getInt("userType");
			if (userType == 0)
				return false;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * Changes status of seat from false (not taken) to true (taken). If already
	 * taken, does nothing.
	 * 
	 * @param seatId seat to update.
	 */
	public void markSeatAsTaken(int seatId) {
		DB.execute("UPDATE seat SET isTaken = 1 WHERE seatId = ?", seatId);
	}
	
	/**
	 * Changes status of seat from true (taken) to false (not taken). If already
	 * empty, does nothing.
	 * 
	 * @param seatId seat to update.
	 */
	public void markSeatAsEmpty(int seatId) {
		DB.execute("UPDATE seat SET isTaken = 0 WHERE seatId = ?", seatId);
	}

	/**
	 * Adds seat to DB and returns the Seat.
	 * 
	 * @param row      Row number of the seat.
	 * @param col      Col number of the seat.
	 * @param screen   Screen that the seat is in.
	 * @param isTaken  If the seat is currently occupied.
	 * @param seatType 0 -> normal seat, 1 -> reserved for RUs.
	 * @return Seat added.
	 */
	public Seat addSeat(int row, int col, String screen, boolean isTaken, int seatType) {
		// Note: the exact same row, col, screen will still have a diff seatId because
		// Seat corresponds
		// to a certain instance of a seat (so that the same seat can be taken or not
		// for different shows)
		int seatId = DB.executeReturnKey(
				"INSERT INTO Seat (rowNumber, colNumber, screen, isTaken, seatType) VALUES (?, ?, ?, ?, ?)", row, col,
				screen, isTaken, seatType);
		if (seatId == -1)
			return null;
		return new Seat(seatId, row, col, screen, isTaken, seatType);
	}

	/**
	 * Assigns a seat to a showtime.
	 * 
	 * @param showtimeId Showtime to add seat to.
	 * @param seatId     Seat being added.
	 * @return True if successful, false if not.
	 */
	public boolean addSeatToShowtime(int showtimeId, int seatId) {
		int rows = DB.execute("INSERT INTO ShowtimeToSeat (showtimeId, seatId) VALUES (?, ?)", showtimeId, seatId);
		if (rows == 1)
			return true;
		return false;
	}

	/**
	 * Gets Ticket for ticketId.
	 * 
	 * @param seatId PK for seat to be retrieved.
	 * @return Seat if found. Null if error.
	 */
	public Seat getSeat(int seatId) {
		ResultSet r = DB.query("SELECT * FROM Seat WHERE seatId = ?", seatId);
		try {
			if (r.next()) {
				return new Seat(r.getInt("seatId"), r.getInt("rowNumber"), r.getInt("colNumber"), r.getString("screen"),
						r.getBoolean("isTaken"), r.getInt("seatType"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
