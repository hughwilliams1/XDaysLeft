package com.xdays.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.xdays.game.Game;
import com.xdays.game.assets.Button;
import com.xdays.game.cutscenes.StartCutsceneState;

public class MapState extends State {
	private static final int BTN_WIDTH = 200;
	private static final int BTN_HEIGHT = 60;

	private Texture background;

	private Marker americaMarker;
	private Marker germanyMarker;
	private Marker russiaMarker;

	private Button tutMarker;
	private Button collectionBtn;
	private Button homeBtn;
	private int currentLevel;

	static Music mainMenuMusic;
	private Sound clickSound;

	public MapState(GameStateManager gsm) {
		super(gsm);

		clickSound = Gdx.audio.newSound(Gdx.files.internal("sounds/ClickSound.wav"));

		cam.setToOrtho(false, Game.WIDTH, Game.HEIGHT);
		background = new Texture("Area Selection2.png");

		americaMarker = new Marker((cam.position.x - 475), (cam.position.y + 150));
		germanyMarker = new Marker((cam.position.x - 70), (cam.position.y + 180));
		russiaMarker = new Marker ((cam.position.x + 220), (cam.position.y + 220));

		tutMarker = new Button((int) americaMarker.getWidth(), (int) americaMarker.getHeight(), Game.WIDTH - 190, 180,
				"Marker.PNG", "Marker Hover.PNG");

		int x = (Game.WIDTH / 2 - BTN_WIDTH / 2);
		collectionBtn = new Button(BTN_WIDTH, BTN_HEIGHT, x + (BTN_WIDTH / 2) + 30,
				(Game.HEIGHT / 2 - BTN_HEIGHT / 2) - (110 + (BTN_HEIGHT * 3) + 30), "DeckBtn.PNG");

		homeBtn = new Button(BTN_WIDTH, BTN_HEIGHT, x - (BTN_WIDTH / 2) - 30,
				(Game.HEIGHT / 2 - BTN_HEIGHT / 2) - (110 + (BTN_HEIGHT * 3) + 30), "HomeBtn.PNG");
		
		currentLevel = 0;
	}

	@Override
	protected void handleInput() {

		if (Gdx.input.justTouched()) {
			Rectangle bounds = new Rectangle(Gdx.input.getX(), -(Gdx.input.getY() - 720), 0.01f, 0.01f);

			MenuState.mainMenuMusic.pause();

			if (bounds.overlaps(americaMarker.getBounds()) && !americaMarker.isCompleted()) {
				clickSound.play();
				gsm.setStateAsNew(new StartCutsceneState(gsm, 3), StateEnum.CUTSCENE_STATE);
				americaMarker.complete();
			}

			if (bounds.overlaps(germanyMarker.getBounds()) && !germanyMarker.isCompleted()) {
				clickSound.play();
				gsm.setStateAsNew(new StartCutsceneState(gsm, 1), StateEnum.CUTSCENE_STATE);
				germanyMarker.complete();
			}
			
			if (bounds.overlaps(russiaMarker.getBounds()) && !russiaMarker.isCompleted()) {
				clickSound.play();
				gsm.setStateAsNew(new StartCutsceneState(gsm, 2), StateEnum.CUTSCENE_STATE);
				russiaMarker.complete();
			}

			// if collectionBtn is clicked changed to collection state
			if (collectionBtn.isPointerOver(Gdx.input.getX(), Gdx.input.getY())) {
				clickSound.play();
				gsm.setState(StateEnum.COLLECTION_STATE);
			}

			// if collectionBtn is clicked changed to collection state
			if (homeBtn.isPointerOver(Gdx.input.getX(), Gdx.input.getY())) {
				clickSound.play();
				gsm.setState(StateEnum.MENU_STATE);
			}

			// if tutorialBtn is clicked changed to collection state
			if (tutMarker.isPointerOver(Gdx.input.getX(), Gdx.input.getY())) {
				clickSound.play();
				gsm.setState(StateEnum.TUTORIAL_STATE);
			}
		}

	}

	@Override
	public void update(float dt) {
		handleInput();
	}

	@Override
	public void render(SpriteBatch sb) {
		if (!MenuState.mainMenuMusic.isPlaying()) {
			MenuState.mainMenuMusic.play();
		}

		sb.setProjectionMatrix(cam.combined);
		sb.begin();
		sb.draw(background, 0, 0);
		renderAllMarkers(sb);
		tutMarker.draw(sb);
		collectionBtn.draw(sb);
		homeBtn.draw(sb);
		sb.end();
	}

	private void renderAllMarkers(SpriteBatch sb) {
		Rectangle bounds = new Rectangle(Gdx.input.getX(), -(Gdx.input.getY() - 720), 1, 1);

		if (bounds.overlaps(americaMarker.getBounds())) {
			americaMarker.drawHoverMarker(sb);
		} else {
			americaMarker.drawNormalMarker(sb);
		}

		if (bounds.overlaps(germanyMarker.getBounds())) {
			germanyMarker.drawHoverMarker(sb);
		} else {
			germanyMarker.drawNormalMarker(sb);
		}
		
		if (bounds.overlaps(russiaMarker.getBounds())) {
			russiaMarker.drawHoverMarker(sb);
		} else {
			russiaMarker.drawNormalMarker(sb);
		}

		if (tutMarker.isPointerOver(Gdx.input.getX(), Gdx.input.getY())) {
			tutMarker.hovering();
		} else {
			tutMarker.notHovering();
		}
	}

	@Override
	public void dispose() {
	}

	class Marker {
		private Texture normalMarker;
		private Texture hoverMarker;
		private Texture completedMarker;
		private Texture redMark;
		private Texture redMarkHover;
		private Rectangle bounds;
		private final int DEFAULT_SCALE = 3;
		private boolean completed;

		public Marker(float x, float y) {
			normalMarker = new Texture("Marker.png");
			hoverMarker = new Texture("Marker Hover.png");
			completedMarker = new Texture("Marker Completed.png");
			redMark = new Texture("RedMark.png");
			redMarkHover = new Texture("RedMark2.png");
			bounds = new Rectangle(x, y, normalMarker.getWidth() / DEFAULT_SCALE,
					normalMarker.getHeight() / DEFAULT_SCALE);
			completed = false;
		}

		public boolean isCompleted() {
			return completed;
		}

		public void complete() {
			completed = true;
		}

		public Rectangle getBounds() {
			return bounds;
		}

		public Texture getHoverMarker() {
			return hoverMarker;
		}

		public Texture getNormalMarker() {
			return normalMarker;
		}

		public float getX() {
			return bounds.getX();
		}

		public float getY() {
			return bounds.getY();
		}

		public float getWidth() {
			return bounds.getWidth();
		}

		public float getHeight() {
			return bounds.getHeight();
		}

		public void drawNormalMarker(SpriteBatch sb) {
			if (completed) {
				sb.draw(completedMarker, getX(), getY(), getWidth(), getHeight());
			} else {
				sb.draw(redMark, getX() - 85, getY() - 85, getWidth() * 5, getHeight() * 5);
				sb.draw(normalMarker, getX(), getY(), getWidth(), getHeight());
			}
		}

		public void drawHoverMarker(SpriteBatch sb) {
			if (completed) {
				sb.draw(completedMarker, getX(), getY(), getWidth(), getHeight());
			} else {
				sb.draw(redMarkHover, getX() - 85, getY() - 85, getWidth() * 5, getHeight() * 5);
				sb.draw(hoverMarker, getX(), getY(), getWidth(), getHeight());
			}
		}
	}

}
