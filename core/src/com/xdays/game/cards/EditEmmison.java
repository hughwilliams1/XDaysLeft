package com.xdays.game.cards;

import java.util.Random;

import com.xdays.game.Board;



public class EditEmmison implements SocialEffect{
	
	private Random rand;
	
	/**
	 * @param board - The board to be altered
	 * @param card - The card who's having a buff. Null if random. Must be a card instance from the board
	 * @param amount - The buff amount +/-.
	 */
	@Override
	public Board doEffect(Board board, Card card, int amount) {
		if (!board.getField().isEmpty()) {
			if(card == null) {
				boolean found = false;
				Card c = null;
				while (!found) {
					c = board.getField().get(rand.nextInt(board.getField().size() - 1));
					if (c instanceof Industry) {
						found = true;
					}
				}
				((Industry) c).editBuff(amount);
			} else {
				((Industry) card).editBuff(amount);
			}
		}
		
		return board;	
	}
}
