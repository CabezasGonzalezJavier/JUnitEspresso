package com.dream.myself;

import com.dream.myself.addnote.AddNoteActivity;
import com.dream.myself.notedetail.NoteDetailActivity;
import com.dream.myself.notes.NotesActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by javierg on 22/09/2017.
 */
@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {

    void inject(BaseActivity baseActivity);

}
