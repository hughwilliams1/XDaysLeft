package com.xdays.game.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.xdays.game.User;
import com.xdays.game.cards.CardCollection;

import java.util.EnumMap;
import java.util.Stack;

public class GameStateManager {
	
	private EnumMap<StateEnum, State> stateMap; 
    
    private StateEnum currentState;
    private StateEnum previousState;
    private User user;
    private CardCollection collection;

    public GameStateManager(){        
        currentState = StateEnum.MENU_STATE;
        previousState = null;
        stateMap = new EnumMap<>(StateEnum.class);
        collection = new CardCollection();
        user = new User("User", collection);
        CreateStates();
    }
    
    private void CreateStates() {
    	stateMap.put(StateEnum.MENU_STATE, new MenuState(this));
    	stateMap.put(StateEnum.MAP_STATE, new MapState(this));
    	stateMap.put(StateEnum.COLLECTION_STATE, new CollectionState(this, new CardCollection(), new User("Player 1", new CardCollection())));
    }
    
    public void setState(StateEnum state) {
    	if(stateMap.containsKey(state)) {
    		previousState = currentState;
        	currentState = state;
    	}
    }
    
    public void setStateAsNew(State state, StateEnum stateEnum) {
    	previousState = currentState;
    	stateMap.remove(stateEnum);
    	stateMap.put(stateEnum, state);
    	currentState = stateEnum;
    }
    
    public void back() {
    	setState(previousState);
    }
    
    public void removeState(StateEnum state) {
    	stateMap.remove(state);
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

}
