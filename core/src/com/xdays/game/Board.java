package com.xdays.game;

import java.util.ArrayList;

import com.xdays.game.cards.Card;
import com.xdays.game.cards.Industry;

public class Board {
	
	private ArrayList<Card> field;
	
	public Board() {
		field = new ArrayList<Card>(10);
	}
	
	public boolean addToField(Card card) {
		return field.add(card);
	}
	
	public ArrayList<Card> getField() {
		return field;
	}
	
	public boolean removeFromField(Card card) {
		return field.remove(card);
	}
	
	public boolean removeGroupFromField(ArrayList<Card> toRemove) {
		if (field.containsAll(toRemove)) {
			for (Card c : toRemove) {
				field.remove(c);
			}
		} else {
			return false;
		}
		return true;
	}
	
	public int getTotalPoints() {
		int points = 0;
		for(Card c : field) {
			points += ((Industry) c).getPoints();
		}
		//System.out.println(points);
		return points;
	}
	
	public int getTotalStars(ArrayList<Card> passedField) {
		int stars = 0;
		for (Card c : passedField) {
			stars += c.getStars();
		}
		return stars;
	}
	
	public boolean mergeCard(Card card, ArrayList<Card> selectedCards) {
		if(card.getStars() <= getTotalStars(selectedCards)) {
			removeGroupFromField(selectedCards);
			return addToField(card);
		} else {
			return false;
		}
	}
}
