package com.example.android.c196tracker.ViewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.android.c196tracker.Database.SchoolTrackerRepository;
import com.example.android.c196tracker.Entities.CourseEntity;

import java.util.List;

public class CourseViewModel extends AndroidViewModel {
    int termId;
    private SchoolTrackerRepository mRepository;
    private LiveData<List<CourseEntity>> mAllCourses;
    private LiveData<List<CourseEntity>> mAssociatedCourses;

    public CourseViewModel(Application application) {
        super(application);
        mRepository = new SchoolTrackerRepository(application);

        mAllCourses = mRepository.getAllCourses();
        mAssociatedCourses = mRepository.getmAssociatedCourses(termId);
    }

    public CourseViewModel(Application application, int termId) {
        super(application);
        mRepository = new SchoolTrackerRepository(application);
        mAssociatedCourses = mRepository.getmAssociatedCourses(termId);
    }

    public LiveData<List<CourseEntity>> getmAllCourses() {
        return mAllCourses;
    }

    public void insert(CourseEntity courseEntity) {
        mRepository.insert(courseEntity);
    }
}
