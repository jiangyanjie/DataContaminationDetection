package net.unknown_degree.seer.Professions.data;

import net.unknown_degree.seer.Professions.Professions;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import java.io.*;
import java.util.ArrayList;

import javax.xml.parsers.*;


public class DataRead extends JavaPlugin  {
    
    /*
     * Prints an awesome looking info box to the player giving them information
     * about a specified job in relevance to them.
     */
    public static void getProfInfo(CommandSender sender,String prof,Professions plugin) throws Exception {
        
        prof = prof.toLowerCase();
        Player p = (Player) sender;        
        
        /*
         * Output sexy messages...
         */
        if ( plugin.getConfig().getString("profs." + prof) != null ) {
            
            /*
             * Parse player data:
             */
            File file = new File("./plugins/professions/data/" + p.getPlayerListName() + ".xml");
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.parse(file);
            NodeList nodes = doc.getElementsByTagName("prof");
        
            p.sendMessage(ChatColor.GREEN + "================= ..::" + ChatColor.WHITE + "Professions Info" + ChatColor.GREEN + "::.. =================");
            
            if ( nodes.getLength() != 0 ) {
                for ( int i = 0; i < nodes.getLength(); ) {
                    Node n = nodes.item(i);
                    Element v = (Element) n;
                    String j = v.getAttribute("name").toLowerCase();
                    
                    if ( j.equals(prof) ) {
                        p.sendMessage(ChatColor.DARK_AQUA + prof.toUpperCase() + "  ::  " + ChatColor.GREEN + "Employed" + ChatColor.DARK_AQUA + "  ::  BASE PAY: " + ChatColor.WHITE + plugin.getConfig().getString("profs." + prof + ".basepay"));
                        p.sendMessage(ChatColor.DARK_AQUA + "LEVEL: " + ChatColor.WHITE + v.getAttribute("level") + ChatColor.DARK_AQUA + "   ::  XP: " + ChatColor.WHITE + v.getAttribute("exp"));
                        
                        // Experience calculations:
                        int Level = Integer.parseInt( v.getAttribute("level") );
                        int Exp = Integer.parseInt( v.getAttribute("exp") );
                        int xpTop = (50 /3 * ( Level^3 - ( 6*Level^2 ) + 17*Level-12 ) + 8) / 11;
                        double perTop = (float)100 / xpTop;
                        double xpCur = (double) (perTop * Exp);
                        
                        Integer cBars = (int) Math.ceil( ((xpCur / 10) * 2) );
                        String gdBars = "";
                        for ( Integer x = 0; x < cBars; x = x + 1 ) {
                            gdBars = gdBars + "=";
                        }
                        
                        Integer oBars = 20 - cBars;
                        String odBars = "";
                        for ( Integer y = 0; y < oBars; y = y + 1 ) {
                            odBars = odBars + "=";
                        }
                        
                        String progress = ChatColor.YELLOW + "LVL PROGRESS: " + xpCur + "% " + ChatColor.YELLOW + "{" + ChatColor.RED + gdBars + ChatColor.WHITE + odBars + ChatColor.YELLOW + "}";
                        p.sendMessage(progress);
                
                        p.sendMessage("");
                        prepareProfInfo(p, prof, plugin); // Output job details
                        break;
                    } 
                }
            } else {
                p.sendMessage(ChatColor.DARK_AQUA + prof.toUpperCase() + "  ::  " + ChatColor.RED + "Unemployed" + ChatColor.DARK_AQUA + "  ::  BASE PAY: " + ChatColor.WHITE + plugin.getConfig().getString("profs." + prof + ".basepay"));
                p.sendMessage(ChatColor.DARK_AQUA + "Type '/prof join " + prof + "' to join this job.");
                p.sendMessage("");
                prepareProfInfo(p, prof, plugin); // Output job details
            }
                
            p.sendMessage(ChatColor.GREEN + "====================================================");
          
        } else {
            p.sendMessage(ChatColor.RED + "Invalid job entered.");
            p.sendMessage(ChatColor.RED + "Type '/prof list' for a list of available jobs.");
        }
                
    }
    
    /*
     *  Retrieves information from the config file in the forum of a
     *  multidimensional array.
     */
    public static ArrayList<ArrayList<ArrayList<String>>> getProfData(String prof, Professions plugin) {
        
        Boolean v = true;
        Integer i = 0;
        Integer c;
        ArrayList<ArrayList<ArrayList<String>>> j = new ArrayList<ArrayList<ArrayList<String>>>();
        
        String action;
        String delimiter;
        String[] temp;
        
        while ( v == true ) {
            
            if ( plugin.getConfig().getString("profs." + prof + ".tier" + (i+1)) != null ) {
                
                j.add(i,new ArrayList<ArrayList<String>>());
                
                /*
                 * Check current tier for onBlockBreak event triggers:
                 */
                if ( plugin.getConfig().getString("profs." + prof + ".tier" + (i+1) + ".onBreak") != null ) {
                    
                    j.get(i).add(0, new ArrayList<String>());
                    
                    action = plugin.getConfig().getString("profs." + prof + ".tier" + (i+1) + ".onBreak");
                    delimiter = ",";
                    
                    temp = action.split(delimiter);

                    for (c = 0; c < temp.length; c++) {
                        (((ArrayList<ArrayList<String>>)j.get(i)).get(0)).add(c,temp[c]);
                    }
                    
                } else {
                    j.get(i).add(0, new ArrayList<String>());
                    (((ArrayList<ArrayList<String>>)j.get(i)).get(0)).add(0,null);
                }
                
                /*
                 * Check current tier for onBlockPlace event triggers:
                 */
                if ( plugin.getConfig().getString("profs." + prof + ".tier" + (i+1) + ".onPlace") != null ) {
                    
                    j.get(i).add(1, new ArrayList<String>());
                    
                    action = plugin.getConfig().getString("profs." + prof + ".tier" + (i+1) + ".onPlace");
                    delimiter = ",";
                    
                    temp = action.split(delimiter);

                    for (c = 0; c < temp.length; c++) {
                        (((ArrayList<ArrayList<String>>)j.get(i)).get(1)).add(c,temp[c]);
                    }
                    
                } else {
                    j.get(i).add(1, new ArrayList<String>());
                    (((ArrayList<ArrayList<String>>)j.get(i)).get(1)).add(0,null);
                }
                
                // TODO Add onAcquire section...

                i = i + 1;
                
            } else {
                // Exit loop when we've found all the tiers...
                v = false;
            }
            
        }

        return j;
        
    }
    
    /*
     * Prepares the profession information so it's in a user-friendly format
     * when it is output by the '/prof info' command.
     */
    private static void prepareProfInfo(Player p, String prof, Professions plugin) {
        
        ArrayList<ArrayList<ArrayList<String>>> j = getProfData(prof, plugin);

        Integer i1;
        Integer i2;
        
        String temp;
        
        for ( i1 = 0; i1 <j.size(); i1++ ) {
            p.sendMessage(ChatColor.RED + "-- TIER" + (i1 + 1) + " --");
            
            /*
             * Send onBlockBreak rewards (if any):
             */
            temp = "";
            if ( j.get(i1).get(0).get(0) != null ) {
                for ( i2 = 0; i2 < j.get(i1).get(0).size(); i2++ ) {
                    temp = j.get(i1).get(0).get(i2) + ", " + temp;
                }
                p.sendMessage(ChatColor.YELLOW + "BREAK: " + ChatColor.WHITE + temp);
            }
            
            /* 
             * Send onBlockPlace rewards (if any):
             */
            temp = "";
            if ( j.get(i1).get(1).get(0) != null ) {
                for ( i2 = 0; i2 < j.get(i1).get(1).size(); i2++ ) {
                    temp = j.get(i1).get(1).get(i2) + ", " + temp;
                }
                p.sendMessage(ChatColor.YELLOW + "PLACE: " + ChatColor.WHITE + temp);
            }
        }
        
    }

    /*
     * Checks to see if the player is employed in a specific job or not.
     * Returns true if they ARE employed. 
     */
    public static boolean isInProf(Player p, String prof, Professions plugin) throws ParserConfigurationException, SAXException, IOException {
        
        if ( plugin.getConfig().getString("profs." + prof) != null ) {
        
            /*
             * Parse player data:
             */
            File file = new File("./plugins/professions/data/" + p.getPlayerListName() + ".xml");
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.parse(file);
            NodeList nodes = doc.getElementsByTagName("prof");
            
            if ( nodes.getLength() != 0 ) {
                for ( int i = 0; i < nodes.getLength(); ) {
                    Node n = nodes.item(i);
                    Element v = (Element) n;
                    String j = v.getAttribute("name").toLowerCase();
                    
                    if ( j.equals(prof) ) {
                        return true;
                    } else { 
                        return false;
                    }
                }
            }
            
        } else {
            p.sendMessage(ChatColor.RED + "Invalid job entered.");
            p.sendMessage(ChatColor.RED + "Type '/prof list' for a list of available jobs.");
            return false;
        }
        return false;
        
    }
    
}
