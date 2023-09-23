package com.crux.controller;

import com.crux.entity.domain.ResponseResult;
import com.crux.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.RequestScope;

/**
 * @author crucistau
 **/
@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/commonList")
    public ResponseResult commonList(Long articleId, Integer pageNum, Integer pageSize){
        return commentService.commonList(articleId, pageNum, pageSize);
    }
}
