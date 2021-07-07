package resources;

import main.controller.MovieController;
import main.controller.ShowtimeController;
import main.controller.SeatController;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import main.controller.EmailController;
import main.controller.UserController;
import main.model.Movie;
import main.model.RegisteredUser;
import main.model.Seat;
import main.model.Showtime;
import main.model.Email;

public class SampleData {

	public static void addSampleData() {
		addMoviesAndShowtimes();
		addPromotionEmails();
	}

	@SuppressWarnings("deprecation")
	public static void addMoviesAndShowtimes() {
		MovieController mc = new MovieController();
		ShowtimeController sc = new ShowtimeController();
		

		Timestamp d1 = new Timestamp(2020 - 1900, 11, 1, 19, 0, 0, 0);
		Timestamp d2 = new Timestamp(2020 - 1900, 11, 7, 21, 0, 0, 0);
		Timestamp d3 = new Timestamp(2020 - 1900, 11, 3, 19, 0, 0, 0);
		Timestamp d4 = new Timestamp(2020 - 1900, 11, 3, 21, 0, 0, 0);
		Timestamp d5 = new Timestamp(2020 - 1900, 11, 1, 13, 0, 0, 0);
		Timestamp d6 = new Timestamp(2020 - 1900, 11, 2, 17, 0, 0, 0);
		Timestamp d7 = new Timestamp(2020 - 1900, 11, 7, 13, 0, 0, 0);
		Timestamp d8 = new Timestamp(2020 - 1900, 11, 2, 15, 0, 0, 0);
		
		Movie m1 = mc.addMovie("The Incredibles");
		Showtime s1a = sc.addShowtime(d1, m1.getMovieId());
		if(s1a != null) addSeats(s1a.getShowtimeId(), "A");
		Showtime s1b = sc.addShowtime(d2, m1.getMovieId());
		if(s1b != null) addSeats(s1b.getShowtimeId(), "B");
		Showtime s1c = sc.addShowtime(d3, m1.getMovieId());
		if(s1c != null) addSeats(s1c.getShowtimeId(), "A");
		Showtime s1d = sc.addShowtime(d4, m1.getMovieId());
		if(s1d != null) addSeats(s1d.getShowtimeId(), "B");

		Movie m2 = mc.addMovie("Mean Girls");
		Showtime s2a = sc.addShowtime(d6, m2.getMovieId());
		if(s2a != null) addSeats(s2a.getShowtimeId(), "C");
		Showtime s2b = sc.addShowtime(d4, m2.getMovieId());
		if(s2b != null) addSeats(s2b.getShowtimeId(), "C");

		Movie m3 = mc.addMovie("The Grinch");
		Showtime s3a = sc.addShowtime(d7, m3.getMovieId());
		if(s3a != null) addSeats(s3a.getShowtimeId(), "A");
		Showtime s3b = sc.addShowtime(d8, m3.getMovieId());
		if(s3b != null) addSeats(s3b.getShowtimeId(), "B");
		Showtime s3c = sc.addShowtime(d2, m3.getMovieId());
		if(s3c != null) addSeats(s3c.getShowtimeId(), "A");

		Movie m4 = mc.addMovie("Clueless");
		Showtime s4a = sc.addShowtime(d1, m4.getMovieId());
		if(s4a != null) addSeats(s4a.getShowtimeId(), "D");
		Showtime s4b = sc.addShowtime(d3, m4.getMovieId());
		if(s4b != null) addSeats(s4b.getShowtimeId(), "D");
		Showtime s4c = sc.addShowtime(d4, m4.getMovieId());
		if(s4c != null) addSeats(s4c.getShowtimeId(), "D");
		Showtime s4d = sc.addShowtime(d8, m4.getMovieId());
		if(s4d != null) addSeats(s4d.getShowtimeId(), "D");

		Movie m5 = mc.addMovie("Avengers: Endgame");
		Showtime s5a = sc.addShowtime(d5, m5.getMovieId());
		if(s5a != null) addSeats(s5a.getShowtimeId(), "E");
		Showtime s5b = sc.addShowtime(d6, m5.getMovieId());
		if(s5b != null) addSeats(s5b.getShowtimeId(), "E");
	}

	public static void addSeats(int showtimeId, String theatre) {
		SeatController sc = new SeatController();
		for (int i = 0; i < Constants.NUM_ROWS; i++) {
			for (int j = 0; j < Constants.NUM_COLS; j++) {
				int type = 0; 
				if((i == 0 && j == 0) || (i == 0 && j == 1)) type = 1;
				Seat added = sc.addSeat(i, j, theatre, false, type);
				sc.addSeatToShowtime(showtimeId, added.getSeatId());
			}
		}
	}

	public static void addPromotionEmails() {
		addPromotionEmailsToUsers(
				"\nAvengers: Endgame is out now! Book tickets today. 2 seats reserved specially for Registered Users.");
		addPromotionEmailsToUsers(
				"\nClueless is out now! Book tickets today. 2 seats reserved specially for Registered Users.");
		addPromotionEmailsToUsers(
				"\nNew movie is coming out tomorrow. Keep an eye out on your inbox for more information soon.");

	}

	public static void addPromotionEmailsToUsers(String message) {
		EmailController ec = new EmailController();
		Email e = ec.addEmail(1, message);
		if(e == null) return;
		UserController uc = new UserController();
		ArrayList<RegisteredUser> ruList = uc.getRegisteredUsers();
		for (int i = 0; i < ruList.size(); i++) {
			ec.addEmailToUser(ruList.get(i).getUsername(), e.getEmailId());
		}
	}

}

