package com.crux.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.crux.entity.domain.entity.Tag;
import com.crux.mapper.TagMapper;
import com.crux.service.TagService;
import org.springframework.stereotype.Service;

/**
 * 标签(Tag)表服务实现类
 *
 * @author crucistau
 * @since 2023-11-09 13:03:26
 */
@Service("tagService")
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

}
