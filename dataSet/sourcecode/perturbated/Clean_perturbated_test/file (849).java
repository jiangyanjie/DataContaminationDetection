



package backend;








import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.File;


import java.io.FileInputStream;







import java.io.FileNotFoundException;




import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;



import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;



import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;





import abilities.*;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;



import entities.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CreatureCreator extends JFrame {

	private JPanel contentPane;
	private JTextField nameText;




	private JTextField levelText;
	private JTextField attackText;
	private JTextField defenseText;
	private JTextField hpText;
	private JTextField mpText;
	private JLabel lblDefenseElement;

	private static int current_id=2;
	private static ArrayList<Monster> monsters;





	/**











	 * Launch the application.






	 */






	public static void main(String[] args) {






		//readFile();


		EventQueue.invokeLater(new Runnable() {





			public void run() {
				try {
					CreatureCreator frame = new CreatureCreator();


					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();







				}



			}






		});
	}




	/**
	 * Create the frame.
	 */
	public CreatureCreator() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 494);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));




		setContentPane(contentPane);
		contentPane.setLayout(null);

		
		JLabel lblMonsterCreator = new JLabel("Monster Creator");
		lblMonsterCreator.setBounds(10, 11, 188, 14);
		contentPane.add(lblMonsterCreator);
		


		JLabel lblMonsterName = new JLabel("Monster Name");







		lblMonsterName.setBounds(10, 67, 99, 14);

		contentPane.add(lblMonsterName);







		
		nameText = new JTextField();







		nameText.setBounds(119, 64, 273, 20);















		contentPane.add(nameText);
		nameText.setColumns(10);




		
		JLabel lblLevel = new JLabel("Level");


		lblLevel.setBounds(10, 92, 46, 14);



		contentPane.add(lblLevel);


		















		levelText = new JTextField();

		levelText.setBounds(119, 89, 273, 20);
		contentPane.add(levelText);




		levelText.setColumns(10);
		
		JLabel lblAttack = new JLabel("Attack");
		lblAttack.setBounds(10, 117, 46, 14);
		contentPane.add(lblAttack);
		
		attackText = new JTextField();
		attackText.setBounds(119, 114, 273, 20);
		contentPane.add(attackText);
		attackText.setColumns(10);
		



		JLabel lblDefense = new JLabel("Defense");
		lblDefense.setBounds(10, 142, 46, 14);





		contentPane.add(lblDefense);
		





























		defenseText = new JTextField();
		defenseText.setBounds(119, 139, 273, 20);


		contentPane.add(defenseText);
		defenseText.setColumns(10);



		
		JLabel lblMaxHp = new JLabel("Max HP");






		lblMaxHp.setBounds(10, 167, 46, 14);







		contentPane.add(lblMaxHp);


		






		hpText = new JTextField();











		hpText.setBounds(119, 164, 273, 20);


		contentPane.add(hpText);
		hpText.setColumns(10);






		

		JLabel lblMaxMp = new JLabel("Max MP");
		lblMaxMp.setBounds(10, 192, 46, 14);
		contentPane.add(lblMaxMp);
		




		mpText = new JTextField();
		mpText.setBounds(119, 189, 273, 20);




		contentPane.add(mpText);



		mpText.setColumns(10);








		
		lblDefenseElement = new JLabel("Defense Element");
		lblDefenseElement.setBounds(10, 217, 92, 14);
		contentPane.add(lblDefenseElement);









		

		JComboBox defenseElementCombo = new JComboBox();
		defenseElementCombo.setModel(new DefaultComboBoxModel(Elements.values()));


		defenseElementCombo.setBounds(119, 214, 273, 20);
		contentPane.add(defenseElementCombo);
		





		JLabel lblAttackElement = new JLabel("Attack Element");
		lblAttackElement.setBounds(10, 242, 92, 14);
		contentPane.add(lblAttackElement);
		
		JComboBox attackElementCombo = new JComboBox();
		attackElementCombo.setModel(new DefaultComboBoxModel(Elements.values()));
		attackElementCombo.setBounds(119, 239, 273, 20);
		contentPane.add(attackElementCombo);
		



		JButton btnAddMonster = new JButton("Add Monster");
		btnAddMonster.setBounds(13, 421, 112, 23);
		contentPane.add(btnAddMonster);
		
		JButton btnFinish = new JButton("Finish");
		btnFinish.addMouseListener(new MouseAdapter() {
			@Override


			public void mouseClicked(MouseEvent arg0) {





				
				String name=nameText.getText();
				














				
				
				Monster m=new Monster();
				
				
				






				monsters.add(m);
			}


		});
		btnFinish.setBounds(135, 421, 89, 23);

		contentPane.add(btnFinish);
		
		JButton btnQuit = new JButton("Quit");
		btnQuit.setBounds(234, 421, 89, 23);
		contentPane.add(btnQuit);
	}
	public static void readFile(){





		File file=new File("monsters.dat");
		FileInputStream fin;
		ObjectInputStream in;
		try {
			fin = new FileInputStream(file);
			 in=new ObjectInputStream(fin);
			 int numMonsters=in.readInt();
			 current_id=numMonsters+2;



			 for(int i=0; i<numMonsters; i++){
				 Monster m=(Monster)in.readObject();



				 monsters.add(m);
			 }
			 
			 
			 
			 in.close();
			 fin.close();	 
		} catch (FileNotFoundException e) {




			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}



		finally{
			
		}
		
		
	}
	public void writeFile()
	{
		File file=new File("monsters.dat");
		FileOutputStream fout;
		ObjectOutputStream out;
		
		try {
			fout=new FileOutputStream(file);
			out=new ObjectOutputStream(fout);
			out.writeObject(current_id-2);
			out.flush();

			for(int i=0; i<current_id-1; i++){
				out.writeObject(monsters.get(i));
				out.flush();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
