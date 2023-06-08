package com.example.tarok.views;

import android.app.Activity;
import android.view.View;
import android.widget.Button;

import com.example.tarok.R;
import com.example.tarok.activities.MainActivity;
import com.example.tarok.utility.PlayMode;

public class PlayButtonsView {
    private MainActivity mainActivity;
    private Button playThree;
    private Button playTwo;
    private Button playOne;
    private Button playSoloThree;
    private Button playSoloTwo;
    private Button playSoloOne;

    public PlayButtonsView(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        this.playThree = mainActivity.findViewById(R.id.playThree);
        this.playTwo = mainActivity.findViewById(R.id.playTwo);
        this.playOne = mainActivity.findViewById(R.id.playOne);
        this.playSoloThree = mainActivity.findViewById(R.id.soloThree);
        this.playSoloTwo = mainActivity.findViewById(R.id.soloTwo);
        this.playSoloOne = mainActivity.findViewById(R.id.soloOne);

        setupButtonOnClick();
    }

    private void setupButtonOnClick(){
        playThree.setOnClickListener(view -> mainActivity.setPlayMode(PlayMode.Three));
        playTwo.setOnClickListener(view -> mainActivity.setPlayMode(PlayMode.Two));
        playOne.setOnClickListener(view -> mainActivity.setPlayMode(PlayMode.One));
        playSoloThree.setOnClickListener(view -> mainActivity.setPlayMode(PlayMode.Solo_Three));
        playSoloTwo.setOnClickListener(view -> mainActivity.setPlayMode(PlayMode.Solo_Two));
        playSoloOne.setOnClickListener(view -> mainActivity.setPlayMode(PlayMode.Solo_One));
    }
}
