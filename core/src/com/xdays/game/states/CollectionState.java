package com.xdays.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.xdays.game.Game;
import com.xdays.game.assets.CircularList;
import com.xdays.game.cards.CardCollection;

public class CollectionState extends State {

	private CircularList<CollectionPage> displayPages;
	private Texture background;
	private Texture playBtn;
	private CollectionPage currentPage;

	// TODO need to change the page size as some sort of constant
	
	public CollectionState(GameStateManager gsm, CardCollection cardCollection) {
		super(gsm);
		cam.setToOrtho(false, Game.WIDTH, Game.HEIGHT);
		background = new Texture("Title.png");
		playBtn = new Texture("playButton.PNG");
		displayPages = new CircularList<CollectionPage>();
		createPages(cardCollection);
		currentPage = displayPages.get(0);
	}

	public void createPages(CardCollection cardCollection) {
		// will create a page for every 9 cards in the collection
		for (int x = 0; x < (int) Math.ceil((double) cardCollection.getSize() / 9); x++) {
			CollectionPage page = new CollectionPage(cardCollection.getAllCards(), x);
			displayPages.add(page);
		}
	}

	@Override
	protected void handleInput() {
		if (Gdx.input.justTouched()) {
			System.out.println("Play button pressed");
			currentPage = displayPages.get(displayPages.indexOf((currentPage))+1);
		}

	}

	@Override
	public void update(float dt) {
		handleInput();
		render(Game.batch);
	}

	@Override
	public void render(SpriteBatch sb) {
		sb.setProjectionMatrix(cam.combined);
		sb.begin();
		sb.draw(background, 0, 0);
		sb.draw(playBtn, (cam.position.x - playBtn.getWidth() / 2), (cam.position.y - playBtn.getHeight() / 2) - 150);
		currentPage.displayCollectionCards(sb);
		sb.end();
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
	}

}
