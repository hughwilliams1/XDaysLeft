package com.xdays.game.cards;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.xdays.game.Game;

public class Industry extends Card {
	
	private int stars;
	private int points;
	private int buff;
	private int starBuff;
	private BitmapFont font;
	
	public Industry(String title, String cardText, int stars, int points) {
		super(title, cardText);
		this.stars = stars;
		this.points = points;
		buff = 0;
		starBuff = 0;
		
		font = new BitmapFont(Gdx.files.internal("calibri-font/calibri.fnt"), false);
		font.getData().setScale(0.15f, 0.15f);
	}
	
	// clone constructor
	public Industry(Industry industry) {
		this(industry.getTitle(), industry.getCardText(), industry.getStars(), industry.getPoints());
		buff = industry.getBuff();
		starBuff = industry.getStarBuff();
	}
	
	public void draw(SpriteBatch sb) {
		sb.draw(getTexture(), getX(), getY(), getBoundsWidth(), getBoundsHeight());
		String points = Integer.toString(getPoints());
		if(points.length()>1) {
			if(getPoints()<0) {
				font.draw(sb, points, getX()+getBoundsWidth()-27, getY()+22);
			}else {
				font.draw(sb, points, getX()+getBoundsWidth()-27, getY()+22);
			}
		}else {
			if(getPoints()<0) {
				font.draw(sb, points, getX()+getBoundsWidth()-22, getY()+22);
			}else {
				font.draw(sb, points, getX()+getBoundsWidth()-22, getY()+22);
			}
		}
	}
	
	public void AiDraw(SpriteBatch sb) {
		sb.draw(getTexture(), getX(), getY()+10, getBoundsWidth(), getBoundsHeight());
		String points = Integer.toString(getPoints());
		if(points.length()>1) {
			if(getPoints()<0) {
				font.draw(sb, points, getX()+getBoundsWidth()-27, getY()+32);
			}else {
				font.draw(sb, points, getX()+getBoundsWidth()-27, getY()+32);	
			}
		}else {
			if(getPoints()<0) {
				font.draw(sb, points, getX()+getBoundsWidth()-22, getY()+32);
			}else {
				font.draw(sb, points, getX()+getBoundsWidth()-22, getY()+32);
			}
		}
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
