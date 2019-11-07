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
	
	public ArrayList<Card> nextCard(Board enemyBoard) {
		ArrayList<Card> cardsToUse = cardsAvailableToPlay(enemyBoard.getField(), getHand());
		ArrayList<Card> cardsToProcess = new ArrayList<Card>();
		if(random.nextInt(100) < 20*(5 - level)) {
			//This means the AI is not selecting the best option.
			//This is to make it easier for the user
			 cardsToProcess.add(cardsToUse.get(random.nextInt(cardsToUse.size() - 1)));
			 cardsToProcess.addAll(cardsToMerge(cardsToProcess.get(0), getHand()));
		} else {
			//This means the AI is selecting the best option.
			cardsToProcess.add(getHighestStarCard(cardsToUse));
			cardsToProcess.addAll(cardsToMerge(cardsToProcess.get(0), getHand()));
		}
		return cardsToProcess;
	}
	
	private ArrayList<Card> cardsToMerge(Card card, ArrayList<Card> cardsInHand){
		ArrayList<Card> cardsToReturn = new ArrayList<Card>();
		int cardStarValue = card.getStars();
		int totalStars = 0;
		int iteration = 1;
		switch(cardStarValue) {
			case 1:
				break;
			case 2:
				for(Card currentCard: cardsInHand) {
					if(currentCard.getStars() == 1) {
						cardsToReturn.add(currentCard);
						totalStars = currentCard.getStars();
					} 
					if(totalStars == cardStarValue) {
						break;
					}
				}
				break; // need two one cards
			case 3:
				boolean oneStarCard = false;
				boolean twoStarCard = false;
				while(totalStars != cardStarValue) {
					for(Card currentCard: cardsInHand) {
						if(iteration < 1) {
							if(currentCard.getStars() == 1) {
								cardsToReturn.add(currentCard);
								totalStars = currentCard.getStars();
							}
						} else {
							if(currentCard.getStars() == 1 && !oneStarCard) {
								cardsToReturn.add(currentCard);
								totalStars = currentCard.getStars();
								oneStarCard = !oneStarCard;
							} else if(currentCard.getStars() == 2 && !twoStarCard) {
								cardsToReturn.add(currentCard);
								totalStars = currentCard.getStars();
								twoStarCard = !oneStarCard;
							}
						}
						if(totalStars == cardStarValue) {
							break;
						}
					}
					iteration++;
				}
				break; // need either three one cards or one one card and a two
			case 4:
				break; // need either 4 one cards or 2 two cards  or 2 of each
		}
		
			
		
		return cardsToReturn;
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

	private ArrayList<Card> cardsAvailableToPlay(ArrayList<Card> cardsOnBoard, ArrayList<Card> cardsInHand){
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
