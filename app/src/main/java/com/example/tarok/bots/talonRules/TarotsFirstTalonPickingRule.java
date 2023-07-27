package com.example.tarok.bots.talonRules;

import com.example.tarok.bots.BotTalonPickingRule;
import com.example.tarok.gameObjects.Card;
import com.example.tarok.utility.CardComparator;
import com.example.tarok.utility.CardSuite;
import com.example.tarok.utility.PlayMode;
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
    public Card pickCardFromTalon(List<Card> deck, List<Card> talon, int cardsToPick){
        List<Card> talonCopy = new ArrayList<>(talon);
        talonCopy.sort(new TalonCardComparator<>());
        //If two groups have same value, the one with higher tarots wins
        //by default, since it is the first one picked thanks to comparator above
        Card pickedCard=talonCopy.get(0);
        int pickedGroupValue = 0;
        for(Card c:talonCopy){
            int groupValue = evaluateGroupValue(getGroup(c,talon,cardsToPick));
            if(groupValue>pickedGroupValue){
                pickedCard = c;
                pickedGroupValue = groupValue;
            }
        }
        return pickedCard;
    }

    /**
     * Calculates the value of a group in the following way
     * Joker and 21 are worth 10 points
     * 1 is worth 8 points
     * Tarots are worth 5 points
     * Suites are worth same as normal (king = 5, queen=4, etc.)
     * @param group group to calculate value
     * @return the resulting value = sum of values of cards
     */
    private int evaluateGroupValue(List<Card> group){
        int result = 0;
        for(Card c:group){
            if(c.getSuite()== CardSuite.Tarot){
                if(c.getValue()==22 || c.getValue()==21){
                    result+=10;
                }
                else if(c.getValue()==1){
                    result+=8;
                }
                else{
                    result+=5;
                }
            }
            else{
                result+=c.getPoints();
            }
        }
        return result;
    }

    private List<Card> getGroup(Card card, List<Card> cards, int cardsToPick) {
        List<Card> result = new ArrayList<>();
        if(cardsToPick==3){
            if(cards.indexOf(card)<=2){
                result.add(cards.get(0)); result.add(cards.get(1)); result.add(cards.get(2));
            }
            else{
                result.add(cards.get(3)); result.add(cards.get(4)); result.add(cards.get(5));
            }
        }
        else if(cardsToPick==2){
            if(cards.indexOf(card)<=1){
                result.add(cards.get(0)); result.add(cards.get(1));
            }
            else if(cards.indexOf(card)<=3){
                result.add(cards.get(2)); result.add(cards.get(3));
            }
            else{
                result.add(cards.get(4)); result.add(cards.get(5));
            }
        }
        else{
            result.add(card);
        }
        return result;
    }
}
