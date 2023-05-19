package com.example.tarok;

import android.graphics.Bitmap;

public abstract class GameObject {
    protected Bitmap image;
    protected int width;
    protected int height;
    protected int x;
    protected int y;

    public GameObject(Bitmap image, int x, int y)  {

        this.image = image;

        this.x= x;
        this.y= y;

        this.width = image.getWidth();
        this.height = image.getHeight();
    }

    protected Bitmap createSubImageWithOffset(int offset)  {
        width-=offset*2;
        height-=offset*2;
        return Bitmap.createBitmap(image, offset, offset , width, height);
    }

    public int getX()  {
        return this.x;
    }

    public int getY()  {
        return this.y;
    }


    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

}
