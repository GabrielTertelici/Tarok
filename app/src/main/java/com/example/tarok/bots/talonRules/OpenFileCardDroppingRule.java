package com.example.tarok.bots.talonRules;

import com.example.tarok.bots.BotCardDroppingRule;
import com.example.tarok.gameObjects.Card;
import com.example.tarok.utility.CardComparator;
import com.example.tarok.utility.CardSuite;
import com.example.tarok.utility.DeckUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OpenFileCardDroppingRule implements BotCardDroppingRule {


    /**
     * Tries to open as many files as possible, while dropping highest point cards
     * @param deck own hand
     * @param numCardsToDrop number of cards to drop
     * @return list of dropped cards
     */
    @Override
    public List<Card> dropCards(List<Card> deck, int numCardsToDrop) {
        deck = new ArrayList<>(deck);
        deck = deck.stream().map(c->c.resetCard(c.getContext())).collect(Collectors.toList());
        for(Card c:DeckUtils.getFivePointCards(deck)){
            deck.remove(c);
        }
        List<Card> result = new ArrayList<>();
        int cardsDropped = 0;
        while(cardsDropped<numCardsToDrop){
            CardSuite minSuite = getMinSuite(deck);
            List<Card> cardsOfMinSuite = getCardsOfSuite(deck,minSuite);
            while(cardsDropped<numCardsToDrop && cardsOfMinSuite.size()>0){
                Card toDrop = cardsOfMinSuite.remove(0);
                result.add(toDrop);
                deck.remove(toDrop);
                cardsDropped++;
            }
        }
        assert result.size() == numCardsToDrop;
        return result;
    }

    private CardSuite getMinSuite(List<Card> deck) {
        Map<CardSuite, Integer> fileCount = DeckUtils.countSuiteCards(deck);
        CardSuite minSuite=CardSuite.Tarot;
        int minSuiteCards=12;
        for(CardSuite e:fileCount.keySet()){
            if(!e.equals(CardSuite.Tarot) && fileCount.get(e)<minSuiteCards && fileCount.get(e)!=0){
                minSuiteCards = fileCount.get(e);
                minSuite=e;
            }
        }
        return minSuite;
    }

    private List<Card> getCardsOfSuite(List<Card> deck, CardSuite minSuite) {
        List<Card> result = new ArrayList<>();
        for(Card c:deck){
            if(c.getSuite().equals(minSuite)){
                result.add(c);
            }
        }
        result.sort(new CardComparator<Card>());
        return result;
    }
}
