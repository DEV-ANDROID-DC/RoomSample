package com.debin.roomsample;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AddWordActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etWord;
    private Button btnAdd;
    public static final String KEY = "Key";
    private static final String TAG = "AddWordActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addword);
        etWord = findViewById(R.id.et_word);
        btnAdd = findViewById(R.id.btn_add);
        btnAdd.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.btn_add :
                Intent replyIntent = new Intent();
                if(TextUtils.isEmpty(etWord.getText().toString())) {
                    setResult(RESULT_CANCELED, replyIntent);
                } else {
                    Log.i(TAG,"Word is not Empty");
                    replyIntent.putExtra(KEY,etWord.getText().toString());
                    setResult(RESULT_OK, replyIntent);
                }
                finish();
        }
    }
}
