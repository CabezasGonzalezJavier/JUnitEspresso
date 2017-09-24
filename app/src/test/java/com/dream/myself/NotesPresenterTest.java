package com.dream.myself;

import com.dream.myself.data.Note;
import com.dream.myself.data.NotesRepository;
import com.dream.myself.notes.NotesContract;
import com.dream.myself.notes.NotesPresenter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

/**
 * Created by javiergonzalezcabezas on 24/9/17.
 */

public class NotesPresenterTest {

    @Mock
    NotesRepository mNotesRepository;

    @Mock
    NotesContract.View mNotesView;

    NotesPresenter mPresenter;


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

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
        Note note = new Note(title, description;

        mPresenter.openNoteDetails(note);


        verify(mNotesView).showNoteDetailUi(any(String.class));
    }


}
