package com.example.android.bakingapp.Classes;

import android.os.Parcel;
import android.os.Parcelable;

public class Step implements Parcelable{

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

    protected Step(Parcel in) {
        mShortDescription = in.readString();
        mLongDescription = in.readString();
        mVideoUrl = in.readString();
        mThumbnailUrl = in.readString();
    }

    public static final Creator<Step> CREATOR = new Creator<Step>() {
        @Override
        public Step createFromParcel(Parcel in) {
            return new Step(in);
        }

        @Override
        public Step[] newArray(int size) {
            return new Step[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mShortDescription);
        parcel.writeString(mLongDescription);
        parcel.writeString(mVideoUrl);
        parcel.writeString(mThumbnailUrl);
    }
}
