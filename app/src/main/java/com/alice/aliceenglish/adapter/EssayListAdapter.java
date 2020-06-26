package com.alice.aliceenglish.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.alice.aliceenglish.R;
import com.alice.aliceenglish.entity.Essay;

import java.util.List;

public class EssayListAdapter extends ArrayAdapter<Essay> {
    private int resourceId;

    public EssayListAdapter(Context context, int resource, List<Essay> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }

    @NonNull
    public View getView(int position, View convertView, ViewGroup parent) {
        Essay essay = getItem(position);//获取当前项的实例
        View view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);

        ImageView image = view.findViewById(R.id.tvEssayImage);
        TextView title=view.findViewById(R.id.tvEssayTitle);
        TextView describe=view.findViewById(R.id.tvEssayDescribe);
        TextView tag=view.findViewById(R.id.tvEssayTag);
        TextView wordNumber=view.findViewById(R.id.tvEssayWordNumber);
        TextView time=view.findViewById(R.id.tvEssayTime);

        title.setText(essay.getTitle());
        describe.setText(essay.getDescribe());
        tag.setText(essay.getTag());
        wordNumber.setText(essay.getWordNumber()+"词");
        time.setText(essay.getTime());

        return view;
    }

}
