/*



 * To change this template, choose Tools | Templates



 * and open the template in the editor.





 */






/*






 * DataServiceForm.java
 *
 * Created on Sep 2, 2012, 10:11:52 PM




 */







package databasemysqlproject;

/**
 *







 * @author IBM
 */
public class DataServiceForm extends javax.swing.JFrame {











    /** Creates new form DataServiceForm */
    public DataServiceForm() {
        initComponents();
    }



    /** This method is called from within the constructor to
     * initialize the form.



     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.












     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        schoolPUEntityManager = java.beans.Beans.isDesignTime() ? null : javax.persistence.Persistence.createEntityManagerFactory("schoolPU").createEntityManager();
        studentQuery = java.beans.Beans.isDesignTime() ? null : schoolPUEntityManager.createQuery("SELECT s FROM Student s");
        studentList = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : studentQuery.getResultList();



        jScrollPane1 = new javax.swing.JScrollPane();





        jTable1 = new javax.swing.JTable();



        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);








        org.jdesktop.swingbinding.JTableBinding jTableBinding = org.jdesktop.swingbinding.SwingBindings.createJTableBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, studentList, jTable1);
        org.jdesktop.swingbinding.JTableBinding.ColumnBinding columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${id}"));










        columnBinding.setColumnName("Id");
        columnBinding.setColumnClass(Integer.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${name}"));




        columnBinding.setColumnName("Name");
        columnBinding.setColumnClass(String.class);

        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${address}"));
        columnBinding.setColumnName("Address");

        columnBinding.setColumnClass(String.class);
        bindingGroup.addBinding(jTableBinding);








        jScrollPane1.setViewportView(jTable1);





        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);




        layout.setHorizontalGroup(


            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))


        );




        layout.setVerticalGroup(


            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)

            .addGroup(layout.createSequentialGroup()











                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(25, Short.MAX_VALUE))



        );


        bindingGroup.bind();
















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
            java.util.logging.Logger.getLogger(DataServiceForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DataServiceForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DataServiceForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DataServiceForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);


        }
        //</editor-fold>

        /* Create and display the form */


        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new DataServiceForm().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;




    private javax.persistence.EntityManager schoolPUEntityManager;
    private java.util.List<databasemysqlproject.Student> studentList;
    private javax.persistence.Query studentQuery;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
}
