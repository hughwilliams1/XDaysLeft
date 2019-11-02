package com.xdays.game.cards;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class CardReader {
	
	private HashMap <String , Industry> industryCards;
	private HashMap <String , Social> socialCards;
	
	private HashMap <String , Industry> industryCardsBad;
	private HashMap <String , Social> socialCardsBad;
	
	public CardReader() {
		industryCards = new HashMap <String , Industry>();
		socialCards = new HashMap <String , Social>();
		
		industryCardsBad = new HashMap <String , Industry>();
		socialCardsBad = new HashMap <String , Social>();
		
		readIndustryCards();
		readSocialCards();
	}
	
	public HashMap <String , Industry> getIndustryCards(){
		return industryCards;
	}
	
	public HashMap <String , Social> getSocialCards(){
		return socialCards;
	}
	
	public HashMap <String , Industry> getIndustryCardsBad(){
		return industryCardsBad;
	}
	
	public HashMap <String , Social> getSocialCardsBad(){
		return socialCardsBad;
	}
	
	public void readIndustryCards() {
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
		
		
	}
	
	public void readSocialCards() {
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
			
			socialCards.put(card.get("title").toString(), new Social(card.get("title").toString(), card.get("cardText").toString()));
			socialCardsBad.put(cardBad.get("title").toString(), new Social(cardBad.get("title").toString(), cardBad.get("cardText").toString()));
		}
	}
}
