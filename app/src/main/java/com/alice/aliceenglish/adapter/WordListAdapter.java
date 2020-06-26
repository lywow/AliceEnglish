package com.alice.aliceenglish.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.alice.aliceenglish.R;
import com.alice.aliceenglish.entity.Essay;
import com.alice.aliceenglish.entity.Word;
import com.google.gson.internal.$Gson$Preconditions;

import java.util.List;

public class WordListAdapter extends ArrayAdapter<Word> {
    private int resourceId;

    public WordListAdapter(Context context, int resource, List<Word> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }

    @NonNull
    public View getView(int position, View convertView, ViewGroup parent) {
        Word word = getItem(position);//获取当前项的实例
        View view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);

        TextView english = view.findViewById(R.id.tvWordEnglish);
        final TextView chinese = view.findViewById(R.id.tvWordChinese);
        TextView frequency = view.findViewById(R.id.tvWordFrequency);

        english.setText(word.getEnglish());
        chinese.setText(word.getChinese());
        frequency.setText("记录"+word.getFrequency()+"次");

        chinese.setBackgroundColor(Color.parseColor("#666666"));
        chinese.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chinese.setBackgroundColor(Color.parseColor("#FFFFFF"));
            }
        });

        return view;
    }
}