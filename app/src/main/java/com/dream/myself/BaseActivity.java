package com.dream.myself;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.dream.myself.data.NotesRepository;
import com.dream.myself.util.ImageFile;

import javax.inject.Inject;
/**
 * Created by javierg on 03/10/2017.
 */

public class BaseActivity extends AppCompatActivity {

    @Inject
    protected NotesRepository mRepository;

    @Inject
    protected ImageFile mImageFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initializeDagger();

    }
    private void initializeDagger() {
        DiscernApplication app = (DiscernApplication) getApplication();
        app.getAppComponent().inject(this);
    }
}
