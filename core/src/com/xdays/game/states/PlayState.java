package com.xdays.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.xdays.game.Game;
import com.xdays.game.sprites.SolarFieldCard;

public class PlayState extends State{
	
	private SolarFieldCard card;

	public PlayState(GameStateManager gsm) {
		super(gsm);
		cam.setToOrtho(false, Game.WIDTH, Game.HEIGHT);
		card = new SolarFieldCard();
	}

	
	@Override
	protected void handleInput() {
        if(Gdx.input.justTouched()) {
        	System.out.println("Play State touched");
        	Rectangle bounds = new Rectangle(Gdx.input.getX(), Gdx.input.getY(), 10, 10);
        	if(card.getBounds().overlaps(bounds)) {
        		card.handleInput();
        	}
        }
	}

	@Override
	public void update(float dt) {
		handleInput();
	}

	@Override
	public void render(SpriteBatch sb) {
		Gdx.gl.glClearColor(135/255f, 206/255f, 235/255f, 1);
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(card.getTexture(), card.getPosition().x, card.getPosition().y, card.getTexture().getWidth()/2, card.getTexture().getHeight()/2);
        sb.end();
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
