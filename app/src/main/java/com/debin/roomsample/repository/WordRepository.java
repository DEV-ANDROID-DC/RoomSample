package com.debin.roomsample.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.debin.roomsample.datasourse.WordDao;
import com.debin.roomsample.datasourse.WordRoomDatabase;
import com.debin.roomsample.model.Word;

import java.util.List;

public class WordRepository {

    private WordDao wordDao;
    private LiveData<List<Word>> listLiveDataOfAllWords;

    public WordRepository(Application application) {
       // WordRoomDatabase wordRoomDatabase = WordRoomDatabase.
        WordRoomDatabase db = WordRoomDatabase.getDataBase(application);
        wordDao = db.wordDao();
        listLiveDataOfAllWords = wordDao.getAllWords();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    public LiveData<List<Word>> getListLiveDataOfAllWords() {
        return listLiveDataOfAllWords;
    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    public void insert(Word word) {
        WordRoomDatabase.databaseWriteExecutor.execute( () -> {
           wordDao.insert(word);
                });
    }
}
