package com.dream.myself;

import com.dream.myself.data.Note;
import com.dream.myself.data.NotesRepository;
import com.dream.myself.notes.NotesContract;
import com.dream.myself.notes.NotesPresenter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

/**
 * Created by javierg on 24/10/2017.
 */

public class NotesPresenterTest {


    private static final String TITLE = "title";
    private static final String DESCRIPTION = "description";

    @Mock
    private NotesRepository mNotesRepository;
    @Mock
    private NotesContract.View mNotesView;

    @Captor
    private ArgumentCaptor<NotesRepository.LoadNotesCallback> mLoadNotesCallbackArgumentCaptor;

    NotesPresenter mNotesPresenter;

    List<Note> mList;

    @Before
    public void setUpNotesPresenterTest() {
        MockitoAnnotations.initMocks(this);

        mNotesPresenter = new NotesPresenter(mNotesRepository, mNotesView);
        mList = new ArrayList<>();
        mList.add(new Note(TITLE, DESCRIPTION));
        mList.add(new Note(TITLE, DESCRIPTION));
    }

    @Test
    public void loadNotes_showNotes() {

        boolean forceUpdate = true;

        mNotesPresenter.loadNotes(forceUpdate);
        verify(mNotesRepository).getNotes(mLoadNotesCallbackArgumentCaptor.capture());
        mLoadNotesCallbackArgumentCaptor.getValue().onNotesLoaded(mList);

        InOrder inOrder = Mockito.inOrder(mNotesView);
        inOrder.verify(mNotesView).setProgressIndicator(true);
        inOrder.verify(mNotesView).setProgressIndicator(false);

        verify(mNotesView).showNotes(mList);
    }

    @Test
    public void addNewNote_showAddNote() {

        mNotesPresenter.addNewNote();

        mNotesView.showAddNote();
    }

    @Test
    public void openNoteDetails_showNoteDetailUi() {

        Note note = new Note(TITLE, DESCRIPTION);

        mNotesPresenter.openNoteDetails(note);

        mNotesView.showNoteDetailUi(note.getId());
    }

}
