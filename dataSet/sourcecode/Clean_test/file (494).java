package control_GUI;



import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import Board.Board;
import control_GUI.AccusationDialog;

public class Control_GUI extends JPanel {

	// whoseTurnPanel variables
	private String playersName;
	private JTextArea displayPlayer;

	// buttonPanel variables
	private JButton nextPlayerButton, accusationButton;

	//diePanel variables
	private String dieRoll;
	private JTextArea die;

	//guessPanel variables
	private String makeAGuess;
	private JTextArea inputGuess;

	//resultPanel variables
	private String response;
	private JTextArea responseArea;




	Board theboard;


	public  Control_GUI(Board input){
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//setTitle("Clue GUI");
		//setSize(800, 800);
		theboard = input;
		controller();
		//createBoard();

		//JMenuBar menuBar = new JMenuBar();
		//setJMenuBar(menuBar);
		//menuBar.add(openNotes());
	}


	public void controller(){

		final JPanel test = new JPanel();
		GridLayout experimentLayout = new GridLayout(0,3);
		test.setLayout(experimentLayout);


		// Whose Turn Label and Current Players Turn

		JPanel whoseTurnPanel = new JPanel();
		JLabel turnLabel = new JLabel("Whose Turn");
		playersName = theboard.getPeople().get(0).getContent();
		System.out.println("the count" + theboard.getTurnCounter());
		displayPlayer = new JTextArea();

		displayPlayer.setBackground(Color.lightGray);
		displayPlayer.setEditable(false);
		displayPlayer.setLineWrap(true);
		whoseTurnPanel.add(turnLabel);
		whoseTurnPanel.add(displayPlayer);
		//add(whoseTurnPanel, BorderLayout.NORTH);

		//empty panel
		JPanel emptyPanel = new JPanel();
		emptyPanel.setOpaque(false);


		// Buttons

		JPanel buttonPanel = new JPanel();
		nextPlayerButton = new JButton("Next Player");
		accusationButton = new JButton("Make an accusation");
		buttonPanel.add(nextPlayerButton);
		buttonPanel.add(accusationButton);
		buttonPanel.setLayout(new GridLayout(0, 2));


		// Die

		JPanel diePanel = new JPanel();
		JLabel dieLabel = new JLabel("Die");
		die = new JTextArea();
		die.setBackground(Color.lightGray);
		die.setEditable(false);
		die.setLineWrap(true);
		Random randomDie = new Random();
		int tempDie = (randomDie.nextInt(5) + 1);
		dieRoll = Integer.toString(tempDie);
		diePanel.add(dieLabel);
		diePanel.add(die);


		diePanel.setBorder(new TitledBorder (new EtchedBorder(), "Die"));



		// Guess

		JPanel guessPanel = new JPanel();
		JLabel guessLabel = new JLabel("Guess");
		makeAGuess = "Plasma Gun";
		inputGuess = new JTextArea();
		inputGuess.setBackground(Color.lightGray);
		inputGuess.setEditable(false);
		inputGuess.setLineWrap(true);
		guessPanel.add(guessLabel);
		guessPanel.add(inputGuess);
		//add(guessPanel, BorderLayout.CENTER);
		guessPanel.setBorder(new TitledBorder (new EtchedBorder(), "Guess"));


		// Guess Result
		JPanel resultPanel = new JPanel();
		JLabel resultLabel = new JLabel("Response");
		response = theboard.getResult();
		System.out.println(response);
		responseArea = new JTextArea();
		responseArea.setBackground(Color.lightGray);
		responseArea.setEditable(false);
		responseArea.setLineWrap(true);
		resultPanel.add(resultLabel);
		resultPanel.add(responseArea);
		//add(resultPanel, BorderLayout.EAST);
		resultPanel.setBorder(new TitledBorder (new EtchedBorder(), "Guess Reuslt"));


		// Allows text to be updated and dispalyed
		updateDisplay();

		// add panels to the grid
		test.add(whoseTurnPanel);
		test.add(emptyPanel);
		test.add(buttonPanel);


		test.add(diePanel);
		test.add(guessPanel);
		test.add(resultPanel);


		add(test, BorderLayout.SOUTH);

	}

	public JButton getNextPlayerButton(){
		return nextPlayerButton;
	}
	public JButton getaccusationButton(){
		return accusationButton;
	}



	public void updateDisplay(){
		die.setText(dieRoll);
		displayPlayer.setText(playersName);
		inputGuess.setText(makeAGuess);
		responseArea.setText(response);
	}
	
	public  void setResponse(){
		response = theboard.getResult();
		updateDisplay();
		//response = theboard.getReuslt();
	}
	
	public void setGuessPane(){
		//inputGuess = 
	}

	public void setPlayerName(){
		playersName = theboard.getPeople().get(theboard.getTurnCounter()).getContent();
		updateDisplay();
	}

	public void setRollDie(){

		int die = theboard.getDie();
		System.out.println(die + " in control");
		dieRoll = Integer.toString(die);
		updateDisplay();	
	}

	/*private JMenu openNotes(){
		JMenu menu = new JMenu("File");
		menu.add(detectiveNotes());
		return menu;
	}

	private JMenuItem detectiveNotes(){
		JMenuItem item = new JMenuItem("Detective Notes");
		class MenuItemListener  implements ActionListener{
			public void actionPerformed(ActionEvent e){
				DetectiveNotes notes = new DetectiveNotes();
				notes.setVisible(true);

			}	
		}
		item.addActionListener(new MenuItemListener());
		return item;

	}
	 */



}