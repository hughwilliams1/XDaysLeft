package com.xdays.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.xdays.game.Game;

/**
 * Created by Brent on 6/26/2015.
 */
public class MenuState extends State{
	
	private static final int BTN_WIDTH = 200;
	private static final int BTN_HEIGHT = 60;
	
	private Texture background;
	
    private Button playBtn;
    private Button settingsBtn;
    private Button quitBtn;
    
    public MenuState(GameStateManager gsm) {
        super(gsm);
        cam.setToOrtho(false, Game.WIDTH, Game.HEIGHT);
        background = new Texture("Title.PNG");
        
        int x = (Game.WIDTH / 2 - BTN_WIDTH / 2);
        
        playBtn = new Button(BTN_WIDTH, BTN_HEIGHT, x, (Game.HEIGHT / 2 - BTN_HEIGHT / 2) - 110, "PlayBtn.PNG");
        settingsBtn = new Button(BTN_WIDTH, BTN_HEIGHT, x, (Game.HEIGHT / 2 - BTN_HEIGHT / 2) - (110 + BTN_HEIGHT + 10), "SettingsBtn.PNG");
        quitBtn = new Button(BTN_WIDTH, BTN_HEIGHT, x, (Game.HEIGHT / 2 - BTN_HEIGHT / 2) - (110 + (BTN_HEIGHT*2) + 20), "QuitBtn.PNG");
    }

    @Override
    public void handleInput() {
        if(Gdx.input.justTouched() && playBtn.isPointerOver(Gdx.input.getX(), Gdx.input.getY(), Game.HEIGHT)){
            gsm.set(new MapState(gsm));
        }
        if(Gdx.input.justTouched() && settingsBtn.isPointerOver(Gdx.input.getX(), Gdx.input.getY(), Game.HEIGHT)){
        	System.out.println("Settings button pressed");
        }
        if(Gdx.input.justTouched() && quitBtn.isPointerOver(Gdx.input.getX(), Gdx.input.getY(), Game.HEIGHT)){
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
        
        playBtn.draw(sb);
        settingsBtn.draw(sb);
        quitBtn.draw(sb);
        
        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        playBtn.dispose();
        settingsBtn.dispose();
        quitBtn.dispose();
        System.out.println("Menu State Disposed");
    }
}
