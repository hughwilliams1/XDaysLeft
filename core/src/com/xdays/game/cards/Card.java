package com.xdays.game.cards;

public abstract class Card {
	
	private String title;
	private String cardText;
	
	public Card(String title, String cardText) {
		this.title = title;
		this.cardText = cardText;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getCardText() {
		return cardText;
	}
	
}
