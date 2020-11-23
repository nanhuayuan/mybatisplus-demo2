package com.poi.mybatisplus.demo2.mapper;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.poi.mybatisplus.demo2.entity.Tenant;
import com.poi.mybatisplus.demo2.entity.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description: mapper
 * @Author: songkai
 * @Date: 2020/11/9
 * @Version: 1.0
 */
@Repository
public interface TenantMapper extends BaseMapper<Tenant> {

}