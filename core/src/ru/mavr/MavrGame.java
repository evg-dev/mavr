package ru.mavr;

import com.badlogic.gdx.Game;

import java.util.ArrayList;
import java.util.Stack;
//import com.badlogic.gdx.Game;

public class MavrGame extends Game {

    private Player player;
    private Player player2;
    public CardDeck cardDeck;
    private Card lastCard;
    public static int playersCount = 2;

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
//        player1 = new Player("Player1");
//        player2 = new Player("Player2");
//        players.add(player2);
//        players.add(player1);
        createPlayers(playersCount, players);
        cardDeck = new CardDeck(players);
        lastCard = cardDeck.shuffleDeckCards.pop();
        cardDeck.playedCards.add(lastCard);

        this.setScreen(new GameScreen(this, cardDeck, players));
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