/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * FramePrincipal.java
 *
 * Created on May 10, 2013, 12:54:40 PM
 */
package Presentacio;

import Domini.CtrDomini;
import javax.swing.JOptionPane;

/**
 *
 * @author miquel.masriera
 */
public class ControladorPresentacio extends javax.swing.JFrame {
    
    CtrDomini cd;
    String unitatDocent;
    PanelLogin pLogin;
    PanelMenuPrincipal pMenup;
    PanelHorariLectiu pHorariLectiu;

    public ControladorPresentacio() {
        initComponents();
        this.setResizable(false);
        this.setVisible(true);
        pLogin = new PanelLogin(this);
        pMenup = new PanelMenuPrincipal(this);
        pHorariLectiu = new PanelHorariLectiu(this);

        
        this.Layered.add(pLogin);
        this.Layered.add(pMenup);
        this.Layered.add(pHorariLectiu);
        canviaPanel("login");
    }
    
    
    /**
     * el que fa aquesta funció es mostrar en el frame principal el panell que se li
     * indica.
     * primer de tot fa invisibles tots els panells i després només activa el que 
     * s ha de mostrar 
     * 
     * @param nomPanel nom del panell a mostrar
     */
    public void canviaPanel(String nomPanel){
        pLogin.setVisible(false);
        pMenup.setVisible(false);
        pHorariLectiu.setVisible(false);

        if(nomPanel.equals("login")) pLogin.setVisible(true);
        else if(nomPanel.equals("menuPrincipal")) {
            pMenup.setNomUnitatDocent(unitatDocent);
            pMenup.setVisible(true);
        }
        else if (nomPanel.equals("PanelHorariLectiu")) pHorariLectiu.setVisible(true);            
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Layered = new javax.swing.JLayeredPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Layered, javax.swing.GroupLayout.DEFAULT_SIZE, 700, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Layered, javax.swing.GroupLayout.DEFAULT_SIZE, 550, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLayeredPane Layered;
    // End of variables declaration//GEN-END:variables

    public void mostraAvis(String text) {
        //PODEMOS TENER UN SEGUNDO PARAMETRO QUE INDICARA QUE TIPO DE AVISO ES PARA MOSTRAR UN WARNING_MESSAGE O OTRO TIPO DE MENSAJE.
        //TIPOS DE MENSAJE: WARNING_MESSAGE, ERROR_MESSAGE, PLAIN_MESSAGE, INFORMATION_MESSAGE
        JOptionPane.showMessageDialog(this, text, "GeneradorHoraris::ERROR", JOptionPane.WARNING_MESSAGE);
    }

    public void identificarUnitatDocent(String nomUnitatDocent) {
        System.out.println(nomUnitatDocent);
        unitatDocent = nomUnitatDocent;
        cd = new CtrDomini(unitatDocent);
    }
}