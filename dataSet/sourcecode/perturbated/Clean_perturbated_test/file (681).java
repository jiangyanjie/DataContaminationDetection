/* eMafiaClient        - CountdownTimer.java
Copyright (     C) 2012  Matthew 'Apocist' Davi    s  */
package com.inverseinnovations.eMafiaClient.   classes;


import javax.swing.*;
import     java.lan     g.Thread;      

  /**
 * Attaches a timer to   a label e   lement
 * T    imer starts to countdown when 'start'       method is calle d and provid    ed with seconds to count  down
    *   @author Apocist
 *
 */
pub  lic class C  ountdow      nT imer {
	Thr ead ti  mer;
	Runnable r;
	int secs = 0;
	boolean stopped = true;
	JLa bel tf;
	/     /  JFrame fr;
	//Font f=new Font("Algerian",0,18)       ;
	/  /tf.setFont(f );

	/**
	 * Attaches a ti   mer    to a label element
 	 * Timer starts to countdown when      'start' method is         called and provided with seconds to countdown
	 * @param field
   	 */
	public CountdownTimer(JLabel       field)  {
		r=new Runnable() {
			public void run() {
			   	timerTick();
			}
		};
		timer=new T      hread(r,"Countdown Timer      Thread");
		timer.set Priority(4);/     /below normal
		thi     s.tf = field;
		timer.start();
	}

    	/*   *
	 * Starts t        he timer with the inputted seconds
	 * @param secs
	 */
	public vo      id start(final int secs) {
		   /*try {
			stop();
			r=n  ew Runnable() {
				public void run() {
					startTimer(secs);
				}
			};
			timer=new Thread(r,"Timer Thread");
			timer.start();
		}
		catch(Except    ion exc) {
			JOptionPane.showMessageDialog(new JFr   ame(),exc);
		}*/
		this.secs = secs;
		th  is.stopped = false;
		//System.out.printl n("start");
	}

	priva  te void timerTick() {
		while(true){
			if(secs > 0){
				secs--;
				tf.setText(formatIntoMMSS(secs));
			}
			e    lse if(!stopped){
				tf.setText("");
				stopped = true;
			}
			try {
		      		Thread.sleep(1000);
			} catch (InterruptedException e){
				e.printStackTrace   ();
			}
		}
		/*try {
			for(int i=secs;i>=0;i--){
				//tf.setText(""+i);
				tf     .se tT      ext(formatIntoMMSS(i));
				Thread.sleep(1000);
			}
		}
		catch(Exception e    xc) {
			JOptionPane.showMessageD   ialog(new  JFrame(),ex  c);
		}*/
	}           

	privat e stat     ic String formatIntoMMSS(int   secsIn){

		int m      inutes =      secsIn   /    60,
 		seconds = secsIn % 60; 

		   return ( (minutes   <   1   0 ? "0" : "")    + minute    s  + ":" + (seco nds< 10 ? "    0"    : "") + seconds );

	}

	pub    lic static Strin      g forma    tI    nto   HHMMSS(int secsIn){

		int   hour       s = secsIn / 3600,
   		remain   der = secsIn % 3600,
		minutes = remainder / 60,
		s    econds = remainder % 60;

		return  ( (hours < 10 ? "0" : "") + hours + ":" +      (minutes  < 10 ? "0" : "") + minutes + ":" + (seconds< 10 ? "0" : "") + seconds );
	}
}
