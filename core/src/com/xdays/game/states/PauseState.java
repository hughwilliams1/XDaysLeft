package com.xdays.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.xdays.game.Game;
import com.xdays.game.assets.Button;

public class PauseState extends State {
	
	private static final int BTN_WIDTH = 200;
	private static final int BTN_HEIGHT = 60;
	
	private Texture background;
	private Button backBtn;
	private Button loadBtn;
	private Button saveBtn;
	private Button quitBtn;
	
	private Sound clickSound;
	
	public PauseState(GameStateManager gsm) {
		super(gsm);

        int x = (Game.WIDTH / 2 - BTN_WIDTH / 2);
		
        cam.setToOrtho(false, Game.WIDTH, Game.HEIGHT);
        
		background = new Texture("pauseMenuBackground.PNG");
		backBtn = new Button(BTN_WIDTH, BTN_HEIGHT, x, (Game.HEIGHT / 2 - BTN_HEIGHT / 2) - 150, "BackBtn.PNG");
		quitBtn = new Button(BTN_WIDTH, BTN_HEIGHT, x, (Game.HEIGHT / 2 - BTN_HEIGHT / 2) - BTN_HEIGHT, "QuitBtn.PNG");
		loadBtn = new Button(BTN_WIDTH, BTN_HEIGHT, x, (Game.HEIGHT / 2 - BTN_HEIGHT / 2) + BTN_HEIGHT + 20, "LoadBtn.PNG");
		saveBtn = new Button(BTN_WIDTH, BTN_HEIGHT, x, (Game.HEIGHT / 2 - BTN_HEIGHT / 2) + 10, "SaveBtn.PNG");

        clickSound = Gdx.audio.newSound(Gdx.files.internal("sounds/ClickSound.wav"));
	}

	@Override
	protected void handleInput() {
		if (Gdx.input.justTouched() && backBtn.isPointerOver(Gdx.input.getX(), Gdx.input.getY())) {
			clickSound.play();
			gsm.back();
		}
		if (Gdx.input.justTouched() && loadBtn.isPointerOver(Gdx.input.getX(), Gdx.input.getY())) {
			clickSound.play();
			//gsm.loadGame();
		}
		if (Gdx.input.justTouched() && saveBtn.isPointerOver(Gdx.input.getX(), Gdx.input.getY())) {
			clickSound.play();
			//gsm.saveGame();
		}
		
        if(Gdx.input.justTouched() && quitBtn.isPointerOver(Gdx.input.getX(), Gdx.input.getY())){
        	Gdx.app.exit();
        }
	}

	@Override
	public void update(float dt) {
		handleInput();
	}

	@Override
	public void render(SpriteBatch sb) {
		sb.setProjectionMatrix(cam.combined);
        sb.begin();
        
        sb.draw(background, 0,0);
        backBtn.draw(sb);
        saveBtn.draw(sb);
        loadBtn.draw(sb);
        quitBtn.draw(sb);
        sb.end();
	}

	@Override
	public void dispose() {
	}

}
