package ru.mavr;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import java.util.Stack;

public class GameScreen extends ScreenAdapter {

	public final static float CARD_WIDTH = 0.75f;
	public final static float CARD_HEIGHT = CARD_WIDTH * 277f / 200f;
	public final static float MINIMUM_VIEWPORT_SIZE = 5f;
	//	public static Stack<Player> players;
//
	public Stack<Player> players;
	//	private static TextureAtlas atlas;
	public Card card;
	private MavrGame game;
	public CardDeck cardDeck;

	private TextButton drawCardButton;
	private SpriteBatch batch;
	SpriteBatch spriteBatch;
	OrthographicCamera cam;
	Vector3 touchPos;
	public Card sprite;
	public static TextureAtlas atlas;
	private Card lastPlayedCard;
	private Card topDeck;
	int i;
	int count;


	public GameScreen(MavrGame game, CardDeck cardDeck, Stack<Player> players) {
		this.game = game;
		this.cardDeck = cardDeck;
		this.players = players;
	}

	/**
	 * @return TextureAtlas
	 */
	public static TextureAtlas getAtlas() {
		atlas = new TextureAtlas("cards/carddeck.atlas");
		return atlas;
	}

	private void renderCard() {
		// deck
		topDeck = this.cardDeck.shuffleDeckCards.peek();
		topDeck.turned = true;
		topDeck.setSize(CARD_WIDTH, CARD_HEIGHT);
		topDeck.setPosition(-1.05f, -0.5f);
		topDeck.draw(spriteBatch);
		// played cards
		lastPlayedCard = this.cardDeck.playedCards.peek();
		lastPlayedCard.setSize(CARD_WIDTH, CARD_HEIGHT);
		lastPlayedCard.setPosition(0.05f, -0.5f);
		lastPlayedCard.draw(spriteBatch);

		i = 0;
		for (Player player : players
		) {
			float len = player.cards.size();
			count = 0;
			// for 2 players, TODO: for 4
			for (Card card : player.cards
			) {
				float cardX;
				if (len <= 4) {
					cardX = -len / 2 + count;
				} else {
					cardX = -2 + 4 / len * count;
				}
				card.setSize(CARD_WIDTH, CARD_HEIGHT);
				if (i == 0) {
					card.setPosition(cardX, -2);
				}

				if (i == 1) {
					card.setPosition(cardX, 1);
				}
				card.draw(this.spriteBatch);
				count++;
			}
			i++;
		}
	}

	@Override
	public void resize(int width, int height) {
		if (width > height) {
			cam.viewportHeight = MINIMUM_VIEWPORT_SIZE;
			cam.viewportWidth = cam.viewportHeight * (float) width / (float) height;
		} else {
			cam.viewportWidth = MINIMUM_VIEWPORT_SIZE;
			cam.viewportHeight = cam.viewportWidth * (float) height / (float) width;
		}
		cam.update();
	}

	@Override
	public void show() {
		spriteBatch = new SpriteBatch();
		touchPos = new Vector3();
		cam = new OrthographicCamera();

//		Gdx.input.setInputProcessor(new InputAdapter() {
//			public boolean touchUp(int x, int y, int pointer, int button) {
//				touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
//				cam.unproject(touchPos); // calibrates the input to your camera's dimentions
//				if (touchPos.x > sprite.getX() && touchPos.x < sprite.getX() + sprite.getWidth()) {
//					if (touchPos.y > sprite.getY() && touchPos.y < sprite.getY() + sprite.getHeight()) {
//						System.out.println("Click sprite");

//						sprite.setPosition(sprite.getX() + 0.5f, -2);
		//clicked on sprite
		// do something that vanish the object clicked
//					}
//				}
//				float touchX = temp.x;
//				float touchY = temp.y;
//				System.out.println("Touch");
//				System.out.println(touchPos.x);
//				System.out.println(sprite.getX());
//				return false;
//			}

//			public boolean touchUp(int x,int y,int pointer,int button){
//				return true; // возвращает true, сообщая, что событие было обработано
//			}
//		});

	}
//	addListener(new ClickListener() {
//		@Override
//		public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

//			Gdx.input.vibrate(25); // Дадим пользователю понять, что он нажал немного вибрируя в момент касания

	@Override
	public void dispose() {
		spriteBatch.dispose();
		atlas.dispose();
	}

	//	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(3.9f / 255.0f, 42.0f / 255.0f, 5.1f / 255.0f, 1.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		spriteBatch.setProjectionMatrix(cam.combined);
		spriteBatch.begin();
		renderCard();
		spriteBatch.end();
	}
}
