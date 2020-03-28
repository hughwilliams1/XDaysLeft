package com.tests.game.cards;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.xdays.game.cards.Card;
import com.xdays.game.cards.CardCollection;

class CardTest {
	
	private static CardCollection cardCollection;
	private static Card card;
	
	@BeforeEach
	  void setUp() throws Exception {
		cardCollection=new CardCollection();
		card=cardCollection.getCard("Windmill");
	}
	
	@Test
	void testSetPlayed() {
		assertEquals(card,null);
		
		//card.setPlayed(true);
		//assertTrue(card.isPlayed());
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
