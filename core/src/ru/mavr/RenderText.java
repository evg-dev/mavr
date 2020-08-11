package ru.mavr;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import java.util.ArrayList;

public class RenderText {

    private final GlyphLayout LabelPlayer1;
    private final GlyphLayout LabelPlayer2;
    private final int count;
    private GlyphLayout LabelPlayer3;
    private GlyphLayout LabelPlayer4;
    private float height;
    private float width;
    private FreeTypeFontGenerator generator;
    private BitmapFont font;

    RenderText(float height, float width, ArrayList<Player> players, BitmapFont font) {
        // TODO: handle from players count
        this.width = width;
        this.height = height;
        this.font = font;
        this.count = players.size();

        this.LabelPlayer1 = new GlyphLayout();
        this.LabelPlayer1.setText(font, players.get(0).name + " 0: " + players.get(0).score);

        this.LabelPlayer2 = new GlyphLayout();
        this.LabelPlayer2.setText(font, players.get(1).name + " 1: " + players.get(1).score);

        if (count > 2) {
            this.LabelPlayer3 = new GlyphLayout();
            this.LabelPlayer3.setText(font, players.get(2).name + " 2: " + players.get(2).score);
        }

        if (count == 4) {
            this.LabelPlayer4 = new GlyphLayout();
            this.LabelPlayer4.setText(font, players.get(3).name + " 3: " + players.get(3).score);
        }
    }

    public void draw(SpriteBatch spriteBatch) {
        // Down(Human)
        this.font.draw(spriteBatch, LabelPlayer1, 0, -(this.height / 2) + 50);
        // Up
        if (this.count == 2) {
            this.font.draw(spriteBatch, LabelPlayer2, 0, (this.height / 2) - 15);
        } else {
            this.font.draw(spriteBatch, LabelPlayer2, -((this.width / 2) - 15), (this.height / 2) - 15);
        }
        // Left
        if (LabelPlayer3 != null) {
            this.font.draw(spriteBatch, LabelPlayer3, 0, (this.height / 2) - 15);
        }
        // Right
        if (LabelPlayer4 != null) {
            this.font.draw(spriteBatch, LabelPlayer4, ((this.width / 2) - (15 + LabelPlayer4.width)),
                    (this.height / 2) - 15);
        }
//        spriteBatch.dispose();
    }
}
