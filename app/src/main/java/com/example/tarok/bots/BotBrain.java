package com.example.tarok.bots;

import com.example.tarok.gameObjects.Card;
import com.example.tarok.utility.PlayedCard;

import java.util.List;

/**
 * Interface for deciding which card to play
 */
public interface BotBrain {

    /**
     * Determines which card the bot should play based on the cards currently
     * on the table and their deck
     * @param table hands currently on the table
     * @param deck the bot's deck
     * @return the card which should be played
     */
    Card playCard(List<PlayedCard> table, List<Card> deck);

    /**
     * Adds the specified teammate represented as an integer to the set of teammates
     * @param teamMate teammate to be added
     */
    void addTeamMate(int teamMate);
}
