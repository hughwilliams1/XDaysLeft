package com.xdays.game;


import java.util.ArrayList;

import com.xdays.game.cards.Card;
import com.xdays.game.cards.CardCollection;

public abstract class Player {
	
	private String name;
	// the current players hand
	protected ArrayList<Card> hand;
	// current players deck
	private Deck deck;
	private String[] starterDeck;
	
	// most card that can be in a hand
	public final int MAX_HAND_SIZE = 8;
	// max amount of one type of card per deck
	public final int MAX_ONE_CARD = 3;
	
	//All cards loaded
	private CardCollection collection;
	
	// constructor used by the player who deck will remain the same throughout different levels
	public Player(String name, CardCollection collection) {
		// string array of the start deck
		starterDeck = new String[]{"Plant Tree", "Plant Tree", "Solar Panel",
				"Solar Panel", "Windmill", "Windmill", "Solar Farm", "Solar Farm", "Protests", "Protests",
				"Plant Tree", "Plant Tree", "Solar Panel", "Solar Panel", "Windmill", "Windmill",
				"Solar Farm", "Solar Farm", "Online Posts", "Protests", "Electric Car", "Household Recycle", 
				"Electric Bus", "Vegan", "Hydroelectric Energy", "Nuclear Plant", "Geothermal"};
		
		this.name = name;
		hand = new ArrayList<Card>();
		
		// uses the string array start deck and the card collection to make a deck
		this.collection = collection;
		deck = new Deck(collection.getMultipleCards(starterDeck));
	}
	
	// constructor used by the ai as there deck differs per level
	public Player(String name, Deck deck) {
		this.deck = deck;
		this.name = name;
		hand = new ArrayList<Card>();
	}
	
	public void resetHand() {
		hand.clear();
		deck.resetDeck(collection.getMultipleCards(starterDeck));
	}
	
	// gets hand size
	public int handSize() {
		return hand.size();
	}
	
	// gets players current hand
	public Card getCardFromHand(int index) {
		return hand.get(index);
	}
	
	public Deck getDeck() {
		return deck;
	}
	
	// set hand as a give array of cards
	public void setHand(ArrayList<Card> givenCards) {
		hand = givenCards;
	}
	
	// Draws till the player hand is full
	public void setHandFromDeck() {
		hand = deck.drawAmount(MAX_HAND_SIZE - hand.size());
	}
	
	// adds a card from the deck to the player hand
	// TODO update hand visually
	public void addCardToHand() {
		if (!deck.isDeckEmpty()) {
			hand.add(deck.draw());
		} else {
			System.out.println("Deck is empty");
		}
	}
	
	// removes a given card from the player hand
	public void removeCard(Card card) {
		if(hand.contains(card)) {
			hand.remove(hand.indexOf(card));
		}
	}
	
	// returns the current hand of the player as a string
	public String currentHandAsString() {
		String returnString = "";
		for (int x = 0 ; x < hand.size() ; x++) {
			if (x != hand.size()-1) {
				returnString = returnString + hand.get(x).getTitle() + ", ";
			} else {
				returnString = returnString + hand.get(x).getTitle();
			}
		}
		return returnString;
	}

}
