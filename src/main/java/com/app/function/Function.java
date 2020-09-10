package com.app.function;

import java.text.SimpleDateFormat;
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
}
