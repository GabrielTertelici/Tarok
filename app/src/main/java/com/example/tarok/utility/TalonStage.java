package com.example.tarok.utility;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.tarok.R;
import com.example.tarok.activities.MainActivity;
import com.example.tarok.bots.BotKingPickingRule;
import com.example.tarok.bots.NaiveKingPickingRule;
import com.example.tarok.gameObjects.Card;
import com.example.tarok.views.DealtCardsView;
import com.example.tarok.views.PlayButtonsView;
import com.example.tarok.views.TalonCardsView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

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
     * The king the player has chosen or null if
     * The player is going solo
     */
    private Card chosenKing;

    private BotKingPickingRule kingPickingRule = new NaiveKingPickingRule(new Random());

    public TalonStage(MainActivity mainActivity, List<Card> fullDeck) {
        this.mainActivity = mainActivity;
        this.playerDeck = mainActivity.findViewById(R.id.dealtCardsView);
        this.talonView = mainActivity.findViewById(R.id.talonCardsView);
        this.advanceButton = mainActivity.findViewById(R.id.advanceButton);

        advanceButton.setVisibility(View.GONE);
        dealToPlayers(fullDeck);

        new PlayButtonsView(mainActivity, List.of(deckP2, deckP3, deckP4));
    }

    private void getTeamMate() {
        if(talonView.getChosenKing()!=null){
            advanceButton.setOnClickListener(view -> checkPutDownCards());
            chosenKing = talonView.getChosenKing();
            talonView.createTalon();
        }
    }

    private void checkPutDownCards() {
        //We put down the correct amount of cards -> start the game
        if(playerDeck.getSelectedCards().size()== playerDeck.getSelectableCards()){
            deckP1 = playerDeck.getCards();
            deckP1.removeAll(playerDeck.getSelectedCards());
            List<Card> pointsPlayer = playerDeck.getSelectedCards();
            List<Card> pointsOpponent = new ArrayList<>(talon);
            pointsOpponent.removeAll((talonView.getChosenCards()));
            mainActivity.startGameStage(deckP1,deckP2,deckP3,deckP4,talon,pointsPlayer,pointsOpponent,chosenKing);
        }
    }

    public void startGameWithPlayMode(PlayMode playMode) {

        setUpTalonView(playMode);

        setDealtCardsSelectableCards(playMode);

        advanceButton.setVisibility(View.VISIBLE);
        advanceButton.setOnClickListener(view -> getTeamMate());
        if(playMode==PlayMode.Solo_Three||playMode==PlayMode.Solo_Two||playMode==PlayMode.Solo_One){
            chosenKing=null;
            advanceButton.setOnClickListener(view -> checkPutDownCards());
            talonView.createTalon();
        }
        else{
            talonView.showKings();
        }
    }

    /**
     * Sets up the talon view
     * @param playMode PlayMode chosen
     */
    private void setUpTalonView(PlayMode playMode){
        mainActivity.findViewById(R.id.buttonGrid).setVisibility(View.GONE);
        talonView.setMainActivity(mainActivity);
        talonView.setPlayMode(playMode);
        talonView.setCards(talon);
    }

    /**
     * Starts the process of the bot who made the lowest bid picking a king (if needed) and
     * cards from the talon, and putting down cards from their hand
     * @param playMode the PlayMode chosen
     * @param player the id of the bot playing
     */
    public void startGameWithBotPlaying(PlayMode playMode, int player){

        setUpTalonView(playMode);

        talonView.setBotPlayer(player);

        List<Card> deck = List.of(deckP2, deckP3, deckP4).get(player - 2);

        if(playMode==PlayMode.Solo_Three||playMode==PlayMode.Solo_Two||playMode==PlayMode.Solo_One){
            chosenKing = null;
            talonView.createUnclickableTalon(deck);
        }
        else{
            talonView.showUnclickableKings();
            int pickedKing = kingPickingRule.pickKing(deck);
            talonView.displayKingChoice(pickedKing, deck);
        }

    }

    private void setDealtCardsSelectableCards(PlayMode playMode) {
        if(playMode==PlayMode.Three||playMode==PlayMode.Solo_Three){
            playerDeck.setSelectableCards(3);
            talonView.setSelectableCards(3);
        }
        else if(playMode==PlayMode.Two||playMode==PlayMode.Solo_Two){
            playerDeck.setSelectableCards(2);
            talonView.setSelectableCards(2);
        }
        else {
            playerDeck.setSelectableCards(1);
            talonView.setSelectableCards(1);
        }
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

    /**
     * Getter for the decks dealt
     * @return list of decks, in order
     */
    public List<List<Card>> getDecks(){
        return List.of(deckP1, deckP2, deckP3, deckP4);
    }
}
