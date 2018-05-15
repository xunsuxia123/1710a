package com.jk.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * Created by asus on 2018/4/25.
 */
@Document(collection = "words")
public class News implements Serializable{

    @Id
    private Integer newsid;

    private String newsname;

    private String newsintr;

    public Integer getNewsid() {
        return newsid;
    }

    public void setNewsid(Integer newsid) {
        this.newsid = newsid;
    }

    public String getNewsname() {
        return newsname;
    }

    public void setNewsname(String newsname) {
        this.newsname = newsname;
    }

    public String getNewsintr() {
        return newsintr;
    }

    public void setNewsintr(String newsintr) {
        this.newsintr = newsintr;
    }

    @Override
    public String toString() {
        return "News{" +
                "newsid=" + newsid +
                ", newsname='" + newsname + '\'' +
                ", newsintr='" + newsintr + '\'' +
                '}';
    }
}
