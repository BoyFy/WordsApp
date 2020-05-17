package com.pers.remwords.entity;

public class Words {
    private String word;
    private String means;

    public Words(String word, String means) {
        this.word = word;
        this.means = means;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getMeans() {
        return means;
    }

    public void setMeans(String means) {
        this.means = means;
    }
}
