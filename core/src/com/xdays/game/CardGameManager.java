package com.xdays.game;

import java.util.ArrayList;
import java.util.Random;

import com.xdays.game.cards.Card;
import com.xdays.game.cards.CardCollection;
import com.xdays.game.cards.Destroy;
import com.xdays.game.cards.Industry;
import com.xdays.game.cards.Social;
import com.xdays.game.states.PlayState;

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
		
		System.out.print(enemyAI.currentHandAsString());

		isPlayerTurn = true;

		playerBoard = new Board();
		aiBoard = new Board();

		aiCard = null;
	}
	
	public void createEnemyAi(int level) {
		int difficulty = 0;
		if (level == 1) {
			enemyDeck = new String[]{ "Remove Tree", "Remove Tree", 
					"Fracking", "Propaganda", "Propaganda", "Propaganda", "Propaganda", "Propaganda"}; //"Landfill", "Landfill", "Remove Tree", "Remove Tree"
		
			difficulty = 1;
		} 
		else if (level == 2) {
			enemyDeck = new String[]{"Cover-up", "Remove Tree", "Remove Tree", "Remove Tree",
					"Remove Tree", "Remove Tree", "Diesel Car", "Online Posts", "Online Posts", "Online Posts", "Cover-up", "Cover-up"}; //"Landfill", "Landfill", "Remove Tree", "Remove Tree"

			difficulty = 3;
		} 
		else if (level == 3) {
			enemyDeck = new String[]{"Plant Tree", "Plant Tree", "Plant Tree", "Online Posts", "Online Posts", "Online Posts",
					"UN Law", "UN Law", "UN Law", "Solar Panel", "Solar Panel", "Solar Panel"};		
			difficulty = 5;
		}
		
		this.enemyAI = new AI("Enemy", difficulty, createEnemyDeck());
	} 

	public void playCardGameRound(Card card, ArrayList<Card> chosenCards) {
		if(isPlayerTurn) {
			if(card == null) {
				switchPlayerTurn();
			} else {
				processCard(card, chosenCards);
				//System.out.println("Player's Current Hand: " + user.currentHandAsString());
				switchPlayerTurn();
				
			}
		}
		if(!isPlayerTurn) {
			ArrayList<Card> cardsToProcess = getAI().nextCard(aiBoard, playerBoard);
			if(cardsToProcess != null) {
				aiCard = cardsToProcess.remove(0);
				// prints out ai's played card
				System.out.println("AI Played: " + aiCard.getTitle());
				aiCardPlayed = aiCard.getTitle();
				processCard(aiCard, cardsToProcess); //Need the chosen cards to destroy too
				// prints out ai's current hand
				//System.out.println("AI's Current Hand: " + enemyAI.currentHandAsString());
			}
			//System.out.println("AI EMISSIONS: " +aiBoard.getTotalPoints() +"Player emmisions: "+  playerBoard.getTotalPoints());
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

	public void processCard(Card card, ArrayList<Card> chosenCards) {
		System.out.println("Pocessing card: " + card.getTitle());
		if(card instanceof Industry) {
			if (isPlayerTurn) {
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
				if(((Social) card).isSelectedCardNeeded()) {
					if(((Social) card).getSocialEffect() instanceof Destroy) {
						((Social) card).doEffect(aiBoard, chosenCards.get(0));
						//handleInput(card);
						user.removeCard(card);
						user.addCardToHand();
						
					}else {
						((Social) card).doEffect(playerBoard, chosenCards.get(0));
						//handleInput(card);
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
						System.out.println("is a strike card");
						if(hasStarCard(aiBoard, 2)) {
							System.out.println("can play card");
							((Social) card).doEffect(aiBoard, null);
							//handleInput(card);
							user.removeCard(card);
							user.addCardToHand();
						} else {
							System.out.println("cannot be played");
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
						 // needs to check whether AI board contains 2/3 star card - happens below as well.
					}
				}
			} else {
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
	
	private boolean hasStarCard(Board board, int starValue) {
		for(Card card: board.getField()) {
			if(card.getStars() == starValue) {
				return true;
			}
		}
		return false;
	}
	
	private void handleInput(Card card) {
		if(!card.isPlayed()) {
			if(card instanceof Social) {
				System.out.println("TASTRETRTRTRT");
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

	private void handleInputEnemy(Card card) {
		if(!card.isPlayed()) {
			if(card.isHalfPlayed()) {
				card.position.y += 80;
			}
			card.updateBounds();
			card.setPlayed(true);
		}
	}

	public Board getPlayerBoard() {
		return playerBoard;
	}
	
	public String getAiCardPlayed() {
		return aiCardPlayed;
	}

	public Board getAIBoard() {
		return aiBoard;
	}

	public User getUser() {
		return user;
	}

	public AI getAI() {
		return enemyAI;
	}

	public void changeEmissions(int amount) {
		emissionsBar += amount;
	}

	public boolean isPlayersTurn() {
		return isPlayerTurn;
	}

	public int getEmissionsBar() {
		return emissionsBar;
	}

	public Card getAiCard() {
		return aiCard;
	}

	private void switchPlayerTurn() {
		isPlayerTurn = !isPlayerTurn;
	}

	// creates a deck by using the string array enemyDeck and the CardCollection class
	// for retrieving cards
	private Deck createEnemyDeck() {
		Deck deck = new Deck(collection.getMultipleCards(enemyDeck));

		return deck;

	}


}
