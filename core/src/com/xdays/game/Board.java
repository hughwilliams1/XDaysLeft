package com.xdays.game;

import java.util.ArrayList;

import com.xdays.game.cards.Card;
import com.xdays.game.cards.Industry;

public class Board {
	
	private final static int MAX_BOARD_SIZE = 8;
	private ArrayList<Card> field;
	
	public Board() {
		field = new ArrayList<Card>();
	}
	
	// TODO need to prevent cards from being added if it exceeds board size
	public void addToField(Card card, boolean isPlayer) {
		
		if (isPlayer) {
			card.position.y = 0;
			card.position.y += 210;
		} else {
			card.position.y -= 220;
		}
		
		field.add(card);
		
		// update cards x 
		updateCards();
		
	}
	
	private void updateCards() {
		for (float cardPosition = 0 ; cardPosition < field.size() ; cardPosition++) {
			
			Card card = field.get((int) cardPosition);
			
			card.position.x = (((Game.WIDTH) * ((cardPosition+1)/ ((float) field.size()+1)))) - card.getBoundsWidth();
			card.updateBounds();
		}
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
	
	public void mergeCard(Card card, ArrayList<Card> selectedCards, Boolean isPlayer) {
		if(card.getStars() <= getTotalStars(selectedCards)) {
			removeGroupFromField(selectedCards);
			addToField(card, isPlayer);
		} 
	}
	
	public int getBoardSize() {
		return field.size();
	}
	
	public Card getCard(int index) {
		return field.get(index);
	}

}
