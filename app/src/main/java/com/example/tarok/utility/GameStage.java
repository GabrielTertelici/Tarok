package com.example.tarok.utility;

import android.content.Context;
import android.util.DisplayMetrics;

import com.example.tarok.R;
import com.example.tarok.activities.MainActivity;
import com.example.tarok.bots.Bot;
import com.example.tarok.bots.botBrains.NegativeGameModeBrain;
import com.example.tarok.bots.botBrains.NormalGameModeBrain;
import com.example.tarok.bots.cardPlayingRules.SuiteCardRule;
import com.example.tarok.bots.cardPlayingRules.TarotCardRule;
import com.example.tarok.gameObjects.Card;
import com.example.tarok.views.DeckView;
import com.example.tarok.views.PlayerIconsView;
import com.example.tarok.views.TableView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GameStage {

    private MainActivity mainActivity;
    private DeckView playerDeck;
    private TableView table;
    private PlayerIconsView iconsView;
    private Context context;
    private final int screenWidth;
    private final int screenHeight;

    private Bot player2;
    private Bot player3;
    private Bot player4;

    private int player;
    private int teamMate;

    private List<PlayedCard> tableCards;
    private int roundCount;
    private List<Card> pointsTeam1;
    private List<Card> pointsTeam2;
    private Card chosenKing;
    private final int delay;
    private boolean isNegativeGameMode;
    private boolean pointsMatter;
    private List<List<Card>> individualPointsList;
    private int firstPlayer;
    private int collectedHands;
    private int PBVGameMode;

    public GameStage(MainActivity mainActivity, int firstPlayer) {
        this.playerDeck = mainActivity.findViewById(R.id.deckView);
        playerDeck.setGameStage(this);

        this.table = mainActivity.findViewById(R.id.tableView);

        this.context = mainActivity.getApplicationContext();
        this.mainActivity = mainActivity;

        this.iconsView = new PlayerIconsView(mainActivity);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        mainActivity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        this.screenWidth = displayMetrics.widthPixels;
        this.screenHeight = displayMetrics.heightPixels;

        this.firstPlayer = firstPlayer;

        this.pointsMatter = true;

        this.collectedHands = 0;

        delay=500;
    }

    public void startGame(List<Card> deckP1, List<Card> deckP2, List<Card> deckP3, List<Card> deckP4, List<Card> talon,  List<Card> pointsPlayer, List<Card> pointsOpponent, Card pickedKing) {
        this.isNegativeGameMode = false;

        //A table can have max 4 cards
        tableCards = new ArrayList<>(4);
        roundCount=0;
        table.setFirstPlayer(firstPlayer);

        pointsTeam1 = new ArrayList<>(pointsPlayer);
        pointsTeam2 = new ArrayList<>(pointsOpponent);
        chosenKing = pickedKing;

        playerDeck.createDeckFromList(deckP1);
        player2 = new Bot(deckP2, new NormalGameModeBrain(new SuiteCardRule(), new TarotCardRule()));
        player3 = new Bot(deckP3, new NormalGameModeBrain(new SuiteCardRule(), new TarotCardRule()));
        player4 = new Bot(deckP4, new NormalGameModeBrain(new SuiteCardRule(), new TarotCardRule()));

        if(this.player == 0){
            this.player = 1;
        }

        teamMate = getTeammate(deckP1,deckP2,deckP3,deckP4);
        //Solo or rufie -> bots know teammates
        if(chosenKing == null || talon.contains(chosenKing)){
            BotTeammateHandler.handleTeammatesOfBots(List.of(player2, player3, player4), player, teamMate);
            mainActivity.runOnUiThread(()->iconsView.setPlayerTeammates(player,teamMate));
        }
        else{
            showPickedKingImage();
        }

        if(firstPlayer != 1){
            playerDeck.lockBoard();
            letBotPlayCard(List.of(player2, player3, player4).get(firstPlayer - 2), firstPlayer, tableCards);
        }
    }

    private void showPickedKingImage() {
        switch (player){
            case 1 -> iconsView.setPlayer1King(chosenKing.getSuite());
            case 2 -> iconsView.setPlayer2King(chosenKing.getSuite());
            case 3 -> iconsView.setPlayer3King(chosenKing.getSuite());
            case 4 -> iconsView.setPlayer4King(chosenKing.getSuite());
        }
    }

    /**
     * Starts game with negative game mode
     * @param decks list of player decks
     */
    public void startNegativeGame(List<List<Card>> decks) {
        this.isNegativeGameMode = true;
        //A table can have max 4 cards
        tableCards = new ArrayList<>(4);
        roundCount = 0;
        table.setFirstPlayer(firstPlayer);

        playerDeck.createDeckFromList(decks.get(0));
        player2 = new Bot(decks.get(1), new NegativeGameModeBrain());
        player3 = new Bot(decks.get(2), new NegativeGameModeBrain());
        player4 = new Bot(decks.get(3), new NegativeGameModeBrain());

        this.individualPointsList = List.of(new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>());

        if(firstPlayer != 1){
            playerDeck.lockBoard();
            letBotPlayCard(List.of(player2, player3, player4).get(firstPlayer - 2), firstPlayer, tableCards);
        }
    }

    private int getTeammate(List<Card> deckP1, List<Card> deckP2, List<Card> deckP3, List<Card> deckP4) {
        int teammate = player;
        if(chosenKing!=null){
            if(deckP2.contains(chosenKing)){
                teammate = 2;
                player2.setTeamMate(player);
            }
            else if(deckP3.contains(chosenKing)) {
                teammate = 3;
                player3.setTeamMate(player);
            }
            else if(deckP4.contains(chosenKing)) {
                teammate = 4;
                player4.setTeamMate(player);
            } else if(deckP1.contains(chosenKing)){
                teammate = 1;
            }
        }
        return teammate;
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

        if(card.equals(chosenKing)){
            BotTeammateHandler.handleTeammatesOfBots(List.of(player2, player3, player4), this.player, teamMate);
            mainActivity.runOnUiThread(()->iconsView.setPlayerTeammates(this.player,teamMate));
        }

        tableCards.add(new PlayedCard(player,card));
        playerDeck.addCardToTable(card);

        //Time to pick up
        if(tableCards.size()==4){
            playerDeck.lockBoard();
            new Thread(this::pickUpCards).start();
        }
        else{
            switch (player) {
                case 1 -> {
                    playerDeck.lockBoard();
                    letBotPlayCard(player2, 2, tableCards);
                }
                case 2 -> letBotPlayCard(player3, 3, tableCards);
                case 3 -> letBotPlayCard(player4, 4, tableCards);
                case 4 -> {
                    playerDeck.unlockBoard();
                    playerDeck.setValidCards(tableCards, isNegativeGameMode);
                }
            }
        }
    }

    private void letBotPlayCard(Bot bot, int player, List<PlayedCard> tableCards){
        new Thread(()->{
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            playCard(bot.playCard(tableCards),player);
        }).start();
    }

    public void pickUpCards(){
        int winningPlayer = DeckUtils.getWinningPlayer(tableCards);

        animateTableCardPickup(winningPlayer);

        if(pointsMatter){
            if(isNegativeGameMode){
                assignPointsToWinningPlayer(winningPlayer);
            } else {
                assignPointsToWinningTeam(winningPlayer);
            }
        } else {
            collectedHands++;
        }

        tableCards = new ArrayList<>(4);

        roundCount++;
        if(roundCount==12){
            handleGameEnding();
        }
        else{
            handleNextRound(winningPlayer);
        }
    }

    /**
     * Handles the end of round 12 (final round)
     */
    public void handleGameEnding(){
        if(isNegativeGameMode){
            mainActivity.runOnUiThread(()->mainActivity.endGameStageNegative(individualPointsList));
        } else {
            if(this.player == 1 || this.teamMate == 1){
                mainActivity.runOnUiThread(()->mainActivity.endGameStage(pointsTeam1,pointsTeam2));
            } else {
                mainActivity.runOnUiThread(()->mainActivity.endGameStage(pointsTeam2,pointsTeam1));
            }
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
                letBotPlayCard(player2,2,tableCards);
                break;
            case 3:
                letBotPlayCard(player3,3,tableCards);
                break;
            case 4:
                letBotPlayCard(player4,4,tableCards);
                break;
        }
    }

    private void assignPointsToWinningTeam(int winningPlayer) {
        if(winningPlayer==player || winningPlayer==teamMate)
            pointsTeam1.addAll(tableCards.stream().map(x->x.card).collect(Collectors.toList()));
        else
            pointsTeam2.addAll(tableCards.stream().map(x->x.card).collect(Collectors.toList()));
    }

    /**
     * Adds the cards picked up to the specific player who won the round, in case of a
     * negative game mode
     * @param winningPlayer player who won the round
     */
    private void assignPointsToWinningPlayer(int winningPlayer){
        individualPointsList.get(winningPlayer - 1).addAll(tableCards.stream().map(x->x.card).collect(Collectors.toList()));
    }

    private void animateTableCardPickup(int winningPlayer) {
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


    public void startGameWithBotPlaying(List<Card> cards, List<Card> cards1, List<Card> cards2, List<Card> cards3, List<Card> pointsPlayer, List<Card> pointsOpponent, List<Card> talon, Card chosenKing, int player) {
        this.player = player;

        startGame(cards, cards1, cards2, cards3, talon, pointsPlayer, pointsOpponent, chosenKing);
    }

    /**
     * Starts a game where Piccolo, Beggar or Valat has been selected (hence PBV)
     * @param player player who made the bet (human player = 1, right hand side bot = 2...)
     * @param currentLowestBid the game mode played for (6 -> piccolo, 7 -> beggar, 8 -> valat)
     * @param decks list of player decks
     */
    public void startPBVGame(int player, int currentLowestBid, List<List<Card>> decks) {
        this.pointsMatter = false;

        this.player = player;

        this.PBVGameMode = currentLowestBid;

        //A table can have max 4 cards
        tableCards = new ArrayList<>(4);
        roundCount = 0;

        playerDeck.createDeckFromList(decks.get(0));

        if(currentLowestBid < 8){
            this.isNegativeGameMode = true;

            this.player2 = new Bot(decks.get(1), new NegativeGameModeBrain());
            this.player3 = new Bot(decks.get(2), new NegativeGameModeBrain());
            this.player4 = new Bot(decks.get(3), new NegativeGameModeBrain());
        } else {
            this.isNegativeGameMode = false;

            this.player2 = new Bot(decks.get(1), new NormalGameModeBrain(new SuiteCardRule(), new TarotCardRule()));
            this.player2 = new Bot(decks.get(2), new NormalGameModeBrain(new SuiteCardRule(), new TarotCardRule()));
            this.player2 = new Bot(decks.get(3), new NormalGameModeBrain(new SuiteCardRule(), new TarotCardRule()));
        }

        int beginningPlayer = 0;

        if(currentLowestBid == 8){
            beginningPlayer  = this.firstPlayer;
        } else if(currentLowestBid == 7){
            // beggar plays second
            beginningPlayer = ((beginningPlayer + 2) % 4) + 1;
        } else if(currentLowestBid == 6){
            // piccolo plays first
            beginningPlayer = player;
        }

        table.setFirstPlayer(beginningPlayer);

        if(beginningPlayer != 1){
            playerDeck.lockBoard();
            letBotPlayCard(List.of(player2, player3, player4).get(beginningPlayer - 2), beginningPlayer, tableCards);
        }
    }
}
