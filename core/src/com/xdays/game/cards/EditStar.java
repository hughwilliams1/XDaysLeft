package com.xdays.game.cards;

import com.xdays.game.Board;

/**  
 * EditStar.java - Models the editing of the stars function of the social cards
 *
 * @author  Damian Hobeanu, Mark Ebel, Roberto Lovece, Ronil Goldenwalla, Hugh Williams
 * @version 1.0 
 * @see SocialEffect
 */ 
public class EditStar implements SocialEffect{
	
	/**
	 * Edits the star of a card depending on the params
	 * 
	 * @param board - The board to be altered
	 * @param card - The card to be altered. Must be a card instance from the board
	 * @param amount - The amount of stars being changed
	 * 
	 * @return Board The modified board
	 */
	@Override
	public Board doEffect(Board board, Card card, int amount) {
		((Industry) card).editStars(amount);
		
		return board;
	}
	
	/**
	 * Gets the card chosen to be edited
	 * 
	 * @return Card The card chosen
	 */
	@Override
	public Card getChosenCard() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
}
