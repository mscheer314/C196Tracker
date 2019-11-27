package com.example.android.c196tracker.ViewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.android.c196tracker.Database.SchoolTrackerRepository;
import com.example.android.c196tracker.Entities.NoteEntity;

import java.util.List;

public class NoteViewModel extends AndroidViewModel {
    private SchoolTrackerRepository mRepository;
    private LiveData<List<NoteEntity>> mAllNotes;

    public NoteViewModel(Application application) {
        super(application);
        mRepository = new SchoolTrackerRepository(application);
        mAllNotes = mRepository.getmAllNotes();
    }

    public LiveData<List<NoteEntity>> getmAllNotes() {
        return mAllNotes;
    }

    public void insert(NoteEntity noteEntity) {
        mRepository.insert(noteEntity);
    }

    public int lastId() {
        return mAllNotes.getValue().size();
    }
}
