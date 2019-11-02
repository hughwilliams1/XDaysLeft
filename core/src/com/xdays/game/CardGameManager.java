package com.xdays.game;

import com.xdays.game.cards.Card;
import com.xdays.game.cards.Industry;

public class CardGameManager {
	
	private int emissionsBar;
	
	private User player;
	private AI enemyAI;
	
	private boolean isPlayerTurn;
	
	private Board board;
	
	public CardGameManager (int emissionsValue, User player, AI enemyAI) {
		emissionsBar = emissionsValue;
		
		this.player = player;
		this.enemyAI = enemyAI;
		
		isPlayerTurn = false;
		
		board = new Board();
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
	
	public void changeEmissions(int ammount) {
		emissionsBar += ammount;
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
