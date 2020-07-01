package ru.mavr;

import java.util.ArrayList;

public class Player {
    public String name;
    public ArrayList<Card> cards;
    private int score;

    Player(String name) {
        this.cards = new ArrayList<Card>();
        this.name = name;
        this.score = 0;
    }

    public int getScore() {
        return this.score;
    }

//    public int getCard() {
//
//    }
//
//    public int pushCard() {
//
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