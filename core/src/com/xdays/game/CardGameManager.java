package com.xdays.game;

import java.util.ArrayList;
import java.util.Random;

import com.xdays.game.cards.Card;
import com.xdays.game.cards.CardReader;
import com.xdays.game.cards.Industry;

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
		CardReader reader = new CardReader();
		
		emissionsBar = emissionsValue;
		
		this.user = user;
		this.enemyAI = enemyAI;
		
		this.user.setHand(reader.getIndustryCardsArray());
		this.enemyAI.setHand(reader.getIndustryCardsBadArray());
		
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
	
		aiCard = cardsToProcess.get(0);
		System.out.println(aiCard.getTitle());
		cardsToProcess.remove(0);
		processCard(aiCard, cardsToProcess);
		
		switchPlayerTurn();
	}
	
//	public void playCardGame () {
//		
//		boolean finished = false;
//		
//		while(emissionsBar != 100 && emissionsBar != 0 && !finished) {
//			
//			if (hasPlayed) {
//				changeEmissions(playerBoard.getTotalPoints());
//				hasPlayed = false;
//				switchPlayerTurn();
//			}
//			
//			if(!isPlayerTurn) {
//				
//				ArrayList<Card> cardsToProcess = getAI().nextCard(aiBoard);
//				
//				aiCard = cardsToProcess.get(0);
//				System.out.println(aiCard.getTitle());
//				cardsToProcess.remove(0);
//				processCard(aiCard, cardsToProcess); //Need the chosen cards to destroy too
//				switchPlayerTurn();
//			}
//			
//			// Quit clause
//		}
//	}
	
	public void processCard(Card card, ArrayList<Card> chosenCards) {
		if(card instanceof Industry) {
			if (isPlayerTurn) {
				if (card.getStars() > 1) {
					card.handleInput();
					playerBoard.mergeCard(card, chosenCards);
					user.removeCard(card);
					for(int i=0; i<chosenCards.size(); i++) {
						user.removeCard(chosenCards.get(i));
					}
				} else {
					card.handleInput();
					playerBoard.addToField(card);
					user.removeCard(card);
				}
				hasPlayed = true;
			} else {
				if (card.getStars() > 1) {
					card.switchTextures();
					card.handleInputEnemy();
					aiBoard.mergeCard(card, chosenCards);
					enemyAI.removeCard(card);
					for(int i=0; i<chosenCards.size(); i++) {
						enemyAI.removeCard(chosenCards.get(i));
					}
				} else {
					card.switchTextures();
					card.handleInputEnemy();
					aiBoard.addToField(card);
					enemyAI.removeCard(card);
				}
				changeEmissions(aiBoard.getTotalPoints());
			}
			doCardAbility();
		}else {
			if (isPlayerTurn) {
				hasPlayed = true;
			} else {
				if(card.getTitle() == "Manipulation" || card.getTitle() == "Fake news") {
					Random random = new Random();
					chosenCards.add(playerBoard.getField().get(random.nextInt(playerBoard.getField().size() - 1)));
				}
			}
			doCardAbility();
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
	
	public void doCardAbility() {
		
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
