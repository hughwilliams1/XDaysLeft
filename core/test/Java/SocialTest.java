package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.xdays.game.Board;
import com.xdays.game.cards.Social;

class SocialTest {
	
	private Social social;

	@BeforeEach
	void setUp() throws Exception {
		social=new Social("Online Post","Online Post",2, true);
	}

	@Test
	void testIsSelectedCardNeeded() {
		assertTrue(social.isSelectedCardNeeded());
	}
}
