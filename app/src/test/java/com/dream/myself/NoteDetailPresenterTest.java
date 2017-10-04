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
 * Created by javierg on 04/10/2017.
 */

public class NoteDetailPresenterTest {

    private final static String TITLE = "title";
    private final static String DESCRIPTION = "description";
    private final static String IMAGE = "Image";

    @Mock
    NotesRepository mNotesRepository;

    @Mock
    NoteDetailContract.View mNotesDetailView;

    @Captor
    private ArgumentCaptor<NotesRepository.GetNoteCallback> mGetNoteCallbackCaptor;

    NoteDetailPresenter mNoteDetailPresenter;

    @Before
    public void setUp () {
        MockitoAnnotations.initMocks(this);

        mNoteDetailPresenter = new NoteDetailPresenter(mNotesRepository, mNotesDetailView);
    }

    @Test
    public void openNote_successful () {

        Note note = new Note(TITLE, DESCRIPTION);

        mNoteDetailPresenter.openNote(note.getId());
        verify(mNotesRepository).getNote(eq(note.getId()), mGetNoteCallbackCaptor.capture());
        mGetNoteCallbackCaptor.getValue().onNoteLoaded(note);

        verify(mNotesDetailView).showTitle(TITLE);
        verify(mNotesDetailView).showDescription(DESCRIPTION);

    }

    @Test
    public void openNote_MissingNote () {

        mNoteDetailPresenter.openNote(null);

        verify(mNotesDetailView).showMissingNote();
    }

    @Test
    public void openNote_HideNote () {

        Note note = new Note(TITLE, DESCRIPTION);

        mNoteDetailPresenter.openNote(note.getId());
        verify(mNotesRepository).getNote(eq(note.getId()), mGetNoteCallbackCaptor.capture());
        mGetNoteCallbackCaptor.getValue().onNoteLoaded(null);

        InOrder inOrder = Mockito.inOrder(mNotesDetailView);
        inOrder.verify(mNotesDetailView).setProgressIndicator(true);
        inOrder.verify(mNotesDetailView).setProgressIndicator(false);

        verify(mNotesDetailView).showMissingNote();
    }

    @Test
    public void showNote_Hide () {

        Note note = new Note("", "");

        mNoteDetailPresenter.showNote(note);

        verify(mNotesDetailView).hideTitle();
        verify(mNotesDetailView).hideDescription();
        verify(mNotesDetailView).hideImage();
    }

    @Test
    public void showNote_successful () {

        Note note = new Note(TITLE, DESCRIPTION);

        mNoteDetailPresenter.showNote(note);

        verify(mNotesDetailView).showTitle(TITLE);
        verify(mNotesDetailView).showDescription(DESCRIPTION);
        verify(mNotesDetailView).hideImage();

    }

    @Test
    public void showNote_successfulWithImage () {

        Note note = new Note(TITLE, DESCRIPTION, IMAGE);

        mNoteDetailPresenter.showNote(note);

        verify(mNotesDetailView).showTitle(TITLE);
        verify(mNotesDetailView).showDescription(DESCRIPTION);
        verify(mNotesDetailView).showImage(IMAGE);
    }

}
