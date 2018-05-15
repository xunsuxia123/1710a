package com.jk.service;

import com.jk.dao.NewDao;
import com.jk.model.News;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by asus on 2018/4/25.
 */
@Service
public class NewsService {

    @Autowired
    private NewDao newsDao;
    /**
     *
     */
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private AmqpTemplate amqpTemplate;


    @Cacheable(key = "queryNews",value = "redis")
    public List<News> queryNews() {
        List<News> list = newsDao.queryNews();

        redisTemplate.opsForValue().set("redisCache",list);

        mongoTemplate.insert(News.class);

        //mongoTemplate.find(new Query(),News.class);

        return list;
    }

    public void deleteNews(Integer newsid) {
        newsDao.deleteNews(newsid);
    }

    public void addNews(News news) {
        newsDao.addNews(news);
    }

    public News queryByIdNews(Integer newsid) {
       return newsDao.queryByIdNews(newsid);
    }

    public void updateNews(News news) {
       newsDao.updateNews(news);
    }

    public void say(News news) {
        amqpTemplate.convertAndSend("123",news);
        System.out.print("news");
    }
}
