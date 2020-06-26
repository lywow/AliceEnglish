package com.alice.aliceenglish.util;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.alice.aliceenglish.R;
import java.util.ArrayList;
import java.util.List;

public class EssayToListTextView {

    public static List<List<TextView>> getListTextView(View view, String essay){
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(10, 5, 10, 5);
        List<List<TextView>> lists=new ArrayList<>();
        String[] str=essay.split("\n");
        for(String s:str){
            String[] words=s.split(" ");
            List<TextView> list=new ArrayList<>();
            for(String word:words){
                TextView tv = view.findViewById(R.id.tvWordItem);
                tv.setText(word);
                tv.setMaxEms(10);
                tv.setSingleLine();
                //tv.setBackgroundResource(R.drawable.text_view_border);
                tv.setLayoutParams(layoutParams);
                list.add(tv);
            }
            lists.add(list);
            lists.add(new ArrayList<TextView>());
        }
        return lists;
    }
}
