package com.crux.controller;

import com.crux.constants.SystemConstants;
import com.crux.entity.domain.ResponseResult;
import com.crux.entity.domain.entity.Comment;
import com.crux.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.RequestScope;

/**
 * @author crucistau
 **/
@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/commentList")
    public ResponseResult commonList(Long articleId, Integer pageNum, Integer pageSize){
        return commentService.commonList(SystemConstants.ARTICLE_COMMENT,articleId, pageNum, pageSize);
    }

    @PostMapping
    public ResponseResult addComment(@RequestBody Comment comment){
        return commentService.addComment(comment);
    }

    @GetMapping("/linkCommentList")
    public ResponseResult linkCommentList(Integer pageNum, Integer pageSize){
        return commentService.commonList(SystemConstants.LINK_COMMENT,null, pageNum, pageSize);
    }
}
