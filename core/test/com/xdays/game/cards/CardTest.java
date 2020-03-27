package com.xdays.game.cards;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.xdays.game.cards.Card;
import com.xdays.game.cards.CardCollection;

class CardTest {
	
	private CardCollection cardCollection;
	private Card card;
	

	@BeforeEach
	void setUp() throws Exception {
		cardCollection=new CardCollection();
		card=cardCollection.getCard("Windmill");
	}
	
	@Test
	void testSetPlayed() {
		card.setPlayed(true);
		assertTrue(card.isPlayed());
	}
	
	@Test
	void testSetPlay() {
		card.setPlayed(true);
		assertTrue(card.isPlayed());
	}
	
	@Test
	void testSwitchTextures() {
		card.switchTextures();
		assertEquals(card.getTexture(),card.getBackTexture());
	}

	@Test
	void testHalfPlayed() {
		card.halfPlayed();
		assertTrue(card.isHalfPlayed());
	}

	@Test
	void testStopHalfPlay() {
		card.stopHalfPlay();
		assertFalse(card.isHalfPlayed());
	}

}
