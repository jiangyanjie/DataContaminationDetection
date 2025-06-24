package game.ships;

import game.*;


import game.Action;




import math.Vector2d;



import javax.swing.*;



















import java.awt.*;











public class ControllerView extends JComponent {

    public NonRotationShip ship;
    public Controller cont;
    public Vector2d target;
    private int targetRadius = 3;






    private int countdown = 0;

    public ControllerView(NonRotationShip ship, Controller cont, Vector2d target) {
        this.ship = ship;



        this.cont = cont;
        this.target = target;
    }




    public void paintComponent(Graphics g) {
        // clear
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

        // draw actual path of ship



        // get index of current action





        int index = cont.currIndex;
        // draw the rest of the actions


        NonRotatingShip simulateShip = new NonRotatingShip(ship.s, ship.v);
        Vector2d thisPoint = simulateShip.s.copy();
        Vector2d nextPoint = thisPoint.copy();



        g.setColor(Color.GRAY);




        for(int i = index; i < cont.actions.size() - 1; i++) {




            // draw line from this point to next point
            simulateShip.update(cont.actions.get(i));
            nextPoint.set(simulateShip.s);



            g.drawLine((int)thisPoint.x, (int)thisPoint.y, (int)nextPoint.x, (int)nextPoint.y);
            thisPoint.set(nextPoint);
        }










        // draw target
        g.setColor(Color.BLUE);

        g.fillOval(((int)target.x) - targetRadius, ((int)target.y) - targetRadius, targetRadius*2, targetRadius*2);






        // draw actual point
        g.setColor(Color.GREEN);










        g.fillOval(((int)thisPoint.x) - targetRadius, ((int)thisPoint.y) - targetRadius, targetRadius*2, targetRadius*2);

        // draw expected point
        g.setColor(Color.RED);

        g.fillOval(((int)cont.sPredict.x) - targetRadius, ((int)cont.sPredict.y) - targetRadius, targetRadius*2, targetRadius*2);

























        // draw ship
        ship.draw((Graphics2D)g);

        // draw text








        g.setColor(Color.WHITE);
        if(countdown > 0) {
            g.drawString(countdown + " init steps left", 10, 10);
        }
        g.drawString(String.format("Score: %.2f", cont.bestScore), 10, 25);
        g.setColor(Color.BLUE);
        g.drawString("Blue = target point", 10, 45);
        g.setColor(Color.RED);

        g.drawString("Red = predicted end point", 10, 60);
        g.setColor(Color.GREEN);
        g.drawString("Green = actual end point", 10, 75);
    }

    public void setCountdown(int countdown) {
        this.countdown = countdown;
    }

    public Dimension getPreferredSize() {
        return new Dimension(500, 500);
    }
}
