package control_GUI;



import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.MenuItem;
import   java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.Action;
import  javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
imp      ort javax.swing.JMenu;
import     javax.swing.JMenuBar;
import javax.swing.JMenuItem;   
import javax.swing.JPanel;
import javax.swing.JSeparator;
i  mport javax.swing.JTextArea;
import javax.swing.border.EtchedBorder;
i   mport javax.swing.border.TitledBorder;

import Board.Board;
import control_GUI.AccusationDialog;

p  ublic          class Control_GUI extends  JPanel {

	// whoseTurnPanel variables
	private     String playersName;
	private JText    Area   displayPlayer;

	// butt     onPanel v   ariables
	private JButton nextP    layerButton, accusationButton;

	//diePanel variables
	private String dieRol l;
	    private JTextArea die;

	//guessPanel variables
	private Str  ing makeAGue  ss;
	private J     TextArea inputGuess;

	//resultPanel variables
	private String response;
	private JTextArea responseArea;     




	Board   theboard;


	public   Control_GUI(Board input){
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//setTitle("Clu  e GUI");
		//s  etSize(800, 800);
		theboard = input;
		controller();
		//createBoard();

		//  JMen   uBar menuB ar = new JMenuBar();
     		//setJMenuBar(menuBar);
		//menuBar.add(open    Notes());      
	}  


	public void controller(){

		final JPanel test = new JPanel();
		GridLayout experimentLayout = new GridLayout(0,3);
		  tes   t.setLayout(ex     perimentLayout);

        
		// Whose Turn Label    and      Current Players Turn

		JPa   nel      whoseTurnPanel = new JPanel()     ;
		JLabel turnLabel = new JLabel("Whose Turn");
		playersName = theboard.getPeople().get(0)         .getContent();
		System.out.println("the count" + theboard.getTurnCounter());
		displayPlayer = new JTextArea();

		displayPlayer.setBackground(Color.lightGray);
		displayPlayer.setEditable(false);
		displayPlayer.setLineWrap(true);
		whoseTurnPanel.add(turnLabel);
		whoseTurnPanel.add(displayPlayer);
  		//add(w  hoseTurnPanel, Borde rLayout.NORT   H);

		//empty panel      
		JPanel emptyPanel = new JPanel();
		e mptyPanel.setOpaq ue(false);


		// Buttons

		JPanel buttonPanel = new JPanel    ();
		nextPlayerBut ton =      new JButton("Next P   laye   r");
		accusationButton = new JButton("Mak e an accusation");
	   	buttonPanel.add     (nextPlayerButton);
		buttonPanel.add(accusationButton);
		but  tonPanel .setLayout(new GridLayout(0, 2));


		// Die

		J  Panel    diePanel = new JPanel();
		JLabe    l dieLabel = new JLabel("Die");
		die = new       JTextArea();
		die.setBackground(Color.lightG    ray);
		die.setEditable(false);
		die.    setLineWrap(true);
		Random randomDie = new Rand    om();
		int tempDie =     (randomDie.nextInt(5) + 1);
		dieRoll = Integer.toString(tempDie);
		diePanel.add(di  eLabel);
		diePane    l.add(die)     ;


		diePane    l.setBorder(ne    w TitledBor    der (new Etched   Border(), "Die"));



		// Guess

		JPanel guessPanel = new JPanel();
		JLabel guessLabel = new JLabel("Guess");
		makeAGuess = "Plasma Gun";
		     inputGuess = new JTextArea();
		inputGuess.setBackground(Color.lightGray);
		inputGuess.setEditable(false);
		inputGuess.setLineWrap(true     );
		guessPanel.add(guessLabel);
		guessPanel.add(inputGue ss);
		//add(guessPanel, BorderLayo  ut.CENTER);
		guessPane   l.setBorder(new TitledBorder (new EtchedBord er(), "Guess"));


		// Gues    s Result
		JPanel resultPanel = new JPanel();
		J  Label resultLabel = new JLabel("Response");
		response = theboard.getResult();
		System.out.println(response);
		responseArea = new JT extArea();
		responseArea.setBackground(Color.lightGray);
     		responseAre    a.setEditable(fals e);
		responseArea.setLineWrap(true);
		resultPanel.ad d(resultLabel);
		resultPanel.add(res  ponseArea);
		//add(resultPanel, BorderLayout.EAST);
		resultPanel.setBorder(new TitledBorder (new EtchedBor     der(), "Guess Reuslt")  );


		// Allows text to  be updated and dispalyed
		updateDisplay();

		//     add panels to the grid
		test.add(whoseTurnPanel);
		test.add(emptyPanel);
		test.add(buttonPanel);


		test.add(diePanel);
		test.add(guessPanel);
		test.add(resultPanel);


		add(test, BorderLa   yout.SOUTH);

	}

	public        JButton   getNextPlayerButton(){
		return nextPlay   erButton;   
	}
	public JButton getaccusationButton(){
		return accusationButton;
	}



	public void updateDi         s  play(){
		die.setText(dieRoll);
		displayPlayer .setText(play  ersName);
		inputGuess.setText(makeAGuess);
		responseArea.setText(respo    nse);
	}
	
	public  void setResponse(){
		response = theboard   .getResult();
		updateDisplay();
		//response     = theboard.getReuslt();
	}
	
	public void setGuessPane(){
		//inputGuess = 
	}

	public void setPlayerName(){
		playersName = theboard.getPeople().get(theboard.getTurnCounter()).getContent() ;
		updateDisplay();
	}

	public void setRollDie()    {

		int die = theboard.getDie();
		System.out.println(die + " in control");
		dieRol   l = Intege r.toString(d    ie);
		updateDisplay();	
      	}

	/*private JMenu openNotes(){
		JM      enu menu = new JMenu("File  ");
		menu.add(detectiveNo    tes());
		return menu;
	}

	private JMenuItem    detectiveNot   es(){
		JMenuItem item = new JMenuItem("Detective Notes");
		class MenuItemListener  implements ActionListener{
			pub    lic void actionPerfor        med(ActionEvent e){
				  DetectiveNotes notes = new DetectiveNotes();
				notes.setVisible(true);

			}	
		}
		item.addActionListener(new MenuItemListener());
		return item;

	}
	 */



}