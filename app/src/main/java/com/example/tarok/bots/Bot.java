package com.example.tarok.bots;

import android.util.Log;

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
    private Random r;
    private SuiteCardRule suiteRule;
    private TarotCardRule tarotRule;

    public Bot(List<Card> deck) {
        this.deck = deck;
        teamMates = new HashSet<>();
        suiteRule = new SuiteCardRule();
        tarotRule = new TarotCardRule();
        r = new Random();
    }

    /**
     * The bot will decide which card to play next
     * @param table The cards on the table
     * @return The card the bot will play
     */
    public Card playCard(List<PlayedCard> table){
        Card c;
        Card bottomCard = table.size()>0 ? table.get(0).card : null;
        List<Card> usableCards = DeckUtils.getLegalCards(deck,bottomCard);

        //When starting -> play lowest point card
        if(table.size()==0){
            //Shuffle suites and tarots before sort
            //So that one point tarots and one point suites are both good
            Collections.shuffle(usableCards);
            usableCards.sort(Comparator.comparingInt(Card::getPoints));
            c = usableCards.get(0);
        }
        else if(DeckUtils.hasCardOfSuite(usableCards, CardSuite.Tarot)){
            c = tarotRule.sortedList(usableCards,table,teamMates).get(0);
        }
        else
            c = suiteRule.sortedList(usableCards,table,teamMates).get(0);
        deck.remove(c);
        return c;
    }

    public void setTeamMate(int teamMate) {
        teamMates.add(teamMate);
    }

    /**
     * Getter for teamMates field, to facilitate observability for testing
     * @return the set of teammate ids
     */
    public Set<Integer> getTeammates(){
        return this.teamMates;
    }
}
