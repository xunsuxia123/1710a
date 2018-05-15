package com.jk.dao;

import com.jk.model.News;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by asus on 2018/4/25.
 * @Mapping
 */
@Repository
public interface NewDao {

    /**
     * @Mapper
     * @S()
     *
     * @return
     */

    List<News> queryNews();

    void deleteNews(@Param("newsid")Integer newsid);

    void addNews(News news);

    News queryByIdNews(@Param("newsid")Integer newsid);

    void updateNews(News news);

}
