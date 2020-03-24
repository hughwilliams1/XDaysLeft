package com.xdays.game;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

import com.xdays.game.cards.Card;
import com.xdays.game.cards.Industry;

public class Deck {
	
	// queue of cards represents deck
	private Queue<Card> deck;
	private ArrayList<Card> arrayDeck;
	
	public Deck(ArrayList<Card> givenCards) {
		deck = new LinkedList<>();
		addCards(givenCards);
		
		// randomises card order each time
		/*Commented this, because it's already done in addCards()?
		arrayDeck = queueToArray();*/
		//shuffle();
	}
	
	public void resetDeck(ArrayList<Card> givenCards) {
		deck = new LinkedList<>();
		addCards(givenCards);
		shuffle();
	}
	
	public void addCard(Card card) {	
		deck.add(card);
		arrayDeck = queueToArray();
	}
	
	// adds cards from a given array to the deck
	public void addCards(ArrayList<Card> givenCards) {
		for(Card c : givenCards) {
			deck.add(c);
		}
		arrayDeck = queueToArray();
	}
	
	public Card draw() {
		Card drawn = deck.poll();
		arrayDeck = queueToArray();
		return drawn;
	}
	
	public int getDeckSize() {
		return deck.size();
	}
	
	// returns true if deck empty
	public boolean isDeckEmpty() {
		return deck.isEmpty();
	}
	
	public Card getCard(int position) {
		return arrayDeck.get(position);
	}
	
	// shuffle the current deck so cards are not drawn in the same order
	private void shuffle() {
		ArrayList<Card> shuffleArray = queueToArray();
		Collections.shuffle(shuffleArray);
		
		// clears the current queue
		deck.clear();
		addCards(shuffleArray);
		arrayDeck = queueToArray();
	}
	
	// convert the deck from a queue into an arraylist note the deck is kept intact
	private ArrayList<Card> queueToArray() {
		ArrayList<Card> returnArray = new ArrayList<Card>();
		for (Card card : deck) {
			returnArray.add(card);
		}
		
		return returnArray;
	}
	
	// draw an amount of cards from the deck returns null if want to draw
	// more card than in the deck otherwise returns array of drawn cards
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
	
}
