package com.alice.aliceenglish.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.alice.aliceenglish.R;
import com.alice.collector.CollectorConfig;
import com.xuexiang.xui.widget.edittext.ValidatorEditText;

public class ChangeUserActivity extends AppCompatActivity {

    private ValidatorEditText validatorEditText;
    private Button bn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_user);
        initData();
        initView();
    }

    private void initData() {
    }

    private void initView() {
        findViewById(R.id.ivBack).setVisibility(View.VISIBLE);
        findViewById(R.id.ivBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        findViewById(R.id.ivSearch).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChangeUserActivity.this,SearchActivity.class);
                startActivity(intent);
            }
        });

        validatorEditText = findViewById(R.id.vetChangeUser);
        validatorEditText.setHint(CollectorConfig.user+"");
        bn = findViewById(R.id.bnChangeUser);

        bn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str = validatorEditText.getInputValue();
                try {
                    int uu = Integer.parseInt(str);
                    if (uu >= 1000 && uu < 1100) {
                        CollectorConfig.user = uu;
                    }
                    Toast.makeText(ChangeUserActivity.this,
                            "成功修改为" + uu,
                            Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(ChangeUserActivity.this,
                            "请输入正确的账号，1000~1099",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
