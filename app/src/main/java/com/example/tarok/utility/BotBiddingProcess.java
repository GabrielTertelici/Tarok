package com.example.tarok.utility;

import android.os.Handler;

import com.example.tarok.bots.BotTalonStageRuleManager;
import com.example.tarok.gameObjects.Card;
import com.example.tarok.views.PlayButtonsView;

import java.util.List;

public class BotBiddingProcess {

    private int currentLowestBid;
    private int skips;
    private PlayButtonsView playButtonsView;
    private BotTalonStageRuleManager botManager;
    private List<List<Card>> decks;

    public BotBiddingProcess(PlayButtonsView playButtonsView,
                             BotTalonStageRuleManager botManager,
                             List<List<Card>> decks){
        // 0 represents playing for 3, -1 represents an invalid initial bid
        this.currentLowestBid = -1;
        this.skips = 0;
        this.playButtonsView = playButtonsView;
        this.decks = decks;
        this.botManager = botManager;
    }

    /**
     * Simulates the process of bots choosing what play mode to go for
     * Each bot randomly chooses whether to play for the next lowest play mode, or to skip
     * @param bidderId id of the bot making a decision (bidderId = 0 -> bot 1...)
     */
    public void makeBids(int bidderId){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(bidderId > 2){
                    playButtonsView.enableAllButtons(true);
                    playButtonsView.displayLatestBid(currentLowestBid);
                    return;
                }

                int decision = botManager.decideWhatToPlayFor(decks.get(bidderId), currentLowestBid);
                //REPLACE THE LINE ABOVE WITH THE LINE BELOW TO FORCE ALL THE BOTS TO SKIP AND PLAY NEGATIVE
                //int decision = -1;

                if(decision > currentLowestBid){
                    currentLowestBid = decision;
                    skips = 0;

                    playButtonsView.setLabelText(currentLowestBid, "PLAYER " + (bidderId + 2));

                    playButtonsView.setInfoLabelText("PLAYER " + (bidderId + 2) + " PLAYS");

                    playButtonsView.displayLatestBid(currentLowestBid);

                    if(currentLowestBid == 5){
                        playButtonsView.announceWhoPlaysAndInformMain(PlayMode.Solo_One, bidderId + 2);
                        return;
                    }
                } else {
                    skips++;

                    playButtonsView.setInfoLabelText("PLAYER " + (bidderId + 2) + " SKIPS");

                    if(skips == 3){
                        if(currentLowestBid != -1){
                            playButtonsView.sendPlayModeToMain((bidderId + 2) % 4 + 1, currentLowestBid);
                            return;
                        }
                    }

                    if(skips == 4 && currentLowestBid == -1){
                        playButtonsView.playNegative();
                        return;
                    }
                }

                makeBids(bidderId + 1);
            }
        }, 1000);
    }

    /**
     * Getter for currentLowestBid field
     * @return currentLowestBid
     */
    public int getCurrentLowestBid() {
        return currentLowestBid;
    }

    /**
     * Getter for skips field
     * @return skips
     */
    public int getSkips() {
        return skips;
    }

    /**
     * Setter for currentLowestBid field
     * @param currentLowestBid
     */
    public void setCurrentLowestBid(int currentLowestBid) {
        this.currentLowestBid = currentLowestBid;
    }

    /**
     * Setter for skips field
     * @param skips
     */
    public void setSkips(int skips) {
        this.skips = skips;
    }

    /**
     * Increases the number of skips by one
     */
    public void incrementSkips() {
        this.skips++;
    }
}
