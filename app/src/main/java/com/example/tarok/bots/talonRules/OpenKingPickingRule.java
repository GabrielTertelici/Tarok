package com.example.tarok.bots.talonRules;

import com.example.tarok.bots.BotKingPickingRule;
import com.example.tarok.gameObjects.Card;
import com.example.tarok.utility.CardSuite;
import com.example.tarok.utility.DeckUtils;

import java.util.List;
import java.util.Map;
import java.util.Random;

public class OpenKingPickingRule implements BotKingPickingRule {

    private Random random;

    public OpenKingPickingRule(Random random){
        this.random = random;
    }

    /**
     * Picks a king randomly, but with weight towards open files <br>
     * -The file with lowest cards has 80% chance to be picked<br>
     * -A random file will be picked otherwise<br>
     * @param deck the bot's hand
     * @return int representing the selected king:
     * 0 -> heart
     * 1 -> diamond
     * 2 -> club
     * 3 -> spade
     */
    @Override
    public int pickKing(List<Card> deck){
        Map<CardSuite, Integer> fileCount = DeckUtils.countSuiteCards(deck);
        CardSuite minSuite=CardSuite.Club;
        int minSuiteCards=12;
        for(CardSuite e:fileCount.keySet()){
            if(!e.equals(CardSuite.Tarot) && fileCount.get(e)<minSuiteCards){
                minSuiteCards = fileCount.get(e);
                minSuite=e;
            }
        }
        //Pick lowest file king
        if(random.nextInt(10)<=7){
            switch (minSuite){
                case Heart -> {return 0;}
                case Diamond -> {return 1;}
                case Club -> {return 2;}
                case Spade -> {return 3;}
                default -> {return random.nextInt(4);}
            }
        }
        else{
            return random.nextInt(4);
        }
    }
}
