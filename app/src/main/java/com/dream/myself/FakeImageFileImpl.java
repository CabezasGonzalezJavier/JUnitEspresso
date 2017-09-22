package com.dream.myself;

import com.dream.myself.util.ImageFileImpl;

import java.io.IOException;

/**
 * Created by javierg on 22/09/2017.
 */

public class FakeImageFileImpl extends ImageFileImpl {

    @Override
    public void create(String name, String extension) throws IOException {
        // Do nothing
    }

    @Override
    public String getPath() {
        return "file:///android_asset/atsl-logo.png";
    }

    @Override
    public boolean exists() {
        return true;
    }
}

