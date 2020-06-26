package com.alice.aliceenglish.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.alice.aliceenglish.R;
import com.alice.aliceenglish.adapter.ParagraphListAdapter;
import com.alice.aliceenglish.entity.Essay;
import com.alice.aliceenglish.util.EssayToListTextView;
import com.xuexiang.xui.widget.searchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EssayActivity  extends AppCompatActivity {
    private Essay essay;
    private List<String> paragraphs;
    private ListView paragraphList;

    private ImageView ivBack;
    private ImageView ivSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_essay);
        initData();
        initView();
    }

    private void initData(){
        essay=(Essay)getIntent().getSerializableExtra("essay");
        paragraphs=getParagraphs();
    }

    private void initView(){
        paragraphList=findViewById(R.id.lvParagraphList);
        ParagraphListAdapter paragraphListAdapter=new ParagraphListAdapter(this,R.layout.item_essay_paragraph,paragraphs);
        paragraphList.setAdapter(paragraphListAdapter);
        paragraphList.setDivider(null);
        ivBack=findViewById(R.id.ivBack);
        ivSearch=findViewById(R.id.ivSearch);
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
                Intent intent = new Intent(EssayActivity.this,SearchActivity.class);
                startActivity(intent);
            }
        });
    }

    private List<String> getParagraphs(){
        List<String> list=new ArrayList<>();
        list.add(essay.getTitle());
        list.add("");
        list.add("TAG="+essay.getTag()+"="+essay.getWordNumber()+"词="+essay.getTime());
        list.add("");
        String[] str=essay.getContent().split("\n");
        for(String s:str){
            list.add(s);
            list.add("");
        }
        return list;
    }
}
