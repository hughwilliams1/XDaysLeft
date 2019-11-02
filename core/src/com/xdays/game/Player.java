package com.xdays.game;

import com.xdays.game.cards.Card;

public abstract class Player {
	
	private String name;
	private Card[] hand;
	
	public Player(String name) {
		this.name = name;
	}
		
	public Card[] getHand() {
		return hand;
	};
	
	public void setHand(Card[] givenCards) {
		hand = givenCards;
	}

}
