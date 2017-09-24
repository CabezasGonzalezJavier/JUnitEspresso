package com.dream.myself;

import com.dream.myself.addnote.AddNoteContract;
import com.dream.myself.addnote.AddNotePresenter;
import com.dream.myself.data.Note;
import com.dream.myself.data.NotesRepository;
import com.dream.myself.util.ImageFile;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

/**
 * Created by javiergonzalezcabezas on 24/9/17.
 */

public class AddNotePresenterTest {

    @Mock
    NotesRepository mNotesRepository;

    @Mock
    AddNoteContract.View mAddNoteView;

    @Mock
    ImageFile mImageFile;

    AddNotePresenter mAddNotesPresenter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);


        mAddNotesPresenter = new AddNotePresenter(mNotesRepository, mAddNoteView, mImageFile);
    }

    @Test
    public void saveNote() {
        String title = "title";
        String description = "description";

        mAddNotesPresenter.saveNote(title, description);

        verify(mNotesRepository).saveNote(any(Note.class));
        verify(mAddNoteView).showNotesList();
    }
}
