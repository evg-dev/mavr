package ru.mavr;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;

public class GameScreen extends ScreenAdapter {

	public final static float CARD_WIDTH = 0.75f;
	public final static float CARD_HEIGHT = CARD_WIDTH * 277f / 200f;
	public final static float MINIMUM_VIEWPORT_SIZE = 5f;
	public final float topDeckX = -1.05f;
	public final float topDeckY = -0.5f;

	public RenderText renderText;
	public GameLogic gameLogic;
	public int currentPlayerIndex;
	public ArrayList<Player> players;
	public MavrGame game;
	public CardDeck cardDeck;

	SpriteBatch spriteBatch;
	OrthographicCamera cam;
	OrthographicCamera textCam;
	Vector3 touchPos;
	public static TextureAtlas atlas;
	public Card sprite;
	public Card card;
	public Card lastPlayedCard;
	public Card topDeck;

	int count;
	public Player currentPlayer;
	public float height;
	public float width;
	private BitmapFont font;

	public GameScreen(MavrGame game, CardDeck cardDeck, ArrayList<Player> players) {
		game.gameScreen = this;
		this.game = game;
		this.cardDeck = cardDeck;
		this.players = players;
		this.currentPlayerIndex = game.playersCount - 1; // Default humnan player begin, after to settings
		this.gameLogic = new GameLogic(this.game);

		if (this.cardDeck.shuffleDeckCards.size() > 0) {
			topDeck = game.cardDeck.shuffleDeckCards.peek();
			topDeck.turned = true;
			topDeck.setSize(CARD_WIDTH, CARD_HEIGHT);
			topDeck.setPosition(topDeckX, topDeckY);
		}
		GameLogic.gameTurn(game);
	}

	public boolean HandleSpriteClick(Card sprite) {
		// Top deck
//		System.out.println("Top deck boolean" + topDeck.getValue());
		System.out.println("getWidth" + sprite.getX());
		System.out.println("Height" + sprite.getY());
		if (touchPos.x > topDeckX && touchPos.x < topDeckX + sprite.getWidth()) {
			if (touchPos.y > topDeckY && touchPos.y < topDeckY + sprite.getHeight()) {
				// topDeck
				Player player = this.players.get(game.playersCount - 1); // Because counter from 0
				System.out.println("Sprite click : " + sprite.getValue());
//				if (sprite == topDeck) {
//						System.out.println("Click Deck");
				card = game.cardDeck.getCard();
				if (card != null) {
//					card.turned = false;
					player.cards.add(card);
					topDeck = game.cardDeck.getCardDeckValue();
//						System.out.println("topDeck1 : " + topDeck.getValue());
					//						topDeck.turned = true;
					//							System.out.println("CadrDeck count : " + game.cardDeck.shuffleDeckCards.size());
					//							System.out.println("topDeck2 : " + card.getValue());
					return topDeck != null;
				}
//				}
			}
		}
		return false;
	}


	/**
	 * Main click handler
	 *
	 * @param player Player
	 */
	public void HandleClick(Player player) {
		System.out.println("Player " + player.name);
		if (player.turn) {
			boolean deckClick = HandleSpriteClick(topDeck);
			if (deckClick) {
				GameLogic.endTurnHandling(game);
				return;
			}
			ArrayList<Card> clickedCards = new ArrayList<Card>();
			int len = player.cards.size();
			for (int i = 0; i < len; i++) {
				sprite = player.cards.get(i);
				if (touchPos.x > sprite.getX() && touchPos.x < sprite.getX() + sprite.getWidth()) {
					if (touchPos.y > sprite.getY() && touchPos.y < sprite.getY() + sprite.getHeight()) {
						System.out.println("Sprite click");
						clickedCards.add(sprite);
					}
				}
			}
//			System.out.println(clickedCards.toString());
			if (clickedCards.size() > 1) {
				// Search Card by click coordinates
				float x1 = clickedCards.get(0).getX();
				float x2 = clickedCards.get(1).getX();
				float delta = Math.abs(x1 - x2);
				for (Card clickedCard : clickedCards
				) {
					float x = clickedCard.getX();
					if (x < touchPos.x && touchPos.x < (x + delta)) {
						if (gameLogic.checkClickedCard(clickedCard)) {
							cardDeck.playedCards.add(clickedCard);
							player.cards.remove(clickedCard);
							GameLogic.endTurnHandling(game);
							return;
						}
						break;
					}
				}
			} else if (clickedCards.size() == 1) {
				if (gameLogic.checkClickedCard(clickedCards.get(0))) {
					cardDeck.playedCards.add(clickedCards.get(0));
					player.cards.remove(clickedCards.get(0));
					GameLogic.endTurnHandling(game);
				}
			}
		}
	}

	private void renderTopDeck() {
		if (cardDeck.shuffleDeckCards != null) {
			card = cardDeck.getCardDeckValue();
//			System.out.println("Top decK render" + card.getValue());
			if (card != null) {
				card.turned = true;
				card.setSize(CARD_WIDTH, CARD_HEIGHT);
				card.setPosition(-1.05f, -0.5f);
				card.render(spriteBatch);
			}
		}
	}

	private void renderText() {
		this.renderText.draw(this.spriteBatch);
	}

	private void renderCard() {
		renderTopDeck();
		// played cards
		lastPlayedCard = game.cardDeck.playedCards.peek();
		lastPlayedCard.turned = false;
		lastPlayedCard.setSize(CARD_WIDTH, CARD_HEIGHT);
		lastPlayedCard.setPosition(0.05f, -0.5f);
		lastPlayedCard.render(spriteBatch);

		int i = 0;
		for (Player player : this.players
		) {
			int playersCount = this.players.size();
			float len = player.cards.size();
			count = 0;
			// for 2 players
			if (playersCount == 2) {
				for (Card card : player.cards
				) {
					if (card != null) {
						card.turned = !player.type;
//						System.out.println("Card Turned : " + card.turned);
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

			if (playersCount == 3) {
				for (Card card : player.cards
				) {
					if (card != null) {
						card.turned = !player.type;
						float cardX;
						float cardY;
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
							cardY = 1 - 4 / len * count;
							card.setPosition(-4, cardY);
						}

						if (i == 2) {
							card.setPosition(cardX, -2);
						}
						card.render(this.spriteBatch);
						count++;
					}
				}
				i++;
			}

			if (playersCount == 4) {
				for (Card card : player.cards
				) {
					if (card != null) {
						card.turned = !player.type;
						float cardX;
						float cardY;
						// Left
						if (len <= 4) {
							cardX = -len / 2 + count;
						} else {
							cardX = -2 + 4 / len * count;
						}

						if (i == 0) {
							cardY = 1 - 4 / len * count;
							card.setPosition(-4, cardY);
						}
						// Top
						card.setSize(CARD_WIDTH, CARD_HEIGHT);
						if (i == 1) {
							card.setPosition(cardX, 1);
						}
						// Right
						if (i == 2) {
							cardY = 1 - 4 / len * count;
							card.setPosition(3, cardY);
						}

						// Human
						if (i == 3) {
							card.setPosition(cardX, -2);
						}
						card.render(this.spriteBatch);
						count++;
					}
				}
				i++;
			}
		}
	}

	@Override
	public void resize(int width, int height) {
		this.width = width;
		this.height = height;
		if (width > height) {
			cam.viewportHeight = MINIMUM_VIEWPORT_SIZE;
			cam.viewportWidth = cam.viewportHeight * (float) width / (float) height;
		} else {
			cam.viewportWidth = MINIMUM_VIEWPORT_SIZE;
			cam.viewportHeight = cam.viewportWidth * (float) height / (float) width;
		}
		textCam.viewportWidth = width;
		textCam.viewportHeight = height;

		cam.update();
		textCam.update();
	}

	@Override
	public void show() {
		this.spriteBatch = new SpriteBatch();
		touchPos = new Vector3();
		cam = new OrthographicCamera();
		textCam = new OrthographicCamera();
//		System.out.println(this.currentPlayerIndex + " currentPlayerIndex");
//		System.out.println(this.currentPlayer.toString() + "Click");
		this.currentPlayer = this.players.get(this.currentPlayerIndex);
		this.currentPlayer.turn = true;
		if (this.currentPlayer.type) {
			Gdx.input.setInputProcessor(new InputAdapter() {
				public boolean touchUp(int x, int y, int pointer, int button) {
					touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
					cam.unproject(touchPos); // calibrates the input to your camera's dimentions
					//				System.out.println("Click");
					//				Player player = players.peek();
					Player humanPlayer = game.gameScreen.players.get(game.playersCount - 1);
					HandleClick(humanPlayer);
					return false;
				}

				public boolean keyUp(int keycode) {
					if (keycode == Input.Keys.BACK) {
						// Back to menu
						if (game != null) {
							game.setScreen(game.menuScreen);
						}
					}
					return false;
				}
			});
		}
	}

	@Override
	public void dispose() {
		this.spriteBatch.dispose();
		atlas.dispose();
		font.dispose();
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(3.9f / 255.0f, 42.0f / 255.0f, 5.1f / 255.0f, 1.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		this.spriteBatch.begin();
		this.spriteBatch.setProjectionMatrix(cam.combined);
		renderCard();
		this.spriteBatch.setProjectionMatrix(textCam.combined);
		renderText();
		this.spriteBatch.end();
	}
}
