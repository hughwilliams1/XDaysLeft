package com.xdays.game.states;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.xdays.game.Board;
import com.xdays.game.CardGameManager;
import com.xdays.game.Game;
import com.xdays.game.cards.Card;
import com.xdays.game.cards.Destroy;
import com.xdays.game.cards.Social;

public class PlayState extends State{
	
	private boolean multipleCardsNeeded = false;
	private boolean selectedCardsNeeded = false;
	private boolean firstRun = true;
	private CardGameManager manager;
	private Card lastCardPlayed;
	private ArrayList<Card> selectedCards;
	private String messageToPrint;
	private Texture background;
	private Rectangle previousBounds;

	public PlayState(GameStateManager gsm) {
		super(gsm);
		//CardReader cardReader = new CardReader();
		background = new Texture("background.png");
		selectedCards = new ArrayList<Card>();
		messageToPrint = "";
		cam.setToOrtho(false, Game.WIDTH, Game.HEIGHT);
		
		//Deck goodDeck = new Deck(cardReader.getInudstryAndSocialCards());
		//Deck badDeck = new Deck(cardReader.getInudstryAndSocialCardsBad());
		
		manager = new CardGameManager(50);
	}

	@Override
	protected void handleInput() {
        if(Gdx.input.justTouched() && manager.isPlayersTurn()) {
        	//System.out.println("Play State touched");
        	Rectangle bounds = new Rectangle(Gdx.input.getX(), -(Gdx.input.getY()-720), 5, 5);
        	if(multipleCardsNeeded) {
        		for(int i=0; i<getNumCards(); i++) {
        			if(checkCardOverlaps(getPlayerCard(i),(bounds))) {
        				Card selectedCard =getPlayerCard(i);
        				if(compareStar() && compareCards(bounds, selectedCard)) {
    		        			selectedCards.add(selectedCard);
    		        			messageToPrint ="Selected card for merge: " + selectedCard.getTitle();
    		        			System.out.println("Selected card for merge: " + selectedCard.getTitle());
    		        			if(calculateTotalStars(selectedCards)>=lastCardPlayed.getStars()) { 
    	        					manager.playCardGameRound(lastCardPlayed, selectedCards);
    	        					selectedCards.clear();
    	        					multipleCardsNeeded = false;
    		        			}
    		        		}
        				}
        			}
        		}
        	else if(selectedCardsNeeded){
        		if(((Social)lastCardPlayed).getSocialEffect() instanceof Destroy) {
            		for(int i=0; i<getNumAiCards(); i++) {
            			if(getAICard(i).getBounds().overlaps(bounds)) {
            				Card selectedCard = getAICard(i);
    		        		if(isCardValid(bounds, selectedCard)) {
    		        			selectedCards.add(selectedCard);
    		        			messageToPrint ="Selected card for apply social card to: " + selectedCard.getTitle();
    		        			System.out.println("Selected card for apply social card to: " + selectedCard.getTitle());
    		        			manager.playCardGameRound(lastCardPlayed, selectedCards);
            					selectedCards.clear();
            					selectedCardsNeeded = false;
            					render(Game.batch);
    		        		}
            			}
            		}
        		}
        	}else {
            	for(int i=0; i<manager.getUser().getHand().size(); i++) {
            		if(getCardHand(i).getBounds().overlaps(bounds)) {
            			System.out.println(getCardHand(i).getTitle());
            			Card selectedCard = getCardHand(i);
            			if(selectedCard instanceof Social) {  
            				if(!manager.getAIBoard().getField().isEmpty() && !getPlayerBoard().getField().isEmpty()) {
                				if(((Social) selectedCard).isSelectedCardNeeded()) {
                					messageToPrint = "Select cards to apply the social card to";
                					System.out.println("Select cards to apply the social card to");
                					selectedCardsNeeded = true;
                					lastCardPlayed = selectedCard;
                					lastCardPlayed.halfPlayed();
                				}else {
                					((Social) selectedCard).doEffect(getPlayerBoard(), null);
                					messageToPrint = "Social card applied.";
                					System.out.println("Select card applied to: " + ((Social) selectedCard).getSocialEffect().getChosenCard());
                					
                				}
            				}else {
            					messageToPrint = "No cards have been placed on the board yet.";
            					System.out.println("No cards have been placed on the board yet.");
            				}
            			}else {
                			if(selectedCard.getStars()>1 && !selectedCard.isPlayed()) {
                				if(checkValidStars(selectedCard)) {
                					messageToPrint = "Not enough cards to play that card.";
                					System.out.println("Not enough cards to play that card.");
                				}else {
                					messageToPrint = "Select cards to merge";
                					System.out.println("Select cards to merge");
                					multipleCardsNeeded = true;
                					lastCardPlayed = selectedCard;
                					lastCardPlayed.halfPlayed();
                				}
                			}else if(!selectedCard.isPlayed()){
                				previousBounds = selectedCard.getBounds();
                				messageToPrint ="User played card: " + selectedCard.getTitle();
                				System.out.println("User played card: " + selectedCard.getTitle());
                				manager.playCardGameRound(selectedCard, null);
                				render(Game.batch);
                			}
            			}
            		}
            	}
        	}
        }
	}




	
	private int calculateTotalStars(ArrayList<Card> selected) {
		int a = 0;
		if(selected.isEmpty()) {
			return 0;
		}else {
			for(int i=0; i<selected.size(); i++) {
				a += selected.get(i).getStars();
			}
			return a;
		}
	}

	@Override
	public void update(float dt) {
		handleInput();
	}

	@Override
	public void render(SpriteBatch sb) {
		BitmapFont console = new BitmapFont();
		BitmapFont emissions = new BitmapFont();
		setBitmap(console);
		setBitmap(emissions);
		
		Gdx.gl.glClearColor(135/255f, 206/255f, 235/255f, 1);
		
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(background, 0,0);
        
        for(int i=0; i<manager.getUser().getHand().size(); i++) {
        	if(firstRun) {
            	getCardHand(i).setPosition(5 + i*127, 0);
        	}else if(!getCardHand(i).haveBoundsBeenSet() && firstRun == false) {
        		//manager.getUser().getHand().get(i).setPosition(previousBounds.getX(), previousBounds.getY());
        		getCardHand(i).setPosition(5 + i*127, 0);
        	}
            sb.draw(getCardHand(i).getTexture(), getXValue(getCardHand(i)), getYValue(getCardHand(i)), getCardWidth(getCardHand(i)), getCardHeight(getCardHand(i)));      
        }
        for(int i=0; i<getNumCards(); i++) {
            sb.draw(getCurrentCard(i).getTexture(), getXValue(getCurrentCard(i)), getYValue(getCurrentCard(i)), getCardWidth(getCurrentCard(i)), getCardHeight(getCurrentCard(i)));      
        }
        
        for(int i=0; i<getAIHand().size(); i++) {
        	if(!getAIHand().get(i).haveBoundsBeenSet()) {
        		getAIHand().get(i).setPosition(5 + i*127, 650);
        	}
            sb.draw(getAIHand().get(i).getBackTexture(), getXValue(getAIHand().get(i)), getYValue(getAIHand().get(i)), getCardWidth(getAIHand().get(i)), getCardHeight(getAIHand().get(i)));
        }
        for(int i=0; i<getNumAiCards(); i++) {
            sb.draw(getAICard(i).getTexture(), getXValue(getAICard(i)), getYValue(getAICard(i)), getCardWidth(getAICard(i)), getCardHeight(getAICard(i)));      
        }
        
        console.draw(sb, messageToPrint, 600, 405);
        emissions.draw(sb, "Emissions: " + Integer.toString(manager.getEmissionsBar()), 600, 380);
        
        firstRun = false;
        sb.end();
	}
	
	private boolean isCardValid(Rectangle bounds, Card selectedCard) {
		return selectedCard.getBounds().overlaps(bounds) && !selectedCard.equals(lastCardPlayed) && selectedCard.isPlayed() && !selectedCards.contains(selectedCard);
	}
	private boolean checkValidStars(Card selectedCard) {
		return getPlayerBoard().getTotalStars(getPlayerBoard().getField())<selectedCard.getStars();
	}
	private Card getPlayerCard(int i) {
		return getCurrentCard(i);
	}
	private Card getAICard(int i) {
		return manager.getAIBoard().getField().get(i);
	}
	private Boolean checkCardOverlaps(Card card, Rectangle bounds){
		return card.getBounds().overlaps(bounds);
	}
	
	private int getNumCards() {
		return getPlayerBoard().getField().size();
	}
	private int getNumAiCards() {
		return manager.getAIBoard().getField().size();
	}	
	private Card getCardHand(int i) {
		return manager.getUser().getHand().get(i);
	}
	private boolean compareStar() {
		return calculateTotalStars(selectedCards)<lastCardPlayed.getStars();
	}	
	private boolean compareCards(Rectangle bounds, Card selectedCard) {
		return checkCardOverlaps(selectedCard,bounds) && !selectedCard.equals(lastCardPlayed) && selectedCard.isPlayed() && !selectedCards.contains(selectedCard);
	}
	
	private Board getPlayerBoard() {
		return manager.getPlayerBoard();
	}
	
	private Card getCurrentCard(int i) {
		return getPlayerBoard().getField().get(i);
	}

	private float getCardWidth(Card card) {
		return card.getTexture().getWidth()/3.4f;
	}
	
	private float getCardHeight(Card card) {
		return card.getTexture().getHeight()/3.4f;
	}
	
	private float getXValue(Card card) {
		return card.getBounds().getX();
	}
	
	private float getYValue(Card card) {
		return card.getBounds().getY();
	}

	private ArrayList<Card> getAIHand() {
		return manager.getAI().getHand();
	}

	private void setBitmap(BitmapFont bitmap) {
		bitmap.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		bitmap.getData().setScale(1);
		bitmap.setColor(Color.ORANGE);
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
	}

}
