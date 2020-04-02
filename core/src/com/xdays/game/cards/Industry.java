package com.xdays.game.cards;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.xdays.game.Game;

/**  
 * Industry.java - Models the Industry cards
 *
 * @author  Damian Hobeanu, Mark Ebel, Roberto Lovece, Ronil Goldenwalla, Hugh Williams
 * @version 1.0 
 * @see Card
 */ 
public class Industry extends Card {
	
	private int stars;
	private int points;
	private int buff;
	private int starBuff;
	private BitmapFont font;
	
	/**
	 * The constructor for Industry
	 *
	 * @param title	The title of the card
	 * @param cardText	The card text
	 * @param stars The star value of the card
	 * @param points The point value of the card
	 */
	public Industry(String title, String cardText, int stars, int points) {
		super(title, cardText);
		this.stars = stars;
		this.points = points;
		buff = 0;
		starBuff = 0;
		
		font = new BitmapFont(Gdx.files.internal("calibri-font/calibri.fnt"), false);
		font.getData().setScale(0.15f, 0.15f);
	}
	
	/**
	 * The clone constructor for Industry
	 *
	 * @param industry The industry card to be cloned
	 */
	public Industry(Industry industry) {
		this(industry.getTitle(), industry.getCardText(), industry.getStars(), industry.getPoints());
		buff = industry.getBuff();
		starBuff = industry.getStarBuff();
	}
	
	/**
	 * Draws the industry card on the batch
	 *
	 * @param sb The sprite batch to be drawn onto
	 */
	public void draw(SpriteBatch sb) {
		sb.draw((Texture) Game.assetManager.get(getTitle()+".PNG"), getX(), getY(), getBoundsWidth(), getBoundsHeight());
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
	
	/**
	 * Draws the AI industry card on the batch
	 *
	 * @param sb The sprite batch to be drawn onto
	 */
	public void AiDraw(SpriteBatch sb) {
		sb.draw((Texture) Game.assetManager.get(getTitle()+".PNG"), getX(), getY()+10, getBoundsWidth(), getBoundsHeight());
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
	
	/**
	 * Gets the points of the cards
	 * 
	 * @return int The points of the card + any buff
	 */
	public int getPoints() {
		return points + getBuff();
	}
	
	@Override
	public boolean isIndustry() {
		return true;
	}
	
	/**
	 * Adds to the card buff by an amount
	 * 
	 * @param amount The int value to add to the cards buff
	 */
	public void editBuff(int amount) {
		buff += amount;
	}
	
	/**
	 * Gets the buff on the card
	 * 
	 * @return int The buff of the card
	 */
	public int getBuff() {
		return buff;
	}
	
	/**
	 * Adds to the card stars by an amount
	 * 
	 * @param amount The int value to add to the cards stars
	 */
	public void editStars(int amount) {
		starBuff += amount;
	}
	
	/**
	 * Returns the star buff of the cards
	 * 
	 * @return int The star buff of the card
	 */
	public int getStarBuff() {
		return starBuff;
	}
}
