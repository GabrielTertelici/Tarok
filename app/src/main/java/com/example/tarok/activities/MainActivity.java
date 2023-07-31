package com.example.tarok.activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.tarok.R;
import com.example.tarok.gameObjects.Card;
import com.example.tarok.utility.CardSuite;
import com.example.tarok.utility.DeckUtils;
import com.example.tarok.utility.GameStage;
import com.example.tarok.utility.PlayMode;
import com.example.tarok.utility.TalonStage;
import com.example.tarok.views.EndGameCardsView;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class MainActivity extends Activity {

    private TalonStage talonStage;
    private GameStage gameStage;
    private List<Card> fullDeck;
    private int firstPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.firstPlayer = 1;

        // Set fullscreen
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Set No Title
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.fullDeck = DeckUtils.getDeck(getApplicationContext());
        startTalonStage();

    }

    public void startTalonStage(){
        setContentView(R.layout.talon_view);
        //Reset full deck
        fullDeck = fullDeck.stream().map(c->c.resetCard(getApplicationContext())).collect(Collectors.toList());
        talonStage = new TalonStage(this,fullDeck, this.firstPlayer);
    }

    public void startGameStage(List<Card> deckP1, List<Card> deckP2, List<Card> deckP3, List<Card> deckP4, List<Card> talon,List<Card> pointsPlayer, List<Card> pointsOpponent, Card pickedKing) {
        setContentView(R.layout.sample_board_view);
        gameStage = new GameStage(this, this.firstPlayer);
        gameStage.startGame(deckP1, deckP2, deckP3, deckP4,talon,pointsPlayer, pointsOpponent, pickedKing);
    }

    public void endGameStage(List<Card> pointsTeam1, List<Card> pointsTeam2){
        setContentView(R.layout.end_game_view);
        TextView textTeam1 = findViewById(R.id.pointsTeam1);
        TextView textTeam2 = findViewById(R.id.pointsTeam2);
        Button playAgain = findViewById(R.id.playAgainButton);
        playAgain.setOnClickListener(view -> {
            playAgain.setVisibility(View.GONE);
            this.firstPlayer = (this.firstPlayer % 4) + 1;
            startTalonStage();
        });

        textTeam2.setTextSize(18);

        EndGameCardsView viewCards1 = findViewById(R.id.cardsTeam1);
        viewCards1.createDeckFromList(pointsTeam1);
        EndGameCardsView viewCards2 = findViewById(R.id.cardsTeam2);
        viewCards2.createDeckFromList(pointsTeam2);
        textTeam1.setText("Your points: "+DeckUtils.sumPoints(pointsTeam1));
        textTeam2.setText("Opponent points: "+DeckUtils.sumPoints(pointsTeam2));
    }

    public void setPlayMode(PlayMode playMode, int player){
        // player == 1 -> human player made the lowest bid
        if(player == 1){
            talonStage.startGameWithPlayMode(playMode);
        } else {
            talonStage.startGameWithBotPlaying(playMode, player);
        }
    }

    public List<Card> getKings() {
        List<Card> result = new ArrayList<>();
        for(Card c:fullDeck){
            if(c.getValue()==8 && c.getSuite()!= CardSuite.Tarot){
                result.add(c.resetCard(getApplicationContext()));
            }
        }
        result.sort(Comparator.comparing(Card::getSuite));
        return result;
    }
    public void addCardsToDeck(List<Card> cards){
        talonStage.addCardsToDeck(cards);
        talonStage.startPuttingCardsDown();
    }

    public void removeTalonCards() {
        talonStage.removeTalonCards();
    }

    /**
     * Starts the game stage with a bot as the bidder
     * @param pointsPlayer list of cards dropped by bot player
     * @param pointsOpponent leftover cards from talon
     * @param talon talon
     * @param chosenKing the king the bot player chose
     * @param player id of the bot who is playing, 2-4
     */
    public void startGameStageWithBotPlaying(List<Card> pointsPlayer, List<Card> pointsOpponent, List<Card> talon, Card chosenKing, int player) {
        List<List<Card>> decks = talonStage.getDecks();
        setContentView(R.layout.sample_board_view);
        gameStage = new GameStage(this, this.firstPlayer);
        gameStage.startGameWithBotPlaying(
                decks.get(0),
                decks.get(1),
                decks.get(2),
                decks.get(3),
                pointsPlayer,
                pointsOpponent,
                talon,
                chosenKing, player);
    }

    /**
     * Starts the game with negative play mode
     */
    public void playNegative() {
        setContentView(R.layout.sample_board_view);
        gameStage = new GameStage(this, this.firstPlayer);
        gameStage.startNegativeGame(talonStage.getDecks());
    }

    /**
     * Handles the ending of a negative round
     * @param individualPointsList list of lists of individual points won by each player
     */
    public void endGameStageNegative(List<List<Card>> individualPointsList) {
        setContentView(R.layout.end_game_view);
        TextView textTeam1 = findViewById(R.id.pointsTeam1);
        TextView textTeam2 = findViewById(R.id.pointsTeam2);
        Button playAgain = findViewById(R.id.playAgainButton);
        playAgain.setOnClickListener(view -> {
            playAgain.setVisibility(View.GONE);
            startTalonStage();
        });

        textTeam2.setMaxLines(4);
        textTeam2.setTextSize(12);

        EndGameCardsView viewCards1 = findViewById(R.id.cardsTeam1);
        viewCards1.createDeckFromList(individualPointsList.get(0));
        EndGameCardsView viewCards2 = findViewById(R.id.cardsTeam2);

        List<Card> allOtherCards = new ArrayList<>(individualPointsList.get(1));
        allOtherCards.addAll(individualPointsList.get(2));
        allOtherCards.addAll(individualPointsList.get(3));
        viewCards2.createDeckFromList(allOtherCards);
        textTeam1.setText("Your points: "+DeckUtils.sumPoints(individualPointsList.get(0)));

        String allOthers = "Player 2: " + DeckUtils.sumPoints(individualPointsList.get(1)) + " in " + individualPointsList.get(1).size() + " cards; " + System.lineSeparator() +
                "Player 3: " + DeckUtils.sumPoints(individualPointsList.get(2)) + " in " + individualPointsList.get(2).size() + " cards; " +
                "Player 4: " + DeckUtils.sumPoints(individualPointsList.get(3)) + " in " + individualPointsList.get(3).size() + " cards; ";

        textTeam2.setText(allOthers);
    }

    public void playPiccolo(int player) {
    }

    public void playBeggar(int player) {
    }

    public void playValat(int player) {
    }
}