package com.example.android.bakingapp.Classes;

public class Step{

    private String mShortDescription;
    private String mLongDescription;
    private String mVideoUrl;
    private String mThumbnailUrl;

    public Step(String shortDescription, String longDescription, String videoUrl, String thumbnailUrl){
        mShortDescription = shortDescription;
        mLongDescription = longDescription;
        mVideoUrl = videoUrl;
        mThumbnailUrl = thumbnailUrl;
    }

    public String getShortDescription(){
        return mShortDescription;
    }

    public String getLongDescription(){
        return mLongDescription;
    }

    public String getVideoUrl(){
        return mVideoUrl;
    }

    public String getThumbnailUrl(){
        return mThumbnailUrl;
    }
}
