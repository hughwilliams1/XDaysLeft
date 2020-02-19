package com.xdays.game.cards;

public class Industry extends Card {
	
	private int stars;
	private int points;
	private int buff;
	private int starBuff;
	
	public Industry(String title, String cardText, int stars, int points) {
		super(title, cardText);
		this.stars = stars;
		this.points = points;
		buff = 0;
		starBuff = 0;
	}
	
	// clone constructor
	public Industry(Industry industry) {
		this(industry.getTitle(), industry.getCardText(), industry.getStars(), industry.getPoints());
	}
	
	@Override
	public int getStars() {
		return stars + getStarBuff();
	}
	
	public int getPoints() {
		return points + getBuff();
	}
	
	@Override
	public boolean isIndustry() {
		return true;
	}
	
	public void editBuff(int amount) {
		buff += amount;
	}
	
	public int getBuff() {
		return buff;
	}
	
	public void editStars(int amount) {
		starBuff += amount;
	}
	
	public int getStarBuff() {
		return starBuff;
	}
}
