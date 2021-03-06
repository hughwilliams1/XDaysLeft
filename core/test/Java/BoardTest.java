package Java;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.xdays.game.Board;
import com.xdays.game.cards.Card;
import com.xdays.game.cards.CardCollection;
import com.xdays.game.cards.Industry;

class BoardTest {

	private Board board;
	private Card card;
	
	@BeforeEach
	void setUp() throws Exception {
		board=new Board();
		card=new Card("Windmill","Windmill");
		card.setPosition(1f, 1f);
		
	}

	@Test
	void testAddToField() {
		board.addToField(card, false);
		assertEquals(-219,card.position.y);
		assertEquals(board.getBoardSize(),1);
	}

	@Test
	void testRemoveFromField() {
		board.addToField(card, false);
		board.removeFromField(card);
		assertEquals(board.getBoardSize(),0);
	}

	@Test
	void testRemoveGroupFromField() {
		board.addToField(card, false);
		Boolean check=board.removeFromField(card);
		assertTrue(check);
	}

	@Test
	void testGetTotalPoints() {
		board.addToField(card, false);
		int actual=board.getTotalPoints();
		assertEquals(-6,actual);
	}

	@Test
	void testGetTotalStars() {
		board.addToField(card, false);
		int actual=board.getTotalPoints();
		assertEquals(2,actual);
	}
}
