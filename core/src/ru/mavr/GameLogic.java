package ru.mavr;

public class GameLogic {

    private Player currentPlayer;
    private CardDeck cardDeck;
    private MavrGame game;

    GameLogic(MavrGame game) {
        this.game = game;
    }

    public void aiTurn() {
        if (this.game.gameScreen.currentPlayer.turn) {
            // TODO: Add AI Logic here


            if (this.game.gameScreen.currentPlayer.cards.size() > 0) {
                Card card = this.game.gameScreen.currentPlayer.cards.remove(0);
                card.turned = false;
                this.game.gameScreen.cardDeck.playedCards.add(card);
            }
            this.game.gameScreen.currentPlayer.turn = false;
        }
    }

    public boolean checkClickedCard(Card card) {
        boolean result = false;

        MavrGame.Suit lastPlayedCardSuit = this.game.gameScreen.lastPlayedCard.getSuit();
        int lastPlayedCardValue = this.game.gameScreen.lastPlayedCard.getValue();

        MavrGame.Suit clickedCardSuit = card.getSuit();
        int clickedCardValue = card.getValue();

        if ((clickedCardValue != lastPlayedCardValue) || (lastPlayedCardSuit != clickedCardSuit)) {

        } else {
            result = true;
        }
        // While testing!!!!!
        result = true;
        return result;
    }


    private void logicHandler(Card lastPlayedCard, int counter) {
        int value = lastPlayedCard.getValue();
        MavrGame.Suit suit = lastPlayedCard.getSuit();
        // A
        if (value == 11) {
            counter++;
        }
        // K SPADES
        if (value == 4 && suit == MavrGame.Suit.SPADES) {
//			Player nextPlayer = this.getPlayer(counter++);
//			nextPlayer.cards.add(cardDeck.getCard());
//			nextPlayer.cards.add(cardDeck.getCard());
//			nextPlayer.cards.add(cardDeck.getCard());
//			nextPlayer.cards.add(cardDeck.getCard());
            counter++;
        }

    }

}
