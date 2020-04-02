package com.tests.game.cards;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.xdays.game.Board;
import com.xdays.game.cards.Card;
import com.xdays.game.cards.EditStar;
import com.xdays.game.cards.Industry;

class EditStarTest {

	private EditStar editStar=new EditStar();
	private Board board=new Board();
	
	@BeforeEach
	void setUp() throws Exception {
		board.addToField(new Industry("Windmill","Windmill",2,3), false);
	}

	@Test
	void testDoEffect() {
		board=editStar.doEffect(board, board.getCard(0), 2);
		Card card=board.getCard(0);
		int actual=((Industry)card).getStars();
		assertEquals(4,actual);
	}

}
