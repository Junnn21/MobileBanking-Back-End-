package com.app.function;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class Function {

	public static long generateCifCode() {
		Random random = new Random();
		return 10000000000L + ((long)random.nextInt(900000000)*100) + random.nextInt(100);
	}
	
	public static long generateAccountNumber() {
		return (long) Math.floor(Math.random() * 9_000_000_000L) + 1_000_000_000L;
	}
	
	public static String generateTransactionReferenceNumber(String code) {
		Random random = new Random();
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMYYYYSSSS");
		String reference = code  + sdf.format(date) + random.nextInt(10000);
		return reference;
	}
	
	public static String generateOtpToken() {
		Random random = new Random();
		int number = 100000 + random.nextInt(900000);
		String token = Integer.toString(number);
		return token;
	}
	
	public static Date generateExpiredDate() {
		Calendar expired = Calendar.getInstance();
		expired.add(Calendar.MINUTE, 5);
		Date expiredDate = expired.getTime();
		return expiredDate;
	}
	
	public static boolean compareDates(Date date1,Date date2){
        if(date1.after(date2)){
            return false;
        }else if(date1.before(date2)){
           	return true;
        }else if(date1.equals(date2)){
            return true;
        }
        return true;
    }
}
