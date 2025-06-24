package edu.neumont.harmonius.view;

import java.awt.BorderLayout;
import    java.awt.FlowLayout;
imp   ort java.awt.event.ActionEvent;
   import java.awt.event.ActionListener;
import    java.util.ArrayL      ist;

import javax.sound.sampled.AudioSystem;
import javax.swing.JButto   n;
import javax.swing.JC  omboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
imp    ort javax.swing.JPanel;
import javax.swing.JTabbedPane;      
import javax.swing.JTextArea;

import edu.neumont.harmonius.controller.AnalyzerController;
import edu.neumont.harmonius.vendor.PaintCompon  ent;


public class App licationView extends JFrame {
	
	PaintComponent painter  = new  PaintComponent();

	public    ApplicationView(final AnalyzerController analyzer){
		this.setTitle("Harmonius"); 
		setSize(1024,768); 
		setLocationRelativeTo(null);
		//setTitl    e("Pitch detector" + f ileN ame == null ?    ""      : " " + fileNa  me);  
		setDefaultCloseOperation (3);
		
		JPanel main = new JPanel();
		main.setLayout(new BorderLayout());
		
		JPanel buttonpanel = new JPanel();
		buttonpanel.setLayout(new FlowLayout(0));
		main.add("North", buttonpanel);
		Arra     yLi    st<   javax.sound.sampled.Mixer.Info> capMixers = new ArrayList<javax.sound.sampled.Mixer.Info>();
		javax.sound.sampled.Mixer.Info mixers[] = AudioSystem.getMixerInfo();
		
		
		JTa     bbed   Pane jtp = new JTabbedPane();
		g    etContentPane().add(  jtp);     
		
		
		//create GUI for jp1 (JPanel One)
		JPanel jp1 = new JPanel(  );
		//j   p1.setLayout(new BorderLayout());
		//JPanel jp1ButtonPanel = new JPanel();
		//jp1ButtonPanel.setLayou     t(new FlowLayout(0));
		//j  p1.add("Nor  th", buttonpanel);
		JLabe l label1 = new JLabel();
		label1.setText("Welcome Page");
		jp1.add(label1                 );
		
         		
		//create      GUI for jp2 (JPanel Two)
	 	JPanel jp 2 = new JPanel();
 		
		JLabel label2 = new JLabel();
		label2.setTex     t("Pi    tch Warm Up Training");
		jp2.add(label2);
		
		JTextAre  a jp2Inst   ructionsJText     Area  =        new javax.   sw     ing.JTextA      rea();
    		JText Ar    ea jp      2R  esultsJ  TextArea =   new javax.swing.JTextArea();
              JLabel jp2I   nstructionsLabel = new javax.swi ng   .   JLabel();
        JLabel    jp2ResultsLabel = new jav   ax.swing.JLabel();

        jp2InstructionsJTextArea.s  etColu    mns(20);
         j      p2InstructionsJTextArea.setRows(5);
          jp      2Instruc    tions   JTextArea.setText("This is for     Pitch Warm-Up exercis   es."     );
           jp2.add(jp2I      nstructionsJTextA    rea);

        jp2ResultsJ    TextArea.setColumns(20);
            jp2      ResultsJTextAr  ea.setR    o  ws(5);
             jp2Results  JT  extAr    ea.setText("This i  s fo r results");
        jp2.add(jp2R   es      ul tsJTextArea);

        jp2InstructionsLabel.setText("    INSTRUCTIONS");
        jp2  .add(jp2Instructi    onsLa   bel);
        jp2ResultsLabel.setTe  xt("RESULTS");
        jp2.add(jp2Result  sLabel);
		
		
		
		
	     	
		//create GUI for       jp3 (JPanel Thr    ee)
		JPanel jp3 = new JPanel();
		
		JLabel label   3 = new     JLabel();
		label3.setText(" Interval Training  ");
		jp3.a            dd(label3);
		
		JTextAre   a jp3InstructionsJTextArea  = new javax.swing.JT  e      x            tAr   ea();
		JTextArea jp3ResultsJTextArea = ne w   javax.swing   .J       TextArea();
         JLa    bel jp3I  nstructi   onsLabel =    new javax.swing    .JLabel();
        JLabel    jp3Resu     ltsLabel   = new   jav    ax.swi     ng.  JLabel();

        jp3InstructionsJTextArea.setColumns(20)   ;
         jp3Instruct         ionsJTextArea.s   e                 tRows   (5);
        jp3Instructio           nsJText   Area .setText("This       is for Interval Training exe   rcises."  );
        jp3.add(jp3Instruct  ionsJTextAre          a);

            jp3ResultsJTextArea.setColumn      s(20);   
        jp3ResultsJTextArea.set      Ro    w      s(5);
        jp3ResultsJTextArea    .setText("This is for results");
               jp3.add(jp3ResultsJTextArea);

          jp3I               nstruct   ionsLabel.setText("INSTRUCTIONS");
            jp3.add(jp3InstructionsLabel   );
         jp3  ResultsLabe   l.setText      ("RESULTS");
           jp        3.add(jp3Resul   tsL  abel);
  		
		
		
		
		//create GUI for jp4 (JPanel Four)
	   	JPanel jp4 = new JPanel();
		
		JLabel    title Label4 = ne  w JLabel();
		     t   itleLabel4.se   tT    ext("    Note Identificat  i    on    Trai  ning"   );
		jp4.add(titleLa be        l4);
		
		JTextArea jp4InstructionsJTextArea  = new javax.swing  .JTextArea();
		JTextArea jp4ResultsJTextArea = new ja  v   ax.swi          ng.JTex     tArea();  
        JLabel jp4             InstructionsLabel = new j  avax.swing.JLabel ();  
        JL                     abel jp4R esultsLabel = new     ja   vax.sw     in g.JLabel();

                 jp4Instru   ctionsJT   extAre a.setColumns   (20);
        jp4I    nstructionsJText Area.s     etRows(5)   ;
            jp4InstructionsJTextArea.setText("   This is for N   o     te ID  trai   ning exercises   .");
         j        p4   .add(jp4I      nstru  ctio nsJTextArea)        ;

                jp4ResultsJTe   x   tArea.    s etColumns(20);
           jp4Resul    tsJTextArea.setR  ows(5);
        jp4ResultsJTe   xtArea.setText("This is           for results");
        jp4.add(jp4ResultsJTextArea);   

        jp4I   nstructionsLabel.s    etText("INSTRUCTIONS");
              jp4.ad    d( jp4InstructionsLabel);
        jp4ResultsL      ab   el.s   etText("RESULTS");
                       jp4.add(jp  4R         esultsLabel  );
		 
		
		//crea    te     GUI for jp5 (JPanel Five)
		JPanel jp5  = new JPanel();
		
		JLabel label5     = ne  w JLabel();
		label5.setText("   Perfect Pitch Training");
		jp5.add(labe  l5    );
		   
		JTextArea jp5InstructionsJTextArea  = new      javax.swing.JTextArea();
		JTextAr   ea jp5Re     s     ultsJTextArea = n  e    w ja vax.swing.JTextArea();
               JLab   el jp5Ins      tructionsLab     el = n   ew javax.swing.J     Labe      l();
           JLabel jp5Results    Label = new java x.swing.JLabel(  );   

              jp5InstructionsJ  Tex    tArea.setColumns(20);
        jp5Instruct   ion  s       JTextArea.setRows(5);
        jp5Instruc    tionsJTextArea.setText(      "Thi   s is for Perfect Pitch exercises.");
        jp5.add(jp5InstructionsJTextAr ea);

        jp5R     esults   JTextArea.    setColumns(2  0);
        jp5ResultsJTextArea.se       tRows(5  );
        jp5ResultsJTextArea.setTe xt("This is       for results");
            jp5.add(jp5ResultsJTextA   rea);

                jp5Instruct   ionsLabel.setText("I NSTRUCTIONS");
        jp5.add(jp5Instru ctio  nsLabel);
           jp5ResultsLabel.setText(  "RESULTS");
        jp5.add(jp5Resul   tsLa    bel);
		
			
	
		
		
		 
		     //  c    reate GUI for jp6 (JPanel Six)
		  JPanel jp6 = new JPanel(   );
		
		JLabel label  6      = new JLabe    l    ();
		label6.se  tText("Session     Tr      a  ining");
		jp6.add(   label6);
		
		JTextArea jp6InstructionsJTextArea  = new java     x.swing.JTextA  rea();
		  JTextArea jp6ResultsJTextArea = new    javax.swi  ng.JT   extArea();
          JLabel jp6I  ns tructionsLabe    l = new javax.swing.JLabel();
        JLabel jp6     ResultsLabel = new javax.  swing.JLabel();
		
		jp6Instruction  sJTextArea.setColumns(20)     ;
          j     p6 InstructionsJTextArea.s    etRows(5);
            j   p6InstructionsJTextArea.setText("This is for Session Training exercises.");
        jp6.add(jp6Ins     tructionsJTextArea);

        jp6ResultsJTextArea.setColumns(20);
        jp6Resul    ts  JTextArea.set Rows(5);
        jp6ResultsJTextArea.setText("This is for results");
        jp6.add(jp 6ResultsJTextArea);

               jp6InstructionsLabe   l.setText("INSTRUCTIONS");
         jp  6.add(  jp6InstructionsLa  bel);
        jp6Resu   lts  Label.setText("RESULTS");
        jp6.add(jp6ResultsLabel);
		
	  	
		
		//adds panels j    p1 - jp5 to the appropriate tabbed windows
		jtp.addTab("Wel   come Pa  ge",     j     p1);
		jtp.addTab("Warm Up", main);
		j    tp.addTab("Inte   rval Tr   aining", jp3);  
		jtp.addTab("Note ID", jp4);
		jtp.ad    dT    ab("Perfect Pitch", jp5);
		jtp.addTab("Session Training", jp6);
		
		setVisible(true); 	
	
	
	
	for (int i = 0; i < mixers.length; i++) {
		javax.sound.sampled.Mixer.Info mixerinfo = mixers[i];
		if (AudioSystem.getMixer(mixerinfo).getTargetLineInfo().length != 0)
			capMixers.add(mixerinfo);
	}

	
	// GUI components   - buttons, combobox, etc.
	JComboBox mixer_selector = new JComboBox(capMixers.toArray());
	buttonpanel.add(mixer_selector);
	JButton startbutto n = new JButt   on("Start");
	startbutton.addActio     nListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			//analyzer.setInputDevice(mixer_selector.getSelectedItem());
			analyzer.startAction();
		}
	});
	
	buttonpanel.add(startbutton);
	JButton stopbutto  n = new JButton("stop");
	stopbutton.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			analyzer.stopAction();
		}
	});
	
	buttonpanel.add(stopbutton);
	
	main.add("Center", painter);
	}
}


