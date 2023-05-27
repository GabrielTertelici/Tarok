package com.example.tarok;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class Card extends GameObject{
    private float _xDelta;
    private float _yDelta;
    private float scale;
    private DeckView rootLayout;

    public Card(Context context, Bitmap image, int x, int y, DeckView rootLayout) {
        super(context,image, x, y);
        this.image = createSubImageWithOffset(50);
        this.setImageBitmap(this.image);

        this.rootLayout = rootLayout;

        this.setOnTouchListener(new ChoiceTouchListener());
    }
    private final class ChoiceTouchListener implements OnTouchListener {
        public boolean onTouch(View view, MotionEvent event) {
            performClick();
            final float X = event.getRawX();
            final float Y = event.getRawY();
            switch (event.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN:
                    _xDelta = X - view.getTranslationX();
                    _yDelta = Y - view.getTranslationY();
                    view.animate().scaleX(1.1f).scaleY(1.1f);
                    view.setZ(1);
                    rootLayout.setZ(1);
                    break;
                case MotionEvent.ACTION_UP:
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
                    break;
            }
            rootLayout.invalidate();
            return true;
        }
    }

}
