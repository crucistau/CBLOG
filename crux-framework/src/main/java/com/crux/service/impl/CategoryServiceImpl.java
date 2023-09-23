package com.crux.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.crux.constants.SystemConstants;
import com.crux.entity.domain.ResponseResult;
import com.crux.entity.domain.entity.Article;
import com.crux.entity.vo.CategoryVo;
import com.crux.mapper.CategoryMapper;
import com.crux.service.ArticleService;
import com.crux.service.CategoryService;
import com.crux.utils.BeanCopyUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.crux.entity.domain.entity.Category;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 分类表(Category)表服务实现类
 *
 * @author crucistau
 * @since 2023-09-01 18:01:58
 */
@RequiredArgsConstructor
@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    private final ArticleService articleService;
    @Override
    public ResponseResult getCategoryList() {
        //查询文章表，状态为已发布
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        List<Article> list = articleService.list(queryWrapper);
        //获取文章的id,并且去重
        Set<Long> categoryIds = list.stream().map(Article::getCategoryId)
                .collect(Collectors.toSet());
        //查询分类表,要求状态为正常的状态
        List<Category> categories = listByIds(categoryIds);
        //封装VO
        categories.stream()
                .filter(category -> SystemConstants.STATUS_NORMAL.equals(category.getStatus()));
        //List<CategoryVo> categoryVos = new ArrayList<>();
        //BeanUtil.copyProperties(categories, categoryVos);
        List<CategoryVo> categoryVos = BeanCopyUtils.copyBeanList(categories, CategoryVo.class);
        return ResponseResult.okResult(categoryVos);
    }
}
