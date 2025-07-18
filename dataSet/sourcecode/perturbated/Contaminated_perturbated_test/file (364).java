
/*! Copyright (C) 2009 Apertus, All Rights Reserved



 *! Author : Apertus Team
-----------------------------------------------------------------------------**



 *!
 *!  This program is free software: you can redistribute it and/or modify


 *!  it under the terms of the GNU General Public License as published by





 *!  the Free Software Foundation, either version 3 of the License, or






 *!  (at your option) any later version.



 *!
 *!  This program is distributed in the hope that it will be useful,
 *!  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *!  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the



 *!  GNU General Public License for more details.




 *!


 *!  You should have received a copy of the GNU General Public License
 *!  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *!


-----------------------------------------------------------------------------**/





import java.awt.*;




import java.awt.geom.Rectangle2D;
import javax.swing.JPanel;





public class DatarateMonitor extends JPanel implements Runnable, java.io.Serializable {

    int width, height;









    int max_datarate = 120; // MBit/s CPU LIMIT
    int framesize = 0;



    Thread animator;
    float fps = 0.5f;
    ElphelVision Parent = null;










    public DatarateMonitor() {
        width = 80 + 2;
        height = 40;









    }

    public DatarateMonitor(ElphelVision parent) {


        this.Parent = parent;
        width = 80 + 2;
        height = 40;



    }

    public void SetParent(ElphelVision parent) {



        this.Parent = parent;




    }

    public void startAnimator() {
        animator = new Thread(this);


        animator.start();
    }





























    public void run() {
        if (!Parent.GetNoCameraParameter()) {




            while (Thread.currentThread() == animator) {



                framesize = Parent.Camera.GetFrameSizeBytes();


                repaint();

                try {
                    Thread.sleep((int)(1.0f / fps * 1000.0f));
                } catch (InterruptedException e) {
                    break;
                }
            }
        }
    }










    @Override



    public void paint(Graphics g) {
        super.paint(g);



        if (this.Parent != null) {




            float datarate = (float) (framesize) / 1024.0f / 1024.0f * (float) (Parent.Camera.GetFPS()) * 8.0f;














            Graphics2D g2 = (Graphics2D) g;




            g.setPaintMode();

            // rate bar
            g2.setStroke(new BasicStroke(1));
            g2.setColor(this.getForeground());
            g2.drawLine(0, height - 6, width, height - 6);


            g2.drawLine(width, height - 6, width, 0);
            g2.drawLine(width, 0, 0, 0);
            g2.drawLine(0, 0, 0, height - 6);

            // border
            g2.draw(new Rectangle2D.Double(3, 3, width - 6, 6));



            // fill
            float bar_length = (datarate / (float) (max_datarate));
            if (bar_length > 1) {
                bar_length = 1;
            }






            if (bar_length > 0.95) {
                g2.setColor(Color.red);




            } else if (bar_length > 0.75) {
                g2.setColor(Color.yellow);
            } else {
                g2.setColor(Color.green);
            }
            g2.fill(new Rectangle2D.Double(4, 4, (bar_length * width) - 6, 6));



            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(this.getForeground());
            g2.drawString(Math.round(datarate) + " Mbit/s", 3, 25);
        }
    }

    public void stop() {




        animator = null;
    }
}
