package com.utd.SentenceSimilarity;

import java.net.*;
import java.io.*;

public class ComputeSentenceSimilarity {
    public static void main(String[] args) throws Exception {
    
    	String str1 = "a small violin is being played by a girl";
    	String str2 = "a child is performing on a tiny instrument";
    	
//    	ParseInput.parseGivenInputFile("corpus1.txt");
    	ParseInput.parseGivenInputFile("corpus1.txt");
    	
    	ComputeSimilarity(str1, str2);
    } 
  
    /**
     * Function which calls the HTTP request and gets the value
     * @param str1
     * @param str2
     * @throws Exception
     */
	private static void ComputeSimilarity(String str1, String str2) throws Exception {

		str1 = ReplaceSpacesByHexDecimalValue(str1);
		str2 = ReplaceSpacesByHexDecimalValue(str2);

//		System.out.println(str1);
//		System.out.println(str2);
		String strURL = "http://swoogle.umbc.edu/StsService/GetStsSim?operation=api&phrase1=" + str1 + "&phrase2=" + str2;
				
    	URL url = new URL(strURL);
        URLConnection yc = url.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
        String inputLine;

        while ((inputLine = in.readLine()) != null) 
            System.out.println(inputLine);
        in.close();

	}

	/**
	 * Function that trims the input string and replaces the multiple spaces with '%20' hex decimal value
	 * @param str
	 * @return
	 */
	private static String ReplaceSpacesByHexDecimalValue(String str) {
		str = str.trim().replaceAll("\\s+", "%20");
		return str;
	}
    
    
}