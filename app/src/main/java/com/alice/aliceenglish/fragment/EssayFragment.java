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
import com.alice.aliceenglish.adapter.EssayListAdapter;
import com.alice.aliceenglish.entity.Essay;
import com.alice.aliceenglish.test.EssayData;

import java.util.ArrayList;
import java.util.List;

public class EssayFragment extends Fragment {
    private List<Essay> essays=new ArrayList<>();
    private ListView listView;
    private TextView tvNoneTip;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_listview, container, false);
        initData();
        initView(view);
        return view;
    }

    private void initData(){
        essays= EssayData.getEssays();
    }

    private void initView(View view){
        tvNoneTip=view.findViewById(R.id.tvNoneTip);
        listView = view.findViewById(R.id.listView);
        if(essays==null||essays.isEmpty()){
            tvNoneTip.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
        }else{
            listView.setVisibility(View.VISIBLE);
            tvNoneTip.setVisibility(View.GONE);
            EssayListAdapter essayListAdapter=new EssayListAdapter(getActivity(),R.layout.item_essay,essays);
            listView.setAdapter(essayListAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(getActivity(), EssayActivity.class);
                    Bundle mBundle = new Bundle();
                    mBundle.putSerializable("essay", essays.get(position));
                    intent.putExtras(mBundle);
                    startActivity(intent);
                }
            });
        }
    }

}
