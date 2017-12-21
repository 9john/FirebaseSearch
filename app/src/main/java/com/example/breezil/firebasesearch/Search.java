package com.example.breezil.firebasesearch;

/**
 * Created by breezil on 12/20/2017.
 */

public class Search {

    private String title;
    private  String desc;

    public Search() {
    }

    public Search(String title, String desc) {

        this.title = title;
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
