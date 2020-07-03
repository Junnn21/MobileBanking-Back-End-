package com.app.function;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class Function {

	public static long generateCifCode() {
		Random random = new Random();
		return 10000000000L + ((long)random.nextInt(900000000)*100) + random.nextInt(100);
	}
	
	public static String generateTransactionReferenceNumber() {
		Random random = new Random();
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMYYYYSSSS");
		String reference = "MB"  + sdf.format(date) + random.nextInt(10000);
		return reference;
	}
}
