import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * A public class that controls the main aspects of the game, including starting the game, controlling each thread,
 * determining when someone has won, and ending the game.
 *
 * @author 221808, 202859
 * @version 1.0
 *
 */
public class CardGame {
    /**
     * Main method to run the game.
     */
    public static void main(String[] args) {
        BufferedReader reader;
        Boolean valid = false;
        do {
            try {
                // Create reader object
                reader = new BufferedReader(new InputStreamReader(System.in));
                // Get number of players from input
                System.out.println("Please enter the number of players: ");
                int numPlayers = Integer.parseInt(reader.readLine());
                // Get pack file from input
                System.out.println("Please enter the pack filename: ");
                String filename = reader.readLine();
                // Run game
                runGame(numPlayers, filename);
                valid = true;

            } catch (IOException e) {
                System.out.println(e);
            } catch (NumberFormatException e) {
                System.out.println("Please enter an integer value for number of players.");
            } catch (IllegalArgumentException e) {
                System.out.println(e);
            }
        } while (valid == false);
    }

    /**
     * Method for the main game logic that runs once the player number and pack have been entered.
     *
     * @param numPlayers An integer of how many players there are.
     * @param filename A String with the path to the pack file.
     */
    public static void runGame(int numPlayers, String filename) throws IOException {
        // Generate pack
        Pack pack;
        pack = new Pack(numPlayers, filename);

        // Create arrays
        Player[] players = new Player[numPlayers];
        CardDeck[] decks = new CardDeck[numPlayers];

        // Boolean to check if there is a winner
        boolean isWinner = false;
        // Which player won?
        int winner = -1;
        // Turns counter for thread synchronisation
        int turns = 0;

        // Initialise players and decks
        for (int i = 0; i < numPlayers; i++) {
            players[i] = new Player(i + 1); // Player index starts from 1
            decks[i] = new CardDeck(i + 1); // Deck index starts from 1
        }

        // Distribute four cards to each player
        for (int i = 0; i < 4 * numPlayers; i++) {
            players[i % numPlayers].addCardToHand(pack.getCards()[i]);
        }

        // Distribute remaining cards to decks
        for (int i = 4 * numPlayers; i < pack.getCards().length; i++) {
            decks[i % numPlayers].addCardToDeck(pack.getCards()[i]);
        }

        // Start each player's thread
        for (Player player : players) {
            player.start();
        }

        // Check for winner after initial distribution
        for (Player player : players) {
            if (player.sameValueHands() && !isWinner) {
                winner = player.getPlayerNum();
                System.out.println("player " + winner + " wins"); // Declare win
                isWinner = true;
            }
        }

        // Keep taking synchronised turns until a player wins
        while (!isWinner) {
            synchronized (players[turns % numPlayers]) {
                // Draw new card from deck
                Card newCard = decks[turns % numPlayers].removeCardFromDeck();
                // Add the new card to the player hand
                players[turns % numPlayers].addCardToHand(newCard);
                // Choose and discard a card from the player hand
                Card discardCard = players[turns % numPlayers].chooseCardToDiscard(numPlayers);
                // Add the discarded card to the next deck
                decks[(turns + 1) % numPlayers].addCardToDeck(discardCard);
            }

            // Check if a player has won after every turn
            if (players[turns % numPlayers].sameValueHands()) {
                winner = players[turns % numPlayers].getPlayerNum();
                System.out.println("player " + winner + " wins");
                isWinner = true;
            }

            turns++;
        }

        // Player has won, so inform other player threads and decks that the game is over.
        if (isWinner) {
            for (int i = 0; i < numPlayers; i++) {
                synchronized (players[i]) {
                    // Inform each player that someone has won
                    players[i].playerHasWon(winner);
                }
                // Inform each deck that someone has won
                decks[i].playerHasWon();
            }
        }
    }
}
