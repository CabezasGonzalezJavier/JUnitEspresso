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
 * Created by javierg on 09/10/2017.
 */

public class NotesPresenterTest {

    @Mock
    NotesRepository mNotesRepository;

    @Mock
    NotesContract.View mNotesView;

    @Captor
    ArgumentCaptor<NotesRepository.LoadNotesCallback> mLoadNotesCallbackArgumentCaptor;

    NotesPresenter mNotesPresenter;

    List<Note> mNoteList;

    @Before
    public void setUpNotesPresenterTest () {
        MockitoAnnotations.initMocks(this);

        mNotesPresenter = new NotesPresenter(mNotesRepository, mNotesView);

        mNoteList = new ArrayList<>();
        mNoteList.add(new Note("title", "description"));
        mNoteList.add(new Note("title", "description"));
    }

    @Test
    public void loadNotes_successful () {
        boolean forceUpdate = true;

        mNotesPresenter.loadNotes(forceUpdate);

        verify(mNotesRepository).getNotes(mLoadNotesCallbackArgumentCaptor.capture());
        mLoadNotesCallbackArgumentCaptor.getValue().onNotesLoaded(mNoteList);

        InOrder inOrder = Mockito.inOrder(mNotesView);
        inOrder.verify(mNotesView).setProgressIndicator(true);
        inOrder.verify(mNotesView).setProgressIndicator(false);
        verify(mNotesView).showNotes(mNoteList);
    }

    @Test
    public void addNewNote () {

        mNotesPresenter.addNewNote();

        verify(mNotesView).showAddNote();
    }

    @Test
    public void openNoteDetails () {

        Note note = new Note("title","description");

        mNotesPresenter.openNoteDetails(note);

        verify(mNotesView).showNoteDetailUi(note.getId());
    }
}
