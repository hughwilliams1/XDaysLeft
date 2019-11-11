package com.xdays.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.xdays.game.Game;

/**
 * Created by Brent on 6/26/2015.
 */
public class MenuState extends State{
    private Texture background;
    private Texture playBtn;
    
    public MenuState(GameStateManager gsm) {
        super(gsm);
        cam.setToOrtho(false, Game.WIDTH, Game.HEIGHT);
        background = new Texture("title.png");
        playBtn = new Texture("playButton.png");
    }

    @Override
    public void handleInput() {
        if(Gdx.input.justTouched()){
        	System.out.println("Play button pressed");
            gsm.set(new PlayState(gsm));
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(background, 0,0);
        sb.draw(playBtn, (cam.position.x - playBtn.getWidth() / 2), (cam.position.y - playBtn.getHeight() / 2) - 150);
        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        playBtn.dispose();
        System.out.println("Menu State Disposed");
    }
}
