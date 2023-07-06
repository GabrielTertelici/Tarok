package com.example.tarok.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.Space;

import com.example.tarok.activities.MainActivity;
import com.example.tarok.bots.BotTalonPickingRule;
import com.example.tarok.bots.GreedyTalonPickingRule;
import com.example.tarok.gameObjects.Card;
import com.example.tarok.utility.DeckUtils;
import com.example.tarok.utility.PlayMode;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TalonCardsView extends LinearLayout {

    private MainActivity mainActivity;
    private List<Card> kings;
    private List<Card> cards;
    private List<Card> chosenCards;
    private Card chosenKing;
    private PlayMode playMode;

    private BotTalonPickingRule talonPickingRule = new GreedyTalonPickingRule();
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

    @SuppressLint("ClickableViewAccessibility")
    public void showKings(){
        this.removeAllViews();
        this.setWeightSum(4);
        kings = mainActivity.getKings();

        params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1.0f);
        params.setMargins(0, 0, 0, 0);

        for(Card c:kings){
            c.setLayoutParams(params);
            c.setOnTouchListener((view, motionEvent) -> {
                performClick();
                handleKingClick(c);
                return false;
            });
            this.addView(c);
        }
    }

    /**
     * Draws the four kings which can be chosen, but since a bot is playing, the
     * human player is unable to select a king
     */
    @SuppressLint("ClickableViewAccessibility")
    public void showUnclickableKings(){
        this.removeAllViews();
        this.setWeightSum(4);
        kings = mainActivity.getKings();

        params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1.0f);
        params.setMargins(0, 0, 0, 0);

        for(Card c:kings){
            this.addView(c);
        }
    }

    public void displayKingChoice(int pickedKing, List<Card> deck){
        Card king = kings.get(pickedKing);
        handleKingClick(king);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                createUnclickableTalon(deck);
            }
        }, 1000);
    }

    @SuppressLint("ClickableViewAccessibility")
    public void createTalon(){
        this.removeAllViews();

        params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1.0f);
        params.setMargins(0, 0, 0, 0);

        for(Card c:cards){
            c.setLayoutParams(params);
            c.setOnTouchListener((view, motionEvent) -> {
                performClick();
                handleClick(c);
                return false;
            });
            this.addView(c);
        }

        addSpaces();
    }

    /**
     * Draws the talon options, but, since a bot is playing, the human player is unable to
     * select any cards
     *
     * @param deck the deck of the bot making the choice
     */
    @SuppressLint("ClickableViewAccessibility")
    public void createUnclickableTalon(List<Card> deck){
        this.removeAllViews();

        params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1.0f);
        params.setMargins(0, 0, 0, 0);

        for(Card c:cards){
            this.addView(c);
        }

        addSpaces();

        Card pickedCard = talonPickingRule.pickCardFromTalon(deck, cards);

        displayTalonChoice(pickedCard);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                putDownCards();
                startGame();
            }
        }, 1000);

    }

    public void putDownCards(){};
    public void startGame(){};

    /**
     * Highlights the talon partition chosen by the bot
     */
    public void displayTalonChoice(Card card){
        List<Card> inGroup = getGroup(card);
        for(Card c:cards){
            c.setAlpha(1f);
            if(inGroup.contains(c)){
                c.setAlpha(0.5f);
            }
        }
        chosenCards = inGroup;
    }

    private void addSpaces() {
        if(playMode == PlayMode.Three||playMode==PlayMode.Solo_Three){
            this.setWeightSum(7);
            this.addView(new Space(getContext()),3,params);
        }
        else if(playMode == PlayMode.Two||playMode==PlayMode.Solo_Two){
            this.setWeightSum(8);
            this.addView(new Space(getContext()),2,params);
            this.addView(new Space(getContext()),5,params);
        }
        else{
            this.setWeightSum(11);
            this.addView(new Space(getContext()),1,params);
            this.addView(new Space(getContext()),3,params);
            this.addView(new Space(getContext()),5,params);
            this.addView(new Space(getContext()),7,params);
            this.addView(new Space(getContext()),9,params);
        }
    }

    private void handleClick(Card card) {
        mainActivity.removeTalonCards();
        List<Card> inGroup = getGroup(card);
        for(Card c:cards){
            c.setAlpha(1f);
            if(inGroup.contains(c)){
                c.setAlpha(0.5f);
            }
        }
        chosenCards = inGroup;
        mainActivity.addCardsToDeck(inGroup);
    }

    private List<Card> getGroup(Card card) {
        List<Card> result = new ArrayList<>();
        if(playMode==PlayMode.Three||playMode==PlayMode.Solo_Three){
            if(cards.indexOf(card)<=2){
                result.add(cards.get(0)); result.add(cards.get(1)); result.add(cards.get(2));
            }
            else{
                result.add(cards.get(3)); result.add(cards.get(4)); result.add(cards.get(5));
            }
        }
        else if(playMode==PlayMode.Two||playMode==PlayMode.Solo_Two){
            if(cards.indexOf(card)<=1){
                result.add(cards.get(0)); result.add(cards.get(1));
            }
            else if(cards.indexOf(card)<=3){
                result.add(cards.get(2)); result.add(cards.get(3));
            }
            else{
                result.add(cards.get(4)); result.add(cards.get(5));
            }
        }
        else{
            result.add(card);
        }
        return result;
    }

    private void handleKingClick(Card c) {
        for(Card card:kings){
            card.setAlpha(1f);
        }
        c.setAlpha(0.5f);
        chosenKing=c;
    }

    public List<Card> getChosenCards() {
        return chosenCards;
    }

    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public void setPlayMode(PlayMode playMode) {
        this.playMode = playMode;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public Card getChosenKing() {
        return chosenKing;
    }
}