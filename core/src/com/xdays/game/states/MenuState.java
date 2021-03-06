package com.xdays.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.xdays.game.Game;
import com.xdays.game.assets.Button;
import com.xdays.game.cutscenes.CutsceneState;

/**  
 * MenuState.java - The main screen that starts, Three buttons Play Quit 
 *
 * @author  Damian Hobeanu, Mark Ebel, Roberto Lovece, Ronil Goldenwalla, Hugh Williams
 * @version 1.0 
 * @see State
 */ 
public class MenuState extends State{
	
	private static final int BTN_WIDTH = 200;
	private static final int BTN_HEIGHT = 60;
	
	private Texture background;
	
    private Button playBtn;
    private Button settingsBtn;
    private Button quitBtn;
    
    static Music mainMenuMusic;
    private Sound clickSound;
    
    /**
	 * Constructor for the menu state
	 * 
	 * @param gsm The GameStateManager
	 */
    public MenuState(GameStateManager gsm) {
        super(gsm);
        
        mainMenuMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/MenuMusic.mp3"));
        mainMenuMusic.setLooping(true);
        mainMenuMusic.setVolume(.1f);        
        mainMenuMusic.play();
        
        clickSound = Gdx.audio.newSound(Gdx.files.internal("sounds/ClickSound.wav"));
        
        cam.setToOrtho(false, Game.WIDTH, Game.HEIGHT);
        background = (Texture) Game.assetManager.get("title.PNG");;
        
        int x = (Game.WIDTH / 2 - BTN_WIDTH / 2);
        
        playBtn = new Button(BTN_WIDTH, BTN_HEIGHT, x, (Game.HEIGHT / 2 - BTN_HEIGHT / 2) - 110, "PlayBtn.PNG");
        settingsBtn = new Button(BTN_WIDTH, BTN_HEIGHT, x, (Game.HEIGHT / 2 - BTN_HEIGHT / 2) - (110 + BTN_HEIGHT + 10), "MenuBtn.PNG");
        quitBtn = new Button(BTN_WIDTH, BTN_HEIGHT, x, (Game.HEIGHT / 2 - BTN_HEIGHT / 2) - (110 + (BTN_HEIGHT*2) + 20), "QuitBtn.PNG");
    }

    @Override
    public void handleInput() {
        if(Gdx.input.justTouched() && playBtn.isPointerOver(Gdx.input.getX(), Gdx.input.getY())){
        	clickSound();
        	mainMenuMusic.pause();
        	gsm.setStateAsNew(new CutsceneState(gsm, 0), StateEnum.CUTSCENE_STATE);
        }
        
        if(Gdx.input.justTouched() && settingsBtn.isPointerOver(Gdx.input.getX(), Gdx.input.getY())){
        	clickSound();
        	gsm.setState(StateEnum.PAUSE_STATE);
        }
        
        if(Gdx.input.justTouched() && quitBtn.isPointerOver(Gdx.input.getX(), Gdx.input.getY())){
        	clickSound();
        	Gdx.app.exit();
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
		if(!MenuState.mainMenuMusic.isPlaying()) {
			MenuState.mainMenuMusic.play();
		}
		
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
    }
    
    private void clickSound() {
    	clickSound.play(0.1f);
    }
}
