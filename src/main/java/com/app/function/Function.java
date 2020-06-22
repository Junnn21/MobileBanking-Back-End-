package com.app.function;

import java.util.Random;

public class Function {

	public static long generateCifCode() {
		Random random = new Random();
		return 10000000000L + ((long)random.nextInt(900000000)*100) + random.nextInt(100);
	}
	
}
