package com.xdays.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.xdays.game.User;
import com.xdays.game.cards.CardCollection;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.Stack;

public class GameStateManager {
	
	private EnumMap<StateEnum, State> stateMap; 
    
    private StateEnum currentState;
    private StateEnum previousState;
    private User user;
    private CardCollection collection;
    private int levelsWon;
    
    public GameStateManager(){        
        currentState = StateEnum.LOADING_STATE;
        previousState = null;
        
        stateMap = new EnumMap<>(StateEnum.class);
		stateMap.put(StateEnum.LOADING_STATE, new LoadingState(this));
        collection = new CardCollection();
        
        user = new User("User", collection);
        levelsWon=0;
    }
    
    public void createStates() {
    	stateMap.put(StateEnum.MENU_STATE, new MenuState(GameStateManager.this));
    	stateMap.put(StateEnum.MAP_STATE, new MapState(GameStateManager.this));
    	stateMap.put(StateEnum.COLLECTION_STATE, new CollectionState(GameStateManager.this, collection, user));
    	stateMap.put(StateEnum.PAUSE_STATE, new PauseState(GameStateManager.this));
    	stateMap.put(StateEnum.TUTORIAL_STATE, new TutorialState(GameStateManager.this));
    	currentState = StateEnum.MENU_STATE;
    }
    
    public State setState(StateEnum state) {
    	if(stateMap.containsKey(state)) {
    		previousState = currentState;
        	currentState = state;
        	return stateMap.get(state);
    	}else {
    		return null;
    	}
    }
    
    public void setStateAsNew(State state, StateEnum stateEnum) {
    	previousState = currentState;
    	stateMap.remove(stateEnum);
    	stateMap.put(stateEnum, state);
    	currentState = stateEnum;
    }
    
    private int getLevelsWon() {
    	return levelsWon;
    }
    
    public void wonLevel() {
    	levelsWon++;
    }
    
    public void back() {
    	setState(previousState);
    }
    
    public void removeState(StateEnum state) {
    	stateMap.remove(state);
    	if (state == StateEnum.PLAY_STATE) {
    		user.resetHand();
    	}
    }
    
    public State getState(StateEnum state) {
    	return stateMap.get(state);
    }

    public void update(float dt){
    	stateMap.get(currentState).update(dt);
    }

    public void render(SpriteBatch sb){
    	stateMap.get(currentState).render(sb);
    }
    
    public User getUser() {
    	return user;
    }
    
    public CardCollection getCollection() {
    	return collection;
    }
    
    public EnumMap<StateEnum, State> getStateMap() {
    	return stateMap;
    }

    public void saveGame() {
    	String[] toWrite = new String[9];
    	String[] completedLevels = ((MapState)getState(StateEnum.MAP_STATE)).getCompletedLevelsName();
    	toWrite[0]=Arrays.toString(completedLevels);
    	//System.out.println(Arrays.toString(completedLevels));
    	/*String[] deck = user.getCurrentDeck();
    	toWrite[1]=Arrays.toString(deck);
    	//System.out.println(Arrays.toString(deck));
    	
    	if(previousState==StateEnum.PLAY_STATE) {
    		String[] playerHand = user.getCurrentHand();
    		toWrite[2]=Arrays.toString(playerHand);
    		//System.out.println(Arrays.toString(playerHand));
    		String[] playerDeck = user.getDeck().deckToString();
    		toWrite[3]=Arrays.toString(playerDeck);
    		//System.out.println(Arrays.toString(playerDeck));
    		String[] playerBoard = ((PlayState)stateMap.get(StateEnum.PLAY_STATE)).getCardGameManager().getPlayedPlayedCards();
    		toWrite[4]=Arrays.toString(playerBoard);
    		//System.out.println(Arrays.toString(playerBoard));
    		
    		String[] enemyHand = ((PlayState)stateMap.get(StateEnum.PLAY_STATE)).getCardGameManager().getAI().getCurrentHand();
    		toWrite[5]=Arrays.toString(enemyHand);
    		//System.out.println(Arrays.toString(enemyHand));
    		String[] enemyDeck = ((PlayState)stateMap.get(StateEnum.PLAY_STATE)).getCardGameManager().getAI().getDeck().deckToString();
    		toWrite[6]=Arrays.toString(enemyDeck);
    		//System.out.println(Arrays.toString(enemyDeck));
    		String[] enemyBoard = ((PlayState)stateMap.get(StateEnum.PLAY_STATE)).getCardGameManager().getEnemyPlayedCards();
    		toWrite[7]=Arrays.toString(enemyBoard);
    		//System.out.println(Arrays.toString(enemyBoard));
    		
    		int emissions = ((PlayState)stateMap.get(StateEnum.PLAY_STATE)).getCardGameManager().getEmissionsBar();
    		toWrite[8]=Integer.toString(emissions);
    		//System.out.println(emissions);
    	}*/
    	writeToSaveFile(toWrite);
    	System.out.println("Game saved.");
    }
    
	private void writeToSaveFile(String[] toWrite) {
		File file = new File("Saves/game.save"); 
		FileWriter fr = null;
		try {
			fr = new FileWriter(file, false);
			for(int i=0; i<toWrite.length; i++) {
				if(i==0) {
					fr.write(toWrite[i]);
				}else {
					fr.write("\n"+toWrite[i]);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Failed to write to save file.");
		}finally {
			try {
				fr.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private String[] readFromSaveFile(int line) {
		File file = new File("Saves/game.save"); 
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e1) {
			System.out.println("Save file doesn't exist.");
		} 
		
		String st; 
		try {
			int i=1;
			while ((st = br.readLine()) != null) {
				if(i==line) {
					return st.replace("[", "").replace("]", "").replaceAll(" ", "").split(",");
				}
				i++;
			}
		} catch (IOException | NullPointerException e) {
			System.out.println("Failed to read save file.");
		} 
		
		try {
			br.close();
		} catch (IOException e) {
		}
		return null;
	}
 
	public void loadGame() {
		String[] completedLevels = readFromSaveFile(1);
		System.out.println(Arrays.toString(completedLevels));
		if(!completedLevels[0].contentEquals("null")) {
			((MapState)getState(StateEnum.MAP_STATE)).loadLevels(readFromSaveFile(1));
		}
		/*user.setCurrentDeck(readFromSaveFile(2));
		
		String[] playerHand = readFromSaveFile(3);
		if(!playerHand[0].contentEquals("null")) {
			user.setHand(collection.getMultipleCards(playerHand));
		}
		
		String[] playerDeck = readFromSaveFile(4);
		if(!playerDeck[0].contentEquals("null")) {
			user.setDeck(playerDeck);
		}
		
		String[] playerBoardString = readFromSaveFile(5);
		Board playerBoard;
		if(!playerBoardString[0].contentEquals("null")) {
			playerBoard = new Board();
			for(int i=0; i<playerBoardString.length; i++) {
				playerBoard.addToField(collection.getCard(playerBoardString[i]), true);
			}
		}
		
		String[] aiHand = readFromSaveFile(6);
		AI ai;
		if(!aiHand[0].contentEquals("null")) {
			String[] aiDeck = readFromSaveFile(7);
			ai = new AI("Enemy", 1, new Deck(collection.getMultipleCards(aiDeck)));
			ai.setHand(collection.getMultipleCards(aiHand));
		}*/
		
	}
    
}