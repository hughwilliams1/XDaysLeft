package com.xdays.game;

import java.util.ArrayList;

import com.xdays.game.cards.Card;
import com.xdays.game.cards.Industry;

/**  
 * Board.java - A class that models the board  
 *
 * @author  Damian Hobeanu, Mark Ebel, Roberto Lovece, Ronil Goldenwalla, Hugh Williams
 * @version 1.0 
 */ 
public class Board {
	
	public final int MAX_BOARD_SIZE = 8;
	private ArrayList<Card> field;
	
	/**
	 * Constructor for Board
	 */
	public Board() {
		field = new ArrayList<Card>();
	}
	
	/**
	 * Adds a card to a field
	 * 
	 * @param card The card to be added
	 * @param isPlayer true if the player is playing
	 */
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
	
	/**
	 * Updates the positions of the cards and their bounds
	 */
	private void updateCards() {
		for (float cardPosition = 0 ; cardPosition < field.size() ; cardPosition++) {
			
			Card card = field.get((int) cardPosition);
			
			card.position.x = (((Game.WIDTH) * ((cardPosition+1)/ ((float) field.size()+1)))) - card.getBoundsWidth();
			card.updateBounds();
		}
	}
	
	/**
	 * @return {@code ArrayList<Card>} Returns the Field
	 */
	public ArrayList<Card> getField() {
		return field;
	}
	
	/**
	 * Removes the specified card from the field
	 * 
	 * @param card The card to be removed
	 * @return boolean true if the card was removed
	 */
	public boolean removeFromField(Card card) {
		return field.remove(card);
	}
	
	/**
	 * Removes a group of cards to remove
	 * 
	 * @param toRemove An {@code ArrayList<Card>} of cards to remove
	 * @return boolean true if the card was removed
	 */
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
	
	/**
	 * Gets the total points of the field
	 * 
	 * @return int The total points of the field
	 */
	public int getTotalPoints() {
		int points = 0;
		for(Card c : field) {
			points += ((Industry) c).getPoints();
		}
		//System.out.println(points);
		return points;
	}
	
	/**
	 * Gets the total stars of the passed field
	 * 
	 * @param passedField {@code ArrayList<Card>} The field for the stars to be counted
	 * @return int The total stars on the field
	 */
	public int getTotalStars(ArrayList<Card> passedField) {
		int stars = 0;
		for (Card c : passedField) {
			stars += c.getStars();
		}
		return stars;
	}
	
	/**
	 * Attempts to merge a card with other cards
	 * 
	 * @param card The card to be merged
	 * @param selectedCards {@code ArrayList<Card>} Of cards going into the merge
	 * @param isPlayer boolean true if it is the player
	 */
	public void mergeCard(Card card, ArrayList<Card> selectedCards, Boolean isPlayer) {
		if(card.getStars() <= getTotalStars(selectedCards)) {
			removeGroupFromField(selectedCards);
			addToField(card, isPlayer);
		} 
	}
	
	/**
	 * 
	 * @return The size of the board
	 */
	public int getBoardSize() {
		return field.size();
	}
	
	/**
	 * Get a card from the board with a given index
	 * 
	 * @param index The index of the card
	 * @return The card from the index in the field
	 */
	public Card getCard(int index) {
		return field.get(index);
	}

}
