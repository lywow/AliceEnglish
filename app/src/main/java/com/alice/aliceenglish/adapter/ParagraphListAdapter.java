package com.alice.aliceenglish.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.alice.aliceenglish.R;
import com.alice.aliceenglish.view.BottomDialog;
import com.alice.aliceenglish.view.FlowLayout;
import java.util.List;

public class ParagraphListAdapter extends ArrayAdapter<String> {
    private int resourceId;

    public ParagraphListAdapter(Context context, int resource, List<String> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }

    @NonNull
    public View getView(int position, View convertView, ViewGroup parent) {
        String paragraph = getItem(position);//获取当前项的实例

        View itemView = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);

        FlowLayout flowLayout=itemView.findViewById(R.id.tvEssayContent);
        View blank=itemView.findViewById(R.id.blank);
        RelativeLayout relativeLayout=itemView.findViewById(R.id.layoutEssayOther);

        flowLayout.setVisibility(View.GONE);
        blank.setVisibility(View.GONE);
        relativeLayout.setVisibility(View.GONE);

        if(paragraph.length()>0){
            if(position==0){
                flowLayout.setVisibility(View.VISIBLE);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(5, 1, 5, 1);
                String[] words=paragraph.split(" ");
                for(final String word:words){
                    final TextView tv = new TextView(getContext());
                    tv.setTextSize(2,32);
                    tv.setTextColor(Color.parseColor("#000000"));
                    tv.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                    tv.setText(word);
                    tv.setSingleLine();
                    tv.setPadding(5, 2, 5, 1);
                    tv.setLayoutParams(layoutParams);
                    tv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            tv.setTextColor(Color.parseColor("#FFFFFF"));
                            tv.setBackgroundResource(R.drawable.text_view_border);
                            BottomDialog bottomDialog=new BottomDialog(getContext(),R.style.DialogTheme);
                            bottomDialog.init(word,tv);
                            bottomDialog.show();
                            bottomDialog.loadingView();
                        }
                    });
                    flowLayout.addView(tv,layoutParams);
                }
            }else if(position==2){
                relativeLayout.setVisibility(View.VISIBLE);
                TextView tag=itemView.findViewById(R.id.tvEssayTag);
                TextView wordNumber=itemView.findViewById(R.id.tvEssayWordNumber);
                TextView time=itemView.findViewById(R.id.tvEssayTime);
                String[] str=paragraph.split("=");
                tag.setText(str[1]);
                wordNumber.setText(str[2]);
                time.setText(str[3]);
            }else{
                flowLayout.setVisibility(View.VISIBLE);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(5, 1, 5, 1);
                String[] words=paragraph.split(" ");
                for(final String word:words){
                    final TextView tv = new TextView(getContext());
                    tv.setText(word);
                    tv.setTextColor(Color.parseColor("#000000"));
                    tv.setTextSize(2,20);
                    tv.setSingleLine();
                    tv.setPadding(5, 2, 5, 1);
                    tv.setLayoutParams(layoutParams);
                    tv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            tv.setTextColor(Color.parseColor("#FFFFFF"));
                            tv.setBackgroundResource(R.drawable.text_view_border);
                            BottomDialog bottomDialog=new BottomDialog(getContext(),R.style.DialogTheme);
                            bottomDialog.init(word,tv);
                            bottomDialog.show();
                            bottomDialog.loadingView();
                        }
                    });
                    flowLayout.addView(tv,layoutParams);
                }
            }
        }else{
            blank.setVisibility(View.VISIBLE);
        }

        return itemView;
    }

    private String getWordEssence(String english){
        char c=english.charAt(english.length());
        if(((c>='a'&&c<='z')   ||   (c>='A'&&c<='Z'))){
            return english;
        }else{
            return english.substring(0,english.length()-1);
        }
    }
}
