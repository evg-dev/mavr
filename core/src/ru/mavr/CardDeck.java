package ru.mavr;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

public class CardDeck {
    public ArrayList<Player> players;
    private static Stack<Card> cardShuffle;
    private Stack<Card> retreatCards;
    private ArrayList<Card> deckCardsArray;

    public Stack<Card> shuffleDeckCards;
    public Stack<Card> playedCards;
    MavrGame game;

    CardDeck(ArrayList<Player> players) {
        this.playedCards = new Stack<Card>();
        Stack<Card> deckCards = new Stack<Card>();
        for (MavrGame.Suit suit : MavrGame.Suit.values()
        ) {
            for (MavrGame.Names name : MavrGame.Names.values()
            ) {
                deckCards.add(new Card(suit, 0, 0, false, name.atlasIndex, name.defaultValue));
            }
        }
        shuffleDeckCards = shuffleDeck(deckCards);
        initialCardToPlayers(players);
    }

    static public Stack<Card> shuffleDeck(Stack<Card> deckCards) {
        Collections.shuffle(deckCards);
        return deckCards;
    }

    /**
     * @param players
     */
    public void initialCardToPlayers(ArrayList<Player> players) {
        for (int i = 0; i < 4; i++) {
            for (Player player : players
            ) {
                player.cards.add(this.shuffleDeckCards.pop());
            }
        }
    }

    public Card getCard() {
        if (!this.shuffleDeckCards.empty()) {
            if (this.shuffleDeckCards.size() == 1) {
                // Last card shuffle deck
                // Show message
                Card lastDeckCard = shuffleDeckCards.pop();
                Card lastPlayedCard = this.playedCards.pop();
                this.shuffleDeckCards = shuffleDeck(this.playedCards);
                this.playedCards.push(lastPlayedCard);
                return lastDeckCard;
            }
            return shuffleDeckCards.pop();
        } else {
            return null;
        }
    }

    public Card getCardDeckValue() {
        if (!this.shuffleDeckCards.empty()) {
            return shuffleDeckCards.peek();
        } else {
            return null;
        }

    }
}
