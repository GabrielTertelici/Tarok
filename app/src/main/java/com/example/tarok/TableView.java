package com.example.tarok;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;


public class TableView extends View {
    private GameThread gameThread;
    private List<Card> playedCards;
    private int currentPlayer;

    public TableView(Context context, AttributeSet attrs) {
        super(context,attrs);

        // Make Game Surface focusable so it can handle events. .
        this.setFocusable(true);

        currentPlayer=0;

        playedCards = new ArrayList<>();

    }

    @Override
    public void draw(Canvas canvas)  {
        super.draw(canvas);
        //Cards are ordered based on the order they are played
        //The rotation angle depends on which player went first

        float scaleFactor = 0.3f;
        float canvasCenterX = canvas.getWidth()/2f/scaleFactor;
        float canvasCenterY = canvas.getHeight()/2f/scaleFactor;
        canvas.save();
        canvas.scale(scaleFactor,scaleFactor);
        for(Card c:playedCards){


            canvas.rotate(currentPlayer*-90,canvasCenterX , canvasCenterY);
            canvas.drawBitmap(c.image,canvasCenterX-c.getWidth()/2f,canvasCenterY-c.getHeight()/3f,null);
            canvas.rotate(currentPlayer*90,canvasCenterX , canvasCenterY);

            currentPlayer++;
        }
        canvas.restore();
        currentPlayer=0;
    }

    @Override
    public void onFinishInflate() {
        super.onFinishInflate();
    }

    public void addCardToTable(Card c){
        playedCards.add(c);
        this.invalidate();
        Log.println(Log.INFO,"L","Added card "+c);
    }
}
