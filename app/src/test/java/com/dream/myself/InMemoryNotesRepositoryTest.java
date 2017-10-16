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

import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;

/**
 * Created by javierg on 16/10/2017.
 */

public class InMemoryNotesRepositoryTest {

    private final static String TITLE = "title";

    @Mock
    private NotesServiceApi mNotesServiceApi;

    @Mock
    private NotesRepository.LoadNotesCallback mLoadNotesCallback;

    @Mock
    private NotesRepository.GetNoteCallback mGetNoteCallback;

    @Captor
    ArgumentCaptor<NotesRepository.GetNoteCallback> mGetNoteCallbackArgumentCaptor;

    InMemoryNotesRepository mInMemoryNotesRepository;

    List<Note> mNotes;

    @Before
    public void setUpInMemoryNotesRepositoryTest() {

        MockitoAnnotations.initMocks(this);

        mInMemoryNotesRepository = new InMemoryNotesRepository(mNotesServiceApi);

        mNotes = new ArrayList<>();
        mNotes.add(new Note("title", "description"));
        mNotes.add(new Note("title", "description"));

    }

    @Test
    public void saveNoteTest() {
        Note note = new Note("title", "description");

        mInMemoryNotesRepository.saveNote(note);

        verify(mNotesServiceApi).saveNote(note);
    }

    @Test
    public void getNotes_requestsAllNotesFromServiceApi() {

        Note note = new Note("title", "description");

        mInMemoryNotesRepository.getNotes(mLoadNotesCallback);

        verify(mNotesServiceApi).getAllNotes(any(NotesServiceApi.NotesServiceCallback.class));
    }

    @Test
    public void saveNote_savesNoteToServiceAPIAndInvalidatesCache() {

        Note newNote = new Note("title", "description");

        mInMemoryNotesRepository.saveNote(newNote);

        assertThat(mInMemoryNotesRepository.mCachedNotes, is(nullValue()));
    }

    @Test
    public void getNote_requestsSingleNoteFromServiceApi() {

        mInMemoryNotesRepository.getNote(TITLE, mGetNoteCallback);

        verify(mNotesServiceApi).getNote(anyString(), any(NotesServiceApi.NotesServiceCallback.class));
    }

}