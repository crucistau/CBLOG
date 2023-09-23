package com.crux.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.crux.entity.domain.ResponseResult;
import com.crux.entity.domain.entity.Link;

public interface LinkService extends IService<Link> {
    ResponseResult getAllLink();
}

