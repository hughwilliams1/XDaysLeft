package com.xdays.game;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import com.xdays.game.cards.Card;
import com.xdays.game.cards.Industry;

public class Deck {
	
	// queue of cards represents deck
	private Queue<Card> deck;
	// smallest amount of cards each deck should have
	private static final int minimumDeckSize = 10;
	
	public Deck(ArrayList<Card> givenCards) {
		deck = new LinkedList<>();
		// deck get cards from player but probably shouldn't do that but what about the enemy
		addCards(givenCards);
	}
	
	public void addCard(Card card) {	
		deck.add(card);
	}
	
	public void addCards(ArrayList<Card> givenCards) {
		for(Card c : givenCards) {
			deck.add(c);
		}
	}
	
	public Card draw() {
		return deck.poll();
	}
	
	public ArrayList<Card> drawAmount(int amount) {
		ArrayList<Card> returnArray = new ArrayList<Card>();
		int industryCards = 0;
		if (!deck.isEmpty() || deck.size() < amount) {
			for (int i = 0; i <= amount; i++) {
				Card c = deck.poll();
				if(c instanceof Industry) {
					industryCards ++;
				}
				if(industryCards<=7) {
					returnArray.add(deck.poll());
				}else {
					while(c instanceof Industry) {
						c = deck.poll();
					}
					returnArray.add(c);
				}
			}
			return returnArray;
		}
		return null;
	}
	
}
