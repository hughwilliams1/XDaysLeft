package com.xdays.game.cutscenes;

import java.util.LinkedList;
import java.util.Queue;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.xdays.game.Game;
import com.xdays.game.states.GameStateManager;
import com.xdays.game.states.PlayState;
import com.xdays.game.states.State;
import com.xdays.game.states.StateEnum;

public class StartCutsceneState extends State{

	private Texture background;
	
	private Texture player;
	
	private Texture opponentTexture;
	private Texture opponentAltTexture;
	private Texture currentOpponentTexture;
	
	private Music cutsceneMusic;
	private Sound clickSound;
		
	private int opponentImageCount = 0;
	private int currentLevel;
	
	private float opponentXPosition;

	private Queue<TextBox> textBoxQueue;

	public StartCutsceneState(GameStateManager gsm, int level) {
		super(gsm);
		currentLevel = level;
		
		cam.setToOrtho(false, Game.WIDTH, Game.HEIGHT);

		cutsceneMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/IntenseDrumMusic.mp3"));
		cutsceneMusic.setLooping(true);
		cutsceneMusic.setVolume(.05f);  
		
		clickSound = Gdx.audio.newSound(Gdx.files.internal("sounds/ClickSound.wav"));
		
		textBoxQueue = new LinkedList<TextBox>();
		player = new Texture("scientist.png");
		
		if (currentLevel == 1) {
			germanyInitialisation();
		} 
		else if (currentLevel == 2) {
			russiaInitialisation();
		}
		else if (currentLevel == 3) {
			americaInitialisation();
		}
		
		currentOpponentTexture = opponentTexture;
	}
	
	private void germanyInitialisation() {
		background = new Texture("European Parliament Background.png");
		opponentTexture = new Texture("Merkel 1.png");
		opponentAltTexture = new Texture("Merkel 2.png");
		
		opponentXPosition = ((Game.WIDTH / 9) * 5);
		
		TextBox text1 = new TextBox("Scientist Y",
				"Trump we finally meet. America is among the most significant emmitters of greenhouse in the world,"
				+ " all in the name of corporate greed what do you have to say for yourself. Reprent now and ill make it a quick death");

		TextBox text2 = new TextBox("Angela Merkel",
				"");

		TextBox text3 = new TextBox("Scientist Y",
				"blahahahaaadaw");
		
		TextBox text4 = new TextBox("Angela Merkel",
				"");
		
		TextBox text5 = new TextBox("Scientist Y",
				"");

		textBoxQueue.add(text1);
		textBoxQueue.add(text2);
		textBoxQueue.add(text3);
		textBoxQueue.add(text4);
		textBoxQueue.add(text5);
	}
	
	private void russiaInitialisation() {
		background = new Texture("Kremlin Background.png");
		opponentTexture = new Texture("Putin 1.png");
		opponentAltTexture = new Texture("Putin 2.png");
		
		opponentXPosition = ((Game.WIDTH / 9) * 5);
		
		TextBox text1 = new TextBox("Scientist Y",
				"Trump we finally meet. America is among the most significant emmitters of greenhouse in the world,"
				+ " all in the name of corporate greed what do you have to say for yourself. Reprent now and ill make it a quick death");

		TextBox text2 = new TextBox("Vladimir Putin",
				"");

		TextBox text3 = new TextBox("Scientist Y",
				"blahahahaaadaw");
		
		TextBox text4 = new TextBox("Vladimir Putin",
				"");
		
		TextBox text5 = new TextBox("Scientist Y",
				"");

		textBoxQueue.add(text1);
		textBoxQueue.add(text2);
		textBoxQueue.add(text3);
		textBoxQueue.add(text4);
		textBoxQueue.add(text5);
	}
	
	private void americaInitialisation() {
		background = new Texture("Whitehouse Background.png");
		opponentTexture = new Texture("Trump 1.png");
		opponentAltTexture = new Texture("Trump 2.png");
		
		opponentXPosition = (Game.WIDTH / 4) * 2;
		
		TextBox text1 = new TextBox("Scientist Y",
				"Trump we finally meet. America is among the most significant emmitters of greenhouse in the world,"
				+ " all in the name of corporate greed what do you have to say for yourself. Reprent now and ill make it a quick death");

		TextBox text2 = new TextBox("Angela Merkel",
				"");

		TextBox text3 = new TextBox("Scientist Y",
				"blahahahaaadaw");
		
		TextBox text4 = new TextBox("Angela Merkel",
				"");
		
		TextBox text5 = new TextBox("Scientist Y",
				"");

		textBoxQueue.add(text1);
		textBoxQueue.add(text2);
		textBoxQueue.add(text3);
		textBoxQueue.add(text4);
		textBoxQueue.add(text5);
	}
	

	@Override
	protected void handleInput() {
		if (Gdx.input.justTouched()) {
			clickSound.play();
			textBoxQueue.poll();
			
			opponentImageCount++;
			
			if (opponentImageCount == 2) {
				opponentImageCount = 0;
			}
		}
	}

	@Override
	public void update(float dt) {
		handleInput();
		render(Game.batch);
	}

	@Override
	public void render(SpriteBatch sb) {

		if(!cutsceneMusic.isPlaying()) {
			cutsceneMusic.play();
		}
		
		sb.setProjectionMatrix(cam.combined);
		sb.begin();
		sb.draw(background, 0, Game.HEIGHT / 4);

		// only call when change textbox
		try {
			textBoxQueue.peek().showTextBox(sb);
		} catch (Exception e) {
			gsm.setStateAsNew(new PlayState(gsm), StateEnum.PLAY_STATE);
			gsm.removeState(StateEnum.CUTSCENE_STATE);
			dispose();
		}
		
		if (opponentImageCount == 0) {
			currentOpponentTexture = opponentTexture;
		} else if (opponentImageCount == 1) {
			currentOpponentTexture = opponentAltTexture;
		}
		
		sb.draw(currentOpponentTexture, opponentXPosition , (Game.HEIGHT / 4));
		
		sb.draw(player, 0, (Game.HEIGHT / 4));
		sb.end();

	}

	@Override
	public void dispose() {
		cutsceneMusic.dispose();
	}
	
}
