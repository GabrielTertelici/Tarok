package com.example.tarok.utility;

import com.example.tarok.gameObjects.Card;

import java.util.List;
import java.util.Random;

public class Bot {
    private List<Card> deck;
    /**
     * The player which the bot KNOWS is in his team
     * 1-4 if the teamMate is known
     * 0 if the teamMate is unknown
     */
    private int teamMate;
    private Random r;

    public Bot(List<Card> deck) {
        this.deck = deck;
        r = new Random();
    }

    /**
     * The bot will decide which card to play next
     * @param bottomCard The card that was played first or null if the bot plays first
     * @return The card the bot will play
     */
    public Card playCard(Card bottomCard){
        Card c;
        if(bottomCard==null){
            int index = r.nextInt(deck.size());
            c = deck.get(index);
        }
        else{
            List<Card> usableCards = DeckUtils.getLegalCards(deck,bottomCard);
            int index = r.nextInt(usableCards.size());
            c = usableCards.get(index);
        }
        deck.remove(c);
        return c;
    }

    public void setTeamMate(int teamMate) {
        this.teamMate = teamMate;
    }
}
