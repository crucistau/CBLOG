package com.crux.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.crux.entity.domain.entity.Article;
import com.crux.entity.domain.ResponseResult;


/**
 * 文章表(Article)表服务接口
 *
 * @author crucistau
 * @since 2023-08-31 15:17:24
 */
public interface ArticleService extends IService<Article> {
    ResponseResult hotArticleList();

    ResponseResult articleList(Integer pageNum, Integer pageSize, Long category);

    ResponseResult getArticleDetail(Long id);

    ResponseResult updateViewCount(Long id);
}

