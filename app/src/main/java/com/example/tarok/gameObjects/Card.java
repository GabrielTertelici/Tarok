package com.example.tarok.gameObjects;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.example.tarok.utility.CardSuite;
import com.example.tarok.views.DeckView;

public class Card extends GameObject{
    private float _xDelta;
    private float _yDelta;
    private DeckView rootLayout;
    private static float width;

    private CardSuite suite;

    /** This is how the Card Value is labeled:
     * 1-4 = Empty suite cards ("1" = one of spades = four of hearts)
     * 5-8 = Boy, Horseman, Queen, King (in that order)
     * 1-22 = Tarots (for comparing who picks up we therefore need to take into account suite as well)
     */
    private int value;

    public Card(Context context, Bitmap image,boolean needsOffset, CardSuite suite, int value) {
        super(context,image);
        if(needsOffset)
            this.image = createSubImageWithOffset(50);
        else
            this.image = createSubImageWithOffset(0);
        this.setImageBitmap(this.image);

        this.suite = suite;
        this.value = value;

        this.setOnTouchListener(new ChoiceTouchListener());

    }
    private final class ChoiceTouchListener implements OnTouchListener {
        public boolean onTouch(View view, MotionEvent event) {
            if(rootLayout==null)
                setupRoot();
            performClick();
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
                    if(view.getAlpha()<1){
                        rootLayout.addCardToTable((Card) view);
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
            return true;
        }
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

    private boolean cardCanBeDropped(View card, float X, float Y ){
        View frame = (View) rootLayout.getParent();
        if(width==0)
            width = this.getWidth();
        X-=width/2f;

        return (Math.abs(X)>frame.getWidth()*0.3 && Math.abs(X)<frame.getWidth()*0.7)//Centered-ish in screen
            && (Math.abs(card.getTranslationY())>card.getHeight() && Y>card.getHeight());
    }

}
