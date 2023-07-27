package com.example.tarok.bots.botBrains;

import com.example.tarok.bots.BotBrain;
import com.example.tarok.bots.cardPlayingRules.SuiteCardRule;
import com.example.tarok.bots.cardPlayingRules.TarotCardRule;
import com.example.tarok.gameObjects.Card;
import com.example.tarok.utility.DeckUtils;
import com.example.tarok.utility.PlayedCard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class NegativeGameModeBrain implements BotBrain {

    /**
     * The player(s) which the bot KNOWS is in his team
     * 1-4 if the teamMate is known
     * 0 if the teamMate is unknown
     */
    private Set<Integer> teamMates;

    public NegativeGameModeBrain(){
        this.teamMates = new HashSet<>();
    }

    /**
     * Decides what card to play in case a negative game mode is selected:
     * If the table is empty, play an arbitrary 1-point card
     * If the bot is able to pick up, it should pick up with the lowest-point card it ca
     * If the bot is unable to pick up, it should play the highest-point card
     * in order to get rid of it
     * @param table hands currently on the table
     * @param deck the bot's deck
     * @return the card to be played
     */
    @Override
    public Card playCard(List<PlayedCard> table, List<Card> deck) {
        List<Card> usableCards = DeckUtils.getLegalCardsNegative(deck, table);

        if(table.size() == 0){
            //Shuffle suites and tarots before sort
            //So that one point tarots and one point suites are both good
            Collections.shuffle(usableCards);
            usableCards.sort(Comparator.comparingInt(Card::getPoints));
            return usableCards.get(0);
        } else {
            List<Card> temporaryCards = new ArrayList<>(table.stream()
                    .map(card -> card.card)
                    .collect(Collectors.toList()));
            temporaryCards.add(usableCards.get(0));

            usableCards.sort(Comparator.comparingInt(Card::getPoints));

            // if the bot is able to pick up the current table, play the lowest point winning card
            if(DeckUtils.getWinningCard(temporaryCards).equals(usableCards.get(0))){
                return usableCards.get(0);
            } else {
                // if the bot cannot pick, play the highest point card, thus making someone else
                // pick up a higher number of points
                return usableCards.get(usableCards.size() - 1);
            }
        }
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
