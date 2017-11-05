package com.example.android.climapp;

/**
 * Created by frksteenhoff on 29-10-2017.
 */

public class Word {
    /* Setting name*/
    private String mSetting;

    /* Setting value */
    private String mSettingValue;

    /* Related art work */
    private int mImageId = NO_IMAGE_PROVIDED;
    private static final int NO_IMAGE_PROVIDED = -1;

    /* New Word object ID */
    public Word(String setting, String settingValue, int imageId) {
        mSetting      = setting;
        mSettingValue = settingValue;
        mImageId      = imageId;
     }

    public Word(String setting, String settingValue){
        mSetting      = setting;
        mSettingValue = settingValue;
    }

    /* GetMiwok translation of word */
    public String getSetting(){
        return mSetting;
    }

    /** Get default translation of word */
    public String getSettingValue() {
        return mSettingValue;
    }

    public int getImage() {
        return mImageId;
    }
    public boolean hasImage() {
        return mImageId != NO_IMAGE_PROVIDED;
    }
}
