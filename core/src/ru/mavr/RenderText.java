package ru.mavr;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.sun.jmx.remote.internal.ArrayNotificationBuffer;

import java.util.ArrayList;

import jdk.nashorn.api.scripting.ScriptObjectMirror;

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

    RenderText(float height, float width, ArrayList<Player> players) {
        // TODO: handle from players count
        this.width = width;
        this.height = height;
//        System.out.println(this.height + "height");
//        System.out.println(this.width + "width");
        generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/AeroMaticsRegular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 32;
        // Soap fo fonts
//		parameter.magFilter = Texture.TextureFilter.Linear;
//		parameter.minFilter = Texture.TextureFilter.Linear;

        this.font = generator.generateFont(parameter);
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
        generator.dispose();
    }
}
