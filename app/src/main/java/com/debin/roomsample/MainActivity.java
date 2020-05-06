package com.debin.roomsample;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.debin.roomsample.adapter.WordAdapter;
import com.debin.roomsample.model.Word;
import com.debin.roomsample.viewmodel.WordViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ConstraintLayout constraintLayout;
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
        deleteWord(); // try to convert this function to abstract class
        wordViewModel = new ViewModelProvider(this).get(WordViewModel.class);
        wordViewModel.getListLiveDataOfAllWords().observe(this, words -> {
           Log.i(TAG, "Word is "+words.size());
            wordAdapter.setWords(words);
        });

        floatingActionButton.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AddWordActivity.class);
            startActivityForResult(intent, ADD_WORD_ACTIVITY_REQUEST_CODE);
        });

    }

    private void initViews() {
        floatingActionButton = findViewById(R.id.fab);
        recyclerView = findViewById(R.id.recyclerView);
        constraintLayout = findViewById(R.id.contraintLayout);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        wordAdapter = new WordAdapter(this);
        recyclerView.setAdapter(wordAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == ADD_WORD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            assert data != null;
            Word word = new Word(Objects.requireNonNull(data.getStringExtra(AddWordActivity.KEY)));
            wordViewModel.insert(word);
        } else {
            Toast.makeText(getApplicationContext(), R.string.word_empty, Toast.LENGTH_LONG).show();
        }
    }

    private void deleteWord() {
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                Word word = wordAdapter.getWordAtPosition(position);
                wordViewModel.delete(word);
                wordAdapter.deleteWord(position);

                Snackbar snackbar = Snackbar.make(constraintLayout, R.string.item_remove_text,
                        Snackbar.LENGTH_LONG);
                snackbar.setAction(R.string.undo, view -> {
                    wordAdapter.restoreWord(position,word);
                    recyclerView.scrollToPosition(position);
                });
                snackbar.setActionTextColor(Color.YELLOW);
                snackbar.show();
            }
        });
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }
}
