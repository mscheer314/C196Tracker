package com.example.android.c196tracker.ViewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.android.c196tracker.Database.SchoolTrackerRepository;
import com.example.android.c196tracker.Entities.NoteEntity;

import java.util.List;

public class NoteViewModel extends AndroidViewModel {
    private SchoolTrackerRepository repository;
    private LiveData<List<NoteEntity>> allNotes;

    public NoteViewModel(Application application) {
        super(application);
        repository = new SchoolTrackerRepository(application);
        allNotes = repository.getAllNotes();
    }

    public LiveData<List<NoteEntity>> getAllNotes() {
        return allNotes;
    }

    public void insert(NoteEntity noteEntity) {
        repository.insert(noteEntity);
    }

    public int lastId() {
        return allNotes.getValue().size();
    }
}
