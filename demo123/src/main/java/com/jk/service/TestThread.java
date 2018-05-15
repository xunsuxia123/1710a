package com.jk.service;

import com.jk.model.News;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by asus on 2018/5/7.
 */
public class TestThread implements Runnable{
    @Autowired
    private NewsService newsService;

    private News news;

    @Autowired
    private AmqpTemplate amqpTemplate;

    public TestThread(NewsService newsService, News news) {
        amqpTemplate.convertAndSend("1369",news);
    }

    @Override
    public void run() {
        newsService.say(news);
    }

    public NewsService getNewsService() {
        return newsService;
    }

    public void setNewsService(NewsService newsService) {
        this.newsService = newsService;
    }

    public News getNews() {
        return news;
    }

    public void setNews(News news) {
        this.news = news;
    }
}
