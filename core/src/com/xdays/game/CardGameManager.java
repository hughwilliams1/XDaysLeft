package com.xdays.game;

import java.util.ArrayList;
import com.xdays.game.cards.Card;
import com.xdays.game.cards.CardCollection;
import com.xdays.game.cards.Destroy;
import com.xdays.game.cards.Industry;
import com.xdays.game.cards.Social;

public class CardGameManager {

	private int emissionsBar;

	private User user;
	private AI enemyAI;

	private boolean isPlayerTurn;
	private boolean hasPlayed;

	private Board playerBoard;
	private Board aiBoard;

	private Card aiCard = null;
	private CardCollection collection;

	// array of strings for representing the enemy deck
	private String[] enemyDeck_1;

	public CardGameManager (int emissionsValue, User givenUser) {

		enemyDeck_1 = new String[]{"Remove Tree", "Remove Tree", "Remove Tree", "Diesel Car",
				"Diesel Car", "Diesel Car", "Landfill", "Landfill", "Remove Tree", "Remove Tree"};

		emissionsBar = emissionsValue;

		collection = new CardCollection();

		user = givenUser;
		this.enemyAI = new AI("Enemy", 1, createEnemyDeck());

		user.setHandFromDeck();
		enemyAI.setHandFromDeck();

		isPlayerTurn = true;
		hasPlayed = false;

		playerBoard = new Board();
		aiBoard = new Board();

		aiCard = null;
	}

	public void playCardGameRound(Card card, ArrayList<Card> chosenCards) {
		processCard(card, chosenCards);
		changeEmissions(playerBoard.getTotalPoints());
		switchPlayerTurn();
		ArrayList<Card> cardsToProcess = getAI().nextCard(aiBoard, playerBoard);
		if(cardsToProcess != null) {
			aiCard = cardsToProcess.remove(0);
			// prints out ai's played card
			System.out.println("AI Played: " + aiCard.getTitle());
			processCard(aiCard, cardsToProcess); //Need the chosen cards to destroy too
			// prints out ai's current hand
			System.out.println("AI's Current Hand: " + enemyAI.currentHandAsString());
			changeEmissions(aiBoard.getTotalPoints());
		}
		switchPlayerTurn();
	}

	public void processCard(Card card, ArrayList<Card> chosenCards) {
		if(card instanceof Industry) {
			if (isPlayerTurn) {
				if (card.getStars() > 1) {
					handleInput(card);
					playerBoard.mergeCard(card, chosenCards, true);
					user.removeCard(card);
					//user.addCardToHand();
					for(int i=0; i<chosenCards.size(); i++) {
						user.removeCard(chosenCards.get(i));
					}
				} else {	

					playerBoard.addToField(card, true);
					handleInput(card);
					user.removeCard(card);
					//user.addCardToHand();
				}
				hasPlayed = true;
			} else {
				if (card.getStars() > 1) {
					card.switchTextures();

					handleInputEnemy(card);
					aiBoard.mergeCard(card, chosenCards, false);
					
					enemyAI.removeCard(card);
					//enemyAI.addCardToHand();
					for(int i=0; i<chosenCards.size(); i++) {
						enemyAI.removeCard(chosenCards.get(i));
					}
				} else {
					card.switchTextures();
					
					handleInputEnemy(card);
					aiBoard.addToField(card, false);
					enemyAI.removeCard(card);
					//enemyAI.addCardToHand();
				}
			}
		}else {
			if (isPlayerTurn) {
				if(((Social) card).isSelectedCardNeeded()) {
					if(((Social) card).getSocialEffect() instanceof Destroy) {
						((Social) card).doEffect(aiBoard, chosenCards.get(0));
						user.removeCard(card);
					}else {
						((Social) card).doEffect(playerBoard, chosenCards.get(0));
					}
				}else {
					switch(card.getTitle()) {
					case "Online Posts":
					case "UN Law":
						((Social) card).doEffect(playerBoard, null);
						break;
					case "Strike":
					case "Petition":
					((Social) card).doEffect(aiBoard, null);
						break;
					}
				}
				hasPlayed = true;
			} else {
				if(((Social) card).isSelectedCardNeeded()) {
					if(((Social) card).getSocialEffect() instanceof Destroy) {
						((Social) card).doEffect(playerBoard, chosenCards.get(0));
						enemyAI.removeCard(card);
					}else {
						((Social) card).doEffect(aiBoard, chosenCards.get(0));
					}
				}else {
					switch(card.getTitle()) {
					case "Online Posts":
					case "Cover-up":
						((Social) card).doEffect(aiBoard, null);
						break;
					case "Bribe":
					case "Propaganda":
					((Social) card).doEffect(playerBoard, null);
						break;
					}
				}
			}
		}
	}
	
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
	
	private void handleInputEnemy(Card card) {
		if(!card.isPlayed()) {
			if(card.isHalfPlayed()) {
				card.position.y += 100;
			}
			card.updateBounds();
			card.setPlayed(true);
		}
	}

	public Board getPlayerBoard() {
		return playerBoard;
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
		Deck deck = new Deck(collection.getMultipleCards(enemyDeck_1));

		return deck;

	}

}
