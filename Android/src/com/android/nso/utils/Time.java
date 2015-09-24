package com.android.nso.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.util.Log;

public class Time {
	public static String getCurrentDate(){
		String currentDate = "";
		try {
			SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
			String currentDateandTime = sdf.format(new Date());
			Date cdate=sdf.parse(currentDateandTime);
			Calendar now2= Calendar.getInstance();
			now2.add(Calendar.DATE, 0);
			
			int d = now2.get(Calendar.DATE);
			int m = now2.get(Calendar.MONTH) + 1;
			String month = "", date = "";
			if(m < 10){
				month = "0" + m;
			} else {
				month = "" + m;
			}
			if(d < 10){
				date = "0" + d;
			} else {
				date = "" + d;
			}
			
			int year = now2.get(Calendar.YEAR);
//			String beforedate = year +"/" + month + "/" + date;
			String beforedate = date +"/" + month + "/" + year;
			Date BeforeDate1=sdf.parse(beforedate);
			cdate.compareTo(BeforeDate1);
			
			currentDate = beforedate.replace("/", "-");
			
			Log.d("MyDebug", "secondary List : " + currentDate);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return currentDate;
	}
}
