package ru.mavr;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import java.util.ArrayList;
import java.util.Stack;

public class GameScreen extends ScreenAdapter {

	public final static float CARD_WIDTH = 0.75f;
	public final static float CARD_HEIGHT = CARD_WIDTH * 277f / 200f;
	public final static float MINIMUM_VIEWPORT_SIZE = 5f;
	//	public static Stack<Player> players;
//
	public Stack<Player> players;
	//	private static TextureAtlas atlas;
	private MavrGame game;
	public CardDeck cardDeck;

	private TextButton drawCardButton;
	private SpriteBatch batch;
	SpriteBatch spriteBatch;
	OrthographicCamera cam;
	Vector3 touchPos;
	public static TextureAtlas atlas;
	public Card sprite;
	public Card card;
	public Card lastPlayedCard;
	public Card topDeck;
	public Card newTopDeck;
	int i;
	int count;
	private Player player;


	public GameScreen(MavrGame game, CardDeck cardDeck, Stack<Player> players) {
		this.game = game;
		this.cardDeck = cardDeck;
		this.players = players;

		topDeck = this.cardDeck.shuffleDeckCards.peek();
		topDeck.turned = true;
		topDeck.setSize(CARD_WIDTH, CARD_HEIGHT);
		topDeck.setPosition(-1.05f, -0.5f);
	}

	/**
	 * @return TextureAtlas
	 */
	public static TextureAtlas getAtlas() {
		atlas = new TextureAtlas("cards/carddeck.atlas");
		return atlas;
	}

	public void HandleSpriteClick(Card sprite) {
		// Top deck
		if (sprite != null) {
			if (touchPos.x > sprite.getX() && touchPos.x < sprite.getX() + sprite.getWidth()) {
				if (touchPos.y > sprite.getY() && touchPos.y < sprite.getY() + sprite.getHeight()) {
					// topDeck
					player = players.peek();
					if (sprite == topDeck) {
						System.out.println("Click Deck");
						card = cardDeck.getCard();
						if (card != null) {
							card.turned = false;
							player.cards.add(card);
							topDeck = cardDeck.getCardDeckValue();
							if (topDeck != null) {
								topDeck.turned = true;
							}
						}
					} else {
						System.out.println("Click");
						cardDeck.playedCards.push(sprite);
						player.cards.remove(sprite);
					}


				}
			}
		}
	}

	private void renderTopDeck() {
		if (cardDeck.shuffleDeckCards != null) {
			card = cardDeck.getCardDeckValue();
			if (card != null) {
				card.turned = true;
				card.setSize(CARD_WIDTH, CARD_HEIGHT);
				card.setPosition(-1.05f, -0.5f);
				card.render(spriteBatch);
			}
		}
	}

	private void renderCard() {
		renderTopDeck();
		// played cards
		lastPlayedCard = this.cardDeck.playedCards.peek();
		lastPlayedCard.setSize(CARD_WIDTH, CARD_HEIGHT);
		lastPlayedCard.setPosition(0.05f, -0.5f);
		lastPlayedCard.render(spriteBatch);

		i = 0;
		for (Player player : players
		) {
			float len = player.cards.size();
			count = 0;
			// for 2 players, TODO: for 4
			for (Card card : player.cards
			) {
				if (card != null) {
					float cardX;
					if (len <= 4) {
						cardX = -len / 2 + count;
					} else {
						cardX = -2 + 4 / len * count;
					}
					card.setSize(CARD_WIDTH, CARD_HEIGHT);
					if (i == 0) {
						card.setPosition(cardX, 1);
					}

					if (i == 1) {
						card.setPosition(cardX, -2);
					}
					card.render(this.spriteBatch);
					count++;
				}
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

		Gdx.input.setInputProcessor(new InputAdapter() {
			public boolean touchUp(int x, int y, int pointer, int button) {
				touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
				cam.unproject(touchPos); // calibrates the input to your camera's dimentions
//				System.out.println("Click");
				HandleSpriteClick(topDeck);
				Player player = players.peek();
//				ArrayList<Card> playerCards = currentPlayer.cards;
//				ArrayList<Card> playerCards = player.cards;
				int len = player.cards.size();
				for (int i = 0; i < len; i++) {
					card = player.cards.get(i);
					HandleSpriteClick(card);
				}
				return false;
			}
		});
	}

	@Override
	public void dispose() {
		spriteBatch.dispose();
		atlas.dispose();
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(3.9f / 255.0f, 42.0f / 255.0f, 5.1f / 255.0f, 1.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		spriteBatch.setProjectionMatrix(cam.combined);
		spriteBatch.begin();
		renderCard();
		spriteBatch.end();
	}
}
