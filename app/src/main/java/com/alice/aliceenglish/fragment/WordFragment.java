package com.alice.aliceenglish.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.alice.aliceenglish.R;
import com.alice.aliceenglish.activity.EssayActivity;
import com.alice.aliceenglish.activity.WordActivity;
import com.alice.aliceenglish.adapter.EssayListAdapter;
import com.alice.aliceenglish.adapter.WordListAdapter;
import com.alice.aliceenglish.entity.Essay;
import com.alice.aliceenglish.entity.Word;
import com.alice.aliceenglish.test.EssayData;
import com.alice.aliceenglish.util.WordDAO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class WordFragment extends Fragment {
    private List<Word> words = new ArrayList<>();
    private ListView listView;
    private View view;
    private TextView tvNoneTip;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_listview, container, false);
        initData();
        initView();
        return view;
    }

    public void initData() {
        words = WordDAO.getWordDAO().queryAllData();
    }

    public void initView() {
        tvNoneTip=view.findViewById(R.id.tvNoneTip);
        listView = view.findViewById(R.id.listView);

        if(words==null||words.isEmpty()){
            tvNoneTip.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
        }else{
            listView.setVisibility(View.VISIBLE);
            tvNoneTip.setVisibility(View.GONE);
            Collections.sort(words, new Comparator<Word>() {
                @Override
                public int compare(Word o1, Word o2) {
                    int age1 = o1.getFrequency();
                    int age2 = o2.getFrequency();
                    if (age1 == age2) {
                        return 0;
                    }else {
                        return age1 > age2 ? -1 : 1 ;// 从大到小
                    }
                }
            });

            WordListAdapter wordListAdapter = new WordListAdapter(getActivity(), R.layout.item_word, words);
            listView.setAdapter(wordListAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(getActivity(), WordActivity.class);
                    Bundle mBundle = new Bundle();
                    mBundle.putSerializable("word", words.get(position));
                    intent.putExtras(mBundle);
                    startActivity(intent);
                }
            });
        }
    }
}