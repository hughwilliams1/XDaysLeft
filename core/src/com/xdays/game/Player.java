package com.xdays.game;


import java.util.ArrayList;

import com.xdays.game.cards.Card;
import com.xdays.game.cards.CardCollection;

/**  
 * Player.java - Abstract class for the player
 *
 * @author  Damian Hobeanu, Mark Ebel, Roberto Lovece, Ronil Goldenwalla, Hugh Williams
 * @version 1.0 
 */ 
public abstract class Player {
	
	private String name;
	// the current players hand
	protected ArrayList<Card> hand;
	// current players deck
	private Deck deck;
	
	private String[] starterDeck;
	private String[] currentDeck;
	
	// most card that can be in a hand
	public final int MAX_HAND_SIZE = 8;
	
	private int completedLevel;
	
	//All cards loaded
	private CardCollection collection;
	
	/**
	 * Constructor used by the player who deck will remain the same throughout different levels
	 * 
	 * @param name The name of the player
	 * @param collection The {@code CardCollection} for the player
	 */
	public Player(String name, CardCollection collection) {
		completedLevel = 0;
		starterDeck = new String[]{"Solar Panel", "Solar Panel", "Solar Panel", "Solar Panel",
				"Household Recycle", "Household Recycle", "Household Recycle", "Household Recycle",
				"Vegan", "Electric Bus",
				"Solar Farm",
				"Geothermal",
				"Protests", "Protests", "Strike", "Online Posts"};
		
		this.name = name;
		hand = new ArrayList<Card>();
		
		// uses the string array start deck and the card collection to make a deck
		this.collection = collection;
		deck = new Deck(collection.getMultipleCards(starterDeck));
		
		currentDeck = starterDeck.clone();
	}
	
	/**
	 * constructor used by the ai as there deck differs per level
	 * 
	 * @param name The name of the player
	 * @param deck The deck of the player
	 */
	public Player(String name, Deck deck) {
		this.deck = deck;
		this.name = name;
		hand = new ArrayList<Card>();
	}
	
	/**
	 * Sets the Players Deck
	 * 
	 * @param givenDeck The {@code String[]} of the deck
	 */
	public void setCurrentDeck(String[] givenDeck) {
		currentDeck = givenDeck;
	}
	
	/**
	 * Resets the players hand by clearing and resetting
	 */
	public void resetHand() {
		hand.clear();
		deck.resetDeck(collection.getMultipleCards(currentDeck));
	} 
	
	/**
	 * @return {@code String[]} The Current players deck
	 */
	public String[] getCurrentDeck() {
		return currentDeck;
	}
	
	/**
	 * Gets hand size
	 * 
	 * @return {@code int} The hand size of the player
	 */
	public int handSize() {
		return hand.size();
	}
	
	/**
	 * Gets a card from an index from the players hand
	 * 
	 * @param index The index of the card
	 * @return {@code Card} The Card from the hand
	 */
	public Card getCardFromHand(int index) {
		return hand.get(index);
	}
	
	/**
	 * @return The {@code Deck} of the Player
	 */ 
	public Deck getDeck() {
		return deck;
	}
	
	/**
	 * Updates the current deck
	 */
	public void updateCurrentDeck() {
		currentDeck = deck.deckToString();
	}
	
	// set hand as a give array of cards
	/**
	 * Sets the hand from a given array of cards
	 * @param givenCards {@code ArrayList<Card>} The new hand set for the player
	 */
	public void setHand(ArrayList<Card> givenCards) {
		hand = givenCards;
	}
	
	/**
	 * Draws till the player hand is full
	 */
	public void setHandFromDeck() {
		hand = deck.drawAmount(MAX_HAND_SIZE - hand.size());
	}
	
	/**
	 * Adds a card from the deck to the player hand
	 */
	public void addCardToHand() {
		if (!deck.isDeckEmpty()) {
			hand.add(deck.draw());
		} else {
			System.out.println("Deck is empty");
		}
	}
	
	/**
	 * Removes a given card from the player hand
	 * 
	 * @param card The card to be removed
	 */
	public void removeCard(Card card) {
		if(hand.contains(card)) {
			System.out.println("Card removed: " + hand.remove(hand.indexOf(card)).getTitle());
		}
	}
	
	/**
	 * Sets the amount of levels the player has completed
	 * @param level The {@code int} number of levels completed
	 */
	public void setCompletedLevel(int level) {
		completedLevel = level;
	}
	
	/**
	 * @return The Completed levels of the players
	 */
	public int getCompletedLevel() {
		return completedLevel;
	}
	
	/**
	 * returns the current hand of the player as a string
	 * @return The current hand as a {@code String}
	 */
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
