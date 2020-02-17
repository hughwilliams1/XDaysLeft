package com.xdays.game;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import com.xdays.game.cards.Card;
import com.xdays.game.cards.CardReader;
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
	
	public CardGameManager (int emissionsValue, User user, AI enemyAI) {
		
		emissionsBar = emissionsValue;
		
		this.user = user;
		this.enemyAI = enemyAI;
		
		CardReader cardReader = new CardReader();
		
		//user.setHandFromDeck();
		//enemyAI.setHandFromDeck();
		
		// currently the deck class isn't used
		// you get the cards to display from card reader aka deck is pointless rn
		
		this.user.setHand(cardReader.getIndustryCardsArray());
		// card game manager should probably determine the enemies deck
		this.enemyAI.setHand(cardReader.getIndustryCardsBadArray());	
		
		// these cards are removed or hand will be too big from card reader?
		this.user.getHand().remove(0);
		this.user.getHand().remove(1);
		
		this.user.getHand().add(cardReader.getSocialCards().get("Protests2"));
		this.user.getHand().add(cardReader.getSocialCards().get("Protests"));
		
		System.out.println(this.user.getHand().size());
		System.out.println(this.enemyAI.getHand().size());
		
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
	
}
