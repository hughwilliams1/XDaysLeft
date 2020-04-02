package com.tests.game.cards;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.xdays.game.cards.Card;

class CardTest {
	
	private Card card;

	@BeforeEach
	  void setUp() throws Exception {
		card=new Card("Windmill","Windmill");
		card.setPosition(1f, 1f);
	}
		
	@Test
	void testSetPlay() {
		card.setPlayed(true);
		assertTrue(card.isPlayed());
	}
	
	@Test
	void testHalfPlayed() {
		card.halfPlayed();
		assertTrue(card.isHalfPlayed());
		
		card.stopHalfPlay();
		assertFalse(card.isHalfPlayed());
	}
	
	
}
