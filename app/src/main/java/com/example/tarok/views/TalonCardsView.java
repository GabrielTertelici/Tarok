package com.example.tarok.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;

import com.example.tarok.gameObjects.Card;

import java.util.List;
import java.util.stream.Collectors;

public class TalonCardsView extends LinearLayout {

    private List<Card> cards;
    LayoutParams params;

    public TalonCardsView(Context context, AttributeSet attributeSet){
        super(context,attributeSet);

        this.setClipChildren(false);
        this.setClipToPadding(false);
        this.setGravity(Gravity.CENTER);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

    }

    /**
     * This method constructs the view from the talon cards
     * @param talonCards The cards that are in the talon
     * @param playedFor 1,2 or 3
     */
    @SuppressLint("ClickableViewAccessibility")
    public void createTalon(List<Card> talonCards, int playedFor){
        cards = talonCards;
        this.setWeightSum(cards.size());

        params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1.0f);
        params.setMargins(0, 0, 0, 0);

        for(Card c:cards){
            c.setLayoutParams(params);
            c.setOnTouchListener((view, motionEvent) -> {
                performClick();
                handleTouch(c);
                return false;
            });
            this.addView(c);
        }
    }

    private void handleTouch(Card c) {
        c.setAlpha(0.5f);
    }
}