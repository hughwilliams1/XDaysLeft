package com.xdays.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.xdays.game.assets.Assets;
import com.xdays.game.states.GameStateManager;

/**  
 * Game.java - Starts the game and has the main render() method called by libgdx
 *
 * @author  Damian Hobeanu, Mark Ebel, Roberto Lovece, Ronil Goldenwalla, Hugh Williams
 * @version 1.0 
 * @see ApplicationAdapter
 */ 
public class Game extends ApplicationAdapter {
	
	public static final int WIDTH = 1280;
	public static final int HEIGHT = 720;
	public static final String TITLE = "X Days Left";
	public static Assets assetManager;
	
	private GameStateManager gsm;
	public static SpriteBatch batch;
	
	Texture img;
	
	
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		Gdx.gl.glClearColor(1, 0, 0, 1);
		assetManager = new Assets();
		assetManager.load();
		
		gsm = new GameStateManager();
	}
	
	/**
	 * Returns the Main batch of the game
	 * @return the batch
	 */
	public SpriteBatch getBatch() {
		return batch;
	}

	@Override
	public void render () {
		/*Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(img, 0, 0);
		batch.end();*/
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        gsm.update(Gdx.graphics.getDeltaTime());
        gsm.render(batch);
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
