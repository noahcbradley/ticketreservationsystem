package main.model;

/**
 * This class holds information about a movie including its name and ID
 * This is used to pass class information to the GUI
  *@author Alexa Calkhoven
 * @author Madisson Carle
 * @author Trevor Brown
 * @author Noah Bradley
 *
 */
public class Movie {

	private String name; //movie name
	private int movieId; //id of movie for database

	/**
	 * Constructor for movie 
	 * @param movieId The movie ID
	 * @param name The movie name
	 */
	public Movie(int movieId, String name) {
		this.name = name;
		this.movieId = movieId;
	}

	/**
	 * Movie constructor
	 */
	public Movie() {

	}

	/**
	 * Converts movie class to string for use in the GUI
	 * @return Returns the movie as a String 
	 */
	@Override
	public String toString() {
		String tabs = "\t";
		if(name.length() <= 8) tabs += "\t";
		return "name: " + name + tabs + "movieId: " + movieId;
	}

	//getters and setters
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getMovieId() {
		return movieId;
	}

	public void setMovieId(int movieId) {
		this.movieId = movieId;
	}

}
