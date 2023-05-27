package com.example.tarok;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class GameSurface2 extends View {
    private GameThread gameThread;
    private List<Card> playedCards;
    private int currentPlayer;

    public GameSurface2(Context context, AttributeSet attributeSet)  {
        super(context);

        // Make Game Surface focusable so it can handle events. .
//        this.setFocusable(true);




        currentPlayer=0;

        playedCards = new ArrayList<>();
        playedCards.add(new Card(context,BitmapFactory.decodeResource(this.getResources(),R.drawable.i),0,0,null));
        playedCards.add(new Card(context,BitmapFactory.decodeResource(this.getResources(), R.drawable.ii),0,0,null));
        playedCards.add(new Card(context,BitmapFactory.decodeResource(this.getResources(), R.drawable.iii),0,0,null));
        playedCards.add(new Card(context,BitmapFactory.decodeResource(this.getResources(),R.drawable.iv),0,0,null));
    }

    @Override
    public void draw(Canvas canvas)  {
        super.draw(canvas);
        //Cards are ordered based on the order they are played
        //The rotation angle depends on which player went first

        float scaleFactor = 0.3f;
        float canvasCenterX = canvas.getWidth()/2/scaleFactor;
        float canvasCenterY = canvas.getHeight()/2/scaleFactor;
        canvas.save();
        canvas.scale(scaleFactor,scaleFactor);
        for(Card c:playedCards){


            canvas.rotate(currentPlayer*-90,canvasCenterX , canvasCenterY);
            canvas.drawBitmap(c.image,canvasCenterX,canvasCenterY,null);
            canvas.rotate(currentPlayer*90,canvasCenterX , canvasCenterY);

            currentPlayer++;
        }
        canvas.restore();
        currentPlayer=0;
    }

    public void setFirstPlayer(int firstPlayer){
        this.currentPlayer=firstPlayer;
    }
}
