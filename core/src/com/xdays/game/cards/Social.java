package com.xdays.game.cards;

import com.xdays.game.Board;

public class Social extends Card {
	
	private SocialEffect socialEffect;
	
	public Social(String title, String cardText) {
		super(title, cardText);
	}
	
	public void setEffect(SocialEffect effect) {
		socialEffect = effect;
	}
	
	public Board doEffect(Board board, Card card, int amount) {
		return socialEffect.doEffect(board, card, amount);
	}
	
}
