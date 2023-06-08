package com.example.tarok.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;

import com.example.tarok.gameObjects.Card;
import com.example.tarok.utility.CardSuite;
import com.example.tarok.utility.DeckUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@SuppressLint("ClickableViewAccessibility")
public class EndGameCardsView extends LinearLayout {

    private List<Card> cards;

    public EndGameCardsView(Context context, AttributeSet attributeSet){
        super(context,attributeSet);

        this.setClipChildren(false);
        this.setClipToPadding(false);
        this.setGravity(Gravity.CENTER);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    public void createDeckFromList(List<Card> cardList){
        cards = cardList.stream().map(x->x.resetCard(getContext())).sorted(this::compareCards).collect(Collectors.toList());
        this.setWeightSum(cards.size());

        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1.0f);
        params.setMargins(0, 0, 0, 0);

        for(Card c:cards){
            c.setLayoutParams(params);
            c.lockCard();
            this.addView(c);
        }
    }

    private int compareCards(Card c1, Card c2){
        if(c1.getPoints()==c2.getPoints())
            return -c1.getSuite().compareTo(c2.getSuite());
        else return -Integer.compare(c1.getPoints(),c2.getPoints());
    }
}