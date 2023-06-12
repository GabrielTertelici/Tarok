package com.example.tarok.bots;

import com.example.tarok.gameObjects.Card;
import com.example.tarok.utility.DeckUtils;
import com.example.tarok.utility.PlayedCard;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class TarotCardRule implements BotRule{

    /**
     * This rule will ensure the bot plays a low value
     * tarot if there are <4 points on table
     * If there are >=4 points, bot will play lowest tarot that allows its team to win,
     * or play the lowest tarot it has if the team cannot win
     */
    public List<Card> sortedList(List<Card> legalCards, List<PlayedCard> tableCards, Set<Integer> teammates) {
        List<Card> table = tableCards.stream().map(x->x.card).collect(Collectors.toList());
        int pointsOnTable = DeckUtils.tablePoints(table);
        //If there are few points on the table and the team is not winning
        if(pointsOnTable<4 && !teamIsWinning(tableCards, teammates)){
            //Play lowest tarot that also has lowest points
            return legalCards.stream().sorted(Comparator.comparingInt(Card::getValue)).sorted(Comparator.comparingInt(Card::getPoints)).collect(Collectors.toList());
        }
        else if(teamIsWinning(tableCards,teammates)){
            //Again return lowest tarot, but this time including 1
            return legalCards.stream().sorted(Comparator.comparingInt(Card::getValue)).collect(Collectors.toList());
        }
        //Here we know our team is not winning
        //Try to win yourself
        List<Card> winningCards = new ArrayList<>();
        List<Card> losingCards = new ArrayList<>();
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
        //Sort by lowest value
        winningCards.sort(Comparator.comparingInt(Card::getValue));
        //Sort by lowest value (and also by lowest points since we dont want to play a 1)
        losingCards.sort(Comparator.comparingInt(Card::getValue));
        losingCards.sort(Comparator.comparingInt(Card::getPoints));
        winningCards.addAll(losingCards);
        return winningCards;
    }

    private boolean teamIsWinning(List<PlayedCard> tableCards, Set<Integer> teammates) {
        return teammates.contains(DeckUtils.getWinningPlayer(tableCards));
    }
}
