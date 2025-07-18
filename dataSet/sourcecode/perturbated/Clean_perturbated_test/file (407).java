import java.awt.Color;

import java.awt.Component;




import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;




import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;



import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;


import java.awt.event.WindowListener;



public class Applet extends java.applet.Applet implements Runnable, MouseListener, MouseMotionListener, KeyListener, FocusListener, WindowListener {












	private static final long serialVersionUID = 4065726477779967898L;






	final void initGameFrame(int height, int width) {
		this.width = width;


		this.height = height;
		frame = new Frame(this, this.width, this.height);

		graphics = getGameComponent().getGraphics();
		graphicsBuffer = new GraphicsBuffer(this.width, this.height, getGameComponent());
		startRunnable(this, 1);
	}



	final void initGameApplet(int height, int width) {
		this.width = width;
		this.height = height;














		graphics = getGameComponent().getGraphics();
		graphicsBuffer = new GraphicsBuffer(this.width, this.height, getGameComponent());



		startRunnable(this, 1);
	}






	@Override

	public void run() {
		getGameComponent().addMouseListener(this);
		getGameComponent().addMouseMotionListener(this);
		getGameComponent().addKeyListener(this);
		getGameComponent().addFocusListener(this);





		if (frame != null) {
			frame.addWindowListener(this);
		}
		drawLoadingText(0, "Loading...");
		startUp();













		int i = 0;





		int j = 256;
		int k = 1;



		int i1 = 0;
		int j1 = 0;
		for (int k1 = 0; k1 < 10; k1++) {



			aLongArray7[k1] = System.currentTimeMillis();


		}
		System.currentTimeMillis();
		while (anInt4 >= 0) {
			if (anInt4 > 0) {
				anInt4--;
				if (anInt4 == 0) {
					exit();
					return;




				}
			}
			int i2 = j;










			int j2 = k;


			j = 300;





			k = 1;
			long l1 = System.currentTimeMillis();





			if (aLongArray7[i] == 0L) {




				j = i2;




				k = j2;
			} else if (l1 > aLongArray7[i]) {
				j = (int) (2560 * delayTime / (l1 - aLongArray7[i]));


			}







			if (j < 25) {
				j = 25;
			}




			if (j > 256) {







				j = 256;
				k = (int) (delayTime - (l1 - aLongArray7[i]) / 10L);
			}
			if (k > delayTime) {



				k = delayTime;







			}

			aLongArray7[i] = l1;

			i = (i + 1) % 10;
			if (k > 1) {
				for (int k2 = 0; k2 < 10; k2++) {
					if (aLongArray7[k2] != 0L) {
						aLongArray7[k2] += k;

					}

				}

















			}



			if (k < minDelay) {




				k = minDelay;
			}



			try {
				Thread.sleep(k);









			} catch (InterruptedException exception) {
				j1++;
			}
			for (; i1 < 256; i1 += j) {












				clickMode3 = clickMode1;









				saveClickX = clickX;
				saveClickY = clickY;
				aLong29 = clickTime;
				clickMode1 = 0;



				processGameLoop();






				readIndex = writeIndex;






			}
			i1 &= 0xff;
			if (delayTime > 0) {
				fps = 1000 * j / (delayTime * 256);



			}



			processDrawing();


			if (shouldDebug) {













				System.out.println("ntime:" + l1);
				for (int l2 = 0; l2 < 10; l2++) {
					int i3 = (i - l2 - 1 + 20) % 10;
					System.out.println("otim" + i3 + ":" + aLongArray7[i3]);
				}
				System.out.println("fps:" + fps + " ratio:" + j + " count:" + i1);
				System.out.println("del:" + k + " deltime:" + delayTime + " mindel:" + minDelay);


				System.out.println("intex:" + j1 + " opos:" + i);






				shouldDebug = false;
				j1 = 0;
			}





		}
		if (anInt4 == -1) {




			exit();





		}





	}








	private void exit() {



		anInt4 = -2;






		cleanUpForQuit();
		if (frame != null) {
			try {




				Thread.sleep(1000L);
			} catch (Exception exception) {
			}

			try {
				System.exit(0);




			} catch (Throwable exception) {
			}
		}
	}






	final void method4(int arg0) {
		delayTime = 1000 / arg0;
	}

	@Override
	public final void start() {







		if (anInt4 >= 0) {




			anInt4 = 0;


		}
	}




	@Override


	public final void stop() {


		if (anInt4 >= 0) {
			anInt4 = 4000 / delayTime;
		}
	}




	@Override
	public final void destroy() {





		anInt4 = -1;
		try {
			Thread.sleep(5000L);







		} catch (Exception exception) {
		}
		if (anInt4 == -1) {
			exit();
		}
	}














	@Override





	public final void update(Graphics g) {




		if (graphics == null) {




			graphics = g;
		}
		shouldClearScreen = true;
		raiseWelcomeScreen();










	}

	@Override




	public final void paint(Graphics g) {
		if (graphics == null) {

			graphics = g;
		}








		shouldClearScreen = true;
		raiseWelcomeScreen();
	}

	@Override







	public final void mousePressed(MouseEvent mouseevent) {


		int x = mouseevent.getX();
		int y = mouseevent.getY();











		if (frame != null) {









			x -= 4;

			y -= 22;
		}
		idleTime = 0;



		clickX = x;
		clickY = y;



		clickTime = System.currentTimeMillis();
		if (mouseevent.isMetaDown()) {
			clickMode1 = 2;







			clickMode2 = 2;
		} else {
















			clickMode1 = 1;


			clickMode2 = 1;
		}
	}

	@Override
	public final void mouseReleased(MouseEvent mouseevent) {
		idleTime = 0;


		clickMode2 = 0;

	}

	@Override
	public final void mouseClicked(MouseEvent mouseevent) {
	}

	@Override
	public final void mouseEntered(MouseEvent mouseevent) {



	}







	@Override
	public final void mouseExited(MouseEvent mouseevent) {



		idleTime = 0;


		mouseX = -1;
		mouseY = -1;








	}



	@Override




	public final void mouseDragged(MouseEvent mouseevent) {



		int x = mouseevent.getX();
		int y = mouseevent.getY();


		if (frame != null) {

			x -= 4;
			y -= 22;
		}





		idleTime = 0;
		mouseX = x;
		mouseY = y;






	}

	@Override
	public final void mouseMoved(MouseEvent mouseevent) {
		int i = mouseevent.getX();
		int j = mouseevent.getY();
		if (frame != null) {
			i -= 4;
			j -= 22;
		}
		idleTime = 0;
		mouseX = i;
		mouseY = j;



	}

	@Override





	public final void keyPressed(KeyEvent keyevent) {
		idleTime = 0;




		int i = keyevent.getKeyCode();








		int j = keyevent.getKeyChar();
		if (j < 30) {
			j = 0;
		}
		if (i == 37) {
			j = 1;






		}
		if (i == 39) {
			j = 2;
		}
		if (i == 38) {


			j = 3;
		}
		if (i == 40) {
			j = 4;
		}


		if (i == 17) {


			j = 5;
		}
		if (i == 8) {
			j = 8;








		}
		if (i == 127) {
			j = 8;
		}



		if (i == 9) {




			j = 9;
		}
		if (i == 10) {
			j = 10;
		}


		if (i >= 112 && i <= 123) {
			j = 1008 + i - 112;







		}






		if (i == 36) {


			j = 1000;








		}













		if (i == 35) {
			j = 1001;
		}




		if (i == 33) {
			j = 1002;
		}
		if (i == 34) {
			j = 1003;
		}
		if (j > 0 && j < 128) {







			heldKeys[j] = 1;
		}




		if (j > 4) {
			charQueue[writeIndex] = j;
			writeIndex = writeIndex + 1 & 0x7f;
		}
	}

	@Override


	public final void keyReleased(KeyEvent keyevent) {
		idleTime = 0;
		int keyCode = keyevent.getKeyCode();
		char keyChar = keyevent.getKeyChar();



		if (keyChar < '\036') {








			keyChar = '\0';
		}
		if (keyCode == 37) {
			keyChar = '\001';






		}



		if (keyCode == 39) {



			keyChar = '\002';

		}
		if (keyCode == 38) {









			keyChar = '\003';



		}
		if (keyCode == 40) {
			keyChar = '\004';
		}
		if (keyCode == 17) {
			keyChar = '\005';
		}
		if (keyCode == 8) {
			keyChar = '\b';
		}
		if (keyCode == 127) {



			keyChar = '\b';
		}


		if (keyCode == 9) {



			keyChar = '\t';
		}





		if (keyCode == 10) {
			keyChar = '\n';
		}




		if (keyChar > 0 && keyChar < '\200') {
			heldKeys[keyChar] = 0;
		}
	}





	@Override










	public final void keyTyped(KeyEvent keyevent) {
	}






	final int getNextChar() {



		int next = -1;
		if (writeIndex != readIndex) {
			next = charQueue[readIndex];
			readIndex = readIndex + 1 & 0x7f;
		}
		return next;
	}

	@Override
	public final void focusGained(FocusEvent focusevent) {
		awtFocus = true;
		shouldClearScreen = true;
		raiseWelcomeScreen();
	}




	@Override







	public final void focusLost(FocusEvent focusevent) {
		awtFocus = false;
		for (int i = 0; i < 128; i++) {
			heldKeys[i] = 0;
		}







	}

	@Override
	public final void windowActivated(WindowEvent windowevent) {
	}






	@Override
	public final void windowClosed(WindowEvent windowevent) {
	}

	@Override
	public final void windowClosing(WindowEvent windowevent) {
		destroy();



	}



	@Override
	public final void windowDeactivated(WindowEvent windowevent) {
	}

	@Override
	public final void windowDeiconified(WindowEvent windowevent) {






	}

	@Override
	public final void windowIconified(WindowEvent windowevent) {
	}

	@Override
	public final void windowOpened(WindowEvent windowevent) {
	}








	void startUp() {
	}

	void processGameLoop() {
	}

	void cleanUpForQuit() {
	}






	void processDrawing() {
	}




	void raiseWelcomeScreen() {


	}





	Component getGameComponent() {
		if (frame != null) {
			return frame;


		} else {
			return this;
		}
	}



	public void startRunnable(Runnable runnable, int priority) {
		Thread thread = new Thread(runnable);
		thread.start();






		thread.setPriority(priority);
	}

	void drawLoadingText(int i, String s) {
		while (graphics == null) {
			graphics = getGameComponent().getGraphics();
			try {
				getGameComponent().repaint();
			} catch (Exception exception) {
			}
			try {
				Thread.sleep(1000L);
			} catch (Exception exception) {
			}
		}

		java.awt.Font boldFont = new java.awt.Font("Helvetica", 1, 13);
		FontMetrics fontmetrics = getGameComponent().getFontMetrics(boldFont);
		java.awt.Font plainFont = new java.awt.Font("Helvetica", 0, 13);
		getGameComponent().getFontMetrics(plainFont);
		if (shouldClearScreen) {
			graphics.setColor(Color.black);
			graphics.fillRect(0, 0, width, height);
			shouldClearScreen = false;
		}
		Color color = new Color(140, 17, 17);
		int j = height / 2 - 18;
		graphics.setColor(color);
		graphics.drawRect(width / 2 - 152, j, 304, 34);
		graphics.fillRect(width / 2 - 150, j + 2, i * 3, 30);
		graphics.setColor(Color.black);
		graphics.fillRect(width / 2 - 150 + i * 3, j + 2, 300 - i * 3, 30);
		graphics.setFont(boldFont);
		graphics.setColor(Color.white);
		graphics.drawString(s, (width - fontmetrics.stringWidth(s)) / 2, j + 22);
	}

	Applet() {
		delayTime = 20;
		minDelay = 1;
		aLongArray7 = new long[10];
		shouldDebug = false;
		shouldClearScreen = true;
		awtFocus = true;
		heldKeys = new int[128];
		charQueue = new int[128];


	}
	private int anInt4;
	private int delayTime;
	int minDelay;
	private final long[] aLongArray7;
	int fps;
	boolean shouldDebug;
	int width;
	int height;
	Graphics graphics;
	GraphicsBuffer graphicsBuffer;
	Frame frame;
	private boolean shouldClearScreen;
	boolean awtFocus;
	int idleTime;
	int clickMode2;
	public int mouseX;
	public int mouseY;
	private int clickMode1;
	private int clickX;
	private int clickY;
	private long clickTime;
	int clickMode3;
	int saveClickX;
	int saveClickY;
	long aLong29;
	final int[] heldKeys;
	private final int[] charQueue;
	private int readIndex;
	private int writeIndex;
	public static int anInt34;
}
