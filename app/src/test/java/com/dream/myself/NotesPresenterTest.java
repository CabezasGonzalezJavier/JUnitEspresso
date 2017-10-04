package com.dream.myself;

import com.dream.myself.data.Note;
import com.dream.myself.data.NotesRepository;
import com.dream.myself.notes.NotesContract;
import com.dream.myself.notes.NotesPresenter;
import com.google.common.collect.Lists;

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

import static org.mockito.Matchers.booleanThat;
import static org.mockito.Mockito.verify;

/**
 * Created by javierg on 04/10/2017.
 */

public class NotesPresenterTest {

    private static List<Note> NOTES;

    private static List<Note> EMPTY_NOTES;

    @Mock
    NotesRepository mNotesRepository;

    @Mock
    NotesContract.View mNotesView;

    NotesPresenter mNotesPresenter;

    @Captor
    ArgumentCaptor<NotesRepository.LoadNotesCallback> mLoadNotesCallbackArgumentCaptor;

    @Before
    public void setUp () {

        MockitoAnnotations.initMocks(this);

        mNotesPresenter = new NotesPresenter(mNotesRepository, mNotesView);

        NOTES = Lists.newArrayList(new Note("Title1", "Description1"), new Note("Title2", "Description2"));
    }

    @Test
    public void loadNotes_successful () {

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
    public void addNewNote_successful () {

        mNotesPresenter.addNewNote();

        verify(mNotesView).showAddNote();

    }

    @Test
    public void openNoteDetails () {

        Note note = new Note("Title1", "Description1");

        mNotesPresenter.openNoteDetails(note);

        verify(mNotesView).showNoteDetailUi(note.getId());
    }

}
