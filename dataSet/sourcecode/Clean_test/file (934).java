package edu.brown.cs32.cjm5.stars;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class CSVFileReader {
	
	private BufferedReader reader;
	private String fileName;
	private Collection<Star> tree; // this collection must support addAll()
	private List<Star> list;
	private Map<String,Star> nameMap;

	/**
	 * @return the reader
	 */
	public BufferedReader getReader() {
		return reader;
	}

	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @return the tree
	 */
	public Collection<Star> getTree() {
		return tree;
	}

	/**
	 * @return the list
	 */
	public List<Star> getList() {
		return list;
	}

	/**
	 * @return the nameMap
	 */
	public Map<String, Star> getNameMap() {
		return nameMap;
	}

	/**
	 * @param fileName filepath to csv file
	 * @param tree some collection holding stars
	 * @param nameMap map of names to stars
	 */
	public CSVFileReader(String fileName,Collection<Star> tree,Map<String,Star> nameMap) {
		this.fileName = fileName;
		this.tree = tree;
		this.list = new ArrayList<Star>();
		this.nameMap = nameMap;
	}
	
	/**
	 * reads CSV star file into kdtree
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public void readCSV() throws IOException,FileNotFoundException  {
		reader = new BufferedReader(new FileReader(fileName));
		String newLine = "";
		Star star;
		reader.readLine(); // skip over description line
		while ((newLine = reader.readLine()) != null) {
			try {
				star = new Star(newLine.split(","));
				if (!star.getName().equals("")) { // add named stars to list
					nameMap.put(star.getName(), star);
				}
				list.add(star);
				//System.out.println(star); //debug
			} catch(NumberFormatException|IndexOutOfBoundsException e) {
				System.out.println("ERROR: Invalid format: skipping line");
			}
		}
		if (tree != null) {
			tree.addAll(list);
		}
		reader.close();
	}

}
