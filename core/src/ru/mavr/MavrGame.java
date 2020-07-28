package ru.mavr;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import java.util.ArrayList;

public class MavrGame extends Game {

    public CardDeck cardDeck;
    public static int playersCount = 2;
    public GameScreen gameScreen;
    public MenuScreen menuScreen;

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
        Gdx.input.setCatchKey(Input.Keys.BACK, true);// Override system back
        ArrayList<Player> players = new ArrayList<Player>();
        //Create Game table and render
        createPlayers(playersCount, players);
        cardDeck = new CardDeck(players);
        Card lastCard = this.cardDeck.shuffleDeckCards.pop();
        // TODO: first playedCards from player
        cardDeck.playedCards.add(lastCard);
        this.gameScreen = new GameScreen(this, cardDeck, players);
        menuScreen = new MenuScreen(this);
        this.setScreen(menuScreen);
//        this.setScreen(gameScreen);
    }

//    private void createNewGame() {
//
//    }

    private void createPlayers(int playersCount, ArrayList<Player> players) {
        for (int i = 1; i <= playersCount; i++) {
            Player player = new Player("Player" + i);
            if (i == playersCount) {
                // Human player
                player.type = true;
            }
            players.add(player);
        }
    }
}