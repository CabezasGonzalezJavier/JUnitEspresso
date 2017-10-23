package com.dream.myself;

import com.dream.myself.data.InMemoryNotesRepository;
import com.dream.myself.data.Note;
import com.dream.myself.data.NotesRepository;
import com.dream.myself.data.NotesServiceApi;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

/**
 * Created by javierg on 23/10/2017.
 */

public class InMemoryNotesRepositoryTest {

    @Mock
    private NotesServiceApi mNotesServiceApi;

    @Mock
    private NotesRepository.LoadNotesCallback mLoadNotesCallback;

    @Mock
    private NotesRepository.GetNoteCallback mGetNoteCallback;

    InMemoryNotesRepository mInMemoryNotesRepository;

    @Before
    public void setUpInMemoryNotesRepositoryTest() {
        MockitoAnnotations.initMocks(this);

        mInMemoryNotesRepository = new InMemoryNotesRepository(mNotesServiceApi);
    }

    @Test
    public void getNotes() {

        mInMemoryNotesRepository.getNotes(mLoadNotesCallback);

        verify(mNotesServiceApi).getAllNotes(any(NotesServiceApi.NotesServiceCallback.class));

    }

    @Test
    public void saveNote() {
        Note note = new Note("title", "description");

        mInMemoryNotesRepository.saveNote(note);

        verify(mNotesServiceApi).saveNote(any(Note.class));
    }

    @Test
    public void getNote() {
        Note note = new Note("title", "description");

        mInMemoryNotesRepository.getNote(note.getId(), mGetNoteCallback);

        verify(mNotesServiceApi).getNote(eq(note.getId()), any(NotesServiceApi.NotesServiceCallback.class));
    }
}
