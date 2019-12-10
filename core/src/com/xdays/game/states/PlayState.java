package com.xdays.game.states;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.xdays.game.AI;
import com.xdays.game.CardGameManager;
import com.xdays.game.Deck;
import com.xdays.game.Game;
import com.xdays.game.User;
import com.xdays.game.cards.Card;
import com.xdays.game.cards.CardReader;
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
		CardReader cardReader = new CardReader();
		background = new Texture("background.png");
		selectedCards = new ArrayList<Card>();
		messageToPrint = "";
		cam.setToOrtho(false, Game.WIDTH, Game.HEIGHT);
		
		Deck goodDeck = new Deck(cardReader.getInudstryAndSocialCards());
		Deck badDeck = new Deck(cardReader.getInudstryAndSocialCardsBad());
		
		manager = new CardGameManager(50, new User("Friendly", goodDeck), new AI("Enemy", 1, badDeck));
	}

	
	@Override
	protected void handleInput() {
        if(Gdx.input.justTouched() && manager.isPlayersTurn()) {
        	//System.out.println("Play State touched");
        	Rectangle bounds = new Rectangle(Gdx.input.getX(), -(Gdx.input.getY()-720), 5, 5);
        	if(multipleCardsNeeded) {
        		for(int i=0; i<manager.getPlayerBoard().getField().size(); i++) {
        			if(manager.getPlayerBoard().getField().get(i).getBounds().overlaps(bounds)) {
        				Card selectedCard = manager.getPlayerBoard().getField().get(i);
        				if(calculateTotalStars(selectedCards)<lastCardPlayed.getStars()) {
    		        		if(selectedCard.getBounds().overlaps(bounds) && !selectedCard.equals(lastCardPlayed) && selectedCard.isPlayed() && !selectedCards.contains(selectedCard)) {
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
        	}else if(selectedCardsNeeded){
        		for(int i=0; i<manager.getPlayerBoard().getField().size(); i++) {
        			if(manager.getPlayerBoard().getField().get(i).getBounds().overlaps(bounds) || manager.getAIBoard().getField().get(i).getBounds().overlaps(bounds)) {
        				Card selectedCard = manager.getPlayerBoard().getField().get(i);
		        		if(selectedCard.getBounds().overlaps(bounds) && !selectedCard.equals(lastCardPlayed) && selectedCard.isPlayed() && !selectedCards.contains(selectedCard)) {
		        			selectedCards.add(selectedCard);
		        			messageToPrint ="Selected card for apply social card to: " + selectedCard.getTitle();
		        			System.out.println("Selected card for apply social card to: " + selectedCard.getTitle());
		        			manager.playCardGameRound(lastCardPlayed, selectedCards);
        					selectedCards.clear();
        					multipleCardsNeeded = false;
		        		}
        			}
        		}
        	}else {
            	for(int i=0; i<manager.getUser().getHand().size(); i++) {
            		if(manager.getUser().getHand().get(i).getBounds().overlaps(bounds)) {
            			System.out.println(manager.getUser().getHand().get(i).getTitle());
            			Card selectedCard = manager.getUser().getHand().get(i);
            			if(selectedCard instanceof Social) {
            				if(((Social) selectedCard).isSelectedCardNeeded()) {
            					messageToPrint = "Select cards to apply the social card to";
            					System.out.println("Select cards to apply the social card to");
            					selectedCardsNeeded = true;
            					lastCardPlayed = selectedCard;
            					lastCardPlayed.halfPlayed();
            				}else {
            					
            				}
            			}else {
                			if(selectedCard.getStars()>1 && !selectedCard.isPlayed()) {
                				if(manager.getPlayerBoard().getTotalStars(manager.getPlayerBoard().getField())<selectedCard.getStars()) {
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
		emissions.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		emissions.getData().setScale(1);
		emissions.setColor(Color.ORANGE);
		console.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		console.getData().setScale(1);
		console.setColor(Color.ORANGE);
		
		Gdx.gl.glClearColor(135/255f, 206/255f, 235/255f, 1);
		
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(background, 0,0);
        
        for(int i=0; i<manager.getUser().getHand().size(); i++) {
        	if(firstRun) {
            	manager.getUser().getHand().get(i).setPosition(5 + i*127, 0);
        	}else if(!manager.getUser().getHand().get(i).haveBoundsBeenSet() && firstRun == false) {
        		//manager.getUser().getHand().get(i).setPosition(previousBounds.getX(), previousBounds.getY());
        		manager.getUser().getHand().get(i).setPosition(5 + i*127, 0);
        	}
            sb.draw(manager.getUser().getHand().get(i).getTexture(), manager.getUser().getHand().get(i).getBounds().getX(), manager.getUser().getHand().get(i).getBounds().getY(), manager.getUser().getHand().get(i).getTexture().getWidth()/3.4f, manager.getUser().getHand().get(i).getTexture().getHeight()/3.4f);      
        }
        for(int i=0; i<manager.getPlayerBoard().getField().size(); i++) {
            sb.draw(manager.getPlayerBoard().getField().get(i).getTexture(), manager.getPlayerBoard().getField().get(i).getBounds().getX(), manager.getPlayerBoard().getField().get(i).getBounds().getY(), manager.getPlayerBoard().getField().get(i).getTexture().getWidth()/3.4f, manager.getPlayerBoard().getField().get(i).getTexture().getHeight()/3.4f);      
        }
        
        for(int i=0; i<manager.getAI().getHand().size(); i++) {
        	if(!manager.getAI().getHand().get(i).haveBoundsBeenSet()) {
        		manager.getAI().getHand().get(i).setPosition(5 + i*127, 650);
        	}
            sb.draw(manager.getAI().getHand().get(i).getBackTexture(), manager.getAI().getHand().get(i).getBounds().getX(), manager.getAI().getHand().get(i).getBounds().getY(), manager.getAI().getHand().get(i).getTexture().getWidth()/3.4f, manager.getAI().getHand().get(i).getTexture().getHeight()/3.4f);
        }
        for(int i=0; i<manager.getAIBoard().getField().size(); i++) {
            sb.draw(manager.getAIBoard().getField().get(i).getTexture(), manager.getAIBoard().getField().get(i).getBounds().getX(), manager.getAIBoard().getField().get(i).getBounds().getY(), manager.getAIBoard().getField().get(i).getTexture().getWidth()/3.4f, manager.getAIBoard().getField().get(i).getTexture().getHeight()/3.4f);      
        }
        
        console.draw(sb, messageToPrint, 600, 405);
        emissions.draw(sb, "Emissions: " + Integer.toString(manager.getEmissionsBar()), 600, 380);
        
        firstRun = false;
        sb.end();
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
	}

}
