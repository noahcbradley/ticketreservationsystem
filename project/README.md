# ticket-reservation-system

ENSF 480 Final Proj


Compile:
javac *.java java TicketApp DataCommunicator GuestFrame MainFrame GuiController HomePageFrame UserLoginFrame


Connecting to the Database:
- Start MySQL in your system Services (search Services from Windows Start button, scroll to MySQL, right-click, start).
- In MySQL Shell type the following commands:
1. \c root@localhost:3306 (may have to adjust based on your server settings which you can view on MySQL Workbench)
2. \sql
3. create database ensf480;
- Change the password macro in DBController to your MySQL password.
- Then, construct a DBController object; this will create the tables in the ensf480 database based on the schema in resources/init.sql.


Using the Program:
1. Login as a guest from the starting frame. Whatever username you use here, you can use to create a Registered User (RU) account with, so REMEMBER THIS USERNAME! 
- Note: this guest username can also then be used to login as an Ordinary User (OU) from this point on!
2. Either test functionalities from here, or click the "Register Now!" button to register as an RU.
3. If you registered as an RU, logout and restart the GUI. Login as an RU from the starting frame using your original username. Test functionalities from here.
