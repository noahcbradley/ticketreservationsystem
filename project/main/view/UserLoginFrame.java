package main.view;
import javax.swing.*;
import java.awt.*;

/*
 * After the user submits username, this is the brief frame to make the user login
 * @author Alexa Calkhoven
 * @author Madisson Carle
 * @author Trevor Brown
 * @author Noah Bradley
 */
@SuppressWarnings("serial")
public class UserLoginFrame extends JFrame{
    
    private JPanel mainPanel, buttonPanel;
    private JButton login;
    private JLabel message;
    //private JTextField usernameInput;
    
    /*
     * Constructor
     * @param s frame name
     */
    public UserLoginFrame (String s){
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
     * Adds label in centre of panel and login button
     */
    private void addComponents(){

        message = new JLabel("   Hello Theatre Member! Please click login.");
        message.setFont(new Font("Serif", Font.PLAIN, 16));
        message.setBounds(300, 300, 300, 300);
        mainPanel.add(message);
        
        login = new JButton("Login");
        login.setAlignmentX(Component.CENTER_ALIGNMENT);
        login.setBackground(new java.awt.Color(255, 215, 0));
        login.setOpaque(true);
        buttonPanel.add(login);

    }
    
    /*
     * Defines login button
     * @param loginButton login button action listener
     */
    public void addActionListeners(GUIController.LoginListener loginButton){
        login.addActionListener(loginButton);
    }
   

}
