package com.alice.aliceenglish.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alice.aliceenglish.R;
import com.alice.aliceenglish.entity.Word;
import com.alice.aliceenglish.util.Translate;
import com.alice.aliceenglish.util.WordDAO;
import com.xuexiang.xui.widget.progress.loading.ARCLoadingView;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class BottomDialog extends Dialog{
    private static final int COMPLETED = 0;

    private View view;
    private TextView tvWord;
    private TextView tvWordPhonetic;
    private TextView tvChinese;
    private Button bnWord;
    private String english;
    private Word word;
    private TextView itemTV;

    private TextView tvTip;
    private ARCLoadingView loadingView;
    private RelativeLayout mainLayout;

    private Translate translate;
    private Thread thread;

    public BottomDialog(@NonNull Context context) {
        super(context);
    }

    public BottomDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected BottomDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public void init(String _english,TextView tv){
        english=_english;
        itemTV=tv;
        view = View.inflate(getContext(), R.layout.activity_word, null);
        this.setContentView(view);

        Window window = this.getWindow();
        window.setGravity(Gravity.BOTTOM);        //设置弹出位置
        window.setWindowAnimations(R.style.main_menu_animStyle);        //设置弹出动画
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);        //设置对话框大小

        tvWord=view.findViewById(R.id.tvWord);
        tvWordPhonetic=view.findViewById(R.id.tvWordPhonetic);
        tvChinese=view.findViewById(R.id.tvChinese);
        bnWord=view.findViewById(R.id.bnWord);
        tvTip=view.findViewById(R.id.tvTip);
        loadingView=view.findViewById(R.id.arcLoading);
        mainLayout=view.findViewById(R.id.mainLayout);

        tvTip.setVisibility(View.GONE);
        mainLayout.setVisibility(View.GONE);

        translate=new Translate(english);
        thread=new Thread(translate);

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

    @Override
    public void show(){
        super.show();
        loadingView.start();

    }

    public void loadingView(){
        word=null;
        List<Word> words=WordDAO.getWordDAO().queryOneData(english);
        if(words==null){
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
            if(word==null){
                tvTip.setVisibility(View.VISIBLE);
            }else{
                mainLayout.setVisibility(View.VISIBLE);
                tvWord.setText(word.getEnglish());
                tvWordPhonetic.setText("/"+word.getPhonetic()+"/");
                tvChinese.setText(word.getChinese());
                bnWord.setText("再一次记录这个单词");
                bnWord.setTextColor(Color.parseColor("#FFFFFF"));
                bnWord.setBackgroundResource(R.drawable.again_word_button_border);
            }
            loadingView.stop();
        }
    }

    @Override
    public void dismiss(){
        itemTV.setTextColor(Color.parseColor("#000000"));
        itemTV.setBackgroundResource(R.drawable.none_background);
        super.dismiss();
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
                    }
                }
                loadingView.stop();
            }
        }
    };

}
