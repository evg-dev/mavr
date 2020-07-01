package ru.mavr;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class Card {

    private final Sprite backSprite;
    private final Sprite topSprite;
    //    public final Suit suit;
    public Sprite sprite;
    private final MavrGame.Suit suit;
    TextureAtlas atlas;
    public boolean turned; //back upside = true

    public float X;
    public float Y;


    public Card(MavrGame.Suit suit, float X, float Y, boolean turned, int index) {

        this.atlas = GameScreen.getAtlas();
        this.turned = turned;
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

    public void update(boolean turned) {
        if (this.turned) {
            this.backSprite.setPosition(X, Y);
        } else {
            this.topSprite.setPosition(X, Y);
        }
    }

    public void draw(SpriteBatch spriteBatch) {
        if (this.turned) {
            this.backSprite.draw(spriteBatch);
        } else {
            this.topSprite.draw(spriteBatch);
        }
    }

    public float getX() {
        return this.sprite.getX();
    }

    public float getY() {
        return this.sprite.getY();
    }

    public float getWidth() {
        return this.sprite.getWidth();
    }

    public float getHeight() {
        return this.sprite.getHeight();
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
