package com.xdays.game.states;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.xdays.game.Deck;
import com.xdays.game.Game;
import com.xdays.game.User;
import com.xdays.game.assets.Button;
import com.xdays.game.assets.CircularList;
import com.xdays.game.cards.Card;
import com.xdays.game.cards.CardCollection;
import com.xdays.game.cards.Industry;
import com.xdays.game.cards.Social;

/**
 * CollectionState.java - Creates and renders a collection page for the player
 * to add a remove card to their deck
 *
 * @author Damian Hobeanu, Mark Ebel, Roberto Lovece, Ronil Goldenwalla, Hugh
 *         Williams
 * @version 1.0
 * @see {@link State}
 */

public class CollectionState extends State {

	private CircularList<CollectionPage> collectionDisplayPages;
	private CircularList<CollectionPage> playerDisplayPages;

	private CollectionPage currentCollectionPage;
	private CollectionPage currentPlayerPage;

	private User player;

	private static final int BTN_WIDTH = 150;
	private static final int BTN_HEIGHT = 45;

	private Texture background;

	private Button collectionNextPageBtn;
	private Button collectionLastPageBtn;

	private Button playerNextPageBtn;
	private Button playerLastPageBtn;

	private Button exitBtn;

	private BitmapFont font;

	private Sound clickSound;
	private String[] lockedCards;

	protected static final int CARDS_PER_PAGE = 9;

	// amount of each type of card allowed in the deck
	private static final int MAX_ONE_INDUSTRY_CARD = 3;
	private static final int MAX_ONE_SOCIAL_CARD = 2;
	private static final int MAX_ONE_STAR_CARD = 5;

	/**
	 * Creates the CollectionState object and loads all the textures used in the in
	 * class
	 *
	 * @param gsm            Gives a GameStateManager to render the textures
	 * @param cardCollection A collection of card for the player to add there deck
	 * @param player         A player whose deck you can customise
	 * 
	 * @return what it returns
	 */

	public CollectionState(GameStateManager gsm, CardCollection cardCollection, User player) {
		super(gsm);

		cam.setToOrtho(false, Game.WIDTH, Game.HEIGHT);
		// background texture
		background = (Texture) Game.assetManager.get("collectionBackground.PNG");
		;

		this.player = player;

		// click sound
		clickSound = Gdx.audio.newSound(Gdx.files.internal("sounds/ClickSound.wav"));

		// buttons
		collectionNextPageBtn = new Button(BTN_WIDTH, BTN_HEIGHT, ((Game.WIDTH / 6) * 3) - 180, 15, "NextBtn.PNG");
		collectionLastPageBtn = new Button(BTN_WIDTH, BTN_HEIGHT, ((Game.WIDTH / 40)), 15, "BackBtn.PNG");

		playerNextPageBtn = new Button(BTN_WIDTH, BTN_HEIGHT, ((Game.WIDTH / 6) * 5) + 40, 15, "NextBtn.PNG");
		playerLastPageBtn = new Button(BTN_WIDTH, BTN_HEIGHT, ((Game.WIDTH / 6) * 3) + 30, 15, "BackBtn.PNG");

		exitBtn = new Button(47, 41, (Game.WIDTH / 2 - 47 / 2), 15, "HomeSBtn.PNG");

		font = (BitmapFont) Game.assetManager.get("font/Staatliches-Regular35.ttf");

		setLockedCard(player.getCompletedLevel());

		// circular pages array
		collectionDisplayPages = new CircularList<CollectionPage>();
		playerDisplayPages = new CircularList<CollectionPage>();

		createCollectionPages(cardCollection);
		createPlayerPages(player);

		currentCollectionPage = collectionDisplayPages.get(0);
		currentPlayerPage = playerDisplayPages.get(0);
	}

	/**
	 * Sets the {@link #lockedCards} array depending on the level so the only
	 * unlocked card can be added to the deck
	 *
	 * @param level used to determine what cards in the collection can be added to
	 *              the deck
	 */

	public void setLockedCard(int level) {
		if (level == 0) {
			String[] test = new String[] { "Plant Tree", "Windmill", "Nuclear Plant", "Hydroelectric Energy", "UN Law",
					"Petition" };
			lockedCards = test;
		} else if (level == 1) {
			lockedCards = new String[] { "UN Law", "Petition" };
		} else if (level == 2) {
			lockedCards = new String[] {};
		}
	}

	/**
	 * Creates the pages for the total amount of cards from the cards in collection
	 * to be rendered
	 *
	 * @param cardCollection Used to render the collection cards that can be added
	 *                       in the deck
	 */

	public void createCollectionPages(CardCollection cardCollection) {
		collectionDisplayPages.clear();
		int collectionPages = (int) Math.ceil((double) cardCollection.getGoodSize() / CARDS_PER_PAGE);

		for (int x = 0; x < collectionPages; x++) {
			CollectionPage page = new CollectionPage(player, cardCollection.getAllGoodCards(), x);
			collectionDisplayPages.add(page);
		}
	}

	/**
	 * Creates the pages of the current players deck to be displayed
	 *
	 * @param player Used to render the player cards
	 */

	public void createPlayerPages(User player) {
		playerDisplayPages.clear();
		int playerPages = (int) Math.ceil((double) player.getDeck().getDeckSize() / CARDS_PER_PAGE);

		for (int x = 0; x < playerPages; x++) {
			CollectionPage page = new CollectionPage(player, x);
			playerDisplayPages.add(page);
		}
	}

	@Override
	protected void handleInput() {
		// this checks if any btns are touched then performs the respective btns
		// functionallity
		if (Gdx.input.justTouched() && collectionNextPageBtn.isPointerOver(Gdx.input.getX(), Gdx.input.getY())) {
			clickSound.play();
			currentCollectionPage = collectionDisplayPages
					.get(collectionDisplayPages.indexOf((currentCollectionPage)) + 1);
		}
		if (Gdx.input.justTouched() && collectionLastPageBtn.isPointerOver(Gdx.input.getX(), Gdx.input.getY())) {
			clickSound.play();
			currentCollectionPage = collectionDisplayPages
					.get(collectionDisplayPages.indexOf((currentCollectionPage)) - 1);
		}

		if (Gdx.input.justTouched() && playerNextPageBtn.isPointerOver(Gdx.input.getX(), Gdx.input.getY())) {
			clickSound.play();
			currentPlayerPage = playerDisplayPages.get(playerDisplayPages.indexOf((currentPlayerPage)) + 1);
		}
		if (Gdx.input.justTouched() && playerLastPageBtn.isPointerOver(Gdx.input.getX(), Gdx.input.getY())) {
			clickSound.play();
			currentPlayerPage = playerDisplayPages.get(playerDisplayPages.indexOf((currentPlayerPage)) - 1);
		}

		if (Gdx.input.justTouched() && exitBtn.isPointerOver(Gdx.input.getX(), Gdx.input.getY())) {
			clickSound.play();
			gsm.setState(StateEnum.MAP_STATE);
		}

		// Checks if any collection cards are touched by comparing the card bounds to a
		// small rectangle created around the mouse
		for (Card card : currentCollectionPage.getDisplayedCards()) {
			if (Gdx.input.justTouched() && card.getBounds()
					.overlaps(new Rectangle(Gdx.input.getX(), -(Gdx.input.getY() - 720), 0.01f, 0.01f))) {
				player.getDeck();
				if (card instanceof Industry && player.getDeck().instancesOfCardInDeck(card) < MAX_ONE_INDUSTRY_CARD
						&& player.getDeck().getDeckSize() < Deck.MAX_DECK_SIZE && card.getStars() > 1) {
					player.getDeck().addCard(card);
					player.updateCurrentDeck();
					clickSound.play();
				} else if (card instanceof Industry && player.getDeck().instancesOfCardInDeck(card) < MAX_ONE_STAR_CARD
						&& player.getDeck().getDeckSize() < Deck.MAX_DECK_SIZE && card.getStars() == 1) {
					player.getDeck().addCard(card);
					player.updateCurrentDeck();
					clickSound.play();
				} else if (card instanceof Social && player.getDeck().instancesOfCardInDeck(card) < MAX_ONE_SOCIAL_CARD
						&& player.getDeck().getDeckSize() < Deck.MAX_DECK_SIZE) {
					player.getDeck().addCard(card);
					player.updateCurrentDeck();
					clickSound.play();
				} else {

				}
				createPlayerPages(player);
			}
		}

		// Checks if any player cards are touched by comparing the card bound to a small
		// rectangle created around the mouse
		for (Card card : currentPlayerPage.getDisplayedCards()) {
			if (Gdx.input.justTouched() && card.getBounds()
					.overlaps(new Rectangle(Gdx.input.getX(), -(Gdx.input.getY() - 720), 0.01f, 0.01f))) {

				if (player.getDeck().getDeckSize() != player.MAX_HAND_SIZE) {
					player.getDeck().removeCard(card);
					player.updateCurrentDeck();
					createPlayerPages(player);
					clickSound.play();
				} else {
					// TODO PLAY SOME FAIL SOUND
				}

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
		if (!MenuState.mainMenuMusic.isPlaying()) {
			MenuState.mainMenuMusic.play();
		}

		setLockedCard(player.getCompletedLevel());

		sb.setProjectionMatrix(cam.combined);
		sb.begin();
		sb.draw(background, 0, 0);

		currentCollectionPage.displayCollectionCards(sb);
		currentPlayerPage.displayPlayerCards(sb);

		collectionNextPageBtn.draw(sb);
		collectionLastPageBtn.draw(sb);

		playerNextPageBtn.draw(sb);
		playerLastPageBtn.draw(sb);

		exitBtn.draw(sb);
		if (currentPlayerPage.isEmpty()) {
			currentPlayerPage = playerDisplayPages.get(0);
		}
		renderPageNumber(sb);
		sb.end();
	}

	/**
	 * Render the current page display of cards for the collection and player deck
	 *
	 * @param sb Used for libgdx to draw objects
	 */
	public void renderPageNumber(SpriteBatch sb) {

		String collectionPageNumbers = "Cards  --  " + currentCollectionPage.getPageNumber() + "/"
				+ collectionDisplayPages.size();
		GlyphLayout layout = new GlyphLayout(font, collectionPageNumbers);
		float pageNumberWidth = layout.width;
		font.draw(sb, collectionPageNumbers, (Game.WIDTH / 4) - (pageNumberWidth / 2), Game.HEIGHT / 14);

		String playerPageNumbers = "Deck  --  " + currentPlayerPage.getPageNumber() + "/" + playerDisplayPages.size();
		layout = new GlyphLayout(font, playerPageNumbers);
		pageNumberWidth = layout.width;

		font.draw(sb, playerPageNumbers, ((Game.WIDTH / 4) * 3) - (pageNumberWidth / 2), Game.HEIGHT / 14);
	}

	@Override
	public void dispose() {

	}

	private class CollectionPage {

		/**
		 * CollectionPage.java - A page of up to 9 cards displayed in a grid like 3 * 3
		 * structure 
		 *
		 * @author Damian Hobeanu, Mark Ebel, Roberto Lovece, Ronil Goldenwalla, Hugh
		 *         Williams
		 * @version 1.0
		 */

		private static final float CARD_WIDTH_OFFSET = 40f;
		private static final int CARD_ROWS = 3;

		private final float[] X_COORDINATES = { (((Game.WIDTH / 2) / CARD_ROWS) * 1) - CARD_WIDTH_OFFSET,
				(((Game.WIDTH / 2) / CARD_ROWS) * 2) - CARD_WIDTH_OFFSET,
				(((Game.WIDTH / 2) / CARD_ROWS) * 3) - CARD_WIDTH_OFFSET };

		private final float[] X_COORDINATES_PLAYER = { (((Game.WIDTH / 2) / CARD_ROWS) * 4) - CARD_WIDTH_OFFSET,
				(((Game.WIDTH / 2) / CARD_ROWS) * 5) - CARD_WIDTH_OFFSET,
				(((Game.WIDTH / 2) / CARD_ROWS) * 6) - CARD_WIDTH_OFFSET };

		private final float[] Y_COORINATES = { ((Game.HEIGHT / 3) * 3) - 7f, ((Game.HEIGHT / 3) * 2) + 20f,
				((Game.HEIGHT / 3) * 1) + 46f };

		private float cardHeight;
		private float cardWidth;

		private ArrayList<Card> displayedCards;

		private int offset;
		private ArrayList<Card> collectionCards;
		private User player;

		private int pageNumber;
		private BitmapFont deckNumberFont;
		
		 /**
		 * Used for creating a CollectionPage for the collection cards
		 * 
		 * @param user    Used for adding cards from the collection to the player deck 
		 * @param cards    Arraylist form of all the collection cards   
		 * @param offset    Offset used to calculate which collection page is being created
		 */

		public CollectionPage(User user, ArrayList<Card> cards, int offset) {
			this.player = user;
			this.pageNumber = offset + 1;
			cardHeight = Card.CARD_HEIGHT / 3.4f;
			cardWidth = Card.CARD_WIDTH / 3.4f;
			displayedCards = new ArrayList<Card>();
			this.collectionCards = cards;
			this.offset = offset;
			
			deckNumberFont = (BitmapFont) Game.assetManager.get("font/Staatliches-Regular20.ttf");
		}

		 /**
		 * Used for creating a CollectionPage for the player deck cards
		 *
		 * @param user    Used for removing cards from the players deck and rendering the deck   
		 * @param offset    Offset used to calculate which collection page is being created   
		 */ 
		
		public CollectionPage(User user, int offset) {
			this.pageNumber = offset + 1;
			displayedCards = new ArrayList<Card>();
			this.player = user;
			this.offset = offset;
		}

		 /**
		 * Displays the collection cards for a page
		 *
		 * @param sb   Used for libgdx to draw objects     
		 */ 
		
		public void displayCollectionCards(SpriteBatch sb) {

			displayedCards.clear();
			// total card per page divide by the card rows
			int currentCardNumber = 0;

			Boolean displayCard = new Boolean(true);

			for (int y = 0; y < CollectionState.CARDS_PER_PAGE / CARD_ROWS; y++) {

				// CollectionState.CARDS_PER_PAGE/CARD_ROWS represents the total cards on page /
				// the number of card row
				// so the card are displayed on separate rows
				for (int x = 0; x < CARD_ROWS; x++, currentCardNumber++) {
					// checks if the card index is out the array
					if ((((offset * CollectionState.CARDS_PER_PAGE)) + currentCardNumber) < collectionCards.size()) {
						// get the card to be rendered using current card and the pageoffset
						Card card = collectionCards.get((offset * CollectionState.CARDS_PER_PAGE) + currentCardNumber);

						for (String lockedCard : lockedCards) {
							if (lockedCard == null) {
								lockedCard = "";
							}

							if (lockedCard.equals(card.getTitle())) {
								displayCard = false;
							}
						}

						if (displayCard) {
							// Gives the cards bound depending on it current position
							card.setPosition(X_COORDINATES[x] - cardWidth, Y_COORINATES[y] - cardHeight);

							if (card instanceof Industry) {
								((Industry) card).draw(sb);
							} else {
								sb.draw((Texture) Game.assetManager.get(card.getTitle() + ".PNG"),
										X_COORDINATES[x] - cardWidth, Y_COORINATES[y] - cardHeight, cardWidth,
										cardHeight);
							}

							displayedCards.add(card);

							// displays the amount in deck out of the collection limit
							String deckAmount = "";
							if (card instanceof Industry && card.getStars() > 1) {
								deckAmount = player.getDeck().instancesOfCardInDeck(card) + "/" + MAX_ONE_INDUSTRY_CARD;
							} else if (card instanceof Industry && card.getStars() == 1) {
								deckAmount = player.getDeck().instancesOfCardInDeck(card) + "/" + MAX_ONE_STAR_CARD;
							} else if (card instanceof Social) {
								deckAmount = player.getDeck().instancesOfCardInDeck(card) + "/" + MAX_ONE_SOCIAL_CARD;
							}

							deckNumberFont.draw(sb, deckAmount, X_COORDINATES[x] - 118,
									Y_COORINATES[y] - cardHeight + 25);
						} else {
							sb.draw((Texture) Game.assetManager.get("back question" + ".PNG"),
									X_COORDINATES[x] - cardWidth, Y_COORINATES[y] - cardHeight, cardWidth, cardHeight);
						}

						displayCard = true;
					}
				}
			}
		}

		 /**
		 * Displays the player deck cards for a page
		 *
		 * @param sb   Used for libgdx to draw objects     
		 */ 
		
		public void displayPlayerCards(SpriteBatch sb) {
			displayedCards.clear();
			int currentCardNumber = 0;
			for (int y = 0; y < CollectionState.CARDS_PER_PAGE / CARD_ROWS; y++) {

				// CollectionState.CARDS_PER_PAGE/CARD_ROWS represents the total cards on page /
				// the number of card row
				// so the card are displayed on separate rows
				for (int x = 0; x < CARD_ROWS; x++, currentCardNumber++) {
					// checks if the card index is out the array
					if (((offset * CollectionState.CARDS_PER_PAGE) + currentCardNumber) < player.getDeck()
							.getDeckSize()) {
						// get the card to be rendered using current card and the pageoffset
						Card card = player.getDeck()
								.getCard(((offset * CollectionState.CARDS_PER_PAGE) + currentCardNumber));

						cardHeight = card.CARD_HEIGHT / 3.4f;
						cardWidth = card.CARD_WIDTH / 3.4f;

						// Gives the cards bound depending on it current position
						card.setPosition(X_COORDINATES_PLAYER[x] - cardWidth, Y_COORINATES[y] - cardHeight);

						if (card instanceof Industry) {
							((Industry) card).draw(sb);
						} else {
							sb.draw((Texture) Game.assetManager.get(card.getTitle() + ".PNG"),
									X_COORDINATES_PLAYER[x] - cardWidth, Y_COORINATES[y] - cardHeight, cardWidth,
									cardHeight);
						}

						displayedCards.add(card);
					}
				}
			}
		}
		
		 /**
		 * @return {@link Boolean} whether a page has any cards to display
		 */ 

		public boolean isEmpty() {
			return displayedCards.isEmpty();
		}

		 /**
		 * @return current page number of a page
		 */ 
		
		public int getPageNumber() {
			return pageNumber;
		}
		
		/**
		 * @return an array of all the cards in a page that would be displayed
		 */ 

		public ArrayList<Card> getDisplayedCards() {
			return displayedCards;
		}

	}

}
