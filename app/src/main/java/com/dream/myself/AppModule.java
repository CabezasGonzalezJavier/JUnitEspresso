package com.dream.myself;

import com.dream.myself.data.NoteRepositories;
import com.dream.myself.data.NotesRepository;
import com.dream.myself.util.ImageFile;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by javierg on 22/09/2017.
 */
@Module
public class AppModule {


    DiscernApplication mDiscernApplication;

    public AppModule(DiscernApplication discernApplication) {
        mDiscernApplication = discernApplication;
    }

    @Singleton
    @Provides
    public static ImageFile provideImageFile() {
        return new FakeImageFileImpl();
    }

    @Singleton
    @Provides
    public static NotesRepository provideNotesRepository() {
        return NoteRepositories.getInMemoryRepoInstance(new FakeNotesServiceApiImpl());
    }

}
