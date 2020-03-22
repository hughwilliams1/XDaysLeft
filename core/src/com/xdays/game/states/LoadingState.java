package com.xdays.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.xdays.game.Game;

public class LoadingState extends State {
	
	private static final int FRAME_COLS = 5, FRAME_ROWS = 4;
	
	private int x;
	private int y; 
	
	private Animation<TextureRegion> loadAnimation;
	private Texture loadSheet;
	
	private float elapsedTime = 0;
	
	private Texture background;
	
	public LoadingState(GameStateManager gsm) {
		super(gsm);
		loadSheet = new Texture(Gdx.files.internal("LoadingAnimation.PNG"));
		
		TextureRegion[][] temp = TextureRegion.split(loadSheet,
				loadSheet.getWidth() / FRAME_COLS,
				loadSheet.getHeight() / FRAME_ROWS);
		
		TextureRegion[] loadFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
		
		int index = 0;
		for(int i = 0; i < FRAME_ROWS; i++) {
			for(int j = 0; j < FRAME_COLS; j++) {
				loadFrames[index++] = temp[i][j];
			}
		}
		
		loadAnimation = new Animation<TextureRegion>(0.05f, loadFrames);
		
		x = (Game.WIDTH / 2 - loadFrames[0].getRegionWidth() / 2);
		y = (Game.HEIGHT / 2 - loadFrames[0].getRegionHeight() / 2);
		
		cam.setToOrtho(false, Game.WIDTH, Game.HEIGHT);
        background = new Texture("loadingBackground.PNG");
	}

	@Override
	protected void handleInput() {
		
	}

	@Override
	public void update(float dt) {
		
	}

	@Override
	public void render(SpriteBatch sb) {
		
		elapsedTime += Gdx.graphics.getDeltaTime();
		
		TextureRegion currentFrame = loadAnimation.getKeyFrame(elapsedTime, true);
		sb.begin();
		sb.draw(background, 0,0);
		sb.draw(currentFrame, x, y - 75);
		sb.end();
	}

	@Override
	public void dispose() {
		
	}

}
