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
 * Created by javierg on 09/10/2017.
 */

public class AddNotePresenterTest {

    private final static String TITLE = "title";
    private final static String DESCRIPTION = "Description";

    @Mock
    NotesRepository mNotesRepository;
    @Mock
    AddNoteContract.View mAddNoteView;
    @Mock
    ImageFile mImageFile;

    AddNotePresenter mAddNotePresenter;

    @Before
    public void setUpAddNotePresenterTest () {
        MockitoAnnotations.initMocks(this);

        mAddNotePresenter = new AddNotePresenter(mNotesRepository, mAddNoteView, mImageFile);
    }

    @Test
    public void saveNote_showNotesList () {

        String url = "url";

        when(mImageFile.exists()).thenReturn(true);
        when(mImageFile.getPath()).thenReturn(url);
        mAddNotePresenter.saveNote(TITLE, DESCRIPTION);

        verify(mNotesRepository).saveNote(any(Note.class));
        verify(mAddNoteView).showNotesList();

    }

    @Test
    public void saveNote_EmptyNoteError () {

        String url = "url";

        when(mImageFile.exists()).thenReturn(true);
        when(mImageFile.getPath()).thenReturn(url);
        mAddNotePresenter.saveNote("", "");

        verify(mAddNoteView).showEmptyNoteError();
    }

    @Test
    public void takePicture_openCamera ()  throws IOException {

        mAddNotePresenter.takePicture();

        verify(mImageFile).create(anyString(), anyString());
        verify(mAddNoteView).openCamera(anyString());

    }

    @Test
    public void imageAvailable_showImagePreview () {

        when(mImageFile.exists()).thenReturn(true);

        mAddNotePresenter.imageAvailable();

        verify(mAddNoteView).showImagePreview(anyString());
    }

    @Test
    public void imageAvailable_imageCaptureFailed () {

        when(mImageFile.exists()).thenReturn(false);

        mAddNotePresenter.imageAvailable();

        verify(mImageFile).delete();
        verify(mAddNoteView).showImageError();
    }

    @Test
    public void imageCaptureFailedTest () {

        mAddNotePresenter.imageCaptureFailed();

        verify(mImageFile).delete();
        verify(mAddNoteView).showImageError();
    }

}
