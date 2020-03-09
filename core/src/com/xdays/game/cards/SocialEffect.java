package com.xdays.game.cards;

import com.xdays.game.Board;

public interface SocialEffect {
	
	abstract Board doEffect(Board board, Card card, int amount);
	
	abstract Card getChosenCard();
}
