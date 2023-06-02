package com.example.tarok.utility;

import android.app.Activity;
import android.content.Context;
import android.icu.text.IDNA;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.tarok.gameObjects.Card;
import com.example.tarok.views.DeckView;
import com.example.tarok.views.TableView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class GameStage {

    private Activity mainActivity;
    private DeckView playerDeck;
    private TableView table;
    private TextView pointsText;
    private Context context;
    private Button playAgain;
    private final int screenWidth;
    private final int screenHeight;

    private Bot player2;
    private Bot player3;
    private Bot player4;

    private List<PlayedCard> tableCards;
    private int cardsPlayed;
    private int roundCount;
    private List<Card> talon;
    private List<Card> pointsTeam1;
    private List<Card> pointsTeam2;
    private final int delay;

    public GameStage(DeckView playerDeck, TableView table, Activity mainActivity, TextView pointsText, Button playAgain, int screenWidth, int screenHeight) {
        this.playerDeck = playerDeck;
        playerDeck.setGameStage(this);

        this.table = table;

        this.context = mainActivity.getApplicationContext();
        this.mainActivity = mainActivity;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;

        this.pointsText = pointsText;
        pointsText.setText("");

        delay=0;
        this.playAgain = playAgain;
        playAgain.setOnClickListener(view -> {
            pointsText.setText("");
            playAgain.setVisibility(View.GONE);
            startGame();
        });
        playAgain.setVisibility(View.GONE);
        startGame();
    }

    private void startGame(){
        //A table can have max 4 cards
        tableCards = new ArrayList<>(4);
        cardsPlayed=0;
        roundCount=0;

        pointsTeam1 = new ArrayList<>();
        pointsTeam2 = new ArrayList<>();

        dealToPlayers(DeckUtils.getDeck(context));
    }

    private void dealToPlayers(List<Card> deck) {
        Collections.shuffle(deck);
        List<Card> deckP1 = new ArrayList<>(deck.subList(0,12));
        List<Card> deckP2 = new ArrayList<>(deck.subList(12,24));
        List<Card> deckP3 = new ArrayList<>(deck.subList(24,36));
        List<Card> deckP4 = new ArrayList<>(deck.subList(36,48));
        talon = new ArrayList<>(deck.subList(48,54));
        if(!DeckUtils.hasCardOfSuite(deckP1, CardSuite.Tarot)||
                !DeckUtils.hasCardOfSuite(deckP2, CardSuite.Tarot)||
                !DeckUtils.hasCardOfSuite(deckP3, CardSuite.Tarot)||
                !DeckUtils.hasCardOfSuite(deckP4, CardSuite.Tarot)){
            dealToPlayers(deck);
        }

        playerDeck.createDeckFromList(deckP1);
        player2 = new Bot(deckP2);
        player3 = new Bot(deckP3);
        player4 = new Bot(deckP4);

        //For now half of talon in T1, half in T2
        pointsTeam1.addAll(talon.subList(0,3));
        pointsTeam2.addAll(talon.subList(3,6));
    }

    /**
     * Plays a given card on the table
     * @param card the card to play
     * @param player 1,2,3 or 4 depending on who played this card
     *               1 = app user = bottom
     *               2,3,4 = bots = counter-clockwise from 1
     */
    public void playCard(Card card, int player) {
        if(player<0 || player>4)
            throw new IllegalArgumentException("Wrong player id");

        cardsPlayed++;
        tableCards.add(new PlayedCard(player,card));
        playerDeck.addCardToTable(card);

        //Time to pick up
        if(cardsPlayed==4){
            playerDeck.lockBoard();
            new Thread(this::pickUpCards).start();
        }
        else{
            switch (player){
                case 1:
                    playerDeck.lockBoard();
                    letBotPlayCard(player2,2,tableCards.get(0).card);
                    break;
                case 2:
                    letBotPlayCard(player3,3,tableCards.get(0).card);
                    break;
                case 3:
                    letBotPlayCard(player4,4,tableCards.get(0).card);
                    break;
                case 4:
                    playerDeck.unlockBoard();
                    playerDeck.setValidCards(tableCards.get(0).card);
                    break;
            }
        }
    }

    private void letBotPlayCard(Bot bot, int player, Card firstCard){
        new Thread(()->{
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            playCard(bot.playCard(firstCard),player);
        }).start();
    }

    public void pickUpCards(){
        int winningPlayer = DeckUtils.getWinningPlayer(tableCards);

        animateTable(winningPlayer);
        assignPointsToWinningTeam(winningPlayer);

        tableCards = new ArrayList<>(4);
        cardsPlayed=0;

        roundCount++;
        if(roundCount==12){
            pointsText.setText("Points Team 1: "+DeckUtils.sumPoints(pointsTeam1)+"\nPoints Team 2: "+DeckUtils.sumPoints(pointsTeam2));
            mainActivity.runOnUiThread(()->playAgain.setVisibility(View.VISIBLE));
        }
        else{
            handleNextRound(winningPlayer);
        }

    }

    private void handleNextRound(int winningPlayer) {
        table.setFirstPlayer(winningPlayer);
        playerDeck.lockBoard();
        switch (winningPlayer){
            case 1:
                playerDeck.unlockBoard();
                break;
            case 2:
                letBotPlayCard(player2,2,null);
                break;
            case 3:
                letBotPlayCard(player3,3,null);
                break;
            case 4:
                letBotPlayCard(player4,4,null);
                break;
        }
    }

    private void assignPointsToWinningTeam(int winningPlayer) {
        //Until talon is implemented 1-3 = team 1, 2-4 = team 2
        if(winningPlayer%2==1)
            pointsTeam1.addAll(tableCards.stream().map(x->x.card).collect(Collectors.toList()));
        else
            pointsTeam2.addAll(tableCards.stream().map(x->x.card).collect(Collectors.toList()));
    }

    private void animateTable(int winningPlayer) {
        try { Thread.sleep(delay*2L);} catch (InterruptedException e) {throw new RuntimeException(e);}
        switch (winningPlayer){
            case 1 -> table.post(() -> table.animate().translationY(screenHeight).setDuration(1000));
            case 2 -> table.post(() -> table.animate().translationX(screenWidth).setDuration(1000));
            case 3 -> table.post(() -> table.animate().translationY(-screenHeight).setDuration(1000));
            case 4 -> table.post(() -> table.animate().translationX(-screenWidth).setDuration(1000));
        }
        try { Thread.sleep(1000);} catch (InterruptedException e) {throw new RuntimeException(e);}
        table.clearTable();
        table.post(() -> table.animate().translationX(0).setDuration(0));
        table.post(() -> table.animate().translationY(0).setDuration(0));
    }
}
