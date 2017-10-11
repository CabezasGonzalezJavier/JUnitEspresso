package com.dream.myself;

import com.dream.myself.data.InMemoryNotesRepository;
import com.dream.myself.data.Note;
import com.dream.myself.data.NotesRepository;
import com.dream.myself.data.NotesServiceApi;
import com.google.common.collect.Lists;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by javierg on 09/10/2017.
 */

public class InMemoryNotesRepositoryTest {

    private final static String TITLE = "title";
    private final static String DESCRIPTION = "Description";

    private static List<Note> NOTES = Lists.newArrayList(new Note("Title1", "Description1"),
            new Note("Title2", "Description2"));

    InMemoryNotesRepository mNotesRepository;

    @Mock
    NotesServiceApi mNotesServiceApi;

    @Mock
    NotesRepository.GetNoteCallback mGetNoteCallback;

    @Mock
    NotesRepository.LoadNotesCallback mLoadNotesCallback;

    @Captor
    ArgumentCaptor<NotesServiceApi.NotesServiceCallback> mNotesServiceCallbackArgumentCaptor;

    @Before
    public void setUpInMemoryNotesRepository() {

        MockitoAnnotations.initMocks(this);
        // Get a reference to the class under test
        mNotesRepository = new InMemoryNotesRepository(mNotesServiceApi);
    }

    @Test
    public void getNotes_repositoryCachesAfterFirstApiCall() {
        // Given a setup Captor to capture callbacks
        // When two calls are issued to the notes repository
        twoLoadCallsToRepository(mLoadNotesCallback);

        // Then notes where only requested once from Service API
        verify(mNotesServiceApi).getAllNotes(any(NotesServiceApi.NotesServiceCallback.class));
    }

    @Test
    public void invalidateCache_DoesNotCallTheServiceApi() {
        twoLoadCallsToRepository(mLoadNotesCallback);

        mNotesRepository.refreshData();
        mNotesRepository.getNotes(mLoadNotesCallback);

        verify(mNotesServiceApi, times(2)).getAllNotes(any(NotesServiceApi.NotesServiceCallback.class));
    }

    private void twoLoadCallsToRepository(NotesRepository.LoadNotesCallback callback) {
        // When notes are requested from repository
        mNotesRepository.getNotes(callback);

        verify(mNotesServiceApi).getAllNotes(mNotesServiceCallbackArgumentCaptor.capture());

        mNotesServiceCallbackArgumentCaptor.getValue().onLoaded(NOTES);

        mNotesRepository.getNotes(callback);// Second call to API
    }

}
