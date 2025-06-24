/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example;

import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

/**
 *
 * @author Alberto Harden Cooper & Ana Paula
 */
public class Actores extends Personajes {
	
	protected int posRen;
	protected int posCol;
	protected int orientacion; //0 neutral, 1 Norte, 2 Este, 3 Sur, 4 Oeste
	protected int vida;
	protected int dinero;
	protected int speed;
	protected int strength;
	protected int defense;
	protected int weapon;
	protected int armor;
	
	/**
	 * Metodo constructor usado para crear el objeto
	 * @param posX es la <code>posicion en x</code> del objeto.
	 * @param posY es la <code>posicion en y</code> del objeto.
	 * @param image es la <code>imagen</code> del objeto.
	 */
	public Actores(int posX, int posY ,Image image) {
		super(posX, posY, image);
		orientacion = 3;
		vida = 10;
		dinero = 0;
		speed = 1;
		strength = 1;
		defense = 1;
		weapon = 1;
		armor = 1;
	}
	
	/**
	 * Metodo constructor usado para crear el objeto
	 * @param posX es la <code>posicion en x</code> del objeto.
	 * @param posY es la <code>posicion en y</code> del objeto.
	 * @param image es la <code>imagen</code> del objeto.
	 * @param indX
	 * @param indY
	 */
	public Actores(int posX, int posY ,Image image, int posRen, int posCol) {
		super(posX, posY, image);
		this.posRen = posRen;
		this.posCol = posCol;
		
		this.posX = posCol*icono.getIconHeight();
		this.posY = posRen*icono.getIconWidth();
		orientacion = 4;
	}
	
	public void setPosRen(int posRen)
	{
		this.posRen = posRen;
		this.posY = posRen*icono.getIconHeight();
	}
	
	public int getPosRen()
	{
		return posRen;
	}
	
	public void setPosCol(int posCol)
	{
		this.posCol = posCol;
		this.posX = posCol*icono.getIconWidth();
	}
	
	public int getPosCol()
	{
		return posCol;
	}
	
	public void setOrientacion(int orientacion)
	{
		this.orientacion = orientacion;
	}
	
	public int getOrientacion()
	{
		return this.orientacion;
	}
	
	public void setVida(int vida)
	{
		this.vida = vida;
	}
	
	public int getVida()
	{
		return vida;
	}
	
	public void setDinero(int dinero)
	{
		this.dinero = dinero;
	}
	
	public int getDinero()
	{
		return dinero;
	}
	
	public void setSpeed(int speed)
	{
		this.speed = speed;
	}
	
	public int getSpeed()
	{
		return speed;
	}
	
	public void setStrength(int strength)
	{
		this.strength = strength;
	}
	
	public int getStrength()
	{
		return strength;
	}
	
	public void setDefense(int defense)
	{
		this.defense = defense;
	}
	
	public int getDefense()
	{
		return defense;
	}
	
	public void setWeapon(int weapon)
	{
		this.weapon = weapon;
	}
	
	public int getWeapon()
	{
		return weapon;
	}
	
	public void setArmor(int armor)
	{
		this.armor = armor;
	}
	
	public int getArmor()
	{
		return armor;
	}
}

