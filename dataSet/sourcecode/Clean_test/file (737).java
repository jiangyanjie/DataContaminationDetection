/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package View.CustomComponent;

/**
 *
 * @author anh
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.LayoutManager;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * This abstract class is used as a framework to paint other self-painted panels.
 *
 * @author vuongdothanhhuy
 */
public class CPanel extends JPanel {

    public Image image;
    private String s;
    private boolean isSelected;

    public boolean isIsSelected() {
        return isSelected;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }



    public CPanel() {
        setOpaque(false);
    }
    
    public CPanel(LayoutManager layout) {
        this.setLayout(layout);
        setOpaque(false);
    }
    
    public CPanel(LayoutManager layout,Color c) {
        this.setLayout(layout);
        this.setBackground(c);
//        setOpaque(false);
    }


    public void setImage(ImageIcon icon) {
        this.image = icon.getImage();;
    }
    
        public CPanel(String text) {
            this.s = text;
        setOpaque(false);
    }

    /**
     * Draw a panel with input image file and FlowLayout.
     *
     * @param fileName
     */
    
    
    
    public CPanel(ImageIcon icon) {
        this(new BorderLayout(), icon);
    }

    /**
     * Draw a panel with input image file and input layout.
     * 
     * @param layout
     * @param fileName
     */
    public CPanel(LayoutManager layout, ImageIcon icon) {
        
        //this panel is used for paint the image
        setLayout(layout);
        setOpaque(false);
        image = icon.getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
         super.paintComponent(g);
//        if(s!=null){
//            g.drawString(s, getWidth(), getHeight());
//        }
        g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
    }
}
