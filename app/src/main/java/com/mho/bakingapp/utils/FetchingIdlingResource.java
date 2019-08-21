package com.mho.bakingapp.utils;

import androidx.test.espresso.IdlingResource;

import java.util.concurrent.atomic.AtomicBoolean;

public class FetchingIdlingResource implements IdlingResource, FetcherListener {

    //region Fields

    private AtomicBoolean idle = new AtomicBoolean(true);

    private IdlingResource.ResourceCallback resourceCallback;

    //endregion

    //region Override Methods & Callbacks

    @Override
    public String getName() {
        return FetchingIdlingResource.class.getSimpleName();
    }

    @Override
    public boolean isIdleNow() {
        return idle.get();
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {
        resourceCallback = callback;
    }

    @Override
    public void doneFetching() {
        idle.set(true);
        resourceCallback.onTransitionToIdle();
    }

    @Override
    public void beginFetching() {
        idle.set(false);
    }

    //endregion
}