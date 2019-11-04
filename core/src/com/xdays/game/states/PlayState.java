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
	
	private boolean firstRun = true;
	CardGameManager manager;

	public PlayState(GameStateManager gsm) {
		super(gsm);
		cam.setToOrtho(false, Game.WIDTH, Game.HEIGHT);
		
		manager = new CardGameManager(50, new User("Friendly"), new AI("Enemy", 1));
	}

	
	@Override
	protected void handleInput() {
        if(Gdx.input.justTouched() && manager.isPlayersTurn()) {
        	System.out.println("Play State touched");
        	Rectangle bounds = new Rectangle(Gdx.input.getX(), Gdx.input.getY(), 5, 5);
        	for(int i=0; i<manager.getUser().getHand().length; i++) {
        		if(manager.getUser().getHand()[i].getBounds().overlaps(bounds)) {
        			System.out.println(manager.getUser().getHand()[i].getTitle());
        			manager.processCard(manager.getUser().getHand()[i]); //Any other cards to sacrafice, if not then null
        		}
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
        //System.out.println("Rendering");
        for(int i=0; i<10; i++) {
        	if(firstRun) {
        		manager.getUser().getHand()[i].setPosition(0 + i*250, 0);
            	System.out.println(i);
            	System.out.println(manager.getUser().getHand()[i].getBounds().getX());
            	System.out.println(manager.getUser().getHand()[i].getBounds().getY());
            	System.out.println(manager.getUser().getHand()[i].getBounds().getWidth());
            	System.out.println(manager.getUser().getHand()[i].getBounds().getHeight());
        	}
            sb.draw(manager.getUser().getHand()[i].getTexture(), 0 + i*250, 0, manager.getUser().getHand()[i].getTexture().getWidth()/2, manager.getUser().getHand()[i].getTexture().getHeight()/2);
            //sb.draw(manager.getAI().getHand()[i].getTexture(), 0 + i*250, 50, manager.getAI().getHand()[i].getTexture().getWidth()/2, manager.getAI().getHand()[i].getTexture().getHeight()/2);
        }
        firstRun = false;
        sb.end();
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
	}

}
