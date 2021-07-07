package main.view;
import resources.SampleData;

/*
 * This is where the program begins. A connection to the db is initialized, then components for the GUI
 * @author Alexa Calkhoven
 * @author Madisson Carle
 * @author Trevor Brown
 * @author Noah Bradley
 */
class TicketApp {

    public static void main(String[] args){
    	SampleData.addSampleData();
        MainFrame frame = new MainFrame("Ticket Reservation System");
        @SuppressWarnings("unused")
		GUIController guiController = new GUIController(frame);
    }

}