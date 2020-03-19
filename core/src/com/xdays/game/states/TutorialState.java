package com.xdays.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.xdays.game.Game;
import com.xdays.game.assets.Button;

public class TutorialState extends State {

	private Texture tutorial;
	
	 private Button quitBtn;
	 private Sound clickSound;
	 
	 private static final int BTN_WIDTH = 200;
	 private static final int BTN_HEIGHT = 60;
	
	public TutorialState(GameStateManager gsm) {
		super(gsm);
		
		
        clickSound = Gdx.audio.newSound(Gdx.files.internal("sounds/ClickSound.wav"));
        
        cam.setToOrtho(false, Game.WIDTH, Game.HEIGHT);
		tutorial = new Texture("Tutorial.png");
		
		int x = (Game.WIDTH / 2 - BTN_WIDTH / 2);
		
		quitBtn = new Button(BTN_WIDTH, BTN_HEIGHT, x, (Game.HEIGHT / 2 - BTN_HEIGHT / 2) - (110 + (BTN_HEIGHT*2) + 20), "QuitBtn.PNG");
	}

	@Override
	protected void handleInput() {

        if(Gdx.input.justTouched() && quitBtn.isPointerOver(Gdx.input.getX(), Gdx.input.getY())){
        	clickSound();
        	gsm.setState(StateEnum.MAP_STATE);
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
        sb.draw(tutorial, 0,0);
        
        quitBtn.draw(sb);
        
        sb.end();
	}

	@Override
	public void dispose() {
		
	}
    private void clickSound() {
    	clickSound.play(0.1f);
    }

}
