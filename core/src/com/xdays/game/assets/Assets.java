package com.xdays.game.assets;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader.FreeTypeFontLoaderParameter;

/**  
 * Assets.java - Loads and Stores all the assests in the game to be called
 * upon later
 *
 * @author  Hugh Williams
 * @version 1.0 
 * @see AssetManager
 */ 
public class Assets {
		
	private final AssetManager manager = new AssetManager();	
	
	/**
	 * loads all the different asset sections
	 */
	public void load() {
		loadCards();
		loadCutscenes();
		loadButtons();
		loadBackgrounds();
		loadFonts();
	}
	
	/**
	 * Loads the card images
	 */
	public void loadCards() {
		manager.load("Battery Farm.PNG", Texture.class);
		manager.load("Blank.PNG", Texture.class);
		manager.load("back.PNG", Texture.class);
		manager.load("back question.PNG", Texture.class);
		manager.load("Boycott.PNG", Texture.class);
		manager.load("Bribe.PNG", Texture.class);
		manager.load("Car Factory.PNG", Texture.class);
		manager.load("Cattle Farming.PNG", Texture.class);
		manager.load("Coal Power Plant.PNG", Texture.class);
		manager.load("Corruption.PNG", Texture.class);
		manager.load("Cover-up.PNG", Texture.class);
		manager.load("Deforestation.PNG", Texture.class);
		manager.load("Diesel Car.PNG", Texture.class);
		manager.load("Electric Bus.PNG", Texture.class);
		manager.load("Electric Car.PNG", Texture.class);
		manager.load("Fake News.PNG", Texture.class);
		manager.load("Fracking.PNG", Texture.class);
		manager.load("Freight Ships.PNG", Texture.class);
		manager.load("Gas Boiler.PNG", Texture.class);
		manager.load("Geothermal.PNG", Texture.class);
		manager.load("Household Recycle.PNG", Texture.class);
		manager.load("Household Waste.PNG", Texture.class);
		manager.load("Hydroelectric Energy.PNG", Texture.class);
		manager.load("Increase Population.PNG", Texture.class);
		manager.load("Industrial Manufacturing.PNG", Texture.class);
		manager.load("Landfill.PNG", Texture.class);
		manager.load("Manipulation.PNG", Texture.class);
		manager.load("Marketing Scheme.PNG", Texture.class);
		manager.load("Meat Eater.PNG", Texture.class);
		manager.load("News.PNG", Texture.class);
		manager.load("Nuclear Plant.PNG", Texture.class);
		manager.load("Online Posts Bad.PNG", Texture.class);
		manager.load("Online Posts.PNG", Texture.class);
		manager.load("Petition.PNG", Texture.class);
		manager.load("Plant Tree.PNG", Texture.class);
		manager.load("Propaganda.PNG", Texture.class);
		manager.load("Protests.PNG", Texture.class);
		manager.load("Remove Tree.PNG", Texture.class);
		manager.load("Solar Farm.PNG", Texture.class);
		manager.load("Solar Panel.PNG", Texture.class);
		manager.load("Strike.PNG", Texture.class);
		manager.load("Taxi.PNG", Texture.class);
		manager.load("UN Law.PNG", Texture.class);
		manager.load("Vegan.PNG", Texture.class);
		manager.load("Wind Farm.PNG", Texture.class);
		manager.load("Windmill.PNG", Texture.class);	
	}
	
	/**
	 * Loads the cutscenes
	 */
	public void loadCutscenes() {
		manager.load("Merkel 1.PNG", Texture.class);
		manager.load("Merkel 2.PNG", Texture.class);
		manager.load("BlankCut.PNG", Texture.class);
		manager.load("European Parliament Background.PNG", Texture.class);
		manager.load("Kremlin Background.PNG", Texture.class);
		manager.load("LabBackground.PNG", Texture.class);
		manager.load("Putin 1.PNG", Texture.class);
		manager.load("Putin 2.PNG", Texture.class);
		manager.load("scientist.PNG", Texture.class);
		manager.load("Trump 1.PNG", Texture.class);
		manager.load("Trump 2.PNG", Texture.class);
		manager.load("Whitehouse Background.PNG", Texture.class);
			
	}
	
	/**
	 * Loads the buttons
	 */
	public void loadButtons() {
		manager.load("BackBtn.PNG", Texture.class);
		manager.load("DeckBtn.PNG", Texture.class);
		manager.load("HomeBtn.PNG", Texture.class);
		manager.load("HomeSBtn.PNG", Texture.class);
		manager.load("LoadBtn.PNG", Texture.class);
		manager.load("MenuBtn.PNG", Texture.class);
		manager.load("NextBtn.PNG", Texture.class);
		manager.load("PauseSBtn.PNG", Texture.class);
		manager.load("PlayBtn.PNG", Texture.class);
		manager.load("QuitBtn.PNG", Texture.class);
		manager.load("SaveBtn.PNG", Texture.class);
		manager.load("SkipSBtn.PNG", Texture.class);
		manager.load("Marker Hover.PNG", Texture.class);
		manager.load("Marker Not Available.PNG", Texture.class);
		manager.load("Marker.PNG", Texture.class);
		manager.load("Marker Completed.PNG", Texture.class);
		manager.load("RedMark.PNG", Texture.class);
		manager.load("RedMark2.PNG", Texture.class);
	}
	
	/**
	 * Load the backgrounds
	 */
	public void loadBackgrounds() {
		manager.load("Area Selection.PNG", Texture.class);
		manager.load("Area Selection2.PNG", Texture.class);
		manager.load("background2.PNG", Texture.class);
		manager.load("collectionBackground.PNG", Texture.class);
		manager.load("title.PNG", Texture.class);
		manager.load("pauseMenuBackground.PNG", Texture.class);
		manager.load("tutorial/Tutorial Example 1 Symbol Highlight.PNG", Texture.class);
		manager.load("tutorial/Tutorial Example 1.PNG", Texture.class);
		manager.load("tutorial/Tutorial Example 1 Emissions Bar Highlight.PNG", Texture.class);
		manager.load("tutorial/Tutorial Example 2.PNG", Texture.class);
		manager.load("tutorial/Tutorial Example 3.PNG", Texture.class);
		manager.load("tutorial/Tutorial Example 4.PNG", Texture.class);
		manager.load("tutorial/Tutorial Example 5.PNG", Texture.class);
		manager.load("tutorial/Tutorial Example 6.PNG", Texture.class);
		manager.load("tutorial/Tutorial Example 7.PNG", Texture.class);
		manager.load("tutorial/Tutorial Example 8.PNG", Texture.class);
		manager.load("tutorial/Tutorial Example 9.PNG", Texture.class);
		manager.load("tutorial/Tutorial Example 10.PNG", Texture.class);
		manager.load("tutorial/Tutorial Example 11.PNG", Texture.class);
		manager.load("tutorial/Tutorial Example 12.PNG", Texture.class);
		manager.load("tutorial/Tutorial Example 13.PNG", Texture.class);
		manager.load("tutorial/Tutorial Example 13 Skip Highlight.PNG", Texture.class);
		manager.load("textBox.PNG", Texture.class);
		manager.load("textBoxBlack.PNG", Texture.class);
	}
	
	/**
	 * Load the fonts
	 */
	public void loadFonts() {
		FileHandleResolver resolver = new InternalFileHandleResolver();
		manager.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
		manager.setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(resolver));
		
		FreeTypeFontLoaderParameter staaLarge = new FreeTypeFontLoaderParameter();
		staaLarge.fontFileName = "font/Staatliches-Regular.ttf";
		staaLarge.fontParameters.size = 35;
		manager.load("font/Staatliches-Regular35.ttf", BitmapFont.class, staaLarge);
		
		FreeTypeFontLoaderParameter staaLargeMed = new FreeTypeFontLoaderParameter();
		staaLargeMed.fontFileName = "font/Staatliches-Regular.ttf";
		staaLargeMed.fontParameters.size = 30;
		manager.load("font/Staatliches-Regular30.ttf", BitmapFont.class, staaLarge);
		
		FreeTypeFontLoaderParameter staaMedium = new FreeTypeFontLoaderParameter();
		staaMedium.fontFileName = "font/Staatliches-Regular.ttf";
		staaMedium.fontParameters.size = 25;
		manager.load("font/Staatliches-Regular25.ttf", BitmapFont.class, staaMedium);
		
		
		FreeTypeFontLoaderParameter staaSmall= new FreeTypeFontLoaderParameter();
		staaSmall.fontFileName = "font/Staatliches-Regular.ttf";
		staaSmall.fontParameters.size = 20;
		manager.load("font/Staatliches-Regular20.ttf", BitmapFont.class, staaSmall);
	}
 	
	
	/**
	 * @see {@code AssetManager.update()}
	 */
	public boolean update() {
		return manager.update();
	}
	
	/**
	 * @see {@code AssetManager.getProgress()}
	 */
	public float getProgress() {
		return manager.getProgress();
	}
	
	/**
	 * @see {@code AssetManager.finishLoading()}
	 */
	public void finishLoading() {
		manager.finishLoading();
	}

	/**
	 * Gets the Object from the asset manager
	 * 
	 * @param fileName The name / Location of the file
	 * @return Object The object that was loaded into the manager
	 */
	public Object get(String fileName) {
		return manager.get(fileName);
	}

}
