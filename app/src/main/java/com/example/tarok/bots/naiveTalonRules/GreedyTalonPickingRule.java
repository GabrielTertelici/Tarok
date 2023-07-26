package com.example.tarok.bots.naiveTalonRules;

import com.example.tarok.bots.BotTalonPickingRule;
import com.example.tarok.gameObjects.Card;
import com.example.tarok.utility.CardComparator;

import java.util.ArrayList;
import java.util.List;

public class GreedyTalonPickingRule implements BotTalonPickingRule {

    /**
     *  Picks the highest value card from the talon by sorting them
     * @param deck deck of the bot making the choice
     * @param talon the current talon
     * @return the highest card from the talon
     */
    @Override
    public Card pickCardFromTalon(List<Card> deck, List<Card> talon){
        List<Card> talonCopy = new ArrayList<>(talon);
        talonCopy.sort(new CardComparator<Card>());

        return talonCopy.get(0);
    }
}
