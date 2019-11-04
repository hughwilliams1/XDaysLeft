package com.xdays.game;

import java.util.ArrayList;
import java.util.Random;

import com.xdays.game.cards.Card;

public class AI extends Player {
	private Random random;
	private int level;
	
	public AI(String name, int level) {
		super(name);
		random = new Random();
		this.level = level;
	}
	
	public Card nextCard(Board enemyBoard) {
		ArrayList<Card> cardsToUse = cardsAvailableToPlay(enemyBoard.getField(), getHand());
		
		if(random.nextInt(100) < 20*(5 - level)) {
			//This means the AI is not selecting the best option.
			//This is to make it easier for the user
			return cardsToUse.get(random.nextInt(cardsToUse.size() - 1));
		} else {
			//This means the AI is selecting the best option.
			return getHighestStarCard(cardsToUse);
		}
	}
	
	private Card getHighestStarCard(ArrayList<Card> cardsToUse) {
		Card currentHighest = cardsToUse.get(0);
		for(Card card: cardsToUse) {
			if(currentHighest.getStars() < card.getStars()) {
				currentHighest = card;
			}
		}
		return currentHighest;
				
	}

	private ArrayList<Card> cardsAvailableToPlay(ArrayList<Card> cardsOnBoard, Card[] cardsInHand){
		int oneStarCards = 0;
		int twoStarCards = 0;
		int threeStarCards = 0;
		ArrayList<Card> acceptableCardsToPlay = new ArrayList<Card>();
		for(Card card:cardsOnBoard) {
			if(card.isIndustry()) {
				switch(card.getStars()) {
				case(1):
					oneStarCards++;
					break;
				case(2):
					twoStarCards++;
					break;
				case(3):
					threeStarCards++;
					break;
				case(4):
					break;
				}
			}
		}
		
		for(Card card: cardsInHand) {
			switch(card.getStars()) {
			case(1):
				acceptableCardsToPlay.add(card);
				break;
			case(2):
				if(oneStarCards >= 2) {
					acceptableCardsToPlay.add(card);
				}
				break;
			case(3):
				if(oneStarCards >= 3 || (twoStarCards >=1 && oneStarCards >= 1)) {
					acceptableCardsToPlay.add(card);
				}
				break;
			case(4):
				if(oneStarCards >= 4 || (threeStarCards >=1 && oneStarCards >= 1) || twoStarCards >= 2 || (twoStarCards >= 1 && oneStarCards >= 2)) {
					acceptableCardsToPlay.add(card);
				}
				break;
			}
		}
		return acceptableCardsToPlay;
	}
}
