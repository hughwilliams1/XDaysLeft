package com.xdays.game.cards;

import com.xdays.game.Board;

public class EditStar implements SocialEffect{
	
	/**
	 * @param board - The board to be altered
	 * @param card - The card to be altered. Must be a card instance from the board
	 * @param amount - The amount of stars being changed
	 */
	@Override
	public Board doEffect(Board board, Card card, int amount) {
		((Industry) card).editStars(amount);
		
		return board;
	}

	@Override
	public Card getChosenCard() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
}
