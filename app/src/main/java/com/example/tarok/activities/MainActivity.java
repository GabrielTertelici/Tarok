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
import java.util.List;
import java.util.stream.Collectors;

public class MainActivity extends Activity {

    private TalonStage talonStage;
    private GameStage gameStage;
    private List<Card> fullDeck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
        talonStage = new TalonStage(this,fullDeck);
    }

    public void startGameStage(List<Card> deckP1, List<Card> deckP2, List<Card> deckP3, List<Card> deckP4, List<Card> talon,List<Card> pointsPlayer, List<Card> pointsOpponent, Card pickedKing) {
        setContentView(R.layout.sample_board_view);
        gameStage = new GameStage(this);
        gameStage.startGame(deckP1, deckP2, deckP3, deckP4,talon,pointsPlayer, pointsOpponent, pickedKing);
    }

    public void endGameStage(List<Card> pointsTeam1, List<Card> pointsTeam2){
        setContentView(R.layout.end_game_view);
        TextView textTeam1 = findViewById(R.id.pointsTeam1);
        TextView textTeam2 = findViewById(R.id.pointsTeam2);
        Button playAgain = findViewById(R.id.playAgainButton);
        playAgain.setOnClickListener(view -> {
            playAgain.setVisibility(View.GONE);
            startTalonStage();
        });
        EndGameCardsView viewCards1 = findViewById(R.id.cardsTeam1);
        viewCards1.createDeckFromList(pointsTeam1);
        EndGameCardsView viewCards2 = findViewById(R.id.cardsTeam2);
        viewCards2.createDeckFromList(pointsTeam2);
        textTeam1.setText("Your points: "+DeckUtils.sumPoints(pointsTeam1));
        textTeam2.setText("Opponent points: "+DeckUtils.sumPoints(pointsTeam2));
    }

    public void setPlayMode(PlayMode playMode){
        talonStage.startGameWithPlayMode(playMode);
    }

    public List<Card> getKings() {
        List<Card> result = new ArrayList<>();
        for(Card c:fullDeck){
            if(c.getValue()==8 && c.getSuite()!= CardSuite.Tarot){
                result.add(c.resetCard(getApplicationContext()));
            }
        }
        return result;
    }
    public void addCardsToDeck(List<Card> cards){
        talonStage.addCardsToDeck(cards);
        talonStage.startPuttingCardsDown();
    }

    public void removeTalonCards() {
        talonStage.removeTalonCards();
    }
}