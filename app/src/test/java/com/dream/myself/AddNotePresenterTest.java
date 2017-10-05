package com.dream.myself;

import android.support.annotation.NonNull;

import com.dream.myself.addnote.AddNoteContract;
import com.dream.myself.addnote.AddNotePresenter;
import com.dream.myself.data.Note;
import com.dream.myself.data.NotesRepository;
import com.dream.myself.util.ImageFile;

import org.bouncycastle.jcajce.provider.symmetric.DES;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;

import javax.annotation.meta.When;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by javierg on 05/10/2017.
 */

public class AddNotePresenterTest {

    private static final String ID = "12";
    private static final String TITLE = "title";
    private static final String DESCRIPTION = "description";

    @Mock
    NotesRepository mNotesRepository;

    @Mock
    AddNoteContract.View mAddNoteView;

    @Mock
    ImageFile mImageFile;

    AddNotePresenter mAddNotePresenter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        mAddNotePresenter = new AddNotePresenter(mNotesRepository, mAddNoteView, mImageFile);
    }

    @Test
    public void saveNote_Successful() {

        String url = "url";

        when(mImageFile.exists()).thenReturn(true);
        when(mImageFile.getPath()).thenReturn(url);


        mAddNotePresenter.saveNote(TITLE, DESCRIPTION);

        verify(mNotesRepository).saveNote(any(Note.class));
        verify(mAddNoteView).showNotesList();

    }

    @Test
    public void saveNote_EmptyNote() {

        String title = "";
        String description = "";
        String url = "url";

        when(mImageFile.exists()).thenReturn(true);
        when(mImageFile.getPath()).thenReturn(url);

        mAddNotePresenter.saveNote(title, description);

        verify(mAddNoteView).showEmptyNoteError();
    }

    @Test
    public void takePicture_successful () throws IOException {

        mAddNotePresenter.takePicture();

        verify(mImageFile).create(anyString(), anyString());
        verify(mAddNoteView).openCamera(anyString());

    }

    @Test
    public void imageAvailable_successful () {

        String url = "url";

        when(mImageFile.exists()).thenReturn(true);
        when(mImageFile.getPath()).thenReturn(url);

        mAddNotePresenter.imageAvailable();

        verify(mAddNoteView).showImagePreview(url);
    }


    @Test
    public void imageAvailable_error () {

        when(mImageFile.exists()).thenReturn(false);

        mAddNotePresenter.imageAvailable();

        verify(mImageFile).delete();
        verify(mAddNoteView).showImageError();
    }

    @Test
    public void imageCaptureFailed () {

        mAddNotePresenter.imageCaptureFailed();

        verify(mImageFile).delete();
        verify(mAddNoteView).showImageError();

    }

}
