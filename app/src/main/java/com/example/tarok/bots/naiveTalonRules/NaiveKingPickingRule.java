package com.example.tarok.bots.naiveTalonRules;

import com.example.tarok.bots.BotKingPickingRule;
import com.example.tarok.gameObjects.Card;

import java.util.List;
import java.util.Random;

public class NaiveKingPickingRule implements BotKingPickingRule {

    private Random random;

    public NaiveKingPickingRule(Random random){
        this.random = random;
    }

    /**
     * Picks a random king with equal probability
     * @param deck the bot's hand
     * @return int representing the selected king:
     * 0 -> heart
     * 1 -> diamond
     * 2 -> club
     * 3 -> spade
     */
    @Override
    public int pickKing(List<Card> deck){
        return random.nextInt(4);
    }
}
