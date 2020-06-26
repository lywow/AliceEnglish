package com.alice.aliceenglish.util;

import android.content.Context;

import com.alice.aliceenglish.entity.Word;

import java.util.List;

public class WordDAO {
    private static DBAdapter wordDAO;

    public WordDAO(Context context){
        wordDAO=new DBAdapter(context);
        wordDAO.open();
    }

    public static DBAdapter getWordDAO() {
        return wordDAO;
    }
}
