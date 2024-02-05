import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Random;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * CardDeckTest class to test CardDeck.java.
 *
 * @author 221808, 202859
 * @version 1.0
 */
class CardDeckTest {

    private int deckNum;
    private CardDeck deck;
    Random random = new Random();

    @BeforeEach
    void setUp() {
        // Create a new deck that's available for every test method
        deckNum = random.nextInt(50);
        deck = new CardDeck(deckNum);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testAddCardToDeck() {
        // Generate test card
        int value = random.nextInt(50);
        Card card = new Card(value);
        ArrayList<Card> expectedDeck = new ArrayList<Card>();

        // Add test card to deck
        deck.addCardToDeck(card);
        expectedDeck.add(card);

        // Check it's been added
        assertEquals(expectedDeck, deck.getCards());
    }

    @Test
    void testRemoveCardFromDeck() {
        // Initialise attributes
        int value;
        Card card;
        ArrayList<Card> expectedDeck = new ArrayList<Card>();

        // Add four cards to the deck to mimic game behaviour
        for (int i = 0; i < 4; i++) {
            value = random.nextInt(50);
            card = new Card(value);
            deck.addCardToDeck(card);
            expectedDeck.add(card);
        }

        // Remove card from deck
        deck.removeCardFromDeck();
        expectedDeck.remove(0);

        // Check it's been removed
        assertEquals(expectedDeck, deck.getCards());
    }

    @Test
    void testGetCards() {
        // Initialise attributes
        int value;
        Card card;
        ArrayList<Card> expectedDeck = new ArrayList<Card>();

        // Add four cards to the deck to mimic game behaviour
        for (int i = 0; i < 4; i++) {
            value = random.nextInt(50);
            card = new Card(value);
            deck.addCardToDeck(card);
            expectedDeck.add(card);
        }

        // Check all four cards get returned
        assertEquals(expectedDeck, deck.getCards());

        // Remove card from deck
        deck.removeCardFromDeck();
        expectedDeck.remove(0);

        // Check three cards get returned
        assertEquals(expectedDeck, deck.getCards());
    }

    @Test
    void testWriteOutputFile() throws Exception {
        // Set filename and generate random alphanumeric string
        String filename = "gameOutputFiles/deck" + deckNum + "_output.txt";
        String expectedText = Long.toHexString(Double.doubleToLongBits(Math.random()));

        // Create an instance of Player
        CardDeck cardDeckClass = new CardDeck(deckNum);

        // Obtain the private method using reflection
        Method writeOutputFile = CardDeck.class.getDeclaredMethod("writeOutputFile", String.class);
        writeOutputFile.setAccessible(true);

        // Invoke the private method on the instance
        writeOutputFile.invoke(cardDeckClass, expectedText);

        // Expected line in output file
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line = reader.readLine();
            assertEquals(expectedText, line);
        } catch (Exception e) {
            fail("An exception was thrown: " + e);
        }
    }

    @Test
    void testDeckToString() {
        int value;
        Card card;
        String expectedHandToString = "";

        // Add cards to the deck so that we can test converting the deck to a string
        for (int i = 0; i < 4; i++) {
            value = random.nextInt(50);
            card = new Card(value);
            deck.addCardToDeck(card);
            expectedHandToString = expectedHandToString + value;
            // If not the last card in the hand, we want to add a space
            if (i < 3) {
                expectedHandToString = expectedHandToString + " ";
            }
        }
        assertEquals(expectedHandToString, deck.deckToString());
    }

    @Test
    void testPlayerHasWon() {
        int value;
        Card card;
        String expectedHandToString = "";
        String filename = "gameOutputFiles/deck" + deckNum + "_output.txt";

        // Add cards to the deck to mimic game situation
        for (int i = 0; i < 4; i++) {
            value = random.nextInt(50);
            card = new Card(value);
            deck.addCardToDeck(card);
            expectedHandToString = expectedHandToString + value;
            // If not the last card in the hand, we want to add a space
            if (i < 3) {
                expectedHandToString = expectedHandToString + " ";
            }
        }

        deck.playerHasWon();

        // Expected line in output file
        String expectedOutput = ("deck " + deckNum + " contents is " + expectedHandToString);
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line = reader.readLine();
            assertEquals(expectedOutput, line);
        } catch (Exception e) {
            fail("An exception was thrown: " + e);
        }
    }
}