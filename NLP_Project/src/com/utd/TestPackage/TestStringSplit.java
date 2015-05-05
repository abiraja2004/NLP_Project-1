package com.utd.TestPackage;

public class TestStringSplit {

	public static void main(String[] args) {
		String str1 = "Hi Maanick. I am studying in UTD. I am graduating this May 2015. Bye!";
		String[] splitStr1 = str1.split("\\.");
		System.out.println(splitStr1[0]);
	}
}
