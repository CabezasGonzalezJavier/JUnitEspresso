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

import java.io.IOException;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by javierg on 01/12/2017.
 */

public class AddNotePresenterTest {

    @Mock
    NotesRepository mNotesRepository;
    @Mock
    AddNoteContract.View mAddNoteView;
    @Mock
    ImageFile mImageFile;

    AddNotePresenter mPresenter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        mPresenter = new AddNotePresenter(mNotesRepository, mAddNoteView, mImageFile);
    }

    @Test
    public void saveNote_saveNote() {
        String title = "title";
        String description = "description";

        mPresenter.saveNote(title, description);

        verify(mNotesRepository).saveNote(any(Note.class));
        verify(mAddNoteView).showNotesList();
    }

    @Test
    public void saveNote_showEmptyNoteError() {
        String title = "";
        String description = "";

        mPresenter.saveNote(title, description);

        verify(mAddNoteView).showEmptyNoteError();
    }

    @Test
    public void takePicture() throws IOException {

        mPresenter.takePicture();

        verify(mImageFile).create(anyString(), anyString());
        verify(mAddNoteView).openCamera(anyString());
    }

    @Test
    public void imageAvailable_showImagePreview(){

        when(mImageFile.exists()).thenReturn(true);

        mPresenter.imageAvailable();

        verify(mAddNoteView).showImagePreview(mImageFile.getPath());
    }

    @Test
    public void imageAvailable() {

        when(mImageFile.exists()).thenReturn(false);

        mPresenter.imageAvailable();

        verify(mAddNoteView).showImageError();
        verify(mImageFile).delete();
    }

    @Test
    public void imageCaptureFailed() {
        mPresenter.imageCaptureFailed();

        verify(mAddNoteView).showImageError();
        verify(mImageFile).delete();
    }

}
