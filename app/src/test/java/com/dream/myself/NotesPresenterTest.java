package com.dream.myself;

import com.dream.myself.data.Note;
import com.dream.myself.data.NotesRepository;
import com.dream.myself.notes.NotesContract;
import com.dream.myself.notes.NotesPresenter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;

/**
 * Created by javierg on 13/10/2017.
 */

public class NotesPresenterTest {

    @Mock
    private NotesRepository mNotesRepository;

    @Mock
    private NotesContract.View mNotesView;

    @Captor
    private ArgumentCaptor<NotesRepository.LoadNotesCallback> mLoadNotesCallbackArgumentCaptor;

    NotesPresenter mNotesPresenter;

    private static List<Note> NOTES;

    @Before
    public void setUpNotesPresenterTest() {
        MockitoAnnotations.initMocks(this);

        mNotesPresenter = new NotesPresenter(mNotesRepository, mNotesView);

        NOTES = new ArrayList<>();
        NOTES.add(new Note("title", "description"));
        NOTES.add(new Note("title1", "description1"));
    }

    @Test
    public void loadNotes_showNotes() {

        boolean forceUpdate = true;

        mNotesPresenter.loadNotes(forceUpdate);

        verify(mNotesRepository).getNotes(mLoadNotesCallbackArgumentCaptor.capture());
        mLoadNotesCallbackArgumentCaptor.getValue().onNotesLoaded(NOTES);

        InOrder inOrder = Mockito.inOrder(mNotesView);
        inOrder.verify(mNotesView).setProgressIndicator(true);
        inOrder.verify(mNotesView).setProgressIndicator(false);
        verify(mNotesView).showNotes(NOTES);
    }

    @Test
    public void addNewNoteTest() {

        mNotesPresenter.addNewNote();

        verify(mNotesView).showAddNote();
    }

    @Test
    public void openNoteDetailsTest() {
        Note note = new Note("title", "description");

        mNotesPresenter.openNoteDetails(note);

        verify(mNotesView).showNoteDetailUi(note.getId());
    }
}
