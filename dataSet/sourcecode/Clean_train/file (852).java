package com.ghostchu.peerbanhelper.gui.crossimpl;

import lombok.Getter;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.util.Locale;

@Getter
public class CrossDownloaderDialog extends JFrame {
    private JPanel mainPane;
    private JLabel tooltip;
    private JLabel taskTitle;
    private JLabel description;
    private JProgressBar progressBar;

    public CrossDownloaderDialog() {
        setContentPane(mainPane);
        setSize(480, 130);
        int width = Toolkit.getDefaultToolkit().getScreenSize().width;
        int height = Toolkit.getDefaultToolkit().getScreenSize().height;
        this.setBounds((width - getWidth()) / 2, (height - getHeight()) / 2, getWidth(), getHeight());
        setVisible(false);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setIconImage(null);
        setResizable(false);
        setLocationRelativeTo(null);
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        mainPane = new JPanel();
        mainPane.setLayout(new BorderLayout(0, 0));
        taskTitle = new JLabel();
        Font taskTitleFont = this.$$$getFont$$$(null, -1, 24, taskTitle.getFont());
        if (taskTitleFont != null) taskTitle.setFont(taskTitleFont);
        taskTitle.setText("Downloading libraries...");
        mainPane.add(taskTitle, BorderLayout.NORTH);
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new BorderLayout(0, 0));
        mainPane.add(panel1, BorderLayout.CENTER);
        description = new JLabel();
        Font descriptionFont = this.$$$getFont$$$(null, -1, 14, description.getFont());
        if (descriptionFont != null) description.setFont(descriptionFont);
        description.setText("Downloading xxx.yyy.zzz....");
        panel1.add(description, BorderLayout.NORTH);
        tooltip = new JLabel();
        tooltip.setText("PeerBanHelper are downloading libraries from Internet, it is necessary to make sure PBH running correctly...");
        panel1.add(tooltip, BorderLayout.SOUTH);
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new BorderLayout(50, 50));
        panel1.add(panel2, BorderLayout.CENTER);
        progressBar = new JProgressBar();
        Font progressBarFont = this.$$$getFont$$$(null, -1, 16, progressBar.getFont());
        if (progressBarFont != null) progressBar.setFont(progressBarFont);
        progressBar.setStringPainted(true);
        panel2.add(progressBar, BorderLayout.NORTH);
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        Font font = new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
        boolean isMac = System.getProperty("os.name", "").toLowerCase(Locale.ENGLISH).startsWith("mac");
        Font fontWithFallback = isMac ? new Font(font.getFamily(), font.getStyle(), font.getSize()) : new StyleContext().getFont(font.getFamily(), font.getStyle(), font.getSize());
        return fontWithFallback instanceof FontUIResource ? fontWithFallback : new FontUIResource(fontWithFallback);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return mainPane;
    }

}
