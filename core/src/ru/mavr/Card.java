package ru.mavr;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class Card {

    public final Sprite backSprite;
    public final Sprite topSprite;
    private final int value;
    //    public final Suit suit;
    public Sprite sprite;
    private final MavrGame.Suit suit;
    TextureAtlas atlas;
    public boolean turned; //back upside = true

    public float X;
    public float Y;
    private CardDeck cardDeck;


    public Card(MavrGame.Suit suit, float X, float Y, boolean turned, int index, int value) {

        this.atlas = GameScreen.getAtlas();
        this.turned = turned;
        this.value = value;
        this.suit = suit;
        this.X = X;
        this.Y = Y;

        this.backSprite = this.atlas.createSprite(("back"), 1);
        this.topSprite = this.atlas.createSprite(suit.suitName, index);
    }

//    public Card(Card.Suit diamonds, int x, int y, boolean turned, int index) {
//    }


    public void setSize(float cardWidth, float cardHeight) {
        if (this.turned) {
            this.backSprite.setSize(GameScreen.CARD_WIDTH, GameScreen.CARD_HEIGHT);
        } else {
            this.topSprite.setSize(GameScreen.CARD_WIDTH, GameScreen.CARD_HEIGHT);
        }
    }

    public void setPosition(float X, float Y) {
        if (this.turned) {
            this.backSprite.setPosition(X, Y);
        } else {
            this.topSprite.setPosition(X, Y);
        }
    }

    public void update() {
        if (this.turned) {
            this.backSprite.setPosition(X, Y);
        } else {
            this.topSprite.setPosition(X, Y);
        }
    }

    public void render(SpriteBatch spriteBatch) {
        if (this.turned) {
            this.backSprite.draw(spriteBatch);
        } else {
            this.topSprite.draw(spriteBatch);
        }
    }

    public float getX() {
        if (this.turned) {
            return this.backSprite.getX();
        } else {
            return this.topSprite.getX();
        }
    }

    public float getY() {
        if (this.turned) {
            return this.backSprite.getY();
        } else {
            return this.topSprite.getY();
        }
    }

    public float getWidth() {
        if (this.turned) {
            return this.backSprite.getWidth();
        } else {
            return this.topSprite.getWidth();
        }
    }

    public float getHeight() {
        if (this.turned) {
            return this.backSprite.getHeight();
        } else {
            return this.topSprite.getHeight();
        }
    }

    public int getValue() {
        return this.value;
    }

    public MavrGame.Suit getSuit() {
        return this.suit;
    }

    public static Card newTopDeck(CardDeck cardDeck, Card topDeck) {
        topDeck = cardDeck.shuffleDeckCards.peek();
        topDeck.turned = true;
        topDeck.setSize(GameScreen.CARD_WIDTH, GameScreen.CARD_HEIGHT);
        topDeck.setPosition(-1.05f, -0.5f);
        return topDeck;
    }

//    @Override
//    public void draw(SpriteBatch batch){
//        super.render();
//        if(this.turned){
//            this.back.draw(batch);
//        } else {
//            this.front.draw(batch);
//        }

}
