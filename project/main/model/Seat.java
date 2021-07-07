package main.model;



/**
 * 
 * @author(s) Noah Bradley, Trevor Brown, Alexa Calkhoven, Madisson Carle
 * 
 * The Seat class. Represents a seat in a specific theater room with a specific row number and column number.
 * May also be a special type of seat only available to a RegisteredUser.
 * 
 */
public class Seat {

	/** The ID of a seat, which is stored in the Database. */
	int seatId;
	
	/** The row that the seat is located in. */
	int row;
	
	/** The column that the seat is located in. */
	int col;
	
	/** The theatre room number the seat is located in */
	String screen;
	
	/** A boolean that determines whether the seat is taken or not. */
	boolean isTaken;
	
	/** The type of the seat, where 1 represents a seat reserved for a RegisteredUser and 0 represents a seat reserved for an OrdinaryUser */
	int type; 
	
	
	/** The default constructor for the Seat object.*/
	public Seat() {
		
	}
	
	
	/** The non default constructor for the Seat object.
	 * @param seatID the ID of the seat for the Database to access.
	 * @param row the row number that the seat object is located
	 * @param col the column number that the seat object is located.
	 * @param screen the theatre room that the seat object is located 
	 * @param isTaken a boolean that checks whether the seat is occupied or not. 
	 * @param type the type of seat object. 0 is for ordinary users and 1 is for registered users. 
	 * 
	 * */
	public Seat(int seatId, int row, int col, String screen, boolean isTaken, int type) {
		this.seatId = seatId;
		this.row = row;
		this.col = col;
		this.screen = screen;
		this.isTaken = isTaken;
		this.type = type;
	}
	
	//getters and setters
	public int getSeatId() {
		return seatId;
	}
	public void setSeatId(int seatId) {
		this.seatId = seatId;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public boolean isTaken() {
		return isTaken;
	}
	public void setTaken(boolean isTaken) {
		this.isTaken = isTaken;
	}
	
	public String getScreen() {
		return screen;
	}
	public void setScreen(String screen) {
		this.screen = screen;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}


	/**
	 * Converts class information into a string to be used by GUI.
	 * @return Returns class in string form.
	 */
	@Override
	public String toString() {
		return "seatId: " + seatId + " row: " + row + " col: " + col + " screen: " + screen + " isTaken: "
				+ isTaken + " type: " + type;
	}
	
	
	
	
	
	/*
	//additional functions 
	void claimSeat() {
		isTaken = true;
	}
	
	void freeSeat() {
		isTaken = false;
	}
	*/
	
}
