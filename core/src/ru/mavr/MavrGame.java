package ru.mavr;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;

import java.util.ArrayList;
import java.util.Stack;

public class MavrGame extends Game {

    public CardDeck cardDeck;
    public int playersCount;
    public GameScreen gameScreen;
    public MenuScreen menuScreen;
    public SettingsScreen settigsScreen;
    public Preferences prefs;
    public Stack<Card> fullDeckCards;

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

    @Override
    public void create() {
        // Settings loading
        this.prefs = Gdx.app.getPreferences("Preferences");
        this.playersCount = this.prefs.getInteger("playersCount", 2);
        // Override system back
        Gdx.input.setCatchKey(Input.Keys.BACK, true);

        this.cardDeck = new CardDeck(this);
        // Menu screen
        this.menuScreen = new MenuScreen(this);
        this.settigsScreen = new SettingsScreen(this);
        this.setScreen(menuScreen);
    }
}