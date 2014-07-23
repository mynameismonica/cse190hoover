package com.example.memgame;

public class buttons {
	
	// It holds numbers that user has clicked
	private static StringBuilder tmp = new StringBuilder();
	
	// A method that appends to the StringBuilder
	public static String strRet(String input)
	{
		tmp.append(input);
		return tmp.toString();
	}
	
	// A method that checks whether the two inputs are equal or not
	public static boolean numCheck(String x, String y)
	{
		return true;
	}

}
