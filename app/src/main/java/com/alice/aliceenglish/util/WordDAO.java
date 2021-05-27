package com.alice.aliceenglish.util;

import android.content.Context;

import com.alice.aliceenglish.MyApplication;
import com.alice.aliceenglish.entity.Word;

import java.util.List;

public class WordDAO {
    private static DBAdapter wordDAO;

    public static DBAdapter getWordDAO() {
        if (wordDAO == null) {
            wordDAO = new DBAdapter(MyApplication.getContext());
            wordDAO.open();
        }
        return wordDAO;
    }
}
