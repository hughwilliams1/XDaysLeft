package com.xdays.game;

import java.util.ArrayList;
import java.util.Random;

import com.xdays.game.cards.Card;
import com.xdays.game.cards.Social;

public class AI extends Player {
	private Random random;
	private int level;
	private Deck deck;
	private ArrayList<Card> playerBoard;

	public AI(String name, int level, Deck deck) {
		super(name, deck);
		this.deck = deck;
		random = new Random();
		this.level = level;

	}

	public ArrayList<Card> nextCard(Board enemyBoard, Board playerBoard) {
		ArrayList<Card> cardsToUse = cardsAvailableToPlay(enemyBoard.getField(), this.hand, playerBoard.getField());
		ArrayList<Card> cardsToProcess = new ArrayList<Card>();
		this.playerBoard = playerBoard.getField();
		if(cardsToUse.size() == 0) {
			return null;
		}
		if(random.nextInt(100) < 20*(4 - level)) {
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

	// TODO merging doesn't work ai just plays whatever
	private ArrayList<Card> cardsToMerge(Card card, ArrayList<Card> cardsOnBoard){
		ArrayList<Card> cardsToReturn = new ArrayList<Card>();
		int cardStarValue = card.getStars();
		int totalStars = 0;
		int iteration = 0;
		if(card.isIndustry()) {
			switch(cardStarValue) {
			case 1:
				break;
			case 2:
				for(Card currentCard: cardsOnBoard) {
					if(currentCard.getStars() == 1) {
						cardsToReturn.add(currentCard);
						totalStars += currentCard.getStars();
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
		} else {
			Social sCard = (Social) card;
			if(sCard.isSelectedCardNeeded()) {
				switch(sCard.getTitle()) {
				case "Fake news":
				case "Manipulation":
					cardsToReturn.add(getHighestStarCard(playerBoard));
					break;
				case "Corruption":
					for(Card currentCard: this.hand) {
						if(currentCard.getStars() == 2) {
							cardsToReturn.add(currentCard);
							break;
						}
						
					}
					break;
				case "Increase Population":
					for(Card currentCard: this.hand) {
						if(currentCard.getStars() == 3) {
							cardsToReturn.add(currentCard);
							break;
						}
					}
					break;
				}
			}
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

	private ArrayList<Card> cardsAvailableToPlay(ArrayList<Card> cardsOnBoard, ArrayList<Card> cardsInHand, ArrayList<Card> playerBoard){
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
				if(!playerBoard.isEmpty() && !cardsOnBoard.isEmpty()) {
					switch(card.getTitle()) {
					case "Propaganda":
						if(hasStarCard(playerBoard, 2)) {
							acceptableCardsToPlay.add(card);
						}
						break;
					case "Bribe":
						if(hasStarCard(playerBoard, 3)) {
							acceptableCardsToPlay.add(card);
						}
						break;
					default:
						acceptableCardsToPlay.add(card);
						break;
					}
					
				}
			}
		}
		return acceptableCardsToPlay;
	}
	private boolean hasStarCard(ArrayList<Card> board, int starValue) {
		for(Card card: board) {
			if(card.getStars() == starValue) {
				return true;
			}
		}
		return false;
	}
}

