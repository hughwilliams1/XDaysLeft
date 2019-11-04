package com.xdays.game.cards;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public abstract class Card {
	
	private String title;
	private String cardText;
	private Texture texture;
	private Rectangle bounds;
	private Vector3 position;
	
	public Card(String title, String cardText) {
		this.title = title;
		this.cardText = cardText;
		texture = new Texture(title + ".PNG");
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
	
	public void setPosition(float x, float y) {
		position = new Vector3 (x, y, 0);
		bounds = new Rectangle(x, y, (texture.getWidth()/2), (texture.getHeight()/2));
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
