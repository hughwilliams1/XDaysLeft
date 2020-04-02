package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.xdays.game.cards.Industry;

class IndustryTest {
	
	private Industry industry=new Industry("Windmill","Windmill",2,3);

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void testEditBuff() {
		int postitveBuff=2;
		int negativeBuff=-4;
		
		industry.editBuff(postitveBuff);
		assertEquals(2,industry.getBuff());
		
		industry.editBuff(negativeBuff);
		assertEquals(-2,industry.getBuff());
	}

	@Test
	void testEditStars() {
		int postitveStar=2;
		int negativeStar=-4;
		
		industry.editStars(postitveStar);
		assertEquals(2,industry.getStarBuff());
		
		industry.editStars(negativeStar);
		assertEquals(-2,industry.getStarBuff());
	}

}
