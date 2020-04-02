package Java;


import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.xdays.game.Deck;
import com.xdays.game.cards.Card;

class DeckTest {
	
	private Deck deck;
	private Card testCard;

	@BeforeEach
	void setUp() throws Exception {
		ArrayList<Card> cards= new ArrayList<Card>();
		testCard=new Card("Windmill","Windmill");
		cards.add(testCard);
		cards.add(new Card("Solar Farm","Solar Farm"));
		cards.add(new Card("Windmill","Windmill"));
		cards.add(new Card("Windmill","Windmill"));
		
		deck=new Deck(cards);
	}

	@Test
	void testResetDeck() {
		ArrayList<Card> newCards= new ArrayList<Card>();
		newCards.add(new Card("Windmill","Windmill"));
		newCards.add(new Card("Solar Farm","Solar Farm"));
		
		deck.resetDeck(newCards);
		assertEquals(2,deck.getDeckSize());
	}

	@Test
	void testAddCard() {
		deck.addCard(new Card("Geothermal","Geothermal"));
		assertEquals(5,deck.getDeckSize());
	}

	@Test
	void testAddCards() {
		ArrayList<Card> testCards= new ArrayList<Card>();
		testCards.add(new Card("News","News"));
		testCards.add(new Card("Geothermal","Geothermal"));
		
		deck.addCards(testCards);
		assertEquals(6,deck.getDeckSize());
	}

	@Test
	void testDraw() {
		Card actual=deck.draw();
		assertNotNull(actual);
	}
	
	@Test
	void testRemoveCard() {
		deck.removeCard(testCard);
		assertEquals(3,deck.getDeckSize());
	}

	@Test
	void testInstancesOfCardInDeck() {
		int actual=deck.instancesOfCardInDeck(testCard);
		assertEquals(3,actual);
		
		int testEmpty=deck.instancesOfCardInDeck(new Card("Geothermal","Geothermal"));
		assertEquals(0,testEmpty);
	}
	
	@Test
	void testDrawAmount() {
		ArrayList<Card> expected=new ArrayList<Card>();
		assertNotNull(deck.drawAmount(2));
		assertEquals(expected,deck.drawAmount(0));
	}

}
