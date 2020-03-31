package com.xdays.game.assets;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.xdays.game.Game;

public class Button {
	
	private int width;
	private int height;
	
	private int posX;
	private int posY;
	
	private Texture texture;
	private Texture altTexture;
	
	private boolean hover;
	
	public Button(int width, int height, int posX, int posY, String textureLocation) {
		this.width = width;
		this.height = height;
		
		this.posX = posX;
		this.posY = posY;
		
		texture = (Texture) Game.assetManager.get(textureLocation);
		altTexture = null;
		hover = false;
	}
	
	public Button(int width, int height, int posX, int posY, String textureLocation, String altTextureLoc) {
		this(width, height, posX, posY, textureLocation);
		
		altTexture = (Texture) Game.assetManager.get(altTextureLoc);
	}
	
	public void draw(SpriteBatch sb) {
		if(!hover) {
			sb.draw(texture, posX, posY, width, height);
		} else {
			sb.draw(altTexture, posX, posY, width, height);
		}
	}
	
	public void dispose() {
		texture.dispose();
	}
	
	public boolean isPointerOver(int pX, int pY) {
		return(pX < posX + width && pX > posX && Game.HEIGHT - pY < posY + height && Game.HEIGHT - pY > posY);
	}
	
	public void hovering() {
		hover = true;
	}
	
	public void notHovering() {
		hover = false;
	}
}
