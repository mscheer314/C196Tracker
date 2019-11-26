package com.example.android.c196tracker.Database;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.android.c196tracker.DAO.CourseDAO;
import com.example.android.c196tracker.DAO.NoteDAO;
import com.example.android.c196tracker.DAO.TermDAO;
import com.example.android.c196tracker.Entities.CourseEntity;
import com.example.android.c196tracker.Entities.NoteEntity;
import com.example.android.c196tracker.Entities.TermEntity;

import java.util.List;

public class SchoolTrackerRepository {
    private TermDAO mTermDAO;
    private CourseDAO mCourseDAO;
    private NoteDAO mNoteDAO;
    private LiveData<List<TermEntity>> mAllTerms;
    private LiveData<List<CourseEntity>> mAllCourses;
    private LiveData<List<NoteEntity>> mAllNotes;
    private LiveData<List<CourseEntity>> mAssociatedCourses;
    private LiveData<List<NoteEntity>> mAssociatedNotes;
    private int courseId;
    private int noteId;

    public SchoolTrackerRepository(Application application) {
        SchoolTrackerDatabase db = SchoolTrackerDatabase.getDatabase(application);
        mTermDAO = db.termDAO();
        mCourseDAO = db.courseDAO();
        mNoteDAO = db.noteDAO();
        mAllTerms = mTermDAO.getAllTerms();
        mAllCourses = mCourseDAO.getAllCourses();
        mAllNotes = mNoteDAO.getAllNotes();
    }

    public LiveData<List<TermEntity>> getAllTerms() {
        return mAllTerms;
    }

    public LiveData<List<CourseEntity>> getAllCourses() {
        return mAllCourses;
    }

    public LiveData<List<NoteEntity>> getmAllNotes() {
        return mAllNotes;
    }

    public LiveData<List<CourseEntity>> getmAssociatedCourses(int termId) {
        return mAssociatedCourses;
    }

    public LiveData<List<NoteEntity>> getmAssociatedNotes(int courseId) {
        return mAssociatedNotes;
    }

    public void insert(TermEntity termEntity) {
        new insertAsyncTask1(mTermDAO).execute(termEntity);
    }

    private static class insertAsyncTask1 extends AsyncTask<TermEntity, Void, Void> {
        private TermDAO mAsyncTaskDAO;

        insertAsyncTask1(TermDAO dao) {
            mAsyncTaskDAO = dao;
        }

        @Override
        protected Void doInBackground(final TermEntity... params) {
            mAsyncTaskDAO.insert(params[0]);
            return null;
        }
    }

    public void insert(CourseEntity courseEntity) {
        new insertAsyncTask2(mCourseDAO).execute(courseEntity);
    }

    private static class insertAsyncTask2 extends AsyncTask<CourseEntity, Void, Void> {
        private CourseDAO mAsyncCourseDAO;

        insertAsyncTask2(CourseDAO dao) {
            mAsyncCourseDAO = dao;
        }

        @Override
        protected Void doInBackground(final CourseEntity... params) {
            mAsyncCourseDAO.insert(params[0]);
            return null;
        }
    }

    public void insert(NoteEntity noteEntity) {
        new insertAsyncTask3(mNoteDAO).execute(noteEntity);
    }

    private static class insertAsyncTask3 extends AsyncTask<NoteEntity, Void, Void> {
        private NoteDAO mAsyncNoteDAO;

        insertAsyncTask3(NoteDAO dao) {
            mAsyncNoteDAO = dao;
        }

        @Override
        protected Void doInBackground(final NoteEntity... params) {
            mAsyncNoteDAO.insert(params[0]);
            return null;
        }
    }
}
