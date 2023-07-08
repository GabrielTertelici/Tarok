package com.example.tarok.bots;

import com.example.tarok.gameObjects.Card;

import java.util.List;

public interface BotCardDroppingRule {

    /**
     * Drops numCardsToDrop cards from own hand after picking from talon
     * @param deck own hand
     * @param numCardsToDrop number of cards to drop
     * @return list of dropped cards
     */
    List<Card> dropCards(List<Card> deck, int numCardsToDrop);
}
