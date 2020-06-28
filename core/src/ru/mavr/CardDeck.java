package ru.mavr;

import java.util.ArrayList;
import java.util.Stack;

public class CardDeck {
    private static Stack<Card> cardShuffle;
    private Stack<Card> deckCards;
    private Stack<Card> retreatCards;
    private ArrayList<Card> deckCardsArray;
    private Stack<Card> shuffleDeckCards;

    CardDeck() {
//        deckCardsArray = new ArrayList<Card>();
        deckCards = new Stack<Card>();
        for (MavrGame.Suit suit : MavrGame.Suit.values()
        ) {
//            System.out.println(suit.suitName);
            for (MavrGame.Names name : MavrGame.Names.values()
            ) {
                deckCards.add(new Card(suit, 0, 0, false, name.atlasIndex));
            }

        }
        shuffleDeckCards = shuffleDeck(deckCards);
//        return shuffleDeckCards;
    }


//        Random rand = new Random();
//        int randomElement = givenList.get(rand.nextInt(givenList.size()));
//        System.out.println(deckCards.size());

    static public Stack<Card> shuffleDeck(Stack<Card> CardShuffle) {
        cardShuffle = CardShuffle;
        return CardShuffle;
    }

    public ArrayList<Card> getCardToPlayer() {
//        this.
//        return
    }


}
