package com.example.tarok.bots;

import com.example.tarok.bots.cardPlayingRules.SuiteCardRule;
import com.example.tarok.bots.cardPlayingRules.TarotCardRule;
import com.example.tarok.gameObjects.Card;
import com.example.tarok.utility.CardSuite;
import com.example.tarok.utility.DeckUtils;
import com.example.tarok.utility.PlayedCard;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Bot {
    private List<Card> deck;
    /**
     * The player(s) which the bot KNOWS is in his team
     * 1-4 if the teamMate is known
     * 0 if the teamMate is unknown
     */
    private Set<Integer> teamMates;
    private SuiteCardRule suiteRule;
    private TarotCardRule tarotRule;

    private BotBrain botBrain;

    public Bot(List<Card> deck, BotBrain botBrain) {
        this.deck = deck;
        teamMates = new HashSet<>();
        suiteRule = new SuiteCardRule();
        tarotRule = new TarotCardRule();

        this.botBrain = botBrain;
    }

    /**
     * The bot will decide which card to play next
     * @param table The cards on the table
     * @return The card the bot will play
     */
    public Card playCard(List<PlayedCard> table){
        return botBrain.playCard(table, deck);
    }

    public void setTeamMate(int teamMate) {
        teamMates.add(teamMate);
        botBrain.addTeamMate(teamMate);
    }

    /**
     * Getter for teamMates field, to facilitate observability for testing
     * @return the set of teammate ids
     */
    public Set<Integer> getTeammates(){
        return this.teamMates;
    }
}
