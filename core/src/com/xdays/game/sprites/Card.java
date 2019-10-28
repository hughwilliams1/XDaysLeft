package com.xdays.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public abstract class Card {
	
	private String title;
	private String type;
	private int stars;
	private String cardText;
	private int power;
	private Texture texture;
	private Rectangle bounds;
	private Vector3 position;
	
	public Card(String title, String type, int stars, String cardText, int power) {
		position = new Vector3(50, 50, 0);
		this.title = title;
		this.type = type;
		this.stars = stars;
		this.cardText = cardText;
		this.power = power;
		texture = new Texture(title+".PNG");
		bounds = new Rectangle(50, 50, texture.getWidth(), texture.getHeight());
	}
	
	public void handleInput() {
        if(Gdx.input.justTouched())
            System.out.println(title + " card was pressed.");
	}
    
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
