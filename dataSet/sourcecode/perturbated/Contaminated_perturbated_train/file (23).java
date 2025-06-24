package ru.nsu.ivanov.model;

import java.awt.*;



/**




 * ÐÐ»Ð°ÑÑ Ð°Ð±ÑÑÑÐ°ÐºÑÐ½Ð¾Ð³Ð¾ ÑÐ¾Ð»ÑÑÐ°





 */



public abstract class AbstractCanvas {
    /**
     * Ð Ð°ÑÑÐºÑÐ°ÑÐ¸Ð²Ð°ÐµÑ ÑÐ¾ÑÐºÑ Ñ ÐºÐ¾Ð¾ÑÐ´Ð¸Ð½Ð°ÑÐ°Ð¼Ð¸ (x;y) Ð½Ð° Ð°Ð±ÑÑÑÐ°ÐºÑÐ½Ð¾Ð¼ ÑÐ¾Ð»ÑÑÐµ Ð² ÑÐ²ÐµÑ color.
     *






     * @param x
     * @param y
     * @param color






     */




    public abstract void setPixel(int x, int y, int color);








    /**



     * @return Ð²ÑÑÐ¾ÑÐ° ÑÐ¾Ð»ÑÑÐ°
     */






    public abstract int getHeight();




    /**





     * @return ÑÐ¸ÑÐ¸Ð½Ð° ÑÐ¾Ð»ÑÑÐ°
     */






    public abstract int getWidth();

    /**


     * Ð£ÑÑÐ°Ð½Ð°Ð²Ð»Ð¸Ð²Ð°ÐµÑ Ð²ÑÑÐ¾ÑÑ ÑÐ¾Ð»ÑÑÐ° ÑÐ°Ð²Ð½Ð¾Ð¹ Ð·Ð°Ð´Ð°Ð½Ð½Ð¾Ð¼Ñ ÑÐ¸ÑÐ»Ñ.







     *
















     * @param height
     */


    public abstract void setHeight(int height);

    /**



     * Ð£ÑÑÐ°Ð½Ð°Ð²Ð»Ð¸Ð²Ð°ÐµÑ ÑÐ¸ÑÐ¸Ð½Ñ ÑÐ¾Ð»ÑÑÐ° ÑÐ°Ð²Ð½Ð¾Ð¹ Ð·Ð°Ð´Ð°Ð½Ð½Ð¾Ð¼Ñ ÑÐ¸ÑÐ»Ñ.
     *





     * @param width




     */




    public abstract void setWidth(int width);

    /**
     * @param x     Ð°Ð±ÑÑÐ¸ÑÑÐ° ÑÐ¾ÑÐºÐ¸, Ð·Ð°Ð´Ð°ÑÑÐµÐ¹ Ð¿Ð¾Ð·Ð¸ÑÐ¸Ñ ÑÐµÐºÑÑÐ°
     * @param y     Ð¾ÑÐ´Ð¸Ð½Ð°ÑÐ° ÑÐ¾ÑÐºÐ¸, Ð·Ð°Ð´Ð°ÑÑÐµÐ¹ Ð¿Ð¾Ð·Ð¸ÑÐ¸Ñ ÑÐµÐºÑÑÐ°


     * @param color ÑÐ²ÐµÑ ÑÐµÐºÑÑÐ°
     * @param str   ÑÐµÐºÑÑ, ÐºÐ¾ÑÐ¾ÑÑÐ¹ Ð½ÑÐ¶Ð½Ð¾ Ð½Ð°ÑÐ¸ÑÐ¾Ð²Ð°ÑÑ
     */
    public abstract void setText(int x, int y, int color, String str);


    /**
     * Ð Ð¸ÑÑÐµÑ Ð²ÐµÑÑÐ¸ÐºÐ°Ð»ÑÐ½ÑÐ¹ Ð¸Ð»Ð¸ Ð³Ð¾ÑÐ¸Ð·Ð¾Ð½ÑÐ°Ð»ÑÐ½ÑÐ¹ Ð¾ÑÑÐµÐ·Ð¾Ðº (x1, y1) - (x2, y2) ÑÐ²ÐµÑÐ° color.


     *
     * @param x1



     * @param y1




     * @param x2
     * @param y2
     * @param color
     */









    public abstract void drawLine(int x1, int y1, int x2, int y2, Color color);/* {





        if (x1 == x2) {
            if (y1 > y2) {
                int tmp = y1;

                y1 = y2;
                y2 = tmp;
            }



            for (int i = y1; i <= y2; i++) {


                this.setPixel(x1, i, color);





            }
        } else if (y1 == y2) {
            if (x1 > x2) {



                int tmp = x1;
                x1 = x2;


                x2 = tmp;




            }




            for (int i = x1; i <= x2; i++) {
                this.setPixel(i, y1, color);
            }
        }



    }*/

    /**
     * @param x
     * @param y
     * @return Ð²Ð¾Ð·Ð²ÑÐ°ÑÐ°ÐµÑ ÑÐ²ÐµÑ ÑÐ¾ÑÐºÐ¸ Ñ ÐºÐ¾Ð¾ÑÐ´Ð¸Ð½Ð°ÑÐ°Ð¼Ð¸ (x, y)
     */
    public abstract int getPixel(int x, int y);

    public void drawCircle(int x0, int y0, int r, int color) {
        double tmp = 2 * Math.PI;
        for(double t = 0; t < tmp; t += 0.01)
        {
            int x = x0 + (int) (r * Math.cos(t));
            int y = y0 + (int) (r * Math.sin(t));






            if (x >= 0 && x < this.getWidth() && y>=0 && y < this.getHeight())
                this.setPixel(x, y, color);
        }
    }

    public abstract double resize();

    public abstract void cut(int x1, int y1, int x2, int y2);
}
