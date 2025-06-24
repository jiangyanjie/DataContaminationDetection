/*
 * Shon Czinner
 * ICS3U
 * Cross Country Running Assignment
 * Taking inputted runner data and outputting summaries table
 * October 31
 * Mr DesLaurier
 */
package com.bayviewglen.crosscountry;

import java.util.Scanner;
import java.text.DecimalFormat;

public class CrossCountry {
	public static void main(String[] args) {
		final int SECONDS_PER_MINUTE = 60;
		
		Scanner in = new Scanner(System.in);
		DecimalFormat secondsFormat = new DecimalFormat("00.000");
		DecimalFormat minutesFormat = new DecimalFormat("0':'");
		
		//runner one
		
		System.out.print("Please enter your first and last name: ");
		String nameOne = in.nextLine(); // Grabs user input
		int separator = nameOne.indexOf(' ');  // Finds separator of first name and last name
		String firstNameOne = nameOne.substring(0,separator); // Takes first name by doing start of string until space 
		String lastNameOne = nameOne.substring(separator+1); // Takes last name by going to the separator and starting the character after
		
		System.out.print(firstNameOne + ", please enter your time after the first mile (mm:ss.sss): ");
		String timeOneOne = in.nextLine(); 
		separator = timeOneOne.indexOf(':');  // Finds separator, ":" between mm and ss.sss
		int minutesOneOne = Integer.parseInt(timeOneOne.substring(0,separator)); // Parses mm part of inputted string, beginning till ":"
		double secondsOneOne = Double.parseDouble(timeOneOne.substring(separator+1)); //parses part after ":" for ss.sss
		
		System.out.print(firstNameOne + ", please enter your time after the second mile (mm:ss.sss): ");
		String timeOneTwo = in.nextLine();
		separator = timeOneTwo.indexOf(':');
		int minutesOneTwo = Integer.parseInt(timeOneTwo.substring(0,separator));
		double secondsOneTwo = Double.parseDouble(timeOneTwo.substring(separator+1));
		
		System.out.print(firstNameOne + ", please enter your time after the entire 5 kilometer race (mm:ss.sss): ");
		String timeOneThree = in.nextLine();
		separator = timeOneThree.indexOf(':');
		int minutesOneThree = Integer.parseInt(timeOneThree.substring(0,separator));
		double secondsOneThree = Double.parseDouble(timeOneThree.substring(separator+1));
				
		double secondsOneSplitTwo = minutesOneTwo*SECONDS_PER_MINUTE-minutesOneOne*SECONDS_PER_MINUTE+secondsOneTwo-secondsOneOne;  
		// Converts minutes to seconds and finds the split through subtraction one from two
		double secondsOneSplitThree = minutesOneThree*SECONDS_PER_MINUTE-minutesOneTwo*SECONDS_PER_MINUTE+secondsOneThree-secondsOneTwo;
		
		System.out.println("Runner One Summary");
		System.out.println("******************");
		
		System.out.printf("%-15s %-15s \n","Runner: ", lastNameOne + ", " + firstNameOne);  //formats each to length of 15, concatenates lastname, comma, and first name\
		
		String splitOneOne = minutesFormat.format(minutesOneOne) + secondsFormat.format(secondsOneOne); 
		// Formats minutes to 0:, formats seconds to 00.000 so 9 seconds becomes 09.000 
		System.out.printf("%-15s %-15s \n", "Split One: ", splitOneOne);
		
		String splitOneTwo = minutesFormat.format((int)secondsOneSplitTwo/SECONDS_PER_MINUTE) + secondsFormat.format(secondsOneSplitTwo%SECONDS_PER_MINUTE);
		System.out.printf("%-15s %-15s \n", "Split Two: ", splitOneTwo);
	
		
		String splitOneThree = minutesFormat.format((int)secondsOneSplitThree/SECONDS_PER_MINUTE) + secondsFormat.format(secondsOneSplitThree%SECONDS_PER_MINUTE);
		System.out.printf("%-15s %-15s \n", "Split Three: ", splitOneThree);

		
		String totalOne = minutesFormat.format(minutesOneThree) + secondsFormat.format(secondsOneThree);
		System.out.printf("%-15s %-15s \n", "Total: ", totalOne);
		
		System.out.println(" ");	// Inserts an empty line
		
		//runner two
		
		System.out.print("Please enter your first and last name: ");
		String nameTwo = in.nextLine();
		separator = nameTwo.indexOf(' ');
		String firstNameTwo = nameTwo.substring(0, separator);
		String lastNameTwo = nameTwo.substring(separator+1);
		
		System.out.print(firstNameTwo + ", please enter your time after the first mile (mm:ss.sss): ");
		String timeTwoOne = in.nextLine();
		separator = timeTwoOne.indexOf(':');
		int minutesTwoOne = Integer.parseInt(timeTwoOne.substring(0, separator));
		double secondsTwoOne = Double.parseDouble(timeTwoOne.substring(separator+1));
		
		System.out.print(firstNameTwo + ", please enter your time after the second mile (mm:ss.sss): ");
		String timeTwoTwo = in.nextLine();
		separator = timeTwoTwo.indexOf(':');
		int minutesTwoTwo = Integer.parseInt(timeTwoTwo.substring(0,separator));
		double secondsTwoTwo = Double.parseDouble(timeTwoTwo.substring(separator+1));
		
		System.out.print(firstNameTwo + ", please enter your time after the entire 5 kilometer race (mm:ss.sss): ");
		String timeTwoThree = in.nextLine();
		separator = timeTwoThree.indexOf(':');
		int minutesTwoThree = Integer.parseInt(timeTwoThree.substring(0,separator));
		double secondsTwoThree = Double.parseDouble(timeTwoThree.substring(separator+1));
				
		double secondsTwoSplitTwo = minutesTwoTwo*SECONDS_PER_MINUTE-minutesTwoOne*SECONDS_PER_MINUTE+secondsTwoTwo-secondsTwoOne;
		double secondsTwoSplitThree = minutesTwoThree*SECONDS_PER_MINUTE-minutesTwoTwo*SECONDS_PER_MINUTE+secondsTwoThree-secondsTwoTwo;
		
		System.out.println("Runner Two Summary");
		System.out.println("******************");
		
		System.out.printf("%-15s %-15s \n","Runner: ", lastNameTwo + ", " + firstNameTwo);
		
		String splitTwoOne = minutesFormat.format(minutesTwoOne) + secondsFormat.format(secondsTwoOne);
		System.out.printf("%-15s %-15s \n","Split One: ", splitTwoOne);
		
		
		String splitTwoTwo = minutesFormat.format((int)secondsTwoSplitTwo/SECONDS_PER_MINUTE) + secondsFormat.format(secondsTwoSplitTwo%SECONDS_PER_MINUTE);
		System.out.printf("%-15s %-15s \n", "Split Two: ", splitTwoTwo);
	
		
		String splitTwoThree = minutesFormat.format((int)secondsTwoSplitThree/SECONDS_PER_MINUTE) + secondsFormat.format(secondsTwoSplitThree%SECONDS_PER_MINUTE);
		System.out.printf("%-15s %-15s \n", "Split Three: ", splitTwoThree);
	
		
		String totalTwo = minutesFormat.format(minutesTwoThree) + secondsFormat.format(secondsTwoThree);
		System.out.printf("%-15s %-15s \n", "Total: ", totalTwo);
		
		System.out.println(" ");
		
		//runner three
		
		System.out.print("Please enter your first and last name: ");
		String nameThree = in.nextLine();
		separator = nameThree.indexOf(' ');
		String firstNameThree = nameThree.substring(0,separator);
		String lastNameThree = nameThree.substring(separator+1);
		
		System.out.print(firstNameThree + ", please enter your time after the first mile (mm:ss.sss): ");
		String timeThreeOne = in.nextLine();
		separator = timeThreeOne.indexOf(':');
		int minutesThreeOne = Integer.parseInt(timeThreeOne.substring(0,separator));
		double secondsThreeOne = Double.parseDouble(timeThreeOne.substring(separator+1));
		
		System.out.print(firstNameThree + ", please enter your time after the second mile (mm:ss.sss): ");
		String timeThreeTwo = in.nextLine();
		separator = timeThreeTwo.indexOf(':');
		int minutesThreeTwo = Integer.parseInt(timeThreeTwo.substring(0,separator));
		double secondsThreeTwo = Double.parseDouble(timeThreeTwo.substring(separator+1));
		
		System.out.print(firstNameThree + ", please enter your time after the entire 5 kilometer race (mm:ss.sss): ");
		String timeThreeThree = in.nextLine();
		separator = timeThreeThree.indexOf(':');
		int minutesThreeThree = Integer.parseInt(timeThreeThree.substring(0,separator));
		double secondsThreeThree = Double.parseDouble(timeThreeThree.substring(separator+1));
				
		double secondsThreeSplitTwo = minutesThreeTwo*SECONDS_PER_MINUTE-minutesThreeOne*SECONDS_PER_MINUTE+secondsThreeTwo-secondsThreeOne;
		double secondsThreeSplitThree = minutesThreeThree*SECONDS_PER_MINUTE-minutesThreeTwo*SECONDS_PER_MINUTE+secondsThreeThree-secondsThreeTwo;
		
		System.out.println("Runner Three Summary");
		System.out.println("********************");
		
		System.out.printf("%-15s %-15s \n", "Runner:", lastNameThree + ", " + firstNameThree);
		
		String splitThreeOne = minutesFormat.format(minutesThreeOne) + secondsFormat.format(secondsThreeOne);
		System.out.printf("%-15s %-15s \n", "Split One: ", splitThreeOne);
		
		String splitThreeTwo = minutesFormat.format((int)secondsThreeSplitTwo/SECONDS_PER_MINUTE) + secondsFormat.format(secondsThreeSplitTwo%SECONDS_PER_MINUTE);
		System.out.printf("%-15s %-15s \n", "Split Two: ", splitThreeTwo);

		String splitThreeThree = minutesFormat.format((int)secondsThreeSplitThree/SECONDS_PER_MINUTE) + secondsFormat.format(secondsThreeSplitThree%SECONDS_PER_MINUTE);
		System.out.printf("%-15s %-15s \n", "Split Three: ", splitThreeThree);
		
		String totalThree = minutesFormat.format(minutesThreeThree) + secondsFormat.format(secondsThreeThree);
		System.out.printf("%-15s %-15s \n", "Total: ", totalThree);
		
		System.out.println(" ");
		
		//runner four
		
		System.out.print("Please enter your first and last name: ");
		String nameFour = in.nextLine();
		separator = nameFour.indexOf(' ');
		String firstNameFour = nameFour.substring(0,separator);
		String lastNameFour = nameFour.substring(separator+1);
		
		System.out.print(firstNameFour + ", please enter your time after the first mile (mm:ss.sss): ");
		String timeFourOne = in.nextLine();
		separator = timeFourOne.indexOf(':');
		int minutesFourOne = Integer.parseInt(timeFourOne.substring(0,separator));
		double secondsFourOne = Double.parseDouble(timeFourOne.substring(separator+1));
		
		System.out.print(firstNameFour + ", please enter your time after the second mile (mm:ss.sss): ");
		String timeFourTwo = in.nextLine();
		separator = timeFourTwo.indexOf(':');
		int minutesFourTwo = Integer.parseInt(timeFourTwo.substring(0,separator));
		double secondsFourTwo = Double.parseDouble(timeFourTwo.substring(separator+1));
		
		System.out.print(firstNameFour + ", please enter your time after the entire 5 kilometer race (mm:ss.sss): ");
		String timeFourThree = in.nextLine();
		separator = timeFourThree.indexOf(':');
		int minutesFourThree = Integer.parseInt(timeFourThree.substring(0,separator));
		double secondsFourThree = Double.parseDouble(timeFourThree.substring(separator+1));
				
		double secondsFourSplitTwo = minutesFourTwo*SECONDS_PER_MINUTE-minutesFourOne*SECONDS_PER_MINUTE+secondsFourTwo-secondsFourOne;
		double secondsFourSplitThree = minutesFourThree*SECONDS_PER_MINUTE-minutesFourTwo*SECONDS_PER_MINUTE+secondsFourThree-secondsFourTwo;
		
		System.out.println("Runner Four Summary");
		System.out.println("*******************");
		
		System.out.printf("%-15s %-15s \n", "Runner:", lastNameFour + ", " + firstNameFour);
		
		String splitFourOne = minutesFormat.format(minutesFourOne) + secondsFormat.format(secondsFourOne);
		System.out.printf("%-15s %-15s \n", "Split One: ", splitFourOne);
		
		String splitFourTwo = minutesFormat.format((int)secondsFourSplitTwo/SECONDS_PER_MINUTE) + secondsFormat.format(secondsFourSplitTwo%SECONDS_PER_MINUTE);
		System.out.printf("%-15s %-15s \n", "Split Two: ", splitFourTwo);
		
		String splitFourThree = minutesFormat.format((int)secondsFourSplitThree/SECONDS_PER_MINUTE) + secondsFormat.format(secondsFourSplitThree%SECONDS_PER_MINUTE);
		System.out.printf("%-15s %-15s \n", "Split Three: ", splitFourThree);
		
		String totalFour = minutesFormat.format(minutesFourThree) + secondsFormat.format(secondsFourThree);
		System.out.printf("%-15s %-15s \n", "Total: ", totalFour);
		
		System.out.println(" ");

		
		//runner five
		
		System.out.print("Please enter your first and last name: ");
		String nameFive = in.nextLine();
		separator = nameFive.indexOf(' ');
		String firstNameFive = nameFive.substring(0,separator+1);
		String lastNameFive = nameFive.substring(separator+1);
		
		System.out.print(firstNameFive + ", please enter your time after the first mile (mm:ss.sss): ");
		String timeFiveOne = in.nextLine();
		separator = timeFiveOne.indexOf(':');
		int minutesFiveOne = Integer.parseInt(timeFiveOne.substring(0,separator));
		double secondsFiveOne = Double.parseDouble(timeFiveOne.substring(separator+1));
		
		System.out.print(firstNameFive + ", please enter your time after the second mile (mm:ss.sss): ");
		String timeFiveTwo = in.nextLine();
		separator = timeFiveTwo.indexOf(':');
		int minutesFiveTwo = Integer.parseInt(timeFiveTwo.substring(0,separator));
		double secondsFiveTwo = Double.parseDouble(timeFiveTwo.substring(separator+1));
		
		System.out.print(firstNameFive + ", please enter your time after the entire 5 kilometer race (mm:ss.sss): ");
		String timeFiveThree = in.nextLine();
		separator = timeFiveThree.indexOf(':');
		int minutesFiveThree = Integer.parseInt(timeFiveThree.substring(0,separator));
		double secondsFiveThree = Double.parseDouble(timeFiveThree.substring(separator+1));
				
		double secondsFiveSplitTwo = minutesFiveTwo*SECONDS_PER_MINUTE-minutesFiveOne*SECONDS_PER_MINUTE+secondsFiveTwo-secondsFiveOne;
		double secondsFiveSplitThree = minutesFiveThree*SECONDS_PER_MINUTE-minutesFiveTwo*SECONDS_PER_MINUTE+secondsFiveThree-secondsFiveTwo;
		
		System.out.println("Runner Five Summary");
		System.out.println("*******************");
		
		System.out.printf("%-15s %-15s \n", "Runner: ", lastNameFive + ", " + firstNameFive);
		
		String splitFiveOne = minutesFormat.format(minutesFiveOne) + secondsFormat.format(secondsFiveOne);
		System.out.printf("%-15s %-15s \n", "Split One: ", splitFiveOne);
		
		String splitFiveTwo = minutesFormat.format((int)secondsFiveSplitTwo/SECONDS_PER_MINUTE) + secondsFormat.format(secondsFiveSplitTwo%SECONDS_PER_MINUTE);
		System.out.printf("%-15s %-15s \n", "Split Two: ", splitFiveTwo);
		
		String splitFiveThree = minutesFormat.format((int)secondsFiveSplitThree/SECONDS_PER_MINUTE) + secondsFormat.format(secondsFiveSplitThree%SECONDS_PER_MINUTE);
		System.out.printf("%-15s %-15s \n", "Split Three: ", splitFiveThree);
		
		String totalFive = minutesFormat.format(minutesFiveThree) + secondsFormat.format(secondsFiveThree);
		System.out.printf("%-15s %-15s \n", "Total: ", totalFive);
		
		System.out.println(" ");
		
		in.close();
		
		String aHeading = "   ";
		String bHeading = "Split One";
		String cHeading = "Split Two";
		String dHeading = "Split Three";
		String eHeading = "Total Time";
		
		System.out.println("Summary Table of All Runners:");
		System.out.printf("%-15s %-15s %-15s %-15s %-15s %-15s \n", aHeading, nameOne, nameTwo, nameThree, nameFour, nameFive); 
		System.out.printf("%-15s %-15s %-15s %-15s %-15s %-15s \n", bHeading, splitOneOne, splitTwoOne, splitThreeOne, splitFourOne, splitFiveOne);
		System.out.printf("%-15s %-15s %-15s %-15s %-15s %-15s \n", cHeading, splitOneTwo, splitTwoTwo, splitThreeTwo, splitFourTwo, splitFiveTwo);
		System.out.printf("%-15s %-15s %-15s %-15s %-15s %-15s \n", dHeading, splitOneThree, splitTwoThree, splitThreeThree, splitFourThree, splitFiveThree);
		System.out.printf("%-15s %-15s %-15s %-15s %-15s %-15s \n", eHeading, totalOne, totalTwo, totalThree, totalFour, totalFive);
		// %-15s is 15 character spacing each 
	}

}
