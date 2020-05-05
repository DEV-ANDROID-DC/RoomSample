package com.debin.roomsample.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.debin.roomsample.R;
import com.debin.roomsample.model.Word;

import java.util.List;

public class WordAdapter extends RecyclerView.Adapter<WordAdapter.MyViewHolder> {

    private List<Word> wordList;
    private Context context;
    private static final String TAG = "WordAdapter";

    public WordAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.word_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bind(wordList.get(position));
    }

    @Override
    public int getItemCount() {
        if(wordList!=null) {
        return wordList.size();} else { return 0; }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvWord;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvWord = itemView.findViewById(R.id.tv_word);
        }

        public void bind(Word word) {
            tvWord.setText(word.getWord());
        }
    }

    public void setWords(List<Word> word){
        Log.i(TAG, "Size of list in Adapter :: " +word.size());
       wordList = word;
       notifyDataSetChanged();
    }
}
