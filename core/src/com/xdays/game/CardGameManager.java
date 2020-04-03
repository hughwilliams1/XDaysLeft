package com.xdays.game;

import java.util.ArrayList;
import java.util.Random;

import com.xdays.game.cards.Card;
import com.xdays.game.cards.CardCollection;
import com.xdays.game.cards.Destroy;
import com.xdays.game.cards.Industry;
import com.xdays.game.cards.Social;

/**  
 * CardGameManager.java - Manages each card game, i.e. plays the players cards and then makes the ai to play.  
 *
 * @author  Damian Hobeanu, Mark Ebel, Roberto Lovece, Ronil Goldenwalla, Hugh Williams
 * @version 1.0 
 */ 

public class CardGameManager {

	private int emissionsBar;

	private User user;
	private AI enemyAI;

	private boolean isPlayerTurn;

	private Board playerBoard;
	private Board aiBoard;

	private Card aiCard = null;
	private CardCollection collection;

	// array of strings for representing the enemy deck
	private String[] enemyDeck;
	
	private String aiCardPlayed;
	
	/**
	 * Creates the the decks for both player and ai as well as the board for each side
	 * 
	 * @param emissionsValue	start value of the emissions bar
	 * @param givenUser			the player playing the game
	 * @param level				current level of the game
	 * @param collection		collection of all the cards in the game
	 */

	public CardGameManager (int emissionsValue, User givenUser, int level, CardCollection collection) {
		emissionsBar = emissionsValue;
		aiCardPlayed = null;
		this.collection = collection;

		user = givenUser;

		createEnemyAi(level);

		//user.resetHand();
		user.setHandFromDeck();
		//enemyAI.resetHand();
		enemyAI.setHandFromDeck();

		isPlayerTurn = true;

		playerBoard = new Board();
		aiBoard = new Board();

		aiCard = null;
	}
	
	/**
	 * Gives the ai a different deck depending on the level
	 * 
	 * @param level	current level used to determine which deck to select
	 */
	
	public void createEnemyAi(int level) {
		int difficulty = 0;
		// ai strong cards are remove tree, gas boiler, landfill, battery farm, Freight Ships, all 4 star are the same power 
		if (level == 1) {
			enemyDeck = new String[]{ "Diesel Car", "Diesel Car", "Diesel Car", "Diesel Car",
					"Household Waste", "Household Waste", "Household Waste", "Household Waste",
					"Fracking", "Taxi",
					"Industrial Manufacturing",
					"Coal Power Plant",
					"Fake news", "Fake news", "Online Posts"}; 
		
			difficulty = 1;
		} 
		else if (level == 2) {
			enemyDeck = new String[]{"Remove Tree", "Remove Tree", "Remove Tree", "Remove Tree",
					"Diesel Car", "Diesel Car", "Household Waste", "Household Waste",
					"Battery Farm", "Fracking",
					"Freight Ships",
					"Coal Power Plant",
					"Fake news", "Fake news", "Online Posts", "Propaganda"};

			difficulty = 3;
		} 
		else if (level == 3) {
			enemyDeck = new String[]{"Remove Tree", "Remove Tree", "Remove Tree", "Remove Tree", "Remove Tree",
					"Gas Boiler", "Gas Boiler", "Gas Boiler", "Gas Boiler", "Gas Boiler",
					"Battery Farm", "Battery Farm", "Fracking",
					"Freight Ships", "Industrial Manufacturing",
					"Coal Power Plant",
					"Fake news", "Fake news", "Cover-up", "Cover-up", "Bribe"};		
			difficulty = 5;
		}
		
		this.enemyAI = new AI("Enemy", difficulty, createEnemyDeck());
	} 

	/**
	 * plays a card game round for either the player of the ai depending on {@link #isPlayerTurn}
	 * 
	 * @param card			the card you want to play
	 * @param chosenCards	the card you want merge/select to destroy with you card can be null if playing a card
	 * 						that doesn't target anything
	 */
	
	public void playCardGameRound(Card card, ArrayList<Card> chosenCards) {
		if(isPlayerTurn) {
			if(card == null) {
				switchPlayerTurn();
			} else {
				processCard(card, chosenCards);
				switchPlayerTurn();
				
			}
		}
		if(!isPlayerTurn) {
			ArrayList<Card> cardsToProcess = getAI().nextCard(aiBoard, playerBoard);
			if(cardsToProcess != null) {
				aiCard = cardsToProcess.remove(0);
				// prints out ai's played card
				aiCardPlayed = aiCard.getTitle();
				processCard(aiCard, cardsToProcess); //Need the chosen cards to destroy too
				
			}
			switchPlayerTurn();
		}
		
		if(card==null) {
			changeEmissions(-playerBoard.getTotalPoints());
			changeEmissions(aiBoard.getTotalPoints());		
		}else if(!card.getTitle().contentEquals("Strike")) {
			if(!card.getTitle().contentEquals("Petition")) {
				changeEmissions(-playerBoard.getTotalPoints());
				changeEmissions(aiBoard.getTotalPoints());		
			}
		}
		
	}

	/**
	 * Processes any type of give card depending on card characteristics
	 * 
	 * @param card			the card you want to play
	 * @param chosenCards	the card you want merge/select to destroy with you card can be null if playing a card
	 * 						that doesn't target anything
	 */
	
	public void processCard(Card card, ArrayList<Card> chosenCards) {
		if(card instanceof Industry) {
			if (isPlayerTurn) {
				// if the card is greater than 1 star merge it to the chosen cards else add it to the field (player)
				if (card.getStars() > 1) {
					handleInput(card);
					playerBoard.mergeCard(card, chosenCards, true);
					user.removeCard(card);
					user.addCardToHand();
					for(int i=0; i<chosenCards.size(); i++) {
						user.removeCard(chosenCards.get(i));
					}
				} else {	

					playerBoard.addToField(card, true);
					handleInput(card);
					user.removeCard(card);
					user.addCardToHand();
				}
			} else {
				// if the card is greater than 1 star merge it to the chosen cards else add it to the field (ai)
				if (card.getStars() > 1) {

					handleInputEnemy(card);
					aiBoard.mergeCard(card, chosenCards, false);

					enemyAI.removeCard(card);
					enemyAI.addCardToHand();
					for(int i=0; i<chosenCards.size(); i++) {
						enemyAI.removeCard(chosenCards.get(i));
					}
				} else {

					handleInputEnemy(card);
					aiBoard.addToField(card, false);
					enemyAI.removeCard(card);
					enemyAI.addCardToHand();
				}
			}
		}else {
			if (isPlayerTurn) {
				// handles social card for the player
				if(((Social) card).isSelectedCardNeeded()) {
					if(((Social) card).getSocialEffect() instanceof Destroy) {
						((Social) card).doEffect(aiBoard, chosenCards.get(0));
						user.removeCard(card);
						user.addCardToHand();
						
					}else {
						((Social) card).doEffect(playerBoard, chosenCards.get(0));
						user.removeCard(card);
						user.addCardToHand();
					}
				}else {
					switch(card.getTitle()) {
					case "Online Posts":
					case "UN Law":
						Random r = new Random();

						if (playerBoard.getBoardSize() == 0 && aiBoard.getBoardSize() > 0) {
							((Social) card).doEffect(aiBoard, null, false);
							user.removeCard(card);
							user.addCardToHand();
						} else if (aiBoard.getBoardSize() == 0 && playerBoard.getBoardSize() > 0) {
							((Social) card).doEffect(playerBoard, null, true);
							user.removeCard(card);
							user.addCardToHand();
						} else {
							if(r.nextBoolean()) {
								((Social) card).doEffect(playerBoard, null, true);
								user.removeCard(card);
								user.addCardToHand();
							}
							else {
								((Social) card).doEffect(aiBoard, null, false);
								user.removeCard(card);
								user.addCardToHand();
							}
						}	
						
						break;
					case "Strike":
						if(hasStarCard(aiBoard, 2)) {
							((Social) card).doEffect(aiBoard, null);
							user.removeCard(card);
							user.addCardToHand();
						} else {
							switchPlayerTurn();
						}
						break;
					case "Petition":
						if(hasStarCard(aiBoard, 3)) {
							((Social) card).doEffect(aiBoard, null);
							handleInput(card);
							user.removeCard(card);
							user.addCardToHand();
						} else {
							switchPlayerTurn();
						}
						break;
					}
				}
			} else {
				// handles social card for the ai
				if(((Social) card).isSelectedCardNeeded()) {
					if(((Social) card).getSocialEffect() instanceof Destroy) {
						((Social) card).doEffect(playerBoard, chosenCards.get(0));
						enemyAI.removeCard(card);
						enemyAI.addCardToHand();
					}else {
						((Social) card).doEffect(aiBoard, chosenCards.get(0));
						enemyAI.removeCard(card);
						enemyAI.addCardToHand();
					}
				} else {
					switch(card.getTitle()) {
					case "Online Posts":
					case "Cover-up":
						Random r = new Random();
						if(r.nextBoolean()) {
							((Social) card).doEffect(playerBoard, null, false);
							enemyAI.removeCard(card);	
							enemyAI.addCardToHand();
						}else {
							((Social) card).doEffect(aiBoard, null, true);
							enemyAI.removeCard(card);
							enemyAI.addCardToHand();
						}
						break;
					case "Bribe":
						if(hasStarCard(playerBoard, 3)) {
							((Social) card).doEffect(playerBoard, null);
							enemyAI.removeCard(card);
							enemyAI.addCardToHand();
						} else {
							switchPlayerTurn();
						}
						break;
					case "Propaganda":
						if(hasStarCard(playerBoard, 2)) {
							((Social) card).doEffect(playerBoard, null);
							enemyAI.removeCard(card);
							enemyAI.addCardToHand();
						} else {
							switchPlayerTurn();
						}
						break;
					}
				}
			}
		}
	}

	/**
	 * @param board			current board we want to check
	 * @param starValue		star value of the card we are searching
	 * @return	whether or not the board contains a card of a certain star
	 */
	
	private boolean hasStarCard(Board board, int starValue) {
		for(Card card: board.getField()) {
			if(card.getStars() == starValue) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * updates the position for card that need merging and social cards that require a target
	 * for player
	 * 
	 * @param card	card that need to be repositioned
	 */
	
	private void handleInput(Card card) {
		if(!card.isPlayed()) {
			if(card instanceof Social) {
				card.position.y += 210;
			}else {
				if(card.isHalfPlayed()) {
					card.position.y += 100;
				}
				card.updateBounds();
				card.setPlayed(true);
			}
		}
	}
	
	/**
	 * handles merging card for the ai
	 * 
	 * @param card	card that requires merging
	 */

	private void handleInputEnemy(Card card) {
		if(!card.isPlayed()) {
			if(card.isHalfPlayed()) {
				card.position.y += 80;
			}
			card.updateBounds();
			card.setPlayed(true);
		}
	}

	/**
	 * @return	current player board
	 */
	
	public Board getPlayerBoard() {
		return playerBoard;
	}
	
	/**
	 * @return	get the card the ai played
	 */
	
	public String getAiCardPlayed() {
		return aiCardPlayed;
	}

	/**
	 * @return	get the ai board
	 */
	
	public Board getAIBoard() {
		return aiBoard;
	}
	
	/**
	 * @return	get the current user
	 */

	public User getUser() {
		return user;
	}
	
	/**
	 * @return	get the current ai
	 */

	public AI getAI() {
		return enemyAI;
	}

	/**
	 * increase the emission by an amount
	 * 
	 * @param 	amount you want the emissions to change
	 */
	
	public void changeEmissions(int amount) {
		emissionsBar += amount;
	}

	/**
	 * @return	true if it the players turn 
	 */
	
	public boolean isPlayersTurn() {
		return isPlayerTurn;
	}

	/**
	 * @return	the current emissions bar
	 */
	
	public int getEmissionsBar() {
		return emissionsBar;
	}

	/**
	 * @return	get the ai card
	 */
	
	public Card getAiCard() {
		return aiCard;
	}
	
	/**
	 * switch to the opposite turn that it is
	 */

	private void switchPlayerTurn() {
		isPlayerTurn = !isPlayerTurn;
	}

	/**
	 * creates the enemy deck using {@link #collection}
	 * 
	 * @return	the enemy deck
	 */
	
	private Deck createEnemyDeck() {
		Deck deck = new Deck(collection.getMultipleCards(enemyDeck));

		return deck;

	}


}
