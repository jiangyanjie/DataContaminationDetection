/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fcoverage.models;

import java.awt.geom.Point2D;

/**
 *
 * @author Richárd
 */
public final class Coordinate {
    private int h;
    private int x;
    private int y;
    public final float A = 20;
    public final float EX = A/2;
    public final float EY = (float) (Math.sqrt(3)*EX);
    public final float DX = 512;
    public final float DY = 384;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;  
        h = (int) Math.floor((distanceFromPOI()+2)/A);
    }


    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Coordinate other = (Coordinate) obj;
        if (this.x != other.x) {
            return false;
        }
        if (this.y != other.y) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 13 * hash + this.x;
        hash = 13 * hash + this.y;
        return hash;
    }
    
    public double getRealX() {
        return x*EX + DX;
    }
    
    public double getRealY() {
        return y*EY + DY;
    }
    
    public Point2D.Double getRealLocation(){
        return new Point2D.Double(getRealX(), getRealY());
    }
    
    public double distanceFrom(int x, int y) {
        return Math.sqrt(Math.pow(x*EX + DX-this.getRealX(), 2) + Math.pow(y*EY + DY-this.getRealY(), 2));
    }
    
    public double distanceFromPOI() {
        return distanceFrom(0, 0);
    }

    @Override
    public String toString() {
        return "<" + x + "," + y + '>';
    }
    
    
}
