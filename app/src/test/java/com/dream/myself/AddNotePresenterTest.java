package com.dream.myself;

import android.support.annotation.NonNull;

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
 * Created by javierg on 04/10/2017.
 */

public class AddNotePresenterTest {

    public static final String TITLE = "title";
    private static final String DESCRIPTION = "description";

    @Mock
    NotesRepository mNotesRepository;

    @Mock
    AddNoteContract.View mAddNoteView;

    @Mock
    ImageFile mImageFile;

    AddNotePresenter mAddNotePresenter;

    @Before
    public void setUp () {
        MockitoAnnotations.initMocks(this);
        mAddNotePresenter = new AddNotePresenter(mNotesRepository, mAddNoteView, mImageFile);
    }

    @Test
    public void saveNote_successful () {

        mAddNotePresenter.saveNote(TITLE, DESCRIPTION);

        verify(mNotesRepository).saveNote(any(Note.class));
        verify(mAddNoteView).showNotesList();
    }

    @Test
    public void saveNote_error () {
        mAddNotePresenter.saveNote("", "");

        verify(mAddNoteView).showEmptyNoteError();
    }

    @Test
    public void takePicture_successful () throws IOException {
        mAddNotePresenter.takePicture();

        verify(mImageFile).create(any(String.class), any(String.class));
        verify(mImageFile).getPath();
        verify(mAddNoteView).openCamera(any(String.class));
    }

    @Test
    public void imageAvailable_successful () {
        String imageUrl = "path/to/file";

        when(mImageFile.exists()).thenReturn(true);
        when(mImageFile.getPath()).thenReturn(imageUrl);
        mAddNotePresenter.imageAvailable();

        verify(mAddNoteView).showImagePreview(anyString());
    }

    @Test
    public void imageAvailable_error () {
        String imageUrl = null;
        when(mImageFile.exists()).thenReturn(false);

        mAddNotePresenter.imageAvailable();

        verify(mImageFile).delete();
        verify(mAddNoteView).showImageError();

    }

    @Test
    public void imageCapture () {

        mAddNotePresenter.imageCaptureFailed();

        verify(mImageFile).delete();
        verify(mAddNoteView).showImageError();

    }
}
