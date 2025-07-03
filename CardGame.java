/*
 * Daniel Pulido-Alaniz
 * 7/3/2025
 * This program simulates two card games:
 * 1. A "pairs" check where the player is dealt 4 cards and checks for pairs.
 *    (This feature was already present.)
 * 2. A Blackjack game where the player plays against a dealer.
 *    (I added this feature, along with the bust probability calculation.)
 * 
 * In the Blackjack game, the player can see their hand value and the probability
 * of busting if they hit, rounded to two decimals. Input is validated to only
 * accept 'h' (hit) or 's' (stand).
 */

//package cardGame;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class CardGame {

    // The main deck of cards used in the game
    private static ArrayList<Card> deckOfCards = new ArrayList<Card>();
    // Cards dealt to the player for the "pairs" check
    private static ArrayList<Card> playerCards = new ArrayList<Card>();

    // Hands for the Blackjack game
    private static ArrayList<Card> playerHand = new ArrayList<Card>();
    private static ArrayList<Card> dealerHand = new ArrayList<Card>();

    public static void main(String[] args) {

        Scanner input = null;
        try {
            // Try to open the cards.txt file to read the deck
            input = new Scanner(new File("cards.txt"));
        } catch (FileNotFoundException e) {
            // Print error if file is not found
            System.out.println("error");
            e.printStackTrace();
        }

        // Read each line from the file and create Card objects
        while(input.hasNext()) {
            String[] fields  = input.nextLine().split(",");
            // Each line should be: suit, name, value, picture
            Card newCard = new Card(fields[0], fields[1].trim(),
                    Integer.parseInt(fields[2].trim()), fields[3]);
            deckOfCards.add(newCard);    
        }

        // Shuffle the deck before dealing
        shuffle();

        // Deal the player 4 cards for the "pairs" check
        for(int i = 0; i < 4; i++) {
            playerCards.add(deckOfCards.remove(i));
        }
        
        System.out.println("players cards");
        for(Card c: playerCards)
            System.out.println(c);

        // Check if the player has a pair (2 of a kind)
        System.out.println("pairs is " + checkFor2Kind());

        /*
		 * MY ADDITION
		 * 
         * Blackjack game section
         * The player and dealer each receive two cards.
         * The dealer's first card is shown to the player.
         * The player can then choose to hit or stand.
         */

        System.out.println("----------------------------------------");
        System.out.println("\nBlackjack Game"); 

        // Deal two cards each to player and dealer
        for (int i = 0; i < 2; i++) {
            playerHand.add(deckOfCards.remove(0));
            dealerHand.add(deckOfCards.remove(0));
        }

        // Show the dealer's first card (upcard)
        System.out.println("Dealer's upcard: " + dealerHand.get(0) + "\n");

        // Show the player's hand
        for (Card c: playerHand) {
            System.out.println("Player's card: " + c);
        }

        // Show the player's hand value and bust probability
        System.out.println("Your hand value is: " + getHandValue(playerHand));
        System.out.println("Probability of busting if you hit: " + getBustProbability(playerHand, deckOfCards));

        // Start the player's turn
        System.out.println("Do you want to hit or stand? (h/s)");
        Scanner scanner = new Scanner(System.in);
        String choice = scanner.nextLine().trim().toLowerCase();

        // Loop until the user chooses to stand or busts
        while (true) {
            // Validate input: only accept "h" or "s"
            while (!choice.equals("h") && !choice.equals("s")) {
                System.out.println("Invalid input. Please enter 'h' to hit or 's' to stand:");
                choice = scanner.nextLine().trim().toLowerCase();
            }

            if (choice.equals("h")) {
                // Player chooses to hit (draw a card)
                if (deckOfCards.isEmpty()) {
                    System.out.println("No more cards in the deck!");
                    break;
                }
                Card newCard = deckOfCards.remove(0);
                playerHand.add(newCard);
                System.out.println("You drew: " + newCard);
                int handValue = getHandValue(playerHand);
                System.out.println("Your hand value is: " + handValue);
                if (handValue > 21) {
                    // Player busts if hand value exceeds 21
                    System.out.println("You busted! Game over.");
                    return;
                }
                // Show updated hand value and bust probability
                System.out.println("Your hand value is: " + getHandValue(playerHand));
                System.out.println("Probability of busting if you hit: " + getBustProbability(playerHand, deckOfCards));
                System.out.println("Do you want to hit or stand? (h/s)");
                choice = scanner.nextLine().trim().toLowerCase();
            } else if (choice.equals("s")) {
                // Player chooses to stand, now dealer's turn
                System.out.println("You chose to stand.");
                int dealerHandValue = getHandValue(dealerHand);
				System.out.println("\nOther dealer's card: " + dealerHand.get(1));
                System.out.println("Dealer's hand value: " + dealerHandValue);
                // Dealer draws cards until hand value is at least 17
                while (dealerHandValue < 17) {
                    Card newCard = deckOfCards.remove(0);
                    dealerHand.add(newCard);
                    dealerHandValue = getHandValue(dealerHand);
                    System.out.println("Dealer drew: " + newCard);
                    System.out.println("Dealer's hand value is now: " + dealerHandValue);
                }
				System.out.println(""); 
                // Determine the outcome of the game
                if (dealerHandValue > 21) {
                    System.out.println("Dealer busted! You win!");
                } else if (dealerHandValue > getHandValue(playerHand)) {
                    System.out.println("Dealer wins!");
                } else if (dealerHandValue < getHandValue(playerHand)) {
                    System.out.println("You win!");
                } else {
                    System.out.println("It's a tie!");
                }
                break;
            }
        }
    }//end main

    // Shuffle the deck by randomly removing and reinserting cards
    public static void shuffle() {
        for (int i = 0; i < deckOfCards.size(); i++) {
            int index = (int) (Math.random()*deckOfCards.size());
            Card c = deckOfCards.remove(index);
            deckOfCards.add(c);
        }
    }

    /*
	 * MY ADDITION
	 * 
     * Calculates the probability of busting based on the player's current hand
     * and the remaining cards in the deck.
     * 
     * @param playerCards The player's current hand of cards.
     * @param deckOfCards The remaining cards in the deck.
     * @return The probability of busting as a float between 0 and 1.
     */
    public static float getBustProbability(ArrayList<Card> playerCards , ArrayList<Card> deckOfCards) {
		int playerHandValue = getHandValue(playerCards);
	
		int bustCount = 0; 
	
		// Count how many cards in the deck would cause the player to bust
		for(Card c: deckOfCards) {
			if(playerHandValue + c.getValue() > 21) {
				bustCount++;
			}
		}
	
		// Calculate probability, multiply by 100, round to two decimals, then divide by 100f
		float probability = bustCount / (float) deckOfCards.size();
		probability = Math.round(probability * 10000f) / 100f; // two decimals after multiplying by 100
		return probability;
	}

    /*
	 * MY ADDITION
	 * 
     * Calculates the total value of the player's hand.
     * 
     * @param currentHand The player's current hand of cards.
     * @return The total value of the hand as an integer.
     */
    public static int getHandValue(ArrayList<Card> currentHand) {
        int handValue = 0;
        for(Card c: currentHand) {
            handValue += c.getValue();
        }
        return handValue;
    }

    // Check for 2 of a kind in the player's hand (pair)
    public static boolean checkFor2Kind() {

        int count = 0;
        for(int i = 0; i < playerCards.size() - 1; i++) {
            Card current = playerCards.get(i);
            Card next = playerCards.get(i+1);
            
            for(int j = i+1; j < playerCards.size(); j++) {
                next = playerCards.get(j);
                // Compare current card to the next card
                if(current.equals(next))
                    count++;
            }//end of inner for
            if(count == 1)
                return true;

        }//end outer for
        return false;
    }
}//end class