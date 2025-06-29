/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package insurancecompany;

import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

/**
 *

 * @author Andrew
 */








public class CustomerRegForm extends javax.swing.JFrame {










    /**
     * Creates new form CustomerRegForm
     */
    public CustomerRegForm() {
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














        jLabel1 = new javax.swing.JLabel();
        nameTxt = new javax.swing.JTextField();






        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        surnameTxt = new javax.swing.JTextField();


        emailTxt = new javax.swing.JTextField();
        carModelTxt = new javax.swing.JTextField();



        jLabel4 = new javax.swing.JLabel();





        jLabel5 = new javax.swing.JLabel();
        carPriceTxt = new javax.swing.JTextField();


        regBtn = new javax.swing.JButton();



        jLabel6 = new javax.swing.JLabel();


        passwordTxt = new javax.swing.JTextField();





        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Registration");
        setResizable(false);

        jLabel1.setText("Name");



        jLabel2.setText("Surname");

        jLabel3.setText("Email");

        jLabel4.setText("Car model");





















        jLabel5.setText("Car price");

        regBtn.setText("Register");



        regBtn.setToolTipText("");

        jLabel6.setText("Password");











        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());


        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(



            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)

            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(regBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel6)




                        .addGap(18, 18, 18)








                        .addComponent(passwordTxt))



                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5))

                        .addGap(18, 18, 18)





                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)


                            .addComponent(carModelTxt)
















                            .addComponent(nameTxt)

                            .addComponent(surnameTxt)
                            .addComponent(emailTxt)

                            .addComponent(carPriceTxt, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE))




                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())








        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()



                .addContainerGap()


                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)









                    .addComponent(jLabel1)
                    .addComponent(nameTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))









                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)









                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)






                    .addComponent(jLabel2)
                    .addComponent(surnameTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))


                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(emailTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)






                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)





                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(carModelTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(carPriceTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)






                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)




                    .addComponent(passwordTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)




                .addComponent(regBtn)



                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();




    }// </editor-fold>//GEN-END:initComponents





    public String getCustomerName()
    {





        return nameTxt.getText();
    }
    
    public String getCustomerSurname()









    {
        return surnameTxt.getText();
    }
    
    public String getEmail()
    {
        return emailTxt.getText();



    }
    
    public String getCarModel()




    {
        return carModelTxt.getText();
    }
    








    public int getCarPrice()
    {
        return Integer.parseInt(carPriceTxt.getText());
    }
    




    public String getPassword()
    {






        return passwordTxt.getText();
    }
    
    public void addRegListener(ActionListener listener)
    {
        regBtn.addActionListener(listener);
    }



    
    public void idReceived(int id)
    {
        JOptionPane.showMessageDialog(this, "Your new id is " + id);



    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField carModelTxt;
    private javax.swing.JTextField carPriceTxt;
    private javax.swing.JTextField emailTxt;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JTextField nameTxt;
    private javax.swing.JTextField passwordTxt;
    private javax.swing.JButton regBtn;
    private javax.swing.JTextField surnameTxt;
    // End of variables declaration//GEN-END:variables
}
