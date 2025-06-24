/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controleur;

import Modele.ModeleDeuxJoueurs;
import Modele.Modele;
import java.awt.event.KeyEvent;

/**
 *
 * @author 4lexandre
 */
public class ControleurDeuxJoueurs extends Controleur {

    protected final Modele plateau2;
    public ControleurDeuxJoueurs(ModeleDeuxJoueurs _plateaux) {
        super(_plateaux.getPlateau1());
        plateau2 = _plateaux.getPlateau2();
    }
    
    
    @Override
    public void keyTyped(KeyEvent e) {
        boolean pause;
        switch (e.getKeyChar()) {
            // Joueur1
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
                
            // Joueur2
            case '1':
                plateau2.deplacementGauche();
                break;
            case '2':
                plateau2.modifierVitesse(4.0f*plateau2.getVitesseBase());
                break;
            case '3':
                plateau2.deplacementDroite();
                break;
            case '6':
                plateau2.rotCW();
                break;
            case '9':
                plateau2.rotACW();
                break;
                
            case KeyEvent.VK_ESCAPE:
                synchronized (plateau) {
                    pause = plateau.getPause();
                }
                synchronized(plateau2)
                {
                    pause |= plateau2.getPause();
                }
                if (pause) {
                    plateau.play();
                    plateau2.play();
                } else {
                    plateau.pause();
                    plateau2.pause();
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
            plateau2.resetVitesse();
        }
    }
    
    
    
}
