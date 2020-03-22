package com.xdays.game.cards;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class Card {
	
	private String title;
	private String cardText;
	private Texture texture;
	private Rectangle bounds;
	public Vector3 position;
	private boolean played;
	private boolean halfPlayed;
	private Texture back;
	private boolean boundsSet;
	
	public Card(String title, String cardText) {
		this.title = title;
		this.cardText = cardText;
		texture = new Texture(title + ".PNG");
		played = false;
		halfPlayed = false;
		back = new Texture("back.PNG");
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
	
	public void switchTextures() {
		back = texture;
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
	
	public Texture getTexture() {
		return texture;
	}
	
	public Texture getBackTexture() {
		return back;
	}
	
	public void setPosition(float x, float y) {
		boundsSet = true;
		position = new Vector3 (x, y, 0);
		bounds = new Rectangle(x, y, (texture.getWidth()/3.4f), (texture.getHeight()/3.4f));
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
