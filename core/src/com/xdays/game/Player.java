package com.xdays.game;


import java.util.ArrayList;

import com.xdays.game.cards.Card;

public abstract class Player {
	
	private String name;
	private ArrayList<Card> hand;
	private Deck deck;
	
	public Player(String name, Deck deck) {
		this.name = name;
		this.deck = deck;
	}
		
	public ArrayList<Card> getHand() {
		return hand;
	};
	
	public void setHand(ArrayList<Card> givenCards) {
		hand = givenCards;
	}
	
	public void setHandFromDeck() {
		hand = deck.drawAmount(8);
	}
	
	public void addCardToHand() {
		hand.add(deck.draw());
	}
	
	public void removeCard(Card card) {
		if(hand.contains(card)) {
			hand.remove(hand.indexOf(card));
		}
	}

}
