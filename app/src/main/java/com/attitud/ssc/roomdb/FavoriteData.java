package com.attitud.ssc.roomdb;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class FavoriteData {

    private String id;
    private String quote;
    private String author;
    @PrimaryKey(autoGenerate = true)
    private int index;

    public FavoriteData(String id, String quote, String author) {
        this.id = id;
        this.quote = quote;
        this.author = author;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
