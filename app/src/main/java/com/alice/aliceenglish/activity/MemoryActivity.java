package com.alice.aliceenglish.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.alice.aliceenglish.R;
import com.alice.aliceenglish.entity.Word;
import com.alice.aliceenglish.util.WordDAO;

import java.util.List;

public class MemoryActivity extends AppCompatActivity {
    private List<Word> words;
    private TextView tvWord;
    private TextView tvWordPhonetic;
    private TextView tvChinese;

    private ImageView ivBack;
    private ImageView ivSearch;

    private Button bnMemoryWord;
    private Button bnDontMemoryWord;
    private TextView tvWordOther;
    private int wordFrequency;

    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory_word);
        initData();
        initView();
    }

    private void initData(){
        index=0;
        words = WordDAO.getWordDAO().queryMemoryData();
    }

    private void initView(){
        tvWord=findViewById(R.id.tvWord);
        tvWordPhonetic=findViewById(R.id.tvWordPhonetic);
        tvChinese=findViewById(R.id.tvChinese);
        ivBack=findViewById(R.id.ivBack);
        ivSearch=findViewById(R.id.ivSearch);
        bnMemoryWord=findViewById(R.id.bnMemoryWord);
        bnDontMemoryWord=findViewById(R.id.bnDontMemoryWord);
        tvWordOther=findViewById(R.id.tvWordOther);

        ivBack.setVisibility(View.VISIBLE);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MemoryActivity.this,SearchActivity.class);
                startActivity(intent);
            }
        });

        if(!words.isEmpty()){
            initWord();
        }

        bnMemoryWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickButtonMemoryWord();
            }
        });

        bnDontMemoryWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickButtonDontMemoryWord();
            }
        });
    }

    private void initWord(){
        tvWord.setText(words.get(index).getEnglish());
        tvWordPhonetic.setText("/"+words.get(index).getPhonetic()+"/");
        tvChinese.setText(words.get(index).getChinese());
        tvChinese.setVisibility(View.INVISIBLE);
        wordFrequency=words.get(index).getFrequency();
        tvWordOther.setText("这个单词你记录了"+wordFrequency+"次\n最后一次在"+words.get(index).getTime());
        bnMemoryWord.setText("我认识");
        bnDontMemoryWord.setText("记不住了");
        bnDontMemoryWord.setVisibility(View.VISIBLE);
    }

    private void clickButtonMemoryWord(){
        if(tvChinese.getVisibility()==View.INVISIBLE){
            tvChinese.setVisibility(View.VISIBLE);
            wordFrequency--;
            if(wordFrequency==0){
                tvWordOther.setText("已经判定你记住了这个单词\n最后一次在"+words.get(index).getTime());
            }
            bnMemoryWord.setText("下一个");
            bnDontMemoryWord.setText("我记错了");
        }else{
            Word w=words.get(index);
            if(wordFrequency==w.getFrequency()){
                index++;
            }else{
                if(wordFrequency==0){
                    w.setFrequency(0);
                    words.remove(index);
                }else{
                    w.setFrequency(wordFrequency);
                    index++;
                }
                WordDAO.getWordDAO().updateOneData(w);
            }
            if(index==words.size()){index=0;}
            initWord();
        }
    }

    private void clickButtonDontMemoryWord(){
        if(tvChinese.getVisibility()==View.INVISIBLE){
            tvChinese.setVisibility(View.VISIBLE);
            bnMemoryWord.setText("下一个");
            bnDontMemoryWord.setVisibility(View.GONE);
        }else{
            wordFrequency++;
            index++;
            if(index==words.size()){index=0;}
            initWord();
        }
    }
}