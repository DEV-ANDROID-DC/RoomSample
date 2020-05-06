package com.debin.roomsample;

import android.app.Application;
import android.content.Context;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.debin.roomsample.datasourse.WordDao;
import com.debin.roomsample.datasourse.WordRoomDatabase;
import com.debin.roomsample.model.Word;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


@RunWith(AndroidJUnit4.class)
public class WordDoaTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();
    private WordDao wordDao;
    private WordRoomDatabase wordRoomDatabase;

    @Before
    public void CreateDataBase() {
        Context context = ApplicationProvider.getApplicationContext();
        wordRoomDatabase = Room.inMemoryDatabaseBuilder(context, WordRoomDatabase.class)
                .allowMainThreadQueries().build();
        wordDao = wordRoomDatabase.wordDao();
    }

    @After
    public void CloseDatabse() {
        wordRoomDatabase.close();
    }

    @Test
    public void insertAndGetWord() throws Exception {
        Word word = new Word("Debin");
        wordDao.insert(word);
        List<Word> words = LiveDataTestUtil.getValue(wordDao.getAllWords());
        assertEquals(words.get(0).getWord(), word.getWord());
    }

    @Test
    public void getAllWords() throws Exception {
        Word word1 = new Word("Chinchu");
        wordDao.insert(word1);
        Word word2 = new Word("Kuncheria");
        wordDao.insert(word2);
        Word word3 = new Word("Miranda");
        wordDao.insert(word3);
        Word word4 = new Word("Who am I");
        wordDao.insert(word4);
        List<Word> words = LiveDataTestUtil.getValue(wordDao.getAllWords());
        assertEquals(words.get(0).getWord(),word1.getWord());
        assertEquals(words.get(1).getWord(),word2.getWord());
        assertEquals(words.get(2).getWord(),word3.getWord());
        assertEquals(words.get(3).getWord(),word4.getWord());
     }

     @Test
    public void deleteAll() throws Exception {
         Word word3 = new Word("Miranda");
         wordDao.insert(word3);
         Word word4 = new Word("Who am I");
         wordDao.insert(word4);
         wordDao.deleteAll();
         List<Word> words = LiveDataTestUtil.getValue(wordDao.getAllWords());
         assertTrue(words.isEmpty());
         assertEquals(words.size(), 0);
     }
     @Test
     public void deleteWord() throws InterruptedException {
         Word word1 = new Word("Chinchu");
         wordDao.insert(word1);
         Word word2 = new Word("Kuncheria");
         wordDao.insert(word2);
         Word word3 = new Word("Miranda");
         wordDao.insert(word3);
         Word word4 = new Word("Who am I");
         wordDao.insert(word4);
         wordDao.deleteWord(word3);
         List<Word> words = LiveDataTestUtil.getValue(wordDao.getAllWords());
         assertEquals(words.size(),3);
     }
}
