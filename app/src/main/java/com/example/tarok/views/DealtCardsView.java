package com.example.tarok.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.example.tarok.gameObjects.Card;
import com.example.tarok.utility.CardSuite;
import com.example.tarok.R;
import com.example.tarok.utility.DeckUtils;
import com.example.tarok.utility.GameStage;

import java.util.List;
import java.util.stream.Collectors;

public class DealtCardsView extends LinearLayout {

    private List<Card> cards;
    LinearLayout.LayoutParams params;

    public DealtCardsView(Context context, AttributeSet attributeSet){
        super(context,attributeSet);

        this.setClipChildren(false);
        this.setClipToPadding(false);
        this.setGravity(Gravity.CENTER);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

    }

    @SuppressLint("ClickableViewAccessibility")
    public void createDeckFromList(List<Card> cardList){
        cards = cardList.stream().sorted(DeckView::compareCards).collect(Collectors.toList());
        this.setWeightSum(cards.size());

        params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1.0f);
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