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
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;

/**
 * Created by javierg on 24/10/2017.
 */

public class InMemoryNotesRepositoryTest {


    private static final String TITLE = "title";
    private static final String DESCRIPTION = "description";

    @Mock
    private NotesServiceApi mNotesServiceApi;

    @Mock
    private NotesRepository.LoadNotesCallback mLoadNotesCallback;

    @Mock
    private NotesRepository.GetNoteCallback mGetNoteCallback;

    private InMemoryNotesRepository mInMemoryNotesRepository;

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

        Note note = new Note(TITLE, DESCRIPTION);

        mInMemoryNotesRepository.saveNote(note);

        verify(mNotesServiceApi).saveNote(any(Note.class));
    }

    @Test
    public void getNote() {

        Note note = new Note(TITLE, DESCRIPTION);

        mInMemoryNotesRepository.getNote(note.getId(), mGetNoteCallback);

        verify(mNotesServiceApi).getNote(anyString(), any(NotesServiceApi.NotesServiceCallback.class));
    }
}
