package com.crux.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.crux.entity.domain.ResponseResult;
import com.crux.entity.domain.entity.Tag;
import com.crux.entity.dto.TagListDto;


/**
 * 标签(Tag)表服务接口
 *
 * @author crucistau
 * @since 2023-11-09 13:03:26
 */
public interface TagService extends IService<Tag> {

    ResponseResult pageTagList(Integer pageNum, Integer pageSize, TagListDto tagListDto);
}


