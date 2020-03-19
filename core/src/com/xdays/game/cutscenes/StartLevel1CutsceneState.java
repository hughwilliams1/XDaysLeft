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

public class StartLevel1CutsceneState extends State {

	private Texture background;

	private Texture opponent;
	private Texture opponent1;
	private Texture currentOpponentTexture;
	private Texture player;
	
	private Music americaCutsceneMusic;
	private Sound clickSound;
		
	private int opponentImageCount = 0;

	private Queue<TextBox> textBoxQueue;

	public StartLevel1CutsceneState(GameStateManager gsm) {
		super(gsm);
		cam.setToOrtho(false, Game.WIDTH, Game.HEIGHT);

		americaCutsceneMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/IntenseDrumMusic.mp3"));
		americaCutsceneMusic.setLooping(true);
		americaCutsceneMusic.setVolume(.05f);  
		
		clickSound = Gdx.audio.newSound(Gdx.files.internal("sounds/ClickSound.wav"));
		
		TextBox text1 = new TextBox("Scientist Y",
				"Trump we finally meet. America is among the most significant emmitters of greenhouse in the world,"
				+ " all in the name of corporate greed what do you have to say for yourself. Reprent now and ill make it a quick death");

		TextBox text2 = new TextBox("Donald Trump",
				"");

		TextBox text3 = new TextBox("Scientist Y",
				"blahahahaaadaw");
		
		TextBox text4 = new TextBox("Donald Trump",
				"");
		
		TextBox text5 = new TextBox("Scientist Y",
				"");
		
		textBoxQueue = new LinkedList<TextBox>();

		textBoxQueue.add(text1);
		textBoxQueue.add(text2);
		textBoxQueue.add(text3);
		textBoxQueue.add(text4);
		textBoxQueue.add(text5);

		background = new Texture("level1CutsceneBackground.png");
		opponent = new Texture("Donald Trump.png");
		opponent1 = new Texture("Donald Trump1.png");
		player = new Texture("scientist.png");
		
		currentOpponentTexture = opponent;
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

		if(!americaCutsceneMusic.isPlaying()) {
			americaCutsceneMusic.play();
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
			currentOpponentTexture = opponent;
		} else if (opponentImageCount == 1) {
			currentOpponentTexture = opponent1;
		}
		
		sb.draw(currentOpponentTexture, (Game.WIDTH / 4) * 2, (Game.HEIGHT / 4));
		
		sb.draw(player, 0, (Game.HEIGHT / 4));
		sb.end();

	}

	@Override
	public void dispose() {
		americaCutsceneMusic.dispose();
	}

}
