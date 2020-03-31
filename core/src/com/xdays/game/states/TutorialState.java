package com.xdays.game.states;

import java.util.LinkedList;
import java.util.Queue;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.xdays.game.Game;
import com.xdays.game.assets.Button;
import com.xdays.game.cutscenes.TextBox;

public class TutorialState extends State {

	private Sound clickSound;
	private Texture texture;

	private Queue<TextBox> textBoxQueue;
	private Queue<Texture> tutorialExampleQueue;

	public TutorialState(GameStateManager gsm) {
		super(gsm);
		cam.setToOrtho(false, Game.WIDTH, Game.HEIGHT);
		clickSound = Gdx.audio.newSound(Gdx.files.internal("sounds/ClickSound.wav"));

		Gdx.gl.glClearColor(157f / 255f, 46f / 255f, 46f / 255f, 1);

		tutorialExampleQueue = new LinkedList<Texture>();
		createTutorialExampleImages();
		
		textBoxQueue = new LinkedList<TextBox>();
		createTutorialDialogue();

	}
	
	private void resetState() {
		createTutorialExampleImages();
		createTutorialDialogue();
	}

	private void createTutorialExampleImages() {

		tutorialExampleQueue.add(new Texture("tutorial/Tutorial Example 1.png"));
		tutorialExampleQueue.add(new Texture("tutorial/Tutorial Example 1 Symbol Highlight.png"));
		tutorialExampleQueue.add(new Texture("tutorial/Tutorial Example 1.png"));
		tutorialExampleQueue.add(new Texture("tutorial/Tutorial Example 1 Emissions Bar Highlight.png"));
		tutorialExampleQueue.add(new Texture("tutorial/Tutorial Example 1 Emissions Bar Highlight.png"));

		tutorialExampleQueue.add(new Texture("tutorial/Tutorial Example 2.png"));
		
		tutorialExampleQueue.add(new Texture("tutorial/Tutorial Example 3.png"));
		tutorialExampleQueue.add(new Texture("tutorial/Tutorial Example 3.png"));
		
		tutorialExampleQueue.add(new Texture("tutorial/Tutorial Example 4.png"));
		tutorialExampleQueue.add(new Texture("tutorial/Tutorial Example 5.png"));
		tutorialExampleQueue.add(new Texture("tutorial/Tutorial Example 6.png"));
		
		tutorialExampleQueue.add(new Texture("tutorial/Tutorial Example 7.png"));
		tutorialExampleQueue.add(new Texture("tutorial/Tutorial Example 7.png"));
		
		tutorialExampleQueue.add(new Texture("tutorial/Tutorial Example 8.png"));
		tutorialExampleQueue.add(new Texture("tutorial/Tutorial Example 9.png"));
		tutorialExampleQueue.add(new Texture("tutorial/Tutorial Example 10.png"));
		tutorialExampleQueue.add(new Texture("tutorial/Tutorial Example 11.png"));
		tutorialExampleQueue.add(new Texture("tutorial/Tutorial Example 12.png"));
		tutorialExampleQueue.add(new Texture("tutorial/Tutorial Example 13.png"));
		tutorialExampleQueue.add(new Texture("tutorial/Tutorial Example 13 Skip Highlight.png"));

	}

	private void createTutorialDialogue() {

		textBoxQueue.add(new TextBox("XDAYSLEFT",
				"Welcome to X DAYS LEFT the card game where the aim is to same the planet from the catastrophic "
						+ "effects of global warming by battling word leaders in a card game."));

		textBoxQueue.add(new TextBox("XDAYSLEFT",
				"The image above is a look at what a typical start to the game looks like. "
						+ "There are two types of cards Industry (highlighted by the red circle) and Social (highlight by the yellow "
						+ "circle)."));

		textBoxQueue.add(new TextBox(90, "XDAYSLEFT",
				"Industry cards have a star value shown at the bottom center of the card, "
						+ "for example Plant Tree has a star value of one. As well as a emissions value shown in the bottom right of "
						+ "the card, for example Windmill has an emissions of six. Note you can only play one star card on the board "
						+ "normal, two or more star cards require special action but more on that soon..."));

		textBoxQueue.add(new TextBox(90, "XDAYSLEFT",
				"In the center of the board there is what is refered to as the \"emissions bar\" "
						+ "it starts at 50% and the aim as a player it to get the emissions value to 0% and to stop it getting to 100% "
						+ "at all costs. The way you do this is having a total emissions on your side of there board greater than that "
						+ "of your opponent."));
		
		textBoxQueue.add(new TextBox(90, "XDAYSLEFT",
				"Lets say you manage to play Hydroelectric Energy on the board and your opponents board is "
						+ "empty as the emissions value is fourteen each turn the emissions bar will -14% until at zero you win! However it "
						+ "won't usually be that easy and your opponent will play his own industry card to fight back!"));

		// image 2
		textBoxQueue.add(new TextBox("XDAYSLEFT", "Now lets play some cards so you can get a hang of the game!"));

		// image 3
		textBoxQueue.add(new TextBox(90, "XDAYSLEFT",
				"As you can see i've now played two copies of the Plant Tree card. Note each "
						+ "time you play a card you automatically will draw a card if your deck still has cards left unless its a "
						+ "social card. Looks like my opponent has played two copies of the Remove Tree card in response."));
		
		textBoxQueue.add(new TextBox("XDAYSLEFT",
				" at the moment its a stalemate us each both having equal total emissions of four on the board but lucky for us we've got a "
						+ "trump card up our sleeve CARD MERGING!"));

		// image 4
		textBoxQueue.add(new TextBox("XDAYSLEFT",
				"As we have two one star cards on the board we can select our two star Windmill "
						+ "card putting it in \"half play\" mode and select our two one star Plant Tree cards to be merged into the Windmill "
						+ "card increasing our emissison output."));

		// image 5, 6
		textBoxQueue.add(new TextBox("XDAYSLEFT",
				"Anyway for now lets keep playing cards and building up our board to counter our opponent"));
		textBoxQueue.add(new TextBox("XDAYSLEFT",
				"Anyway for now lets keep playing cards and building up our board to counter our opponent"));

		// image 7
		textBoxQueue.add(new TextBox(90, "XDAYSLEFT",
				"Now time to showcase the second type of card, Social cards. This particular "
						+ "card protest will destroy an selected opponents industry card. Similarly to when we merge a card selecting "
						+ "protest put it in a \"half play\" state waiting for us to select an opponents card destroy."));
		
		textBoxQueue.add(new TextBox(90, "XDAYSLEFT",
				"If we then "
						+ "reclick the card we can remove it out of the \"half play\" mode and select another card to play. In this "
						+ "situation im going to destroy the opponents right Diesel Car card, guess he'll have to take the bus from "
						+ "now on!"));

		// image 8, 9, 10, 11, 12
		textBoxQueue.add(new TextBox("XDAYSLEFT", "Lets just continue to play out the game until something interesting happens"));
		textBoxQueue.add(new TextBox("XDAYSLEFT", "Lets just continue to play out the game until something interesting happens"));
		textBoxQueue.add(new TextBox("XDAYSLEFT", "Lets just continue to play out the game until something interesting happens"));
		textBoxQueue.add(new TextBox("XDAYSLEFT", "Lets just continue to play out the game until something interesting happens"));
		textBoxQueue.add(new TextBox("XDAYSLEFT", "Lets just continue to play out the game until something interesting happens"));
		
		// image 13
		textBoxQueue.add(new TextBox(90, "XDAYSLEFT",
				"Finally! Now if you look at the opponents side of board and counter the emissions he has eight due to his serve lack of card merging. However if "
						+ "you focus your attention to our side of the board you'll see theres eleven, yes we're winning! That's great news for us as the opponents hand is "
						+ "empty."));
		
		textBoxQueue.add(new TextBox(90, "XDAYSLEFT",
				"We can win easily by using a another core function of the game the skip button. Pressing this a few times will cause the emissions to "
						+ "tick towards zero while not need to play a card, you can use this anytime in the duel however be cautious using it or your opponent may take advantage!"
						+ ""));
	}

	@Override
	protected void handleInput() {
		if (Gdx.input.justTouched()) {
			clickSound.play();
			textBoxQueue.poll();
			tutorialExampleQueue.poll();
		}
	}

	@Override
	public void update(float dt) {
		handleInput();
		render(Game.batch);
	}

	@Override
	public void render(SpriteBatch sb) {
		sb.setProjectionMatrix(cam.combined);
		sb.begin();

		try {
			textBoxQueue.peek().showTextBox(sb);
			sb.draw(tutorialExampleQueue.peek(), Game.WIDTH / 8, 180);
		} catch (Exception e) {
			resetState();
			gsm.setState(StateEnum.MAP_STATE);
			gsm.removeState(StateEnum.CUTSCENE_STATE);
			dispose();

		}

		sb.end();
	}

	@Override
	public void dispose() {

	}

	private void clickSound() {
		clickSound.play(0.1f);
	}

}
