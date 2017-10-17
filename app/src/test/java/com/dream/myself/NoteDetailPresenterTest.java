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
 * Created by javierg on 17/10/2017.
 */

public class NoteDetailPresenterTest {


    private final static String TITLE = "title";
    private final static String DESCRIPTION = "description";

    @Mock
    private NotesRepository mNotesRepository;
    @Mock
    private NoteDetailContract.View mNotesDetailView;
    @Mock
    private NoteDetailPresenter mNoteDetailPresenter;

    @Captor
    ArgumentCaptor<NotesRepository.GetNoteCallback> mGetNoteCallbackArgumentCaptor;

    @Before
    public void setUpNoteDetailPresenterTest () {
        MockitoAnnotations.initMocks(this);

        mNoteDetailPresenter = new NoteDetailPresenter(mNotesRepository, mNotesDetailView);
    }

    @Test
    public void openNote_showNote() {
        String noteId = "noteId";
        Note note = new Note(TITLE, DESCRIPTION);

        mNoteDetailPresenter.openNote(noteId);

        verify(mNotesRepository).getNote(eq(noteId), mGetNoteCallbackArgumentCaptor.capture());
        mGetNoteCallbackArgumentCaptor.getValue().onNoteLoaded(note);

        InOrder inOrder = Mockito.inOrder(mNotesDetailView);
        inOrder.verify(mNotesDetailView).setProgressIndicator(true);
        inOrder.verify(mNotesDetailView).setProgressIndicator(false);

        verify(mNotesDetailView).showTitle(TITLE);
        verify(mNotesDetailView).showDescription(DESCRIPTION);
        verify(mNotesDetailView).hideImage();
    }

    @Test
    public void openNote_showMissingNote() {
        String noteId = "noteId";

        mNoteDetailPresenter.openNote(noteId);

        verify(mNotesRepository).getNote(eq(noteId), mGetNoteCallbackArgumentCaptor.capture());
        mGetNoteCallbackArgumentCaptor.getValue().onNoteLoaded(null);

        InOrder inOrder = Mockito.inOrder(mNotesDetailView);
        inOrder.verify(mNotesDetailView).setProgressIndicator(true);
        inOrder.verify(mNotesDetailView).setProgressIndicator(false);

        verify(mNotesDetailView).showMissingNote();
    }
}
