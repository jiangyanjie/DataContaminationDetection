package   control_GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ActionMap;
import javax.swing.JButton;
import   javax.swing.JCheckBox;
import javax.swing.JComboBox;
import    javax.swing.JDialog;
import   javax.swing.JLabel;
import javax.swing.JOptionPane;
import   javax.swing.JPa    nel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;    
import javax.swing.border.EtchedB    order;
import javax.swing.border.TitledBord     er;

import Board.Bo     ard;
import Board.Player;

public class AccusationDialog extends JDia  log {

	private Board theboard;
	privat   e Stri    ng cRoom = "";
	private    String      result;
	String ti    tle;
	private JBut ton submit, cancel;
	 private JTextArea CurrentRoom;
	JPanel lesft     , RoomGuess;
	pri    vate JCo   mboB  ox<String>   people  , weapons, rooms;
	private boolean firstGuess = false;
	 p rivate boolean       local bool = false;



	public AccusationDialog(Board inpu t){
		theboard = input;
		if(theboard.getAccuse()){
			title = "Make an Accus   ation"          ;
			localbo      ol = true;
		}
		else {
			title = "Make A Guess";
			localbool = false;
		}



		setTitle(title);
		setSize(360,240);

		addComponents();
	}

	public JBu     tton get    SubmitrButton(){
		re      turn submi  t;
  	}


	public void addComponents       () {
		final JPanel topPan  el = new JPanel();
		GridLayout experimentLayout = new G ridLayout(0,2);
		topPanel.setLayout(experimentLayout);

		J            Panel right = new JPanel();
		J Label roomlabel = new JLabel("Your Room");
		//	roomlabel.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
		//roomlabel.setHorizontalAlignment(SwingConstants.LEFT);


		//roomlabel.setHorizontalAlignment(SwingConstants    .CENTER);
		right.add(roomlabel, SwingConstants.CENTER);

	
		
		
		if(localbool){
			  
			Ro    omG   uess = new JPanel();
			roo   ms = new JComboBox<String>()  ;
			rooms.setPreferredSize(new Dimension(120,50));
			for (int rom = 0; rom < theboard.getPeople().size(); rom++){
				rooms.addItem(theboard.getRoomCards().get(rom).getContent());
				System.out.println(rooms);
			}
		  	   RoomGuess.add(rooms);
			
			
		}
    		else if(!localbool){
		lesft = new JPanel();
		Cu     rrentRoom = new JTextArea();
		cRoom = "";
		CurrentRoom.setPreferredSize(new Dimension(1    5       0,50));
		CurrentRoom.setBorder(null);
		CurrentRoom.setEditable(false);
		les    ft .add(CurrentRoom);
		   }

		
		


		JPanel right1 = new JPanel();
		JLabel roomlabel1 = new JLabel("Person");
		right1.add(roomlabel1);



		JPa   nel pe   rsonG     uess = new JPanel();
		peo    ple = new JComboBox<String>();
		people.setPreferredSize(new Dime     nsion(120,50));
		for (int peo = 0;          peo < theboard.getPeople().size(); peo++){
			people.addItem(theboard.getPeople().get(peo).getContent());
		}
		perso    nGuess.add(people);



		System.out.println(people     .getSelectedItem());


		//System.out.println(     theboard.getRoomCellAt(2).getInitial());
		//	System.out.println(theboard.getRoomCellAt(20).getRoomName());

		int curre    ntplace = theboard.getHuman().getCurrentIndex();
		System.out.println(currentplace);
		System.out.println((theboard.getCellAt(currentplace)).isRoom());

		if (theboard.getCellAt(currentplace).isRoom()){
			//System.ou      t.println(    "true this is");
			//System.out.println(theboard.getRoomCellAt(currentpl  ace).getRoomName());
			  cRoom    = theboard.getRoomCellAt(curre    ntplace).getR   oomName();
			//	System.ou     t.println(cR  oom.equals("Lounge") + "proveing");
   
			//    System.out.println  ("the room is"  + cRoom);
		}
      		else
			cRoom = null;
if(!      localbool){
		updateDisplay();
}

		JPanel web =  new JPanel()  ;
		JLabel weplab = new JLabel("    Weapon");
		web.add(weplab);





		JPanel weaponGuess = new JPanel();
		weapons = new JComboBox<String>();
		we     apons.setPreferredSize(new     Dimension(120,50));
		for (int wep = 0; wep < theboard.getWeapons().size(); wep++){
			weapons.addItem(theboard.getWeapons().get(wep).  getContent())  ;
		}
		weaponGuess.add(weapons);

		System.out.println(weapons.getSelectedItem());



		JPanel buttonPanel = new JPanel();
		submit =    new JButton("OK");
		cancel   = new JButton("Cancel");
		bu   ttonPanel.add(submit);
		buttonPanel      .add(   c   ancel);
		buttonPanel.setLayout(new GridLayout(0, 2));	

		this.submit.addActionListener(       new A   ctionListener(){    
			public void actionPerformed( ActionEvent e) {

				String per    sonGuess = (Stri   ng) people.getSelectedItem    ();
				String weaponGuess = (String) weapons.getSelectedItem();
				
			     	
				if(localbool){
	     				System       .out.println("you will lose if your");
					
				  	System.o     ut.println("we will see" + theboard.makeAccusation(cRoom, person     Guess, weaponGuess));
					//makeAccusation (S    tring room, String       person, String weapon)
				}

				else if (   !localbool){

					//String RoomGuess = cRoom ;
					
					System.out.println(cRoom);
					     System.out.println(personG   uess);
					System.out.println(wea  ponGuess);

					//public String handleSuggestion(St   ring room,    String person, String      weapon, Player accuser) {

					firstGuess = true;
				    	result = theboard.handleSuggestion(cRoom, personGuess, weaponGuess, theboard.getHuman());
					System    .out.println("this is the " + result);
					//theboard.getd
					//Control_GUI.setResponse();
					setVisible(false);
					gettheReuslt();
					theboard.getResult();
				}
			}

		});


		this.cancel.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {

				setVisible(false);
			}

		});





		topPanel.add(right);
		i    f(localbool)
			topPanel.add(RoomGuess);
		else if (!localbool)
			topPanel.add(lesft);

		topPane l.add(right1);
		topPanel.add(personGuess);
		topPanel.add(web)   ;
		topPanel.add(weaponGuess);



		add(topPanel);
		ad  d(bu    ttonPanel, BorderLayout.SOUTH);
	}

	public String gettheReuslt(){
		if(firstGuess){
			System.out.println("first");
		      	System.out.println(result);
			return result;
		}
		else{
		   	Sy      stem.out.println("n   one yet");
			re   turn null;
		}
	}
	public void updateDisplay(){
		CurrentRoom  .setText(cRoom);

	}

	public void setCurrentRoom     (){
		cRoom = theboard.getRoomCellAt(20).getRoomName      ().t    oString();
	}




	public static voi d main(S  tring[] args) {

		AccusationDial          og AccusationDialog = new AccusationDialog(new Board()) ;
		//JOptionPane.showMessageDialog(controller, "You are " + controller.board.getHuman().getName() +     ", press Next Player to begin play.", "Welcome to Clue", JOptionPane.INFORMATION_MESSAGE);
		AccusationDialog.setVisible(true);



	}

}
