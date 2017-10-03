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
 * Created by javierg on 03/10/2017.
 */

public class NoteDetailPresenterTest {

    private final static String EMPTY = "";
    private final static String ID = "123";
    private final static String TITLE = "Title";
    private final static String DESCRIPTION = "Description";
    private final static String URL_IMAGE = "Image";

    @Mock
    NotesRepository mNotesRepository;

    @Mock
    NoteDetailContract.View mNotesDetailView;

    @Captor
    private ArgumentCaptor<NotesRepository.GetNoteCallback> mGetNoteCallbackCaptor;

    NoteDetailPresenter mPresenter;

    @Before
    public void setUp() {

        MockitoAnnotations.initMocks(this);

        mPresenter = new NoteDetailPresenter(mNotesRepository, mNotesDetailView);
    }

    @Test
    public void openNote_EmptyNote() {

        mPresenter.openNote(EMPTY);

        verify(mNotesDetailView).showMissingNote();
    }

    @Test
    public void openNote_NullNote() {

        mPresenter.openNote(ID);

        verify(mNotesRepository).getNote(eq(ID), mGetNoteCallbackCaptor.capture());
        mGetNoteCallbackCaptor.getValue().onNoteLoaded(null);

        InOrder inOrder = Mockito.inOrder(mNotesDetailView);
        inOrder.verify(mNotesDetailView).setProgressIndicator(true);
        inOrder.verify(mNotesDetailView).setProgressIndicator(false);

        verify(mNotesDetailView).showMissingNote();

    }

    @Test
    public void openNote_Successful () {

        Note note = new Note(TITLE, DESCRIPTION);

        mPresenter.openNote(note.getId());

        verify(mNotesRepository).getNote(eq(note.getId()), mGetNoteCallbackCaptor.capture());
        mGetNoteCallbackCaptor.getValue().onNoteLoaded(note);

        InOrder inOrder = Mockito.inOrder(mNotesDetailView);
        inOrder.verify(mNotesDetailView).setProgressIndicator(true);
        inOrder.verify(mNotesDetailView).setProgressIndicator(false);

        verify(mNotesDetailView).showTitle(TITLE);
        verify(mNotesDetailView).showDescription(DESCRIPTION);
    }

    @Test
    public void showNote_Successful () {

        Note note = new Note(TITLE, DESCRIPTION);

        mPresenter.showNote(note);

        verify(mNotesDetailView).showTitle(TITLE);
        verify(mNotesDetailView).showDescription(DESCRIPTION);
    }

    @Test
    public void showNote_SuccessfulWithImage () {
        Note note = new Note(TITLE, DESCRIPTION, URL_IMAGE);

        mPresenter.showNote(note);

        verify(mNotesDetailView).showTitle(TITLE);
        verify(mNotesDetailView).showDescription(DESCRIPTION);
        verify(mNotesDetailView).showImage(URL_IMAGE);
    }

    @Test
    public void showNote_Hide () {

        Note note = new Note("", "");

        mPresenter.showNote(note);

        verify(mNotesDetailView).hideTitle();
        verify(mNotesDetailView).hideDescription();
        verify(mNotesDetailView).hideImage();
    }

}
