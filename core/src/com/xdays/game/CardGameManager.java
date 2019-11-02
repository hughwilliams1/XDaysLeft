package com.xdays.game;

import com.xdays.game.cards.Card;
import com.xdays.game.cards.Industry;

public class CardGameManager {

	public void processCard(Card card) {
		if(card instanceof Industry) {
			changeEmissions(((Industry) card).getPoints());
			doCardAbility();
		}else {
			doCardAbility();
		}
	}
	
	public void changeEmissions(int ammount) {
		
	}
	
	public void doCardAbility() {
		
	}
}
