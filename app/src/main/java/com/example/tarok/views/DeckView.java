package com.example.tarok.views;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import com.example.tarok.gameObjects.Card;
import com.example.tarok.utility.CardSuite;
import com.example.tarok.R;
import com.example.tarok.utility.DeckDecoder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class DeckView extends LinearLayout {

    private List<Card> cards;
    private TableView tableView;
    LinearLayout.LayoutParams params;

    public DeckView(Context context, AttributeSet attributeSet){
        super(context,attributeSet);

        cards = DeckDecoder.getDeck(context);
        Collections.shuffle(cards);
        cards = cards.stream().limit(12).sorted(this::compareCards).collect(Collectors.toList());
        this.setWeightSum(cards.size());

        params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT,
                1.0f);
        params.setMargins(0, 0, 0, 0);

        this.setClipChildren(false);
        this.setClipToPadding(false);
        this.setGravity(Gravity.CENTER);

        for(Card c:cards){
            c.setLayoutParams(params);
            this.addView(c);
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

    }

    public void addCardToTable(Card card){
        if(tableView==null){
            tableView = ((View) this.getParent()).findViewById(R.id.tableView);
        }
        tableView.addCardToTable(card);

        cards.remove(card);
        this.removeView(card);
        this.setWeightSum(cards.size());
        this.invalidate();
    }

    public int compareCards(Card c1, Card c2){
        if(c1.getSuite()==c2.getSuite())
            return Integer.compare(c1.getValue(),c2.getValue());
        else if(c1.getSuite() == CardSuite.Tarot)
            return 1;
        else if(c2.getSuite() == CardSuite.Tarot)
            return -1;
        else return c1.getSuite().compareTo(c2.getSuite());
    }
}