package com.debin.roomsample.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.debin.roomsample.model.Word;
import com.debin.roomsample.repository.WordRepository;

import java.util.List;

public class WordViewModel extends AndroidViewModel {

    private WordRepository wordRepository;
    private LiveData<List<Word>> listLiveDataOfAllWords;
    public WordViewModel(@NonNull Application application) {
        super(application);
        wordRepository = new WordRepository(application);
        listLiveDataOfAllWords = wordRepository.getListLiveDataOfAllWords();
    }

    public LiveData<List<Word>> getListLiveDataOfAllWords() {
        return listLiveDataOfAllWords;
    }

    public void insert(Word word) {
        wordRepository.insert(word);
    }

    public void delete(Word word) {
        wordRepository.delete(word);
    }

}
