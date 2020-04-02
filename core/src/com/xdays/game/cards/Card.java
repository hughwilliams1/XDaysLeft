package com.xdays.game.cards;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

/**  
 * Card.java - A class that models our cards and their positions
 *
 * @author  Damian Hobeanu, Mark Ebel, Roberto Lovece, Ronil Goldenwalla, Hugh Williams
 * @version 1.0 
 */ 
public class Card {
	
	public static final int CARD_WIDTH = 467;
	public static final int CARD_HEIGHT = 722;
	
	private String title;
	private String cardText;
	private Rectangle bounds;
	public Vector3 position;
	private boolean played;
	private boolean halfPlayed;
	private boolean boundsSet;
	
	/**
	 * Constructor for Card
	 *
	 * @param title	The title of the card 
	 * @param cardText	The text of the card
	 */
	public Card(String title, String cardText) {
		this.title = title;
		this.cardText = cardText;
		played = false;
		halfPlayed = false;
		boundsSet = false;
	}
	
	/**
	 * Returns if the card has been played
	 *
	 * @return boolean If the card has been played
	 */
	public boolean isPlayed() {
		return played;
	}
	
	/**
	 * Sets if the card has been played
	 *
	 * @param played Boolean for if the card has been played
	 */
	public void setPlayed(Boolean played) {
		this.played = played;
	}
	
	
	/**
	 * Returns if the card has been half played
	 *
	 * @return boolean If the card has been half played
	 */
	public boolean isHalfPlayed() {
		return halfPlayed;
	}
	
	/**
	 * Changes the position to half played and flips the played boolean
	 */
	public void halfPlayed() {
		if(!played && halfPlayed == false) {
			position.y += 110;
			bounds.setY(position.y);
			halfPlayed = true;
		} 
	}
	
	/**
	 * Changes the position back to normal and flips the played boolean
	 */
	public void stopHalfPlay() {
		position.y = 0;
		bounds.setY(position.y);
		halfPlayed = false;
	}
	
	/**
	 * Resets the position of the card
	 */
	public void resetPosition() {
		if(played) {
			position.y -= 210;
			bounds.setY(position.y);
			played = false;
		}
	}
	
	/**
	 * Sets the bounds of the cards to their X and Y
	 */
	public void updateBounds() {
		bounds.setX(position.x);
		bounds.setY(position.y);
	}
	
	/**
	 * Gets the title of the card
	 *
	 * @return String The title of the card
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * Gets the cardText
	 *
	 * @return String The card Text
	 */
	public String getCardText() {
		return cardText;
	}
	
	/**
	 * Sets the position for the card
	 *
	 * @param x	The X position of the card 
	 * @param y	The Y position of the card
	 */
	public void setPosition(float x, float y) {
		boundsSet = true;
		position = new Vector3 (x, y, 0);
		bounds = new Rectangle(x, y, (Card.CARD_WIDTH/3.4f), (Card.CARD_HEIGHT/3.4f));
	}
	
	/**
	 * Returns if the bounds have been set
	 *
	 * @return Boolean If the bounds have been set
	 */
	public boolean haveBoundsBeenSet() {
		return boundsSet;
	}
	
	/**
	 * Gets the bounds of the Card
	 *
	 * @return Rectangle The card Text
	 */
	public Rectangle getBounds() {
		return bounds;
	}
	
	/**
	 * Gets the X of the cards bounds
	 *
	 * @return float The cards bounds X
	 */
	public float getX() {
		return bounds.getX();
	}
	
	/**
	 * Gets the Y of the cards bounds
	 *
	 * @return float The cards bounds Y
	 */
	public float getY() {
		return bounds.getY();
	}
	
	/**
	 * Gets the width of the cards bounds
	 *
	 * @return float The cards bounds width
	 */
	public float getBoundsWidth() {
		return bounds.getWidth();
	}
	
	/**
	 * Gets the height of the cards bounds
	 *
	 * @return float The cards bounds height
	 */
	public float getBoundsHeight() {
		return bounds.getHeight();
	}
	
	/**
	 * Gets the position of the card
	 *
	 * @return Vector3 The cards position
	 */
	public Vector3 getPosition() {
		return position;
	}
	
	/**
	 * Returns if the card is an industry
	 *
	 * @return float If the card is an industry
	 */
	public boolean isIndustry() {
		return false;
	}

	/**
	 * Returns the stars of the cards
	 * 
	 * @return int The star of the card
	 */
	public int getStars() {
		return 0;
	}

}
