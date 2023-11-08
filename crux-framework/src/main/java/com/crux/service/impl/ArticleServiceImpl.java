package com.crux.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.crux.constants.SystemConstants;
import com.crux.entity.domain.entity.Article;
import com.crux.entity.domain.ResponseResult;
import com.crux.entity.domain.entity.Category;
import com.crux.entity.vo.ArticleDetailVo;
import com.crux.entity.vo.ArticleListVo;
import com.crux.entity.vo.HotArticleVo;
import com.crux.entity.vo.PageVo;
import com.crux.mapper.ArticleMapper;
import com.crux.service.ArticleService;
import com.crux.service.CategoryService;
import com.crux.utils.BeanCopyUtils;
import com.crux.utils.RedisCache;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.crux.constants.RedisConstants.ARTICLE_VIEWCOUNT;
import static com.crux.constants.RedisConstants.COUNT;

/**
 * 文章表(Article)表服务实现类
 *
 * @author crucistau
 * @since 2023-08-31 15:17:26
 */
@Service
@RequiredArgsConstructor
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Autowired
    private  CategoryService categoryService;

    @Resource
    private RedisCache redisCache;
    @Override
    public ResponseResult hotArticleList() {
        //查询热门文章，封装成ResponseResult返回（只有十条）
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        //文章不能是草稿，逻辑删除的不显示
        //按照浏览量进行排序
        queryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_DRAFT)
                .orderByDesc(Article::getViewCount);
        //最多只有十条
        Page<Article> page = new Page(1,10);
        page(page, queryWrapper);

        //封装成VO对象
        List<Article> list = page.getRecords();
        //bean拷贝
        List<HotArticleVo> collect = list.stream().map(article -> {
            HotArticleVo articleVo = new HotArticleVo();
            BeanUtils.copyProperties(article, articleVo);
            return articleVo;
        }).collect(Collectors.toList());
        //BeanUtil.copyProperties(list,collect);
        return ResponseResult.okResult(collect);
    }

    @Override
    public ResponseResult articleList(Integer pageNum, Integer pageSize, Long category) {
        //在首页和分类中都需要查询文章列表（进行分页查询）
        //查询条件
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        //如果有categoryId则要传入
        queryWrapper.eq(Objects.nonNull(category) && category > 0,Article::getCategoryId, category);
        //文章状态为发布的
        queryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        //对isTop进行排序
        queryWrapper.orderByDesc(Article::getIsTop);

        //查询条件
        Page<Article> page = new Page<>(pageNum, pageSize);
        page(page, queryWrapper);
        List<Article> articles = page.getRecords();
        //查询categoryName
        for (Article article : articles) {
            Category category1 = categoryService.getById(article.getCategoryId());
            article.setCategoryName(category1.getName());
        }
        //封装查询结果
        List<ArticleListVo> articleListVos = BeanCopyUtils
                .copyBeanList(page.getRecords(), ArticleListVo.class);
        //System.out.println(page.getRecords());
        PageVo pageVo = new PageVo(articleListVos,page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult getArticleDetail(Long id) {
        //根据id查询文章
        Article article = getById(id);
        //从Redis中查询浏览量
        Integer viewCount = redisCache.getCacheMapValue(ARTICLE_VIEWCOUNT, id.toString());
        article.setViewCount(viewCount.longValue());
        //转换成VO
        ArticleDetailVo articleDetailVo = BeanCopyUtils.copyBean(article, ArticleDetailVo.class);
        //根据分类id查询分类名
        Long categoryId = articleDetailVo.getCategoryId();
        Category category = categoryService.getById(categoryId);
        if(category!=null){
            articleDetailVo.setCategoryName(category.getName());
        }
        //封装响应返回
        return ResponseResult.okResult(articleDetailVo);
    }

    @Override
    public ResponseResult updateViewCount(Long id) {
        redisCache.incrementCacheMapValue(ARTICLE_VIEWCOUNT, id.toString(), COUNT);
        return ResponseResult.okResult();
    }
}

