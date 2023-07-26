package com.example.tarok.bots.cardPlayingRules;

import com.example.tarok.bots.BotRule;
import com.example.tarok.gameObjects.Card;
import com.example.tarok.utility.DeckUtils;
import com.example.tarok.utility.PlayedCard;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class SuiteCardRule implements BotRule {

    /**
     * This rule will ensure the bot plays a high point suite card
     * If their team can win it (either them or teammate)
     * And will play a low point suite card if not
     */
    public List<Card> sortedList(List<Card> legalCards, List<PlayedCard> tableCards, Set<Integer> teammates) {
        if(tableCards.size()>0 && teamIsWinning(tableCards, teammates)){
            //If they are winning just play biggest point card and hope
            return legalCards.stream().sorted((x,y)->-Integer.compare(x.getPoints(),y.getPoints())).collect(Collectors.toList());
        }
        //Try to win yourself
        List<Card> winningCards = new ArrayList<>();
        List<Card> losingCards = new ArrayList<>();
        List<Card> table = tableCards.stream().map(x->x.card).collect(Collectors.toList());
        for(Card c:legalCards){
            table.add(c);
            if(DeckUtils.getWinningCard(table).equals(c)){
                winningCards.add(c);
            }
            else{
                losingCards.add(c);
            }
            table.remove(c);
        }
        //Sort by biggest points
        winningCards.sort((x,y)->-Integer.compare(x.getPoints(),y.getPoints()));
        //Sort by lowest points
        losingCards.sort(Comparator.comparingInt(Card::getPoints));
        winningCards.addAll(losingCards);
        return winningCards;
    }

    private boolean teamIsWinning(List<PlayedCard> tableCards, Set<Integer> teammates) {
        return teammates.contains(DeckUtils.getWinningPlayer(tableCards));
    }
}
