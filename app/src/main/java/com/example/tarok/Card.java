package com.example.tarok;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Card extends GameObject{
    private GameSurface gameSurface;

    public Card(GameSurface gameSurface, Bitmap image, int x, int y) {
        super(image, x, y);

        this.gameSurface = gameSurface;
        this.image = createSubImageWithOffset(50);
    }

    public void draw(Canvas canvas)  {
        Bitmap bitmap = this.image;
        canvas.drawBitmap(bitmap,x, y, null);
    }

}
