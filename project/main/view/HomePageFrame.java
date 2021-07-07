package main.view;
import javax.swing.*;
import java.awt.*;


/**
 *  Page used as homepage for all users. This is the page with most GUI functionality
 * @author Alexa Calkhoven
 * @author Madisson Carle
 * @author Trevor Brown
 * @author Noah Bradley
 *
 */
@SuppressWarnings("serial")
public class HomePageFrame extends JFrame{
	private JPanel mainPanel, buttonPanel;
	//buttons for frame
	private JButton registerGuest =new JButton("Register now!");
	private JButton viewMovies  = new JButton("View all movies");
	private JButton searchMovies = new JButton("Search movie");
	private JButton purchaseTicket  = new JButton("Purchase ticket");
	private JButton viewTickets = new JButton("View my tickets");
	private JButton cancelTicket = new JButton("Cancel ticket");
	private JButton viewEmail = new JButton("View my emails");
	private JButton paySubscription = new JButton("Pay subscription");
	private JButton logoutButton = new JButton("Logout");
	//labels for frame
	private JLabel welcome;
	private JLabel message = new JLabel("Please choose an option below:       ");
	//text area for all information
	private JTextArea displayArea= new JTextArea();
	private String username;

	private String userType;

	/*
	 * Constructor
	 * @param s Frame name
	 * @param u username of user
	 * @param type type of user
	 */
	public HomePageFrame (String s, String u, String type){
		super(s);
		username = u;
		userType = type;
		//initializing welcome message
		String temp = "Welcome to your Homepage " + username + "!       ";
		welcome = new JLabel(temp);
		welcome.setAlignmentX(CENTER_ALIGNMENT);
		welcome.setFont(new Font("Serif", Font.PLAIN, 30));
		//setting frame info
		setSize(1000, 1000);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout());

		//main panel with welcome message
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(welcome);

		//button panel
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

		//Initializing button alignment
		message.setAlignmentX(CENTER_ALIGNMENT);
		message.setFont(new Font("Serif", Font.PLAIN, 30));
		if(userType == "G"){
			registerGuest.setAlignmentX(Component.CENTER_ALIGNMENT);
			registerGuest.setBackground(new java.awt.Color(255, 215, 0));
			registerGuest.setOpaque(true);
		}
		searchMovies.setAlignmentX(Component.CENTER_ALIGNMENT);
		viewMovies.setAlignmentX(Component.CENTER_ALIGNMENT);
		purchaseTicket.setAlignmentX(Component.CENTER_ALIGNMENT);
		cancelTicket.setAlignmentX(Component.CENTER_ALIGNMENT);
		viewTickets.setAlignmentX(Component.CENTER_ALIGNMENT);
		viewEmail.setAlignmentX(Component.CENTER_ALIGNMENT);
		paySubscription.setAlignmentX(Component.CENTER_ALIGNMENT);
		logoutButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		//setting colour
		searchMovies.setBackground(new java.awt.Color(255, 215, 0));
		searchMovies.setOpaque(true);
		viewMovies.setBackground(new java.awt.Color(255, 215, 0));
		viewMovies.setOpaque(true);
		purchaseTicket.setBackground(new java.awt.Color(255, 215, 0));
		purchaseTicket.setOpaque(true);
		cancelTicket.setBackground(new java.awt.Color(255, 215, 0));
		cancelTicket.setOpaque(true);
		viewTickets.setBackground(new java.awt.Color(255, 215, 0));
		viewTickets.setOpaque(true);
		viewEmail.setBackground(new java.awt.Color(255, 215, 0));
		viewEmail.setOpaque(true);
		paySubscription.setBackground(new java.awt.Color(255, 215, 0));
		paySubscription.setOpaque(true);
		logoutButton.setBackground(new java.awt.Color(255, 215, 0));
		logoutButton.setOpaque(true);

		//adding buttons to button panel
		buttonPanel.add(Box.createRigidArea(new Dimension(20, 20)));
		buttonPanel.add(message);
		if(userType == "G"){
			buttonPanel.add(Box.createRigidArea(new Dimension(20, 10)));
			buttonPanel.add(registerGuest);
		}

		buttonPanel.add(Box.createRigidArea(new Dimension(20, 10)));
		buttonPanel.add(searchMovies);
		buttonPanel.add(Box.createRigidArea(new Dimension(20, 10)));
		buttonPanel.add(viewMovies);
		buttonPanel.add(Box.createRigidArea(new Dimension(20, 10)));
		buttonPanel.add(purchaseTicket);
		buttonPanel.add(Box.createRigidArea(new Dimension(20, 10)));
		buttonPanel.add(cancelTicket);
		buttonPanel.add(Box.createRigidArea(new Dimension(20, 10)));
		buttonPanel.add(viewTickets);
		buttonPanel.add(Box.createRigidArea(new Dimension(20, 10)));
		buttonPanel.add(viewEmail);
		buttonPanel.add(Box.createRigidArea(new Dimension(20, 10)));
		buttonPanel.add(paySubscription);
		buttonPanel.add(Box.createRigidArea(new Dimension(20, 10)));
		buttonPanel.add(logoutButton);


		//center panel that holds all text
		displayArea.setSize(100,100);
		displayArea.setBackground(new java.awt.Color(0, 200, 250));
		JPanel panel3= new JPanel();
		panel3.setSize(100,100);
		panel3.setLayout(new BorderLayout());
		panel3.add(displayArea,BorderLayout.CENTER);
		JScrollPane scroll = new JScrollPane (displayArea);
		scroll.setSize(100, 100);
		panel3.add(scroll);

		//adding all panels to the frame
		add(mainPanel, BorderLayout.NORTH);
		add(panel3,BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);
		
		getContentPane().setBackground(new java.awt.Color(0, 150, 200));
        buttonPanel.setBackground(new java.awt.Color(0, 150, 200));
		setVisible(true);

	}


	/*
	 * Once buttons are created, their actionListeners are linked
	 * @param movies view movies listener
	 * @param ticket purchase ticket listener
	 * @param tickets user tickets listener
	 * @param cancel cancel button listener
	 * @param emails email button listener
	 * @param pay pay subscription button listener
	 * @param quit logout button listener
	 * @param searchM search movie listener
	 * @param register register user for guest - button listener
	 */
	public void addActionListeners(GUIController.ViewMoviesListener movies, GUIController.PurchaseTicketListener ticket, 
			GUIController.ViewTicketsListener tickets, GUIController.CancelTicketListener cancel,
			GUIController.ViewEmailListener emails, GUIController.PaySubscriptionListener pay,
			GUIController.QuitListener quit, GUIController.SearchMovieListener searchM,
			GUIController.RegisterListener register){
		if(userType == "G")
			registerGuest.addActionListener(register);

		searchMovies.addActionListener(searchM);
		viewMovies.addActionListener(movies);
		purchaseTicket.addActionListener(ticket);
		viewTickets.addActionListener(tickets);
		cancelTicket.addActionListener(cancel);
		viewEmail.addActionListener(emails);
		paySubscription.addActionListener(pay);
		logoutButton.addActionListener(quit);
	}

	/**
	 * prints string to text area
	 * @param s The string to be printed
	 */
	public void print(String s) {
		displayArea.append(s);
	}
	/**
	 * resets text area so new text can be printed
	 */
	public void resetDisplay() {
		displayArea.setText(null);
	}
	/*
	 * Prints argument to text area in GUI
	 * @param s message to print
	 */
	public void printToTextArea(String s){
		resetDisplay();
		print(s);
	}
}
