package com.dream.myself;

import com.dream.myself.data.Note;
import com.dream.myself.data.NotesRepository;
import com.dream.myself.notes.NotesContract;
import com.dream.myself.notes.NotesPresenter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;

/**
 * Created by javierg on 11/10/2017.
 */

public class NotesPresenterTest {

    private static List<Note> NOTES;

    @Mock
    private NotesRepository mNotesRepository;

    @Mock
    private NotesContract.View mNotesView;

    @Captor
    private ArgumentCaptor<NotesRepository.LoadNotesCallback> mLoadNotesCallbackArgumentCaptor;

    private NotesPresenter mNotesPresenter;

    @Before
    public void setUpNotesPresenterTest () {
        MockitoAnnotations.initMocks(this);

        mNotesPresenter = new NotesPresenter(mNotesRepository, mNotesView);

        NOTES = new ArrayList<>();

        NOTES.add(new Note("title", "description"));
        NOTES.add(new Note("title", "description"));
    }

    @Test
    public void loadNotes_successful () {
        boolean forceUpdate = true;

        mNotesPresenter.loadNotes(forceUpdate);

        verify(mNotesRepository).getNotes(mLoadNotesCallbackArgumentCaptor.capture());
        mLoadNotesCallbackArgumentCaptor.getValue().onNotesLoaded(NOTES);

        verify(mNotesView).showNotes(NOTES);
    }

    @Test
    public void addNewNoteTest() {

        mNotesPresenter.addNewNote();

        verify(mNotesView).showAddNote();
    }

    @Test
    public void openNoteDetails() {
        Note note = new Note("title", "description");

        mNotesPresenter.openNoteDetails(note);

        verify(mNotesView).showNoteDetailUi(note.getId());
    }
}
