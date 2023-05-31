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

        cards = decodeResources(context);
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


    public List<Card> decodeResources(Context context){
        List<Card> result = new ArrayList<>();

        result.addAll(decodeTarots(context));
        result.addAll(decodeHearts(context));
        result.addAll(decodeDiamonds(context));
        result.addAll(decodeClubs(context));
        result.addAll(decodeSpades(context));

        return result;
    }

    private List<Card> decodeTarots(Context context){
        List<Card> result = new ArrayList<>();

        result.add(new Card(context,BitmapFactory.decodeResource(this.getResources(),R.drawable.i),0,0,this, CardSuite.Tarot,1));
        result.add(new Card(context,BitmapFactory.decodeResource(this.getResources(), R.drawable.ii),0,0,this,CardSuite.Tarot,2));
        result.add(new Card(context,BitmapFactory.decodeResource(this.getResources(), R.drawable.iii),0,0,this,CardSuite.Tarot,3));
        result.add(new Card(context,BitmapFactory.decodeResource(this.getResources(),R.drawable.iv),0,0,this,CardSuite.Tarot,4));
        result.add(new Card(context,BitmapFactory.decodeResource(this.getResources(), R.drawable.v),0,0,this,CardSuite.Tarot,5));
        result.add(new Card(context,BitmapFactory.decodeResource(this.getResources(), R.drawable.vi),0,0,this,CardSuite.Tarot,6));
        result.add(new Card(context,BitmapFactory.decodeResource(this.getResources(),R.drawable.vii),0,0,this,CardSuite.Tarot,7));
        result.add(new Card(context,BitmapFactory.decodeResource(this.getResources(), R.drawable.viii),0,0,this,CardSuite.Tarot,8));
        result.add(new Card(context,BitmapFactory.decodeResource(this.getResources(), R.drawable.ix),0,0,this,CardSuite.Tarot,9));
        result.add(new Card(context,BitmapFactory.decodeResource(this.getResources(),R.drawable.x),0,0,this,CardSuite.Tarot,10));
        result.add(new Card(context,BitmapFactory.decodeResource(this.getResources(), R.drawable.xi),0,0,this,CardSuite.Tarot,11));
        result.add(new Card(context,BitmapFactory.decodeResource(this.getResources(), R.drawable.xii),0,0,this,CardSuite.Tarot,12));
        result.add(new Card(context,BitmapFactory.decodeResource(this.getResources(), R.drawable.xiii),0,0,this,CardSuite.Tarot,13));
        result.add(new Card(context,BitmapFactory.decodeResource(this.getResources(),R.drawable.xiv),0,0,this,CardSuite.Tarot,14));
        result.add(new Card(context,BitmapFactory.decodeResource(this.getResources(), R.drawable.xv),0,0,this,CardSuite.Tarot,15));
        result.add(new Card(context,BitmapFactory.decodeResource(this.getResources(), R.drawable.xvi),0,0,this,CardSuite.Tarot,16));
        result.add(new Card(context,BitmapFactory.decodeResource(this.getResources(),R.drawable.xvii),0,0,this,CardSuite.Tarot,17));
        result.add(new Card(context,BitmapFactory.decodeResource(this.getResources(), R.drawable.xviii),0,0,this,CardSuite.Tarot,18));
        result.add(new Card(context,BitmapFactory.decodeResource(this.getResources(), R.drawable.xix),0,0,this,CardSuite.Tarot,19));
        result.add(new Card(context,BitmapFactory.decodeResource(this.getResources(),R.drawable.xx),0,0,this,CardSuite.Tarot,20));
        result.add(new Card(context,BitmapFactory.decodeResource(this.getResources(), R.drawable.xxi),0,0,this,CardSuite.Tarot,21));
        result.add(new Card(context,BitmapFactory.decodeResource(this.getResources(), R.drawable.xxii),0,0,this,CardSuite.Tarot,22));

        return result;
    }

    private List<Card> decodeHearts(Context context){
        List<Card> result = new ArrayList<>();

        result.add(new Card(context,BitmapFactory.decodeResource(this.getResources(),R.drawable.heart_1),20,0,this,CardSuite.Heart,1));
        result.add(new Card(context,BitmapFactory.decodeResource(this.getResources(), R.drawable.heart_2),20,0,this,CardSuite.Heart,2));
        result.add(new Card(context,BitmapFactory.decodeResource(this.getResources(), R.drawable.heart_3),20,0,this,CardSuite.Heart,3));
        result.add(new Card(context,BitmapFactory.decodeResource(this.getResources(),R.drawable.heart_4),20,0,this,CardSuite.Heart,4));
        result.add(new Card(context,BitmapFactory.decodeResource(this.getResources(), R.drawable.heart_boy),0,0,this,CardSuite.Heart,5));
        result.add(new Card(context,BitmapFactory.decodeResource(this.getResources(), R.drawable.heart_horse),0,0,this,CardSuite.Heart,6));
        result.add(new Card(context,BitmapFactory.decodeResource(this.getResources(),R.drawable.heart_queen),0,0,this,CardSuite.Heart,7));
        result.add(new Card(context,BitmapFactory.decodeResource(this.getResources(), R.drawable.heart_king),0,0,this,CardSuite.Heart,8));

        return result;
    }

    private List<Card> decodeDiamonds(Context context){
        List<Card> result = new ArrayList<>();

        result.add(new Card(context,BitmapFactory.decodeResource(this.getResources(),R.drawable.diamond_1),20,0,this,CardSuite.Diamond,1));
        result.add(new Card(context,BitmapFactory.decodeResource(this.getResources(), R.drawable.diamond_2),20,0,this,CardSuite.Diamond,2));
        result.add(new Card(context,BitmapFactory.decodeResource(this.getResources(), R.drawable.diamond_3),20,0,this,CardSuite.Diamond,3));
        result.add(new Card(context,BitmapFactory.decodeResource(this.getResources(),R.drawable.diamond_4),20,0,this,CardSuite.Diamond,4));
        result.add(new Card(context,BitmapFactory.decodeResource(this.getResources(), R.drawable.diamond_boy),0,0,this,CardSuite.Diamond,5));
        result.add(new Card(context,BitmapFactory.decodeResource(this.getResources(), R.drawable.diamond_horse),0,0,this,CardSuite.Diamond,6));
        result.add(new Card(context,BitmapFactory.decodeResource(this.getResources(),R.drawable.diamond_queen),0,0,this,CardSuite.Diamond,7));
        result.add(new Card(context,BitmapFactory.decodeResource(this.getResources(), R.drawable.diamond_king),0,0,this,CardSuite.Diamond,8));

        return result;
    }

    private List<Card> decodeClubs(Context context){
        List<Card> result = new ArrayList<>();

        result.add(new Card(context,BitmapFactory.decodeResource(this.getResources(),R.drawable.club_1),20,0,this,CardSuite.Club,1));
        result.add(new Card(context,BitmapFactory.decodeResource(this.getResources(), R.drawable.club_2),20,0,this,CardSuite.Club,2));
        result.add(new Card(context,BitmapFactory.decodeResource(this.getResources(), R.drawable.club_3),20,0,this,CardSuite.Club,3));
        result.add(new Card(context,BitmapFactory.decodeResource(this.getResources(),R.drawable.club_4),20,0,this,CardSuite.Club,4));
        result.add(new Card(context,BitmapFactory.decodeResource(this.getResources(), R.drawable.club_boy),0,0,this,CardSuite.Club,5));
        result.add(new Card(context,BitmapFactory.decodeResource(this.getResources(), R.drawable.club_horse),0,0,this,CardSuite.Club,6));
        result.add(new Card(context,BitmapFactory.decodeResource(this.getResources(),R.drawable.club_queen),0,0,this,CardSuite.Club,7));
        result.add(new Card(context,BitmapFactory.decodeResource(this.getResources(), R.drawable.club_king),0,0,this,CardSuite.Club,8));

        return result;
    }

    private List<Card> decodeSpades(Context context){
        List<Card> result = new ArrayList<>();

        result.add(new Card(context,BitmapFactory.decodeResource(this.getResources(),R.drawable.spade_1),20,0,this,CardSuite.Spade,1));
        result.add(new Card(context,BitmapFactory.decodeResource(this.getResources(), R.drawable.spade_2),20,0,this,CardSuite.Spade,2));
        result.add(new Card(context,BitmapFactory.decodeResource(this.getResources(), R.drawable.spade_3),20,0,this,CardSuite.Spade,3));
        result.add(new Card(context,BitmapFactory.decodeResource(this.getResources(),R.drawable.spade_4),20,0,this,CardSuite.Spade,4));
        result.add(new Card(context,BitmapFactory.decodeResource(this.getResources(), R.drawable.spade_boy),0,0,this,CardSuite.Spade,5));
        result.add(new Card(context,BitmapFactory.decodeResource(this.getResources(), R.drawable.spade_horse),0,0,this,CardSuite.Spade,6));
        result.add(new Card(context,BitmapFactory.decodeResource(this.getResources(),R.drawable.spade_queen),0,0,this,CardSuite.Spade,7));
        result.add(new Card(context,BitmapFactory.decodeResource(this.getResources(), R.drawable.spade_king),0,0,this,CardSuite.Spade,8));

        return result;
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