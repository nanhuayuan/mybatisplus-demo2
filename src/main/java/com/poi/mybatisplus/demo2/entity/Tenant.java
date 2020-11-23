package com.poi.mybatisplus.demo2.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Description: 租户
 * @Author: songkai
 * @Date: 2020/11/11
 * @Version: 1.0
 */
@Data
public class Tenant {
    @TableId
    private Long id;
    private String Name;
    private String pid;
    private String tenantId;

    @TableField(exist = false)
    private List<Tenant> children;
}
