package main.controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import main.model.Movie;

/**
 * Controls access to the Movie table in the database.
 * 
 * @author Alexa Calkhoven
 *
 */
public class MovieController {

	private DBController DB;

	public MovieController() {
		DB = DBController.getInstance();
	}

	/**
	 * Returns all movies in the database. will be useful for search movies.
	 * 
	 * @return ArrayList of all movies.
	 */
	public ArrayList<Movie> getMovies() {
		ResultSet r = DB.query("SELECT * FROM Movie");
		ArrayList<Movie> movieList = new ArrayList<Movie>();
		try {
			while (r.next()) {
				Movie movie = new Movie(r.getInt("movieId"), r.getString("name"));
				movieList.add(movie);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return movieList;
	}

	/**
	 * Adds movie to the database. Used to fill the database with initial data.
	 * 
	 * @param name Name of the movie.
	 * @return New movie added.
	 */
	public Movie addMovie(String name) {
		int movieId = DB.executeReturnKey("INSERT INTO Movie (name) VALUES (?)", name);
		if (movieId == -1) {
			// movie might already exist... let's try to search it
			return searchMovie(name);
		}
		return new Movie(movieId, name);
	}

	/**
	 * Gets movie from the database with matching id.
	 * 
	 * @param movieId Id of desired movie.
	 * @return Movie found. Null if error.
	 */
	public Movie getMovie(int movieId) {
		ResultSet r = DB.query("SELECT * FROM Movie WHERE movieId = ?", movieId);
		try {
			if (r.next()) {
				return new Movie(r.getInt("movieId"), r.getString("name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Gets movie from the database with matching name.
	 * 
	 * @param movieId Name of desired movie.
	 * @return Movie found. Null if error.
	 */
	public Movie searchMovie(String name) {
		ResultSet r = DB.query("SELECT * FROM Movie WHERE name = ?", name);
		try {
			if (r.next()) {
				return new Movie(r.getInt("movieId"), r.getString("name"));
			} else {
				return null; // no match found!
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void clearMovies() {
		DB.execute("DELETE FROM Movie");
	}

}
