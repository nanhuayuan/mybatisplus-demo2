package com.poi.mybatisplus.demo2.mapper;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
public interface UserMapper extends BaseMapper<User> {

    /**
     * @Param(Constants. WRAPPE) 是固定写法。
     * ${ew.customSqlSegment}，ew就是Constants. WRAPPE，也是固定用法
     */

    @Select("select * from user ${ew.customSqlSegment}")
    List<User> selectAll(@Param(Constants.WRAPPER) Wrapper<User> wrapper);

    IPage<User> selectUserPage(Page<User> page, @Param(Constants.WRAPPER) Wrapper<User> wrapper);


    /*返回值可以是VO。也可以是实体类，
    但要要记住，如果用实体类接返回值，实体中非该实体对应表的数据库字段的属性上要标注@TableField(exist = false)，
    如果使用了条件构造器，条件构造器的字段名别忘了带别名。*/
    List<User> mySelectList(@Param(Constants.WRAPPER) Wrapper<User> wrapper);


    /**
     * 自定义SQL：默认也会增加多租户条件
     * 参考打印的SQL
     * @return
     */
    public Integer myCount();

    public List<User> getUserAndAddr(@Param("username") String username);

    public List<User> getUseAddr(@Param("username") String username);

    public List<User> getAddrAndUser(@Param("name") String name);

    public List<User> getAddrUser(@Param("name") String name);

}