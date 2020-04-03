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
import com.xdays.game.assets.Button;
import com.xdays.game.cards.Card;
import com.xdays.game.cards.Destroy;
import com.xdays.game.cards.EditEmission;
import com.xdays.game.cards.Industry;
import com.xdays.game.cards.Social;
import com.xdays.game.cutscenes.CutsceneState;

/**  
 * PlayState.java - this class is used to render and handle input for the the state where the card game is being played.
 *
 * @author  Damian Hobeanu, Mark Ebel, Roberto Lovece, Ronil Goldenwalla, Hugh Williams
 * @version 1.0 
 * @see State.java
 */ 
public class PlayState extends State {

	private boolean multipleCardsNeeded = false;
	private boolean selectedCardsNeeded = false;
	private CardGameManager manager;
	private Card lastCardPlayed;
	private ArrayList<Card> selectedCards;
	private String messageToPrint;
	private Texture background;
	
	private Button pauseBtn;
	private Button skipBtn;
	private Button homeBtn;
	
	private static final int BTN_WIDTH = 47;
	private static final int BTN_HEIGHT = 41;
	
	private Music battleMusic;
	private Sound selectCard;
	private Sound clickSound;
	
	private BitmapFont font;
	
	private int level;
	private boolean firstTurn;
	private String enemy;
	
	/**
	 * Constructor.
	 *
	 * @param gsm	GameStateManager used to store the various states of the game and switch between them.
	 * @param level	Used to specify the difficulty level for enemy.
	 * 
	 */
	public PlayState(GameStateManager gsm, int level) {
		super(gsm);
		
		pauseBtn = new Button(BTN_WIDTH, BTN_HEIGHT, 15, 15, "PauseSBtn.PNG");
		skipBtn = new Button(BTN_WIDTH, BTN_HEIGHT, 15, 30 + BTN_HEIGHT, "SkipSBtn.PNG");
		homeBtn = new Button(BTN_WIDTH, BTN_HEIGHT, 15, 45 + (BTN_HEIGHT * 2), "HomeSBtn.PNG");
		
		battleMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/BattleMusic.wav"));
		battleMusic.setLooping(true);
		battleMusic.setVolume(.2f);        
		
		font = (BitmapFont) Game.assetManager.get("font/Staatliches-Regular25.ttf");
		
		clickSound = Gdx.audio.newSound(Gdx.files.internal("sounds/clickSound.wav"));
		
		selectCard = Gdx.audio.newSound(Gdx.files.internal("sounds/SelectCard.wav"));
		
		// CardReader cardReader = new CardReader();
		background = new Texture("background2.png");
		selectedCards = new ArrayList<Card>();
		messageToPrint = "";
		firstTurn = true;
		
		switch(level) {
		case(4):
			enemy = "Merkel";
			break;
		case(5):
			enemy = "Putin";
			break;
		case(6):
			enemy = "Trump";
			break;
		}
		
		cam.setToOrtho(false, Game.WIDTH, Game.HEIGHT);
		
		this.level = level;

		manager = new CardGameManager(50, gsm.getUser(), level - 3, gsm.getCollection());
	}

	 /**
	 * Used to handle the users input and give the selected cards to the card game manager.
	 */
	@Override
	protected void handleInput() {
		
        if(Gdx.input.justTouched() && pauseBtn.isPointerOver(Gdx.input.getX(), Gdx.input.getY())){
        	clickSound.play();
        	gsm.setState(StateEnum.PAUSE_STATE);
        }
		
		if(Gdx.input.justTouched() && skipBtn.isPointerOver(Gdx.input.getX(), Gdx.input.getY())){
			manager.playCardGameRound(null, null);
        	clickSound.play();
        }
		
		if(Gdx.input.justTouched() && homeBtn.isPointerOver(Gdx.input.getX(), Gdx.input.getY())){
			clickSound.play();
			dispose();
			Gdx.gl.glClearColor(157f / 255f, 46f / 255f, 46f / 255f, 1);
			gsm.setState(StateEnum.MAP_STATE);
			gsm.removeState(StateEnum.PLAY_STATE);
        }
		
		if (hasPlayerWon()) {
			messageToPrint = "You have won this battle";
			dispose();
			Gdx.gl.glClearColor(157f / 255f, 46f / 255f, 46f / 255f, 1);
			manager.getUser().setCompletedLevel(level - 3);
			// Player win return to edited map
			gsm.setStateAsNew(new CutsceneState(gsm, level), StateEnum.CUTSCENE_STATE);
			gsm.removeState(StateEnum.PLAY_STATE);
		}
		if (hasAIWon()) {
			System.out.println(getEmissionBar());
			dispose();
			Gdx.gl.glClearColor(157f / 255f, 46f / 255f, 46f / 255f, 1);
			
			// AI win return to original map
			gsm.setState(StateEnum.MAP_STATE);
			gsm.removeState(StateEnum.PLAY_STATE);
		}
		
        if (AreDecksEmpty() && manager.getUser().handSize() == 0 && manager.getAI().handSize() == 0) {
			messageToPrint = "Sudden Death";
			System.out.println("Sudden Death");
			if (getEmissionBar() >= 50) {
				messageToPrint = "You have lost this battle";
				dispose();
				// AI win return to original map
				gsm.setState(StateEnum.MAP_STATE);
				gsm.removeState(StateEnum.PLAY_STATE);
			} else {
				messageToPrint = "You have won this battle";
				dispose();
				manager.getUser().setCompletedLevel(level - 3);
				// Player win return to edited map
				gsm.setStateAsNew(new CutsceneState(gsm, level), StateEnum.CUTSCENE_STATE);
				gsm.removeState(StateEnum.PLAY_STATE);
				
			}
			Gdx.gl.glClearColor(157f / 255f, 46f / 255f, 46f / 255f, 1);
        }
		
		if (Gdx.input.justTouched() && manager.isPlayersTurn()) {
			
			Rectangle bounds = new Rectangle(Gdx.input.getX(), -(Gdx.input.getY() - 720), 1, 1);
            
			if (multipleCardsNeeded) {
				// goes through the board
				for (int i = 0; i < getNumCards(); i++) {
					Card selectedCard = getPlayerCard(i);
					if (checkCardOverlaps(selectedCard, (bounds)) && multipleCardsNeeded) {
						if (compareStar() && compareCards(bounds, selectedCard)) {
							selectedCards.add(selectedCard);
							messageToPrint = "Selected card for merge: " + selectedCard.getTitle();
							if (calculateTotalStars(selectedCards) >= lastCardPlayed.getStars()) {
								manager.playCardGameRound(lastCardPlayed, selectedCards);
								firstTurn = false;
								selectCardSound();
								selectedCards.clear();
								multipleCardsNeeded = false;
							}
						}
					} else if (checkCardOverlaps(lastCardPlayed, bounds) && multipleCardsNeeded) {
						lastCardPlayed.stopHalfPlay();
						multipleCardsNeeded = false;
						messageToPrint = "Unselected card for merge: " + lastCardPlayed.getTitle();
					}
				}
			} else if (selectedCardsNeeded) { // maybe deselection of protest/ boycott below?
				if (((Social) lastCardPlayed).getSocialEffect() instanceof Destroy) {
					
					for (int i = 0; i < getNumAiCards(); i++) {
						if (getAICard(i).getBounds().overlaps(bounds)) {
							Card selectedCard = getAICard(i);
							if (isCardValid(bounds, selectedCard)) {
								selectedCards.add(selectedCard);
								messageToPrint = "Selected card for apply social card to: " + selectedCard.getTitle();
								manager.playCardGameRound(lastCardPlayed, selectedCards);
								selectedCards.clear();
								selectedCardsNeeded = false;
								firstTurn= false;
								render(Game.batch);
							}
						}
						else if (checkCardOverlaps(lastCardPlayed, bounds) && ((Social) lastCardPlayed).getSocialEffect() instanceof Destroy) {
							lastCardPlayed.stopHalfPlay();
							selectedCardsNeeded = false;
							messageToPrint = "Unselected card for merge: " + lastCardPlayed.getTitle();
						}
					}
				}
			} else {
				User player = manager.getUser();
				for (int i = 0; i < player.handSize(); i++) {
					//System.out.println(i + " : " + player.getCardFromHand(i).getTitle() + " : " + player.getCardFromHand(i).getBounds());
					Card card = player.getCardFromHand(i);
					if (card.getBounds().overlaps(bounds)) {
						Card selectedCard = card;
						if (selectedCard instanceof Social) {
							selectCardSound();
							
							if (((!manager.getAIBoard().getField().isEmpty() && (!getPlayerBoard().getField().isEmpty())) || ((Social) selectedCard).getSocialEffect() instanceof Destroy)) {
								if (((Social) selectedCard).isSelectedCardNeeded()) {
									messageToPrint = "Select cards to apply the social card to";
									if (getNumAiCards() != 0) {
										selectedCardsNeeded = true;
										lastCardPlayed = selectedCard;
										lastCardPlayed.halfPlayed();
									}
									
								} else {
									//manager.processCard(selectedCard, null);
									manager.playCardGameRound(selectedCard, null); 
									firstTurn = false;
									break;
								}
								
							} else if (((Social) selectedCard).getSocialEffect() instanceof EditEmission && ((!manager.getAIBoard().getField().isEmpty() || (!getPlayerBoard().getField().isEmpty())))) {
								//manager.processCard(selectedCard, null);
								manager.playCardGameRound(selectedCard, null); 
								firstTurn = false;
								break;
							}
							
							else {
								messageToPrint = "No cards have been placed on the board yet."; 
							}
						} else {
							if (selectedCard.getStars() > 1 && !selectedCard.isPlayed()) {
								if (checkValidStars(selectedCard)) {
									messageToPrint = "Not enough cards to play that card.";
								} else {
									selectCardSound();
									messageToPrint = "Select cards to merge";
									multipleCardsNeeded = true;
									lastCardPlayed = selectedCard;
									lastCardPlayed.halfPlayed();
								}
								// checks if an industry card has space on the board to be played
							} else if (!selectedCard.isPlayed() && (manager.getPlayerBoard().getBoardSize() < manager.getPlayerBoard().MAX_BOARD_SIZE)) {
								selectCardSound();
								messageToPrint = "User played card: " + selectedCard.getTitle();
								manager.playCardGameRound(selectedCard, null);
								firstTurn = false;
								render(Game.batch);
							}
						}
					}
				}
			}
		}
	}
	
	 /**
	 * Calculated the total stars for a given array list of cards.
	 * 
	 * @param selected an array list of cards
	 * 
	 * @return the total stars of the given cards
	 */
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
	
	 /**
	 * Method used to check for input every frame
	 * 
	 * @param dt  
	 * 
	 */
	@Override
	public void update(float dt) {
		handleInput();
	}
	
	 /**
	 * Method used to render every frame.
	 * 
	 * @param sb sprite batch is given all the textured and coordinates necessary to render the elements  
	 * 
	 */
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
		pauseBtn.draw(sb);
		skipBtn.draw(sb);
		homeBtn.draw(sb);
		
		if(!firstTurn) {
			if(manager.getAiCardPlayed() != null) {
				font.draw(sb, enemy + " Played: " + manager.getAiCardPlayed(), 10, 450);
			} else {
				font.draw(sb, enemy + " has skipped a turn!", 10, 450);
			}
		}
	
		// gets player to be used
		User player = manager.getUser();

		// Renders the players cards from the player board
		// Turn this into a render board method
		Board playerBoard = manager.getPlayerBoard();
		int playerNumBoardCards = playerBoard.getBoardSize();
		//manager.getPlayerBoard().updateCards();
		
		for (int i = 0; i < playerNumBoardCards; i++) {
			
			Card currentCard = playerBoard.getCard(i);

			if(currentCard instanceof Industry) {
				((Industry) currentCard).draw(sb);
			}else {
				sb.draw((Texture) Game.assetManager.get(currentCard.getTitle()+".PNG"), getXValue(currentCard), getYValue(currentCard), getCardWidth(currentCard),
						getCardHeight(currentCard));
			}
		}
		
		// renders player hand
		for (int i = 0; i < player.handSize(); i++) {
			Card card = player.getCardFromHand(i);
			// remove && !card.haveBoundsBeenSet() the hand will move cards when you play
			try {
				if (card.isHalfPlayed() != true) {
					card.setPosition(0, 0);
					card.setPosition(((((Game.WIDTH) * ((i + 1) / ((float) player.handSize() + 1)))) - card.getBoundsWidth() / 2),
							0);
				}

				if(card instanceof Industry) {
					((Industry) card).draw(sb);
				}else {
					sb.draw((Texture) Game.assetManager.get(card.getTitle()+".PNG"), getXValue(card), getYValue(card), getCardWidth(card),
							getCardHeight(card));
				}
			}catch(Exception e) {}
		}

		// gets the ai from manager to be changed
		AI ai = manager.getAI();

		for (int i = 0; i < ai.handSize(); i++) {
			Card aiCard = ai.getCardFromHand(i);

			if (aiCard.isHalfPlayed() != true) {
				aiCard.setPosition(0, 0);
				aiCard.setPosition(
						((((Game.WIDTH) * ((i + 1) / ((float) ai.handSize() + 1)))) - aiCard.getBoundsWidth() / 2), 650+25);

			}
			sb.draw((Texture) Game.assetManager.get("back.PNG"), getXValue(aiCard), getYValue(aiCard), getCardWidth(aiCard),
					getCardHeight(aiCard));
		}

		// render the ais cards on the board
		for (int i = 0; i < getNumAiCards(); i++) {
			Card aiCard = getAICard(i);
			if(aiCard instanceof Industry) {
				((Industry) aiCard).AiDraw(sb);
			}else {
				sb.draw((Texture) Game.assetManager.get(aiCard.getTitle()+".PNG"), getXValue(aiCard), getYValue(aiCard)+10, getCardWidth(aiCard),
						getCardHeight(aiCard));
			}
			/*sb.draw(getAICard(i).getTexture(), getXValue(getAICard(i)), getYValue(getAICard(i)) + 10,
					getCardWidth(getAICard(i)), getCardHeight(getAICard(i)));*/
		}

		sb.end();	
		drawEmissionsBar();
	    
		sb.begin();
		sb.end();
	}
	
	 /**
	 * Renders the emissions bar
	 */
	private void drawEmissionsBar() {
		ShapeRenderer shapeRenderer = new ShapeRenderer();
	    shapeRenderer.begin(ShapeType.Filled);
	    shapeRenderer.setColor(Color.RED);
	    shapeRenderer.rect(cam.position.x-100, cam.position.y+65, getEmissionBar()*2, 30);
	    shapeRenderer.end();
	}
	
	 /**
	 * Return the current value of the emissions from card game manager
	 * 
	 * @return the int value of the emissions
	 */
	private int getEmissionBar() {
		return manager.getEmissionsBar();
	}

	 /**
	 * Check to see if the player has won the play state by checking if the emissions have reached the desired value
	 * 
	 * @return true if the player has won or false if he hasn't 
	 */
	private Boolean hasPlayerWon() {
		if (getEmissionBar() <= 0) {
			return true;
		}
		return false;
	}
	
	 /**
	 * Check to see if the AI has won the play state by checking if the emissions have reached the desired value
	 * 
	 * @return true if the AI has won or false if it hasn't 
	 */
	private Boolean hasAIWon() {
		if (getEmissionBar() >= 100) {
			return true;
		}
		return false;
	}

	 /**
	 * Check if both the player and the AI decks are empty
	 * 
	 * @return true if both decks are empty false if they are not
	 */
	private Boolean AreDecksEmpty() {
		if ((manager.getUser().getDeck().getDeckSize() == 0) || (manager.getAI().getDeck().getDeckSize() == 0)) {
			return true;
		}

		return false;

	}
	
	 /**
	 * Check if a given card can be selected by verifying that the given bounds overlap the card bounds and that the given card hasn't already been selected or played
	 * 
	 * @param bounds given bounds to see of they overlap with the bounds of the given card
	 * 
	 * @param selectedCard the card given to check if it meets necessary conditions for it to be valid to be played
	 * 
	 * @return true if the card is valid, false if it isn't
	 */
	private boolean isCardValid(Rectangle bounds, Card selectedCard) {
		return selectedCard.getBounds().overlaps(bounds) && !selectedCard.equals(lastCardPlayed)
				&& selectedCard.isPlayed() && !selectedCards.contains(selectedCard);
	}
	
	 /**
	 * Check if the player has enough total stars on his board to be able to play the selected card
	 * 
	 * @param selectedCard card given to compare its stars to the total stars on the player board
	 * 
	 * @return true if the card has more stars than the total stars of the cards on the players board or else return false
	 */
	private boolean checkValidStars(Card selectedCard) {
		return getPlayerBoard().getTotalStars(getPlayerBoard().getField()) < selectedCard.getStars();
	}
	
	 /**
	 * Get specific card from the player board
	 * 
	 * @param i integer specifying the position of the card in the player board 
	 * 
	 * @return card from player board
	 */
	private Card getPlayerCard(int i) {
		return getCurrentCard(i);
	}
	
	 /**
	 * Get specific card from the AI board
	 * 
	 * @param i integer specifying the position of the card in the AI board 
	 * 
	 * @return card from AI board
	 */
	private Card getAICard(int i) {
		return manager.getAIBoard().getCard(i);
	}
	
	 /**
	 * Check if the bounds of a given card overlap with the given bounds
	 * 
	 * @param card to check if bounds overlap with given bounds
	 * 
	 * @param bounds to check if bounds overlap with given card
	 * 
	 * @return true if the bounds overlap, false if they don't
	 */
	private Boolean checkCardOverlaps(Card card, Rectangle bounds) {
		return card.getBounds().overlaps(bounds);
	}
	
	 /**
	 * Get the number of cards placed on the player's board
	 * 
	 * @return int of the total number of cards on the player's board
	 */
	private int getNumCards() {
		return getPlayerBoard().getBoardSize();
	}
	
	 /**
	 * Get the number of cards placed on the AI board
	 * 
	 * @return int of the total number of cards on the AI board
	 */
	private int getNumAiCards() {
		return manager.getAIBoard().getBoardSize();
	}
	
	 /**
	 * Compare the total stars of the selected cards array with the stars of the last card played
	 * 
	 * @return boolean true if stars of the last card played are higher than the total of the selected cards
	 */
	private boolean compareStar() {
		return calculateTotalStars(selectedCards) < lastCardPlayed.getStars();
	}

	 /**
	 * To check if a card has already been played
	 * 
	 * @param bounds to check if bounds overlap with given card
	 * 
	 * @param selectedCard check if the card is the last card played, if it's already been played and if it is being selected
	 * 
	 * @return boolean true if the card has been clicked and hasn't already been played
	 */
	private boolean compareCards(Rectangle bounds, Card selectedCard) {
		return checkCardOverlaps(selectedCard, bounds) && !selectedCard.equals(lastCardPlayed)
				&& selectedCard.isPlayed() && !selectedCards.contains(selectedCard);
	}
	
	 /**
	 * Get the player board
	 */
	private Board getPlayerBoard() {
		return manager.getPlayerBoard();
	}
	
	 /**
	 * Get a specific card from the player's board
	 * 
	 * @param i int specifying the position of the card in the player board
	 * 
	 * @return Card card from the player's board
	 */
	private Card getCurrentCard(int i) {
		return getPlayerBoard().getCard(i);
	}
	
	 /**
	 * Get the height of a given card
	 * 
	 * @param card given card to get width of
	 * 
	 * @return float width of given card
	 */
	private float getCardWidth(Card card) {
		return card.CARD_WIDTH / 3.4f;
	}
	
	 /**
	 * Get the height of a given card
	 * 
	 * @param card given card to get height of
	 * 
	 * @return float height of given card
	 */
	private float getCardHeight(Card card) {
		return card.CARD_HEIGHT / 3.4f;
	}
	
	 /**
	 * Get the X coordinates of a given card
	 * 
	 * @param card given card to get X coordinates of
	 * 
	 * @return float X coordinates of given card
	 */
	private float getXValue(Card card) {
		return card.getBounds().getX();
	}
	
	 /**
	 * Get the Y coordinates of a given card
	 * 
	 * @param card given card to get Y coordinates of
	 * 
	 * @return float Y coordinates of given card
	 */
	private float getYValue(Card card) {
		return card.getBounds().getY();
	}

	private void setBitmap(BitmapFont bitmap) {
		bitmap.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		bitmap.getData().setScale(1);
		bitmap.setColor(Color.ORANGE);

	}
	
	 /**
	 * Play the sound associated with a card being clicked
	 * 
	 */
	private void selectCardSound() {
		selectCard.play();
	}
	
	 /**
	 * Method used to dispose of state
	 * 
	 */
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		selectCard.dispose();
		battleMusic.dispose();
	}

}
