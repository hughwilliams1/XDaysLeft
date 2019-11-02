package com.xdays.game;

import com.xdays.game.cards.Card;

public class User implements Player {
	
	private Card[] hand;
	
	public User(Card[] hand) {
		this.hand = hand;
	}

	@Override
	public Card[] getHand() {
		return hand;
	}

}
