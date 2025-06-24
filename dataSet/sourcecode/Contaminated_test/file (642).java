package view;

import net.miginfocom.swing.MigLayout;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import controller.CipherHandler;

class DecoderPanel extends JPanel {

    private final int random;

    public DecoderPanel(int usableSize, int random) {
        super();

        this.random = random;
        int cipherPanelSize = 400;
        int wrapamount = (usableSize / cipherPanelSize);

        //set LayoutMng
        this.setLayout(new MigLayout("wrap" + wrapamount, "[grow, center]", ""));
        this.setBackground(Color.BLACK);

        Border panelBorder = new EmptyBorder(0, 0, 0, 0);
        this.setBorder(panelBorder);

        int cipherPanelHeight = 130;
        for(JPanel jPanel : CipherHandler.activeCiphers.values())
            add(jPanel, "w " + cipherPanelSize + "!, h " + cipherPanelHeight + "!");

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        Image image = null;
        if(random == 0) image = makeImageIcon("/images/Enlightened_Green.png").getImage();
        else if(random == 1) image = makeImageIcon("/images/Resistance_Blue.png").getImage();
        else if(random == 2) image = makeImageIcon("/images/Ingress_Dual.png").getImage();
        if(image != null) {
            int x = (this.getWidth() - image.getWidth(null)) / 2;
            int y = (this.getHeight() - image.getHeight(null)) / 2;
            g2d.drawImage(image, x, y, null);
        }
    }

    public void executeActiveCiphers(String code) {
        int activatedCiphersAmount = getComponentCount();
        for(int i = 0; i < activatedCiphersAmount; i++) {
            ((CipherPanel) getComponent(i)).executeCipher(code);
        }
    }

    public void executeSingleCipher(int methodID, String code) {
        ((CipherPanel) CipherHandler.activeCiphers.get(methodID)).executeCipher(code);
    }

    ImageIcon makeImageIcon(String relative_path) {
        URL imgURL = getClass().getResource(relative_path);
        return new ImageIcon(imgURL);
    }
}
