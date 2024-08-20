package com.example.myapplication.models;
// Book Model For All Books This model set And Controlled All   Book  title Cover Url And Type
public class Books {

    private final String title;
    private final String  cover;
    private final String    content;
    private final String  type;


    public Books(String title, String cover, String content, String type) {
        this.title = title;
        this.cover = cover;
        this.content = content;
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public String getCover() {
        return cover;
    }

    public String getContent() {
        return content;
    }

    public String getType() {
        return type;
    }
}


