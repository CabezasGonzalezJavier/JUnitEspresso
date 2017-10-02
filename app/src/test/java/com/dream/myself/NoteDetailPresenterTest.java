package com.dream.myself;

import com.dream.myself.data.Note;
import com.dream.myself.data.NotesRepository;
import com.dream.myself.notedetail.NoteDetailContract;
import com.dream.myself.notedetail.NoteDetailPresenter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

/**
 * Created by javiergonzalezcabezas on 1/10/17.
 */

public class NoteDetailPresenterTest {

    public static final String INVALID_ID = "INVALID_ID";
    public static final String VALID_ID = "VALID_ID";
    public static final String TITLE_TEST = "title";

    public static final String DESCRIPTION_TEST = "description";

    @Mock
    NotesRepository mNotesRepository;

    @Mock
    NoteDetailContract.View mNotesDetailView;

    @Captor
    private ArgumentCaptor<NotesRepository.GetNoteCallback> mGetNoteCallbackCaptor;

    NoteDetailPresenter mNoteDetailPresenter;

    @Before
    public void setUpNoteDetailPresenter() {
        MockitoAnnotations.initMocks(this);

        mNoteDetailPresenter = new NoteDetailPresenter(mNotesRepository, mNotesDetailView);
    }

    @Test
    public void showNote_Successful() {

        Note note = new Note(VALID_ID, TITLE_TEST, DESCRIPTION_TEST);
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

    @Test
    public void openNote_Successful() {

        Note note = new Note(TITLE_TEST, DESCRIPTION_TEST);
        String noteID = note.getId();

        mNoteDetailPresenter.openNote(noteID);

        verify(mNotesRepository).getNote(eq(noteID), mGetNoteCallbackCaptor.capture());
        mGetNoteCallbackCaptor.getValue().onNoteLoaded(note);

        InOrder inOrder = Mockito.inOrder(mNotesDetailView);
        inOrder.verify(mNotesDetailView).setProgressIndicator(true);
        inOrder.verify(mNotesDetailView).setProgressIndicator(false);

        verify(mNotesDetailView).showTitle(TITLE_TEST);
        verify(mNotesDetailView).showDescription(DESCRIPTION_TEST);
    }

    @Test
    public void missingNote () {

        mNoteDetailPresenter.openNote("");

        verify(mNotesDetailView).showMissingNote();
    }

    @Test
    public void getUnKnowNote_FromRepositoryAndLoadIntoView () {
        mNoteDetailPresenter.openNote(INVALID_ID);

        verify(mNotesDetailView).setProgressIndicator(true);
        verify(mNotesRepository).getNote(eq(INVALID_ID), mGetNoteCallbackCaptor.capture());

        mGetNoteCallbackCaptor.getValue().onNoteLoaded(null);

        verify(mNotesDetailView).setProgressIndicator(false);
        verify(mNotesDetailView).showMissingNote();

    }

}
