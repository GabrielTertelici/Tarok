package com.example.tarok.bots;

import com.example.tarok.gameObjects.Card;

import java.util.List;

public class BotTalonStageRuleManager {

    private BotBidRule bidRule;
    private BotKingPickingRule kingPickingRule;
    private BotTalonPickingRule talonPickingRule;
    private BotCardDroppingRule cardDroppingRule;

    public BotTalonStageRuleManager(BotBidRule bidRule,
                                    BotKingPickingRule kingPickingRule,
                                    BotTalonPickingRule talonPickingRule,
                                    BotCardDroppingRule cardDroppingRule) {
        this.bidRule = bidRule;
        this.kingPickingRule = kingPickingRule;
        this.talonPickingRule = talonPickingRule;
        this.cardDroppingRule = cardDroppingRule;
    }

    /**
     * Returns an int value representing what bid they want to make
     * ints are used instead of PlayModes because they are easier to manipulate
     * and can be converted to PlayModes with a switch statement
     * @param deck the deck based on which the decision will be made
     * @param currentLowestBid integer representing the current lowest bid made
     * @return int representing what PlayMode has been chosen
     */
    public int decideWhatToPlayFor(List<Card> deck, int currentLowestBid){
        return bidRule.decideWhatToPlayFor(deck, currentLowestBid);
    }

    /**
     * Returns an int representing which king this bot has chosen, based on the bot's hand
     * @param deck the bot's hand
     * @return an int representing the chosen king:
     * 0 -> club
     * 1 -> heart
     * 2 -> spade
     * 3 -> diamond
     */
    public int pickKing(List<Card> deck){
        return kingPickingRule.pickKing(deck);
    }

    /**
     * Lets bot pick a Card from the talon, essentially emulating a click on a card like a human would do.
     * The choice is based on the deck of the bot and the talon
     * @param deck deck of the bot making the choice
     * @param talon the current talon
     * @param amountPlayedFor number of cards the player has played for
     * @return the Card the user would click on
     */
    public Card pickCardFromTalon(List<Card> deck, List<Card> talon, int amountPlayedFor){
        return talonPickingRule.pickCardFromTalon(deck, talon);
    }

    /**
     * Drops numCardsToDrop cards from own hand after picking from talon
     * @param deck own hand
     * @param numCardsToDrop number of cards to drop
     * @return list of dropped cards
     */
    public List<Card> dropCards(List<Card> deck, int numCardsToDrop){
        return cardDroppingRule.dropCards(deck, numCardsToDrop);
    }

}
