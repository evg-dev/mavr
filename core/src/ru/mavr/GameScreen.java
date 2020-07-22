package ru.mavr;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.graphics.Color;

import java.util.ArrayList;

public class GameScreen extends ScreenAdapter {

	public final static float CARD_WIDTH = 0.75f;
	public final static float CARD_HEIGHT = CARD_WIDTH * 277f / 200f;
	public final static float MINIMUM_VIEWPORT_SIZE = 5f;
	public int currentPlayerIndex;
	public ArrayList<Player> players;
	private MavrGame game;
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
	int i;
	int count;
	private Player player;
	public Player currentPlayer;
	//	private BitmapFont font;
	private float height;
	private float width;
	private GameScreen generator;
	private BitmapFont font;


	public GameScreen(MavrGame game, CardDeck cardDeck, ArrayList<Player> players) {
		this.game = game;
		this.cardDeck = cardDeck;
		this.players = players;
		this.currentPlayerIndex = MavrGame.playersCount - 1; // Defaulr player begin, after to settings

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

	public boolean HandleSpriteClick(Card sprite) {
		// Top deck
		if (sprite != null) {
			if (touchPos.x > sprite.getX() && touchPos.x < sprite.getX() + sprite.getWidth()) {
				if (touchPos.y > sprite.getY() && touchPos.y < sprite.getY() + sprite.getHeight()) {
					// topDeck
					player = this.players.get(MavrGame.playersCount - 1); // Because counter from 0
					if (sprite == topDeck) {
//						System.out.println("Click Deck");
						card = cardDeck.getCard();
						if (card != null) {
							card.turned = false;
							player.cards.add(card);
							topDeck = cardDeck.getCardDeckValue();
							if (topDeck != null) {
								topDeck.turned = true;
								return true;
							}
						}
					}
				}
			}
		}
		return false;
	}

	public void HandleClick(Player player) {
//		System.out.println("Click");
		if (player.turn) {
			boolean deckClick = HandleSpriteClick(topDeck);
			if (deckClick) {
				turnHandling();
//				System.out.println(this.currentPlayerIndex + " currentPlayerIndex 2");
//				System.out.println(this.currentPlayer.toString() + "Click 2");
			}
			ArrayList<Card> clickedCards = new ArrayList<Card>();
			int len = player.cards.size();
			for (int i = 0; i < len; i++) {
				sprite = player.cards.get(i);
				if (touchPos.x > sprite.getX() && touchPos.x < sprite.getX() + sprite.getWidth()) {
					if (touchPos.y > sprite.getY() && touchPos.y < sprite.getY() + sprite.getHeight()) {
						clickedCards.add(sprite);
					}
				}
			}
			if (clickedCards.size() > 1) {
				// Search Card by click coordinates
				float x1 = clickedCards.get(0).getX();
				float x2 = clickedCards.get(1).getX();
				float delta = Math.abs(x1 - x2);
				for (Card clickedCard : clickedCards
				) {
					float x = clickedCard.getX();
					if (x < touchPos.x && touchPos.x < (x + delta)) {
						cardDeck.playedCards.add(clickedCard);
						player.cards.remove(clickedCard);
						turnHandling();
						break;
					}
				}
			} else if (clickedCards.size() == 1) {
				cardDeck.playedCards.add(clickedCards.get(0));
				player.cards.remove(clickedCards.get(0));
				turnHandling();
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

	private void renderText() {

		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/AeroMaticsRegular.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.size = 32;
		// Soap
//		parameter.magFilter = Texture.TextureFilter.Linear;
//		parameter.minFilter = Texture.TextureFilter.Linear;

		GlyphLayout glyphLayout = new GlyphLayout();
		font = generator.generateFont(parameter);
//		font.getRegion().getTexture().setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
//		font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
//		font.getData().setScale(width / 1000f);
//		font.getData().setScale(2f);
		glyphLayout.setText(font, "Player 1");

		font.draw(this.spriteBatch, glyphLayout, 0, -height / 2 + 50);
	}

	private void renderCard() {
		renderTopDeck();
		// played cards
		lastPlayedCard = this.cardDeck.playedCards.peek();
		lastPlayedCard.setSize(CARD_WIDTH, CARD_HEIGHT);
		lastPlayedCard.setPosition(0.05f, -0.5f);
		lastPlayedCard.render(spriteBatch);

		i = 0;
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
						float cardX;
						float cardY;
						if (len <= 4) {
							cardX = -len / 2 + count;
						} else {
							cardX = -2 + 4 / len * count;
						}

						if (i == 0) {
							cardY = 1 - 4 / len * count;
							card.setPosition(-4, cardY);
						}

						card.setSize(CARD_WIDTH, CARD_HEIGHT);
						if (i == 1) {
							card.setPosition(cardX, 1);
						}

						if (i == 2) {
							cardY = 1 - 4 / len * count;
							card.setPosition(3, cardY);
						}

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
		// Handling init
//		System.out.println(this.currentPlayerIndex + " currentPlayerIndex");
//		System.out.println(this.currentPlayer.toString() + "Click");
		this.currentPlayer = this.players.get(this.currentPlayerIndex);
		this.currentPlayer.turn = true;
//		if(this.currentPlayer.type) {
		Gdx.input.setInputProcessor(new InputAdapter() {
			public boolean touchUp(int x, int y, int pointer, int button) {
				touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
				cam.unproject(touchPos); // calibrates the input to your camera's dimentions
				//				System.out.println("Click");
				//				Player player = players.peek();
				Player humanPlayer = game.gameScreen.players.get(MavrGame.playersCount - 1);
				HandleClick(humanPlayer);
				return false;
			}
		});
//		}
	}

	@Override
	public void dispose() {
		this.spriteBatch.dispose();
		atlas.dispose();
		font.dispose();
		generator.dispose();
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(3.9f / 255.0f, 42.0f / 255.0f, 5.1f / 255.0f, 1.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		this.spriteBatch.begin();
		this.spriteBatch.setProjectionMatrix(cam.combined);
//		renderCard();
		this.spriteBatch.setProjectionMatrix(textCam.combined);
		renderText();
		this.spriteBatch.end();
	}

	/**
	 * @return player for 0 counter
	 */

	private void turnHandling() {
		this.currentPlayer.turn = false;
		this.currentPlayerIndex++;
		if (this.currentPlayerIndex > MavrGame.playersCount - 1) {
			this.currentPlayerIndex -= MavrGame.playersCount;
		}
		this.currentPlayer = this.players.get(currentPlayerIndex);
		this.currentPlayer.turn = true;
		if (!this.currentPlayer.type) {
			aiTurn();
			turnHandling();
		}
	}

	public void aiTurn() {
		if (this.currentPlayer.turn) {
			// TODO: Add AI Logic here
			if (this.currentPlayer.cards.size() > 0) {
				card = this.currentPlayer.cards.remove(0);
				card.turned = false;
				cardDeck.playedCards.add(card);
			}
			this.currentPlayer.turn = false;
		}
	}

//	private void logicHandler(Card lastPlayedCard, int counter) {
//		int value = lastPlayedCard.getValue();
//		suit = lastPlayedCard.getSuit();
//		// A
//		if (value == 11) {
//			counter++;
//		}
//		// K SPADES
//		if (value == 4 && suit == MavrGame.Suit.SPADES) {
//			Player nextPlayer = this.getPlayer(counter++);
//			nextPlayer.cards.add(cardDeck.getCard());
//			nextPlayer.cards.add(cardDeck.getCard());
//			nextPlayer.cards.add(cardDeck.getCard());
//			nextPlayer.cards.add(cardDeck.getCard());
//			counter++;
//		}
//
//	}

	public Player getPlayer(int i) {
//		System.out.println("ddd " + players.toString());
		return this.players.get(i - 1);
	}
}
