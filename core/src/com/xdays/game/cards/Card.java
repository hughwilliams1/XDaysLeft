package com.xdays.game.cards;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class Card {
	
	private String title;
	private String cardText;
	private Texture texture;
	private Rectangle bounds;
	private Vector3 position;
	private boolean played;
	private boolean halfPlayed;
	private Texture back;
	
	public Card(String title, String cardText) {
		this.title = title;
		this.cardText = cardText;
		texture = new Texture(title + ".PNG");
		played = false;
		halfPlayed = false;
		back = new Texture("back.PNG");
	}
	
	public boolean isPlayed() {
		return played;
	}
	
	public void switchTextures() {
		back = texture;
	}
	
	public void handleInputEnemy() {
		if(!played) {
			if(halfPlayed) {
				position.y += 100;
			}else {
				position.y -= 220;
			}
			bounds.setY(position.y);
			played = true;
		}
	}
	
	public void handleInput() {
		if(!played) {
			if(halfPlayed) {
				position.y += 100;
			}else {
				position.y += 210;
			}
			bounds.setY(position.y);
			played = true;
		}
	}
	
	public void halfPlayed() {
		if(!played) {
			position.y += 110;
			bounds.setY(position.y);
			halfPlayed = true;
		}
	}
	
	public void resetPosition() {
		if(played) {
			position.y -= 210;
			bounds.setY(position.y);
			played = false;
		}
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
		position = new Vector3 (x, y, 0);
		bounds = new Rectangle(x, y, (texture.getWidth()/3.4f), (texture.getHeight()/3.4f));
	}
	
	public Rectangle getBounds() {
		return bounds;
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
