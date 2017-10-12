package com.dream.myself;

import com.dream.myself.data.InMemoryNotesRepository;
import com.dream.myself.data.Note;
import com.dream.myself.data.NotesRepository;
import com.dream.myself.data.NotesServiceApi;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by javierg on 12/10/2017.
 */

public class InMemoryNotesRepositoryTest {

    @Mock
    private NotesServiceApi mNotesServiceApi;

    @Mock
    private NotesRepository.LoadNotesCallback mLoadNotesCallback;

    @Mock
    private NotesRepository.GetNoteCallback mGetNoteCallback;


    @Captor
    ArgumentCaptor<NotesServiceApi.NotesServiceCallback> mNotesServiceCallbackArgumentCaptor;

    InMemoryNotesRepository mMemoryNotesRepository;

    private static List<Note> NOTES;

    @Before
    public void setUpInInMemoryNotesRepositoryTest() {
        MockitoAnnotations.initMocks(this);

        mMemoryNotesRepository = new InMemoryNotesRepository(mNotesServiceApi);

        NOTES = new ArrayList<>();
        NOTES.add(new Note("title", "description"));
        NOTES.add(new Note("title", "description"));
    }

    @Test
    public void getNotes_noCachedNotes() {

        twoLoadCallsToRepository(mLoadNotesCallback);

        verify(mNotesServiceApi).getAllNotes(any(NotesServiceApi.NotesServiceCallback.class));
    }

    @Test
    public void invalidateCache_DoesNotCallTheServiceApi() {
        // Given a setup Captor to capture callbacks
        twoLoadCallsToRepository(mLoadNotesCallback);

        // When data refresh is requested
        mMemoryNotesRepository.refreshData();
        mMemoryNotesRepository.getNotes(mLoadNotesCallback); // Third call to API

        // The notes where requested twice from the Service API (Caching on first and third call)
        verify(mNotesServiceApi, times(2)).getAllNotes(any(NotesServiceApi.NotesServiceCallback.class));
    }

    @Test
    public void getNotes_requestsAllNotesFromServiceApi() {
        // When notes are requested from the notes repository
        mMemoryNotesRepository.getNotes(mLoadNotesCallback);

        // Then notes are loaded from the service API
        verify(mNotesServiceApi).getAllNotes(any(NotesServiceApi.NotesServiceCallback.class));
    }

    @Test
    public void saveNote_savesNoteToServiceAPIAndInvalidatesCache() {
        Note note = new Note("title", "description");

        mMemoryNotesRepository.saveNote(note);

        assertThat(mMemoryNotesRepository.mCachedNotes, is(nullValue()));

    }

    @Test
    public void getNote_requestsSingleNoteFromServiceApi() {
        Note note = new Note("title", "description");
        mMemoryNotesRepository.getNote(note.getId(), mGetNoteCallback);

        verify(mNotesServiceApi).getNote(eq(note.getId()), any(NotesServiceApi.NotesServiceCallback.class));

    }

    public void twoLoadCallsToRepository(NotesRepository.LoadNotesCallback callback) {
        // When notes are requested from repository
        mMemoryNotesRepository.getNotes(callback); // First call to API

        verify(mNotesServiceApi).getAllNotes(mNotesServiceCallbackArgumentCaptor.capture());

        mNotesServiceCallbackArgumentCaptor.getValue().onLoaded(NOTES);

        mMemoryNotesRepository.getNotes(callback); // Second call to API
    }


}
