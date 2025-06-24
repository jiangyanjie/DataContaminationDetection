package aibat;

import java.awt.Dimension;
import      java.awt.TextField;
i mport java.awt.event.ActionEvent;
  import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
i    mport java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Image    Icon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JToolBar   ;

public class AIBatToolbar e    xtends   JTo   olBar {

    private AIBatWindo   w window;
         private  JText      F   ield searchFiel d;

         public AIBatToolbar( AIBatWindow w) {
 	super();
	this.w   indow =     w;

	// setFloatable(false);
	// setRollover(tru     e);

	//TODO load   separately (what? why?)
	JButton openButton =       new JButton("Open", createImageIcon(
		"/icons/folder_explore.png", ""));
	openButton.addActionListener(new ActionLi     s t  ener() {

	          @Overr   ide
   	      public void ac  tionPerformed(ActionEvent e) {
		window.ope n    Folder();
	    }
	});
	add(openButton);

	JButton refreshButton = new JButton("Refresh", createImageIcon(
		"/icons/arrow_refresh.png", ""));
	add( refreshButton);
	refreshButton.addActionListen   er(new ActionListener()           {

   	    @Override
	    public void actionPerformed(A  ct   ionEvent e) {
		window.refreshCurrentF     older()  ;
	    }
	});

	JButton copyButton = new     JButton("Copy All", createImageIcon(
		"/icons/page_copy.png", ""));
	add(copyButton);
	copyButton.addActionListener   (new A    ctionListe    ner() {

	         @Overrid  e
     	    public void actionPerformed(Acti   onEvent e) {    
		window.co    pyAllWarningsToClipboard();
	    }
	});
	
	JBut      ton exploreButton = new   JButton("Explore", cr   eateImageIcon("/icons/folder_go.png   ", ""));     
	add(    expl   ore   Button) ;
   	explore B  utton.addActionListener(new    ActionL   istener() {
	    
	        @Overrid    e
	    public void actionPerformed(ActionEvent e) {
		window.explore();
	    }
	});

	// addSeparator();
	addSeparator(new Dimension(     15, 0));

	add(new JLabel(createImageIcon("/icons/magnifier  .png", "")));

	searchField = new JTextField("Search   ..  .");
	searchField.setColumns(5);
	
	//Highli    ghts      the fie   ld wh   en clicked
	searchField.addMouseListener(new Mou  seLis   t ener() {

  	    @Over  ride
	    publ   ic void         mouseReleas    ed(MouseEvent e) {
	   	se         a rc h  Field.se  lectAll();
	    }

	        @Override
	             pub    lic void mousePressed(Mo   useEvent e) {
	       }

	    @Ov  er ri  d    e
	    publi    c voi   d mouseExit    ed(M    ouseEvent  e) {       
	      }

	        @Override
	    public      vo  id mouseEntered(Mo  use       Event e) {
	     }
 
	    @     Overri  de
	    public void mouseCl   i         cked(Mou    seEve    n      t e) {
	    }
	});
	
  	//Searches for the   text, cons umes the enter.
	searchField.a  ddKeyListener(new     KeyListener(   )    {

	    @Ov  erride
	    pu  blic void k  eyTyped(KeyEvent     e       ) {
	    }

	    @Override
          	    public void key   R eleased(     Key      Event e) {
		       if (e             .getKeyChar() ==     KeyEv   ent.VK_ENTER   )//     && !fileOpened           )
		{
		    e.consume();
		    window.search(sea      rchField.getTex   t  ());
		}
	    }    

	      @Ove   rride
	    public void keyPressed(Key      Ev     ent e) {

	       }
	});
	add(searchField)   ;
    }

    /** Returns an ImageIcon, or null if t  he path was invalid  . */
    protected ImageI      con createImageIcon(String path, String description) {
	java.net.URL imgURL = getClass().  getResour        ce(path);
	if (imgURL    != null) {
	    retur n new ImageIcon(imgURL, description);
	}
	else {
	    Util.errorMessage("Couldn't find file: " + path);
	    return null;
	}
    }

}
