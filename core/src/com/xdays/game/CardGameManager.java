package com.xdays.game;

import com.xdays.game.cards.Card;
import com.xdays.game.cards.CardReader;
import com.xdays.game.cards.Industry;

public class CardGameManager {
	
	private int emissionsBar;
	
	private User user;
	private AI enemyAI;
	
	private boolean isPlayerTurn;
	
	private Board board;
	
	public CardGameManager (int emissionsValue, User user, AI enemyAI) {
		CardReader reader = new CardReader();
		
		emissionsBar = emissionsValue;
		
		this.user = user;
		this.enemyAI = enemyAI;
		
		this.user.setHand(reader.getIndustryCardsArray());
		this.enemyAI.setHand(reader.getIndustryCardsBadArray());
		
		isPlayerTurn = false;
		
		board = new Board();
	}
	
	public User getUser() {
		return user;
	}
	
	public AI getAI() {
		return enemyAI;
	}
	
	public void playCardGame () {
		
		boolean finished = false;
		
		while(emissionsBar != 100 && emissionsBar != 0 && !finished) {
			
			//User Plays
			//AI plays
			//changeEmissions(((Industry) card).getPoints());
		}
	}

	public void processCard(Card card) {
		if(card instanceof Industry) {
			board.addToField(isPlayerTurn, card);
			doCardAbility();
		}else {
			doCardAbility();
		}
	}
	
	public void changeEmissions(int amount) {
		emissionsBar += amount;
	}
	
	public void doCardAbility() {
		
	}
	
	public boolean isPlayersTurn() {
		return isPlayerTurn;
	}
	
	private void switchPlayerTurn() {
		isPlayerTurn = !isPlayerTurn;
	}
}
