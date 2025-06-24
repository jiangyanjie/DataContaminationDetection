/*
 * Author: Andreas Bjoru, abjru2012@my.fit.edu
 * Course: CSE 4051, Fall 2013
 * Project: proj08, Lambda Lifting Game
 */
package impl.display;

import static impl.utils.UIUtils.display;
import static javax.swing.JOptionPane.PLAIN_MESSAGE;
import static javax.swing.JOptionPane.showMessageDialog;
import impl.utils.Tuple;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.EventListener;
import java.util.ResourceBundle;

import api.IGameController;

/**
 * Abstract factory responsible for creating different {@link EventListener}'s.
 * This class follows the 'abstract factory' pattern.
 */
public abstract class AbstractEventFactory {

   /**
    * Defines a factory capable of creation {@link ActionListener} instances.
    */
   interface EventFactory<T extends EventListener, V> {
      public T create (final V input);
   }

   /**
    * Returns a factory capable of creating an action that disposes the main
    * display (i.e. exits the swing event loop).
    */
   public static final EventFactory<ActionListener, Tuple<? extends Container, ResourceBundle>> EXIT_ACTION =
         new EventFactory<ActionListener, Tuple<? extends Container, ResourceBundle>> () {

            @Override
            public ActionListener create (
                  final Tuple<? extends Container, ResourceBundle> input) {
               return new ActionListener () {

                  @Override
                  public void actionPerformed (final ActionEvent e) {
                     display (input._1).dispose ();
                  }
               };
            }
         };

   /**
    * Returns a factory capable of creating an action that displays the about dialog.
    */
   public static final EventFactory<ActionListener, Tuple<? extends Container, ResourceBundle>> ABOUT_DIALOG_ACTION =
         new EventFactory<ActionListener, Tuple<? extends Container, ResourceBundle>> () {

            @Override
            public ActionListener create (
                  final Tuple<? extends Container, ResourceBundle> in) {
               return new ActionListener () {

                  @Override
                  public void actionPerformed (final ActionEvent e) {
                     final String title = in._2.getString ("dlg.about.title");
                     final String message = in._2.getString ("dlg.about.text");
                     showMessageDialog (display (in._1), message, title, PLAIN_MESSAGE);
                  }
               };
            }
         };

   /**
    * Returns a factory capable of creating a key listener that acts upon
    * predefined keys in order to control the game.
    */
   public static final EventFactory<KeyListener, IGameController> KEY_CONTROLS =
         new EventFactory<KeyListener, IGameController> () {

            @Override
            public KeyListener create (final IGameController controller) {
               return new KeyAdapter () {
                  @Override
                  public void keyReleased (final KeyEvent e) {
                     final int key = e.getKeyCode ();
                     switch (key) {
                     case KeyEvent.VK_DOWN:
                     case KeyEvent.VK_D:
                        controller.moveDown ();
                        break;
                     case KeyEvent.VK_UP:
                     case KeyEvent.VK_U:
                        controller.moveUp ();
                        break;
                     case KeyEvent.VK_LEFT:
                     case KeyEvent.VK_L:
                        controller.moveLeft ();
                        break;
                     case KeyEvent.VK_RIGHT:
                     case KeyEvent.VK_R:
                        controller.moveRight ();
                        break;
                     case KeyEvent.VK_W:
                        controller.moveWait ();
                        break;
                     case KeyEvent.VK_ESCAPE:
                     case KeyEvent.VK_A:
                        controller.abort ();
                        break;
                     default:
                     }
                  }
               };
            }
         };

}
