/***********************************************
 Name:        DataFileReader
 Purpose:     Read in all lines of text from a
              text data file.
 Assumptions: Data file is assumed to be in the
              current working directory.
 ***********************************************/

import java.io.*;
import java.util.*;

public class DataFileReader {

    private String filename;

    public DataFileReader(String pFilename) {
        this.filename = pFilename;
    }

    /******************************************************************
     Name:        getData
     Purpose:     Read in all lines of text from the file specified by
                  member filename and return them in a collection.
    *******************************************************************/
    public List<String> getData() {
        List<String> lines = new ArrayList<String>();

        try {
            // Open the file that is the first
            // command line parameter
            FileInputStream fstream = new FileInputStream(this.filename);

            // Get the object of DataInputStream
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));

            String strLine;
            //Read File Line By Line
            while ((strLine = br.readLine()) != null)   {
                // Store the line in the collection
                lines.add(strLine);
            }

            //Close the input stream
           in.close();
        } catch (Exception e) {
            //Catch exception if any
            System.err.println("Error: " + e.getMessage());
        }
        return lines;
    }

    public static void main(String args[]) {
		String dataFileName = args[0];
		System.out.println("Processing data file " + dataFileName + "...");
		DataFileReader fileReader = new DataFileReader(dataFileName);
		List<String> lines = fileReader.getData();
	    for (String line: lines) {
		    System.out.println( line );
        }
    }
}