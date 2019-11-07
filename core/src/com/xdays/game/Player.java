package com.xdays.game;


import java.util.ArrayList;

import com.xdays.game.cards.Card;

public abstract class Player {
	
	private String name;
	private ArrayList<Card> hand;
	
	public Player(String name) {
		this.name = name;
	}
		
	public ArrayList<Card> getHand() {
		return hand;
	};
	
	public void setHand(ArrayList<Card> givenCards) {
		hand = givenCards;
	}
	
	public void removeCard(Card card) {
		if(hand.contains(card)) {
			hand.remove(hand.indexOf(card));
		}
	}

}
