package lib;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * <p> This class allows for simple console input.
 * <p> It is intended to be used as part of the AP Computer Science A class.
 * <p> In all cases, the console will wait for input until the user enters a carriage-return (hits enter or return on the keyboard)
 * @author Braskin, Aaron
 * @version 2.0
 */
public class ConsoleInput {
	private InputStreamReader rdr; 
	private BufferedReader console; 
	private boolean ready = false;
	private boolean suppressErrorReporting;
	
	/**
	 * @return The text typed into the console.
	 */
	public static String inString() {
		ConsoleInput input = new ConsoleInput(true);
		return input.getString();
	}
	/**
	 * Outputs {@code prompt} to the console before waiting for console input.
	 * @return The text typed into the console.
	 */
	public static String inString(String prompt) {
		ConsoleInput input = new ConsoleInput(true);
		return input.getString(prompt);		
	}
	/**
	 * @return The integer value entered in the console or 0 if no integer value can be found.
	 */
	public static int inInt() {
		ConsoleInput input = new ConsoleInput(true);
		return input.getInt();
	}
	/**
	 * Outputs {@code prompt} to the console before waiting for console input.
	 * @return The integer value entered in the console or 0 if no integer value can be found.
	 */
	public static int inInt(String prompt) {
		ConsoleInput input = new ConsoleInput(true);
		return input.getInt(prompt);		
	}
	/**
	 * @return The integer value entered in the console or {@code defaultValue} if no integer value can be found.
	 */
	public static int inInt(int defaultValue) {
		ConsoleInput input = new ConsoleInput(true);
		return input.getInt(defaultValue);					
	}
	/**
	 * Outputs {@code prompt} to the console before waiting for console input.
	 * @return The integer value entered in the console or {@code defaultValue} if no integer value can be found.
	 */
	public static int inInt(String prompt, int defaultValue) {
		ConsoleInput input = new ConsoleInput(true);
		return input.getInt(prompt, defaultValue);			
	}
	/**
	 * @return The double value entered in the console or 0.0 if no double value can be found.
	 */
	public static double inDouble() {
		ConsoleInput input = new ConsoleInput(true);
		return input.getDouble();
	}
	/**
	 * Outputs {@code prompt} to the console before waiting for console input.
	 * @return The double value entered in the console or 0.0 if no double value can be found.
	 */
	public static double inDouble(String prompt) {
		ConsoleInput input = new ConsoleInput(true);
		return input.getDouble(prompt);		
	}
	/**
	 * @return The double value entered in the console or {@code defaultValue} if no integer value can be found.
	 */
	public static double inDouble(double defaultValue) {
		ConsoleInput input = new ConsoleInput(true);
		return input.getDouble(defaultValue);					
	}
	/**
	 * Outputs {@code prompt} to the console before waiting for console input.
	 * @return The double value entered in the console or {@code defaultValue} if no integer value can be found.
	 */
	public static double inDouble(String prompt, double defaultValue) {
		ConsoleInput input = new ConsoleInput(true);
		return input.getDouble(prompt, defaultValue);			
	}
	/**
	 * <p>Default Constructor
	 * <p>Error reporting is <b>not</b> <em>not</em> suppressed.
	 */
	public ConsoleInput() {
		suppressErrorReporting = false;
		try {
			rdr = new InputStreamReader(System.in); 
			console = new BufferedReader(rdr);
			ready = true;
		} catch (IllegalArgumentException e) {
			System.out.println("Stop arguing with ME!");
		} catch (Exception e) {
			e.printStackTrace();			
		} catch (Error e) {
			e.printStackTrace();
		}
	}
	/**
	 * Constructor
	 * @param suppressErrorReports Will throw a {@code NumberFormatException} if {@code suppressErrorReports} is {@code false}
	 */
	public ConsoleInput(boolean suppressErrorReports) {
		suppressErrorReporting = suppressErrorReports;
		try {
			rdr = new InputStreamReader(System.in); 
			console = new BufferedReader(rdr);
			ready = true;
		} catch (IllegalArgumentException e) {
			System.out.println("Stop arguing with ME!");
		} catch (Exception e) {
			e.printStackTrace();			
		} catch (Error e) {
			e.printStackTrace();
		}
	}
	/**
	 * Turn error reporting on or off
	 * @param suppressErrorReports Will throw a {@code NumberFormatException} if {@code suppressErrorReports} is {@code false}
	 */
	public void setErrorReporting(boolean suppressErrorReports) {
		suppressErrorReporting = suppressErrorReports;
	}
	/**
	 * @return The text typed into the console.
	 */
	public String getString() {
		String result = "";
		if (ready) {
			try {
				result = console.readLine();
			} catch (IOException e) {
				if (!suppressErrorReporting) {
					e.printStackTrace();
				}
			} catch(IllegalArgumentException e) {
				if (!suppressErrorReporting) {
					e.printStackTrace();
				}				
			}
		}
		return result;	
	}
	/**
	 * Outputs {@code prompt} to the console before waiting for console input.
	 * @return The text typed into the console.
	 */
	public String getString(String prompt) {
		System.out.print(prompt);
		return getString();
	}
	/**
	 * @return The double value entered in the console or {@code defaultValue} if no integer value can be found.
	 */
	public double getDouble(double defaultValue) {
		String inString = getString();
		double result;
		try {
			result = Double.parseDouble(inString);
		} catch(NumberFormatException e) {
			if (!suppressErrorReporting) {
				System.out.println("NumberFormatException: Cannot convert " + inString + " to a Double using parseDouble");
			}
			result = defaultValue;
		}
		return result;		
	}
	/**
	 * Outputs {@code prompt} to the console before waiting for console input.
	 * @return The double value entered in the console or {@code defaultValue} if no double value can be found.
	 */
	public double getDouble(String prompt, double defaultValue) {
		System.out.print(prompt);
		return getDouble(defaultValue);		
	}
	/**
	 * Outputs {@code prompt} to the console before waiting for console input.
	 * @return The double value entered in the console or 0.0 if no double value can be found.
	 */
	public double getDouble(String prompt) {
		return getDouble(prompt, 0);
	}
	/**
	 * @return The double value entered in the console or 0.0 if no double value can be found.
	 */
	public double getDouble() {
		return getDouble(0);
	}
	/**
	 * @return The integer value entered in the console or {@code defaultValue} if no integer value can be found.
	 */
	public int getInt(int defaultValue) {
		int result;
		String inString = getString();
		try {
			result = Integer.parseInt(inString);
		} catch(NumberFormatException e) {
			if (!suppressErrorReporting) {
				System.out.println("NumberFormatException: Cannot convert " + inString + " to an Integer using parseInt");
			}
			result = defaultValue;
		}
		return result;
	}
	/**
	 * Outputs {@code prompt} to the console before waiting for console input.
	 * @return The integer value entered in the console or {@code defaultValue} if no integer value can be found.
	 */
	public int getInt(String prompt, int defaultValue) {
		System.out.print(prompt);
		return getInt(defaultValue);
	}
	/**
	 * Outputs {@code prompt} to the console before waiting for console input.
	 * @return The integer value entered in the console or 0 if no integer value can be found.
	 */
	public int getInt(String prompt) {
		return getInt(prompt, 0);
	}
	/**
	 * @return The integer value entered in the console or 0 if no integer value can be found.
	 */
	public int getInt() {
		return getInt(0);
	}
}
