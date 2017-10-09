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

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.any;
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

    InMemoryNotesRepository mInMemoryNotesRepository;

    List<Note> mCachedNotes;

    @Before
    public void setUpInMemoryNotesRepository() {

        MockitoAnnotations.initMocks(this);

        mInMemoryNotesRepository = new InMemoryNotesRepository(mNotesServiceApi);
        mCachedNotes = new ArrayList();
        mCachedNotes.add(new Note("title", "description"));
        mCachedNotes.add(new Note("title", "description"));
    }

    @Test
    public void getNotes_successful() {

        mInMemoryNotesRepository.getNotes(mLoadNotesCallback);

        verify(mNotesServiceApi).getAllNotes(any(NotesServiceApi.NotesServiceCallback.class));

    }

//    @Test
//    public void invalidateCache_DoesNoCallTheServiceApi() {
//        twoLoadCallsToRepository(mLoadNotesCallback);
//    }


    private void twoLoadCallsToRepository(NotesRepository.LoadNotesCallback callback) {
        // When notes are requested from repository
        mNotesRepository.getNotes(callback); // First call to API

        // Use the Mockito Captor to capture the callback
        verify(mNotesServiceApi).getAllNotes(mNotesServiceCallbackArgumentCaptor.capture());

        // Trigger callback so notes are cached
        mNotesServiceCallbackArgumentCaptor.getValue().onLoaded(NOTES);

        mNotesRepository.getNotes(callback); // Second call to API
    }
}
