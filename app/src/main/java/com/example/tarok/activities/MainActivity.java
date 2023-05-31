package com.example.tarok.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.example.tarok.R;
import com.example.tarok.utility.GameStage;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set fullscreen
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Set No Title
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);



//        setContentView(new GameSurface(this));
        setContentView(R.layout.sample_board_view);

        GameStage g = new GameStage(findViewById(R.id.deckView),findViewById(R.id.tableView),getApplicationContext());
    }
}