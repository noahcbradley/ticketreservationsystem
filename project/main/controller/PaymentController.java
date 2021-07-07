package main.controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import main.model.Payment;

/**
 * Controls access to the Payment table in the database.
 * 
 * @author Alexa Calkhoven
 *
 */
public class PaymentController {

	private DBController DB;

	public PaymentController() {
		DB = DBController.getInstance();
	}

	/**
	 * Adds a payment to the DB, returns the new payment object.
	 * 
	 * @param amount  Amount paid in the payment.
	 * @param cardNum Card number used for the payment.
	 * @return Payment object created.
	 */
	public Payment addPayment(double amount, int cardNum) {
		int paymentId = DB.executeReturnKey("INSERT INTO Payment (amount, cardNum) VALUES (?, ?)", amount, cardNum);
		if (paymentId == -1)
			return null;
		return new Payment(paymentId, amount, cardNum);
	}

	/**
	 * Gets Payment for matching paymentId.
	 * 
	 * @param paymentId Id of payment desired.
	 * @return Payment object found. Null if error.
	 */
	public Payment getPayment(int paymentId) {
		ResultSet r = DB.query("SELECT * FROM Payment WHERE paymentId = ?", paymentId);
		try {
			if (r.next()) {
				return new Payment(r.getInt("paymentId"), r.getDouble("amount"), r.getInt("cardNum"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
