package com.xdays.game.cards;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**  
 * CardCollection.java - a collection of all social and industry cards in the game.  
 *
 * @author  Damian Hobeanu, Mark Ebel, Roberto Lovece, Ronil Goldenwalla, Hugh Williams
 * @version 1.0 
 */ 

public class CardCollection {

	private HashMap <String , Industry> goodIndustryCards;
	private HashMap <String , Industry> badIndustryCards;
	
	private HashMap <String , Social> goodSocialCards;
	private HashMap <String , Social> badSocialCards;
	
	private HashMap <String , Industry> industryCollection;
	private HashMap <String , Social> socialCollection;
	
	public CardCollection() {
		
		CardReader reader = new CardReader();
		
		ArrayList<Map<String, Industry>> industryCardsTemp = reader.readIndustryCards();
		ArrayList<Map<String, Social>> socialCardsTemp = reader.readSocialCards();
		
		goodIndustryCards = (HashMap<String, Industry>) industryCardsTemp.get(0);
		badIndustryCards = (HashMap<String, Industry>) industryCardsTemp.get(1);
		
		goodSocialCards = (HashMap<String, Social>) socialCardsTemp.get(0);
		badSocialCards = (HashMap<String, Social>) socialCardsTemp.get(1);
		
		industryCollection = (HashMap<String, Industry>) goodIndustryCards.clone();
		industryCollection.putAll(badIndustryCards);
		
		socialCollection = (HashMap<String, Social>) goodSocialCards.clone();
		socialCollection.putAll(badSocialCards);
	}
	
	 /**
	 *	Get a social or industry card from the collection
	 *
	 * @param key    a key for the hashmap to get a card   
	 * 
	 * @return a card from either the industry cards or the social card, return null if card isn't in the collection
	 */
	public Card getCard(String key) {
		
		Card returnCard = industryCollection.get(key);
		if (returnCard == null) {
			returnCard = socialCollection.get(key);
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
	
	public int getGoodSize() {
		return goodIndustryCards.size() + goodSocialCards.size();
	}
	
	/**
	 * Gets multiple cards for the collection using {@link #getCard(String)}
	 * 
	 * @param keys
	 * @return multiple cards from the collection
	 */
	public ArrayList<Card> getMultipleCards(String[] keys) {	
		ArrayList<Card> cardArray = new ArrayList<Card>();
		
		for(String key : keys) {
			// need to check if its an industry or 
			cardArray.add(getCard(key));
		}
		
		return cardArray;
	}
	
	/**
	 * @return all social and industry cards from the deck good and bad
	 */
	
	public ArrayList<Card> getAllCards() {
		ArrayList<Card> returnArray = new ArrayList<Card>();
		for (String key : industryCollection.keySet()) {
			returnArray.add(industryCollection.get(key));
		}
		
		for (String key : socialCollection.keySet()) {
			returnArray.add(socialCollection.get(key));
		}
		return returnArray;
	}
	
	/**
	 * @return all social and industry cards from the deck good only
	 */
	
	public ArrayList<Card> getAllGoodCards() {
		ArrayList<Card> returnArray = new ArrayList<Card>();
		for (String key : goodIndustryCards.keySet()) {
			returnArray.add(goodIndustryCards.get(key));
		}
		
		for (String key : goodSocialCards.keySet()) {
			returnArray.add(goodSocialCards.get(key));
		}
		
		return returnArray;
	}
	
	/**
	 * @return goes through the industry and social cards and returns a string for all valid keys
	 */
	
	private String getKeySet() {
		String keyString = "Industry Cards Keyset: \n";
		for (String key : industryCollection.keySet()) {
			keyString = keyString + key + ", ";
		}
		
		keyString = keyString + "\n Social Cards Keyset: \n";
		for (String key : socialCollection.keySet()) {
			keyString = keyString + key + ", ";
		}
		
		return keyString;
	}
	
}
