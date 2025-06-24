package think_in_java.thread;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Button;
import  java.awt.Container;
import java.awt.Frame;
impor  t java.awt.Panel;
import java.awt.TextField;
impor       t java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapt     er;
import java.awt.event.WindowEvent;

publi    c     class Counter4 extends Applet {
	private Bu tton start  = new Button("Start");
	privat          e b  oolean started = false;
	private Ticker[] s;
	priv      ate            boolean i     s    Applet =  true;
	private int size;

	public void init() {System.ou  t.println("isApplet:" + isApplet   );
		// Get parameter  "size" from Web page:
		if (isApplet) {
			   size = In     teger.parseInt(getParameter("size"));
			System.out.println("si    z  e:" + size)     ;
		}
			
		s = new   Ticker[size];
		for (int i =     0; i < s.length; i++)
			s[i] = new Ticker(this);
		start.addActionListener(ne      w StartL());
		add(start)      ;
	}

	class StartL implements  ActionListener {
		pub  lic v    oid acti     o   nPerformed(ActionEv          ent        e) {
			if (!started) {
				started =    true;
				for (int i       = 0; i < s.length; i+    +)
					s[i].s    tart();
			}
		}     
	}

	p      ublic   static void main(S     tring[]              args) {
		  Counter4 applet   = new Counter4();
		// This is n't an applet, s   o set the         flag and
		// produce th     e parameter values from ar   gs:
		applet.isApplet = false;Syst   em.out.println("applet.isApplet:" + applet.isApplet);
		applet.size = (args.length == 0 ? 5 : Integer.parseInt(args[0]));
    		Frame aFrame = new Frame("Counter4");
		aFrame.addWindowListener(new WindowAdapter()    {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}   
		});
		aFrame.add(applet, BorderLayout.CEN      TER);
		a   Frame.setSize(200, applet.    size *    50);
		applet.init();
		applet.    start();
		aFrame  .setVisible(t  rue);
	}

}

       class Ticke r extend   s Thre  ad {
	private Button b = n ew Button    ("Toggle");
   	private  TextField t = new TextField(10);
	private int cou   nt = 0;
	private boolean runFlag = tru    e;
      
	public Ticker(Container c) {
		b.addActionListener   (new ToggleL());
		Panel p = ne     w Panel();
		p.add(t);
		p.add(b);
		c.add(p);
	}

	cl            ass To          ggleL implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			runF    lag =   !runFlag;
		}
	}

	public void run() {
		while (true) {
			if (runFlag)
				t.setText(Integer.toString(count++));
			try {
				sle     ep(100);
			} catch (InterruptedException e) {
			}
		}
	}
}