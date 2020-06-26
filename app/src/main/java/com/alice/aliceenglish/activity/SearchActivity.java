package com.alice.aliceenglish.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.alice.aliceenglish.R;
import com.alice.aliceenglish.entity.Word;
import com.alice.aliceenglish.util.Translate;
import com.alice.aliceenglish.util.WordDAO;
import com.xuexiang.xui.widget.edittext.ValidatorEditText;
import com.xuexiang.xui.widget.progress.loading.ARCLoadingView;

import java.util.Date;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    private static final int COMPLETED = 0;

    private ValidatorEditText validatorEditText;
    private TextView tvWord;
    private TextView tvWordPhonetic;
    private TextView tvChinese;
    private Button bnWord;
    private Word word;

    private TextView tvTip;
    private ARCLoadingView loadingView;
    private RelativeLayout mainLayout;

    private Translate translate;
    private Thread thread;

    private ImageView ivBack;
    private ImageView ivSearch;
    private Thread ttt;

    private String english;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initData();
        initView();
    }

    private void initData(){
        english="";
    }

    private void initView(){
        ivBack=findViewById(R.id.ivBack);
        ivSearch=findViewById(R.id.ivSearch);
        ivBack.setVisibility(View.VISIBLE);
        ivSearch.setVisibility(View.INVISIBLE);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        validatorEditText=findViewById(R.id.vetSearch);
        validatorEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                english=editable.toString().trim();
                if(!english.isEmpty()){
                    if(ttt!=null&&ttt.isAlive()){
                        ttt.interrupt();
                    }
                    tvTip.setVisibility(View.GONE);
                    mainLayout.setVisibility(View.GONE);
                    loadingView.start();
                    ttt=new Thread(new Runnable() {
                        @Override
                        public void run() {
                            String str=english;
                            try {
                                Thread.sleep(800);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            if(str.equals(english)){
                                loadingView();
                            }
                        }
                    });
                    ttt.start();
                }
            }
        });

        tvWord=findViewById(R.id.tvWord);
        tvWordPhonetic=findViewById(R.id.tvWordPhonetic);
        tvChinese=findViewById(R.id.tvChinese);
        bnWord=findViewById(R.id.bnWord);
        tvTip=findViewById(R.id.tvTip);
        loadingView=findViewById(R.id.arcLoading);
        mainLayout=findViewById(R.id.mainLayout);

        tvTip.setVisibility(View.GONE);
        mainLayout.setVisibility(View.GONE);

        bnWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                word.setFrequency(word.getFrequency()+1);
                word.setTime(new Date());
                System.out.println(new Date().getTime());
                if(word.getFrequency()==1){
                    WordDAO.getWordDAO().insert(word);
                }else{
                    WordDAO.getWordDAO().updateOneData(word);
                    bnWord.setTextColor(Color.parseColor("#000000"));
                }
                bnWord.setText("这个单词完成记录了");
                bnWord.setBackgroundResource(R.drawable.none_background);
                bnWord.setClickable(false);
            }
        });
    }

    public void loadingView(){
        word=null;
        List<Word> words= WordDAO.getWordDAO().queryOneData(english);
        if(words==null){
            translate=new Translate(english);
            thread=new Thread(translate);
            thread.start();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(2000);
                        if(thread.isAlive()){
                            thread.interrupt();
                        }
                        word=translate.getWord();
                        Message message = new Message();
                        message.what = COMPLETED;
                        handler.sendMessage(message);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }else{
            word=words.get(0);
            Message message = new Message();
            message.what = COMPLETED;
            handler.sendMessage(message);
            loadingView.stop();
        }
    }
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == COMPLETED) {
                if(word==null){
                    tvTip.setVisibility(View.VISIBLE);
                }else{
                    mainLayout.setVisibility(View.VISIBLE);
                    tvWord.setText(word.getEnglish());
                    tvWordPhonetic.setText("/"+word.getPhonetic()+"/");
                    tvChinese.setText(word.getChinese());
                    if(word.getFrequency()==-1){
                        bnWord.setVisibility(View.INVISIBLE);
                    }else if(word.getFrequency()>0){
                        bnWord.setText("再一次记录这个单词");
                        bnWord.setTextColor(Color.parseColor("#FFFFFF"));
                        bnWord.setBackgroundResource(R.drawable.again_word_button_border);
                    }
                }
                loadingView.stop();
            }
        }
    };
}
