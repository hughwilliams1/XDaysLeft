package com.xdays.game;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import com.xdays.game.cards.Card;
import com.xdays.game.cards.Industry;

public class Deck {
	
	private Queue<Card> deck;
	
	public Deck(ArrayList<Card> givenCards) {
		deck = new LinkedList<>();
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
