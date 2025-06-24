package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class DataHandler {

	public static final String CONFIG_HEADER = "adBase " + Window.VERSION_NUMBER + " --CONFIGURATION FILE--";

	public static ArrayList<Business> businessList = new ArrayList<Business>();
	public static int currentBusinessIndex;
	public static int currentMonth;
	public static int year;
	public static String currentDataFileName;
	private static FileWriter configWriter;
	private static BufferedReader configReader;
	private static FileWriter dataWriter;
	private static BufferedReader dataReader;

	/**
	 * Adds a new business to the ArrayList of Businesses.
	 * @param text must have a length of 10.
	 */
	public static void addBusiness(String[] text) {
		businessList.add(new Business(text));
	}

	/**
	 * Adds a new business to the ArrayList of Businesses.
	 * @param text must have a length of 7.
	 * @param ads
	 */
	public static void addBusiness(String[] text, ArrayList<Advertisement> ads) {
		businessList.add(new Business(text, ads));
	}

	/**
	 * Returns the currently selected business.
	 */
	public static Business getCurrentBusiness() {
		if (businessList.isEmpty())
			return null;
		return businessList.get(currentBusinessIndex);
	}

	/**
	 * Changes the currently selected business to the one before it.
	 */
	public static void previousBusiness() {
		if (currentBusinessIndex > 0) {
			currentBusinessIndex--;
		}
	}

	/**
	 * Changes the currently selected business to the one after it.
	 */
	public static void nextBusiness() {
		if (currentBusinessIndex < businessList.size() - 1) {
			currentBusinessIndex++;
		}
	}

	/**
	 * Loads data for adBase from a data file. Also loads the config file.
	 * @throws IOException
	 */
	public static void load() throws IOException {
		String[] arguments = new String[] {};

		try {
			configReader = new BufferedReader(new FileReader("adBase\\config.txt"));

			String line;
			while ((line = configReader.readLine()) != null) {
				if (!line.equals(CONFIG_HEADER)) {
					currentDataFileName = line;
				}
			}

			if (currentDataFileName != null) {
				dataReader = new BufferedReader(new FileReader(currentDataFileName));

				while ((line = dataReader.readLine()) != null) {
					if (line.startsWith("<HEAD>")) {
						try {
							year = Integer.parseInt(line.substring(7, 11));
						} catch (Exception e) {
							e.printStackTrace();
						}
					} else {
						arguments = line.split(",");
						businessList.add(new Business(arguments));
					}
				}
			}

		} finally {
			if (configReader != null) {
				configReader.close();
			}
			if (dataReader != null) {
				dataReader.close();
			}
		}
	}

	/**
	 * Saves data for adBase to a data file. Also saves the config file.
	 * @throws IOException
	 */
	public static void save() throws IOException {
		try {
			File directory = new File("adBase");
			if (!directory.exists()) {
				directory.mkdirs();
			}

			configWriter = new FileWriter("adBase\\config.txt");

			configWriter.write(CONFIG_HEADER + "\r\n");

			if (currentDataFileName != null) {
				configWriter.write(currentDataFileName + "\r\n");
				
				dataWriter = new FileWriter(currentDataFileName);

				dataWriter.write("<HEAD> " + year + "\r\n");

				for (Business b : businessList) {
					dataWriter.write(b.toCSV());
				}
			}
				
		} finally {
			if (configWriter != null) {
				configWriter.close();
			}
			if (dataWriter != null) {
				dataWriter.close();
			}
		}
	}

	/**
	 * Saves the current file, then loads inputFile.
	 * @param inputFile
	 */
	public static void openNewFile(File inputFile) {
		try {
			save();
			currentDataFileName = inputFile.getName();
			load();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

}
