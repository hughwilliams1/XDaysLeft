package com.xdays.game.assets;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.xdays.game.Game;

/**  
 * Button.java - Creates a bounded button from a texture file
 *
 * @author  Hugh Williams
 * @version 1.0 
 */
public class Button {
	
	private int width;
	private int height;
	
	private int posX;
	private int posY;
	
	private Texture texture;
	private Texture altTexture;
	
	private boolean hover;
	
	/**
	 * Constructor for button
	 * 
	 * @param width int width of the button
	 * @param height int height of the button
	 * @param posX int X position of the button
	 * @param posY int Y position of the button
	 * @param textureLocation String location of the texture for the button
	 */
	public Button(int width, int height, int posX, int posY, String textureLocation) {
		this.width = width;
		this.height = height;
		
		this.posX = posX;
		this.posY = posY;
		
		texture = (Texture) Game.assetManager.get(textureLocation);
		altTexture = null;
		hover = false;
	}
	
	/**
	 * Constructor for button with an alt texture
	 * 
	 * @param width int width of the button
	 * @param height int height of the button
	 * @param posX int X position of the button
	 * @param posY int Y position of the button
	 * @param textureLocation String location of the texture for the button
	 * @param altTextureLoc String location of the alt texture for the button
	 */
	public Button(int width, int height, int posX, int posY, String textureLocation, String altTextureLoc) {
		this(width, height, posX, posY, textureLocation);
		
		altTexture = (Texture) Game.assetManager.get(altTextureLoc);
	}
	
	/**
	 * Draws the button a texture depending on if it's being hovered over
	 * 
	 * @param sb The Batch to draw the Button onto
	 */
	public void draw(SpriteBatch sb) {
		if(!hover) {
			sb.draw(texture, posX, posY, width, height);
		} else {
			sb.draw(altTexture, posX, posY, width, height);
		}
	}
	
	/**
	 * Disposes the button
	 */
	public void dispose() {
		texture.dispose();
	}
	
	/**
	 * Checks if the passed position is on the button
	 * 
	 * @param pX Position of the X
	 * @param pY Position of the Y
	 * @return boolean true if the coord's are over the button
	 */
	public boolean isPointerOver(int pX, int pY) {
		return(pX < posX + width && pX > posX && Game.HEIGHT - pY < posY + height && Game.HEIGHT - pY > posY);
	}
	
	/**
	 * Sets the button to hovering state. Displaying the alt texture
	 */
	public void hovering() {
		hover = true;
	}
	
	/**
	 * Sets the button to not hovering state. Displaying the normal texture
	 */
	public void notHovering() {
		hover = false;
	}
}
