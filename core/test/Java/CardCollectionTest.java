package Java;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.xdays.game.cards.Card;
import com.xdays.game.cards.CardCollection;

class CardCollectionTest {
	
	private CardCollection cardCollection;

	@BeforeEach
	void setUp() throws Exception {
		cardCollection=new CardCollection();
	}

	@Test
	void testGetCard() {
		Card testCard=cardCollection.getCard("Windmill");
		assertEquals(testCard.getTitle(),"Windmill");
	}

	@Test
	void testGetMultipleCards() {
		String[] testCards= {"Windmill","Plant Tree","Solar Panel","Solar Farm"};
		ArrayList<Card> cards=cardCollection.getMultipleCards(testCards);
		Boolean check=false;
		
		for(int i=0;i<cards.size();i++) {
			
			if(cards.get(i).getTitle().equals(testCards[i])) {
				check=true;
			}
			else {
				check=false;
				assertTrue(check);
			}
		}
		assertTrue(check);
	}

	@Test
	void testGetAllCards() {
		assertNotNull(cardCollection.getAllCards());
	}

}
