package main.model;

public class Time {

	private String day;
	private String month;
	private String year;
	private String hour;
	private String minute;
	private String second;
	
	public Time(String day, String month, String year, String hour, String minute) {
		this.day = day;
		this.month = month;
		this.year = year;
		this.hour = hour;
		this.minute = minute;
	}

	public Time() {
		
	}
	
	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMinute() {
		return minute;
	}

	public void setMinute(String minute) {
		this.minute = minute;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public void setHour(String hour) {
		this.hour = hour;
	}
	
	
	public String getTime() {
		return year+ "-"+ month +"-"+day+" "+hour+": "+minute+": "+second;
	}
	
	@Override
	public String toString() {
		return year+ "-"+ month +"-"+day+" "+hour+": "+minute+": "+second;
	}
	
}
