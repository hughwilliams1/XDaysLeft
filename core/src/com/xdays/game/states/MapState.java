package com.xdays.game.states;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.math.Rectangle;
import com.xdays.game.Game;
import com.xdays.game.assets.Button;
import com.xdays.game.cutscenes.CutsceneState;

public class MapState extends State {
	private static final int BTN_WIDTH = 200;
	private static final int BTN_HEIGHT = 60;

	private Texture background;
	
	private HashMap<String, Marker> markers;

	private Marker previusMarker;
	private Button tutMarker;
	private Button winMarker;
	private Button collectionBtn;
	private Button homeBtn;

	static Music mainMenuMusic;
	private Sound clickSound;
	
	private BitmapFont font;

	public MapState(GameStateManager gsm) {
		super(gsm);
		clickSound = Gdx.audio.newSound(Gdx.files.internal("sounds/ClickSound.wav"));

		cam.setToOrtho(false, Game.WIDTH, Game.HEIGHT);
		background = new Texture("Area Selection2.png");
		
		markers = new HashMap<String, Marker>();
		markers.put("germany", new Marker((cam.position.x - 70), (cam.position.y + 180), 1, null));
		markers.put("russia", new Marker ((cam.position.x + 220), (cam.position.y + 220), 2, markers.get("germany")));
		markers.put("america", new Marker((cam.position.x - 475), (cam.position.y + 150), 3, markers.get("russia")));

		tutMarker = new Button((int) markers.get("america").getWidth(), (int) markers.get("america").getHeight(), Game.WIDTH - 190, 180,
				"Marker.PNG", "Marker Hover.PNG");
		
		winMarker = new Button((int) markers.get("america").getWidth(), (int) markers.get("america").getHeight(), Game.WIDTH / 2, 350,
				"Marker.PNG", "Marker Hover.PNG");

		int x = (Game.WIDTH / 2 - BTN_WIDTH / 2);
		collectionBtn = new Button(BTN_WIDTH, BTN_HEIGHT, x + (BTN_WIDTH / 2) + 30,
				(Game.HEIGHT / 2 - BTN_HEIGHT / 2) - (110 + (BTN_HEIGHT * 3) + 30), "DeckBtn.PNG");

		homeBtn = new Button(BTN_WIDTH, BTN_HEIGHT, x - (BTN_WIDTH / 2) - 30,
				(Game.HEIGHT / 2 - BTN_HEIGHT / 2) - (110 + (BTN_HEIGHT * 3) + 30), "MenuBtn.PNG");
		
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font/Staatliches-Regular.ttf"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 35;
		font = generator.generateFont(parameter);
	}

	@Override
	protected void handleInput() {

		if (Gdx.input.justTouched()) {
			Rectangle bounds = new Rectangle(Gdx.input.getX(), -(Gdx.input.getY() - 720), 0.01f, 0.01f);

			MenuState.mainMenuMusic.pause();
			
			for(String key : markers.keySet()) {
				if(bounds.overlaps(markers.get(key).getBounds())) {
					markers.get(key).handleInput();
					previusMarker= markers.get(key);
				}
			}

			// if collectionBtn is clicked changed to collection state
			if (collectionBtn.isPointerOver(Gdx.input.getX(), Gdx.input.getY())) {
				clickSound.play();
				gsm.setState(StateEnum.COLLECTION_STATE);
			}

			// if homeBtn is clicked changed to collection state
			if (homeBtn.isPointerOver(Gdx.input.getX(), Gdx.input.getY())) {
				clickSound.play();
				gsm.setState(StateEnum.PAUSE_STATE);
			}

			// if tutorialBtn is clicked changed to collection state
			if (tutMarker.isPointerOver(Gdx.input.getX(), Gdx.input.getY())) {
				clickSound.play();
				gsm.setState(StateEnum.TUTORIAL_STATE);
			}
			
			// if collectionBtn is clicked changed to collection state
			if (winMarker.isPointerOver(Gdx.input.getX(), Gdx.input.getY()) && areAllLevelsComplete()) {
				clickSound.play();
				gsm.setStateAsNew(new CutsceneState(gsm, 99), StateEnum.CUTSCENE_STATE);
			}
		}
	}
	
	public boolean areAllLevelsComplete() {
		if(getCompletedLevels()==markers.size()) {
			return true;
		}else {
			return false;
		}
	}
	
	public void displayCompletedLevels(SpriteBatch sb) {		
		font.draw(sb, getCompletedLevels()+ "/" + markers.size() + " zones completed", 10,50);
	}
	
	public int getCompletedLevels() {
		int completedLevels = 0;
		for(String key : markers.keySet()) {
			if(markers.get(key).isCompleted()) {
				completedLevels++;
			}
		}
		return completedLevels;
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
		if (areAllLevelsComplete()) {
			winMarker.draw(sb);
			font.draw(sb, "Complete Game!", Game.WIDTH / 2 - 90, 350);
		}
		sb.end();
	}

	private void renderAllMarkers(SpriteBatch sb) {
		Rectangle bounds = new Rectangle(Gdx.input.getX(), -(Gdx.input.getY() - 720), 1, 1);
		
		for(String key : markers.keySet()) {
			if(bounds.overlaps(markers.get(key).getBounds())) {
				markers.get(key).drawHoverMarker(sb);
			}else {
				markers.get(key).drawNormalMarker(sb);
			}
		}

		if (tutMarker.isPointerOver(Gdx.input.getX(), Gdx.input.getY())) {
			tutMarker.hovering();
		} else {
			tutMarker.notHovering();
		}
		
		if (winMarker.isPointerOver(Gdx.input.getX(), Gdx.input.getY())) {
			winMarker.hovering();
		} else {
			winMarker.notHovering();
		}
		
		displayCompletedLevels(sb);
	}
	
	public Marker getPreviusMarker() {
		return previusMarker;
	}

	@Override
	public void dispose() {
	}

	public class Marker {
		
		private int cutscene;
		private Marker previusMarker;
		private Texture notAvailableMarker;
		private Texture normalMarker;
		private Texture hoverMarker;
		private Texture completedMarker;
		private Texture redMark;
		private Texture redMarkHover;
		private Rectangle bounds;
		private final int DEFAULT_SCALE = 3;
		private boolean completed;

		public Marker(float x, float y, int cutscene, Marker previousMarker) {
			this.previusMarker = previousMarker;
			this.cutscene = cutscene;
			normalMarker = new Texture("Marker.png");
			hoverMarker = new Texture("Marker Hover.png");
			completedMarker = new Texture("Marker Completed.png");
			notAvailableMarker = new Texture("Marker Not Available.png");
			redMark = new Texture("RedMark.png");
			redMarkHover = new Texture("RedMark2.png");
			bounds = new Rectangle(x, y, normalMarker.getWidth() / DEFAULT_SCALE,
					normalMarker.getHeight() / DEFAULT_SCALE);
			completed = false;
		}
		
		public void handleInput() {
			if(previusMarker==null || previusMarker.isCompleted()) {
				clickSound.play();
				gsm.setStateAsNew(new CutsceneState(gsm, cutscene), StateEnum.CUTSCENE_STATE);
			}else {
				System.out.println("Previous marker not completed.");
			}
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
			}else if(previusMarker!=null && !previusMarker.isCompleted()) {
				sb.draw(redMark, getX() - 85, getY() - 85, getWidth() * 5, getHeight() * 5);
				sb.draw(notAvailableMarker, getX(), getY(), getWidth(), getHeight());
			}else {
				sb.draw(redMark, getX() - 85, getY() - 85, getWidth() * 5, getHeight() * 5);
				sb.draw(normalMarker, getX(), getY(), getWidth(), getHeight());
			}
		}

		public void drawHoverMarker(SpriteBatch sb) {
			if (completed) {
				sb.draw(completedMarker, getX(), getY(), getWidth(), getHeight());
			}else if(previusMarker!=null && !previusMarker.isCompleted()) {
				sb.draw(redMark, getX() - 85, getY() - 85, getWidth() * 5, getHeight() * 5);
				sb.draw(notAvailableMarker, getX(), getY(), getWidth(), getHeight());
			}else {
				sb.draw(redMarkHover, getX() - 85, getY() - 85, getWidth() * 5, getHeight() * 5);
				sb.draw(hoverMarker, getX(), getY(), getWidth(), getHeight());
			}
		}
	}

}
