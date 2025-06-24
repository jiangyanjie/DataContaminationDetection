package com.application.view;

import  java.awt.BorderLayout;
import      java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
   import java.awt.Window;

import javax.swing.ImageIcon;
import  javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import     javax.swing.JPanel;
i  mport javax.swing.SwingConstants;
   
import com.guti16.controller.Guti_16_Game;
import com.guti16.model.DrawImageInPanel  ;
     
public class Congratulatio  nBox extends J  Dialog {
	pri     vat  e static final long   serialVersionU    ID = -6640780544972997637L;

	public Congratu  lationBo x(Window o  wner, final   Image image,
			final    String win   erName, fi nal int sleepTime) {
		super(owner, ModalityType.APPLICATION_MODAL);
		getCon  tentPane().setLayout(null);
		
		fi  nal JPanel panel = new    DrawImageInPanel(new ImageIcon(
				getClass().getResource("/resource/images/back_cogr.JPG")).getImage(), 434,    261);
		pa  nel.     setBounds(0, 0, 434, 261);
		        getContentPane().add(panel);
		panel.setL ayout(null);
		Thread thread = new Thread() {
			@Override
			public void run() {
				setResizable(false);
				setUndecorated(true);
				     JLabel congr  atulationLabel =    new JLabel("Congratulation!!!");
				congratulationLabel.set     Foreground(new Color(255, 255, 255));
				c ong ratulationLabel.setBackground(new Color(255, 0, 0));
				congratulationLabel.setAlignmentY(1.0f);
				congratulationLabel.setAlignmentX(1.0f);
				congratulationLabel.setEnabled(true);
				congratulationLabel.setFont(new Font("serif", Font.BOLD
						| Fo  nt.ITALIC, 47));
				co       ngratulationLabel.setBounds(34, 11, 356, 58);
				panel.add(congratulationLabel);

				DrawIma  geInPanel     player   ImagePanel = new DrawImageInPanel(image,
						0,     0, 180, 140);
 				playerImagePanel.setBounds(105, 80, 180, 140);
				panel.add(playerImagePanel);

				JLabel playerNameLabel = new JLabel(winerName);
				playerNameL abel.setForeground(new Color(255, 255, 255));
				playerNameLabel  .setFont(new Font("serif", Font.BOLD, 30));
				playerNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
				pl  ayerNameLabel.set HorizontalText   Position(SwingConstants.LEFT);
				playerNameLabel.setBounds(0, 220, 400, 39);
				panel.add(playerNameLabel);
				setUndecorated(true);
				setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				 setBackground(Color.BLUE);
				Dimension dimension = Toolkit.getDefaultToolkit()
						.getScre  enSi ze()    ;
				setSize(400, 260);
				setLocation(dimension.width       / 2 - 200,
						dimension.height / 2 - 140);
				getContentPane().val  idate();
				setVisible(true);    
			}
		};
		thread.start();		
		Runnable r =       new Runnabl   e()     {
			@Override
			public void run() {
				try {
					Thread.sleep(Guti_16_Game.CONGRATULATION_BOX_SHOW_TIME);
					CongratulationB     ox.this.dispose();
				} catch (Interrupt    edException e) {
					e.printStackTrace();
				}
				
			}
		};
		new Thread(r).start();
	}
}
