package ru.nsu.ivanov.model;

import java.awt.*;

/**
 * Класс абстрактного холста
 */
public abstract class AbstractCanvas {
    /**
     * Расскрашивает точку с координатами (x;y) на абстрактном холсте в цвет color.
     *
     * @param x
     * @param y
     * @param color
     */
    public abstract void setPixel(int x, int y, int color);

    /**
     * @return высота холста
     */
    public abstract int getHeight();

    /**
     * @return ширина холста
     */
    public abstract int getWidth();

    /**
     * Устанавливает высоту холста равной заданному числу.
     *
     * @param height
     */
    public abstract void setHeight(int height);

    /**
     * Устанавливает ширину холста равной заданному числу.
     *
     * @param width
     */
    public abstract void setWidth(int width);

    /**
     * @param x     абсцисса точки, задающей позицию текста
     * @param y     ордината точки, задающей позицию текста
     * @param color цвет текста
     * @param str   текст, который нужно нарисовать
     */
    public abstract void setText(int x, int y, int color, String str);

    /**
     * Рисует вертикальный или горизонтальный отрезок (x1, y1) - (x2, y2) цвета color.
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
     * @return возвращает цвет точки с координатами (x, y)
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
