package com.example.tarok.bots;

import com.example.tarok.gameObjects.Card;
import java.util.List;

public interface BotKingPickingRule {

    /**
     * Returns an int representing which king this bot has chosen, based on the bot's hand
     * @param deck the bot's hand
     * @return an int representing the chosen king:
     * 0 -> club
     * 1 -> heart
     * 2 -> spade
     * 3 -> diamond
     */
    int pickKing(List<Card> deck);
}
