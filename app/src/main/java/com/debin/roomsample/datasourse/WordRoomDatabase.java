package com.debin.roomsample.datasourse;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.debin.roomsample.model.Word;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Word.class}, version = 1, exportSchema = false)
public abstract class WordRoomDatabase extends RoomDatabase {


    public abstract WordDao wordDao();
    private static volatile WordRoomDatabase INSTANCE;
    private static final int numberOfThreads = 4;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(numberOfThreads);

    public static WordRoomDatabase getDataBase(final Context context) {
        if(INSTANCE == null) {
            synchronized (WordRoomDatabase.class) {
                if(INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            WordRoomDatabase.class, "word_database")
                            .addCallback(sRoomDatabseCallback)
                            .build();
                }
            }

        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabseCallback =  new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            // If you want to keep data through app restarts,
            // comment out the following block
            databaseWriteExecutor.execute(() -> {
                // Populate the database in the background.
                // If you want to start with more words, just add them.
                WordDao wordDao = INSTANCE.wordDao();
                wordDao.deleteAll();
                Word word = new Word("Debin");
                wordDao.insert(word);
                word = new Word("Tom");
                wordDao.insert(word);
            });
        }
    };
}
