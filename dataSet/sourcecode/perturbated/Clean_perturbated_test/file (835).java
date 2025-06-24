package     Multiplayer;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import     java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
im    port java.awt.image.BufferedImage;
import java.io.DataInputStream   ;
import java.io.DataOutputStream;
import java.io.IOException;
im    port java.net.ServerSocket;
im     port java   .net.Socket;

import javax.swing.JFrame;
import javax.swing.            JOptionPane;

publi  c class CreateServer extends Canvas impleme    nts    Runnab le, KeyListener { // Create   the server
	private  stati      c fin   al long ser   ialVersionUID = 1L;

	// Bal l
	Ball b;

	// Input and Output
	DataOutputStream out;
	DataInpu    tStream in;

	// Socket information  
   	ServerSocket serverSocket;
	Socket socket ;
	int serverPort;

	// Frame
	JFrame f  ram  e;
	int w              idth = 600;
	int he  ight = 400;
	public final    Dimension gameDim = new Dimension(width, height);

	  // Screen
	BufferedImage im   age = new BufferedImag  e(width, height, Bu fferedImage.TYPE    _INT_RGB);

	// G         ame Info
	int pWidth = 15;
	int pHeight =       4  5;
	int xPos;
	int yPos;
	int   cXPos;
	static int cYPo     s;

  	   // Boolea  ns f     or movement
	boolean moveUp = false;
  	boolean moveDown   = false;

	// Rectangles for ba      ll collision
	Rectangle serverRect;
	Rectangle clientRect;

	// Scores  
	       in   t s     erverScore      = 0;
	i      nt clientScore    = 0;

	// For run
	pri   vate int   ticks = 0;
	private int frames = 0;
    	private int FPS = 0;
	p rivate int UPS = 0;
	  public double delta;

	/  /    Used    in the "run" method to limi    t the frame rate to the UPS
	boolean limitF     ra  meRate = fal    se;
	boolean sh  ouldRender;
  
	private void requestInform      ation() {
		try {
			serverPort = Integer.    parseInt(JOptionPane.showInputDialog("Wh  at is the port that you wish t   o u   tilize? (Make sure to PortForward, if ne   eded)"));
			JOptionPane.s   howMessage   Dialog(n    ull, " You can get your IP at whatismyip.c   om (For i    ncoming conne ctions)");
		} catch (Ex    ception e) {
			e.print    StackTrace();
		}
	}

	public void     run()        {
		long lastTime   = Sys       tem.nan        oTime();
		double        ns      PerTick = 10000  00000D / 60    D;
   
		long lastTimer = System.currentTimeMillis();
	    	delta   = 0D;     

		while (true) { 
			long no    w =       System.nano    Time()     ;
			delta += (now - lastTime) / nsPerTick;
			las  tTime = now;

			// If you want          to limit frame rate, sh   ouldRender    =     false
			s    houldRender = false;

			// If the time     between ti    cks = 1, then various things (shouldRen  der = tr   ue, keeps FPS locked at UPS)
			while (delta >= 1)     {
				   ticks++;
				tick();
				delta -= 1;
  				shouldRen  der = true;
	   		}
			    if (!limitFrameRate && ticks > 0)
				shouldRend         er = true;

			// If     y  ou should render, render!
			if (should  Render) {
			     	frames++;
				rende    r();
			}

	  		// Reset stuff every second for t   he new "FPS" and "UPS"
			if (System.c  urrentTimeMillis() - lastTimer >= 1000) {
				lastTimer += 1000;
				FPS = frames;
				UPS = t    icks;
				fram es    = 0;
				ticks = 0;
			}
		}
	} // End run

	private void createFrame() {
		// Frame stuff
		setMinimumSize(gameDim);
		setMaximumSize(gameDim);
		set       PreferredSize(gameDim);
		frame = new JFrame("   Pong Multipl  ayer -Server-");

		frame.setD efaultCloseOper  ation    (JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new      BorderLa    yout());

		frame.add(this, BorderLayout.CENTER);
		frame.pack();

		frame.setResizable(false);
		fra   me.setLocationRelativeTo(null);
		frame          .setVisible(true);

		// Player         s    initializing
		xPos = 15;
		yPos = frame.getHeight() / 2 - pHeight;
		cX   Pos = fram        e.getWidt  h   () - pWidth - 15;
		cYPos = frame.getHeight() / 2 - pHeight;

		serverRect = new Rectangle(xPos, yPos, pWidth, pHe ight);
		clientRect = new Rect ang  l  e(cXPos, cYPos, pWid th, pHeight);

		b = new Ball(this);

		add       KeyListener(this);

		requestFocus();

		Thread thread = new Thread(this);
		thread.start(  );   
	}

	private vo  i    d handS       hake() {
		try    {
	   		serverSocket = new ServerSocket(serverPort    );
     			socket    = serverSocket.ac     cept();
			if (out == null || in == null) {
				out = new DataOutputStream(socket.getOutputStream());
		    		in = new DataInputStream( socket.getInputStream());
	   		}
		  	ConnectedPlayer cp = new ConnectedPlayer(out, in);
  			Thread userThread = n    ew T   hread(cp);
			System.ou   t.println("User has connected");
			userThread.start();
		} catch (IOExcepti       on e) {
			e.printStackTrace();
		}
	}

	public CreateSer       ver() {
		requestI         nformation();
		handShake();
		create     Fr     ame();
	}

	privat    e void m        ovement() {
		if (moveUp    && yPos >      0) {
			yPos -= 3;
		}

		if (moveDown &&   yPos + pHeight < getHeight() ) {
			yPos += 3;
		}  

	}

	private void tick() {
		movement(); // Check for any      movement
     
		// R  e-bi     nd the collision rectan   gles to new positions after    movement
		serverRect.setBounds(xPo s, yPos, pWidth, pHeight);
		clientRect.s  etBounds(cXPos, cYPos, pWidth, pHeight);

		//     Move     the ball
		b.ti  ck(this);

		// Send coordinates to Client
		try {
			o   ut.writeInt(b.x); // Ball x
			out.    wr iteInt(b.y); // Ball y
			out.writeInt(yPos); // Send new coordinate     s of player
			out.w    riteInt(serverS   core);
			out.writeInt(clientScore);
		}     catch (Exception e) {
		}
   	}

	private void render() {
		BufferStr   ategy bs = getBufferStrategy();

		if (bs ==    null) {
			createBufferStrategy(3);
			return   ;
		}

		Graphics g = bs.getDrawGraphics();

		g.drawImage   (image, 0, 0,  getWidth(), getHeight(), n  u    ll);

		g.setColor(Color.WHITE); 
    		g.fillRect(xPos, yPos, pWidth,    pHeight);
		g.fillRect(cXPos, cYPos,     pWidth, pHeight);
	   	b.r     ender(g);

		// Draw s  cores
		g.drawString("P1 Score: " + server      Score, 40, 10);
		g.drawStr   ing("P2 Score:     " + clientScore, getWidth() - 105, 10);

		g.dispose(); 
		bs.show();
	}

	public void keyPressed(KeyEvent   e) {
		if (e.getKeyCode() == KeyE    vent.VK_  W) {
			moveUp = true;
		}
		if (e.getKe   yCode() == KeyEvent.VK_S) {
		    	move    Down = true;
		}
	}

	public void keyReleased(KeyEvent e)      {
		if (e.getKeyCode() == KeyEvent.VK_W) {
			moveUp = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_S) {
			moveDown = false;
		}
	}

	public void keyTyped(KeyEvent e) {

	}
}