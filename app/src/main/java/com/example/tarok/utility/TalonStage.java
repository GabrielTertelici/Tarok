package com.example.tarok.utility;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.tarok.R;
import com.example.tarok.activities.MainActivity;
import com.example.tarok.gameObjects.Card;
import com.example.tarok.views.DealtCardsView;
import com.example.tarok.views.PlayButtonsView;
import com.example.tarok.views.TalonCardsView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TalonStage {
    private MainActivity mainActivity;
    private DealtCardsView playerDeck;
    private TalonCardsView talonView;
    private Button advanceButton;
    private List<Card> deckP1;
    private List<Card> deckP2;
    private List<Card> deckP3;
    private List<Card> deckP4;
    private List<Card> talon;
    /**
     * 1-4 representing who the teammate of the player is
     */
    private int teamMateOfPlayer;

    public TalonStage(MainActivity mainActivity, List<Card> fullDeck) {
        this.mainActivity = mainActivity;
        this.playerDeck = mainActivity.findViewById(R.id.dealtCardsView);
        this.talonView = mainActivity.findViewById(R.id.talonCardsView);
        this.advanceButton = mainActivity.findViewById(R.id.advanceButton);

        new PlayButtonsView(mainActivity);
        advanceButton.setVisibility(View.GONE);
        dealToPlayers(fullDeck);
    }

    private void getTeamMate() {
        if(talonView.getChosenKing()!=null){
            advanceButton.setOnClickListener(view -> checkPutDownCards());
            Card chosenKing = talonView.getChosenKing();
            if(deckP1.contains(chosenKing)||talon.contains(chosenKing))
                teamMateOfPlayer=1;
            else if(deckP2.contains(chosenKing))
                teamMateOfPlayer=2;
            else if(deckP3.contains(chosenKing))
                teamMateOfPlayer=3;
            else if(deckP4.contains(chosenKing))
                teamMateOfPlayer=4;

            talonView.createTalon();
        }
    }

    private void checkPutDownCards() {
        //We put down the correct amount of cards -> start the game
        if(playerDeck.getSelectedCards().size()== playerDeck.getSelectableCards()){
            deckP1 = playerDeck.getCards();
            deckP1.removeAll(playerDeck.getSelectedCards());
            talon.removeAll(talonView.getChosenCards());
            List<Card> pointsPlayer = playerDeck.getSelectedCards();
            List<Card> pointsOpponent = talon;
            mainActivity.startGameStage(deckP1,deckP2,deckP3,deckP4,pointsPlayer,pointsOpponent,teamMateOfPlayer);
        }
    }

    public void startGameWithPlayMode(PlayMode playMode) {
        mainActivity.findViewById(R.id.buttonGrid).setVisibility(View.GONE);
        talonView.setMainActivity(mainActivity);
        talonView.setPlayMode(playMode);
        talonView.setCards(talon);
        setDealtCardsSelectableCards(playMode);
        advanceButton.setVisibility(View.VISIBLE);
        advanceButton.setOnClickListener(view -> getTeamMate());
        if(playMode==PlayMode.Solo_Three||playMode==PlayMode.Solo_Two||playMode==PlayMode.Solo_One){
            teamMateOfPlayer=1;
            advanceButton.setOnClickListener(view -> checkPutDownCards());
            talonView.createTalon();
        }
        else{
            talonView.showKings();
        }
    }

    private void setDealtCardsSelectableCards(PlayMode playMode) {
        if(playMode==PlayMode.Three||playMode==PlayMode.Solo_Three){
            playerDeck.setSelectableCards(3);
        }
        else if(playMode==PlayMode.Two||playMode==PlayMode.Solo_Two){
            playerDeck.setSelectableCards(2);
        }
        else
            playerDeck.setSelectableCards(1);
    }

    private void dealToPlayers(List<Card> deck) {
        Collections.shuffle(deck);
        deckP1 = new ArrayList<>(deck.subList(0,12));
        deckP2 = new ArrayList<>(deck.subList(12,24));
        deckP3 = new ArrayList<>(deck.subList(24,36));
        deckP4 = new ArrayList<>(deck.subList(36,48));
        talon = new ArrayList<>(deck.subList(48,54));
        if(!DeckUtils.hasCardOfSuite(deckP1, CardSuite.Tarot)||
                !DeckUtils.hasCardOfSuite(deckP2, CardSuite.Tarot)||
                !DeckUtils.hasCardOfSuite(deckP3, CardSuite.Tarot)||
                !DeckUtils.hasCardOfSuite(deckP4, CardSuite.Tarot)){
            dealToPlayers(deck);
        }

        playerDeck.createDeckFromList(deckP1);
    }

    public void addCardsToDeck(List<Card> cards){
        for(Card c:cards){
            playerDeck.addCard(c.resetCard(playerDeck.getContext()));
        }
    }

    public void startPuttingCardsDown(){
        playerDeck.beginPutCardsDown();
    }

    public void removeTalonCards() {
        for(Card c:talon){
            playerDeck.removeCard(c);
        }
    }
}
