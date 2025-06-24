package interpolation;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;


/**
 * represents a a collection of 2d point that make up a function
 * interpolation classes interpolated these points in various ways
 * 
 * @author Scott Valentine
 * 
 */
public class DataFunction {

    /** character used in read in files to comment out lines */
    private static final char COMMENT_CHAR = '#';

    private Map<Double, List<Double>> myVals;

    public DataFunction () {
        myVals = new TreeMap<Double, List<Double>>();
    }
    
    /**
     * constructor that creates DataFunction from mapping of values
     * @param values
     */
    public DataFunction (Map<Double, List<Double>> values) {
        myVals = values;
    }

    /**
     * loads this Data function from file
     * 
     * @param file - current file to read from
     */
    public void load (File dataFile) {
        try {
            Scanner readIn = new Scanner(dataFile);

            // TODO: don't assume that file is perfect
            // TODO: add ability to add comments in read in file
            while (readIn.hasNext()) {
                Scanner line = new Scanner(readIn.nextLine());

                double x = line.nextDouble();

                List<Double> derivs = new ArrayList<Double>();

                while (line.hasNextDouble()) {
                    double a = line.nextDouble();
                    derivs.add(a);
                }
                myVals.put(x, derivs);

            }

        }
        catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * returns know derivatives for the given point
     * 
     * @param x where the derivatives were recorded
     * @return a list of each order derivative that exists
     */
    public List<Double> getDerivatives (double x) {
        return myVals.get(x);
    }
    /**
     *TODO
     * @return
     */
    public Map<Double, List<Double>> getValues(){
        return myVals;
    }

    /**
     * gives all of the x points used for data
     * 
     * @return a set of xvals that represent the points
     */
    public List<Double> getXVals () {
        List<Double> list = new ArrayList<Double>();
        for(double d: myVals.keySet()){
            list.add(myVals.get(d).get(0));
        }
        Collections.sort(list);
        return list;
    }
}
