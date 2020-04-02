package com.xdays.game.cards;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**  
 * CardReader.java - Reads all the card in the .json files to be used by card collection
 *
 * @author  Damian Hobeanu, Mark Ebel, Roberto Lovece, Ronil Goldenwalla, Hugh Williams
 * @version 1.0 
 */ 
public class CardReader {
	
	public CardReader() {	
		
	}
	
	/**
	 * Reads the industry cards from the .json
	 * 
	 * @return {@code ArrayList<Map<String, Industry>>} An ArrayList of Map<String, Industry> to divide good and bad Industry cards
	 */
	public ArrayList<Map<String, Industry>> readIndustryCards() {
		
		// creates the good industry card hashmap to be returned
		HashMap<String, Industry> industryCards = new HashMap <String , Industry>();
		
		// creates the bad social card hashmap to be returned
		HashMap<String, Industry> industryCardsBad = new HashMap <String , Industry>();
		
		//Array of Map<String, Industry> to divide good and bad cards
		ArrayList<Map<String, Industry>> industryCollection = new ArrayList<Map<String, Industry>>();
		
		JSONParser jsonParser = new JSONParser();
		JSONArray cards = new JSONArray();
		JSONArray cardsBad = new JSONArray();

		try {
			Object obj = jsonParser.parse(new FileReader(System.getProperty("user.dir") + "\\cards\\industry.json"));
			Object objBad = jsonParser.parse(new FileReader(System.getProperty("user.dir") + "\\cards\\industryBad.json"));
			
			cards = (JSONArray)obj;
			cardsBad = (JSONArray)objBad;
		} catch (IOException | ParseException e) {
			//e.printStackTrace();
			System.out.println("Failed to read industry cards.");
		}

		for(int i=0; i<cards.size(); i++) {
			JSONObject card = (JSONObject) cards.get(i);
			JSONObject cardBad = (JSONObject) cardsBad.get(i);
			
			Industry industry = new Industry(card.get("title").toString(), card.get("cardText").toString(), Integer.valueOf(card.get("stars").toString()), Integer.valueOf(card.get("points").toString()));
			Industry industryBad = new Industry(cardBad.get("title").toString(), cardBad.get("cardText").toString(), Integer.valueOf(cardBad.get("stars").toString()), Integer.valueOf(cardBad.get("points").toString()));
			
			industryCards.put(industry.getTitle(), industry);
			industryCardsBad.put(industryBad.getTitle(), industryBad);
		}
		
		industryCollection.add(industryCards);
		industryCollection.add(industryCardsBad);
		
		return industryCollection;
	}
	
	/**
	 * Reads the social cards from the .json
	 * 
	 * @return {@codeArrayList<Map<String, Social>>} An ArrayList of Map<String, Social> to divide good and bad social cards
	 */
	public ArrayList<Map<String, Social>> readSocialCards() {
		// creates the good social card hashmap to be returned
		HashMap<String, Social> socialCards = new HashMap <String , Social>();
		
		// creates the bad social card hashmap to be returned
		HashMap<String, Social> socialCardsBad = new HashMap <String , Social>();
		
		// creates array of HashMaps<String, Social> so you can return bad or good cards
		ArrayList<Map<String, Social>> socialCollection = new ArrayList<Map<String, Social>>();
		
		JSONParser jsonParser = new JSONParser();
		JSONArray cards = new JSONArray();
		JSONArray cardsBad = new JSONArray();
		
		try {
			Object obj = jsonParser.parse(new FileReader(System.getProperty("user.dir") + "\\cards\\social.json"));
			Object objBad = jsonParser.parse(new FileReader(System.getProperty("user.dir") + "\\cards\\socialBad.json"));
			
			cards = (JSONArray)obj;
			cardsBad = (JSONArray)objBad;
		} catch (IOException | ParseException e) {
			//e.printStackTrace();
			System.out.println("Failed to read social cards.");
		}

		for(int i=0; i<cards.size(); i++) {
			JSONObject card = (JSONObject) cards.get(i);
			JSONObject cardBad = (JSONObject) cardsBad.get(i);
			
			Social tempSocial = new Social(card.get("title").toString(), card.get("cardText").toString(), Integer.valueOf(card.get("amount").toString()),  Boolean.parseBoolean(card.get("selectedCardsNeeded").toString()));
			if(card.get("type").toString().equals("Destroy")) {
				tempSocial.setEffect(new Destroy());
			}else if(card.get("type").toString().equals("EditEmission")) {
				tempSocial.setEffect(new EditEmission());
			}else {
				tempSocial.setEffect(new EditStar());
			}
			socialCards.put(card.get("title").toString(), tempSocial);
			
			Social tempSocialBad = new Social(cardBad.get("title").toString(), cardBad.get("cardText").toString(), Integer.valueOf(cardBad.get("amount").toString()), Boolean.parseBoolean(cardBad.get("selectedCardsNeeded").toString()));
			if(cardBad.get("type").toString().equals("Destroy")) {
				tempSocialBad.setEffect(new Destroy());
			}else if(cardBad.get("type").toString().equals("EditEmission")) {
				tempSocialBad.setEffect(new EditEmission());
			}else {
				tempSocialBad.setEffect(new EditStar());
			}
			socialCardsBad.put(cardBad.get("title").toString(), tempSocialBad);
		}
		
		// merges bad and good social cards together to be returned
		socialCollection.add(socialCards);
		socialCollection.add(socialCardsBad);
		
		return socialCollection;
	}
}
