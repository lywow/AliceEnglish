package com.alice.aliceenglish.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.alice.aliceenglish.R;
import com.alice.aliceenglish.entity.Word;


public class WordActivity extends AppCompatActivity {
    private Word word;
    private TextView tvWord;
    private TextView tvWordPhonetic;
    private TextView tvChinese;
    private TextView tvWordOther;

    private ImageView ivBack;
    private ImageView ivSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onlyone_word);
        initData();
        initView();
    }

    private void initData(){
        word=(Word)getIntent().getSerializableExtra("word");
    }

    private void initView(){
        tvWord=findViewById(R.id.tvWord);
        tvWordPhonetic=findViewById(R.id.tvWordPhonetic);
        tvChinese=findViewById(R.id.tvChinese);
        tvWordOther=findViewById(R.id.tvWordOther);
        ivBack=findViewById(R.id.ivBack);
        ivSearch=findViewById(R.id.ivSearch);

        tvWord.setText(word.getEnglish());
        tvWordPhonetic.setText("/"+word.getPhonetic()+"/");
        tvChinese.setText(word.getChinese());
        tvWordOther.setText("这个单词你记录了"+word.getFrequency()+"次\n最后一次在"+word.getTime());
        ivBack.setVisibility(View.VISIBLE);
        ivSearch.setVisibility(View.INVISIBLE);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
