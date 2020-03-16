package com.xdays.game.states;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.xdays.game.AI;
import com.xdays.game.Board;
import com.xdays.game.CardGameManager;
import com.xdays.game.Game;
import com.xdays.game.User;
import com.xdays.game.cards.Card;
import com.xdays.game.cards.Destroy;
import com.xdays.game.cards.Social;

public class PlayState extends State {

	private boolean multipleCardsNeeded = false;
	private boolean selectedCardsNeeded = false;
	private CardGameManager manager;
	private Card lastCardPlayed;
	private ArrayList<Card> selectedCards;
	private String messageToPrint;
	private Texture background;
	
	private Music battleMusic;
	private Sound selectCard;

	public PlayState(GameStateManager gsm) {
		super(gsm);
		
		battleMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/BattleMusic.wav"));
		battleMusic.setLooping(true);
		battleMusic.setVolume(.6f);        
		
		selectCard = Gdx.audio.newSound(Gdx.files.internal("sounds/SelectCard.wav"));
		
		// CardReader cardReader = new CardReader();
		background = new Texture("background.png");
		selectedCards = new ArrayList<Card>();
		messageToPrint = "";
		cam.setToOrtho(false, Game.WIDTH, Game.HEIGHT);

		// Deck goodDeck = new Deck(cardReader.getInudstryAndSocialCards());
		// Deck badDeck = new Deck(cardReader.getInudstryAndSocialCardsBad());

		manager = new CardGameManager(99, gsm.getUser());
	}

	@Override
	protected void handleInput() {
		
		if (hasPlayerWon()) {
			System.out.println("Player Win");
			messageToPrint = "You have won this battle";
			// Player win return to edited map
			gsm.setState(StateEnum.MAP_STATE);
			gsm.removeState(StateEnum.PLAY_STATE);
		}
		if (hasAIWon()) {
			System.out.println("Enemy Win");
			System.out.println(getEmissionBar());
			// //AI win return to original map
			messageToPrint = "You have lost this battle";
			gsm.setState(StateEnum.MAP_STATE);
			gsm.removeState(StateEnum.PLAY_STATE);
		}
        if (AreDecksEmpty() && manager.getUser().handSize() == 0 && manager.getAI().handSize() == 0) {
			messageToPrint = "Sudden Death";
			System.out.println("Sudden Death");
			if (getEmissionBar() >= 50) {
				System.out.println("Enemy Win");
				messageToPrint = "You have lost this battle";
				// AI win return to original map
				gsm.setState(StateEnum.MAP_STATE);
				gsm.removeState(StateEnum.PLAY_STATE);
			} else {
				System.out.println("Player Win");
				messageToPrint = "You have won this battle";
				// Player win return to edited map
				gsm.setState(StateEnum.MAP_STATE);
				gsm.removeState(StateEnum.PLAY_STATE);

			}
        }
		
		
		if (Gdx.input.justTouched() && manager.isPlayersTurn()) {
			// System.out.println("Play State touched");
			Rectangle bounds = new Rectangle(Gdx.input.getX(), -(Gdx.input.getY() - 720), 1, 1);
            
			if (multipleCardsNeeded) {
				// goes through the board
				for (int i = 0; i < getNumCards(); i++) {
					Card selectedCard = getPlayerCard(i);
					if (checkCardOverlaps(selectedCard, (bounds)) && multipleCardsNeeded) {
						if (compareStar() && compareCards(bounds, selectedCard)) {
							selectedCards.add(selectedCard);
							messageToPrint = "Selected card for merge: " + selectedCard.getTitle();
							System.out.println("Selected card for merge: " + selectedCard.getTitle());
							if (calculateTotalStars(selectedCards) >= lastCardPlayed.getStars()) {
								manager.playCardGameRound(lastCardPlayed, selectedCards);
								selectCardSound();
								selectedCards.clear();
								multipleCardsNeeded = false;
							}
						}
					} else if (checkCardOverlaps(lastCardPlayed, bounds) && multipleCardsNeeded) {
						lastCardPlayed.stopHalfPlay();
						multipleCardsNeeded = false;
						System.out.println("Unselected card for merge: " + lastCardPlayed.getTitle());
						messageToPrint = "Unselected card for merge: " + lastCardPlayed.getTitle();
					}
				}
			} else if (selectedCardsNeeded) {
				if (((Social) lastCardPlayed).getSocialEffect() instanceof Destroy) {
					for (int i = 0; i < getNumAiCards(); i++) {
						if (getAICard(i).getBounds().overlaps(bounds)) {
							Card selectedCard = getAICard(i);
							if (isCardValid(bounds, selectedCard)) {
								selectedCards.add(selectedCard);
								messageToPrint = "Selected card for apply social card to: " + selectedCard.getTitle();
								System.out
										.println("Selected card for apply social card to: " + selectedCard.getTitle());
								manager.playCardGameRound(lastCardPlayed, selectedCards);
								selectedCards.clear();
								selectedCardsNeeded = false;
								render(Game.batch);
							}
						}
					}
				}
			} else {
				User player = manager.getUser();
				for (int i = 0; i < player.handSize(); i++) {
					Card card = player.getCardFromHand(i);
					if (card.getBounds().overlaps(bounds)) {
						System.out.println(card.getTitle());
						Card selectedCard = card;
						if (selectedCard instanceof Social) {
							selectCardSound();
							if (!manager.getAIBoard().getField().isEmpty() && !getPlayerBoard().getField().isEmpty()) {
								if (((Social) selectedCard).isSelectedCardNeeded()) {
									messageToPrint = "Select cards to apply the social card to";
									System.out.println("Select cards to apply the social card to");
									selectedCardsNeeded = true;
									lastCardPlayed = selectedCard;
									lastCardPlayed.halfPlayed();
								} else {
									((Social) selectedCard).doEffect(getPlayerBoard(), null);
									messageToPrint = "Social card applied.";
									System.out.println("Select card applied to: "
											+ ((Social) selectedCard).getSocialEffect().getChosenCard());

								}
							} else {
								messageToPrint = "No cards have been placed on the board yet.";
								System.out.println("No cards have been placed on the board yet.");
							}
						} else {
							if (selectedCard.getStars() > 1 && !selectedCard.isPlayed()) {
								if (checkValidStars(selectedCard)) {
									messageToPrint = "Not enough cards to play that card.";
									System.out.println("Not enough cards to play that card.");
								} else {
									selectCardSound();
									messageToPrint = "Select cards to merge";
									System.out.println("Select cards to merge");
									multipleCardsNeeded = true;
									lastCardPlayed = selectedCard;
									lastCardPlayed.halfPlayed();
								}
							} else if (!selectedCard.isPlayed()) {
								selectCardSound();
								messageToPrint = "User played card: " + selectedCard.getTitle();
								System.out.println("User played card: " + selectedCard.getTitle());
								manager.playCardGameRound(selectedCard, null);
								render(Game.batch);
							}
						}
					}
				}
			}
		}
	}

	private int calculateTotalStars(ArrayList<Card> selected) {
		int a = 0;
		if (selected.isEmpty()) {
			return 0;
		} else {
			for (int i = 0; i < selected.size(); i++) {
				a += selected.get(i).getStars();
			}
			return a;
		}
	}

	@Override
	public void update(float dt) {
		handleInput();
	}

	@Override
	public void render(SpriteBatch sb) {
		if(!battleMusic.isPlaying()) {
			battleMusic.play();
		}
		
		
		BitmapFont console = new BitmapFont();
		BitmapFont emissions = new BitmapFont();
		setBitmap(console);
		setBitmap(emissions);

		Gdx.gl.glClearColor(135 / 255f, 206 / 255f, 235 / 255f, 1);

		sb.setProjectionMatrix(cam.combined);
		sb.begin();
		sb.draw(background, 0, 0);

		// gets player to be used
		User player = manager.getUser();

		// Renders the players cards from the player board
		// Turn this into a render board method
		Board playerBoard = manager.getPlayerBoard();
		int playerNumBoardCards = playerBoard.getBoardSize();

		for (int i = 0; i < playerNumBoardCards; i++) {

			Card currentCard = playerBoard.getCard(i);

			sb.draw(currentCard.getTexture(), getXValue(currentCard), getYValue(currentCard), getCardWidth(currentCard),
					getCardHeight(currentCard));
		}
		
		// renders player hand
		for (int i = 0; i < player.handSize(); i++) {
			Card card = player.getCardFromHand(i);

			// remove && !card.haveBoundsBeenSet() the hand will move cards when you play
			if (card.isHalfPlayed() != true) {
				card.setPosition(0, 0);
				card.setPosition(((((Game.WIDTH) * ((i + 1) / ((float) player.handSize() + 1)))) - card.getWidth() / 2),
						0);
			}

			sb.draw(card.getTexture(), getXValue(card), getYValue(card), getCardWidth(card), getCardHeight(card));
		}

		// gets the ai from manager to be changed
		AI ai = manager.getAI();

		for (int i = 0; i < ai.handSize(); i++) {
			Card aiCard = ai.getCardFromHand(i);

			if (aiCard.isHalfPlayed() != true) {
				aiCard.setPosition(0, 0);
				aiCard.setPosition(
						((((Game.WIDTH) * ((i + 1) / ((float) player.handSize() + 1)))) - aiCard.getWidth() / 2), 650);
			}

			sb.draw(aiCard.getBackTexture(), getXValue(aiCard), getYValue(aiCard), getCardWidth(aiCard),
					getCardHeight(aiCard));
		}

		// render the ais cards on the board
		for (int i = 0; i < getNumAiCards(); i++) {
			sb.draw(getAICard(i).getTexture(), getXValue(getAICard(i)), getYValue(getAICard(i)),
					getCardWidth(getAICard(i)), getCardHeight(getAICard(i)));
		}

		console.draw(sb, messageToPrint, 600, 405);
		emissions.draw(sb, "Emissions: " + Integer.toString(getEmissionBar()), 600, 380);

		sb.end();
		
		drawEmissionsBar();
	}
	
	private void drawEmissionsBar() {
		ShapeRenderer shapeRenderer = new ShapeRenderer();
	    shapeRenderer.begin(ShapeType.Filled);
	    shapeRenderer.setColor(Color.RED);
	    shapeRenderer.rect(400, 400, getEmissionBar()*4, 50);
	    shapeRenderer.end();
	}

	private int getEmissionBar() {
		return manager.getEmissionsBar();
	}

	private Boolean hasPlayerWon() {
		if (getEmissionBar() <= 0) {
			return true;
		}
		return false;
	}

	private Boolean hasAIWon() {
		if (getEmissionBar() >= 100) {
			return true;
		}
		return false;
	}

	// TODO Also need to check if hand is empty
	private Boolean AreDecksEmpty() {
		if ((manager.getUser().getDeck().getDeckSize() == 0) || (manager.getAI().getDeck().getDeckSize() == 0)) {
			return true;
		}

		return false;

	}

	private boolean isCardValid(Rectangle bounds, Card selectedCard) {
		return selectedCard.getBounds().overlaps(bounds) && !selectedCard.equals(lastCardPlayed)
				&& selectedCard.isPlayed() && !selectedCards.contains(selectedCard);
	}

	private boolean checkValidStars(Card selectedCard) {
		return getPlayerBoard().getTotalStars(getPlayerBoard().getField()) < selectedCard.getStars();
	}

	private Card getPlayerCard(int i) {
		return getCurrentCard(i);
	}

	private Card getAICard(int i) {
		return manager.getAIBoard().getCard(i);
	}

	private Boolean checkCardOverlaps(Card card, Rectangle bounds) {
		return card.getBounds().overlaps(bounds);
	}

	private int getNumCards() {
		return getPlayerBoard().getBoardSize();
	}

	private int getNumAiCards() {
		return manager.getAIBoard().getBoardSize();
	}

	private boolean compareStar() {
		return calculateTotalStars(selectedCards) < lastCardPlayed.getStars();
	}

	private boolean compareCards(Rectangle bounds, Card selectedCard) {
		return checkCardOverlaps(selectedCard, bounds) && !selectedCard.equals(lastCardPlayed)
				&& selectedCard.isPlayed() && !selectedCards.contains(selectedCard);
	}

	private Board getPlayerBoard() {
		return manager.getPlayerBoard();
	}

	private Card getCurrentCard(int i) {
		return getPlayerBoard().getCard(i);
	}

	private float getCardWidth(Card card) {
		return card.getTexture().getWidth() / 3.4f;
	}

	private float getCardHeight(Card card) {
		return card.getTexture().getHeight() / 3.4f;
	}

	private float getXValue(Card card) {
		return card.getBounds().getX();
	}

	private float getYValue(Card card) {
		return card.getBounds().getY();
	}

	private void setBitmap(BitmapFont bitmap) {
		bitmap.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		bitmap.getData().setScale(1);
		bitmap.setColor(Color.ORANGE);

	}
	
	private void selectCardSound() {
		selectCard.play();
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		selectCard.dispose();
		battleMusic.dispose();
	}

}
