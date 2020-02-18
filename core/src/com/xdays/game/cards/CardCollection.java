package com.xdays.game.cards;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 
 * Collection of all of both social and industry cards in the game
 * 
 * @author Roberto
 *
 */

public class CardCollection {

	private HashMap <String , Industry> industryCards;
	private HashMap <String , Social> socialCards;
	
	public CardCollection() {
		
		CardReader reader = new CardReader();
		
		industryCards = reader.readIndustryCards();
		socialCards = reader.readSocialCards();
	}
	
	// returns a card from either the industry cards or the social card
	// note if key is invalid with return null which may cause a null point error
	public Card getCard(String key) {
		Card returnCard = industryCards.get(key);
		if (returnCard == null) {
			returnCard = socialCards.get(key);
		}
		return returnCard;
	}
	
	// draw multiple card from the deck using an array of strings
	// TODO could maybe have CardCollection direction create the decks and return that
	public ArrayList<Card> getMultipleCards(String[] keys) {	
		ArrayList<Card> cardArray = new ArrayList<Card>();
		
		for(String key : keys) {
			cardArray.add(getCard(key));
		}
		
		return cardArray;
	}
	
	/** 
	 * Mainly just used for testing goes through the industry and social cards
	 * and returns a string for all valid keys
	 */
	
	private String getKeySet() {
		String keyString = "Industry Cards Keyset: \n";
		for (String key : industryCards.keySet()) {
			keyString = keyString + key + ", ";
		}
		
		keyString = keyString + "\n Social Cards Keyset: \n";
		for (String key : socialCards.keySet()) {
			keyString = keyString + key + ", ";
		}
		
		return keyString;
	}
	
}
