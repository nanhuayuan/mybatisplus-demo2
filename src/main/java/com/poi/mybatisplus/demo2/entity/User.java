package com.poi.mybatisplus.demo2.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @Description: 用户
 * @Author: songkai
 * @Date: 2020/11/9
 * @Version: 1.0
 */
@Data
public class User {

    @TableId
    private Long id;
    //@TableField(condition = SqlCondition)
    private String Name;
    //@TableField(condition="%s&lt;#{%s}")
    //@TableField(condition="%s IN (%s)")
    private Integer age;
    private String email;
    //@TableField("manager_id")
    private Long managerId ;

    //创建时间
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    //更新时间
    @TableField(fill = FieldFill.UPDATE)//也可以插入，更新时填充
    private LocalDateTime updateTime;

    @TableLogic//逻辑删除标识
    @TableField(select = false)//不检索出来
    private Integer deleted;
    @Version
    private Integer version;
}
