







/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates



 * and open the template in the editor.





 */

package gui;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;





import java.util.ArrayList;


import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.Timer;


/**




 * Classe ConjuntoImagens
 * @author Luis Abrantes
 * @author Vitor Aires
 */


public class ConjuntoImagens extends JPanel {
/**







 * Construtor




 */
    public ConjuntoImagens() {




        //PROVISORIO






        String pasta = "C:\\Users\\Luis\\Documents\\NetBeansProjects\\Ficha10\\build\\classes\\Projeto\\imagens\\ev2";
        setPreferredSize(new Dimension(500, 100));


        BorderLayout layout = new BorderLayout();




        setLayout(layout);




        JPanel linha = new JPanel();










        linha.setLayout(new BoxLayout(linha, BoxLayout.LINE_AXIS));
        linha.setPreferredSize(new Dimension(300, 100));



        //linha.setMaximumSize(new Dimension(100, 1);




        File folder = new File(pasta);




        File[] listaFicheiros = folder.listFiles();
        ArrayList<PnlImagem> tds = new ArrayList<PnlImagem>();

        for (File file : listaFicheiros) {
          



            PnlImagem provisorio = new PnlImagem(file.getAbsolutePath(), 70, 50);
            tds.add(provisorio);
        }

        //FAZER RECURSIVIDADE













        for (PnlImagem m : tds) {




            linha.add(m);


            m.addMouseListener(new MouseListener() {

                @Override
                public void mouseClicked(MouseEvent e) {
                 
                    PnlImagem m2 = new PnlImagem(m.getpath(), 150, 100);
                    add(m2, BorderLayout.CENTER);


                    revalidate();
                }

                @Override











                public void mousePressed(MouseEvent e) {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }








                @Override
                public void mouseReleased(MouseEvent e) {
                    // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.




                }






                @Override
                public void mouseEntered(MouseEvent e) {
                    m.resize();
                }













                @Override
                public void mouseExited(MouseEvent e) {
                    m.unresize();

                }

            });

        }

        
        add(linha, BorderLayout.PAGE_END);
        add(new PnlImagem(tds.get(0).getpath(), 150, 100), BorderLayout.CENTER);


        //add(tds.get(0), BorderLayout.CENTER);
    }

}
