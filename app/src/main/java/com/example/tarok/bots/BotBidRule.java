package com.example.tarok.bots;

import com.example.tarok.gameObjects.Card;

import java.util.List;

public interface BotBidRule {
    /**
     * Returns an int value representing what bid they want to make
     * ints are used instead of PlayModes because they are easier to manipulate
     * and can be converted to PlayModes with a switch statement
     * @param deck the deck based on which the decision will be made
     * @param currentLowestBid integer representing the current lowest bid made
     * @return int representing what PlayMode has been chosen
     */
     int decideWhatToPlayFor(List<Card> deck, int currentLowestBid);
}
