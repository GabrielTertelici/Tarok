package com.example.tarok.bots.talonRules;

import com.example.tarok.bots.BotTalonPickingRule;
import com.example.tarok.gameObjects.Card;
import com.example.tarok.utility.CardComparator;
import com.example.tarok.utility.TalonCardComparator;

import java.util.ArrayList;
import java.util.List;

public class TarotsFirstTalonPickingRule implements BotTalonPickingRule {

    /**
     * Sorts the cards based on biggest points, prioritizing tarots
     * @param deck deck of the bot making the choice
     * @param talon the current talon
     * @return the highest card from the talon
     */
    @Override
    public Card pickCardFromTalon(List<Card> deck, List<Card> talon){
        List<Card> talonCopy = new ArrayList<>(talon);
        talonCopy.sort(new TalonCardComparator<>());

        return talonCopy.get(0);
    }
}
