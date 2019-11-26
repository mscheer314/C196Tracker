package com.example.android.c196tracker.ViewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.android.c196tracker.Database.SchoolTrackerRepository;
import com.example.android.c196tracker.Entities.NoteEntity;

import java.util.List;

public class NoteViewModel extends AndroidViewModel {
    int noteId;

    private SchoolTrackerRepository mRepository;
    private LiveData<List<NoteEntity>> mAllNotes;
    private LiveData<List<NoteEntity>> mAssociatedNotes;

    public NoteViewModel(Application application) {
        super(application);
        mRepository = new SchoolTrackerRepository(application);

        mAllNotes = mRepository.getmAllNotes();
        mAssociatedNotes = mRepository.getmAssociatedNotes(noteId);
    }

    public NoteViewModel(Application application, int courseId) {
        super(application);
        mRepository = new SchoolTrackerRepository(application);

        mAssociatedNotes = mRepository.getmAssociatedNotes(courseId);
    }

    public LiveData<List<NoteEntity>> getmAllNotes() {
        return mAllNotes;
    }

    public LiveData<List<NoteEntity>> getmAssociatedNotes(int courseId) {
        return mRepository.getmAssociatedNotes(courseId);
    }

    private void insert(NoteEntity noteEntity) {
        mRepository.insert(noteEntity);
    }

    // not sure why I need this.
    public int lastId() {
        return mAllNotes.getValue().size();
    }
}
