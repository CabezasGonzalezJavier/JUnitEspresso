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

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

/**
 * Created by javiergonzalezcabezas on 24/9/17.
 */

public class NotesPresenterTest {

    private static List<Note> NOTES;

    private static List<Note> EMPTY_NOTES;

    @Mock
    NotesRepository mNotesRepository;

    @Mock
    NotesContract.View mNotesView;

    @Captor
    private ArgumentCaptor<NotesRepository.LoadNotesCallback> mLoadNotesCallbackCaptor;

    NotesPresenter mPresenter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        NOTES = Lists.newArrayList(new Note("Title1", "Description1"),
                new Note("Title2", "Description2"));
        EMPTY_NOTES = new ArrayList<>(0);

        mPresenter = new NotesPresenter(mNotesRepository, mNotesView);
    }

    @Test
    public void addNewNoteTest() {

        mPresenter.addNewNote();

        verify(mNotesView).showAddNote();
    }

    @Test
    public void openNoteDetailsTest() {

        String title = "title";
        String description = "description";
        Note note = new Note(title, description);

        mPresenter.openNoteDetails(note);

        verify(mNotesView).showNoteDetailUi(any(String.class));
    }

    @Test
    public void loadNotesTest() {

        boolean forceUpdate = true;

        mPresenter.loadNotes(forceUpdate);

        verify(mNotesRepository).getNotes(mLoadNotesCallbackCaptor.capture());
        mLoadNotesCallbackCaptor.getValue().onNotesLoaded(NOTES);

        InOrder inOrder = Mockito.inOrder(mNotesView);
        inOrder.verify(mNotesView).setProgressIndicator(true);
        inOrder.verify(mNotesView).setProgressIndicator(false);
        verify(mNotesView).showNotes(NOTES);

    }
}
