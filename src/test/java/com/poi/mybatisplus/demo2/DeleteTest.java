package com.poi.mybatisplus.demo2;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.poi.mybatisplus.demo2.entity.Tenant;
import com.poi.mybatisplus.demo2.entity.User;
import com.poi.mybatisplus.demo2.mapper.TenantMapper;
import com.poi.mybatisplus.demo2.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DeleteTest {


/*    逻辑删除实现
    1.配置文件
    逻辑删除是1，未删除是0(默认)
    2.配置类
    逻辑删除3.1.1以上自动生效，不需要配置。3.1.1以下需要配置
    @Configuration
    public class MybatisPlusConfiguration {
        @Bean
        public ISqllnjector sqllnjector() {
            return new LegieSellnjecter();
        }
    }
    3.对应实体类标识逻辑删除属性
    可以设置局部删除的标识，但是一般不建议使用

    @TableLogid
    private Integer deleted;

    */
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private TenantMapper tenantMapper;
    @Test
    public void doDeleteTest() {
        int rows = userMapper.deleteById(1094592041087729666L);
        System.out.println("影响行数："+rows);
    }
/*
//执行的是更新语句 其他删除也是差不多
==>  Preparing: UPDATE user SET deleted=1 WHERE id=? AND deleted=0
==> Parameters: 1094592041087729666(Long)
<==    Updates: 1
Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@59546cfe]
影响行数：1
    */


    /*
    查询
        */
    @Test
    public void testSelect() {
        List<User> userList = userMapper.selectList(null);
        //Assert.assertEquals(5, userList.size());
        userList.forEach(System.out::println);
    }
/*
//实际数据五条，查出来只有一条，因为已经加上了一个条件
==>  Preparing: SELECT id,name,age,email,manager_id,create_time,update_time,deleted,version FROM user WHERE deleted=0
==> Parameters:
<==    Columns: id, name, age, email, manager_id, create_time, update_time, deleted, version
<==        Row: 1087982257332887553, 大boss, 40, boss@baomidou.com, null, 2019-01-11 14:20:20, null, 0, 1
<==        Row: 1088248166370832385, 王天风, 25, wtf@baomidou.com, 1087982257332887553, 2019-02-05 11:12:22, null, 0, 1
<==        Row: 1088250446457389058, 李艺伟, 28, lyw@baomidou.com, 1088248166370832385, 2019-02-14 08:31:16, null, 0, 1
<==        Row: 1094590409767661570, 张雨琪, 31, zjq@baomidou.com, 1088248166370832385, 2019-01-14 09:15:15, null, 0, 1
<==      Total: 4
Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@29ea78b1]
User(id=1087982257332887553, Name=大boss, age=40, email=boss@baomidou.com, managerId=null, createTime=2019-01-11T14:20:20, updateTime=null, deleted=0, version=1)
User(id=1088248166370832385, Name=王天风, age=25, email=wtf@baomidou.com, managerId=1087982257332887553, createTime=2019-02-05T11:12:22, updateTime=null, deleted=0, version=1)
User(id=1088250446457389058, Name=李艺伟, age=28, email=lyw@baomidou.com, managerId=1088248166370832385, createTime=2019-02-14T08:31:16, updateTime=null, deleted=0, version=1)
User(id=1094590409767661570, Name=张雨琪, age=31, email=zjq@baomidou.com, managerId=1088248166370832385, createTime=2019-01-14T09:15:15, updateTime=null, deleted=0, version=1)
    */

    /*
        更新
    */
    @Test
    public void testUpdate() {
        User user=new User();
        user.setAge(26);
        user.setId(1088248166370832385L);
        int rows=userMapper.updateById(user);
        System.out.println("影响行数："+ rows );
    }
/*
//也添加了删除标识
==>  Preparing: UPDATE user SET age=? WHERE id=? AND deleted=0
==> Parameters: 26(Integer), 1088248166370832385(Long)
<==    Updates: 1
Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@2d5f7182]
影响行数：1
    */

    // TODO 注意事项
    /*
    查询中排除删除标识字段及注意事项
    查询中把delete字段也给查出来了，不想查出来
    可以用在属性中使用注解
    @TableLogic//逻辑删除标识
    @TableField(select = false)//不检索出来
    private Integer deleted;
*/
    @Test
    public void testSelect2() {
        List<User> userList = userMapper.selectList(null);
        //Assert.assertEquals(5, userList.size());
        userList.forEach(System.out::println);
    }
/*
//检索的字段不包括删除标识
==>  Preparing: SELECT id,name,age,email,manager_id,create_time,update_time,version FROM user WHERE deleted=0
==> Parameters:
<==    Columns: id, name, age, email, manager_id, create_time, update_time, version
<==        Row: 1087982257332887553, 大boss, 40, boss@baomidou.com, null, 2019-01-11 14:20:20, null, 1
<==        Row: 1088248166370832385, 王天风, 26, wtf@baomidou.com, 1087982257332887553, 2019-02-05 11:12:22, null, 1
<==        Row: 1088250446457389058, 李艺伟, 28, lyw@baomidou.com, 1088248166370832385, 2019-02-14 08:31:16, null, 1
<==        Row: 1094590409767661570, 张雨琪, 31, zjq@baomidou.com, 1088248166370832385, 2019-01-14 09:15:15, null, 1
<==      Total: 4
Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@58294867]
User(id=1087982257332887553, Name=大boss, age=40, email=boss@baomidou.com, managerId=null, createTime=2019-01-11T14:20:20, updateTime=null, deleted=null, version=1)
User(id=1088248166370832385, Name=王天风, age=26, email=wtf@baomidou.com, managerId=1087982257332887553, createTime=2019-02-05T11:12:22, updateTime=null, deleted=null, version=1)
User(id=1088250446457389058, Name=李艺伟, age=28, email=lyw@baomidou.com, managerId=1088248166370832385, createTime=2019-02-14T08:31:16, updateTime=null, deleted=null, version=1)
User(id=1094590409767661570, Name=张雨琪, age=31, email=zjq@baomidou.com, managerId=1088248166370832385, createTime=2019-01-14T09:15:15, updateTime=null, deleted=null, version=1)
    */

    /*
        自定义sql不生效
    */
    @Test
    public void testSql() {
        List<User> userList = userMapper.mySelectList(Wrappers.<User>lambdaQuery());
        userList.forEach(System.out::println);
    }
/*
//这次查出来的就是五条了 可以看到没有加 delete = 1
这个时候的处理办法有两种,一种是写在sql,另一种是写在warpper
==>  Preparing: SELECT * FROM user
==> Parameters:
<==    Columns: id, name, age, email, manager_id, create_time, update_time, version, deleted
<==        Row: 1087982257332887553, 大boss, 40, boss@baomidou.com, null, 2019-01-11 14:20:20, null, 1, 0
<==        Row: 1088248166370832385, 王天风, 26, wtf@baomidou.com, 1087982257332887553, 2019-02-05 11:12:22, null, 1, 0
<==        Row: 1088250446457389058, 李艺伟, 28, lyw@baomidou.com, 1088248166370832385, 2019-02-14 08:31:16, null, 1, 0
<==        Row: 1094590409767661570, 张雨琪, 31, zjq@baomidou.com, 1088248166370832385, 2019-01-14 09:15:15, null, 1, 0
<==        Row: 1094592041087729666, 刘红雨, 32, lhm@baomidou.com, 1088248166370832385, 2019-01-14 09:48:16, null, 1, 1
<==      Total: 5
Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@30ec7d21]
User(id=1087982257332887553, Name=大boss, age=40, email=boss@baomidou.com, managerId=null, createTime=2019-01-11T14:20:20, updateTime=null, deleted=0, version=1)
User(id=1088248166370832385, Name=王天风, age=26, email=wtf@baomidou.com, managerId=1087982257332887553, createTime=2019-02-05T11:12:22, updateTime=null, deleted=0, version=1)
User(id=1088250446457389058, Name=李艺伟, age=28, email=lyw@baomidou.com, managerId=1088248166370832385, createTime=2019-02-14T08:31:16, updateTime=null, deleted=0, version=1)
User(id=1094590409767661570, Name=张雨琪, age=31, email=zjq@baomidou.com, managerId=1088248166370832385, createTime=2019-01-14T09:15:15, updateTime=null, deleted=0, version=1)
User(id=1094592041087729666, Name=刘红雨, age=32, email=lhm@baomidou.com, managerId=1088248166370832385, createTime=2019-01-14T09:48:16, updateTime=null, deleted=1, version=1)
    */
}
