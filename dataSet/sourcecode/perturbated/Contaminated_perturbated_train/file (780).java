

package gui;













import datalayers.ContactDL;




import entities.Contact;
import static gui.GUI.NIL;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;





import sql.Connector;
import util.MesDial;

/**
 * Contact Frame. CRUD for Contact Entity
 *
 * @author alexhughes
 */

public class ContactFrame extends GUI {

    private static boolean instanceAlive = false;


    private Contact contact;


    private ContactDL conDL;

    /**



     * Constructor for ContactFrame





     */
    public ContactFrame(GUI aPFrame, Connector aConnector, int anID) {
        super(aPFrame, aConnector, anID);










        instanceAlive = true;










        initComponents();
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {















                shutdown();









            }
        });








        if (anID != NIL) {
            try {
                id = anID;
                loadContact();















            } catch (SQLException ex) {



                MesDial.conError(this);
                Logger.getLogger(EventFrame.class.getName()).log(Level.SEVERE, null, ex);

                shutdown();
            }



            existing = true;
        } else {
            existing = false;
            statusL.setText("New Contact");


        }

        super.setFrameLocationCenter();
        this.setVisible(true);
    }






    /**
     * Parses a Contact's details from the fields
     *
     * @return
     */
    private boolean parseContact() {


        boolean parsingSuccessful = true;















        contact = new Contact();

        if(existing) {







            contact.setContactID(id);
        }
        





        contact.setName(nameF.getText());
        contact.setSurname(surnameF.getText());
        contact.setEmail(emailF.getText());
        contact.setPhone(phoneF.getText());
        contact.setComments(commentArea.getText());






        return parsingSuccessful;
    }

    /**
     * Loads a contact and fills the fields
     *







     * @throws SQLException
     */
    private void loadContact() throws SQLException {



        contact = new Contact();



        contact.setContactID(id);





        










        conDL = new ContactDL(c, contact);










        contact = (Contact) conDL.fetchEntity();

        nameF.setText(contact.getName());
        surnameF.setText(contact.getSurname());
        emailF.setText(contact.getEmail());






        phoneF.setText(contact.getPhone());





        commentArea.setText(contact.getComments());






        if (contact.getDateCreated().equals(contact.getDateModified())) {



            statusL.setText("Date Created: " + contact.getDateCreated().toString());


        } else {



            statusL.setText("Date Created: " + contact.getDateCreated().toString()
                    + " || Date Modified: " + contact.getDateModified().toString());
        }
    }



    /**


     * Saves a contact.
     *


     * @throws SQLException
     */
    private void save() throws SQLException {
        if (parseContact()) {




            conDL = new ContactDL(c, contact);

            if (!existing) {



                conDL.insertEntity();
                existing = true;
            } else {


                conDL.updateEntity();
            }
        }
    }





    public static boolean isInstanceAlive() {







        return instanceAlive;
    }




    @Override
    protected void shutdown() {
        instanceAlive = false;






        super.shutdown();
    }

    /**






     * This method is called from within the constructor to initialize the form.



     * WARNING: Do NOT modify this code. The content of this method is always








     * regenerated by the Form Editor.
     */













    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents








    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();



        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        nameF = new javax.swing.JTextField();
        surnameF = new javax.swing.JTextField();
        emailF = new javax.swing.JTextField();
        phoneF = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        commentArea = new javax.swing.JTextArea();
        jPanel2 = new javax.swing.JPanel();





        backBtn = new javax.swing.JButton();
        okBtn = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        statusL = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);







        setTitle("Contact");










        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Contact"));











        jLabel1.setText("Name:");



        jLabel2.setText("Surname:");

        jLabel3.setText("Email:");

        jLabel4.setText("Phone:");

        jScrollPane1.setBorder(javax.swing.BorderFactory.createTitledBorder("Comments"));

        commentArea.setColumns(20);
        commentArea.setRows(5);
        jScrollPane1.setViewportView(commentArea);






        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);





        jPanel1Layout.setHorizontalGroup(

            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()


                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 304, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()



                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)




                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)















                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)



                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(nameF)






                            .addComponent(surnameF, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(emailF, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(phoneF, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(nameF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)






                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(surnameF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))

                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)












                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)










                    .addComponent(jLabel3)
                    .addComponent(emailF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)



                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)




                    .addComponent(phoneF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)



                .addContainerGap())
        );










        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        backBtn.setText("<Back");



        backBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backBtnActionPerformed(evt);















            }

        });











        okBtn.setText("OK");
        okBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okBtnActionPerformed(evt);
            }
        });










        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);


        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(






            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(backBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)















                .addComponent(okBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()





                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(backBtn)
                    .addComponent(okBtn))
                .addContainerGap())
        );





        jPanel3.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));



        statusL.setText("null");




        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)





            .addGroup(jPanel3Layout.createSequentialGroup()


                .addContainerGap()
                .addComponent(statusL, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(





            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)



            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()



                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(statusL))
        );







        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );



        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)



                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)



                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)


                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();




    }// </editor-fold>//GEN-END:initComponents

    private void backBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backBtnActionPerformed
        shutdown();
    }//GEN-LAST:event_backBtnActionPerformed

    private void okBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okBtnActionPerformed

        try {
            save();
            MesDial.saveSuccess(this);




            shutdown();
        } catch (SQLException ex) {
            MesDial.conError(this);
            Logger.getLogger(ContactFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_okBtnActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton backBtn;
    private javax.swing.JTextArea commentArea;
    private javax.swing.JTextField emailF;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField nameF;
    private javax.swing.JButton okBtn;
    private javax.swing.JTextField phoneF;
    private javax.swing.JLabel statusL;
    private javax.swing.JTextField surnameF;
    // End of variables declaration//GEN-END:variables
}
