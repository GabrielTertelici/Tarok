package com.example.tarok.views;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Handler;
import android.widget.Button;
import android.widget.TextView;

import com.example.tarok.R;
import com.example.tarok.activities.MainActivity;
import com.example.tarok.bots.BotTalonStageRuleManager;
import com.example.tarok.gameObjects.Card;
import com.example.tarok.utility.BidClickListener;
import com.example.tarok.utility.BotBiddingProcess;
import com.example.tarok.utility.PlayMode;

import java.util.ArrayList;
import java.util.List;
//import java.util.logging.Handler;


public class PlayButtonsView {
    private MainActivity mainActivity;
    private Button playThree;
    private Button playTwo;
    private Button playOne;
    private Button playSoloThree;
    private Button playSoloTwo;
    private Button playSoloOne;
    private Button playPiccolo;
    private Button playBeggar;
    private Button playValat;
    private Button skipButton;
    private TextView playThreeLabel;
    private TextView playTwoLabel;
    private TextView playOneLabel;
    private TextView playSoloThreeLabel;
    private TextView playSoloTwoLabel;
    private TextView playSoloOneLabel;
    private TextView playPiccoloLabel;
    private TextView playBeggarLabel;
    private TextView playValatLabel;
    private TextView bidInformerLabel;

    private List<Button> buttonList;
    private List<TextView> textViewList;

    private BidClickListener clickListenerHandler;

    public PlayButtonsView(MainActivity mainActivity, List<List<Card>> decks, BotTalonStageRuleManager botManager, int firstPlayer) {
        this.mainActivity = mainActivity;
        this.playThree = mainActivity.findViewById(R.id.playThree);
        this.playTwo = mainActivity.findViewById(R.id.playTwo);
        this.playOne = mainActivity.findViewById(R.id.playOne);
        this.playSoloThree = mainActivity.findViewById(R.id.soloThree);
        this.playSoloTwo = mainActivity.findViewById(R.id.soloTwo);
        this.playSoloOne = mainActivity.findViewById(R.id.soloOne);
        this.playPiccolo = mainActivity.findViewById(R.id.piccolo);
        this.playBeggar = mainActivity.findViewById(R.id.beggar);
        this.playValat = mainActivity.findViewById(R.id.valat);

        this.skipButton = mainActivity.findViewById(R.id.skipButton);

        this.playThreeLabel = mainActivity.findViewById(R.id.playThreeLabel);
        this.playTwoLabel = mainActivity.findViewById(R.id.playTwoLabel);
        this.playOneLabel = mainActivity.findViewById(R.id.playOneLabel);
        this.playSoloThreeLabel = mainActivity.findViewById(R.id.playSoloThreeLabel);
        this.playSoloTwoLabel = mainActivity.findViewById(R.id.playSoloTwoLabel);
        this.playSoloOneLabel = mainActivity.findViewById(R.id.playSoloOneLabel);
        this.playPiccoloLabel = mainActivity.findViewById(R.id.playPiccoloLabel);
        this.playBeggarLabel = mainActivity.findViewById(R.id.playBeggarLabel);
        this.playValatLabel = mainActivity.findViewById(R.id.playValatLabel);

        this.bidInformerLabel = mainActivity.findViewById(R.id.bidInformerLabel);

        buttonList = addAllButtons();

        textViewList = addAllLabels();

        bidInformerLabel.setText("PLAYER " + firstPlayer + " MAKES THE FIRST BID");

        enableAllButtons(false);

        this.clickListenerHandler = new BidClickListener(this,
                new BotBiddingProcess(this, botManager, decks));
        this.clickListenerHandler.setSkipButtonListener(skipButton);
        this.clickListenerHandler.setButtonListeners(buttonList);

        this.clickListenerHandler.getBotBiddingProcess().makeBids((firstPlayer + 2) % 4);
    }

    /**
     * Takes a PlayMode and a number (1-4) representing which player made the lowest bit,
     * and then displays this information to the players and send is to the main action to start the game
     * @param mode the chosen PlayMode
     * @param player the player who is playing
     */
    public void announceWhoPlaysAndInformMain(PlayMode mode, int player){
        Handler outerHandler = new Handler();
        outerHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                bidInformerLabel.setText("PLAYER " + player + " IS PLAYING " + mode.toString());
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mainActivity.setPlayMode(mode, player);
                    }
                }, 1500);
            }
        }, 1000);
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
    public void sendPlayModeToMain(int player, int currentLowestBid){
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
     * Alerts the player that the negative mode has been entered
     * and starts the game as such
     */
    public void playNegative() {
        bidInformerLabel.setText("ALL PLAYERS SKIPPED, PLAYING NEGATIVE");
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mainActivity.playNegative();
            }
        }, 1500);
    }

    /**
     * Method for starting a game with a mode not requiring a talon
     * @param player player who made the winning bid
     * @param currentLowestBid the bid being played for
     */
    public void skipTalonStage(int player, int currentLowestBid) {
        String message = "PLAYING ";

        if(currentLowestBid == 6){
            message += "PICCOLO";
        } else if (currentLowestBid == 7){
            message += "BEGGAR";
        } else if(currentLowestBid == 8){
            message += "VALAT";
        }

        bidInformerLabel.setText(message);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mainActivity.skipTalon(player, currentLowestBid);
            }
        }, 1500);
    }

    /**
     * Disables all the buttons corresponding to high PlayModes which are no longer playable
     * and indicates who went for what option, and what the current lowest bid is
     */
    public void displayLatestBid(int currentLowestBid){
        // do nothing for invalid indices
        if(currentLowestBid < 0){
            return;
        }
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
    public void enableAllButtons(boolean enable){
        for(Button button : buttonList){
            button.setEnabled(enable);
        }

        skipButton.setEnabled(enable);
    }

    /**
     * Adds all of the play buttons into a List
     * @return the list with all the buttons
     */
    private List<Button> addAllButtons(){
        List<Button> result = new ArrayList<>();

        result.add(playThree);
        result.add(playTwo);
        result.add(playOne);
        result.add(playSoloThree);
        result.add(playSoloTwo);
        result.add(playSoloOne);
        result.add(playPiccolo);
        result.add(playBeggar);
        result.add(playValat);

        return result;
    }

    /**
     * Adds all of the play button labels to a List
     * @return the list of all the labels
     */
    private List<TextView> addAllLabels(){
        List<TextView> result = new ArrayList<>();

        result.add(playThreeLabel);
        result.add(playTwoLabel);
        result.add(playOneLabel);
        result.add(playSoloThreeLabel);
        result.add(playSoloTwoLabel);
        result.add(playSoloOneLabel);
        result.add(playPiccoloLabel);
        result.add(playBeggarLabel);
        result.add(playValatLabel);

        return result;
    }

    /**
     * Sets the text of the ith label to the given text
     * @param i position of the label in the textViewList
     * @param text text to set the label to
     */
    public void setLabelText(int i, String text) {
        textViewList.get(i).setText(text);
    }

    /**
     * Sets the text value of the bidInformationLabel
     * @param text text to set
     */
    public void setInfoLabelText(String text) {
        this.bidInformerLabel.setText(text);
    }
}