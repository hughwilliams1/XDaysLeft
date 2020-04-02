package com.tests.game.cards;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.xdays.game.cards.Card;
import com.xdays.game.cards.CardCollection;

class CardTest {
	
	//private static Card card=new Card("Windmill","Windmill");
	/**
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}
	
	@BeforeEach
	  void setUp() throws Exception {
		//card=new Card("Windmill","Windmill");
	}*/
		
	@Test
	void testSetPlay() {
		Card card=new Card("Windmill","Windmill");
		card.setPlayed(true);
		assertTrue(card.isPlayed());
	}
	
	@Test
	void testHalfPlayed() {
		Card card=new Card("Windmill","Windmill");
		card.halfPlayed();
		assertTrue(card.isHalfPlayed());
		
		card.stopHalfPlay();
		assertFalse(card.isHalfPlayed());
	}
/**
	@Test
	void testStopHalfPlay() {
		card.stopHalfPlay();
		assertFalse(card.isHalfPlayed());
	}*/

}
