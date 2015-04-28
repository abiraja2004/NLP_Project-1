package com.utd.SentenceSimilarity;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class ParseInput {
	
	/**
	 * Method that runs 'parsing' function on all the input files.
	 * All input files are listed in the folder 'Corpus'
	 * Input file format: 'corpus1.txt', 'corpus2.txt', 'corpus3.txt'
	 * @throws IOException
	 */
	public static void parseAllInputFiles() throws IOException {
		
		File folder = new File("Corpus");
		File allFiles[] = folder.listFiles();

		BufferedWriter bw = new BufferedWriter(new FileWriter(new File("Corpus/allCorpus.txt")));
		bw.write("");
		bw.close();
		
		for(File file : allFiles) {
			if(file.getName().contains("corpus")) 
				parseGivenInputFile(file);
		}
		
	}

	/**
	 * Given a file name, extracts each sentence and append it to the 'allCorpus.txt'
	 * @param file
	 * @throws IOException
	 */
	public static void parseGivenInputFile(File file) throws IOException {

		String appendStr = "";
		Scanner fileScanner = new Scanner(file);
		BufferedWriter bw = new BufferedWriter(new FileWriter(file.getParent()+"/allCorpus.txt", true));
		while(fileScanner.hasNextLine()) {
			
			String strLine = fileScanner.nextLine().trim();
			if(strLine.length() == 0)								// if just a blank space, then skip it.
				continue;
			strLine = customizeInputLine(strLine);
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

	/**
	 * Method that calls the 2 methods given below
	 * @param strLine
	 * @return
	 */
	private static String customizeInputLine(String strLine) {
		strLine = ReplaceMultipleDotsBySingle(strLine);
		strLine = ReplaceDotsInProperNoun(strLine);
		strLine = ReplaceQuotes(strLine);
		strLine = ReplaceComma(strLine);
		strLine = ReplaceMultipleSpacesIntoSingleOne(strLine);
		return strLine.trim();
	}

	/**
	 * Method to replace comma(,) with ''
	 * @param strLine
	 * @return
	 */
	private static String ReplaceComma(String strLine) {
		return strLine.replaceAll("\\,", "");
	}

	/**
	 * Method to replace '  ' into ' '
	 * @param strLine
	 * @return
	 */
	private static String ReplaceMultipleSpacesIntoSingleOne(String strLine) {
		strLine = strLine.replaceAll("\\s+", " ");
		return strLine;
	}

	/**
	 * Method that replaces single & double quotes into space 
	 * @param strLine
	 * @return
	 */
	private static String ReplaceQuotes(String strLine) {
		strLine = strLine.replaceAll("\"", "");
		strLine = strLine.replaceAll("\'", "");
		return strLine;
	}

	/**
	 * Method that replaces 'U.S. Defence' into 'U S Defence'
	 * @param strLine
	 * @return
	 */
	private static String ReplaceDotsInProperNoun(String strLine) {
		for(int i=1;i<strLine.length();i++)
			if(strLine.charAt(i) == '.' && strLine.charAt(i-1) >= 65 && strLine.charAt(i-1) <= 90) {
				StringBuilder sbr = new StringBuilder(strLine);
				strLine = sbr.replace(i, i+1, "").toString();
			}
		return strLine;
	}

	/**
	 * Method that replaces '...' into '.'
	 * @param strLine
	 * @return
	 */
	private static String ReplaceMultipleDotsBySingle(String strLine) {
		return strLine.replaceAll("\\.+", "\\.");
	}
	
}
