/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package View.Input;

import FileData.FileProcess;
import Model.Account;
import Model.Model;
import Model.Ultility;
import View.AccountsGUI;
import View.CustomComponent.CPanel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ResourceBundle;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 *
 * @author s3255203
 */
public class AccountInput extends JDialog implements Ultility {

    String strtem;
    private Model model = Model.getModel();//08365247
    private static int CHECK = 0;
    private final int limit = 3;
    private final int limit1 = 4;
    private JTextField accountName = new JTextField(10);
    private JPasswordField accountPassword = new JPasswordField(10);
    public String[] codeData = {"08", "090", "091", "092", "093", "094", "095", "096", "097", "098", "099", "0123", "0124", "0125", "0126", "0127", "0128", "0129"};
    private JComboBox cbCode = new JComboBox(codeData);
    private JTextField accountPhone1 = new JTextField(3);
    private JTextField accountPhone2 = new JTextField(3);
    private JTextField accountEmail = new JTextField(10);
    private JRadioButton accountStaff = new JRadioButton(model.getResources().getString("STAFF_A_TXT"), true);
    private JRadioButton accountManager = new JRadioButton(model.getResources().getString("MANAGER_A_TXT"));
    private ButtonGroup accountGroup = new ButtonGroup();
    private JTextField picName = new JTextField(10);
    private JButton accountPicture = new JButton(model.getResources().getString("UPLOAD_TXT"));
    
    private //<editor-fold defaultstate="collapsed" desc="comment">
            AccountsGUI
            //</editor-fold>
 aGUI;
    
    ResourceBundle resourse = model.getResources();
    // private FileProcess fp = new FileProcess();
    // private ArrayList<Account> accountList;

    // ADD ACCOUNT
    public AccountInput(AccountsGUI a) {

        super();
        aGUI = a;
        //a.setVisible(false);
        a.setFocusTraversalKeysEnabled(false);
        this.requestFocus();
        setTitle("Add new account");
        add(getAddAccountInputPanel());
        pack();
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);


    }

    private CPanel getAddAccountInputPanel() {


        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        CPanel mainPanel = new CPanel(new BorderLayout(), INPUT_PANEL_PIC);
        CPanel buttonPanel = new CPanel();
        CPanel contentPanel = new CPanel(new BorderLayout());
        CPanel labelPanel = new CPanel(new GridLayout(8, 2));
        CPanel textPanel = new CPanel(new GridLayout(8, 2));
        CPanel phonePanel = new CPanel(new FlowLayout());
        CPanel picPanel = new CPanel(new FlowLayout());

        phonePanel.add(cbCode);
        phonePanel.add(new JLabel("-"));
        phonePanel.add(accountPhone1);
        phonePanel.add(new JLabel("-"));
        phonePanel.add(accountPhone2);

        accountGroup.add(accountStaff);
        accountGroup.add(accountManager);
        accountStaff.isSelected();

        labelPanel.add(new JLabel(resourse.getString("USERNAME_TXT")));
        textPanel.add(accountName);
        labelPanel.add(new JLabel(resourse.getString("PASSWORD_TXT")));
        textPanel.add(accountPassword);
        labelPanel.add(new JLabel(resourse.getString("EMAIL_TXT")));
        textPanel.add(accountEmail);
        labelPanel.add(new JLabel(resourse.getString("PHONE_TXT")));
        textPanel.add(phonePanel);
        labelPanel.add(new JLabel(resourse.getString("ROLE_TXT")));
        textPanel.add(accountStaff);
        labelPanel.add(new JLabel(""));
        textPanel.add(accountManager);
        labelPanel.add(new JLabel(resourse.getString("IMAGE_TXT")));


        picPanel.add(picName);
        picName.setEditable(false);
        picPanel.add(accountPicture);
        textPanel.add(picPanel);
        picName.setText(DEFAULT_PHOTO);


        // actionlistener for combobox
        cbCode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                accountPhone1.setText("");
                accountPhone2.setText("");
                
                accountPhone2.setDocument(new PlainDocument() {
                        @Override
                        public void insertString(int offs, String str, AttributeSet a)
                                throws BadLocationException {
                            if (getLength() + str.length() <= limit1) {
                                super.insertString(offs, str, a);
                            }
                        }
                    });


                //  accountPhone2.removeKeyListener(accountPhone2.getKeyListeners());
                for (KeyListener kl : accountPhone1.getKeyListeners()) {
                    accountPhone1.removeKeyListener(kl);
                }

                if (cbCode.getSelectedItem().equals("08")) {
                    //      System.out.println("check");

                    accountPhone1.addKeyListener(new KeyAdapter() {
                        @Override
                        public void keyReleased(KeyEvent e) {
                            String value = accountPhone1.getText();
                            if (value.length() == 4) {
                                accountPhone2.requestFocus();
                            }
                        }
                    });
                    
                    accountPhone1.setDocument(new PlainDocument() {
                        @Override
                        public void insertString(int offs, String str, AttributeSet a)
                                throws BadLocationException {
                            if (getLength() + str.length() <= limit1) {
                                super.insertString(offs, str, a);
                            }
                        }
                    });

                } else {
                    accountPhone1.addKeyListener(new KeyAdapter() {
                        @Override
                        public void keyReleased(KeyEvent e) {
                            String value = accountPhone1.getText();
                            System.out.println("check2");
                            if (value.length() == 3) {
                                accountPhone2.requestFocus();
                            }
                        }
                    });
                    
                    accountPhone1.setDocument(new PlainDocument() {
                        @Override
                        public void insertString(int offs, String str, AttributeSet a)
                                throws BadLocationException {
                            if (getLength() + str.length() <= limit) {
                                super.insertString(offs, str, a);
                            }
                        }
                    });

                }
            }
        });
        cbCode.setSelectedIndex(0);
/////////////////////////////////////////////////////////////////////////////////////
        //keep account phone in length of 4
        accountPhone2.setDocument(new PlainDocument() {
            @Override
            public void insertString(int offs, String str, AttributeSet a)
                    throws BadLocationException {
                if (getLength() + str.length() <= limit1) {
                    super.insertString(offs, str, a);
                }
            }
        });

        accountPicture.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                int option = chooser.showSaveDialog(null);
                System.out.println("0");
                if (option == JFileChooser.APPROVE_OPTION) {
                    System.out.println("1");
                    System.out.println(FileProcess.checkExt(chooser.getSelectedFile().getPath()));
                    if (FileProcess.checkExt(chooser.getSelectedFile().getPath())) {
                        String name = FileProcess.generateName(FileProcess.findExt(chooser.getSelectedFile().getName()));
                        String path = "Images/" + name;

                        FileProcess.copyFile(chooser.getSelectedFile().getPath(), path);
                        picName.setText(name);
                    }
                }
            }
        });

        //create Add button
        JButton addButton = new JButton(resourse.getString("ADD_TXT"));
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String aName = accountName.getText().trim();
                String aPassword = accountPassword.getText().trim();
                String aPhone = cbCode.getSelectedItem() + "-" + accountPhone1.getText().trim() + "-" + accountPhone2.getText().trim();
                String aEmail = accountEmail.getText().trim();

                String message = "";


                if (aName.isEmpty()) {
                    message += resourse.getString("EMPTY_USER_TEXT_ERROR");
                } else if (model.findAccountByUserName(aName)) {
                    message = resourse.getString("SAME_USERNAME_ERROR");
                } else if (!aName.matches("^[a-zA-Z0-9 ]*")) {
                    message += resourse.getString("INVALID_ACCOUNT_NAME_FORMAT_ERROR");
                }
                if (aPassword.isEmpty()) {
                    message += resourse.getString("EMPTY_PASS_TEXT_ERROR");
                }
                if (aPhone.isEmpty()) {
                    message += resourse.getString("EMPTY_PHONE_TEXT_ERROR");
                } else if (!aPhone.matches("(((09[\\d]{1,1}|01[\\d]{2,2})-[\\d]{3,3}-[\\d]{4,4}|[\\d]{4,4}-[\\d]{4,4})|08-[\\d]{4,4}-[\\d]{4,4})")) {
                    message += resourse.getString("INVALID_PHONE_FORMAT_ERROR");
                }
                if (aEmail.isEmpty()) {
                    message += resourse.getString("EMPTY_EMAIL_TEXT_ERROR");
                } else if (!aEmail.matches("^[\\w\\-]([\\.\\w])+[\\w]+@([\\w\\-]+\\.)+[\\w]{2,4}$")) {
                    message += resourse.getString("INVALID_EMAIL_FORMAT_ERROR");
                }
                if (message.isEmpty()) {
                    model.addAccount(new Account(model.newAccountId(), aPassword, aName, aEmail, aPhone, convertRole(), picName.getText()));
                    JOptionPane.showMessageDialog(null, "Account has been added successful!");
                    aGUI.updateAccountPanel();
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, message);
                }
            }
        });

        //create cancel button and add dispose listener
        JButton cancelButton = new JButton(resourse.getString("CANCEL_TXT"));
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        buttonPanel.add(addButton);
        buttonPanel.add(cancelButton);
        contentPanel.add(labelPanel, BorderLayout.WEST);
        contentPanel.add(textPanel, BorderLayout.CENTER);
        mainPanel.add(contentPanel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        contentPanel.setBorder(BorderFactory.createTitledBorder(resourse.getString("ADD_TXT")));


        return mainPanel;

    }

    // EDIT ACCOUNT
    public AccountInput(Account a, AccountsGUI ag) {

        super();
        aGUI = ag;
        setTitle("Edit account");
        add(editAccountInputPanel(a));
        pack();
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

    }

    private CPanel editAccountInputPanel(final Account a) {

        CPanel mainPanel = new CPanel(new BorderLayout(), INPUT_PANEL_PIC);
        CPanel contentPanel = new CPanel(new BorderLayout());
        CPanel labelPanel = new CPanel(new GridLayout(8, 2));
        CPanel textPanel = new CPanel(new GridLayout(8, 2));
        CPanel phonePanel = new CPanel(new FlowLayout());
        CPanel picPanel = new CPanel(new FlowLayout());

        phonePanel.add(cbCode);
        phonePanel.add(new JLabel("-"));
        phonePanel.add(accountPhone1);
        phonePanel.add(new JLabel("-"));
        phonePanel.add(accountPhone2);

        String[] phoneParts = a.getPhoneNumber().split("-");
      


        accountGroup.add(accountStaff);
        accountGroup.add(accountManager);
        System.out.println(a.getRole());

        if (a.getRole() == STAFF) {
            accountStaff.setSelected(true);
        } else {
            accountManager.setSelected(true);
        }


        labelPanel.add(new JLabel(resourse.getString("USERNAME_TXT")));
        textPanel.add(accountName);
        accountName.setText(a.getUserName());
        labelPanel.add(new JLabel(resourse.getString("PASSWORD_TXT")));
        textPanel.add(accountPassword);
        accountPassword.setText(a.getPassword());
        labelPanel.add(new JLabel(resourse.getString("EMAIL_TXT")));
        textPanel.add(accountEmail);
        accountEmail.setText(a.getEmail());
        labelPanel.add(new JLabel(resourse.getString("PHONE_TXT")));
        textPanel.add(phonePanel);

        labelPanel.add(new JLabel(resourse.getString("ROLE_TXT")));
        textPanel.add(accountStaff);
        labelPanel.add(new JLabel(""));
        textPanel.add(accountManager);
        labelPanel.add(new JLabel(resourse.getString("IMAGE_TXT")));
        picPanel.add(picName);
        picName.setEditable(false);
        picPanel.add(accountPicture);
        picName.setText(a.getPic());
        textPanel.add(picPanel);

        cbCode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                accountPhone1.setText("");
                accountPhone2.setText("");
                
                accountPhone2.setDocument(new PlainDocument() {
                        @Override
                        public void insertString(int offs, String str, AttributeSet a)
                                throws BadLocationException {
                            if (getLength() + str.length() <= limit1) {
                                super.insertString(offs, str, a);
                            }
                        }
                    });
                 // accountPhone2.removeKeyListener(accountPhone2.getKeyListeners());
                for (KeyListener kl : accountPhone1.getKeyListeners()) {
                    accountPhone1.removeKeyListener(kl);
                }

                if (cbCode.getSelectedItem().equals("08")) {
                    //      System.out.println("check");

                    accountPhone1.addKeyListener(new KeyAdapter() {
                        @Override
                        public void keyReleased(KeyEvent e) {
                            String value = accountPhone1.getText();
                            System.out.println("check1");
                            if (value.length() == 4) {
                                accountPhone2.requestFocus();
                            }
                        }
                    });
                    
                    accountPhone1.setDocument(new PlainDocument() {
                        @Override
                        public void insertString(int offs, String str, AttributeSet a)
                                throws BadLocationException {
                            if (getLength() + str.length() <= limit1) {
                                super.insertString(offs, str, a);
                            }
                        }
                    });
                    
                    accountPhone2.setDocument(new PlainDocument() {
                        @Override
                        public void insertString(int offs, String str, AttributeSet a)
                                throws BadLocationException {
                            if (getLength() + str.length() <= limit1) {
                                super.insertString(offs, str, a);
                            }
                        }
                    });
                } else {
                    accountPhone1.addKeyListener(new KeyAdapter() {
                        @Override
                        public void keyReleased(KeyEvent e) {
                            String value = accountPhone1.getText();                            
                            if (value.length() == 3) {
                                accountPhone2.requestFocus();
                            }
                        }
                    });
                    
                    accountPhone1.setDocument(new PlainDocument() {
                        @Override
                        public void insertString(int offs, String str, AttributeSet a)
                                throws BadLocationException {
                            if (getLength() + str.length() <= limit) {
                                super.insertString(offs, str, a);
                            }
                        }
                    });
                    
                    accountPhone2.setDocument(new PlainDocument() {
                        @Override
                        public void insertString(int offs, String str, AttributeSet a)
                                throws BadLocationException {
                            if (getLength() + str.length() <= limit1) {
                                super.insertString(offs, str, a);
                            }
                        }
                    });
                    
                }
            }
        });
        cbCode.setSelectedItem(phoneParts[0]);
        accountPhone1.setText(phoneParts[1]);
        accountPhone2.setText(phoneParts[2]);
        //cbCode.setSelectedIndex(0);
        
       
            

//        accountPhone2.addKeyListener(new KeyListener() {
//            @Override
//            public void keyTyped(KeyEvent e) {
//        }        
//            @Override
//            public void keyPressed(KeyEvent e) {
//            }            
//            @Override
//            public void keyReleased(KeyEvent e) {
//                if (accountPhone2.getText().length() == 4) {
//                    setStrtmp(accountPhone2.getText());                    
//                }
//                if (accountPhone2.getText().length() > 4) {
//                    accountPhone2.setText(strtem);                    
//                }              
//            }
//        });
        

        accountPicture.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                int option = chooser.showSaveDialog(null);
                System.out.println("0");
                if (option == JFileChooser.APPROVE_OPTION) {
                    System.out.println("1");
                    System.out.println(FileProcess.checkExt(chooser.getSelectedFile().getPath()));
                    if (FileProcess.checkExt(chooser.getSelectedFile().getPath())) {
                        String name = FileProcess.generateName(FileProcess.findExt(chooser.getSelectedFile().getName()));
                        String path = "Images/" + name;
//                        System.out.println(path);
//                        System.out.println("2");
                        FileProcess.copyFile(chooser.getSelectedFile().getPath(), path);
                        picName.setText(name);
                    }
                }
            }
        });


        //create button panel
        CPanel buttonPanel = new CPanel();

        //create Edit button
        JButton editButton = new JButton(resourse.getString("SAVE_TXT"));

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String aName = accountName.getText().trim();
                String aPassword = accountPassword.getText().trim();
                String aPhone = cbCode.getSelectedItem() + "-" + accountPhone1.getText().trim() + "-" + accountPhone2.getText().trim();
                String aEmail = accountEmail.getText().trim();

                String message = "";

                if (aName.isEmpty()) {
                    message += resourse.getString("EMPTY_USER_TEXT_ERROR");
                } else if (!a.getUserName().equals(aName)) {
                    if (model.findAccountByUserName(aName)) {
                        message = resourse.getString("SAME_USERNAME_ERROR");
                    }
                } else if (!aName.matches("^[a-zA-Z0-9 ]*")) {
                    message += resourse.getString("INVALID_ACCOUNT_NAME_FORMAT_ERROR");
                }
                if (aPassword.isEmpty()) {
                    message += resourse.getString("EMPTY_PASS_TEXT_ERROR");
                }
                if (aPhone.isEmpty()) {
                    message += resourse.getString("EMPTY_PHONE_TEXT_ERROR");
                } else if (!aPhone.matches("(((09[\\d]{1,1}|01[\\d]{2,2})-[\\d]{3,3}-[\\d]{4,4}|[\\d]{4,4}-[\\d]{4,4})|08-[\\d]{4,4}-[\\d]{4,4})")) {
                    message += resourse.getString("INVALID_PHONE_FORMAT_ERROR");
                }
                if (aEmail.isEmpty()) {
                    message += resourse.getString("EMPTY_EMAIL_TEXT_ERROR");
                } else if (!aEmail.matches("^[\\w\\-]([\\.\\w])+[\\w]+@([\\w\\-]+\\.)+[\\w]{2,4}$")) {
                    message += resourse.getString("INVALID_EMAIL_FORMAT_ERROR");
                }
                if (message.isEmpty()) {
                    a.setUserName(aName);
                    a.setPassword(aPassword);
                    a.setPhoneNumber(aPhone);
                    a.setEmail(aEmail);
                    a.setRole(convertRole());
                    a.setPic(picName.getText());
                    JOptionPane.showMessageDialog(null, "Account has been changed successful!");
                    aGUI.updateAccountPanel();
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, message);
                }
            }
        });



        //create cancel button and add dispose listener
        JButton cancelButton = new JButton(resourse.getString("CANCEL_TXT"));
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        buttonPanel.add(editButton);
        buttonPanel.add(cancelButton);
        contentPanel.add(labelPanel, BorderLayout.WEST);
        contentPanel.add(textPanel, BorderLayout.CENTER);
        mainPanel.add(contentPanel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        contentPanel.setBorder(BorderFactory.createTitledBorder(resourse.getString("EDIT_TXT")));

        return mainPanel;

    }
    // convertRole() between MANAGER AND STAFF

    private int convertRole() {
        if (accountManager.isSelected()) {
            return ADMIN;
        }
        return STAFF;
    }

//    public void setStrtmp(String srr) {
//        this.strtem = srr;
//    }
}
