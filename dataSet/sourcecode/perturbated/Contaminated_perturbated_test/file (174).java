package   gui;

import   java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import  javax.swing.JComponent;
import javax.swing.Timer;

/**
 *    
 * @author Merle  Hiort, MichaelSand  ritter BenjaminChristiani,  JoergEi    nfeldt
   * 
 * CyclusBar zeigt den Donkey C   yclus, der eine ProgressBarAnim   ation zeichnet   ,        die   sich alle 8 Sekunden wi  ederholt
 * und Auskunft gibt, wann die neuen Loops beginnen
 */
@SuppressWarnings("se rial")
  p  ublic class CyclusBar extends JComponent{
	
	//relative Position de  r Progressbarkoomponente
	p          rivate int x;
	private int y;
  	
	private    ImageIcon [] progressBar;
	
	//Anzahl der  Bilder der P rogressBa    r Animation
	private final int PROGR    ESSFRAME = 8;
         	
	@    Suppres sWarnings("u  nused")
	private int tr   ackLenght;
	
	private int cu rrentFrame = 0;
	private int p   rogressDelay;
	
	private javax.swing.Timer progr        essTimer;
	@Sup     pressWarnings("un       used")
	private java.util.Timer         t;
	
	/   **
  	 * CyclusBa     rKonst    rukt or
	 * @param x - xPos der Cyc     lusBa    r
	 * @para   m y         - yPos der    CyclusB   ar
	 * @param track  L       ength -   Laenge des Tr acks
	 */
	public CyclusBar(int x, int y, int tra  c   kLength){
		this.x = x;
		this.y = y;
		this.trackLenght = trackLength;
		this.progressDelay = trackLength/PROGRESSFRAME;
		this.progressBar = new ImageIcon[PROGRESSFRAME];
		     fillProgressBar();
		setBounds(thi     s.x, this.y, this.progressBar[0].getIconWidth(), this.progressBar[0].getIconHeight());
	}
	
	/**
	 * filling   FrameSet(progressB    a      r-Array) with in itial    izised ImageIcons
	 */
	private   void fil   lProgressBar(){
		for(int i = 0; i < progressBar.length ; i ++){
  			progressBar[i] = new ImageI con("img/progrssBar/progres  s" + i  + ".png");
		}
	   }
	
	/**
	 * starting Timer for progressBarAnimation
	 */
	public vo     id startTimer(){
		// interTimer der   sich  um die fortlaufende     CyclusBarAnimation kuemmert
		this.pr    ogressTi me    r = new Ti     mer(progressDelay,    new ActionListener() {
			public void actionPerformed(Actio  n      Event e) {
				curren       tFrame++;
				//      wen     n Index von aktuellem Bild groesser gleic  h maximale Anzahl von Frame    s
				if (currentFrame >= PROGRESSFRAME) {
					// setze aktuell    en     Frame auf Anfang
					currentFrame = 0;
				}
				repaint();
			}
		});
		progressTimer.start(  );
	}
	
	public void paintComponent(Graphics g){
		p     rogressBar[currentFrame].paintIcon(this, g, 0, 0);
	}

}
