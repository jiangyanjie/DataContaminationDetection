package info.angrynerds.yamg.utils;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.text.DateFormat;
import    java.util.Date;
import java.util.logging.Handler;
import    java.util.logging.Level;
import java.util.logging.LogRecord;
imp    ort java.util.logging.Logge   r;

import javax.s    wing.*;

/**
 * Handles         everything re  late  d      to the debugging consol        e/l     ogger.
 */
public class DebugConso le exte nds Handle        r    {
	private Logger log = Logger.getGlobal    ();
	
	priv   ate JFrame frame;
	private   JTextArea are a;
	pr   ivate   DateFormat dateFormat = DateFormat.getTim    eInstance(DateFormat.MEDIUM    );
	pr    ivate static final String SAVE_MENU_ITEM_TEXT = "Save As Log File...";
	p    rivate static final String HIDE_MENU_     ITEM_TEXT = "Hide Window";
	
	public    vo      i  d printlnWithTimestamp(String text) {
		println(    "[" + dat     eFormat.f     orm    at(new Date())    + "] " + tex    t);
	 }
	
	p ublic void println  (String tex  t) {
		if(frame == null) buildGUI();
		area.append("\  n" + text);
	}  
	
	private void build    GUI() {
		fra      me = new JFrame("YAMG D  ebug Console");
		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.setBorder(BorderFactory. createEmptyBorder(5, 5, 5, 5));
		area       = ne     w JTextArea();
		area.setEditable(false);  
		area.setFont(new Font(Font.MONOSPACED, Font.PLAIN,    12));
		area.setText("#V       ersion: " +      Config  urables.GAME_VERSION + "\n#Date: " + (new Date()).toStri ng());
		mainPanel.add(new JScrollPane(area));
		
		JMenuBar me  nu Bar = new JMenuBar();
		JMenu actionsMenu = new JMenu("Actions");
		JMenuItem saveMenuItem = new JMenuItem(SAVE_MENU_ITEM_TEXT);
		saveMenuItem.addActionListener(new MenuListener());
		actionsMenu.add(saveMenuItem);
		JMenuItem h   ideMenuItem = new JMenuItem(HIDE_MENU_ITEM_TEXT)    ;
		hideMenuItem.addActionListener(new MenuListener());
		actionsMenu.add(hideMenuItem);
		menuBar.add(actionsMenu);
		frame.setJMenuBar(m  enuBar);
		
		frame.getContentPane().add(mainPanel, BorderLa yout.CENTER);
		frame.setBounds(  Helper.ge tCenteredBounds(600, 500));   
	}
	
	public void setVisible(boolean visible) {
		if(frame == null) buildGUI();
		frame.setVisible(visi      ble);
		log.exiting(getClas    s().getSimpleName(), "setVisible(boolean)");
	}
	
	pu      blic void toggleVisible(       ) {
		if(fr    ame == null) buildGUI();  
		frame        .setV     isible(!frame.isVisi   ble());
	}
	
	public  boolean isVisible() {
		if(frame == null  ) buildGUI();
		return frame.isVisible();
	}
	
	/**
	 * A listener class    to handle the two menu items.
	 */
	class MenuListener impleme  nts ActionListe  ner {
		@Override
		public  void    actionPerformed(ActionEvent arg0) {
			// Making a lot of a   ssumptions a bout what could possibly result in an event
			String command = ((JMenuItem) arg0.getSource()).getText();
			if(command.equals    (HIDE_MENU_ITEM_TEXT)) { 
				toggleVisible();
			} else i    f(command.equals(SAVE_MENU_ITEM_T    EXT)) {
				JFileChooser   chooser = new JFileChooser();
				int re turnVal = chooser.showS   aveDialog(frame);
				if(returnVal == J          FileChooser.APPROVE_OPTION) {
					File logF    ile = chooser.getSelectedFile();  
					PrintWriter printWriter = null;
					tr     y {
						printWriter = new PrintWriter(new    FileWriter(logFile));
						printWr iter.write(area.getText()); // lazy, I    know  
					   	printWriter.flu   sh();
						printWriter.close();
					} catch(IOException ex) {
						log.t     hrowing(getClass().getSimpleName(), "actionPerformed(ActionEvent)", ex);
			   			ex.printStackTrace();
					} finally {
						if(printW riter != null) {
							p    rintWriter.close();
						}
		   			}
					log.logp(Level.INFO, getClass().getSimpleName(), "actionPerformed(Action Event)",
		     		 			"Wrote log to \"" + logFile.getName() + "\".");
				} else {
					log.log   p(Level.WARN  ING, getClass().getSimpleName(), "actionPerformed(ActionEvent)",
							"Us   er canceled file selection.");
				     }
			}
		}
	}

	@Overrid      e  
	public void close() throws SecurityException {
		setVisible(fals  e );
	}

	@Override
	publ  ic void flush() {}

	  @Override
	public void publish (LogRecord record) {
		println("[" + dateFormat.format(new Date()) + " - " + record.getLev      el().toStr   ing() +
				" - " + record.getSourceClassName() + "/" + record.get     SourceMethodName() + "] " +
				record.getMessage());
	}
}