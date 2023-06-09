package com.example.tarok.gameObjects;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.graphics.PorterDuff;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.example.tarok.utility.CardSuite;
import com.example.tarok.utility.DeckUtils;
import com.example.tarok.views.DeckView;

public class Card extends GameObject{
    private float _xDelta;
    private float _yDelta;
    private DeckView rootLayout;
    private boolean locked;
    /**
     * This is bad design but for some reason the width
     * of an image is not constant (when cards get removed
     * from deck it grows), so I just set it to the
     * first width = smallest one (since the deck is full)
     * and use that for all calculations
     */
    private static float width;
    private final CardSuite suite;

    /** This is how the Card Value is labeled:
     * 1-4 = Empty suite cards ("1" = one of spades = four of hearts)
     * 5-8 = Boy, Horseman, Queen, King (in that order)
     * 1-22 = Tarots (for comparing who picks up we therefore need to take into account suite as well)
     */
    private final int value;

    private final int points;

    public Card(Context context, Bitmap image, CardSuite suite, int value) {
        super(context,image);
        this.setImageBitmap(this.image);

        this.suite = suite;
        this.value = value;
        this.locked = false;
        this.points = DeckUtils.calculatePoints(this.suite,this.value);

        this.setOnTouchListener(new ChoiceTouchListener());

    }
    private final class ChoiceTouchListener implements OnTouchListener {
        public boolean onTouch(View view, MotionEvent event) {
            performClick();
            if(!locked){
                if(rootLayout==null)
                    setupRoot();
                final float X = event.getRawX();
                final float Y = event.getRawY();
                switch (event.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_DOWN:
                        _xDelta = X - view.getTranslationX();
                        _yDelta = Y - view.getTranslationY();
                        view.animate().scaleX(1.1f).scaleY(1.1f);
                        view.setZ(1);
                        break;
                    case MotionEvent.ACTION_UP:
                        if(view.getAlpha()==0.5f){
                            //For now we know that this belongs to the human = player 1
                            rootLayout.getGameStage().playCard((Card) view,1);
                        }
                        view.animate().scaleX(1.0f).scaleY(1.0f);
                        view.animate().translationX(0);
                        view.animate().translationY(0);
                        view.setZ(0);
                        view.animate().rotationX(0);
                        break;
                    case MotionEvent.ACTION_POINTER_DOWN:
                        break;
                    case MotionEvent.ACTION_POINTER_UP:
                        break;
                    case MotionEvent.ACTION_MOVE:
                        view.setTranslationX(X-_xDelta);
                        if(Y-_yDelta<=0){
                            view.setTranslationY(Y-_yDelta);
                            view.setRotationX(Math.min(Math.abs(Y-_yDelta)/10,30));
                        }
                        if(cardCanBeDropped(view,X,Y)){
                            view.setAlpha(0.5f);
                        }
                        else{
                            view.setAlpha(1f);
                        }
                        break;
                }
                rootLayout.invalidate();
            }
            return true;
        }
    }
    public void lockCard(){
        locked = true;
    }

    public void unlockCard(){
        locked=false;
    }
    public boolean isLocked(){return locked;}
    public void invalidateCard(){
        lockCard();
        this.setColorFilter(new LightingColorFilter(0xFFFFFF00, 0x00000000));
    }
    public void validateCard(){
        unlockCard();
        this.clearColorFilter();
    }
    private void setupRoot() {
        rootLayout = (DeckView) this.getParent();
        setAllParentsClip(this);
    }

    private void setAllParentsClip(View view) {
        while (view.getParent() != null && view.getParent() instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view.getParent();
            viewGroup.setClipChildren(false);
            viewGroup.setClipToPadding(false);
            view = viewGroup;
        }
    }

    public CardSuite getSuite() {
        return suite;
    }

    public int getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o){
        if(o == null) return false;
        if(o instanceof Card){
            Card c = (Card) o;
            return c.image.equals(this.image);
        }
        return false;
    }

    public static float getCardImageWidth() {
        return width;
    }

    public int getPoints() {
        return points;
    }

    private boolean cardCanBeDropped(View card, float X, float Y ){
        View frame = (View) rootLayout.getParent();
        if(width==0)
            width = this.getWidth();
        X-=width/2f;

        return (Math.abs(X)>frame.getWidth()*0.3 && Math.abs(X)<frame.getWidth()*0.7)//Centered-ish in screen
            && (Math.abs(card.getTranslationY())>card.getHeight() && Y>card.getHeight());
    }

    @Override
    public String toString() {
        return "Card{" +
                "suite=" + suite +
                ", value=" + value +
                ", points=" + points +
                '}';
    }

    /**
     * This method should be called for each card
     * before restarting the game so as to reset
     * the internal variables of each card
     */
    public Card resetCard(Context context){
        return new Card(context,this.image,this.suite,this.value);
    }
}
