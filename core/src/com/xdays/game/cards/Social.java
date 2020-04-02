package com.xdays.game.cards;

import com.xdays.game.Board;

/**  
 * Social.java - a simple class for demonstrating the use of javadoc comments.  
 *
 * @author  Damian Hobeanu, Mark Ebel, Roberto Lovece, Ronil Goldenwalla, Hugh Williams
 * @version 1.0 
 * @see Card
 */ 
public class Social extends Card {
	
	private int amount;
	private SocialEffect socialEffect;
	private boolean selectedCardNeeded;
	
	/**
	 * The constructor for the social cards
	 * 
	 * @param title The title of the cards
	 * @param cardText The card text of the cards
	 * @param amount The Buff amount of the card /  The star of card to destroy
	 * @param selectedCardNeeded Bool if the card needs the user/AI to select a card when this is played
	 */
	public Social(String title, String cardText, int amount, boolean selectedCardNeeded) {
		super(title, cardText);
		this.amount = amount;
		this.selectedCardNeeded = selectedCardNeeded;
	}
	
	// clone constructor
	/**
	 * A constructor that clones a given social card
	 * 
	 * @param social The card to be cloned
	 */
	public Social(Social social) {
		this(social.getTitle(), social.getCardText(), social.getAmount(), social.isSelectedCardNeeded());
		setEffect(social.getSocialEffect());
	}
	
	/**
	 * Returns if the Social card needs another card to be selected
	 * 
	 * @return boolean If you need to select a card
	 */
	public boolean isSelectedCardNeeded() {
		return selectedCardNeeded;
	}
	
	/**
	 * 
	 * @return int The social cards amount
	 */
	public int getAmount() {
		return amount;
	}
	
	/**
	 * Sets the effect of the social card with a given SocialEffect
	 * 
	 * @param effect The SocialEffect the social card will have
	 */
	public void setEffect(SocialEffect effect) {
		socialEffect = effect;
	}
	
	/**
	 * Calls the doEffect of the set SocialEffect
	 * 
	 * @param board The board for the effect to apply to
	 * @param card The card for the effect to apply to
	 * @return Board the modified board
	 */
	public Board doEffect(Board board, Card card) {
		return socialEffect.doEffect(board, card, amount);
	}
	
	/**
	 * Calls the doEffect of the set SocialEffect with the +/-
	 * if its a ai or player board
	 * 
	 * @param board The board for the effect to apply to
	 * @param card The card for the effect to apply to]
	 * @param playerBoardOrAIBoard If it's the player (true) or AI (false)
	 * @return Board the modified board
	 */
	public Board doEffect(Board board, Card card, boolean playerBoardOrAIBoard) {
		if(playerBoardOrAIBoard) {
			return socialEffect.doEffect(board, card, amount);
		}else {
			return socialEffect.doEffect(board, card, -amount);
		}
	}
	
	/**
	 * Gets the SociaEffect class that Social calls
	 * @return SocialEffect The SocailEffect of the card
	 */
	public SocialEffect getSocialEffect() {
		return socialEffect;
	}
	
	
}
