package com.xdays.game.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.xdays.game.User;
import com.xdays.game.cards.CardCollection;

import java.util.Stack;

public class GameStateManager {

    private Stack<State> states;
    private User user;
    private CardCollection collection;

    public GameStateManager(){
        states = new Stack<State>();
        collection = new CardCollection();
        user = new User("User", collection);
    }

    public void push(State state){
        states.push(state);
    }

    public void pop(){
        states.pop().dispose();
    }

    public void set(State state){
        states.pop().dispose();
        states.push(state);
    }

    public void update(float dt){
        states.peek().update(dt);
    }

    public void render(SpriteBatch sb){
        states.peek().render(sb);
    }
    
    public User getUser() {
    	return user;
    }
    
    public CardCollection getCollection() {
    	return collection;
    }
}
