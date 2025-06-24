import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.geom.*;
// import java.util.*;

public class Connect4 {

   public static void main(String[] args) {
       Host host = new Host();
       host.start();
   }
}

/* To run the tournament, we'll comment out the strawmen players.
 */
/*
class Player1 implements Player {

    public int play(Board board, int opponentsPlay, Timer ignored) {
        java.util.Random rand = new java.util.Random();

        // Generate a random legal move.
        //
        int m = rand.nextInt(Util.BOARDSIZE);
        while (!board.isLegalPlay(m)) m = rand.nextInt(Util.BOARDSIZE);

        if(Control.DEBUG)
            System.out.println("player1.play: making move = " + m);

        return m;
    }

    public String teamName() { return "Bulldogs"; }
}

class Player2 implements Player {

    public int play(Board board, int opponentsPlay, Timer ignored) {
        java.util.Random rand = new java.util.Random();

        // Generate a random legal move.
        //
        int m = rand.nextInt(Util.BOARDSIZE);
        while (!board.isLegalPlay(m)) m = rand.nextInt(Util.BOARDSIZE);

        if(Control.DEBUG)
            System.out.println("player2.play: making move = " + m);

        return m;
    }
    public String teamName() { return "Terriers"; }
}*/


class Connect4Frame extends JFrame {
    private int boardSize;
    private Display mainDisplay;

    public Connect4Frame(int size, Host.Listeners listeners, 
                         String team1Name, String team2Name) {
        this.boardSize = size;
        setTitle("CS102 Connect4++ Tournament");
        setSize(600, 500);

        mainDisplay = new Display(boardSize, listeners, team1Name, team2Name);

        Container contentPane = getContentPane();
        contentPane.add(mainDisplay);
    }

    // Getter
    //
    public int getBoardSize() { return boardSize; }
    public Display getDisplay() { return mainDisplay; }
}

class Display extends JPanel {

    // Instance variables
    //
    private int boardSize;
    private ControlDisplay controlDisplay;
    private BoardDisplay boardDisplay;
    private Host.Listeners listeners;
    
    public Display(int bs, Host.Listeners ls,String team1Name, String team2Name) {
        boardSize = bs;
        listeners = ls;

        setLayout(new BorderLayout());

        controlDisplay = new ControlDisplay(this,listeners,team1Name,team2Name);
        boardDisplay = new BoardDisplay(this);

        add(controlDisplay,BorderLayout.SOUTH);
        add(boardDisplay,BorderLayout.CENTER);
    }

    // Getters
    //
    public int getBoardSize() { return boardSize; }
    public ControlDisplay getControlDisplay() { return controlDisplay; }
    public BoardDisplay getBoardDisplay() { return boardDisplay; }

    // If you've got your hands on the main display, you can feed
    // this method a board to display.
    //
    public void displayBoard(Board board) {
        // Remove all of the column panels from the board.
        //
        boardDisplay.removeAll();

        int[][] boardArray = board.getArray();

        for (int i = 0; i < boardSize; i++) {

            Box columnPanel = Box.createVerticalBox();

            JButton button = new JButton("Drop" + (i + 1));

            button.addActionListener(listeners.getButtonListener(i));

            columnPanel.add(button);

            for (int j = 0; j < boardSize; j++) {
                Disk disk = new DiskC(boardArray[j][i]);
                JPanel diskPanel = disk.getDisplayPanel();
                columnPanel.add(diskPanel);
            }
            boardDisplay.add(columnPanel);
        }

	// Repaint the whole display.
	//
	validate();
    }
}

class BoardDisplay extends Box {
    private Display mainDisplayPanel;

    public BoardDisplay() { super(BoxLayout.X_AXIS); }

    public BoardDisplay(Display display) {
        super(BoxLayout.X_AXIS);
        mainDisplayPanel = display;
    }
}

class ControlDisplay extends Box {
    private JButton stepButton;

    private JTextField team1FourCount;
    private JTextField team1GameCount;

    private JTextField team2FourCount;
    private JTextField team2GameCount;

    private Display mainDisplayPanel;

    public ControlDisplay(Display display, Host.Listeners listeners, String team1Name, String team2Name) {
        super(BoxLayout.X_AXIS);

        mainDisplayPanel = display;

        JPanel buttonControlPanel = new JPanel();

        stepButton = new JButton("Step");
        stepButton.addActionListener(listeners.getStepListener());

        JButton goButton = new JButton("Go");
        goButton.addActionListener(listeners.getGoListener());
                
        JButton matchPlayButton = new JButton("Match Play");
        matchPlayButton.addActionListener(listeners.getMatchPlayListener());

        buttonControlPanel.add(matchPlayButton);
        buttonControlPanel.add(stepButton);
        buttonControlPanel.add(goButton);

        JPanel sliderControlPanel = new JPanel();
        //		JSlider delay = new JSlider(0,100,0);
        JSlider delay = new JSlider(JSlider.HORIZONTAL, 0, 30, 15);

        delay.addChangeListener(listeners.getDelayListener());
        sliderControlPanel.add(delay);

        Box leftControlPanel = Box.createVerticalBox();
        leftControlPanel.add(buttonControlPanel);
        leftControlPanel.add(sliderControlPanel);

        JPanel rightControlPanel = new JPanel();

        rightControlPanel.setLayout(new GridLayout(2,1));

        team1FourCount = new JTextField("0");
        team1GameCount = new JTextField("0");

        team2FourCount = new JTextField("0");
        team2GameCount = new JTextField("0");

        JPanel player1ControlPanel = 
            makePlayerControlPanel(team1Name,team1FourCount,team1GameCount);
        JPanel player2ControlPanel = 
            makePlayerControlPanel(team2Name,team2FourCount,team2GameCount);

        rightControlPanel.setLayout(new GridLayout(1,2));

        rightControlPanel.add(player1ControlPanel);
        rightControlPanel.add(player2ControlPanel);

        add(leftControlPanel);
        add(rightControlPanel);
    }

    public void setFourScores(Score fs) {
        Integer p1 = new Integer(fs.getPlayer1());
        Integer p2 = new Integer(fs.getPlayer2());

        team1FourCount.setText(p1.toString());
        team2FourCount.setText(p2.toString());
    }

    public void setMatchScores(Score gs) {
        Integer p1 = new Integer(gs.getPlayer1());
        Integer p2 = new Integer(gs.getPlayer2());

        team1GameCount.setText(p1.toString());
        team2GameCount.setText(p2.toString());
    }

    public JPanel makePlayerControlPanel(String teamName, JTextField fc, JTextField gc) {
        JPanel outer = new JPanel();

        outer.setLayout(new GridLayout(2,1));

        outer.add(new JLabel(teamName));

        JPanel pe1 = makeControlPanelEntry("Fours",fc);
        JPanel pe2 = makeControlPanelEntry("Games",gc);

        JPanel inner = new JPanel();
        inner.setLayout(new GridLayout(1,2));

        inner.add(pe1);
        inner.add(pe2);
        
        outer.add(inner);

        return outer;
    }

    public JPanel makeControlPanelEntry(String fieldName, JTextField tf) {
        JPanel jp = new JPanel();

        jp.setLayout(new GridLayout(2,1));

        JLabel l = new JLabel(fieldName);

        jp.add(l);
        jp.add(tf);

        return jp;
    }
}

class FillPanel extends JPanel
{
	private Color color;

	public FillPanel(Color c)
	{
		color = c;
	}

   public void paintComponent(Graphics g)
   {
      super.paintComponent(g);
      Graphics2D g2 = (Graphics2D)g;

      // draw a rectangle

      double leftX = 0; // 100
      double topY = 0;  // 100
      double width = 50;
      double height = 50;

      Rectangle2D rect = new Rectangle2D.Double(leftX, topY, width, height);
      g2.setPaint(Util.emptyCellColor);
      g2.fill(rect);

      // draw the enclosed ellipse

      Ellipse2D ellipse = new Ellipse2D.Double();
      ellipse.setFrame(rect);
      g2.setPaint(color); // new Color(0,  128, 128)); // a dull blue-green
      g2.fill(ellipse);
   }
}


class ColumnOverFlowException extends RuntimeException {}

class ColumnUnderFlowException extends RuntimeException {}

class RanOutOfTimeException extends RuntimeException {}
