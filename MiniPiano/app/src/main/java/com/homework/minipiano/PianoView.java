package com.homework.minipiano;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class PianoView extends View {
    private static int NUMBER_KEYS= 14;
    private Paint black, white, yellow;
    private ArrayList<Key> whites, blacks;
    private int keyWidth, keyHeight;
    private SoundManager soundPlayer;
    private Handler mHandler;
    public PianoView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        black = new Paint();
        black.setColor(Color.BLACK);
        black.setStyle(Paint.Style.FILL);

        white = new Paint();
        white.setColor(Color.WHITE);
        white.setStyle(Paint.Style.FILL);

        yellow = new Paint();
        yellow.setColor(Color.YELLOW);
        yellow.setStyle(Paint.Style.FILL);

        whites = new ArrayList<Key>();
        blacks = new ArrayList<Key>();
        mHandler = new Handler();
        soundPlayer = SoundManager.getInstance();
        soundPlayer.init(context);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        keyWidth = w / NUMBER_KEYS;
        keyHeight = h;
        int blackCount = 15;
        for (int i = 0; i<NUMBER_KEYS; i++){
            int left = i*keyWidth;
            int right = left + keyWidth;

            if (i == NUMBER_KEYS - 1) {
                right = w;
            }

            RectF rect = new RectF(left, 0, right, keyHeight);
            whites.add(new Key(i+1, rect));
            if (i!=0 && i!= 3 && i!= 7 && i!= 10){
                rect = new RectF((float)(i-1)*keyWidth+0.75f*keyWidth,
                                0,
                                (float)i*keyWidth+0.25f*keyWidth,
                                keyHeight*0.67f);
                blacks.add(new Key(blackCount++, rect));
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (Key k : whites){
            canvas.drawRect(k.rect, k.isDown?yellow:white);
        }
        for (int i = 1; i <NUMBER_KEYS; i++){
            canvas.drawLine(i*keyWidth,0,i*keyWidth,keyHeight,black);
        }
        for (Key k : blacks){
            canvas.drawRect(k.rect, k.isDown?yellow:black);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        boolean isDownAction = action == MotionEvent.ACTION_DOWN
                            || action == MotionEvent.ACTION_MOVE;

        for (int touchIndex = 0; touchIndex < event.getPointerCount(); touchIndex++) {
            float x = event.getX(touchIndex);
            float y = event.getY(touchIndex);

            Key k = keyForCoords(x,y);

            if (k != null) {
                k.isDown = isDownAction;
            }
        }
        ArrayList<Key> tmp = new ArrayList<>(whites);
        tmp.addAll(blacks);

        for (Key k : tmp) {
            if (k.isDown) {
                if (!soundPlayer.isNotePlaying(k.sound)) {
                    soundPlayer.playNote(k.sound);
                    invalidate();
                } else {
                    releaseKey(k);
                }
            } else {
                soundPlayer.stopNote(k.sound);
                releaseKey(k);
            }
        }
        return true;

    }
    private Key keyForCoords(float x, float y) {
        for (Key k : blacks) {
            if (k.rect.contains(x,y)) {
                return k;
            }
        }

        for (Key k : whites) {
            if (k.rect.contains(x,y)) {
                return k;
            }
        }

        return null;
    }

    private void releaseKey(final Key k) {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                k.isDown = false;
                invalidate();
            }
        },100);
    }

}
