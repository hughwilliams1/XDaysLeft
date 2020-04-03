package com.xdays.game.cards;

import java.util.ArrayList;
import java.util.Random;

import com.xdays.game.Board;


/**  
 * EditEmmission.java - Models the emissions editing function of the social cards
 *
 * @author  Damian Hobeanu, Mark Ebel, Roberto Lovece, Ronil Goldenwalla, Hugh Williams
 * @version 1.0 
 * @see SocialEffect
 */ 
public class EditEmission implements SocialEffect{
	
	private Random rand = new Random();
	private Card chosenCard;
	
	/**
	 * Edits the emissions of a card depending on the params
	 * 
	 * @param board - The board to be altered
	 * @param card - The card who's having a buff. Null if random. Must be a card instance from the board
	 * @param amount - The buff amount +/-.
	 * 
	 * @return Board The modified board
	 */
	@Override
	public Board doEffect(Board board, Card card, int amount) {
		if (!board.getField().isEmpty()) {
			if(card == null) {
				boolean found = false;
				Card c = null;
				while (!found) { //
					ArrayList<Card> cards = board.getField();
					int cardSize = board.getField().size();
					//System.out.println(cardSize);
					int nextRandom = rand.nextInt(cardSize);
					c = cards.get(nextRandom); 
				//	c = board.getField().get(rand.nextInt(board.getField().size())); // is this supposed to be one??
					if (c instanceof Industry) {
						found = true;
					}
				}
				chosenCard = c;
				((Industry) c).editBuff(amount);
			} else {
				((Industry) card).editBuff(amount);
			}
		}
		
		return board;	
	}
	
	/**
	 * Gets the card chosen to be edited
	 * 
	 * @return Card The card chosen
	 */
	public Card getChosenCard() {
		return chosenCard;
	}
}
