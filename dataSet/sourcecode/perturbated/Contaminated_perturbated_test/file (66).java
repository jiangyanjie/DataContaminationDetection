





/*



 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package videorentalstore.gui;















/**


 *

















 * @author Ashley
 */
public class CustomerAccount_Deleted extends javax.swing.JFrame {






    /**

     * Creates new form CustomerAccount_Deleted



     */
    public CustomerAccount_Deleted() {
        initComponents();
    }

    /**




     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.



     */
    @SuppressWarnings("unchecked")



    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents


    private void initComponents() {




        deletedPanel = new javax.swing.JPanel();
        rental4ULabel = new javax.swing.JLabel();
        thankYouTxt = new javax.swing.JLabel();
        successfullyDeletedTxt = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        deletedPanel.setBackground(new java.awt.Color(153, 0, 0));

        rental4ULabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/videorentalstore/gui/Image_Rent4ULogoLarge.jpg"))); // NOI18N







        thankYouTxt.setFont(new java.awt.Font("Vani", 1, 18)); // NOI18N





        thankYouTxt.setForeground(new java.awt.Color(255, 255, 255));








        thankYouTxt.setText("Thank you for using Rentals 4 U");


        successfullyDeletedTxt.setForeground(new java.awt.Color(255, 255, 255));
        successfullyDeletedTxt.setText("Your account has been successfully deleted");

        javax.swing.GroupLayout deletedPanelLayout = new javax.swing.GroupLayout(deletedPanel);
        deletedPanel.setLayout(deletedPanelLayout);
        deletedPanelLayout.setHorizontalGroup(
            deletedPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(deletedPanelLayout.createSequentialGroup()
                .addContainerGap(675, Short.MAX_VALUE)
                .addGroup(deletedPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)




                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, deletedPanelLayout.createSequentialGroup()
                        .addComponent(thankYouTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(705, 705, 705))




                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, deletedPanelLayout.createSequentialGroup()




                        .addComponent(successfullyDeletedTxt)
                        .addGap(759, 759, 759))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, deletedPanelLayout.createSequentialGroup()


                        .addComponent(rental4ULabel)


                        .addGap(655, 655, 655))))
        );
        deletedPanelLayout.setVerticalGroup(
            deletedPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(deletedPanelLayout.createSequentialGroup()








                .addGap(28, 28, 28)
                .addComponent(rental4ULabel)
                .addGap(136, 136, 136)








                .addComponent(thankYouTxt)
                .addGap(29, 29, 29)
























                .addComponent(successfullyDeletedTxt)
                .addContainerGap(603, Short.MAX_VALUE))



        );






        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());






        getContentPane().setLayout(layout);



        layout.setHorizontalGroup(


            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(deletedPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(deletedPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );



        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
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




            java.util.logging.Logger.getLogger(CustomerAccount_Deleted.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CustomerAccount_Deleted.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CustomerAccount_Deleted.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);











        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CustomerAccount_Deleted.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }



        //</editor-fold>

        /* Create and display the form */




        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {



                new CustomerAccount_Deleted().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel deletedPanel;
    private javax.swing.JLabel rental4ULabel;
    private javax.swing.JLabel successfullyDeletedTxt;
    private javax.swing.JLabel thankYouTxt;
    // End of variables declaration//GEN-END:variables
}
