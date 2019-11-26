package com.example.android.c196tracker.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.android.c196tracker.Database.SchoolTrackerRepository;
import com.example.android.c196tracker.Entities.TermEntity;

import java.util.List;

public class TermViewModel extends AndroidViewModel {
    private SchoolTrackerRepository mRepository;
    private LiveData<List<TermEntity>> mAllTerms;

    public TermViewModel(@NonNull Application application) {
        super(application);
        mRepository = new SchoolTrackerRepository(application);
        mAllTerms = mRepository.getAllTerms();
    }

    public LiveData<List<TermEntity>> getAllTerms() {
        return mAllTerms;
    }

    public void insert(TermEntity termEntity) {
        mRepository.insert(termEntity);
    }

    public int lastId() {
        return mAllTerms.getValue().size();
    }
}
