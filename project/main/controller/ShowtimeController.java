package main.controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import main.model.Showtime;

/**
 * Controls access to the Showtime table in the database.
 * 
 * @author Alexa Calkhoven
 *
 */
public class ShowtimeController {

	private DBController DB;

	public ShowtimeController() {
		DB = DBController.getInstance();
	}

	/**
	 * Retrieves all showtimes in the database.
	 * 
	 * @return Showtime list.
	 */
	public ArrayList<Showtime> getShowtimes() {
		ResultSet r = DB.query("SELECT * FROM Showtime");
		ArrayList<Showtime> showtimeList = new ArrayList<Showtime>();
		try {
			while (r.next()) {
				Showtime showtime = new Showtime(r.getInt("showtimeId"), r.getTimestamp("time"), r.getInt("movieId"));
				showtimeList.add(showtime);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return showtimeList;
	}

	/**
	 * Adds a showtime to the DB.
	 * 
	 * @param time    Time of the showtime (in form of a string).
	 * @param movieId PK of movie to link to showtime.
	 * @return Showtime object added. Null if error.
	 */
	public Showtime addShowtime(Timestamp time, int movieId) {
		int showtimeId = DB.executeReturnKey("INSERT INTO Showtime (time, movieId) VALUES (?, ?)", time, movieId);
		if (showtimeId == -1)
			return null;
		return new Showtime(showtimeId, time, movieId);
	}

	/**
	 * Gets Showtime for showtimeId.
	 * 
	 * @param showtimeId PK of showtime to retrieve.
	 * @return Showtime if successful. Null if error.
	 */
	public Showtime getShowtime(int showtimeId) {
		ResultSet r = DB.query("SELECT * FROM Showtime WHERE showtimeId = ?", showtimeId);
		try {
			if (r.next()) {
				return new Showtime(r.getInt("showtimeId"), r.getTimestamp("time"), r.getInt("movieId"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Gets Showtime for movieId.
	 * 
	 * @param showtimeId PK of showtime to retrieve.
	 * @return Showtime if successful. Null if error.
	 */
	public ArrayList<Showtime> getShowtimesForMovie(int movieId) {
		ResultSet r = DB.query("SELECT * FROM Showtime WHERE movieId = ?", movieId);
		ArrayList<Showtime> showtimeList = new ArrayList<Showtime>();
		try {
			while (r.next()) {
				Showtime showtime = new Showtime(r.getInt("showtimeId"), r.getTimestamp("time"), r.getInt("movieId"));
				showtimeList.add(showtime);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return showtimeList;
	}
	
	public boolean hasSeats(int showtimeId) {
		ResultSet result = DB.query("SELECT COUNT(*) AS count FROM ShowtimeToSeat WHERE showtimeId = ?", showtimeId);
		try {
			result.next();
			int seatCount = result.getInt("count");
			if (seatCount >= 20)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public void clearShowtimes() {
		DB.execute("DELETE FROM Showtime");
	}
}
