package com.crux.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.crux.entity.domain.ResponseResult;
import com.crux.entity.domain.entity.Category;


/**
 * 分类表(Category)表服务接口
 *
 * @author crucistau
 * @since 2023-09-01 18:01:58
 */
public interface CategoryService extends IService<Category> {

    ResponseResult getCategoryList();
}


