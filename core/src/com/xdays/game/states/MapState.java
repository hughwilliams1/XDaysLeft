package com.xdays.game.states;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.xdays.game.Game;
import com.xdays.game.assets.Button;
import com.xdays.game.cutscenes.CutsceneState;

/**  
 * MapState.java - a map state main menu.  
 *
 * @author  Damian Hobeanu, Mark Ebel, Roberto Lovece, Ronil Goldenwalla, Hugh Williams
 * @version 1.0 
 * @see Class {@link State}
 */ 

public class MapState extends State {
	private static final int BTN_WIDTH = 200;
	private static final int BTN_HEIGHT = 60;

	private Texture background;
	
	private HashMap<String, Marker> markers;

	private Marker previousMarker;
	private Button tutMarker;
	private Button winMarker;
	private Button collectionBtn;
	private Button homeBtn;

	static Music mainMenuMusic;
	private Sound clickSound;
	
	private BitmapFont font;
	
	/**
	 * Sets up the markers for each leading to the different states as well as some
	 * buttons
	 * 
	 * @param gsm	used for libgdx rendering
	 */

	public MapState(GameStateManager gsm) {
		super(gsm);
		clickSound = Gdx.audio.newSound(Gdx.files.internal("sounds/ClickSound.wav"));

		cam.setToOrtho(false, Game.WIDTH, Game.HEIGHT);
		background = (Texture) Game.assetManager.get("Area Selection2.PNG");
		
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
		
		font = (BitmapFont) Game.assetManager.get("font/Staatliches-Regular35.ttf");
	}

	@Override
	protected void handleInput() {

		if (Gdx.input.justTouched()) {
			Rectangle bounds = new Rectangle(Gdx.input.getX(), -(Gdx.input.getY() - 720), 0.01f, 0.01f);

			MenuState.mainMenuMusic.pause();
			
			// checks if the cursor overlaps any markers
			for(String key : markers.keySet()) {
				if(bounds.overlaps(markers.get(key).getBounds())) {
					markers.get(key).handleInput();
					setPreviousMarker(markers.get(key));
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
	
	/**
	 * set the previous marker to the last one
	 * 
	 * @param givenMarker	the marker you want to set
	 */
	
	public void setPreviousMarker(Marker givenMarker) {
		previousMarker = givenMarker;
	}
	
	/**
	 * @return	returns if all level are complete
	 */
	
	public boolean areAllLevelsComplete() {
		if(getCompletedLevels()==markers.size()) {
			return true;
		}else {
			return false;
		}
	}
	
	/**
	 * @return	a array of all the completed levels
	 */
	
	public String[] getCompletedLevelsName() {
		String[] completedLevels = new String[markers.size()];
		int j=0;
		for(String key: markers.keySet()) {
			if(markers.get(key).isCompleted()) {
				completedLevels[j]=key;
				j++;
			}
		}
		return completedLevels;
	}
	
	/**
	 * loads the levels based on the save
	 * 
	 * @param completedLevels	the level completed in last save file
	 */
	
	public void loadLevels(String[] completedLevels) {
		for(int i=0; i<completedLevels.length; i++) {
			if(!completedLevels[i].contentEquals("null")) {
				markers.get(completedLevels[i]).complete();	
				previousMarker = markers.get(completedLevels[i]);
			}
		}
	}
	
	/**
	 * displays the levels completed out of the total levels
	 * 
	 * @param sb	used for libgdx rendering
	 */
	
	public void displayCompletedLevels(SpriteBatch sb) {		
		font.draw(sb, getCompletedLevels()+ "/" + markers.size() + " zones completed", 25,50);
	}
	
	/**
	 * @return 	the number of completed levels
	 */
	
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

	/**
	 * Renders all the markers including whether they've been hovered over and if they're completed
	 * 
	 * @param sb	Used for libgdx rendering
	 */
	
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
	
	/**
	 * @return	get the previous marker
	 */
	
	public Marker getPreviousMarker() {
		return previousMarker;
	}

	@Override
	public void dispose() {
	}

	public class Marker {
		
		/**  
		  * Marker.java - a marker for the map state.  
		  *
		  * @author  Damian Hobeanu, Mark Ebel, Roberto Lovece, Ronil Goldenwalla, Hugh Williams
		  * @version 1.0 
		  */ 
		
		private int cutscene;
		private Marker previousMarker;
		private Texture notAvailableMarker;
		private Texture normalMarker;
		private Texture hoverMarker;
		private Texture completedMarker;
		private Texture redMark;
		private Texture redMarkHover;
		private Rectangle bounds;
		private final int DEFAULT_SCALE = 3;
		private boolean completed;
		
		/**
		 * 
		 * 
		 * @param x					x position of the marker
		 * @param y					y position of the marker
		 * @param cutscene			the cutscene the marker leads to
		 * @param previousMarker	the previous marker following it
		 */

		public Marker(float x, float y, int cutscene, Marker previousMarker) {
			this.previousMarker = previousMarker;
			this.cutscene = cutscene;
			normalMarker = (Texture) Game.assetManager.get("Marker.PNG");
			hoverMarker = (Texture) Game.assetManager.get("Marker Hover.PNG");
			completedMarker = (Texture) Game.assetManager.get("Marker Completed.PNG");
			notAvailableMarker = (Texture) Game.assetManager.get("Marker Not Available.PNG");
			redMark = (Texture) Game.assetManager.get("RedMark.PNG");
			redMarkHover = (Texture) Game.assetManager.get("RedMark2.PNG");
			bounds = new Rectangle(x, y, normalMarker.getWidth() / DEFAULT_SCALE,
					normalMarker.getHeight() / DEFAULT_SCALE);
			completed = false;
		}
		
		/**
		 * handles what happens when you click a marker changes it to the cutscene as well as stopping you 
		 * from going to the next marker without completing the prior markers
		 */
		
		public void handleInput() {
			if(previousMarker==null) {
				if(!isCompleted()) {
					clickSound.play();
					gsm.setStateAsNew(new CutsceneState(gsm, cutscene), StateEnum.CUTSCENE_STATE);
				}
			}else if(!isCompleted()){
				clickSound.play();
				gsm.setStateAsNew(new CutsceneState(gsm, cutscene), StateEnum.CUTSCENE_STATE);
			}
		}
		
		/**
		 * @return	whether a marker is completed
		 */

		public boolean isCompleted() {
			return completed;
		}

		/**
		 * changes completed to true
		 */
		
		public void complete() {
			completed = true;
		}
		
		/**
		 * @return	the bounds of marker 
		 */

		public Rectangle getBounds() {
			return bounds;
		}
		
		/**
		 * @return	hover marker texture
		 */

		public Texture getHoverMarker() {
			return hoverMarker;
		}
		
		/**
		 * @return	normal marker texture
		 */

		public Texture getNormalMarker() {
			return normalMarker;
		}
		
		/**
		 * @return	x value of the bounds
		 */

		public float getX() {
			return bounds.getX();
		}
		
		/**
		 * @return	y value of the bounds
		 */

		public float getY() {
			return bounds.getY();
		}
		
		/**
		 * @return	width of the bounds
		 */

		public float getWidth() {
			return bounds.getWidth();
		}
		
		/**
		 * @return	height of the bound
		 */

		public float getHeight() {
			return bounds.getHeight();
		}
		
		/**
		 * draws normal markers 
		 * 
		 * @param sb	libgdx uses for rendering 
		 */

		public void drawNormalMarker(SpriteBatch sb) {
			if (completed) {
				sb.draw(completedMarker, getX(), getY(), getWidth(), getHeight());
			}else if(previousMarker!=null && !previousMarker.isCompleted()) {
				sb.draw(redMark, getX() - 85, getY() - 85, getWidth() * 5, getHeight() * 5);
				sb.draw(notAvailableMarker, getX(), getY(), getWidth(), getHeight());
			}else {
				sb.draw(redMark, getX() - 85, getY() - 85, getWidth() * 5, getHeight() * 5);
				sb.draw(normalMarker, getX(), getY(), getWidth(), getHeight());
			}
		}
		
		/**
		 * draws the hover markers
		 * 
		 * @param sb	libgdx uses for rendering
		 */

		public void drawHoverMarker(SpriteBatch sb) {
			if (completed) {
				sb.draw(completedMarker, getX(), getY(), getWidth(), getHeight());
			}else if(previousMarker!=null && !previousMarker.isCompleted()) {
				sb.draw(redMark, getX() - 85, getY() - 85, getWidth() * 5, getHeight() * 5);
				sb.draw(notAvailableMarker, getX(), getY(), getWidth(), getHeight());
			}else {
				sb.draw(redMarkHover, getX() - 85, getY() - 85, getWidth() * 5, getHeight() * 5);
				sb.draw(hoverMarker, getX(), getY(), getWidth(), getHeight());
			}
		}
	}

}
