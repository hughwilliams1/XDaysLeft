package com.xdays.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.xdays.game.states.GameStateManager;
import com.xdays.game.states.MenuState;

public class Game extends ApplicationAdapter {
	public static final int WIDTH = 1280;
	public static final int HEIGHT = 720;
	public static final String TITLE = "X Days Left";
	
	private GameStateManager gsm;
	public static SpriteBatch batch;
	Texture img;
	
	private Music music;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		music = Gdx.audio.newMusic(Gdx.files.internal("Wind-Up-Things.mp3"));
		music.setLooping(true);
		music.setVolume(0.2f);
		music.play();
		Gdx.gl.glClearColor(1, 0, 0, 1);
		gsm = new GameStateManager();
		gsm.push(new MenuState(gsm));
	}
	
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
		music.dispose();
	}
}
