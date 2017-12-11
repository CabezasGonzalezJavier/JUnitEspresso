/*
 * Copyright 2015, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dream.myself.addnote;

import android.os.Bundle;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import com.dream.myself.BaseActivity;
import com.dream.myself.R;
import com.dream.myself.util.EspressoIdlingResource;
import com.dream.myself.util.StartActivity;

/**
 * Displays an add note screen.
 */
public class AddNoteActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addnote);

        // Set up the toolbar.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.add_note);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        if (null == savedInstanceState) {
            initFragment(AddNoteFragment.newInstance());
        }

        initFragment();
    }

    private void initFragment () {
        AddNoteFragment addNoteFragment = (AddNoteFragment) getSupportFragmentManager().
                findFragmentById(R.id.contentFrame);
        if (addNoteFragment == null) {
            addNoteFragment = addNoteFragment.newInstance();
            StartActivity.addFragmentToActivity(getSupportFragmentManager(),
                    addNoteFragment, R.id.contentFrame);
        }

        new AddNotePresenter(mRepository, addNoteFragment, mImageFile);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void initFragment(Fragment detailFragment) {
        // Add the AddNoteFragment to the layout
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.contentFrame, detailFragment);
        transaction.commit();
    }

    @VisibleForTesting
    public IdlingResource getCountingIdlingResource() {
        return EspressoIdlingResource.getIdlingResource();
    }
}
