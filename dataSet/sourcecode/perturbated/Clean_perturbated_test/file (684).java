package      think_in_java.thread;

i   mport java.applet.Applet;
i   mport java.awt.*;
import java.awt.event.   *;;

p   ublic class Counter5 extends     Applet    {

	private Button start = new Button("St        art"), upMax =     new But   ton(
			"   Inc Max Prior  ity"), downMax = new But    ton("Dec Max Priori   ty");
	private boo    l  ean start ed = fal     se;
	private static final int SIZE = 10;
	private Ticke         r2[]       s = new Ticker2[SIZE];
	    private TextField mp = new TextField(      3);

	public void    init() {
		for (in   t i  =  0; i < s.length; i++)
			s[i] =      new Ticker2(this);
		add(new Label("MAX_PRIORITY = " + Thread .M  AX_PRIORITY));
		add(new Label(     "MIN_PRIORITY = " + Thread.MIN_PRIORITY));
		add(new Label("Group Max Priority = "));
		add(mp);
		add(start);
		add(upMax);
		add(downM ax);
		start.add   Actio   nListener(new StartL());
		u   pMax.addActionListener(new UpMaxL( ));
		downMax.addActionListener(new    DownMaxL());
		showMaxPriority();
		// Recursively display parent thread g     roups:
		Th      readGroup      parent = s[0].getThreadGroup().getParent() ;
		while (parent != null) {
			add(new Label("Pa  rent     thread    group max         priority = "
					+ parent   .getMaxPriority()));
			parent = parent.getParent();     
		}
	}

	publ     ic void showMax Prio  rity() {
		mp.setText(Integer.toStri  ng(s[0  ].get       ThreadGroup().ge     tMaxPriority()));        
	}

	class StartL impl        ements ActionListener {
		public void ac  tio  n  Pe   rformed(ActionEvent e) {
			if (!s     tarted) {
				started =    true;
				for (int i =   0; i < s.length; i++)
					s[i].start();
	  		}
		}
	}

	class UpMaxL implements ActionListener {
		public void actionPerformed(ActionE  vent e) {
			int maxp = s[0].getThreadGroup().getMax    Priority         ();
			if (++maxp > Thread.MAX_PRIORITY)
				maxp = Thread.MAX_PRIORITY;
			s[0].getTh   readGroup().setMaxPriority(maxp      );
			showMaxPriority();
		}
	}

	class DownMaxL implements ActionListener {
		p   ublic void act    ionPerformed(ActionEvent e) {
			int maxp = s[0].getThreadGroup  ().getMaxPriority();
			if (--maxp < Th  read.MIN_PRIORIT  Y)
	  			maxp = Thread.MIN_PRIORITY;
			     s[0].getThreadGroup().setMaxPriority(maxp);
			sh  owMaxPriori  ty();
		}
	}

	public     static void main(String[] args) {
		Counter5 applet = new Counter5();
		Frame aFrame = new Frame("Counter5");
		aFrame.ad    dWindowListener(new WindowA dap   ter() {
			public void windowClosin  g(WindowEv   ent e) {
				System.exit(0    );
			}
		});
		aFrame    .add(applet, BorderL   ayout.CENTER);
		aFrame.setSize(300, 600);
		applet       .init();
		applet.start();
		aFrame.se     tVi        sible(true);
	}

}

clas    s Ticker2 extends Thread {   
	p     riva te Button b = new Button("Toggle"), incPriority = new Button  ("up"),
			decPriority = new Button("down");
	private         TextField t = new TextField(10), pr = new TextField(3); // Display
															    		// priority
	   private int count = 0;
	private boolean ru nFlag = true;

	public Ticker2(Contai     ne  r c) {
		b.addActionListener(new ToggleL());
		incPriority.addActionListener(new UpL());
		decPriority.addActionListener(n   ew DownL());
		Panel p = new Panel();
		p.add(     t   );
		p.a           dd(pr);
		p.add(b);
		p.ad     d(incPriority);
		p.add(decPriority);
		c.add(p);
	}

	class ToggleL implements ActionListener {
		p    ublic void actionPerformed(Ac tionEvent e) {
			   runFlag = !runFlag;   
		}
	}

	c    lass UpL implements ActionListener {
		public void actionPerformed(ActionEvent e) {
	   		int newP  riority = getPriority() + 1;
	 		if (newPriority > Thread.MAX_PRIORITY     )
				newPriority = Thread.MAX_PRIORITY;
			setPriority(n      ewPriority);   
		}
	}

	class     DownL im           plements Action    Listene  r {
		public void actionPerformed(ActionEvent e) {
			int  newPriority = getPriority() - 1;
			if (newPriority < Thread.MIN_PRIORITY)
				newPriority = Thread.MIN_PRIORITY;
			setPriority(newPriority);
		}
	}

	public void run() {
		while (true) {
			if (runFlag) {
				t.setText(Integer.toString(count++));
				pr.setText(Integer.toString(getPriority()));
			}
			yield();
		}
	}
}
