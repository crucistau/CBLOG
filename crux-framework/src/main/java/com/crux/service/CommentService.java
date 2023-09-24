package com.crux.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.crux.entity.domain.ResponseResult;
import com.crux.entity.domain.entity.Comment;

/**
* @author moon
* @description 针对表【c_comment(评论表)】的数据库操作Service
* @createDate 2023-09-22 19:51:14
*/
public interface CommentService extends IService<Comment> {

    ResponseResult commonList(String commentType, Long articleId, Integer pageNum, Integer pageSize);

    ResponseResult addComment(Comment comment);
}
