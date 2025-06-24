package think_in_java.thread;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Container;
import java.awt.Frame;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Counter4 extends Applet {
	private Button start = new Button("Start");
	private boolean started = false;
	private Ticker[] s;
	private boolean isApplet = true;
	private int size;

	public void init() {System.out.println("isApplet:" + isApplet);
		// Get parameter "size" from Web page:
		if (isApplet) {
			size = Integer.parseInt(getParameter("size"));
			System.out.println("size:" + size);
		}
			
		s = new Ticker[size];
		for (int i = 0; i < s.length; i++)
			s[i] = new Ticker(this);
		start.addActionListener(new StartL());
		add(start);
	}

	class StartL implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (!started) {
				started = true;
				for (int i = 0; i < s.length; i++)
					s[i].start();
			}
		}
	}

	public static void main(String[] args) {
		Counter4 applet = new Counter4();
		// This isn't an applet, so set the flag and
		// produce the parameter values from args:
		applet.isApplet = false;System.out.println("applet.isApplet:" + applet.isApplet);
		applet.size = (args.length == 0 ? 5 : Integer.parseInt(args[0]));
		Frame aFrame = new Frame("Counter4");
		aFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		aFrame.add(applet, BorderLayout.CENTER);
		aFrame.setSize(200, applet.size * 50);
		applet.init();
		applet.start();
		aFrame.setVisible(true);
	}

}

class Ticker extends Thread {
	private Button b = new Button("Toggle");
	private TextField t = new TextField(10);
	private int count = 0;
	private boolean runFlag = true;

	public Ticker(Container c) {
		b.addActionListener(new ToggleL());
		Panel p = new Panel();
		p.add(t);
		p.add(b);
		c.add(p);
	}

	class ToggleL implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			runFlag = !runFlag;
		}
	}

	public void run() {
		while (true) {
			if (runFlag)
				t.setText(Integer.toString(count++));
			try {
				sleep(100);
			} catch (InterruptedException e) {
			}
		}
	}
}