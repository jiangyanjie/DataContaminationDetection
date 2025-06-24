package org.agmip.ace;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import au.com.bytecode.opencsv.CSVReader;

public enum AcePathfinder {
    INSTANCE;

    private final HashMap<String, String> pathfinder = new HashMap<String, String>();
    private final HashMap<String, String> pathfinderAlias = new HashMap<String, String>();
    private final ArrayList<String> datefinder = new ArrayList<String>();
    private final Logger LOG = LoggerFactory.getLogger("org.agmip.util.AcePathfinder");

    AcePathfinder() {
        InputStream master = getClass().getClassLoader().getResourceAsStream("pathfinder_1.0.csv");
        InputStream observed = getClass().getClassLoader().getResourceAsStream("obs_pathfinder_1.0.csv");
        loadFromEmbeddedCSV(master);
        loadFromEmbeddedCSV(observed);
    }

    public String getPath(String lookup) {
    	if (lookup != null ) {
            if (lookup.contains("__")) {
                String[] tmp = lookup.split("__");
                if (tmp.length > 1 && !tmp[1].trim().equals("")) {
                    return setSuffixMatch(tmp[1].trim());
                } else {
                    lookup = tmp[0];
                }
            }
            // Temporary hardwire
            if (lookup.toLowerCase().endsWith("cul_id")) {
                return pathfinder.get("cul_id");
            } else if (isAlias(lookup.toLowerCase())) {
                return pathfinder.get(getAlias(lookup.toLowerCase()));
            }
    		return pathfinder.get(lookup.toLowerCase());
    	} else {
    		LOG.error("Passed a null to getPath()");
    		return null;
    	}
    }

    public void setPath(String lookup, String path) {
        pathfinder.put(lookup.toLowerCase(), path);
    }

    public boolean isDate(String lookup) {
        return datefinder.contains(lookup);
    }
    
    public boolean isAlias(String lookup) {
        return pathfinderAlias.containsKey(lookup);
    }
    
    public String getAlias(String lookup) {
        if (isAlias(lookup)) {
            return pathfinderAlias.get(lookup);
        } else {
            return lookup;
        }
    }

    private void loadFromEmbeddedCSV(InputStream res) {
        try {
            if( res != null ) {
                CSVReader reader = new CSVReader(new InputStreamReader(res));
                String[] line;
                reader.readNext(); // Skip the first line
                while(( line = reader.readNext()) != null) {
                    if(! line[24].equals("-2") ) {
                        String path = setGroupMatch(line[15]);
                        if(line[2].toLowerCase().equals("wst_id")) {
                            if( path != null ) path = ",weather";
                        } else if (line[2].toLowerCase().equals("soil_id")) {
                            if( path != null ) path = ",soil";
                        }
                        if( pathfinder.containsKey(line[4].toLowerCase()) ) LOG.debug("Conflict with variable: "+line[0]+" Original Value: "+getPath(line[0])+" New Value: "+path);
                        if( path != null ) {
                            setPath(line[2], path);
                            String codeSynon = line[19].trim();
                            if (!codeSynon.equals("")) {
                                String[] keys = codeSynon.split("[\\s,]");
                                for (String key : keys) {
                                    if (!key.trim().equals("")) {
                                        pathfinderAlias.put(key.trim().toLowerCase(), line[2].toLowerCase());
                                    }
                                }
                                
                            }
                        } 
                        if (line[8].toLowerCase().equals("date")) {
                            datefinder.add(line[2].toLowerCase());
                        }
                    }
                }
                reader.close();
            } else {
                LOG.error("Missing embedded CSV file for configuration. AcePathfinder will be blank");
            }
        } catch(IOException ex) {
            LOG.debug(ex.toString());
            throw new RuntimeException(ex);
        }
    }
    
    private String setSuffixMatch(String suffix) {
        if (suffix == null) {
            return "";
        }
        suffix = suffix.toLowerCase().trim();
        if (suffix.equals("soil")) {
            return setGroupMatch("4051");
        } else if (suffix.equals("soillayer")) {
            return setGroupMatch("4052");
        } else if (suffix.equals("weather")) {
            return setGroupMatch("5041");
        } else if (suffix.equals("weatherdaily")) {
            return setGroupMatch("5052");
        } else {
            return "";
        }
    }

    private String setGroupMatch(String groupOrder) {
        try {
            int id = new BigInteger(groupOrder).intValue();
            if( ( id >= 1011 && id <= 1081 ) || id == 2011 || id == 2031 || id == 2121 || id == 2071 || id == 2081 || id == 2091 || id == 2101 || id == 2111 || id == 2141 || id == 2211 ) {
                // Global bucket
                return "";
            } else if ( ( id >= 5001 && id <= 5013 ) || id == 5041 || id == 5046 ) {
                // Weather Global bucket
                return "weather";
            } else if ( id == 5052 ) {
                // Weather Daily data
                return "weather@dailyWeather";
            } else if ( ( id >= 4001 && id <= 4031 ) || ( id >= 4041 && id <= 4042 ) || id == 4051 ) {
                // Soil Global
                return "soil";
            } else if ( id == 4052 ) {
                // Soil Layer data
                return "soil@soilLayer";
            } else if ( id == 2051 ) {
                // Initial Conditions
                return "initial_conditions";
            } else if ( id == 2052 ) {
                // Initial Conditions soil layer data
                return "initial_conditions@soilLayer";
            } else if ( id == 2021 || id == 2061 ) {
                // Events - planting
                return "management@events!planting";
            } else if ( id == 2072 ) {
                // Events - irrigation
                return "management@events!irrigation";
            } else if ( id == 2073 ) {
                // Events - auto-irrigation
                return "management@events!auto_irrig";
            } else if ( id == 2082 ) {
                // Events - fertilizer
                return "management@events!fertilizer";
            } else if ( id == 2122 ) {
                // Events - tillage
                return "management@events!tillage";
            } else if ( id == 2142 ) {
                // Events - harvest
                return "management@events!harvest";
            } else if ( id == 2092 ) {
                // Events - organic material
                return "management@events!organic_matter";
            } else if ( id == 2112 ) {
                // Events - chemical
                return "management@events!chemicals";
            } else if ( id == 2102 ) {
                // Events - mulch
                return "management@events!mulch";
            } else if ( id >= 2502 && id <= 2510 ) {
                // Observed summary data
                return "observed";
            } else if ( id >= 2511 && id <= 2599 ) {
                // Observed time series data
                return "observed@timeSeries";
            } else {
                // Ignored!
            }
        } catch (NumberFormatException ex) {
            LOG.debug(ex.toString());
            throw new RuntimeException(ex);
        }
        return null;
    }

    HashMap<String, String> peekAtPathfinder() {
        return pathfinder;
    }

    public ArrayList<String> peekAtDatefinder() {
        return datefinder;
    }
}
