package com.crux.entity.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 文章标签关联表(ArticleTag)表实体类
 *
 * @author crucistau
 * @since 2023-08-31 15:22:33
 */
@SuppressWarnings("serial")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleTag {

    //文章id
    @TableId
    private Long articleId;
    //标签id
    private Long tagId;

}

