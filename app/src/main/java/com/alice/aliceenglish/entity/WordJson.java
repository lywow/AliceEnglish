package com.alice.aliceenglish.entity;

import java.io.Serializable;
import java.util.List;

public class WordJson implements Serializable {
    private String query;//": "hello",
    private String errorCode;//": "0",
    private Basic basic;

    public WordJson(){}

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public Basic getBasic() {
        return basic;
    }

    public void setBasic(Basic basic) {
        this.basic = basic;
    }

    class Basic{
        private String phonetic;//": "həˈləʊ",
        private List<String> explains;//": ["int. 喂；哈罗，你好，您好", "n. 表示问候， 惊奇或唤起注意时的用语", "n. (Hello) 人名；（法）埃洛"],

        public Basic(){}

        public String getPhonetic() {
            return phonetic;
        }

        public void setPhonetic(String phonetic) {
            this.phonetic = phonetic;
        }

        public List<String> getExplains() {
            return explains;
        }

        public void setExplains(List<String> explains) {
            this.explains = explains;
        }
    }

    public Word toWord(){
        Word word=new Word();
        word.setEnglish(query);
        word.setFrequency(0);
        word.setChinese("");
        if(basic==null){
            word.setPhonetic("暂无音标");
            word.setChinese("查询不到翻译");
            word.setFrequency(-1);
        }else{
            if(basic.phonetic==null||basic.phonetic.length()>0){
                word.setPhonetic(basic.phonetic);
            }else{
                word.setPhonetic("暂无音标");
            }
            for(String chinese : basic.explains){
                word.setChinese(word.getChinese()+chinese+"\n");
            }
        }
        return word;
    }
}
