package com.example.tarok.unused;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class GameThreadOld extends Thread{
    private boolean running;
    private GameSurfaceOld gameSurfaceOld;
    private SurfaceHolder surfaceHolder;

    public GameThreadOld(GameSurfaceOld gameSurfaceOld, SurfaceHolder surfaceHolder)  {
        this.gameSurfaceOld = gameSurfaceOld;
        this.surfaceHolder= surfaceHolder;
    }

    @Override
    public void run()  {
        long startTime = System.nanoTime();

        while(running)  {
            Canvas canvas= null;
            try {
                // Get Canvas from Holder and lock it.
                canvas = this.surfaceHolder.lockCanvas();

                // Synchronized
                synchronized (canvas)  {
                    this.gameSurfaceOld.draw(canvas);
                }
            }catch(Exception e)  {
                // Do nothing.
            } finally {
                if(canvas!= null)  {
                    // Unlock Canvas.
                    this.surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
            long now = System.nanoTime() ;
            // Interval to redraw game
            // (Change nanoseconds to milliseconds)
            long waitTime = (now - startTime)/1000000;
            if(waitTime < 10)  {
                waitTime= 10; // Millisecond.
            }
            System.out.print(" Wait Time="+ waitTime);

            try {
                // Sleep.
                sleep(waitTime);
            } catch(InterruptedException e)  {
                throw new RuntimeException("Thread sleep failed");
            }
            startTime = System.nanoTime();
            System.out.print(".");
        }
    }

    public void setRunning(boolean running)  {
        this.running= running;
    }
}
