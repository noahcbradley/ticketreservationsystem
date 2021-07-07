package main.model;



/**
 * 
 * @author(s) Noah Bradley, Trevor Brown, Alexa Calkhoven, Madisson Carle
 * 
 * ThePayment class. Responsible for keeping track of payment information.
 * 
 */
public class Payment {

	/** The ID of the a payment object. Stored in the Database. */
	int paymentId;
	
	/** The amount to be paid.*/
	double amount;
	
	/** The 8-digit card number to be charged to. */
	int cardNum;
	
	/** The default constructor for the Seat object.*/
	public Payment() {
		
	}
	
	/** The non-default constructor for the Seat object.
	 * @param paymentId The ID of the a payment object. Stored in the Database.
	 * @param amount The amount to be paid.
	 * @param cardNum The 8-digit card number to be charged to.
	 * */
	public Payment(int paymentId, double amount, int cardNum) {
		this.paymentId = paymentId;
		this.amount = amount;
		this.cardNum = cardNum;
	}
	

	//getters and setters:
	public int getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(int paymentId) {
		this.paymentId = paymentId;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public int getCardNum() {
		return cardNum;
	}

	public void setCardNum(int cardNum) {
		this.cardNum = cardNum;
	}

	/**
	 * Converts class information int oa string to be passed to GUI
	 * @return Returns class in string form
	 */
	@Override
	public String toString() {
		return "amount: " + amount +  "\tcardNum: " + cardNum+ "\tpaymentId: " + paymentId ;
	}
	
	

	/*
	//additoinal functions 
	void makePaymentToTheatre(User user) { //need to change payment functionality now that account is no longer going to be considered . 
		
		
	}
	
	void makeRefundToUser(User user) { //need to change payment functionality now that account is no longer going to be considered . 
		
	}
	*/
}
