package com.crux.service.impl;



import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.crux.constants.SystemConstants;
import com.crux.entity.domain.ResponseResult;
import com.crux.entity.domain.entity.Comment;
import com.crux.entity.vo.CommentVo;
import com.crux.entity.vo.PageVo;
import com.crux.enums.AppHttpCodeEnum;
import com.crux.mapper.CommentMapper;
import com.crux.service.CommentService;
import com.crux.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @author moon
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Override
    public ResponseResult commonList(Long articleId, Integer pageNum, Integer pageSize) {
        //查询对应文章的根评论
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        //对articleId进行判断
        queryWrapper.eq(Comment::getArticleId, articleId)
                //根评论对应的rootId为-1
                .eq(Comment::getRootId, SystemConstants.COMMENT_ROOT_ID);
        //分页查询
        Page<Comment> page= new Page(pageNum, pageSize);
        page(page, queryWrapper);
        //VO类进行封装返回
        List<CommentVo> commentVos = BeanCopyUtils
                .copyBeanList(page.getRecords(), CommentVo.class);
        //空判断？
        if (page.getTotal() == 0){
            return  ResponseResult.errorResult(AppHttpCodeEnum.DATA_EMPTY);
        }
        return ResponseResult.okResult(new PageVo(commentVos, page.getTotal()));
    }
}