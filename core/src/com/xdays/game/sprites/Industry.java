package com.xdays.game.sprites;

import com.badlogic.gdx.Gdx;

public class Industry extends Card {
	
	private int stars;
	private int points;
	
	public Industry(String title, String cardText, int stars, int points) {
		super(title, cardText);
		this.stars = stars;
		this.points = points;
	}
	
	@Override
	public void handleInput() {
		if(Gdx.input.justTouched())
            System.out.println("industry card was pressed.");
	}
}
