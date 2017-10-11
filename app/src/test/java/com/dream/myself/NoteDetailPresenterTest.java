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

import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

/**
 * Created by javierg on 11/10/2017.
 */

public class NoteDetailPresenterTest {

    private static final String TITLE = "title";
    private static final String DESCRIPTION = "description";

    @Mock
    NotesRepository mNotesRepository;

    @Mock
    NoteDetailContract.View mNotesDetailView;

    NoteDetailPresenter mNoteDetailPresenter;

    @Captor
    ArgumentCaptor<NotesRepository.GetNoteCallback> mGetNoteCallbackArgumentCaptor;

    @Before
    public void setUpNoteDetailPresenterTest () {
        MockitoAnnotations.initMocks(this);

        mNoteDetailPresenter = new NoteDetailPresenter(mNotesRepository, mNotesDetailView);
    }

    @Test
    public void openNote_successful() {

        Note note = new Note(TITLE, DESCRIPTION);

        mNoteDetailPresenter.openNote(note.getId());

        verify(mNotesRepository).getNote(eq(note.getId()), mGetNoteCallbackArgumentCaptor.capture());
        mGetNoteCallbackArgumentCaptor.getValue().onNoteLoaded(note);

        InOrder inOrder = Mockito.inOrder(mNotesDetailView);
        inOrder.verify(mNotesDetailView).setProgressIndicator(true);
        inOrder.verify(mNotesDetailView).setProgressIndicator(false);
        verify(mNotesDetailView).showTitle(TITLE);
        verify(mNotesDetailView).showDescription(DESCRIPTION);
        verify(mNotesDetailView).hideImage();
    }

    @Test
    public void openNote_successfulWithImageUrl () {
        Note note = new Note(TITLE, DESCRIPTION, "imageUrl");

        mNoteDetailPresenter.openNote(note.getId());

        verify(mNotesRepository).getNote(eq(note.getId()), mGetNoteCallbackArgumentCaptor.capture());
        mGetNoteCallbackArgumentCaptor.getValue().onNoteLoaded(note);


        InOrder inOrder = Mockito.inOrder(mNotesDetailView);
        inOrder.verify(mNotesDetailView).setProgressIndicator(true);
        inOrder.verify(mNotesDetailView).setProgressIndicator(false);
        verify(mNotesDetailView).showTitle(TITLE);
        verify(mNotesDetailView).showDescription(DESCRIPTION);
        verify(mNotesDetailView).showImage("imageUrl");
    }

    @Test
    public void openNote_showMissingNote() {
        Note note = new Note(TITLE, DESCRIPTION, "imageUrl");

        mNoteDetailPresenter.openNote(note.getId());

        verify(mNotesRepository).getNote(eq(note.getId()), mGetNoteCallbackArgumentCaptor.capture());
        mGetNoteCallbackArgumentCaptor.getValue().onNoteLoaded(null);

        InOrder inOrder = Mockito.inOrder(mNotesDetailView);
        inOrder.verify(mNotesDetailView).setProgressIndicator(true);
        inOrder.verify(mNotesDetailView).setProgressIndicator(false);
        verify(mNotesDetailView).showMissingNote();
    }

    @Test
    public void openNote_hideTitleDescriptionImageURL() {
        Note note = new Note("", "");

        mNoteDetailPresenter.openNote(note.getId());

        verify(mNotesRepository).getNote(eq(note.getId()), mGetNoteCallbackArgumentCaptor.capture());
        mGetNoteCallbackArgumentCaptor.getValue().onNoteLoaded(note);

        InOrder inOrder = Mockito.inOrder(mNotesDetailView);
        inOrder.verify(mNotesDetailView).setProgressIndicator(true);
        inOrder.verify(mNotesDetailView).setProgressIndicator(false);
        verify(mNotesDetailView).hideTitle();
        verify(mNotesDetailView).hideDescription();
        verify(mNotesDetailView).hideImage();
    }

    @Test
    public void showNote_showTitleDescription() {

        Note note = new Note(TITLE, DESCRIPTION);

        mNoteDetailPresenter.showNote(note);

        verify(mNotesDetailView).showTitle(anyString());
        verify(mNotesDetailView).showDescription(anyString());
        verify(mNotesDetailView).hideImage();

    }

    @Test
    public void showNote_showTitleDescriptionImageURL() {

        Note note = new Note(TITLE, DESCRIPTION, "imageURL");

        mNoteDetailPresenter.showNote(note);

        verify(mNotesDetailView).showTitle(anyString());
        verify(mNotesDetailView).showDescription(anyString());
        verify(mNotesDetailView).showImage("imageURL");

    }

    @Test
    public void showNote_hideTitleDescriptionImageURL() {

        Note note = new Note("", "");

        mNoteDetailPresenter.showNote(note);

        verify(mNotesDetailView).hideTitle();
        verify(mNotesDetailView).hideDescription();
        verify(mNotesDetailView).hideImage();

    }
}
