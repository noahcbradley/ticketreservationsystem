CREATE TABLE IF NOT EXISTS User (
    username VARCHAR(25) PRIMARY KEY,
    userType INT NOT NULL CHECK (userType BETWEEN 0 AND 1)
);

CREATE TABLE IF NOT EXISTS RegUser (
    username VARCHAR(25) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    address VARCHAR(255) NOT NULL,
    cardNum INT NOT NULL CHECK (cardNum BETWEEN 1000000 AND 99999999),
    feePaid TINYINT NOT NULL,
	FOREIGN KEY (username) REFERENCES User (username) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Email (
	emailId INT PRIMARY KEY AUTO_INCREMENT,
    emailType INT NOT NULL CHECK (emailType BETWEEN 0 AND 1),
    message VARCHAR(400) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS UserToEmail (
	username VARCHAR(25),
    emailId INT,
    CONSTRAINT UserToEmailPK PRIMARY KEY (username, emailId),
    FOREIGN KEY (username) REFERENCES User (username) ON DELETE CASCADE,
    FOREIGN KEY (emailId) REFERENCES Email (emailId) 
);

CREATE TABLE IF NOT EXISTS Seat (
	seatId INT PRIMARY KEY AUTO_INCREMENT,
    rowNumber INT NOT NULL CHECK (rowNumber BETWEEN 0 AND 3),
    colNumber INT NOT NULL CHECK (colNumber BETWEEN 0 AND 4),
    screen VARCHAR(1) NOT NULL,
    isTaken BOOLEAN NOT NULL,
    seatType INT NOT NULL CHECK (seatType BETWEEN 0 AND 1)
);

CREATE TABLE IF NOT EXISTS Payment (
	paymentId INT PRIMARY KEY AUTO_INCREMENT,
    amount DOUBLE NOT NULL CHECK (amount BETWEEN 0 AND 100),
    cardNum INT NOT NULL CHECK (cardNum BETWEEN 1000000 AND 99999999)    
);

CREATE TABLE IF NOT EXISTS Movie (
	movieId INT PRIMARY KEY AUTO_INCREMENT,
	name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS Showtime (
	showtimeId INT PRIMARY KEY AUTO_INCREMENT,
    time TIMESTAMP NOT NULL,
    movieId INT NOT NULL,
    FOREIGN KEY (movieId) REFERENCES Movie (movieId),
    CONSTRAINT dateMovie UNIQUE (time, movieId)
);

CREATE TABLE IF NOT EXISTS ShowtimeToSeat (
	showtimeId INT,
    seatId INT,
    CONSTRAINT ShowtimeToSeatPK PRIMARY KEY (showtimeId, seatId),
    FOREIGN KEY (showtimeId) REFERENCES Showtime (showtimeId) ON DELETE CASCADE,
    FOREIGN KEY (seatId) REFERENCES Seat (seatId) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Ticket (
    ticketId INT PRIMARY KEY AUTO_INCREMENT,
    seatId INT NOT NULL,
    showtimeId INT NOT NULL,
    paymentId INT NOT NULL,
    timePurchased TIMESTAMP NOT NULL,
    FOREIGN KEY (seatId) REFERENCES Seat (seatId) ON DELETE CASCADE,
    FOREIGN KEY (showtimeId) REFERENCES Showtime (showtimeId) ON DELETE CASCADE,
    FOREIGN KEY (paymentId) REFERENCES Payment (paymentId) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS UserToTicket (
	username VARCHAR(25),
    ticketId INT,
    CONSTRAINT UserToTicketPK PRIMARY KEY (username, ticketId),
    FOREIGN KEY (username) REFERENCES User (username) ON DELETE CASCADE,
    FOREIGN KEY (ticketId) REFERENCES Ticket (ticketId) ON DELETE CASCADE 
);