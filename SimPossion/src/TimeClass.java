import java.util.Calendar;

public class TimeClass {

	public TimeClass() {
		
	}
	
	
	public Calendar getCurrentTime(){
		Calendar currentTime = Calendar.getInstance();
		return currentTime;
	}
	
	public int getHour(Calendar currentTime) {

		int hour = currentTime.get(Calendar.HOUR_OF_DAY);
		return hour;
	}

	public int getDay(Calendar currentTime) {

		int day = currentTime.get(Calendar.DAY_OF_WEEK);
		return day;
	}
	
	

}
