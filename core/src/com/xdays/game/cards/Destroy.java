package com.xdays.game.cards;

import java.util.Random;

import com.xdays.game.Board;

/**  
 * Destroy.java - Models the destroy function of the social cards
 *
 * @author  Damian Hobeanu, Mark Ebel, Roberto Lovece, Ronil Goldenwalla, Hugh Williams
 * @version 1.0 
 * @see SocialEffect
 */ 
public class Destroy implements SocialEffect {
	
	private Random rand = new Random();
	
	/**
	 * The method that does the destroy effect
	 * 
	 * @param board - The board to be altered
	 * @param card - The card to be destroyed. Null if not known. Must be a card instance from the board
	 * @param amount - If card Null, the star of the card to be destroyed
	 * 
	 * @return Board The new updated board
	 */
	@Override
	public Board doEffect(Board board, Card card, int amount) {
		if (!board.getField().isEmpty()) {
			if(card == null) {
				boolean found = false;
				Card c = null;
				while (!found) {
					c = board.getField().get(rand.nextInt(board.getField().size())); 
					if (c instanceof Industry) {
						if (c.getStars() == amount) {
							found = true;
						}
					}
				}
				board.getField().remove(c);
			} else {
				board.getField().remove(card);
			}
		}
		return board;
	}
	
	/**
	 * Blank overwritten method as no card chosen
	 * 
	 * 
	 * @return null as no card chosen
	 */
	@Override
	public Card getChosenCard() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
}
