package com.xdays.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.xdays.game.Game;

public class MapState extends State{
	   private Texture background;
	   private Texture marker;
	   private Texture hoverMarker;
	   private Rectangle markerBounds;
	   
	public MapState(GameStateManager gsm) {
		super(gsm);
        cam.setToOrtho(false, Game.WIDTH, Game.HEIGHT);
        background = new Texture("Area Selection.png");
        marker = new Texture("Marker.png");
        hoverMarker = new Texture("Marker Hover.png");
        markerBounds = new Rectangle((cam.position.x - 335), (cam.position.y - 120), marker.getWidth()/3, marker.getHeight()/3);
	}

	@Override
	protected void handleInput() {
		 if(Gdx.input.justTouched()) {
			 Rectangle bounds = new Rectangle(Gdx.input.getX(), -(Gdx.input.getY()-720), 5, 5);
			 if(bounds.overlaps(markerBounds)) {
				 gsm.set(new PlayState(gsm));
			 }
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
        Rectangle bounds = new Rectangle(Gdx.input.getX(), -(Gdx.input.getY()-720), 5, 5);
        if(bounds.overlaps(markerBounds)) {
        	sb.draw(hoverMarker, (cam.position.x - 335), (cam.position.y - 120), hoverMarker.getWidth()/3, hoverMarker.getHeight()/3);
        	//System.out.println("True");
        }else {
        	sb.draw(marker, (cam.position.x - 335), (cam.position.y - 120), marker.getWidth()/3, marker.getHeight()/3);
        }
        sb.end();
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
