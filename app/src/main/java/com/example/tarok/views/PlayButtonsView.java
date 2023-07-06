package com.example.tarok.views;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.tarok.R;
import com.example.tarok.activities.MainActivity;
import com.example.tarok.bots.BotBidRule;
import com.example.tarok.bots.NaiveBidRule;
import com.example.tarok.gameObjects.Card;
import com.example.tarok.utility.PlayMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
//import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class PlayButtonsView {
    private MainActivity mainActivity;
    private Button playThree;
    private Button playTwo;
    private Button playOne;
    private Button playSoloThree;
    private Button playSoloTwo;
    private Button playSoloOne;
    private Button skipButton;
    private TextView playThreeLabel;
    private TextView playTwoLabel;
    private TextView playOneLabel;
    private TextView playSoloThreeLabel;
    private TextView playSoloTwoLabel;
    private TextView playSoloOneLabel;
    private TextView bidInformerLabel;

    private List<Button> buttonList;
    private List<TextView> textViewList;

    private List<List<Card>> decks;

    private BotBidRule naiveBidRule = new NaiveBidRule(new Random());

    private int currentLowestBid;
    private int skips;

    public PlayButtonsView(MainActivity mainActivity, List<List<Card>> decks) {
        this.mainActivity = mainActivity;
        this.playThree = mainActivity.findViewById(R.id.playThree);
        this.playTwo = mainActivity.findViewById(R.id.playTwo);
        this.playOne = mainActivity.findViewById(R.id.playOne);
        this.playSoloThree = mainActivity.findViewById(R.id.soloThree);
        this.playSoloTwo = mainActivity.findViewById(R.id.soloTwo);
        this.playSoloOne = mainActivity.findViewById(R.id.soloOne);

        this.skipButton = mainActivity.findViewById(R.id.skipButton);

        this.playThreeLabel = mainActivity.findViewById(R.id.playThreeLabel);
        this.playTwoLabel = mainActivity.findViewById(R.id.playTwoLabel);
        this.playOneLabel = mainActivity.findViewById(R.id.playOneLabel);
        this.playSoloThreeLabel = mainActivity.findViewById(R.id.playSoloThreeLabel);
        this.playSoloTwoLabel = mainActivity.findViewById(R.id.playSoloTwoLabel);
        this.playSoloOneLabel = mainActivity.findViewById(R.id.playSoloOneLabel);

        this.bidInformerLabel = mainActivity.findViewById(R.id.bidInformerLabel);

        buttonList = new ArrayList<>();

        buttonList.add(playThree);
        buttonList.add(playTwo);
        buttonList.add(playOne);
        buttonList.add(playSoloThree);
        buttonList.add(playSoloTwo);
        buttonList.add(playSoloOne);

        textViewList = new ArrayList<>();

        textViewList.add(playThreeLabel);
        textViewList.add(playTwoLabel);
        textViewList.add(playOneLabel);
        textViewList.add(playSoloThreeLabel);
        textViewList.add(playSoloTwoLabel);
        textViewList.add(playSoloOneLabel);

        this.decks = decks;

        setUpBiddingProcess();
    }

    /**
     * Allows the human player to make a bid, and initiates the bot bidding process
     */
    private void setUpBiddingProcess(){
        skipButton.setOnClickListener(view -> {
            if(currentLowestBid == 0){
                bidInformerLabel.setText("PLEASE MAKE THE FIRST BID");
            } else {
                skips++;
                bidInformerLabel.setText("PLAYER 1 SKIPS");
                if (skips == 3) {
                    sendPlayModeToMain(2);
                } else {
                    makeBotBids(0);
                }
            }
        });
        playThree.setOnClickListener(view -> {
            currentLowestBid = 0;
            playThreeLabel.setText("PLAYER 1");
            enableAllButtons(false);
            displayLatestBid();
            skips = 0;
            makeBotBids(0);
        });
        playTwo.setOnClickListener(view -> {
            currentLowestBid = 1;
            playTwoLabel.setText("PLAYER 1");
            enableAllButtons(false);
            displayLatestBid();
            skips = 0;
            makeBotBids(0);
        });
        playOne.setOnClickListener(view -> {
            currentLowestBid = 2;
            playOneLabel.setText("PLAYER 1");
            enableAllButtons(false);
            displayLatestBid();
            skips = 0;
            makeBotBids(0);
        });
        playSoloThree.setOnClickListener(view -> {
            currentLowestBid = 3;
            playSoloThreeLabel.setText("PLAYER 1");
            enableAllButtons(false);
            displayLatestBid();
            skips = 0;
            makeBotBids(0);
        });
        playSoloTwo.setOnClickListener(view -> {
            currentLowestBid = 4;
            playSoloTwoLabel.setText("PLAYER 1");
            enableAllButtons(false);
            displayLatestBid();
            skips = 0;
            makeBotBids(0);
        });
        playSoloOne.setOnClickListener(view -> {
            currentLowestBid = 5;
            playSoloOneLabel.setText("PLAYER 1");
            displayLatestBid();
            announceWhoPlaysAndInformMain(PlayMode.Solo_One, 1);
        });
    }

    /**
     * Simulates the process of bots choosing what play mode to go for
     * Each bot randomly chooses whether to play for the next lowest play mode, or to skip
     * @param bidderId id of the bot making a decision (bidderId = 0 -> bot 1...)
     */
    public void makeBotBids(int bidderId){
        // use a Handler to delay the UI by 1000ms when displaying the decision of each bot
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(bidderId > 2){
                    enableAllButtons(true);
                    displayLatestBid();
                    return;
                }

                int decision = naiveBidRule.decideWhatToPlayFor(decks.get(bidderId), currentLowestBid);

                if(decision > currentLowestBid){
                    currentLowestBid = decision;
                    skips = 0;
                    textViewList.get(currentLowestBid).setText("PLAYER " + (bidderId + 2));

                    bidInformerLabel.setText("PLAYER " + (bidderId + 2) + " PLAYS");

                    displayLatestBid();

                    if(currentLowestBid == 5){
                        announceWhoPlaysAndInformMain(PlayMode.Solo_One, bidderId + 2);
                        return;
                    }
                } else {
                    skips++;

                    bidInformerLabel.setText("PLAYER " + (bidderId + 2) + " SKIPS");

                    if(skips == 3){
                        sendPlayModeToMain((bidderId + 2) % 4 + 1);
                        return;
                    }
                }

                makeBotBids(bidderId + 1);
            }
        }, 1000);
    }

    /**
     * Takes a PlayMode and a number (1-4) representing which player made the lowest bit,
     * and then displays this information to the players and send is to the main action to start the game
     * @param mode the chosen PlayMode
     * @param player the player who is playing
     */
    private void announceWhoPlaysAndInformMain(PlayMode mode, int player){
        bidInformerLabel.setText("PLAYER " + player + " IS PLAYING " + mode.toString());
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mainActivity.setPlayMode(mode, player);
            }
        }, 1500);
    }

    /**
     * Decodes the PlayMode based on the currentLowestBid int field,
     * and then displays this information along with the player number and sends it to
     * the main action
     * 0 -> Three
     * 1 -> Two
     * 2 -> One
     * 3 -> Solo_Three
     * 4 -> Solo_Two
     * 5 -> Solo_One
     * @param player player number of the player who made this bid
     */
    private void sendPlayModeToMain(int player){
        PlayMode lowestBid = null;

        switch (currentLowestBid){
            case 0:
                lowestBid = PlayMode.Three;
                break;
            case 1:
                lowestBid = PlayMode.Two;
                break;
            case 2:
                lowestBid = PlayMode.One;
                break;
            case 3:
                lowestBid = PlayMode.Solo_Three;
                break;
            case 4:
                lowestBid = PlayMode.Solo_Two;
                break;
            case 5:
                lowestBid = PlayMode.Solo_One;
                break;
        }

        announceWhoPlaysAndInformMain(lowestBid, player);
    }

    /**
     * Disables all the buttons corresponding to high PlayModes which are no longer playable
     * and indicates who went for what option, and what the current lowest bid is
     */
    public void displayLatestBid(){
        for(int i = 0; i < currentLowestBid; i++){
            buttonList.get(i).setEnabled(false);
            buttonList.get(i).setAlpha(0.5f);
            buttonList.get(i).getBackground().setColorFilter(Color.parseColor("#ff0000"), PorterDuff.Mode.DARKEN);
        }

        buttonList.get(currentLowestBid).setEnabled(false);
        buttonList.get(currentLowestBid).getBackground().setColorFilter(Color.parseColor("#00ff00"), PorterDuff.Mode.DARKEN);
    }

    /**
     * Enables or disables the pressing of the buttons based on the enable parameter
     * @param enable enables the buttons if true, disables them if false
     */
    private void enableAllButtons(boolean enable){
        playThree.setEnabled(enable);
        playTwo.setEnabled(enable);
        playOne.setEnabled(enable);
        playSoloThree.setEnabled(enable);
        playSoloTwo.setEnabled(enable);
        playSoloOne.setEnabled(enable);
    }
}
