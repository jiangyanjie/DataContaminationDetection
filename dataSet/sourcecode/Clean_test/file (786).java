package com.se1by.entity.enemy;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import com.se1by.Game.Game;
import com.se1by.entity.Direction;
import com.se1by.entity.Entity;
import com.se1by.util.Vector2;

public class Crawler implements Enemy{
	private float health;
	private float visibility;
	private float speed;
	private Image image;
	private Animation animationUP;
	private Animation animationDOWN;
	private Animation animationLEFT;
	private Animation animationRIGHT;
	private Direction direction;
	private Vector2 position;

	public Crawler(float x, float y) {
		this();
		setPosition(new Vector2(x, y));
	}
	public Crawler(){
		Animation walkingAnimation = new Animation(Game.spritesheet, 0, 4, 3, 4, true, 200, true);
		
		setHealth(40);
		setVisibility(5*64);
		setSpeed(0.5f);
		setImage(walkingAnimation.getImage(0));
		setAnimationUP(walkingAnimation);
		setAnimationDOWN(walkingAnimation);
		setAnimationLEFT(walkingAnimation);
		setAnimationRIGHT(walkingAnimation);
		setDirection(Direction.UP);
	}

	@Override
	public void render(GameContainer con, Graphics g) {
		g.drawAnimation(getAnimationUP(), getPosition().getX(), getPosition().getY());		
	}
	
	@Override
	public void update(GameContainer con, int delta) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public float getHealth() {
		return health;
	}

	public void setHealth(float health) {
		this.health = health;
	}

	@Override
	public float getVisibility() {
		return visibility;
	}

	public void setVisibility(float visibility) {
		this.visibility = visibility;
	}

	@Override
	public Animation getAnimationUP() {
		return animationUP;
	}

	public void setAnimationUP(Animation animationUP) {
		this.animationUP = animationUP;
	}

	@Override
	public Animation getAnimationDOWN() {
		return animationDOWN;
	}

	public void setAnimationDOWN(Animation animationDOWN) {
		this.animationDOWN = animationDOWN;
	}

	@Override
	public Animation getAnimationLEFT() {
		return animationLEFT;
	}

	public void setAnimationLEFT(Animation animationLEFT) {
		this.animationLEFT = animationLEFT;
	}

	@Override
	public Animation getAnimationRIGHT() {
		return animationRIGHT;
	}

	public void setAnimationRIGHT(Animation animationRIGHT) {
		this.animationRIGHT = animationRIGHT;
	}

	@Override
	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	@Override
	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}
	
	@Override
	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	@Override
	public Vector2 getPosition() {
		return position;
	}

	public void setPosition(Vector2 position) {
		this.position = position;
	}
	@Override
	public boolean collidesWith(Entity e) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
