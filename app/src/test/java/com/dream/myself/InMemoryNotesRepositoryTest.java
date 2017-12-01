package com.dream.myself;

import com.dream.myself.data.InMemoryNotesRepository;
import com.dream.myself.data.Note;
import com.dream.myself.data.NotesRepository;
import com.dream.myself.data.NotesServiceApi;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyList;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by javierg on 01/12/2017.
 */

public class InMemoryNotesRepositoryTest {

    @Mock
    private NotesRepository.LoadNotesCallback mLoadNotesCallback;

    @Mock
    private NotesRepository.GetNoteCallback mGetNoteCallback;

    @Mock
    private NotesServiceApi mNotesServiceApi;
    Note mNote;

    InMemoryNotesRepository mInMemoryNotesRepository;

    @Before
    public void setUp() {

        MockitoAnnotations.initMocks(this);
        mNote = new Note("title", "description");
        mInMemoryNotesRepository = new InMemoryNotesRepository(mNotesServiceApi);
    }

    @Test
    public void getNotes() {

        mInMemoryNotesRepository.getNotes(mLoadNotesCallback);

        verify(mNotesServiceApi).getAllNotes(any(NotesServiceApi.NotesServiceCallback.class));
    }

    @Test
    public void saveNote() {

        mInMemoryNotesRepository.saveNote(mNote);

        // Then the notes cache is cleared
        assertThat(mInMemoryNotesRepository.mCachedNotes, is(nullValue()));
    }

    @Test
    public void getNote() {

        String noteId = "noteId";

        mInMemoryNotesRepository.getNote(eq(noteId), mGetNoteCallback);

        verify(mNotesServiceApi).getNote(noteId, any(NotesServiceApi.NotesServiceCallback.class));

    }

}
