package com.example.tarok.gameObjects;

import android.content.Context;
import android.graphics.Bitmap;

public abstract class GameObject extends androidx.appcompat.widget.AppCompatImageView{
    protected Bitmap image;
    protected int width;
    protected int height;

    public GameObject(Context context, Bitmap image)  {
        super(context);

        this.image = image;

        this.width = image.getWidth();
        this.height = image.getHeight();
    }

    protected Bitmap createSubImageWithOffset(int offset)  {
        width-=offset*2;
        height-=offset*2;
        return Bitmap.createBitmap(image, offset, offset , width, height);
    }

    public Bitmap getImage() {
        return image;
    }
}
