package screens.server;

import controllers.server.ConnectedPlayersController;
import entities.Player;
import screens.controls.IMainFrame;
import screens.controls.ImagePanel;
import views.server.ConnectedPlayersView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

public class ConnectedPlayersScreen implements ConnectedPlayersView {

    private static final String BG_IMAGE = "images/connected.jpg";
    private final IMainFrame mainFrame;
    private final ConnectedPlayersController controller;
    private ImagePanel panel;
    private DefaultListModel<String> playersNameList = new DefaultListModel<String>();
    private JList playersName = new JList(playersNameList);
    private DefaultListModel<String> playersRoleList = new DefaultListModel<String>();
    private JList playersRole = new JList(playersRoleList);
    private JButton continueButton;
    private JButton exit;
    private JLabel playerNames;
    private JLabel role;


    public ConnectedPlayersScreen(IMainFrame mainFrame, ConnectedPlayersController controller) {
        this.mainFrame = mainFrame;
        this.controller = controller;
        panel = mainFrame.createImagePanel(BG_IMAGE);
        playersName = createList(playersName, 50, 100);
        playersRole = createList(playersRole, 200, 100);
        continueButton = createButton(400, 100, "Continue");
        exit = createButton(400, 200, "Cancel");
        playerNames = createLabel("Player Name", 50, -125, 150, 400);
        role = createLabel("Role", 250, -120, 150, 400);

        panel.add(continueButton);
        panel.add(playersName);
        panel.add(playersRole);
        panel.add(exit);
        panel.add(playerNames);
        panel.add(role);

        addListener();
        mainFrame.setSize(600, 600);
        setDefaultCloseAction(mainFrame, controller);
        panel.repaint();
    }

    private void setDefaultCloseAction(IMainFrame mainFrame, final ConnectedPlayersController controller) {
        mainFrame.getFrame().addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                controller.close();
            }
        });
    }

    private void addListener() {
        continueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.Continue();
            }
        });
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.close();
            }
        });
    }

    private JList createList(JList playersList, int x_bound, int y_bound) {

        playersList.setSize(150, 450);
        playersList.setLocation(x_bound, y_bound);
        playersList.setBackground(Color.ORANGE);
        playersList.setForeground(Color.BLACK);
        playersList.setBorder(BorderFactory.createLineBorder(SystemColor.BLACK));
        Font f = new Font("Monospaced", Font.BOLD, 20);
        playersList.setFont(f);
        return playersList;
    }

    private JLabel createLabel(String labelName, int xBound, int yBound, int xSize, int ySize) {
        JLabel label = new JLabel(labelName);
        label.setFont(new Font("Monospaced", Font.BOLD, 20));
        label.setForeground(Color.WHITE);
        label.setSize(xSize, ySize);
        label.setLocation(xBound, yBound);
        return label;
    }

    @Override
    public void display(List<Player> players) {
        for (Player player : players) {
            playersNameList.addElement(player.getName());
            playersRoleList.addElement(player.getRole());
        }
    }

    private JButton createButton(int x_axis, int y_axis, String buttonName) {
        JButton button = new JButton(buttonName);
        button.setSize(145, 50);
        button.setLocation(x_axis, y_axis);
        button.setFont(new Font("Verdana", Font.BOLD, 16));
        return button;
    }

}
