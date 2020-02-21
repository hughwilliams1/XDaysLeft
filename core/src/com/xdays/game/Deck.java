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
	
	public Deck(ArrayList<Card> givenCards) {
		deck = new LinkedList<>();
		
		addCards(givenCards);
		
		// randomises card order each time
		//shuffle();
	}
	
	public void addCard(Card card) {	
		deck.add(card);
	}
	
	// adds cards from a given array to the deck
	public void addCards(ArrayList<Card> givenCards) {
		for(Card c : givenCards) {
			deck.add(c);
		}
	}
	
	public Card draw() {
		return deck.poll();
	}
	
	// shuffle the current deck so cards are not drawn in the same order
	private void shuffle() {
		ArrayList<Card> shuffleArray = queueToArray();
		Collections.shuffle(shuffleArray);
		
		// clears the current queue
		deck.clear();
		addCards(shuffleArray);
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
			return returnArray;
		}
		return null;
	}
	
}
