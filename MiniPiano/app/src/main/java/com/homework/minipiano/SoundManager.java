package com.homework.minipiano;
import android.app.Activity;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioAttributes;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.media.SoundPool;
import android.os.Handler;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseIntArray;

import java.io.InputStream;


public class SoundManager {
    private SoundPool mSoundPool;
    private SparseArray<PlayThread> threadMap = null;
    private Context mContext;
    private static final int MAX_STREAM = 10;
    private static final SparseArray<Integer> SOUND_MAP = new SparseArray<>();
    private SparseIntArray mSoundPoolMap;
    private static final int STOP_DELAY_MILLIS = 2000;
    private static SoundManager instance =null;
    private Handler mHandler;
    static {
        // white keys sounds
        SOUND_MAP.put(1, R.raw.note_do);
        SOUND_MAP.put(2, R.raw.note_re);
        SOUND_MAP.put(3, R.raw.note_mi);
        SOUND_MAP.put(4, R.raw.note_fa);
        SOUND_MAP.put(5, R.raw.note_sol);
        SOUND_MAP.put(6, R.raw.note_la);
        SOUND_MAP.put(7, R.raw.note_si);
        SOUND_MAP.put(8, R.raw.second_do);
        SOUND_MAP.put(9,  R.raw.second_re);
        SOUND_MAP.put(10, R.raw.second_mi);
        SOUND_MAP.put(11, R.raw.second_fa);
        SOUND_MAP.put(12, R.raw.second_sol);
        SOUND_MAP.put(13, R.raw.second_la);
        SOUND_MAP.put(14, R.raw.second_si);
        // black keys sounds
        SOUND_MAP.put(15, R.raw.do_dies);
        SOUND_MAP.put(16, R.raw.re_dies);
        SOUND_MAP.put(17, R.raw.fa_dies);
        SOUND_MAP.put(18, R.raw.sol_dies);
        SOUND_MAP.put(19, R.raw.la_dies);
        SOUND_MAP.put(20, R.raw.second_dies_do);
        SOUND_MAP.put(21, R.raw.second_dies_re);
        SOUND_MAP.put(22, R.raw.second_dies_fa);
        SOUND_MAP.put(23, R.raw.second_dies_sol);
        SOUND_MAP.put(24, R.raw.second_dies_la);
    }

    public SoundManager() {

        AudioAttributes audioAttrib = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build();

        mSoundPool = new SoundPool.Builder()
                .setMaxStreams(MAX_STREAM)
                .setAudioAttributes(audioAttrib)
                .build();
        mSoundPoolMap = new SparseIntArray();
        threadMap = new SparseArray<>();
        mHandler = new Handler();
    }
    public static SoundManager getInstance(){
        if (instance == null){
            instance = new SoundManager();
        }

        return instance;
    }
    public void init(Context context){
        this.mContext = context;
        instance.initStreamTypeMedia((Activity)mContext);
        for (int i = 1 ;i<=24; i++){
            instance.addSound(i);
        }
    }
    public void addSound(int soundID){
        mSoundPoolMap.put(soundID, mSoundPool.load(mContext, SOUND_MAP.get(soundID), 1));
    }
    public void initStreamTypeMedia(Activity activity){
        activity.setVolumeControlStream(AudioManager.STREAM_MUSIC);
    }

    public void playNote(int note) {
        if (!isNotePlaying(note)) {
            PlayThread thread = new PlayThread(note);
            thread.start();
            threadMap.put(note, thread);
        }
    }

    public void stopNote(int note) {
        PlayThread thread = threadMap.get(note);

        if (thread != null) {
            threadMap.remove(note);
        }
    }
    private void scheduleSoundStop(final int soundID){
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mSoundPool.stop(soundID);
            }
        }, STOP_DELAY_MILLIS);
    }
    public boolean isNotePlaying(int note) {
        return threadMap.get(note) != null;
    }

    private class PlayThread extends Thread {
        int note;

        public PlayThread(int note) {
            this.note = note;
        }

        @Override
        public void run() {
            try {
                final int soundId = mSoundPool.play(mSoundPoolMap.get(note),1,1,1,0,1f);
                scheduleSoundStop(soundId);
            } catch (Exception e) {
                Log.d("SoundManager",e.getMessage());
            }
        }
    }

}