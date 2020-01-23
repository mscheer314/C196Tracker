package com.example.android.c196tracker.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.android.c196tracker.Database.SchoolTrackerRepository;
import com.example.android.c196tracker.Entities.AssessmentEntity;

import java.util.List;

public class AssessmentViewModel extends AndroidViewModel {
    private SchoolTrackerRepository repository;
    private LiveData<List<AssessmentEntity>> allAssessments;
    private LiveData<List<AssessmentEntity>> associatedAssessments;

    public AssessmentViewModel(@NonNull Application application) {
        super(application);
        repository = new SchoolTrackerRepository(application);
        allAssessments = repository.getAllAssessments();
    }

    public LiveData<List<AssessmentEntity>> getAllAssessments() {
        return allAssessments;
    }

    public LiveData<List<AssessmentEntity>> getAssociatedAssessments(int courseId) {
        return repository.getAssociatedAssessments(courseId);
    }

    public void insert(AssessmentEntity assessmentEntity) {
        repository.insert(assessmentEntity);
    }

    public void delete(AssessmentEntity assessmentEntity) {
        repository.delete(assessmentEntity);
    }
}
