package com.crux.runner;

import com.crux.entity.domain.entity.Article;
import com.crux.mapper.ArticleMapper;
import com.crux.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 在应用启动的时候将博客的浏览量读取到Redis中
 * @author crucistau
 **/
@Component
public class ViewCountRunner implements CommandLineRunner {
    @Resource
    private ArticleMapper articleMapper;

    @Resource
    private RedisCache redisCache;
    @Override
    public void run(String... args) throws Exception {
        //查询博客信息 Id viewCount
        List<Article> articles = articleMapper.selectList(null);
        Map<String, Integer> collect = articles.stream()
                .collect(Collectors.toMap(article1 -> article1.getId().toString(), article -> {
            return article.getViewCount().intValue();
        }));
        //存储到Redis中
        redisCache.setCacheMap("article:viewCount",collect);
    }
}
