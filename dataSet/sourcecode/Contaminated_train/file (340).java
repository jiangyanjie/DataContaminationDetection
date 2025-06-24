
package View;

import Main.Main;
import Model.Account;
import Model.ImportExportSCV;
import Model.Model;
import Model.Ultility;
import View.CustomComponent.CButton;
import View.CustomComponent.CLabel;
import View.CustomComponent.CPanel;
import View.Input.AccountInput;
import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author anh
 */
public class AccountsGUI extends CPanel implements Ultility {

    private String username;
    private CPanel topPanel, menuPanel, mainPanel, accountPanel, contentPanel, botPanel, smallPanel;
    private JMenu fileMenu, viewMenu, helpMenu, toolMenu;
    private JMenuItem exitAction, switchViewAction, aboutAction, userGuideAction, logoutAction;
    private JMenuItem addAction, editAction, deleteAction, deleteAllAction, activeAction;
    private CButton accountBt, teacherBt, studentBt, classBt, invoiceBt, classTypeBt;
    private CButton addBt, editBt, deleteBt, activeBt, deleteAllBt, logoutBt, exportBt, importBt;
    private JButton langBt;
    private ArrayList<Account> selectedItems = new ArrayList<>();
    private static int index = 0;
    Model model = Model.getModel();
     ResourceBundle resourse = model.getResources();
    ImportExportSCV imp = new ImportExportSCV();

    public AccountsGUI(String username) {

        super(new BorderLayout(), BACKGROUND_PIC);
        this.username = username;
        topPanel = new CPanel(TOP_PANEL_PIC);
        mainPanel = new CPanel(new BorderLayout());
        menuPanel = new CPanel(new BorderLayout());

        menuPanel.add(getMenuBar(), BorderLayout.NORTH);
        menuPanel.add(getToolBar(), BorderLayout.CENTER);

        checkButton();

        contentPanel = getContentPanel();
        topPanel.add(menuPanel);
        mainPanel.add(getLeftPanel(), BorderLayout.WEST);
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        add(topPanel, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);

    }

    private CPanel getLeftPanel() {
        CPanel leftPanel = new CPanel(new GridLayout(8, 1), LEFT_PANEL_PIC);
        accountBt = new CButton(resourse.getString("ACCOUNT_TXT"));
        teacherBt = new CButton(resourse.getString("TEACHER_TXT"));
        studentBt = new CButton(resourse.getString("STUDENT_TXT"));
        classBt = new CButton(resourse.getString("CLASS_TXT"));
        invoiceBt = new CButton(resourse.getString("INVOICES_TXT"));
        classTypeBt = new CButton(resourse.getString("CLASS_TYPE_TXT"));
        


        CPanel thisPanel = new CPanel(new BorderLayout(), ITEM_MENU_PIC);
        thisPanel.add(accountBt);

        leftPanel.add(thisPanel);
        leftPanel.add(teacherBt);
        leftPanel.add(studentBt);
        leftPanel.add(classTypeBt);
        leftPanel.add(classBt);
        leftPanel.add(invoiceBt);


        accountBt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.changePanel(new AccountsGUI(username));
            }
        });

        teacherBt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.changePanel(new TeacherGUI(username));
            }
        });

        studentBt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.changePanel(new StudentGUI(username));
            }
        });
        classBt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.changePanel(new ClassGUI(username));
            }
        });
        invoiceBt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.changePanel(new InvoiceGUI(username));
            }
        });
        classTypeBt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.changePanel(new ClassTypeGUI(username));
            }
        });

        leftPanel.setBorder(new EmptyBorder(0, 5, 0, 10));

        return leftPanel;
    }

    private JToolBar getToolBar() {
        JToolBar toolBar = new JToolBar(JToolBar.HORIZONTAL);

        addBt = new CButton(ADD_BUTTON);
        editBt = new CButton(EDIT_BUTTON);
        deleteBt = new CButton(DELETE_BUTTON);
        deleteAllBt = new CButton(DELETE_ALL_BUTTON);
        activeBt = new CButton(ACTIVE_BUTTON);
        logoutBt = new CButton(LOG_OUT_BUTTON);
        exportBt = new CButton(EXPORT_BUTTON);
        importBt = new CButton(IMPORT_BUTTON);
        

        toolBar.add(addBt);
        toolBar.add(editBt);
        toolBar.add(deleteBt);
        toolBar.add(deleteAllBt);
        toolBar.add(importBt);
        toolBar.add(exportBt);
        toolBar.add(activeBt);
        langBt = new JButton(model.getResources().getString("LANGUAGE_TXT"));
        toolBar.add(langBt);
        langBt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                model.switchLanguage();
                Main.changePanel(new AccountsGUI(username));
            }
        });
        toolBar.add(logoutBt);

        addBt.setToolTipText(resourse.getString("ADD_TXT"));
        editBt.setToolTipText(resourse.getString("EDIT_TXT"));
        deleteBt.setToolTipText(resourse.getString("DELETE_TXT"));
        deleteAllBt.setToolTipText(resourse.getString("DELETE_ALL_TXT"));
        activeBt.setToolTipText(resourse.getString("ACTIVE_TXT"));
        logoutBt.setToolTipText(resourse.getString("LOGOUT_TXT"));

        // action listener
        
        
        exportBt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                
                imp.activeExport(ACCOUNT_PORT);
            }
        });
        
        importBt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                imp.activeImport(ACCOUNT_PORT);
            }
        });
        
        editBt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AccountInput accountInput = new AccountInput(selectedItems.get(0), getThis());
                updateAccountPanel();
                checkButton();
            }
        });

        deleteBt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                for (int i = 0; i < selectedItems.size(); i++) {
                    if (!selectedItems.get(i).getUserName().equals(username)) {
                        model.getAccountList().remove(selectedItems.get(i));
                    }
                }
                updateAccountPanel();
            }
        });

        deleteAllBt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

//                model.getAccountList().clear();
                Account cur = null;
                for (int i = 0; i < model.getAccountList().size(); i++) {
                    if (model.getAccountList().get(i).getUserName().equals(username)) {
                        cur = model.getAccountList().get(i);
                    }
                }
                index = 0;
                model.getAccountList().clear();
                model.addAccount(cur);
                updateAccountPanel();
            }
        });

        addBt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //                new AccountInput(this);
                //                model.getAccountList().add(new Account("00000", "1234566", "anh", "email@email.com", "+1234565", 1, "pic1.png"));
                AccountInput accountInput = new AccountInput(getThis());
                index = getTotalIndex();
                updateAccountPanel();
            }
        });

        activeBt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < selectedItems.size(); i++) {
                    if (selectedItems.get(i).isActive() == ACTIVE) {
                        selectedItems.get(i).setActive(DEACTIVE);
                    } else {
                        selectedItems.get(i).setActive(ACTIVE);
                    }
                }

                updateAccountPanel();
            }
        });

        editAction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AccountInput accountInput = new AccountInput(selectedItems.get(0), getThis());
                updateAccountPanel();
                checkButton();
            }
        });

        logoutBt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.changePanel(new LoginGUI());
            }
        });

        toolBar.setOpaque(false);
        toolBar.setBorder(new EmptyBorder(5, 0, 0, 0));
        return toolBar;
    }

    //menu bar and Menu
    private JMenuBar getMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        fileMenu = new JMenu(resourse.getString("FILE_MENU"));
        viewMenu = new JMenu(resourse.getString("VIEW_MENU"));
        helpMenu = new JMenu(resourse.getString("HELP_MENU"));
        toolMenu = new JMenu(resourse.getString("TOOL_MENU"));

        //JMenuItem
        exitAction = new JMenuItem(resourse.getString("EXIT_TXT"));
        switchViewAction = new JMenuItem(resourse.getString("SWITCH_TXT"));
        aboutAction = new JMenuItem(resourse.getString("ABOUT_TXT"));
        userGuideAction = new JMenuItem(resourse.getString("USER_GUIDE_TXT"));
        addAction = new JMenuItem(resourse.getString("ADD_TXT"));
        editAction = new JMenuItem(resourse.getString("EDIT_TXT"));
        deleteAction = new JMenuItem(resourse.getString("DELETE_TXT"));
        deleteAllAction = new JMenuItem(resourse.getString("DELETE_ALL_TXT"));
        activeAction = new JMenuItem(resourse.getString("ACTIVE_TXT"));
        logoutAction = new JMenuItem(resourse.getString("LOGOUT_TXT"));

        //action listener
        addAction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //                new AccountInput();
                //                model.getAccountList().add(new Account("00000", "1234566", "anh", "email@email.com", "+1234565", 1, "pic1.png"));
                AccountInput accountInput = new AccountInput(getThis());
                index = getTotalIndex();
                updateAccountPanel();
            }
        });

        deleteAction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                for (int i = 0; i < selectedItems.size(); i++) {
                    if (!selectedItems.get(i).getUserName().equals(username)) {
                        model.getAccountList().remove(selectedItems.get(i));
                    }
                }
                updateAccountPanel();
            }
        });

        activeAction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < selectedItems.size(); i++) {
                    if (selectedItems.get(i).isActive() == ACTIVE) {
                        selectedItems.get(i).setActive(DEACTIVE);
                    } else {
                        selectedItems.get(i).setActive(ACTIVE);
                    }
                }

                updateAccountPanel();
            }
        });

        deleteAllAction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

//                model.getAccountList().clear();
                Account cur = null;
                for (int i = 0; i < model.getAccountList().size(); i++) {
                    if (model.getAccountList().get(i).getUserName().equals(username)) {
                        cur = model.getAccountList().get(i);
                    }
                }
                index = 0;
                model.getAccountList().clear();
                model.addAccount(cur);
                updateAccountPanel();
            }
        });

        exitAction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (getDialogConfirmation(resourse.getString("QUIT_CONFRIM_TXT")) == JOptionPane.YES_OPTION) {
                    try {
                        model.saveAllData();
                        System.exit(0);
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(TeacherGUI.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });

        logoutAction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.changePanel(new LoginGUI());
            }
        });

        userGuideAction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String htmlFilePath = "userguide/index.html";
                File htmlFile = new File(htmlFilePath);
                try {
                    Desktop.getDesktop().browse(htmlFile.toURI());
                } catch (IOException ex) {
                    Logger.getLogger(AccountsGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    Desktop.getDesktop().open(htmlFile);
                } catch (IOException ex) {
                    Logger.getLogger(AccountsGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });


        //add shorcut
        addAction.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, KeyEvent.CTRL_MASK));
        editAction.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, KeyEvent.CTRL_MASK));
        deleteAction.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0));
        deleteAllAction.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, KeyEvent.CTRL_MASK));
        logoutAction.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, KeyEvent.CTRL_MASK));
        exitAction.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, KeyEvent.CTRL_MASK));
        switchViewAction.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_MASK));
        aboutAction.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
        userGuideAction.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0));

        //Combine Menu
        toolMenu.add(addAction);
        toolMenu.add(editAction);
        toolMenu.add(activeAction);
        toolMenu.add(deleteAction);
        toolMenu.add(deleteAllAction);
        fileMenu.add(logoutAction);
        fileMenu.add(exitAction);
        viewMenu.add(switchViewAction);
        helpMenu.add(aboutAction);
        helpMenu.add(userGuideAction);


        menuBar.add(fileMenu);
        menuBar.add(toolMenu);
        menuBar.add(viewMenu);
        menuBar.add(helpMenu);

        menuBar.setOpaque(false);


        return menuBar;
    }

    private CPanel getContentPanel() {
//        contentPanel = new CPanel(new GridLayout(1, 1));
        contentPanel = new CPanel(new GridLayout(1, 1));
        smallPanel = new CPanel(new BorderLayout());

        accountPanel = getAccountPanel();
        botPanel = getBotPanel();
        smallPanel.add(accountPanel, BorderLayout.CENTER);
        smallPanel.add(botPanel, BorderLayout.SOUTH);

        contentPanel.add(smallPanel);
        contentPanel.updateUI();


        return contentPanel;

    }

    private CPanel getAccountPanel() {
        int a;
        accountPanel = new CPanel(new GridLayout(10, 1));

        if (index < getTotalIndex()) {
            a = 9;
        } else {
            a = model.getAccountList().size() % 9;
        }

        for (int i = 0; i < a; i++) {
            System.out.println("a");
            accountPanel.add(getAccountDetailPanel(model.getAccountList().get(((index) * 9) + i)));

        }

        accountPanel.updateUI();

        return accountPanel;
    }

    private CPanel getAccountDetailPanel(final Account a) {
//        final CPanel itemPanel = new CPanel(new GridLayout(2, 3), new Color(255, 255, 255, 28));
        final CPanel mPanel = new CPanel(new BorderLayout(), ITEM_PANEL_PIC);

        CPanel picPanel = new CPanel(new ImageIcon("Images/" + a.getPic()));
        picPanel.setBorder(new EmptyBorder(0, 0, 0, BORDER_LEFT));

        mPanel.add(picPanel, BorderLayout.WEST);

        final CPanel itemPanel = new CPanel(new GridLayout(2, 3));
        mPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (mPanel.isIsSelected() == false) {
                    mPanel.setImage(SELECTED_ITEM_PANEL_PIC);
//                    itemPanel.setBackground(Color.LIGHT_GRAY);
                    mPanel.updateUI();
                    selectedItems.add(a);
                    mPanel.setIsSelected(true);
                    checkButton();
                } else {
//                  itemPanel.setBackground(new Color(255, 255, 255, 28));
                    mPanel.setImage(ITEM_PANEL_PIC);
                    mPanel.updateUI();
                    selectedItems.remove(a);
                    mPanel.setIsSelected(false);
                    checkButton();
                }
            }
        });

        itemPanel.add(new CLabel("ID: " + a.getId(), TEXT_SIZE));
        itemPanel.add(new CLabel(resourse.getString("USERNAME_TXT")+": " + a.getUserName(), TEXT_SIZE));
        itemPanel.add(new CLabel(resourse.getString("EMAIL_TXT")+": " + a.getEmail(), TEXT_SIZE));
        itemPanel.add(new CLabel(resourse.getString("PHONE_TXT")+": " + a.getPhoneNumber(), TEXT_SIZE));
        itemPanel.add(new CLabel(resourse.getString("ROLE_TXT")+": " + ROLE[a.getRole()], TEXT_SIZE));
        itemPanel.add(new CLabel(resourse.getString("ACTIVATION_TXT")+": " + a.isActive(), TEXT_SIZE));

        itemPanel.updateUI();

        mPanel.add(itemPanel);

        return mPanel;
    }

    private CPanel getBotPanel() {
        botPanel = new CPanel(new BorderLayout());

        CPanel p = new CPanel();

        model.getAccountList().size();
        for (int i = 0; i <= getTotalIndex(); i++) {
            p.add(new CButton(i, index, this));
        }
        botPanel.add(p, BorderLayout.CENTER);
        return botPanel;
    }

    private int getTotalIndex() {
        int a, b, c, d = 0;
        a = model.getAccountList().size();
        b = 9;
        if (a > 9) {
            c = a % b;
            d = (a - c) / b;
        }
        return d;
    }

    private boolean isUser() {


        for (int i = 0; i < selectedItems.size(); i++) {

            if (selectedItems.get(i).getUserName().equals(username) && (selectedItems.size() == 1)) {
                return true;
            }
        }

        return false;
    }

    private void checkButton() {

        if (model.getAccountList().isEmpty()) {
            editBt.setEnabled(false);
            deleteBt.setEnabled(false);
            deleteAllBt.setEnabled(false);
            activeBt.setEnabled(false);
            editAction.setEnabled(false);
            deleteAction.setEnabled(false);
            deleteAllAction.setEnabled(false);
            activeAction.setEnabled(false);
        } else if (selectedItems.size() == 1) {
            editBt.setEnabled(true);
            deleteBt.setEnabled(true);
            deleteAllBt.setEnabled(true);
            activeBt.setEnabled(true);
            editAction.setEnabled(true);
            deleteAction.setEnabled(true);
            deleteAllAction.setEnabled(true);
            activeAction.setEnabled(true);
        } else if (selectedItems.size() > 1) {
            editBt.setEnabled(false);
            deleteBt.setEnabled(true);
            deleteAllBt.setEnabled(true);
            activeBt.setEnabled(true);
            editAction.setEnabled(false);
            deleteAction.setEnabled(true);
            deleteAllAction.setEnabled(true);
            activeAction.setEnabled(true);
        } else if (selectedItems.isEmpty()) {
            editBt.setEnabled(false);
            deleteBt.setEnabled(false);
            deleteAllBt.setEnabled(true);
            activeBt.setEnabled(false);
            editAction.setEnabled(false);
            deleteAction.setEnabled(false);
            deleteAllAction.setEnabled(true);
            activeAction.setEnabled(false);
        }

        if (isUser()) {
            editBt.setEnabled(true);
            deleteBt.setEnabled(false);
            deleteAllBt.setEnabled(true);
            activeBt.setEnabled(false);
            editAction.setEnabled(true);
            deleteAction.setEnabled(false);
            deleteAllAction.setEnabled(true);
            activeAction.setEnabled(false);
        }

        int count = 0;

        for (int i = 0; i < selectedItems.size(); i++) {
            if (selectedItems.get(i).isActive()) {
                count++;
            }
        }

        if (count != selectedItems.size()) {
            activeBt.enable(false);
            activeAction.enable(false);
        } else {
            activeBt.enable(true);
            activeAction.enable(true);

            if (!selectedItems.isEmpty()) {

                if (selectedItems.get(0).isActive() == ACTIVE) {
//                    activeBt.setIcon(DEACTIVE_BUTTON);
                } else {
//                    activeBt.setIcon(ACTIVE_BUTTON);
                }
            }
        }


    }

    public static void setIndex(int index) {
        AccountsGUI.index = index;
    }

    public void updateAccountPanel() {
        selectedItems.clear();
        smallPanel.removeAll();
        smallPanel.updateUI();
        accountPanel = getAccountPanel();
        botPanel = getBotPanel();
        smallPanel.add(accountPanel, BorderLayout.CENTER);
        smallPanel.add(botPanel, BorderLayout.SOUTH);

    }

    private int getDialogConfirmation(String message) {
        int choose = JOptionPane.showConfirmDialog(null, message, "Information", JOptionPane.YES_NO_OPTION);
        return choose;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(PANEL_WIDTH, PANEL_HEIGHT);
    }

    private AccountsGUI getThis() {
        return this;
    }
}
