package com.example.tarok.utility;

import android.widget.Button;

import com.example.tarok.views.PlayButtonsView;

import java.util.List;

public class BidClickListener {

    private PlayButtonsView playButtonsView;
    private BotBiddingProcess botBiddingProcess;

    public BidClickListener(PlayButtonsView playButtonsView, BotBiddingProcess botBiddingProcess){
        this.playButtonsView = playButtonsView;
        this.botBiddingProcess = botBiddingProcess;
    }

    /**
     * Attaches the appropriate clickListeners to each of the play buttons in the playButtonsView
     * The last button represents the lowest possible bid, meaning that the game automatically starts if
     * this button is clicked, so it gets a special listener
     * @param buttons the list of play buttons from the playButtonsView
     */
    public void setButtonListeners(List<Button> buttons){
        for(int i = 0; i < buttons.size() - 1; i++){
            int finalI = i;
            buttons.get(i).setOnClickListener(view -> {
                botBiddingProcess.setCurrentLowestBid(finalI);
                playButtonsView.enableAllButtons(false);
                playButtonsView.setLabelText(finalI, "PLAYER 1");
                playButtonsView.displayLatestBid(finalI);
                botBiddingProcess.setSkips(0);
                botBiddingProcess.makeBids(0);
            });
        }

        int lastButtonIndex = buttons.size() - 1;

        buttons.get(lastButtonIndex).setOnClickListener(view ->  {
            botBiddingProcess.setCurrentLowestBid(5);
            playButtonsView.setLabelText(lastButtonIndex, "PLAYER 1");
            playButtonsView.displayLatestBid(5);
            playButtonsView.announceWhoPlaysAndInformMain(PlayMode.Solo_One, 1);
        });
    }

    /**
     * Sets the listeners of the skip button
     * @param skipButton skip button
     */
    public void setSkipButtonListener(Button skipButton){
        skipButton.setOnClickListener(view -> {
            playButtonsView.enableAllButtons(false);
            botBiddingProcess.incrementSkips();
            playButtonsView.setInfoLabelText("PLAYER 1 SKIPS");
            if (botBiddingProcess.getSkips() == 3) {
                if(botBiddingProcess.getCurrentLowestBid() != -1){
                    playButtonsView.sendPlayModeToMain(2, botBiddingProcess.getCurrentLowestBid());
                } else {
                    botBiddingProcess.makeBids(0);
                }
            } else if(botBiddingProcess.getSkips() == 4 && botBiddingProcess.getCurrentLowestBid() == -1){
                playButtonsView.playNegative();
            } else {
                botBiddingProcess.makeBids(0);
            }
        });
    }
}
