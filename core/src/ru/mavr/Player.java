package ru.mavr;

import java.util.ArrayList;

import jdk.nashorn.internal.IntDeque;

public class Player {
    public boolean turn = false;
    public boolean type = false; // true - human, false - AI
    public String name;
    public ArrayList<Card> cards;
    public int score;

    Player(String name) {
        this.cards = new ArrayList<Card>();
        this.name = name;
        this.score = 0;
        this.turn = false;
    }

    public int getScore() {
        return this.score;
    }

    public Card getCard() {
        if (!this.cards.isEmpty()) {
            return cards.get(0);
        } else {
            return null;
        }
    }

//    public Card getDeckCard() {
//        if (CardDeck. .isEmpty()) {
//            return cards.get(0);
//        } else {
//            return null;
//        }
//    }

//    public Card getCardByCoordinates(float x, float y) {
//        float coordinates [][] = {};
//        for (Card card: this.cards
//             ) {
//            coordinates[][x, y];
//            x = card.getY();
//            y = card.getY();
//        return card;
//    }

//    public int pushCard() {
//
//    }

//    public Player getPlayer() {
//        return this.players.pop();
//    }

    /**
     * Calculate score after round and check winner
     *
     * @return int
     */
    public int calculateRoundScore() {
        // sum all cards by rules
        return this.score;
    }


}
