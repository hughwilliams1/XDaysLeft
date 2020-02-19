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
	
	public CardGameManager (int emissionsValue) {
		
		// array of strings for representing the enemy deck
		// TODO In industry.json when deleting some unused card it crashes the game
		// TODO Same with landfill 2 and car factory 2 in industryBad.json
		// TODO Also protests2 in social.json
		
		enemyDeck_1 = new String[]{"Remove Tree", "Remove Tree", "Remove Tree", "Diesel Car",
				"Diesel Car", "Diesel Car", "Landfill", "Landfill", "Coal Power Plant", "Car Factory",
				"Online Posts", "Fake news"};

		emissionsBar = emissionsValue;
		
		collection = new CardCollection();
		
		this.user = new User("Friendly");
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
		ArrayList<Card> cardsToProcess = getAI().nextCard(aiBoard);
		if(cardsToProcess != null) {
		aiCard = cardsToProcess.get(0);
		System.out.println(aiCard.getTitle());
		cardsToProcess.remove(0);
		processCard(aiCard, cardsToProcess); //Need the chosen cards to destroy too
		changeEmissions(aiBoard.getTotalPoints());
		}
		switchPlayerTurn();
	}
	
	public void processCard(Card card, ArrayList<Card> chosenCards) {
		if(card instanceof Industry) {
			if (isPlayerTurn) {
					if (card.getStars() > 1) {
						card.handleInput();
						playerBoard.mergeCard(card, chosenCards);
						user.removeCard(card);
						//user.addCardToHand();
						for(int i=0; i<chosenCards.size(); i++) {
							user.removeCard(chosenCards.get(i));
						}
					} else {	
						card.handleInput();
						playerBoard.addToField(card);
						user.removeCard(card);
						//user.addCardToHand();
					}
				hasPlayed = true;
			} else {
				if (card.getStars() > 1) {
					card.switchTextures();
					card.handleInputEnemy();
					aiBoard.mergeCard(card, chosenCards);
					enemyAI.removeCard(card);
					//enemyAI.addCardToHand();
					for(int i=0; i<chosenCards.size(); i++) {
						enemyAI.removeCard(chosenCards.get(i));
					}
				} else {
					card.switchTextures();
					card.handleInputEnemy();
					aiBoard.addToField(card);
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
					((Social) card).doEffect(playerBoard, null);
				}
				hasPlayed = true;
			}else {
				
			}
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
