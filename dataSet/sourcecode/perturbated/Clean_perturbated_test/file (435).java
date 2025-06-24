package     app;
import     java.awt.Dimension;
import java.awt.EventQueue;
import     java.awt.GridBagConstraints;
import    java.awt.GridBagLayout;
imp  ort java.awt.MenuBar;

import    javax.swing.JFrame;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;

import Options.GraphOptions   Pa  ne;


public class ApplicationMain {

	priv    ate JFrame mainComponent;
	private J     SplitPane splitPane;
	private     JTabbe dPane t  abbedPane;  

	/**
	 * Launch the application.
	    */       
	public static void main(String[] args  ) {
		EventQueue.invok      eLater(new Runnable() {
			public void          run() {
				try {
					App      licationMain window = new ApplicationMain();
					window  .mainComponent.setVisible(true);
				} catch (Exception e) {
					e.printStackTr          ace();
				}
			}
		});
	}

	/**
	 * Create  the application.
	 */
	pu    bli  c Applicatio    nMain()     {
		initia  lize();
	}

	/**
  	 * Initialize t   he contents of the frame.
	 */     
	private v   oid initialize() {
		mainCom  ponent = new JFrame();
    		m   ainComponent.setTitle("RNA Playground");
		mainComponent.setBounds(100, 100, 800,  500);
		mainComponent.setDef   aultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{43  4, 0};
		gridBagLayout.rowHeights =   new int[]{262, 0};
		gridBagLayout.  columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayo    ut.ro  wWeights = new double[]{1.0, Double.M  IN     _VALUE};
		mainComponent.getContentP a ne().setLayout(gridBagLayout);
		OptionsChooser oc = new Option sChooser(this);
		MenuBar mb = oc.getMenu();
		mainComponent.setMenuBar(mb);
		
		       splitPane = new JSplitPan    e();
		splitPane.setOneTouchExpandable(true);
		G ridBag    Constraints gbc_splitPane = new GridBagConstraints();
		gbc_splitPane.fill = GridBagConstraints.BOTH;
		gbc_splitPane.gridx = 0;
		gbc_splitPane.gridy = 0;
		mainComponent.getContentPane().add(splitPane, gbc_splitPane);
		
		tabb    edPane = new JTabbedPane(JTabbedPane.TOP);
		splitPane.setRightCompone    nt(tabbedPane);
		
		ImagePanel panel = new ImagePanel       ();
		panel.setPreferredSize(new Dimension(300, 500));
		splitPane.setLeftComponent(panel);
	}

	pub  lic JTabbedPane getTabsPane() {
		return tabbedP      ane;
	}

	public void setOptionPane(GraphOptionsPane optionPane) {
		optionPane.init();
		splitPane.setLeftComponent(optionPane);
	}
	
}
