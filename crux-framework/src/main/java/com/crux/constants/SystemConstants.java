package com.crux.constants;

public class SystemConstants
{
    /**
     *  文章是草稿
     */
    public static final int ARTICLE_STATUS_DRAFT = 1;
    /**
     *  文章是正常分布状态
     */
    public static final int ARTICLE_STATUS_NORMAL = 0;

    /**
     *  分类是正常状态
     */
    public static final String STATUS_NORMAL = "0";

    /**
     * 友链状态为审核通过
     */
    public static final String  LINK_STATUS_NORMAL = "0";

    /**
     * 评论为根评论
     */
    public static final Integer  COMMENT_ROOT_ID = -1;

    /**
     * 评论类型为文章评论
     */
    public static final String ARTICLE_COMMENT = "0";

    /**
     * 评论类型为友链评论
     */
    public static final String LINK_COMMENT = "1";
}