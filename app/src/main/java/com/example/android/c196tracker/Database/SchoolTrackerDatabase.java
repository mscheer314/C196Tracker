package com.example.android.c196tracker.Database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.android.c196tracker.DAO.AssessmentDAO;
import com.example.android.c196tracker.DAO.CourseDAO;
import com.example.android.c196tracker.DAO.NoteDAO;
import com.example.android.c196tracker.DAO.TermDAO;
import com.example.android.c196tracker.Entities.AssessmentEntity;
import com.example.android.c196tracker.Entities.CourseEntity;
import com.example.android.c196tracker.Entities.NoteEntity;
import com.example.android.c196tracker.Entities.TermEntity;

@Database(entities = {TermEntity.class, CourseEntity.class, NoteEntity.class, AssessmentEntity.class}, version = 1, exportSchema = false)

public abstract class SchoolTrackerDatabase extends RoomDatabase {
    public abstract TermDAO termDAO();
    public abstract CourseDAO courseDAO();
    public abstract NoteDAO noteDAO();
    public abstract AssessmentDAO assessmentDAO();

    private static volatile SchoolTrackerDatabase INSTANCE;

    static SchoolTrackerDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (SchoolTrackerDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext()
                            , SchoolTrackerDatabase.class, "school_tracker_database")
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            new PopulateDbAsync(INSTANCE).execute();
        }
    };


    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {
        private final TermDAO mTermDao;
        private final CourseDAO mCourseDao;
        private final NoteDAO mNoteDao;
        private final AssessmentDAO mAssessmentDao;

        PopulateDbAsync(SchoolTrackerDatabase db) {
            mTermDao = db.termDAO();
            mCourseDao = db.courseDAO();
            mNoteDao = db.noteDAO();
            mAssessmentDao = db.assessmentDAO();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            return null;
        }
    }
}
