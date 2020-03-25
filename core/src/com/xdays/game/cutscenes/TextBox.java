package com.xdays.game.cutscenes;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.xdays.game.Game;

public class TextBox {

	private Texture texture;
	private BitmapFont font;

	private String name;

	private final int CHARS_PER_LINE = 60;

	private ArrayList<String> lines;

	public TextBox(String name, String dialogue) {
		texture = new Texture("textBoxBlack.png");
		font = new BitmapFont(Gdx.files.internal("font/recreativos.fnt"), false);

		this.name = name;

		font.getData().setScale(0.15f, 0.15f);

		lines = processDialogue(dialogue);
		
	}


	private ArrayList<String> processDialogue(String dialogue) {
		char[] array = dialogue.toCharArray();

		ArrayList<String> lines = new ArrayList<String>();

		boolean process = true;

		int lastCharIndex = 0;
		int startCharIndex = 0;

		char currentLetter = ' ';

		while (process) {

			try {
				currentLetter = array[startCharIndex + CHARS_PER_LINE];

				int offset = 0;

				while (currentLetter != ' ') {

					offset++;
					currentLetter = array[(startCharIndex + CHARS_PER_LINE) - offset];

				}

				lastCharIndex = (startCharIndex + CHARS_PER_LINE) - offset;
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

		for (String line : lines) {
			System.out.print(line + "\n");
		}

		return lines;
	}

	private String removeWhitespace(String line) {

		String leftRemoved = "";
		String rightRemoved = "";

		leftRemoved = line.replaceAll("^\\s+", "");
		rightRemoved = leftRemoved.replaceAll("\\s+$", "");

		return rightRemoved;
	}

	private String convertCharArray(char[] array, int startPoint, int lastPoint) {

		String returnString = "";

		while (startPoint < lastPoint) {
			returnString = returnString + array[startPoint];
			startPoint++;
		}

		return returnString;
	}

	public void showTextBox(SpriteBatch sb) {
		sb.draw(texture, 0, 0);

		GlyphLayout layout = new GlyphLayout(font, name);
		float nameWidth = layout.width;
		
		if (name.equals("Scientist Y")) {
			font.setColor(new Color().GREEN);
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