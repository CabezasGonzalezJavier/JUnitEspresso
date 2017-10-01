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
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.contains;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by javiergonzalezcabezas on 24/9/17.
 */

public class AddNotePresenterTest {

    @Mock
    NotesRepository mNotesRepository;

    @Mock
    AddNoteContract.View mAddNoteView;

    @Mock
    ImageFile mImageFile;

    AddNotePresenter mAddNotesPresenter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);


        mAddNotesPresenter = new AddNotePresenter(mNotesRepository, mAddNoteView, mImageFile);
    }

    @Test
    public void saveNote_showsSuccessMessageUi() {
        String title = "title";
        String description = "description";

        mAddNotesPresenter.saveNote(title, description);

        verify(mNotesRepository).saveNote(any(Note.class));
        verify(mAddNoteView).showNotesList();
    }

    @Test
    public void saveNote_emptyNoteShowsErrorUi() {
        mAddNotesPresenter.saveNote("", "");

        verify(mAddNoteView).showEmptyNoteError();
    }

    @Test
    public void imageAvailable_SavesImageAndUpdatesUiWithThumbnail() {
        // Given an a stubbed image file
        String imageUrl = "path/to/file";
        when(mImageFile.exists()).thenReturn(true);
        when(mImageFile.getPath()).thenReturn(imageUrl);

        mAddNotesPresenter.imageAvailable();

        verify(mAddNoteView).showImagePreview(contains(imageUrl));

    }

    @Test
    public void imageAvailable_FileDoesNotExistShowsErrorUi() {
        when(mImageFile.exists()).thenReturn(false);

        mAddNotesPresenter.imageAvailable();


        verify(mImageFile).delete();
        verify(mAddNoteView).showImageError();

    }

    @Test
    public void noImageAvailable_ShowsErrorUi() {

        mAddNotesPresenter.imageCaptureFailed();

        verify(mImageFile).delete();
        verify(mAddNoteView).showImageError();
    }

    @Test
    public void takePicture_CreatesFileAndOpensCamera() throws IOException {

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";

        mAddNotesPresenter.takePicture();


        verify(mImageFile).create(contains(imageFileName),anyString());
        verify(mAddNoteView).openCamera(anyString());

    }
}
