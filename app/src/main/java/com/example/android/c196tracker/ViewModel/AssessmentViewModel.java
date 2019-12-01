package com.example.android.c196tracker.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.android.c196tracker.Database.SchoolTrackerRepository;
import com.example.android.c196tracker.Entities.AssessmentEntity;
import com.example.android.c196tracker.Entities.TermEntity;

import java.util.List;

public class AssessmentViewModel extends AndroidViewModel {
    private SchoolTrackerRepository mRepository;
    private LiveData<List<AssessmentEntity>> mAllAssessments;

    public AssessmentViewModel(@NonNull Application application) {
        super(application);
        mRepository = new SchoolTrackerRepository(application);
        mAllAssessments = mRepository.getmAllAssessments();
    }

    public LiveData<List<AssessmentEntity>> getAllAssessments() { return mAllAssessments; }

    public void insert (AssessmentEntity assessmentEntity) {
        mRepository.insert(assessmentEntity);
    }
}
