package com.xdays.game.states;

import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
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
	private Button mapBackBtn;
	
    private Sound clickSound;
	
	protected static final int CARDS_PER_PAGE = 9;
	
	public CollectionState(GameStateManager gsm, CardCollection cardCollection, User player) {
		super(gsm);
		cam.setToOrtho(false, Game.WIDTH, Game.HEIGHT);
		// textures
		background = new Texture("collectionBackground.png");
		
		// Click sound
        clickSound = Gdx.audio.newSound(Gdx.files.internal("sounds/ClickSound.wav"));

		
		// Buttons
		// TODO give btns a texture
		collectionNextPageBtn = new Button(BTN_WIDTH, BTN_HEIGHT, ((Game.WIDTH / 6 ) * 3 ) - 180, 15, "NextBtn.PNG");
		playerNextPageBtn = new Button(BTN_WIDTH, BTN_HEIGHT, ((Game.WIDTH / 6 ) * 3 ) + 30, 15, "NextBtn.PNG");
		mapBackBtn = new Button(BTN_WIDTH, BTN_HEIGHT, ((Game.WIDTH / 6 ) * 5 ) + 60, 15, "BackBtn.PNG");
		
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
		// this checks if any btns are touched then performs the respective btns functionallity
		if(Gdx.input.justTouched() && collectionNextPageBtn.isPointerOver(Gdx.input.getX(), Gdx.input.getY())){
			clickSound.play();
			currentCollectionPage = collectionDisplayPages.get(collectionDisplayPages.indexOf((currentCollectionPage))+1);
        }
		
		if (Gdx.input.justTouched() && playerNextPageBtn.isPointerOver(Gdx.input.getX(), Gdx.input.getY())) {
			clickSound.play();
			currentPlayerPage = playerDisplayPages.get(playerDisplayPages.indexOf((currentPlayerPage))+1);
		}
		
		if (Gdx.input.justTouched() && mapBackBtn.isPointerOver(Gdx.input.getX(), Gdx.input.getY())) {
			clickSound.play();
			gsm.setState(StateEnum.MAP_STATE);
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
		if(!MenuState.mainMenuMusic.isPlaying()) {
			MenuState.mainMenuMusic.play();
		}
		
		sb.setProjectionMatrix(cam.combined);
		sb.begin();
		sb.draw(background, 0, 0);
		currentCollectionPage.displayCollectionCards(sb);
		currentPlayerPage.displayPlayerCards(sb);
		collectionNextPageBtn.draw(sb);
		playerNextPageBtn.draw(sb);
		mapBackBtn.draw(sb);
		sb.end();
	}

	@Override
	public void dispose() {
		System.out.println("DISDAPIHS{ODIGPOF@SDF");
		// TODO Not sure what this does or if its important
	}

	
	
	private class CollectionPage {

		private static final float CARD_WIDTH_OFFSET = 40f;
		private static final int CARD_ROWS = 3;
		
		
		private final float[] X_COORDINATES = { (((Game.WIDTH/ 2) / CARD_ROWS) * 1 ) - CARD_WIDTH_OFFSET,
				(((Game.WIDTH / 2) / CARD_ROWS) * 2) - CARD_WIDTH_OFFSET,
				(((Game.WIDTH /2) / CARD_ROWS) * 3) - CARD_WIDTH_OFFSET};
		
		private final float[] X_COORDINATES_PLAYER = { (((Game.WIDTH / 2 ) / CARD_ROWS) * 4) - CARD_WIDTH_OFFSET,
				(((Game.WIDTH / 2) / CARD_ROWS) * 5) - CARD_WIDTH_OFFSET,
				(((Game.WIDTH / 2) / CARD_ROWS) * 6) - CARD_WIDTH_OFFSET};
		
		private final float[] Y_COORINATES = {((Game.HEIGHT/ 3) * 3) - 7f,
				((Game.HEIGHT/ 3) * 2) + 20f,
				((Game.HEIGHT/ 3) * 1) + 46f};
		
		private float cardHeight;
		private float cardWidth;
		
		private ArrayList<Card> displayedCards;
		
		private int offset;
		private ArrayList<Card> collectionCards;
		private User player;

		public CollectionPage(ArrayList<Card> cards, int offset) {
			cardHeight = cards.get(0).getTexture().getHeight() / 3.4f;
			cardWidth = cards.get(0).getTexture().getWidth() / 3.4f;
			displayedCards = new ArrayList<Card>();
			this.collectionCards = cards;
			this.offset = offset;
		}
		
		public CollectionPage(User user, int offset) {
			displayedCards = new ArrayList<Card>();
			this.player = user;
			this.offset = offset;
		}

		public void displayCollectionCards(SpriteBatch sb) {
			displayedCards.clear();
			// total card per page divide by the card rows
			int currentCardNumber = 0;
			for (int y = 0; y < CollectionState.CARDS_PER_PAGE / CARD_ROWS; y++) {
				
				// CollectionState.CARDS_PER_PAGE/CARD_ROWS represents the total cards on page / the number of card row
				// so the card are displayed on separate rows
				for (int x = 0; x < CARD_ROWS; x++, currentCardNumber++) {
					// checks if the card index is out the array
					if (((offset * CollectionState.CARDS_PER_PAGE) + currentCardNumber) < collectionCards.size()) {
						// get the card to be rendered using current card and the pageoffset
						Card card = collectionCards.get((offset * CollectionState.CARDS_PER_PAGE) + currentCardNumber);
						
						// Gives the cards bound depending on it current position
						card.setPosition(X_COORDINATES[x] - cardWidth, Y_COORINATES[y] - cardHeight);
						
						sb.draw(card.getTexture(), X_COORDINATES[x] - cardWidth, Y_COORINATES[y] - cardHeight, cardWidth,
								cardHeight);
						displayedCards.add(card);
					}
				}
			}
		}
		
		public void displayPlayerCards(SpriteBatch sb) {
			displayedCards.clear();
			int currentCardNumber = 0;
			for (int y = 0; y < CollectionState.CARDS_PER_PAGE / CARD_ROWS; y++) {
				
				// CollectionState.CARDS_PER_PAGE/CARD_ROWS represents the total cards on page / the number of card row
				// so the card are displayed on separate rows
				for (int x = 0; x < CARD_ROWS; x++, currentCardNumber++) {
					// checks if the card index is out the array
					if (((offset * CollectionState.CARDS_PER_PAGE) + currentCardNumber) < player.getDeck().getDeckSize()) {
						// get the card to be rendered using current card and the pageoffset
						Card card = player.getDeck().getCard(((offset * CollectionState.CARDS_PER_PAGE) + currentCardNumber));
						
						// Gives the cards bound depending on it current position
						card.setPosition(X_COORDINATES_PLAYER[x] - cardWidth, Y_COORINATES[y] - cardHeight);
						
						sb.draw(card.getTexture(), X_COORDINATES_PLAYER[x] - cardWidth, Y_COORINATES[y] - cardHeight, cardWidth,
								cardHeight);
						displayedCards.add(card);
					}
				}
			}
		}
		
		public ArrayList<Card> getDisplayedCards() {
			return displayedCards;
		}
		
		
	}

}
