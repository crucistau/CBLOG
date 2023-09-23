package com.crux.controller;

import com.crux.entity.domain.ResponseResult;
import com.crux.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author crucistau
 **/
@RequiredArgsConstructor
@RestController
@RequestMapping("/article")
public class ArticleController {


    private final ArticleService articleService;

    @GetMapping("/hotArticleList")
    public ResponseResult hotArticleList(){
        ResponseResult result = articleService.hotArticleList();
        return result;
   }
   
   @GetMapping("/articleList")
    public ResponseResult articleList(Integer pageNum, Integer pageSize, Long category){
        return articleService.articleList(pageNum, pageSize, category);
   }

    @GetMapping("/{id}")
    public ResponseResult getArticleDetail(@PathVariable("id") Long id){
        return articleService.getArticleDetail(id);
    }
}
