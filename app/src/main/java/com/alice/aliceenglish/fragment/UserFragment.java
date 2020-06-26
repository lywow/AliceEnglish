package com.alice.aliceenglish.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import com.alice.aliceenglish.R;
import com.alice.aliceenglish.activity.MainActivity;
import com.alice.aliceenglish.activity.MemoryActivity;
import com.alice.aliceenglish.activity.SearchActivity;
import com.alice.aliceenglish.util.WordDAO;
import com.hb.dialog.dialog.ConfirmDialog;
import com.xuexiang.xui.widget.dialog.materialdialog.MaterialDialog;

public class UserFragment extends Fragment {
    private View view;
    private LinearLayout layoutDeleteAllWord;
    private LinearLayout layoutMemoryWord;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_user, container, false);
        initData();
        initView();
        return view;
    }

    public void initData() {

    }

    public void initView() {
        layoutDeleteAllWord=view.findViewById(R.id.layoutDeleteAllWord);
        layoutMemoryWord=view.findViewById(R.id.layoutMemoryWord);

        layoutDeleteAllWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ConfirmDialog confirmDialog = new ConfirmDialog(getActivity());
                confirmDialog.setLogoImg(R.mipmap.dialog_notice).setMsg("确定要删除所有单词记录吗？");
                confirmDialog.setClickListener(new ConfirmDialog.OnBtnClickListener() {
                    @Override
                    public void ok() {
                        WordDAO.getWordDAO().deleteAllData();
                        Toast.makeText(getActivity(), "已经删除了所有单词记录了", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void cancel() {
                        confirmDialog.cancel();
                    }
                });
                confirmDialog.show();
            }
        });

        layoutMemoryWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MemoryActivity.class);
                startActivity(intent);
            }
        });
    }
}