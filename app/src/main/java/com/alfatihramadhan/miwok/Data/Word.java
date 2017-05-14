package com.alfatihramadhan.miwok.Data;

/**
 * Created by alfatih on 4/30/2017.
 */

public class Word {

    private String englishTranslation;
    private String miwokTranslation;
    private int audioResourceID;
    private int imageId = NO_IMAGE_PROVIDED;
    public static final int NO_IMAGE_PROVIDED = -1;

    public Word(String english, String miwok,int audio){
        englishTranslation = english;
        miwokTranslation = miwok;
        audioResourceID = audio;
    }

    public Word(String english, String miwok, int imageId, int audio){
        this(english,miwok,audio);
        this.imageId = imageId;
    }

    public String getEnglishTranslation() {
        return englishTranslation;
    }

    public String getMiwokTranslation() {
        return miwokTranslation;
    }

    public int getImageId() {
        return imageId;
    }

    public boolean getNoImageProvided() {
        return imageId != NO_IMAGE_PROVIDED;
    }

    public int getAudioResourceID() {
        return audioResourceID;
    }
}
