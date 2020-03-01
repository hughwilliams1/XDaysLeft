package com.xdays.game.states;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.xdays.game.Game;
import com.xdays.game.cards.Card;

public class CollectionPage {

	// private static final int CARD_WIDTH = 0;
	// private static final int CARD_HEIGHT = 0;
	private static final int CARD_ROWS = 3;
	private static final int[] X_COORDINATES = { ((Game.WIDTH / 2) / CARD_ROWS),
			((Game.WIDTH / 2) / CARD_ROWS) * 2, Game.WIDTH /2};
	private static final int[] Y_COORINATES = { Game.HEIGHT / 3, (Game.HEIGHT / 3) * 2,
			Game.HEIGHT};
	private int collectionOffset;
	private ArrayList<Card> cards;

	public CollectionPage(ArrayList<Card> cards, int collectionOffset) {
		this.cards = cards;
		this.collectionOffset = collectionOffset;
	}

	public void displayCollectionCards(SpriteBatch sb) {
		// total card per page divide by the card rows
		int currentCardNumber = 0;
		for (int y = 0; y < 9 / CARD_ROWS; y++) {
			
			// 9/card rows represents the total cards on page / the number of card row
			// so the card are displayed on separate rows
			for (int x = 0; x < CARD_ROWS; x++, currentCardNumber++) {
				// checks if the card index is out the array
				if (((collectionOffset * 9) + currentCardNumber) < cards.size()) {
					// get the card to be rendered using current card and the pageoffset
					Card card = cards.get((collectionOffset * 9) + currentCardNumber);
					// TODO clean this up
					sb.draw(card.getTexture(), X_COORDINATES[x] - card.getTexture().getWidth() / 3.4f, Y_COORINATES[y]-card.getTexture().getHeight() / 3.4f, card.getTexture().getWidth() / 3.4f,
							card.getTexture().getHeight() / 3.4f);
				}
			}
		}
	}
	
	
}
