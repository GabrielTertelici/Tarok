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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@SuppressLint("ClickableViewAccessibility")
public class DealtCardsView extends LinearLayout {

    private List<Card> cards;
    private List<Card> selectedCards;
    private int selectableCards;
    LinearLayout.LayoutParams params;

    public DealtCardsView(Context context, AttributeSet attributeSet){
        super(context,attributeSet);

        this.setClipChildren(false);
        this.setClipToPadding(false);
        this.setGravity(Gravity.CENTER);

        selectedCards = new ArrayList<>();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

    }

    public void createDeckFromList(List<Card> cardList){
        cards = cardList.stream().sorted(DeckView::compareCards).collect(Collectors.toList());
        this.setWeightSum(cards.size());

        params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1.0f);
        params.setMargins(0, 0, 0, 0);

        for(Card c:cards){
            c.setLayoutParams(params);
            c.setOnTouchListener((view, motionEvent) -> {
                performClick();
                if(!c.isLocked()){
                    handleTouch(c);
                }
                return false;
            });
            c.lockCard();
            this.addView(c);
        }
    }

    public void beginPutCardsDown(){
        unlockBoard();
        List<Card> invalidCards = DeckUtils.getFivePointCards(cards);
        for(Card c:invalidCards){
            c.invalidateCard();
        }
    }

    public void unlockBoard(){
        for(Card c:cards){
            c.unlockCard();
        }
    }

    private void handleTouch(Card c) {
        if(!selectedCards.contains(c)){
            if(selectedCards.size()<selectableCards){
                selectedCards.add(c);
                c.setAlpha(0.5f);
            }
        }
        else{
            selectedCards.remove(c);
            c.setAlpha(1f);
        }

    }

    public void addCard(Card c) {
        cards.add(c);
        this.removeAllViews();
        createDeckFromList(cards);
    }

    public void removeCard(Card c) {
        cards.remove(c);
        selectedCards.remove(c);
        this.removeView(c);
        this.setWeightSum(cards.size());
    }

    public List<Card> getCards() {
        return cards;
    }

    public List<Card> getSelectedCards() {
        return selectedCards;
    }

    public int getSelectableCards() {
        return selectableCards;
    }
    public void setSelectableCards(int selectableCards) {
        this.selectableCards = selectableCards;
    }
}