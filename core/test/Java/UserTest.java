package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.xdays.game.User;
import com.xdays.game.cards.Card;
import com.xdays.game.cards.CardCollection;
import com.xdays.game.cards.Industry;
import com.xdays.game.cards.Social;

class UserTest {
	
	private User user;
	
	@BeforeEach
	void setUp() throws Exception {
		user=new User("Player1",new CardCollection());
	}
	@Test
	void testSetCurrentDeck() {
		String[] newDeck= new String[]{"Solar Panel", "Solar Panel", "Solar Panel"};
		user.setCurrentDeck(newDeck);
		assertEquals(3,user.getCurrentDeck().length);
	}


	@Test
	void testSetHandFromDeck() {
		user.setHandFromDeck();
		assertEquals(8,user.handSize());
	}
	
	@Test
	void testGetCardFromHand() {
		String[] newDeck= new String[]{"Solar Panel", "Solar Panel", "Solar Panel"};
		user.setCurrentDeck(newDeck);
		user.setHandFromDeck();
		Card actual=user.getCardFromHand(1);
		assertEquals("Solar Panel",actual.getTitle());
		
	}


	
}


