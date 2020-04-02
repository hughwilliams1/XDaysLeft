package com.xdays.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.xdays.game.Game;

/**  
 * LoadingState.java - The first state to load, waits for {@link AssetManager} to load assets then moves on
 *
 * @author  Damian Hobeanu, Mark Ebel, Roberto Lovece, Ronil Goldenwalla, Hugh Williams
 * @version 1.0 
 * @see State
 */ 
public class LoadingState extends State {
	
	private static final int FRAME_COLS = 5, FRAME_ROWS = 4;
	
	private int x;
	private int y; 
	
	private Animation<TextureRegion> loadAnimation;
	private Texture loadSheet;
	
	private float elapsedTime = 0;
	
	private Texture background;
	private BitmapFont font;
	
	/**
	 * Constructor for the loading state
	 * 
	 * @param gsm The GameStateManager
	 */
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
		
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font/Staatliches-Regular.ttf"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 35;
		font = generator.generateFont(parameter);
		
        background = new Texture("loadingBackground.PNG");
	}

	@Override
	protected void handleInput() {
		
	}

	@Override
	public void update(float dt) {
		
	}
	
	/**
	 * Waits until assets are loaded in AssetManager then chnages states
	 */
	@Override
	public void render(SpriteBatch sb) {
		if (Game.assetManager.update()) {
			gsm.createStates();
		}
		
		elapsedTime += Gdx.graphics.getDeltaTime();
		
		TextureRegion currentFrame = loadAnimation.getKeyFrame(elapsedTime, true);
		sb.begin();
		sb.draw(background, 0,0);
		sb.draw(currentFrame, x, y - 75);
		float progress = Game.assetManager.getProgress();
		font.draw(sb, "" + 5*Math.round(Math.round(progress*100) / 5) + "%",
				x - 20 + currentFrame.getRegionWidth()/2, y -65 + currentFrame.getRegionHeight() / 2);
		sb.end();
	}

	@Override
	public void dispose() {
		
	}

}
