package com.msb.mall.pay.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 用户表 传输实体
 * </p>
 *
 * @author 马士兵 · 项目架构部
 * @version V1.0
 * @contact
 * @date 2020-08-12
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="KsUser传输实体", description="用户表传输实体")
public class KsUserDTO implements Serializable {

    //private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户编号")
    private String userNo;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "手机号码")
    private String phone;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "性别：0->未知；1->男；2->女")
    private Integer gender;

    @ApiModelProperty(value = "生日，出生年月日，格式：2020-03-01")
    private String birthday;

    @ApiModelProperty(value = "所在城市")
    private String city;

    @ApiModelProperty(value = "职业")
    private String job;

    @ApiModelProperty(value = "是否超管，0-否，1-是")
    private Boolean managerFlag;

    @ApiModelProperty(value = "个性签名")
    private String personalizedSignature;

    private Long memberLevelId;

    @ApiModelProperty(value = "用户来来源")
    private Integer sourceType;

    @ApiModelProperty(value = "密码重置时间")
    private Long pwdResetTime;

    @ApiModelProperty(value = "用户类型: 0- 普通用户；2-超级管理员")
    private Integer userType;

}
