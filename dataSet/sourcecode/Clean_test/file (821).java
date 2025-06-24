package ProjectInformaticaPackage;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class CreateNewWorld extends JFrame { 
	Image imageCreation;
	JTextField numberPredators = new JTextField(25);
	JTextField numberHerbivores = new JTextField(25);
	JTextField numberGrass=new JTextField(25);
	JLabel labelPredator=new JLabel("���������� ��������:");
	JLabel labelHerbivore=new JLabel("���������� ����������:");
	JLabel labelGrass=new JLabel("���������� �����:");
	JButton cansel=new JButton("�������");
	JButton enter=new JButton("������");
	//JButton option=new JButton("�������������..");
	
	int intNumberPredators;
	int intNumberHerbivores;
	int intNumberGrass;
	
		public CreateNewWorld() {
			
			try {
				imageCreation=ImageIO.read(new File("Textures/Creation.jpg"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			this.setIconImage(imageCreation);
			this.setTitle("�������� ����� ����������");
			this.setSize(300,180);
			setLayout(null); 
			add(numberPredators);
			add(numberHerbivores);
			add(numberGrass);
			add(labelPredator);
			add(labelHerbivore);
			add(labelGrass);
			add(cansel);
			add(enter);
			//add(option);
			
			//������������ ��������� � ����
			labelPredator.setBounds(5,5,150,20);
			labelHerbivore.setBounds(5, 30, 150, 20);
			labelGrass.setBounds(5,55,150, 20);
			numberPredators.setBounds(180, 5, 90, 20);
			numberHerbivores.setBounds(180,30,90,20);
			numberGrass.setBounds(180,55,90,20);
			//option.setBounds(5, 100, 150, 20);
			enter.setBounds(170, 100, 100, 20);
			
			//�������������� ��������
			numberPredators.setText("5");
			numberHerbivores.setText("5");
			numberGrass.setText("70");
			
			CreationAction creationNewWorld=new CreationAction(this);
			enter.addActionListener(creationNewWorld);
			
		}
	
}

class CreationAction implements ActionListener {
	int numberOfPredators;
	int numberOfHerbivores;
	int numberOfGrass;
	CreateNewWorld  a;
	public CreationAction(CreateNewWorld d) {
		a=d;
	}
	public void actionPerformed(ActionEvent event) {
		
		//������������� ���������
		Information.setWorldCreated(false);
		
		//�������� ������ � Information
		Information.setAmountOfPredators((Integer.parseInt(a.numberPredators.getText())));
		Information.setAmountOfHerbivores((Integer.parseInt(a.numberHerbivores.getText())));
		Information.setAmountOfGrass((Integer.parseInt(a.numberGrass.getText())));
		
		//Information ���������� ���������
		Information.readPredatorsFromConsole();
		Information.readHerbivoreFromConsole();
		Information.readGrassFromConsole();
		
		//��������� ����
		a.setVisible(false);
		
		//�������, ��� ��� �����������
		Information.setWorldCreated(true);
	}
}

class WriteTextAction implements MouseListener {
	
	public void mouseClicked(MouseEvent e) {
		
	}
	public void mouseEntered(MouseEvent e) {
		
	}
	public void mouseExited(MouseEvent e) {
		
	}
	public void mousePressed(MouseEvent e) {
		
	}
	public void mouseReleased(MouseEvent e) {
		
	}
	
}
