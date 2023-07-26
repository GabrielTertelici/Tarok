package com.example.tarok.bots;

import com.example.tarok.gameObjects.Card;

import java.util.List;
import java.util.Random;

public class NaiveKingPickingRule implements BotKingPickingRule{

    private Random random;

    public NaiveKingPickingRule(Random random){
        this.random = random;
    }

    /**
     * Picks a random king with equal probability
     * @param deck the bot's hand
     * @return int representing the selected king:
     * 0 -> clubs
     * 1 -> hearts
     * 2 -> spades
     * 3 -> diamonds
     */
    @Override
    public int pickKing(List<Card> deck){
        return random.nextInt(4);
    }
}
