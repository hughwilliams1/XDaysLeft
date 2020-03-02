package com.xdays.game.states;

import com.badlogic.gdx.math.Rectangle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.xdays.game.Game;
import com.xdays.game.User;
import com.xdays.game.assets.Button;
import com.xdays.game.assets.CircularList;
import com.xdays.game.cards.Card;
import com.xdays.game.cards.CardCollection;

public class CollectionState extends State {

	private CircularList<CollectionPage> collectionDisplayPages;
	private CircularList<CollectionPage> playerDisplayPages;
	
	private CollectionPage currentCollectionPage;
	private CollectionPage currentPlayerPage;
	
	private static final int BTN_WIDTH = 150;
	private static final int BTN_HEIGHT = 45;
	
	private Texture background;
	
	private Button collectionNextPageBtn;
	private Button playerNextPageBtn;
	
	
	protected static final int CARDS_PER_PAGE = 9;
	
	public CollectionState(GameStateManager gsm, CardCollection cardCollection, User player) {
		super(gsm);
		cam.setToOrtho(false, Game.WIDTH, Game.HEIGHT);
		// textures
		background = new Texture("collectionBackground.png");
		
		// Buttons
		// TODO give btns a texture
		collectionNextPageBtn = new Button(BTN_WIDTH, BTN_HEIGHT, ((Game.WIDTH / 6 ) * 3 ) - 180, 15, "PlayBtn.PNG");
		playerNextPageBtn = new Button(BTN_WIDTH, BTN_HEIGHT, ((Game.WIDTH / 6 ) * 3 ) + 30, 15, "PlayBtn.PNG");
		
		// Circular pages array
		collectionDisplayPages = new CircularList<CollectionPage>();
		playerDisplayPages = new CircularList<CollectionPage>();
		
		createPages(cardCollection, player);
		currentCollectionPage = collectionDisplayPages.get(0);
		currentPlayerPage = playerDisplayPages.get(0);
	}

	public void createPages(CardCollection cardCollection, User player) {
		
		int collectionPages = (int) Math.ceil((double) cardCollection.getSize() / CARDS_PER_PAGE);
		int playerPages = (int) Math.ceil((double) player.getDeck().getDeckSize() / CARDS_PER_PAGE);
		
		for (int x = 0; x < collectionPages; x++) {
			CollectionPage page = new CollectionPage(cardCollection.getAllCards(), x);
			collectionDisplayPages.add(page);
		}
		
		for (int x = 0; x < playerPages; x++) {
			CollectionPage page = new CollectionPage(player, x);
			playerDisplayPages.add(page);
		}
	}

	@Override
	protected void handleInput() {
		if(Gdx.input.justTouched() && collectionNextPageBtn.isPointerOver(Gdx.input.getX(), Gdx.input.getY(), Game.HEIGHT)){
			currentCollectionPage = collectionDisplayPages.get(collectionDisplayPages.indexOf((currentCollectionPage))+1);
        }
		
		if (Gdx.input.justTouched() && playerNextPageBtn.isPointerOver(Gdx.input.getX(), Gdx.input.getY(), Game.HEIGHT)) {
			currentPlayerPage = playerDisplayPages.get(playerDisplayPages.indexOf((currentPlayerPage))+1);
		}
		
		// Checks if any collection cards are touched by comparing the card bounds to a small rectangle created around the mouse
		for (Card card : currentCollectionPage.getDisplayedCards()) {
			if (Gdx.input.justTouched() && card.getBounds().overlaps(new Rectangle(Gdx.input.getX(), -(Gdx.input.getY() - 720), 0.01f, 0.01f))) {
				System.out.print("Collection Card: " + card.getTitle() + " was touched \n");
			}
		}
		
		// Checks if any player cards are touched by comparing the card bound to a small rectangle created around the mouse
		for (Card card : currentPlayerPage.getDisplayedCards()) {
			if (Gdx.input.justTouched() && card.getBounds().overlaps(new Rectangle(Gdx.input.getX(), -(Gdx.input.getY() - 720), 0.01f, 0.01f))) {
				System.out.print("Player Card: " + card.getTitle() + " was touched \n");
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
		sb.setProjectionMatrix(cam.combined);
		sb.begin();
		sb.draw(background, 0, 0);
		currentCollectionPage.displayCollectionCards(sb);
		currentPlayerPage.displayPlayerCards(sb);
		collectionNextPageBtn.draw(sb);
		playerNextPageBtn.draw(sb);
		sb.end();
	}

	@Override
	public void dispose() {
		// TODO Not sure what this does or if its important
	}

}
