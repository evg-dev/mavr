package ru.mavr;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

public class CardDeck {

    public Stack<Card> shuffleDeckCards;
    public Stack<Card> playedCards;

    CardDeck(MavrGame game) {

        this.playedCards = new Stack<Card>();

        this.playedCards = new Stack<Card>();
        if (game.fullDeckCards == null) {
            Stack<Card> deckCards = new Stack<Card>();
            int i = 1;
            for (MavrGame.Suit suit : MavrGame.Suit.values()
            ) {
                for (MavrGame.Names name : MavrGame.Names.values()
                ) {
                    deckCards.add(new Card(suit, 0, 0, false, name.atlasIndex, name.defaultValue));
                    System.out.println("Card Create count :" + i + "  " + suit + "  " + name.atlasIndex);
                    i++;
                }
            }
            game.fullDeckCards = new Stack<Card>();
            game.fullDeckCards.addAll(deckCards);
            this.shuffleDeckCards = shuffleDeck(game.fullDeckCards);
        } else {
            Stack<Card> deckCards = new Stack<Card>();
            deckCards.addAll(game.fullDeckCards);
            this.shuffleDeckCards = shuffleDeck(deckCards);
        }
//        initialCardToPlayers(players, shuffleDeckCards);
    }

    static public Stack<Card> shuffleDeck(Stack<Card> deckCards) {
        Collections.shuffle(deckCards);
        return deckCards;
    }

    /**
     * @param players
     */
    public static void initialCardToPlayers(ArrayList<Player> players, Stack<Card> deckCards) {
        for (int i = 0; i < 5; i++) {
            for (Player player : players
            ) {
                if (deckCards.size() > 0) {
                    Card card = deckCards.pop();

                    if (!player.type) {
                        card.turned = true;
                        System.out.println("player.name : " + player.name);
                    } else {
                        System.out.println("player.name Human : " + player.name);
                        card.turned = false;
                    }
                    player.cards.add(card);
                }
            }
        }
    }

    public Card getCard() {
        System.out.println("shuffleDeckCards :" + this.shuffleDeckCards.size());
        System.out.println("playedCards : " + this.playedCards.size());
        if (!this.shuffleDeckCards.empty()) {
            if (this.shuffleDeckCards.size() == 1) {
                // Last card shuffle deck
                // Show message
                Card lastDeckCard = this.shuffleDeckCards.pop();
                Card lastPlayedCard = this.playedCards.pop();

                Stack<Card> newDeck = new Stack<Card>();
                newDeck.addAll(this.playedCards);

                this.playedCards.removeAllElements();
//                lastPlayedCard.turned = false;
                this.playedCards.add(lastPlayedCard);
                this.shuffleDeckCards = shuffleDeck(newDeck);
//                this.playedCards.push(lastPlayedCard);
                return lastDeckCard;
            }
            return this.shuffleDeckCards.pop();
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
