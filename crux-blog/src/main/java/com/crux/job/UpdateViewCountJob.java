package com.crux.job;

import com.crux.entity.domain.entity.Article;
import com.crux.service.ArticleService;
import com.crux.utils.RedisCache;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.crux.constants.RedisConstants.ARTICLE_VIEWCOUNT;

/**
 * 每个一段时间将Redis中的文件浏览量更新到数据库中
 * @author crucistau
 **/
@Component
public class UpdateViewCountJob {
    @Resource
    private RedisCache redisCache;
    @Resource
    private ArticleService articleService;

    @Scheduled(cron = "0/5 * * * * ?")
    public void updateViewCount(){
        //获取Redis中的浏览量
        Map<String, Integer> viewCountMap = redisCache.getCacheMap(ARTICLE_VIEWCOUNT);

        //先将双列集合转换为单列集合
        List<Article> articleList = viewCountMap.entrySet()
                .stream()
                .map(entry -> new Article(Long.valueOf(entry.getKey()), entry.getValue().longValue()))
                .collect(Collectors.toList());

        //更新到数据库中（批量更新）
        articleService.updateBatchById(articleList);

    }
}
