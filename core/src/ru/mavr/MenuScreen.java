package ru.mavr;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import java.util.ArrayList;
import java.util.Stack;

public class MenuScreen extends ScreenAdapter {

	private final TextButton.TextButtonStyle textButtonStyle;
	public Stack<Card> fullDeckCards;
	public BitmapFont font;
	private final TextButton continueGame;
	private final TextButton statistic;
	private final TextButton newGame;
	private final TextButton settings;
	private final TextButton exit;
	private final FreeTypeFontGenerator generator;
	public MavrGame game;
	private Camera cam;
	private SpriteBatch spriteBatch;
	private Vector3 touchPos;
	public float width;
	public float height;
	private CardDeck cardDeck;


	public MenuScreen(MavrGame game) {

		this.game = game;
//		skin = new Skin();
//		buttonAtlas = new TextureAtlas(Gdx.files.internal("buttons/buttons.pack"));
//		skin.addRegions(buttonAtlas);
		this.width = Gdx.graphics.getWidth();
		this.height = Gdx.graphics.getHeight();
//		this.fullDeckCards = new Stack<Card>();

		this.spriteBatch = new SpriteBatch();
		this.touchPos = new Vector3();
		this.cam = new OrthographicCamera();

		generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/AeroMaticsRegular.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.size = 50;
		font = generator.generateFont(parameter);

		textButtonStyle = new TextButton.TextButtonStyle();
		textButtonStyle.font = font;

		continueGame = new TextButton("Continue", textButtonStyle);
		continueGame.setPosition(-continueGame.getWidth() / 2, this.height / 2 - 100);

		newGame = new TextButton("New Game", textButtonStyle);
		newGame.setPosition(-newGame.getWidth() / 2, this.height / 3 - 100);

		settings = new TextButton("Settings", textButtonStyle);
		settings.setPosition(-settings.getWidth() / 2, 0);

		statistic = new TextButton("Statistic", textButtonStyle);
		statistic.setPosition(-statistic.getWidth() / 2, -this.height / 3 + 100);

		exit = new TextButton("Exit", textButtonStyle);
		exit.setPosition(-exit.getWidth() / 2, -this.height / 2 + 100);
	}

	public static void createPlayers(int playersCount, ArrayList<Player> players) {
		for (int i = 1; i <= playersCount; i++) {
			Player player = new Player("Player" + i);
			if (i == playersCount) {
				// Human player
				player.type = true;
//				System.out.println("Player Human : " + i);
			}
//			System.out.println("Player : " + i);
			players.add(player);
		}
	}

	@Override
	public void show() {
//		spriteBatch = this.spriteBatch;
//		touchPos = new Vector3();
//		cam = new OrthographicCamera();

		Gdx.input.setInputProcessor(new InputAdapter() {

			public boolean touchUp(int x, int y, int pointer, int button) {
				touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
				cam.unproject(touchPos); // calibrates the input to your camera's dimentions

				// continueGame;
				if (touchPos.x > continueGame.getX() && touchPos.x < continueGame.getX() + continueGame.getWidth()) {
					if (touchPos.y > continueGame.getY() && touchPos.y < continueGame.getY() + continueGame.getHeight()) {
						game.setScreen(game.gameScreen);
					}
				}

				// newGame;
				if (touchPos.x > newGame.getX() && touchPos.x < newGame.getX() + newGame.getWidth()) {
					if (touchPos.y > newGame.getY() && touchPos.y < newGame.getY() + newGame.getHeight()) {
						// Create new game
//						System.out.println("Create start");

						ArrayList<Player> players = new ArrayList<Player>();
						game.playersCount = game.prefs.getInteger("playersCount", 2);
						MenuScreen.createPlayers(game.playersCount, players);
						MavrGame.shuffleDeck(game, players);
						game.gameScreen = new GameScreen(game, game.cardDeck, players);

//						System.out.println("Create end");
						game.setScreen(game.gameScreen);
					}
				}
				// Settings
				if (touchPos.x > settings.getX() && touchPos.x < settings.getX() + settings.getWidth()) {
					if (touchPos.y > settings.getY() && touchPos.y < settings.getY() + settings.getHeight()) {
						game.setScreen(game.settigsScreen);
					}
				}
				// Exit
				if (touchPos.x > exit.getX() && touchPos.x < exit.getX() + exit.getWidth()) {
					if (touchPos.y > exit.getY() && touchPos.y < exit.getY() + exit.getHeight()) {
						Gdx.app.exit();
					}
				}

				return false;
			}

			public boolean keyUp(int keycode) {
				if (keycode == Input.Keys.BACK) {
					// Exit
					Gdx.app.exit();
				}
				return false;
			}
		});
	}

	public void resize(int width, int height) {
		cam.viewportWidth = width;
		cam.viewportHeight = height;
		cam.update();
	}

	@Override
	public void dispose() {
		spriteBatch.dispose();
//		atlas.dispose();
	}

	@Override
	public void render(float delta) {
		super.render(delta);
		Gdx.gl.glClearColor(3.9f / 255.0f, 42.0f / 255.0f, 5.1f / 255.0f, 1.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		spriteBatch.setProjectionMatrix(cam.combined);
		spriteBatch.begin();
		// Hide continue first
		if (game.gameScreen != null) {
			continueGame.draw(spriteBatch, 1);
		}
		newGame.draw(spriteBatch, 1);
		settings.draw(spriteBatch, 1);
		statistic.draw(spriteBatch, 1);
		exit.draw(spriteBatch, 1);
		spriteBatch.end();
	}
}
