package com.xdays.game.cards;

import com.xdays.game.Board;

public class Social extends Card {
	
	private int amount;
	private SocialEffect socialEffect;
	private boolean selectedCardNeeded;
	
	public Social(String title, String cardText, int amount, boolean selectedCardNeeded) {
		super(title, cardText);
		this.amount = amount;
		this.selectedCardNeeded = selectedCardNeeded;
	}
	
	public boolean isSelectedCardNeeded() {
		return selectedCardNeeded;
	}
	
	public void setEffect(SocialEffect effect) {
		socialEffect = effect;
	}
	
	public Board doEffect(Board board, Card card) {
		return socialEffect.doEffect(board, card, amount);
	}
	
}
