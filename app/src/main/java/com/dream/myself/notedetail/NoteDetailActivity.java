/*
 * Copyright (C) 2015 The Android Open Source Project
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

package com.dream.myself.notedetail;

import android.os.Bundle;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.dream.myself.DiscernApplication;
import com.dream.myself.R;
import com.dream.myself.data.NotesRepository;
import com.dream.myself.util.EspressoIdlingResource;
import com.dream.myself.util.ImageFile;
import com.dream.myself.util.StartActivity;

import javax.inject.Inject;

/**
 * Displays note details screen.
 */
public class NoteDetailActivity extends AppCompatActivity {

    @Inject
    NotesRepository mRepository;

    @Inject
    ImageFile mImageFile;

    public static final String EXTRA_NOTE_ID = "NOTE_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail);

        // Set up the toolbar.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowHomeEnabled(true);

        // Get the requested note id
        String noteId = getIntent().getStringExtra(EXTRA_NOTE_ID);
        initializeDagger();
        initFragment(noteId);
    }

    private void initializeDagger() {
        DiscernApplication app = (DiscernApplication) getApplication();
        app.getAppComponent().inject(this);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void initFragment(String noteId) {
        NoteDetailFragment noteDetailFragment = (NoteDetailFragment) getSupportFragmentManager().
                findFragmentById(R.id.contentFrame);
        if (noteDetailFragment == null) {
            noteDetailFragment = noteDetailFragment.newInstance(noteId);
            StartActivity.addFragmentToActivity(getSupportFragmentManager(), noteDetailFragment, R.id.contentFrame);
        }

        new NoteDetailPresenter(mRepository, noteDetailFragment);

    }

    @VisibleForTesting
    public IdlingResource getCountingIdlingResource() {
        return EspressoIdlingResource.getIdlingResource();
    }
}
