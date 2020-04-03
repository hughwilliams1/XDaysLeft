package com.xdays.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

import com.xdays.game.cards.Card;
import com.xdays.game.cards.Industry;
import com.xdays.game.cards.Social;

/**  
 * Deck.java - Used to model the card deck in our game 
 *
 * @author  Damian Hobeanu, Mark Ebel, Roberto Lovece, Ronil Goldenwalla, Hugh Williams
 * @version 1.0 
 */ 

public class Deck {
	
	// queue of cards represents deck
	private Queue<Card> deck;
	private ArrayList<Card> arrayDeck;
	
	public static final int MAX_DECK_SIZE = 30;
	
	/**
	 * Creates a new deck queue, queues up the cards gave in the parameter and shuffles the queue
	 * 
	 * @param givenCards	card you want to add to a deck
	 */
	
	public Deck(ArrayList<Card> givenCards) {
		deck = new LinkedList<>();
		addCards(givenCards);
		
		// randomises card order each time
		/*Commented this, because it's already done in addCards()?
		arrayDeck = queueToArray();*/
		shuffle();
	}
	
	/**
	 * Resets the deck to a given set of cards
	 * 
	 * @param givenCards	cards you want the deck to be reset to
	 */
	
	public void resetDeck(ArrayList<Card> givenCards) {
		deck = new LinkedList<>();
		addCards(givenCards);
		shuffle();
	}
	
	/**
	 * Adds a card to the deck
	 * 
	 * @param card	card to be added to deck
	 */
	
	public void addCard(Card card) {
		Card deckCard = null;
		// the card to either social or industry depending on the instance of
		if (card instanceof Social) {
			deckCard = new Social((Social) card);
		}
		else if (card instanceof Industry) {
			deckCard = new Industry((Industry) card);
		}
		deck.add(deckCard);
		arrayDeck = queueToArray();
	}
	
	/**
	 * Adds multiple cards to a deck by using {@link #addCard(Card)}
	 * 
	 * @param givenCards	arraylist of the cards to be added
	 */
	public void addCards(ArrayList<Card> givenCards) {
		for(Card c : givenCards) {
			deck.add(c);
		}
		arrayDeck = queueToArray();
	}
	
	/**
	 * Removes a card from the deck 
	 * 
	 * @param card	the card to be removed
	 */
	
	public void removeCard(Card card) {
		if (deck.contains(card)) {
			deck.remove(card);
		}
		arrayDeck = queueToArray();
	}
	
	/**
	 * @return	a card drawn from the deck
	 */
	
	public Card draw() {
		Card drawn = deck.poll();
		arrayDeck = queueToArray();
		return drawn;
	}
	
	/**
	 * @return	the deck size
	 */
	
	public int getDeckSize() {
		return deck.size();
	}
	
	/**
	 * @return	true if the deck is empty
	 */
	
	public boolean isDeckEmpty() {
		return deck.isEmpty();
	}
	
	/**
	 * @param position
	 * @return	a card at a specific position in the deck
	 */
	
	public Card getCard(int position) {
		return arrayDeck.get(position);
	}
	
	/**
	 * @param card	card to check the instances
	 * @return 	how many instances of a card are in the deck
	 */
	
	public int instancesOfCardInDeck(Card card) {
		int returnNumber = 0;
		for (Card deckCard : arrayDeck) {
			if (card.getTitle().equals(deckCard.getTitle())) {
				returnNumber++;
			}
		}
		
		return returnNumber;
	}

	/**
	 * shuffles the deck into a random order
	 */
	
	private void shuffle() {
		ArrayList<Card> shuffleArray = queueToArray();
		Collections.shuffle(shuffleArray);
		
		// clears the current queue
		deck.clear();
		addCards(shuffleArray);
		arrayDeck = queueToArray();
	}
	
	/** 
	 * @return	the queue of the deck as an array
	 */
	
	private ArrayList<Card> queueToArray() {
		ArrayList<Card> returnArray = new ArrayList<Card>();
		for (Card card : deck) {
			returnArray.add(card);
		}
		
		return returnArray;
	}

	
	/**
	 * draw an amount of cards from deck 
	 * 
	 * @param amount	amount of cards you want to do
	 * @return	cards you drew from the deck return null if you want draw more than deck.size()
	 */
	
	public ArrayList<Card> drawAmount(int amount) {
		ArrayList<Card> returnArray = new ArrayList<Card>();

		if (!deck.isEmpty() || deck.size() < amount) {
			for (int i = 0; i < amount; i++) {
				Card c = draw();
				returnArray.add(c);
			}
			arrayDeck = queueToArray();
			return returnArray;
		}
		return null;
	}
	
	/**
	 * @return	the deck as a string
	 */
	
	public String[] deckToString() {
		String[] returnArray = new String[arrayDeck.size()]; 
		for (int x = 0 ; x < arrayDeck.size() ; x++) {
			returnArray[x] = arrayDeck.get(x).getTitle();
		}
		return returnArray;
	}
}
