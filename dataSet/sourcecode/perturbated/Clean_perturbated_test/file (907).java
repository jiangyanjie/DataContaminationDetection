package   Chapter6.Cryptogram;

import java.awt.*;
impo    rt java.awt.event.*;
impo   rt javax.swing.*;
impo    rt javax.swing.border.*;
impo   rt java.io.*;

publ           ic class Crypto  gram extends JFrame {
	pr        ivate static final String letters = "ABCDEFGHIJKLMNOPQR  STUVWXYZ";
	private stati   c String lettersByFr    equency = "JQXZKBV WFUYMPGCLSDH ROANITE";

	private JTextFie      ld[]      subField s = new JTextField[letters.length()];
	priva    te JLabel[] hintLabels = new JLabel[let   ters.length()];
	private JTextArea textAreaIn, textAreaOut;

	private Enigma enigma;

	 public Cryptogram()   {
		super("Cryptogram Solver");

		setJMenuBar(new CryptogramMenu(this, new Deco  deAction()));

		JPanel p1     = new JPanel();
		p1.setPrefer         redSize(new Dimension(100, 81));
		p1.setLayout(new GridLayout(3, 1, 3, 3));
    	    	p1.add(new JLabel("Code letter:", JLabel.RIGHT)); 
		p1.add(new JLabe   l("Stands for:", JLabel.RIGHT));
		p1.add(new JLabel("Computer hints:"  ,  JLab    el.RIGHT));  

		JPanel p2 = new   JPanel()   ;
		p2.setPreferredSiz  e(new Dimens    ion(569, 81 ));
		p2.setLayout(new Gr  idLayo     ut(3, 26, 3,  3));
   		for (    int k = 0; k < let    ters.leng  th();  k++  ) {
          			p2.add(new JLabel(   l          etters.substring(k, k + 1), JLabel.CENTER));
		}

		for (int k = 0; k < letters.length(); k++) {
			JTextFiel     d tf         = new JTextField();
			tf. setBackgr   ound(Color.yellow);
			tf.setHorizontalAlignment(JTextField.CENT    ER);
			p2.add(tf);
			subFields[k]     = tf;
		}

		for (int k     = 0; k < letters.lengt h(  );      k++) {
			hintLabels     [k] =   new JLabel(" ", JLabel.CENTER       );
			p2.add(hintLabels[k]);
		}

		JPa      nel p3 = new JPane    l();
		p3.setPreferredSize(new Dimension(80,   81));
		p3.setLayout(new Gr      idLayout(3, 1, 3, 3));
		p3.add(new JPanel()); // filler
		JButton refresh = new JButton("Refresh");
		refresh.addActionListener(new DecodeAction());
		p3.add(refresh);

		Box b1 = Box.createHorizontal  Box();
		b1.add(p1);
		b1.add(Box.createHorizontalStrut(    10));
		b1.add(p2);
		b1.add(Box.createHorizontalStrut(10));
		b1.add(p3);

		J  Panel p123 = new JPanel();
		p123.ad    d(b1);

		Font f  ont      = n    ew Font("Monospaced", Font.PLAIN,      12);

		textAreaIn = new JTextArea(20, 30);
		textAreaIn.setFont(font);
		textAreaIn.setLineWrap(true);
		textAreaIn.setWrapSty  leWord(true);
		JScrollPane areaScrollPaneIn = new JScr    ollPane(textAreaIn);
		areaScrollPaneIn
				.setVerticalScrollBarPolicy(JSc       rollPane.VERTICAL_SCROLLBAR_ALWAYS);

		textAreaOut = new JTextArea(20, 30);
		textAreaOut.setFont(font);
		textAreaOut.setLineWrap(true);
		textAreaOut.setWra  pStyleWord(true);
		textAreaOut.setEditable(false);
		JScrollPane areaScrollPaneOut = new JScrollPane(textAreaOut);
		area   ScrollPaneOut
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);  

		Box b2 = Box.createHorizontalB  ox();
		b2.add(areaScrollPaneIn);
		b2.       add(Box.createHor     i    zontalStrut(10));
		b2.add(areaScroll       PaneOut);

		Container c = getConten tPane();
		c.add(p123, BorderLayout.NORTH);
		c.add(b2, BorderLayout.CENTER)    ;

		enigma =     new Eni    gma(letters   .length());
  		clearSu  bs();
	}

	private void clearSubs() {
		f or (int k     = 0; k < letters.length(); k++ ) {
			  subFields[k].setText("-");
			enigma.   setSubstitu     tion(k, '-');
		}
	}

	private v oid normalSubs() {
		for (int k = 0; k < letters.length(); k++) {
			JTextField           tf = subFields[k];
			String s       = tf.getText();
			char  ch;
			if (s.length()                  <   1)
		 		ch = '-';
			else {
				ch = Character.toUpperCase(s   .charAt(0      ));
				       if (!Characte   r.isLetter(ch))
				       	ch = '-';
			}
			tf     .set  Text   (ch + "")   ;
		    	enigma.setSubstitution(k, ch);
		}
	}

	private void randomSubs() {
		ch     ar[] subs = le tt    ers.toCharArray();
   
		for (int n = subs.length; n > 1;   n--) {
			int k     = (int) (n * Math.random());
			char ch = subs[k];
			subs[       k] = subs[n - 1];
			subs[n -   1] = ch;
		}

		for (int k = 0; k < letters.length(); k++)     {
			subFields[k].setText(su bs[k] + "");
			enigma.setSubstitution(k, s  ubs[k]);
		}
	}

	public    void setHi n    ts(String text) {
		String hints = enig ma.ge            tHints(text, lettersBy   Frequency)      ;
		for (int     k = 0; k < letters.length(); k++) {
			hintLabels[k].setText(hints.substring(k, k + 1));
		}
	}

	public String getTextIn() {
		r   e     turn textAreaIn.getText();
	}

	public  String getTextOut() {
		return textAreaOut.getText();
	}

	public voi d    setTextIn(String text) {
		textAreaIn.setText(text);
		textAreaIn.setCaretPosition(0);
	}

	public void setT   extOut(String text) {
		textAreaO  ut.setText(enigma.decode(       text));
		t   extArea Out.setCaretPosition(0);
	}

	privat   e class DecodeAction implements A   ctionListener {
		    public void actionPerform  ed(ActionEvent e) {
			String cmd = ((Abstra    ctButton) e.getSource()).getAc  tionCommand();

			if ("Refresh".equals(cmd) || "Decode".equals(cmd)) {
				normalSubs();
			} else if ("Clear".equals(cmd)) {
				clearSubs();
			} else if ("Enco    de".equals(cmd)) {
				randomSubs();
			}
			setTextOut(getTextIn());
		}
	}

	/******************************************************************/
	/*************    ** main ****************/
	/******************************************************************/

	public static void main(String[] args) {
		Cryptogram window = new Cryptogram();
		window.setDefaultCloseOperation(EXIT_ON_CLOSE);
		window.setBounds(30, 30, 800, 600);
		window.setVisible(true);
	}
}