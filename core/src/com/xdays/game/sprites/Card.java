package com.xdays.game.sprites;

import com.badlogic.gdx.Gdx;
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
		position = new Vector3(50, 50, 0);
		this.title = title;
		this.cardText = cardText;
		texture = new Texture(title+".PNG");
		bounds = new Rectangle(50, 50, texture.getWidth(), texture.getHeight());
	}
	
	public abstract void handleInput();
    
    public Vector3 getPosition() {
        return position;
    }

    public Texture getTexture() {
        return texture;
    }
    
    public Rectangle getBounds(){
        return bounds;
    }
}
