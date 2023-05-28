package com.example.tarok;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

public class GameSurface extends SurfaceView implements SurfaceHolder.Callback{
    private GameThread gameThread;
    private List<Card> playedCards;
    private int currentPlayer;

    public GameSurface(Context context, AttributeSet attributeSet)  {
        super(context);

        // Make Game Surface focusable so it can handle events. .
        this.setFocusable(true);

        // Sét callback.
        this.getHolder().addCallback(this);

        setupBackground(context);


        currentPlayer=0;

        playedCards = new ArrayList<>();
    }

    private void setupBackground(Context context) {
        // Setup your SurfaceView
        SurfaceView surfaceView = this;  // use any SurfaceView you want
        surfaceView.setZOrderOnTop(true);
//        surfaceView.setZOrderMediaOverlay(true);
        surfaceView.getHolder().setFormat(PixelFormat.TRANSPARENT);

//// Setup your ImageView
//        ImageView bgImagePanel = new ImageView(context);
//        bgImagePanel.setBackgroundResource(R.drawable.background); // use any Bitmap or BitmapDrawable you want
//
//// Use a RelativeLayout to overlap both SurfaceView and ImageView
//        RelativeLayout.LayoutParams fillParentLayout = new RelativeLayout.LayoutParams(
//                RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
//        RelativeLayout rootPanel = new RelativeLayout(context);
//        rootPanel.setLayoutParams(fillParentLayout);
//        rootPanel.addView(surfaceView, fillParentLayout);
//        rootPanel.addView(bgImagePanel, fillParentLayout);
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

    // Implements method of SurfaceHolder.Callback
    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        this.gameThread = new GameThread(this,holder);
        this.gameThread.setRunning(true);
        this.gameThread.start();
    }

    // Implements method of SurfaceHolder.Callback
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    // Implements method of SurfaceHolder.Callback
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry= true;
        while(retry) {
            try {
                this.gameThread.setRunning(false);

                // Parent thread must wait until the end of GameThread.
                this.gameThread.join();
            }catch(InterruptedException e)  {
                e.printStackTrace();
            }
            retry= true;
        }
    }

    public void setFirstPlayer(int firstPlayer){
        this.currentPlayer=firstPlayer;
    }
}
