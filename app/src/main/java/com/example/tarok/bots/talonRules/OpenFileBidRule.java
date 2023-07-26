package com.example.tarok.bots.talonRules;

import com.example.tarok.bots.BotBidRule;
import com.example.tarok.gameObjects.Card;
import com.example.tarok.utility.CardSuite;
import com.example.tarok.utility.DeckUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class OpenFileBidRule implements BotBidRule {

    Random r;
    public OpenFileBidRule(Random r) {
        this.r = r;
    }

    /**
     * The bots decide in the following way:<br>
     * Play 3 -> can drop 3 cards and have 2 files open<br>
     * Play 2 -> can drop 2 cards and have 2 files open<br>
     * Play 1 -> can drop 1 card and have 2 files open<br>
     * Solo 3 -> can drop 2 cards and have 3 files open<br>
     * Solo 2 -> can drop 1 cards and have 3 files open<br>
     * Solo 1 -> already has 3 files open<br>
     * The bot will always go for the lowest option it can,
     * no matter what the current bid is
     * @param deck the deck the bot has
     * @param currentLowestBid the current lowest bid
     * @return the bid made
     */
    @Override
    public int decideWhatToPlayFor(List<Card> deck, int currentLowestBid){
        Map<CardSuite, Integer> fileCount = DeckUtils.countSuiteCards(deck);
        //Get the minimum files (not tarot) = files easiest to open/already opened
        List<Integer> cardsRequiredToOpenFiles = new ArrayList<>();
        for(CardSuite e:fileCount.keySet()){
            if(!e.equals(CardSuite.Tarot)){
                cardsRequiredToOpenFiles.add(fileCount.get(e));
            }
        }
        Collections.sort(cardsRequiredToOpenFiles);

        int chosenOption = 0;
        //Go over every option in reverse order and decide for it or against it
        //Solo options -> look at opening 3 files
        int sumOf3 = cardsRequiredToOpenFiles.get(0) + cardsRequiredToOpenFiles.get(1) + cardsRequiredToOpenFiles.get(2);
        if(sumOf3==0){chosenOption = 5;}
        if(sumOf3==1){chosenOption = 4;}
        if(sumOf3==2){chosenOption = 3;}
        //We cannot go solo -> look at opening 2 files
        int sumOf2 = cardsRequiredToOpenFiles.get(0) + cardsRequiredToOpenFiles.get(1);
        if(sumOf2<=1){chosenOption = 2;}
        if(sumOf2==2){chosenOption = 1;}
        if(sumOf2==3){chosenOption = 0;}

        //Here bot can decide to bluff by not playing as low as it can go
        //Instead it just goes one step lower
        if(chosenOption>currentLowestBid) {
            if(r.nextInt(10)<8)
                return chosenOption;
            else
                return currentLowestBid+1;
        }
        return currentLowestBid;
    }
}
