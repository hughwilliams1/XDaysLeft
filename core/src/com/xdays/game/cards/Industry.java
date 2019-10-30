package com.xdays.game.cards;

public class Industry extends Card {
	
	private int stars;
	private int points;
	
	public Industry(String title, String cardText, int stars, int points) {
		super(title, cardText);
		this.stars = stars;
		this.points = points;
	}
	
	public int getStars() {
		return stars;
	}
	
	public int getPoints() {
		return points;
	}

}
