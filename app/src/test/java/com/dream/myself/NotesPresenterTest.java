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
 * Created by javierg on 05/10/2017.
 */

public class NotesPresenterTest {

    List<Note> mList;

    @Mock
    NotesRepository mNotesRepository;

    @Mock
    NotesContract.View mNotesView;

    @Captor
    ArgumentCaptor<NotesRepository.LoadNotesCallback> mLoadNotesCallbackArgumentCaptor;

    NotesPresenter mNotePresenter;

    @Before
    public void setUpNotesPresenterTest() {
        MockitoAnnotations.initMocks(this);

        mNotePresenter = new NotesPresenter(mNotesRepository, mNotesView);

        mList = new ArrayList<>();

        mList.add(new Note("title", "description"));
        mList.add(new Note("title", "description"));
        mList.add(new Note("title", "description"));
    }

    @Test
    public void loadNotes_successful() {
        boolean forceUpdate = true;

        mNotePresenter.loadNotes(forceUpdate);
        verify(mNotesRepository).refreshData();

        verify(mNotesRepository).getNotes(mLoadNotesCallbackArgumentCaptor.capture());
        mLoadNotesCallbackArgumentCaptor.getValue().onNotesLoaded(mList);

        InOrder inOrder = Mockito.inOrder(mNotesView);
        inOrder.verify(mNotesView).setProgressIndicator(true);
        inOrder.verify(mNotesView).setProgressIndicator(false);

        verify(mNotesView).showNotes(mList);
    }

    @Test
    public void addNewNote () {

        mNotePresenter.addNewNote();

        verify(mNotesView).showAddNote();
    }

    @Test
    public void openNoteDetails() {
        Note note = new Note("title", "description");

        mNotePresenter.openNoteDetails(note);

        verify(mNotesView).showNoteDetailUi(note.getId());
    }

}
