/**
 * Copyright (C) 2014 Aniruddh Fichadia
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 * 
 * If you use or enhance the code, please let me know using the provided author information or via
 * email Ani.Fichadia@gmail.com.
 */

package com.anifichadia.toolkit.geometry;

import java.io.Serializable;

import com.anifichadia.toolkit.utilities.MathUtils;

/**
 * Represents an x, y coordinate pair using doubles.
 * 
 * @author Aniruddh Fichadia | Email: Ani.Fichadia@gmail.com | GitHub Username: AniFichadia
 *         (http://github.com/AniFichadia)
 */
public class Coordinate2D implements Serializable
{
	// ============================= Attributes ==============================
	private static final long	serialVersionUID	= 1L;
	
	protected double			x					= 0d;
	protected double			y					= 0d;
	
	
	// ============================ Constructors =============================
	public Coordinate2D ()
	{}
	
	
	public Coordinate2D (double x, double y)
	{
		this.x = x;
		this.y = y;
	}
	
	
	// =============================== Methods ===============================
	/**
	 * Calculates the Euclidean distance between two coordinates
	 * 
	 * @param c1 First coordinate
	 * @param c2 Second coordinate
	 * 
	 * @return Euclidean distance between the coordinates
	 */
	public static double distanceBetween(Coordinate2D c1, Coordinate2D c2)
	{
		return MathUtils.euclideanDistance (new double[] {c1.getX (), c1.getY ()},
				new double[] {c2.getX (), c2.getY ()});
	}
	
	
	/**
	 * Calculates the Euclidean distance between this coordinate and another
	 * 
	 * @param other Other Coordinate to calculate the distance to
	 * 
	 * @return Distance between this coordinate and another
	 */
	public double distanceBetween(Coordinate2D other)
	{
		return distanceBetween (this, other);
	}
	
	
	/**
	 * Calculates the angle (in degrees) between two coordinates
	 * 
	 * @param c1 First coordinate
	 * @param c2 Second coordinate
	 * 
	 * @return Angle between both coordinates in degrees
	 */
	public static double angleBetween(Coordinate2D c1, Coordinate2D c2)
	{
		return MathUtils.angle2DBetween (new double[] {c1.getX (), c1.getY ()},
				new double[] {c2.getX (), c2.getY ()});
	}
	
	
	/**
	 * Calculates the angle (in degrees) between this coordinate and another
	 * 
	 * @param other Other Coordinate to calculate the angle between
	 * 
	 * @return Angle between this coordinate and another in degrees
	 */
	public double angleBetween(Coordinate2D other)
	{
		return angleBetween (this, other);
	}
	
	
	/**
	 * Rotates the {@link Coordinate2D} around the origin (coordinate (0, 0)) by a specific angle
	 * 
	 * @param angle Angle to rotate {@link Coordinate2D}
	 */
	public void rotate(double angle)
	{
		rotate (angle, 0d, 0d);
	}
	
	
	/**
	 * Rotates the {@link Coordinate2D} around a specific 2-dimensional coordinate by a specific
	 * angle
	 * 
	 * @param angle Angle to rotate {@link Coordinate2D}
	 * @param rotatePointX Rotation point x-coordinate
	 * @param rotatePointY Rotation point y-coordinate
	 */
	public void rotate(double angle, double rotatePointX, double rotatePointY)
	{
		if (this.equals (new Coordinate2D (rotatePointX, rotatePointY)))
			return;
		
		double theta = Math.toRadians (angle);
		double cos = Math.cos (theta);
		double sin = Math.sin (theta);
		
		double newX = rotatePointX + (getX () - rotatePointX) * cos - (getY () - rotatePointY)
				* sin;
		double newY = rotatePointY + (getX () - rotatePointX) * sin + (getY () - rotatePointY)
				* cos;
		
		this.setCoordinates (newX, newY);
	}
	
	
	/**
	 * Refer to {@link #rotate(double, double, double)}. Invokes method with appropriate values from
	 * the supplied coordinate
	 * 
	 * @param angle Angle to rotate {@link Coordinate2D}
	 * @param rotateCoord Rotation point
	 */
	public void rotate(double angle, Coordinate2D rotateCoord)
	{
		rotate (angle, rotateCoord.getX (), rotateCoord.getY ());
	}
	
	
	// ====================== Custom Getters & Setters =======================
	/**
	 * Adds specified values to the coordinates
	 * 
	 * @param addX Value to add to the x-coordinate
	 * @param addY Value to add to the y-coordinate
	 */
	public void addToCoordinates(double addX, double addY)
	{
		x += addX;
		y += addY;
	}
	
	
	/**
	 * Set both x- and y- coordinates
	 * 
	 * @param x New x-coordinate
	 * @param y New y-coordinate
	 */
	public void setCoordinates(double x, double y)
	{
		this.setX (x);
		this.setY (y);
	}
	
	
	// ========================== Getters & Setters ==========================
	public double getX()
	{
		return x;
	}
	
	
	public void setX(double x)
	{
		this.x = x;
	}
	
	
	public double getY()
	{
		return y;
	}
	
	
	public void setY(double y)
	{
		this.y = y;
	}
	
	
	// ======================== Overridden from Object =======================
	@ Override
	public boolean equals(Object obj)
	{
		if (obj instanceof Coordinate2D) {
			Coordinate2D another = (Coordinate2D) obj;
			
			return (x == another.getX () && y == another.getY ());
		} else
			return false;
	}
	
	
	/**
	 * Generates a string in format: x,y
	 */
	@ Override
	public String toString()
	{
		return String.format ("%f,%f", x, y);
	}
	
	
	// ================================ String ===============================
	/**
	 * Creates a coordinate from a string. Note, must be in the same format as toString()
	 * 
	 * @param fStr String to extract coordinate from.
	 * 
	 * @return Coordinate from string, or null on exception
	 */
	public static Coordinate2D fromString(String fStr)
	{
		String[] fStrSplit = fStr.split (",");
		double cX = 0;
		double cY = 0;
		
		try {
			cX = Double.parseDouble (fStrSplit[0].trim ());
			cY = Double.parseDouble (fStrSplit[1].trim ());
		} catch (Exception e) {
			e.printStackTrace ();
			return null;
		}
		
		return new Coordinate2D (cX, cY);
	}
}