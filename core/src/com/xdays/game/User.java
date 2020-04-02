package com.xdays.game;

import com.xdays.game.cards.CardCollection;

/**  
 * User.java - Used for expansion of the game in the future. A concrete implementation of Player
 *
 * @author  Damian Hobeanu, Mark Ebel, Roberto Lovece, Ronil Goldenwalla, Hugh Williams
 * @version 1.0 
 * @see Player
 */ 
public class User extends Player {
	
	/**
	 * Constructs the user class
	 * 
	 * @param name The name of the user
	 * @param collection The collection the user has initially
	 */ 
	public User(String name, CardCollection collection) {
		super(name, collection);
	}
}
