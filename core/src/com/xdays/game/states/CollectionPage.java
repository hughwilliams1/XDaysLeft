package com.xdays.game.states;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.xdays.game.Game;
import com.xdays.game.Player;
import com.xdays.game.User;
import com.xdays.game.cards.Card;

public class CollectionPage {

	private static final float CARD_WIDTH_OFFSET = 40f;
	private static final int CARD_ROWS = 3;
	
	
	private static final float[] X_COORDINATES = { (((Game.WIDTH/ 2) / CARD_ROWS) * 1 ) - CARD_WIDTH_OFFSET,
			(((Game.WIDTH / 2) / CARD_ROWS) * 2) - CARD_WIDTH_OFFSET,
			(((Game.WIDTH /2) / CARD_ROWS) * 3) - CARD_WIDTH_OFFSET};
	
	private static final float[] X_COORDINATES_PLAYER = { (((Game.WIDTH / 2 ) / CARD_ROWS) * 4) - CARD_WIDTH_OFFSET,
			(((Game.WIDTH / 2) / CARD_ROWS) * 5) - CARD_WIDTH_OFFSET,
			(((Game.WIDTH / 2) / CARD_ROWS) * 6) - CARD_WIDTH_OFFSET};
	
	private static final float[] Y_COORINATES = {((Game.HEIGHT/ 3) * 3) - 7f,
			((Game.HEIGHT/ 3) * 2) + 20f,
			((Game.HEIGHT/ 3) * 1) + 46f};
	
	private static float cardHeight;
	private static float cardWidth;
	
	private ArrayList<Card> displayedCards;
	
	private int offset;
	private ArrayList<Card> collectionCards;
	private User player;

	public CollectionPage(ArrayList<Card> cards, int offset) {
		cardHeight = cards.get(0).getTexture().getHeight() / 3.4f;
		cardWidth = cards.get(0).getTexture().getWidth() / 3.4f;
		displayedCards = new ArrayList<Card>();
		this.collectionCards = cards;
		this.offset = offset;
	}
	
	public CollectionPage(User user, int offset) {
		displayedCards = new ArrayList<Card>();
		this.player = user;
		this.offset = offset;
	}

	public void displayCollectionCards(SpriteBatch sb) {
		displayedCards.clear();
		// total card per page divide by the card rows
		int currentCardNumber = 0;
		for (int y = 0; y < CollectionState.CARDS_PER_PAGE / CARD_ROWS; y++) {
			
			// CollectionState.CARDS_PER_PAGE/CARD_ROWS represents the total cards on page / the number of card row
			// so the card are displayed on separate rows
			for (int x = 0; x < CARD_ROWS; x++, currentCardNumber++) {
				// checks if the card index is out the array
				if (((offset * CollectionState.CARDS_PER_PAGE) + currentCardNumber) < collectionCards.size()) {
					// get the card to be rendered using current card and the pageoffset
					Card card = collectionCards.get((offset * CollectionState.CARDS_PER_PAGE) + currentCardNumber);
					
					// Gives the cards bound depending on it current position
					card.setPosition(X_COORDINATES[x] - cardWidth, Y_COORINATES[y] - cardHeight);
					
					sb.draw(card.getTexture(), X_COORDINATES[x] - cardWidth, Y_COORINATES[y] - cardHeight, cardWidth,
							cardHeight);
					displayedCards.add(card);
				}
			}
		}
	}
	
	public void displayPlayerCards(SpriteBatch sb) {
		displayedCards.clear();
		int currentCardNumber = 0;
		for (int y = 0; y < CollectionState.CARDS_PER_PAGE / CARD_ROWS; y++) {
			
			// CollectionState.CARDS_PER_PAGE/CARD_ROWS represents the total cards on page / the number of card row
			// so the card are displayed on separate rows
			for (int x = 0; x < CARD_ROWS; x++, currentCardNumber++) {
				// checks if the card index is out the array
				if (((offset * CollectionState.CARDS_PER_PAGE) + currentCardNumber) < player.getDeck().getDeckSize()) {
					// get the card to be rendered using current card and the pageoffset
					Card card = player.getDeck().getCard(((offset * CollectionState.CARDS_PER_PAGE) + currentCardNumber));
					
					// Gives the cards bound depending on it current position
					card.setPosition(X_COORDINATES_PLAYER[x] - cardWidth, Y_COORINATES[y] - cardHeight);
					
					sb.draw(card.getTexture(), X_COORDINATES_PLAYER[x] - cardWidth, Y_COORINATES[y] - cardHeight, cardWidth,
							cardHeight);
					displayedCards.add(card);
				}
			}
		}
	}
	
	public ArrayList<Card> getDisplayedCards() {
		return displayedCards;
	}
	
	
}
