package com.debin.roomsample;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.debin.roomsample.adapter.WordAdapter;
import com.debin.roomsample.model.Word;
import com.debin.roomsample.viewmodel.WordViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private WordAdapter wordAdapter;
    private WordViewModel wordViewModel;
    private FloatingActionButton floatingActionButton;
    public static final int ADD_WORD_ACTIVITY_REQUEST_CODE = 1;
    private static final String TAG = "MainActivity";
    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        wordViewModel = new ViewModelProvider(this).get(WordViewModel.class);
        wordViewModel.getListLiveDataOfAllWords().observe(this, new Observer<List<Word>>() {
            @Override
            public void onChanged(List<Word> words) {
               Log.i(TAG, "Word is "+words.size());
                wordAdapter.setWords(words);
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddWordActivity.class);
                startActivityForResult(intent, ADD_WORD_ACTIVITY_REQUEST_CODE);
            }
        });


    }

    private void initViews() {
        floatingActionButton = findViewById(R.id.fab);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        wordAdapter = new WordAdapter(this);
        recyclerView.setAdapter(wordAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == ADD_WORD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Word word = new Word(data.getStringExtra(AddWordActivity.KEY));
            wordViewModel.insert(word);
        } else {
            Toast.makeText(getApplicationContext(), R.string.word_empty, Toast.LENGTH_LONG).show();
        }
    }
}
