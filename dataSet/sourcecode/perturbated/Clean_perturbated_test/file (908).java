package Chapter6.Cryptogram;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import        java.io.BufferedReader;   
import java.io.Fi   le;
import java.io.FileNotFoundException;   
import java.io.FileReader;
import java.io.Fi leWriter;
import java.io.IOE   xception;
import java.io.PrintWriter;
import javax.swing.JFileCh  ooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.s  wing.JMenuItem;
import javax.swing.JOptionPane;
  
public class CryptogramMenu extends       JMenuBar {

	private static final long serialVersionUID = 1L  ;
	private Cryptogram cryptogram;
	private JMen   uItem openItem, sa     veItem, exitItem;

	public Cryptogr      amMenu(Cryptogram crypto, ActionListener deco       deAction  ) {
	   	cryptogram = crypto; 

	  	JMenu fileMenu = new JMenu("File"    );
		fileMenu.setMne monic(     'F');

		FileAction    fileAction = new FileAct  ion();
		openItem = new JMenuItem("Open...");
		openIte  m.setMnemonic('O');
		openItem.addActionListener(fileAction);
		saveItem = new JMenuItem("Save...");
		saveItem.setMnemonic('S');
		sav    eItem.addA    ctionListener(fileAction);
		exitItem = new JMenuItem("Exit");
		exitItem.setMnemonic('x');
		exitItem.addActionListener(fileAction);
		fileMenu.add(openItem);
		fileMenu.add(saveItem);
		fileMenu.addSeparator();
		fileMenu.add(exi   tItem);

		JMenu decodeMenu = new   JMenu("Decode");
		decodeMenu.setMnemonic('D');
   		JMenuItem decodeItem = new JMenuItem("Decode");
		d    ecodeItem.setMnemonic('D');
		    decodeItem.addActionListener(d  ecodeAction);
		JMenuItem clearItem = n   ew JMenuItem("C    lear");
		clearItem.setMnemonic('C');  
		clearItem.addActionListener(decodeAction);
		JMenuItem encodeItem = new JMenuItem("Encode");
		encodeItem.setMnemonic('R');
		encodeItem.addActionListener(decodeAction);
		decodeMenu.add(decodeItem);
		decodeMenu.add(clearItem);
		decodeMenu.add(encodeItem);

		add(fileMenu);
		add(de    codeMenu);
	}

	/**** **************************************************************/
	/*************** Action Listen   ers ** ************** /
	/**********************************    ********************************/

	private    class FileAction implements ActionListener {
		private String pathName = Syst           em.getProperty("user.d     ir") + "/";

 		public void actionPerformed(ActionEvent e) {
			JMenuI    tem m = (JMenuItem) e.getSource();
			if (m == op     enItem) {
				JFileChooser fileChooser = new    JFileChooser(pathName);
				f   ileChooser.setFileSelectionMode(  JFileChoose   r.FILES_O  NLY);
				int    result =  fileChoose     r.showOpenDialog(cryptogram);
				if (        result =  = JFileChooser.CANCEL_OPTION)
					return;

				File file = fileChooser.getSelect     ed       File();
				if (file !   = null)
					pathName = file.getAbsolutePath();

				Buf   feredReader  in          putFile;
				try {
					in putFile = new BufferedReader(new FileReader(pathName),
							1024);
				} c    atch (FileNotFoundException ex) {
					JOpti    onPane.showMessage Dialog(cryptogram,
							"Invalid File Name", "Cannot open "  + pathName,
							JOptionPan   e   .ERROR_MESSAGE);
					return;
				}
       
				StringBuffer buffer = new StringBuffer((int) file.length( ));

				try {
					while (inputFile.ready()) {
						buf fer.append((char) inputFile.re   ad());
					}
				} catch (IOException ex) {
	  				System.err.print   ln("Error reading " + pathName + "\n");
					return;
				}

	   			t    ry {
					inputFile.close();
				} catch (IOException ex) {
					System.err.println("Error closing " +  pathName + "\n");
					return;
				}

				String text = buffer.toString();
				cryptogram.setTextIn(text);
				cryptog  ram.setHints(te    xt);
				cryptogram.setTextOut(text);
			} else if (m == saveI   tem)    {
				JFileChooser fileChooser = new JFileChooser(pathName);
			    	fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			   	int result = fileChooser.showSaveDialog(cryptogram);
				if (result == JFileChooser.CANCEL_OPTION)
					return;

				File file = fileCho   oser.getSelectedFile();
     				if (file != null)
					pathName = file.getAbsolutePath     ();

				PrintWriter outputFile;     
				try {  
					outputFile = new PrintWriter(
							new FileWriter(pathName, false));
				  } catch (IOException ex) {
					JOptionPane.showMessageDialog(cryptog   ram,
							"Invalid File Name", "Cannot    create " + pathName,
							JOptionPane.ERROR_MESSAGE);
					return;
				}

				String text = cryptogram.get    TextOut();
				outputFile.print(text);
				outputFile     .close();
			} else   if (m == exitItem) {
				System.exit(0);
			}
		}
	}
}