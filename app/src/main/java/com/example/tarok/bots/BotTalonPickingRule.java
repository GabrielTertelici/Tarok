package com.example.tarok.bots;

import com.example.tarok.gameObjects.Card;

import java.util.List;

public interface BotTalonPickingRule {

    /**
     * Lets bot pick a Card from the talon, essentially emulating a click on a card like a human would do.
     * The choice is based on the deck of the bot and the talon
     * @param deck deck of the bot making the choice
     * @param talon the current talon
     * @return the Card the user would click on
     */
    Card pickCardFromTalon(List<Card> deck, List<Card> talon);
}
