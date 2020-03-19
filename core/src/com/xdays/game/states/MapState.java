package com.xdays.game.states;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.xdays.game.Game;
import com.xdays.game.User;
import com.xdays.game.assets.Button;
import com.xdays.game.cards.CardCollection;
import com.xdays.game.cutscenes.StartLevel1CutsceneState;

public class MapState extends State {
	private static final int BTN_WIDTH = 200;
	private static final int BTN_HEIGHT = 60;

	private Texture background;
	private ArrayList<Marker> markers;
	private Button collectionBtn;
	private Button homeBtn;
	
    static Music mainMenuMusic;
    private Sound clickSound;

	public MapState(GameStateManager gsm) {
		super(gsm);
		
        clickSound = Gdx.audio.newSound(Gdx.files.internal("sounds/ClickSound.wav"));
		
		cam.setToOrtho(false, Game.WIDTH, Game.HEIGHT);
		background = new Texture("Area Selection.png");
		markers = new ArrayList<Marker>();
		markers.add(new Marker((cam.position.x - 335), (cam.position.y - 120)));
		markers.add(new Marker((cam.position.x - 350), (cam.position.y - 220)));

		int x = (Game.WIDTH / 2 - BTN_WIDTH / 2);
		collectionBtn = new Button(BTN_WIDTH, BTN_HEIGHT, x + (BTN_WIDTH/2) + 30,
				(Game.HEIGHT / 2 - BTN_HEIGHT / 2) - (110 + (BTN_HEIGHT * 3) + 30), "DeckBtn.PNG");
		
		homeBtn = new Button(BTN_WIDTH, BTN_HEIGHT, x - (BTN_WIDTH/2) - 30, 
				(Game.HEIGHT / 2 - BTN_HEIGHT / 2) - (110 + (BTN_HEIGHT * 3) + 30), "HomeBtn.PNG");
	}

	@Override
	protected void handleInput() {
		if (Gdx.input.justTouched()) {
			Rectangle bounds = new Rectangle(Gdx.input.getX(), -(Gdx.input.getY() - 720), 1, 1);
			//Pause the background music when going into a battle
			MenuState.mainMenuMusic.pause();
			for (int i = 0; i < markers.size(); i++) {
				if (bounds.overlaps(markers.get(i).getBounds())) {
					clickSound.play();
					gsm.setStateAsNew(new StartLevel1CutsceneState(gsm), StateEnum.CUTSCENE_STATE);
				}
			}
		}

		// if collectionBtn is clicked changed to collection state
		if (Gdx.input.justTouched() && collectionBtn.isPointerOver(Gdx.input.getX(), Gdx.input.getY())) {
			clickSound.play();
			gsm.setState(StateEnum.COLLECTION_STATE);
		}
		
		// if collectionBtn is clicked changed to collection state
		if (Gdx.input.justTouched() && homeBtn.isPointerOver(Gdx.input.getX(), Gdx.input.getY())) {
			clickSound.play();
			gsm.setState(StateEnum.MENU_STATE);
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
		sb.draw(background, 0, 0);
		renderAllMarkers(sb);
		collectionBtn.draw(sb);
		homeBtn.draw(sb);
		sb.end();
	}

	private void renderAllMarkers(SpriteBatch sb) {
		Rectangle bounds = new Rectangle(Gdx.input.getX(), -(Gdx.input.getY() - 720), 1, 1);
		for (int i = 0; i < markers.size(); i++) {
			if (bounds.overlaps(markers.get(i).getBounds())) {
				markers.get(i).drawHoverMarker(sb);
			} else {
				markers.get(i).drawNormalMarker(sb);
			}
		}
	}

	@Override
	public void dispose() {
	}

	class Marker {
		private Texture normalMarker;
		private Texture hoverMarker;
		private Rectangle bounds;
		private final int DEFAULT_SCALE = 3;

		public Marker(float x, float y) {
			normalMarker = new Texture("Marker.png");
			hoverMarker = new Texture("Marker Hover.png");
			bounds = new Rectangle(x, y, normalMarker.getWidth() / DEFAULT_SCALE,
					normalMarker.getHeight() / DEFAULT_SCALE);
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
				sb.draw(normalMarker, getX(), getY(), getWidth(), getHeight());
		}

		public void drawHoverMarker(SpriteBatch sb) {
			sb.draw(hoverMarker, getX(), getY(), getWidth(), getHeight());
		}
	}

}
