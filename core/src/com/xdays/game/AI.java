package com.xdays.game;

import com.xdays.game.cards.Card;

public class AI implements Player {

	private Card[] hand;

	@Override
	public Card[] getHand() {
		return hand;
	}

}
