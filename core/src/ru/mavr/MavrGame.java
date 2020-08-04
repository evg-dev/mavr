package ru.mavr;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.async.AsyncTask;

import java.util.ArrayList;
import java.util.Stack;

public class MavrGame extends Game {

    public CardDeck cardDeck;
    public int playersCount;
    public GameScreen gameScreen;
    public MenuScreen menuScreen;
    public LoadingScreen loadingScreen;
    public SettingsScreen settigsScreen;
    public Preferences prefs;
    public Stack<Card> fullDeckCards;
    public static TextureAtlas atlas;

    public enum Suit {
        HEARTS("hearts"), SPADES("spades"), DIAMONDS("diamonds"), CLUBS("clubs");

        public final String suitName;

        Suit(String suitName) {
            this.suitName = suitName;
        }
    }

    public enum Names {
        Six("6", 6, 6),
        Seven("7", 7, 7),
        Eight("8", 8, 8),
        Nine("9", 0, 9), // Nine is zero!!!
        Ten("10", 10, 10),
        Jack("J", 2, 11),
        Queen("Q", 3, 12),
        King("K", 4, 13),
        Ace("A", 11, 1);

        public final String label;
        public final int defaultValue;
        public final int atlasIndex;

        Names(String label, int defaultValue, int atlasIndex) {
            this.label = label;
            this.defaultValue = defaultValue;
            this.atlasIndex = atlasIndex;
        }
    }

    public AssetManager manager = new AssetManager();

    @Override
    public void create() {

        // Static load
        atlas = new TextureAtlas("cards/carddeck.atlas");

        loadingScreen = new LoadingScreen(this);
        this.setScreen(loadingScreen);
        // Settings loading
        this.prefs = Gdx.app.getPreferences("Preferences");
        this.playersCount = this.prefs.getInteger("playersCount", 2);
        // Override system back
        Gdx.input.setCatchKey(Input.Keys.BACK, true);
        this.cardDeck = new CardDeck(this);
        this.menuScreen = new MenuScreen(this);
        this.setScreen(this.menuScreen);
        // Menu screen
        this.settigsScreen = new SettingsScreen(this);
    }
}