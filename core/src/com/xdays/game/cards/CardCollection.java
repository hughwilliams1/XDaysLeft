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

		// ** IMPORTANT **
		// If the card isn't a null object uses the copy constructor for both industry and social cards
		// this stops two cards referencing the same object even if the card its the same card
		// this allows us to get the same card from the hashmap and use it multiple times
		if (returnCard != null) {
			if (returnCard.isIndustry()) {
				returnCard = new Industry((Industry) returnCard);
			} else if (!returnCard.isIndustry()) {
				returnCard = new Social((Social) returnCard);
			}
		}

		return returnCard;
	}
	
	public int getSize() {
		return industryCards.size() + socialCards.size();
	}
	
	// draw multiple card from the deck using an array of strings
	// TODO could maybe have CardCollection direction create the decks and return that
	public ArrayList<Card> getMultipleCards(String[] keys) {	
		ArrayList<Card> cardArray = new ArrayList<Card>();
		
		for(String key : keys) {
			// need to check if its an industry or 
			cardArray.add(getCard(key));
		}
		
		return cardArray;
	}
	
	public ArrayList<Card> getAllCards() {
		ArrayList<Card> returnArray = new ArrayList<Card>();
		for (String key : industryCards.keySet()) {
			returnArray.add(industryCards.get(key));
		}
		
		for (String key : socialCards.keySet()) {
			returnArray.add(socialCards.get(key));
		}
		
		return returnArray;
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
