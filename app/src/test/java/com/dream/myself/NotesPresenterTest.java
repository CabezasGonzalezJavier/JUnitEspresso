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
import static org.mockito.Mockito.when;

/**
 * Created by javierg on 06/10/2017.
 */

public class NotesPresenterTest {

    @Mock
    NotesRepository mNotesRepository;

    @Mock
    NotesContract.View mNotesView;

    NotesPresenter mNotesPresenter;

    List<Note> mList;

    @Captor
    ArgumentCaptor<NotesRepository.LoadNotesCallback> mLoadNotesCallbackArgumentCaptor;

    @Before
    public void setUp () {
        MockitoAnnotations.initMocks(this);

        mNotesPresenter = new NotesPresenter(mNotesRepository, mNotesView);

        mList = new ArrayList<>();
        mList.add(new Note("title", "description"));
        mList.add(new Note("title2", "description2"));
    }

    @Test
    public void loadNotes_successful () {
        boolean forceUpdate = true;

        mNotesPresenter.loadNotes(forceUpdate);

        verify(mNotesRepository).getNotes(mLoadNotesCallbackArgumentCaptor.capture());
        mLoadNotesCallbackArgumentCaptor.getValue().onNotesLoaded(mList);

        InOrder inOrder = Mockito.inOrder(mNotesView);
        inOrder.verify(mNotesView).setProgressIndicator(true);
        inOrder.verify(mNotesView).setProgressIndicator(false);

        verify(mNotesRepository).refreshData();
        verify(mNotesView).showNotes(mList);
    }

    @Test
    public void addNewNoteTest () {

        mNotesPresenter.addNewNote();

        verify(mNotesView).showAddNote();
    }

    @Test
    public void openNoteDetails () {
        Note note = new Note("title", "description");

        mNotesPresenter.openNoteDetails(note);

        verify(mNotesView).showNoteDetailUi(note.getId());

    }
}
