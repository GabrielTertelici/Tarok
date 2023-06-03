package com.example.tarok.views;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.tarok.gameObjects.Card;
import com.example.tarok.unused.GameThreadOld;

import java.util.ArrayList;
import java.util.List;


public class TableView extends View {
    private List<Card> playedCards;
    private int firstPlayer;

    public TableView(Context context, AttributeSet attrs) {
        super(context,attrs);

        // Make Game Surface focusable so it can handle events. .
        this.setFocusable(true);

        firstPlayer=1;

        playedCards = new ArrayList<>();

    }

    @Override
    public void draw(Canvas canvas)  {
        super.draw(canvas);
        //Cards are ordered based on the order they are played
        //The rotation angle depends on which player went first

        float scaleFactor = 0.27f;
        float centerX = canvas.getWidth()/2f/scaleFactor;
        float centerY = canvas.getHeight()/2f/scaleFactor;
        canvas.save();
        canvas.scale(scaleFactor,scaleFactor);
        int currentPlayer = firstPlayer-1;
        for(Card c:playedCards){


            canvas.rotate(currentPlayer*-90,centerX , centerY);
            canvas.drawBitmap(c.getImage(),centerX-Card.getCardImageWidth()/2f,centerY-c.getHeight()/2f,null);
            canvas.rotate(currentPlayer*90,centerX , centerY);

            currentPlayer++;
        }
        canvas.restore();
    }

    @Override
    public void onFinishInflate() {
        super.onFinishInflate();
    }

    public void addCardToTable(Card c){
        playedCards.add(c);
        this.invalidate();
    }

    public void clearTable(){
        playedCards = new ArrayList<>();
        this.invalidate();
    }

    public void setFirstPlayer(int firstPlayer) {
        this.firstPlayer = firstPlayer;
    }
}
