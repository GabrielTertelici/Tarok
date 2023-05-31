package com.example.tarok.utility;

import android.content.Context;

import com.example.tarok.gameObjects.Card;
import com.example.tarok.views.DeckView;
import com.example.tarok.views.TableView;

import java.util.Collections;
import java.util.List;

public class GameStage {

    private DeckView playerDeck;
    private TableView table;
    private Context context;
    private List<Card> deckP1;
    private List<Card> deckP2;
    private List<Card> deckP3;
    private List<Card> deckP4;
    private List<Card> talon;
    private List<Card> pointsTeam1;
    private List<Card> pointsTeam2;

    public GameStage(DeckView playerDeck, TableView table, Context context) {
        this.playerDeck = playerDeck;
        this.table = table;
        this.context = context;

        assembleLegalDeck();
    }

    private void assembleLegalDeck() {
        List<Card> deck = DeckUtils.getDeck(context);
        splitDeckIntoPlayerCards(deck);

        playerDeck.createDeckFromList(deckP1);
    }

    private void splitDeckIntoPlayerCards(List<Card> deck) {
        Collections.shuffle(deck);
        deckP1 = deck.subList(0,12);
        deckP2 = deck.subList(12,24);
        deckP3 = deck.subList(24,36);
        deckP4 = deck.subList(36,48);
        talon = deck.subList(48,54);
        if(!DeckUtils.hasCardOfSuite(deckP1, CardSuite.Tarot)||
                !DeckUtils.hasCardOfSuite(deckP2, CardSuite.Tarot)||
                !DeckUtils.hasCardOfSuite(deckP3, CardSuite.Tarot)||
                !DeckUtils.hasCardOfSuite(deckP4, CardSuite.Tarot)){
            splitDeckIntoPlayerCards(deck);
        }
    }

}
