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
 * Created by javierg on 13/10/2017.
 */

public class NoteDetailPresenterTest {

    private final static String TITLE = "title";
    private final static String DESCRIPTION = "description";

    @Mock
    private NotesRepository mNotesRepository;

    @Mock
    private NoteDetailContract.View mNotesDetailView;

    @Captor
    private ArgumentCaptor<NotesRepository.GetNoteCallback> mGetNoteCallbackArgumentCaptor;

    NoteDetailPresenter mNoteDetailPresenter;

    @Before
    public void setUpNoteDetailPresenter() {
        MockitoAnnotations.initMocks(this);

        mNoteDetailPresenter = new NoteDetailPresenter(mNotesRepository, mNotesDetailView);
    }

    @Test
    public void openNote_showNote() {

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
    public void openNote_showNoteWithImageURL() {

        Note note = new Note(TITLE, DESCRIPTION, "imageURL");

        mNoteDetailPresenter.openNote(note.getId());
        verify(mNotesRepository).getNote(eq(note.getId()), mGetNoteCallbackArgumentCaptor.capture());
        mGetNoteCallbackArgumentCaptor.getValue().onNoteLoaded(note);

        InOrder inOrder = Mockito.inOrder(mNotesDetailView);
        inOrder.verify(mNotesDetailView).setProgressIndicator(true);
        inOrder.verify(mNotesDetailView).setProgressIndicator(false);
        verify(mNotesDetailView).showTitle(TITLE);
        verify(mNotesDetailView).showDescription(DESCRIPTION);
        verify(mNotesDetailView).showImage("imageURL");

    }

    @Test
    public void openNote_showMissingNote() {

        Note note = new Note(TITLE, DESCRIPTION);

        mNoteDetailPresenter.openNote(note.getId());
        verify(mNotesRepository).getNote(eq(note.getId()), mGetNoteCallbackArgumentCaptor.capture());
        mGetNoteCallbackArgumentCaptor.getValue().onNoteLoaded(null);

        InOrder inOrder = Mockito.inOrder(mNotesDetailView);
        inOrder.verify(mNotesDetailView).setProgressIndicator(true);
        inOrder.verify(mNotesDetailView).setProgressIndicator(false);
        verify(mNotesDetailView).showMissingNote();
    }

    @Test
    public void showNote_showTitleDescriptionImageURL() {
        Note note = new Note(TITLE, DESCRIPTION, "imageURL");

        mNoteDetailPresenter.showNote(note);

        verify(mNotesDetailView).showTitle(TITLE);
        verify(mNotesDetailView).showDescription(DESCRIPTION);
        verify(mNotesDetailView).showImage("imageURL");
    }

    @Test
    public void showNote_showTitleDescription() {
        Note note = new Note(TITLE, DESCRIPTION);

        mNoteDetailPresenter.showNote(note);

        verify(mNotesDetailView).showTitle(TITLE);
        verify(mNotesDetailView).showDescription(DESCRIPTION);
        verify(mNotesDetailView).hideImage();
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
