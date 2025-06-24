package org.agmip.ace.util;

import java.util.ArrayList;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.agmip.ace.AcePathfinder;


/**
 * A collection of utilities to interact with the AcePathfinder.
 *
 * <pre>AcePathfinderUtil</pre> is a collection of static methods used to interact with
 * the {@link AcePathfinder} singleton.
 *
 * The <i>path</i>, for the purposes of this class, is formated as:
 * <pre>a@b!c,a@b!c,...</pre>
 * <ul>
 * <li>a - nested bucket(s) seperated by <pre>:</pre> [<pre>HashMap</pre>]</li>
 * <li>b - series data container [<pre>ArrayList</pre>]</li>
 * <li>c - event-based series data record [<pre>&lt;String,String&gt; = &lt;"event",c&gt;</pre></li>
 * </ul>
 */
public class AcePathfinderUtil {
    private static final Logger LOG = LoggerFactory.getLogger("org.agmip.util.AcePathfinderUtil");

    public enum PathType {
        UNKNOWN,
        EXPERIMENT,
        WEATHER,
        SOIL
    }

    public static AcePathfinder getInstance() {
        return AcePathfinder.INSTANCE;
    }


    /**
     * Returns the general section of this variable (weather, soil, experiment).
     * 
     * @param var Variable to lookup
     */
    public static PathType getVariableType(String var) {
        if (var == null) {
            return PathType.UNKNOWN;
        }
        //String path = AcePathfinder.INSTANCE.getPath(var);
        AcePathfinder instance = getInstance();
        String path = instance.getPath(var);
        LOG.debug("Current var: "+var+", Current Path: "+path);
        if (path == null) {
        	return PathType.UNKNOWN;
        }
        if (path.contains("weather")) {
            return PathType.WEATHER;
        } else if (path.contains("soil")) {
            if (path.contains("initial_conditions")) {
                return PathType.EXPERIMENT;
            }
            return PathType.SOIL;
        } else {
            return PathType.EXPERIMENT;
        }
    }
    
    /**
     * Returns if the variable is a date.
     *
     * @param var Variable to check
     */
    public static boolean isDate(String var) {
    	if ( var != null ) {
    		return AcePathfinder.INSTANCE.isDate(var);
    	} else {
    		return false;
    	}
    }

    /**
     * Inserts the variable in the appropriate place in a {@link HashMap},
     * according to the AcePathfinder.
     *
     * @param m the HashMap to add the variable to.
     * @param var Variable to lookup
     * @param val the value to insert into the HashMap
     */
    public static void insertValue(HashMap m, String var, String val) {
        insertValue(m, var, val, null);
    }

    /**
     * Inserts the variable in the appropriate place in a {@link HashMap},
     * according to the AcePathfinder.
     *
     * @param m the HashMap to add the variable to.
     * @param var Variable to lookup
     * @param val the value to insert into the HashMap
     * @param eventAppendMode True for add variable into last same type event; False for add to last event without type check
     */
    public static void insertValue(HashMap m, String var, String val, boolean eventAppendMode) {
        insertValue(m, var, val, null, eventAppendMode);
    }

    /**
     * Inserts the variable in the appropriate place in a {@link HashMap},
     * according to the AcePathfinder.
     *
     * @param m the HashMap to add the variable to.
     * @param var the variable to lookup in the AcePathfinder
     * @param val the value to insert into the HashMap
     * @param path use a custom path vs. a lookup path, useful if dealing with custom variables
     */
    public static void insertValue(HashMap m, String var, String val, String path) {
        insertValue(m, var, val, path, false);
    }

    /**
     * Inserts the variable in the appropriate place in a {@link HashMap},
     * according to the AcePathfinder.
     *
     * @param m the HashMap to add the variable to.
     * @param var the variable to lookup in the AcePathfinder
     * @param val the value to insert into the HashMap
     * @param path use a custom path vs. a lookup path, useful if dealing with custom variables
     * @param eventAppendMode True for add variable into last same type event; False for add to last event without type check
     */
    public static void insertValue(HashMap m, String var, String val, String path, boolean eventAppendMode) {
        if (m == null || var == null) { return; }
        if (path == null) {
            var = AcePathfinder.INSTANCE.getAlias(var);
            path = AcePathfinder.INSTANCE.getPath(var);
        }
        if (path == null) { path = ""; }
        String[] paths = path.split(",");
        int l = paths.length;
        for(int i=0; i < l; i++) {
            if( paths[i] != null ) {
                if( paths[i].equals("") ) {
                    m.put(var, val);
                } else {
                    if( paths[i].contains("@") ) {
                        buildPath(m, paths[i]);
                        boolean isEvent = false;
                        // This is a nested value
                        String[] temp = paths[i].split("[!@]"); 
                        if( paths[i].contains("!") ) isEvent = true;
                        HashMap pointer = traverseToPoint(m, temp[0]);
                        ArrayList a = (ArrayList) pointer.get(temp[1]);
                        if( a.isEmpty() )
                            newRecord(m, paths[i]);
                        HashMap d = (HashMap) a.get(a.size()-1);
                        if( isEvent ) {
                            if (eventAppendMode) {
                                for (int j = a.size() - 1; j > -1; j--) {
                                    HashMap tmp = (HashMap) a.get(j);
                                    if( tmp.containsKey("event") ) {
                                        if (((String) tmp.get("event")).equals(temp[2])) {
                                            d = tmp;
                                            break;
                                        }
                                    }
                                }
                            }
                            if( d.containsKey("event") ) {
                                if ( ! ((String) d.get("event")).equals(temp[2])) {
                                    // Uh oh, we have a new event without newRecord being called
                                    newRecord(m,  paths[i]);
                                    d = (HashMap) a.get(a.size()-1);
                                    d.put("event", temp[2]);
                                }
                            } else {
                                // New event
                                d.put("event", temp[2]);
                            }
                        }
                        var = setEventDateVar(var, isEvent);
//                        if (isEvent && (var.equals("pdate") || var.equals("idate") || var.equals("fedate") | var.equals("omdat") || var.equals("mladat") || var.equals("mlrdat") || var.equals("cdate") || var.equals("tdate") || var.equals("hadat"))) {
//                            var = "date";
//                        }
                        if (d.containsKey(var)) {
                            newRecord(m, paths[i]);
                            d = (HashMap) a.get(a.size()-1);
                            if (isEvent) d.put("event", temp[2]);
                        }
                        d.put(var, val);
                    } else {
                        // This is a bucket-level, non-series value.
                        buildNestedBuckets(m, paths[i]);
                        HashMap pointer = traverseToPoint(m, paths[i]);
                        pointer.put(var, val);
                    }
                }
            }
        }
    }

    /**
     * Creates a new record in a series, such as daily 
     * weather records, soil layers, etc. If the multi-line dataset space
     * is not already in the <pre>m</pre> parameter, it will be created.
     *
     * @param m The {@link HashMap} to be modified.
     * @param path The path to lookup and see if a multi-line record is
     *             supported for this field.
     */
    public static void newRecord(HashMap m, String path) {
        if( path != null ) {
            String[] paths = path.split(",");
            int l = paths.length;
            for(int i=0; i < l; i++) {
                if( paths[i].contains("@") ) {
                    String temp[] = paths[i].split("[@!]");
                    buildPath(m, paths[i]);
                    HashMap pointer = traverseToPoint(m, temp[0]);
                    ArrayList a = (ArrayList) pointer.get(temp[1]);
                    a.add(new HashMap());
                } 
            }
        } 
    }

    /**
     * Creates a nested path, if not already present in the map. This does not
     * support multipath definitions. Please split prior to sending the path to
     * this function.
     *
     * @param m The map to create the path inside of.
     * @param p The full path to create.
     */
    private static void buildPath(HashMap m, String p) {
        boolean isEvent = false;
        if(p.contains("@")) {
            String[] components = p.split("@");
            int cl = components.length;
            buildNestedBuckets(m, components[0]);
            if(cl == 2) {
                if(p.contains("!")) isEvent = true;
                HashMap pointer = traverseToPoint(m, components[0]);
                String d;
                if(isEvent) {
                    String[] temp = components[1].split("!");
                    d = temp[0];
                } else {
                    d = components[1];
                }
                if( ! pointer.containsKey(d) ) 
                    pointer.put(d, new ArrayList());
            }
        } 
    }

    /**
     * A helper method to created the nested bucket structure.
     * @param m The map in which to build the nested structure.
     * @param p The nested bucket structure to create
     */
    private static void buildNestedBuckets(HashMap m, String p) {
        String[] components = p.split(":");
        int cl = components.length;
        if(cl == 1) {
            if( ! m.containsKey(components[0]) )
                m.put(components[0], new HashMap());
        } else {
            HashMap temp = m;
            for(int i=0; i < cl; i++) {
                if( ! temp.containsKey(components[i]) )
                    temp.put(components[i], new HashMap());
                temp = (HashMap) temp.get(components[i]);
            }
        }
    }

    /**
     * A helper method to help traverse to a certain point in the map.
     * @param m Map to traverse
     * @param p Path to traverse to
     * @return A reference to the point in the map.
     */
    public static HashMap traverseToPoint(HashMap m, String p) {
        HashMap pointer = m;
        String[] base = p.split("[@]");
        String[] b = base[0].split("[:]");
        int bl = b.length;
        for(int i=0; i < bl; i++) {
            pointer = (HashMap) pointer.get(b[i]);
        }
        return pointer;
    }

    public static String getPathOr(HashMap<String, Object> map, String var, String alternatePath) {
        String path = AcePathfinder.INSTANCE.getPath(var);
        if (path == null) {
            return alternatePath;
        } else {
            return path;
        }
    }

    /**
     * Renames a date variable for an event
     */
    public static String setEventDateVar(String var, boolean isEvent) {
        var = var.toLowerCase();
//        if (isEvent && (var.equals("pdate") || var.equals("idate") || var.equals("fedate") | var.equals("omdat") || var.equals("mladat") || var.equals("mlrdat") || var.equals("cdate") || var.equals("tdate") || var.equals("hadat"))) {
//            var = "date";
//        }
        if (isEvent && isDate(var) && !var.equals("edate")) {
            var = "date";
        }
        return var;
    }

    /**
     * Renames a date variable from an event
     */
    public static String getEventDateVar(String event, String var) {
        var = var.toLowerCase();
        if (event == null || var.endsWith("date") || var.endsWith("dat")) return var;
        if (event.equals("planting")) {
            var = "pdate";
        } else if (event.equals("irrigation")) {
            var = "idate";
        } else if (event.equals("fertilizer")) {
            var = "fedate";
        } else if (event.equals("tillage")) {
            var = "tdate";
        } else if (event.equals("harvest")) {
            var = "hadat";
        } else if (event.equals("organic_matter")) {
            var = "omdat";
        } else if (event.equals("chemicals")) {
            var = "cdate";
        } else if (event.equals("mulch-apply")) {
            var = "mladat";
        } else if (event.equals("mulch-remove")) {
            var = "mlrdat";
        }
        return var;
    }
}