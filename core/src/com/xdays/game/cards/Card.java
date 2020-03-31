package com.xdays.game.cards;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class Card {
	
	public static final int CARD_WIDTH = 467;
	public static final int CARD_HEIGHT = 722;
	
	private String title;
	private String cardText;
	private Rectangle bounds;
	public Vector3 position;
	private boolean played;
	private boolean halfPlayed;
	private boolean boundsSet;
	
	public Card(String title, String cardText) {
		this.title = title;
		this.cardText = cardText;
		played = false;
		halfPlayed = false;
		boundsSet = false;
	}
	
	public boolean isPlayed() {
		return played;
	}
	
	public void setPlayed(Boolean played) {
		this.played = played;
	}
	
	public boolean isHalfPlayed() {
		return halfPlayed;
	}
	
	public void halfPlayed() {
		if(!played && halfPlayed == false) {
			position.y += 110;
			bounds.setY(position.y);
			halfPlayed = true;
		} 
	}
	
	public void stopHalfPlay() {
		position.y = 0;
		bounds.setY(position.y);
		halfPlayed = false;
	}
	
	
	public void resetPosition() {
		if(played) {
			position.y -= 210;
			bounds.setY(position.y);
			played = false;
		}
	}
	
	public void updateBounds() {
		bounds.setX(position.x);
		bounds.setY(position.y);
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getCardText() {
		return cardText;
	}
	
	public void setPosition(float x, float y) {
		boundsSet = true;
		position = new Vector3 (x, y, 0);
		bounds = new Rectangle(x, y, (Card.CARD_WIDTH/3.4f), (Card.CARD_HEIGHT/3.4f));
	}
	
	public boolean haveBoundsBeenSet() {
		return boundsSet;
	}
	
	public Rectangle getBounds() {
		return bounds;
	}
	
	public float getX() {
		return bounds.getX();
	}
	
	public float getY() {
		return bounds.getY();
	}
	
	public float getBoundsWidth() {
		return bounds.getWidth();
	}
	
	public float getBoundsHeight() {
		return bounds.getHeight();
	}
	
	public Vector3 getPosition() {
		return position;
	}
	
	public boolean isIndustry() {
		return false;
	}

	public int getStars() {
		return 0;
	}

}
