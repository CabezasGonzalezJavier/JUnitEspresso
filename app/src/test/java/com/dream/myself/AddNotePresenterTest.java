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
 * Created by javierg on 17/10/2017.
 */

public class AddNotePresenterTest {

    private final static String TITLE = "title";
    private final static String DESCRIPTION = "description";

    @Mock
    private NotesRepository mNotesRepository;
    @Mock
    private AddNoteContract.View mAddNoteView;
    @Mock
    private ImageFile mImageFile;

    AddNotePresenter mAddNotePresenter;

    @Before
    public void setUpAddNotePresenterTest () {
        MockitoAnnotations.initMocks(this);

        mAddNotePresenter = new AddNotePresenter(mNotesRepository, mAddNoteView, mImageFile);
    }

    @Test
    public void saveNote_showNotesList() {

        String imageUrl = "imageUrl";
        Note newNote = new Note(TITLE, DESCRIPTION, imageUrl);

        when(mImageFile.exists()).thenReturn(true);
        when(mImageFile.getPath()).thenReturn(imageUrl);

        mAddNotePresenter.saveNote(TITLE, DESCRIPTION);

        verify(mNotesRepository).saveNote(any(Note.class));
        verify(mAddNoteView).showNotesList();
    }

    @Test
    public void saveNote_showEmptyNoteError() {
        String imageUrl = "imageUrl";

        when(mImageFile.exists()).thenReturn(true);
        when(mImageFile.getPath()).thenReturn(imageUrl);

        mAddNotePresenter.saveNote("", "");

        verify(mAddNoteView).showEmptyNoteError();
    }

    @Test
    public void takePicture() throws IOException {

        mAddNotePresenter.takePicture();

        verify(mImageFile).create(anyString(), anyString());
        verify(mAddNoteView).openCamera(anyString());
    }

    @Test
    public void imageAvailable_showImagePreview() {

        when(mImageFile.exists()).thenReturn(true);

        mAddNotePresenter.imageAvailable();

        verify(mAddNoteView).showImagePreview(anyString());
    }

    @Test
    public void imageAvailable_failed() {

        when(mImageFile.exists()).thenReturn(false);

        mAddNotePresenter.imageAvailable();

        verify(mImageFile).delete();
        verify(mAddNoteView).showImageError();
    }

    @Test
    public void imageCaptureFailed() {

        mAddNotePresenter.imageCaptureFailed();

        verify(mImageFile).delete();
        verify(mAddNoteView).showImageError();
    }

}
