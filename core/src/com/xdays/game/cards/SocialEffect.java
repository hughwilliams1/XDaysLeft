package com.xdays.game.cards;

import com.xdays.game.Board;

/**  
 * SocialEffect.java - An interface for SocailEffects
 *
 * @author  Damian Hobeanu, Mark Ebel, Roberto Lovece, Ronil Goldenwalla, Hugh Williams
 * @version 1.0 
 */ 
public interface SocialEffect {
	
	abstract Board doEffect(Board board, Card card, int amount);
	
	abstract Card getChosenCard();
}
