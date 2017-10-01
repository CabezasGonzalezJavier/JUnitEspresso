package com.dream.myself;

import com.dream.myself.data.Note;
import com.dream.myself.data.NotesRepository;
import com.dream.myself.notedetail.NoteDetailContract;
import com.dream.myself.notedetail.NoteDetailPresenter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;

/**
 * Created by javiergonzalezcabezas on 1/10/17.
 */

public class NoteDetailPresenterTest {

    public static final String INVALID_ID = "INVALID_ID";

    public static final String TITLE_TEST = "title";

    public static final String DESCRIPTION_TEST = "description";

    @Mock
    NotesRepository mNotesRepository;

    @Mock
    NoteDetailContract.View mNotesDetailView;

    NoteDetailPresenter mNoteDetailPresenter;

    @Before
    public void setUpNoteDetailPresenter() {
        MockitoAnnotations.initMocks(this);

        mNoteDetailPresenter = new NoteDetailPresenter(mNotesRepository, mNotesDetailView);
    }

    @Test
    public void showNoteTest() {

        Note note = new Note(INVALID_ID, TITLE_TEST, DESCRIPTION_TEST);
        String title = note.getTitle();
        String description = note.getDescription();
        String imageUrl = note.getImageUrl();

        mNoteDetailPresenter.showNote(note);

        verify(mNotesDetailView).showTitle(title);
        verify(mNotesDetailView).showDescription(description);
        verify(mNotesDetailView).showImage(imageUrl);
    }

    @Test
    public void show_EmptyNote() {

        String titleNote = new String();
        String descriptionNote = new String();
        Note note = new Note(titleNote, descriptionNote);

        mNoteDetailPresenter.showNote(note);

        verify(mNotesDetailView).hideTitle();
        verify(mNotesDetailView).hideDescription();
        verify(mNotesDetailView).hideImage();
    }

}
