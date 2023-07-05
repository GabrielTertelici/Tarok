package com.example.tarok.bots;

import com.example.tarok.gameObjects.Card;

import java.util.List;
import java.util.Random;

public class NaiveBidRule implements BotBidRule{

    private Random random;

    public NaiveBidRule(Random random){
        this.random = random;
    }

    /**
     * This rule just generates a random boolean and plays for the
     * next lowest PlayMode if the boolean is true, and skips otherwise
     * @param deck the deck based on which the decision will be made
     * @param currentLowestBid integer representing the current lowest bid made
     * @return int representing what PlayMode has been chosen
     */
    @Override
    public int decideWhatToPlayFor(List<Card> deck, int currentLowestBid){
        if(random.nextBoolean()){
            return  (currentLowestBid + 1);
        } else {
            return currentLowestBid;
        }
    }
}
