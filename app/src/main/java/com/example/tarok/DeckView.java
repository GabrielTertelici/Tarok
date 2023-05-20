package com.example.tarok;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO: document your custom view class.
 */
public class DeckView extends LinearLayout {

    private List<Card> cards;

    public DeckView(Context context, AttributeSet attributeSet){
        super(context,attributeSet);

        cards = decodeResources(context);
        this.setWeightSum(cards.size());

        for(Card c:cards){
            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                    c.width,
                    c.height,
                    1.0f
            );
            param.setMargins(0, 0, 0, 0);
            c.setLayoutParams(param);
            this.addView(c);
        }


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }


    public List<Card> decodeResources(Context context){
        List<Card> result = new ArrayList<>();

        result.add(new Card(context,BitmapFactory.decodeResource(this.getResources(),R.drawable.i),0,0,this));
        result.add(new Card(context,BitmapFactory.decodeResource(this.getResources(), R.drawable.ii),0,0,this));
        result.add(new Card(context,BitmapFactory.decodeResource(this.getResources(), R.drawable.iii),0,0,this));
        result.add(new Card(context,BitmapFactory.decodeResource(this.getResources(),R.drawable.iv),0,0,this));
        result.add(new Card(context,BitmapFactory.decodeResource(this.getResources(), R.drawable.v),0,0,this));
        result.add(new Card(context,BitmapFactory.decodeResource(this.getResources(), R.drawable.vi),0,0,this));
        result.add(new Card(context,BitmapFactory.decodeResource(this.getResources(),R.drawable.xvii),0,0,this));
        result.add(new Card(context,BitmapFactory.decodeResource(this.getResources(), R.drawable.xviii),0,0,this));
        result.add(new Card(context,BitmapFactory.decodeResource(this.getResources(), R.drawable.xix),0,0,this));
        result.add(new Card(context,BitmapFactory.decodeResource(this.getResources(),R.drawable.xx),0,0,this));
        result.add(new Card(context,BitmapFactory.decodeResource(this.getResources(), R.drawable.xxi),0,0,this));
        result.add(new Card(context,BitmapFactory.decodeResource(this.getResources(), R.drawable.xxii),0,0,this));

        return result;
    }
}