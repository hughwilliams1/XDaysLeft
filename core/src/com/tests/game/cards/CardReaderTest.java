package com.tests.game.cards;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.xdays.game.cards.CardReader;
import com.xdays.game.cards.Industry;

class CardReaderTest {

	private CardReader cardReader;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		cardReader=new CardReader();
	}

	@Test
	void testReadIndustryCards() {
		HashMap<String, Industry> industryCards = new HashMap <String , Industry>();
		
		fail("Not yet implemented"); // TODO
	}

	@Test
	void testReadSocialCards() {
		fail("Not yet implemented"); // TODO
	}

}
