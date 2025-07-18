/*





 * To change this template, choose Tools | Templates
 * and open the template in the editor.


 */
package videorentalstore.gui;







import java.sql.ResultSet;






import java.sql.ResultSetMetaData;








import java.sql.SQLException;



import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;







import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;








import javax.swing.table.DefaultTableModel;


import videorentalstore.database.Database;



import videorentalstore.User.User;

/**
 *
 * @author ahurley
 */






public class CustomerAccount_Movies extends javax.swing.JFrame {
    private Database db;


    private User currentUser;







    private int selectedMovieID;


    /**
     * Creates new form CustomerAccount_Movie
     */
    public CustomerAccount_Movies(Database db, User currentUser)  {




        initComponents();








        this.db = db;


        this.currentUser = currentUser;


    }

    /**
     * This method is called from within the constructor to initialize the form.


     * WARNING: Do NOT modify this code. The content of this method is always








     * regenerated by the Form Editor.




     */
    @SuppressWarnings("unchecked")



    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        moviesPanel = new javax.swing.JPanel();
        sideBarPanel = new javax.swing.JPanel();





        rentalsRULogoSmallLabel = new javax.swing.JLabel();
        blackBarPanel = new javax.swing.JPanel();
        viewPaymentHistoryToolBar = new javax.swing.JToolBar();
        viewRentalHistoryToolBar = new javax.swing.JToolBar();







        deleteAccountToolBar = new javax.swing.JToolBar();

        GenresDropDownMenu = new javax.swing.JComboBox();




        MPAARDropDownMenu = new javax.swing.JComboBox();






        BrowseByLabel = new javax.swing.JLabel();




        searchTxt = new javax.swing.JTextField();
        findAMovieLabel = new javax.swing.JLabel();


        searchLabel = new javax.swing.JLabel();




        searchButton = new javax.swing.JButton();
        toolBar = new javax.swing.JToolBar();
        homeButton = new javax.swing.JButton();
        divider1 = new javax.swing.JLabel();
        myAccountButton = new javax.swing.JButton();






        divider2 = new javax.swing.JLabel();

        logoutButton = new javax.swing.JButton();
        movieBDScrollPanel = new javax.swing.JScrollPane();



        movieDBTable = new javax.swing.JTable();
        moviesLabel = new javax.swing.JLabel();
        rentMovieButton = new javax.swing.JButton();






        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);





        moviesPanel.setBackground(new java.awt.Color(255, 255, 255));











        moviesPanel.setName(""); // NOI18N






        sideBarPanel.setBackground(new java.awt.Color(153, 0, 0));



        rentalsRULogoSmallLabel.setBackground(new java.awt.Color(153, 0, 0));








        rentalsRULogoSmallLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/videorentalstore/gui/Image_Rent4ULogoSmall.jpg"))); // NOI18N

        blackBarPanel.setBackground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout blackBarPanelLayout = new javax.swing.GroupLayout(blackBarPanel);







        blackBarPanel.setLayout(blackBarPanelLayout);
        blackBarPanelLayout.setHorizontalGroup(
            blackBarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 25, Short.MAX_VALUE)
        );



        blackBarPanelLayout.setVerticalGroup(
            blackBarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)



            .addGap(0, 0, Short.MAX_VALUE)
        );




        viewPaymentHistoryToolBar.setBackground(new java.awt.Color(153, 0, 0));
        viewPaymentHistoryToolBar.setBorder(null);
        viewPaymentHistoryToolBar.setFloatable(false);
        viewPaymentHistoryToolBar.setRollover(true);
        viewPaymentHistoryToolBar.setBorderPainted(false);


        viewRentalHistoryToolBar.setBorder(null);


        viewRentalHistoryToolBar.setFloatable(false);
        viewRentalHistoryToolBar.setForeground(new java.awt.Color(255, 255, 255));
        viewRentalHistoryToolBar.setRollover(true);
        viewRentalHistoryToolBar.setBorderPainted(false);




        deleteAccountToolBar.setBorder(null);
        deleteAccountToolBar.setFloatable(false);
        deleteAccountToolBar.setForeground(new java.awt.Color(255, 255, 255));
        deleteAccountToolBar.setRollover(true);
        deleteAccountToolBar.setBorderPainted(false);






        GenresDropDownMenu.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Genres", "Action", "Adventure", "Comedy", "Thriller" }));



        GenresDropDownMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {








                GenresDropDownMenuActionPerformed(evt);

            }




        });

        MPAARDropDownMenu.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "MPAA Rating", "G", "PG", "PG-13", "R", "NC-17" }));
        MPAARDropDownMenu.setMinimumSize(new java.awt.Dimension(1800, 1000));
        MPAARDropDownMenu.setPreferredSize(new java.awt.Dimension(1800, 1000));
        MPAARDropDownMenu.addActionListener(new java.awt.event.ActionListener() {




















            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MPAARDropDownMenuActionPerformed(evt);
            }

        });













        BrowseByLabel.setFont(new java.awt.Font("Vani", 0, 14)); // NOI18N
        BrowseByLabel.setForeground(new java.awt.Color(255, 255, 255));
        BrowseByLabel.setText("Browse By:");





        findAMovieLabel.setFont(new java.awt.Font("Vani", 1, 24)); // NOI18N








        findAMovieLabel.setText("Find A Movie");










        searchLabel.setFont(new java.awt.Font("Vani", 0, 14)); // NOI18N





        searchLabel.setForeground(new java.awt.Color(255, 255, 255));






        searchLabel.setText("Search by: Title, Actor, Director");




        searchButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/videorentalstore/gui/Image_SearchIcon.png"))); // NOI18N
        searchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {





                searchButtonActionPerformed(evt);
            }
        });


        javax.swing.GroupLayout sideBarPanelLayout = new javax.swing.GroupLayout(sideBarPanel);
        sideBarPanel.setLayout(sideBarPanelLayout);



        sideBarPanelLayout.setHorizontalGroup(

            sideBarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sideBarPanelLayout.createSequentialGroup()






                .addGroup(sideBarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)






                    .addGroup(sideBarPanelLayout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addGroup(sideBarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rentalsRULogoSmallLabel)



                            .addGroup(sideBarPanelLayout.createSequentialGroup()
                                .addGap(74, 74, 74)





                                .addComponent(viewRentalHistoryToolBar, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(sideBarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(findAMovieLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(viewPaymentHistoryToolBar, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(sideBarPanelLayout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addGroup(sideBarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)




                            .addGroup(sideBarPanelLayout.createSequentialGroup()
                                .addComponent(searchTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(searchButton, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(deleteAccountToolBar, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(sideBarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)

                                .addComponent(MPAARDropDownMenu, javax.swing.GroupLayout.Alignment.LEADING, 0, 0, Short.MAX_VALUE)




                                .addComponent(GenresDropDownMenu, javax.swing.GroupLayout.Alignment.LEADING, 0, 187, Short.MAX_VALUE))))





                    .addGroup(sideBarPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(BrowseByLabel))
                    .addGroup(sideBarPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(searchLabel)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)






                .addComponent(blackBarPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))



        );
        sideBarPanelLayout.setVerticalGroup(
            sideBarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sideBarPanelLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(rentalsRULogoSmallLabel)






                .addGap(148, 148, 148)
                .addComponent(findAMovieLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)


                .addComponent(viewPaymentHistoryToolBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)




                .addGap(22, 22, 22)



                .addComponent(searchLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)






                .addGroup(sideBarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(searchButton)



                    .addComponent(searchTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))






                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(viewRentalHistoryToolBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38)


                .addComponent(BrowseByLabel)



                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)



                .addComponent(GenresDropDownMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)





                .addGap(18, 18, 18)
                .addComponent(MPAARDropDownMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)






                .addGap(1039, 1039, 1039)
                .addComponent(deleteAccountToolBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addComponent(blackBarPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)


        );





        toolBar.setBackground(new java.awt.Color(255, 255, 255));
        toolBar.setBorder(null);


        toolBar.setFloatable(false);
        toolBar.setRollover(true);
        toolBar.setBorderPainted(false);














        homeButton.setBackground(new java.awt.Color(255, 255, 255));
        homeButton.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N


        homeButton.setText("Home");
        homeButton.setBorderPainted(false);
        homeButton.setFocusable(false);
        homeButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        homeButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);






        homeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {





                homeButtonActionPerformed(evt);
            }
        });









        toolBar.add(homeButton);




        divider1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        divider1.setText("  |  ");




        toolBar.add(divider1);







        myAccountButton.setBackground(new java.awt.Color(255, 255, 255));



        myAccountButton.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        myAccountButton.setText("My Account");
        myAccountButton.setFocusable(false);
        myAccountButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);




        myAccountButton.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        myAccountButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                myAccountButtonActionPerformed(evt);
            }





        });
        toolBar.add(myAccountButton);

        divider2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        divider2.setText("  |  ");
        toolBar.add(divider2);






        logoutButton.setBackground(new java.awt.Color(255, 255, 255));



        logoutButton.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        logoutButton.setText("Logout");


        logoutButton.setFocusable(false);
        logoutButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);










        logoutButton.setVerticalAlignment(javax.swing.SwingConstants.TOP);




        logoutButton.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        logoutButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logoutButtonActionPerformed(evt);

            }
        });











        toolBar.add(logoutButton);















        movieDBTable.setModel(new javax.swing.table.DefaultTableModel(










            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},




                {null, null, null, null}
            },
















            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        movieBDScrollPanel.setViewportView(movieDBTable);





        moviesLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/videorentalstore/gui/Image_MovieLabel.png"))); // NOI18N








        rentMovieButton.setText("Rent");


        rentMovieButton.addActionListener(new java.awt.event.ActionListener() {





            public void actionPerformed(java.awt.event.ActionEvent evt) {


                rentMovieButtonActionPerformed(evt);


            }



        });

        javax.swing.GroupLayout moviesPanelLayout = new javax.swing.GroupLayout(moviesPanel);

        moviesPanel.setLayout(moviesPanelLayout);


        moviesPanelLayout.setHorizontalGroup(


            moviesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(moviesPanelLayout.createSequentialGroup()



                .addComponent(sideBarPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)







                .addGroup(moviesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(moviesPanelLayout.createSequentialGroup()






                        .addGap(18, 18, 18)
                        .addComponent(moviesLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(toolBar, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE))



                    .addGroup(moviesPanelLayout.createSequentialGroup()




                        .addGap(25, 25, 25)
                        .addGroup(moviesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)





                            .addComponent(rentMovieButton, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(movieBDScrollPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 1322, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(16262, Short.MAX_VALUE))
        );
        moviesPanelLayout.setVerticalGroup(


            moviesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(moviesPanelLayout.createSequentialGroup()
                .addGroup(moviesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(sideBarPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(moviesPanelLayout.createSequentialGroup()





                        .addGroup(moviesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(moviesPanelLayout.createSequentialGroup()
                                .addGap(31, 31, 31)
                                .addComponent(moviesLabel))

                            .addGroup(moviesPanelLayout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(toolBar, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(168, 168, 168)
                        .addComponent(movieBDScrollPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(rentMovieButton)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );


        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 18150, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 115, Short.MAX_VALUE)
                    .addComponent(moviesPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 116, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(





            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1000, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)




                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)




                    .addComponent(moviesPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 1000, javax.swing.GroupLayout.PREFERRED_SIZE)



                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void homeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_homeButtonActionPerformed





        dispose();
        CustomerAccount_Movies f = new CustomerAccount_Movies (db, currentUser);




        f.setVisible(true);

    }//GEN-LAST:event_homeButtonActionPerformed




    private void myAccountButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_myAccountButtonActionPerformed
        dispose();






        CustomerAccount_MyAccount  f = new CustomerAccount_MyAccount(db, currentUser);
        f.setVisible(true);




    }//GEN-LAST:event_myAccountButtonActionPerformed

    private void logoutButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logoutButtonActionPerformed
        dispose();


        Account_SignIn f = new Account_SignIn(db);








        f.setVisible(true);
    }//GEN-LAST:event_logoutButtonActionPerformed




    private void rentMovieButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rentMovieButtonActionPerformed




        dispose();
        CustomerAccount_Rent  f = new CustomerAccount_Rent(db, currentUser, Integer.parseInt(movieDBTable.getModel().getValueAt(movieDBTable.getSelectedRow(), 0).toString()));
        f.setVisible(true);



    }//GEN-LAST:event_rentMovieButtonActionPerformed

    private void MPAARDropDownMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MPAARDropDownMenuActionPerformed
        ResultSet rs = null;
        



        if (searchTxt.getText().isEmpty()) {
            if(MPAARDropDownMenu.getSelectedItem().toString().equals("MPAA Rating")){
                return;
            }




            rs = db.findMoviesWithMPAARating(MPAARDropDownMenu.getSelectedItem().toString());
        }
        else {


            rs = db.findMoviesWithMPAARating(MPAARDropDownMenu.getSelectedItem().toString(), searchTxt.getText());
        }
        





        try {
           


           movieDBTable.setModel(buildTableModel(rs));
         }



        catch (SQLException e){
            System.out.println(e);
        }
    }//GEN-LAST:event_MPAARDropDownMenuActionPerformed

    
    
    private void GenresDropDownMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GenresDropDownMenuActionPerformed






        ResultSet rs = null;
        
        if (searchTxt.getText().isEmpty()) {
            if(GenresDropDownMenu.getSelectedItem().toString().equals("Genres")){
            return;
        }
            rs = db.browseMoviesByGenre(GenresDropDownMenu.getSelectedItem().toString());
        }




        else {
            rs = db.browseMoviesByGenre(GenresDropDownMenu.getSelectedItem().toString(), searchTxt.getText());



        }
        
        try {
           
           movieDBTable.setModel(buildTableModel(rs));
         }
        catch (SQLException e){




            System.out.println(e);
        }
    }//GEN-LAST:event_GenresDropDownMenuActionPerformed

    
    






    private void searchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchButtonActionPerformed


        ResultSet rs = null;
        if(searchTxt.getText().isEmpty()){
            rs = db.displayMovieTable();
        }



        else {







            rs = db.generalSearch(searchTxt.getText());
        }
        try {
           movieDBTable.setModel(buildTableModel(rs));
         }
        catch (SQLException e){
            System.out.println(e);
        }


    }//GEN-LAST:event_searchButtonActionPerformed


    /**
     * @param args the command line arguments
     */


    public void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">



        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.

         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());



                    break;
                }
            }
        } catch (ClassNotFoundException ex) {




            java.util.logging.Logger.getLogger(CustomerAccount_Movies.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CustomerAccount_Movies.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CustomerAccount_Movies.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CustomerAccount_Movies.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                
                    new CustomerAccount_Movies(db, currentUser).setVisible(true);
                 



            }
        });
        
    }
    
    /*
     * This method reads the result set data returned from SQL Queries and parses
     * them so they can be displayed within a DefualtTableModel jTable
     */
    public static DefaultTableModel buildTableModel(ResultSet rs)
        throws SQLException {
        
        ResultSetMetaData metaData = rs.getMetaData();

        // names of columns
        Vector<String> columnNames = new Vector<String>();
        int columnCount = metaData.getColumnCount();
        for (int column = 1; column <= columnCount; column++) {
            columnNames.add(metaData.getColumnName(column));
            }

        // data of the table
        Vector<Vector<Object>> data = new Vector<Vector<Object>>();
        while (rs.next()) {
            Vector<Object> vector = new Vector<Object>();
            for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                vector.add(rs.getObject(columnIndex));
        }


        data.add(vector);
    }


    return new DefaultTableModel(data, columnNames);





}



	
	
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel BrowseByLabel;
    private javax.swing.JComboBox GenresDropDownMenu;

    private javax.swing.JComboBox MPAARDropDownMenu;
    private javax.swing.JPanel blackBarPanel;
    private javax.swing.JToolBar deleteAccountToolBar;
    private javax.swing.JLabel divider1;
    private javax.swing.JLabel divider2;
    private javax.swing.JLabel findAMovieLabel;
    private javax.swing.JButton homeButton;
    private javax.swing.JButton logoutButton;
    private javax.swing.JScrollPane movieBDScrollPanel;
    private javax.swing.JTable movieDBTable;
    private javax.swing.JLabel moviesLabel;
    private javax.swing.JPanel moviesPanel;
    private javax.swing.JButton myAccountButton;
    private javax.swing.JButton rentMovieButton;
    private javax.swing.JLabel rentalsRULogoSmallLabel;
    private javax.swing.JButton searchButton;
    private javax.swing.JLabel searchLabel;
    private javax.swing.JTextField searchTxt;
    private javax.swing.JPanel sideBarPanel;
    private javax.swing.JToolBar toolBar;
    private javax.swing.JToolBar viewPaymentHistoryToolBar;
    private javax.swing.JToolBar viewRentalHistoryToolBar;
    // End of variables declaration//GEN-END:variables
}
