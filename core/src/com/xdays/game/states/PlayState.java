package com.xdays.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.xdays.game.AI;
import com.xdays.game.CardGameManager;
import com.xdays.game.Game;
import com.xdays.game.User;
import com.xdays.game.cards.Card;
import com.xdays.game.cards.Industry;

public class PlayState extends State{
	
	private Industry industry;
	private Industry cards[];
	CardGameManager manager;
	private Boolean a = true;

	public PlayState(GameStateManager gsm) {
		super(gsm);
		cam.setToOrtho(false, Game.WIDTH, Game.HEIGHT);
		
		manager = new CardGameManager(50, new User("Friendly"), new AI("Enemy"));
		
		cards = new Industry[10];
		for(int i=0; i<10; i++) {
			if(i==1) {
				cards[i] = new Industry("cardTwo", "cardText", 1, 2);
			}else {
				cards[i] = new Industry("cardOne", "cardText", 1, 2);
			}
		}
	}

	
	@Override
	protected void handleInput() {
        if(Gdx.input.justTouched() && manager.isPlayersTurn()) {
        	System.out.println("Play State touched");
        	Rectangle bounds = new Rectangle(Gdx.input.getX(), Gdx.input.getY(), 10, 10);
        	/*if(card.getBounds().overlaps(bounds)) {
        		card.handleInput();
        	}*/
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
        System.out.println("Rendering");
        for(int i=0; i<10; i++) {
            sb.draw(manager.getUser().getHand()[i].getTexture(), 0 + i*250, 0, manager.getUser().getHand()[i].getTexture().getWidth()/2, manager.getUser().getHand()[i].getTexture().getHeight()/2);
            sb.draw(manager.getAI().getHand()[i].getTexture(), 0 + i*250, 50, manager.getAI().getHand()[i].getTexture().getWidth()/2, manager.getAI().getHand()[i].getTexture().getHeight()/2);
        }
        sb.end();
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
	}

}
