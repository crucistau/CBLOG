package com.crux.controller;

import com.crux.entity.domain.ResponseResult;
import com.crux.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


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

   @PutMapping("/updateViewCount/{id}")
   public ResponseResult updateViewCount(@PathVariable Long id){
        return articleService.updateViewCount(id);
   }
    @GetMapping("/{id}")
    public ResponseResult getArticleDetail(@PathVariable("id") Long id){
        return articleService.getArticleDetail(id);
    }
}
