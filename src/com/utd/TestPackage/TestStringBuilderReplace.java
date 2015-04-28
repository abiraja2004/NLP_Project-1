package com.utd.TestPackage;

public class TestStringBuilderReplace {

	public static void main(String[] args) {

		StringBuilder sbr = new StringBuilder("U.S. Defence");
		sbr = sbr.replace(1, 2, "");
		System.out.println(sbr);
		
	}

}
