package com.example.tarok.bots.botBrains;

import com.example.tarok.bots.BotBrain;
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
import java.util.Set;

public class NormalGameModeBrain implements BotBrain {

    private SuiteCardRule suiteRule;
    private TarotCardRule tarotRule;
    /**
     * The player(s) which the bot KNOWS is in his team
     * 1-4 if the teamMate is known
     * 0 if the teamMate is unknown
     */
    private Set<Integer> teamMates;

    public NormalGameModeBrain(SuiteCardRule suiteCardRule, TarotCardRule tarotCardRule){
        this.suiteRule = suiteCardRule;
        this.tarotRule = tarotCardRule;
        this.teamMates = new HashSet<>();
    }

    /**
     * Used in case a normal game mode (not negative) is played, to determine the card
     * to be played
     * @param table hands currently on the table
     * @param deck the bot's deck
     * @return the card to be played
     */
    @Override
    public Card playCard(List<PlayedCard> table, List<Card> deck) {
        Card c;
        List<Card> usableCards = DeckUtils.getLegalCardsNonNegative(deck, table);

        //When starting -> play lowest point card
        if(table.size()==0){
            //Shuffle suites and tarots before sort
            //So that one point tarots and one point suites are both good
            Collections.shuffle(usableCards);
            usableCards.sort(Comparator.comparingInt(Card::getPoints));
            c = usableCards.get(0);
        }
        else if(DeckUtils.hasCardOfSuite(usableCards, CardSuite.Tarot)){
            c = tarotRule.sortedList(usableCards, table, teamMates).get(0);
        }
        else
            c = suiteRule.sortedList(usableCards, table, teamMates).get(0);
        deck.remove(c);
        return c;
    }

    /**
     * Adds a teammate to the set of teammates
     * @param teamMate teammate to be added
     */
    @Override
    public void addTeamMate(int teamMate) {
        this.teamMates.add(teamMate);
    }
}
