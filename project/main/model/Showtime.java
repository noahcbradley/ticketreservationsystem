package main.model;

import java.sql.Timestamp;

/**
 * Holds information on all the available showtimes for specific movies.
 * This class is used to pass information between GUI and controllers.
 * @author Alexa Calkhoven
 * @author Madisson Carle
 * @author Trevor Brown
 * @author Noah Bradley
 * 
 *
 */
public class Showtime {

	private Timestamp timeOfShow; // keep this as a string for now since we only really have to print it out.
	private int showtimeId;
	private int movieId;

	/**
	 * Constructs showtime
	 * @param showtimeId Id of showtime
	 * @param time time of showtime
	 * @param movieId movie at showtime
	 */
	public Showtime(int showtimeId, Timestamp time, int movieId) {
		this.showtimeId = showtimeId;
		this.timeOfShow = time;
		this.movieId = movieId;
	}

	/**
	 * constructor
	 */
	public Showtime() {
			
	}

	/**
	 * Converts showtime to string for GUI
	 * @return Returns the string result
	 */
	@Override
	public String toString() {
		return "time:" + timeOfShow.toString() + "\tshowtimeId: " + showtimeId + "\tmovieId: " + movieId;
	}

	/**
	 * Gets time for showtime
	 * @return Returns time as a string
	 */
	public Timestamp getTimeOfShow() {
		return timeOfShow;
	}

	/**
	 * Sets time for showtime
	 * @param time Time to be set
	 */
	public void setTime(Timestamp time) {
		this.timeOfShow = time;
	}

	/**
	 * Gets the showtime Id
	 * @return Returns the int showtime ID
	 */
	public int getShowtimeId() {
		return showtimeId;
	}

	/**
	 * Sets the showtime ID
	 * @param showtimeId The ID to be set
	 */
	public void setShowtimeId(int showtimeId) {
		this.showtimeId = showtimeId;
	}

	/**
	 * Gets the movie ID for the showtime
	 * @return Returns the int Movie ID
	 */
	public int getMovieId() {
		return movieId;
	}

	/**
	 * Sets the movie ID for the showtime
	 * @param movieId The movie ID to be set
	 */
	public void setMovieId(int movieId) {
		this.movieId = movieId;
	}

}

