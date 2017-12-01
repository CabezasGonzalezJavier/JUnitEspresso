package com.dream.myself;

import com.dream.myself.data.Note;
import com.dream.myself.data.NotesRepository;
import com.dream.myself.notedetail.NoteDetailContract;
import com.dream.myself.notedetail.NoteDetailPresenter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

/**
 * Created by javierg on 01/12/2017.
 */

public class NoteDetailPresenterTest {


    @Mock
    NotesRepository mNotesRepository;

    @Mock
    NoteDetailContract.View mNotesDetailView;

    @Captor
    ArgumentCaptor<NotesRepository.GetNoteCallback> mGetNoteCallbackArgumentCaptor;

    NoteDetailPresenter mPresenter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        mPresenter = new NoteDetailPresenter(mNotesRepository, mNotesDetailView);
    }

    @Test
    public void openNoteDifferentWay() {
        String title = "title";
        String description = "descripiton";

        Note note = new Note(title, description);

        mPresenter.openNote(note.getId());

        verify(mNotesRepository).getNote(eq(note.getId()), any(NotesRepository.GetNoteCallback.class));
    }

    @Test
    public void openNote() {
        String title = "title";
        String description = "descripiton";

        Note note = new Note(title, description);

        mPresenter.openNote(note.getId());

        verify(mNotesRepository).getNote(eq(note.getId()), mGetNoteCallbackArgumentCaptor.capture());
        mGetNoteCallbackArgumentCaptor.getValue().onNoteLoaded(note);

        verify(mNotesDetailView).showTitle(title);
        verify(mNotesDetailView).showDescription(description);
        verify(mNotesDetailView).hideImage();
    }

    @Test
    public void openNoteTitleDescriptionImage() {
        String title = "title";
        String description = "descripiton";
        String image = "image";

        Note note = new Note(title, description, image);

        mPresenter.openNote(note.getId());

        verify(mNotesRepository).getNote(eq(note.getId()), mGetNoteCallbackArgumentCaptor.capture());
        mGetNoteCallbackArgumentCaptor.getValue().onNoteLoaded(note);

        verify(mNotesDetailView).showTitle(title);
        verify(mNotesDetailView).showDescription(description);
        verify(mNotesDetailView).showImage(image);
    }

    @Test
    public void openNote_hideTitleDescriptionImage() {
        Note note = new Note("", "");

        mPresenter.openNote(note.getId());

        verify(mNotesRepository).getNote(eq(note.getId()), mGetNoteCallbackArgumentCaptor.capture());
        mGetNoteCallbackArgumentCaptor.getValue().onNoteLoaded(note);

        verify(mNotesDetailView).hideTitle();
        verify(mNotesDetailView).hideDescription();
        verify(mNotesDetailView).hideImage();
    }
}
