package com.xdays.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.xdays.game.User;
import com.xdays.game.cards.CardCollection;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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
    
//    public void saveGame() {
//    	try {
//            FileWriter fileWriter = new FileWriter("saveFile.txt");
//            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
//            bufferedWriter.write(getLevelsWon());
//            bufferedWriter.close();
//        }
//        catch(IOException ex) {
//        	ex.printStackTrace();
//        }
//    }
//    
//    public void loadGame() {
//
//        String line = null;
//        String levelValue=null;
//
//        try {
//            FileReader fileReader = new FileReader("saveFile.txt");
//
//            // Always wrap FileReader in BufferedReader.
//            BufferedReader bufferedReader = new BufferedReader(fileReader);
//
//            while((line = bufferedReader.readLine()) != null) {
//                levelValue=line;
//            }   
//
//            // Always close files.
//            bufferedReader.close();         
//        }
//        catch(FileNotFoundException ex) {
//        	ex.printStackTrace();            
//        }
//        catch(IOException ex) {
//            ex.printStackTrace();
//        }
//        levelsWon=Integer.valueOf(levelValue);
//    }
    
}
