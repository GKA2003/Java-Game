import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * A public class that creates the pack of cards from the pack file specified by the user.
 *
 * @author 221808, 202859
 * @version 1.0
 *
 */
public class Pack {

    // Array containing all cards in the pack.
    private final Card[] cards;

    /**
     * Constructor that populates the pack of cards from the pack file.
     *
     * @param numPlayers The number of players provided by the user as an integer.
     * @param filename The relative path to the pack file provided by the user.
     * @throws IOException If there is an error reading the file.
     * @throws IllegalArgumentException If the file contains too many, too few, or non-integer values.
     */
    public Pack(int numPlayers, String filename) throws IOException, IllegalArgumentException {
        BufferedReader reader;
        int expectedCards = 8 * numPlayers;
        int numCards = 0;
        this.cards = new Card[expectedCards];

        // Create reader object
        reader = new BufferedReader(new FileReader(filename));
        String line;

        // Read each line of the file until the end is reached
        try {
            while ((line = reader.readLine()) != null) {
                numCards++;
                if (numCards > expectedCards) {
                    throw new IllegalArgumentException("This pack contains too many cards for " + numPlayers +
                            " players. The pack should only contain " + expectedCards + " cards for this number of players.");
                }
                this.cards[numCards - 1] = new Card(Integer.parseInt(line));
            }
            // The end of the file has been reached, so check the number of cards isn't less than expected
            if (numCards < expectedCards) {
                throw new IllegalArgumentException("This pack contains too few cards for " + numPlayers +
                        " players. The pack should contain " + expectedCards + " cards for this number of players.");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("The pack contains a non-integer value.");
        } finally {
            reader.close();
        }
    }

    /**
     * Method to return the full pack of cards.
     *
     * @return Card[] The pack of cards.
     */
    public Card[] getCards() {
        return cards;
    }
}
