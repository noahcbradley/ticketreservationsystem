package main.view;
import javax.swing.*;
import java.awt.*;

/*
 * First frame in the application. Welcomes user then gets them to sign in
 * @author Alexa Calkhoven
 * @author Madisson Carle
 * @author Trevor Brown
 * @author Noah Bradley
 */
@SuppressWarnings("serial")
public class MainFrame extends JFrame{

    private JPanel mainPanel, buttonPanel;
    private JButton loginAsGuest, loginAsRegisteredUser, loginAsOrdinaryUser;
    private JLabel initialMessage;
    
    /*
     * Constructor!
     * @param s name of frame
     */
    public MainFrame (String s){
        super(s);

        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        addComponents();

        setSize(300, 300);
        setContentPane(mainPanel);
        getContentPane().setBackground(new java.awt.Color(0, 150, 200));
        buttonPanel.setBackground(new java.awt.Color(0, 150, 200));
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setDefaultLookAndFeelDecorated(true);

    }
    
    /*
     * Adds buttons to panels
     */
    private void addComponents(){
        initialMessage = new JLabel(" Welcome to Ticket Registration System!");
        initialMessage.setFont(new Font("Serif", Font.PLAIN, 17));
        initialMessage.setBounds(300, 300, 300, 300);
        mainPanel.add(initialMessage);

        loginAsGuest = new JButton("Login as Guest");
        loginAsGuest.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginAsGuest.setBackground(new java.awt.Color(255, 215, 0));
        loginAsGuest.setOpaque(true);
        //loginAsGuest.setBorderPainted(false);
        buttonPanel.add(loginAsGuest);

        loginAsRegisteredUser = new JButton("Login as Registered User");
        loginAsRegisteredUser.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginAsRegisteredUser.setBackground(new java.awt.Color(255, 215, 0));
        loginAsRegisteredUser.setOpaque(true);
        buttonPanel.add(loginAsRegisteredUser);

        loginAsOrdinaryUser = new JButton("Login as Ordinary User");
        loginAsOrdinaryUser.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginAsOrdinaryUser.setBackground(new java.awt.Color(255, 215, 0));
        loginAsOrdinaryUser.setOpaque(true);
        buttonPanel.add(loginAsOrdinaryUser);
    }
    
    /*
     * Adds action listeners to all buttons on frame
     * @param loginGuest login guest button listener
     * @param loginRU login registered user button listener
     * @param loginOU login ordinary user button listener
     */
    public void addActionListeners(GUIController.LoginGuestListener loginGuest, GUIController.LoginRUListener loginRU, GUIController.LoginOUListener loginOU){
        loginAsGuest.addActionListener(loginGuest);
        loginAsRegisteredUser.addActionListener(loginRU);
        loginAsOrdinaryUser.addActionListener(loginOU);
    }
}
