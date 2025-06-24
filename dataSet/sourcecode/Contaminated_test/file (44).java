/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cabservice;

import java.awt.Color;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.Object;
 
public class Current_cab extends JPanel {
    private boolean DEBUG = false;
   // Object[][] data;
    //Object[][] data=new Object[1][7];
 
    public Current_cab() throws SQLException {
        super(new GridLayout(1,0));
 
        String[] columnNames = {"Vehicle_Id","User_Id","Source_City","Source_LandMark","Dest_City","Dest_Landmark","Date_time","Current-X","Current-Y"};
                
        Dbconn db=new Dbconn();
        db.executequery("select * from booking_log");
        
         db.rs.next();
        
            String V=db.rs.getString(2).trim();
            String U=db.rs.getString(3).trim();
            String SC=db.rs.getString(4).trim();
            String SL=db.rs.getString(5).trim();
            String DC=db.rs.getString(6).trim();
            String DL=db.rs.getString(7).trim();
            String DT=db.rs.getString(8).trim();
            String CX=db.rs.getString(9).trim();
            String CY=db.rs.getString(10).trim();
            
                   
         Object[][] data = {
        {V, U,
         SC, SL, DC,DL,DT,CX,CY}};
                   
        
 
        
       
 
        final JTable table = new JTable(data, columnNames);
        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        table.setFillsViewportHeight(true);
 
        if (DEBUG) {
            table.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    printDebugData(table);
                }
            });
        }
 
        //Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(table);
 
        //Add the scroll pane to this panel.
        add(scrollPane);
    }
 
    private void printDebugData(JTable table) {
        int numRows = table.getRowCount();
        int numCols = table.getColumnCount();
        javax.swing.table.TableModel model = table.getModel();
 
        System.out.println("Value of data: ");
        for (int i=0; i < numRows; i++) {
            System.out.print("    row " + i + ":");
            for (int j=0; j < numCols; j++) {
                System.out.print("  " + model.getValueAt(i, j));
            }
            System.out.println();
        }
        System.out.println("--------------------------");
    }
 
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() throws SQLException {
        //Create and set up the window.
        JFrame frame = new JFrame("");
        frame.setSize(1000, 100);
        
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
 
        //Create and set up the content pane.
        Current_cab newContentPane = new Current_cab();
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);
       // frame.getContentPane().setBackground(Color.yellow);
        //Display the window.
       // frame.pack();
        frame.setVisible(true);
    }
 
    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    createAndShowGUI();
                } catch (SQLException ex) {
                    Logger.getLogger(Current_cab.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
}