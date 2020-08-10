package ru.mavr;

public class GameLogic {

    private Player currentPlayer;
    private CardDeck cardDeck;
    private MavrGame game;

    GameLogic(MavrGame game) {
        this.game = game;
    }

    /**
     * Main turn game
     *
     * @param game MavrGame
     */
    public static void gameTurn(MavrGame game) {
        game.gameScreen.currentPlayer = game.gameScreen.players.get(game.gameScreen.currentPlayerIndex);

        game.gameScreen.currentPlayer.turn = true;

        if (game.gameScreen.currentPlayer.type) {
            humanTurn(game);
        } else {
            aiTurn(game);
        }
    }

    public static void endTurnHandling(MavrGame game) {
//        gameTurn(game);
//		System.out.println("Current Player" + currentPlayer.name);
        System.out.println("CadrDeck count : " + game.cardDeck.shuffleDeckCards.size());
        game.gameScreen.currentPlayer.turn = false;
        if (checkEndRound(game)) {
            // Refresh counted
            // TODO: random first turn
//			this.currentPlayerIndex = game.playersCount - 1;
            game.gameScreen.currentPlayer = game.gameScreen.players.get(game.gameScreen.currentPlayerIndex);
            game.gameScreen.currentPlayer.turn = true;

            if (!game.gameScreen.currentPlayer.type) {
                aiTurn(game);
            }
        } else {
            game.gameScreen.currentPlayerIndex++;
            if (game.gameScreen.currentPlayerIndex > game.playersCount - 1) {
                game.gameScreen.currentPlayerIndex -= game.playersCount;
            }
            game.gameScreen.currentPlayer = game.gameScreen.players.get(game.gameScreen.currentPlayerIndex);
            game.gameScreen.currentPlayer.turn = true;
            if (!game.gameScreen.currentPlayer.type) {
                aiTurn(game);
            }
        }
    }

    public static void humanTurn(MavrGame game) {
//        endTurnHandling(game);
    }

    public static void aiTurn(MavrGame game) {
        if (game.gameScreen.currentPlayer.turn) {
            // TODO: Add AI Logic here
            if (game.gameScreen.currentPlayer.cards.size() > 0) {
                Card card = game.gameScreen.currentPlayer.cards.remove(0);
//                card.turned = false;
                game.gameScreen.cardDeck.playedCards.add(card);
            }
            endTurnHandling(game);
        }
    }

    private static boolean checkEndRound(MavrGame game) {
        if (game.gameScreen.currentPlayer.cards.size() == 0) {
            System.out.println(game.gameScreen.currentPlayer.name + " Win!!!");
            for (Player player : game.gameScreen.players
            ) {
                for (Card card : player.cards
                ) {
                    player.score += card.countPoint();
                }
                player.cards.clear();
            }
            // TODO: Go to Score screen, Long running

            MavrGame.shuffleDeck(game, game.gameScreen.players);
            game.gameScreen.topDeck = game.cardDeck.shuffleDeckCards.peek();
//			topDeck.turned = true;
            return true;
        } else {
            return false;
        }
    }

    public boolean checkClickedCard(Card card) {
        boolean result = false;

        MavrGame.Suit lastPlayedCardSuit = game.gameScreen.lastPlayedCard.getSuit();
        int lastPlayedCardValue = game.gameScreen.lastPlayedCard.getValue();

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
