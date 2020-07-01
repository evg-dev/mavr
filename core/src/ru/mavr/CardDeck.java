package ru.mavr;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

public class CardDeck {
    private Stack<Player> players;
    private static Stack<Card> cardShuffle;
    private Stack<Card> retreatCards;
    private ArrayList<Card> deckCardsArray;

    public Stack<Card> shuffleDeckCards;
    public Stack<Card> playedCards;
    MavrGame game;

    CardDeck(Stack<Player> players) {
//        this.players;
        this.playedCards = new Stack<Card>();
        Stack<Card> deckCards = new Stack<Card>();
        for (MavrGame.Suit suit : MavrGame.Suit.values()
        ) {
            for (MavrGame.Names name : MavrGame.Names.values()
            ) {
                deckCards.add(new Card(suit, 0, 0, false, name.atlasIndex));
            }
        }
        shuffleDeckCards = shuffleDeck(deckCards);
        initialCardToPlayers(players);
    }

//        Random rand = new Random();
//        int randomElement = givenList.get(rand.nextInt(givenList.size()));
//        System.out.println(deckCards.size());

    static public Stack<Card> shuffleDeck(Stack<Card> deckCards) {
        Collections.shuffle(deckCards);
        return deckCards;

    }

    /**
     * @param players
     * @return
     */
    public boolean initialCardToPlayers(Stack<Player> players) {
        for (int i = 0; i < 4; i++) {
            for (Player player : players
            ) {
                player.cards.add(this.shuffleDeckCards.pop());
            }
        }
        return true;
    }
}