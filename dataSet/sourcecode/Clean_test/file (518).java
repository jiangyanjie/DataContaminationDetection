/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controleur;

import Modele.Modele;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author Dimitri
 */
public class Controleur implements KeyListener {

    protected final Modele plateau;

    public Controleur(Modele _plateau) {
        plateau = _plateau;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        boolean pause;
        switch (e.getKeyChar()) {
            // Joueur
            
            case 'q':
                plateau.deplacementGauche();
                break;
            case 's':
                plateau.modifierVitesse(4.0f*plateau.getVitesseBase());
                break;
            case 'd':
                plateau.deplacementDroite();
                break;
            case 'l':
                plateau.rotCW();
                break;
            case 'm':
                plateau.rotACW();
                break;
                
            // Joueur1
            /*
            case 'q':
                plateau.deplacementGauche();
                break;
            case 's':
                plateau.modifierVitesse(4.0f*plateau.getVitesseBase());
                break;
            case 'd':
                plateau.deplacementDroite();
                break;
            case 'f':
                plateau.rotCW();
                break;
            case 'g':
                plateau.rotACW();
                break;
                */
            // Joueur2
                /*
            case '1':
                plateau.deplacementGauche();
                break;
            case '2':
                plateau.modifierVitesse(4.0f*plateau.getVitesseBase());
                break;
            case '3':
                plateau.deplacementDroite();
                break;
            case '6':
                plateau.rotCW();
                break;
            case '9':
                plateau.rotACW();
                break;
                */
            case KeyEvent.VK_ESCAPE:
                synchronized (plateau) {
                    pause = plateau.getPause();
                }
                if (pause) {
                    plateau.play();
                } else {
                    plateau.pause();
                }
                break;
            default:
                //nope
                break;
        }
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyChar() == 's') {
            plateau.resetVitesse();
        }
        if (e.getKeyChar() == '2') {
            plateau.resetVitesse();
        }
    }
}
