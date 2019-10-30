package com.xdays.game.sprites;

import com.badlogic.gdx.Gdx;

public class Social extends Card {

	public Social(String title, String cardText) {
		super(title, cardText);
	}

	@Override
	public void handleInput() {
		if(Gdx.input.justTouched())
            System.out.println("Social card was pressed.");
	}

}
