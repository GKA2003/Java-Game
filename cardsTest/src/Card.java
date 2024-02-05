/**
 * A public class that stores the value of each card object.
 *
 * @author 221808, 202859
 * @version 1.0
 *
 */
public class Card {
    private final int value;

    /**
     * Constructor that sets the value of the Card object.
     *
     * @param value An integer
     */
    public Card (int value) {
        this.value = value;
    }

    /**
     * Gets the value of the Card object.
     *
     * @return Integer value of Card object.
     */
    public synchronized int getValue () {
        return value;
    }

    /**
     * Returns the value of the Card object as a string.
     *
     * @return String value of Card object.
     */
    @Override
    public String toString () {
        return Integer.toString(value);
    }
}
