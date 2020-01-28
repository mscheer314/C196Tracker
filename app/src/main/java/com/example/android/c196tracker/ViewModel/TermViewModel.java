package com.example.android.c196tracker.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.android.c196tracker.Database.SchoolTrackerRepository;
import com.example.android.c196tracker.Entities.TermEntity;

import java.util.List;

public class TermViewModel extends AndroidViewModel {
    private SchoolTrackerRepository repository;
    private LiveData<List<TermEntity>> allTerms;


    public TermViewModel(@NonNull Application application) {
        super(application);
        repository = new SchoolTrackerRepository(application);
        allTerms = repository.getAllTerms();
    }

    public LiveData<List<TermEntity>> getAllTerms() {
        return allTerms;
    }

    public List<TermEntity> getTermById(int termId) {
        return repository.getTermById(termId);
    }

    public void insert(TermEntity termEntity) {
        repository.insert(termEntity);
    }

    public void update(TermEntity termEntity) { repository.update(termEntity); }

    public void delete(TermEntity termEntity) { repository.delete(termEntity); }

    public int lastId() {
        return allTerms.getValue().size();
    }
}
