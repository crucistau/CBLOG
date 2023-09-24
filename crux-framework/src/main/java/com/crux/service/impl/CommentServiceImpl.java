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
import com.crux.exception.SystemException;
import com.crux.mapper.CommentMapper;
import com.crux.service.CommentService;
import com.crux.service.UserService;
import com.crux.utils.BeanCopyUtils;
import com.crux.utils.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;


/**
 * @author moon
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Resource
    private UserService userService;
    @Override
    public ResponseResult commonList(String commentType, Long articleId, Integer pageNum, Integer pageSize) {
        //查询对应文章的根评论
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        //对articleId进行判断
        queryWrapper
                .eq(SystemConstants.ARTICLE_COMMENT.equals(commentType),Comment::getArticleId, articleId)
                //根评论对应的rootId为-1
                .eq(Comment::getRootId, SystemConstants.COMMENT_ROOT_ID);

        //评论类型
        queryWrapper.eq(Comment::getType,commentType);

        //分页查询
        Page<Comment> page= new Page(pageNum, pageSize);
        page(page, queryWrapper);
        //VO类进行封装返回
        List<CommentVo> commentVos = toCommentVoList(page.getRecords());

        //查询所有根评论对应的子评论集合，并且赋值给对应的属性
        commentVos.forEach(commentVo -> {
            //查询对应的子评论
            List<CommentVo> children = getChildren(commentVo.getId());
            commentVo.setChildren(children);
        });
        //空判断？
        if (page.getTotal() == 0){
            return  ResponseResult.errorResult(AppHttpCodeEnum.DATA_EMPTY);
        }
        return ResponseResult.okResult(new PageVo(commentVos, page.getTotal()));
    }

    /**
     * 新增评论
     * @param comment
     * @return
     */
    @Override
    @Transactional
    public ResponseResult addComment(Comment comment) {
        if (!StringUtils.hasText(comment.getContent())){
            throw new SystemException(AppHttpCodeEnum.CONTENT_NOT_NULL);
        }
        //获取登录用户
        Long userId = SecurityUtils.getUserId();
        comment.setCreateBy(userId);
        save(comment);
        return ResponseResult.okResult();
    }

    private List<CommentVo> toCommentVoList(List<Comment> comments){

        List<CommentVo> commentVo = BeanCopyUtils
                .copyBeanList(comments, CommentVo.class);
        //遍历vo通过查询用户名称以及toCommentUserId进行对userName以及toCommentUserName赋值
        commentVo.forEach(c->{
            String nickName = userService.getById(c.getCreateBy()).getNickName();
            c.setUsername(nickName);
            //通过toCommentUserId查询当前用户的昵称并赋值
            //如果toCommentUserId不为-1才进行查询
            c.getToCommentUserId();
            if (c.getToCommentUserId() != -1){
                c.setToCommentUserName(userService
                        .getById(c.getToCommentUserId()).getNickName());
            }

        });
        return commentVo;
    }

    /**
     * 通过当前评论的Id查找到对应子评论
     * @param id
     * @return
     */
    private List<CommentVo> getChildren(Long id){
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getRootId, id);
        queryWrapper.orderByAsc(Comment::getCreateBy);

        List<Comment> list = list(queryWrapper);
        return toCommentVoList(list);
    }
}