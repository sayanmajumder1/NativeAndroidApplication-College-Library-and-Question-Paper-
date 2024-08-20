package com.example.myapplication.models;

public class TextBook {
    private final   String chapterName;
    private final String    chapter;

    public TextBook(String chapterName, String chapter) {
        this.chapterName = chapterName;
        this.chapter = chapter;
    }

    public String getChapterName() {
        return chapterName;
    }

    public String getChapter() {
        return chapter;
    }
}
