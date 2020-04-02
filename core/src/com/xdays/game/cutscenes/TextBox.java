package com.xdays.game.cutscenes;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.xdays.game.Game;

/**  
 * TextBox.java - A textbox for the games cutscenes
 *
 * @author  Damian Hobeanu, Mark Ebel, Roberto Lovece, Ronil Goldenwalla, Hugh Williams
 * @version 1.0
 */ 
public class TextBox {

	private Texture texture;
	private BitmapFont font;

	private String name;

	private int charPerLine = 60;

	private ArrayList<String> lines;
	
	/**
	 * Constructor for the textbox with the sting to be displayed
	 * 
	 * @param name The name of the person speaking
	 * @param dialogue The String that they will be saying
	 */
	public TextBox(String name, String dialogue) {
		texture = (Texture) Game.assetManager.get("textBoxBlack.PNG");
		
		font = (BitmapFont) Game.assetManager.get("font/Staatliches-Regular30.ttf");

		this.name = name;

		lines = processDialogue(dialogue);
		
	}
	
	/**
	 * Constructor for the textbox with the sting to be displayed with the amount of characters per line
	 * 
	 * @param charPerLine The amount of characters per line	
	 * @param name The name of the person speaking
	 * @param dialogue The String that they will be saying
	 */
	public TextBox(int charPerLine, String name, String dialogue) {
		this.charPerLine = charPerLine;
		texture = (Texture) Game.assetManager.get("textBoxBlack.PNG");
		
		font = (BitmapFont) Game.assetManager.get("font/Staatliches-Regular30.ttf");

		this.name = name;

		lines = processDialogue(dialogue);
	}

	/**
	 * Parses the dialogue
	 * 
	 * @param dialogue The dialogue to be parsed
	 * @return An array of the parsed dialogue
	 */
	private ArrayList<String> processDialogue(String dialogue) {
		char[] array = dialogue.toCharArray();

		ArrayList<String> lines = new ArrayList<String>();

		boolean process = true;

		int lastCharIndex = 0;
		int startCharIndex = 0;

		char currentLetter = ' ';

		while (process) {

			try {
				currentLetter = array[startCharIndex + charPerLine];

				int offset = 0;

				while (currentLetter != ' ') {

					offset++;
					currentLetter = array[(startCharIndex + charPerLine) - offset];

				}

				lastCharIndex = (startCharIndex + charPerLine) - offset;
				lines.add (removeWhitespace (convertCharArray (array, startCharIndex, lastCharIndex)));
				startCharIndex = lastCharIndex;

			} catch (Exception e) {

				String endString = "";

				while (startCharIndex < array.length) {
					endString = endString + array[startCharIndex];
					startCharIndex++;
				}

				endString = removeWhitespace(endString);
				lines.add(endString);
				process = false;
			}
		}

		return lines;
	}
	
	/**
	 * 
	 * @param line The line to remove white spaces from
	 * @return The line with removed white spaces
	 */
	private String removeWhitespace(String line) {

		String leftRemoved = "";
		String rightRemoved = "";

		leftRemoved = line.replaceAll("^\\s+", "");
		rightRemoved = leftRemoved.replaceAll("\\s+$", "");

		return rightRemoved;
	}
	
	/**
	 * Converts the char array to a string
	 * 
	 * @param array The char array to be converted
	 * @param startPoint The starting point to convert
	 * @param lastPoint The end point to convert until
	 * @return The converted array
	 */
	private String convertCharArray(char[] array, int startPoint, int lastPoint) {

		String returnString = "";

		while (startPoint < lastPoint) {
			returnString = returnString + array[startPoint];
			startPoint++;
		}

		return returnString;
	}
	
	/**
	 * Displays the textbox on the sprite batch
	 * 
	 * @param sb The batch the text box is drawing on
	 */
	public void showTextBox(SpriteBatch sb) {
		sb.draw(texture, 0, 0);

		GlyphLayout layout = new GlyphLayout(font, name);
		float nameWidth = layout.width;
		
		if (name.equals("Scientist Y")) {
			font.setColor(new Color().GREEN);
		} else if (name.equals("XDAYSLEFT")) {
			font.setColor(new Color().PURPLE);
		} else {
			font.setColor(new Color().SCARLET);
		}
		
		font.draw(sb, name, (Game.WIDTH / 2) - nameWidth / 2, Game.HEIGHT / 25 * 6);
		
		int count = 5;
		font.setColor(new Color().WHITE);
		
		for (String line : lines) {
			// need to minus text width
			layout = new GlyphLayout(font, line);
			nameWidth = layout.width;
			
			font.draw(sb, line, (Game.WIDTH / 2) - nameWidth / 2, Game.HEIGHT / 25 * count);
			
			count--;
		}
	}

}
