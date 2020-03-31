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
import com.xdays.game.states.MapState;
import com.xdays.game.states.PlayState;
import com.xdays.game.states.State;
import com.xdays.game.states.StateEnum;

public class CutsceneState extends State{

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

	public CutsceneState(GameStateManager gsm, int level) {
		super(gsm);
		cam.setToOrtho(false, Game.WIDTH, Game.HEIGHT);
		
		cutsceneMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/IntenseDrumMusic.mp3"));
		cutsceneMusic.setLooping(true);
		cutsceneMusic.setVolume(.05f);  
		clickSound = Gdx.audio.newSound(Gdx.files.internal("sounds/ClickSound.wav"));
		
		currentLevel = level;	

		textBoxQueue = new LinkedList<TextBox>();
		player = new Texture("scientist.png");
		
		switch(currentLevel) {
		case(0):
			introInitialisation();
			break;
		case(1):
			germanyInitialisation();
			break;
		case(2):
			russiaInitialisation();
			break;
		case(3):
			americaInitialisation();
			break;
		case(4):
			germanyWinInitialisation();
			break;
		case(5):
			russiaWinInitialisation();
			break;
		case(6):
			americaWinInitialisation();
			break;
		case(99):
			endInitialisation();
			break;
		}
		
		currentOpponentTexture = opponentTexture;
	}
	
	private void introInitialisation() {
		background = new Texture("LabBackground.png");
		opponentTexture = new Texture("BlankCut.png");
		opponentAltTexture = new Texture("BlankCut.png");
		
		opponentXPosition = ((Game.WIDTH / 9) * 5);
		
		textBoxQueue.add(new TextBox("Scientist Y",
				"All my life I have been in search of a goal.  184 years i've spent ..."));

		textBoxQueue.add(new TextBox("Scientist Y",
				"wait,  no no,  let me see,  MOON GUY    err    THAT WAR THINGY"));

		textBoxQueue.add(new TextBox("Scientist Y",
				"That's it!  I've lived for 84 years with nothing to show for it,  except for my devilishly good looks of course."));
		
		textBoxQueue.add(new TextBox("Scientist Y",
				"Finally I have something to fight for!  This world,  our world.  "
				+ "Climate change will be the end of us all,  if I don't act quick it will be too late!"));
		
		textBoxQueue.add(new TextBox("Scientist Y",
				"Ah!  Here's my walking stick.  Now off to fight this from the bottom up,  Mrs Merkel,  you're first."));
	}
	
	
	private void germanyInitialisation() {
		background = new Texture("European Parliament Background.png");
		opponentTexture = new Texture("Merkel 1.png");
		opponentAltTexture = new Texture("Merkel 2.png");
		
		opponentXPosition = ((Game.WIDTH / 9) * 5);
		
		textBoxQueue.add(new TextBox("Scientist Y",
				"There you are,  we need a talk,  air pollution is the biggest environmental "
				+ "health risk in Europe.  People are chocking,the planet is dying.  You're willing to stand by?"));

		textBoxQueue.add(new TextBox("Angela Merkel",
				"Was zur Hölle?"));

		textBoxQueue.add(new TextBox("Scientist Y",
				"What? Enough of the profanities, this is serious!"));
		
		textBoxQueue.add(new TextBox("Angela Merkel",
				"How dare you! I don't even know who you are, we are ze best in ze whole world for the environment."));
		
		textBoxQueue.add(new TextBox("Scientist Y",
				"Pointing fingers will get you nowhere. The time to act is now, we are already too late."));
		
		textBoxQueue.add(new TextBox("Angela Merkel",
				"Sheisse! Show me what you're made of."));
		
		textBoxQueue.add(new TextBox("Scientist Y",
				"Bring it on!"));
	}
	
	private void germanyWinInitialisation() {
		background = new Texture("European Parliament Background.png");
		opponentTexture = new Texture("Merkel 1.png");
		opponentAltTexture = new Texture("Merkel 2.png");
		
		opponentXPosition = ((Game.WIDTH / 9) * 5);
		
		textBoxQueue.add(new TextBox("Scientist Y",
				"You see! Now you are on track, your denial that you could do no more led us to this mess."));

		textBoxQueue.add(new TextBox("Angela Merkel",
				"You were right, sorry ... "));

		textBoxQueue.add(new TextBox("Scientist Y",
				"...  Now that I have dealt with you, only two more big polluters are left, Putin and Trump."));
		
		textBoxQueue.add(new TextBox("Angela Merkel",
				"You will never defeat them. Unmöglich!"));
		
		textBoxQueue.add(new TextBox("Scientist Y",
				"We'll see ..."));
	}
	
	private void russiaInitialisation() {
		background = new Texture("Kremlin Background.png");
		opponentTexture = new Texture("Putin 1.png");
		opponentAltTexture = new Texture("Putin 2.png");
		
		opponentXPosition = ((Game.WIDTH / 9) * 5);
		
		textBoxQueue.add(new TextBox("Scientist Y",
				"Mr. Putin, your layers of corruption and damage to this world are going to be torn apart like a Matryoshka doll."));

		textBoxQueue.add(new TextBox("Putin",
				"WHO ARE YOU?!?! GUARDS!"));

		textBoxQueue.add(new TextBox("Scientist Y",
				"Ok, maybe a little up-front. Look, we have no time, not acting now on our climate emergency will mean no Russia. "
				+ "The motherland will be childless."));
		
		textBoxQueue.add(new TextBox("Putin",
				"I've heard the rumours coming from Europe of a man like you, dishevelled, old and most of all not a good looker."
				+ " Angela is weak, I am not."));
		
		textBoxQueue.add(new TextBox("Scientist Y",
				"How dare you! My looks are most certainly not ... never mind."
				+ " Prove your worth, show your people your power, they will be watching."));
		
		textBoxQueue.add(new TextBox("Putin",
				"Your offer is insulting at best, this will be quick."));
		
		textBoxQueue.add(new TextBox("Scientist Y",
				"It sure will."));
	}
	
	private void russiaWinInitialisation() {
		background = new Texture("Kremlin Background.png");
		opponentTexture = new Texture("Putin 1.png");
		opponentAltTexture = new Texture("Putin 2.png");
		
		opponentXPosition = ((Game.WIDTH / 9) * 5);
		
		textBoxQueue.add(new TextBox("Scientist Y",
				"The Babushkas will be happy, their land will no longer be ravaged."));

		textBoxQueue.add(new TextBox("Putin",
				"Mother Russia owes you a great debt."));

		textBoxQueue.add(new TextBox("Scientist Y",
				"The one I seek now is Trump, he spreads lies to the whole western world."
				+ " More damaging than anything we have seen before."));
		
		textBoxQueue.add(new TextBox("Putin",
				"I know, it's my script. Now get out before I have my men throw you in the gulag."));
	}
	
	private void americaInitialisation() {
		background = new Texture("Whitehouse Background.png");
		opponentTexture = new Texture("Trump 1.png");
		opponentAltTexture = new Texture("Trump 2.png");
		
		opponentXPosition = (Game.WIDTH / 4) * 2;
		
		textBoxQueue.add(new TextBox("Scientist Y",
				"Donald, we finally meet. I have defeated everyone in my path, you are the only one left."));

		textBoxQueue.add(new TextBox("Trump",
				"I've seen what you have done, very impressive, I am the best for the environment, you wouldn't even know."
				+ " We have the best people on it, the best."));

		textBoxQueue.add(new TextBox("Scientist Y",
				"You emit 10% of the worlds CO2, most of which is completely avoidable."));
		
		textBoxQueue.add(new TextBox("Trump",
				"We have the cleanest air. I would know. Our air is so clean, the scientists are even baffled."
				+ " They come to me and say, Donald, how have you done this? Never has our air been cleaner."));
		
		textBoxQueue.add(new TextBox("Scientist Y",
				"Okk, how about this, the US is home to a quarter of the world's cars but only 5% of the world's population."));
		
		textBoxQueue.add(new TextBox("Trump",
				"Driving makes everyone healthier, you get to really stretch your legs."
				+ " More cars, more jobs, more money ... I mean ... more American. Yes, America."));
		
		textBoxQueue.add(new TextBox("Scientist Y",
				"This is never going to work, I'm going to have to show you myself."));
		
		textBoxQueue.add(new TextBox("Trump",
				"It's freezing and snowing in New York--we need global warming!"));
		
		textBoxQueue.add(new TextBox("Scientist Y",
				"It's time to cut you off, the Russians can't help you now."));
	}
	
	private void americaWinInitialisation() {
		background = new Texture("Whitehouse Background.png");
		opponentTexture = new Texture("Trump 1.png");
		opponentAltTexture = new Texture("Trump 2.png");
		
		opponentXPosition = (Game.WIDTH / 4) * 2;
		
		textBoxQueue.add(new TextBox("Scientist Y",
				"It's over, your lies are no more."));

		textBoxQueue.add(new TextBox("Trump",
				"All truths, no lies were ever spoken. I would know."));

		textBoxQueue.add(new TextBox("Scientist Y",
				"No one will have to hear you speak again. The world now knows who you really are, who you really serve."));
	}
	
	private void endInitialisation() {
		background = new Texture("LabBackground.png");
		opponentTexture = new Texture("BlankCut.png");
		opponentAltTexture = new Texture("BlankCut.png");
		
		opponentXPosition = ((Game.WIDTH / 9) * 5);
		
		textBoxQueue.add(new TextBox("Scientist Y",
				"Now I can finally rest, my work is done."));

		textBoxQueue.add(new TextBox("Scientist Y",
				"A Global team set up to never allow this to happen again. My faith has been restored in humanity."));

		textBoxQueue.add(new TextBox("Phone",
				"*BZZZ BZZZ*"));
		
		textBoxQueue.add(new TextBox("Phone",
				"## NEWS ALERT COVID-19 Officially A PANDEMIC ##"));
		
		textBoxQueue.add(new TextBox("Scientist Y",
				"Oh F*** Not again."));
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
			if(currentLevel == 0) {
				gsm.setState(StateEnum.MAP_STATE);
				gsm.removeState(StateEnum.CUTSCENE_STATE);
				dispose();			
			} else if (currentLevel == 99) {
				Gdx.app.exit();
			} else if (currentLevel > 3) {
				gsm.wonLevel();
				((MapState) gsm.setState(StateEnum.MAP_STATE)).getPreviusMarker().complete();
				gsm.removeState(StateEnum.CUTSCENE_STATE);
				dispose();		
			} else {
				gsm.setStateAsNew(new PlayState(gsm, currentLevel + 3), StateEnum.PLAY_STATE);
				gsm.removeState(StateEnum.CUTSCENE_STATE);
				dispose();
			}
			
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
