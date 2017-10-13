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

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

/**
 * Created by javierg on 13/10/2017.
 */

public class InMemoryNotesRepositoryTest {

    private final static String TITLE = "title";
    private final static String DESCRIPTION = "description";

    @Mock
    private NotesServiceApi mNotesServiceApi;

    @Mock
    NotesRepository.LoadNotesCallback mLoadNotesCallback;

    @Mock
    NotesRepository.GetNoteCallback mGetNoteCallback;

    @Captor
    ArgumentCaptor<NotesServiceApi.NotesServiceCallback> mNotesServiceCallbackArgumentCaptor;

    InMemoryNotesRepository mInMemoryNotesRepository;

    @Before
    public void setUpInMemoryNotesRepositoryTest() {
        MockitoAnnotations.initMocks(this);

        mInMemoryNotesRepository = new InMemoryNotesRepository(mNotesServiceApi);
    }

    @Test
    public void getNotesTest() {
        mInMemoryNotesRepository.getNotes(mLoadNotesCallback);

        verify(mNotesServiceApi).getAllNotes(any(NotesServiceApi.NotesServiceCallback.class));
    }

    @Test
    public void getNote_onNotesLoaded() {

        Note note = new Note(TITLE, DESCRIPTION);

        mInMemoryNotesRepository.getNote(note.getId(), mGetNoteCallback);

        verify(mNotesServiceApi).getNote(eq(note.getId()), any(NotesServiceApi.NotesServiceCallback.class));

    }

    @Test
    public void saveNoteTest() {


        Note note = new Note(TITLE, DESCRIPTION);

        mInMemoryNotesRepository.saveNote(note);

        verify(mNotesServiceApi).saveNote(note);
        assertThat(mInMemoryNotesRepository.mCachedNotes, is(nullValue()));
    }

}
