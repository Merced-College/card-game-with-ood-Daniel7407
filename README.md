[![Open in Codespaces](https://classroom.github.com/assets/launch-codespace-2972f46106e565e64193e422d61a12cf1da4916b45550586e14ef0a7c637dd04.svg)](https://classroom.github.com/open-in-codespaces?assignment_repo_id=19891521)

# Card Game with OOD

## Overview

This Java program simulates two card games:

1. **Pairs Check**  
   The player is dealt 4 cards and the program checks if there is a pair (two cards of the same kind).  
   *(This feature was already present in the original code.)*

2. **Blackjack Game**  
   The player plays a simple game of Blackjack against a dealer.  
   - The player and dealer each get two cards.
   - The dealer's first card is shown.
   - The player can choose to "hit" (draw another card) or "stand" (end their turn).
   - The program displays the player's hand value and the probability of busting if they hit (rounded to two decimals).
   - The dealer draws until reaching at least 17, and the winner is determined.

   *These Blackjack features and the bust probability calculation were my additions.*

## Additional Implementation Notes

- I added a `Card` class with constructors, accessors (getters), mutators (setters), and a `toString()` method to make the code work and to represent each card's suit, name, value, and picture.
- The code validates user input for the Blackjack game, only accepting `h` (hit) or `s` (stand).
- The bust probability is shown as a percentage with two decimal places after multiplying by 100.

## Author

Daniel Pulido-Alaniz  
Date: 7/3/2025