package com.dream.myself;

import com.dream.myself.data.NotesRepository;
import com.dream.myself.notes.NotesContract;
import com.dream.myself.notes.NotesPresenter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;

/**
 * Created by javiergonzalezcabezas on 3/12/17.
 */

public class NotesPresenterTest {

    @Mock
    NotesRepository mNotesRepository;

    @Mock
    NotesContract.View mNotesView;

    NotesPresenter mPresenter;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        mPresenter = new NotesPresenter(mNotesRepository, mNotesView);
    }

    @Test
    public void addNewNotes_() {

        mPresenter.addNewNote();

        verify(mNotesView).showAddNote();

    }

    @Test
    public void loadNotes_
}
