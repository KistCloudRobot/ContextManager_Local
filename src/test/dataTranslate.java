package test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class dataTranslate {

	public static void main(String[] args) {
		String date = "1";
		String date2 = "2017-11-14T16:00:00";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		long startTimeEpoch = 0;
	      Date startTime = null;
	      try {
	         startTime = sdf.parse(date2);
	         startTimeEpoch = startTime.getTime() / 1000;
	      } catch (ParseException e) {
	         // TODO Auto-generated catch block
	    	  e.printStackTrace();
	      }
	   System.out.println(startTimeEpoch);

	}

}
