package ru.mavr;

import com.badlogic.gdx.Game;

import java.util.ArrayList;

public class MavrGame extends Game {

    private Player player;
    public CardDeck cardDeck;
    private Card lastCard;
    public static int playersCount = 4;
    public GameScreen gameScreen;

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
        Nine("9", 9, 9),
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
        ArrayList<Player> players = new ArrayList<Player>();
        //Create Game table and render
        createPlayers(playersCount, players);
        cardDeck = new CardDeck(players);
        lastCard = cardDeck.shuffleDeckCards.pop();
        cardDeck.playedCards.add(lastCard);
        gameScreen = new GameScreen(this, cardDeck, players);
        this.setScreen(gameScreen);

//        this.setScreen(new MenuScreen());
    }

    private void createPlayers(int playersCount, ArrayList<Player> players) {
        for (int i = 1; i <= playersCount; i++) {
            player = new Player("Player" + i);
            if (i == playersCount) {
                // Human player
                player.type = true;
            }
            players.add(player);
        }
    }
}