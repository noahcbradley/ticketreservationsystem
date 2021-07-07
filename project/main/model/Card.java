package main.model;

// likely wont need this class anymore either
public class Card {

	private int number;
	private int pin;
	
	public Card(int number, int pin) {
		this.number = number;
		this.pin = pin;
	}
	
	public boolean recieveRefund(double value) {
		//also not sure
		return true;
	}
	
	public boolean makePayment(double value) {
		//not sure how payment is going to work
		return true;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public int getPin() {
		return pin;
	}

	public void setPin(int pin) {
		this.pin = pin;
	}
}
