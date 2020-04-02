package com.tests.game.cards;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.xdays.game.Board;
import com.xdays.game.cards.Destroy;
import com.xdays.game.cards.Industry;

class DestroyTest {

	private static Destroy destroy=new Destroy();
	private static Board board=new Board();
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		destroy=new Destroy();
		board=new Board();
		
		board.addToField(new Industry("Windmill","Windmill",2,3), false);
		board.addToField(new Industry("Solar Panel","Solar Panel",3,4), false);
		board.addToField(new Industry("Solar Farm","Solar Fame",4,5), false);
	}

	@Test
	void testCorrectDoEffect() {
		board=destroy.doEffect(board, board.getCard(0), 2);
		assertEquals(2,board.getBoardSize());
	}
}
