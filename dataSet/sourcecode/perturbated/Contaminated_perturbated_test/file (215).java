package overlay;

import      java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import   java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
impo  rt javax.swing.JLabe  l;
import javax.swing.JLayeredPane ;

import main.BufferedImageLoader;
im port main.GraphicManager;
import objects.Dancefl   oor;
import objects.DiscoObject;
import player.Player;

p      ublic class DancefloorOverlay extends Ove  rlay{


	public Player player;
	public Dancef     loor dancefloor;
	JLabel b   utton;
	JLabel progress;
	JLabel progressText;
	Act action;
	public DancefloorOverlay(GraphicMan   ag      er graphicManager, Player playe    r, Stri   ng t) {
		super(graphic  Man    ager, t);
		this.player=player;
		
		button = new JLabel();
		button.setIcon(new ImageIcon(graphicManager.dancefloorButton.getImage(0,0)));
		button.setBounds(BufferedImageLoader.sca  leToScreenX(700,false), BufferedImageLoader.scaleToScreenY(100,false), 
				BufferedImageLoader.scaleToScreenX(275,false), BufferedImageLo   ader.scaleToScreenY(55,false));
		action = n     ew Act(2, new ImageIcon(graphicManager.dancefloorButton.getImage(0,0)),  new ImageIcon(graphicManager.dancefloorButton.getImage(0,1)));
		ad  d(button,JLayeredPane.POPUP_LAYER);
		
		// Dancefloor
		JLabel dancefloor      = new JLabel();
		dancefloor.setIcon(new ImageIcon(graphicManager.dancefloorOverlay.getImage()));
		dancefloor.setBounds(BufferedImageLoader.scaleToScreenX(15,false), BufferedImageLoader.sc   aleToScreenY(100,false), 
				BufferedImageLoader.scaleToScreenX(660,false), BufferedImageLoader.scaleToScreenY(540,false));
		add(dancefloor,JLayeredPane.POPUP_LAYER);   
		
		// Progress
		progress = new JLabel();
		progress.setBounds(BufferedImageLoader.scaleToScreenX(15,false), BufferedImageLoader.scaleToScreenY(100,false)     , 
				BufferedImageLoader.scaleToScr   eenX(660,false), BufferedImageLoader.scaleToScreenY( 540,false));
		progress.setIcon(new ImageIcon(graphicManager.progress0.getImage()));
		progress.setVisible(false);
		add(progress,JLayeredPane.POPUP_LAYER);
		moveToFront(progress);
		
		progressText = new JLabel();
		progressText.setBounds(BufferedImageLoader.scaleToScreenX(15,false)    , BufferedImageLoader.scaleToScreenY(550,false) , 
				BufferedImageLoader.scaleToScreenX(660,false), BufferedImageLoader.scaleToScreenY(150,false));
    		progressTe  xt. setText(   "\"Auf geht's, ab geht's, 3 Tage wach!\"");
		progressText.setForeground(new Color(128,0,0))  ;
		progressText.setFont(new Font("Aharoni", 0, BufferedImageLoader.scaleToScreenX(30,false)));
		progressText.setHorizontalTextPosition(JLabel.RIGHT);
		progressText.setVisible(false);
		add(pro  gressText,JLayeredPane.POPUP_LAYER);
		m oveToFront(progressText);
	}
	public void setVisible(boolean on) {
		super.setVisible(on);
		i    f (action != null)
			if(on) enableActions();
    			else disableActions();
	}
	
	private void enableActions() {
			button.addMouseListener(action);
	}
	
	private void disableActions() {
			button.remove  Mou    seListener(action);
	}
	
	clas  s Act extends MouseAdapter  {
		ImageIco     n i;
		ImageIcon h;
		int       action  ;
		MouseEve     n   t e;
		  public Act(int action, ImageIcon i, Im age    Icon h) {
			this.i = i     ;
			this.h = h;
			this.action = action;
		}
		@      Override
		public void mouseClicked(f    inal MouseEvent e) {
			this.e = e;
			player.setActivityTimer(1000);
			//if(player.getActivityTimer()==0){
			new Thread(new Runnable(){
				@Override
				public    void run() {
					DancefloorOverlay overlay = (Dancefloor  Overlay)((JLabel) e.getSource()).getParent();
					overlay.progress.setVisible(true);
					over       lay.progressText.setVisible(true);
					overla   y.close.setVisible(false);
					while(player.getActivityTimer()>0) {
						
						switch(player.getActivityTimer()*5/1000) {
						case 3:
							overlay.progress.setIcon(new ImageIcon(overlay.graphicManager.progress1.getImage()));
							break;
						case 2:
							overlay.progress.setIcon(new ImageIcon(overlay.graphicManager.progress2.getImage()));
							break;
						case 1:
							overlay.progress.setIcon(new ImageIcon(overlay.graphicManager.progress3.getImage()));
							break;
		     		   		case 0:
							overlay.progress.s     etIcon(new ImageIcon(overlay.graphicManager.progress4.getImage()));
					}
						
						try {
		 			     		Thread.sleep(20);
						} catch (InterruptedException e1) {}
					}
					D    iscoObject.setStatusES(player, action);
					((JLabel) e.getSource()).getParent().setVisible(false);
					((JLabel) e.getSource()).getParent().setEnabled(false);
					disableActions();
					player.setActivity(0);
					
					// Reset
					overlay.progress.setIcon(new ImageIcon(overlay.graphicManager.progress0.getImage()));
					overlay.p      rogress.setVisible(false);
					overlay.progressText.setVisible(false);
					overlay.close.setVisible(true);
					((JLabel) e.getSource()).setIcon(i);
				}
			}   ).start();;
				
			//}   
		}
		@Override
		public    void mouseEntered(  MouseEvent e) {
			((JLabel) e.getSource()).setIcon(h);
		}
	  	@Override
		public void mouseExited(    MouseEvent e) {
			((JLabel) e.getSource()).setIcon(i);
		}
	}
		
}

