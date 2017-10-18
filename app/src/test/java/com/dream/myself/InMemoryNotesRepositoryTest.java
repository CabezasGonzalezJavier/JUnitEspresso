package com.dream.myself;

import com.dream.myself.data.InMemoryNotesRepository;
import com.dream.myself.data.Note;
import com.dream.myself.data.NotesRepository;
import com.dream.myself.data.NotesServiceApi;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertFalse;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

/**
 * Created by javierg on 18/10/2017.
 */

public class InMemoryNotesRepositoryTest {

    @Mock
    private NotesServiceApi mNotesServiceApi;

    @Mock
    private NotesRepository.LoadNotesCallback mLoadNotesCallback;

    @Mock
    private NotesRepository.GetNoteCallback mGetNoteCallback;

    @Captor
    ArgumentCaptor<NotesServiceApi.NotesServiceCallback> mNotesServiceCallbackArgumentCaptor;

    InMemoryNotesRepository mInMemoryNotesRepository;
    List<Note> NOTES;

    @Before
    public void setUpInMemoryNotesRepositoryTest() {
        MockitoAnnotations.initMocks(this);

        mInMemoryNotesRepository = new InMemoryNotesRepository(mNotesServiceApi);
        NOTES = new ArrayList();
        NOTES.add(new Note("title", "description"));
        NOTES.add(new Note("title", "description"));
    }

    @Test
    public void getNote() {
        Note note = new Note("title", "description");
        mInMemoryNotesRepository.getNote(note.getId(), mGetNoteCallback);

        verify( mNotesServiceApi).getNote(eq(note.getId()),any(NotesServiceApi.NotesServiceCallback.class));
    }

    @Test
    public void getNotes() {
        mInMemoryNotesRepository.getNotes(mLoadNotesCallback);
        verify(mNotesServiceApi).getAllNotes(any(NotesServiceApi.NotesServiceCallback.class));
    }

    @Test
    public void saveNoteTest(){

        Note note = new Note("title", "description");

        mInMemoryNotesRepository.saveNote(note);

        verify(mNotesServiceApi).saveNote(note);
        assertThat(mInMemoryNotesRepository.mCachedNotes, is(nullValue()));
    }
}
