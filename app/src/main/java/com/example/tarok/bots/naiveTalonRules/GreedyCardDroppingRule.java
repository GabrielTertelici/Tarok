package com.example.tarok.bots.naiveTalonRules;

import com.example.tarok.bots.BotCardDroppingRule;
import com.example.tarok.gameObjects.Card;
import com.example.tarok.utility.CardComparator;

import java.util.ArrayList;
import java.util.List;

public class GreedyCardDroppingRule implements BotCardDroppingRule {


    /**
     * Sorts the cards and then picks the highest legal ones
     * @param deck own hand
     * @param numCardsToDrop number of cards to drop
     * @return list of dropped cards
     */
    @Override
    public List<Card> dropCards(List<Card> deck, int numCardsToDrop) {
        deck.sort(new CardComparator<Card>());

        List<Card> result = new ArrayList<>();

        for(Card card : deck){
            if (result.size() == numCardsToDrop){
                return result;
            }
            if(card.getPoints() < 5){
                result.add(card);
            }
        }

        return result;
    }
}
