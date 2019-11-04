package com.xdays.game;

import java.util.ArrayList;

import com.xdays.game.cards.Card;

public class Board {
	
	private ArrayList<Card> userField;
	private ArrayList<Card> aiField;
	
	public Board() {
		
		userField = new ArrayList<Card>(10);
		aiField = new ArrayList<Card>(10);
		
	}
	
	public boolean addToField(boolean isPlayer, Card card) {
		if (isPlayer) {
			return userField.add(card);
		} else {
			return(aiField.add(card));
		}
	}
	
	public ArrayList<Card> getAIField() {
		return aiField;
	}
	
	public boolean removeFromField(boolean isPlayer, Card card) {
		if (isPlayer) {
			return userField.remove(card);
		} else {
			return aiField.remove(card);
		}
	}
}
