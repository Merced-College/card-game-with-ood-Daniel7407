/*
 * Daniel Pulido-Alaniz
 * 7/3/2025
 * Card class
 * -----------
 * Represents a playing card with a suit, name, value, and picture.
 * Includes:
 *  - Default and parameterized constructors
 *  - Accessors (getters) and mutators (setters) for all fields
 *  - toString() method for readable output
 *  - equals() method for comparing cards
 * 
 * This class was added to support the card game logic and make the code work.
 */
public class Card {
    private String suit;
    private String name;
    private int value;
    private String picture;

    // Default constructor
    public Card() {
        this.suit = "";
        this.name = "";
        this.value = 0;
        this.picture = "";
    }

    // Parameterized constructor
    public Card(String suit, String name, int value, String picture) {
        this.suit = suit;
        this.name = name;
        this.value = value;
        this.picture = picture;
    }

    // Accessors (getters)
    public String getSuit() {
        return suit;
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }

    public String getPicture() {
        return picture;
    }

    // Mutators (setters)
    public void setSuit(String suit) {
        this.suit = suit;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    // toString method
    @Override
    public String toString() {
        return name + " of " + suit + "s (Value: " + value + ", Picture: " + picture + ")";
    }

    // Overriding equals method to compare two Card objects
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Card card = (Card) obj;
        return value == card.value &&
               suit.equals(card.suit) &&
               name.equals(card.name) &&
               picture.equals(card.picture);
    }
}