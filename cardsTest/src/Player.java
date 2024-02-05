import java.io.File;
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;
import java.util.Objects;

/**
 * A public class that extends Thread. It contains all the methods required by a player who is running the game.
 *
 * @author 221808, 202859
 * @version 1.0
 *
 */
public class Player extends Thread {

    // Declare required attributes for the player
    private final int playerNum;
    private ArrayList<Card> hand;
    private final String outputPath;
    private int initialHand;
    private int preferredCard;

    /**
     * Constructor that sets the initial attributes and creates the output file ready for writing.
     *
     * @param playerNum An integer with the unique player ID.
     */
    public Player(int playerNum) {
        this.playerNum = playerNum;
        this.hand = new ArrayList<>(); // Initialise the hand as an empty array list
        this.outputPath = "gameOutputFiles/player" + playerNum + "_output.txt"; // Output file based on playerNum
        this.initialHand = 0;
        this.preferredCard = playerNum;

        try {
            // Remove previous output files first
            // Not needed in CardDeck class otherwise the new player output files will be removed
            File directory = new File("./gameOutputFiles");
            directory.mkdir();
            for (File file: Objects.requireNonNull(directory.listFiles())) {
                file.delete();
            }

            // Create empty file
            FileWriter fileWriter = new FileWriter(outputPath);
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("IOException: " + e);
            System.exit(1);
        }
    }

    /**
     * Getter method to get the player number.
     *
     * @return An integer with the unique player number.
     */
    public int getPlayerNum() {
        return playerNum;
    }

    /**
     * Method to print player actions to the output file.
     *
     * @param playerAction A string to be printed to the output file.
     */
    private void writeOutputFile(String playerAction) {
        try {
            FileWriter fileWriter = new FileWriter(outputPath, true); // Append to existing file
            fileWriter.write(playerAction + "\n"); // Write playerAction to file
            fileWriter.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found in: " + outputPath);
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file '" + outputPath + "': " + e);
        }
    }

    /**
     * Method to output hand in required string format.
     *
     * @return A String with the current hand separated by spaces.
     */
    public String handToString() {
        return this.hand.toString().replaceAll("\\[", "")
                .replaceAll("\\]", "").replaceAll(",", "");
    }

    /**
     * Method to add a card to the player hand.
     *
     * @param card A Card object.
     */
    public void addCardToHand(Card card) {
        this.hand.add(card);
        if (this.hand.size() == 4 && this.initialHand == 0) { // Initial hand dealing
            writeOutputFile("player " + this.playerNum + " initial hand is " + handToString());
            this.initialHand = 1;
        } else if (this.initialHand == 1) { // Draw from deck during game
            writeOutputFile("player " + playerNum + " draws a " + card.getValue() + " from deck " + playerNum);
        }
    }

    /**
     * Method to choose which card to discard during turn.
     *
     * @param numPlayers The number of players.
     * @return The Card to discard.
     */
    public Card chooseCardToDiscard(int numPlayers) {
        int cardIndex;
        Random chooseCard = new Random();

        // Keep choosing a random card until it isn't the player's preferred card
        do {
            cardIndex = chooseCard.nextInt(4);
        } while (hand.get(cardIndex).getValue() == preferredCard);

        // Discard the random card that is picked
        Card discardCard = hand.get(cardIndex);
        hand.remove(cardIndex);
        writeOutputFile("player " + playerNum + " discards a " + discardCard.getValue() + " to deck " + ((playerNum % numPlayers) + 1));
        writeOutputFile("player " + playerNum + " current hand is " + handToString());
        return discardCard;
    }

    /**
     * Iteratively checks if all cards in the player's hand have the same value.
     *
     * @return true if all cards in hand are the same, false otherwise.
     */
    public boolean sameValueHands() {
        // Ensure player has exactly 4 cards
        if (this.hand.size() != 4) {
            return false;
        }

        int firstCardValue = this.hand.get(0).getValue(); // Get value of first card in hand

        // Iterate through remaining cards in hand to check for same values
        for (int i = 1; i < this.hand.size(); i++) {
            if (this.hand.get(i).getValue() != firstCardValue) {
                return false; // If not all cards in hand equal
            }
        }
        return true; // If all cards in hand equal
    }

    /**
     * Method called when a player has won the game.
     *
     * @param winner An int of player who has won the game.
     */
    public void playerHasWon(int winner) {
        if (winner == playerNum) {
            // This player has won the game
            writeOutputFile("player " + playerNum + " wins");
            writeOutputFile("player " + playerNum + " exits");
            writeOutputFile("player " + playerNum + " final hand is " + handToString());
        } else {
            // This player has not won the game
            writeOutputFile("player " + winner + " has informed player " + playerNum + " that player " + winner + " has won");
            writeOutputFile("player " + playerNum + " exits");
            writeOutputFile("player " + playerNum + " hand is " + handToString());
        }
    }
}