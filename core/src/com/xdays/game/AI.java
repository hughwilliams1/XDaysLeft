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
		if(cardsToUse.size() == 0) {
			return null;
		}
		if(random.nextInt(100) < 20*(5 - level)) {
			//This means the AI is not selecting the best option.
			//This is to make it easier for the user
			cardsToProcess.add(cardsToUse.get(random.nextInt(cardsToUse.size())));
			cardsToProcess.addAll(cardsToMerge(cardsToProcess.get(0), enemyBoard.getField()));
		} else {
			//This means the AI is selecting the best option.
			cardsToProcess.add(getHighestStarCard(cardsToUse));
			cardsToProcess.addAll(cardsToMerge(cardsToProcess.get(0), enemyBoard.getField()));
		}
		return cardsToProcess;
	}

	private ArrayList<Card> cardsToMerge(Card card, ArrayList<Card> cardsOnBoard){
		
		ArrayList<Card> cardsToReturn = new ArrayList<Card>();
		
		if(!card.isIndustry()) {
			switch(card.getTitle()) {
			case "Online post":
			case "Cover up":
				cardsToReturn.add(getHighestStarCard(cardsOnBoard));
				return cardsToReturn;
			case "Increase population":
				for(Card currentCard : cardsOnBoard) {
					if(currentCard.getStars() == 1) {
						cardsToReturn.add(currentCard);
						return cardsToReturn;
					}
				}
				return null;
			case "Corruption":
				for(Card currentCard : cardsOnBoard) {
					if(currentCard.getStars() == 3) {
						cardsToReturn.add(currentCard);
						return cardsToReturn;
					}
				}
				return null;
			}
			return null;
		}
		int cardStarValue = card.getStars();
		int totalStars = 0;
		int iteration = 0;
		
		switch(cardStarValue) {
		case 1:
			break;
		case 2:
			for(Card currentCard: cardsOnBoard) {
				if(currentCard.getStars() == 1) {
					cardsToReturn.add(currentCard);
					totalStars = currentCard.getStars();
				} 
				if(totalStars == cardStarValue)	break;

			}
			break; // need two one cards
		case 3: {
			boolean[] starCardsFound = {false, false};
			ArrayList<Card> tempCardList = new ArrayList<Card>();
			while(totalStars != cardStarValue) {
				for(Card currentCard: cardsOnBoard) {
					if(iteration < 1) {
						if(currentCard.getStars() == 1) {
							tempCardList.add(currentCard);
							totalStars += currentCard.getStars();
						}
					} else {
						if(currentCard.getStars() == 1 && !starCardsFound[0]) {
							tempCardList.add(currentCard);
							totalStars += currentCard.getStars();
							starCardsFound[0] = true;
						} else if(currentCard.getStars() == 2 && !starCardsFound[1]) {
							tempCardList.add(currentCard);
							totalStars += currentCard.getStars();
							starCardsFound[1] = true;
						}
					}
					if(totalStars == cardStarValue) break;

				}
				iteration++;
				if(totalStars == cardStarValue) {
					cardsToReturn.addAll(tempCardList);
				} else {
					tempCardList = new ArrayList<Card>();
					totalStars = 0;
				}
			}
			break; } // need either three one cards or one one card and a two
		case 4: {
			boolean[] starCardsFound = {false, false, false};
			ArrayList<Card> tempCardList = new ArrayList<Card>();
			while(totalStars != cardStarValue) {
				for(Card currentCard: cardsOnBoard) {
					if(iteration < 1) {
						if(currentCard.getStars() == 1) {
							tempCardList.add(currentCard);
							totalStars += currentCard.getStars();
						}
					} else if(iteration == 1) {
						if(currentCard.getStars() == 2 && !starCardsFound[0]) {
							tempCardList.add(currentCard);
							totalStars += currentCard.getStars();
							starCardsFound[0] = true;
						} else if(currentCard.getStars() == 1 && (!starCardsFound[1] || !starCardsFound[2] )) {
							tempCardList.add(currentCard);
							totalStars += currentCard.getStars();
							if(!starCardsFound[1]) {
								starCardsFound[1] = true;
							} else if (!starCardsFound[2]){
								starCardsFound[2] = true;
							}
						}
					} else if(iteration == 2){
						if(currentCard.getStars() == 2 && (!starCardsFound[0] || !starCardsFound[1])) {
							tempCardList.add(currentCard);
							totalStars += currentCard.getStars();
							starCardsFound[0] = !starCardsFound[0];
							if(!starCardsFound[1]) {
								starCardsFound[1] = true;
							} else {
								starCardsFound[0] = true;
							}
						}
					} else {
						if(currentCard.getStars() == 3 && !starCardsFound[0]) {
							tempCardList.add(currentCard);
							totalStars += currentCard.getStars();
							starCardsFound[0] = true;
						} else if(currentCard.getStars() == 1 && !starCardsFound[1]) {
							tempCardList.add(currentCard);
							totalStars += currentCard.getStars();
							starCardsFound[1] = true;
						}
					}
					if(totalStars == cardStarValue) break;
				}
				if(totalStars == cardStarValue) {
					cardsToReturn.addAll(tempCardList);
				} else {
					tempCardList = new ArrayList<Card>();
					totalStars = 0;
					starCardsFound[0] = false;
					starCardsFound[1] = false;
					starCardsFound[2] = false;
				}
				iteration++;
			}

			break; } // need either 4 one cards or (one 2 card and 2 one cards )or 2 two cards  or (1 one card and a 3 card)
		}
		return cardsToReturn;
	}

	private Card getHighestStarCard(ArrayList<Card> cardsToUse) {
		if(cardsToUse != null) {
			
			Card currentHighest = cardsToUse.get(0);
			for(Card card: cardsToUse) {
				if(card.isIndustry()) {
					if(currentHighest.getStars() < card.getStars()) {
						currentHighest = card;
					}
				}
			}
			return currentHighest;
		}
		return null;

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
			if(card.isPlayed()) {
				continue;
			}
			if(card.isIndustry()) {

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
			} else {
				acceptableCardsToPlay.add(card);
			}

		}
		return acceptableCardsToPlay;
	}
}
