package com.example.tarok.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import com.example.tarok.R;
import com.example.tarok.activities.MainActivity;
import com.example.tarok.utility.CardSuite;

import java.util.ArrayList;
import java.util.List;

public class PlayerIconsView {
    private MainActivity mainActivity;
    private ImageView player1;
    private ImageView player2;
    private ImageView player3;
    private ImageView player4;
    private Bitmap heart;
    private Bitmap diamond;
    private Bitmap club;
    private Bitmap spade;
    private Bitmap teammate;

    public PlayerIconsView(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        player1 = mainActivity.findViewById(R.id.player_1_image);
        player2 = mainActivity.findViewById(R.id.player_2_image);
        player3 = mainActivity.findViewById(R.id.player_3_image);
        player4 = mainActivity.findViewById(R.id.player_4_image);
        resetImageViews();
        decodeResources();
    }

    private void decodeResources() {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 2;
        options.inJustDecodeBounds = false;
        Context context = mainActivity.getApplicationContext();
        heart = BitmapFactory.decodeResource(context.getResources(),R.drawable.heart_icon, options);
        diamond = BitmapFactory.decodeResource(context.getResources(),R.drawable.diamond_icon, options);
        club = BitmapFactory.decodeResource(context.getResources(),R.drawable.club_icon, options);
        spade = BitmapFactory.decodeResource(context.getResources(),R.drawable.spade_icon, options);
        teammate = BitmapFactory.decodeResource(context.getResources(),R.drawable.teammate_icon, options);
    }

    public void setPlayer1King(CardSuite e){
        setPlayerKing(e,player1);
    }
    public void setPlayer2King(CardSuite e){
        setPlayerKing(e,player2);
    }
    public void setPlayer3King(CardSuite e){
        setPlayerKing(e,player3);
    }
    public void setPlayer4King(CardSuite e){
        setPlayerKing(e,player4);
    }

    private void setPlayerKing(CardSuite e,ImageView player){
        switch(e){
            case Heart -> player.setImageBitmap(heart);
            case Diamond -> player.setImageBitmap(diamond);
            case Club -> player.setImageBitmap(club);
            case Spade -> player.setImageBitmap(spade);
            default -> player.setImageBitmap(teammate);//This should not happen, here for debugging
        }
    }
    private void setPlayerTeammate(ImageView player){
        player.setImageBitmap(teammate);
    }

    public void setPlayerTeammates(int player, int teamMate) {
        resetImageViews();
        //The human played -> can have at most one teammate = the one given
        if(player==1){
            switch (teamMate){
                case 2->setPlayerTeammate(player2);
                case 3->setPlayerTeammate(player3);
                case 4->setPlayerTeammate(player4);
            }
        }
        //Another bot played and picked the human -> at most one teammate
        else if(teamMate==1){
            switch (player){
                case 2->setPlayerTeammate(player2);
                case 3->setPlayerTeammate(player3);
                case 4->setPlayerTeammate(player4);
            }
        }
        else{
            List<Integer> teammates = new ArrayList<>();
            teammates.add(2); teammates.add(3); teammates.add(4);
            teammates.remove((Integer) player);
            teammates.remove((Integer) teamMate);
            for(Integer i:teammates){
                switch (i){
                    case 2->setPlayerTeammate(player2);
                    case 3->setPlayerTeammate(player3);
                    case 4->setPlayerTeammate(player4);
                }
            }
        }
    }

    private void resetImageViews(){
        player1.setImageBitmap(null);
        player2.setImageBitmap(null);
        player3.setImageBitmap(null);
        player4.setImageBitmap(null);
    }
}
