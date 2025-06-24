/*
 * Created by JFormDesigner on Fri Feb 17 15:36:50 CST 2023
 */

package ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

import burp.BurpExtender;
import burp.IBurpExtenderCallbacks;
import crypto.Decryption;
import crypto.Encryption;
//import org.jdesktop.swingx.*;

/**
 * @author YD
 */
public class CryptUI extends JPanel {

    IBurpExtenderCallbacks callbacks;

    public CryptUI(BurpExtender parent) {
        callbacks = parent.callbacks;
        initComponents();
    }

    public static JTextField getTextField3() {
        return textField3;
    }

    public static JTextField getTextField4() {
        return textField4;
    }

    public static JTextArea getTextArea7() {
        return textArea7;
    }

    public static JTextArea getTextArea8() {
        return textArea8;
    }

    private void button9(ActionEvent e) {
        // TODO add your code here
        // 解密
        String key = textField3.getText();
        String iv = textField4.getText();
        String data = textArea8.getText();
//        String result = JudgeInput(key,iv,data,"en");
        String De_Result = Decryption.decryptData(data,iv,key);
        System.out.println(De_Result);
        textArea7.setText(De_Result);
    }

    private void button10(ActionEvent e) {
        // TODO add your code here
        // 加密
        String key = textField3.getText();
        String iv = textField4.getText();
        String data = textArea7.getText();
//        String result = JudgeInput(key,iv,data,"en");
        String De_Result = Encryption.encryptData(data,iv,key);
        System.out.println(De_Result);
        textArea8.setText(De_Result);
    }

    private void button11(ActionEvent e) {
        // TODO add your code here
        // url编码
        String Bs64Date = textArea8.getText();
        if (!Bs64Date.isEmpty()){
            try {
                Bs64Date = java.net.URLEncoder.encode(Bs64Date, "UTF-8");
                textArea8.setText(Bs64Date);
            } catch (Exception e1) {
                textArea8.setText("UrlDecode Err:" + e1.toString());
            }
        }else {
            textArea8.setText("Please enter encrypt data!");
        }
    }

    private void button12(ActionEvent e) {
        // TODO add your code here
        // url解码
        String Bs64Date = textArea8.getText();
        if (!Bs64Date.isEmpty()){
            try {
                Bs64Date = Bs64Date.replace("+","%2B");
                Bs64Date = java.net.URLDecoder.decode(Bs64Date, "UTF-8");
                textArea8.setText(Bs64Date);
            } catch (Exception e1) {
                textArea8.setText("UrlDecode Err:" + e1.toString());
            }
        }else {
            textArea8.setText("Please enter encrypt data!");
        }
    }

    private void textField3(ActionEvent e) {
        // TODO add your code here
    }

    private void button1(ActionEvent e) {
        // TODO add your code here
        textField3.setText("");
        textField4.setText("");
        textArea7.setText("");
        textArea8.setText("");
    }


    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        panel2 = new JPanel();
        label6 = new JLabel();
        textField3 = new JTextField();
        label7 = new JLabel();
        textField4 = new JTextField();
        panel6 = new JPanel();
        scrollPane8 = new JScrollPane();
        textArea7 = new JTextArea();
        panel7 = new JPanel();
        button9 = new JButton();
        button10 = new JButton();
        button11 = new JButton();
        button12 = new JButton();
        button1 = new JButton();
        scrollPane9 = new JScrollPane();
        textArea8 = new JTextArea();

        //======== this ========
        setLayout(new BorderLayout());

        //======== panel2 ========
        {
            panel2.setMinimumSize(new Dimension(174, 30));
            panel2.setPreferredSize(new Dimension(174, 60));

            //---- label6 ----
            label6.setText("SessionKey");
            label6.setAlignmentY(0.0F);

            //---- textField3 ----
            textField3.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
            textField3.addActionListener(e -> textField3(e));

            //---- label7 ----
            label7.setText("IV");

            GroupLayout panel2Layout = new GroupLayout(panel2);
            panel2.setLayout(panel2Layout);
            panel2Layout.setHorizontalGroup(
                panel2Layout.createParallelGroup()
                    .addGroup(panel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(label6)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textField3, GroupLayout.DEFAULT_SIZE, 365, Short.MAX_VALUE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(label7)
                        .addGap(12, 12, 12)
                        .addComponent(textField4, GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE)
                        .addContainerGap())
            );
            panel2Layout.setVerticalGroup(
                panel2Layout.createParallelGroup()
                    .addGroup(GroupLayout.Alignment.TRAILING, panel2Layout.createSequentialGroup()
                        .addContainerGap(30, Short.MAX_VALUE)
                        .addGroup(panel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(textField3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addComponent(label6)
                            .addComponent(textField4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addComponent(label7, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
            );
        }
        add(panel2, BorderLayout.PAGE_START);

        //======== panel6 ========
        {
            panel6.setAlignmentX(0.0F);

            //======== scrollPane8 ========
            {
                scrollPane8.setBorder(new TitledBorder("\u660e\u6587"));

                //---- textArea7 ----
                textArea7.setLineWrap(true);
                textArea7.setWrapStyleWord(true);
                scrollPane8.setViewportView(textArea7);
            }

            //======== panel7 ========
            {
                panel7.setLayout(null);

                //---- button9 ----
                button9.setText("<--\u89e3\u5bc6");
                button9.addActionListener(e -> button9(e));
                panel7.add(button9);
                button9.setBounds(10, 75, 90, button9.getPreferredSize().height);

                //---- button10 ----
                button10.setText("\u52a0\u5bc6-->");
                button10.addActionListener(e -> button10(e));
                panel7.add(button10);
                button10.setBounds(10, 135, 90, button10.getPreferredSize().height);

                //---- button11 ----
                button11.setText("URL\u7f16\u7801");
                button11.addActionListener(e -> button11(e));
                panel7.add(button11);
                button11.setBounds(10, 250, 90, button11.getPreferredSize().height);

                //---- button12 ----
                button12.setText("URL\u89e3\u7801");
                button12.addActionListener(e -> button12(e));
                panel7.add(button12);
                button12.setBounds(10, 300, 90, button12.getPreferredSize().height);

                //---- button1 ----
                button1.setText("\u6e05\u7a7a\u5185\u5bb9");
                button1.addActionListener(e -> button1(e));
                panel7.add(button1);
                button1.setBounds(10, 380, 95, button1.getPreferredSize().height);

                {
                    // compute preferred size
                    Dimension preferredSize = new Dimension();
                    for(int i = 0; i < panel7.getComponentCount(); i++) {
                        Rectangle bounds = panel7.getComponent(i).getBounds();
                        preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                        preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                    }
                    Insets insets = panel7.getInsets();
                    preferredSize.width += insets.right;
                    preferredSize.height += insets.bottom;
                    panel7.setMinimumSize(preferredSize);
                    panel7.setPreferredSize(preferredSize);
                }
            }

            //======== scrollPane9 ========
            {
                scrollPane9.setBorder(new TitledBorder("\u5bc6\u6587"));

                //---- textArea8 ----
                textArea8.setLineWrap(true);
                textArea8.setWrapStyleWord(true);
                scrollPane9.setViewportView(textArea8);
            }

            GroupLayout panel6Layout = new GroupLayout(panel6);
            panel6.setLayout(panel6Layout);
            panel6Layout.setHorizontalGroup(
                panel6Layout.createParallelGroup()
                    .addGroup(panel6Layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addComponent(scrollPane8)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(panel7, GroupLayout.PREFERRED_SIZE, 111, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(scrollPane9)
                        .addGap(34, 34, 34))
            );
            panel6Layout.setVerticalGroup(
                panel6Layout.createParallelGroup()
                    .addGroup(GroupLayout.Alignment.TRAILING, panel6Layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addGroup(panel6Layout.createParallelGroup()
                            .addGroup(GroupLayout.Alignment.TRAILING, panel6Layout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addComponent(scrollPane8)
                                .addGap(30, 30, 30))
                            .addGroup(GroupLayout.Alignment.TRAILING, panel6Layout.createSequentialGroup()
                                .addComponent(scrollPane9)
                                .addGap(28, 28, 28))))
                    .addGroup(panel6Layout.createSequentialGroup()
                        .addComponent(panel7, GroupLayout.PREFERRED_SIZE, 475, GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
            );
        }
        add(panel6, BorderLayout.CENTER);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JPanel panel2;
    private JLabel label6;
    private static JTextField textField3;
    private JLabel label7;
    private static JTextField textField4;
    private JPanel panel6;
    private JScrollPane scrollPane8;
    private static JTextArea textArea7;
    private JPanel panel7;
    private JButton button9;
    private JButton button10;
    private JButton button11;
    private JButton button12;
    private JButton button1;
    private JScrollPane scrollPane9;
    private static JTextArea textArea8;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
