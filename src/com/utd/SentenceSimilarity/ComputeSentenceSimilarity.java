package com.utd.SentenceSimilarity;

import java.net.*;
import java.io.*;

public class ComputeSentenceSimilarity {
    public static void main(String[] args) throws Exception {
    	
    	URL url = new URL("http://swoogle.umbc.edu/StsService/GetStsSim?operation=api&phrase1=a%20small%20violin%20is%20being%20played%20by%20a%20girl&phrase2=a%20child%20is%20performing%20on%20a%20tiny%20instrument");
        URLConnection yc = url.openConnection();
        BufferedReader in = new BufferedReader(
                                new InputStreamReader(
                                yc.getInputStream()));
        String inputLine;

        while ((inputLine = in.readLine()) != null) 
            System.out.println(inputLine);
        in.close();
    }
}