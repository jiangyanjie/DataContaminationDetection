package gui;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.Timer;

/**
 * 
 * @author MerleHiort, MichaelSandritter BenjaminChristiani, JoergEinfeldt
 * 
 * CyclusBar zeigt den Donkey Cyclus, der eine ProgressBarAnimation zeichnet, die sich alle 8 Sekunden wiederholt
 * und Auskunft gibt, wann die neuen Loops beginnen
 */
@SuppressWarnings("serial")
public class CyclusBar extends JComponent{
	
	//relative Position der Progressbarkoomponente
	private int x;
	private int y;
	
	private ImageIcon [] progressBar;
	
	//Anzahl der Bilder der ProgressBar Animation
	private final int PROGRESSFRAME = 8;
	
	@SuppressWarnings("unused")
	private int trackLenght;
	
	private int currentFrame = 0;
	private int progressDelay;
	
	private javax.swing.Timer progressTimer;
	@SuppressWarnings("unused")
	private java.util.Timer t;
	
	/**
	 * CyclusBarKonstruktor
	 * @param x - xPos der CyclusBar
	 * @param y - yPos der CyclusBar
	 * @param trackLength - Laenge des Tracks
	 */
	public CyclusBar(int x, int y, int trackLength){
		this.x = x;
		this.y = y;
		this.trackLenght = trackLength;
		this.progressDelay = trackLength/PROGRESSFRAME;
		this.progressBar = new ImageIcon[PROGRESSFRAME];
		fillProgressBar();
		setBounds(this.x, this.y, this.progressBar[0].getIconWidth(), this.progressBar[0].getIconHeight());
	}
	
	/**
	 * filling FrameSet(progressBar-Array) with initializised ImageIcons
	 */
	private void fillProgressBar(){
		for(int i = 0; i < progressBar.length; i ++){
			progressBar[i] = new ImageIcon("img/progrssBar/progress" + i + ".png");
		}
	}
	
	/**
	 * starting Timer for progressBarAnimation
	 */
	public void startTimer(){
		// interTimer der sich um die fortlaufende CyclusBarAnimation kuemmert
		this.progressTimer = new Timer(progressDelay, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				currentFrame++;
				// wenn Index von aktuellem Bild groesser gleich maximale Anzahl von Frames
				if (currentFrame >= PROGRESSFRAME) {
					// setze aktuellen Frame auf Anfang
					currentFrame = 0;
				}
				repaint();
			}
		});
		progressTimer.start();
	}
	
	public void paintComponent(Graphics g){
		progressBar[currentFrame].paintIcon(this, g, 0, 0);
	}

}
