package com.xdays.game.states;

import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.xdays.game.Deck;
import com.xdays.game.Game;
import com.xdays.game.User;
import com.xdays.game.assets.Button;
import com.xdays.game.assets.CircularList;
import com.xdays.game.cards.Card;
import com.xdays.game.cards.CardCollection;
import com.xdays.game.cards.Industry;

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
	
	private Button playerTitle;
	private Button collectionTitle;
	
	private BitmapFont font;
	
    private Sound clickSound;
	
	protected static final int CARDS_PER_PAGE = 9;
	
	public CollectionState(GameStateManager gsm, CardCollection cardCollection, User player) {
		super(gsm);
		
		this.player = player;
		
		cam.setToOrtho(false, Game.WIDTH, Game.HEIGHT);
		// textures
		background = new Texture("collectionBackground.png");
		
		// Click sound
        clickSound = Gdx.audio.newSound(Gdx.files.internal("sounds/ClickSound.wav"));
		
		// Buttons
		// TODO give btns a texture
		collectionNextPageBtn = new Button(BTN_WIDTH, BTN_HEIGHT, ((Game.WIDTH / 6 ) * 3 ) - 180, 15, "NextBtn.PNG");
		collectionLastPageBtn = new Button(BTN_WIDTH, BTN_HEIGHT, ((Game.WIDTH / 40 )), 15, "BackBtn.PNG");
		
		playerNextPageBtn = new Button(BTN_WIDTH, BTN_HEIGHT, ((Game.WIDTH / 6 ) * 5 ) + 40, 15, "NextBtn.PNG");
		playerLastPageBtn = new Button(BTN_WIDTH, BTN_HEIGHT, ((Game.WIDTH / 6 ) * 3 ) + 30, 15, "BackBtn.PNG");
		
		exitBtn = new Button(30, 30, Game.WIDTH - 35 , (Game.HEIGHT/15) * 14 + 15, "BackBtn.PNG");
		
		playerTitle = new Button(BTN_WIDTH, BTN_HEIGHT, ((Game.WIDTH / 13 ) * 9 ), (Game.HEIGHT/15) * 14, "BackBtn.PNG");
		collectionTitle = new Button(BTN_WIDTH, BTN_HEIGHT, ((Game.WIDTH / 5 ) - 10 ), (Game.HEIGHT/15) * 14, "BackBtn.PNG");
		
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font/Staatliches-Regular.ttf"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 35;
		font = generator.generateFont(parameter);
		
		// Circular pages array
		collectionDisplayPages = new CircularList<CollectionPage>();
		playerDisplayPages = new CircularList<CollectionPage>();
			
		createCollectionPages(cardCollection);
		createPlayerPages(player);

		currentCollectionPage = collectionDisplayPages.get(0);
		currentPlayerPage = playerDisplayPages.get(0);
	}
	
	public void createCollectionPages(CardCollection cardCollection) {
		collectionDisplayPages.clear();
		int collectionPages = (int) Math.ceil((double) cardCollection.getGoodSize() / CARDS_PER_PAGE);
		
		for (int x = 0; x < collectionPages; x++) {
			CollectionPage page = new CollectionPage(player, cardCollection.getAllGoodCards(), x);
			collectionDisplayPages.add(page);
		}
	}
	
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
		// this checks if any btns are touched then performs the respective btns functionallity
		if(Gdx.input.justTouched() && collectionNextPageBtn.isPointerOver(Gdx.input.getX(), Gdx.input.getY())){
			clickSound.play();
			currentCollectionPage = collectionDisplayPages.get(collectionDisplayPages.indexOf((currentCollectionPage))+1);
        }
		if (Gdx.input.justTouched() && collectionLastPageBtn.isPointerOver(Gdx.input.getX(), Gdx.input.getY())){
			clickSound.play();
			currentCollectionPage = collectionDisplayPages.get(collectionDisplayPages.indexOf((currentCollectionPage))-1);
		}
		
		if (Gdx.input.justTouched() && playerNextPageBtn.isPointerOver(Gdx.input.getX(), Gdx.input.getY())) {
			clickSound.play();
			currentPlayerPage = playerDisplayPages.get(playerDisplayPages.indexOf((currentPlayerPage))+1);
		}
		if (Gdx.input.justTouched() && playerLastPageBtn.isPointerOver(Gdx.input.getX(), Gdx.input.getY())) {
			clickSound.play();
			currentPlayerPage = playerDisplayPages.get(playerDisplayPages.indexOf((currentPlayerPage))-1);
		}
		
		if (Gdx.input.justTouched() && exitBtn.isPointerOver(Gdx.input.getX(), Gdx.input.getY())) {
			clickSound.play();
			gsm.setState(StateEnum.MAP_STATE);
		}
		
		
		// Checks if any collection cards are touched by comparing the card bounds to a small rectangle created around the mouse
		for (Card card : currentCollectionPage.getDisplayedCards()) {
			if (Gdx.input.justTouched() && card.getBounds().overlaps(new Rectangle(Gdx.input.getX(), -(Gdx.input.getY() - 720), 0.01f, 0.01f))) {
				System.out.print("Collection Card: " + card.getTitle() + " was touched \n");
				player.getDeck();
				if (player.getDeck().instancesOfCardInDeck(card) < player.MAX_ONE_CARD && player.getDeck().getDeckSize() < Deck.MAX_DECK_SIZE) {
					player.getDeck().addCard(card);
					clickSound.play();
				} else {
					//TODO play some fail sound
				}
				createPlayerPages(player);
			}
		}
		
		// Checks if any player cards are touched by comparing the card bound to a small rectangle created around the mouse
		for (Card card : currentPlayerPage.getDisplayedCards()) {
			if (Gdx.input.justTouched() && card.getBounds().overlaps(new Rectangle(Gdx.input.getX(), -(Gdx.input.getY() - 720), 0.01f, 0.01f))) {
				System.out.print("Player Card: " + card.getTitle() + " was touched \n");
				
				if (player.getDeck().getDeckSize() != player.MAX_HAND_SIZE) {
					player.getDeck().removeCard(card);
					createPlayerPages(player);
					clickSound.play();
				} else {
					//TODO PLAY SOME FAIL SOUND
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
		if(!MenuState.mainMenuMusic.isPlaying()) {
			MenuState.mainMenuMusic.play();
		}
		
		sb.setProjectionMatrix(cam.combined);
		sb.begin();
		sb.draw(background, 0, 0);
		
		currentCollectionPage.displayCollectionCards(sb);
		currentPlayerPage.displayPlayerCards(sb);
		
		playerTitle.draw(sb);
		collectionTitle.draw(sb);
		
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
	
	public void renderPageNumber(SpriteBatch sb) {
		
		String collectionPageNumbers = currentCollectionPage.getPageNumber() + "/" + collectionDisplayPages.size();
		GlyphLayout layout = new GlyphLayout(font, collectionPageNumbers);
		float pageNumberWidth = layout.width;
		font.draw(sb, collectionPageNumbers, (((Game.WIDTH / 2) - (pageNumberWidth/2)) * 0.5f) - 7f, Game.HEIGHT/14);
		
		String playerPageNumbers = currentPlayerPage.getPageNumber() + "/" + playerDisplayPages.size();
		layout = new GlyphLayout(font, playerPageNumbers);
		pageNumberWidth = layout.width;
		
		font.draw(sb, playerPageNumbers, (((Game.WIDTH / 2) - (pageNumberWidth/2)) * 1.5f) + 9f, Game.HEIGHT/14);
	}

	@Override
	public void dispose() {
		
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

		private int pageNumber;
		
		public CollectionPage(User user, ArrayList<Card> cards, int offset) {
			this.player = user;
			this.pageNumber = offset + 1;
			cardHeight = cards.get(0).getTexture().getHeight() / 3.4f;
			cardWidth = cards.get(0).getTexture().getWidth() / 3.4f;
			displayedCards = new ArrayList<Card>();
			this.collectionCards = cards;
			this.offset = offset;
		}
		
		public CollectionPage(User user, int offset) {
			this.pageNumber = offset + 1;
			displayedCards = new ArrayList<Card>();
			this.player = user;
			this.offset = offset;
		}

		public void displayCollectionCards(SpriteBatch sb) {
			
			FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font/Staatliches-Regular.ttf"));
			FreeTypeFontParameter parameter = new FreeTypeFontParameter();
			parameter.size = 20;
			BitmapFont deckNumberFont = generator.generateFont(parameter);
			
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
						
						
						
						if(card instanceof Industry) {
							((Industry) card).draw(sb);
						} else {
							sb.draw(card.getTexture(), X_COORDINATES[x] - cardWidth, Y_COORINATES[y] - cardHeight, cardWidth,
								cardHeight);
						}
						
						displayedCards.add(card);
						
						// displays the amount in deck out of the collection limit
						String deckAmount = player.getDeck().instancesOfCardInDeck(card) + "/" + player.MAX_ONE_CARD;
						deckNumberFont.draw(sb, deckAmount, X_COORDINATES[x] - 118, Y_COORINATES[y] - cardHeight + 25);
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
						
						cardHeight = card.getTexture().getHeight() / 3.4f;
						cardWidth = card.getTexture().getWidth() / 3.4f;
						
						// Gives the cards bound depending on it current position
						card.setPosition(X_COORDINATES_PLAYER[x] - cardWidth, Y_COORINATES[y] - cardHeight);
						
						if(card instanceof Industry) {
							((Industry) card).draw(sb);
						} else {
							sb.draw(card.getTexture(), X_COORDINATES_PLAYER[x] - cardWidth, Y_COORINATES[y] - cardHeight, cardWidth,
							cardHeight);
						}
						
						displayedCards.add(card);
					}
				}
			}
		}
		
		public boolean isEmpty() {
			return displayedCards.isEmpty();
		}
		
		public int getPageNumber() {
			return pageNumber;
		}
		
		public ArrayList<Card> getDisplayedCards() {
			return displayedCards;
		}
		
		
	}

}
