package com.example.android.c196tracker.ViewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.android.c196tracker.Database.SchoolTrackerRepository;
import com.example.android.c196tracker.Entities.NoteEntity;

import java.util.List;

public class NoteViewModel extends AndroidViewModel {
    private int courseId;
    private SchoolTrackerRepository repository;
    private LiveData<List<NoteEntity>> allNotes;
    private LiveData<List<NoteEntity>> associatedNotes;

    public NoteViewModel(Application application) {
        super(application);
        repository = new SchoolTrackerRepository(application);
        allNotes = repository.getAllNotes();
        associatedNotes = repository.getAssociatedNotes(courseId);
    }

    public LiveData<List<NoteEntity>> getAllNotes() {
        return allNotes;
    }

    public LiveData<List<NoteEntity>> getAssociatedNotes(int courseId) {
        return repository.getAssociatedNotes(courseId);
    }

    public void insert(NoteEntity noteEntity) {
        repository.insert(noteEntity);
    }

    public int lastId() {
        return allNotes.getValue().size();
    }
}
