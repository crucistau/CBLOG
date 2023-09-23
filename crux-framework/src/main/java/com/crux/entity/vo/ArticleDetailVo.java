package com.crux.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author crucistau
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDetailVo {
    private Long id;
    private Long categoryId;
    private String categoryName;
    private Date createTime;
    private String title;
    private String viewCount;
    private String isComment;
    private String content;

}
