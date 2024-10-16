package com.htx.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: htx
 * @GZH: 二哈学习之路
 * @Date: 2024/10/11 19:39
 * @Desc: 评论表
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Comments对象", description="评论表")
public class Comments implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "comment_ID", type = IdType.AUTO)
    private Long commentId;

    @ApiModelProperty(value = "对应文章ID")
    @TableField("comment_post_ID")
    private Long commentPostId;

    @ApiModelProperty(value = "评论者")
    private String commentAuthor;

    @ApiModelProperty(value = "评论者邮箱")
    private String commentAuthorEmail;

    @ApiModelProperty(value = "评论者网址")
    private String commentAuthorUrl;

    @ApiModelProperty(value = "评论者IP")
    @TableField("comment_author_IP")
    private String commentAuthorIp;

    @ApiModelProperty(value = "评论时间")
    private Date commentDate;

    @ApiModelProperty(value = "评论正文")
    private String commentContent;

    @ApiModelProperty(value = "评论是否被批准")
    private String commentApproved;

    @ApiModelProperty(value = "评论者的USER AGENT")
    private String commentAgent;

    @ApiModelProperty(value = "评论类型(pingback/普通)")
    private String commentType;

    @ApiModelProperty(value = "父评论ID")
    private Long commentParent;

    @ApiModelProperty(value = "评论者用户ID（不一定存在）")
    private Long userId;
}
