package com.xdays.game.states;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.xdays.game.Game;
import com.xdays.game.User;
import com.xdays.game.assets.Button;
import com.xdays.game.cards.CardCollection;

public class MapState extends State {
	private static final int BTN_WIDTH = 200;
	private static final int BTN_HEIGHT = 60;

	private Texture background;
	private ArrayList<Marker> markers;
	private Button collectionBtn;

	public MapState(GameStateManager gsm) {
		super(gsm);
		cam.setToOrtho(false, Game.WIDTH, Game.HEIGHT);
		background = new Texture("Area Selection.png");
		markers = new ArrayList<Marker>();
		markers.add(new Marker((cam.position.x - 335), (cam.position.y - 120)));
		markers.add(new Marker((cam.position.x - 350), (cam.position.y - 220)));

		int x = (Game.WIDTH / 2 - BTN_WIDTH / 2);
		// TODO give collectionBtn a texture
		collectionBtn = new Button(BTN_WIDTH, BTN_HEIGHT, x,
				(Game.HEIGHT / 2 - BTN_HEIGHT / 2) - (110 + (BTN_HEIGHT * 3) + 30), "QuitBtn.PNG");
	}

	@Override
	protected void handleInput() {
		if (Gdx.input.justTouched()) {
			Rectangle bounds = new Rectangle(Gdx.input.getX(), -(Gdx.input.getY() - 720), 1, 1);
			for (int i = 0; i < markers.size(); i++) {
				if (bounds.overlaps(markers.get(i).getBounds())) {
					gsm.set(new PlayState(gsm));
				}
			}
		}

		// if collectionBtn is clicked changed to collection state
		if (Gdx.input.justTouched() && collectionBtn.isPointerOver(Gdx.input.getX(), Gdx.input.getY(), Game.HEIGHT)) {
			gsm.set(new CollectionState(gsm, new CardCollection(), new User("Player 1", new CardCollection())));
		}
	}

	@Override
	public void update(float dt) {
		handleInput();
	}

	@Override
	public void render(SpriteBatch sb) {
		// System.out.println("Here");
		sb.setProjectionMatrix(cam.combined);
		sb.begin();
		sb.draw(background, 0, 0);
		renderAllMarkers(sb);
		collectionBtn.draw(sb);
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
		// TODO Auto-generated method stub

	}

	private class Marker {
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
