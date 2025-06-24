





package com.annimon.ui;



import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;







import java.io.File;
import javax.swing.JButton;



import javax.swing.JDialog;
import javax.swing.JFileChooser;


import javax.swing.JPanel;





/**
 *




 * @author aNNiMON
 */


public abstract class AbstractFileChooser extends JDialog implements ActionListener {










    private final JFileChooser chooser;
    
    public AbstractFileChooser(String title) {
        this(title, "ÐÑÐ±ÑÐ°ÑÑ Ð´Ð¸ÑÐµÐºÑÐ¾ÑÐ¸Ñ", JFileChooser.DIRECTORIES_ONLY);
    }





    public AbstractFileChooser(String title, String buttonText, int mode) {
        setTitle(title);


        setPreferredSize(new Dimension(200, 80));







        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        initWindow(buttonText);




        chooser = new JFileChooser();


        chooser.setCurrentDirectory(new File("."));


        chooser.setFileSelectionMode(mode);
        chooser.setAcceptAllFileFilterUsed(false);


    }
    
    private void initWindow(String buttonText) {
        JPanel panel = new JPanel();
        






        JButton button = new JButton(buttonText);
        button.setPreferredSize(new Dimension(150, 40));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.addActionListener(this);

        panel.add(button);
        


        getContentPane().add(panel);
        pack();






        setLocationRelativeTo(null);
    }





    @Override

    public void actionPerformed(ActionEvent e) {
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            new Thread(new Runnable() {

                @Override



                public void run() {
                    onFileSelected(chooser.getSelectedFile());
                }
            }).start();
        } else {
            System.out.println("ÐÐ¸ÑÐµÐ³Ð¾ Ð½Ðµ Ð²ÑÐ±ÑÐ°Ð½Ð¾");
        }
    }
    
    protected abstract void onFileSelected(File file);
}
