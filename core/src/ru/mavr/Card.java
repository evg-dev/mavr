package ru.mavr;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Card {

    public final Sprite backSprite;
    public final Sprite topSprite;
    private final int value;
    private final MavrGame.Suit suit;
    public boolean turned; //back upside = true, back down = false

    public float X;
    public float Y;


    public Card(MavrGame.Suit suit, float X, float Y, boolean turned, int index, int value) {

        this.turned = turned;
        this.value = value;
        this.suit = suit;
        this.X = X;
        this.Y = Y;

        this.backSprite = MavrGame.atlas.createSprite(("back"), 1);
        this.topSprite = MavrGame.atlas.createSprite(suit.suitName, index);
    }

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

    public int countPoint() {
        int result = 0;

        int value = this.getValue();
        MavrGame.Suit suit = this.getSuit();


        // Queen
//        if(value == 3) {
//            if(suit == MavrGame.Suit.DIAMONDS) {
//                result = 40;
//            }
//            result = 20;
//        }


        return value;
    }

//    public static Card newTopDeck(CardDeck cardDeck, Card topDeck) {
//        topDeck = cardDeck.shuffleDeckCards.peek();
//        topDeck.turned = true;
//        topDeck.setSize(GameScreen.CARD_WIDTH, GameScreen.CARD_HEIGHT);
//        topDeck.setPosition(-1.05f, -0.5f);
//        return topDeck;
//    }

//    @Override
//    public void draw(SpriteBatch batch){
//        super.render();
//        if(this.turned){
//            this.back.draw(batch);
//        } else {
//            this.front.draw(batch);
//        }

}
