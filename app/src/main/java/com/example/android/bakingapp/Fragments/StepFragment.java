package com.example.android.bakingapp.Fragments;

import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.bakingapp.Classes.Recipe;
import com.example.android.bakingapp.R;
import com.example.android.bakingapp.StepActivity;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;

import java.lang.annotation.Target;

public class StepFragment extends Fragment {

    private Recipe mRecipe;
    private int mIndex;
    private long mPlayerPositon;
    private boolean mIsPlayWhenReady = true;
    private TextView mInstruction;
    private SimpleExoPlayer mSimpleExoPlayer;
    private SimpleExoPlayerView mSimpleExoPlayerView;
    private BottomNavigationView mBottomNavigationView;

    public StepFragment(){
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_step, container, false);

        if(savedInstanceState != null){
            mRecipe = savedInstanceState.getParcelable("recipeStep");
            mIndex = savedInstanceState.getInt("indexStep", 0);
            mPlayerPositon = savedInstanceState.getLong("playerPosition", 0);
            mIsPlayWhenReady = savedInstanceState.getBoolean("isPlayWhenReady");
        }

        mInstruction = rootView.findViewById(R.id.fragment_step_instruction);
        mBottomNavigationView = rootView.findViewById(R.id.fragment_step_navigation);
        mSimpleExoPlayerView = rootView.findViewById(R.id.fragment_step_video);

        mInstruction.setText(mRecipe.getSteps().get(mIndex).getLongDescription());


        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getTitle().equals(getResources().getString(R.string.fragment_step_navigation_previous))){
                    if(mIndex > 0){
                        mIndex--;
                    }
                } else if(item.getTitle().equals(getResources().getString(R.string.fragment_step_navigation_next))){
                    if(mIndex < mRecipe.getSteps().size()-1 ){
                        mIndex++;
                    }
                }
                mInstruction.setText(mRecipe.getSteps().get(mIndex).getLongDescription());
                releasePlayer();
                mIsPlayWhenReady = true;
                initializePlayer(mRecipe.getSteps().get(mIndex).getVideoUrl(), mRecipe.getSteps().get(mIndex).getThumbnailUrl());
                return true;
            }
        });
        if(getActivity().getClass().getName() == StepActivity.class.getName()){
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                goFullScreen();
                mSimpleExoPlayerView.getLayoutParams().height = FrameLayout.LayoutParams.MATCH_PARENT;
            }
            else {
                goNormalScreen();
            }
        } else {
            goNormalScreen();
        }


        initializePlayer(mRecipe.getSteps().get(mIndex).getVideoUrl(), mRecipe.getSteps().get(mIndex).getThumbnailUrl());

        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("recipeStep", mRecipe);
        outState.putInt("indexStep", mIndex);
        outState.putLong("playerPosition", mPlayerPositon);
        outState.putBoolean("isPlayWhenReady", mIsPlayWhenReady);
    }

    public void setRecipe(Recipe recipe){
        mRecipe = recipe;
    }

    public void setIndex(int i){
        mIndex = i;
    }

    private void initializePlayer(String mediaUrl, String thumbnailUrl){
        if(mSimpleExoPlayer == null){
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mSimpleExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
            mSimpleExoPlayerView.setPlayer(mSimpleExoPlayer);
            mSimpleExoPlayerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIXED_WIDTH);

            if(mediaUrl.isEmpty()){

                if(!thumbnailUrl.isEmpty()){
                    Picasso.with(getContext())
                            .load(thumbnailUrl)
                            .into(new com.squareup.picasso.Target() {
                                @Override
                                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                                    mSimpleExoPlayerView.setDefaultArtwork(bitmap);
                                }

                                @Override
                                public void onBitmapFailed(Drawable errorDrawable) {
                                    mSimpleExoPlayerView.setDefaultArtwork(BitmapFactory.decodeResource(
                                            getResources(), R.drawable.no_video));
                                }

                                @Override
                                public void onPrepareLoad(Drawable placeHolderDrawable) {

                                }
                            });

                } else {
                    mSimpleExoPlayerView.setDefaultArtwork(BitmapFactory.decodeResource(
                            getResources(), R.drawable.no_video));
                }

                mSimpleExoPlayerView.setUseController(false);
            } else {
                Uri mediaUri = Uri.parse(mediaUrl);
                String userAgent = Util.getUserAgent(getContext(), "BakingApp");
                MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                        getContext(), userAgent), new DefaultExtractorsFactory(), null, null);
                mSimpleExoPlayer.prepare(mediaSource);
                mSimpleExoPlayer.seekTo(mPlayerPositon);
                mSimpleExoPlayer.setPlayWhenReady(mIsPlayWhenReady);
                mSimpleExoPlayerView.setUseController(true);
            }
        }
    }

    private void releasePlayer(){
        if(mSimpleExoPlayer != null) {
            mSimpleExoPlayer.stop();
            mPlayerPositon = mSimpleExoPlayer.getCurrentPosition();
            mIsPlayWhenReady = mSimpleExoPlayer.getPlayWhenReady();
            mSimpleExoPlayer.release();
            mSimpleExoPlayer = null;
        }
    }

    private void goFullScreen(){
        mBottomNavigationView.setVisibility(View.GONE);
        mInstruction.setVisibility(View.GONE);
    }

    private void goNormalScreen(){
        mBottomNavigationView.setVisibility(View.VISIBLE);
        mInstruction.setVisibility(View.VISIBLE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        releasePlayer();
    }

    @Override
    public void onPause() {
        super.onPause();
        releasePlayer();
    }

    @Override
    public void onStop() {
        releasePlayer();
        super.onStop();
    }
}
