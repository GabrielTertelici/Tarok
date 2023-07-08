package com.example.tarok.utility;

import android.widget.Button;

import com.example.tarok.views.PlayButtonsView;

import java.util.List;

public class BidClickListener {

    private PlayButtonsView playButtonsView;

    public BidClickListener(PlayButtonsView playButtonsView){
        this.playButtonsView = playButtonsView;
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
                playButtonsView.setCurrentLowestBid(finalI);
                playButtonsView.setLabelText(finalI, "PLAYER 1");
                playButtonsView.enableAllButtons(false);
                playButtonsView.displayLatestBid();
                playButtonsView.setSkips(0);
                playButtonsView.makeBotBids(0);
            });
        }

        int lastButtonIndex = buttons.size() - 1;

        buttons.get(lastButtonIndex).setOnClickListener(view ->  {
            playButtonsView.setCurrentLowestBid(5);
            playButtonsView.setLabelText(lastButtonIndex, "PLAYER 1");
            playButtonsView.displayLatestBid();
            playButtonsView.announceWhoPlaysAndInformMain(PlayMode.Solo_One, 1);
        });
    }

    /**
     * Sets the listeners of the skip button
     * @param skipButton skip button
     */
    public void setSkipButtonListener(Button skipButton){
        skipButton.setOnClickListener(view -> {
            if(playButtonsView.getCurrentLowestBid() == 0){
                playButtonsView.setInfoLabelText("PLEASE MAKE THE FIRST BID");
            } else {
                playButtonsView.incrementSkips();
                playButtonsView.setInfoLabelText("PLAYER 1 SKIPS");
                if (playButtonsView.getSkips() == 3) {
                    playButtonsView.sendPlayModeToMain(2);
                } else {
                    playButtonsView.makeBotBids(0);
                }
            }
        });
    }
}
