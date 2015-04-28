package com.utd.SentenceSimilarity;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class ParseInput {

	public static void parseGivenInputFile(String fileName) throws IOException {
		
		String appendStr = "";
		Scanner fileScanner = new Scanner(new File("Corpus/"+fileName));
		BufferedWriter bw = new BufferedWriter(new FileWriter(new File("Corpus/allCorpus.txt")));
		while(fileScanner.hasNextLine()) {
			
			String strLine = fileScanner.nextLine().trim();
			String strLineArray[] = strLine.split("\\.");
			for(int i=0;i<strLineArray.length;i++)
				strLineArray[i] = strLineArray[i].trim();
			
			if(strLineArray.length == 1 && strLine.charAt(strLine.length()-1)!='.') {		// if no '.' in the line
				if(appendStr.equals(""))
					appendStr = strLine;
				else
					appendStr = appendStr.concat(" " + strLine);
				continue;
			}
				
			if(!appendStr.equals(""))												// append to the old value
				bw.write(appendStr.concat(" " + strLineArray[0]) + ".\n");
			else
				bw.write(strLineArray[0] + ".\n");
			
			for(int i=1;i<strLineArray.length-1;i++) {								// write all sentences except first and last sentence 
//				System.out.println(strLineArray[i]);
				bw.write(strLineArray[i] + ".\n");
			}
				
			
			if(strLine.charAt(strLine.length()-1) != '.')							// if last char is not '.', then create appendString
				appendStr = strLineArray[strLineArray.length-1];
			else if(strLineArray.length > 1)  										// if last char is '.', then check if there are more than 2 sub strings to prevent duplicate printing 
					bw.write(strLineArray[strLineArray.length-1] + ".\n");
			else
					appendStr = "";
			
		}
		bw.close();
		fileScanner.close();
	}
	
}
