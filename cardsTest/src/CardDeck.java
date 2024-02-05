import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * A public class which controls the four decks.
 *
 * @author 221808, 202859
 * @version 1.0
 *
 */
public class CardDeck {
    // Declare required attributes for decks
    private final int deckNum;
    private ArrayList<Card> cards;
    private final String outputPath;

    /**
     * Constructor that sets the initial attributes and creates the output file ready for writing.
     *
     * @param deckNum An integer with the unique deck ID
     */
    public CardDeck(int deckNum) {
        this.deckNum = deckNum;
        this.cards = new ArrayList<>();
        this.outputPath = "gameOutputFiles/deck" + deckNum + "_output.txt"; // Output file based on playerNum

        try {
            File directory = new File("./gameOutputFiles");
            directory.mkdir();
            FileWriter fileWriter = new FileWriter(outputPath);
            fileWriter.close(); // Create empty file
        } catch (IOException e) {
            System.out.println("IOException: " + e);
            System.exit(1);
        }
    }

    /**
     * Adds a card to the bottom of the deck.
     *
     * @param card A Card object to be added to the deck.
     */
    public void addCardToDeck(Card card) {
        cards.add(card);
    }

    /**
     * Removes a card from the top of the deck.
     *
     * @return A Card object of the removed card.
     */
    public Card removeCardFromDeck() {
        return cards.remove(0);
    }

    /**
     * Retrieves the list of cards in the deck.
     *
     * @return The ArrayList of cards in the deck.
     */
    public ArrayList<Card> getCards() {
        return cards;
    }

    /**
     * Method to print deck contents to the output file.
     *
     * @param deckContents A string to be printed to the output file.
     */
    private void writeOutputFile(String deckContents) {
        try {
            FileWriter fileWriter = new FileWriter(outputPath, true); // Append to existing file
            fileWriter.write(deckContents + "\n"); // Write deckContents to file
            fileWriter.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found in: " + outputPath);
            System.exit(1);
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file '" + outputPath + "': " + e);
            System.exit(1);
        }
    }

    /**
     * Method to output deck in required string format.
     *
     * @return A String with the current deck separated by spaces.
     */
    public String deckToString() {
        return this.cards.toString().replaceAll("\\[", "")
                .replaceAll("\\]", "").replaceAll(",", "");
    }

    /**
     * Method called when a player has won the game.
     */
    public void playerHasWon() {
        writeOutputFile("deck " + deckNum + " contents is " + deckToString());
    }

}
