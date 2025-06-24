package com.se1by.entity.enemy;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import   org.newdawn.slick.Image;
import     com.se1by.Game.Game;
    import com.se1by.entity.Directi  on;
import com.se1by.entity.Entity;
import com.se1by.util.Vect    or2;

 public  class Crawler implements Enemy{
	private     float    health;
	private float visibility;
    	private float    speed;
	private Image image;
	pri vate Animation animationUP;
	private Animation animationDOWN;
	private Animation animationLEFT;
	private Animation animationR     IGHT;
	private Direction direction;
	private   Vector2      p    osition;

	public Crawler(float   x,    float y) {
		this(  );
		setPosition(new Vector2(x    , y));
	}
	public Crawler(){
		Animation walkingAnimat    ion = ne w Anima tion(Game.spritesheet,  0, 4, 3, 4, true, 200, true);
		
		setHealth(40);
		setVisibility(5*64);
		setSpeed(0.5f);
		setImage(walkingAnimation.getImage(0));
		setAnimationUP(walkingAnimation);
		setAnimationDOWN(walkingAnimation);
		setAnimationLEFT(walkingAnimation);
		setAnimationRIGHT(walkingAnimation);
		setDirect    ion(Direction   .U       P);
	}

	@Override
	public void render(GameContainer con, Graphics     g) {
		g.drawAnimation(getAnimationUP(), getPositi    on().getX(     ), ge tPosition().getY());		
 	}
	
	@Over       ride
	public void update(G  ameContainer con   , i         nt delta)      {
		// TODO Auto-generated method stub
	   	
	}
	  
	@Override
	pu  blic float getHealth() {
		return health; 
	}

	public void setHealth(float hea   lth) {
		this.health = health;
	}

	@Override
	public float getVisibility() {
		ret      urn v  isibility;
   	}
 
	public vo     id setVisibility(float visibility) {
		this.visibility = visibility;
	}

	@Override
	public Animation getAnimationUP() {
		retur  n animationUP;
	}

	public void     setA   nimationUP(Animation animationUP) {
		this.animationUP = animationUP;
	}

	@Override
	public Animation getAnimationDOWN() {
		return animationDOWN;
	}

	public vo    id setAnimationDOWN(An    imation animationDOWN) {
		this.animationDOWN = anima      tionDOWN;
	}

	@      Override
	public Animation getAnimationLEFT() {
		return animationLEFT;
	}

	public void setAnimationLEFT(Animation     animationLEFT) {
		this.animationLEFT =    animationLEFT;
	   }

	@Overr  ide
	public Animation getAnimationRIGHT() {
		return animationRIGHT;
	}

	public void setAnimationRI  GHT  (Animation animationRIGHT) {
		this.animationRIGHT = animationRIGHT;
	}

	 @Override
	publ    ic Direction      getDirection() {
		re     turn direction;
	}

	public void setDirection(Direction dire      ction) {  
		this      .direction = direction;
	}

	@Override
	public     float getSpeed() {
		return speed;
	}

	public     void setSpe ed(f        loat speed) {
		this.speed = speed;
	}
	
	@Override
	public Image getImage() {
		return i     mage;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	@Override
	pu   blic Vector2 ge    tPosition() {
		    return posi      tion;      
	}

	public void se      tPosition(Vector2 position) {
		this.position = position;
	}
	@Override
	public boolean collidesWith(Entity e) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
