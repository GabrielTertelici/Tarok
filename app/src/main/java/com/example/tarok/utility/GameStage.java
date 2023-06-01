package com.example.tarok.utility;

import android.content.Context;
import android.icu.text.IDNA;
import android.util.Log;

import com.example.tarok.gameObjects.Card;
import com.example.tarok.views.DeckView;
import com.example.tarok.views.TableView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class GameStage {

    private DeckView playerDeck;
    private TableView table;
    private Context context;

    private Bot player2;
    private Bot player3;
    private Bot player4;

    private List<PlayedCard> tableCards;
    private int cardsPlayed;
    private int roundCount;
    private List<Card> talon;
    private List<Card> pointsTeam1;
    private List<Card> pointsTeam2;

    public GameStage(DeckView playerDeck, TableView table, Context context) {
        this.playerDeck = playerDeck;
        playerDeck.setGameStage(this);
        this.table = table;
        this.context = context;

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
            new Thread(()->{
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                pickUpCards();
            }).start();
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
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            playCard(bot.playCard(firstCard),player);
        }).start();
    }

    public void pickUpCards(){
        //Until talon is implemented 1-3 = team 1, 2-4 = team 2
        int winningPlayer = DeckUtils.getWinningPlayer(tableCards);
        if(winningPlayer%2==1)
            pointsTeam1.addAll(tableCards.stream().map(x->x.card).collect(Collectors.toList()));
        else
            pointsTeam2.addAll(tableCards.stream().map(x->x.card).collect(Collectors.toList()));

        table.clearTable();
        tableCards = new ArrayList<>(4);
        cardsPlayed=0;

        roundCount++;
        //If game is done -> log points
        //If not, allow next player to play
        if(roundCount==12){
            Log.println(Log.INFO,"T1",pointsTeam1.toString());
            Log.println(Log.INFO,"T2",pointsTeam2.toString());
        }
        else{
            table.setFirstPlayer(winningPlayer);
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

    }
}
